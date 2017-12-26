package com.wing.socialcontact.service.wx.api;

import java.util.List;
import java.util.Map;

import com.wing.socialcontact.common.model.DataGrid;
import com.wing.socialcontact.common.model.PageParam;
import com.wing.socialcontact.service.wx.bean.UserHonor;

/**
 * 
 * @author gaojun
 * @date 2017-04-14 22:34:36
 * @version 1.0
 */
public interface IUserHonorService {
	/**
	 * 条件查询
	 * 
	 * @param param
	 * @param newsClass
	 * @return
	 */
	public DataGrid selectAllUserHonor(PageParam param, UserHonor userHonor);

	/**
	 * 条件查询
	 * 
	 * @param param
	 * @param newsClass
	 * @return
	 */
	public List<Map> selectAllUserHonor(UserHonor userHonor);

	/*
	 * 获取用户列表
	 * 
	 * @param param
	 * 
	 * @param nickname
	 * 
	 * @return
	 */
	public DataGrid queryUserListByparam(PageParam param, String nickname, String true_name,String job, String industry, String mobile,String recon_mobile,String bind_phone,String orderBy,String level,String address);
	public DataGrid queryUserListByparam_select(PageParam param, String nickname, String true_name,String job, String industry, String mobile,String recon_mobile,String bind_phone);

	public List<Map<String, Object>> queryUserListByparam(String id);

	/**
	 * 添加
	 * 
	 * @param newsClass
	 * @return
	 */
	public String addUserHonor(UserHonor userHonor);

	/**
	 * 更新
	 * 
	 * @param newsClass
	 * @return
	 */
	public String updateUserHonor(UserHonor userHonor);

	/**
	 * 批量删除
	 * 
	 * @param ids
	 * @return
	 */
	public boolean deleteUserHonor(String[] ids);

	public UserHonor selectByPrimaryKey(String key);

	public UserHonor selectById(String id);

	public boolean updateStatus(String id, int status);
	
	public boolean isExistsByUserId(String userId, String honorId);
	public List<UserHonor> selectByUserIdAndHonorId(String userId, String honorId);
	
	public boolean addUserAndHonor(String userId,String honorCode);

	/**
	 * 查询审核列表
	 * 
	 * @Title: queryShUserListByParam
	 * @Description: TODO
	 * @param param
	 * @param searchMap
	 * @return
	 * @return: Object
	 * @author: zengmin
	 * @date: 2017年5月14日 下午11:02:04
	 */
	public Object queryShUserListByParam(PageParam param, Map<String, Object> searchMap);
	
	public List<Map<String, Object>>  selectByUserId(String userId);

	public List<UserHonor> getAllHonors();

}