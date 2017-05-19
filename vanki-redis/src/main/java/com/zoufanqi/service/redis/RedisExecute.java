package com.zoufanqi.service.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import redis.clients.jedis.ShardedJedis;

@Repository("redisExecute")
public class RedisExecute {
	
	@Autowired
	private RedisDataSource redisDataSource;

	private static final Logger log = LoggerFactory.getLogger(RedisExecute.class);
	
	public <T> T execute(Callback<T> callback){
		T t = null;
		if(callback==null){
			return null;
		}
		ShardedJedis client = this.redisDataSource.getRedisClient();
		
		if(client==null){
			return null;
		}
		try{
			t = callback.exe(client);
		} catch(Exception e){
			log.error(e.getMessage(),e);
		} finally{
			this.redisDataSource.returnResource(client);
		}
		return t; 
	}
	
	interface Callback<T>{
		T exe(ShardedJedis client);
	}
}
