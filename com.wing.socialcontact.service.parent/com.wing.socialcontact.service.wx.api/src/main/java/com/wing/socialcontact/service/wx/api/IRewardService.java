package com.wing.socialcontact.service.wx.api;

import java.util.List;
import java.util.Map;

import com.wing.socialcontact.common.model.DataGrid;
import com.wing.socialcontact.common.model.PageParam;
import com.wing.socialcontact.service.wx.bean.Reward;

/**
 * 
 * @author zhangfan
 * @date 2017-06-12 20:37:47
 * @version 1.0
 */
public interface IRewardService{

	/**
	 * 查询后台列表
	 * @return
	 */
	public DataGrid selectAllReward(PageParam param,Reward reward,String startTime,String endTime,
			String createUserId,String startTimeyx,String endTimeyx,String userId);
	
	public DataGrid selectAllReward2(PageParam param,Reward reward,String startTime,String endTime,
			String createUserId,String startTimeyx,String endTimeyx,String userId);
	
	/**
	 * 添加
	 * @param reward
	 * @return
	 */
	public String addReward(Reward reward);
	/**
	 * 更新
	 * @return
	 */
	public String updateReward(Reward reward);
	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	public boolean deleteRewards(String[] ids);

	/**
	 * 根据id查询缓存
	 * @param key
	 * @return
	 */
	public Reward selectByPrimaryKey(String key);
	
	public Reward selectById(String id);
	/**
	 * 批量更新审核状态
	 * @param ids
	 * @return
	 */
	public boolean updateStatus(String[] ids);
	/**
	 * 根据id查询
	 * @param id
	 * @return
	 */
	public Map<String, Object> selectRewardById(String id);
	/**
	 * 查询过期的悬赏
	 * @return
	 */
	public List selectPastReward();
	/**
	 * 前台查询悬赏列表
	 * @param page 
	 * @param size
	 * @param question 问题
	 * @param voType   行业
	 * @param createUserId  创建人
	 * @param type     1 高额悬赏,2最新提问,3最热
	 * @param rewardFinish 悬赏状态
	 * @return
	 */
	public List selectFrontReward(Integer page,Integer size,String question,String voType,
			String createUserId,Integer type,Integer rewardFinish,String curruserId);
	
	/**
	 * 查询我的关注
	 * @param userId
	 * @return
	 */
	public List selectMyAttention(String userId,Integer page,Integer size);
	/**
	 * 征集榜
	 * @param top
	 * @return
	 */
	public List selectZjTopList(Integer top);
	/**
	 * 悬赏榜
	 * @param top
	 * @return
	 */
	public List selectXsTopList(Integer top);
	/**
	 * 应答榜
	 * @param top
	 * @return
	 */
	public List selectYdTopList(Integer top);
	//征集榜排名
	public String selectZjPm(String curruserId);
	//悬赏榜排名
	public String selectXsPm(String curruserId);
	//应答榜排名
	public String selectYdPm(String curruserId);
	/**
	 * 查询我参与的悬赏
	 * @param userId
	 * @return
	 */
	public List selectMyReward(String userId,Integer page,Integer size);
	
}