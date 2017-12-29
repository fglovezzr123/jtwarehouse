package com.wing.socialcontact.service.wx.impl;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;

import com.wing.socialcontact.service.wx.dao.DynamicGratuityLogDao;
import com.wing.socialcontact.service.wx.api.IDynamicGratuityLogService;

/**
 * 
 * @author xuxinyuan
 * @date 2017-04-17 09:54:05
 * @version 1.0
 */
@Service
public class DynamicGratuityLogServiceImpl implements IDynamicGratuityLogService{
	/**
	 * 缓存的key值
	 */
	private static final String cache_name = "";
	
	@Resource
	private DynamicGratuityLogDao dynamicGratuityLogDao;

}