package com.wing.socialcontact.action;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.wing.socialcontact.common.model.PageParam;
import com.wing.socialcontact.commons.util.ApplicationPath;
import com.wing.socialcontact.commons.util.ConstantDefinition;
import com.wing.socialcontact.config.MsgConfig;
import com.wing.socialcontact.service.im.api.IImFriendService;
import com.wing.socialcontact.service.im.api.IImGroupfavService;
import com.wing.socialcontact.service.im.api.IImGroupinfoService;
import com.wing.socialcontact.service.im.api.IImGroupusersService;
import com.wing.socialcontact.service.im.bean.ImGroupinfo;
import com.wing.socialcontact.service.wx.api.ITjyUserService;
import com.wing.socialcontact.service.wx.api.IUserEmpiricalService;
import com.wing.socialcontact.service.wx.api.IUserIntegralLogService;
import com.wing.socialcontact.service.wx.api.IWxUserService;
import com.wing.socialcontact.service.wx.bean.TjyUser;
import com.wing.socialcontact.service.wx.bean.UserEmpirical;
import com.wing.socialcontact.service.wx.bean.WxUser;
import com.wing.socialcontact.sys.action.BaseAction;
import com.wing.socialcontact.sys.bean.ListValues;
import com.wing.socialcontact.sys.service.IListValuesService;
import com.wing.socialcontact.util.im.IMUtil;

import net.sf.json.JSONArray;

/**
 * 用户勋章管理
 * 
 * @author gaojun
 *
 */
@Controller
@RequestMapping("/im")
public class IMAction extends BaseAction {

	@Autowired
	private IImFriendService imFriendService;
	@Autowired
	private IImGroupinfoService imGroupinfoService;
	@Autowired
	private IImGroupusersService imGroupusersService;
	@Autowired
	private IImGroupfavService imGroupfavService;
	@Autowired
	private IListValuesService listValuesService;
	@Autowired
	private ITjyUserService tjyUserService;
	@Autowired
	private IWxUserService wxUserService;
	@Resource
	protected IUserIntegralLogService userIntegralLogService;
	@Autowired
	private IUserEmpiricalService userEmpiricalService;

	private String imPrefix = ApplicationPath.getParameter(ConstantDefinition.IM_PREFIX);

	@RequiresPermissions("im:read")
	@RequestMapping("queryFriendList")	
	public ModelAndView queryFriendList(PageParam param, String userId, String nickname, String comName, String jobName,
			String cityName, String industryName, String mobile) {
		Map paramMap = new HashMap();
		paramMap.put("userId", userId);
		paramMap.put("nickname", nickname);
		paramMap.put("comName", comName);
		paramMap.put("jobName", jobName);
		paramMap.put("cityName", cityName);
		paramMap.put("industryName", industryName);
		paramMap.put("mobile", mobile);
		return ajaxJsonEscape(imFriendService.queryFriendListByparam(param, paramMap));
	}

	@RequiresPermissions("im:read")
	@RequestMapping("queryGroupList")
	public ModelAndView queryGroupList(PageParam param, String userId, String groupName, String userCount,
			String groupType) {
		Map paramMap = new HashMap();
		paramMap.put("userId", userId);
		paramMap.put("groupName", groupName);
		if (!StringUtils.isEmpty(userCount)) {
			paramMap.put("userCount", Long.parseLong(userCount));
		}
		if (!StringUtils.isEmpty(groupType)) {
			paramMap.put("groupType", Integer.parseInt(groupType));
		}
		return ajaxJsonEscape(imGroupinfoService.queryGroupListByparam(param, paramMap));
	}

	@RequiresPermissions("im:read")
	@RequestMapping("load")
	public String load(ModelMap map) {
		List<ListValues> values = listValuesService.selectListByType(12);
		map.addAttribute("jobs", values);
		List<ListValues> industrys = listValuesService.selectListByType(8001);
		map.addAttribute("industrys", industrys);

		List<UserEmpirical> userLevelList = userEmpiricalService.selectAllUserEmpirical();
		map.addAttribute("userLevelList", userLevelList);

		return "im/group_load";

	}

	@RequiresPermissions("im:read")
	@RequestMapping("queryGroupList2")
	public ModelAndView queryGroupList2(PageParam param, String userId, String groupName, String userCount,
			String groupType, String startTime, String endTime, String userName) {
		Map paramMap = new HashMap();
		paramMap.put("userId", userId);
		paramMap.put("groupName", groupName);
		paramMap.put("startTime", startTime);
		paramMap.put("endTime", endTime);
		// paramMap.put("userName", userName);
		if (!StringUtils.isEmpty(userCount)) {
			paramMap.put("userCount", Long.parseLong(userCount));
		}
		if (!StringUtils.isEmpty(groupType)) {
			paramMap.put("groupType", Integer.parseInt(groupType));
		}
		return ajaxJsonEscape(imGroupinfoService.queryGroupListByparam2(param, paramMap));
	}

	@RequiresPermissions("im:read")
	@RequestMapping("querygroupUserList")
	public ModelAndView querygroupUserList(PageParam param, String groupId, String nickname, String comName,
			String jobName, String cityName, String industryName, String mobile, String level, String affiliations) {
		Map paramMap = new HashMap();
		paramMap.put("groupId", groupId);
		paramMap.put("nickname", nickname);
		paramMap.put("comName", comName);
		paramMap.put("industry", industryName);
		paramMap.put("job", jobName);
		paramMap.put("mobile", mobile);
		paramMap.put("level", level);
		paramMap.put("affiliations", affiliations);
		return ajaxJsonEscape(imGroupusersService.findUserListByGroupId2(param, paramMap));
	}

	@RequiresPermissions("im:add")
	@RequestMapping("addPage")
	public String addPage(ModelMap map, String userId) {
		List<ListValues> favs = listValuesService.selectListByType(8002);
		map.addAttribute("favs", favs);
		return "im/group_add";

	}

	@RequiresPermissions("im:update")
	@RequestMapping("updatePage")
	public String updatePage(String id, ModelMap map) {
		ImGroupinfo imGroupinfo = imGroupinfoService.findById(id);
		map.addAttribute("imGroupinfo", imGroupinfo);
		TjyUser tjyUser = tjyUserService.selectById(imGroupinfo.getCreator());
		map.addAttribute("tjyUser", tjyUser);

		List<Map<String, Object>> favList = imGroupfavService.findFavListByGroupId(imGroupinfo.getId());
		map.addAttribute("favList", favList);
		List<ListValues> favs = listValuesService.selectListByType(8002);
		map.addAttribute("favs", favs);
		String groupfavs = "";
		for (Map<String, Object> m : favList) {
			if (StringUtils.isEmpty(groupfavs)) {
				groupfavs += m.get("fav_id");
			} else {
				groupfavs += ",";
				groupfavs += m.get("fav_id");
			}

		}
		map.addAttribute("fav_id", groupfavs);

		List<Map<String, Object>> userList = imGroupusersService.findUserListByGroupId(imGroupinfo.getId());
		String user_ids = "";
		String user_names = "";
		for (Map<String, Object> m : userList) {
			if (StringUtils.isEmpty(user_ids)) {
				user_ids += m.get("user_id");
			} else {
				user_ids += ",";
				user_ids += m.get("user_id");
			}
			if (StringUtils.isEmpty(user_names)) {
				user_names += m.get("mobile");
			} else {
				user_names += ",";
				user_names += m.get("mobile");
			}
		}
		map.addAttribute("user_ids", user_ids);
		map.addAttribute("user_names", user_names);

		return "im/group_update";
	}

	@RequiresPermissions("im:read")
	@RequestMapping("viewPage")
	public String viewPage(String id, ModelMap map) {
		ImGroupinfo imGroupinfo = imGroupinfoService.findById(id);
		map.addAttribute("imGroupinfo", imGroupinfo);
		TjyUser tjyUser = tjyUserService.selectById(imGroupinfo.getCreator());
		map.addAttribute("tjyUser", tjyUser);

		List<Map<String, Object>> favList = imGroupfavService.findFavListByGroupId(imGroupinfo.getId());
		map.addAttribute("favList", favList);
		List<ListValues> favs = listValuesService.selectListByType(8002);
		map.addAttribute("favs", favs);
		String groupfavs = "";
		for (Map<String, Object> m : favList) {
			if (StringUtils.isEmpty(groupfavs)) {
				groupfavs += m.get("fav_id");
			} else {
				groupfavs += ",";
				groupfavs += m.get("fav_id");
			}

		}
		map.addAttribute("fav_id", groupfavs);

		List<Map<String, Object>> userList = imGroupusersService.findUserListByGroupId(imGroupinfo.getId());
		String user_ids = "";
		String user_names = "";
		for (Map<String, Object> m : userList) {
			if (StringUtils.isEmpty(user_ids)) {
				user_ids += m.get("user_id");
			} else {
				user_ids += ",";
				user_ids += m.get("user_id");
			}
			if (StringUtils.isEmpty(user_names)) {
				user_names += m.get("mobile");
			} else {
				user_names += ",";
				user_names += m.get("mobile");
			}
		}
		map.addAttribute("user_ids", user_ids);
		map.addAttribute("user_names", user_names);

		return "im/group_view";
	}

	@RequiresPermissions("im:update")
	@RequestMapping("addUsersPage")
	public String addUsersPage(String pid, ModelMap map) {
		ImGroupinfo imGroupinfo = imGroupinfoService.findById(pid);
		map.addAttribute("imGroupinfo", imGroupinfo);
		TjyUser tjyUser = tjyUserService.selectById(imGroupinfo.getCreator());
		map.addAttribute("tjyUser", tjyUser);

		List<Map<String, Object>> favList = imGroupfavService.findFavListByGroupId(imGroupinfo.getId());
		map.addAttribute("favList", favList);
		List<ListValues> favs = listValuesService.selectListByType(8002);
		map.addAttribute("favs", favs);
		String groupfavs = "";
		for (Map<String, Object> m : favList) {
			if (StringUtils.isEmpty(groupfavs)) {
				groupfavs += m.get("fav_id");
			} else {
				groupfavs += ",";
				groupfavs += m.get("fav_id");
			}

		}
		map.addAttribute("fav_id", groupfavs);

		List<Map<String, Object>> userList = imGroupusersService.findUserListByGroupId(imGroupinfo.getId());
		String user_ids = "";
		String user_names = "";
		for (Map<String, Object> m : userList) {
			if (StringUtils.isEmpty(user_ids)) {
				user_ids += m.get("user_id");
			} else {
				user_ids += ",";
				user_ids += m.get("user_id");
			}
			if (StringUtils.isEmpty(user_names)) {
				user_names += m.get("mobile");
			} else {
				user_names += ",";
				user_names += m.get("mobile");
			}
		}
		map.addAttribute("user_ids", user_ids);
		map.addAttribute("user_names", user_names);

		return "im/group_users_update";
	}

	@RequiresPermissions("im:add")
	@RequestMapping("add")
	public ModelAndView add(HttpServletRequest request, ImGroupinfo imGroupinfo, String favs, String users,
			String mobile, Errors errors) {
		if (errors.hasErrors()) {
			ModelAndView mav = getValidationMessage(errors);
			if (mav != null)
				return mav;
		}
		WxUser user = this.wxUserService.selectByMobile(mobile);
		if (null == user) {
			return ajaxDone(MsgConfig.MSG_KEY_FAIL);
		}
		imGroupinfo.setCreateTime(new Date());
		imGroupinfo.setMainUser(user.getId() + "");
		imGroupinfo.setCreator(user.getId() + "");
		String[] _favs = favs.split(",");
		String user_ids = "";
		String _errormobiles = "";
		if (!StringUtils.isEmpty(users)) {
			String[] _usermobiles = users.split(",");
			for (int i = 0; i < _usermobiles.length; i++) {
				List<TjyUser> tjyusers = this.tjyUserService.selectbymobile(_usermobiles[i], 2);
				/// WxUser user_temp = this.wxUserService.selectByMobile(_usermobiles[i]);
				if (null != tjyusers && tjyusers.size() > 0) {
					if (StringUtils.isEmpty(user_ids)) {
						user_ids += tjyusers.get(0).getId();
					} else {
						user_ids += ",";
						user_ids += tjyusers.get(0).getId();
					}
				} else {
					if (StringUtils.isEmpty(_errormobiles)) {
						_errormobiles += _usermobiles[i];
					} else {
						_errormobiles += ",";
						_errormobiles += _usermobiles[i];
					}

				}
			}
		}
		String[] _user_ids = user_ids.split(",");
		if (imGroupinfo.getHeadPortrait() == null) {
			String path = request.getContextPath();
			String imgPath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
					+ path + "/" + "resource/images/qun.png";
			imGroupinfo.setHeadPortrait(imgPath);
		}
		if (!StringUtils.isEmpty(_errormobiles)) {
			return ajaxDoneTextError(_errormobiles + "该手机用户未在本系统中注册或没认证通过！");
		}
		String groupId = imGroupinfoService.insertGroupInfo(imGroupinfo, _user_ids, "0", "0", _favs);
		// 通知IM
		String tid = "";
		JSONArray members = new JSONArray();
		if (_user_ids != null && _user_ids.length != 0) {
			for (int i = 0; i < _user_ids.length; i++) {
				members.add(imPrefix + _user_ids[i]);
			}
		}
		tid = IMUtil.createTeamOne(imGroupinfo.getGroupName(), imPrefix + imGroupinfo.getMainUser(), members);
		if (tid == "" || tid.equals("")) {
			return ajaxDoneTextError("创建群失败，检查群主和群成员是否在IM里面注册过！");
		}
		ImGroupinfo img = imGroupinfoService.findById(groupId);
		img.setTid(tid);
		imGroupinfoService.updateGI(img);
		// 此项年度积分增加上限为100
		userIntegralLogService.addLntAndEmp(user.getId() + "", "task_0017");
		return ajaxDone(MsgConfig.MSG_KEY_SUCCESS);
	}

	@RequiresPermissions("im:update")
	@RequestMapping("update")
	public ModelAndView update(ImGroupinfo imGroupinfo, String favs, String users, Errors errors) {
		if (errors.hasErrors()) {
			ModelAndView mav = getValidationMessage(errors);
			if (mav != null)
				return mav;
		}
		imGroupinfo.setUpdateTime(new Date());
		imGroupinfo.setMainUser(imGroupinfo.getCreator());
		imGroupinfoService.updateGroupInfo(imGroupinfo, imGroupinfo.getCreator());
		// 通知IM
		ImGroupinfo img = imGroupinfoService.findById(imGroupinfo.getId());
		IMUtil.updateTeam(img.getTid(), img.getGroupName(), imPrefix + img.getMainUser(), "", "");

		// 修改favs
		String[] _favs = favs.split(",");
		imGroupfavService.updateFavListByGroupId(imGroupinfo.getId(), _favs);
		// 修改user
		String[] _usermobiles = users.split(",");
		String user_ids = "";
		String _errormobiles = "";
		for (int i = 0; i < _usermobiles.length; i++) {
			List<TjyUser> tjyusers = this.tjyUserService.selectbymobile(_usermobiles[i], 2);
			/// WxUser user_temp = this.wxUserService.selectByMobile(_usermobiles[i]);
			if (null != tjyusers && tjyusers.size() > 0) {
				if (StringUtils.isEmpty(user_ids)) {
					user_ids += tjyusers.get(0).getId();
				} else {
					user_ids += ",";
					user_ids += tjyusers.get(0).getId();
				}
			} else {
				if (StringUtils.isEmpty(_errormobiles)) {
					_errormobiles += _usermobiles[i];
				} else {
					_errormobiles += ",";
					_errormobiles += _usermobiles[i];
				}

			}
		}
		if (!StringUtils.isEmpty(_errormobiles)) {
			return ajaxDoneTextError(_errormobiles + "该手机用户未在本系统中注册或没认证通过！");
		}
		String[] _user_ids = user_ids.split(",");
		// String[] _users = users.split(",");
		imGroupusersService.delUser2(imGroupinfo.getId());
		imGroupusersService.insertUser(imGroupinfo.getId(), _user_ids);
		if (StringUtils.isEmpty(_errormobiles)) {
			return ajaxDone(MsgConfig.MSG_KEY_SUCCESS);
		} else {
			return ajaxDoneTextError(_errormobiles + "该手机用户未在本系统中注册或没认证通过！");
		}
	}

	@RequiresPermissions("im:update")
	@RequestMapping("addUsers")
	public ModelAndView addUers(ImGroupinfo imGroupinfo, String pid, String users, Errors errors) {
		if (errors.hasErrors()) {
			ModelAndView mav = getValidationMessage(errors);
			if (mav != null)
				return mav;
		}

		// 修改user
		String[] _usermobiles = users.split(",");
		String user_ids = "";
		String _errormobiles = "";
		for (int i = 0; i < _usermobiles.length; i++) {
			List<TjyUser> tjyusers = this.tjyUserService.selectbymobile(_usermobiles[i], 2);
			/// WxUser user_temp = this.wxUserService.selectByMobile(_usermobiles[i]);
			if (null != tjyusers && tjyusers.size() > 0) {
				if (StringUtils.isEmpty(user_ids)) {
					user_ids += tjyusers.get(0).getId();
				} else {
					user_ids += ",";
					user_ids += tjyusers.get(0).getId();
				}
			} else {
				if (StringUtils.isEmpty(_errormobiles)) {
					_errormobiles += _usermobiles[i];
				} else {
					_errormobiles += ",";
					_errormobiles += _usermobiles[i];
				}

			}
		}
		if (!StringUtils.isEmpty(_errormobiles)) {
			return ajaxDoneTextError(_errormobiles + "该手机用户未在本系统中注册或没认证通过！");
		}
		String[] _user_ids = user_ids.split(",");
		imGroupusersService.insertUser(pid, _user_ids);
		if (StringUtils.isEmpty(_errormobiles)) {
			return ajaxDone(MsgConfig.MSG_KEY_SUCCESS);
		} else {
			return ajaxDoneTextError(_errormobiles + "该手机用户未在本系统中注册或没认证通过！");
		}
	}

	@RequiresPermissions("im:delete")
	@RequestMapping("del")
	public ModelAndView del(String[] ids) {
		// 通知IM
		if (ids != null && ids.length != 0) {
			for (int i = 0; i < ids.length; i++) {
				ImGroupinfo gnew = imGroupinfoService.findById(ids[i]);
				IMUtil.removeTeam(gnew.getTid(), imPrefix + gnew.getMainUser());
			}
		}
		return ajaxDone(imGroupinfoService.delGroupInfo(ids));
	}

	@RequestMapping("delUser")
	@ResponseBody
	public Map<String, Object> delUser(String groupId, String userIds) {
		// 修改user
		String[] _users = userIds.split(",");
		String result = imGroupusersService.delUser(groupId, _users);
		if (result.equals(MsgConfig.MSG_KEY_SUCCESS)) {
			return super.getAjaxResult("0", "修改状态成功", null);
		} else {
			return super.getAjaxResult("999", "修改状态失败", null);
		}
	}

	/**
	 * 根据手机号获取用户id
	 * 
	 * @param mobile
	 * @return
	 */
	@RequestMapping("checkMobile")
	@ResponseBody
	public Map<String, Object> checkMobile(String mobile) {
		try {
			WxUser user = this.wxUserService.selectByMobile(mobile);
			return getAjaxResult("0", "获取用户信息成功！", user);
		} catch (Exception e) {
			return getAjaxResult("-1", "操作失败", null);
		}
	}
}
