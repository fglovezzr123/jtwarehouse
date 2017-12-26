package com.wing.socialcontact.service.wx.api;

import com.wing.socialcontact.common.model.DataGrid;
import com.wing.socialcontact.common.model.PageParam;
import com.wing.socialcontact.service.wx.bean.UserEmpiricalLog;
import com.wing.socialcontact.service.wx.bean.UserEmpiricalLog;

/**
 * 
 * @author gaojun
 * @date 2017-07-04 11:25:52
 * @version 1.0
 */
public interface IUserEmpiricalLogService{
	/**
	 * 条件查询
	 * 
	 * @param param
	 * @param role
	 * @return
	 */
	public DataGrid selectUserEmpiricalLogs(PageParam param, UserEmpiricalLog userEmpiricalLog,String nickname,String mobile,String userId);
	
	/**
	 * 批量删除
	 * @param id
	 * @return
	 */
	public boolean deleteUserEmpiricalLog(String[] ids);
	/**
	 * 新增
	 * @param dto
	 * @return
	 */
	public String addUserEmpiricalLog(UserEmpiricalLog dto);
	/**
	 * 更新
	 * @param dto
	 * @return
	 */
	public String updateUserEmpiricalLog(UserEmpiricalLog dto);
	
	
	public UserEmpiricalLog selectById(String id);
	
	public UserEmpiricalLog selectByPrimaryKey(String key);
	/**
	 *是否有记录（只有一条记录）
	 * @param userId
	 * @param code
	 * @return
	 */

	public UserEmpiricalLog selectOneTimes(String userId, String taskCode);
	/**
	 *当天是否有记录（当天只有一条记录）
	 * @param userId
	 * @param code
	 * @return
	 */
	public UserEmpiricalLog selectOneDay(String userId, String taskCode);
	/**
	 *当年是否有记录（当年只有一条记录）
	 * @param userId
	 * @param code
	 * @return
	 */
	public UserEmpiricalLog selectOneYear(String userId, String taskCode);
	
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