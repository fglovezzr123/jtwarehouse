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
 * tjy_keywords 关键词搜索
 * 
 * @author liangwj
 * @version 1.0
 */
@Table(name = "tjy_keywords")
public class Keywords implements Serializable{
	private static final long serialVersionUID = 1L;

    /** 主键 */
    @Id
	@Column(name = "id")
	@GeneratedValue(generator = "UUID")
	private String id;

    /** 用户ID */
	private String userId;

    /** 关键词 */
	private String keywords;

    /** 搜索类型，1会议2项目联营3项目征集 */
	private Integer types;

    /** 创建日期 */
	private Date createTime;

	@Transient 
    private Date gtcreateTime;
    
    @Transient
    private Date gecreateTime;
    
    @Transient
    private Date ltcreateTime;
    
    @Transient
    private Date lecreateTime;
	
	@Transient
	private Map<String,Object>  extProps = new HashMap<String,Object>();
	public Keywords(){}


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
	 * 获取用户ID
	 */
	public String getUserId() {
    	return userId;
    }
  	
	/**
	 * 设置用户ID
	 */
	public void setUserId(String userId) {
    	this.userId = userId;
    }

    

	/**
	 * 获取关键词
	 */
	public String getKeywords() {
    	return keywords;
    }
  	
	/**
	 * 设置关键词
	 */
	public void setKeywords(String keywords) {
    	this.keywords = keywords;
    }

    

	/**
	 * 获取搜索类型，1会议2项目联营3项目征集
	 */
	public Integer getTypes() {
    	return types;
    }
  	
	/**
	 * 设置搜索类型，1会议2项目联营3项目征集
	 */
	public void setTypes(Integer types) {
    	this.types = types;
    }

    

	/**
	 * 获取创建日期
	 */
	public Date getCreateTime() {
    	return createTime;
    }
  	
	/**
	 * 设置创建日期
	 */
	public void setCreateTime(Date createTime) {
    	this.createTime = createTime;
    }

	public Date getGtcreateTime() {
    	return gtcreateTime;
    }
    
    public void setGtcreateTime(Date gtcreateTime) {
    	this.gtcreateTime = gtcreateTime;
    }
    
    public Date getGecreateTime() {
    	return createTime;
    }
    
    public void setGecreateTime(Date gecreateTime) {
    	this.gecreateTime = gecreateTime;
    }
    
    public Date getLtcreateTime() {
    	return ltcreateTime;
    }
    
    public void setLtcreateTime(Date ltcreateTime) {
    	this.ltcreateTime = ltcreateTime;
    }
    
    public Date getLecreateTime() {
    	return lecreateTime;
    }
    
    public void setLecreateTime(Date lecreateTime) {
    	this.lecreateTime = lecreateTime;
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
