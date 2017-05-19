package com.zoufanqi.mapper;

import com.zoufanqi.entity.NoteDetail;
import com.zoufanqi.entity.NoteDetailExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface NoteDetailMapper {
    int countByExample(NoteDetailExample example);

    int deleteByExample(NoteDetailExample example);

    int deleteByPrimaryKey(Long id);

    int insert(NoteDetail record);

    int insertSelective(NoteDetail record);

    List<NoteDetail> selectByExampleWithBLOBs(NoteDetailExample example);

    List<NoteDetail> selectByExample(NoteDetailExample example);

    NoteDetail selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") NoteDetail record, @Param("example") NoteDetailExample example);

    int updateByExampleWithBLOBs(@Param("record") NoteDetail record, @Param("example") NoteDetailExample example);

    int updateByExample(@Param("record") NoteDetail record, @Param("example") NoteDetailExample example);

    int updateByPrimaryKeySelective(NoteDetail record);

    int updateByPrimaryKeyWithBLOBs(NoteDetail record);

    int updateByPrimaryKey(NoteDetail record);
}