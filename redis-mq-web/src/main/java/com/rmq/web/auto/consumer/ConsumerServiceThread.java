package com.rmq.web.auto.consumer;


import com.rmq.web.redis.config.RedisService;
import com.rmq.web.redis.constants.RedisConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @title 
 * @author xulz
 * @date 2019年1月21日上午11:26:09
 */
public class ConsumerServiceThread extends Thread {

	private final static Logger log = LoggerFactory.getLogger(ConsumerServiceThread.class);

	private ConsumerServiceImpl consumerService;
	
	private RedisService redisService;
	
	public ConsumerServiceThread() {}
	
	@Override
	public void run() {
		log.info("消费者线程{}启动...",this.getName());
		int count = 0;
		String lockKey = RedisConstants.getKey(RedisConstants.REDIS_CONSUMER_THREAD_LOCK_PREFIX, consumerService.getDefaultConsumer().getTopicName(), consumerService.getDefaultConsumer().getGroupName());
		String heartKey = RedisConstants.getKey(RedisConstants.REDIS_CONSUMER_HEART_PREFIX, consumerService.getDefaultConsumer().getTopicName(), consumerService.getDefaultConsumer().getGroupName());
		/**重启时删除key*/
		redisService.del(lockKey);
		while(true) {
			try {
				/**通过redis缓存锁安全关闭线程，保证队列的安全性和完整性*/
				if(redisService.exists(lockKey)) {
					log.info("消费者线程{}安全关闭...",this.getName());
					break;
				}else {
					/**加入心跳，监听线程*/
					redisService.zadd(heartKey, System.currentTimeMillis(), this.getName());
					ConsumeMessageResult result = consumerService.dealMessage();
					/**消费消息结果处理 消费成功、失败、没有更多消息*/
					if(!result.isSuccess()) {
						sleep(3000);
					}
					count++;
				}
			}catch(Exception e) {
				log.error(this.getName() + "消费者线程异常", e);
			}
		}
		log.info("消费者线程{}安全结束...消费消息{}条",this.getName(),count);
	}
	
	public void setRedisService(RedisService redisService) {
		this.redisService = redisService;
	}
	
	public void setConsumerService(ConsumerServiceImpl consumerService) {
		this.consumerService = consumerService;
	}
}
