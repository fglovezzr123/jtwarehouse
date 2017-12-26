package com.wing.socialcontact.im.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import tk.mybatis.mapper.common.Mapper;
import com.wing.socialcontact.service.im.bean.ImFriend;

/**
 * 
 * @author xuxinyuan
 * @date 2017-03-26 19:21:38
 * @version 1.0
 */
@Repository
public interface ImFriendDao extends Mapper<ImFriend> {
	/**
	 * 根据用户id查询好友列表并已首字母排序含星标
	 * @param userId
	 * @return
	 */
	public List findMyFriendListByUserId(Map paramMap);
	/**
	 * 根据用户id查询好友列表并已首字母排序含星标
	 * @param userId
	 * @return
	 */
	public List findMyFriendListByUserId3(Map paramMap);
	/**
	 * 根据用户id查询好友列表并已首字母排序不含星标
	 * @param userId
	 * @return
	 */
	public List findMyFriendListByUserId1(Map paramMap);
	/**
	 * 根据用户id查询星标好友
	 * @param userId
	 * @return
	 */
	public List findMyFriendListByUserId2(Map paramMap);
	
	/**
	 * 根据用户id获取好友数量
	 * @param userId
	 * @return
	 */
	public int findMyFriendCountByUserId(String userId);
	
	/**
	 * 根据id删除
	 * @param id
	 * @return
	 */
	public int deleteById(String id);
	
	/**
	 * 根据id查询
	 * @param id
	 * @return
	 */
	public ImFriend loadById(String id);
	
	/**
	 * 根据用户id和好友id获取信息
	 * @param paramMap
	 * @return
	 */
	public ImFriend selectByUserIdAndFriendId(Map paramMap);
	
	/**
	 * 获取统统好友
	 * @param paramMap
	 * @return
	 */
	public List findCommonFriendsByUserAndUser(Map paramMap);
	
	/**
	 * 获取我的好友的个人信息
	 * @param paramMap
	 * @return
	 */
	public Map findMyFriendUserInfo(Map paramMap);
	
	/**
	 * 查询好友列表
	 * @param userId
	 * @return
	 */
	public List findFriendListByParam(Map paramMap);
	
	//=============================================APP
	public List getStarFlag(Map paramMap);
	public List selectStarFriendList(Map paramMap);
	
}
