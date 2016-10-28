package com.zoufanqi.mapper;

import com.zoufanqi.entity.Tree;
import com.zoufanqi.entity.TreeExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TreeMapper {
    int countByExample(TreeExample example);

    int deleteByExample(TreeExample example);

    int deleteByPrimaryKey(Long id);

    int insert(Tree record);

    int insertSelective(Tree record);

    List<Tree> selectByExample(TreeExample example);

    Tree selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") Tree record, @Param("example") TreeExample example);

    int updateByExample(@Param("record") Tree record, @Param("example") TreeExample example);

    int updateByPrimaryKeySelective(Tree record);

    int updateByPrimaryKey(Tree record);
}