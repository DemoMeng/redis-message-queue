package com.rmq.web.auto.producer;

import com.rmq.web.redis.config.RedisService;
import com.rmq.web.redis.constants.RedisConstants;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Set;

/**
 * @title
 * @author xulz
 * @date 2019年1月22日下午6:00:20
 */
public class ProducerServiceImpl{

	private final static Logger log = LoggerFactory.getLogger(ProducerServiceImpl.class);

	private DefaultProducer defaultProducer;

	private RedisService redisService;

	public ProducerServiceImpl(DefaultProducer defaultProducer, RedisService redisService) {
		this.defaultProducer = defaultProducer;
		this.redisService = redisService;
	}

	public void sendMessage(String message) {
		try {
			/**已注册的topic，有分组的发送给分组，没有分组的发送给topic下所有的分组*/
			if(StringUtils.isNotBlank(defaultProducer.getGroupName())) {
				/**有分组的发送给分组*/
				redisService.rpush(RedisConstants.getKey(RedisConstants.REDIS_MESSAGE_PREFIX, defaultProducer.getTopicName(), defaultProducer.getGroupName()), message);
			}else {
				/**没有分组的发送给topic下所有的分组*/
				Set<String> groups = redisService.smembers(RedisConstants.getKey(RedisConstants.REDIS_TOPIC_GROUPS_PREFIX, defaultProducer.getTopicName()));
				if(groups != null) {
					for(String group : groups) {
						redisService.rpush(RedisConstants.getKey(RedisConstants.REDIS_MESSAGE_PREFIX, defaultProducer.getTopicName(), group), message);
					}
				}
			}
		}catch(Exception e) {
			log.error("sendMessage error", e);
		}
	}

}
