package com.wing.socialcontact.event;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class EventPublish implements ApplicationContextAware {

	private ApplicationContext applicationContext;

	public void setApplicationContext(ApplicationContext ac) throws BeansException {
		this.applicationContext = ac;
	}

	/**
	 * 要发布或是触发的事件
	 * @param e
	 */
	public void publishEvent(Event e) {
		this.applicationContext.publishEvent(e);
	}
}
