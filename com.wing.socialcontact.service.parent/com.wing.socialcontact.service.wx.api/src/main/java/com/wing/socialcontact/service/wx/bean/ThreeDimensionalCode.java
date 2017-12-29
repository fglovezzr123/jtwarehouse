package com.wing.socialcontact.service.wx.bean;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;




@Table(name = "tjy_tdcode")
public class ThreeDimensionalCode implements Serializable
{
	
	
	
	private static final long serialVersionUID = 1L;
	/** 主键 */
	/** ID */
	@Id
	@Column(name = "id")
	private long id;
	
	/** 三维码id */
	private long td_Id;
	/** 用户id */
	private long user_Id;
	/** 创建时间 */
	private Date create_Time;
	/** 三维码生成时间 */
	private Date tdcreate_Time;
	/** 三维码生成路径 */
	private String picPath;

	public long getId()
	{
		return id;
	}

	public void setId(long id)
	{
		this.id = id;
	}

	public long getTd_Id()
	{
		return td_Id;
	}

	public void setTd_Id(long td_Id)
	{
		this.td_Id = td_Id;
	}

	public long getUser_Id()
	{
		return user_Id;
	}

	public void setUser_Id(long user_Id)
	{
		this.user_Id = user_Id;
	}

	public Date getCreate_Time()
	{
		return create_Time;
	}

	public void setCreate_Time(Date create_Time)
	{
		this.create_Time = create_Time;
	}

	public Date getTdcreate_Time()
	{
		return tdcreate_Time;
	}

	public void setTdcreate_Time(Date tdcreate_Time)
	{
		this.tdcreate_Time = tdcreate_Time;
	}

	public String getPicPath()
	{
		return picPath;
	}

	public void setPicPath(String picPath)
	{
		this.picPath = picPath;
	}
	
	
	
	
}
