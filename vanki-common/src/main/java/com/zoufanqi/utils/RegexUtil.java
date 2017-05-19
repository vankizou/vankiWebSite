package com.zoufanqi.utils;

import java.util.regex.Pattern;

public class RegexUtil {

    /**
     * 中国移动拥有号码段为:134 135 136 137 138 139 147 150 151 152 157 158 159 178 182 183 184 187 188
     * 19个号段 中国联通拥有号码段为:130 131 132 145 155 156 175 176 185 186;10个号段
     * 中国电信拥有号码段为:133 153 177 180 181 189;6个号码段
     * 虚拟运营商:170
     */
    private static Pattern regMobilePattern = Pattern.compile("^1(([3][456789])|([4][7])|([5][012789])|([7][8])|([8][23478]))[0-9]{8}$");
    private static Pattern regUnicomPattern = Pattern.compile("^1(([3][012])|([4][5])|([5][56])|([7][5])|([8][56]))[0-9]{8}$");
    private static Pattern regTelecomPattern = Pattern.compile("^1(([3][3])|([5][3])|([7][07])|([8][019]))[0-9]{8}$");

    private static Pattern regIpPattern = Pattern.compile("([1-9]|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])(\\.(\\d|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])){3}");
    private static Pattern regNumberPattern = Pattern.compile("^-?\\d+$");
    private static Pattern regDecimalPattern = Pattern.compile("^-?\\d?\\.?\\d+$");
    private static Pattern regEmailPattern = Pattern.compile("^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\\.[a-zA-Z0-9_-]{2,3}){1,2})$");
    private static Pattern regDatetimePattern = Pattern.compile("^\\d{4}-\\d{1,2}-\\d{1,2}\\s+\\d{1,2}:\\d{1,2}:\\d{1,2}$");

    public static boolean isNumber(String str) {
        return str != null ? regNumberPattern.matcher(str).find() : false;
    }

    public static boolean isDecimals(String str) {
        return str != null ? regDecimalPattern.matcher(str).find() : false;
    }

    public static boolean isEmail(String email) {
        return email != null ?  regEmailPattern.matcher(email).find() : false;
    }

    /**
     * 有 bug ,慎用
     *
     * @param ip
     *
     * @return
     */
    public static boolean isIp(String ip) {
        //regIpStr = "[1-2]?[0-5]?[0-5]?\\.[1-2]?[0-5]?[0-5]?\\.[1-2]?[0-5]?[0-5]?\\.[1-2]?[0-5]?[0-5]?";
        return ip != null ? regIpPattern.matcher(ip).find() : false;
    }

    public static boolean isPhone(String mobile) {
        if (!StringUtil.isEmpty(mobile)) {
            /** */
            /** 第一步判断中国移动 */
            if (regMobilePattern.matcher(mobile).find()) {
                return true;
            }
            /** */
            /** 第二步判断中国联通 */
            if (regUnicomPattern.matcher(mobile).find()) {
                return true;
            }
            /** */
            /** 第三步判断中国电信 */
            if (regTelecomPattern.matcher(mobile).find()) {
                return true;
            }
        }
        return false;

    }

    /**
     * 验证字符串是否为正确的日期时间格式, 例: <br />
     * 2016-03-10 16:00:26
     *
     * @param datetime
     *
     * @return
     */
    public static boolean isDatetime(String datetime) {
        if (!StringUtil.isEmpty(datetime) && regDatetimePattern.matcher(datetime).find()) {
            return true;
        }
        return false;
    }


    public static boolean isMatcher(String str, String regex) {
        return Pattern.compile(regex).matcher(str).find();
    }

    public static void main(String[] args) {
        System.out.println(regNumberPattern.matcher(" 123 sdf").matches());
    }


}
