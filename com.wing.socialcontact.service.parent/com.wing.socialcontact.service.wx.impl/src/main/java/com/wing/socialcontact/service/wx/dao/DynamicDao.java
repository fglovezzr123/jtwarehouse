package com.wing.socialcontact.service.wx.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import tk.mybatis.mapper.common.Mapper;

import com.wing.socialcontact.service.wx.bean.Dynamic;

/**
 * 
 * @author xuxinyuan
 * @date 2017-04-17 09:54:05
 * @version 1.0
 */
@Repository
public interface DynamicDao extends Mapper<Dynamic> {
	/**
	 * 分页查询
	 * @param parm
	 * @return
	 */
	List selectByParam(Map parm);
	/**
	 * 分页查询
	 * @param parm
	 * @return
	 */
	List selectByParam2(Map parm);
	
	/**
	 * 根据用户id获取好友动态信息
	 * @param userId
	 * @return
	 */
	public List selectFriendUserDynamicByUserId(String userId);
	
	/**
	 * 根据用户id获取关注用户动态信息
	 * @param userId
	 * @return
	 */
	public List selectFollowUserDynamicByUserId(String userId);
	
	/**
	 * 根据用户id获取该用户的动态列表
	 * @param userId
	 * @return
	 */
	public List selectMyDynamicByUserId(String userId);
	
	
	/**
	 * 根据用户id获取好友动态信息
	 * @param userId
	 * @return
	 */
	public Map selectDynamicById(Map paramMap);
	
	/**
	 * 获取所有商友动态信息
	 * @param userId
	 * @return
	 */
	public List selectAllUserDynamicByUserId(Map paramMap);
	
	/**
	 * 获取所有商友动态信息
	 * @return
	 */
	public List selectAllDynamic();
	
	/**
	 * 获取访问商友动态信息
	 * @param userId
	 * @return
	 */
	public List selectVisitUserDynamicByParam(Map paramMap);
	
	/**
	 * 获取新动态数量
	 * @param userId
	 * @return
	 */
	public int selectCountAllUserDynamicByUserId(Map paramMap);
	
}
