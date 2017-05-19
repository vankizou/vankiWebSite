package com.zoufanqi.mapper;

import com.zoufanqi.entity.Unite;
import com.zoufanqi.entity.UniteExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UniteMapper {
    int countByExample(UniteExample example);

    int deleteByExample(UniteExample example);

    int deleteByPrimaryKey(Long id);

    int insert(Unite record);

    int insertSelective(Unite record);

    List<Unite> selectByExample(UniteExample example);

    Unite selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") Unite record, @Param("example") UniteExample example);

    int updateByExample(@Param("record") Unite record, @Param("example") UniteExample example);

    int updateByPrimaryKeySelective(Unite record);

    int updateByPrimaryKey(Unite record);
}