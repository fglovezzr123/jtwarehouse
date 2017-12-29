package com.wing.socialcontact.service.wx.api;

import com.wing.socialcontact.common.model.DataGrid;
import com.wing.socialcontact.common.model.PageParam;
import com.wing.socialcontact.service.wx.bean.LeaveMsg;

/**
 * 客服留言
 * 
 * @author zengmin
 * @date 2017-04-28 23:41:19
 * @version 1.0
 */
public interface ILeaveMsgService {

	/**
	 * 企服云条件查询客户留言
	 * @param param
	 * @param nickname
	 * @param leaveMsg
	 * @return
	 */
	public DataGrid selectAllLeaveMsg(PageParam param, String nickname,
			LeaveMsg leaveMsg);
	
	public LeaveMsg selectByPrimaryKey(String key);

	public LeaveMsg selectById(String id);

	public boolean addLeaveMsg(LeaveMsg leaveMsg);

	public boolean deleteMsgById(String id);
	
	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	public boolean deleteMsgs(String[] ids);

	
	

}