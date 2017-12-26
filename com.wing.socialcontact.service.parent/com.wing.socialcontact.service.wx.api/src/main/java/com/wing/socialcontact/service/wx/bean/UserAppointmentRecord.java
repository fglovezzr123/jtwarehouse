package com.wing.socialcontact.service.wx.bean;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TJY_USER_APPOINTMENT_RECORD 商友约见表
 * 
 * @author liutao
 * 
 */
@Table(name = "TJY_USER_APPOINTMENT_RECORD")
public class UserAppointmentRecord implements Serializable
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
	/** 状态：0 待约见；1 进行中；2 完成；3 取消 */
	private String status;
	/** 发起时间 */
	private Date create_Time;
	/** 开始约见时间 */
	private Date start_Time;
	/** 期望时间 */
	private Date expect_Time;
	/** 预计约见时长 */
	private long estimateTimeLength;
	/** 约见总时长（单位：秒） */
	private long duration;
	/** 约见理由 */
	private String msg;
	/** 模块0：打招呼  1：约见 */
	private String module;

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

	public Date getStart_Time()
	{
		return start_Time;
	}

	public void setStart_Time(Date start_Time)
	{
		this.start_Time = start_Time;
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

	public String getModule()
	{
		return module;
	}

	public void setModule(String module)
	{
		this.module = module;
	}
	
}
