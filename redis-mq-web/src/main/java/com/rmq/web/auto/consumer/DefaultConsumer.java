package com.rmq.web.auto.consumer;


import com.rmq.web.auto.MessageLinser;
import com.rmq.web.redis.config.RedisService;
import com.rmq.web.redis.factory.RedisFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @title 
 * @author xulz
 * @date 2019年1月18日下午3:30:17
 */
public class DefaultConsumer {
	private final static Logger log = LoggerFactory.getLogger(ConsumerServiceThread.class);

	//默认启动的处理message线程数ConsumerServiceImpl
	private int threadNum = 1;
	
	//消息的topic
	private String topicName;
	
	//消费者的group
	private String groupName;
	
	private ConsumerServiceImpl consumerService;
	
	private RedisService redisService;
	
	private MessageLinser messageLinser;
	
	public DefaultConsumer(String topicName, String groupName, int threadNum) {
		this.topicName = topicName;
		this.groupName = groupName;
		if(threadNum > 0) this.threadNum = threadNum;
		redisService = RedisFactory.getRedisService();
		consumerService = new ConsumerServiceImpl(this, redisService);
	}
	
	public DefaultConsumer(String topicName, String groupName) {
		this.topicName = topicName;
		this.groupName = groupName;
		redisService = RedisFactory.getRedisService();
		consumerService = new ConsumerServiceImpl(this, redisService);
	}
	
	//启动。保证消息不重复，不丢失，但是不保存历史消息
	public void start() {
		for(int i=0;i<this.threadNum;i++) {
			ConsumerServiceThread thread = new ConsumerServiceThread();
			thread.setConsumerService(consumerService);
			thread.setRedisService(redisService);
			thread.setName("ConsumerServiceThread_" + i);
			thread.start();
		}
		log.info("消息者线程启动已启动");
	}

	public String getTopicName() {
		return topicName;
	}

	public void setTopicName(String topicName) {
		this.topicName = topicName;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	
	public void setMessageLinser(MessageLinser messageLinser) {
		this.messageLinser = messageLinser;
	}
	
	public MessageLinser getMessageLinser() {
		return this.messageLinser;
	}
}
