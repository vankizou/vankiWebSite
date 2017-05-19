package com.zoufanqi.service;

import com.zoufanqi.entity.AuthRoleOperation;

import java.util.List;

/**
 * Created by vanki on 2017/5/9.
 */
public interface AuthRoleOperationService {
    int add(AuthRoleOperation operation);

    int updateById(AuthRoleOperation operation);

    int deleteById(Long id);

    List<AuthRoleOperation> getListByRoleId(Long roleId);
}
