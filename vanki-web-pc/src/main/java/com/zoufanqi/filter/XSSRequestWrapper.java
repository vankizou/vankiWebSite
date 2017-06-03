package com.zoufanqi.filter;

import com.zoufanqi.utils.StringUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by vanki on 16/5/27.
 */
public class XSSRequestWrapper extends HttpServletRequestWrapper {
    // 排除过滤的
    private static final List<String> excludeList = new ArrayList<>();

    static {
        excludeList.add("noteDetailList[0].content");
    }

    public XSSRequestWrapper(HttpServletRequest request) {
        super(request);
    }

    @Override
    public String[] getParameterValues(String parameter) {
        String[] values = super.getParameterValues(parameter);
        if (values == null || excludeList.contains(parameter)) return values;

        int count = values.length;
        String[] encodedValues = new String[count];
        for (int i = 0; i < count; i++) {
            encodedValues[i] = stripXSS(values[i]);
        }
        return encodedValues;
    }

    @Override
    public String getParameter(String parameter) {
        String value = super.getParameter(parameter);
        if (value == null || excludeList.contains(parameter)) return value;
        return stripXSS(value);
    }

    @Override
    public String getHeader(String name) {
        String value = super.getHeader(name);
        if (value == null || excludeList.contains(name)) return value;
        return stripXSS(value);
    }

    private String stripXSS(String value) {
        return StringUtil.stripXSS(value);
    }
}
