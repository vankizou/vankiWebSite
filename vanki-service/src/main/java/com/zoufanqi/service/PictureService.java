package com.zoufanqi.service;

import com.zoufanqi.entity.Picture;
import com.zoufanqi.exception.ZouFanqiException;
import com.zoufanqi.param.common.Page;

public interface PictureService {
    int add(Picture pic);

    Picture getById(Long id) throws ZouFanqiException;

    Picture getByUUID(String uuid) throws ZouFanqiException;

    Page<Picture> getPage(Long loginUserId, Integer useType, Integer pageNo, Integer pageSize, Integer navNum) throws ZouFanqiException;
}