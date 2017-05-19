package com.zoufanqi.mapper.impl;

import com.zoufanqi.entity.User;
import com.zoufanqi.entity.UserExample;
import com.zoufanqi.mapper.UserMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserMapperImpl extends BaseMapper implements UserMapper {

    @Override
    public int countByExample(UserExample example) {
        UserMapper mapper = this.getReadSqlSessionTemplate().getMapper(UserMapper.class);
        return mapper.countByExample(example);
    }

    @Override
    public int deleteByExample(UserExample example) {
        UserMapper mapper = this.getWriteSqlSessionTemplate().getMapper(UserMapper.class);
        return mapper.deleteByExample(example);
    }

    @Override
    public int deleteByPrimaryKey(Long id) {
        UserMapper mapper = this.getWriteSqlSessionTemplate().getMapper(UserMapper.class);
        return mapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(User record) {
        UserMapper mapper = this.getWriteSqlSessionTemplate().getMapper(UserMapper.class);
        return mapper.insert(record);
    }

    @Override
    public int insertSelective(User record) {
        UserMapper mapper = this.getWriteSqlSessionTemplate().getMapper(UserMapper.class);
        return mapper.insertSelective(record);
    }

    @Override
    public List<User> selectByExample(UserExample example) {
        UserMapper mapper = this.getReadSqlSessionTemplate().getMapper(UserMapper.class);
        return mapper.selectByExample(example);
    }

    @Override
    public User selectByPrimaryKey(Long id) {
        UserMapper mapper = this.getReadSqlSessionTemplate().getMapper(UserMapper.class);
        return mapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByExampleSelective(User record, UserExample example) {
        UserMapper mapper = this.getWriteSqlSessionTemplate().getMapper(UserMapper.class);
        return mapper.updateByExampleSelective(record, example);
    }

    @Override
    public int updateByExample(User record, UserExample example) {
        UserMapper mapper = this.getWriteSqlSessionTemplate().getMapper(UserMapper.class);
        return mapper.updateByExample(record, example);
    }

    @Override
    public int updateByPrimaryKeySelective(User record) {
        UserMapper mapper = this.getWriteSqlSessionTemplate().getMapper(UserMapper.class);
        return mapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(User record) {
        UserMapper mapper = this.getWriteSqlSessionTemplate().getMapper(UserMapper.class);
        return mapper.updateByPrimaryKey(record);
    }

}
