package com.zoufanqi.consts;

/**
 * Created by vanki on 16/10/26.
 */
public class ConstService {
    public static final String SESSION_LOGIN_USER = "userContext";
    public static final String COOKIE_LOGIN_TOKEN = "token";
    public static final int COOKIE_LOGIN_TOKEN_TIME = 60 * 60 * 24 * 90;

    public static final String COOKIE_IMAGE_CODE_VALUE = "IMAGE_CODE_VALUE"; // 图片验证码值
    public static final int COOKIE_IMAGE_CODE_VALUE_TIME = 60 * 10;          // 图片验证码值保存时间

    public static final String COOKIE_NOTE_VIEW_LIST = "NOTE_VIEW_LIST";    // 观看笔记列表
    public static final int COOKIE_NOTE_VIEW_LIST_TIME = 60 * 10;        // 观看笔记列表保存时间

    public static final int NAME_LEN_MIN = 1;           // 用户名最小长度
    public static final int NAME_LEN_MAX = 32;          // 用户名最大长度
    public static final int PASSWORD_LEN_MIN = 1;       // 用户密码最小长度
    public static final int PASSWORD_LEN_MAX = 32;      // 用户密码最大长度
    public static final int NICKNAME_LEN_MIN = 1;       // 用户昵称最大长度
    public static final int NICKNAME_LEN_MAX = 32;      // 用户昵称最大长度
    public static final String USERNAME_REG = "^[\\u4E00-\\u9FA5A-Za-z0-9_]+$"; // 用户名规则

    public static final String REDIS_DEFAULT_INFO = "_DEFAULT_INFO_";   // redis默认数据，当获取的是该值直接返回null，防攻击

    public static final int NOTE_TITLE_LEN_MAX = 200;
}