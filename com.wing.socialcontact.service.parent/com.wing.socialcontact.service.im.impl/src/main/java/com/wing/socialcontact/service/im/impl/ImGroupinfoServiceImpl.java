package com.wing.socialcontact.service.im.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import net.sf.json.JSONArray;

import org.apache.commons.lang.StringUtils;
import org.springframework.cache.Cache.ValueWrapper;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.util.StringUtil;
import com.wing.socialcontact.common.model.DataGrid;
import com.wing.socialcontact.common.model.PageParam;
import com.wing.socialcontact.config.MsgConfig;
import com.wing.socialcontact.config.OssConfig;
import com.wing.socialcontact.im.dao.ImFriendrequestDao;
import com.wing.socialcontact.im.dao.ImGroupfavDao;
import com.wing.socialcontact.im.dao.ImGroupinfoDao;
import com.wing.socialcontact.im.dao.ImGroupusersDao;
import com.wing.socialcontact.im.dao.ImToprelatDao;
import com.wing.socialcontact.service.im.api.IImGroupfavService;
import com.wing.socialcontact.service.im.api.IImGroupinfoService;
import com.wing.socialcontact.service.im.api.IImGroupusersService;
import com.wing.socialcontact.service.im.bean.ImGroupinfo;
import com.wing.socialcontact.service.im.bean.ImGroupusers;
import com.wing.socialcontact.service.im.bean.ImToprelat;
import com.wing.socialcontact.service.wx.api.IConfigService;
import com.wing.socialcontact.service.wx.bean.Config;
import com.wing.socialcontact.util.Constants;
import com.wing.socialcontact.util.RedisCache;
import com.wing.socialcontact.util.SpringContextUtil;
import com.wing.socialcontact.util.im.IMUtil;

/**
 * 
 * @author xuxinyuan
 * @date 2017-03-29 15:33:12
 * @version 1.0
 */
@Service
public class ImGroupinfoServiceImpl implements IImGroupinfoService {
	/**
	 * 缓存的key值
	 */
	private static final String cache_name = "";

	@Resource
	private ImGroupinfoDao imGroupinfoDao;

	@Resource
	private ImGroupusersDao imGroupusersDao;

	@Resource
	private ImFriendrequestDao imFriendrequestDao;

	@Resource
	private ImGroupfavDao imGroupfavDao;

	@Resource
	private ImToprelatDao imToprelatDao;

	@Resource
	private IImGroupusersService imGroupusersService;

	@Resource
	private IImGroupfavService imGroupfavService;

	@Resource
	private IConfigService configService;

	@Override
	public List findMyGroupInfoListByUserId(String creator, int pageNum, int pageSize, String groupName)
			throws RuntimeException {
		if (creator == null) {
			throw new RuntimeException("参数无效，请检查！");
		}
		Map paramMap = new HashMap();
		paramMap.put("groupName", groupName);
		paramMap.put("userId", creator);
		List lst = new ArrayList();
		if (pageNum != 0 && pageSize != 0) {
			Page page = PageHelper.startPage(pageNum, pageSize, true);
			lst = imGroupinfoDao.findMyGroupInfoListByUserId(paramMap);
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
			lst = imGroupinfoDao.findMyGroupInfoListByUserId(paramMap);
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
	public DataGrid queryGroupListByparam(PageParam param, Map paramMap) {
		DataGrid data = new DataGrid();
		String orderStr = param.getOrderByString();
		PageHelper.startPage(param.getPage(), param.getRows());
		Map parm = new HashMap();
		List<Map<String, Object>> lst = imGroupinfoDao.findGroupListByParam(paramMap);
		PageInfo page = new PageInfo(lst);
		data.setRows(lst);
		data.setTotal(page.getTotal());
		return data;
	}

	/**
	 * 获取用户群组列表
	 * 
	 * @param param
	 * @param nickname
	 * @return
	 */
	public DataGrid queryGroupListByparam2(PageParam param, Map paramMap) {
		DataGrid data = new DataGrid();
		String orderStr = param.getOrderByString();
		PageHelper.startPage(param.getPage(), param.getRows());
		Map parm = new HashMap();
		List<Map<String, Object>> lst = imGroupinfoDao.findGroupListByParam2(paramMap);
		for (Map<String, Object> m : lst) {
			String id = (String) m.get("id");
			List<Map<String, Object>> favList = imGroupfavService.findFavListByGroupId(id);
			String groupfavs = "";
			for (Map<String, Object> m1 : favList) {
				if (StringUtils.isEmpty(groupfavs)) {
					groupfavs += m1.get("list_value");
				} else {
					groupfavs += ",";
					groupfavs += m1.get("list_value");
				}
			}
			m.put("favs", groupfavs);
		}
		PageInfo page = new PageInfo(lst);
		data.setRows(lst);
		data.setTotal(page.getTotal());
		return data;
	}

	/**
	 * 获取用户群聊数量
	 * 
	 * @param userId
	 * @return
	 */
	public int findMyGroupInfCountByUserId(String creator) throws RuntimeException {
		if (creator == null) {
			throw new RuntimeException("参数无效，请检查！");
		}
		return imGroupinfoDao.findMyGroupInfoCounttByUserId(creator);
	}

	@Override
	public String insertGroupInfo(ImGroupinfo imGroupinfo, String[] userIds, String isTop, String disturb,
			String[] favIds) throws RuntimeException {
		if (imGroupinfo == null || imGroupinfo.getCreator() == null || imGroupinfo.getGroupName() == null
				|| userIds == null || userIds.length == 0 || imGroupinfo.getGroupType() == null) {
			throw new RuntimeException("参数无效，请检查！");
		}

		String creator = imGroupinfo.getCreator();
		String groupName = imGroupinfo.getGroupName();
		Map paramMap = new HashMap();
		paramMap.put("creator", creator);
		paramMap.put("groupName", groupName);
		ImGroupinfo imGroupinfoOld = imGroupinfoDao.findByCreaterAndName(paramMap);
		if (imGroupinfoOld != null) {
			throw new RuntimeException("群名重复，请重新创建！");
		} else {
			ImGroupinfo imGroupinfoNew = new ImGroupinfo();
			String groupId = UUID.randomUUID().toString();
			imGroupinfoNew.setId(groupId);
			imGroupinfoNew.setGroupNo(com.wing.socialcontact.util.StringUtil.createStringNo());
			if (imGroupinfo.getHeadPortrait() != null) {
				imGroupinfoNew.setHeadPortrait(imGroupinfo.getHeadPortrait());
			} else {
				imGroupinfoNew.setHeadPortrait("");
			}
			imGroupinfoNew.setGroupName(groupName);
			imGroupinfoNew.setCreator(creator);
			imGroupinfoNew.setMainUser(creator);
			imGroupinfoNew.setGroupType(imGroupinfo.getGroupType());
			imGroupinfoNew.setGroupPower(imGroupinfo.getGroupPower());
			imGroupinfoNew.setGroupDesc(imGroupinfo.getGroupDesc());
			imGroupinfoNew.setMembersMax(500);
			imGroupinfoNew.setCreateTime(new Date());
			imGroupinfoNew.setDeleted(0);
			imGroupinfoDao.insert(imGroupinfoNew);

			if ("1".equals(isTop)) {
				paramMap.clear();
				paramMap.put("subjectId", groupId);
				paramMap.put("userId", creator);
				paramMap.put("subjectType", 2);
				ImToprelat imtoprelat = imToprelatDao.loadByCondition(paramMap);
				if (imtoprelat == null) {
					imtoprelat = new ImToprelat();
					imtoprelat.setId(UUID.randomUUID().toString());
					imtoprelat.setSubjectId(groupId);
					imtoprelat.setRelatUser(creator);
					imtoprelat.setSubjectType(2);
					imtoprelat.setTopTime(new Date());
					imToprelatDao.insert(imtoprelat);
				}
			}

			ImGroupusers groupusersManager = new ImGroupusers();
			groupusersManager.setId(UUID.randomUUID().toString());
			groupusersManager.setGroupId(groupId);
			groupusersManager.setUserId(creator);
			groupusersManager.setAffiliations("owner");
			groupusersManager.setMsgDisturb(Integer.parseInt(disturb));
			groupusersManager.setCreateTime(new Date());

			Map userMap = imFriendrequestDao.findUserMapByUserId(creator);
			groupusersManager.setNickname((String) userMap.get("nickname"));
			imGroupusersDao.insert(groupusersManager);
			try {
				imGroupusersService.insertUser(groupId, userIds);
			} catch (Exception e) {
				throw new RuntimeException(e.getMessage());
			}
			if (favIds != null && favIds.length != 0) {
				imGroupfavService.insertFavs(groupId, favIds);
			}
			return groupId;
		}

	}

	/**
	 * 修改群信息
	 * 
	 * @param imGroupinfo
	 * @param userIds
	 * @return
	 */
	public String updateGroupInfo(ImGroupinfo imGroupinfo, String userId) throws RuntimeException {
		if (imGroupinfo == null || StringUtils.isEmpty(imGroupinfo.getId())) {
			throw new RuntimeException("参数无效，请检查！");
		} else {
			ImGroupinfo imGroupinfo1 = this.findById(imGroupinfo.getId());

			if (imGroupinfo1 != null) {
				if (!imGroupinfo1.getCreator().equals(userId)) {
					throw new RuntimeException("更新失败，用户只能修改自己创建的群！");
				}
				imGroupinfoDao.updateByPrimaryKeySelective(imGroupinfo);
				return imGroupinfo.getId();
			} else {
				throw new RuntimeException("更新失败，未找到该群组！");
			}
		}
	}

	@Override
	public ImGroupinfo findById(String id) {
		return imGroupinfoDao.loadById(id);
	}

	@Override
	public String setGroupIsPrivate(String groupId, String status) throws RuntimeException {
		if (groupId == null || (!"1".equals(status) && !"2".equals(status))) {
			throw new RuntimeException("参数无效，请检查！");
		}
		ImGroupinfo groupInfo = imGroupinfoDao.loadById(groupId);
		if (groupInfo != null) {
			groupInfo.setGroupType(Integer.parseInt(status));
			imGroupinfoDao.updateByPrimaryKeySelective(groupInfo);
		} else {
			throw new RuntimeException("群组信息无效，请检查！");
		}
		return MsgConfig.MSG_KEY_SUCCESS;
	}

	@Override
	public String delGroupInfo(String groupId, String userId) throws RuntimeException {
		if (groupId == null || StringUtil.isEmpty(userId)) {
			throw new RuntimeException("参数无效，请检查！");
		}
		ImGroupinfo groupInfo = imGroupinfoDao.loadById(groupId);
		if (groupInfo != null) {
			String creator = groupInfo.getCreator();
			if (creator.equals(userId)) {
				groupInfo.setDeleted(1);
				groupInfo.setDeleteDate(new Date());
				imGroupinfoDao.updateByPrimaryKeySelective(groupInfo);
			} else {
				throw new RuntimeException("非创建人不能解散群，请检查！");
			}

		} else {
			throw new RuntimeException("群组信息无效，请检查！");
		}
		return MsgConfig.MSG_KEY_SUCCESS;
	}

	@Override
	public List selIdenticalHobbyWorld(String userId, String hobby, String groupName, int pageNum, int pageSize) {
		if (userId == null || pageNum == 0 || pageSize == 0) {
			throw new RuntimeException("参数无效，请检查！");
		}
		Page page = PageHelper.startPage(pageNum, pageSize, true);
		Map paramMap = new HashMap();
		paramMap.put("userId", userId);
		paramMap.put("hobby", hobby);
		paramMap.put("groupName", groupName);
		List lst = imGroupinfoDao.findHobbyGroupByUser(paramMap);
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
	public List selIdenticalHobbyFriends(String userId, String hobby, String groupName, int pageNum, int pageSize) {
		if (userId == null || pageNum == 0 || pageSize == 0) {
			throw new RuntimeException("参数无效，请检查！");
		}
		Page page = PageHelper.startPage(pageNum, pageSize, true);
		Map paramMap = new HashMap();
		paramMap.put("userId", userId);
		paramMap.put("hobby", hobby);
		paramMap.put("groupName", groupName);
		List lst = imGroupinfoDao.findHobbyFriendsByUser(paramMap);
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
	public boolean delGroupInfo(String[] ids) {
		// 等待删除的对象集合
		int count = 0;
		for (String id : ids) {
			if (StringUtils.isNotBlank(id)) {
				String[] myids = id.split(",");
				for (String string : myids) {
					ImGroupinfo groupInfo = imGroupinfoDao.loadById(string);
					groupInfo.setDeleted(1);
					groupInfo.setDeleteDate(new Date());
					imGroupinfoDao.updateByPrimaryKeySelective(groupInfo);
					count++;
				}
			}
		}
		return count > 0;
	}

	/**
	 * 同行精英
	 * 
	 * @param userId
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	@Override
	public List selPeerElite(String userId, String industry, String nickname, int pageNum, int pageSize) {
		if (StringUtils.isEmpty(userId) || pageNum == 0 || pageSize == 0) {
			throw new RuntimeException("参数无效，请检查！");
		}
		Page page = PageHelper.startPage(pageNum, pageSize, true);
		Map paramMap = new HashMap();
		paramMap.put("userId", userId);
		paramMap.put("industry", industry);
		paramMap.put("nickname", nickname);

		List lst = imGroupinfoDao.findPeerEliteByUser(paramMap);

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

	/**
	 * 同行精英2
	 * 
	 * @param userId
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	@Override
	public List selPeerElite2(String userId, String industry, String nickname) {
		if (StringUtils.isEmpty(userId)) {
			throw new RuntimeException("参数无效，请检查！");
		}
		Map paramMap = new HashMap();
		paramMap.put("userId", userId);
		paramMap.put("industry", industry);
		paramMap.put("nickname", nickname);

		int tj_num = 30;// 默认推荐人数30人
		Config config = configService.selectByType("1");
		if (null != config) {
			String config_num = config.getConfig1();
			if (!StringUtils.isEmpty(config_num)) {
				tj_num = Integer.parseInt(config_num);
			}
		}
		paramMap.put("tjNum", tj_num);
		List lst = imGroupinfoDao.findPeerEliteByUser(paramMap);
		if (StringUtils.isEmpty(nickname)) {
			// 保存到redis,每天要清一次redis
			RedisCache redisCache = (RedisCache) SpringContextUtil.getBean("redisCache");
			try {
				ValueWrapper vw = redisCache.get("selPeerElite" + userId);

				List peerEliteList = null;
				if (vw != null) {
					peerEliteList = (List) vw.get();
				}

				if (peerEliteList == null) {
					peerEliteList = lst;
					// 设置缓存时间为24小时
					redisCache.put("selPeerElite" + userId, peerEliteList, 86400l);
				}

				List resultList = null;
				if (null != peerEliteList && peerEliteList.size() >= 10) {
					resultList = createRandomList(peerEliteList, 10);
				} else {
					resultList = peerEliteList;
				}

				return resultList;

			} catch (Exception e) {
				System.out.println("selPeerElite error!");
				return null;
			}
		} else {
			return lst;
		}

	}

	/**
	 * 从list中随机抽取元素 @return @Title: createRandomList @Description: TODO @param
	 * list @param i @return void @throws
	 */
	private static List createRandomList(List list, int n) {
		// TODO Auto-generated method stub
		Map map = new HashMap();
		List listNew = new ArrayList();
		if (list.size() <= n) {
			return list;
		} else {
			while (map.size() < n) {
				int random = (int) (Math.random() * list.size());
				if (!map.containsKey(random)) {
					map.put(random, "");
					System.out.println(random + "===========" + list.get(random));
					listNew.add(list.get(random));
				}
			}
			return listNew;
		}
	}

	/**
	 * 同城精英
	 * 
	 * @param userId
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	@Override
	public List selCityElite(String userId, int pageNum, int pageSize) {
		if (userId == null || pageNum == 0 || pageSize == 0) {
			throw new RuntimeException("参数无效，请检查！");
		}
		Page page = PageHelper.startPage(pageNum, pageSize, true);
		Map paramMap = new HashMap();
		paramMap.put("userId", userId);
		List lst = imGroupinfoDao.findCityEliteByUser(paramMap);
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

	/**
	 * 同城精英
	 * 
	 * @param userId
	 * @return
	 */
	@Override
	public List selCityElite2(String userId) {
		if (userId == null) {
			throw new RuntimeException("参数无效，请检查！");
		}

		Map paramMap = new HashMap();
		paramMap.put("userId", userId);
		int tj_num = 30;// 默认推荐人数30人
		Config config = configService.selectByType("1");
		if (null != config) {
			String config_num = config.getConfig1();
			if (!StringUtils.isEmpty(config_num)) {
				tj_num = Integer.parseInt(config_num);
			}
		}
		paramMap.put("tjNum", tj_num);
		List lst = imGroupinfoDao.findCityEliteByUser(paramMap);

		// 保存到redis,每天要清一次redis
		RedisCache redisCache = (RedisCache) SpringContextUtil.getBean("redisCache");
		try {
			ValueWrapper vw = redisCache.get("selcityElite" + userId);

			List cityEliteList = null;
			if (vw != null) {
				cityEliteList = (List) vw.get();
			}

			if (cityEliteList == null) {
				cityEliteList = lst;
				// 设置缓存时间为120秒
				redisCache.put("selcityElite" + userId, cityEliteList, 86400l);
			}

			List resultList = null;
			if (null != cityEliteList && cityEliteList.size() >= 10) {
				resultList = createRandomList(cityEliteList, 10);
			} else {
				resultList = cityEliteList;
			}

			return resultList;

		} catch (Exception e) {
			System.out.println("selcityElite error!");
			return null;
		}
	}

	/**
	 * 智同道合
	 * 
	 * @param userId
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	@Override
	public List selSameIdeasElite(String userId, int pageNum, int pageSize) {
		if (userId == null || pageNum == 0 || pageSize == 0) {
			throw new RuntimeException("参数无效，请检查！");
		}
		Page page1 = PageHelper.startPage(pageNum, pageSize, true);
		List lst = imGroupinfoDao.findSameIdeasEliteByUser(userId);
		long totalCount = page1.getTotal();

		if (lst.size() == 0) {
			Page page2 = PageHelper.startPage(pageNum, pageSize, true);
			// 推荐志同道合
			lst = imGroupinfoDao.getRecommendZtdhList(userId);
			totalCount = page2.getTotal();
		}
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

	/**
	 * 获取大咖列表
	 * 
	 * @param userId
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	@Override
	public List selDKList(String userId, int pageNum, int pageSize) {
		if (userId == null || pageNum == 0 || pageSize == 0) {
			throw new RuntimeException("参数无效，请检查！");
		}
		Page page = PageHelper.startPage(pageNum, pageSize, true);

		List lst = imGroupinfoDao.findDKListByUser(userId);
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

	/**
	 * 获取二度人脉
	 * 
	 * @param userId
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	@Override
	public List selTwoDegreeConnections(String userId, int pageNum, int pageSize) {
		if (userId == null || pageNum == 0 || pageSize == 0) {
			throw new RuntimeException("参数无效，请检查！");
		}
		Page page = PageHelper.startPage(pageNum, pageSize, true);
		List lst = imGroupinfoDao.findTwoDegreeConnectionsByUser(userId);
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

	/**
	 * 根据群名称搜索群
	 * 
	 * @param userId
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	public List selGourpsListByName(String userId, String groupName, int pageNum, int pageSize) {
		if (userId == null || pageNum == 0 || pageSize == 0) {
			throw new RuntimeException("参数无效，请检查！");
		}
		Page page = PageHelper.startPage(pageNum, pageSize, true);
		Map paramMap = new HashMap();
		paramMap.put("userId", userId);
		paramMap.put("groupName", groupName);
		List lst = imGroupinfoDao.selGourpsListByName(paramMap);
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
	public void initGourps(Integer page, Integer size) {
		List dataList = new ArrayList();
		Map parm = new HashMap();
		if (page != null && size != null) {
			parm.put("start", (page - 1) * size);
			parm.put("size", size);
		}
		List list = imGroupinfoDao.selectAllGroup(parm);
		for (int i = 0; i < list.size(); i++) {
			Map map = new HashMap();
			map = (Map) list.get(i);
			Map data = new HashMap();
			data.put("id", map.get("id"));
			data.put("tname", map.get("groupName"));
			data.put("owner", map.get("mainUser"));

			List ulist = imGroupusersDao.findListByGroupId((String) map.get("id"));
			JSONArray members = new JSONArray();
			for (int j = 0; j < ulist.size(); j++) {
				Map u = new HashMap();
				u = (Map) ulist.get(j);
				members.add(u.get("userId").toString());
			}
			data.put("members", members);
			dataList.add(data);
		}
		List rlist = IMUtil.createTeam(dataList);
		for (int i = 0; i < rlist.size(); i++) {
			Map rm = new HashMap();
			rm = (Map) rlist.get(i);
			ImGroupinfo gi = imGroupinfoDao.selectByPrimaryKey(rm.get("id"));
			gi.setTid((String) rm.get("tid"));
			imGroupinfoDao.updateByPrimaryKeySelective(gi);
		}
	}

	@Override
	public String updateGI(ImGroupinfo imGroupinfo) {
		if (imGroupinfo == null || StringUtils.isEmpty(imGroupinfo.getId())) {
			throw new RuntimeException("参数无效，请检查！");
		} else {
			imGroupinfoDao.updateByPrimaryKeySelective(imGroupinfo);
			return MsgConfig.MSG_KEY_SUCCESS;
		}
	}

	@Override
	public ImGroupinfo findByTid(String groupId) {
		// TODO Auto-generated method stub
		return imGroupinfoDao.findByTid(groupId);
	}
	/**
	 * 获取二度人脉中，我的好友为其好友的个数
	 * 
	 * */
	@Override
	public List selTwoDegreeConnectionsCount(String user_id, String friend_user)
	{
		
		if (user_id == null || "".equals(user_id) || friend_user == null || "".equals(friend_user)) {
			throw new RuntimeException("参数无效，请检查！");
		}
		Map map = new HashMap();
		map.put("user_id", user_id);
		map.put("friend_user", friend_user);
		List lst = imGroupinfoDao.selTwoDegreeConnectionsCount(map);
		return lst;
	}

}