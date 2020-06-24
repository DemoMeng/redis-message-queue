package com.rmq.web.auto.producer;

import com.rmq.web.redis.config.RedisService;
import com.rmq.web.redis.constants.RedisConstants;
import com.rmq.web.redis.factory.RedisFactory;
import lombok.Data;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @title 
 * @author xulz
 * @date 2019年1月18日下午3:23:30
 */
@Data
public class DefaultProducer {
	private final static Logger log = LoggerFactory.getLogger(DefaultProducer.class);

	/**消息的topic*/
	private String topicName;
	/**消费者的group*/
	private String groupName;
	
	private ProducerServiceImpl producerService;
	
	private RedisService redisService;
	
	/**
	 * 生产者，
	 * @param topicName，消息主题
	 * @param groupName，消费者group，为空的时候，消息发送给topic下所有group
	 */
	public DefaultProducer(String topicName, String groupName) {
		this.topicName = topicName;
		this.groupName = groupName;
		redisService = RedisFactory.getRedisService();
		if(checkTopicAndGroup()) {
			producerService = new ProducerServiceImpl(this, redisService);
		}
	}
	
	public void sendMessage(String message) {
		if(producerService != null) {
			producerService.sendMessage(message);
		}else {
			log.info("发送消息失败，请检查消息topic和group是否注册");
		}
	}
	
	public boolean checkTopicAndGroup() {
		try {
			/**是否注册的topic*/
			boolean exists = redisService.sismember(RedisConstants.getKey(RedisConstants.REDIS_TOPIC), this.getTopicName());
			if(exists) {
				/**已注册*/
				if(StringUtils.isNotBlank(this.getGroupName())) {
					/**是否注册group*/
					exists = redisService.sismember(RedisConstants.getKey(RedisConstants.REDIS_TOPIC_GROUPS_PREFIX, this.getTopicName()), this.getGroupName());
				}
			}
			if(exists) return true; 
		}catch(Exception e) {
			log.error("checkTopicAndGroup error", e);
		}
		return false;
	}

}