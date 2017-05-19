package com.zoufanqi.controller;

import com.zoufanqi.exception.ZouFanqiException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by vanki on 16/10/26.
 */
@Controller
@RequestMapping("/")
public class RootController extends BaseController {

    @RequestMapping("/")
    public String index() throws ZouFanqiException {
        return "index/index";
    }

    @RequestMapping("/logout.html")
    public String logout() throws ZouFanqiException {
        this.clearCookieAndSessionInfo();
        return "redirect:/";
    }

    @RequestMapping("/404.html")
    public String error404() {
        return "common/404";
    }
}
