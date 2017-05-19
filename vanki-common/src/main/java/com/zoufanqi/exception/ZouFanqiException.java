package com.zoufanqi.exception;

import com.zoufanqi.status.EnumStatusCode;

public class ZouFanqiException extends Exception {

    private static final long serialVersionUID = -4010787523984781561L;

    private int code;

    public ZouFanqiException(EnumStatusCode code) {
        super(code.getMsg());
        this.code = code.getCode();
    }

    public int getCode() {
        return code;
    }

}
