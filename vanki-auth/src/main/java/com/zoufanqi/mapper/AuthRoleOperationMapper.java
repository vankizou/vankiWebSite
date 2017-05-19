package com.zoufanqi.mapper;

import org.apache.ibatis.annotations.Param;
import com.zoufanqi.entity.AuthRoleOperation;
import com.zoufanqi.entity.AuthRoleOperationExample;

import java.util.List;

public interface AuthRoleOperationMapper {
    int countByExample(AuthRoleOperationExample example);

    int deleteByExample(AuthRoleOperationExample example);

    int deleteByPrimaryKey(Long id);

    int insert(AuthRoleOperation record);

    int insertSelective(AuthRoleOperation record);

    List<AuthRoleOperation> selectByExample(AuthRoleOperationExample example);

    AuthRoleOperation selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") AuthRoleOperation record, @Param("example") AuthRoleOperationExample example);

    int updateByExample(@Param("record") AuthRoleOperation record, @Param("example") AuthRoleOperationExample example);

    int updateByPrimaryKeySelective(AuthRoleOperation record);

    int updateByPrimaryKey(AuthRoleOperation record);
}