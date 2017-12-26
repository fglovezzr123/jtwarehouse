package com.wing.socialcontact.service.wx.api;

import java.util.List;

import com.wing.socialcontact.common.model.DataGrid;
import com.wing.socialcontact.common.model.PageParam;
import com.wing.socialcontact.service.wx.bean.UserFav;

/**
 * 
 * @author gaojun
 * @date 2017-04-11 15:34:06
 * @version 1.0
 */
public interface IUserFavService{

	
	/**
	 * 查询出所有分类
	 * @return
	 */
	public List selectAllUserFav(UserFav userFav);
	/**
	 * 查询出所有分类
	 * @return
	 */
	public List selectAllUserFav2(UserFav userFav);
	/**
	 * 条件查询
	 * @param param
	 * @param role
	 * @return
	 */
	public DataGrid selectUserFavs(PageParam param,UserFav userFav);
	/**
	 * 添加
	 * @param role
	 * @return
	 */
	public String addUserFav(UserFav userFav);
	/**
	 * 更新
	 * @param role
	 * @return
	 */
	public String updateUserFav(UserFav userFav);
	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	public boolean deleteUserFavs(String[] ids);
	
	public UserFav selectByPrimaryKey(String key);
	
	public UserFav selectById(String id);
}