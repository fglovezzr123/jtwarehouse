package com.wing.socialcontact.im.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.wing.socialcontact.service.im.bean.ImFollow;

import tk.mybatis.mapper.common.Mapper;

/**
 * 
 * @author xuxinyuan
 * @date 2017-03-29 15:31:02
 * @version 1.0
 */
@Repository
public interface ImFollowDao extends Mapper<ImFollow> {

	/**
	 * 获取用户关注列表
	 * @param userId
	 * @return
	 */
	public List findMyFollowUsers(String userId);
	
	/**
	 * 获取用户粉丝列表
	 * @param userId
	 * @return
	 */
	public List findMyFansUsers(String userId);
	
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
	public ImFollow loadById(String id);
	
	/**
	 * 根据用户id和关注用户id获取关注信息
	 * @param paramMap
	 * @return
	 */
	public ImFollow selectByUserIdAndFollowId(Map paramMap);
	
	/**
	 * 获取我关注的用户数
	 * @param userId
	 * @return
	 */
	public int findMyFollowUsersCount(String userId);
	
	/**
	 * 获取我的粉丝数量
	 * @param userId
	 * @return
	 */
	public int findMyFansUsersCount(String userId);
}
