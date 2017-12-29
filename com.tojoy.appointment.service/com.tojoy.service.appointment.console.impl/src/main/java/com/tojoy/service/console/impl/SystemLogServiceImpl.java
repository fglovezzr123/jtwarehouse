/**  
 * @Project: tjy
 * @Title: SystemLogServiceImpl.java
 * @Package com.wing.socialcontact.sys.service.impl
 * @date 2016-4-11 下午3:48:17
 * @Copyright: 2016 
 */
package com.tojoy.service.console.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.tojoy.service.console.service.ISystemLogService;
import com.tojoy.service.console.dao.SyLoginLogDao;
import com.tojoy.service.console.dao.SystemLogDao;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tojoy.common.model.DataGrid;
import com.tojoy.common.model.IpInfo;
import com.tojoy.common.model.Member;
import com.tojoy.common.model.PageParam;
import com.tojoy.service.console.bean.SyLog;
import com.tojoy.service.console.bean.SyLoginLog;
import com.tojoy.util.DateUtil;
import com.tojoy.util.ServletUtil;

/**
 * 
 * 类名：SystemLogServiceImpl
 * 功能：系统登录日志 系统重要操作日志 业务层实现
 * 详细：
 * 作者：dijuli
 * 版本：1.0
 * 日期：2016-4-11 下午3:48:17
 *
 */
@Service
public class SystemLogServiceImpl implements ISystemLogService {

	@Resource
	private SystemLogDao dao;
	@Resource
	private SyLoginLogDao syLoginLogDao;
	
	public DataGrid selectSyLoginLog(PageParam param,SyLoginLog log,Date startDate,Date endDate,boolean isUserName){
		
		DataGrid data=new DataGrid();
		
		String orderStr = param.getOrderByString();
		PageHelper.startPage(param.getPage(), param.getRows());
		Map parm = new HashMap();
		parm.put("userId", log.getUserId());
		parm.put("loginType", log.getLoginType());
		parm.put("loginIp", log.getLoginIp());
		if(null != startDate){
		    parm.put("startDate", startDate.getTime()/1000);
		}
		if(null != endDate){
		    parm.put("endDate", endDate.getTime()/1000);
		}
		
		parm.put("orderStr", orderStr);
		List lst = syLoginLogDao.selectForLogByParam(parm);
		PageInfo page = new PageInfo(lst);

		data.setRows(lst);
		data.setTotal(page.getTotal());
		
		return data;
		
	
	}
	public List<SyLoginLog> selectSyLoginLogs(String[] ids){
		List<SyLoginLog> list=new ArrayList<SyLoginLog>();
		for(String id : ids){
			list.add(syLoginLogDao.selectByPrimaryKey( id));
		}
		return list;
	}
	public boolean deleteLoginLog(String[] ids){
		for(String id:ids){
			syLoginLogDao.deleteByPrimaryKey(id);
		}
		saveLog("删除登录日志", "删除"+ids.length+"条");
		return true;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public DataGrid selectSyLog(PageParam param,SyLog log,Date startDate,Date endDate){
		DataGrid data=new DataGrid();
		
		String orderStr = param.getOrderByString();
		PageHelper.startPage(param.getPage(), param.getRows());
		Map parm = new HashMap();
		parm.put("userId", log.getUserId());
		parm.put("actionIp", log.getActionIp());
		if(startDate != null)
		    parm.put("startDate", startDate.getTime()/1000);
		if(endDate != null)
		    parm.put("endDate", endDate.getTime()/1000);
		
		parm.put("orderStr", orderStr);
		List lst = dao.selectByParam(parm);
		PageInfo page = new PageInfo(lst);

		data.setRows(lst);
		data.setTotal(page.getTotal());
		
		return data;
	}
	
	public boolean deleteLog(String[] ids){
		for(String id:ids){
			dao.deleteByPrimaryKey(id);
		}
		saveLog("删除操作日志", "删除"+ids.length+"条");
		return true;
	}
	public void saveLog(String actionContent, String actionDesc) {
		//获取session
		Member me=ServletUtil.getMember(); //获取当前登录者
		SyLog log=new SyLog();
		log.setActionContent(actionContent);
		log.setActionDesc(actionDesc);
		log.setActionTime(DateUtil.currentTimestamp());
		log.setUserId(me.getId());
		IpInfo ipInfo=me.getIpInfo();
		log.setActionIp(ipInfo.getIp());
		log.setActionIpInfo(ipInfo.getCountry()+" "+ipInfo.getRegion()+" "+ipInfo.getCity()+" "+ipInfo.getIsp());
		
		dao.insert(log);
	}
}
