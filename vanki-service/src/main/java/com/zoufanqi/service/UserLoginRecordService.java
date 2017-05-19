package com.zoufanqi.service;

import com.zoufanqi.entity.UserLoginRecord;
import com.zoufanqi.exception.ZouFanqiException;
import com.zoufanqi.result.ResultJson;

/**
 * Created by vanki on 2017/5/19.
 */
public interface UserLoginRecordService {
    ResultJson add(UserLoginRecord record) throws ZouFanqiException;
}
