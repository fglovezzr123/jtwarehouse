package com.wing.socialcontact.service.wx.api;

import com.wing.socialcontact.common.model.DataGrid;
import com.wing.socialcontact.common.model.PageParam;
import com.wing.socialcontact.service.wx.bean.MessageBulk;
import com.wing.socialcontact.service.wx.bean.Template;

/**
 * 
 * @author gaojun
 * @date 2017-06-28 11:18:36
 * @version 1.0
 */
public interface IMessageBulkService{

	/**
	 * 条件查询
	 * 
	 * @param param
	 * @param role
	 * @return
	 */
	public DataGrid selectMessageBulks(PageParam param, MessageBulk messageBulk,String startTimef,String endTimef);
	
	/**
	 * 批量删除消息群发
	 * @param id
	 * @return
	 */
	public boolean deleteMessageBulk(String[] ids);
	/**
	 * 新增消息群发
	 * @param dto
	 * @return
	 */
	public String addMessageBulk(MessageBulk dto);
	/**
	 * 更新消息群发
	 * @param dto
	 * @return
	 */
	public String updateMessageBulk(MessageBulk dto);
	
	
	public MessageBulk selectById(String id);

}