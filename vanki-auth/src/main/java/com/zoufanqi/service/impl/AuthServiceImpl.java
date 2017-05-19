package com.zoufanqi.service.impl;

import com.zoufanqi.entity.AuthResource;
import com.zoufanqi.entity.AuthRoleOperation;
import com.zoufanqi.service.*;
import com.zoufanqi.utils.AuthRoleOperationUtil;
import com.zoufanqi.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by vanki on 2017/5/10.
 */
@Service("authService")
public class AuthServiceImpl implements AuthService {
//    @Autowired
    private CacheService cacheService;
    @Autowired
    private AuthResourceService authResourceService;
    @Autowired
    private AuthUserRoleService authUserRoleService;
    @Autowired
    private AuthRoleOperationService authRoleOperationService;
    @Autowired
    private HttpServletRequest request;

    /**
     * @param userId
     * @param resourceOrigin 资源所属
     * @param saveUrlToDB    如果该url不存在，是否保存
     *
     * @return
     */
    @Override
    public boolean isAuthPass(Long userId, Integer resourceOrigin, boolean saveUrlToDB) {
        // 是否为有效路径
        boolean isUrl = (Boolean) request.getAttribute(HandlerMapping.INTROSPECT_TYPE_LEVEL_MAPPING);
        if (!isUrl) return false;
        // 原路径，比如一些restful的动态路径的原路径
        String srcUrl = (String) request.getAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE);

        return isAuthPass(userId, srcUrl, resourceOrigin, saveUrlToDB);
    }

    /**
     * @param userId
     * @param url
     * @param resourceOrigin 资源所属
     * @param saveUrlToDB    如果该url不存在，是否保存
     *
     * @return
     */
    @Override
    public boolean isAuthPass(Long userId, String url, Integer resourceOrigin, boolean saveUrlToDB) {
        if (StringUtil.isEmpty(url)) return false;

        AuthResource authResource = getAuthResource(url, resourceOrigin, saveUrlToDB);
        if (authResource == null) return false;

        /**
         * 角色权限校验
         */
        Set<Long> roleIdSet = this.getRoleListByUserId(userId);
        if (roleIdSet == null || roleIdSet.isEmpty()) return false;

        List<String> urlPartList = AuthRoleOperationUtil.getUrlPartList(url);

        for (Long roleId : roleIdSet) {
            boolean isAuthPass = AuthRoleOperationUtil.isAuthPass(getRoleOperationMap(roleId), urlPartList,
                    authResource.getOperation(), authResource.getOrigin());
            if (isAuthPass) return true;
        }
        return false;
    }

    @Override
    public Set<Long> getRoleListByUserId(Long userId) {
        Set<Long> roleIdSet = this.authUserRoleService.getRoleIdListByUserId(userId);
        return roleIdSet;
    }

    @Override
    public AuthResource getAuthResource(String url, Integer resourceOrigin, boolean saveUrlToDB) {
        if (StringUtil.isEmpty(url)) return null;
//        Cache<String, Integer> cache = this.cacheService.getAuthResourceCache();
//        Integer operation = cache.get(url);
//        if (operation != null) return operation;

        AuthResource authResource = this.authResourceService.getByUrl(url, resourceOrigin);
        if (saveUrlToDB && authResource == null) {
            authResource = new AuthResource();
            authResource.setUrl(url);
            authResource.setOrigin(resourceOrigin);
            this.authResourceService.add(authResource);
            return null;
        }
        return authResource;
    }

    @Override
    public Map<String, Object> getRoleOperationMap(Long roleId) {
        if (roleId == null) return null;

        Map<String, Object> roleOperationMap;

//        Cache<Long, Map> cache = this.cacheService.getAuthRoleOperationCache();
//        Map<String, Object> roleOperationMap = cache.get(roleId);
//        if (roleOperationMap != null) return roleOperationMap;

        List<AuthRoleOperation> list = this.authRoleOperationService.getListByRoleId(roleId);
        roleOperationMap = AuthRoleOperationUtil.buildRoleOperationMap(list);

//        cache.put(roleId, roleOperationMap);

        return roleOperationMap;
    }
}
