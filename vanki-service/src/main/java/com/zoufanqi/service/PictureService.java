package com.zoufanqi.service;

import com.zoufanqi.entity.Picture;
import com.zoufanqi.exception.ZouFanqiException;

public interface PictureService {
    Picture getById(Long id) throws ZouFanqiException;
}