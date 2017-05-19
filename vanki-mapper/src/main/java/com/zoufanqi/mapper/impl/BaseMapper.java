package com.zoufanqi.mapper.impl;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

/**
 * Created by vanki on 2017/3/24.
 */
public class BaseMapper {
    @Autowired
    @Qualifier("writeSqlSessionTemplate")
    private SqlSessionTemplate writeSqlSessionTemplate;

    @Autowired
    @Qualifier("readSqlSessionTemplate1")
    private SqlSessionTemplate readSqlSessionTemplate1;


    public SqlSessionTemplate getReadSqlSessionTemplate() {
        return this.readSqlSessionTemplate1;
    }

    public SqlSessionTemplate getWriteSqlSessionTemplate() {
        return this.writeSqlSessionTemplate;
    }
}
