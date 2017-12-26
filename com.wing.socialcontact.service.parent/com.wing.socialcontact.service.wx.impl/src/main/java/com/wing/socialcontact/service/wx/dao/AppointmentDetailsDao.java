package com.wing.socialcontact.service.wx.dao;

import java.util.List;
import com.wing.socialcontact.service.wx.bean.UserAppointmentRecordDetails;
import com.wing.socialcontact.service.wx.bean.UserPaymentAppointment;
import tk.mybatis.mapper.common.Mapper;

public interface AppointmentDetailsDao extends Mapper<UserAppointmentRecordDetails>
{

	/** 添加约见详情 */
	int addAppointmentRecordDetails(UserAppointmentRecordDetails userAppointmentRecordDetails);
	/** 修改约见详情 */
	int updateAppointmentDetails(UserAppointmentRecordDetails userAppointmentRecordDetails);
	/** 获取约见详情 */
	UserAppointmentRecordDetails getAppointmentRecordDetails(long id);
	List getAppointmentRecordDetailsList(long id);
}
