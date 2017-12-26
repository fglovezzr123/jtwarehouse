package com.wing.socialcontact.service.wx.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.github.pagehelper.PageHelper;
import com.wing.socialcontact.service.wx.api.IAppointmentService;
import com.wing.socialcontact.service.wx.bean.UserAppointmentRecord;
import com.wing.socialcontact.service.wx.bean.UserAppointmentRecordDetails;
import com.wing.socialcontact.service.wx.dao.AppointmentDao;
import com.wing.socialcontact.sys.service.impl.BaseServiceImpl;

@Service
public class AppointmentServiceImpl extends BaseServiceImpl<UserAppointmentRecord> implements IAppointmentService
{
	@Resource
	private AppointmentDao appointmentDao;

	/**
	 * 生成约见订单
	 * 
	 * @param
	 * @return
	 */
	@Override
	public UserAppointmentRecord addAppointment(UserAppointmentRecord userAppointmentRecord)
	{
		appointmentDao.insertAPPointmentRecord(userAppointmentRecord);
		return userAppointmentRecord;
	}

	/**
	 * 修改约见订单
	 * 
	 * @param
	 * @return
	 */
	@Override
	public int updateAppointmentRecord(UserAppointmentRecord userAppointmentRecord)
	{
		
		return appointmentDao.updateAppointmentRecord(userAppointmentRecord);
	}

	/**
	 * 根据id获取约见订单
	 * 
	 * @param
	 * @return
	 */
	@Override
	public UserAppointmentRecord getUserAppointmentRecordByid(long id)
	{
		return appointmentDao.getUserAppointmentRecordByid(id);
	}

	/**
	 * 根据userId获取待约见订单
	 * 
	 * @param
	 * @return
	 */
	@Override
	public List findWaitAppointmentRecord(int userId, int pageNum, int pageSize)
	{
		PageHelper.startPage(pageNum, pageSize);
		Map paramMap = new HashMap();
		paramMap.put("userId", userId);
		return appointmentDao.findWaitAppointmentRecord(paramMap);
	}

	/**
	 * 根据userId获取已约见订单
	 * 
	 * @param
	 * @return
	 */
	@Override
	public List findHaveAppointmentRecord(int userId, int pageNum, int pageSize)
	{
		PageHelper.startPage(pageNum, pageSize);
		Map paramMap = new HashMap();
		paramMap.put("userId", userId);
		return appointmentDao.findHaveAppointmentRecord(paramMap);
	}

	/**
	 * 根据userId获取被约见订单
	 * 
	 * @param
	 * @return
	 */
	@Override
	public List findByAppointmentRecord(int userId, int pageNum, int pageSize)
	{
		PageHelper.startPage(pageNum, pageSize);
		Map paramMap = new HashMap();
		paramMap.put("userId", userId);
		return appointmentDao.findByAppointmentRecord(paramMap);
	}

	/**
	 * 生成约见详情记录
	 * 
	 * @param
	 * @return
	 */
	@Override
	public UserAppointmentRecordDetails addDetails(UserAppointmentRecordDetails uard)
	{
		appointmentDao.insertDetails(uard);
		return uard;
	}
	/**
	 * 根据期望约见时间查询订单
	 * 
	 * @param
	 * @return
	 */
	@Override
	public List getUserAppointmentRecordByExpecttime(Date expecttime)
	{
		return appointmentDao.getUserAppointmentRecordByExpecttime(expecttime);
	}

	@Override
	public List getDyjList(long from_Id,int pageNum,int pageSize)
	{
		PageHelper.startPage(pageNum, pageSize);
		Map paramMap = new HashMap();
		paramMap.put("from_Id", from_Id);
		return appointmentDao.getDyjList(paramMap);
	}

	@Override
	public List getYyjList(long from_Id, int pageNum, int pageSize)
	{
		PageHelper.startPage(pageNum, pageSize);
		Map paramMap = new HashMap();
		paramMap.put("from_Id", from_Id);
		return appointmentDao.getYyjList(paramMap);
	}

	@Override
	public List getByjList(long to_Id, int pageNum, int pageSize)
	{
		PageHelper.startPage(pageNum, pageSize);
		Map paramMap = new HashMap();
		paramMap.put("to_Id", to_Id);
		return appointmentDao.getByjList(paramMap);
	}
	/**
	 * 
	 * 获取用户爱好
	 * */
	@Override
	public String getUserHobby(String userId)
	{
		return appointmentDao.getUserHobby(userId);
	}

}
