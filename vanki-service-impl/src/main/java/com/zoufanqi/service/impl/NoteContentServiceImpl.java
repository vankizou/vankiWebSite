package com.zoufanqi.service.impl;

import com.zoufanqi.consts.ConstDB;
import com.zoufanqi.entity.NoteContent;
import com.zoufanqi.entity.NoteContentExample;
import com.zoufanqi.exception.ZouFanqiException;
import com.zoufanqi.mapper.NoteContentMapper;
import com.zoufanqi.service.NoteContentService;
import com.zoufanqi.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service("noteContentService")
public class NoteContentServiceImpl implements NoteContentService {
    @Autowired
    private NoteContentMapper noteContentMapper;

    @Override
    public int add(Long noteId, Integer type, Integer order, String content) throws ZouFanqiException {
        if (StringUtil.isNotAutoId(noteId) || type == null || order == null || StringUtil.isEmpty(content))
            return -1;
        NoteContent nc = new NoteContent();
        nc.setCreateDatetime(new Date());
        nc.setIsDel(ConstDB.ISDEL_FALSE);

        nc.setNoteId(noteId);
        nc.setType(type);
        nc.setOrder(order);
        nc.setContent(content);

        return this.noteContentMapper.insertSelective(nc);
    }

    @Override
    public int deleteByNoteId(Long noteId) throws ZouFanqiException {
        if (StringUtil.isNotAutoId(noteId)) return -1;

        NoteContentExample example = new NoteContentExample();
        example.createCriteria().andIsDelEqualTo(ConstDB.ISDEL_FALSE).andNoteIdEqualTo(noteId);

        NoteContent nc = new NoteContent();
        nc.setIsDel(ConstDB.ISDEL_TRUE);

        return this.noteContentMapper.updateByExampleSelective(nc, example);
    }

    @Override
    public List<NoteContent> getListByNoteId(Long noteId) throws ZouFanqiException {
        if (StringUtil.isNotAutoId(noteId)) return null;

        NoteContentExample example = new NoteContentExample();
        example.setOrderByClause("ID ASC");
        example.createCriteria().andIsDelEqualTo(ConstDB.ISDEL_FALSE).andNoteIdEqualTo(noteId);

        return this.noteContentMapper.selectByExample(example);
    }
}