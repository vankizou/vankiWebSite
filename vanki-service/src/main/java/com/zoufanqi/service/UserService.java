package com.zoufanqi.service;

import com.zoufanqi.entity.User;
import com.zoufanqi.exception.ZouFanqiException;
import com.zoufanqi.result.ResultJson;

public interface UserService {
    ResultJson add(String phone, String password, Long avatarId,
                   Integer sex, String nickname, Long cityId, String description, String registerIp) throws ZouFanqiException;

    ResultJson updateById(Long loginUserId, Long id, String phone, String password, Long avatarId,
                          Integer sex, String nickname, Long cityId, String description) throws ZouFanqiException;

    int autoUpdateLoginDataById(Long id, String loginIp) throws ZouFanqiException;

    ResultJson deleteByPhone(String phone) throws ZouFanqiException;

    User getById(Long id) throws ZouFanqiException;

    User getByPhone(String phone) throws ZouFanqiException;
}