package com.wing.socialcontact.service.wx.bean;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TJY_OPEN_SCREEN_ADVERTIS 
 * @author 刘涛
 */
@Table(name = "TJY_OPEN_SCREEN_ADVERTIS")
public class OpenScreenAdvertis implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

    /**  */
    @Id
	@Column(name = "id")
	private String id;
    
    /** 幻灯片图片地址 */
	private String imgUrl;

    /** 幻灯片图片名称 */
	private String imgName;

    /** 幻灯片链接 */
	private String imgLink;
	
	/** 排序 */
	private Integer orderNum;

    /** 状态（1有效0无效） */
	private Integer status;

    /** 创建人 */
	private String createUserId;

    /** 创建时间 */
	private Date createTime;
	
	/** 分辨率 */
	private String resolvingpower;

	public OpenScreenAdvertis(){}


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
	 * 获取状态（1有效0无效）
	 */
	public Integer getStatus() {
    	return status;
    }
  	
	/**
	 * 设置状态（1有效0无效）
	 */
	public void setStatus(Integer status) {
    	this.status = status;
    }

	/**
	 * 获取创建人
	 */
	public String getCreateUserId() {
    	return createUserId;
    }
  	
	/**
	 * 设置创建人
	 */
	public void setCreateUserId(String createUserId) {
    	this.createUserId = createUserId;
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


	public Integer getOrderNum()
	{
		return orderNum;
	}


	public void setOrderNum(Integer orderNum)
	{
		this.orderNum = orderNum;
	}


	public String getImgUrl()
	{
		return imgUrl;
	}


	public void setImgUrl(String imgUrl)
	{
		this.imgUrl = imgUrl;
	}


	public String getImgName()
	{
		return imgName;
	}


	public void setImgName(String imgName)
	{
		this.imgName = imgName;
	}


	public String getImgLink()
	{
		return imgLink;
	}


	public void setImgLink(String imgLink)
	{
		this.imgLink = imgLink;
	}


	public String getResolvingpower()
	{
		return resolvingpower;
	}


	public void setResolvingpower(String resolvingpower)
	{
		this.resolvingpower = resolvingpower;
	}
	
	
}
