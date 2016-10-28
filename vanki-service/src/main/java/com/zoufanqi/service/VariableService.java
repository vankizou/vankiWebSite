package com.zoufanqi.service;

import com.zoufanqi.entity.Variable;
import com.zoufanqi.exception.ZouFanqiException;
import com.zoufanqi.result.ResultJson;

public interface VariableService {

    ResultJson deleteById(Long id) throws ZouFanqiException;

    ResultJson deleteByCode(String code) throws ZouFanqiException;

    Variable getByCode(String code) throws ZouFanqiException;
}