package com.wing.socialcontact.service.wx.bean;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 
 * <p>Title: 活动用户报名表</p>
 * <p>Description: </p>
 * <p>Company: </p> 
 * @author zhangzheng
 * @Date  2017年4月19日 下午6:45:10
 */
@Table(name = "TJY_Activity_user")
public class ActivityUser implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7443307523849405740L;

	/** 主键 */
    @Id
	@Column(name = "id")
	@GeneratedValue(generator = "UUID")
	private String id;

    /**
     * 活动id
     */
    private String activityId;
    /**
     * 用户id
     */
    private String userId;
    /**
     * 用户名 
     */
    private String userName;
    /**
     * 联系电话
     */
    private String phone;
    /**
     * 报名时间
     */
    private Date createTime;
    /**
     * 报名状态 1、待确认  2 用户取消  3 活动取消   4已确认 5已拒绝
     * @return
     */
    private Integer status;
    
    /**
     * 结算状态   0未结算   1已结算  
     * @return
     */
    private Integer balance;
    
    /** 订单ID */
	private String orderId;
	
	/** 微信返回订单ID */
	private String transactionId;

	/** 支付状态，1预支付2已支付 */
	private Integer orderStatus;

    /** 支付时间 */
	private Date payTime;
	
	/** 实际支付金额*/
	private Double payPrice;

	/** 优惠券金额*/
	private Double coupon;
    
    
    
	public Integer getBalance() {
		return balance;
	}
	public void setBalance(Integer balance) {
		this.balance = balance;
	}
	public Double getCoupon() {
		return coupon;
	}
	public void setCoupon(Double coupon) {
		this.coupon = coupon;
	}
	public String getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}
	public Double getPayPrice() {
		return payPrice;
	}
	public void setPayPrice(Double payPrice) {
		this.payPrice = payPrice;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public Integer getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(Integer orderStatus) {
		this.orderStatus = orderStatus;
	}
	public Date getPayTime() {
		return payTime;
	}
	public void setPayTime(Date payTime) {
		this.payTime = payTime;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getActivityId() {
		return activityId;
	}
	public void setActivityId(String activityId) {
		this.activityId = activityId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
    
    
    
}
