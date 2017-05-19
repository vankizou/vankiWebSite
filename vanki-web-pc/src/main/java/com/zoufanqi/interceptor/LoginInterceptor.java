package com.zoufanqi.interceptor;

import com.zoufanqi.consts.ConstService;
import com.zoufanqi.consts.EnumRedisKey;
import com.zoufanqi.controller.BaseController;
import com.zoufanqi.entity.User;
import com.zoufanqi.exception.ZouFanqiException;
import com.zoufanqi.param.result.UserContext;
import com.zoufanqi.service.UserService;
import com.zoufanqi.service.redis.RedisTemplate;
import com.zoufanqi.utils.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User:lc
 * Date:12/29/15 2:39 PM
 * Desc: PC端判断用户是否登录
 */
public class LoginInterceptor extends BaseController implements HandlerInterceptor {
    private static final Logger LOG = LoggerFactory.getLogger(LoginInterceptor.class);
    private static final List<String> AUTO_LOGIN_URI_LIST = new ArrayList<>();  // 需要自动登录的地址

    static {
        AUTO_LOGIN_URI_LIST.add("/");
        AUTO_LOGIN_URI_LIST.add("/note/list.html");
    }

    @Autowired
    private UserService userService;
    @Autowired
    private RedisTemplate redisTemplate;
    private List<String> commonExcludedUrls;
    private List<String> excludedUrls;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=UTF-8");

        String requestUri = request.getRequestURI();

        requestUri = StringUtil.isEmpty(requestUri) ? "/" : requestUri;
        /**
         * 静态数据依赖于接口. 所以, 可以直接过滤
         */
        if (commonExcludedUrls != null && !commonExcludedUrls.isEmpty()) {
            for (String excludeUrl : commonExcludedUrls) {
                if (requestUri.endsWith(excludeUrl)) return true;
            }
        }
        if (excludedUrls != null && !excludedUrls.isEmpty()) {
            for (String excludeUrl : excludedUrls) {
                if (requestUri.endsWith(excludeUrl)) return true;
            }
        }

        autoLogin(requestUri);

        /*if (userContextObj == null) {
            String head = request.getHeader("X-Requested-With");
            if (head == null) {// 若不是ajax请求值为null
                LOG.debug("拦截器：从session中获取的UserContext为空, 不通过, 跳转到登录页面, 请求路径: {}", requestUri);
                String path = request.getContextPath();
                String basePath = new StringBuffer().append(request.getScheme()).append("://").
                        append(request.getServerName()).append(":").append(request.getServerPort()).
                        append(path).append("/").toString();
                response.sendRedirect(basePath + "users/login.html");
                return false;
            } else {
                LOG.debug("拦截器：从session中获取的UserContext为空, 不通过, 请求路径: {}", requestUri);
                throw new ZouFanqiException(StatusCode.NOT_LOGIN);
            }
        }*/
        return true;
    }

    private boolean autoLogin(String requestUri) throws ZouFanqiException {
        /**
         * 不需要登录
         */
        if (requestUri == null) return false;

        // session有值，已登录
        UserContext uc = (UserContext) this.request.getSession().getAttribute(ConstService.SESSION_LOGIN_USER);
        if (uc != null) return true;

        String token = this.getCookie(ConstService.COOKIE_LOGIN_TOKEN);

        // 无token，不晓得身份
        if (StringUtil.isEmpty(token)) return false;

        /**
         * 有token, 但session过期，从缓存中获取数据到session
         */
        String userId = this.redisTemplate.hget(EnumRedisKey.MAP_TOKEN_USER_ID.name(), token);
        if (StringUtil.isEmpty(userId)) {
            this.clearCookieAndSessionInfo();
            return false;   // 用户之前未登录过
        }

        User user = this.userService.getById(Long.valueOf(userId));
        if (user == null) {
            this.clearCookieAndSessionInfo(userId);
            return false;
        }
        this.buildUCAndSetSessionData(user);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }


    public void setCommonExcludedUrls(List<String> commonExcludedUrls) {
        this.commonExcludedUrls = commonExcludedUrls;
    }

    public void setExcludedUrls(List<String> excludedUrls) {
        this.excludedUrls = excludedUrls;
    }
}
