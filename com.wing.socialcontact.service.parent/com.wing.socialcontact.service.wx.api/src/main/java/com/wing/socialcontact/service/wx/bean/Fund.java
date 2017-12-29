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
 * TJY_FUND 基金投资信息
 * 
 * @author liangwj
 * @date 2017-03-26
 * @version 1.0
 */
@Table(name = "TJY_FUND")
public class Fund implements Serializable{
	private static final long serialVersionUID = 1L;

    /** 主键 */
    @Id
	@Column(name = "id")
	@GeneratedValue(generator = "UUID")
	private String id;

    /** 类型(关联字典表) */
	private String fundTye;

    /** 描述（html文本） */
	private String fundDesc;

    /** 基金级别 */
	private Integer fundLvl;

    /** 是否推荐 */
	private Integer recommend;

    /** 首页展示 */
	private Integer homeShow;

    /** 状态 */
	private Integer status;

    /** 起始时间 */
	private Date startTime;

    /** 截至时间 */
	private Date endTime;

    /** 创建时间 */
	private Date createTime;

    /** 创建人id */
	private String createUserId;

    /** 创建人名称 */
	private String createUserName;

    /** 逻辑删除标识 */
	private Integer deletd;
	@Transient
	private Map<String,Object>  extProps = new HashMap<String,Object>();
	public Fund(){}


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
	 * 获取类型(关联字典表)
	 */
	public String getFundTye() {
    	return fundTye;
    }
  	
	/**
	 * 设置类型(关联字典表)
	 */
	public void setFundTye(String fundTye) {
    	this.fundTye = fundTye;
    }

	/**
	 * 获取描述（html文本）
	 */
	public String getFundDesc() {
    	return fundDesc;
    }
  	
	/**
	 * 设置描述（html文本）
	 */
	public void setFundDesc(String fundDesc) {
    	this.fundDesc = fundDesc;
    }

	/**
	 * 获取基金级别
	 */
	public Integer getFundLvl() {
    	return fundLvl;
    }
  	
	/**
	 * 设置基金级别
	 */
	public void setFundLvl(Integer fundLvl) {
    	this.fundLvl = fundLvl;
    }

	/**
	 * 获取是否推荐
	 */
	public Integer getRecommend() {
    	return recommend;
    }
  	
	/**
	 * 设置是否推荐
	 */
	public void setRecommend(Integer recommend) {
    	this.recommend = recommend;
    }

	/**
	 * 获取首页展示
	 */
	public Integer getHomeShow() {
    	return homeShow;
    }
  	
	/**
	 * 设置首页展示
	 */
	public void setHomeShow(Integer homeShow) {
    	this.homeShow = homeShow;
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

	/**
	 * 获取起始时间
	 */
	public Date getStartTime() {
    	return startTime;
    }
  	
	/**
	 * 设置起始时间
	 */
	public void setStartTime(Date startTime) {
    	this.startTime = startTime;
    }

	/**
	 * 获取截至时间
	 */
	public Date getEndTime() {
    	return endTime;
    }
  	
	/**
	 * 设置截至时间
	 */
	public void setEndTime(Date endTime) {
    	this.endTime = endTime;
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
	public String getCreateUserId() {
    	return createUserId;
    }
  	
	/**
	 * 设置创建人id
	 */
	public void setCreateUserId(String createUserId) {
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
