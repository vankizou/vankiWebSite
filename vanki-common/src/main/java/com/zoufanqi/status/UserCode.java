package com.zoufanqi.status;

import com.alibaba.druid.support.spring.stat.annotation.Stat;

/**
 * Created by vanki on 16/10/26.
 */
public class UserCode {
    public static final StatusCode USERNAME_LEN_NOT_ALLOW = new StatusCode(10000, "用户名称长度不符");
    public static final StatusCode PASSWORD_LEN_NOT_ALLOW = new StatusCode(10001, "用户密码长度不符");
    public static final StatusCode USERNAME_NOT_ALLOW = new StatusCode(10002, "用户名不符合规范");
    public static final StatusCode NICKNAME_LEN_NOT_ALLOW = new StatusCode(10003, "用户昵称长度不符");
    public static final StatusCode PASSWORD_ERROR = new StatusCode(10004, "密码错误");

    public static final StatusCode USER_EXISTS = new StatusCode(10010, "用户不存在");
    public static final StatusCode USER_NOT_EXISTS = new StatusCode(10011, "用户不存在");
    public static final StatusCode PHONE_EXISTS = new StatusCode(10012, "手机号已存在");
    public static final StatusCode PHONE_NOT_EXISTS = new StatusCode(10013, "手机号不存在");
}
