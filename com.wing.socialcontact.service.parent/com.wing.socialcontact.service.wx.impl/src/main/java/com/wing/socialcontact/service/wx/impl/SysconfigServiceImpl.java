package com.wing.socialcontact.service.wx.impl;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;

import com.wing.socialcontact.service.wx.dao.SysconfigDao;
import com.wing.socialcontact.sys.service.impl.BaseServiceImpl;
import com.wing.socialcontact.service.wx.api.ISysconfigService;
import com.wing.socialcontact.service.wx.bean.Sysconfig;

/**
 * 系统配置信息表
 * 
 * @author zengmin
 * @date 2017-04-17 15:04:05
 * @version 1.0
 */
@Service
public class SysconfigServiceImpl extends BaseServiceImpl<Sysconfig> implements ISysconfigService {
	/**
	 * 缓存的key值
	 */
	private static final String cache_name = "\"DB:Sysconfig:\" + ";

	@Resource
	private SysconfigDao sysconfigDao;

	@Override
	public Sysconfig getSysconfig() {
		return super.selectByPrimaryKeyCache(1l, Sysconfig.class);
	}

	@Override
	public int getRemoteErpSwitch() {
		
		return sysconfigDao.remoteEnable();
	}
}