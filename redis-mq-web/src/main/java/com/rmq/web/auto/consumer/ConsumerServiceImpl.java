package com.rmq.web.auto.consumer;

import com.rmq.web.auto.MessageStatus;
import com.rmq.web.redis.config.RedisService;
import com.rmq.web.redis.constants.RedisConstants;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @title 
 * @author xulz
 * @date 2019年1月21日上午11:34:07
 */
public class ConsumerServiceImpl{

	private final static Logger log = LoggerFactory.getLogger(ConsumerServiceThread.class);

	private DefaultConsumer defaultConsumer;
	
	private RedisService redisService;
	
	public ConsumerServiceImpl(DefaultConsumer defaultConsumer, RedisService redisService) {
		this.defaultConsumer = defaultConsumer;
		this.redisService = redisService;
	}

	public ConsumeMessageResult dealMessage() {
		ConsumeMessageResult result = new ConsumeMessageResult();
		try {
			/**redis队列中获取数据*/
			String key = RedisConstants.getKey(RedisConstants.REDIS_MESSAGE_PREFIX, defaultConsumer.getTopicName(), defaultConsumer.getGroupName());
			String message = redisService.lpop(key);
			if(StringUtils.isNotBlank(message)) {
				/**处理消息*/
				MessageStatus messageStatus = defaultConsumer.getMessageListener().consumeMessage(message);
				if(messageStatus != null) {
					switch(messageStatus) {
					case SUCCESS :
						result.setSuccess(true);
						break;
					case ERROR :
						result.setSuccess(false);
						/**处理失败放回队列中*/
						redisService.rpush(key, message);
						break;
					default:
		                break;
					}
				}else {
					result.setSuccess(false);
				}
			}else {
				result.setSuccess(false);
				result.setStatus(1);
			}
		}catch(Exception e) {
			log.error("dealMessage error", e);
		}
		return result;
	}

	public DefaultConsumer getDefaultConsumer() {
		return this.defaultConsumer;
	}
	
	public void setRedisService(RedisService redisService) {
		this.redisService = redisService;
	}
}
