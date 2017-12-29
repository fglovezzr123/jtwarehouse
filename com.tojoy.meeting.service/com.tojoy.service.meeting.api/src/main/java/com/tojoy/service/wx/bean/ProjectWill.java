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
 * TJY_PROJECT_WILL 项目合作意向表
 * 
 * @author liangwj
 * @date 2017-03-26
 * @version 1.0
 */
@Table(name = "TJY_PROJECT_WILL")
public class ProjectWill implements Serializable{
	private static final long serialVersionUID = 1L;

    /** 主键 */
    @Id
	@Column(name = "id")
	@GeneratedValue(generator = "UUID")
	private String id;

    /** 项目id */
	private String prjId;
	
	/** 项目名称 */
	@Transient
	private String prjName;

    /** 用户id */
	private String userId;
	
	/** 咨询类型（关联字典表） */
	private String willType;
	
	/** 咨询类型（关联字典表） */
	@Transient
	private String willTypeName;

    /** 合作意向说明 */
	private String willDesc;

    /** 姓名 */
	private String linkman;

    /** 联系方式 */
	private String linkphone;

    /** 状态 */
	private Integer status;

    /** 创建时间 */
	private Date createTime;

    /** 创建人id */
	private Integer createUserId;

    /** 创建人名称 */
	private String createUserName;

	 /** 项目主题 */
	@Transient
	private String titles;
	/** 用户名 */
	@Transient
	private String userName;
	/** 联系方式 */
	@Transient
	private String mobile;
	
    /** 逻辑删除标识 */
	private Integer deleted;
	/**公司名称*/
	@Transient
	private String comName; 
	
	@Transient
	private Map<String,Object>  extProps = new HashMap<String,Object>();
	public ProjectWill(){}

	public ProjectWill(String id, String prjId){
		this.id=id;
		this.prjId=prjId;
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
	 * 获取项目id
	 */
	public String getPrjId() {
    	return prjId;
    }
  	
	/**
	 * 设置项目id
	 */
	public void setPrjId(String prjId) {
    	this.prjId = prjId;
    }

	/**
	 * 获取用户id
	 */
	public String getUserId() {
    	return userId;
    }
  	
	/**
	 * 设置用户id
	 */
	public void setUserId(String userId) {
    	this.userId = userId;
    }

	/**
	 * 获取合作意向说明
	 */
	public String getWillDesc() {
    	return willDesc;
    }
  	
	/**
	 * 设置合作意向说明
	 */
	public void setWillDesc(String willDesc) {
    	this.willDesc = willDesc;
    }

	/**
	 * 获取姓名
	 */
	public String getLinkman() {
    	return linkman;
    }
  	
	/**
	 * 设置姓名
	 */
	public void setLinkman(String linkman) {
    	this.linkman = linkman;
    }

	/**
	 * 获取联系方式
	 */
	public String getLinkphone() {
    	return linkphone;
    }
  	
	/**
	 * 设置联系方式
	 */
	public void setLinkphone(String linkphone) {
    	this.linkphone = linkphone;
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

	public String getWillTypeName() {
		return willTypeName;
	}

	public void setWillTypeName(String willTypeName) {
		this.willTypeName = willTypeName;
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
	
	public String getTitles() {
		return titles;
	}

	public void setTitles(String titles) {
		this.titles = titles;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	@SuppressWarnings("unchecked")
	public <T> T getExtProp(String key) {
		return (T) extProps.get(key);
	}
	
	public void setExtProp(String key ,Object value) {
		extProps.put(key, value);
	}

	public String getComName() {
		return comName;
	}

	public void setComName(String comName) {
		this.comName = comName;
	}

	public String getPrjName() {
		return prjName;
	}

	public void setPrjName(String prjName) {
		this.prjName = prjName;
	}

	public String getWillType() {
		return willType;
	}

	public void setWillType(String willType) {
		this.willType = willType;
	}
}
