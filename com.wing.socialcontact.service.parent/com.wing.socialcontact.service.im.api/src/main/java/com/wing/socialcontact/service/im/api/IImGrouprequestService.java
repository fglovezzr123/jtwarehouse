package com.wing.socialcontact.service.im.api;

import java.util.List;

import com.wing.socialcontact.service.im.bean.ImGrouprequest;

/**
 * 
 * @author xuxinyuan
 * @date 2017-03-29 17:08:39
 * @version 1.0
 */
public interface IImGrouprequestService {

	/**
	 * 加入群组申请
	 * 
	 * @param groupId
	 * @param userId
	 * @return
	 */
	public String insertGroupUserRequest(String groupId, String userId, String askmessage);

	/**
	 * 处理加群申请 同意后将该用户加入到群用户中
	 * 
	 * @param groupRequestId
	 * @param status
	 *            1待确认2已同意3已拒绝
	 * @return
	 */
	public String updateDualGroupUserRequest(String requestId, String status);

	/**
	 * 获取我创建的群的请求入群信息
	 * 
	 * @param creator
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	public List findMyGroupRequest(String creator, int pageNum, int pageSize);

	/**
	 * 根据Id获取加群申请信息
	 * 
	 * @param requestId
	 * @return
	 */
	public ImGrouprequest findGroupRequestByID(String requestId);

	/**
	 * 获取我新群通知数量
	 * 
	 * @param userId
	 * @return
	 */
	public int findNewGroupRequestCountByUserid(String userId);

	/**
	 * 获取所有请求
	 * 
	 * @param imGrouprequest
	 * @return
	 */
	public List selectAllImGrouprequest(ImGrouprequest imGrouprequest);

}