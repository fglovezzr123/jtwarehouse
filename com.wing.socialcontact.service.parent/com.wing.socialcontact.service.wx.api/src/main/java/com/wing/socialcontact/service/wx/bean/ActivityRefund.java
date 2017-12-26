package com.wing.socialcontact.service.wx.bean;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
/**
 * 
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Company: </p> 
 * @author zhangzheng
 * @Date  2017年5月4日 下午4:36:05
 */
@Table(name = "TJY_Activity_refund")
public class ActivityRefund implements Serializable {

	 /**
		 * 
		 */
		private static final long serialVersionUID = -256714248102917079L;

		/** 主键 */
	    @Id
		@Column(name = "id")
		@GeneratedValue(generator = "UUID")
		private String id;
	    
	    /**用户id*/
	    private String userId;
	    
	    /**退款状态*/
	    private Integer status;
	    
	    /**退款金额*/
	    private Double amount;

		/**创建时间*/
	    private Date createTime;
	    
	    /**退款时间*/
	    private Date refundTime;
	    
	    /**类型   1:审核未通过  2：活动取消*/
	    private Integer type;
	    
	    /**订单id*/
	    private String orderId;
	    
	    /**活动id*/
	    private String activityId;
	    
	    /** 微信返回订单ID */
		private String transactionId;
	    
	    
	    
	    
	    public String getTransactionId() {
			return transactionId;
		}

		public void setTransactionId(String transactionId) {
			this.transactionId = transactionId;
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

		public String getOrderId() {
			return orderId;
		}

		public void setOrderId(String orderId) {
			this.orderId = orderId;
		}

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}


		public Integer getStatus() {
			return status;
		}

		public void setStatus(Integer status) {
			this.status = status;
		}

		public Double getAmount() {
			return amount;
		}

		public void setAmount(Double amount) {
			this.amount = amount;
		}

		public Date getCreateTime() {
			return createTime;
		}

		public void setCreateTime(Date createTime) {
			this.createTime = createTime;
		}

		public Date getRefundTime() {
			return refundTime;
		}

		public void setRefundTime(Date refundTime) {
			this.refundTime = refundTime;
		}

		public Integer getType() {
			return type;
		}

		public void setType(Integer type) {
			this.type = type;
		}

		public static long getSerialversionuid() {
			return serialVersionUID;
		}

	    
}
