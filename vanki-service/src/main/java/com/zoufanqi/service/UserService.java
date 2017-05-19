package com.zoufanqi.service;

import com.zoufanqi.entity.User;
import com.zoufanqi.exception.ZouFanqiException;
import com.zoufanqi.result.ResultJson;

/**
 * Created by vanki on 2017/4/10.
 */
public interface UserService {
    ResultJson register(User user) throws ZouFanqiException;

    ResultJson updateById(Long loginUserId, User user) throws ZouFanqiException;

    ResultJson deleteById(Long id) throws ZouFanqiException;

    User getById(Long id) throws ZouFanqiException;

    User getByName(String name) throws ZouFanqiException;
}
