package com.zoufanqi.mapper.impl;

import com.zoufanqi.entity.Unite;
import com.zoufanqi.entity.UniteExample;
import com.zoufanqi.mapper.UniteMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UniteMapperImpl extends BaseMapper implements UniteMapper {

    @Override
    public int countByExample(UniteExample example) {
        UniteMapper mapper = this.getReadSqlSessionTemplate().getMapper(UniteMapper.class);
        return mapper.countByExample(example);
    }

    @Override
    public int deleteByExample(UniteExample example) {
        UniteMapper mapper = this.getWriteSqlSessionTemplate().getMapper(UniteMapper.class);
        return mapper.deleteByExample(example);
    }

    @Override
    public int deleteByPrimaryKey(Long id) {
        UniteMapper mapper = this.getWriteSqlSessionTemplate().getMapper(UniteMapper.class);
        return mapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(Unite record) {
        UniteMapper mapper = this.getWriteSqlSessionTemplate().getMapper(UniteMapper.class);
        return mapper.insert(record);
    }

    @Override
    public int insertSelective(Unite record) {
        UniteMapper mapper = this.getWriteSqlSessionTemplate().getMapper(UniteMapper.class);
        return mapper.insertSelective(record);
    }

    @Override
    public List<Unite> selectByExample(UniteExample example) {
        UniteMapper mapper = this.getReadSqlSessionTemplate().getMapper(UniteMapper.class);
        return mapper.selectByExample(example);
    }

    @Override
    public Unite selectByPrimaryKey(Long id) {
        UniteMapper mapper = this.getReadSqlSessionTemplate().getMapper(UniteMapper.class);
        return mapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByExampleSelective(Unite record, UniteExample example) {
        UniteMapper mapper = this.getWriteSqlSessionTemplate().getMapper(UniteMapper.class);
        return mapper.updateByExampleSelective(record, example);
    }

    @Override
    public int updateByExample(Unite record, UniteExample example) {
        UniteMapper mapper = this.getWriteSqlSessionTemplate().getMapper(UniteMapper.class);
        return mapper.updateByExample(record, example);
    }

    @Override
    public int updateByPrimaryKeySelective(Unite record) {
        UniteMapper mapper = this.getWriteSqlSessionTemplate().getMapper(UniteMapper.class);
        return mapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(Unite record) {
        UniteMapper mapper = this.getWriteSqlSessionTemplate().getMapper(UniteMapper.class);
        return mapper.updateByPrimaryKey(record);
    }

}
