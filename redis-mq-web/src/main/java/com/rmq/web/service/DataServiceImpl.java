package com.rmq.web.service;

import com.rmq.web.common.MsgListStatus;
import com.rmq.web.model.vo.TopicsVO;
import com.rmq.web.redis.config.RedisService;
import com.rmq.web.redis.constants.RedisConstants;
import com.rmq.web.redis.factory.RedisFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @title 
 * @author xulz
 * @date 2019年1月24日下午6:05:41
 */
@Service
public class DataServiceImpl{
	private static final Logger log = LoggerFactory.getLogger(DataServiceImpl.class);

	private RedisService redisService = RedisFactory.getRedisService();

	public List<TopicsVO> getAllTopic(){
		List<TopicsVO> list = new ArrayList<>();
		Set<String> sets = redisService.smembers(RedisConstants.REDIS_TOPIC);
		for(String tName:sets){
			Set<String> gSets = redisService.smembers(RedisConstants.getKey(RedisConstants.REDIS_TOPIC_GROUPS_PREFIX, tName));
			StringBuffer sb = new StringBuffer();
			for(String g:gSets){
				sb.append(g);
				sb.append(";");
			}
			TopicsVO vo = new TopicsVO()
					.setTopicName(tName)
					.setGroups(sb.toString());
			list.add(vo);
		}
		return list;
	}

	
	public Set<String> getAllTopics() {
		Set<String> data = null;
		try {
			RedisService redisService = RedisFactory.getRedisService();
			data = redisService.smembers(RedisConstants.REDIS_TOPIC);
		}catch(Exception e) {
			log.error("getAllTopics error", e);
		}
		return data;
	}
	
	public Set<String> getAllGroups() {
		Set<String> data = null;
		try {
			RedisService redisService = RedisFactory.getRedisService();
			data = redisService.smembers(RedisConstants.REDIS_GROUP);
		}catch(Exception e) {
			log.error("getAllTopics error", e);
		}
		return data;
	}

	public Set<String> getTopicGroups(String topic) {
		Set<String> data = null;
		try {
			RedisService redisService = RedisFactory.getRedisService();
			data = redisService.smembers(RedisConstants.getKey(RedisConstants.REDIS_TOPIC_GROUPS_PREFIX, topic));
		}catch(Exception e) {
			log.error("getTopicGroups error", e);
		}
		return data;
	}

	public Set<String> getGroupTopics(String group) {
		Set<String> data = null;
		try {
			RedisService redisService = RedisFactory.getRedisService();
			data = redisService.smembers(RedisConstants.getKey(RedisConstants.REDIS_GROUP_TOPICS_PREFIX, group));
		}catch(Exception e) {
			log.error("getGroupTopics error", e);
		}
		return data;
	}

	public MsgListStatus getMsgList(String topic, String group) {
		MsgListStatus msgListStatus = new MsgListStatus();
		try {
			RedisService redisService = RedisFactory.getRedisService();
			long leftMsg = redisService.llen(RedisConstants.getKey(RedisConstants.REDIS_MESSAGE_PREFIX, topic, group));
			long time = System.currentTimeMillis();
			long leftThread = redisService.zcount(RedisConstants.getKey(RedisConstants.REDIS_CONSUMER_HEART_PREFIX, topic, group), time - 10000, time + 10000);
			msgListStatus.setLeftMsg(leftMsg);
			msgListStatus.setLeftThread(leftThread);
			msgListStatus.setGroup(group);
			msgListStatus.setTopic(topic);
		}catch(Exception e) {
			log.error("getMsgList error", e);
		}
		return msgListStatus;
	}
	
	public List<MsgListStatus> getAllMsgList() {
		List<MsgListStatus> list = new ArrayList<MsgListStatus>();
		try {
			Set<String> topics = this.getAllTopics();
			if(topics != null && topics.size() > 0) {
				for(String topic : topics) {
					Set<String> groups = this.getTopicGroups(topic);
					if(groups != null && groups.size() > 0) {
						for(String group : groups) {
							MsgListStatus object = this.getMsgList(topic, group);
							list.add(object);
						}
					}
				}
			}
		}catch(Exception e) {
			log.error("getMsgList error", e);
		}
		return list;
	}

	public int changeConsumerStatus(String topic, String group, int status) {
		int result = 0;
		try {
			RedisService redisService = RedisFactory.getRedisService();
			String key = RedisConstants.getKey(RedisConstants.REDIS_CONSUMER_THREAD_LOCK_PREFIX, topic, group);
			if(status == 0) {
				//关闭
				redisService.set(key, "1");
				result = 1;
			}else if(status == 1) {
				//开启
				redisService.del(key);
				result = 1;
			}
		}catch(Exception e) {
			log.error("getMsgList error", e);
			result = -1;
		}
		return result;
	}

}
