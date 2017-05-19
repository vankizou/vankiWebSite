package com.zoufanqi.mapper;

import com.zoufanqi.entity.UserLoginRecord;
import com.zoufanqi.entity.UserLoginRecordExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UserLoginRecordMapper {
    int countByExample(UserLoginRecordExample example);

    int deleteByExample(UserLoginRecordExample example);

    int deleteByPrimaryKey(Long id);

    int insert(UserLoginRecord record);

    int insertSelective(UserLoginRecord record);

    List<UserLoginRecord> selectByExample(UserLoginRecordExample example);

    UserLoginRecord selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") UserLoginRecord record, @Param("example") UserLoginRecordExample example);

    int updateByExample(@Param("record") UserLoginRecord record, @Param("example") UserLoginRecordExample example);

    int updateByPrimaryKeySelective(UserLoginRecord record);

    int updateByPrimaryKey(UserLoginRecord record);
}