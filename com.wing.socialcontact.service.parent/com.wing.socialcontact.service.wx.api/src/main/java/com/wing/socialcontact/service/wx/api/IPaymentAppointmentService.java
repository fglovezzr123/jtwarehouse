package com.wing.socialcontact.service.wx.api;

import java.util.List;
import com.wing.socialcontact.service.wx.bean.UserPaymentAppointment;

public interface IPaymentAppointmentService
{
	/**
	 * 根据userId获取未支付订单
	 * 
	 * @param
	 * @return
	 */
	List getUnpaidByUserId(String userId);//
	
	/**
	 * 生成支付订单
	 * 
	 * @param
	 * @return
	 */
	UserPaymentAppointment addAppointment(UserPaymentAppointment userPaymentAppointment);
	/**
	 * 根据id获取订单
	 * 
	 * @param
	 * @return
	 */
	UserPaymentAppointment getUnpaidById(long id);
	/**
	 * 修改订单
	 * 
	 * @param
	 * @return
	 */
	int updateUnpaidById(UserPaymentAppointment userPaymentAppointment);
}
