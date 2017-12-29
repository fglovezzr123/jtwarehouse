package com.tojoy.service.wx.api;

import java.util.List;

import com.tojoy.service.wx.bean.MeetingProject;
import com.tojoy.common.model.DataGrid;
import com.tojoy.common.model.PageParam;

/**
 * 
 * @author liangwj
 * @date 2017-04-04 00:12:35
 * @version 1.0
 */
public interface IMeetingProjectService{
	/**
	 * 新增
	 * @param t
	 * @author liangwj
	 * @date 2017-04-04 00:12:35
	 */
	public int insertMeetingProject(MeetingProject t);
	/**
	 * 修改
	 * @param t
	 * @return
	 * @author liangwj
	 * @date 2017-04-04 00:12:35
	 */
	public int updateMeetingProject(MeetingProject t);
	/**
	 * 删除
	 * @param t
	 * @return
	 * @author liangwj
	 * @date 2017-04-04 00:12:35
	 */
	public int deleteMeetingProject(MeetingProject t);
	/**
	 * 查询 
	 * @param t
	 * @return
	 * @author liangwj
	 * @date 2017-04-04 00:12:35
	 */
	public List<MeetingProject> queryMeetingProject(MeetingProject t);
	/**
	 * 单个查询
	 * @param t
	 * @return
	 * @author liangwj
	 * @date 2017-04-04 00:12:35
	 */
	public MeetingProject getMeetingProject(String id);
	/**
	 * 分页查询
	 * @param param
	 * @param t
	 * @return
	 * @author liangwj
	 * @date 2017-04-04 00:12:35
	 */
	public DataGrid selectAllMeetingProject(PageParam param, MeetingProject t);
}