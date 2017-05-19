package com.zoufanqi.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.zoufanqi.entity.AuthRole;
import com.zoufanqi.entity.AuthRoleExample;
import com.zoufanqi.mapper.AuthRoleMapper;
import com.zoufanqi.service.AuthRoleService;

import java.util.List;

/**
 * Created by vanki on 2017/5/9.
 */
@Service("authRoleService")
public class AuthRoleServiceImpl implements AuthRoleService {
    @Autowired
    private AuthRoleMapper authRoleMapper;

    @Override
    public int add(AuthRole authRole) {
        if (authRole == null) return -1;
        authRole.setId(null);
        int status = this.authRoleMapper.insertSelective(authRole);
        return status;
    }

    @Override
    public int updateById(AuthRole authRole) {
        if (authRole == null || authRole.getId() == null) return -1;

        int status = this.authRoleMapper.updateByPrimaryKeySelective(authRole);

        return status;
    }

    @Override
    public int deleteById(Long id) {
        if (id == null) return 1;

        int status = this.authRoleMapper.deleteByPrimaryKey(id);

        return status;
    }

    @Override
    public List<AuthRole> getAllRoles() {
        return this.authRoleMapper.selectByExample(new AuthRoleExample());
    }

	@Override
	public List<AuthRole> getRolesByCondition(AuthRoleExample example) {
		if(example==null) return null;
		return this.authRoleMapper.selectByExample(example);
	}

	@Override
	public int countByCondition(AuthRoleExample example) {
		return this.authRoleMapper.countByExample(example);
	}
}
