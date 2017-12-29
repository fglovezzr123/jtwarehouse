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
		long num = orderItem.getNum();
		double price = orderItem.getPrice();
		double total_Fee = orderItem.getTotal_Fee();
		String type = orderItem.getType();
		Map paramMap = new HashMap();
		paramMap.put("id", id);
		paramMap.put("num", num);
		paramMap.put("price", price);
		paramMap.put("total_Fee", total_Fee);
		return orderItemDao.updateOrderItem(paramMap);
	}

	@Override
	public List<OrderItem> queryOrderItem(OrderItem orderItem)
	{
		return orderItemDao.queryOrderItem(orderItem);
	}

	@Override
	public OrderItem getOrderItem(OrderItem orderItem)
	{
		Map paramMap = new HashMap();
		paramMap.put("id", orderItem.getId());
		paramMap.put("order_Id", orderItem.getOrder_Id());
		paramMap.put("item_Id", orderItem.getItem_Id());
		return orderItemDao.getOrderItem(paramMap);
	}


}
