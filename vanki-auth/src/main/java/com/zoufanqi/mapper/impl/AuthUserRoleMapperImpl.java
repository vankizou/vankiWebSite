package com.zoufanqi.mapper.impl;

import org.springframework.stereotype.Repository;
import com.zoufanqi.entity.AuthUserRole;
import com.zoufanqi.entity.AuthUserRoleExample;
import com.zoufanqi.mapper.AuthUserRoleMapper;

import java.util.List;

@Repository
public class AuthUserRoleMapperImpl extends BaseMapper implements AuthUserRoleMapper {

    @Override
    public int countByExample(AuthUserRoleExample example) {
        AuthUserRoleMapper mapper = this.getReadSqlSessionTemplate().getMapper(AuthUserRoleMapper.class);
        return mapper.countByExample(example);
    }

    @Override
    public int deleteByExample(AuthUserRoleExample example) {
        AuthUserRoleMapper mapper = this.getWriteSqlSessionTemplate().getMapper(AuthUserRoleMapper.class);
        return mapper.deleteByExample(example);
    }

    @Override
    public int deleteByPrimaryKey(Long autoId) {
        AuthUserRoleMapper mapper = this.getWriteSqlSessionTemplate().getMapper(AuthUserRoleMapper.class);
        return mapper.deleteByPrimaryKey(autoId);
    }

    @Override
    public int insert(AuthUserRole record) {
        AuthUserRoleMapper mapper = this.getWriteSqlSessionTemplate().getMapper(AuthUserRoleMapper.class);
        return mapper.insert(record);
    }

    @Override
    public int insertSelective(AuthUserRole record) {
        AuthUserRoleMapper mapper = this.getWriteSqlSessionTemplate().getMapper(AuthUserRoleMapper.class);
        return mapper.insertSelective(record);
    }

    @Override
    public List<AuthUserRole> selectByExample(AuthUserRoleExample example) {
        AuthUserRoleMapper mapper = this.getReadSqlSessionTemplate().getMapper(AuthUserRoleMapper.class);
        return mapper.selectByExample(example);
    }

    @Override
    public AuthUserRole selectByPrimaryKey(Long autoId) {
        AuthUserRoleMapper mapper = this.getReadSqlSessionTemplate().getMapper(AuthUserRoleMapper.class);
        return mapper.selectByPrimaryKey(autoId);
    }

    @Override
    public int updateByExampleSelective(AuthUserRole record, AuthUserRoleExample example) {
        AuthUserRoleMapper mapper = this.getWriteSqlSessionTemplate().getMapper(AuthUserRoleMapper.class);
        return mapper.updateByExampleSelective(record, example);
    }

    @Override
    public int updateByExample(AuthUserRole record, AuthUserRoleExample example) {
        AuthUserRoleMapper mapper = this.getWriteSqlSessionTemplate().getMapper(AuthUserRoleMapper.class);
        return mapper.updateByExample(record, example);
    }

    @Override
    public int updateByPrimaryKeySelective(AuthUserRole record) {
        AuthUserRoleMapper mapper = this.getWriteSqlSessionTemplate().getMapper(AuthUserRoleMapper.class);
        return mapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(AuthUserRole record) {
        AuthUserRoleMapper mapper = this.getWriteSqlSessionTemplate().getMapper(AuthUserRoleMapper.class);
        return mapper.updateByPrimaryKey(record);
    }

}
