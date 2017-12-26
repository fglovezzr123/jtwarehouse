package com.wing.socialcontact.service.wx.api;

import java.util.List;

import com.wing.socialcontact.common.model.DataGrid;
import com.wing.socialcontact.common.model.PageParam;
import com.wing.socialcontact.service.wx.bean.UserGreetings;

/**
 * 
 * @author gaojun
 * @date 2017-04-22 20:19:46
 * @version 1.0
 */
public interface IUserGreetingsService{

	/**
	 * 条件查询
	 * @param param
	 * @param userGreetings
	 * @return
	 */
	public DataGrid selectAllUserGreetings(PageParam param,UserGreetings userGreetings);
	/**
	 * 查询出所有
	 * @return
	 */
	public List selectAllUserGreetings(UserGreetings userGreetings);
	/**
	 * 添加
	 * @param userGreetings
	 * @return
	 */
	public String addUserGreetings(UserGreetings userGreetings);
	/**
	 * 更新
	 * @param userGreetings
	 * @return
	 */
	public String updateUserGreetings(UserGreetings userGreetings);
	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	public boolean deleteUserGreetings(String[] ids);

	public UserGreetings selectByPrimaryKey(String key);
	
	public UserGreetings selectById(String id);
	
	public List selectUserGreetings(UserGreetings userGreetings);
	
	public Integer getCountOneDay(String userId);

}