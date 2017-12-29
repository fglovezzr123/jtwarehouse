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
		Date actualEnd_Time = appointmentPay.getActualEnd_Time();
		String appointmentStatus = appointmentPay.getAppointmentStatus();
		long duration = appointmentPay.getDuration();
		Date estimateEnd_Time = appointmentPay.getEstimateEnd_Time();
		long estimateTimeLength = appointmentPay.getEstimateTimeLength();
		Date expect_Time = appointmentPay.getExpect_Time();
		Date start_Time = appointmentPay.getStart_Time();
		Map paramMap = new HashMap();
		paramMap.put("id", id);
		paramMap.put("status", status);
		paramMap.put("actualEnd_Time", actualEnd_Time);
		paramMap.put("appointmentStatus", appointmentStatus);
		paramMap.put("duration", duration);
		paramMap.put("estimateEnd_Time", estimateEnd_Time);
		paramMap.put("estimateTimeLength", estimateTimeLength);
		paramMap.put("expect_Time", expect_Time);
		paramMap.put("start_Time", start_Time);
		
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
	public List getYyjAllList(long from_Id, int pageNum, int pageSize)
	{
		PageHelper.startPage(pageNum, pageSize);
		Map paramMap = new HashMap();
		paramMap.put("from_Id", from_Id);
		return appointmentPayDao.getYyjAllList(paramMap);
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
	/** 
	 * 获取在期望约见时间是否有被确认的约见 
	 * */
	@Override
	public List<AppointmentPay> queryConfirmList(AppointmentPay appointmentPay)
	{
		Map paramMap = new HashMap();
		paramMap.put("expect_Time", appointmentPay.getExpect_Time());
		paramMap.put("user_Id", appointmentPay.getFrom_Id());
		paramMap.put("status", appointmentPay.getStatus());
		
		return appointmentPayDao.queryConfirmList(paramMap);
	}
	@Override
	public List getYyjList(long to_Id, int pageNum, int pageSize)
	{
		PageHelper.startPage(pageNum, pageSize);
		Map paramMap = new HashMap();
		paramMap.put("to_Id", to_Id);
		return appointmentPayDao.getYyjList(paramMap);
	}
	
	
}
