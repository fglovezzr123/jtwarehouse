package com.tojoy.service.wx.dao;

import com.tojoy.service.wx.bean.MeetingGuest;
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
public interface MeetingGuestDao extends Mapper<MeetingGuest> {
	/**
	 * 分页查询
	 * @param parm
	 * @return
	 */
	List selectByParam(Map parm);
	/**
	 * 根据会议ID查询会议嘉宾
	 * @param id
	 * @return
	 */
	List<MeetingGuest> selectByMeetingId(String meetingId);
	void deleteByMeetingId(String meetingId);
}
