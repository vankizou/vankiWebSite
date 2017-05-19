package com.zoufanqi.result;

import com.zoufanqi.status.EnumStatusCode;

public class ResultBuilder {

    private static final String NULL = "";

    public static ResultJson build() {
        return new ResultJson(NULL);
    }

    public static ResultJson build(Object data) {
        return new ResultJson(EnumStatusCode.SUCCESS, data);
    }

    public static ResultJson build(EnumStatusCode code, Object data) {
        return new ResultJson(code, data);
    }

    public static ResultJson buildError() {
        return new ResultJson(EnumStatusCode.ERROR, NULL);
    }

    public static ResultJson buildError(EnumStatusCode code) {
        return build(code, NULL);
    }

    public static ResultJson buildError(EnumStatusCode code, Object data) {
        return build(code, data);
    }

    public static ResultJson buildNotFound() {
        return new ResultJson(EnumStatusCode.NOT_FOUND, NULL);
    }

    public static ResultJson buildDBError() {
        return new ResultJson(EnumStatusCode.DB_ERROR, NULL);
    }

    public static ResultJson buildException() {
        return new ResultJson(EnumStatusCode.EXCEPTION, NULL);
    }

    public static ResultJson buildParamError() {
        return new ResultJson(EnumStatusCode.ERROR_PARAM, NULL);
    }


}
