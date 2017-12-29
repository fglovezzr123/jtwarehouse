package com.tojoy.service.wx.api;

import java.util.List;

import com.tojoy.service.wx.bean.Meeting;
import com.tojoy.common.model.DataGrid;
import com.tojoy.common.model.PageParam;

import com.tojoy.service.wx.bean.MeetingWhitelist;

/**
 * 
 * @author liangwj
 * @date 2017-04-04 00:12:35
 * @version 1.0
 */
public interface IMeetingService{
	/**
	 * 新增
	 * @param t
	 * @author liangwj
	 * @date 2017-04-04 00:12:35
	 */
	public int insertMeeting(Meeting t);
	/**
	 * 修改
	 * @param t
	 * @return
	 * @author liangwj
	 * @date 2017-04-04 00:12:35
	 */
	public int updateMeeting(Meeting t);
	/**
	 * 删除
	 * @param t
	 * @return
	 * @author liangwj
	 * @date 2017-04-04 00:12:35
	 */
	public int deleteMeeting(Meeting t);
	/**
	 * 查询 
	 * @param t
	 * @return
	 * @author liangwj
	 * @date 2017-04-04 00:12:35
	 */
	public List<Meeting> queryMeeting(Meeting t);
	/**
	 * 单个查询
	 * @param t
	 * @return
	 * @author liangwj
	 * @date 2017-04-04 00:12:35
	 */
	public Meeting getMeeting(String id);
	/**
	 * 分页查询
	 * @param param
	 * @param t
	 * @return
	 * @author liangwj
	 * @date 2017-04-04 00:12:35
	 */
	public DataGrid selectAllMeeting(PageParam param, Meeting t);
	/**
	 * vhall k生成
	 * @param openK 是否开启k认证 1开启0不开启
	 * @param tjyUserId
	 * @param webinar_id
	 * @return
	 */
	public String createVhallKey(String openK,String tjyUserId,String webinar_id);
	/**
	 * vhall k验证
	 * @param k
	 * @return
	 */
	public String validVhallKey(String k);
	/**
	 * 关注时间倒序查询我关注的会议
	 * @param pageParam
	 * @param param
	 * @return
	 */
	public DataGrid selectMyCollectMeeting(PageParam pageParam, Meeting param);
	/**
	 * 会议白名单查询
	 * @param param
	 * @param t
	 * @return
	 */
	public DataGrid selectAllMeetingWhitelist(PageParam param, MeetingWhitelist t);
	/**
	 * 添加会议白名单
	 * @param meetingId
	 * @param ids
	 * @return
	 */
	public String insertWhitelists(String meetingId,String ids);
	/**
	 * 批量删除白名单
	 * @param ids
	 * @return
	 */
	public String deleteGlobalWhitelists(String ids);
	/**
	 * 是否是白名单用户
	 * @param userId
	 * @return
	 */
	public boolean isWhitelist(String userId, String meetingId);
}