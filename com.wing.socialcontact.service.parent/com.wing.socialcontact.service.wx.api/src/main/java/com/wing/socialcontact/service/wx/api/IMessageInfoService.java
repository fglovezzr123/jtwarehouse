package com.wing.socialcontact.service.wx.api;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.wing.socialcontact.common.model.DataGrid;
import com.wing.socialcontact.common.model.PageParam;
import com.wing.socialcontact.service.wx.bean.ImMsgBean;
import com.wing.socialcontact.service.wx.bean.MessageInfo;

/**
 * 
 * @author liangwj
 * @date 2017-03-28 15:00:44
 * @version 1.0
 */
public interface IMessageInfoService {

	/**
	 * 查询出所有分类
	 * 
	 * @return
	 */
	public List selectAllMessageInfo();

	/**
	 * 条件查询
	 * 
	 * @param param
	 * @param role
	 * @return
	 */
	public DataGrid selectMessageInfos(PageParam param, MessageInfo messageInfo);

	/**
	 * 条件查询
	 * 
	 * @param messageInfo
	 * @param role
	 * @return
	 */

	public List selectMessageInfos(MessageInfo messageInfo);

	/**
	 * 添加
	 * 
	 * @param role
	 * @return
	 */
	public String addMessageInfo(MessageInfo messageInfo);

	/**
	 * 更新
	 * 
	 * @param role
	 * @return
	 */
	public String updateMessageInfo(MessageInfo messageInfo);

	/**
	 * 批量删除
	 * 
	 * @param ids
	 * @return
	 */
	public boolean deleteMessageInfos(String[] ids);

	public MessageInfo selectByPrimaryKey(String key);

	public MessageInfo selectById(String id);

	public String getMsgContent(String mark, Map<String, Object> map);

	/**
	 * 发送消息，不存数据库
	 * 
	 * @Title: sendMessage
	 * @Description: TODO
	 * @param messageInfo
	 * @return: void
	 * @author: zengmin
	 * @date: 2017年5月16日 上午10:24:06
	 */
	public void sendImAppMessage(ImMsgBean imMsgBean) throws IOException;
	
	/**
     * 发送消息，不存数据库
     * 
     * @Title: sendMessage
     * @Description: TODO
     * @param messageInfo
     * @return: void
     * @author: zengmin
     * @date: 2017年5月16日 上午10:24:06
     */
    public void sendImMessage(ImMsgBean imMsgBean) throws IOException;

	/**
	 * 企服云app 查询接收方信息(免打扰、token等信息) //TODO 添加方法功能描述
	 * 
	 * @param toUserId
	 * @return
	 */
	public Map selToken(String toUserId);

	/**
	 * 查询单聊是否免打扰 //TODO 添加方法功能描述
	 * 
	 * @param fromUserId
	 * @param toUserId
	 * @return
	 */
	public List<Map> selOneToOneDnd(String fromUserId, String toUserId);

	/**
	 * 分页获取消息列表
	 * 
	 * @Title: selectMessageInfoPageByUserIdAndType
	 * @Description: TODO
	 * @param userId
	 * @param type
	 * @param page
	 * @param pageSize
	 * @return
	 * @return: List
	 * @author: zengmin
	 * @date: 2017年5月27日 下午2:28:02
	 */
	public List selectMessageInfoPageByUserIdAndType(String userId, int type, int page, int pageSize);

	/**
	 * 批量修改用户消息状态
	 * 
	 * @Title: updateStatusByUserIdAndType
	 * @Description: TODO
	 * @param userId
	 * @param type
	 * @return
	 * @return: boolean
	 * @author: zengmin
	 * @date: 2017年5月27日 下午2:39:07
	 */
	public boolean updateStatusByUserIdAndType(String userId, int type);

	public Integer countMessage(String userId, Integer type, int status);

}