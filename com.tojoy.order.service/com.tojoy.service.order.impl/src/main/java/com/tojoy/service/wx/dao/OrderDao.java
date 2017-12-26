package com.tojoy.service.wx.dao;

import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;
import com.tojoy.service.wx.bean.Order;
import tk.mybatis.mapper.common.Mapper;
@Repository
public interface OrderDao extends Mapper<Order>
{
	/**
	 * 新增
	 * @param appointmentPay
	 */
	int insertOrder(Order order);
	/**
	 * 修改
	 * @param appointmentPay
	 */
	int updateOrder(Map paramMap);
	/**
	 * 查询 
	 * @param appointmentPay
	 */
	List<Order> queryOrder(Order order);
	/**
	 * 单个查询
	 * @param id
	 */
	Order getOrder(String id);
}
