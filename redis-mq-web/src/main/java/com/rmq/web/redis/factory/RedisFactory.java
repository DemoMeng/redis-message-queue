package com.rmq.web.redis.factory;


import com.rmq.web.redis.config.RedisService;
import com.rmq.web.redis.config.RedisServiceClusterImpl;
import com.rmq.web.redis.config.RedisServiceImpl;
import com.rmq.web.redis.constants.ConfigConstants;

/**
 * @author mqz
 */
public class RedisFactory {

	private static RedisService redisService;
	
	public static RedisService getRedisService() {
		if(redisService == null) {
			if(ConfigConstants.REDIS_CLUSTER_OPEN == 1) {
				redisService = new RedisServiceClusterImpl();
			}else {
				redisService = new RedisServiceImpl();
			}
		}
		return redisService;
	}
	
}
