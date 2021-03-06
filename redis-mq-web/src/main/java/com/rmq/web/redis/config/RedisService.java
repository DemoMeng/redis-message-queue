package com.rmq.web.redis.config;

import java.util.Set;

/**
 * @title
 * @author xulz
 * @date 2019年3月8日
 */
public interface RedisService {

	void set(String key, String value);
	
	String get(String key);
	
	void del(String key);
	
	boolean exists(String key);
	
	long llen(String key);
	
	void rpush(String key, String value);
	
	void lpush(String key, String value);
	
	String rpop(String key);
	
	String lpop(String key);
	
	Set<String> smembers(String key);
	
	void sadd(String key, String value);
	
	boolean sismember(String key, String member);
	
	void zadd(String key, double score, String member);
	
	long zcount(String key, double min, double max);
	
}
