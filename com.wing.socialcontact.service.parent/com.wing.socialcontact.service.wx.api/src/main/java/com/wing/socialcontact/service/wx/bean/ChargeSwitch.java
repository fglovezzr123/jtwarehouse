package com.wing.socialcontact.service.wx.bean;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TJY_CHARGE_SWITCH
 * @author 刘涛
 */
@Table(name = "TJY_CHARGE_SWITCH")
public class ChargeSwitch implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

    /**  */
    @Id
	@Column(name = "id")
	private String id;
    
	/** 排序 */
	private Integer orderNum;

    /** 状态（1打开0关闭） */
	private Integer status;

    /** 创建人 */
	private String createUserId;

    /** 创建时间 */
	private Date createTime;
	/** 标识 */
	private String sign;
	
	/** 平台收费 */
	private double platformFee;
	/** 名称 */
	private String name;

	public ChargeSwitch(){}

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}


	public Integer getOrderNum()
	{
		return orderNum;
	}

	public void setOrderNum(Integer orderNum)
	{
		this.orderNum = orderNum;
	}

	public Integer getStatus()
	{
		return status;
	}

	public void setStatus(Integer status)
	{
		this.status = status;
	}

	public String getCreateUserId()
	{
		return createUserId;
	}

	public void setCreateUserId(String createUserId)
	{
		this.createUserId = createUserId;
	}

	public Date getCreateTime()
	{
		return createTime;
	}

	public void setCreateTime(Date createTime)
	{
		this.createTime = createTime;
	}

	


	public double getPlatformFee()
	{
		return platformFee;
	}

	public void setPlatformFee(double platformFee)
	{
		this.platformFee = platformFee;
	}

	public String getSign()
	{
		return sign;
	}

	public void setSign(String sign)
	{
		this.sign = sign;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	
	

}
