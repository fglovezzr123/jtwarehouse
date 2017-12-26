package com.wing.socialcontact.service.im.api;

import java.util.List;

/**
 * 
 * @author xuxinyuan
 * @date 2017-04-03 18:37:16
 * @version 1.0
 */
public interface IImGroupfavService{

	/**
	 * 根据组id获取用户列表
	 * @param groupId
	 * @return
	 */
	public List findFavListByGroupId(String groupId);
	
	/**
	 * 更新标签新
	 * @param groupId
	 * @param favs
	 * @return
	 */
	public String updateFavListByGroupId(String groupId,String[] favs);
	
	/**
	 * 插入标签
	 * @param groupId
	 * @param favs
	 * @return
	 */
	public String insertFavs(String groupId,String[] favs);

}