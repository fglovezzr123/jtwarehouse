package com.tojoy.service.wx.api;

import java.util.Date;
import java.util.List;
import com.tojoy.common.model.DataGrid;
import com.tojoy.common.model.PageParam;
import com.tojoy.service.wx.bean.AppointmentPay;

public interface IAppointmentPayService
{
	/**
	 * 新增
	 * @param appointmentPay
	 */
	public AppointmentPay insertAppointmentPay(AppointmentPay appointmentPay);
	
	/**
	 * 修改
	 * @param appointmentPay
	 */
	public int updateAppointmentPay(AppointmentPay appointmentPay);
	
	/**
	 * 查询 
	 * @param appointmentPay
	 */
	public List<AppointmentPay> queryAppointmentPay(AppointmentPay appointmentPay);
	
	/**
	 * 单个查询
	 * @param id
	 */
	public AppointmentPay getAppointmentPay(String id);
	/**
	 * 获取被约见人是自己的待约见列表
	 * 
	 * @param
	 * @return
	 */
	List getDyjList(long to_Id,int pageNum,int pageSize);
	/**
	 * 获取被约见人是自己的已约见列表
	 * 
	 * @param
	 * @return
	 */
	List getYyjList(long to_Id,int pageNum,int pageSize);
	/**
	 * 获取所有待约见列表
	 * 
	 * @param
	 * @return
	 */
	List getDyjAllList(long userId,int pageNum,int pageSize);
	/**
	 * 获取已约见列表
	 * 
	 * @param
	 * @return
	 */
	List getYyjAllList(long from_Id,int pageNum,int pageSize);
	
	/**
	 * 获取用户爱好
	 * */
	String getUserHobby(String userId);
	/** 
	 * 获取在期望约见时间是否有被确认的约见 
	 * */
	public List<AppointmentPay> queryConfirmList(AppointmentPay appointmentPay);
}
