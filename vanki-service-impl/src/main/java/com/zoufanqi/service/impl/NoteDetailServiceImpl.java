package com.zoufanqi.service.impl;

import com.alibaba.fastjson.JSON;
import com.zoufanqi.consts.ConstDB;
import com.zoufanqi.consts.EnumRedisKey;
import com.zoufanqi.entity.NoteDetail;
import com.zoufanqi.entity.NoteDetailExample;
import com.zoufanqi.exception.ZouFanqiException;
import com.zoufanqi.mapper.NoteDetailMapper;
import com.zoufanqi.result.ResultBuilder;
import com.zoufanqi.result.ResultJson;
import com.zoufanqi.service.NoteDetailService;
import com.zoufanqi.service.redis.RedisTemplate;
import com.zoufanqi.status.EnumStatusCode;
import com.zoufanqi.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service("noteDetailService")
public class NoteDetailServiceImpl implements NoteDetailService {
    @Autowired
    private NoteDetailMapper noteDetailMapper;
    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public ResultJson add(NoteDetail noteDetail) throws ZouFanqiException {
        if (noteDetail == null ||
                StringUtil.isNotId(noteDetail.getUserId()) ||
                StringUtil.isNotId(noteDetail.getNoteId()) ||
                StringUtil.isEmpty(noteDetail.getContent()))
            return ResultBuilder.buildParamError();

        if (noteDetail.getType() == null) noteDetail.setType(ConstDB.NoteDetail.TYPE_MARKDOWN);
        if (noteDetail.getSequence() == null) noteDetail.setSequence(ConstDB.FIRST_SEQUENCE);

        noteDetail.setCreateDatetime(new Date());

        int status = this.noteDetailMapper.insertSelective(noteDetail);
        if (status > 0)
            return ResultBuilder.build();
        else
            return ResultBuilder.buildDBError();
    }

    @Override
    public ResultJson updateById(NoteDetail noteDetail) throws ZouFanqiException {
        if (StringUtil.isNotId(noteDetail.getId()) || StringUtil.isNotId(noteDetail.getUserId()))
            return ResultBuilder.buildParamError();

        Long userId = noteDetail.getUserId();
        if (StringUtil.isNotId(userId)) {
            NoteDetail old = this.getById(noteDetail.getId());
            if (old == null) return ResultBuilder.buildError(EnumStatusCode.DB_NOT_FOUND);
            if (StringUtil.notEquals(userId, old.getUserId()))
                ResultBuilder.buildError(EnumStatusCode.DB_DATA_NOT_YOURS);
        }
        int status = this.noteDetailMapper.updateByPrimaryKeyWithBLOBs(noteDetail);
        if (status > 0)
            return ResultBuilder.build();
        else
            return ResultBuilder.buildError(EnumStatusCode.DB_ERROR);
    }

    @Override
    public ResultJson deleteById(Long id) throws ZouFanqiException {
        if (StringUtil.isNotId(id)) return ResultBuilder.build();
        int status = this.noteDetailMapper.deleteByPrimaryKey(id);
        if (status > 0) return ResultBuilder.build();
        return ResultBuilder.buildDBError();
    }

    @Override
    public ResultJson deleteByNoteId(Long userId, Long noteId) throws ZouFanqiException {
        return deleteByNoteId(userId, noteId, null);
    }

    @Override
    public ResultJson deleteByNoteId(Long userId, Long noteId, List<Long> excludeIdList) throws ZouFanqiException {
        if (StringUtil.isNotId(userId) || StringUtil.isNotId(noteId)) return ResultBuilder.build();

        NoteDetailExample example = new NoteDetailExample();
        NoteDetailExample.Criteria c = example.createCriteria();
        c.andNoteIdEqualTo(noteId).andUserIdEqualTo(userId);

        if (excludeIdList != null && !excludeIdList.isEmpty()) c.andIdNotIn(excludeIdList);

        this.noteDetailMapper.deleteByExample(example);

        return ResultBuilder.build();
    }

    @Override
    public NoteDetail getById(Long id) throws ZouFanqiException {
        if (StringUtil.isNotId(id)) return null;
        return this.noteDetailMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<NoteDetail> getListByNoteId(Long noteId) throws ZouFanqiException {
        if (StringUtil.isNotId(noteId)) return new ArrayList<>();

        String key = EnumRedisKey.TIME_NOTE_DETAIL_LIST_.name() + noteId;
        String redisInfo = this.redisTemplate.get(key);

        // 缓存数据
        if (StringUtil.isNotEmpty(redisInfo)) return JSON.parseArray(redisInfo, NoteDetail.class);

        NoteDetailExample example = new NoteDetailExample();
        example.createCriteria().andNoteIdEqualTo(noteId);

        List<NoteDetail> list = this.noteDetailMapper.selectByExampleWithBLOBs(example);

        if (list == null || list.isEmpty()) list = new ArrayList<>();
        this.redisTemplate.setex(key, EnumRedisKey.TIME_NOTE_DETAIL_LIST_.getTime(), JSON.toJSONString(list));
        return list;
    }
}