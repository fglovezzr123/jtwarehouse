package com.wing.socialcontact.service.wx.api;


import com.wing.socialcontact.service.wx.bean.Sysconfig;

/**
 * 系统配置信息表
 * 
 * @author zengmin
 * @date 2017-04-17 15:04:04
 * @version 1.0
 */
public interface ISysconfigService {

	public Sysconfig getSysconfig();
	
	public int getRemoteErpSwitch();

}