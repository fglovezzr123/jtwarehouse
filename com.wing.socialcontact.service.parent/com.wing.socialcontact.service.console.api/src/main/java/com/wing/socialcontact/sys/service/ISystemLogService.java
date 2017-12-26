/**  
 * @Project: tjy
 * @Title: ISystemLogService.java
 * @Package com.oa.manager.system.service
 * @date 2016-4-11 下午3:48:00
 * @Copyright: 2016 
 */
package com.wing.socialcontact.sys.service;

import java.util.Date;
import java.util.List;

import com.wing.socialcontact.common.model.DataGrid;
import com.wing.socialcontact.common.model.PageParam;
import com.wing.socialcontact.sys.bean.SyLog;
import com.wing.socialcontact.sys.bean.SyLoginLog;

/**
 * 
 * 类名：ISystemLogService
 * 功能：系统登录日志 系统重要操作日志 业务层
 * 详细：
 * 作者：dijuli
 * 版本：1.0
 * 日期：2016-4-11 下午3:48:00
 *
 */
public interface ISystemLogService{
	
	/**
	 * 条件分页查询登录日志
	 * @param param
	 * @param log
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public DataGrid selectSyLoginLog(PageParam param,SyLoginLog log,Date startDate,Date endDate,boolean isUserName);
	/**
	 * 根据id 获取登录日志
	 * @param ids
	 * @return
	 */
	public List<SyLoginLog> selectSyLoginLogs(String[] ids);
	/**
	 * 批量删除登录日志
	 * @param ids 
	 * @return
	 */
	public boolean deleteLoginLog(String[] ids);
	
	/**
	 * 条件分页查询操作日志
	 * @param param
	 * @param log
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public DataGrid selectSyLog(PageParam param,SyLog log,Date startDate,Date endDate);
	
	/**
	 * 批量删除操作日志
	 * @param ids
	 * @return
	 */
	public boolean deleteLog(String[] ids);
	
	public void saveLog(String string, String string2);
	
	
}
