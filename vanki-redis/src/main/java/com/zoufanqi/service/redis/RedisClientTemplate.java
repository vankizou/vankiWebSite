package com.zoufanqi.service.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import redis.clients.jedis.ShardedJedis;

@Repository("redisClientTemplate")
public class RedisClientTemplate {
	
	private static final Logger LOG = LoggerFactory.getLogger(RedisClientTemplate.class);

	@Autowired
	private RedisExecute redisExecute;
	
	
	public RedisClientTemplate() {
		LOG.info("init RedisClientTemplate ....");
	}

	public String set(final String key, final String value){
		return exec(new RedisExecute.Callback<String>() {
			public String exe(ShardedJedis client) {
				return client.set(key, value);
			}
		});
	}
	
	public String get(final String key){
		return exec(new RedisExecute.Callback<String>(){
			public String exe(ShardedJedis client) {
				return client.get(key);
			}
		});
	}
	
	private <T> T exec(RedisExecute.Callback<T> callback){
		return this.redisExecute.execute(callback);
	}
	
}
