package com.tojoy.service.wx.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.tojoy.service.wx.api.IOrderItemService;
import com.tojoy.service.wx.bean.Order;
import com.tojoy.service.wx.bean.OrderItem;
import com.tojoy.service.wx.dao.OrderDao;
import com.tojoy.service.wx.dao.OrderItemDao;

@Service
public class OrderItemServiceImpl implements IOrderItemService
{
	@Resource
	private OrderItemDao orderItemDao;
	@Override
	public OrderItem insertOrderItem(OrderItem orderItem)
	{
		orderItemDao.insertOrderItem(orderItem);
		return orderItem;
	}

	@Override
	public int updateOrderItem(OrderItem orderItem)
	{
		String id = orderItem.getId()+"";
		Map paramMap = new HashMap();
		paramMap.put("id", id);
		return orderItemDao.updateOrderItem(paramMap);
	}

	@Override
	public List<OrderItem> queryOrderItem(OrderItem orderItem)
	{
		return orderItemDao.queryOrderItem(orderItem);
	}

	@Override
	public Order getOrderItem(String id)
	{
		return orderItemDao.getOrderItem(id);
	}

}
