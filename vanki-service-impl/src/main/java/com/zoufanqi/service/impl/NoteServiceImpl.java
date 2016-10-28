package com.zoufanqi.service.impl;

import com.alibaba.fastjson.JSON;
import com.zoufanqi.consts.ConstDB;
import com.zoufanqi.entity.Note;
import com.zoufanqi.entity.NoteContent;
import com.zoufanqi.entity.NoteExample;
import com.zoufanqi.exception.ZouFanqiException;
import com.zoufanqi.mapper.NoteMapper;
import com.zoufanqi.result.ResultBuilder;
import com.zoufanqi.result.ResultJson;
import com.zoufanqi.service.NoteContentService;
import com.zoufanqi.service.NoteService;
import com.zoufanqi.status.StatusCode;
import com.zoufanqi.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

@Service("noteService")
public class NoteServiceImpl implements NoteService {
    @Autowired
    private NoteMapper noteMapper;
    @Autowired
    private NoteContentService noteContentService;

    @Override
    public ResultJson add(Long userId, String title, Boolean isPublic, String keyword, String description,
                          String noteContentJsonArrStr)
            throws ZouFanqiException {
        if (StringUtil.isNotAutoId(userId) || StringUtil.isEmpty(title))
            return ResultBuilder.buildParamError();

        Note note = new Note();
        Date date = new Date();
        note.setCreateDatetime(date);
        note.setUpdateDatetime(date);
        note.setStatus(ConstDB.Note.STATUS_PASS);

        note.setUserId(userId);
        note.setTitle(title);
        note.setIsPublic(isPublic != null && isPublic ? ConstDB.Note.IS_PUBLIC_YES : ConstDB.Note.IS_PUBLIC_NO);
        note.setKeyword(keyword);
        note.setDescription(description);

        int status = this.noteMapper.insertSelective(note);
        if (status > 0) {
            this.addNoteContent(note.getId(), noteContentJsonArrStr);
            return ResultBuilder.build();
        }
        return ResultBuilder.buildErrorDB();
    }

    /**
     * @param loginUserId
     * @param id
     * @param title
     * @param isPublic
     * @param keyword
     * @param description
     * @param noteContentJsonArrStr
     *
     * @return <br />
     * 1000 数据不存在
     * 1002 不是本人操作
     *
     * @throws ZouFanqiException
     */
    @Override
    public ResultJson updateById(Long loginUserId, Long id, String title, Boolean isPublic, String keyword,
                                 String description, String noteContentJsonArrStr)
            throws ZouFanqiException {
        if (StringUtil.isNotAutoId(loginUserId)) return ResultBuilder.buildParamError();
        Note note = this.getById(id);
        if (note == null) return ResultBuilder.buildError(StatusCode.ID_NOT_EXIST);
        if (note.getUserId() != loginUserId) return ResultBuilder.buildError(StatusCode.DATA_NOT_YOURSELF);

        Note uNote = new Note();
        uNote.setId(id);
        uNote.setTitle(title);
        uNote.setIsPublic(isPublic == null ? null : isPublic ? ConstDB.Note.IS_PUBLIC_YES : ConstDB.Note.IS_PUBLIC_NO);
        uNote.setKeyword(keyword);
        uNote.setDescription(description);
        int status = this.noteMapper.updateByPrimaryKeySelective(uNote);
        if (status > 0) {
            this.addNoteContent(id, noteContentJsonArrStr);
            return ResultBuilder.build();
        }
        return ResultBuilder.buildErrorDB();
    }

    /**
     * 添加笔记内容
     *
     * @param noteId
     * @param noteContentJsonArrStr
     *
     * @return <br />
     * -1 noteId或jsonArr为空
     * 1 成功
     *
     * @throws ZouFanqiException
     */
    private int addNoteContent(Long noteId, String noteContentJsonArrStr) throws ZouFanqiException {
        if (StringUtil.isNotAutoId(noteId) || StringUtil.isEmpty(noteContentJsonArrStr)) return -1;
        List<NoteContent> ncList = JSON.parseArray(noteContentJsonArrStr, NoteContent.class);
        if (ncList == null || ncList.isEmpty()) return 1;

        /**
         * 笔记内容
         */
        // 排序, 若内容对象有null, 则直接报错
        Collections.sort(ncList, new Comparator<NoteContent>() {
            @Override
            public int compare(NoteContent o1, NoteContent o2) {
                Integer o1Order = o1.getOrder();
                Integer o2Order = o2.getOrder();
                if (o1Order == null || o2Order == null) return 0;
                return o1Order - o2Order;
            }
        });
        for (NoteContent nc : ncList) {
            int ncStatus = this.noteContentService.add(noteId, nc.getType(), nc.getOrder(), nc.getContent());
            if (ncStatus <= 0) throw new ZouFanqiException(StatusCode.ERROR_PARAM);
        }
        return 1;
    }

    @Override
    public ResultJson deleteById(Long loginUserId, Long id) throws ZouFanqiException {
        if (StringUtil.isNotAutoId(loginUserId) || StringUtil.isNotAutoId(id)) return ResultBuilder.buildParamError();

        Note old = this.getById(id);
        if (old == null || old.getUserId() != loginUserId) return ResultBuilder.build();

        Note note = new Note();
        note.setId(id);
        note.setIsDel(ConstDB.ISDEL_TRUE);
        int status = this.noteMapper.updateByPrimaryKeySelective(note);
        if (status > 0) {
            this.noteContentService.deleteByNoteId(id);
            return ResultBuilder.build();
        }
        return ResultBuilder.buildErrorDB();
    }

    @Override
    public Note getById(Long id) throws ZouFanqiException {
        if (StringUtil.isNotAutoId(id)) return null;

        NoteExample example = new NoteExample();
        example.createCriteria().andIsDelEqualTo(ConstDB.ISDEL_FALSE).andIdEqualTo(id);

        List<Note> list = this.noteMapper.selectByExample(example);
        if (list == null || list.isEmpty()) return null;
        return list.get(0);
    }
}