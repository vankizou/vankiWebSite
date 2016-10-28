package com.zoufanqi.service;

import com.zoufanqi.entity.MyFile;
import com.zoufanqi.entity.Variable;
import com.zoufanqi.exception.ZouFanqiException;
import com.zoufanqi.result.ResultJson;

public interface MyFileService {
    MyFile getById(Long id) throws ZouFanqiException;
}