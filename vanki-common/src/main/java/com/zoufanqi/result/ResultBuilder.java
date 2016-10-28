package com.zoufanqi.result;

import com.zoufanqi.status.StatusCode;

public class ResultBuilder {

    private static final String NULL = "";

    public static ResultJson build() {
        return new ResultJson(NULL);
    }

    public static ResultJson buildError500() {
        return new ResultJson(StatusCode.ERROR_500, NULL);
    }

    public static ResultJson buildError404() {
        return new ResultJson(StatusCode.ERROR_404, NULL);
    }

    public static ResultJson buildErrorDB() {
        return new ResultJson(StatusCode.ERROR_DB, NULL);
    }

    public static ResultJson buildException() {
        return new ResultJson(StatusCode.EXCEPTION, NULL);
    }

    public static ResultJson buildParamError() {
        return new ResultJson(StatusCode.ERROR_PARAM, NULL);
    }

    public static ResultJson build(StatusCode code, Object data) {
        return new ResultJson(code, data);
    }

    public static ResultJson buildError(StatusCode code) {
        return build(code, NULL);
    }

    public static ResultJson build(Object data) {
        return new ResultJson(StatusCode.SUCCESS, data);
    }


}
