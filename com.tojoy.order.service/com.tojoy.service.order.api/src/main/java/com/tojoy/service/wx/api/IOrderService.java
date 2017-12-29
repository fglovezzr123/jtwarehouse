package com.tojoy.service.wx.api;

import java.util.List;
import com.tojoy.service.wx.bean.Order;

public interface IOrderService
{
	/**
	 * 新增
	 */
	public Order insertOrder(Order order);
	
	/**
	 * 修改
	 */
	public int updateOrder(Order order);
	
	/**
	 * 查询 
	 */
	public List<Order> queryOrder(Order order);
	
	/**
	 * 单个查询
	 */
	public Order getOrder(Order order);
}
