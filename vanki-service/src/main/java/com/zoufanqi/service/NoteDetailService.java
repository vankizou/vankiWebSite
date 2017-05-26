package com.zoufanqi.service;

import com.zoufanqi.entity.NoteDetail;
import com.zoufanqi.exception.ZouFanqiException;
import com.zoufanqi.result.ResultJson;

import java.util.List;

public interface NoteDetailService {

    ResultJson add(NoteDetail noteDetail) throws ZouFanqiException;

    ResultJson updateById(NoteDetail noteDetail) throws ZouFanqiException;

    ResultJson deleteById(Long id) throws ZouFanqiException;

    ResultJson deleteByNoteId(Long userId, Long noteId) throws ZouFanqiException;

    ResultJson deleteByNoteId(Long userId, Long noteId, List<Long> excludeIdList) throws ZouFanqiException;

    NoteDetail getById(Long id) throws ZouFanqiException;

    List<NoteDetail> getListByNoteId(Long noteId) throws ZouFanqiException;
}