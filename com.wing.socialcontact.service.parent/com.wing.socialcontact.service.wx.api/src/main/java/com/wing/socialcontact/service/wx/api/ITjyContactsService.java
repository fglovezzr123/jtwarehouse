package com.wing.socialcontact.service.wx.api;

import com.wing.socialcontact.service.wx.bean.TjyContacts;

import java.util.List;
import java.util.Map;

/**
 * @author devil
 * @desicription: 通讯录列表接口
 * @date Created in 2017/12/14 11:02
 */
public interface ITjyContactsService {

	/**
	 * 获取通讯录版本
	 * @param userId 用户id
	 * @return
	 */
	Map getVersionByUserId(Long userId) throws Exception;

	/**
	 * 上传通讯录（分片）
	 * @param userId 用户id
	 * @param mobiles 手机号字符串，以“,”分割
	 * @param isLast 是否是最新
	 * @return
	 */
	int uploadContacts(Long userId, String mobiles, String isLast) throws Exception;

	/**
	 * 获取通讯录
	 * @param userId 用户id
	 * @param pageNum 页码
	 * @param pageSize 条数
	 * @param status 状态 0：未邀请 1：已邀请 2：未添加 3：已添加
	 * @param randomFlag 随机标志 0：不随机 1：随机
	 * @return
	 * @throws Exception
	 */
	List<TjyContacts> getContacts(Long userId, int pageNum, int pageSize, String status, boolean randomFlag) throws Exception;

	/**
	 * 删除
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	int removeByUserId(Long userId) throws Exception;
}
