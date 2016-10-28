package com.zoufanqi.service;

import com.zoufanqi.entity.NoteContent;
import com.zoufanqi.exception.ZouFanqiException;

import java.util.List;

public interface NoteContentService {

    int add(Long noteId, Integer type, Integer order, String content) throws ZouFanqiException;

    int deleteByNoteId(Long noteId) throws ZouFanqiException;

    List<NoteContent> getListByNoteId(Long noteId) throws ZouFanqiException;
}