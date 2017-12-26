package com.wing.socialcontact.service.im.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.util.StringUtil;
import com.wing.socialcontact.common.model.DataGrid;
import com.wing.socialcontact.common.model.PageParam;
import com.wing.socialcontact.config.MsgConfig;
import com.wing.socialcontact.im.dao.ImFriendrequestDao;
import com.wing.socialcontact.im.dao.ImGroupinfoDao;
import com.wing.socialcontact.im.dao.ImGroupusersDao;
import com.wing.socialcontact.im.dao.ImToprelatDao;
import com.wing.socialcontact.service.im.api.IImGroupusersService;
import com.wing.socialcontact.service.im.bean.ImGroupinfo;
import com.wing.socialcontact.service.im.bean.ImGroupusers;
import com.wing.socialcontact.service.im.bean.ImToprelat;

/**
 * 
 * @author xuxinyuan
 * @date 2017-03-29 15:33:12
 * @version 1.0
 */
@Service
public class ImGroupusersServiceImpl implements IImGroupusersService {
	/**
	 * 缓存的key值
	 */
	private static final String cache_name = "";

	@Resource
	private ImGroupusersDao imGroupusersDao;

	@Resource
	private ImGroupinfoDao imGroupinfoDao;

	@Resource
	private ImToprelatDao imToprelatDao;

	@Resource
	private ImFriendrequestDao imFriendrequestDao;

	@Override
	public String insertUser(String groupId, String[] userIds) throws RuntimeException {
		if (userIds == null || userIds.length == 0 || groupId == null) {
			throw new RuntimeException("用户或组信息无效，请检查！");
		}

		ImGroupinfo imGroupinfo = imGroupinfoDao.loadById(groupId);
		int maxMemberCount = imGroupinfo.getMembersMax();
		int userCount = this.findUserCountByGroupId(groupId);

		List tjUsers = imGroupusersDao.findTjyUsersByUserIds(userIds);
		if (tjUsers != null && tjUsers.size() != 0) {
			for (int i = 0; i < userIds.length; i++) {
				Map tjyUser = imFriendrequestDao.findUserMapByUserId(userIds[i]);
				if (tjyUser == null) {
					continue;
				}
				Map paramMap = new HashMap();
				paramMap.put("groupId", groupId);
				paramMap.put("userId", userIds[i]);
				ImGroupusers guser = imGroupusersDao.findByGroupAndUser(paramMap);
				if (guser == null) {
					if (maxMemberCount < userCount) {
						throw new RuntimeException("用户数量到达上限，不能再次添加！");
					}
					guser = new ImGroupusers();
					guser.setId(UUID.randomUUID().toString());
					guser.setGroupId(groupId);
					guser.setUserId((String) tjyUser.get("id"));
					guser.setAffiliations("member");
					guser.setNickname((String) tjyUser.get("nickname"));
					guser.setMsgDisturb(0);
					guser.setCreateTime(new Date());
					imGroupusersDao.insert(guser);
					userCount++;
				} else {
					continue;
				}
			}
		} else {
			throw new RuntimeException("创建失败，获取群内用户信息失败！");
		}
		return MsgConfig.MSG_KEY_SUCCESS;
	}

	@Override
	public String delUser(String groupId, String[] userIds) throws RuntimeException {
		if (userIds == null || userIds.length == 0 || groupId == null) {
			throw new RuntimeException("用户或组信息无效，请检查！");
		}
		for (int i = 0; i < userIds.length; i++) {
			Map paramMap = new HashMap();
			paramMap.put("groupId", groupId);
			paramMap.put("userId", userIds[i]);
			imGroupusersDao.deleteByGroupAndUser(paramMap);
		}
		return MsgConfig.MSG_KEY_SUCCESS;
	}

	@Override
	public String delUser2(String groupId) throws RuntimeException {
		if (groupId == null) {
			throw new RuntimeException("用户或组信息无效，请检查！");
		}
		Map paramMap = new HashMap();
		paramMap.put("groupId", groupId);
		imGroupusersDao.deleteByGroupAndUser(paramMap);
		return MsgConfig.MSG_KEY_SUCCESS;
	}

	@Override
	public List findUserListByGroupId(String groupId) {
		if (StringUtil.isEmpty(groupId)) {
			throw new RuntimeException("组信息无效，请检查！");
		}
		return imGroupusersDao.findUsersByGroupIdNoOwner(groupId);
	}

	@Override
	public List findUserListByGroupIdHasOwner(String groupId) {
		if (StringUtil.isEmpty(groupId)) {
			throw new RuntimeException("组信息无效，请检查！");
		}
		return imGroupusersDao.findUserListByGroupIdHasOwner(groupId);
	}

	/**
	 * 获取用户群组列表
	 * 
	 * @param param
	 * @param nickname
	 * @return
	 */
	public DataGrid findUserListByGroupId2(PageParam param, Map paramMap) {
		DataGrid data = new DataGrid();
		String orderStr = param.getOrderByString();
		PageHelper.startPage(param.getPage(), param.getRows());
		Map parm = new HashMap();
		List<Map<String, Object>> lst = imGroupusersDao.findUsersByGroupId2(paramMap);
		PageInfo page = new PageInfo(lst);
		data.setRows(lst);
		data.setTotal(page.getTotal());
		return data;
	}

	@Override
	public int findUserCountByGroupId(String groupId) {
		if (StringUtil.isEmpty(groupId)) {
			throw new RuntimeException("组信息无效，请检查！");
		}
		// TODO Auto-generated method stub
		return imGroupusersDao.findCountByGroupId(groupId);
	}

	@Override
	public String isTop(String groupId, String userId, String status) {
		if (StringUtil.isEmpty(groupId) || StringUtil.isEmpty(userId) || StringUtil.isEmpty(status)
				|| (!"0".equals(status) && !"1".equals(status))) {
			throw new RuntimeException("组信息无效，请检查！");
		}
		Map paramMap = new HashMap();
		paramMap.put("subjectId", groupId);
		paramMap.put("userId", userId);
		paramMap.put("subjectType", 2);

		ImToprelat topRelat = imToprelatDao.loadByCondition(paramMap);
		// 置顶
		if ("1".equals(status)) {
			if (topRelat == null) {
				topRelat = new ImToprelat();
				topRelat.setId(UUID.randomUUID().toString());
				topRelat.setRelatUser(userId);
				topRelat.setSubjectId(groupId);
				topRelat.setSubjectType(2);
				topRelat.setTopTime(new Date());
				imToprelatDao.insert(topRelat);
			}
		} else if ("0".equals(status)) { // 取消置顶
			if (topRelat != null) {
				imToprelatDao.delete(topRelat);
			}
		}

		return MsgConfig.MSG_KEY_SUCCESS;
	}

	@Override
	public String msgDistrub(String groupId, String userId, String status) {
		if (StringUtil.isEmpty(groupId) || StringUtil.isEmpty(userId) || StringUtil.isEmpty(status)
				|| (!"0".equals(status) && !"1".equals(status))) {
			throw new RuntimeException("组信息无效，请检查！");
		}
		Map paramMap = new HashMap();
		paramMap.put("groupId", groupId);
		paramMap.put("userId", userId);
		ImGroupusers groupUser = imGroupusersDao.findByGroupAndUser(paramMap);
		if (groupUser != null) {
			groupUser.setMsgDisturb(Integer.parseInt(status));
			imGroupusersDao.updateByPrimaryKeySelective(groupUser);
		} else {
			throw new RuntimeException("该群组中未找到用户信息，操作失败！");
		}

		return MsgConfig.MSG_KEY_SUCCESS;
	}

	@Override
	public String backGroup(String groupId, String userId) {
		if (StringUtil.isEmpty(groupId) || StringUtil.isEmpty(userId)) {
			throw new RuntimeException("组信息无效，请检查！");
		}
		Map paramMap = new HashMap();
		paramMap.put("groupId", groupId);
		paramMap.put("userId", userId);
		ImGroupusers groupUser = imGroupusersDao.findByGroupAndUser(paramMap);
		if (groupUser != null) {
			imGroupusersDao.deleteById(groupUser.getId());
		} else {
			throw new RuntimeException("该群组中未找到用户信息，操作失败！");
		}

		return MsgConfig.MSG_KEY_SUCCESS;
	}

	@Override
	public ImGroupusers findByUserAndGroupId(String toUserId, String formGroupId) {
		if (StringUtils.isEmpty(toUserId) || StringUtils.isEmpty(formGroupId)) {
			return null;
		}
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("groupId", formGroupId);
		paramMap.put("userId", toUserId);
		return imGroupusersDao.findByGroupAndUser(paramMap);
	}

}