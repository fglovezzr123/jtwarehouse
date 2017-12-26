package com.wing.socialcontact.service.wx.api;

import java.util.List;
import java.util.Map;

import com.wing.socialcontact.common.model.DataGrid;
import com.wing.socialcontact.common.model.PageParam;
import com.wing.socialcontact.service.wx.bean.Business;

/**
 * 
 * @author zhangfan
 * @date 2017-04-18 12:01:49
 * @version 1.0
 */
public interface IBusinessService{

	/**
	 * 查询后台列表
	 * @param param
	 * @param business
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public DataGrid selectAllBusiness(PageParam param,Business business,String startTime,String endTime,String createUserId,String userId);
	public DataGrid selectAllBusiness2(PageParam param,Business business,String startTime,String endTime,String createUserId,String userId);
	
	/**
	 * 添加
	 * @param newsClass
	 * @return
	 */
	public String addBusiness(Business business);
	/**
	 * 更新
	 * @param newsClass
	 * @return
	 */
	public String updateBusiness(Business business);
	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	public boolean deleteBusiness(String[] ids);

	/**
	 * 根据id查询缓存
	 * @param key
	 * @return
	 */
	public Business selectByPrimaryKey(String key);
	
	public Business selectById(String id);
	/**
	 * 批量更新审核状态
	 * @param ids
	 * @return
	 */
	public boolean updateStatus(String[] ids);
	/**
	 * 前台查询合作洽谈
	 * @param queryRows
	 * @param titles
	 * @param bizType
	 * @return
	 */
	public List selectFrontBusiness(Integer page,Integer size,String titles,String bizType,Integer isRecommend,
			String createUserId,Integer isReward,String curruserId);
	/**
	 * 根据id查询
	 * @param id
	 * @return
	 */
	public Map<String, Object> selectBusinessById(String id);
	/**
	 * 查询我的合作
	 * @param createUserId
	 * @return
	 */
	public List selectMyBusiness(String createUserId,Integer page,Integer size);
	/**
	 * 查询我的关注
	 * @param userId
	 * @return
	 */
	public List selectMyAttention(String userId,Integer page,Integer size);
	/**
	 * 查询过期的合作
	 * @return
	 */
	public List selectPastBusiness();
}