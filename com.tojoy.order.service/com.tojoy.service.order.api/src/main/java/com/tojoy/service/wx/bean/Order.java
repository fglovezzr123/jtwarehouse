package com.tojoy.service.wx.bean;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "TJY_ORDER")
public class Order implements Serializable
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
	/** 订单id */
	private String order_Id;
	/** 付款总金额 */
	private double payment;
	/** 支付类型0在线支付1其他支付 */
	private String payment_Type;
	/** 0未知付、1已支付、2取消支付 */
	private String status;
	/** 修改时间 */
	private Date update_Time;
	/** 付款时间 */
	private Date payment_Time;
	/** 交易完成时间 */
	private Date end_Time;
	/** 用户id */
	private long user_Id;
	private Date create_Time;
	/** 0普通约见、1业务模块、2业务模块、3业务模块 */
	private String type;
	/** 订单类型 0业务订单1充值订单*/
	private String order_Type;
	/** 商品id */
	private long item_Id;
	public long getId()
	{
		return id;
	}
	public void setId(long id)
	{
		this.id = id;
	}
	public double getPayment()
	{
		return payment;
	}
	public void setPayment(double payment)
	{
		this.payment = payment;
	}
	public String getStatus()
	{
		return status;
	}
	public void setStatus(String status)
	{
		this.status = status;
	}
	public String getType()
	{
		return type;
	}
	public void setType(String type)
	{
		this.type = type;
	}
	public String getOrder_Id()
	{
		return order_Id;
	}
	public void setOrder_Id(String order_Id)
	{
		this.order_Id = order_Id;
	}
	public String getPayment_Type()
	{
		return payment_Type;
	}
	public void setPayment_Type(String payment_Type)
	{
		this.payment_Type = payment_Type;
	}
	public Date getUpdate_Time()
	{
		return update_Time;
	}
	public void setUpdate_Time(Date update_Time)
	{
		this.update_Time = update_Time;
	}
	public Date getPayment_Time()
	{
		return payment_Time;
	}
	public void setPayment_Time(Date payment_Time)
	{
		this.payment_Time = payment_Time;
	}
	public Date getEnd_Time()
	{
		return end_Time;
	}
	public void setEnd_Time(Date end_Time)
	{
		this.end_Time = end_Time;
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
	public String getOrder_Type()
	{
		return order_Type;
	}
	public void setOrder_Type(String order_Type)
	{
		this.order_Type = order_Type;
	}
	public long getItem_Id()
	{
		return item_Id;
	}
	public void setItem_Id(long item_Id)
	{
		this.item_Id = item_Id;
	}
	
}
