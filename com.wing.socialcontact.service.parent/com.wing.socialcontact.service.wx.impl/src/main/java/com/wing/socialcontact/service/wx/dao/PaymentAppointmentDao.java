package com.wing.socialcontact.service.wx.dao;

import java.util.List;
import com.wing.socialcontact.service.wx.bean.UserPaymentAppointment;
import tk.mybatis.mapper.common.Mapper;

public interface PaymentAppointmentDao extends Mapper<UserPaymentAppointment>
{
	/** 获取未完成支付的订单 */
	List getUnpaidByUserId(String userId);
	
	/** 生成支付订单 */
	int addPaymentAppointment(UserPaymentAppointment userPaymentAppointment);
	
	/** 根据id获取订单 */
	UserPaymentAppointment getUnpaidById(long id);
	/** 根据订单id修改订单 */
	int updateUnpaidById(UserPaymentAppointment userPaymentAppointment);
}
