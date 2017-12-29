package com.wing.socialcontact.service.wx.api;

import java.util.List;
import java.util.Map;

import com.wing.socialcontact.common.model.DataGrid;
import com.wing.socialcontact.common.model.PageParam;
import com.wing.socialcontact.service.wx.bean.UserFav;
import com.wing.socialcontact.service.wx.bean.UserFriendimpress;

/**
 * 
 * @author gaojun
 * @date 2017-04-14 22:34:36
 * @version 1.0
 */
public interface IUserFriendimpressService {

	
	/**
	 * 查询出所有分类
	 * @return
	 */
	public List selectAllUserFriendimpress(UserFriendimpress userFriendimpress);
	/**
	 * 条件查询
	 * 
	 * @param param
	 * @param newsClass
	 * @return
	 */
	public DataGrid selectAllUserFriendimpress(PageParam param, UserFriendimpress userFriendimpress);



	/**
	 * 添加
	 * 
	 * @param newsClass
	 * @return
	 */
	public String addUserFriendimpress(UserFriendimpress userFriendimpress);

	/**
	 * 更新
	 * 
	 * @param newsClass
	 * @return
	 */
	public String updateUserFriendimpress(UserFriendimpress userFriendimpress);

	/**
	 * 批量删除
	 * 
	 * @param ids
	 * @return
	 */
	public boolean deleteUserFriendimpress(String[] ids);

	public UserFriendimpress selectByPrimaryKey(String key);

	public UserFriendimpress selectById(String id);

	public List<Map<String, Object>> selectByUserIdAndType(String userId, int type);
	
	public List<Map<String, Object>> selectByUserIdAndType(String createUserId,String userId, int type);
	
	public List<Map<String, Object>> selectcountByUserId(String userId, int type);

}