package com.wing.socialcontact.service.wx.api;

import java.util.List;
import java.util.Map;

import com.wing.socialcontact.common.model.DataGrid;
import com.wing.socialcontact.common.model.PageParam;
import com.wing.socialcontact.service.wx.bean.Topic;

/**
 * 
 * @author zhangfan
 * @date 2017-03-29 11:34:46
 * @version 1.0
 */
public interface ITopicService{

	public DataGrid selectAllTopic(PageParam param,Topic topic,String startTime,String endTime,String createUserId,String userId,Integer isAd);

	public DataGrid selectMyVote(PageParam param, String userId);
	/**
	 * 添加
	 * @param newsClass
	 * @return
	 */
	public String addTopic(Topic topic);
	/**
	 * 更新
	 * @param newsClass
	 * @return
	 */
	public String updateTopic(Topic topic);
	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	public boolean deleteTopics(String[] ids);

	public Topic selectByPrimaryKey(String key);
	
	public Topic selectById(String id);
	/**
	 * 最热
	 * @return
	 */
	public List selectHotList(String titles,String curruserId,Integer page,Integer size);
	/**
	 * 最新
	 * @return
	 */
	public List selectNewList(String titles,String curruserId,Integer page,Integer size);
	/**
	 * 最火
	 * @return
	 */
	public List selectFireList(String titles,String curruserId,Integer page,Integer size);
	
	public Map<String, Object> selectTopicById(String id);
	
	/**
	 * 根据用户id查询话题
	 * @param createUserId
	 * @return
	 */
	public List selectMyTopic(String createUserId,Integer page,Integer size);
	/**
	 * 根据话题id，查询出总关注数
	 * @param parm
	 * @return
	 */
	public Integer attentionCount(String fkId);
	/**
	 * 查询精选话题
	 * @return
	 */
	public List selectJxTopic(String curruserId);
	/**
	 * 查询我的关注
	 * @param userId
	 * @return
	 */
	public List selectMyAttention(String userId);
	/**
	 * 查询我参与的话题
	 * @param userId
	 * @return
	 */
	public List selectMyVote(String userId);
	/**
	 * 个人中心-我发布的话题
	 * @param createUserId
	 * @param page
	 * @param size
	 * @return
	 */
	public List selectMyTopicMy(String createUserId,Integer page,Integer size);

	//===================================================APP接口==================================
	/**
	 * 我参与的话题列表
	 * @param userId
	 * @param parseInt
	 * @param parseInt2
	 * @return
	 */
	public List selectMyVoteApp(String userId, int page, int size);

	/**
	 * 我收藏的话题列表
	 * @param userId
	 * @param parseInt
	 * @param parseInt2
	 * @return
	 */
	public List selectMyAttentionApp(String userId, int page, int size);
}