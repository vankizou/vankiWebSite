package com.zoufanqi.mapper.impl;

import org.springframework.stereotype.Repository;
import com.zoufanqi.entity.AuthRole;
import com.zoufanqi.entity.AuthRoleExample;
import com.zoufanqi.mapper.AuthRoleMapper;

import java.util.List;

@Repository
public class AuthRoleMapperImpl extends BaseMapper implements AuthRoleMapper {

    @Override
    public int countByExample(AuthRoleExample example) {
        AuthRoleMapper mapper = this.getReadSqlSessionTemplate().getMapper(AuthRoleMapper.class);
        return mapper.countByExample(example);
    }

    @Override
    public int deleteByExample(AuthRoleExample example) {
        AuthRoleMapper mapper = this.getWriteSqlSessionTemplate().getMapper(AuthRoleMapper.class);
        return mapper.deleteByExample(example);
    }

    @Override
    public int deleteByPrimaryKey(Long id) {
        AuthRoleMapper mapper = this.getWriteSqlSessionTemplate().getMapper(AuthRoleMapper.class);
        return mapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(AuthRole record) {
        AuthRoleMapper mapper = this.getWriteSqlSessionTemplate().getMapper(AuthRoleMapper.class);
        return mapper.insert(record);
    }

    @Override
    public int insertSelective(AuthRole record) {
        AuthRoleMapper mapper = this.getWriteSqlSessionTemplate().getMapper(AuthRoleMapper.class);
        return mapper.insertSelective(record);
    }

    @Override
    public List<AuthRole> selectByExample(AuthRoleExample example) {
        AuthRoleMapper mapper = this.getReadSqlSessionTemplate().getMapper(AuthRoleMapper.class);
        return mapper.selectByExample(example);
    }

    @Override
    public AuthRole selectByPrimaryKey(Long id) {
        AuthRoleMapper mapper = this.getReadSqlSessionTemplate().getMapper(AuthRoleMapper.class);
        return mapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByExampleSelective(AuthRole record, AuthRoleExample example) {
        AuthRoleMapper mapper = this.getWriteSqlSessionTemplate().getMapper(AuthRoleMapper.class);
        return mapper.updateByExampleSelective(record, example);
    }

    @Override
    public int updateByExample(AuthRole record, AuthRoleExample example) {
        AuthRoleMapper mapper = this.getWriteSqlSessionTemplate().getMapper(AuthRoleMapper.class);
        return mapper.updateByExample(record, example);
    }

    @Override
    public int updateByPrimaryKeySelective(AuthRole record) {
        AuthRoleMapper mapper = this.getWriteSqlSessionTemplate().getMapper(AuthRoleMapper.class);
        return mapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(AuthRole record) {
        AuthRoleMapper mapper = this.getWriteSqlSessionTemplate().getMapper(AuthRoleMapper.class);
        return mapper.updateByPrimaryKey(record);
    }

}
