package com.zoufanqi.mapper.impl;

import com.zoufanqi.entity.UserLoginRecord;
import com.zoufanqi.entity.UserLoginRecordExample;
import com.zoufanqi.mapper.UserLoginRecordMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserLoginRecordMapperImpl extends BaseMapper implements UserLoginRecordMapper {

    @Override
    public int countByExample(UserLoginRecordExample example) {
        UserLoginRecordMapper mapper = this.getReadSqlSessionTemplate().getMapper(UserLoginRecordMapper.class);
        return mapper.countByExample(example);
    }

    @Override
    public int deleteByExample(UserLoginRecordExample example) {
        UserLoginRecordMapper mapper = this.getWriteSqlSessionTemplate().getMapper(UserLoginRecordMapper.class);
        return mapper.deleteByExample(example);
    }

    @Override
    public int deleteByPrimaryKey(Long id) {
        UserLoginRecordMapper mapper = this.getWriteSqlSessionTemplate().getMapper(UserLoginRecordMapper.class);
        return mapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(UserLoginRecord record) {
        UserLoginRecordMapper mapper = this.getWriteSqlSessionTemplate().getMapper(UserLoginRecordMapper.class);
        return mapper.insert(record);
    }

    @Override
    public int insertSelective(UserLoginRecord record) {
        UserLoginRecordMapper mapper = this.getWriteSqlSessionTemplate().getMapper(UserLoginRecordMapper.class);
        return mapper.insertSelective(record);
    }

    @Override
    public List<UserLoginRecord> selectByExample(UserLoginRecordExample example) {
        UserLoginRecordMapper mapper = this.getReadSqlSessionTemplate().getMapper(UserLoginRecordMapper.class);
        return mapper.selectByExample(example);
    }

    @Override
    public UserLoginRecord selectByPrimaryKey(Long id) {
        UserLoginRecordMapper mapper = this.getReadSqlSessionTemplate().getMapper(UserLoginRecordMapper.class);
        return mapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByExampleSelective(UserLoginRecord record, UserLoginRecordExample example) {
        UserLoginRecordMapper mapper = this.getWriteSqlSessionTemplate().getMapper(UserLoginRecordMapper.class);
        return mapper.updateByExampleSelective(record, example);
    }

    @Override
    public int updateByExample(UserLoginRecord record, UserLoginRecordExample example) {
        UserLoginRecordMapper mapper = this.getWriteSqlSessionTemplate().getMapper(UserLoginRecordMapper.class);
        return mapper.updateByExample(record, example);
    }

    @Override
    public int updateByPrimaryKeySelective(UserLoginRecord record) {
        UserLoginRecordMapper mapper = this.getWriteSqlSessionTemplate().getMapper(UserLoginRecordMapper.class);
        return mapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(UserLoginRecord record) {
        UserLoginRecordMapper mapper = this.getWriteSqlSessionTemplate().getMapper(UserLoginRecordMapper.class);
        return mapper.updateByPrimaryKey(record);
    }

}
