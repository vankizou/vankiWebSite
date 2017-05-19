package com.zoufanqi.service.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.ShardedJedis;

public interface RedisDataSource {

    ShardedJedis getRedisClient();

    void returnResource(ShardedJedis shardedJedis);

    Jedis getJedis();

    ShardedJedis getRedisReadClient(Integer... flag);    // 读
}
