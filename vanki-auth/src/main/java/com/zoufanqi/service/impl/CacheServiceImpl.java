package com.zoufanqi.service.impl;

import org.ehcache.Cache;
import org.ehcache.CacheManager;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;
import com.zoufanqi.service.CacheService;

import java.util.Map;

/**
 * Created by vanki on 2017/5/9.
 */
//@Service("cacheService")
public class CacheServiceImpl implements CacheService, InitializingBean, DisposableBean {
    private CacheManager cacheManager;

    private Cache<String, Integer> authResourceCache;
    private Cache<Long, Map> authRoleOperationCache;

    @Override
    public Cache<String, Integer> getAuthResourceCache() {
        return authResourceCache;
    }

    @Override
    public Cache<Long, Map> getAuthRoleOperationCache() {
        return authRoleOperationCache;
    }

    @Override
    public void clearAuthResourceCache() {
        this.authResourceCache.clear();
    }

    @Override
    public void clearAuthRoleOperationCache() {
        this.authRoleOperationCache.clear();
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        this.cacheManager = CacheManagerBuilder.newCacheManagerBuilder().build();
        this.cacheManager.init();

        final String authResourceCacheAlias = "authResourceCache";
        this.cacheManager.createCache(authResourceCacheAlias, CacheConfigurationBuilder.
                newCacheConfigurationBuilder(String.class, Integer.class, ResourcePoolsBuilder.heap(3000)));
        this.authResourceCache = this.cacheManager.getCache(authResourceCacheAlias, String.class, Integer.class);

        final String authRoleOperationCacheAlias = "authRoleOperationCache";
        this.cacheManager.createCache(authRoleOperationCacheAlias, CacheConfigurationBuilder.
                newCacheConfigurationBuilder(Long.class, Map.class, ResourcePoolsBuilder.heap(100)));
        this.authRoleOperationCache = this.cacheManager.getCache(authRoleOperationCacheAlias, Long.class, Map.class);

//        final String authUserRoleCacheAlias = "authUserRoleCache";
//        this.cacheManager.createCache(authUserRoleCacheAlias, CacheConfigurationBuilder)
    }

    @Override
    public void destroy() throws Exception {
        if (this.cacheManager != null) this.cacheManager.close();
    }
}
