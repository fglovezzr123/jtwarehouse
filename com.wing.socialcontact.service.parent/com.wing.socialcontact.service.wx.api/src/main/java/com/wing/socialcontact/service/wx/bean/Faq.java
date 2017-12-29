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
 * TJY_FAQ 诸葛解惑
 * 
 * @author liangwj
 * @date 2017-03-26
 * @version 1.0
 */
@Table(name = "TJY_FAQ")
public class Faq implements Serializable{
	private static final long serialVersionUID = 1L;

    /** 主键 */
    @Id
	@Column(name = "id")
	@GeneratedValue(generator = "UUID")
	private String id;

    /** 标题 */
	private String titles;

    /** 行业（关联数据字典） */
	private String types;

    /** 悬赏金额 */
	private Double offeredAmount;

    /** 有效开始日期 */
	private Date startTime;

    /** 有效截至日期 */
	private Date endTime;

    /** 是否允许评论,1允许2不允许 */
	private Integer allowComment;

    /** 状态，1审核中2审核通过3审核不通过 */
	private Integer status;

    /** 创建时间 */
	private Date createTime;

    /** 创建用户id */
	private String createUserId;

    /** 创建用户名称 */
	private String createUserName;

    /** 逻辑删除标识 */
	private Integer deleted;
	@Transient
	private Map<String,Object>  extProps = new HashMap<String,Object>();
	public Faq(){}


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
	 * 获取标题
	 */
	public String getTitles() {
    	return titles;
    }
  	
	/**
	 * 设置标题
	 */
	public void setTitles(String titles) {
    	this.titles = titles;
    }

	/**
	 * 获取行业（关联数据字典）
	 */
	public String getTypes() {
    	return types;
    }
  	
	/**
	 * 设置行业（关联数据字典）
	 */
	public void setTypes(String types) {
    	this.types = types;
    }

	/**
	 * 获取悬赏金额
	 */
	public Double getOfferedAmount() {
    	return offeredAmount;
    }
  	
	/**
	 * 设置悬赏金额
	 */
	public void setOfferedAmount(Double offeredAmount) {
    	this.offeredAmount = offeredAmount;
    }

	/**
	 * 获取有效开始日期
	 */
	public Date getStartTime() {
    	return startTime;
    }
  	
	/**
	 * 设置有效开始日期
	 */
	public void setStartTime(Date startTime) {
    	this.startTime = startTime;
    }

	/**
	 * 获取有效截至日期
	 */
	public Date getEndTime() {
    	return endTime;
    }
  	
	/**
	 * 设置有效截至日期
	 */
	public void setEndTime(Date endTime) {
    	this.endTime = endTime;
    }

	/**
	 * 获取是否允许评论,1允许2不允许
	 */
	public Integer getAllowComment() {
    	return allowComment;
    }
  	
	/**
	 * 设置是否允许评论,1允许2不允许
	 */
	public void setAllowComment(Integer allowComment) {
    	this.allowComment = allowComment;
    }

	/**
	 * 获取状态，1审核中2审核通过3审核不通过
	 */
	public Integer getStatus() {
    	return status;
    }
  	
	/**
	 * 设置状态，1审核中2审核通过3审核不通过
	 */
	public void setStatus(Integer status) {
    	this.status = status;
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
	 * 获取创建用户id
	 */
	public String getCreateUserId() {
    	return createUserId;
    }
  	
	/**
	 * 设置创建用户id
	 */
	public void setCreateUserId(String createUserId) {
    	this.createUserId = createUserId;
    }

	/**
	 * 获取创建用户名称
	 */
	public String getCreateUserName() {
    	return createUserName;
    }
  	
	/**
	 * 设置创建用户名称
	 */
	public void setCreateUserName(String createUserName) {
    	this.createUserName = createUserName;
    }

	/**
	 * 获取逻辑删除标识
	 */
	public Integer getDeleted() {
    	return deleted;
    }
  	
	/**
	 * 设置逻辑删除标识
	 */
	public void setDeleted(Integer deleted) {
    	this.deleted = deleted;
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
