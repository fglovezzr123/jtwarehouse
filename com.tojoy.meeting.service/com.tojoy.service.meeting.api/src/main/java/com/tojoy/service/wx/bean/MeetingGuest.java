package com.tojoy.service.wx.bean;

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
 * TJY_MEETING_GUEST 会议邀请的嘉宾
 * 
 * @author liangwj
 * @date 2017-04-04 00:05:28
 * @version 1.0
 */
@Table(name = "TJY_MEETING_GUEST")
public class MeetingGuest implements Serializable{
	private static final long serialVersionUID = 1L;

    /**  */
    @Id
	@Column(name = "id")
	@GeneratedValue(generator = "UUID")
	private String id;

    /**  */
	private String meetingId;

    /**  */
	private String imgUrl;

    /**  */
	private String name;

    /**  */
	private Date createTime;

	/**排序*/
	private Integer sort;

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	@Transient
	private Map<String,Object>  extProps = new HashMap<String,Object>();
	public MeetingGuest(){}


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
	 * 获取
	 */
	public String getMeetingId() {
    	return meetingId;
    }
  	
	/**
	 * 设置
	 */
	public void setMeetingId(String meetingId) {
    	this.meetingId = meetingId;
    }

	/**
	 * 获取
	 */
	public String getImgUrl() {
    	return imgUrl;
    }
  	
	/**
	 * 设置
	 */
	public void setImgUrl(String imgUrl) {
    	this.imgUrl = imgUrl;
    }

	/**
	 * 获取
	 */
	public String getName() {
    	return name;
    }
  	
	/**
	 * 设置
	 */
	public void setName(String name) {
    	this.name = name;
    }

	/**
	 * 获取
	 */
	public Date getCreateTime() {
    	return createTime;
    }
  	
	/**
	 * 设置
	 */
	public void setCreateTime(Date createTime) {
    	this.createTime = createTime;
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
