package com.rmq.web.auto.consumer;

import lombok.Data;

/**
 * @title 
 * @author xulz
 * @date 2019年1月22日上午11:48:01
 */
@Data
public class ConsumeMessageResult {
	/**消费成功 失败*/
	private boolean success;
	/**默认为0，1是队列为空,2处理消息失败*/
	private int status = 0;
}
