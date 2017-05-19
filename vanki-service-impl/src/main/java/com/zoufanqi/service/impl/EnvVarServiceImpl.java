package com.zoufanqi.service.impl;

import com.zoufanqi.consts.EnumEnvVar;
import com.zoufanqi.entity.EnvVar;
import com.zoufanqi.entity.EnvVarExample;
import com.zoufanqi.exception.ZouFanqiException;
import com.zoufanqi.mapper.EnvVarMapper;
import com.zoufanqi.result.ResultBuilder;
import com.zoufanqi.result.ResultJson;
import com.zoufanqi.service.EnvVarService;
import com.zoufanqi.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 全局变量
 */
@Service("envVarService")
public class EnvVarServiceImpl implements EnvVarService {
    @Autowired
    private EnvVarMapper envVarMapper;

    @Override
    public ResultJson deleteById(Long id) throws ZouFanqiException {
        if (StringUtil.isNotId(id)) return ResultBuilder.build();

        int status = this.envVarMapper.deleteByPrimaryKey(id);
        if (status > 0) return ResultBuilder.build();
        return ResultBuilder.buildDBError();
    }

    @Override
    public ResultJson deleteByKey(EnumEnvVar key) throws ZouFanqiException {
        if (key == null) return ResultBuilder.build();

        EnvVarExample example = new EnvVarExample();
        example.createCriteria().andKEqualTo(key.name());

        int status = this.envVarMapper.deleteByExample(example);
        if (status > 0) return ResultBuilder.build();
        return ResultBuilder.buildDBError();
    }

    @Override
    public EnvVar getByKey(EnumEnvVar key) throws ZouFanqiException {
        if (key == null) return null;

        EnvVarExample example = new EnvVarExample();
        example.createCriteria().andKEqualTo(key.name());

        List<EnvVar> list = this.envVarMapper.selectByExample(example);

        int size;
        if (list == null || (size = list.size()) == 0) {
            return null;
        } else if (size > 1) {
            for (int i = 1; i < size; i++) {
                EnvVar v = list.get(i);
                if (v == null) continue;
                this.deleteById(v.getId());
            }
        }
        return list.get(0);
    }
}