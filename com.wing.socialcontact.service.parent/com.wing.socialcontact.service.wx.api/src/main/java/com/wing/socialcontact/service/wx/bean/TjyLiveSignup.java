package com.wing.socialcontact.service.wx.bean;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
@Table(name="tjy_live_signup")
public class TjyLiveSignup implements Serializable {

	@Id
	@Column(name="id")
	@GeneratedValue(generator = "UUID")
	private String  id;
	/**
	 * 用户id
	 */
	private String  userid;
	/**
	 * 直播id
	 */
	private String  liveid;
	/**
	 * 报名手机
	 */
	private String  mobile;
	/**
	 * 报名名称
	 */
	private String  name;
	/**
	 * 支付金额
	 */
	private int  amount;
	/**
	 * 支付状态 1未支付  2已支付  3已退款
	 */
	private int  paystatus;
	/**
	 * 订单状态 待用  
	 */
	private int  orderstatus;
	/**
	 * 支付时间
	 */
	private Date  paytime;
	/**
	 * 创建时间
	 */
	private Date  createtime;
	/**
	 * 是否发送过提醒：0否   1是
	 */
	private Integer isremind;
	
	
	public Integer getIsremind() {
		return isremind;
	}

	public void setIsremind(Integer isremind) {
		this.isremind = isremind;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getOrderstatus() {
		return orderstatus;
	}
	public void setOrderstatus(int orderstatus) {
		this.orderstatus = orderstatus;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getLiveid() {
		return liveid;
	}
	public void setLiveid(String liveid) {
		this.liveid = liveid;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public int getPaystatus() {
		return paystatus;
	}
	public void setPaystatus(int paystatus) {
		this.paystatus = paystatus;
	}
	public Date getPaytime() {
		return paytime;
	}
	public void setPaytime(Date paytime) {
		this.paytime = paytime;
	}
	public Date getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
	
	
}
