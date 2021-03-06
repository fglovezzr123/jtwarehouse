package com.tojoy.service.wx.dao;

import com.tojoy.service.wx.bean.MeetingProject;
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
public interface MeetingProjectDao extends Mapper<MeetingProject> {
	/**
	 * 分页查询
	 * @param parm
	 * @return
	 */
	List selectByParam(Map parm);

	void deleteByMeetingId(String meetingId);
}
