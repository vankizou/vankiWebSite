package com.zoufanqi.service.impl;

import com.alibaba.fastjson.JSON;
import com.zoufanqi.consts.RedisKeyEnum;
import com.zoufanqi.entity.AuthResource;
import com.zoufanqi.entity.AuthResourceExample;
import com.zoufanqi.mapper.AuthResourceMapper;
import com.zoufanqi.service.AuthResourceService;
import com.zoufanqi.service.redis.RedisTemplate;
import com.zoufanqi.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by vanki on 2017/5/9.
 */
@Service("authResourceService")
public class AuthResourceServiceImpl implements AuthResourceService {
    @Autowired
    private AuthResourceMapper authResourceMapper;
    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public int add(AuthResource authResource) {
        if (authResource == null || authResource.getUrl() == null || authResource.getOrigin() == null) return -1;
        authResource.setId(null);

        AuthResource old = this.getByUrl(authResource.getUrl(), authResource.getOrigin());
        if (old != null) return -2;

        if (!authResource.getUrl().startsWith("/")) authResource.setUrl("/" + authResource.getUrl());

        int status = this.authResourceMapper.insertSelective(authResource);
        if (status > 0)
            this.redisTemplate.hdel(RedisKeyEnum.MAP_AUTH_RESOURCE.name(), old.getUrl(), old.getUrl() + old.getOrigin());

        return status;
    }

    @Override
    public int updateById(AuthResource authResource) {
        if (authResource == null || authResource.getId() == null) return -1;

        int status = this.authResourceMapper.updateByPrimaryKeySelective(authResource);
        if (status > 0) this.redisTemplate.hdel(RedisKeyEnum.MAP_AUTH_RESOURCE.name(),
                authResource.getUrl(), authResource.getUrl() + authResource.getOrigin());

        return status;
    }

    @Override
    public int deleteById(Long id) {
        if (id == null) return 1;
        AuthResource old = this.authResourceMapper.selectByPrimaryKey(id);
        if (old == null) return 1;

        int status = this.authResourceMapper.deleteByPrimaryKey(id);
        if (status > 0)
            this.redisTemplate.hdel(RedisKeyEnum.MAP_AUTH_RESOURCE.name(), old.getUrl(), old.getUrl() + old.getOrigin());
        return status;
    }

    @Override
    public AuthResource getByUrl(String url, Integer origin) {
        if (url == null) return null;
        if (!url.startsWith("/")) url = "/" + url;

        String redisKey = url;
        if (origin != null) redisKey += origin;

        String redisInfo = this.redisTemplate.hget(RedisKeyEnum.MAP_AUTH_RESOURCE.name(), redisKey);
        if (StringUtil.isEmpty(redisInfo)) {
            boolean exists = this.redisTemplate.hexists(RedisKeyEnum.MAP_AUTH_RESOURCE.name(), redisKey);
            if (exists) return null;
        } else {
            return JSON.parseObject(redisInfo, AuthResource.class);
        }

        AuthResourceExample example = new AuthResourceExample();
        example.createCriteria().andUrlEqualTo(url).andOriginEqualTo(origin);

        List<AuthResource> list = this.authResourceMapper.selectByExample(example);

        if (list == null || list.isEmpty()) {
            this.redisTemplate.hset(RedisKeyEnum.MAP_AUTH_RESOURCE.name(), redisKey, null);
            return null;
        }
        this.redisTemplate.hset(RedisKeyEnum.MAP_AUTH_RESOURCE.name(), redisKey, JSON.toJSONString(list.get(0)));
        return list.get(0);
    }

    @Override
    public int countByCondition(AuthResourceExample example) {
        return this.authResourceMapper.countByExample(example);
    }

    @Override
    public List<AuthResource> selectByCondition(AuthResourceExample example) {
        return this.authResourceMapper.selectByExample(example);
    }


}
