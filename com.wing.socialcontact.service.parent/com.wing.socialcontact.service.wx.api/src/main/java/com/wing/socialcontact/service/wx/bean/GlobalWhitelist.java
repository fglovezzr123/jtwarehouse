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
 * tjy_global_whitelist 白名单设置
 * 
 * @author liangwj
 * @version 1.0
 */
@Table(name = "tjy_global_whitelist")
public class GlobalWhitelist implements Serializable{
	private static final long serialVersionUID = 1L;

    /** 主键 */
    @Id
	@Column(name = "id")
	@GeneratedValue(generator = "UUID")
	private String id;

    /** 白名单类型 */
	private String types;

    /** 用户ID */
	private String userId;

    /** 创建时间 */
	private Date createTime;

	@Transient 
    private Date gtcreateTime;
    
    @Transient
    private Date gecreateTime;
    
    @Transient
    private Date ltcreateTime;
    
    @Transient
    private Date lecreateTime;
    /** 用户名 */
    @Transient
	private String userName;
    /** 用户所在公司 */
    @Transient
    private String comName;
    /** 用户手机号 */
    @Transient
    private String mobile;
    /** 昵称 */
    @Transient
    private String nickname;
	
	@Transient
	private Map<String,Object>  extProps = new HashMap<String,Object>();
	public GlobalWhitelist(){}

	public GlobalWhitelist(String userId,String types){
		this.userId = userId;
		this.types = types;
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
	 * 获取白名单类型
	 */
	public String getTypes() {
    	return types;
    }
  	
	/**
	 * 设置白名单类型
	 */
	public void setTypes(String types) {
    	this.types = types;
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
	 * 获取用户名
	 */
	public String getUserName() {
    	return userName;
    }
  	
	/**
	 * 设置用户名
	 */
	public void setUserName(String userName) {
    	this.userName = userName;
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
    
	public String getComName() {
		return comName;
	}


	public void setComName(String comName) {
		this.comName = comName;
	}


	public String getMobile() {
		return mobile;
	}


	public void setMobile(String mobile) {
		this.mobile = mobile;
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

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
}
