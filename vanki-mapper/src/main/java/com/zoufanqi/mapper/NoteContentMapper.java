package com.zoufanqi.mapper;

import com.zoufanqi.entity.NoteContent;
import com.zoufanqi.entity.NoteContentExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface NoteContentMapper {
    int countByExample(NoteContentExample example);

    int deleteByExample(NoteContentExample example);

    int deleteByPrimaryKey(Long id);

    int insert(NoteContent record);

    int insertSelective(NoteContent record);

    List<NoteContent> selectByExampleWithBLOBs(NoteContentExample example);

    List<NoteContent> selectByExample(NoteContentExample example);

    NoteContent selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") NoteContent record, @Param("example") NoteContentExample example);

    int updateByExampleWithBLOBs(@Param("record") NoteContent record, @Param("example") NoteContentExample example);

    int updateByExample(@Param("record") NoteContent record, @Param("example") NoteContentExample example);

    int updateByPrimaryKeySelective(NoteContent record);

    int updateByPrimaryKeyWithBLOBs(NoteContent record);

    int updateByPrimaryKey(NoteContent record);
}