package com.zoufanqi.controller;

import com.zoufanqi.exception.ZouFanqiException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

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

    @RequestMapping("/info/markdown/case.html")
    public ModelAndView markdownExample() {
        ModelAndView mv = new ModelAndView("info/markdown");
        return mv;
    }

    @RequestMapping("/404.html")
    public String error404() {
        return "common/404";
    }

    @RequestMapping("/500.html")
    public String error500() {
        return "common/500";
    }
}
