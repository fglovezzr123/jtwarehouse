package com.wing.socialcontact.service.wx.impl;

import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.wing.socialcontact.service.wx.api.IAppointmentDetailsService;
import com.wing.socialcontact.service.wx.bean.UserAppointmentRecordDetails;
import com.wing.socialcontact.service.wx.dao.AppointmentDao;
import com.wing.socialcontact.service.wx.dao.AppointmentDetailsDao;

@Service
public class AppointmentDetailsServiceImpl implements IAppointmentDetailsService
{
	@Resource
	private AppointmentDetailsDao appointmentDetailsDao;
	/**
	 * 添加约见详情
	 * 
	 * @param
	 * @return
	 */
	@Override
	public UserAppointmentRecordDetails addAppointmentRecordDetails(UserAppointmentRecordDetails userAppointmentRecordDetails)
	{
		appointmentDetailsDao.addAppointmentRecordDetails(userAppointmentRecordDetails);
		return userAppointmentRecordDetails;
	}
	/**
	 * 修改约见详情
	 * 
	 * @param
	 * @return
	 */
	@Override
	public int updateAppointmentDetails(UserAppointmentRecordDetails userAppointmentRecordDetails)
	{
		return appointmentDetailsDao.updateAppointmentDetails(userAppointmentRecordDetails);
	}
	/**
	 * 获取约见详情
	 * 
	 * @param
	 * @return
	 */
	@Override
	public UserAppointmentRecordDetails getAppointmentRecordDetails(long id)
	{
		return appointmentDetailsDao.getAppointmentRecordDetails(id);
	}
	@Override
	public List getAppointmentRecordDetailsList(long id)
	{
		return appointmentDetailsDao.getAppointmentRecordDetailsList(id);
	}
	
	
	
}
