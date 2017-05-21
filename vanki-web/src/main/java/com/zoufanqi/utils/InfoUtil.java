package com.zoufanqi.utils;

import java.util.List;

/**
 * Created by vanki on 2017/5/22.
 */
public class InfoUtil {

    public static void main(String[] args) {
        getMarkdownExample();
    }
    public static void getMarkdownExample() {
        List<String> list = FileUtil.readFileOfLine("/data/a/aa");

        StringBuffer sb = new StringBuffer();
        for (String str : list) {
            str = str.replace("\\", "\\\\");
            str = str.replace("\"", "\\\"");
            sb.append("\"").append(str).append("\\r\\n").append("\"+\r\n");
        }
        System.out.println(sb.toString());
    }
}
