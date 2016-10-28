package com.zoufanqi.service.impl;

import com.zoufanqi.entity.MyFile;
import com.zoufanqi.exception.ZouFanqiException;
import com.zoufanqi.mapper.MyFileMapper;
import com.zoufanqi.service.MyFileService;
import com.zoufanqi.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("myFileService")
public class MyFileServiceImpl implements MyFileService {
    @Autowired
    private MyFileMapper myFileMapper;

    @Override
    public MyFile getById(Long id) throws ZouFanqiException {
        if (StringUtil.isNotAutoId(id)) return null;
        return this.myFileMapper.selectByPrimaryKey(id);
    }
}