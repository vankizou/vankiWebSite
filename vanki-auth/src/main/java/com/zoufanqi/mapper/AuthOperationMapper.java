package com.zoufanqi.mapper;

import com.zoufanqi.entity.AuthOperation;
import com.zoufanqi.entity.AuthOperationExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface AuthOperationMapper {
    int countByExample(AuthOperationExample example);

    int deleteByExample(AuthOperationExample example);

    int deleteByPrimaryKey(Long id);

    int insert(AuthOperation record);

    int insertSelective(AuthOperation record);

    List<AuthOperation> selectByExample(AuthOperationExample example);

    AuthOperation selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") AuthOperation record, @Param("example") AuthOperationExample example);

    int updateByExample(@Param("record") AuthOperation record, @Param("example") AuthOperationExample example);

    int updateByPrimaryKeySelective(AuthOperation record);

    int updateByPrimaryKey(AuthOperation record);
}