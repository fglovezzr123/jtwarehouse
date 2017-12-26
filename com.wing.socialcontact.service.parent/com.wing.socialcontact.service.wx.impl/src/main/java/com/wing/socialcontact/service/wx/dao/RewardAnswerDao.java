package com.wing.socialcontact.service.wx.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import tk.mybatis.mapper.common.Mapper;
import com.wing.socialcontact.service.wx.bean.RewardAnswer;

/**
 * 
 * @author zhangfan
 * @date 2017-06-14 06:47:31
 * @version 1.0
 */
@Repository
public interface RewardAnswerDao extends Mapper<RewardAnswer> {
	
	List<Map<String, Object>> selectRAByParam(Map parm);
	
	List selectByParam(Map parm);
	
	List<Map<String, Object>> selectRAById(String id);
}
