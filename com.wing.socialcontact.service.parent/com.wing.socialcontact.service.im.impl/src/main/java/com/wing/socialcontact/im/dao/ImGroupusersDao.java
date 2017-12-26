package com.wing.socialcontact.im.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import tk.mybatis.mapper.common.Mapper;

import com.wing.socialcontact.service.im.bean.ImGroupfav;
import com.wing.socialcontact.service.im.bean.ImGroupusers;

/**
 * 
 * @author xuxinyuan
 * @date 2017-03-29 15:31:02
 * @version 1.0
 */
@Repository
public interface ImGroupusersDao extends Mapper<ImGroupusers> {
	
	/**
	 * 根据用户id数组获取用户信息
	 * @param userIds
	 * @return
	 */
	public List findTjyUsersByUserIds(String[] userIds);
	
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
	public ImGroupusers loadById(String id);
	
	/**
	 * 获取组用户信息（根据组id和用户id）
	 * @param paramMap
	 * @return
	 */
	public ImGroupusers findByGroupAndUser(Map paramMap);
	
	/**
	 * 根据组和用户删除组成员
	 * @param paramMap
	 * @return
	 */
	public int deleteByGroupAndUser(Map paramMap);
	
	/**
	 * 跟剧组id获取用户列表
	 * @param groupId
	 * @return
	 */
	public List findUsersByGroupId(String groupId);
	
	/**
	 * 跟剧组id获取用户列表（不包括群主）
	 * @param groupId
	 * @return
	 */
	public List findUsersByGroupIdNoOwner(String groupId);
	/**
	 * 跟剧组id获取用户列表
	 * @param groupId
	 * @return
	 */
	public List findUsersByGroupId2(Map paramMap);
	
	/**
	 * 根据组id获取组成员数量
	 * @param groupId
	 * @return
	 */
	public int findCountByGroupId(String groupId);

	public List findUserListByGroupIdHasOwner(String groupId);
	/**
	 * 获取组成员
	 * @param groupId
	 * @return
	 */
	public List findListByGroupId(String groupId);
	
}
