package com.wing.socialcontact.im.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import tk.mybatis.mapper.common.Mapper;

import com.wing.socialcontact.service.im.bean.ImFriendrequest;

/**
 * 
 * @author xuxinyuan
 * @date 2017-03-26 19:21:38
 * @version 1.0
 */
@Repository
public interface ImFriendrequestDao extends Mapper<ImFriendrequest> {
	/**
	 * 根据用户id查询新好友待添加列表
	 * @param userId
	 * @return
	 */
	public List findMyNewFriendListByUserId(String userId);
	/**
	 * 获取鑫好友数量
	 * @param userId
	 * @return
	 */
	public int findMyNewfriendCountByUserId(String userId);
	
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
	public ImFriendrequest loadById(String id);
	
	/**
	 * 根据userid获取用户信息
	 * @param userId
	 * @return
	 */

	public Map findUserMapByUserId(String userId);
	
	/**
	 * 根据userId获取用户爱列表
	 * @param userId
	 * @return
	 */
	public List findUserFavListByUserId(String userId);
	
	/**
	 * 获取非好友列表
	 * @param userId
	 * @return
	 */
	public List findNofriendUsersByUserId(Map paramMap );
	
	/**
	 * 根据条件获取请求信息
	 * @return
	 */
	public ImFriendrequest findRequestByParam(Map paramMap);
	
	
	/**
	 *累积当天发送的好友请求
	 * @param userId
	 * @return
	 */
	Integer  selectOneDaySendSum(@Param("userId") String userId);
	
	/**
	 *累积当天接收的好友请求
	 * @param friendUser
	 * @return
	 */
	Integer  selectOneDayGetSum(@Param("friendUser") String friendUser);
	
	
}
