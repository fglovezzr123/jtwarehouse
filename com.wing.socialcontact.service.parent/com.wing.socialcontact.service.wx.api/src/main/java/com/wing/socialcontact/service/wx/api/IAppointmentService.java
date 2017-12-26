package com.wing.socialcontact.service.wx.api;

import java.util.Date;
import java.util.List;
import com.wing.socialcontact.service.wx.bean.UserAppointmentRecord;
import com.wing.socialcontact.service.wx.bean.UserAppointmentRecordDetails;

public interface IAppointmentService
{
	/**
	 * 生成约见订单
	 * 
	 * @param
	 * @return
	 */
	UserAppointmentRecord addAppointment(UserAppointmentRecord userAppointmentRecord);

	/**
	 * 修改约见订单
	 * 
	 * @param
	 * @return
	 */
	int updateAppointmentRecord(UserAppointmentRecord userAppointmentRecord);

	/**
	 * 根据id获取约见订单
	 * 
	 * @param
	 * @return
	 */
	UserAppointmentRecord getUserAppointmentRecordByid(long id);

	/**
	 * 根据userId获取已约见订单
	 * 
	 * @param
	 * @return
	 */
	List findHaveAppointmentRecord(int userId, int pageNum, int pageSize);

	/**
	 * 根据userId获取待约见订单
	 * 
	 * @param
	 * @return
	 */
	List findWaitAppointmentRecord(int userId, int pageNum, int pageSize);//

	/**
	 * 根据userId获取被约见订单
	 * 
	 * @param
	 * @return
	 */
	List findByAppointmentRecord(int userId, int pageNum, int pageSize);

	/**
	 * 生成约见详情记录
	 * 
	 * @param
	 * @return
	 */
	UserAppointmentRecordDetails addDetails(UserAppointmentRecordDetails uard);
	/**
	 * 根据期望约见时间查询约见订单
	 * 
	 * @param
	 * @return
	 */
	List getUserAppointmentRecordByExpecttime(Date expecttime);
	/**
	 * 获取待约见列表
	 * 
	 * @param
	 * @return
	 */
	List getDyjList(long from_Id,int pageNum,int pageSize);
	
	/**
	 * 获取已约见列表
	 * 
	 * @param
	 * @return
	 */
	List getYyjList(long from_Id,int pageNum,int pageSize);
	
	List getByjList(long to_Id,int pageNum,int pageSize);
	/**
	 * 获取用户爱好
	 * */
	String getUserHobby(String userId);
	
}
