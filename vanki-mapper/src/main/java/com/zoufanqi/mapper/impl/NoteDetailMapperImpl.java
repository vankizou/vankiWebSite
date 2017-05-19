package com.zoufanqi.mapper.impl;

import com.zoufanqi.entity.NoteDetail;
import com.zoufanqi.entity.NoteDetailExample;
import com.zoufanqi.mapper.NoteDetailMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class NoteDetailMapperImpl extends BaseMapper implements NoteDetailMapper {

    @Override
    public int countByExample(NoteDetailExample example) {
        NoteDetailMapper mapper = this.getReadSqlSessionTemplate().getMapper(NoteDetailMapper.class);
        return mapper.countByExample(example);
    }

    @Override
    public int deleteByExample(NoteDetailExample example) {
        NoteDetailMapper mapper = this.getWriteSqlSessionTemplate().getMapper(NoteDetailMapper.class);
        return mapper.deleteByExample(example);
    }

    @Override
    public int deleteByPrimaryKey(Long id) {
        NoteDetailMapper mapper = this.getWriteSqlSessionTemplate().getMapper(NoteDetailMapper.class);
        return mapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(NoteDetail record) {
        NoteDetailMapper mapper = this.getWriteSqlSessionTemplate().getMapper(NoteDetailMapper.class);
        return mapper.insert(record);
    }

    @Override
    public int insertSelective(NoteDetail record) {
        NoteDetailMapper mapper = this.getWriteSqlSessionTemplate().getMapper(NoteDetailMapper.class);
        return mapper.insertSelective(record);
    }

    @Override
    public List<NoteDetail> selectByExampleWithBLOBs(NoteDetailExample example) {
        NoteDetailMapper mapper = this.getReadSqlSessionTemplate().getMapper(NoteDetailMapper.class);
        return mapper.selectByExampleWithBLOBs(example);
    }

    @Override
    public List<NoteDetail> selectByExample(NoteDetailExample example) {
        NoteDetailMapper mapper = this.getReadSqlSessionTemplate().getMapper(NoteDetailMapper.class);
        return mapper.selectByExample(example);
    }

    @Override
    public NoteDetail selectByPrimaryKey(Long id) {
        NoteDetailMapper mapper = this.getReadSqlSessionTemplate().getMapper(NoteDetailMapper.class);
        return mapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByExampleSelective(NoteDetail record, NoteDetailExample example) {
        NoteDetailMapper mapper = this.getWriteSqlSessionTemplate().getMapper(NoteDetailMapper.class);
        return mapper.updateByExampleSelective(record, example);
    }

    @Override
    public int updateByExampleWithBLOBs(NoteDetail record, NoteDetailExample example) {
        NoteDetailMapper mapper = this.getWriteSqlSessionTemplate().getMapper(NoteDetailMapper.class);
        return mapper.updateByExampleWithBLOBs(record, example);
    }

    @Override
    public int updateByExample(NoteDetail record, NoteDetailExample example) {
        NoteDetailMapper mapper = this.getWriteSqlSessionTemplate().getMapper(NoteDetailMapper.class);
        return mapper.updateByExample(record, example);
    }

    @Override
    public int updateByPrimaryKeySelective(NoteDetail record) {
        NoteDetailMapper mapper = this.getWriteSqlSessionTemplate().getMapper(NoteDetailMapper.class);
        return mapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKeyWithBLOBs(NoteDetail record) {
        NoteDetailMapper mapper = this.getWriteSqlSessionTemplate().getMapper(NoteDetailMapper.class);
        return mapper.updateByPrimaryKeyWithBLOBs(record);
    }

    @Override
    public int updateByPrimaryKey(NoteDetail record) {
        NoteDetailMapper mapper = this.getWriteSqlSessionTemplate().getMapper(NoteDetailMapper.class);
        return mapper.updateByPrimaryKey(record);
    }

}
