package com.zoufanqi.service;

import org.ehcache.Cache;

import java.util.Map;

/**
 * Created by vanki on 2017/5/9.
 */
public interface CacheService {
    Cache<String, Integer> getAuthResourceCache();

    Cache<Long, Map> getAuthRoleOperationCache();

    void clearAuthResourceCache();

    void clearAuthRoleOperationCache();
}
