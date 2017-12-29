/**  
 * @Project: tjy
 * @Title: IMainService.java
 * @Package com.oa.manager.system.service
 * @date 2016-5-3 下午4:08:14
 * @Copyright: 2016 
 */
package com.wing.socialcontact.sys.service;

import java.util.List;
import java.util.Map;

import com.wing.socialcontact.sys.bean.SyMenu;
import com.wing.socialcontact.sys.bean.SyUsers;

/**
 * 
 * 类名：IMainService
 * 功能：
 * 详细：
 * 作者：dijuli
 * 版本：1.0
 * 日期：2016-5-3 下午4:08:14
 *
 */
public interface IMainService {
	
	/**
	 * 获取用户最新资料，刷新session，ServletContext全局变量
	 * @return
	 */
	public void init(SyUsers u);
	
	public List<Map<String,Object>> selectMenusTop(String userId);
	
	public void clearRedis();
}
