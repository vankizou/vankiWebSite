package com.zoufanqi.service.redis.init;

import redis.clients.jedis.JedisShardInfo;

/**
 * Created by vanki on 16/11/7.
 */
public class MyJedisShardInfo extends JedisShardInfo {
    public MyJedisShardInfo(String host, String password, Integer port, Integer db) {
        super(new StringBuffer("http://")
                .append(host)
                .append(":")
                .append(port)
                .append("/")
                .append(db == null ? 0 : db)
                .toString());
        this.setPassword(password);
    }
}
