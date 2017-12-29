package com.wing.socialcontact.service.im.api;

import java.util.List;

/**
 * 
 * @author xuxinyuan
 * @date 2017-04-03 18:37:16
 * @version 1.0
 */
public interface IImToprelatService{

	/**
	 * 根据用户id获取置顶用户信息列表
	 * @param userId
	 * @return
	 */
	public List findTopFriendListByUserId(String userId,int pageNum, int pageSize) throws RuntimeException;
	
	/**
	 *根据用户id获取置顶群列表
	 * @param userId
	 * @param pageNum
	 * @param pageSize
	 * @return
	 * @throws RuntimeException
	 */
	public List findTopGroupListByUserId(String userId,int pageNum, int pageSize)throws RuntimeException;
	
	/**
	 * 根据组id获取组用户置顶列表
	 * @param userId
	 * @return
	 */
	public List findTopFriendListByGroupId(String groupId,int pageNum, int pageSize) throws RuntimeException;

}