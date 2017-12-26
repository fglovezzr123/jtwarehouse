package com.wing.socialcontact.service.wx.bean;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TJY_WALLET_LOG 钱包操作记录（包括充值及消费）
 * 
 * @author zengmin
 * @date 2017-04-11 08:52:35
 * @version 1.0
 */
@Table(name = "TJY_WALLET_LOG")
public class WalletLog implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**  */
	@Id
	private String id;

	/** 操作类型（1：金额2：J币3：互助宝） */
	private String type;

	/** 操作金额 */
	private Double amount;

	/** 支付金额(用于充值) */
	private Double rmbAmount;

	/** 支付状态（用于充值0:待支付1:成功2:失败） */
	private String payStatus;

	/** 支付方式（用于充值，默认1：微信支付（即线上支付）2：线下支付） */
	private String payType;

	/** 微信支付sn(用于充值) */
	private String paySn;

	/** 记录类型（1：收入2：消费） */
	private String pdType;

	/** 用户编号 */
	private String userId;

	/** 删除标记 */
	private String deleted;

	/** 操作时间 */
	private Date createTime;

	/** 付款类型（待定义） */
	private String fkType;

	/** 备注说明 */
	private String remark;

	/** 操作后钱包余额 */
	private Double yeAmount;
	
	/** 
	 * 业务类型
	 * 1、充值
	 * 2、提现
	 * 3、提现退回
	 * 4、订单消费
	 * 5、悬赏合作
	 * 6、悬赏合作退回
	 * 7、打赏动态
	 * 8、打赏合作
	 * 9、打赏收费
	 * 10、打赏话题
	 * 11、活动收益
	 * 12、获得悬赏
	 * 13、语音付费
	 * 14、语音收费
	 * 15、获得打赏
	 * 16、直播报名
	 * 17、打赏文章
	 */
	private Integer businessType;
	
	/**商户名称-一般钱包收入会用到该字段，存用户编号*/
	private String sourceUser;
	
	private String fkId;

	public WalletLog() {
	}

	/**
	 * 获取
	 */
	public String getId() {
		return id;
	}

	/**
	 * 设置
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * 获取操作类型（1：金额2：J币3：互助宝）
	 */
	public String getType() {
		return type;
	}

	/**
	 * 设置操作类型（1：金额2：J币3：互助宝）
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * 获取操作金额
	 */
	public Double getAmount() {
		return amount;
	}

	/**
	 * 设置操作金额
	 */
	public void setAmount(Double amount) {
		this.amount = amount;
	}

	/**
	 * 获取支付金额(用于充值)
	 */
	public Double getRmbAmount() {
		return rmbAmount;
	}

	/**
	 * 设置支付金额(用于充值)
	 */
	public void setRmbAmount(Double rmbAmount) {
		this.rmbAmount = rmbAmount;
	}

	/**
	 * 获取支付状态（用于充值1:成功2:失败）
	 */
	public String getPayStatus() {
		return payStatus;
	}

	/**
	 * 设置支付状态（用于充值0:待支付1:成功2:失败）
	 */
	public void setPayStatus(String payStatus) {
		this.payStatus = payStatus;
	}

	/**
	 * 获取支付方式（用于充值，默认1：微信支付（即线上支付）2：线下支付））
	 */
	public String getPayType() {
		return payType;
	}

	/**
	 * 设置支付方式（用于充值，默认1：微信支付（即线上支付）2：线下支付））
	 */
	public void setPayType(String payType) {
		this.payType = payType;
	}

	/**
	 * 获取微信支付sn(用于充值)
	 */
	public String getPaySn() {
		return paySn;
	}

	/**
	 * 设置微信支付sn(用于充值)
	 */
	public void setPaySn(String paySn) {
		this.paySn = paySn;
	}

	/**
	 * 获取记录类型（1：收入2：消费）
	 */
	public String getPdType() {
		return pdType;
	}

	/**
	 * 设置记录类型（1：收入2：消费）
	 */
	public void setPdType(String pdType) {
		this.pdType = pdType;
	}

	/**
	 * 获取用户编号
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * 设置用户编号
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * 获取删除标记
	 */
	public String getDeleted() {
		return deleted;
	}

	/**
	 * 设置删除标记
	 */
	public void setDeleted(String deleted) {
		this.deleted = deleted;
	}

	/**
	 * 获取操作时间
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * 设置操作时间
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * 获取付款类型（待定义）
	 */
	public String getFkType() {
		return fkType;
	}

	/**
	 * 设置付款类型（待定义）
	 */
	public void setFkType(String fkType) {
		this.fkType = fkType;
	}

	/**
	 * 获取备注说明
	 */
	public String getRemark() {
		return remark;
	}

	/**
	 * 设置备注说明
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Double getYeAmount() {
		return yeAmount;
	}

	public void setYeAmount(Double yeAmount) {
		this.yeAmount = yeAmount;
	}

	public Integer getBusinessType() {
		return businessType;
	}

	/** 
	 * 业务类型
	 * 1、充值
	 * 2、提现
	 * 3、提现退回
	 * 4、订单消费
	 * 5、悬赏合作
	 * 6、悬赏合作退回
	 * 7、打赏动态
	 * 8、打赏合作
	 * 9、打赏收费
	 * 10、打赏话题
	 * 11、活动收益
	 * 12、获得悬赏
	 * 13、语音付费
	 * 14、语音收费
	 * 15、获得打赏
	 */
	public void setBusinessType(Integer businessType) {
		this.businessType = businessType;
	}

	public String getSourceUser() {
		return sourceUser;
	}

	public void setSourceUser(String sourceUser) {
		this.sourceUser = sourceUser;
	}

	public String getFkId() {
		return fkId;
	}

	public void setFkId(String fkId) {
		this.fkId = fkId;
	}
	
}
