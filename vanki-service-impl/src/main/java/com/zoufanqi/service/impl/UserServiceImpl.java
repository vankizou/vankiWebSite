package com.zoufanqi.service.impl;

import com.zoufanqi.consts.ConstDB;
import com.zoufanqi.consts.ConstService;
import com.zoufanqi.consts.ConstVariable;
import com.zoufanqi.entity.MyFile;
import com.zoufanqi.entity.User;
import com.zoufanqi.entity.UserExample;
import com.zoufanqi.entity.Variable;
import com.zoufanqi.exception.ZouFanqiException;
import com.zoufanqi.mapper.UserMapper;
import com.zoufanqi.result.ResultBuilder;
import com.zoufanqi.result.ResultJson;
import com.zoufanqi.service.MyFileService;
import com.zoufanqi.service.UserService;
import com.zoufanqi.service.VariableService;
import com.zoufanqi.status.StatusCode;
import com.zoufanqi.status.UserCode;
import com.zoufanqi.utils.EncryptUtil;
import com.zoufanqi.utils.RegexUtil;
import com.zoufanqi.utils.StringUtil;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service("userService")
public class UserServiceImpl implements UserService, InitializingBean {
    private static final Map<String, String> variableMap = new ConcurrentHashMap();
    private static final String vCodeAvatarId = "avatarId";
    private static final String vCodeAvatarPath = "avatarPath";

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private VariableService variableService;
    @Autowired
    private MyFileService myFileService;

    /**
     * @param phone
     * @param password
     * @param avatarId
     * @param sex
     * @param nickname
     * @param cityId
     * @param description
     * @param registerIp
     *
     * @return <br />
     * 10001 用户密码长度不符
     * 10003 用户昵称长度不符
     *
     * @throws ZouFanqiException
     */
    @Override
    public ResultJson add(String phone, String password, Long avatarId,
                          Integer sex, String nickname, Long cityId, String description, String registerIp) throws ZouFanqiException {
        if (!RegexUtil.isPhone(phone) || StringUtil.isEmpty(nickname)) return ResultBuilder.buildParamError();

        ResultJson validatePasswordResult;
        if ((validatePasswordResult = this.validatePassword(password)) != null) return validatePasswordResult;
        ResultJson validateNicknameResult;
        if ((validateNicknameResult = this.validateNickname(nickname)) != null) return validateNicknameResult;

        String avatarPath;
        MyFile avatar;
        if ((avatar = this.myFileService.getById(avatarId)) == null) {
            avatarId = variableMap.containsKey(vCodeAvatarId) ? Long.valueOf(variableMap.get(vCodeAvatarId)) : null;
            avatarPath = variableMap.containsKey(vCodeAvatarPath) ? variableMap.get(vCodeAvatarPath) : null;
        } else {
            avatarPath = avatar.getPath();
        }

        User user = new User();
        user.setIsDel(ConstDB.ISDEL_FALSE);
        user.setLoginCount(0);
        user.setSex(sex == null ? ConstDB.User.SEX_DEFAULT : sex);
        user.setAvatarId(avatarId);
        user.setAvatarPath(avatarPath);

        user.setPassword(EncryptUtil.sha(password));

        Date date = new Date();
        user.setRegisterDatetime(date);
        user.setLastLoginDatetime(date);

        user.setNickname(nickname);
        user.setCityId(cityId);
        user.setDescription(description);
        user.setRegisterIp(registerIp);
        user.setLastLoginIp(registerIp);

        int status = this.userMapper.insertSelective(user);
        if (status > 0) return ResultBuilder.build();

        return ResultBuilder.buildErrorDB();
    }

    /**
     * @param loginUserId
     * @param id
     * @param phone
     * @param password
     * @param avatarId
     * @param sex
     * @param nickname
     * @param cityId
     * @param description
     *
     * @return <br />
     * 1002 不是本人操作
     * 10001 用户密码长度不符
     * 10003 用户名称长度不符
     * 10011 用户不存在
     * 10012 手机号已存在
     *
     * @throws ZouFanqiException
     */
    @Override
    public ResultJson updateById(Long loginUserId, Long id, String phone, String password, Long avatarId,
                                 Integer sex, String nickname, Long cityId, String description) throws ZouFanqiException {
        if (StringUtil.isNotAutoId(loginUserId) || StringUtil.isNotAutoId(id)) return ResultBuilder.buildParamError();

        User old;
        if ((old = this.getById(id)) == null)
            return ResultBuilder.buildError(UserCode.USER_NOT_EXISTS);

        if (loginUserId != old.getId())
            return ResultBuilder.buildError(StatusCode.DATA_NOT_YOURSELF);

        if (StringUtil.isEmpty(password) && StringUtil.isEmpty(old.getPassword()))
            return ResultBuilder.buildError(UserCode.PASSWORD_LEN_NOT_ALLOW);

        ResultJson validatePasswordResult;
        if (password != null && (validatePasswordResult = this.validatePassword(password)) != null)
            return validatePasswordResult;

        ResultJson validateNicknameResult;
        if (nickname != null && (validateNicknameResult = this.validateNickname(nickname)) != null)
            return validateNicknameResult;

        if (RegexUtil.isPhone(phone) && !phone.equals(old.getPhone())) {
            User user = this.getByPhone(phone);
            if (user != null) return ResultBuilder.buildError(UserCode.PHONE_EXISTS);
        } else {
            phone = null;
        }

        User user = new User();
        user.setId(id);
        user.setPhone(phone);
        user.setPassword(password == null ? null : EncryptUtil.sha(password));
        user.setSex(sex);
        user.setNickname(nickname);
        user.setCityId(cityId);
        user.setDescription(description);

        if (StringUtil.isNotAutoId(avatarId) && (old.getAvatarId() == null || old.getAvatarId() != avatarId)) {
            MyFile avatar = this.myFileService.getById(avatarId);
            if (avatar != null) {
                user.setAvatarId(avatar.getId());
                user.setAvatarPath(avatar.getPath());
            }
        }
        int status = this.userMapper.updateByPrimaryKeySelective(user);
        if (status > 0) return ResultBuilder.build();
        return ResultBuilder.buildErrorDB();
    }

    /**
     * 登录用户, 修改用户登录信息
     *
     * @param id
     * @param loginIp
     *
     * @return -1 用户不存在
     *
     * @throws ZouFanqiException
     */
    @Override
    public int autoUpdateLoginDataById(Long id, String loginIp) throws ZouFanqiException {
        User user = this.getById(id);
        if (user == null) return -1;

        int loginCount = user.getLoginCount() == null ? 1 : user.getLoginCount() + 1;

        User uUser = new User();
        uUser.setId(id);
        uUser.setLoginCount(loginCount);
        uUser.setLastLoginIp(loginIp);
        uUser.setLastLoginDatetime(new Date());

        return this.userMapper.updateByPrimaryKeySelective(uUser);
    }

    @Override
    public ResultJson deleteByPhone(String phone) throws ZouFanqiException {
        if (!RegexUtil.isPhone(phone)) return ResultBuilder.build();

        UserExample example = new UserExample();
        example.createCriteria().andPhoneEqualTo(phone).andIsDelEqualTo(ConstDB.ISDEL_FALSE);

        User user = new User();
        user.setIsDel(ConstDB.ISDEL_TRUE);

        this.userMapper.updateByExampleSelective(user, example);

        return ResultBuilder.build();
    }

    @Override
    public User getById(Long id) throws ZouFanqiException {
        if (StringUtil.isNotAutoId(id)) return null;
        return this.userMapper.selectByPrimaryKey(id);
    }

    @Override
    public User getByPhone(String phone) throws ZouFanqiException {
        if (!RegexUtil.isPhone(phone)) return null;

        UserExample example = new UserExample();
        example.createCriteria().andIsDelEqualTo(ConstDB.ISDEL_FALSE).andPhoneEqualTo(phone);

        List<User> list = this.userMapper.selectByExample(example);
        int len;
        if (list == null || (len = list.size()) == 0) {
            return null;
        } else if (len > 1) {
            for (int i = 1; i < len; i++) {
                User user = list.get(i);
                if (user == null) continue;
                this.deleteByPhone(user.getPhone());
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
            return ResultBuilder.buildError(UserCode.PASSWORD_LEN_NOT_ALLOW);
        return null;
    }

    private ResultJson validateNickname(String nickname) {
        int nicknameLen;
        if (StringUtil.isEmpty(nickname) ||
                (nicknameLen = nickname.length()) < ConstService.NICKNAME_LEN_MIN ||
                nicknameLen > ConstService.NICKNAME_LEN_MAX)
            return ResultBuilder.buildError(UserCode.NICKNAME_LEN_NOT_ALLOW);
        return null;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Variable v = this.variableService.getByCode(ConstVariable.USER_AVATAR_ID);
        if (v == null) return;

        Long avatarId = RegexUtil.isNumber(v.getValue()) ? Long.valueOf(v.getValue()) : null;
        MyFile myFile = this.myFileService.getById(avatarId);
        if (myFile == null) return;

        variableMap.put(vCodeAvatarId, String.valueOf(avatarId));
        variableMap.put(vCodeAvatarPath, myFile.getPath());
    }
}