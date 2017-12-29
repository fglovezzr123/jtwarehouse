package com.wing.socialcontact.service.wx.api;

import java.util.List;

import com.wing.socialcontact.common.model.DataGrid;
import com.wing.socialcontact.common.model.PageParam;
import com.wing.socialcontact.service.wx.bean.RewardClass;

/**
 * 
 * @author zhangfan
 * @date 2017-06-12 13:25:02
 * @version 1.0
 */
public interface IRewardClassService{

	/**
	 * 查询出分类
	 * @return
	 */
	public List selectAllClass(Integer queryRows,Integer isRecommend);
	/**
	 * 条件查询
	 * @return
	 */
	public DataGrid selectClass(PageParam param,RewardClass rClass);
	/**
	 * 添加
	 * @param role
	 * @return
	 */
	public String addClass(RewardClass rClass);
	/**
	 * 更新
	 * @param role
	 * @return
	 */
	public String updateClass(RewardClass rClass);
	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	public boolean deleteClass(String[] ids);

	public RewardClass selectByPrimaryKey(String key);
	
	public RewardClass selectById(String id);
	
	public String deleteClass(String id);

}