package com.wing.socialcontact.service.im.api;

import java.util.List;
import java.util.Map;

import com.wing.socialcontact.service.im.bean.ImFriendrequest;

/**
 * 
 * @author xuxinyuan
 * @date 2017-03-26 19:16:59
 * @version 1.0
 */
public interface IImFriendrequestService{

	/**
	 * 根据用户id查询新好友待添加列表
	 * @param userId
	 * @return
	 */
	public List findMyNewFriendListByUserId(String userId,int pageNum, int pageSize) throws RuntimeException;
	
	/**
	 * 获取我的新好友数量
	 * @param userId
	 * @return
	 */
	public int findMyNewfriendCountByUserId(String userId) throws RuntimeException;
	
	/**
	 * 
	 * @param userId 用户id
	 * @param friendUserId 好友id
	 * @return
	 * @throws RuntimeException
	 */
	public String saveFriendRequest(String userId,String friendUserId,String askmessage) throws RuntimeException;
	
	/**
	 * 
	 * @param friendRequestId 请求id
	 * @param status 状态：1待确认2已同意3已拒绝
	 * @return
	 */
	public String updateFriendRequestStatus(String friendRequestId,int status,String friendMemo)throws RuntimeException;
	
	/**
	 * 根据用户id获取用户信息
	 * @param userId
	 * @return
	 */
	public Map findUserByUserId(String userId)throws RuntimeException;
	
	/**
	 * 获取非好友用户列表
	 * @return
	 * @throws RuntimeException
	 */
	public List findNoFriendUserListByUserId(String userId,int pageNum, int pageSize,String trueName,String cityId,String industryId)throws RuntimeException;
	
	/**
	 * 根据请求id获取好友请求记录
	 * @param friendRequestId
	 * @return
	 */
	public ImFriendrequest loadById(String friendRequestId);
	
	
	/**
	 *累积当天发送的好友请求
	 * @param userId
	 * @return
	 */
	public Integer selectOneDaySendSum(String userId);
	
	/**
	 *累积当天接收的好友请求
	 * @param friendUser
	 * @return
	 */
	public Integer selectOneDayGetSum(String friendUser);
	
	
	/**
	 * 根据条件获取请求信息
	 * @return
	 */
	public ImFriendrequest findRequestByParam(Map paramMap);
	
	

}