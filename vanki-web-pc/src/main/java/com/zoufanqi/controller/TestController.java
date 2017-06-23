package com.zoufanqi.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by vanki on 2017/4/10.
 */
@Controller
@RequestMapping("/test")
public class TestController {
    //@RequestMapping("/p.html")
    public String aa(String p) {
        return p;
    }

    @ResponseBody
    @RequestMapping("/a.html")
    public String a(HttpServletRequest request) {
        System.out.println("Protocol: " + request.getProtocol());
        System.out.println("Scheme: " + request.getScheme());
        System.out.println("Server Name: " + request.getServerName() );
        System.out.println("Protocol: " + request.getProtocol());
        System.out.println("Remote Addr: " + request.getRemoteAddr());
        System.out.println("Remote Host: " + request.getRemoteHost());
        System.out.println("Character Encoding: " + request.getCharacterEncoding());
        System.out.println("Accept: " + request.getHeader("Accept"));
        System.out.println("Host: " + request.getHeader("Host"));
        System.out.println("Accept-Language : " + request.getHeader("Accept-Language"));
        System.out.println("Accept-Encoding : " + request.getHeader("Accept-Encoding"));
        System.out.println("User-Agent : " + request.getHeader("User-Agent"));
        System.out.println("Connection : " + request.getHeader("Connection"));

        return "";
    }
}
