package com.zoufanqi.mapper.impl;

import org.springframework.stereotype.Repository;
import com.zoufanqi.entity.AuthRoleOperation;
import com.zoufanqi.entity.AuthRoleOperationExample;
import com.zoufanqi.mapper.AuthRoleOperationMapper;

import java.util.List;

@Repository
public class AuthRoleOperationMapperImpl extends BaseMapper implements AuthRoleOperationMapper {

    @Override
    public int countByExample(AuthRoleOperationExample example) {
        AuthRoleOperationMapper mapper = this.getReadSqlSessionTemplate().getMapper(AuthRoleOperationMapper.class);
        return mapper.countByExample(example);
    }

    @Override
    public int deleteByExample(AuthRoleOperationExample example) {
        AuthRoleOperationMapper mapper = this.getWriteSqlSessionTemplate().getMapper(AuthRoleOperationMapper.class);
        return mapper.deleteByExample(example);
    }

    @Override
    public int deleteByPrimaryKey(Long id) {
        AuthRoleOperationMapper mapper = this.getWriteSqlSessionTemplate().getMapper(AuthRoleOperationMapper.class);
        return mapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(AuthRoleOperation record) {
        AuthRoleOperationMapper mapper = this.getWriteSqlSessionTemplate().getMapper(AuthRoleOperationMapper.class);
        return mapper.insert(record);
    }

    @Override
    public int insertSelective(AuthRoleOperation record) {
        AuthRoleOperationMapper mapper = this.getWriteSqlSessionTemplate().getMapper(AuthRoleOperationMapper.class);
        return mapper.insertSelective(record);
    }

    @Override
    public List<AuthRoleOperation> selectByExample(AuthRoleOperationExample example) {
        AuthRoleOperationMapper mapper = this.getReadSqlSessionTemplate().getMapper(AuthRoleOperationMapper.class);
        return mapper.selectByExample(example);
    }

    @Override
    public AuthRoleOperation selectByPrimaryKey(Long id) {
        AuthRoleOperationMapper mapper = this.getReadSqlSessionTemplate().getMapper(AuthRoleOperationMapper.class);
        return mapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByExampleSelective(AuthRoleOperation record, AuthRoleOperationExample example) {
        AuthRoleOperationMapper mapper = this.getWriteSqlSessionTemplate().getMapper(AuthRoleOperationMapper.class);
        return mapper.updateByExampleSelective(record, example);
    }

    @Override
    public int updateByExample(AuthRoleOperation record, AuthRoleOperationExample example) {
        AuthRoleOperationMapper mapper = this.getWriteSqlSessionTemplate().getMapper(AuthRoleOperationMapper.class);
        return mapper.updateByExample(record, example);
    }

    @Override
    public int updateByPrimaryKeySelective(AuthRoleOperation record) {
        AuthRoleOperationMapper mapper = this.getWriteSqlSessionTemplate().getMapper(AuthRoleOperationMapper.class);
        return mapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(AuthRoleOperation record) {
        AuthRoleOperationMapper mapper = this.getWriteSqlSessionTemplate().getMapper(AuthRoleOperationMapper.class);
        return mapper.updateByPrimaryKey(record);
    }

}
