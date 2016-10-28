package com.zoufanqi.utils;

import java.util.regex.Pattern;

/**
 * 字符串操作工具
 */
public final class StringUtil {

    private final static String[] AGENT_ARR = {"Android", "iPhone", "iPod", "iPad", "Windows Phone", "MQQBrowser"};

    public static boolean isPhoneRequest(String userAgent) {
        if (StringUtil.isEmpty(userAgent)) return false;

        for (String agent : AGENT_ARR) {
            if (userAgent.indexOf(agent) != -1) return true;
        }
        return false;
    }

    /**
     * xss数据更改
     *
     * @param value
     *
     * @return
     */
    public static String stripXSS(String value) {
        if (value != null) {
            // NOTE: It's highly recommended to use the ESAPI library and uncomment the following line to
            // avoid encoded attacks.
            // value = ESAPI.encoder().canonicalize(value);
            // Avoid null characters
            value = value.replaceAll("", "");
            // Avoid anything between script tags
            Pattern scriptPattern = Pattern.compile("(.*?)", Pattern.CASE_INSENSITIVE);
            value = scriptPattern.matcher(value).replaceAll("");
            // Avoid anything in a src="http://www.yihaomen.com/article/java/..." type of e­xpression
            //scriptPattern = Pattern.compile("src[\r\n]*=[\r\n]*\\\'(.*?)\\\'", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
            //value = scriptPattern.matcher(value).replaceAll("");
            //scriptPattern = Pattern.compile("src[\r\n]*=[\r\n]*\\\"(.*?)\\\"", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
            //value = scriptPattern.matcher(value).replaceAll("");
            // Remove any lonesome  tag
            scriptPattern = Pattern.compile("", Pattern.CASE_INSENSITIVE);
            value = scriptPattern.matcher(value).replaceAll("");
            // Remove any lonesome  tag
            scriptPattern = Pattern.compile("", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
            value = scriptPattern.matcher(value).replaceAll("");
            // Avoid eval(...) e­xpressions
            scriptPattern = Pattern.compile("eval\\((.*?)\\)", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
            value = scriptPattern.matcher(value).replaceAll("");
            // Avoid e­xpression(...) e­xpressions
            scriptPattern = Pattern.compile("e­xpression\\((.*?)\\)", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
            value = scriptPattern.matcher(value).replaceAll("");
            // Avoid javascript:... e­xpressions
            scriptPattern = Pattern.compile("javascript:", Pattern.CASE_INSENSITIVE);
            value = scriptPattern.matcher(value).replaceAll("");
            // Avoid vbscript:... e­xpressions
            scriptPattern = Pattern.compile("vbscript:", Pattern.CASE_INSENSITIVE);
            value = scriptPattern.matcher(value).replaceAll("");
            // Avoid onload= e­xpressions
            scriptPattern = Pattern.compile("onload(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
            value = scriptPattern.matcher(value).replaceAll("");

            value = value.replaceAll("<", "&lt;").replaceAll(">", "&gt;");
            value = value.replaceAll("\\(", "&#40;").replaceAll("\\)", "&#41;");
            value = value.replaceAll("'", "&#39;");
            value = value.replaceAll("eval\\((.*)\\)", "");
            value = value.replaceAll("[\\\"\\\'][\\s]*javascript:(.*)[\\\"\\\']", "\"\"");
            value = value.replaceAll("\\$\\{.{1,}\\}", "");
            //value = value.replaceAll("script","");
            return value;
        }
        return value;
    }

    /**
     * 判断一个字符串是否为空或为"".
     *
     * @param str
     *
     * @return
     */
    public static boolean isEmpty(String str) {
        return (str == null || str.length() == 0);
    }

    /**
     * 判断一个字符串是否不为空或为"".
     *
     * @param str
     *
     * @return
     */
    public static boolean isNotEmpty(String str) {
        return str != null && str.length() > 0;
    }

    /**
     * 求等比缩放后的值 <br />
     * 如: x / y = x1 / y1
     *
     * @param xSrc          x的值
     * @param ySrc          y的值
     * @param xxOrYy        xx 或 yy 的值
     * @param returnValIsXx true: 返回值是xx值, false: 返回值是yy值, 默认true
     *
     * @return null 或 int值
     */
    public static Integer getRatioVal(String xSrc, String ySrc, String xxOrYy, boolean... returnValIsXx) {
        if (!RegexUtil.isNumber(xSrc) || !RegexUtil.isNumber(ySrc) || !RegexUtil.isNumber(xxOrYy)) {
            return null;
        }
        Double xSrcVal = Double.valueOf(xSrc);
        Double ySrcVal = Double.valueOf(ySrc);
        Double xxOrYyVal = Double.valueOf(xxOrYy);
        if (xSrcVal <= 0 || ySrcVal <= 0 || xxOrYyVal <= 0) return null;

        boolean isXx = returnValIsXx == null || returnValIsXx.length == 0 ? true : returnValIsXx[0];

        Double returnXxOrYy;
        if (isXx) {   // 求等比宽
            returnXxOrYy = (xSrcVal / ySrcVal) * xxOrYyVal;
        } else {    // 求等比高
            returnXxOrYy = (ySrcVal * xxOrYyVal) / xSrcVal;
        }
        return returnXxOrYy == null ? null : returnXxOrYy.intValue();
    }

    /**
     * 求等比缩放后的值 <br />
     * 如: x / y = x1 / y1
     *
     * @param xSrc          x的值
     * @param ySrc          y的值
     * @param xxOrYy        xx 或 yy 的值
     * @param returnValIsXx true: 返回值是xx值, false: 返回值是yy值, 默认true
     *
     * @return null 或 int值
     */
    public static Integer getRatioVal(int xSrc, int ySrc, int xxOrYy, boolean... returnValIsXx) {
        if (xSrc <= 0 || ySrc <= 0 || xxOrYy <= 0) return null;

        boolean isXx = returnValIsXx == null || returnValIsXx.length == 0 ? true : returnValIsXx[0];

        Double returnXxOrYy;
        if (isXx) {   // 求等比宽
            returnXxOrYy = ((double) xSrc / ySrc) * xxOrYy;
        } else {    // 求等比高
            returnXxOrYy = ((double) ySrc * xxOrYy) / xSrc;
        }
        return returnXxOrYy == null ? null : returnXxOrYy.intValue();
    }


    /**
     * 判断字符串是否为时装平台中的UUID, 规则: <br />
     * 数字/大小写字母/总字符为32位
     *
     * @param id
     *
     * @return
     */
    public static boolean isUUID(String id) {
        if (StringUtil.isEmpty(id)) return false;
        return RegexUtil.isMatcher(id, "^[0-9A-Za-z]{32}$");
    }

    public static boolean isNotUUID(String id) {
        return !StringUtil.isUUID(id);
    }

    public static boolean isAutoId(Long id) {
        return id != null && id > 0;
    }

    public static boolean isNotAutoId(Long id) {
        return id == null || id <= 0;
    }
}
