package com.wing.socialcontact.service.im.api;

import java.util.List;

/**
 * 
 * @author xuxinyuan
 * @date 2017-03-29 15:27:44
 * @version 1.0
 */
public interface IImFollowService{

	/**
	 * 获取用户关注列表
	 * @param userId
	 * @return
	 */
	public List findMyFollowUsers(String userId,int pageNum, int pageSize) throws RuntimeException;
	
	/**
	 * 获取用户粉丝列表
	 * @param userId
	 * @return
	 */
	public List findMyFansUsers(String userId,int pageNum, int pageSize) throws RuntimeException;
	
	/**
	 * 关注用户
	 * @param userId
	 * @param followUser
	 * @return
	 */
	public String insertFollowUsers(String userId,String followUser);
	
	/**
	 * 用户取消关注
	 * @param userId
	 * @param followUser
	 * @return
	 */
	public String deleteFollowUsers(String userId,String followUser);
	
	/**
	 * 获取用户的关注数量
	 * @param userId
	 * @return
	 */
	public int findMyFollowUsersCount(String userId);
	
	/**
	 * 获取用户的粉丝数量
	 * @param userId
	 * @return
	 */
	public int findMyFansUsersCount(String userId);
	
	/**
	 * 判断是否为已关注用户
	 * @param userId
	 * @param followUser
	 * @return
	 */
	public boolean isFollowUser(String userId,String followUser);

}