package com.tojoy.service.wx.bean;

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

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSON;

/**
 * TJY_PROJECT 项目信息
 * 
 * @author liangwj
 * @date 2017-03-26
 * @version 1.0
 */
@Table(name = "TJY_PROJECT")
public class Project implements Serializable{
	private static final long serialVersionUID = 1L;

    /**  */
    @Id
	@Column(name = "id")
	@GeneratedValue(generator = "UUID")
	private String id;

    /** 标题 */
	private String titles;
	
	/** 标题2 */
	private String titles2;
	
	/** 微吼会议id */
	private String webinarId;
	
	/** 项目封面图地址 */
	private String coverImg;
	
    /** 项目类型 */
	private String prjType;

    /** 项目图片地址 */
	private String prjPoster;

    /** 融资主体 */
	private String financingSubject;

    /** 融资用途 */
	private String financingPurpose;

    /** 可提供资料(多个时用逗号隔开) */
	private String availableData;

    /** 所在地区 */
	private String region;

    /** 所属行业 */
	private String industry;

    /** 融资金额 */
	private Double financingAmount;

    /** 融资方式(多个时用逗号隔开) */
	private String financingType;
	
	/** 项目介绍 */
	private String prjPres;

    /** 项目概述 */
	private String prjDesc;

    /** 项目起始时间 */
	private Date startTime;

    /** 项目结束时间 */
	private Date endTime;

    /** 项目状态 */
	private Integer status;

    /** 关联的自荐项目(预留) */
	private Integer recommendId;

    /** 创建时间 */
	private Date createTime;

    /** 创建人id */
	private Integer createUserId;

    /** 创建人名称 */
	private String createUserName;
	/** 是否审核 */
	private Integer isApl;
	/** 是否推荐 */
	private Integer isRecommend;
	/** 是否发布 */
	private Integer showEnable;

    /** 逻辑删除标志，1删除0未删除 */
	private Integer deleted;
	
	/** 联系电话 */
	private String mobiles;
	
	/** 联系电话 */
	@Transient
	private List<String> mobileList = new ArrayList<String>();

    /** 项目状态描述 */
	@Transient
	private String statusDesc;
	
	/** 微吼会议主题 */
	private String webinarSubject;
	
	/** 权重 */
	private Integer sort;
	
	/** 项目类型名称 */
	@Transient
	private String prjTypeName;
	@Transient
	private Map<String,Object>  extProps = new HashMap<String,Object>();
	
	@Transient
	private String images;
	@Transient
	private List<ProjectImages> projectImages = new ArrayList<ProjectImages>();
	@Transient
	private String userId;
	@Transient
	private Integer isWilled;
	@Transient
	private Integer isAttentioned;
	@Transient
	private String orderBy;
	
	
	/** 项目起始时间  查询条件*/
	@Transient
	private Date gtStartTime;//大于
	@Transient
	private Date geStartTime;//大于等于
	@Transient
	private Date ltStartTime;//小于
	@Transient
	private Date leStartTime;//小于等于

    /** 项目结束时间  查询条件*/
	@Transient
	private Date gtEndTime;//大于
	public String getPrjPres() {
		return prjPres;
	}

	public void setPrjPres(String prjPres) {
		this.prjPres = prjPres;
	}

	@Transient
	private Date geEndTime;//大于等于
	@Transient
	private Date ltEndTime;//小于
	@Transient
	private Date leEndTime;//小于等于
	
	public Project(){}

	
	
	
	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

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
	 * 获取项目类型
	 */
	public String getPrjType() {
    	return prjType;
    }
  	
	/**
	 * 设置项目类型
	 */
	public void setPrjType(String prjType) {
    	this.prjType = prjType;
    	this.prjTypeName = "8006001".equals(this.prjType)?"项目联营":("8006002".equals(this.prjType)?"项目融资":"项目联营");
    }

	/**
	 * 获取项目图片地址
	 */
	public String getPrjPoster() {
    	return prjPoster;
    }
  	
	/**
	 * 设置项目图片地址
	 */
	public void setPrjPoster(String prjPoster) {
    	this.prjPoster = prjPoster;
    }

	/**
	 * 获取融资主体
	 */
	public String getFinancingSubject() {
    	return financingSubject;
    }
  	
	/**
	 * 设置融资主体
	 */
	public void setFinancingSubject(String financingSubject) {
    	this.financingSubject = financingSubject;
    }

	/**
	 * 获取融资用途
	 */
	public String getFinancingPurpose() {
    	return financingPurpose;
    }
  	
	/**
	 * 设置融资用途
	 */
	public void setFinancingPurpose(String financingPurpose) {
    	this.financingPurpose = financingPurpose;
    }

	/**
	 * 获取可提供资料(多个时用逗号隔开)
	 */
	public String getAvailableData() {
    	return availableData;
    }
  	
	/**
	 * 设置可提供资料(多个时用逗号隔开)
	 */
	public void setAvailableData(String availableData) {
    	this.availableData = availableData;
    }

	/**
	 * 获取所在地区
	 */
	public String getRegion() {
    	return region;
    }
  	
	/**
	 * 设置所在地区
	 */
	public void setRegion(String region) {
    	this.region = region;
    }

	/**
	 * 获取所属行业
	 */
	public String getIndustry() {
    	return industry;
    }
  	
	/**
	 * 设置所属行业
	 */
	public void setIndustry(String industry) {
    	this.industry = industry;
    }

	/**
	 * 获取融资金额
	 */
	public Double getFinancingAmount() {
    	return financingAmount;
    }
  	
	/**
	 * 设置融资金额
	 */
	public void setFinancingAmount(Double financingAmount) {
    	this.financingAmount = financingAmount;
    }

	/**
	 * 获取融资方式(多个时用逗号隔开)
	 */
	public String getFinancingType() {
    	return financingType;
    }
  	
	/**
	 * 设置融资方式(多个时用逗号隔开)
	 */
	public void setFinancingType(String financingType) {
    	this.financingType = financingType;
    }

	/**
	 * 获取项目概述
	 */
	public String getPrjDesc() {
    	return prjDesc;
    }
  	
	public String getStatusDesc() {
		return statusDesc;
	}


	public void setStatusDesc(String statusDesc) {
		this.statusDesc = statusDesc;
	}


	/**
	 * 设置项目概述
	 */
	public void setPrjDesc(String prjDesc) {
    	this.prjDesc = prjDesc;
    }

	/**
	 * 获取项目起始时间
	 */
	public Date getStartTime() {
    	return startTime;
    }
  	
	/**
	 * 设置项目起始时间
	 */
	public void setStartTime(Date startTime) {
    	this.startTime = startTime;
    	if(this.startTime!=null&&this.endTime!=null){
    		if(this.startTime.compareTo(new Date())>-1){
    			this.statusDesc = "新项目";
    		}else if(this.endTime.compareTo(new Date())==-1){
    			this.statusDesc = "已结束";
    		}else{
    			this.statusDesc = "招募中";
    		}
    	}else{
    		this.statusDesc = "";
    	}
    	
    }

	/**
	 * 获取项目结束时间
	 */
	public Date getEndTime() {
    	return endTime;
    }
  	
	/**
	 * 设置项目结束时间
	 */
	public void setEndTime(Date endTime) {
    	this.endTime = endTime;
    	if(this.startTime!=null&&this.endTime!=null){
    		if(this.startTime.compareTo(new Date())>-1){
    			this.statusDesc = "新项目";
    		}else if(this.endTime.compareTo(new Date())==-1){
    			this.statusDesc = "已结束";
    		}else{
    			this.statusDesc = "招募中";
    		}
    	}else{
    		this.statusDesc = "";
    	}
    }

	/**
	 * 获取项目状态
	 */
	public Integer getStatus() {
    	return status;
    }
  	
	/**
	 * 设置项目状态
	 */
	public void setStatus(Integer status) {
    	this.status = status;
    }

	/**
	 * 获取关联的自荐项目(预留)
	 */
	public Integer getRecommendId() {
    	return recommendId;
    }
  	
	/**
	 * 设置关联的自荐项目(预留)
	 */
	public void setRecommendId(Integer recommendId) {
    	this.recommendId = recommendId;
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
	 * 获取逻辑删除标志，1删除0未删除
	 */
	public Integer getDeleted() {
    	return deleted;
    }
  	
	/**
	 * 设置逻辑删除标志，1删除0未删除
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


	public Integer getIsApl() {
		return isApl;
	}


	public void setIsApl(Integer isApl) {
		this.isApl = isApl;
	}


	public Integer getIsRecommend() {
		return isRecommend;
	}


	public void setIsRecommend(Integer isRecommend) {
		this.isRecommend = isRecommend;
	}


	public Integer getShowEnable() {
		return showEnable;
	}


	public void setShowEnable(Integer showEnable) {
		this.showEnable = showEnable;
	}
	
	public List<ProjectImages> getProjectImages() {
		return projectImages;
	}


	public void setProjectImages(List<ProjectImages> projectImages) {
		if(projectImages==null){
			this.projectImages.clear();
		}else{
			this.projectImages = projectImages;
		}
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
		return this.projectImages;
	}


	public String getCoverImg() {
		return coverImg;
	}


	public void setCoverImg(String coverImg) {
		this.coverImg = coverImg;
	}


	public String getImages() {
		return images;
	}


	public void setImages(String images) {
		this.images = images;
	}


	public String getPrjTypeName() {
		return prjTypeName;
	}


	public void setPrjTypeName(String prjTypeName) {
		this.prjTypeName = "8006001".equals(this.prjType)?"项目联营":("8006002".equals(this.prjType)?"项目融资":"项目联营");
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	public Integer getIsWilled() {
		return isWilled;
	}

	public void setIsWilled(Integer isWilled) {
		this.isWilled = isWilled;
	}

	public Integer getIsAttentioned() {
		return isAttentioned;
	}

	public void setIsAttentioned(Integer isAttentioned) {
		this.isAttentioned = isAttentioned;
	}

	public String getMobiles() {
		return mobiles;
	}

	public void setMobiles(String mobiles) {
		mobileList.clear();
		this.mobiles = mobiles;
		if(StringUtils.isNotBlank(this.mobiles)){
			String[] arr = this.mobiles.split(";");
			for(String s : arr){
				if(StringUtils.isNotBlank(s)){
					mobileList.add(s);
				}
			}
		}
	}

	public List<String> getMobileList() {
		return mobileList;
	}

	public void setMobileList(List<String> mobileList) {
	}

	public String getTitles2() {
		return titles2;
	}

	public void setTitles2(String titles2) {
		this.titles2 = titles2;
	}

	public String getWebinarId() {
		return webinarId;
	}

	public void setWebinarId(String webinarId) {
		this.webinarId = webinarId;
	}

	public Date getGtStartTime() {
		return gtStartTime;
	}

	public void setGtStartTime(Date gtStartTime) {
		this.gtStartTime = gtStartTime;
	}

	public Date getGeStartTime() {
		return geStartTime;
	}

	public void setGeStartTime(Date geStartTime) {
		this.geStartTime = geStartTime;
	}

	public Date getLtStartTime() {
		return ltStartTime;
	}

	public void setLtStartTime(Date ltStartTime) {
		this.ltStartTime = ltStartTime;
	}

	public Date getLeStartTime() {
		return leStartTime;
	}

	public void setLeStartTime(Date leStartTime) {
		this.leStartTime = leStartTime;
	}

	public Date getGtEndTime() {
		return gtEndTime;
	}

	public void setGtEndTime(Date gtEndTime) {
		this.gtEndTime = gtEndTime;
	}

	public Date getGeEndTime() {
		return geEndTime;
	}

	public void setGeEndTime(Date geEndTime) {
		this.geEndTime = geEndTime;
	}

	public Date getLtEndTime() {
		return ltEndTime;
	}

	public void setLtEndTime(Date ltEndTime) {
		this.ltEndTime = ltEndTime;
	}

	public Date getLeEndTime() {
		return leEndTime;
	}

	public void setLeEndTime(Date leEndTime) {
		this.leEndTime = leEndTime;
	}

	public String getWebinarSubject() {
		return webinarSubject;
	}

	public void setWebinarSubject(String webinarSubject) {
		this.webinarSubject = webinarSubject;
	}
	
}
