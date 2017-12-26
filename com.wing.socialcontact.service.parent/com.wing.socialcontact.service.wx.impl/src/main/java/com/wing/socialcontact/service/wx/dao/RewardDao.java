package com.wing.socialcontact.service.wx.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import tk.mybatis.mapper.common.Mapper;
import com.wing.socialcontact.service.wx.bean.Reward;

/**
 * 
 * @author zhangfan
 * @date 2017-06-12 20:37:47
 * @version 1.0
 */
@Repository
public interface RewardDao extends Mapper<Reward> {
	
	List selectByParam(Map parm);
	List selectByParam2(Map parm);
	
	List<Map<String, Object>> selectRewardById(String id);
	
	List selectPastReward();
	
	List selectFrontByParam(Map parm);
	
	List<Map<String, Object>> selectMyAttention(Map parm);
	
	List selectZjTopList(Map parm);
	
	List selectXsTopList(Map parm);
	
	List selectYdTopList(Map parm);
	
	List selectZjPm(Map parm);
	
	List selectXsPm(Map parm);
	
	List selectYdPm(Map parm);
	
	List selectMyReward(Map parm);
}
