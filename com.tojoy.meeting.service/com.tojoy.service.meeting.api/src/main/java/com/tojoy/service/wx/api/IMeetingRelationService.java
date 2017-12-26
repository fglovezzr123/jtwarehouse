package com.tojoy.service.wx.api;

import com.tojoy.common.model.DataGrid;
import com.tojoy.common.model.PageParam;
import com.tojoy.service.wx.bean.Meeting;
import com.tojoy.service.wx.bean.MeetingRelation;
import com.tojoy.service.wx.bean.MeetingWhitelist;

import java.util.List;

/**
 * 
 * @author liangwj
 * @date 2017-04-04 00:12:35
 * @version 1.0
 */
public interface IMeetingRelationService {

	/**
	 * 新增
	 * @param list
	 * @author liangwj
	 * @date 2017-04-04 00:12:35
	 */
	public int addRecordBatch(List<MeetingRelation> list);

	/**
	 * 删除
	 * @param meetingId
	 * @return
	 * @author liangwj
	 * @date 2017-04-04 00:12:35
	 */
	public int deleteByMeetingId(String meetingId);

	/**
	 * 查询 
	 * @param meetingId
	 * @return
	 * @author liangwj
	 * @date 2017-04-04 00:12:35
	 */
	public List<MeetingRelation> queryMeetingRelationByMeetingId(String meetingId);

}