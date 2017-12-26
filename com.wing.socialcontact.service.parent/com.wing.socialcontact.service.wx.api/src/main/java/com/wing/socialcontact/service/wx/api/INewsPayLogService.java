package com.wing.socialcontact.service.wx.api;

import java.util.List;

import com.wing.socialcontact.service.wx.bean.NewsPayLog;
/**
 * 
 * @author zhangfan
 * @date 2017-06-28 09:31:34
 * @version 1.0
 */
public interface INewsPayLogService{

	/**
	 * 查询
	 * @param userId
	 * @param fkId
	 * @return
	 */
	public List selectPayLog(String userId,String fkId);

	/**
	 * 添加
	 */
	public String addNewsPayLog(NewsPayLog npl);
	/**
	 * 更新
	 */
	public String updateNewsPayLog(NewsPayLog npl);

	public NewsPayLog selectByPrimaryKey(String key);
	
	public NewsPayLog selectById(String id);
}