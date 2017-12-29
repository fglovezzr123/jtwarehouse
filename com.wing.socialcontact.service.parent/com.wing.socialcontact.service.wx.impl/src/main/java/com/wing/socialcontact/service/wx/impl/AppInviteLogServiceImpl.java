package com.wing.socialcontact.service.wx.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.wing.socialcontact.service.wx.api.IAppInviteLogService;
import com.wing.socialcontact.service.wx.bean.AppInviteLog;
import com.wing.socialcontact.service.wx.dao.AppInviteLogDao;
import com.wing.socialcontact.sys.service.impl.BaseServiceImpl;
@Service
public class AppInviteLogServiceImpl extends BaseServiceImpl<AppInviteLog> implements IAppInviteLogService{

	@Resource
	private AppInviteLogDao appInviteLogDao;
	@Override
	public void insertLog(AppInviteLog ail) {
		// TODO Auto-generated method stub
		appInviteLogDao.insert(ail);
	}

}
