package com.zoufanqi.status;

/**
 * Created by vanki on 2017/4/10.
 */
public enum EnumStatusCode {
    /**
     * 公共code，100 - 999
     */
    SUCCESS(200, "成功"),
    NOT_LOGIN(201, "未登录"),
    FORBIDDEN(202, "禁止访问"),

    ERROR_PARAM(400, "参数不符"),
    NOT_FOUND(404, "资源未找到"),

    ERROR(500, "系统错误"),
    EXCEPTION(501, "系统异常"),

    FILE_EMPTY(600, "未选择文件"),
    FILE_TOO_LARGE(601, "文件过大"),
    FILE_NOT_SUPPORT(602, "不支持的资源类型"),
    FILE_UPDATE_FAIL(603, "文件上传失败"),

    DB_ERROR(610, "数据操作失败"),
    DB_DATA_NOT_YOURS(611, "不是本人操作"),
    DB_NOT_FOUND(612, "数据不存在"),
    DB_PARENT_NOT_FOUND(613, "上一级数据不存在"),

    IMAGE_CODE_VALIDATE_FAIL(620, "验证码错误"),

    /**
     * 用户相关，1000 - 1099
     */
    USER_PASSWORD_LEN_NOT_ALLOW(1001, "用户密码长度不符"),
    USER_NAME_LEN_NOT_ALLOW(1002, "用户名长度不符"),
    USER_ALIAS_LEN_NOT_ALLOW(1003, "用户昵称长度不符"),
    USER_PASSWORD_ERROR(1004, "密码错误"),

    USER_EXISTS(1010, "用户已存在"),
    USER_NOT_EXISTS(1011, "用户不存在"),
    USER_NAME_NOT_ALLOW(1012, "用户名不能全为数字"),
    USER_PHONE_EXISTS(1013, "手机号已存在"),
    USER_PHONE_NOT_EXISTS(1014, "手机号不存在"),

    /**
     * 笔记相关，1100 - 1199
     */
    NOTE_PASSWORD_ERROR(1100, "密码错误"),
    NOTE_EXPORT_CONTENT_IS_EMPTY(1101, "导出失败，笔记内容为空");



    private Integer code;
    private String msg;

    EnumStatusCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
