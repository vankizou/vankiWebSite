package com.zoufanqi.mapper.impl;

import com.zoufanqi.entity.Note;
import com.zoufanqi.entity.NoteExample;
import com.zoufanqi.mapper.NoteMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class NoteMapperImpl extends BaseMapper implements NoteMapper {

    @Override
    public int countByExample(NoteExample example) {
        NoteMapper mapper = this.getReadSqlSessionTemplate().getMapper(NoteMapper.class);
        return mapper.countByExample(example);
    }

    @Override
    public int deleteByExample(NoteExample example) {
        NoteMapper mapper = this.getWriteSqlSessionTemplate().getMapper(NoteMapper.class);
        return mapper.deleteByExample(example);
    }

    @Override
    public int deleteByPrimaryKey(Long id) {
        NoteMapper mapper = this.getWriteSqlSessionTemplate().getMapper(NoteMapper.class);
        return mapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(Note record) {
        NoteMapper mapper = this.getWriteSqlSessionTemplate().getMapper(NoteMapper.class);
        return mapper.insert(record);
    }

    @Override
    public int insertSelective(Note record) {
        NoteMapper mapper = this.getWriteSqlSessionTemplate().getMapper(NoteMapper.class);
        return mapper.insertSelective(record);
    }

    @Override
    public List<Note> selectByExample(NoteExample example) {
        NoteMapper mapper = this.getReadSqlSessionTemplate().getMapper(NoteMapper.class);
        return mapper.selectByExample(example);
    }

    @Override
    public Note selectByPrimaryKey(Long id) {
        NoteMapper mapper = this.getReadSqlSessionTemplate().getMapper(NoteMapper.class);
        return mapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByExampleSelective(Note record, NoteExample example) {
        NoteMapper mapper = this.getWriteSqlSessionTemplate().getMapper(NoteMapper.class);
        return mapper.updateByExampleSelective(record, example);
    }

    @Override
    public int updateByExample(Note record, NoteExample example) {
        NoteMapper mapper = this.getWriteSqlSessionTemplate().getMapper(NoteMapper.class);
        return mapper.updateByExample(record, example);
    }

    @Override
    public int updateByPrimaryKeySelective(Note record) {
        NoteMapper mapper = this.getWriteSqlSessionTemplate().getMapper(NoteMapper.class);
        return mapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(Note record) {
        NoteMapper mapper = this.getWriteSqlSessionTemplate().getMapper(NoteMapper.class);
        return mapper.updateByPrimaryKey(record);
    }

}
