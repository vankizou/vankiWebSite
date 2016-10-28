package com.zoufanqi.service;

import com.zoufanqi.entity.Note;
import com.zoufanqi.exception.ZouFanqiException;
import com.zoufanqi.result.ResultJson;

public interface NoteService {

    ResultJson add(Long userId, String title, Boolean isPublic, String keyword, String description,
                   String noteContentJsonArrStr)
            throws ZouFanqiException;

    ResultJson updateById(Long loginUserId, Long id, String title, Boolean isPublic, String keyword,
                          String description, String noteContentJsonArrStr)
            throws ZouFanqiException;

    ResultJson deleteById(Long loginUserId, Long id) throws ZouFanqiException;

    Note getById(Long id) throws ZouFanqiException;
}