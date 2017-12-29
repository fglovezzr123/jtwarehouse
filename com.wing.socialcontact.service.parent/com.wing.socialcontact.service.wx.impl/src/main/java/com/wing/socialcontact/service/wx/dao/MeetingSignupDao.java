package com.wing.socialcontact.service.wx.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import tk.mybatis.mapper.common.Mapper;
import com.wing.socialcontact.service.wx.bean.MeetingSignup;

/**
 * 
 * @author liangwj
 * @version 1.0
 */
@Repository
public interface MeetingSignupDao extends Mapper<MeetingSignup> {
	/**
	 * 分页查询
	 * @param parm
	 * @return
	 */
	List selectByParam(Map parm);
	/**
	 * 唯一约束条件查询
	 * @param userId
	 * @param meetingId
	 * @return
	 */
	MeetingSignup selectByMeetingIdAndUserId(@Param("userId") String userId, @Param("meetingId") String meetingId);
	/**
	 * 会议开始未提醒的数据查询(会议开始前一天的数据)
	 * @return
	 */
	List<MeetingSignup> selectUnRemind();
	/**
	 * 报名用户支付状态修改
	 * @param id 
	 * @return
	 */
	int changeOrderStatusById(@Param("id") String id);
}
