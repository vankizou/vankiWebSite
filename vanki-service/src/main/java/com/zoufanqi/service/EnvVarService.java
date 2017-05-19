package com.zoufanqi.service;

import com.zoufanqi.consts.EnumEnvVar;
import com.zoufanqi.entity.EnvVar;
import com.zoufanqi.exception.ZouFanqiException;
import com.zoufanqi.result.ResultJson;

public interface EnvVarService {

    ResultJson deleteById(Long id) throws ZouFanqiException;

    ResultJson deleteByKey(EnumEnvVar key) throws ZouFanqiException;

    EnvVar getByKey(EnumEnvVar key) throws ZouFanqiException;
}