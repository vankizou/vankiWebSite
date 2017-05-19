package com.zoufanqi.service;

import com.zoufanqi.entity.AuthResource;

import java.util.Map;
import java.util.Set;

/**
 * Created by vanki on 2017/5/10.
 */
public interface AuthService {
    boolean isAuthPass(Long userId, Integer resourceOrigin, boolean saveUrlToDB);

    boolean isAuthPass(Long userId, String url, Integer resourceOrigin, boolean saveUrlToDB);

    Set<Long> getRoleListByUserId(Long userId);

    AuthResource getAuthResource(String url, Integer resourceOrigin, boolean saveUrlToDB);

    Map<String, Object> getRoleOperationMap(Long roleId);
}
