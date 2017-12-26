package com.wing.socialcontact.service.wx.api;

import java.util.List;

import com.wing.socialcontact.common.model.DataGrid;
import com.wing.socialcontact.common.model.PageParam;
import com.wing.socialcontact.service.wx.bean.UserLatestvistor;

/**
 * 
 * @author gaojun
 * @date 2017-05-02 11:14:40
 * @version 1.0
 */
public interface IUserLatestvistorService{

	public List selectLatestVistors(String userId, String vistorUserId, int pageNum, int pageSize);
	
	/**
	 * 查询出所有分类
	 * @return
	 */
	public List selectAllUserLatestvistor(UserLatestvistor userLatestvistor);
	/**
	 * 条件查询
	 * @param param
	 * @param role
	 * @return
	 */
	public DataGrid selectUserLatestvistors(PageParam param,UserLatestvistor userLatestvistor);
	/**
	 * 添加
	 * @param role
	 * @return
	 */
	public String addUserLatestvistor(UserLatestvistor userLatestvistor);
	/**
	 * 更新
	 * @param role
	 * @return
	 */
	public String updateUserLatestvistor(UserLatestvistor userLatestvistor);
	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	public boolean deleteUserLatestvistors(String ids);
	
	public UserLatestvistor selectByPrimaryKey(String key);
	
	public UserLatestvistor selectById(String id);

}