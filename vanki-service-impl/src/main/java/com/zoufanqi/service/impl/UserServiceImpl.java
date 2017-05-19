package com.zoufanqi.service.impl;

import com.alibaba.fastjson.JSON;
import com.zoufanqi.consts.ConstDB;
import com.zoufanqi.consts.ConstService;
import com.zoufanqi.consts.EnumRedisKey;
import com.zoufanqi.entity.Picture;
import com.zoufanqi.entity.User;
import com.zoufanqi.entity.UserExample;
import com.zoufanqi.exception.ZouFanqiException;
import com.zoufanqi.mapper.UserMapper;
import com.zoufanqi.result.ResultBuilder;
import com.zoufanqi.result.ResultJson;
import com.zoufanqi.service.PictureService;
import com.zoufanqi.service.UserService;
import com.zoufanqi.service.redis.RedisTemplate;
import com.zoufanqi.status.EnumStatusCode;
import com.zoufanqi.utils.EncryptUtil;
import com.zoufanqi.utils.RegexUtil;
import com.zoufanqi.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by vanki on 2017/4/10.
 */
@Service("userService")
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private PictureService pictureService;
    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * @return <br />
     * 1001 用户密码长度不符
     * 1003 用户昵称长度不符
     * 1014 邮箱已存在
     *
     * @throws ZouFanqiException
     */
    @Override
    public ResultJson register(User user) throws ZouFanqiException {
        if (user == null || StringUtil.isEmpty(user.getAlias()))
            return ResultBuilder.buildParamError();

        /*ResultJson validateNameResult;
        if ((validateNameResult = this.validateName(user.getName())) != null)
            return validateNameResult;*/

        ResultJson validatePasswordResult;
        if ((validatePasswordResult = this.validatePassword(user.getPassword())) != null)
            return validatePasswordResult;

        ResultJson validateAliasResult;
        if ((validateAliasResult = this.validateAlias(user.getAlias())) != null)
            return validateAliasResult;

        user.setIsDel(ConstDB.ISDEL_FALSE);
        user.setGender(user.getGender() == null ? ConstDB.User.SEX_DEFAULT : user.getGender());
        user.setPassword(EncryptUtil.sha(user.getPassword()));

        Date date = new Date();
        user.setCreateDatetime(date);

        int status = this.userMapper.insertSelective(user);
        if (status <= 0) return ResultBuilder.buildDBError();

        this.redisTemplate.hdel(EnumRedisKey.MAP_USER_ID_USER.name(), String.valueOf(user.getId()));
        if (StringUtil.isNotEmpty(user.getName()))
            this.redisTemplate.hdel(EnumRedisKey.MAP_NAME_USER.name(), user.getName());

        return ResultBuilder.build(user);
    }

    /**
     * @param loginUserId
     *
     * @return <br />
     * 611 不是本人操作
     * 1001 用户密码长度不符
     * 1003 用户名称长度不符
     * 1011 用户不存在
     * 1012 手机号已存在
     *
     * @throws ZouFanqiException
     */
    @Override
    public ResultJson updateById(Long loginUserId, User user) throws ZouFanqiException {
        Long id;
        if (StringUtil.isNotId(loginUserId) ||
                user == null ||
                StringUtil.isNotId(id = user.getId())) return ResultBuilder.buildParamError();

        User old;
        if ((old = this.getById(id)) == null)
            return ResultBuilder.buildError(EnumStatusCode.USER_NOT_EXISTS);

        if (!StringUtil.equals(loginUserId, old.getId()))
            return ResultBuilder.buildError(EnumStatusCode.DB_DATA_NOT_YOURS);

        ResultJson validateNameResult;
        if ((validateNameResult = this.validateName(user.getName())) != null)
            return validateNameResult;

        String password;
        ResultJson validatePasswordResult;
        if ((password = user.getPassword()) != null &&
                (validatePasswordResult = this.validatePassword(password)) != null)
            return validatePasswordResult;

        ResultJson validateAliasResult;
        if (user.getAlias() != null &&
                (validateAliasResult = this.validateAlias(user.getAlias())) != null)
            return validateAliasResult;

        user.setId(id);
        user.setPassword(password == null ? null : EncryptUtil.sha(password));

        Long avatarId;
        if (StringUtil.isNotId(avatarId = user.getAvatarId()) &&
                (old.getAvatarId() == null || !StringUtil.equals(old.getAvatarId(), avatarId))) {
            Picture avatar = this.pictureService.getById(avatarId);
            if (avatar != null) {
                user.setAvatarId(avatar.getId());
            }
        }
        int status = this.userMapper.updateByPrimaryKeySelective(user);
        if (status <= 0) return ResultBuilder.buildDBError();

        this.redisTemplate.hdel(EnumRedisKey.MAP_USER_ID_USER.name(), String.valueOf(id));
        if (StringUtil.isNotEmpty(old.getName()))
            this.redisTemplate.hdel(EnumRedisKey.MAP_NAME_USER.name(), old.getName());
        if (StringUtil.isNotEmpty(user.getName()) && !user.getName().equals(old.getName()))
            this.redisTemplate.hdel(EnumRedisKey.MAP_NAME_USER.name(), user.getName());

        return ResultBuilder.build();
    }

    @Override
    public ResultJson deleteById(Long id) throws ZouFanqiException {
        User old = this.getById(id);
        if (old == null) return ResultBuilder.build();

        User user = new User();
        user.setId(id);
        user.setIsDel(ConstDB.ISDEL_TRUE);

        int status = this.userMapper.updateByPrimaryKeySelective(user);
        if (status > 0) {
            this.redisTemplate.hdel(EnumRedisKey.MAP_USER_ID_USER.name(), String.valueOf(id));
            if (StringUtil.isNotEmpty(old.getName()))
                this.redisTemplate.hdel(EnumRedisKey.MAP_NAME_USER.name(), old.getName());
        }
        return ResultBuilder.build();
    }

    @Override
    public User getById(Long id) throws ZouFanqiException {
        if (StringUtil.isNotId(id)) return null;

        String redisInfo = this.redisTemplate.hget(EnumRedisKey.MAP_USER_ID_USER.name(), String.valueOf(id));
        if (StringUtil.isNotEmpty(redisInfo)) {
            if (ConstService.REDIS_DEFAULT_INFO.equals(redisInfo)) return null;
            return JSON.parseObject(redisInfo, User.class);
        }
        /**
         * 缓存
         */
        UserExample example = new UserExample();
        example.createCriteria().
                andIdEqualTo(id).
                andStatusEqualTo(ConstDB.User.STATUS_OPEN).
                andIsDelEqualTo(ConstDB.ISDEL_FALSE);
        List<User> userList = this.userMapper.selectByExample(example);
        if (userList == null || userList.isEmpty()) {
            this.redisTemplate.hset(EnumRedisKey.MAP_USER_ID_USER.name(), String.valueOf(id), ConstService.REDIS_DEFAULT_INFO);
            return null;
        } else {
            this.redisTemplate.hset(EnumRedisKey.MAP_USER_ID_USER.name(), String.valueOf(id), JSON.toJSONString(userList.get(0)));
            return userList.get(0);
        }
    }

    @Override
    public User getByName(String name) throws ZouFanqiException {
        if (StringUtil.isEmpty(name)) return null;
        String redisInfo = this.redisTemplate.hget(EnumRedisKey.MAP_NAME_USER.name(), name);
        if (StringUtil.isNotEmpty(redisInfo)) {
            if (ConstService.REDIS_DEFAULT_INFO.equals(redisInfo)) return null;
            return JSON.parseObject(redisInfo, User.class);
        }
        /**
         * 缓存
         */
        UserExample example = new UserExample();
        example.createCriteria().
                andIsDelEqualTo(ConstDB.ISDEL_FALSE).
                andNameEqualTo(name).
                andStatusEqualTo(ConstDB.User.STATUS_OPEN);

        List<User> list = this.userMapper.selectByExample(example);
        int len;
        if (list == null || (len = list.size()) == 0) {
            return null;
        } else if (len > 1) {
            for (int i = 1; i < len; i++) {
                User user = list.get(i);
                if (user == null) continue;
                this.deleteById(user.getId());
            }
        }
        User user = list.get(0);
        if (user == null)
            this.redisTemplate.hset(EnumRedisKey.MAP_USER_ID_USER.name(), name, ConstService.REDIS_DEFAULT_INFO);
        else
            this.redisTemplate.hset(EnumRedisKey.MAP_USER_ID_USER.name(), name, JSON.toJSONString(user));
        return user;
    }

    /**
     * 验证密码, 成功返回null
     *
     * @param password
     *
     * @return
     */
    private ResultJson validatePassword(String password) {
        int passwordLen;
        if (StringUtil.isEmpty(password) ||
                (passwordLen = password.length()) < ConstService.PASSWORD_LEN_MIN ||
                passwordLen > ConstService.PASSWORD_LEN_MAX)
            return ResultBuilder.buildError(EnumStatusCode.USER_PASSWORD_LEN_NOT_ALLOW);
        return null;
    }

    private ResultJson validateAlias(String alias) {
        int nicknameLen;
        if (StringUtil.isEmpty(alias) ||
                (nicknameLen = alias.length()) < ConstService.NICKNAME_LEN_MIN ||
                nicknameLen > ConstService.NICKNAME_LEN_MAX)
            return ResultBuilder.buildError(EnumStatusCode.USER_ALIAS_LEN_NOT_ALLOW);
        return null;
    }

    private ResultJson validateName(String name) {
        int nameLen;
        if (StringUtil.isEmpty(name) ||
                (nameLen = (name = name.trim()).length()) < ConstService.NAME_LEN_MIN ||
                nameLen > ConstService.NAME_LEN_MAX)
            return ResultBuilder.buildError(EnumStatusCode.USER_NAME_LEN_NOT_ALLOW);
        if (RegexUtil.isNumber(name)) return ResultBuilder.buildError(EnumStatusCode.USER_NAME_NOT_ALLOW);
        return null;
    }
}
