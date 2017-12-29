package com.wing.socialcontact.service.wx.api;

import java.util.List;
import java.util.Map;

import com.wing.socialcontact.common.model.DataGrid;
import com.wing.socialcontact.common.model.PageParam;
import com.wing.socialcontact.service.wx.bean.InviteRecord;

/**
 * 
 * @author zengmin
 * @date 2017-07-13 09:51:06
 * @version 1.0
 */
public interface IInviteRecordService {

	/**
	 * 条件查询
	 * 
	 * @param param
	 * @param newsClass
	 * @return
	 */
	public DataGrid selectAllInviteRecord(PageParam param, InviteRecord inviteRecord);

	/**
	 * 添加
	 * 
	 * @param newsClass
	 * @return
	 */
	public String addInviteRecord(InviteRecord inviteRecord);

	/**
	 * 更新
	 * 
	 * @param newsClass
	 * @return
	 */
	public String updateInviteRecord(InviteRecord inviteRecord);

	/**
	 * 删除
	 * 
	 * @param id
	 * @return
	 */
	public boolean deleteInviteRecord(String id);

	public InviteRecord selectByPrimaryKey(String key);

	public InviteRecord selectById(String id);

	public List<Map<String, Object>> selectInviteRecordPageByUserId(String userId, int pageNum, int pageSize);
	
	public List<Map<String, Object>> selectInviteRecordPageByOpenId(String openId);
}