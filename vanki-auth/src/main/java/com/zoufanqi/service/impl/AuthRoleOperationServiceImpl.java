package com.zoufanqi.service.impl;

import com.alibaba.fastjson.JSON;
import com.zoufanqi.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.zoufanqi.consts.RedisKeyEnum;
import com.zoufanqi.entity.AuthRoleOperation;
import com.zoufanqi.entity.AuthRoleOperationExample;
import com.zoufanqi.mapper.AuthRoleOperationMapper;
import com.zoufanqi.service.AuthRoleOperationService;
import com.zoufanqi.service.redis.RedisTemplate;

import java.util.List;

/**
 * Created by vanki on 2017/5/9.
 */
@Service("authRoleOperationService")
public class AuthRoleOperationServiceImpl implements AuthRoleOperationService {
    @Autowired
    private AuthRoleOperationMapper authRoleOperationMapper;
    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public int add(AuthRoleOperation operation) {
        if (operation == null || operation.getRoleId() == null ||
                operation.getContent() == null || operation.getOperation() == null ||
                operation.getType() == null) return -1;

        operation.setId(null);
        int status = this.authRoleOperationMapper.insertSelective(operation);

        return status;
    }

    @Override
    public int updateById(AuthRoleOperation operation) {
        if (operation == null || operation.getId() == null) return -1;

        int status = this.authRoleOperationMapper.updateByPrimaryKeySelective(operation);

        return status;
    }

    @Override
    public int deleteById(Long id) {
        if (id == null) return 1;

        int status = this.authRoleOperationMapper.deleteByPrimaryKey(id);

        return status;
    }

    @Override
    public List<AuthRoleOperation> getListByRoleId(Long roleId) {
        if (roleId == null) return null;

        String redisInfo = this.redisTemplate.hget(RedisKeyEnum.MAP_AUTH_ROLE_OPERATION_LIST_FOR_ROLE_ID.name(), String.valueOf(roleId));
        if (StringUtil.isEmpty(redisInfo)) {
            boolean exists = this.redisTemplate.hexists(RedisKeyEnum.MAP_AUTH_ROLE_OPERATION_LIST_FOR_ROLE_ID.name(), String.valueOf(roleId));
            if (exists) return null;
        } else {
            return JSON.parseArray(redisInfo, AuthRoleOperation.class);
        }

        AuthRoleOperationExample example = new AuthRoleOperationExample();
        example.createCriteria().andRoleIdEqualTo(roleId);

        List<AuthRoleOperation> list = this.authRoleOperationMapper.selectByExample(example);
        this.redisTemplate.hset(RedisKeyEnum.MAP_AUTH_ROLE_OPERATION_LIST_FOR_ROLE_ID.name(), String.valueOf(roleId), JSON.toJSONString(list));
        return list;
    }
}
