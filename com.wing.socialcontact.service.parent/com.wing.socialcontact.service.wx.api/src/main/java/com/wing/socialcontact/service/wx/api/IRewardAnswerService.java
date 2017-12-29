package com.wing.socialcontact.service.wx.api;

import java.util.List;
import java.util.Map;

import com.wing.socialcontact.common.model.DataGrid;
import com.wing.socialcontact.common.model.PageParam;
import com.wing.socialcontact.service.wx.bean.RewardAnswer;


/**
 * 
 * @author zhangfan
 * @date 2017-06-14 06:47:31
 * @version 1.0
 */
public interface IRewardAnswerService{

	/**
	 * 查询后台列表
	 * @return
	 */
	public DataGrid selectAllRA(PageParam param,RewardAnswer ra,String question,String fbUserName,
			String ydUserName,String startTime,String endTime);
	/**
	 * 添加
	 * @param bd
	 * @return
	 */
	public String addRA(RewardAnswer ra);
	/**
	 * 更新
	 * @param bd
	 * @return
	 */
	public String updateRA(RewardAnswer ra);
	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	public boolean deleteRAs(String[] ids);

	/**
	 * 根据id查询缓存
	 * @param key
	 * @return
	 */
	public RewardAnswer selectByPrimaryKey(String key);
	
	public RewardAnswer selectById(String id);

	/**
	 * 通过fkid查找应答列表
	 * @param fkId
	 * @return
	 */
	public List<Map<String, Object>> selectRAByFkId(String fkId,Integer isAccept);
	
	/**
	 * 根据id查询
	 * @param id
	 * @return
	 */
	public Map<String, Object> selectRAById(String id);

}