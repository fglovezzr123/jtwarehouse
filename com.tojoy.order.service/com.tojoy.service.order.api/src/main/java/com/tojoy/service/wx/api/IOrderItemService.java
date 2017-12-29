package com.tojoy.service.wx.api;

import java.util.List;
import com.tojoy.service.wx.bean.Order;
import com.tojoy.service.wx.bean.OrderItem;

public interface IOrderItemService
{
	/**
	 * 新增
	 */
	public OrderItem insertOrderItem(OrderItem orderItem);
	
	/**
	 * 修改
	 */
	public int updateOrderItem(OrderItem orderItem);
	
	/**
	 * 查询 
	 */
	public List<OrderItem> queryOrderItem(OrderItem orderItem);
	
	/**
	 * 单个查询
	 */
	public OrderItem getOrderItem(OrderItem orderItem);
}
