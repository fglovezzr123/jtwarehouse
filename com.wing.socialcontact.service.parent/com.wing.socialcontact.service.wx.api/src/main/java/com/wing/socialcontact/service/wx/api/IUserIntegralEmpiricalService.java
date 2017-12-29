package com.wing.socialcontact.service.wx.api;

import com.wing.socialcontact.common.model.DataGrid;
import com.wing.socialcontact.common.model.PageParam;
import com.wing.socialcontact.service.wx.bean.UserEmpirical;
import com.wing.socialcontact.service.wx.bean.UserIntegralEmpirical;
import com.wing.socialcontact.service.wx.bean.WxUser;

/**
 * 
 * @author gaojun
 * @date 2017-07-04 11:25:53
 * @version 1.0
 */
public interface IUserIntegralEmpiricalService{
	/**
	 * 条件查询
	 * 
	 * @param param
	 * @param role
	 * @return
	 */
	public DataGrid selectUserIntegralEmpiricals(PageParam param, UserIntegralEmpirical userIntegralEmpirical);
	
	/**
	 * 批量删除
	 * @param id
	 * @return
	 */
	public boolean deleteUserIntegralEmpirical(String[] ids);
	/**
	 * 新增
	 * @param dto
	 * @return
	 */
	public String addUserIntegralEmpirical(UserIntegralEmpirical dto);
	/**
	 * 更新
	 * @param dto
	 * @return
	 */
	public String updateUserIntegralEmpirical(UserIntegralEmpirical dto);
	
	
	public UserIntegralEmpirical selectById(String id);
	
	public UserIntegralEmpirical selectByPrimaryKey(String key);
	
	

    /** 类型（1：积分 2：经验值） */
	public UserIntegralEmpirical selectByIeType(String ieType);
	

}