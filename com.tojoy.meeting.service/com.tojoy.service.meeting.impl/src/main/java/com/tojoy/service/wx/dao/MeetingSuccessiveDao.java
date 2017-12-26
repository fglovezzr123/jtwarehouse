package com.tojoy.service.wx.dao;

import com.tojoy.service.wx.bean.MeetingRelation;
import com.tojoy.service.wx.bean.MeetingSuccessive;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * 历届会议
 * @author wangyansheng
 * @date 2017-12-04 00:05:28
 * @version 1.0
 */
@Repository
public interface MeetingSuccessiveDao extends Mapper<MeetingSuccessive> {

	/**
	 * 根据会议id查询往届会议
	 * @param meetingId
	 * @return
	 */
	List<MeetingSuccessive> queryMeetingSuccessiveByMeetingId(String meetingId);

	/**
	 * 删除往届会议
	 * @param meetingId
	 * @return
	 */
	int deleteByMeetingId(String meetingId);
}
