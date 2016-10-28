package com.zoufanqi.exception;

import com.zoufanqi.status.StatusCode;

public class ZouFanqiException extends Exception{

	private static final long serialVersionUID = -4010787523984781564L;

	private int code;

	public ZouFanqiException(StatusCode code) {
		super(code.getMsg());
		this.code = code.getCode();
	}

	public int getCode() {
		return code;
	}

}
