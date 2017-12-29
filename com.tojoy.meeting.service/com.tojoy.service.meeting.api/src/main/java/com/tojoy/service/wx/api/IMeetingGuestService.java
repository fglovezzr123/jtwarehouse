package com.tojoy.service.wx.api;

import java.util.List;
import com.tojoy.common.model.DataGrid;
import com.tojoy.common.model.PageParam;

import com.tojoy.service.wx.bean.MeetingGuest;

/**
 * 
 * @author liangwj
 * @date 2017-04-04 00:12:35
 * @version 1.0
 */
public interface IMeetingGuestService{
	/**
	 * 新增
	 * @param t
	 * @author liangwj
	 * @date 2017-04-04 00:12:35
	 */
	public int insertMeetingGuest(MeetingGuest t);
	/**
	 * 修改
	 * @param t
	 * @return
	 * @author liangwj
	 * @date 2017-04-04 00:12:35
	 */
	public int updateMeetingGuest(MeetingGuest t);
	/**
	 * 删除
	 * @param t
	 * @return
	 * @author liangwj
	 * @date 2017-04-04 00:12:35
	 */
	public int deleteMeetingGuest(MeetingGuest t);
	/**
	 * 查询 
	 * @param t
	 * @return
	 * @author liangwj
	 * @date 2017-04-04 00:12:35
	 */
	public List<MeetingGuest> queryMeetingGuest(MeetingGuest t);
	/**
	 * 单个查询
	 * @param t
	 * @return
	 * @author liangwj
	 * @date 2017-04-04 00:12:35
	 */
	public MeetingGuest getMeetingGuest(String id);
	/**
	 * 分页查询
	 * @param param
	 * @param t
	 * @return
	 * @author liangwj
	 * @date 2017-04-04 00:12:35
	 */
	public DataGrid selectAllMeetingGuest(PageParam param, MeetingGuest t);
}