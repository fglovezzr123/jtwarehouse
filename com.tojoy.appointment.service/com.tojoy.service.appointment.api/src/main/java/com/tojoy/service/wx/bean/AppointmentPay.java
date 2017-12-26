package com.tojoy.service.wx.bean;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
/**
 * TJY_APPOINTMENT_PAY 商友约见--付费版
 * 
 * 
 */
@Table(name = "TJY_APPOINTMENT_PAY")
public class AppointmentPay implements Serializable
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
	/** 发起人USERID */
	private long from_Id;
	/** 被约见人USERID */
	private long to_Id;
	/** 状态0待约见、1已约见、2确认约见、3完成、4取消 */
	private String status;
	/** 发起时间 */
	private Date create_Time;
	/** 期望约见时间 */
	private Date expect_Time;
	/** 预计约见时长 */
	private long estimateTimeLength;
	/** 预计约见结束时间 */
	private Date estimateEnd_Time;
	/** 实际约见开始时间 */
	private Date start_Time;
	/** 实际约见时长 */
	private long duration;
	/** 实际结束时间 */
	private Date actualEnd_Time;
	/** 约见理由 */
	private String msg;
	/** 约见分类0定时、1立即 */
	private String appointmentStatus;
	/** 类型 0普通约见、1同城约见、2同行约见、3同趣约见、4大卡约见、5网红约见、6名医约见*/
	private String type;
	public long getId()
	{
		return id;
	}
	public void setId(long id)
	{
		this.id = id;
	}
	public long getFrom_Id()
	{
		return from_Id;
	}
	public void setFrom_Id(long from_Id)
	{
		this.from_Id = from_Id;
	}
	public long getTo_Id()
	{
		return to_Id;
	}
	public void setTo_Id(long to_Id)
	{
		this.to_Id = to_Id;
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
	public Date getExpect_Time()
	{
		return expect_Time;
	}
	public void setExpect_Time(Date expect_Time)
	{
		this.expect_Time = expect_Time;
	}
	public long getEstimateTimeLength()
	{
		return estimateTimeLength;
	}
	public void setEstimateTimeLength(long estimateTimeLength)
	{
		this.estimateTimeLength = estimateTimeLength;
	}
	public long getDuration()
	{
		return duration;
	}
	public void setDuration(long duration)
	{
		this.duration = duration;
	}
	public String getMsg()
	{
		return msg;
	}
	public void setMsg(String msg)
	{
		this.msg = msg;
	}
	public String getAppointmentStatus()
	{
		return appointmentStatus;
	}
	public void setAppointmentStatus(String appointmentStatus)
	{
		this.appointmentStatus = appointmentStatus;
	}
	public String getType()
	{
		return type;
	}
	public void setType(String type)
	{
		this.type = type;
	}
	public Date getEstimateEnd_Time()
	{
		return estimateEnd_Time;
	}
	public void setEstimateEnd_Time(Date estimateEnd_Time)
	{
		this.estimateEnd_Time = estimateEnd_Time;
	}
	public Date getStart_Time()
	{
		return start_Time;
	}
	public void setStart_Time(Date start_Time)
	{
		this.start_Time = start_Time;
	}
	public Date getActualEnd_Time()
	{
		return actualEnd_Time;
	}
	public void setActualEnd_Time(Date actualEnd_Time)
	{
		this.actualEnd_Time = actualEnd_Time;
	}
	
}
