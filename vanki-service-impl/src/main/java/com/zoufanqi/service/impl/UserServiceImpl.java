package com.zoufanqi.service.impl;

import com.zoufanqi.consts.ConstDB;
import com.zoufanqi.consts.ConstService;
import com.zoufanqi.entity.Picture;
import com.zoufanqi.entity.User;
import com.zoufanqi.entity.UserExample;
import com.zoufanqi.exception.ZouFanqiException;
import com.zoufanqi.mapper.UserMapper;
import com.zoufanqi.result.ResultBuilder;
import com.zoufanqi.result.ResultJson;
import com.zoufanqi.service.PictureService;
import com.zoufanqi.service.UserService;
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
        if (status > 0) return ResultBuilder.build(user);

        return ResultBuilder.buildDBError();
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

        if (loginUserId != old.getId())
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
                (old.getAvatarId() == null || old.getAvatarId() != avatarId)) {
            Picture avatar = this.pictureService.getById(avatarId);
            if (avatar != null) {
                user.setAvatarId(avatar.getId());
            }
        }
        int status = this.userMapper.updateByPrimaryKeySelective(user);
        if (status > 0) return ResultBuilder.build();
        return ResultBuilder.buildDBError();
    }

    @Override
    public ResultJson deleteById(String id) throws ZouFanqiException {
        if (!RegexUtil.isPhone(id)) return ResultBuilder.build();

        UserExample example = new UserExample();
        example.createCriteria().andPhoneEqualTo(id).andIsDelEqualTo(ConstDB.ISDEL_FALSE);

        User user = new User();
        user.setIsDel(ConstDB.ISDEL_TRUE);

        this.userMapper.updateByExampleSelective(user, example);

        return ResultBuilder.build();
    }

    @Override
    public User getById(Long id) throws ZouFanqiException {
        if (StringUtil.isNotId(id) || id > 1000000000) return null;
        return this.userMapper.selectByPrimaryKey(id);
    }

    @Override
    public User getByName(String name) throws ZouFanqiException {
        if (StringUtil.isEmpty(name)) return null;

        UserExample example = new UserExample();
        example.createCriteria().andIsDelEqualTo(ConstDB.ISDEL_FALSE).andNameEqualTo(name);

        List<User> list = this.userMapper.selectByExample(example);
        int len;
        if (list == null || (len = list.size()) == 0) {
            return null;
        } else if (len > 1) {
            for (int i = 1; i < len; i++) {
                User user = list.get(i);
                if (user == null) continue;
                this.deleteById(user.getPhone());
            }
        }
        return list.get(0);
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
