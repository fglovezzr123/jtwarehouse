package com.wing.socialcontact.service.wx.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import tk.mybatis.mapper.common.Mapper;
import com.wing.socialcontact.service.wx.bean.Topic;

/**
 * 
 * @author zhangfan
 * @date 2017-03-29 11:34:46
 * @version 1.0
 */
@Repository
public interface TopicDao extends Mapper<Topic> {
	
	List selectByParam(Map parm);
	
	List<Map<String, Object>> selectHotList(Map parm);
	
	List<Map<String, Object>> selectFireList(Map parm);
	
	List<Map<String, Object>> selectNewList(Map parm);
	
	List<Map<String, Object>> selectTopicById(String id);
	
	List<Map<String, Object>> selectMyTopic(Map parm);
	
	Integer attentionCount(Map parm);
	
	List<Map<String, Object>> selectJxTopic(Map parm);
	
	List<Map<String, Object>> selectMyAttention(Map parm);
	
	List<Map<String, Object>> selectMyVote(Map parm);
	
	List<Map<String, Object>> selectMyTopicMy(Map parm);

	List selectMyVoteApp(Map parm);

	List selectMyAttentionApp(Map parm);
	
}
