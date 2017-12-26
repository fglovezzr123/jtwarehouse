package com.wing.socialcontact.service.wx.bean;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TJY_USER_APPOINTMENT_RECORD_Details 商友约见详表
 * 
 * @author liutao
 * 
 */
@Table(name = "tjy_user_appointment_record_details")
public class UserAppointmentRecordDetails implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/** 主键 */
	/** ID */
	@Id
	@Column(name = "id")
	private long id;
	/** 对应TJY_USER_APPOINTMENT_RECORD表的id */
	private long ar_Id;
	/** 状态：0 进行中；1 完成 */
	private String status;
	/** 创建时间 */
	private Date create_Time;
	/** 开始约见时间 */
	private Date start_Time;
	/** 结束时间 */
	private Date end_Time;
	/** 本次约见时长（单位：秒） */
	private long duration;
	public long getId()
	{
		return id;
	}
	public void setId(long id)
	{
		this.id = id;
	}
	public long getAr_Id()
	{
		return ar_Id;
	}
	public void setAr_Id(long ar_Id)
	{
		this.ar_Id = ar_Id;
	}
	public String getStatus()
	{
		return status;
	}
	public void setStatus(String status)
	{
		this.status = status;
	}
	public Date getCreate_Time()
	{
		return create_Time;
	}
	public void setCreate_Time(Date create_Time)
	{
		this.create_Time = create_Time;
	}
	public Date getStart_Time()
	{
		return start_Time;
	}
	public void setStart_Time(Date start_Time)
	{
		this.start_Time = start_Time;
	}
	public Date getEnd_Time()
	{
		return end_Time;
	}
	public void setEnd_Time(Date end_Time)
	{
		this.end_Time = end_Time;
	}
	public long getDuration()
	{
		return duration;
	}
	public void setDuration(long duration)
	{
		this.duration = duration;
	}

}
