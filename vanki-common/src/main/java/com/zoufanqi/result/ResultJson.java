package com.zoufanqi.result;

import com.zoufanqi.status.StatusCode;

public class ResultJson {

    private int code;
    private String msg;
    private Object data;

    public ResultJson() {
        this(StatusCode.SUCCESS, null);
    }

    public ResultJson(Object data) {
        this(StatusCode.SUCCESS, data);
    }

    public ResultJson(StatusCode code, Object data) {
        this.setCode(code.getCode());
        this.setMsg(code.getMsg());
        this.data = data;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
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

}
