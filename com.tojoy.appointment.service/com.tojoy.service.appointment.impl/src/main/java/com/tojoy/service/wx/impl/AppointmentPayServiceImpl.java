package com.tojoy.service.wx.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.github.pagehelper.PageHelper;
import com.tojoy.service.wx.api.IAppointmentPayService;
import com.tojoy.service.wx.bean.AppointmentPay;
import com.tojoy.service.wx.dao.AppointmentPayDao;
@Service
public class AppointmentPayServiceImpl implements IAppointmentPayService
{

	@Resource
	private AppointmentPayDao appointmentPayDao;
	
	
	/**
	 * 新增
	 * @param appointmentPay
	 */
	@Override
	public AppointmentPay insertAppointmentPay(AppointmentPay appointmentPay)
	{
		appointmentPayDao.insertAppointmentPay(appointmentPay);
		return appointmentPay;
	}
	/**
	 * 修改
	 * @param appointmentPay
	 */
	@Override
	public int updateAppointmentPay(AppointmentPay appointmentPay)
	{
		String id = appointmentPay.getId()+"";
		String status = appointmentPay.getStatus();
		Map paramMap = new HashMap();
		paramMap.put("id", id);
		paramMap.put("status", status);
		return appointmentPayDao.updateAppointmentPay(paramMap);
	}
	/**
	 * 查询 
	 * @param appointmentPay
	 */
	@Override
	public List<AppointmentPay> queryAppointmentPay(AppointmentPay appointmentPay)
	{
		return appointmentPayDao.queryAppointmentPay(appointmentPay);
	}
	/**
	 * 单个查询
	 * @param id
	 */
	@Override
	public AppointmentPay getAppointmentPay(String id)
	{
		return appointmentPayDao.getAppointmentPay(id);
	}
	@Override
	public List getDyjList(long to_Id,int pageNum,int pageSize)
	{
		PageHelper.startPage(pageNum, pageSize);
		Map paramMap = new HashMap();
		paramMap.put("to_Id", to_Id);
		return appointmentPayDao.getDyjList(paramMap);
	}

	@Override
	public List getYyjList(long from_Id, int pageNum, int pageSize)
	{
		PageHelper.startPage(pageNum, pageSize);
		Map paramMap = new HashMap();
		paramMap.put("from_Id", from_Id);
		return appointmentPayDao.getYyjList(paramMap);
	}
	@Override
	public String getUserHobby(String userId)
	{
		return appointmentPayDao.getUserHobby(userId);
	}
	@Override
	public List getDyjAllList(long userId, int pageNum, int pageSize)
	{
		PageHelper.startPage(pageNum, pageSize);
		Map paramMap = new HashMap();
		paramMap.put("userId", userId);
		return appointmentPayDao.getDyjAllList(paramMap);
	}
	
	
}
