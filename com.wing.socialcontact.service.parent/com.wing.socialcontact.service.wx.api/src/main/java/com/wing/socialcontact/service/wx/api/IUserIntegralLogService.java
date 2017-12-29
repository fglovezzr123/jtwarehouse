package com.wing.socialcontact.service.wx.api;

import java.util.List;
import java.util.Map;

import com.wing.socialcontact.common.model.DataGrid;
import com.wing.socialcontact.common.model.PageParam;
import com.wing.socialcontact.service.wx.bean.UserIntegralEmpirical;
import com.wing.socialcontact.service.wx.bean.UserIntegralLog;

/**
 * 
 * @author gaojun
 * @date 2017-07-04 11:25:54
 * @version 1.0
 */
public interface IUserIntegralLogService{

	/**
	 * 条件查询
	 * 
	 * @param param
	 * @param role
	 * @return
	 */
	public DataGrid selectUserIntegralLogs(PageParam param, UserIntegralLog userIntegralLog,String nickname,String mobile,String userId);
	
	public List<Map<String, Object>> selectIntegralLogPageByUserId(String userId, int pageNum, int pageSize);
	
	/**
	 * 批量删除
	 * @param id
	 * @return
	 */
	public boolean deleteUserIntegralLog(String[] ids);
	/**
	 * 新增
	 * @param dto
	 * @return
	 */
	public String addUserIntegralLog(UserIntegralLog dto);
	/**
	 * 更新
	 * @param dto
	 * @return
	 */
	public String updateUserIntegralLog(UserIntegralLog dto);
	
	
	public UserIntegralLog selectById(String id);
	
	public UserIntegralLog selectByPrimaryKey(String key);
	/**
	 * 根据任务编码和用户修积分和经验值
	 * @param dto
	 * @return
	 */
	public boolean addLntAndEmp(String userId,String code);
	
	public boolean addLntAndEmp2(String userId,String code,Double amount);
	/*inviteUserId 邀请人 userId：被邀请人*/
	//public boolean addLntAndEmp3(String userId,String code,String inviteUserId);
	
	
	
	/**
	 *是否有记录（只有一条记录）
	 * @param userId
	 * @param code
	 * @return
	 */

	public UserIntegralLog selectOneTimes(String userId, String taskCode);
	/**
	 *当天是否有记录（当天只有一条记录）
	 * @param userId
	 * @param code
	 * @return
	 */
	public UserIntegralLog selectOneDay(String userId, String taskCode);
	/**
	 *当年是否有记录（当年只有一条记录）
	 * @param userId
	 * @param code
	 * @return
	 */
	public UserIntegralLog selectOneYear(String userId, String taskCode);
	
	/**
	 *累积当天的积分
	 * @param userId
	 * @param code
	 * @return
	 */
	public Integer  selectOneDaySum(String userId, String taskCode);
	/**
	 *累积当年的积分
	 * @param userId
	 * @param code
	 * @return
	 */
	public Integer  selectOneYearSum(String userId, String taskCode);



}