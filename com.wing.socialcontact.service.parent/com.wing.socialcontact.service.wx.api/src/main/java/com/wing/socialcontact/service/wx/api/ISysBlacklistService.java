package com.wing.socialcontact.service.wx.api;

import com.wing.socialcontact.common.model.DataGrid;
import com.wing.socialcontact.common.model.PageParam;
import com.wing.socialcontact.service.wx.bean.SysBlacklist;

/**
 * 
 * @author zengmin
 * @date 2017-08-08 08:56:14
 * @version 1.0
 */
public interface ISysBlacklistService {

	/**
	 * 查询黑名单
	 * 
	 * @Title: selectAllSysBlacklist
	 * @Description: TODO
	 * @param param
	 * @param sysBlacklist
	 * @return
	 * @return: DataGrid
	 * @author: zengmin
	 * @date: 2017年8月8日 上午9:44:13
	 */
	public DataGrid selectAllSysBlacklist(PageParam param, SysBlacklist sysBlacklist);

	/**
	 * 添加
	 * 
	 * @param sysBlacklist
	 * @return
	 */
	public String addSysBlacklist(SysBlacklist sysBlacklist);

	/**
	 * 更新
	 * 
	 * @param sysBlacklist
	 * @return
	 */
	public String updateSysBlacklist(SysBlacklist sysBlacklist);

	/**
	 * 根据id查询(缓存)
	 * 
	 * @param key
	 * @return
	 */
	public SysBlacklist selectByPrimaryKey(String key);

	public SysBlacklist selectById(String id);

	public String deleteSysBlacklist(String[] id);

	/**
	 * 检测用户是否加入黑名单
	 * 
	 * @Title: selectSysBlacklistByUserId
	 * @Description: TODO
	 * @param userId
	 * @return
	 * @return: boolean
	 * @author: zengmin
	 * @date: 2017年8月8日 上午9:05:05
	 */
	public boolean selectSysBlacklistByUserId(String userId);

}