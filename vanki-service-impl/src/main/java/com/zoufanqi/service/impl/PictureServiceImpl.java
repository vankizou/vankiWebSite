package com.zoufanqi.service.impl;

import com.zoufanqi.entity.Picture;
import com.zoufanqi.exception.ZouFanqiException;
import com.zoufanqi.mapper.PictureMapper;
import com.zoufanqi.service.PictureService;
import com.zoufanqi.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("pictureService")
public class PictureServiceImpl implements PictureService {
    @Autowired
    private PictureMapper PictureMapper;

    @Override
    public Picture getById(Long id) throws ZouFanqiException {
        if (StringUtil.isNotId(id)) return null;
        return this.PictureMapper.selectByPrimaryKey(id);
    }
}