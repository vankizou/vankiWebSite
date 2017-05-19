package com.zoufanqi.mapper.impl;

import org.springframework.stereotype.Repository;
import com.zoufanqi.entity.AuthResource;
import com.zoufanqi.entity.AuthResourceExample;
import com.zoufanqi.mapper.AuthResourceMapper;

import java.util.List;

@Repository
public class AuthResourceMapperImpl extends BaseMapper implements AuthResourceMapper {

    @Override
    public int countByExample(AuthResourceExample example) {
        AuthResourceMapper mapper = this.getReadSqlSessionTemplate().getMapper(AuthResourceMapper.class);
        return mapper.countByExample(example);
    }

    @Override
    public int deleteByExample(AuthResourceExample example) {
        AuthResourceMapper mapper = this.getWriteSqlSessionTemplate().getMapper(AuthResourceMapper.class);
        return mapper.deleteByExample(example);
    }

    @Override
    public int deleteByPrimaryKey(Long id) {
        AuthResourceMapper mapper = this.getWriteSqlSessionTemplate().getMapper(AuthResourceMapper.class);
        return mapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(AuthResource record) {
        AuthResourceMapper mapper = this.getWriteSqlSessionTemplate().getMapper(AuthResourceMapper.class);
        return mapper.insert(record);
    }

    @Override
    public int insertSelective(AuthResource record) {
        AuthResourceMapper mapper = this.getWriteSqlSessionTemplate().getMapper(AuthResourceMapper.class);
        return mapper.insertSelective(record);
    }

    @Override
    public List<AuthResource> selectByExample(AuthResourceExample example) {
        AuthResourceMapper mapper = this.getReadSqlSessionTemplate().getMapper(AuthResourceMapper.class);
        return mapper.selectByExample(example);
    }

    @Override
    public AuthResource selectByPrimaryKey(Long id) {
        AuthResourceMapper mapper = this.getReadSqlSessionTemplate().getMapper(AuthResourceMapper.class);
        return mapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByExampleSelective(AuthResource record, AuthResourceExample example) {
        AuthResourceMapper mapper = this.getWriteSqlSessionTemplate().getMapper(AuthResourceMapper.class);
        return mapper.updateByExampleSelective(record, example);
    }

    @Override
    public int updateByExample(AuthResource record, AuthResourceExample example) {
        AuthResourceMapper mapper = this.getWriteSqlSessionTemplate().getMapper(AuthResourceMapper.class);
        return mapper.updateByExample(record, example);
    }

    @Override
    public int updateByPrimaryKeySelective(AuthResource record) {
        AuthResourceMapper mapper = this.getWriteSqlSessionTemplate().getMapper(AuthResourceMapper.class);
        return mapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(AuthResource record) {
        AuthResourceMapper mapper = this.getWriteSqlSessionTemplate().getMapper(AuthResourceMapper.class);
        return mapper.updateByPrimaryKey(record);
    }

}
