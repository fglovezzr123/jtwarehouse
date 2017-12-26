package com.tojoy.service.wx.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.tojoy.service.wx.api.IOrderService;
import com.tojoy.service.wx.bean.Order;
import com.tojoy.service.wx.dao.OrderDao;
@Service
public class OrderServiceImpl implements IOrderService
{
	@Resource
	private OrderDao orderDao;

	@Override
	public Order insertOrder(Order order)
	{
		orderDao.insertOrder(order);
		return order;
	}

	@Override
	public int updateOrder(Order order)
	{
		String id = order.getId()+"";
		Map paramMap = new HashMap();
		paramMap.put("id", id);
		return orderDao.updateOrder(paramMap);
	}

	@Override
	public List<Order> queryOrder(Order order)
	{
		return orderDao.queryOrder(order);
	}

	@Override
	public Order getOrder(String id)
	{
		return orderDao.getOrder(id);
	}
}
