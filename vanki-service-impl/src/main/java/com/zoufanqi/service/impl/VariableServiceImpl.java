package com.zoufanqi.service.impl;

import com.zoufanqi.entity.Variable;
import com.zoufanqi.entity.VariableExample;
import com.zoufanqi.exception.ZouFanqiException;
import com.zoufanqi.mapper.VariableMapper;
import com.zoufanqi.result.ResultBuilder;
import com.zoufanqi.result.ResultJson;
import com.zoufanqi.service.VariableService;
import com.zoufanqi.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("variableService")
public class VariableServiceImpl implements VariableService {
    @Autowired
    private VariableMapper variableMapper;

    @Override
    public ResultJson deleteById(Long id) throws ZouFanqiException {
        if (StringUtil.isNotAutoId(id)) return ResultBuilder.build();

        int status = this.variableMapper.deleteByPrimaryKey(id);
        if (status > 0) return ResultBuilder.build();
        return ResultBuilder.buildErrorDB();
    }

    @Override
    public ResultJson deleteByCode(String code) throws ZouFanqiException {
        if (StringUtil.isEmpty(code)) return ResultBuilder.build();

        VariableExample example = new VariableExample();
        example.createCriteria().andCodeEqualTo(code);

        int status = this.variableMapper.deleteByExample(example);
        if (status > 0) return ResultBuilder.build();
        return ResultBuilder.buildErrorDB();
    }

    @Override
    public Variable getByCode(String code) throws ZouFanqiException {
        if (StringUtil.isEmpty(code)) return null;

        VariableExample example = new VariableExample();
        example.createCriteria().andCodeEqualTo(code);

        List<Variable> list = this.variableMapper.selectByExample(example);

        int size;
        if (list == null || (size = list.size()) == 0) {
            return null;
        } else if (size > 1) {
            for (int i = 1; i < size; i++) {
                Variable v = list.get(i);
                if (v == null) continue;
                this.deleteById(v.getId());
            }
        }
        return list.get(0);
    }
}