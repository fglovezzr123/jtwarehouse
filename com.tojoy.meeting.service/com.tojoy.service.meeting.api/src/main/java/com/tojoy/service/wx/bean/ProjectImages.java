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
 * tjy_project_images 
 * 
 * @author liangwj
 * @version 1.0
 */
@Table(name = "tjy_project_images")
public class ProjectImages implements Serializable{
	private static final long serialVersionUID = 1L;

    /** 主键 */
    @Id
	@Column(name = "id")
	@GeneratedValue(generator = "UUID")
	private String id;

    /** 项目主键 */
	private String projectId;

    /** 图片地址 */
	private String imageUrl;

    /** 创建时间 */
	private Date createTime;

    /** 逻辑删除标识，1删除0不删除 */
	private Integer deleted;
	@Transient
	private Map<String,Object>  extProps = new HashMap<String,Object>();
	public ProjectImages(){}


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
	 * 获取项目主键
	 */
	public String getProjectId() {
    	return projectId;
    }
  	
	/**
	 * 设置项目主键
	 */
	public void setProjectId(String projectId) {
    	this.projectId = projectId;
    }

	/**
	 * 获取图片地址
	 */
	public String getImageUrl() {
    	return imageUrl;
    }
  	
	/**
	 * 设置图片地址
	 */
	public void setImageUrl(String imageUrl) {
    	this.imageUrl = imageUrl;
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
	 * 获取逻辑删除标识，1删除0不删除
	 */
	public Integer getDeleted() {
    	return deleted;
    }
  	
	/**
	 * 设置逻辑删除标识，1删除0不删除
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
