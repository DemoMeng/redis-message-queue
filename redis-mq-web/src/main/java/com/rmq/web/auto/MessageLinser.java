package com.rmq.web.auto;

/**
 * @title 
 * @author xulz
 * @date 2019年1月22日上午11:30:28
 */
public interface MessageLinser {

	/**
	 * 处理消息
	 * @param message
	 * @return
	 */
	MessageStatus consumeMessage(String message);
}
