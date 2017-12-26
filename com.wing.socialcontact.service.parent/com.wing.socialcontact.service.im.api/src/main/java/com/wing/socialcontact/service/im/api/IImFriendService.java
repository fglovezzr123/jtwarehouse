package com.wing.socialcontact.service.im.api;

import java.util.List;
import java.util.Map;

import com.wing.socialcontact.common.model.DataGrid;
import com.wing.socialcontact.common.model.PageParam;
import com.wing.socialcontact.service.im.bean.ImFriend;

/**
 * 
 * @author xuxinyuan
 * @date 2017-03-26 19:16:59
 * @version 1.0
 */
public interface IImFriendService{

	/**
	 * 根据用户id查询好友列表并已首字母排序
	 * @param userId
	 * @param isAll 0 非全部 1 全部
	 * @return
	 */
	public List findMyFriendListByUserId(String userId,int pageNum, int pageSize,String isAll,String nickname) throws RuntimeException;
	
	public DataGrid findMyFriendListByUserId2(PageParam param,Map paramMap);
	/**
	 * 根据用户id查询好友列表并已首字母排序
	 * @param userId
	 * @param isAll 0 非全部 1 全部
	 * @return
	 */
	public List findMyFriendListByUserId1(String userId,int pageNum, int pageSize,String isAll,String nickname) throws RuntimeException;
	
	/**
	 * 根据用户id查询星标好友
	 * @param userId
	 * @return
	 */
	public List findMyFriendListByUserId2(String userId,int pageNum, int pageSize,String nickname) throws RuntimeException;
	
	/**
	 * 根据用户id查询好友数量
	 * @param userId
	 * @return
	 */
	public int findMyFriendCountByUserId(String userId) throws RuntimeException;
	
	/**
	 * 根据好友请求id相互添加好友
	 * @param fRequestId
	 * @return
	 */
	public String addEachOtherFriendByRequestId(String fRequestId,String friendMemo ) throws RuntimeException;
	
	/**
	 * 删除好友，同时删除对方好友
	 * @param userId
	 * @param friendUserId
	 * @return
	 */
	public String deleteEachOtherFriendByUserAndFriend(String userId,String friendUserId) throws RuntimeException;

	/**
	 * 修改好友信息
	 * @param imFriend
	 * @return
	 * @throws RuntimeException
	 */
	public String updateFriendUserInfo(ImFriend imFriend ) throws RuntimeException;
	
	/**
	 * 判断是否为我的好友
	 * @param friendId
	 * @param userId
	 * @return
	 */
	public boolean isMyFriend(String friendId,String userId);
	
	
	/**
	 * 获取共同的好友
	 * @param firstUserId
	 * @param sencondUserId
	 * @return
	 */
	public List findCommonFriendsByUserAndUser(String firstUserId,String sencondUserId,int pageNum, int pageSize);
	
	/**
	 * 根据用户id和好友id获取通讯录id
	 * @param userId
	 * @param friendUserId
	 * @return
	 */
	public ImFriend findByUserAndFriend(String userId,String friendUserId);
	
	/**
	 * 获取我的好友的个人详细信息
	 * @param userId
	 * @param friendUserId
	 * @return
	 */
	public Map findMyFriendUserInfo(String userId,String friendUserId);
	
	/**
	 * 修改好友置顶状态
	 * @param friendUserId
	 * @param status 1 置顶 0 不置顶
	 * @return
	 */
	public String updateFriendUserIsTop(String userId,String friendUserId,String status);
	
	
	/**
	 * 修改星标用户状态
	 * @param friendUserId
	 * @param starFlag 1 星标 0 不星标
	 * @return
	 */
	public String UpdateStarFlagByFriendUserid(String userId,String friendUserId,String starFlag);
	
	
	/*
	 * 获取用户好友列表
	 * @param param
	 * @param nickname
	 * @return
	 */
	public DataGrid queryFriendListByparam(PageParam param,Map paramMap);
	/**
	 * 初始化所有用户
	 */
	public void initUsers(Integer page,Integer size);
	/**
	 * 初始化所有用户的好友关系
	 */
	public void initFrined();
	
	/**
	 * 更新企服云客服
	 */
	public void initQfyEntryCustomer();

	//=========================================================APP
	/**
	 * 验证是否为心标好友
	 * @param userId
	 * @param tjUserId
	 * @return
	 */
	public int getStarFlag(String userId, String tjUserId);

	/**
	 * 分页获取星标好友列表
	 * @param parseInt
	 * @param parseInt2
	 * @return
	 */
	public List selectStarFriendList(String userId);

}