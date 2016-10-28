package com.zoufanqi.mapper;

import com.zoufanqi.entity.MyFile;
import com.zoufanqi.entity.MyFileExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface MyFileMapper {
    int countByExample(MyFileExample example);

    int deleteByExample(MyFileExample example);

    int deleteByPrimaryKey(Long id);

    int insert(MyFile record);

    int insertSelective(MyFile record);

    List<MyFile> selectByExample(MyFileExample example);

    MyFile selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") MyFile record, @Param("example") MyFileExample example);

    int updateByExample(@Param("record") MyFile record, @Param("example") MyFileExample example);

    int updateByPrimaryKeySelective(MyFile record);

    int updateByPrimaryKey(MyFile record);
}