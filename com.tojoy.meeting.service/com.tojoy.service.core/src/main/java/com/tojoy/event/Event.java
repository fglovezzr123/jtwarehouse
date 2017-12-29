package com.tojoy.event;

import org.springframework.context.ApplicationEvent;

/**
 * 语言变化事件
 * 
 * @author lj
 *
 */
public class Event extends ApplicationEvent {

	/**
	 * 
	 */
	private static final long serialVersionUID = -322830857515524739L;
	
	/**
	 * 事件类型（1：删除，2：更新, 3:查询）
	 */
	private Integer operateFlag;

	/**
	 * 事件类型（1：删除，2：更新, 3:查询）
	 * @return
	 */
	public Integer getOperateFlag() {
		return operateFlag;
	}

	public Event(Object source) {
		super(source);
	}
	
	/**
	 * 事件构造方法
	 * @param source : 数据实体对象
	 * @param operateFlag : 事件类型（1：删除，2：更新, 3:查询）
	 */
	public Event(Object source, Integer operateFlag) {
		super(source);
		this.operateFlag = operateFlag;
	}
	
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Event [operateFlag=");
		builder.append(operateFlag);
		builder.append(",source=");
		builder.append(this.getSource());
		builder.append("]");
		return builder.toString();
	}
}
