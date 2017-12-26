package com.wing.socialcontact.service.wx.api;

import java.util.List;
import com.wing.socialcontact.service.wx.bean.UserAppointmentRecordDetails;

public interface IAppointmentDetailsService
{
	/**
	 * 添加约见详情
	 * 
	 * @param
	 * @return
	 */
	UserAppointmentRecordDetails addAppointmentRecordDetails(UserAppointmentRecordDetails userAppointmentRecordDetails);//
	/**
	 * 修改约见详情
	 * 
	 * @param
	 * @return
	 */
	int updateAppointmentDetails(UserAppointmentRecordDetails userAppointmentRecordDetails);
	/**
	 * 获取约见详情
	 * 
	 * @param
	 * @return
	 */
	UserAppointmentRecordDetails getAppointmentRecordDetails(long id);
	
	List getAppointmentRecordDetailsList(long id);
}
