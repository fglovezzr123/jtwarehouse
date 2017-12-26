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
 * tjy_meeting_signup_remind 预报名提醒
 * 
 * @author liangwj
 * @version 1.0
 */
@Table(name = "tjy_meeting_signup_remind")
public class MeetingSignupRemind implements Serializable{
	private static final long serialVersionUID = 1L;

    /** 主键 */
    @Id
	@Column(name = "id")
	@GeneratedValue(generator = "UUID")
	private String id;

    /** 关联业主主键 */
	private String fkId;

    /** 用户ID */
	private String userId;

    /** 提醒时间 */
	private String remindTime;

    /** 提醒内容 */
	private String contents;

    /** 是否已提醒1已提醒0为提醒 */
	private Integer isRemind;
	
	/** 会议主题 */
	@Transient
	private String titles;
	
	@Transient
	private String openId;
	
	@Transient
	private String wxUserId;
	
	@Transient
	private String trueName;
	
	@Transient
	private Date endSignupTime;
	@Transient
	private Date startSignupTime;
	/**手机号*/
	@Transient
	private String mobile;
	/**公司*/
	@Transient
	private String comName;
	/**职位*/
	@Transient
	private String job;
	/**地区*/
	@Transient
	private String region;
	
	@Transient
	private Map<String,Object>  extProps = new HashMap<String,Object>();
	public MeetingSignupRemind(){
		super();
	}
	
	public MeetingSignupRemind(String fkId,String userId, Integer isRemind){
		this();
		this.fkId = fkId;
		this.userId = userId;
		this.isRemind = isRemind;
	}

	
	public Date getStartSignupTime() {
		return startSignupTime;
	}

	public void setStartSignupTime(Date startSignupTime) {
		this.startSignupTime = startSignupTime;
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
	 * 获取关联业主主键
	 */
	public String getFkId() {
    	return fkId;
    }
  	
	/**
	 * 设置关联业主主键
	 */
	public void setFkId(String fkId) {
    	this.fkId = fkId;
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
	 * 获取提醒时间
	 */
	public String getRemindTime() {
    	return remindTime;
    }
  	
	/**
	 * 设置提醒时间
	 */
	public void setRemindTime(String remindTime) {
    	this.remindTime = remindTime;
    }

	/**
	 * 获取提醒内容
	 */
	public String getContents() {
    	return contents;
    }
  	
	/**
	 * 设置提醒内容
	 */
	public void setContents(String contents) {
    	this.contents = contents;
    }

    

	/**
	 * 获取是否已提醒1已提醒0为提醒
	 */
	public Integer getIsRemind() {
    	return isRemind;
    }
  	
	/**
	 * 设置是否已提醒1已提醒0为提醒
	 */
	public void setIsRemind(Integer isRemind) {
    	this.isRemind = isRemind;
    }

    
	public String getTitles() {
		return titles;
	}

	public void setTitles(String titles) {
		this.titles = titles;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getWxUserId() {
		return wxUserId;
	}

	public void setWxUserId(String wxUserId) {
		this.wxUserId = wxUserId;
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

	public String getTrueName() {
		return trueName;
	}

	public void setTrueName(String trueName) {
		this.trueName = trueName;
	}

	public Date getEndSignupTime() {
		return endSignupTime;
	}

	public void setEndSignupTime(Date endSignupTime) {
		this.endSignupTime = endSignupTime;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getComName() {
		return comName;
	}

	public void setComName(String comName) {
		this.comName = comName;
	}

	public String getJob() {
		return job;
	}

	public void setJob(String job) {
		this.job = job;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}
}
