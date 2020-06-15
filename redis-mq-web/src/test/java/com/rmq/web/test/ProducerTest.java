package com.rmq.web.test;


import com.rmq.web.auto.producer.DefaultProducer;

/**
 * @title 
 * @author xulz
 * @date 2019年1月18日下午5:38:08
 */
public class ProducerTest {
	
	public static void main(String[] args) {
		String topicName = "testTopic";
		String groupName = "testGroup";
		DefaultProducer defaultProducer = new DefaultProducer(topicName, groupName);
		for(int i=0;i<100;i++) {
			String message = "i am message " + i;
			defaultProducer.sendMessage(message);
		}
	}
}
