package com.zoufanqi.controller;

import com.zoufanqi.exception.ZouFanqiException;
import com.zoufanqi.result.ResultJson;
import com.zoufanqi.service.NoteService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 笔记
 * Created by vanki on 16/10/26.
 */
@Controller
@RequestMapping("/note")
public class NoteController {
    private NoteService noteService;

    @ResponseBody
    @RequestMapping(value = "/add.json.login", method = RequestMethod.POST)
    public ResultJson add(Long userId, String title, Boolean isPublic, String keyword, String description,
                          String noteContentJsonArrStr) throws ZouFanqiException {
        return this.noteService.add(userId, title, isPublic, keyword, description, noteContentJsonArrStr);
    }
}
