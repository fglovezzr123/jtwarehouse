package com.tojoy.service.wx.dao;

import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;
import com.tojoy.service.wx.bean.Order;
import com.tojoy.service.wx.bean.OrderItem;
import tk.mybatis.mapper.common.Mapper;

@Repository
public interface OrderItemDao extends Mapper<OrderItem>
{
	/**
	 * 新增
	 */
	int insertOrderItem(OrderItem orderItem);
	/**
	 * 修改
	 */
	int updateOrderItem(Map paramMap);
	/**
	 * 查询 
	 */
	List<OrderItem> queryOrderItem(OrderItem orderItem);
	/**
	 * 单个查询
	 */
	OrderItem getOrderItem(Map paramMap);
}
