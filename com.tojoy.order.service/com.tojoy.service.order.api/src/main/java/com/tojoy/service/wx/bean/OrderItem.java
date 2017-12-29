package com.tojoy.service.wx.bean;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "TJY_ORDER_ITEM")
public class OrderItem implements Serializable
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
	/** 商品id */
	private long item_Id;
	/** 商品数量 */
	private long num;
	/** 单价  */
	private double price;
	/** 总金额 */
	private double total_Fee;
	/** 创建时间 */
	private Date create_Time;
	/** 类型 0约见模块、1其他业务模块、2其他业务模块*/
	private String type;
	public long getId()
	{
		return id;
	}
	public void setId(long id)
	{
		this.id = id;
	}
	public String getOrder_Id()
	{
		return order_Id;
	}
	public void setOrder_Id(String order_Id)
	{
		this.order_Id = order_Id;
	}
	public long getItem_Id()
	{
		return item_Id;
	}
	public void setItem_Id(long item_Id)
	{
		this.item_Id = item_Id;
	}
	public long getNum()
	{
		return num;
	}
	public void setNum(long num)
	{
		this.num = num;
	}
	public double getPrice()
	{
		return price;
	}
	public void setPrice(double price)
	{
		this.price = price;
	}
	public double getTotal_Fee()
	{
		return total_Fee;
	}
	public void setTotal_Fee(double total_Fee)
	{
		this.total_Fee = total_Fee;
	}
	public Date getCreate_Time()
	{
		return create_Time;
	}
	public void setCreate_Time(Date create_Time)
	{
		this.create_Time = create_Time;
	}
	public String getType()
	{
		return type;
	}
	public void setType(String type)
	{
		this.type = type;
	}
	
}
