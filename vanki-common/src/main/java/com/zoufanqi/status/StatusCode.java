package com.zoufanqi.status;

/**
 * 定义公共的 状态码 200 成功 500 系统错误 400 参数错误
 */
public class StatusCode {
    public static final StatusCode SUCCESS = new StatusCode(200, "success");
    public static final StatusCode NOT_LOGIN = new StatusCode(201, "未登录");

    public static final StatusCode ERROR_PARAM = new StatusCode(400, "参数不符");
    public static final StatusCode ERROR_403 = new StatusCode(403, "没有权限，禁止访问");
    public static final StatusCode ERROR_404 = new StatusCode(404, "not found");
    public static final StatusCode ERROR_DB = new StatusCode(405, "数据操作失败");

    public static final StatusCode ERROR_500 = new StatusCode(500, "system error");
    public static final StatusCode EXCEPTION = new StatusCode(501, "system exception");

    public static final StatusCode FILE_EMPTY = new StatusCode(900, "未选择文件");
    public static final StatusCode FILE_TOO_LARGE = new StatusCode(901, "文件过大");
    public static final StatusCode FILE_NOT_SUPPORT = new StatusCode(902, "不支持的资源类型");
    public static final StatusCode FILE_UPLOAD_FAIL = new StatusCode(903, "文件上传失败");

    public static final StatusCode ID_NOT_EXIST = new StatusCode(1000, "数据不存在");
    public static final StatusCode PARENT_ID_NOT_EXIST = new StatusCode(1001, "父节点数据不存在");
    public static final StatusCode DATA_NOT_YOURSELF = new StatusCode(1002, "不是本人操作");

    private int code;
    private String msg;

    protected StatusCode() {
    }

    public StatusCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "code: " + this.getCode() + ", msg: " + this.getMsg();
    }
}


