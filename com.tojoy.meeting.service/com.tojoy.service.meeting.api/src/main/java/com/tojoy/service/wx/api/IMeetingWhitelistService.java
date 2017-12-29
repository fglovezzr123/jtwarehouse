package com.tojoy.service.wx.api;

import java.util.List;

import com.tojoy.common.model.DataGrid;
import com.tojoy.common.model.PageParam;
import com.tojoy.service.wx.bean.MeetingWhitelist;

/**
 * 
 * @author liangwj
 * @version 1.0
 */
public interface IMeetingWhitelistService{
	/**
	 * 新增
	 * @param t
	 * @author liangwj
	 */
	public int insertMeetingWhitelist(MeetingWhitelist t);
	/**
	 * 修改
	 * @param t
	 * @return
	 * @author liangwj
	 */
	public int updateMeetingWhitelist(MeetingWhitelist t);
	/**
	 * 修改
	 * @param t
	 * @return
	 * @author liangwj
	 */
	public int updateSelectiveMeetingWhitelist(MeetingWhitelist t);
	/**
	 * 删除
	 * @param t
	 * @return
	 * @author liangwj
	 */
	public int deleteMeetingWhitelist(MeetingWhitelist t);
	/**
	 * 查询 
	 * @param t
	 * @return
	 * @author liangwj
	 */
	public List<MeetingWhitelist> queryMeetingWhitelist(MeetingWhitelist t);
	/**
	 * 单个查询
	 * @param t
	 * @return
	 * @author liangwj
	 */
	public MeetingWhitelist getMeetingWhitelist(String id);
	/**
	 * 分页查询
	 * @param param
	 * @param t
	 * @return
	 * @author liangwj
	 */
	public DataGrid selectAllMeetingWhitelist(PageParam param, MeetingWhitelist t);
	/**
	 * 添加会议白名单
	 * @param meetingid
	 * @param ids
	 * @return
	 */
	public String insertWhitelists(String meetingId,String ids);
	/**
	 * 批量删除会议白名单
	 * @param ids
	 * @return
	 */
	public String deleteMeetingWhitelists(String ids);
}