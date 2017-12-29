package com.wing.socialcontact.service.wx.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import tk.mybatis.mapper.common.Mapper;

import com.wing.socialcontact.service.wx.bean.TjyUser;

/**
 * 
 * @author zengmin
 * @date 2017-04-07 01:41:07
 * @version 1.0
 */
@Repository
public interface TjyUserDao extends Mapper<TjyUser> {
	//大咖相关
	/**
	 * 根据条件获取大咖或非大咖列表
	 * @param param
	 * @return
	 */
	public List getUserListByParam(Map param);
	/**
	 * 根据条件获取用户列表
	 * @param param
	 * @return
	 */
	public List getUserListByParam2(Map param);
	/**
	 * 根据条件获取用户列表
	 * @param param
	 * @return
	 */
	public List getUserListByParam_select(Map param);
	/**
	 * 根据条件获取用户列表
	 * @param param
	 * @return
	 */
	//public 	List<Map<String, Object>>  getUserListById(String userId);
	
	public 	List getUserListById(Map paraMap);
	public TjyUser loadById(String id);
	
	public int update(TjyUser user);
	
	public TjyUser load(Map param);
	
	List selAllUserForApp(Map param);
	
	List selUserBySendType(Map param);
	
	List getShUserListByParam(Map param);
	
	List<TjyUser> query(TjyUser t);
	
	List selectByMobile(Map param);
	
	/**
	 * 查出所有企服云客服信息
	 * @return
	 */
	List selAllUserForQFY();
	/**
	 * 查出所有用户
	 * @param parm
	 * @return
	 */
	List selAllUserList(Map parm);
	
	List<Map<String, Object>> selectTaskRecon();
	

}
