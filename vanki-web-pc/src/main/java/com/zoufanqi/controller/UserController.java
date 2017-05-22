package com.zoufanqi.controller;

import com.zoufanqi.consts.ConstDB;
import com.zoufanqi.consts.ConstService;
import com.zoufanqi.entity.User;
import com.zoufanqi.entity.UserLoginRecord;
import com.zoufanqi.exception.ZouFanqiException;
import com.zoufanqi.result.ResultBuilder;
import com.zoufanqi.result.ResultJson;
import com.zoufanqi.service.UserLoginRecordService;
import com.zoufanqi.service.UserService;
import com.zoufanqi.status.EnumStatusCode;
import com.zoufanqi.utils.EncryptUtil;
import com.zoufanqi.utils.RegexUtil;
import com.zoufanqi.utils.RemoteRequestUtil;
import com.zoufanqi.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;

/**
 * 用户
 * Created by vanki on 16/10/22.
 */
@Controller
@RequestMapping("/user")
public class UserController extends BaseController {
    @Autowired
    private UserService userService;
    @Autowired
    private ImageCodeController imageCodeController;
    @Autowired
    private UserLoginRecordService userLoginRecordService;

    @RequestMapping(value = "/login.html")
    public ModelAndView loginHtml() throws IOException {
        ModelAndView mv = new ModelAndView("index/login");
        return mv;
    }

    @RequestMapping("/logout.html")
    public String logout() throws ZouFanqiException {
        this.clearCookieAndSessionInfo();
        return "redirect:/";
    }

    @RequestMapping(value = "/home.html")
    public String homeHtml() throws ZouFanqiException {
        Long userId;
        if ((userId = this.justGetUserId()) != null) return "redirect:/user/" + userId + ".html";
        return "redirect:/";
    }

    @RequestMapping(value = "/{userIdOrName}.html")
    public ModelAndView listHtml(@PathVariable("userIdOrName") String userIdOrName) throws ZouFanqiException {
        if (StringUtil.isEmpty(userIdOrName)) userIdOrName = String.valueOf(this.getUserId());
        if (StringUtil.isEmpty(userIdOrName)) throw new ZouFanqiException(EnumStatusCode.NOT_FOUND);
        User user;
        if (RegexUtil.isNumber(userIdOrName)) {
            user = this.userService.getById(Long.valueOf(userIdOrName));
        } else {
            user = this.userService.getByName(userIdOrName);
        }
        if (user == null) throw new ZouFanqiException(EnumStatusCode.NOT_FOUND);
        ModelAndView mv = new ModelAndView("note/list");
        mv.addObject("userId", user.getId());
        return mv;
    }

    /**
     * 添加用户
     *
     * @return <br />
     * 10001 用户密码长度不符
     * 10003 用户昵称长度不符
     *
     * @throws ZouFanqiException
     */
    @ResponseBody
    @RequestMapping(value = "/register.json", method = RequestMethod.POST)
    public ResultJson register(User user, String imageCode) throws ZouFanqiException, IOException {
        if (!imageCodeController.validate(imageCode)) {
            String newImageCode = this.imageCodeController.getImageCodeBase64(null, null);
            return ResultBuilder.buildError(EnumStatusCode.IMAGE_CODE_VALIDATE_FAIL, newImageCode);
        }
        String ip = RemoteRequestUtil.getRequestIP(this.request);
        user.setRegisterIp(ip);
        ResultJson result = this.userService.register(user);
        if (result.getCode() == EnumStatusCode.SUCCESS.getCode()) {
            this.buildUCAndSetSessionData(user);
            this.deleteCookie(ConstService.COOKIE_IMAGE_CODE_VALUE);
            this.userLoginRecordService.add(UserLoginRecord.build(user.getId(), ConstDB.UserLoginRecord.ORIGIN_NONE, request));
        }
        return result;
    }

    /**
     * ID/NAME
     *
     * @param account
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
    @RequestMapping(value = "/login.json", method = RequestMethod.GET)
    public ResultJson login(String account, String password) throws ZouFanqiException {
        if (StringUtil.isEmpty(account))
            return ResultBuilder.buildParamError();
        if (StringUtil.isEmpty(password))
            return ResultBuilder.buildError(EnumStatusCode.USER_PASSWORD_LEN_NOT_ALLOW);

        User user;
        if (RegexUtil.isNumber(account)) {
            user = this.userService.getById(Long.valueOf(account));
        } else {
            user = this.userService.getByName(account);
        }
        if (user == null) return ResultBuilder.buildError(EnumStatusCode.USER_NOT_EXISTS);

        if (!EncryptUtil.sha(password).equals(user.getPassword()))
            return ResultBuilder.buildError(EnumStatusCode.USER_PASSWORD_ERROR);

        this.buildUCAndSetSessionData(user);

        this.userLoginRecordService.add(UserLoginRecord.build(user.getId(), ConstDB.UserLoginRecord.ORIGIN_NONE, request));

        return ResultBuilder.build();
    }

    @ResponseBody
    @RequestMapping(value = "/updateById.json", method = RequestMethod.POST)
    public ResultJson updateById(User user) throws ZouFanqiException {
        return this.userService.updateById(this.getUserId(), user);
    }
}
