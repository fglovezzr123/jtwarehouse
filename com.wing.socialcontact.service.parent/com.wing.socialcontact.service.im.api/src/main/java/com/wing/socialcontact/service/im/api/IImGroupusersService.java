package com.wing.socialcontact.service.im.api;

import java.util.List;
import java.util.Map;

import com.wing.socialcontact.common.model.DataGrid;
import com.wing.socialcontact.common.model.PageParam;
import com.wing.socialcontact.service.im.bean.ImGroupusers;

/**
 * 
 * @author xuxinyuan
 * @date 2017-03-29 15:27:44
 * @version 1.0
 */
public interface IImGroupusersService {

	/**
	 * 添加人员
	 * 
	 * @param groupId
	 * @param userIds
	 * @return
	 */
	public String insertUser(String groupId, String[] userIds);

	/**
	 * 删除用户信息
	 * 
	 * @param groupId
	 * @param userIds
	 * @return
	 */
	public String delUser(String groupId, String[] userIds);
	public String delUser2(String groupId);

	/**
	 * 根据组id获取用户列表
	 * 
	 * @param groupId
	 * @return
	 */
	public List findUserListByGroupId(String groupId);
	/**
	 * 根据组id获取用户列表,包含群主
	 * 
	 * @param groupId
	 * @return
	 */
	public List findUserListByGroupIdHasOwner(String groupId);
	
	public DataGrid findUserListByGroupId2(PageParam param,Map paramMap);
	
	/**
	 * 根据组id获取用户数量
	 * 
	 * @param groupId
	 * @return
	 */
	public int findUserCountByGroupId(String groupId);

	/**
	 * 群置顶
	 * 
	 * @param groupId
	 * @param userId
	 * @param status
	 *            1 置顶 0 取消置顶
	 * @return
	 */
	public String isTop(String groupId, String userId, String status);

	/**
	 * 群打扰
	 * 
	 * @param groupId
	 * @param userId
	 *            0 可打扰 1 免打扰
	 * @param status
	 * @return
	 */
	public String msgDistrub(String groupId, String userId, String status);

	/**
	 * 退群
	 * 
	 * @param groupId
	 * @param userId
	 * @return
	 */
	public String backGroup(String groupId, String userId);

	/**
	 * 根据用户编号及群号获取群通讯录
	 * 
	 * @Title: findByUserAndGroupId
	 * @Description: TODO
	 * @param toUserId
	 * @param formGroupId
	 * @return
	 * @return: ImGroupusers
	 * @author: zengmin
	 * @date: 2017年6月5日 上午11:21:23
	 */
	public ImGroupusers findByUserAndGroupId(String toUserId, String formGroupId);

}