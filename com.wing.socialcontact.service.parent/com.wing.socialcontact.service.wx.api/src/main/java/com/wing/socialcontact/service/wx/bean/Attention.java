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
 * TJY_ATTENTION 关注表信息表
 * 
 * @author liangwj
 * @date 2017-03-26
 * @version 1.0
 */
@Table(name = "TJY_ATTENTION")
public class Attention implements Serializable{
	private static final long serialVersionUID = 1L;

    /** 主键 */
    @Id
	@Column(name = "id")
	@GeneratedValue(generator = "UUID")
	private String id;

    /** 关注人id */
	private String userId;

    /** 关注类别对应表的主键(如话题表id) */
	private String fkId;

    /** 关注类别(如话题pk) */
	private String attType;

    /** 创建时间 */
	private Date createTime;

    /** 逻辑删除标识 */
	private Integer deleted;
	@Transient
	private Map<String,Object>  extProps = new HashMap<String,Object>();
	public Attention(){}

	public Attention(String id){
		this.id=id;
	}

	public Attention(String userId,String fkId){
		this.userId=userId;
		this.fkId=fkId;
	}
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
	 * 获取关注人id
	 */
	public String getUserId() {
    	return userId;
    }
  	
	/**
	 * 设置关注人id
	 */
	public void setUserId(String userId) {
    	this.userId = userId;
    }

	/**
	 * 获取关注类别对应表的主键(如话题表id)
	 */
	public String getFkId() {
    	return fkId;
    }
  	
	/**
	 * 设置关注类别对应表的主键(如话题表id)
	 */
	public void setFkId(String fkId) {
    	this.fkId = fkId;
    }

	/**
	 * 获取关注类别(如话题pk)
	 */
	public String getAttType() {
    	return attType;
    }
  	
	/**
	 * 设置关注类别(如话题pk)
	 */
	public void setAttType(String attType) {
    	this.attType = attType;
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
