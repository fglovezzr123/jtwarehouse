package org.com.wing.enterprise.bean;

import javax.persistence.Table;

/**
 * 
 * <p>Title:系统消息管理 </p>
 * <p>Description: </p>
 * <p>Company: </p> 
 * @author zhangzheng
 * @Date  2017年5月10日 上午11:44:54
 */
@Table(name = "qfy_sys_message")
public class SysMessage extends EnterPriseBaseBean {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 消息内容
	 */
	private String content;
	/**
	 * 消息发送状态  0 发送   1未发送  2 发送失败
	 */
	private int status;
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
}
