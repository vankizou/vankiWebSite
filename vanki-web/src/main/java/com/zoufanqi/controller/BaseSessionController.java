package com.zoufanqi.controller;

import com.zoufanqi.consts.ConstService;
import com.zoufanqi.entity.User;
import com.zoufanqi.exception.ZouFanqiException;
import com.zoufanqi.param.result.UserContext;
import com.zoufanqi.status.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by vanki on 16/10/26.
 */
public class BaseSessionController {
    @Autowired
    protected HttpServletRequest request;

    protected UserContext assembledUCAndSetSessionData(Object... ucPart) {
        if (ucPart == null || ucPart.length == 0) return null;

        UserContext uc = new UserContext();
        boolean isMatch = false;

        for (Object u : ucPart) {
            if (u.getClass().equals(User.class)) {
                uc.setUser((User) u);
                isMatch = true;
            } // else if...
        }
        if (isMatch) this.request.getSession().setAttribute(ConstService.SESSION_LOGIN_USER, uc);
        return uc;
    }

    /**
     * 是否已登录
     *
     * @return
     *
     * @throws ZouFanqiException
     */
    protected boolean isLogin() throws ZouFanqiException {
        UserContext user = (UserContext) request.getSession().getAttribute(ConstService.SESSION_LOGIN_USER);
        return user != null;
    }

    protected UserContext justGetUserContext() throws ZouFanqiException {
        UserContext user = (UserContext) request.getSession().getAttribute(ConstService.SESSION_LOGIN_USER);
        return user;
    }

    protected UserContext getUserContext() throws ZouFanqiException {
        UserContext user = (UserContext) request.getSession().getAttribute(ConstService.SESSION_LOGIN_USER);
        if (user == null) {
//            throw new ZouFanqiException(StatusCode.NOT_LOGIN);
        }
        return user;
    }

    protected Long justGetUserId() throws ZouFanqiException {
        UserContext uc = (UserContext) request.getSession().getAttribute(ConstService.SESSION_LOGIN_USER);
        User user;
//        if (uc == null || (user = uc.getUser()) == null) return null;
//        return user.getId();
        return 1L;
    }

    protected Long getUserId() throws ZouFanqiException {
        User user;
        UserContext uc = this.getUserContext();
        if (uc == null || (user = uc.getUser()) == null) {
//            throw new ZouFanqiException(StatusCode.NOT_LOGIN);
        }
//        return user.getId();
        return 1L;
    }
}
