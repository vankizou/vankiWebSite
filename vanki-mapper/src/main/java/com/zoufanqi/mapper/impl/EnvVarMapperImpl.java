package com.zoufanqi.mapper.impl;

import com.zoufanqi.entity.EnvVar;
import com.zoufanqi.entity.EnvVarExample;
import com.zoufanqi.mapper.EnvVarMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class EnvVarMapperImpl extends BaseMapper implements EnvVarMapper {

    @Override
    public int countByExample(EnvVarExample example) {
        EnvVarMapper mapper = this.getReadSqlSessionTemplate().getMapper(EnvVarMapper.class);
        return mapper.countByExample(example);
    }

    @Override
    public int deleteByExample(EnvVarExample example) {
        EnvVarMapper mapper = this.getWriteSqlSessionTemplate().getMapper(EnvVarMapper.class);
        return mapper.deleteByExample(example);
    }

    @Override
    public int deleteByPrimaryKey(Long id) {
        EnvVarMapper mapper = this.getWriteSqlSessionTemplate().getMapper(EnvVarMapper.class);
        return mapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(EnvVar record) {
        EnvVarMapper mapper = this.getWriteSqlSessionTemplate().getMapper(EnvVarMapper.class);
        return mapper.insert(record);
    }

    @Override
    public int insertSelective(EnvVar record) {
        EnvVarMapper mapper = this.getWriteSqlSessionTemplate().getMapper(EnvVarMapper.class);
        return mapper.insertSelective(record);
    }

    @Override
    public List<EnvVar> selectByExample(EnvVarExample example) {
        EnvVarMapper mapper = this.getReadSqlSessionTemplate().getMapper(EnvVarMapper.class);
        return mapper.selectByExample(example);
    }

    @Override
    public EnvVar selectByPrimaryKey(Long id) {
        EnvVarMapper mapper = this.getReadSqlSessionTemplate().getMapper(EnvVarMapper.class);
        return mapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByExampleSelective(EnvVar record, EnvVarExample example) {
        EnvVarMapper mapper = this.getWriteSqlSessionTemplate().getMapper(EnvVarMapper.class);
        return mapper.updateByExampleSelective(record, example);
    }

    @Override
    public int updateByExample(EnvVar record, EnvVarExample example) {
        EnvVarMapper mapper = this.getWriteSqlSessionTemplate().getMapper(EnvVarMapper.class);
        return mapper.updateByExample(record, example);
    }

    @Override
    public int updateByPrimaryKeySelective(EnvVar record) {
        EnvVarMapper mapper = this.getWriteSqlSessionTemplate().getMapper(EnvVarMapper.class);
        return mapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(EnvVar record) {
        EnvVarMapper mapper = this.getWriteSqlSessionTemplate().getMapper(EnvVarMapper.class);
        return mapper.updateByPrimaryKey(record);
    }

}
