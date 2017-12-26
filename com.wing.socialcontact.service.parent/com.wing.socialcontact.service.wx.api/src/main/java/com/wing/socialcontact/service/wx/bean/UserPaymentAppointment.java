package com.wing.socialcontact.service.wx.bean;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
/**
 * TJY_USER_PAYMENT_APPOINTMENT 商友约见表
 * 
 * @author liutao
 * 
 */
@Table(name = "TJY_USER_PAYMENT_APPOINTMENT")
public class UserPaymentAppointment implements Serializable
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
	private long user_Id;
	/** status：状态：0 待支付；1 已支付；2 已取消 */
	private String status;
	/** 发起时间 */
	private Date create_Time;
	/** isPayment：是否有未完成的订单0 没有；1有  */
	private String isPayment;
	/** 约见理由 */
	private String msg;
	/** 期望时间（期望与被约见人约见的时间） */
	private Date expect_Time;
	/** 预计约见时长（单位：秒） */
	private long estimateTimeLength;
	public long getId()
	{
		return id;
	}
	public void setId(long id)
	{
		this.id = id;
	}
	public long getUser_Id()
	{
		return user_Id;
	}
	public void setUser_Id(long user_Id)
	{
		this.user_Id = user_Id;
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
	public String getIsPayment()
	{
		return isPayment;
	}
	public void setIsPayment(String isPayment)
	{
		this.isPayment = isPayment;
	}
	public String getMsg()
	{
		return msg;
	}
	public void setMsg(String msg)
	{
		this.msg = msg;
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
	
}
