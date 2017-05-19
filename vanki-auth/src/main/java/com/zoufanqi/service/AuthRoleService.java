package com.zoufanqi.service;

import com.zoufanqi.entity.AuthRole;
import com.zoufanqi.entity.AuthRoleExample;

import java.util.List;

/**
 * Created by vanki on 2017/5/9.
 */
public interface AuthRoleService {
    int add(AuthRole authRole);

    int updateById(AuthRole authRole);

    int deleteById(Long id);

    List<AuthRole> getAllRoles();
    
    List<AuthRole> getRolesByCondition(AuthRoleExample example);

	int countByCondition(AuthRoleExample example);
}
