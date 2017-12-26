package com.tojoy.service.wx.dao;

import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;
import com.tojoy.service.wx.bean.AppointmentPay;
import tk.mybatis.mapper.common.Mapper;
@Repository
public interface AppointmentPayDao extends Mapper<AppointmentPay>
{
	/**
	 * 新增
	 * @param appointmentPay
	 */
	int insertAppointmentPay(AppointmentPay appointmentPay);
	/**
	 * 修改
	 * @param appointmentPay
	 */
	int updateAppointmentPay(Map paramMap);
	/**
	 * 查询 
	 * @param appointmentPay
	 */
	List<AppointmentPay> queryAppointmentPay(AppointmentPay appointmentPay);
	/**
	 * 单个查询
	 * @param id
	 */
	AppointmentPay getAppointmentPay(String id);
	
	AppointmentPay normalHangUpAppointment(Map paramMap);
	
	List getDyjList(Map paramMap);
	List getDyjAllList(Map paramMap);
	List getYyjList(Map paramMap);
	/**
	 * 获取用户爱好
	 * 
	 * */
	String getUserHobby(String userId);
}
