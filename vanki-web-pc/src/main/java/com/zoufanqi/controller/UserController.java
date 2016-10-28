package com.zoufanqi.controller;

import com.zoufanqi.entity.User;
import com.zoufanqi.exception.ZouFanqiException;
import com.zoufanqi.result.ResultBuilder;
import com.zoufanqi.result.ResultJson;
import com.zoufanqi.service.UserService;
import com.zoufanqi.status.UserCode;
import com.zoufanqi.utils.EncryptUtil;
import com.zoufanqi.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 用户
 * Created by vanki on 16/10/22.
 */
@Controller
@RequestMapping("/user")
public class UserController extends BaseSessionController {
    @Autowired
    private UserService userService;

    /**
     * 添加用户
     *
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
    @ResponseBody
    @RequestMapping(value = "/add.json", method = RequestMethod.POST)
    public ResultJson add(String phone, String password, Long avatarId,
                          Integer sex, String nickname, Long cityId, String description, String registerIp) throws ZouFanqiException {
        return this.userService.add(phone, password, avatarId,
                sex, nickname, cityId, description, registerIp);
    }

    /**
     * 手机号登录
     *
     * @param phone
     * @param password
     *
     * @return <br />
     * 10001 用户密码长度不符
     * 10004 密码错误
     * 10011 用户不存在
     *
     * @throws ZouFanqiException
     */
    @ResponseBody
    @RequestMapping(value = "/loginByPhone.json", method = RequestMethod.GET)
    public ResultJson loginByPhone(String phone, String password) throws ZouFanqiException {
        if (StringUtil.isEmpty(password))
            return ResultBuilder.buildError(UserCode.PASSWORD_LEN_NOT_ALLOW);
        User user = this.userService.getByPhone(phone);
        if (user == null) return ResultBuilder.buildError(UserCode.USER_NOT_EXISTS);

        if (!EncryptUtil.sha(password).equals(user.getPassword()))
            return ResultBuilder.buildError(UserCode.PASSWORD_ERROR);

        this.assembledUCAndSetSessionData(user);

        return ResultBuilder.build();
    }
}
