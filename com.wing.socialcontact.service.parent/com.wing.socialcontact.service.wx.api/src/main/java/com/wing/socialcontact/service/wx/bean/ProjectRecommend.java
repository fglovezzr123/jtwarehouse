package com.wing.socialcontact.service.wx.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.alibaba.fastjson.JSON;

/**
 * tjy_project_recommend 用户自荐项目
 * 
 * @author liangwj
 * @date 2017-03-26
 * @version 1.0
 */
@Table(name = "tjy_project_recommend")
public class ProjectRecommend implements Serializable{
	private static final long serialVersionUID = 1L;

    /** 逐渐 */
    @Id
	@Column(name = "id")
	@GeneratedValue(generator = "UUID")
	private String id;

    /** 推荐项目类别（关联字典表） */
	private String prjType;
	
	/** 推荐项目类别（关联字典表） */
	private String prjTypeName;

    /** 项目名称 */
	private String prjName;
	
	/** 项目图片 */
	private String imgUrl;
	
	/** 企业名称 */
	private String comName;

    /** 注册资本 */
	private Double registeredCapital;
	
	/** 认证企业 */
	private String authCom;
	
	/** 项目描述 */
	private String prjDesc;

    /** 项目简介 */
	private String prjProfile;

    /** 用户id */
	private String userId;
	
	/** 用户名 */
	@Transient
	private String userName;
	
	/** 联系方式 */
	@Transient
	private String mobile;
	
	/** 是否显示 */
	private Integer showEnable;

    /** 创建时间 */
	private Date createTime;

    /** 状态   0未通过   1通过   2 未审核*/
	private Integer status;

    /** 逻辑删除标识:1删除0未删除 */
	private Integer deleted;
	@Transient
	private Map<String,Object>  extProps = new HashMap<String,Object>();
	@Transient
	private String images;
	@Transient
	private List<ProjectImages> projectImages = new ArrayList<ProjectImages>();
	
	 @Transient
    private String kfTelephone;
    
    
    public String getKfTelephone() {
		return kfTelephone;
	}
	public void setKfTelephone(String kfTelephone) {
		this.kfTelephone = kfTelephone;
	}
		
	
	public ProjectRecommend(){}


	/**
	 * 获取逐渐
	 */
	public String getId() {
    	return id;
    }
  	
	/**
	 * 设置逐渐
	 */
	public void setId(String id) {
    	this.id = id;
    }

	/**
	 * 获取推荐项目类别（关联字典表）
	 */
	public String getPrjType() {
    	return prjType;
    }
  	
	/**
	 * 设置推荐项目类别（关联字典表）
	 */
	public void setPrjType(String prjType) {
    	this.prjType = prjType;
    }

	/**
	 * 获取企业名称
	 */
	public String getComName() {
    	return comName;
    }
  	
	/**
	 * 设置企业名称
	 */
	public void setComName(String comName) {
    	this.comName = comName;
    }

	/**
	 * 获取注册资本
	 */
	public Double getRegisteredCapital() {
    	return registeredCapital;
    }
  	
	/**
	 * 设置注册资本
	 */
	public void setRegisteredCapital(Double registeredCapital) {
    	this.registeredCapital = registeredCapital;
    }

	/**
	 * 获取项目简介
	 */
	public String getPrjProfile() {
    	return prjProfile;
    }
  	
	/**
	 * 设置项目简介
	 */
	public void setPrjProfile(String prjProfile) {
    	this.prjProfile = prjProfile;
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
	 * 获取逻辑删除标识:1删除0未删除
	 */
	public Integer getDeleted() {
    	return deleted;
    }
  	
	/**
	 * 设置逻辑删除标识:1删除0未删除
	 */
	public void setDeleted(Integer deleted) {
    	this.deleted = deleted;
    }
	public String getPrjName() {
		return prjName;
	}


	public void setPrjName(String prjName) {
		this.prjName = prjName;
	}


	public String getImgUrl() {
		return imgUrl;
	}


	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
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


	public String getPrjTypeName() {
		return prjTypeName;
	}


	public void setPrjTypeName(String prjTypeName) {
		this.prjTypeName = prjTypeName;
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


	public Integer getShowEnable() {
		return showEnable;
	}


	public void setShowEnable(Integer showEnable) {
		this.showEnable = showEnable;
	}


	public String getImages() {
		return images;
	}


	public void setImages(String images) {
		this.images = images;
		parse();
	}


	public List<ProjectImages> getProjectImages() {
		return projectImages;
	}


	public void setProjectImages(List<ProjectImages> projectImages) {
		this.projectImages = projectImages;
	}
	
	public String getAuthCom() {
		return authCom;
	}


	public void setAuthCom(String authCom) {
		this.authCom = authCom;
	}


	public String getPrjDesc() {
		return prjDesc;
	}


	public void setPrjDesc(String prjDesc) {
		this.prjDesc = prjDesc;
	}


	public List<ProjectImages> parse(){
		this.projectImages.clear();
		
		if(this.images!=null&&this.images.trim().length()>0){
			List<ProjectImages> list = JSON.parseArray(this.images,ProjectImages.class);
			for(ProjectImages g : list){
				g.setProjectId(this.id);
				g.setCreateTime(new Date());
			}
			this.projectImages = list;
		}
		this.imgUrl = this.projectImages.size()>0?this.projectImages.get(0).getImageUrl():"";
		return this.projectImages;
	}
}
