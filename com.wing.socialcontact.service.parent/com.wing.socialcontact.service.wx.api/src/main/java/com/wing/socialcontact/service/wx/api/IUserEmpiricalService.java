package com.wing.socialcontact.service.wx.api;

import java.util.List;

import com.wing.socialcontact.common.model.DataGrid;
import com.wing.socialcontact.common.model.PageParam;
import com.wing.socialcontact.service.wx.bean.UserEmpirical;
import com.wing.socialcontact.service.wx.bean.UserEmpiricalLog;

/**
 * 
 * @author gaojun
 * @date 2017-07-04 11:25:51
 * @version 1.0
 */
public interface IUserEmpiricalService{
	/**
	 * 条件查询
	 * 
	 * @param param
	 * @param role
	 * @return
	 */
	public DataGrid selectUserEmpiricals(PageParam param, UserEmpirical userEmpirical);
	
	/**
	 * 批量删除
	 * @param id
	 * @return
	 */
	public boolean deleteUserEmpirical(String[] ids);
	/**
	 * 新增
	 * @param dto
	 * @return
	 */
	public String addUserEmpirical(UserEmpirical dto);
	/**
	 * 更新
	 * @param dto
	 * @return
	 */
	public String updateUserEmpirical(UserEmpirical dto);
	
	
	public UserEmpirical selectById(String id);
	
	public UserEmpirical selectByPrimaryKey(String key);
	
	public UserEmpirical selectBylevel(String level);

	public List<UserEmpirical> selectAllUserEmpirical();
	
	
	public UserEmpirical selectByEmpirical(Integer Empirical);
	

}