package com.rmq.web.test;


import com.rmq.web.auto.MessageLinser;
import com.rmq.web.auto.MessageStatus;
import com.rmq.web.auto.consumer.DefaultConsumer;

/**
 * @title 
 * @author xulz
 * @date 2019年1月18日下午5:38:08
 */
public class ConsumerTest {
	
	public static void main(String[] args) {
		String topicName = "testTopic";
		String groupName = "testGroup";
		DefaultConsumer defaultConsumer = new DefaultConsumer(topicName, groupName, 1);
		defaultConsumer.setMessageLinser(new MessageLinser() {
			@Override
			public MessageStatus consumeMessage(String message) {
				System.out.println(message);
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				return MessageStatus.SUCCESS;
			}
		});
		defaultConsumer.start();
	}
}
