package com.wing.socialcontact.service.wx.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import tk.mybatis.mapper.common.Mapper;

import com.wing.socialcontact.service.wx.bean.MeetingSignup;
import com.wing.socialcontact.service.wx.bean.UserIntegralLog;

/**
 * 
 * @author gaojun
 * @date 2017-07-04 11:25:54
 * @version 1.0
 */
@Repository
public interface UserIntegralLogDao extends Mapper<UserIntegralLog> {
	List selectByParam(Map parm);
	
	/**
	 *是否有记录（只有一条记录）
	 * @param userId
	 * @param code
	 * @return
	 */
	UserIntegralLog selectOneTimes(@Param("userId") String userId, @Param("taskCode") String taskCode);
	/**
	 *当天是否有记录（当天只有一条记录）
	 * @param userId
	 * @param code
	 * @return
	 */
	UserIntegralLog selectOneDay(@Param("userId") String userId, @Param("taskCode") String taskCode);
	/**
	 *当年是否有记录（当年只有一条记录）
	 * @param userId
	 * @param code
	 * @return
	 */
	UserIntegralLog selectOneYear(@Param("userId") String userId, @Param("taskCode") String taskCode);
	
	/**
	 *累积当天的积分
	 * @param userId
	 * @param code
	 * @return
	 */
	Integer  selectOneDaySum(@Param("userId") String userId, @Param("taskCode") String taskCode);
	/**
	 *累积当年的积分
	 * @param userId
	 * @param code
	 * @return
	 */
	Integer  selectOneYearSum(@Param("userId") String userId, @Param("taskCode") String taskCode);
}
