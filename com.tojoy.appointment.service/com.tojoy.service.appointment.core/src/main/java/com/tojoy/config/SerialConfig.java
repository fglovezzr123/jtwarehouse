/**  
 * @Project: jtoa
 * @Title: SerialConfig.java
 * @Package com.oa.commons.config
 * @date 2016-10-14 下午4:47:17
 * @Copyright: 2016 
 */
package com.tojoy.config;

/**
 * 
 * 类名：SerialConfig
 * 功能：
 * 详细：
 * 作者：zcl
 * 版本：1.0
 * 日期：2016-10-14 下午4:47:17
 *
 */
public class SerialConfig {
	
	private String clientname;
	private String clientcode ;
	private long validstart;
	private long validend ;
	private String validday;
	private String key ;
	private String usetime;
	public String getClientname() {
		return clientname;
	}
	public void setClientname(String clientname) {
		this.clientname = clientname;
	}
	public String getClientcode() {
		return clientcode;
	}
	public void setClientcode(String clientcode) {
		this.clientcode = clientcode;
	}
	 
	public long getValidstart() {
		return validstart;
	}
	public void setValidstart(long validstart) {
		this.validstart = validstart;
	}
	public long getValidend() {
		return validend;
	}
	public void setValidend(long validend) {
		this.validend = validend;
	}
	public String getValidday() {
		return validday;
	}
	public void setValidday(String validday) {
		this.validday = validday;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getUsetime() {
		return usetime;
	}
	public void setUsetime(String usetime) {
		this.usetime = usetime;
	} 
	
	
	
	

}
