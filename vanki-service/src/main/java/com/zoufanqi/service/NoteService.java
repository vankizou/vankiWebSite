package com.zoufanqi.service;

import com.zoufanqi.entity.Note;
import com.zoufanqi.entity.NoteDetail;
import com.zoufanqi.exception.ZouFanqiException;
import com.zoufanqi.param.common.Page;
import com.zoufanqi.result.ResultJson;
import com.zoufanqi.vo.NoteVo;

import java.util.List;
import java.util.Set;

public interface NoteService {

    ResultJson add(Note note, List<NoteDetail> noteDetailList) throws ZouFanqiException;

    ResultJson updateById(Long loginUserId, Note note, List<NoteDetail> noteDetailList, boolean isSystem) throws ZouFanqiException;

    ResultJson deleteById(Long loginUserId, Long id) throws ZouFanqiException;

    NoteVo getNoteVoById(Long loginUserId, Long id, String password) throws ZouFanqiException;

    List<NoteVo> getTreeList(Long loginUserId, Long userId, Long parentId, Integer deep) throws ZouFanqiException;

    Page<Note> getTreePage(Long loginUserId, Long userId, Long parentId, Integer pageNo, Integer pageSize, Integer navNum) throws ZouFanqiException;

    Page<Note> getHomePage(Integer pageNo, Integer pageSize, Integer navNum) throws ZouFanqiException;

    void updateNoteViewNumInRedis(Long noteId, long currViewNum) throws ZouFanqiException;

    long getNoteViewNumInRedis(Long noteId) throws ZouFanqiException;

    boolean isNoteOpenedInRedis(Long userId, Long noteId) throws ZouFanqiException;

    void closeNoteInRedis(Long userId, Long noteId) throws ZouFanqiException;

    void openNoteInRedis(Long userId, Long noteId) throws ZouFanqiException;
}