package com.wing.socialcontact.service.wx.impl;

import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.wing.socialcontact.service.wx.api.IPaymentAppointmentService;
import com.wing.socialcontact.service.wx.bean.UserAppointmentRecord;
import com.wing.socialcontact.service.wx.bean.UserPaymentAppointment;
import com.wing.socialcontact.service.wx.dao.AppointmentDao;
import com.wing.socialcontact.service.wx.dao.PaymentAppointmentDao;
import com.wing.socialcontact.sys.service.impl.BaseServiceImpl;
@Service
public class PaymentAppointmentServiceImpl extends BaseServiceImpl<UserPaymentAppointment> implements IPaymentAppointmentService
{
	@Resource
	private PaymentAppointmentDao paymentAppointmentDao;
	
	
	/**
	 * 根据userId获取未知付的订单
	 * 
	 * @param
	 * @return
	 */
	@Override
	public List getUnpaidByUserId(String userId)
	{
		return paymentAppointmentDao.getUnpaidByUserId(userId);
	}

	/**
	 * 生成支付订单
	 * 
	 * @param
	 * @return
	 */
	@Override
	public UserPaymentAppointment addAppointment(UserPaymentAppointment userPaymentAppointment)
	{
		paymentAppointmentDao.addPaymentAppointment(userPaymentAppointment);
		return userPaymentAppointment;
	}
	
	
	/**
	 * 根据id获取订单
	 * 
	 * @param
	 * @return
	 */
	@Override
	public UserPaymentAppointment getUnpaidById(long id)
	{
		return paymentAppointmentDao.getUnpaidById(id);
	}

	/**
	 * 根据订单id修改订单
	 * 
	 * @param
	 * @return
	 */
	@Override
	public int updateUnpaidById(UserPaymentAppointment userPaymentAppointment)
	{
		
		return paymentAppointmentDao.updateUnpaidById(userPaymentAppointment);
	}
	
	

}
