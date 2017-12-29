package com.wing.socialcontact.service.wx.bean;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TJY_DYNAMIC 用户动态主表
 * 
 * @author xuxinyuan
 * @date 2017-04-17 09:54:05
 * @version 1.0
 */
@Table(name = "TJY_DYNAMIC")
public class Dynamic implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

    /** 主键 */
    @Id
	@Column(name = "id")
	@GeneratedValue(generator = "UUID")
	private String id;

    /** 用户ID */
	private String userId;

    /** 发布时间 */
	private Date issuedDate;
	
	/** 新增时间 */
	private Date createTime;

    /** 动态文字内容 */
	private String dyContent;

    /** 语音id */
	private String mediaId;

    /** 语音价格 */
	private Long mediaPrice;
	
	/**媒体时长*/
	private Integer mediaSeconds;

    /** 访问量 */
	private Long visitQuantity;

    /** 访问类型：1好友可见 2、所有人可见 */
	private Integer visitType;
	
	private String visitUrl;
	/**是否允许打赏*/
	private Integer allowReword;
	/**是否允许评论*/
	private Integer allowComment;
    /** 状态,0、已删除、1已公布 */
	private Integer status;
	//动态广告的url
	private String adUrl;
	//动态类型（0：动态 1：动态广告）
	private String dyType;
	//文章id
	private String articleid;
	//文章标题
	private String atitle;
	//文章图片路径
	private String aimgpath;
	//文章分类名称
	private String aclassname;
	//文章时间
	private Date adate;
	//是否置顶
	private int isStick;
	
	

	public int getIsStick() {
		return isStick;
	}
	public void setIsStick(int isStick) {
		this.isStick = isStick;
	}
	public Dynamic(){}
	public String getAtitle() {
		return atitle;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public void setAtitle(String atitle) {
		this.atitle = atitle;
	}
	public String getAimgpath() {
		return aimgpath;
	}
	public void setAimgpath(String aimgpath) {
		this.aimgpath = aimgpath;
	}
	public String getAclassname() {
		return aclassname;
	}
	public void setAclassname(String aclassname) {
		this.aclassname = aclassname;
	}
	public Date getAdate() {
		return adate;
	}
	public void setAdate(Date adate) {
		this.adate = adate;
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
	public String getArticleid() {
		return articleid;
	}
	public void setArticleid(String articleid) {
		this.articleid = articleid;
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
	 * 获取发布时间
	 */
	public Date getIssuedDate() {
    	return issuedDate;
    }
	/**
	 * 设置发布时间
	 */
	public void setIssuedDate(Date issuedDate) {
    	this.issuedDate = issuedDate;
    }
	/**
	 * 获取动态文字内容
	 */
	public String getDyContent() {
    	return dyContent;
    }
  	
	/**
	 * 设置动态文字内容
	 */
	public void setDyContent(String dyContent) {
    	this.dyContent = dyContent;
    }
	/**
	 * 获取语音id
	 */
	public String getMediaId() {
    	return mediaId;
    }
	/**
	 * 设置语音id
	 */
	public void setMediaId(String mediaId) {
    	this.mediaId = mediaId;
    }

	/**
	 * 获取语音价格
	 */
	public Long getMediaPrice() {
    	return mediaPrice;
    }
  	
	/**
	 * 设置语音价格
	 */
	public void setMediaPrice(Long mediaPrice) {
    	this.mediaPrice = mediaPrice;
    }
	
	

	public Integer getMediaSeconds() {
		return mediaSeconds;
	}


	public void setMediaSeconds(Integer mediaSeconds) {
		this.mediaSeconds = mediaSeconds;
	}


	/**
	 * 获取访问量
	 */
	public Long getVisitQuantity() {
    	return visitQuantity;
    }
  	
	/**
	 * 设置访问量
	 */
	public void setVisitQuantity(Long visitQuantity) {
    	this.visitQuantity = visitQuantity;
    }


	/**
	 * 获取访问类型：1好友可见 2、所有人可见
	 */
	public Integer getVisitType() {
    	return visitType;
    }
  	
	/**
	 * 设置访问类型：1好友可见 2、所有人可见
	 */
	public void setVisitType(Integer visitType) {
    	this.visitType = visitType;
    }

	
	public String getVisitUrl() {
		return visitUrl;
	}


	public void setVisitUrl(String visitUrl) {
		this.visitUrl = visitUrl;
	}


	/**
	 * 获取状态,0、已删除、1已公布
	 */
	public Integer getStatus() {
    	return status;
    }
  	
	/**
	 * 设置状态,0、已删除、1已公布
	 */
	public void setStatus(Integer status) {
    	this.status = status;
    }


	public String getAdUrl() {
		return adUrl;
	}


	public void setAdUrl(String adUrl) {
		this.adUrl = adUrl;
	}


	public String getDyType() {
		return dyType;
	}


	public void setDyType(String dyType) {
		this.dyType = dyType;
	}




	public Integer getAllowReword() {
		return allowReword;
	}




	public void setAllowReword(Integer allowReword) {
		this.allowReword = allowReword;
	}

	public Integer getAllowComment() {
		return allowComment;
	}

	public void setAllowComment(Integer allowComment) {
		this.allowComment = allowComment;
	}
	
	
	
}
