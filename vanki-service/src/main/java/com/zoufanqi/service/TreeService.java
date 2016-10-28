package com.zoufanqi.service;

import com.zoufanqi.entity.Tree;
import com.zoufanqi.exception.ZouFanqiException;
import com.zoufanqi.result.ResultJson;

import java.util.List;

public interface TreeService {

    ResultJson add(Long parentId, Long userId, Integer type, Long targetId, String title,
                   Boolean isPublic, String description) throws ZouFanqiException;

    ResultJson updateById(Long loginUserId, Long id, Long parentId, String title, Boolean isPublic,
                          String description) throws ZouFanqiException;

    int autoUpdateById(Long id, Boolean isOpen, Integer countTarget) throws ZouFanqiException;

    ResultJson deleteById(Long loginUserId, Long id) throws ZouFanqiException;

    Tree getById(Long id, boolean isLogin) throws ZouFanqiException;

    List<Tree> getListByParentId(Long userId, Long parentId, boolean isLogin) throws ZouFanqiException;
}