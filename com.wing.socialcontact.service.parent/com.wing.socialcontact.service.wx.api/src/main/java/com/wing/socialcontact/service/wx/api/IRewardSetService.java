package com.wing.socialcontact.service.wx.api;

import java.util.List;

import com.wing.socialcontact.service.wx.bean.RewardSet;

/**
 * 
 * @author zhangfan
 * @date 2017-06-15 12:03:43
 * @version 1.0
 */
public interface IRewardSetService{

	/**
	 * 添加
	 * @param role
	 * @return
	 */
	public String addRewardSet(RewardSet rs);
	/**
	 * 更新
	 * @param role
	 * @return
	 */
	public String updateRewardSet(RewardSet rs);

	public RewardSet selectByPrimaryKey(String key);
	
	public RewardSet selectById(String id);
	
	public List selectRewardSet();

}