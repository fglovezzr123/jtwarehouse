package com.wing.socialcontact.service.wx.api;

import com.wing.socialcontact.common.model.DataGrid;
import com.wing.socialcontact.common.model.PageParam;
import com.wing.socialcontact.service.wx.bean.UserIntegral;
import com.wing.socialcontact.service.wx.bean.UserIntegralLog;

/**
 * 
 * @author gaojun
 * @date 2017-07-04 11:25:53
 * @version 1.0
 */
public interface IUserIntegralService{
	/**
	 * 条件查询
	 * 
	 * @param param
	 * @param role
	 * @return
	 */
	public DataGrid selectUserIntegrals(PageParam param, UserIntegral userIntegral);
	
	/**
	 * 批量删除
	 * @param id
	 * @return
	 */
	public boolean deleteUserIntegral(String[] ids);
	/**
	 * 新增
	 * @param dto
	 * @return
	 */
	public String addUserIntegral(UserIntegral dto);
	/**
	 * 更新
	 * @param dto
	 * @return
	 */
	public String updateUserIntegral(UserIntegral dto);
	
	
	public UserIntegral selectById(String id);
	
	public UserIntegral selectByCode(String code);
	
	public UserIntegral selectByPrimaryKey(String key);

	

}