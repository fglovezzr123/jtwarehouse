package com.wing.socialcontact.service.wx.api;

import java.util.List;
import com.wing.socialcontact.common.model.DataGrid;
import com.wing.socialcontact.common.model.PageParam;

import com.wing.socialcontact.service.wx.bean.GlobalWhitelist;
import com.wing.socialcontact.service.wx.bean.TjyUser;

/**
 * 
 * @author liangwj
 * @version 1.0
 */
public interface IGlobalWhitelistService{
	/**
	 * 新增
	 * @param t
	 * @author liangwj
	 */
	public int insertGlobalWhitelist(GlobalWhitelist t);
	/**
	 * 修改
	 * @param t
	 * @return
	 * @author liangwj
	 */
	public int updateGlobalWhitelist(GlobalWhitelist t);
	/**
	 * 修改
	 * @param t
	 * @return
	 * @author liangwj
	 */
	public int updateSelectiveGlobalWhitelist(GlobalWhitelist t);
	/**
	 * 删除
	 * @param t
	 * @return
	 * @author liangwj
	 */
	public int deleteGlobalWhitelist(GlobalWhitelist t);
	/**
	 * 查询 
	 * @param t
	 * @return
	 * @author liangwj
	 */
	public List<GlobalWhitelist> queryGlobalWhitelist(GlobalWhitelist t);
	/**
	 * 单个查询
	 * @param t
	 * @return
	 * @author liangwj
	 */
	public GlobalWhitelist getGlobalWhitelist(String id);
	/**
	 * 分页查询
	 * @param param
	 * @param t
	 * @return
	 * @author liangwj
	 */
	public DataGrid selectAllGlobalWhitelist(PageParam param, GlobalWhitelist t);
	/**
	 * 分页查询
	 * @param param
	 * @param t
	 * @return
	 * @author liangwj
	 */
	public DataGrid selectAllTjyUser(PageParam param, TjyUser t);
	/**
	 * 批量添加白名单
	 * @param types
	 * @param ids
	 * @return
	 */
	public String insertGlobalWhitelists(String types, String ids);
	/**
	 * 批量删除
	 * @param ids
	 * @return
	 * @author liangwj
	 */
	public String deleteGlobalWhitelists( String ids);
}