package com.zoufanqi.mapper.impl;

import com.zoufanqi.entity.Picture;
import com.zoufanqi.entity.PictureExample;
import com.zoufanqi.mapper.PictureMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PictureMapperImpl extends BaseMapper implements PictureMapper {

    @Override
    public int countByExample(PictureExample example) {
        PictureMapper mapper = this.getReadSqlSessionTemplate().getMapper(PictureMapper.class);
        return mapper.countByExample(example);
    }

    @Override
    public int deleteByExample(PictureExample example) {
        PictureMapper mapper = this.getWriteSqlSessionTemplate().getMapper(PictureMapper.class);
        return mapper.deleteByExample(example);
    }

    @Override
    public int deleteByPrimaryKey(Long id) {
        PictureMapper mapper = this.getWriteSqlSessionTemplate().getMapper(PictureMapper.class);
        return mapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(Picture record) {
        PictureMapper mapper = this.getWriteSqlSessionTemplate().getMapper(PictureMapper.class);
        return mapper.insert(record);
    }

    @Override
    public int insertSelective(Picture record) {
        PictureMapper mapper = this.getWriteSqlSessionTemplate().getMapper(PictureMapper.class);
        return mapper.insertSelective(record);
    }

    @Override
    public List<Picture> selectByExample(PictureExample example) {
        PictureMapper mapper = this.getReadSqlSessionTemplate().getMapper(PictureMapper.class);
        return mapper.selectByExample(example);
    }

    @Override
    public Picture selectByPrimaryKey(Long id) {
        PictureMapper mapper = this.getReadSqlSessionTemplate().getMapper(PictureMapper.class);
        return mapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByExampleSelective(Picture record, PictureExample example) {
        PictureMapper mapper = this.getWriteSqlSessionTemplate().getMapper(PictureMapper.class);
        return mapper.updateByExampleSelective(record, example);
    }

    @Override
    public int updateByExample(Picture record, PictureExample example) {
        PictureMapper mapper = this.getWriteSqlSessionTemplate().getMapper(PictureMapper.class);
        return mapper.updateByExample(record, example);
    }

    @Override
    public int updateByPrimaryKeySelective(Picture record) {
        PictureMapper mapper = this.getWriteSqlSessionTemplate().getMapper(PictureMapper.class);
        return mapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(Picture record) {
        PictureMapper mapper = this.getWriteSqlSessionTemplate().getMapper(PictureMapper.class);
        return mapper.updateByPrimaryKey(record);
    }

}
