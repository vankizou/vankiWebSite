package com.zoufanqi.controller;

import com.zoufanqi.consts.ConstService;
import com.zoufanqi.consts.EnumRedisKey;
import com.zoufanqi.entity.User;
import com.zoufanqi.exception.ZouFanqiException;
import com.zoufanqi.param.result.UserContext;
import com.zoufanqi.service.redis.RedisTemplate;
import com.zoufanqi.status.EnumStatusCode;
import com.zoufanqi.utils.ExceptionUtil;
import com.zoufanqi.utils.StringUtil;
import com.zoufanqi.utils.UUIDUtil;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by vanki on 16/10/26.
 */
public class BaseController {
    @Autowired
    protected HttpServletRequest request;
    @Autowired
    protected HttpServletResponse response;
    @Autowired
    protected RedisTemplate redisTemplate;

    /**
     * 是否已登录
     *
     * @return
     *
     * @throws ZouFanqiException
     */
    protected boolean isLogin() throws ZouFanqiException {
        UserContext user = this.justGetUserContext();
        return user != null;
    }

    protected Long getUserId() throws ZouFanqiException {
        User user;
        UserContext uc = this.getUserContext();
        if (uc == null || (user = uc.getUser()) == null) {
            throw new ZouFanqiException(EnumStatusCode.NOT_LOGIN);
        }
        return user.getId();
    }

    protected Long justGetUserId() throws ZouFanqiException {
        UserContext uc = this.justGetUserContext();
        User user;
        if (uc == null || (user = uc.getUser()) == null) return null;
        return user.getId();
    }

    protected UserContext getUserContext() throws ZouFanqiException {
        UserContext user = this.justGetUserContext();
        if (user == null) {
            throw new ZouFanqiException(EnumStatusCode.NOT_LOGIN);
        }
        return user;
    }

    protected UserContext justGetUserContext() throws ZouFanqiException {
        UserContext user = (UserContext) request.getSession().getAttribute(ConstService.SESSION_LOGIN_USER);
        return user;
    }

    protected void clearCookieAndSessionInfo() throws ZouFanqiException {
        clearCookieAndSessionInfo(null);
    }

    protected void clearCookieAndSessionInfo(String userId) throws ZouFanqiException {
        if (StringUtil.isEmpty(userId)) {
            Long uId = this.justGetUserId();
            if (StringUtil.isId(uId)) userId = uId.toString();
        }
        this.request.getSession().invalidate();
        this.deleteCookie(ConstService.COOKIE_LOGIN_TOKEN);

        if (StringUtil.isEmpty(userId)) return;
        String token = this.redisTemplate.hget(EnumRedisKey.MAP_USER_ID_TOKEN.name(), userId);
        if (StringUtil.isNotEmpty(token))
            this.redisTemplate.hdel(EnumRedisKey.MAP_TOKEN_USER_ID.name(), token);
        this.redisTemplate.hdel(EnumRedisKey.MAP_USER_ID_TOKEN.name(), userId);
    }

    protected UserContext buildUCAndSetSessionData(Object... ucPart) throws ZouFanqiException {
        if (ucPart == null || ucPart.length == 0) return null;

        UserContext uc = new UserContext();

        for (Object u : ucPart) {
            if (u == null) continue;
            if (u.getClass().equals(User.class)) {
                uc.setUser((User) u);
            } // else if...
        }
        if (uc.getUser() == null) return null;
        /**
         * 添加登录信息到缓存
         */
        updateCookieAndSessionInfo(uc);
        return uc;
    }

    protected void updateCookieAndSessionInfo(UserContext uc) throws ZouFanqiException {
        User user;
        if (uc == null || (user = uc.getUser()) == null) throw new ZouFanqiException(EnumStatusCode.NOT_LOGIN);
        String userId = user.getId().toString();
        String oldToken = this.redisTemplate.hget(EnumRedisKey.MAP_USER_ID_TOKEN.name(), userId);
        if (StringUtil.isNotEmpty(oldToken)) {// 清除旧token
            this.redisTemplate.hdel(EnumRedisKey.MAP_TOKEN_USER_ID.name(), oldToken);
        }
        String token = UUIDUtil.getUUID();
        this.redisTemplate.hset(EnumRedisKey.MAP_USER_ID_TOKEN.name(), userId, token);
        this.redisTemplate.hset(EnumRedisKey.MAP_TOKEN_USER_ID.name(), token, userId);
        this.request.getSession().setAttribute(ConstService.SESSION_LOGIN_USER, uc);
        this.setCookie(ConstService.COOKIE_LOGIN_TOKEN, token, ConstService.COOKIE_LOGIN_TOKEN_TIME);
    }

    protected String getCookie(String name) {
        if (StringUtil.isEmpty(name)) return null;
        Cookie[] cookies = this.request.getCookies();
        if (cookies == null) return null;
        for (Cookie c : cookies) {
            String n = c.getName();
            if (name.equals(n)) return c.getValue();
        }
        return null;
    }

    /**
     * 设置cookie
     *
     * @param name
     * @param value
     * @param time  时间, 秒
     */
    protected void setCookie(String name, String value, Integer time) {
        if (StringUtil.isEmpty(name)) return;
        Cookie cookie = new Cookie(name, value);
        // tomcat下多应用共享
        cookie.setPath("/");
        // 如果cookie的值中含有中文时，需要对cookie进行编码，不然会产生乱码
        try {
            if (StringUtil.isNotEmpty(value)) value = URLEncoder.encode(value, "utf-8");
        } catch (UnsupportedEncodingException e) {
            ExceptionUtil.getExceptionAllMsg(e);
        }
        if (time != null) cookie.setMaxAge(time);
        // 将Cookie添加到Response中,使之生效
        this.response.addCookie(cookie); // addCookie后，如果已经存在相同名字的cookie，则最新的覆盖旧的cookie
    }

    protected void deleteCookie(String name) {
        if (StringUtil.isEmpty(name)) return;
        this.setCookie(name, null, 0);
    }
}
