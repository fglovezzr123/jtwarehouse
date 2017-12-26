package com.wing.socialcontact.service.im.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.util.StringUtil;
import com.wing.socialcontact.common.model.DataGrid;
import com.wing.socialcontact.common.model.PageParam;
import com.wing.socialcontact.config.MsgConfig;
import com.wing.socialcontact.im.dao.ImFriendDao;
import com.wing.socialcontact.im.dao.ImFriendrequestDao;
import com.wing.socialcontact.im.dao.ImToprelatDao;
import com.wing.socialcontact.service.im.api.IImFriendService;
import com.wing.socialcontact.service.im.bean.ImFriend;
import com.wing.socialcontact.service.im.bean.ImFriendrequest;
import com.wing.socialcontact.service.im.bean.ImToprelat;
import com.wing.socialcontact.service.wx.bean.TjyUser;
import com.wing.socialcontact.service.wx.dao.TjyUserDao;
import com.wing.socialcontact.util.im.IMUtil;

/**
 * 
 * @author xuxinyuan
 * @date 2017-03-26 19:21:38
 * @version 1.0
 */
@Service
public class ImFriendServiceImpl implements IImFriendService {
	/**
	 * 缓存的key值
	 */
	private static final String cache_name = "";

	@Resource
	private ImFriendDao imFriendDao;
	@Resource
	private ImFriendrequestDao imFriendrequestDao;
	@Resource
	private ImToprelatDao imToprelatDao;
	@Resource
	private TjyUserDao tjyUserDao;

	/**
	 * 根据用户id查询好友列表并已首字母排序
	 * 
	 * @param userId
	 * @return
	 */
	public List findMyFriendListByUserId(String userId, int pageNum, int pageSize, String isAll, String nickname)
			throws RuntimeException {
		if (userId == null) {
			throw new RuntimeException("查询错误，参数无效，请检查！");
		}
		Map paramMap = new HashMap();
		paramMap.put("userId", userId);
		paramMap.put("isAll", isAll);
		paramMap.put("nickname", nickname);
		List lst = new ArrayList();

		if (pageNum != 0 && pageSize != 0) {
			Page page = PageHelper.startPage(pageNum, pageSize, true);
			lst = imFriendDao.findMyFriendListByUserId(paramMap);
			long totalCount = page.getTotal();
			int totalPageCount = 0;
			if (pageSize != 0) {
				totalPageCount = (int) (totalCount / pageSize);
				if (totalCount % pageSize > 0) {
					totalPageCount = totalPageCount + 1;
				}
				if (totalPageCount < pageNum) {
					lst = new ArrayList();
				}
			}
		} else {
			lst = imFriendDao.findMyFriendListByUserId(paramMap);
		}

		return lst;
	}

	/**
	 * 获取用户群组列表
	 * 
	 * @param param
	 * @param nickname
	 * @return
	 */
	public DataGrid findMyFriendListByUserId2(PageParam param, Map paramMap) {
		DataGrid data = new DataGrid();
		String orderStr = param.getOrderByString();
		PageHelper.startPage(param.getPage(), param.getRows());
		Map parm = new HashMap();
		List<Map<String, Object>> lst = imFriendDao.findMyFriendListByUserId3(paramMap);
		;
		PageInfo page = new PageInfo(lst);
		data.setRows(lst);
		data.setTotal(page.getTotal());
		return data;
	}

	/**
	 * 根据用户id查询好友列表并已首字母排序 不含星标
	 * 
	 * @param userId
	 * @return
	 */
	public List findMyFriendListByUserId1(String userId, int pageNum, int pageSize, String isAll, String nickname)
			throws RuntimeException {
		if (userId == null) {
			throw new RuntimeException("查询错误，参数无效，请检查！");
		}
		Map paramMap = new HashMap();
		paramMap.put("userId", userId);
		paramMap.put("isAll", isAll);
		paramMap.put("nickname", nickname);
		List lst = new ArrayList();

		if (pageNum != 0 && pageSize != 0) {
			Page page = PageHelper.startPage(pageNum, pageSize, true);
			lst = imFriendDao.findMyFriendListByUserId1(paramMap);
			long totalCount = page.getTotal();
			int totalPageCount = 0;
			if (pageSize != 0) {
				totalPageCount = (int) (totalCount / pageSize);
				if (totalCount % pageSize > 0) {
					totalPageCount = totalPageCount + 1;
				}
				if (totalPageCount < pageNum) {
					lst = new ArrayList();
				}
			}
		} else {
			lst = imFriendDao.findMyFriendListByUserId1(paramMap);
		}

		return lst;
	}

	/**
	 * 根据用户id查询星标好友
	 * 
	 * @param userId
	 * @return
	 */
	public List findMyFriendListByUserId2(String userId, int pageNum, int pageSize, String nickname)
			throws RuntimeException {
		if (userId == null) {
			throw new RuntimeException("查询错误，参数无效，请检查！");
		}
		Map paramMap = new HashMap();
		paramMap.put("userId", userId);
		paramMap.put("nickname", nickname);
		List lst = new ArrayList();

		if (pageNum != 0 && pageSize != 0) {
			Page page = PageHelper.startPage(pageNum, pageSize, true);
			lst = imFriendDao.findMyFriendListByUserId2(paramMap);
			long totalCount = page.getTotal();
			int totalPageCount = 0;
			if (pageSize != 0) {
				totalPageCount = (int) (totalCount / pageSize);
				if (totalCount % pageSize > 0) {
					totalPageCount = totalPageCount + 1;
				}
				if (totalPageCount < pageNum) {
					lst = new ArrayList();
				}
			}
		} else {
			lst = imFriendDao.findMyFriendListByUserId2(paramMap);
		}

		return lst;
	}

	/**
	 * 根据用户id查询好友数量
	 * 
	 * @param userId
	 * @return
	 */
	public int findMyFriendCountByUserId(String userId) throws RuntimeException {
		if (StringUtils.isEmpty(userId)) {
			throw new RuntimeException("参数无效，请检查！");
		}
		int count = imFriendDao.findMyFriendCountByUserId(userId);
		return count;
	}

	/**
	 * 根据好友请求id相互添加好友
	 * 
	 * @param fRequestId
	 * @return
	 */
	public String addEachOtherFriendByRequestId(String fRequestId, String friendMemo) throws RuntimeException {
		ImFriendrequest request = imFriendrequestDao.loadById(fRequestId);
		if (request != null) {
			String userId = request.getUserId();
			String friendUser = request.getFriendUser();
			if (userId.equals(friendUser)) {
				throw new RuntimeException("添加失败，自己不能加自己为好友！");
			}
			Map paramMap = new HashMap();
			paramMap.put("userId", userId);
			paramMap.put("friendUser", friendUser);
			ImFriend imfriend = imFriendDao.selectByUserIdAndFriendId(paramMap);
			if (imfriend == null) {
				ImFriend imfriend1 = new ImFriend();
				imfriend1.setId(UUID.randomUUID().toString());
				imfriend1.setUserId(userId);
				imfriend1.setFriendUser(friendUser);
				Map userMap = imFriendrequestDao.findUserMapByUserId(friendUser);
				imfriend1.setFriendMemo((String) userMap.get("nickname"));
				imfriend1.setCreateTime(new Date());
				imFriendDao.insert(imfriend1);
			}

			paramMap.put("userId", friendUser);
			paramMap.put("friendUser", userId);
			ImFriend imfriend2 = imFriendDao.selectByUserIdAndFriendId(paramMap);
			if (imfriend2 == null) {
				imfriend2 = new ImFriend();
				imfriend2.setId(UUID.randomUUID().toString());
				imfriend2.setUserId(friendUser);
				imfriend2.setFriendUser(userId);
				if (!StringUtil.isEmpty(friendMemo)) {
					imfriend2.setFriendMemo(friendMemo);
				} else {
					Map userMap = imFriendrequestDao.findUserMapByUserId(userId);
					if (userMap != null) {
						imfriend2.setFriendMemo((String) userMap.get("nickname"));
					}

				}
				imfriend2.setCreateTime(new Date());
				imFriendDao.insert(imfriend2);
			}
		} else {
			throw new RuntimeException("操作失败，好友添加请求！");
		}
		return MsgConfig.MSG_KEY_SUCCESS;
	}

	/**
	 * 删除好友，同时删除对方好友
	 * 
	 * @param userId
	 * @param friendUserId
	 * @return
	 */
	public String deleteEachOtherFriendByUserAndFriend(String userId, String friendUserId) throws RuntimeException {
		Map paramMap = new HashMap();
		paramMap.put("userId", userId);
		paramMap.put("friendUser", friendUserId);
		ImFriend imfriend = imFriendDao.selectByUserIdAndFriendId(paramMap);
		if (imfriend != null) {
			// 删除好友信息
			imFriendDao.delete(imfriend);
			paramMap.put("userId", friendUserId);
			paramMap.put("friendUser", userId);
			ImFriend imfriend2 = imFriendDao.selectByUserIdAndFriendId(paramMap);
			imFriendDao.delete(imfriend2);
			// 删除置顶信息
			Map paramMapfriend = new HashMap();
			paramMapfriend.put("subjectId", userId);
			paramMapfriend.put("userId", friendUserId);
			paramMapfriend.put("subjectType", 1);
			ImToprelat imToprelat1 = imToprelatDao.loadByCondition(paramMapfriend);
			if (imToprelat1 != null) {
				imToprelatDao.delete(imToprelat1);
			}
			paramMapfriend.clear();

			paramMapfriend.put("subjectId", friendUserId);
			paramMapfriend.put("userId", userId);
			paramMapfriend.put("subjectType", 1);

			ImToprelat imToprelat2 = imToprelatDao.loadByCondition(paramMapfriend);
			if (imToprelat2 != null) {
				imToprelatDao.delete(imToprelat2);
			}

			// 删除好友请求记录
			ImFriendrequest record = new ImFriendrequest();
			record.setUserId(userId);
			record.setFriendUser(friendUserId);
			imFriendrequestDao.delete(record);

		} else {
			throw new RuntimeException("删除失败，好友信息不存在！");
		}

		return MsgConfig.MSG_KEY_SUCCESS;
	}

	@Override
	public String updateFriendUserInfo(ImFriend imFriend) throws RuntimeException {
		if (imFriend != null && !StringUtils.isEmpty(imFriend.getId())) {
			ImFriend imFriendOld = imFriendDao.selectByPrimaryKey(imFriend.getId());
			if (imFriendOld != null) {
				imFriendDao.updateByPrimaryKeySelective(imFriend);
			} else {
				throw new RuntimeException("修改失败，好友信息为空！请检查！");
			}
		} else {
			throw new RuntimeException("修改失败，好友信息为空！请检查！");
		}
		return MsgConfig.MSG_KEY_SUCCESS;
	}

	@Override
	public boolean isMyFriend(String friendId, String userId) throws RuntimeException {
		ImFriend imFriend = imFriendDao.loadById(friendId);
		boolean flag = false;
		if (imFriend != null) {
			if (userId.equals(imFriend.getUserId())) {
				flag = true;
			}
		} else {
			flag = false;
			// throw new RuntimeException("获取好友信息失败！请检查！");
		}
		return flag;
	}

	@Override
	public List findCommonFriendsByUserAndUser(String firstUserId, String sencondUserId, int pageNum, int pageSize) {
		if (StringUtils.isEmpty(firstUserId) || StringUtils.isEmpty(sencondUserId) || pageNum == 0 || pageSize == 0) {
			throw new RuntimeException("查询错误，参数无效，请检查！");
		}
		Page page = PageHelper.startPage(pageNum, pageSize, true);
		Map paramMap = new HashMap();
		paramMap.put("firstUserId", firstUserId);
		paramMap.put("sencondUserId", sencondUserId);
		List lst = imFriendDao.findCommonFriendsByUserAndUser(paramMap);
		long totalCount = page.getTotal();
		int totalPageCount = 0;
		if (pageSize != 0) {
			totalPageCount = (int) (totalCount / pageSize);
			if (totalCount % pageSize > 0) {
				totalPageCount = totalPageCount + 1;
			}
			if (totalPageCount < pageNum) {
				lst = new ArrayList();
			}
		}
		return lst;
	}

	@Override
	public ImFriend findByUserAndFriend(String userId, String friendUserId) {
		if (StringUtils.isEmpty(userId) || StringUtils.isEmpty(friendUserId)) {
			throw new RuntimeException("参数无效，请检查！");
		}
		Map paramMap = new HashMap();
		paramMap.put("userId", userId);
		paramMap.put("friendUser", friendUserId);
		ImFriend imfriend = imFriendDao.selectByUserIdAndFriendId(paramMap);
		return imfriend;
	}

	@Override
	public Map findMyFriendUserInfo(String userId, String friendUserId) {
		ImFriend imFriend = this.findByUserAndFriend(userId, friendUserId);
		Map friendUserInfo = null;
		if (imFriend == null) {
			throw new RuntimeException("好友信息不存在，请检查！");
		} else {
			Map paramMap = new HashMap();
			paramMap.put("userId", userId);
			paramMap.put("friendUser", friendUserId);
			friendUserInfo = imFriendDao.findMyFriendUserInfo(paramMap);
		}
		return friendUserInfo;
	}

	@Override
	public String UpdateStarFlagByFriendUserid(String userId, String friendUserId, String starFlag) {
		ImFriend imFriend = this.findByUserAndFriend(userId, friendUserId);
		Map friendUserInfo = null;
		if (imFriend == null) {
			throw new RuntimeException("好友信息不存在，请检查！");
		} else {
			imFriend.setStarFlag(starFlag);
			imFriend.setStarFlagDate(new Date());
			imFriendDao.updateByPrimaryKey(imFriend);
		}
		return MsgConfig.MSG_KEY_SUCCESS;
	}

	@Override
	public String updateFriendUserIsTop(String userId, String friendUserId, String status) {
		if (StringUtils.isEmpty(userId) || StringUtils.isEmpty(friendUserId)
				|| (!"1".equals(status) && !"0".equals(status))) {
			throw new RuntimeException("参数无效，请检查！");
		}

		ImFriend imFriend = this.findByUserAndFriend(userId, friendUserId);
		if (imFriend == null) {
			throw new RuntimeException("好友信息不存在，请检查！");
		} else {
			Map paramMap = new HashMap();
			paramMap.put("subjectId", userId);
			paramMap.put("userId", friendUserId);
			paramMap.put("subjectType", 1);
			ImToprelat imToprelat = imToprelatDao.loadByCondition(paramMap);
			if ("1".equals(status)) {
				if (imToprelat == null) {
					imToprelat = new ImToprelat();
					imToprelat.setId(UUID.randomUUID().toString());
					imToprelat.setSubjectId(userId);
					imToprelat.setRelatUser(friendUserId);
					imToprelat.setSubjectType(1);
					imToprelat.setTopTime(new Date());
					imToprelatDao.insert(imToprelat);
				}
			} else {
				if (imToprelat != null) {
					imToprelatDao.deleteByPrimaryKey(imToprelat.getId());
				}
			}

		}
		return MsgConfig.MSG_KEY_SUCCESS;
	}

	@Override
	public DataGrid queryFriendListByparam(PageParam param, Map paramMap) {
		DataGrid data = new DataGrid();
		String orderStr = param.getOrderByString();

		PageHelper.startPage(param.getPage(), param.getRows());
		Map parm = new HashMap();
		List<Map<String, Object>> lst = imFriendDao.findFriendListByParam(paramMap);
		PageInfo page = new PageInfo(lst);
		data.setRows(lst);
		data.setTotal(page.getTotal());
		return data;
	}

	@Override
	public void initUsers(Integer page, Integer size) {
		Map parm = new HashMap();
		List dataList = new ArrayList();
		if (page != null && size != null) {
			parm.put("start", (page - 1) * size);
			parm.put("size", size);
		}
		List list = tjyUserDao.selAllUserList(parm);
		for (int i = 0; i < list.size(); i++) {
			Map map = new HashMap();
			map = (Map) list.get(i);
			Map data = new HashMap();
			data.put("account", map.get("id"));
			data.put("token", UUID.randomUUID().toString() + map.get("id"));
			data.put("nickname", map.get("nickname"));
			data.put("headPortrait", map.get("headPortrait"));
			dataList.add(data);
		}
		IMUtil.genUser(dataList);
		IMUtil.updateUser(dataList);
	}

	@Override
	public void initFrined() {
		List dataList = new ArrayList();
		List<ImFriend> list = imFriendDao.selectAll();
		for (ImFriend f : list) {
			Map data = new HashMap();
			data.put("accid", f.getUserId());
			data.put("faccid", f.getFriendUser());
			dataList.add(data);
		}
		IMUtil.addFriend(dataList);
	}

	@Override
	public void initQfyEntryCustomer() {
		List dataList = new ArrayList();
		List list = tjyUserDao.selAllUserForQFY();
		for (int i = 0; i < list.size(); i++) {
			Map map = new HashMap();
			map = (Map) list.get(i);
			Map data = new HashMap();
			data.put("account", map.get("id"));
			data.put("token", UUID.randomUUID().toString() + map.get("id"));
			data.put("nickname", map.get("nickname"));
			data.put("headPortrait", map.get("headPortrait"));
			dataList.add(data);
		}
		IMUtil.genUser(dataList);
		IMUtil.updateUser(dataList);
	}

	@Override
	public int getStarFlag(String userId, String tjUserId) {
		Map paramMap = new HashMap();
		paramMap.put("userId", userId);
		paramMap.put("friendUserId", tjUserId);
		List list = imFriendDao.getStarFlag(paramMap);
		if (list.size() > 0) {
			return 1;
		}
		return 0;
	}

	@Override
	public List selectStarFriendList(String userId) {
		Map paramMap = new HashMap();
		paramMap.put("userId", userId);
		List list = imFriendDao.selectStarFriendList(paramMap);
		return list;
	}

}