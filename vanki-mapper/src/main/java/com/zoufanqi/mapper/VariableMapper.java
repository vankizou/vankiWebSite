package com.zoufanqi.mapper;

import com.zoufanqi.entity.Variable;
import com.zoufanqi.entity.VariableExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface VariableMapper {
    int countByExample(VariableExample example);

    int deleteByExample(VariableExample example);

    int deleteByPrimaryKey(Long id);

    int insert(Variable record);

    int insertSelective(Variable record);

    List<Variable> selectByExample(VariableExample example);

    Variable selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") Variable record, @Param("example") VariableExample example);

    int updateByExample(@Param("record") Variable record, @Param("example") VariableExample example);

    int updateByPrimaryKeySelective(Variable record);

    int updateByPrimaryKey(Variable record);
}