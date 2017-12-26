package com.wing.socialcontact.service.wx.api;

import java.util.List;
import java.util.Map;

import com.wing.socialcontact.common.model.DataGrid;
import com.wing.socialcontact.common.model.PageParam;
import com.wing.socialcontact.service.wx.bean.BusinessDisscuss;

/**
 * 
 * @author zhangfan
 * @date 2017-04-21 10:59:49
 * @version 1.0
 */
public interface IBusinessDisscussService{
	/**
	 * 查询后台列表
	 * @param param
	 * @param business
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public DataGrid selectAllBD(PageParam param,BusinessDisscuss bd,String titles,String startTime,String endTime);
	
	/**
	 * 添加
	 * @param bd
	 * @return
	 */
	public String addBD(BusinessDisscuss bd);
	/**
	 * 更新
	 * @param bd
	 * @return
	 */
	public String updateBD(BusinessDisscuss bd);
	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	public boolean deleteBDs(String[] ids);

	/**
	 * 根据id查询缓存
	 * @param key
	 * @return
	 */
	public BusinessDisscuss selectByPrimaryKey(String key);
	
	public BusinessDisscuss selectById(String id);
	/**
	 * 前台查询合作洽谈
	 * @param queryRows
	 * @param titles
	 * @param bizType
	 * @return
	 */
	public List selectFrontBD(Integer queryRows,String titles,String bizType,Integer isRecommend);
	/**
	 * 根据id查询
	 * @param id
	 * @return
	 */
	public Map<String, Object> selectBDById(String id);
	/**
	 * 通过fkid查找商洽列表
	 * @param fkId
	 * @return
	 */
	public List<Map<String, Object>> selectBDByFkId(String fkId,Integer isAccept);
	
	/**
	 * 查询我发布的商洽
	 * @param page
	 * @param size
	 * @param curruserId
	 * @return
	 */
	public List selectMyBD(Integer page,Integer size,String curruserId);

}