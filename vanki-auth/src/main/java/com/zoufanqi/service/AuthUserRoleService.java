package com.zoufanqi.service;

import com.zoufanqi.entity.AuthUserRole;

import java.util.Set;

/**
 * Created by vanki on 2017/5/9.
 */
public interface AuthUserRoleService {
    int add(AuthUserRole authUserRole);

    int updateById(AuthUserRole authUserRole);

    int deleteById(Long id);

    boolean isRoleExists(Long userId, Long roleId);

    AuthUserRole getById(Long id);

    Set<Long> getRoleIdListByUserId(Long userId);
}
