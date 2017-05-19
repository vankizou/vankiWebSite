package com.zoufanqi.mapper;

import com.zoufanqi.entity.EnvVar;
import com.zoufanqi.entity.EnvVarExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface EnvVarMapper {
    int countByExample(EnvVarExample example);

    int deleteByExample(EnvVarExample example);

    int deleteByPrimaryKey(Long id);

    int insert(EnvVar record);

    int insertSelective(EnvVar record);

    List<EnvVar> selectByExample(EnvVarExample example);

    EnvVar selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") EnvVar record, @Param("example") EnvVarExample example);

    int updateByExample(@Param("record") EnvVar record, @Param("example") EnvVarExample example);

    int updateByPrimaryKeySelective(EnvVar record);

    int updateByPrimaryKey(EnvVar record);
}