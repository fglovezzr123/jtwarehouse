package com.tojoy.service.wx.impl;

import java.util.Date;
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
		Date end_Time = order.getEnd_Time();
		long item_Id = order.getItem_Id();
		String order_Id = order.getOrder_Id();
		String order_Type = order.getOrder_Type();
		double payment = order.getPayment();
		Date payment_Time = order.getPayment_Time();
		String payment_Type = order.getPayment_Type();
		String status = order.getStatus();
		String type = order.getType();
		Date update_Time = order.getUpdate_Time();
		long user_Id = order.getUser_Id();
		Map paramMap = new HashMap();
		paramMap.put("id", id);
		paramMap.put("end_Time", end_Time);
		paramMap.put("item_Id", item_Id);
		paramMap.put("order_Id", order_Id);
		paramMap.put("order_Type", order_Type);
		paramMap.put("payment", payment);
		paramMap.put("payment_Time", payment_Time);
		paramMap.put("payment_Type", payment_Type);
		paramMap.put("status", status);
		paramMap.put("type", type);
		paramMap.put("update_Time", update_Time);
		paramMap.put("user_Id", user_Id);
		return orderDao.updateOrder(paramMap);
	}

	@Override
	public List<Order> queryOrder(Order order)
	{
		return orderDao.queryOrder(order);
	}

	@Override
	public Order getOrder(Order order)
	{
		Map paramMap = new HashMap();
		paramMap.put("id", order.getId());
		paramMap.put("order_Id", order.getOrder_Id());
		paramMap.put("item_Id", order.getItem_Id());
		return orderDao.getOrder(paramMap);
	}
}
