package com.tojoy.service.wx.dao;

import com.tojoy.service.wx.bean.Meeting;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import tk.mybatis.mapper.common.Mapper;

/**
 * 
 * @author liangwj
 * @date 2017-04-04 00:05:28
 * @version 1.0
 */
@Repository
public interface MeetingDao extends Mapper<Meeting> {
	/**
	 * 分页查询
	 * @param parm
	 * @return
	 */
	List<Meeting> selectByParam(Meeting parm);
	/**
	 * 按关注时间倒序查询我关注的会议
	 * @param t
	 * @return
	 */
	List<Meeting> selectMyCollectMeeting(Meeting t);
	/**
	 * 是否是白名单用户
	 * @param userId
	 * @param meetingId
	 * @return
	 */
	int isWhitelist(@Param("userId") String userId,@Param("meetingId") String meetingId);
}
