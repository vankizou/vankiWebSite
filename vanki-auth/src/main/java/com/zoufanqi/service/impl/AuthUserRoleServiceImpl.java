package com.zoufanqi.service.impl;

import com.alibaba.fastjson.JSON;
import com.zoufanqi.consts.RedisKeyEnum;
import com.zoufanqi.entity.AuthUserRole;
import com.zoufanqi.entity.AuthUserRoleExample;
import com.zoufanqi.mapper.AuthUserRoleMapper;
import com.zoufanqi.service.AuthUserRoleService;
import com.zoufanqi.service.redis.RedisTemplate;
import com.zoufanqi.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by vanki on 2017/5/9.
 */
@Service("authUserRoleService")
public class AuthUserRoleServiceImpl implements AuthUserRoleService {
    @Autowired
    private AuthUserRoleMapper authUserRoleMapper;
    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public int add(AuthUserRole authUserRole) {
        if (authUserRole == null || authUserRole.getRoleId() == null || authUserRole.getUserId() == null) return -1;
        authUserRole.setId(null);

        if (isRoleExists(authUserRole.getUserId(), authUserRole.getRoleId())) return -2;

        int status = this.authUserRoleMapper.insertSelective(authUserRole);
        if (status > 0)
            this.redisTemplate.hdel(RedisKeyEnum.MAP_AUTH_USER_ROLE_IDS.name(), String.valueOf(authUserRole.getUserId()));
        return status;
    }

    @Override
    public int updateById(AuthUserRole authUserRole) {
        if (authUserRole == null || authUserRole.getId() == null) return -1;

        AuthUserRole old = this.getById(authUserRole.getId());
        if (old == null) return -2;

        int status = this.authUserRoleMapper.updateByPrimaryKeySelective(authUserRole);
        if (status > 0) {
            this.redisTemplate.hdel(RedisKeyEnum.MAP_AUTH_USER_ROLE_IDS.name(), String.valueOf(authUserRole.getUserId()));
            if (!old.getUserId().equals(authUserRole.getUserId()))
                this.redisTemplate.hdel(RedisKeyEnum.MAP_AUTH_USER_ROLE_IDS.name(), String.valueOf(old.getUserId()));
        }

        return status;
    }

    @Override
    public int deleteById(Long id) {
        if (id == null) return 1;

        AuthUserRole old = this.getById(id);
        if (old == null) return 1;

        int status = this.authUserRoleMapper.deleteByPrimaryKey(id);
        if (status > 0)
            this.redisTemplate.hdel(RedisKeyEnum.MAP_AUTH_USER_ROLE_IDS.name(), String.valueOf(old.getUserId()));

        return status;
    }

    @Override
    public boolean isRoleExists(Long userId, Long roleId) {
        if (userId == null || roleId == null) return false;

        AuthUserRoleExample example = new AuthUserRoleExample();
        example.createCriteria().andUserIdEqualTo(userId).andRoleIdEqualTo(roleId);

        return this.authUserRoleMapper.countByExample(example) > 0;
    }

    @Override
    public AuthUserRole getById(Long id) {
        if (id == null) return null;
        return this.authUserRoleMapper.selectByPrimaryKey(id);
    }

    @Override
    public Set<Long> getRoleIdListByUserId(Long userId) {
        Set<Long> roleSet = new HashSet<>();
        if (userId == null) return roleSet;

        String redisInfo = this.redisTemplate.hget(RedisKeyEnum.MAP_AUTH_USER_ROLE_IDS.name(), String.valueOf(userId));
        if (StringUtil.isEmpty(redisInfo)) {
            boolean exists = this.redisTemplate.hexists(RedisKeyEnum.MAP_AUTH_USER_ROLE_IDS.name(), String.valueOf(userId));
            if (exists) return roleSet;
        } else {
            return new HashSet<>(JSON.parseArray(redisInfo, Long.class));
        }

        AuthUserRoleExample example = new AuthUserRoleExample();
        example.createCriteria().andUserIdEqualTo(userId);

        List<AuthUserRole> list = this.authUserRoleMapper.selectByExample(example);
        if (list != null && !list.isEmpty())
            for (AuthUserRole authUserRole : list)
                roleSet.add(authUserRole.getRoleId());

        this.redisTemplate.hset(RedisKeyEnum.MAP_AUTH_USER_ROLE_IDS.name(), String.valueOf(userId), JSON.toJSONString(roleSet));

        return roleSet;
    }
}
