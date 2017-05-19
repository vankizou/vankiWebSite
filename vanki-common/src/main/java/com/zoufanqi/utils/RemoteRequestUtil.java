package com.zoufanqi.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import javax.servlet.http.HttpServletRequest;

public class RemoteRequestUtil {

    /**
     * 获取远程访问IP地址
     * @return
     */
    public static String getRequestIP(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        if (ip != null) {
            String[] ipArray = ip.split(",");
            if (ipArray != null && ipArray.length > 1) {
                ip = ipArray[0];
            }
        }
        return ip;
    }

    /**
     * 获取请求参数
     * @param request
     * @return
     */
    @SuppressWarnings("all")
    public static Map<String, String> getRequsetParamMap(HttpServletRequest request) {
        Map properties  = request.getParameterMap();
        Iterator<Entry> it = properties.entrySet().iterator();
        Map<String, String> paramMap = new HashMap<String, String>();
        String key;
        String value;
        while(it.hasNext()) {
            Entry entry = it.next();
            key = (String) entry.getKey();
            Object objectValue = entry.getValue();
            if (null == objectValue) {
                value = "";
            } else if (objectValue instanceof String[]) {
                String[] values = (String[]) objectValue; //只打印同名参数的第一个值
                value = values[0];
            } else {
                value = objectValue.toString();
            }
            try {
				value = URLDecoder.decode(value, "utf-8");
			} catch (UnsupportedEncodingException e) {
				value = URLDecoder.decode(value);
			}
            paramMap.put(key, value);
        }
        return paramMap;
    }
    
    /**
     * 获取请求参数，带过滤
     * @param request
     * @return
     */
    @SuppressWarnings("all")
	public static Map<String, String> getRequsetParamMap(HttpServletRequest request, String filterList) {
        Map properties  = request.getParameterMap();
       
        Iterator<Entry> it = properties.entrySet().iterator();
        Map<String, String> paramMap = new HashMap<String, String>();
        String key;
        String value;
        while(it.hasNext()) {
            Entry entry = it.next();
            key = (String) entry.getKey();
            Object objectValue = entry.getValue();
            if (null == objectValue) {
                value = "";
            } else if (objectValue instanceof String[]) {
                String[] values = (String[]) objectValue; //只打印同名参数的第一个值
                value = values[0];
            } else {
                value = objectValue.toString();
            }
            try {
				value = URLDecoder.decode(value, "utf-8");
			} catch (UnsupportedEncodingException e) {
				value = URLDecoder.decode(value);
			}
            paramMap.put(key, value);
        }
        if(filterList != null){
       	 String[] filters = filterList.split(",");
            
            for (String iterable_element : filters) {
            	paramMap.remove(iterable_element);
    		}
       }
        return paramMap;
    }

    /**
     * 获取请求数据
     * @param request
     * @return
     */
    public static String getRequestData(HttpServletRequest request) {
        StringBuilder requestData = new StringBuilder();
        requestData.append("RequestInfo=RemoteAddr:").append(getRequestIP(request));
        requestData.append("--RequestURL:").append(request.getRequestURL());
        requestData.append("--Referer:").append(request.getHeader("referer"));
        requestData.append("--ParameterMap:").append(getRequsetParamMap(request));
        return requestData.toString();
    }


}
