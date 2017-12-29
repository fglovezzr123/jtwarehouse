package com.wing.socialcontact.service.wx.bean;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * TJY_FUND_WILL 基金投资意向
 * 
 * @author liangwj
 * @date 2017-03-26
 * @version 1.0
 */
@Table(name = "TJY_FUND_WILL")
public class FundWill implements Serializable{
	private static final long serialVersionUID = 1L;

    /** 主键 */
    @Id
	@Column(name = "id")
	@GeneratedValue(generator = "UUID")
	private String id;

    /** 投资主表id */
	private String fundId;

    /** 投资兴趣（多个时用逗号隔开） */
	private String favs;

    /** 投资额度（元） */
	private Double investAmount;

    /** 投资意向留言 */
	private String willDesc;

    /** 创建时间 */
	private Date createTime;

    /** 创建人id */
	private Integer createUserId;

    /** 创建人名称 */
	private String createUserName;

    /** 逻辑删除标识 */
	private Integer deletd;

    /** 状态 */
	private Integer status;
	@Transient
	private Map<String,Object>  extProps = new HashMap<String,Object>();
	public FundWill(){}


	/**
	 * 获取主键
	 */
	public String getId() {
    	return id;
    }
  	
	/**
	 * 设置主键
	 */
	public void setId(String id) {
    	this.id = id;
    }

	/**
	 * 获取投资主表id
	 */
	public String getFundId() {
    	return fundId;
    }
  	
	/**
	 * 设置投资主表id
	 */
	public void setFundId(String fundId) {
    	this.fundId = fundId;
    }

	/**
	 * 获取投资兴趣（多个时用逗号隔开）
	 */
	public String getFavs() {
    	return favs;
    }
  	
	/**
	 * 设置投资兴趣（多个时用逗号隔开）
	 */
	public void setFavs(String favs) {
    	this.favs = favs;
    }

	/**
	 * 获取投资额度（元）
	 */
	public Double getInvestAmount() {
    	return investAmount;
    }
  	
	/**
	 * 设置投资额度（元）
	 */
	public void setInvestAmount(Double investAmount) {
    	this.investAmount = investAmount;
    }

	/**
	 * 获取投资意向留言
	 */
	public String getWillDesc() {
    	return willDesc;
    }
  	
	/**
	 * 设置投资意向留言
	 */
	public void setWillDesc(String willDesc) {
    	this.willDesc = willDesc;
    }

	/**
	 * 获取创建时间
	 */
	public Date getCreateTime() {
    	return createTime;
    }
  	
	/**
	 * 设置创建时间
	 */
	public void setCreateTime(Date createTime) {
    	this.createTime = createTime;
    }

	/**
	 * 获取创建人id
	 */
	public Integer getCreateUserId() {
    	return createUserId;
    }
  	
	/**
	 * 设置创建人id
	 */
	public void setCreateUserId(Integer createUserId) {
    	this.createUserId = createUserId;
    }

	/**
	 * 获取创建人名称
	 */
	public String getCreateUserName() {
    	return createUserName;
    }
  	
	/**
	 * 设置创建人名称
	 */
	public void setCreateUserName(String createUserName) {
    	this.createUserName = createUserName;
    }

	/**
	 * 获取逻辑删除标识
	 */
	public Integer getDeletd() {
    	return deletd;
    }
  	
	/**
	 * 设置逻辑删除标识
	 */
	public void setDeletd(Integer deletd) {
    	this.deletd = deletd;
    }

	/**
	 * 获取状态
	 */
	public Integer getStatus() {
    	return status;
    }
  	
	/**
	 * 设置状态
	 */
	public void setStatus(Integer status) {
    	this.status = status;
    }
	public Map<String, Object> getExtProps() {
		return extProps;
	}

	public void setExtProps(Map<String, Object> extProps) {
		if(extProps==null){
			this.extProps.clear();
		}else{
			this.extProps = extProps;
		}
	}
	
	@SuppressWarnings("unchecked")
	public <T> T getExtProp(String key) {
		return (T) extProps.get(key);
	}
	
	public void setExtProp(String key ,Object value) {
		extProps.put(key, value);
	}
}
