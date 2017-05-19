package com.zoufanqi.service.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

@Repository("redisDataSource")
public class RedisDataSourceImpl implements RedisDataSource {

    private static final Logger log = LoggerFactory.getLogger(RedisDataSourceImpl.class);

    @Autowired
    @Qualifier("shardedJedisPool")
    private ShardedJedisPool shardedJedisPool;

    @Autowired
    private JedisPool jedisPool;

    @Autowired
    @Qualifier("shardedJedisReadPool1")
    private ShardedJedisPool shardedJedisReadPool1; // 只读服务器一

    @Autowired
    @Qualifier("shardedJedisReadPool2")
    private ShardedJedisPool shardedJedisReadPool2; // 只读服务器二

    private static int status = 0;    // 上一次获取连接时的状态


    public RedisDataSourceImpl() {
        log.info("init RedisDataSourceImpl ....");
    }

    @Override
    public ShardedJedis getRedisClient() {
        ShardedJedis shardedJedis = null;
        try {
            shardedJedis = this.shardedJedisPool.getResource();
        } catch (Exception e) {
            log.error("getRedisClient error", e);
        }
        return shardedJedis;
    }

    @Override
    public void returnResource(ShardedJedis shardedJedis) {
        shardedJedis.close();
        //this.shardedJedisPool.returnResourceObject(shardedJedis);
    }

    @Override
    public Jedis getJedis() {
        return this.jedisPool.getResource();
    }

    /**
     * 只用于读
     *
     * @return
     */
    @Override
    public ShardedJedis getRedisReadClient(Integer... flag) {
        /**
         * 0 全部都可以连接
         * 1 readPool1 不可以连接
         * 2 readPool2 不可以连接
         * 3 readPool1 和 readPool2 不可以连接
         * 4 全部不可以连接
         */
        if (status == 4) return null;

        int f = flag == null || flag.length == 0 ? status : flag[0];

        if (this.shardedJedisReadPool1 != null && this.shardedJedisReadPool2 != null && f == 0) {
            if (this.shardedJedisReadPool1.getNumWaiters() > this.shardedJedisReadPool2.getNumWaiters()) {
                try {
                    return this.shardedJedisReadPool2.getResource();
                } catch (Exception e) {
                    status = 1;
                    return getRedisReadClient(1);
                }
            } else {
                try {
                    return this.shardedJedisReadPool1.getResource();
                } catch (Exception e) {
                    status = 2;
                    return getRedisReadClient(2);
                }
            }
        }
        if (this.shardedJedisReadPool1 != null && f != 1 && f != 3) {
            try {
                return this.shardedJedisReadPool1.getResource();
            } catch (Exception e) {
                status = 3;
                return getRedisReadClient(3);
            }
        }
        if (this.shardedJedisReadPool2 != null && f != 2 && f != 3) {
            try {
                return this.shardedJedisReadPool2.getResource();
            } catch (Exception e) {
                status = 3;
                return getRedisReadClient(3);
            }
        }
        if (this.shardedJedisPool != null && f != 4) {
            try {
                return this.shardedJedisPool.getResource();
            } catch (Exception e) {
                status = 4;
                return getRedisReadClient(4);
            }
        }
        status = 4;
        return null;
    }
}
