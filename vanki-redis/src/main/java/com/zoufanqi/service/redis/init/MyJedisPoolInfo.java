package com.zoufanqi.service.redis.init;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import redis.clients.jedis.JedisPool;

/**
 * Created by vanki on 16/11/7.
 */
public class MyJedisPoolInfo extends JedisPool {
    public MyJedisPoolInfo(GenericObjectPoolConfig poolConfig, String host, int port, int timeout, String password, Integer db) {
        super(poolConfig, host, port, timeout, password, db == null ? 0 : db);
    }
}
