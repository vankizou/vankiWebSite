package com.zoufanqi.service;

import com.zoufanqi.entity.AuthResource;
import com.zoufanqi.entity.AuthResourceExample;

import java.util.List;

/**
 * Created by vanki on 2017/5/9.
 */
public interface AuthResourceService {
    int add(AuthResource authResource);

    int updateById(AuthResource authResource);

    int deleteById(Long id);

    AuthResource getByUrl(String url, Integer origin);

	int countByCondition(AuthResourceExample example);

	List<AuthResource> selectByCondition(AuthResourceExample example);
}
