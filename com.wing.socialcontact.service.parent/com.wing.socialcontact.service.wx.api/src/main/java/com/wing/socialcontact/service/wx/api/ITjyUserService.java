package com.wing.socialcontact.service.wx.api;

import java.util.List;
import java.util.Map;

import com.wing.socialcontact.service.wx.bean.TjyUser;

/**
 * 
 * @author zengmin
 * @date 2017-04-07 01:41:07
 * @version 1.0
 */
public interface ITjyUserService {

	public TjyUser selectByPrimaryKey(String key);

	public TjyUser selectById(String id);

	public boolean addTjyUser(TjyUser tjyUser);

	public boolean updateTjyUser(TjyUser tjyUser);

	public TjyUser selectByWxUniqueId(String wxUniqueId);

	public List selAllUserForApp(String uids);
	
	public List getUserListById(String id);

	public List selectByMobile(String mobile);

	/**
	 * @author devil
	 * @desicription: 获取认证用户列表
	 * @date Created in 2017/12/18 11:15
	 */
	List getReconUserList(Map paraMap);

	/**
	 * 根据mobile,认证状态查询用户
	 * @param phone
	 * @return
	 */
	public List<TjyUser> selectbymobile(String phone,int recon_status);
	
	
	public List<Map<String, Object>> selectTaskRecon();

//	/**
//	 * 创建远程数据库用户
//	 * @param tjyUser
//	 * @return
//	 */
//	public boolean remotingAddTjyUser(TjyUser tjyUser,String mobile);
	
	/**
	 * 更新远程数据库用户
	 * @param tjyUser
	 * @return
	 */
	public boolean remotingUpdateTjyUser(TjyUser tjyUser,String mobile) throws Exception;
	
	/**
	 * 更新获取数据库用户
	 * @param tjyUser
	 * @return
	 */
	public Map remotingGetUser(String mobile) throws Exception;
	
}