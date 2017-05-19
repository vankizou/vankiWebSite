package com.zoufanqi.service.impl;

import com.zoufanqi.entity.UserLoginRecord;
import com.zoufanqi.exception.ZouFanqiException;
import com.zoufanqi.mapper.UserLoginRecordMapper;
import com.zoufanqi.result.ResultBuilder;
import com.zoufanqi.result.ResultJson;
import com.zoufanqi.service.UserLoginRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by vanki on 2017/5/19.
 */
@Service("userLoginRecordService")
public class UserLoginRecordServiceImpl implements UserLoginRecordService {
    @Autowired
    private UserLoginRecordMapper userLoginRecordMapper;

    @Override
    public ResultJson add(UserLoginRecord record) throws ZouFanqiException {
        if (record == null) return ResultBuilder.buildParamError();

        int status = this.userLoginRecordMapper.insertSelective(record);

        if (status > 0) return ResultBuilder.build();
        return ResultBuilder.buildDBError();
    }
}
