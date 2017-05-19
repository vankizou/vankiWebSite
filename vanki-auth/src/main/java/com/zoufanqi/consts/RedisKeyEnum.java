package com.zoufanqi.consts;

/**
 * Created by vanki on 2017/5/10.
 */
public enum RedisKeyEnum {
    MAP_AUTH_RESOURCE,  // key - url, value - AuthResource
    MAP_AUTH_ROLE_OPERATION_LIST_FOR_ROLE_ID,   // key - roleId, [RoleOperation]
    MAP_AUTH_USER_ROLE_IDS, // key - userId, value - [roleId]
    ;
}
