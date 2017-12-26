package com.wing.socialcontact.service.wx.api;

import java.util.List;

import com.wing.socialcontact.service.wx.bean.Vote;

/**
 * 
 * @author zhangfan
 * @date 2017-04-13 10:46:02
 * @version 1.0
 */
public interface IVoteService{

	/**
	 * 添加
	 * @param vote
	 * @return
	 */
	public String addVote(Vote vote);
	/**
	 * 更新
	 * @param vote
	 * @return
	 */
	public String updateVote(Vote vote);
	/**
	 * 根据id查询(缓存)
	 * @param key
	 * @return
	 */
	public Vote selectByPrimaryKey(String key);
	
	public Vote selectById(String id);
	
	public String deleteVote(String id);
	/**
	 * 查出用户的观点
	 * @param userId
	 * @param fkId
	 * @return
	 */
	public String selectMyVoteType(String userId,String fkId);

}