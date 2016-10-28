package com.zoufanqi.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by vanki on 16/10/26.
 */
@Controller
@RequestMapping("/")
public class RootController extends BaseSessionController {

    @RequestMapping("/logout.json")
    public String logout() {
        return "redirect:/";
    }
}
