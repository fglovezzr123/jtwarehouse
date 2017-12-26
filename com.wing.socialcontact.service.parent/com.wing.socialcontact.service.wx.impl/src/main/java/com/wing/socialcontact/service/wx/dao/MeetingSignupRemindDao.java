package com.wing.socialcontact.service.wx.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.wing.socialcontact.service.wx.bean.MeetingSignupRemind;

import tk.mybatis.mapper.common.Mapper;

/**
 * 
 * @author liangwj
 * @version 1.0
 */
@Repository
public interface MeetingSignupRemindDao extends Mapper<MeetingSignupRemind> {
	/**
	 * 分页查询
	 * @param parm
	 * @return
	 */
	List<MeetingSignupRemind> selectByParam(MeetingSignupRemind t);
	/**
	 * 查询待提醒的会议报名信息
	 * @return
	 */
	List<MeetingSignupRemind> selectMeetingRemind();
	/**
	 * 根据会议ID和用户ID查询预报名信息
	 * @param meetingId
	 * @param userId
	 * @return
	 */
	MeetingSignupRemind selectByMeetingIdAndUserId(@Param("meetingId") String meetingId, @Param("userId") String userId);
}
