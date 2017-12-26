package com.wing.socialcontact.service.wx.api;

import java.util.List;
import java.util.Map;

import com.wing.socialcontact.common.model.DataGrid;
import com.wing.socialcontact.common.model.PageParam;

import com.wing.socialcontact.service.wx.bean.MeetingSignup;
import com.wing.socialcontact.service.wx.bean.MeetingSignupRemind;

/**
 * 
 * @author liangwj
 * @version 1.0
 */
public interface IMeetingSignupService{
	/**
	 * 查询
	 * @param userId
	 * @param meetingId
	 * @author liangwj
	 */
	public MeetingSignup selectByMeetingIdAndUserId(String userId,String meetingId);
	/**
	 * 新增
	 * @param t
	 * @author liangwj
	 */
	public int insertMeetingSignup(MeetingSignup t);
	/**
	 * 修改
	 * @param t
	 * @return
	 * @author liangwj
	 */
	public int updateMeetingSignup(MeetingSignup t);
	/**
	 * 删除
	 * @param t
	 * @return
	 * @author liangwj
	 */
	public int deleteMeetingSignup(MeetingSignup t);
	/**
	 * 查询 
	 * @param t
	 * @return
	 * @author liangwj
	 */
	public List<MeetingSignup> queryMeetingSignup(MeetingSignup t);
	/**
	 * 单个查询
	 * @param t
	 * @return
	 * @author liangwj
	 */
	public MeetingSignup getMeetingSignup(String id);
	/**
	 * 分页查询
	 * @param param
	 * @param t
	 * @return
	 * @author liangwj
	 */
	public DataGrid selectAllMeetingSignup(PageParam param, Map<String,Object> parm);
	/**
	 * 待提醒会议报名信息新增
	 * @param param
	 * @param t
	 * @return
	 * @author liangwj
	 */
	public int insertMeetingSignupRemind(MeetingSignupRemind t);
	/**
	 *  会议预报名提醒报名信息更新
	 * @param param
	 * @param t
	 * @return
	 * @author liangwj
	 */
	public int updateMeetingSignupRemind(MeetingSignupRemind t);
	/**
	 * 待提醒会议报名信息查询
	 * @param param
	 * @param t
	 * @return
	 * @author liangwj
	 */
	public DataGrid selectAllMeetingSignupRemind(PageParam param);
	/**
	 * 根据会议ID和用户ID查询预报名信息
	 * @param meetingId
	 * @param userId
	 * @return
	 */
	public MeetingSignupRemind getMeetingSignupRemindByMeetingIdAndUserId(String meetingId,String userId);
	/**
	 * 会议开始未提醒的数据查询(会议开始前一天的数据)
	 * @return
	 */
	public List<MeetingSignup> selectUnRemind();
	/**
	 * 报名提醒用户
	 * @param param
	 * @param t
	 * @return
	 */
	public DataGrid selectAllMeetingSignupRemind(PageParam param, MeetingSignupRemind t);
	/**
	 *  报名用户支付状态修改
	 * @param id 
	 * @return
	 */
	public int updateForChangestatus(String id);
}