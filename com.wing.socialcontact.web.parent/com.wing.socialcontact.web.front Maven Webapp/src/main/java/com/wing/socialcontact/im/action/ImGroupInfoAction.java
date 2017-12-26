/**  
 * @Title: DataPermissionsAction.java
 * @date 2016-10-18 下午4:05:33
 * @Copyright: 2016 
 */
package com.wing.socialcontact.im.action;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import tk.mybatis.mapper.util.StringUtil;

import com.wing.socialcontact.common.action.BaseAction;
import com.wing.socialcontact.config.MsgConfig;
import com.wing.socialcontact.config.OssConfig;
import com.wing.socialcontact.front.util.ApplicationPath;
import com.wing.socialcontact.front.util.ConstantDefinition;
import com.wing.socialcontact.front.util.OkhttpUtils;
import com.wing.socialcontact.service.im.api.IImGroupfavService;
import com.wing.socialcontact.service.im.api.IImGroupinfoService;
import com.wing.socialcontact.service.im.api.IImGrouprequestService;
import com.wing.socialcontact.service.im.api.IImGroupusersService;
import com.wing.socialcontact.service.im.api.IImToprelatService;
import com.wing.socialcontact.service.im.bean.ImGroupinfo;
import com.wing.socialcontact.service.im.bean.ImGrouprequest;
import com.wing.socialcontact.service.wx.api.IUserIntegralLogService;
import com.wing.socialcontact.util.Constants;
import com.wing.socialcontact.util.im.IMUtil;

import net.sf.json.JSONArray;

/**
 * 群组操作
 * 
 * @author xuxinyuan
 * @version 1.0
 *
 */
@Controller
@RequestMapping("/im/m")
public class ImGroupInfoAction extends BaseAction {

	@Autowired
	private IImGroupinfoService imGroupinfoService;

	@Autowired
	private IImGrouprequestService imGrouprequestService;

	@Autowired
	private IImGroupusersService imGroupusersService;

	@Autowired
	private IImGroupfavService imGroupfavService;

	@Autowired
	private IImToprelatService imToprelatService;

	@Autowired
	private OssConfig ossConfig;
	@Resource
	protected IUserIntegralLogService userIntegralLogService;

	private String imPrefix = ApplicationPath.getParameter(ConstantDefinition.IM_PREFIX);

	/**
	 * 获取我的群组列表
	 * 
	 * @return
	 */
	@RequestMapping("selMyGroupInfoList")
	public @ResponseBody Map findMyGroupInfoListByUserId(HttpServletRequest request, String pageNum, String pageSize)
			throws IOException {
		String userId = super.checkLogin(request);
		if (userId == null || "".equals(userId)) {
			return super.getAjaxResult(Constants.AJAX_CODE_ERROR_NOTLOGIN, "未登录，请重新登录！", "");
		}

		if (StringUtil.isEmpty(pageNum) || StringUtil.isEmpty(pageSize)
				|| !com.wing.socialcontact.util.StringUtil.isNumeric(pageNum)
				|| !com.wing.socialcontact.util.StringUtil.isNumeric(pageSize)) {
			return super.getAjaxResult(Constants.AJAX_CODE_ERROR_PARAM, "参数无效，请检查！", "");
		} else {
			try {
				List performs = imGroupinfoService.findMyGroupInfoListByUserId(userId, Integer.parseInt(pageNum),
						Integer.parseInt(pageSize), null);
				int newRequestCount = imGrouprequestService.findNewGroupRequestCountByUserid(userId);
				Map resultMap = new HashMap();
				resultMap.put("groupInfoList", performs);
				resultMap.put("newRequestCount", newRequestCount);
				return super.getSuccessAjaxResult("获取成功！", resultMap);
			} catch (Exception e) {
				return super.getAjaxResult(Constants.AJAX_CODE_ERROR_INFO, e.getMessage(), "");
			}

		}
	}

	/**
	 * 获取我的群组列表
	 * 
	 * @return
	 */
	@RequestMapping("selMyGroupInfoCount")
	public @ResponseBody Map selMyGroupInfoCount(HttpServletRequest request) throws IOException {
		String userId = super.checkLogin(request);
		if (userId == null || "".equals(userId)) {
			return super.getAjaxResult(Constants.AJAX_CODE_ERROR_NOTLOGIN, "未登录，请重新登录！", "");
		}
		try {
			int count = imGroupinfoService.findMyGroupInfCountByUserId(userId);
			return super.getSuccessAjaxResult("获取群组数量成功！", count);
		} catch (Exception e) {
			return super.getAjaxResult(Constants.AJAX_CODE_ERROR_INFO, e.getMessage(), "");
		}
	}

	/**
	 * 创建群
	 * 
	 * @param imGroupinfo
	 * @param userIds
	 * @param isTop
	 *            1 置顶 0不置顶
	 * @param disturb
	 *            0可打扰 1、免打扰
	 * @param favIds
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("insertMyGroupInfo")
	public @ResponseBody Map insertMyGroupInfo(HttpServletRequest request, ImGroupinfo imGroupinfo, String[] userIds,
			String isTop, String disturb, String[] favIds) throws IOException {

		String userId = super.checkLogin(request);
		if (userId == null || "".equals(userId)) {
			return super.getAjaxResult(Constants.AJAX_CODE_ERROR_NOTLOGIN, "未登录，请重新登录！", "");
		}

		if (imGroupinfo == null || imGroupinfo.getGroupName() == null
				|| (imGroupinfo.getGroupType() != 1 && imGroupinfo.getGroupType() != 2) || isTop == null
				|| disturb == null || (!"1".equals(isTop) && !"0".equals(isTop))
				|| (!"1".equals(disturb) && !"0".equals(disturb)) || userIds == null || userIds.length == 0) {

			return super.getAjaxResult(Constants.AJAX_CODE_ERROR_PARAM, "参数无效，请检查！", "");
		} else {
			if (imGroupinfo.getGroupName().length() > 15) {
				return super.getAjaxResult(Constants.AJAX_CODE_ERROR_INFO, "群名称不能超过15个字符！", "");
			}
			imGroupinfo.setCreator(userId);
			imGroupinfo.setMainUser(userId);
			try {
				if (imGroupinfo.getHeadPortrait() == null) {
					String path = request.getContextPath();
					String imgPath = request.getScheme() + "://" + request.getServerName() + ":"
							+ request.getServerPort() + path + "/" + "resource/img/images/qun.png";
					imGroupinfo.setHeadPortrait(imgPath);
				}
				String groupId = imGroupinfoService.insertGroupInfo(imGroupinfo, userIds, isTop, disturb, favIds);
				// 创建群发送通知给IM服务器
				/*
				 * String result =
				 * OkhttpUtils.sendHttpRequest(ossConfig.getCreate_group_notify_url(),
				 * "{\n  \"id\": \""+groupId+"\"\n}", Constants.AUTHORIZATION);
				 */
				// 通知IM
				String tid = "";
				JSONArray members = new JSONArray();
				if (userIds != null && userIds.length != 0) {
					for (int i = 0; i < userIds.length; i++) {
						members.add(imPrefix + userIds[i]);
					}
				}
				tid = IMUtil.createTeamOne(imGroupinfo.getGroupName(), imPrefix + userId, members);
				ImGroupinfo gnew = imGroupinfoService.findById(groupId);
				gnew.setTid(tid);
				imGroupinfoService.updateGI(gnew);
				// 此项年度积分增加上限为100
				userIntegralLogService.addLntAndEmp(userId, "task_0017");
				Map res = new HashMap();
				res.put("groupId", groupId);
				res.put("tid", tid);
				return super.getSuccessAjaxResult("创建成功！", res);
			} catch (Exception e) {
				return super.getAjaxResult(Constants.AJAX_CODE_ERROR_INFO, e.getMessage(), "");
			}

		}
	}

	/**
	 * 修改群信息
	 * 
	 * @param imGroupinfo
	 * @param userIds
	 * @param isTop
	 *            1 置顶 0不置顶
	 * @param disturb
	 *            0可打扰 1、免打扰
	 * @param favIds
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("updateMyGroupInfo")
	public @ResponseBody Map updateMyGroupInfo(HttpServletRequest request, ImGroupinfo imGroupinfo) throws IOException {

		String userId = super.checkLogin(request);
		if (userId == null || "".equals(userId)) {
			return super.getAjaxResult(Constants.AJAX_CODE_ERROR_NOTLOGIN, "未登录，请重新登录！", "");
		}
		if (imGroupinfo == null || StringUtils.isEmpty(imGroupinfo.getId())) {
			return super.getAjaxResult(Constants.AJAX_CODE_ERROR_INFO, "参数无效，请检查！", "");
		}
		if (imGroupinfo.getGroupName().length() > 15) {
			return super.getAjaxResult(Constants.AJAX_CODE_ERROR_INFO, "群名称不能超过15个字符！", "");
		}
		try {
			String groupId = imGroupinfoService.updateGroupInfo(imGroupinfo, userId);
			// 创建群发送通知给IM服务器
			/*
			 * String result =
			 * OkhttpUtils.sendHttpRequest(ossConfig.getCreate_group_notify_url(),
			 * "{\n  \"id\": \""+groupId+"\"\n}", Constants.AUTHORIZATION);
			 */
			// 通知IM
			ImGroupinfo gnew = imGroupinfoService.findById(groupId);
			IMUtil.updateTeam(gnew.getTid(), gnew.getGroupName(), imPrefix + gnew.getMainUser(), "", "");
			return super.getSuccessAjaxResult("修改成功！", groupId);
		} catch (Exception e) {
			return super.getAjaxResult(Constants.AJAX_CODE_ERROR_INFO, e.getMessage(), "");
		}
	}

	/**
	 * 请用加入群组
	 * 
	 * @param groupId
	 * @param askmessage
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("insertGroupRequest")
	public @ResponseBody Map insertGroupRequest(HttpServletRequest request, String groupId, String askmessage)
			throws IOException {

		String userId = super.checkLogin(request);
		if (userId == null || "".equals(userId)) {
			return super.getAjaxResult(Constants.AJAX_CODE_ERROR_NOTLOGIN, "未登录，请重新登录！", "");
		}

		if (StringUtil.isEmpty(groupId)) {
			return super.getAjaxResult(Constants.AJAX_CODE_ERROR_PARAM, "参数无效，请检查！", "");
		} else {
			try {
				String requestStr = imGrouprequestService.insertGroupUserRequest(groupId, userId, askmessage);
				return super.getSuccessAjaxResult(requestStr, MsgConfig.MSG_KEY_SUCCESS);
			} catch (Exception e) {
				return super.getAjaxResult(Constants.AJAX_CODE_ERROR_INFO, e.getMessage(), "");
			}

		}
	}

	/**
	 * 同意或拒绝加群请求
	 * 
	 * @param requestId
	 * @param status
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("updateGroupRequest")
	public @ResponseBody Map updateGroupRequest(HttpServletRequest request, String requestId, String status)
			throws IOException {

		String userId = super.checkLogin(request);
		if (userId == null || "".equals(userId)) {
			return super.getAjaxResult(Constants.AJAX_CODE_ERROR_NOTLOGIN, "未登录，请重新登录！", "");
		}

		if (StringUtil.isEmpty(requestId) || (!"2".equals(status) && !"3".equals(status))) {
			return super.getAjaxResult(Constants.AJAX_CODE_ERROR_PARAM, "参数无效，请检查！", "");
		} else {
			try {
				String requestStr = imGrouprequestService.updateDualGroupUserRequest(requestId, status);
				if (status.equals("2")) {
					ImGrouprequest imGrequest = imGrouprequestService.findGroupRequestByID(requestId);
					// 创建群发送通知给IM服务器
					/*
					 * String result =
					 * OkhttpUtils.sendHttpRequest(ossConfig.getCreate_group_notify_url(),
					 * "{\n  \"id\": \""+imGrequest.getGroupId()+"\"\n}", Constants.AUTHORIZATION);
					 */
					// 通知IM
					ImGroupinfo groupinfo = imGroupinfoService.findById(imGrequest.getGroupId());
					JSONArray members = new JSONArray();
					members.add(imPrefix + imGrequest.getUserId());
					IMUtil.addTeam(groupinfo.getTid(), imPrefix + groupinfo.getMainUser(), members, "0", "welcome");

				}
				return super.getSuccessAjaxResult("处理请求成功！", requestStr);
			} catch (Exception e) {
				return super.getAjaxResult(Constants.AJAX_CODE_ERROR_INFO, e.getMessage(), "");
			}

		}
	}

	/**
	 * 获取我的群申请
	 * 
	 * @param pageNum
	 * @param pageSize
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("selMyGroupRequest")
	public @ResponseBody Map selMyGroupRequest(HttpServletRequest request, String pageNum, String pageSize)
			throws IOException {

		String userId = super.checkLogin(request);
		if (userId == null || "".equals(userId)) {
			return super.getAjaxResult(Constants.AJAX_CODE_ERROR_NOTLOGIN, "未登录，请重新登录！", "");
		}

		if (StringUtil.isEmpty(pageNum) || StringUtil.isEmpty(pageSize)
				|| !com.wing.socialcontact.util.StringUtil.isNumeric(pageNum)
				|| !com.wing.socialcontact.util.StringUtil.isNumeric(pageSize)) {
			return super.getAjaxResult(Constants.AJAX_CODE_ERROR_PARAM, "参数无效，请检查！", "");
		} else {
			try {
				List performs = imGrouprequestService.findMyGroupRequest(userId, Integer.parseInt(pageNum),
						Integer.parseInt(pageSize));
				return super.getSuccessAjaxResult("获取成功！", performs);
			} catch (Exception e) {
				return super.getAjaxResult(Constants.AJAX_CODE_ERROR_INFO, e.getMessage(), "");
			}

		}
	}

	/**
	 * 根据id获取群信息
	 * 
	 * @param id
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("selGroupInfoById")
	public @ResponseBody Map selGroupInfoById(HttpServletRequest request, String groupId) throws IOException {

		String userId = super.checkLogin(request);
		if (userId == null || "".equals(userId)) {
			return super.getAjaxResult(Constants.AJAX_CODE_ERROR_NOTLOGIN, "未登录，请重新登录！", "");
		}

		if (StringUtil.isEmpty(groupId)) {
			return super.getAjaxResult(Constants.AJAX_CODE_ERROR_PARAM, "参数无效，请检查！", "");
		} else {
			try {
				Map groupInfo = new HashMap();

				ImGroupinfo group = imGroupinfoService.findById(groupId);
				List groupUsers = imGroupusersService.findUserListByGroupIdHasOwner(groupId);
				List groupFavs = imGroupfavService.findFavListByGroupId(groupId);

				groupInfo.put("groupInfo", group);
				groupInfo.put("users", groupUsers);
				groupInfo.put("favs", groupFavs);
				return super.getSuccessAjaxResult("获取成功！", groupInfo);
			} catch (Exception e) {
				return super.getAjaxResult(Constants.AJAX_CODE_ERROR_INFO, e.getMessage(), "");
			}

		}
	}

	/**
	 * 向群组中添加用户信息
	 * 
	 * @param groupId
	 * @param userIds
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("insertGroupUsers")
	public @ResponseBody Map insertGroupUsers(HttpServletRequest request, String groupId, String[] userIds)
			throws IOException {

		String userId = super.checkLogin(request);
		if (userId == null || "".equals(userId)) {
			return super.getAjaxResult(Constants.AJAX_CODE_ERROR_NOTLOGIN, "未登录，请重新登录！", "");
		}

		if (StringUtil.isEmpty(groupId) || userIds == null || userIds.length == 0) {
			return super.getAjaxResult(Constants.AJAX_CODE_ERROR_PARAM, "参数无效，请检查！", "");
		} else {
			try {
				ImGroupinfo group = imGroupinfoService.findById(groupId);
				if (group != null) {
					String creator = group.getCreator();
					imGroupusersService.insertUser(groupId, userIds);

					// 创建群发送通知给IM服务器
					/*
					 * String result =
					 * OkhttpUtils.sendHttpRequest(ossConfig.getCreate_group_notify_url(),
					 * "{\n  \"id\": \""+groupId+"\"\n}", Constants.AUTHORIZATION);
					 */

					// 通知IM
					JSONArray members = new JSONArray();
					if (userIds != null && userIds.length != 0) {
						for (int i = 0; i < userIds.length; i++) {
							members.add(imPrefix + userIds[i]);
						}
					}
					IMUtil.addTeam(group.getTid(), imPrefix + group.getMainUser(), members, "0", "welcome");

				} else {
					throw new RuntimeException("获取群组信息失败，请检查群ID信息。");
				}

				return super.getSuccessAjaxResult("添加成功！", "");
			} catch (Exception e) {
				return super.getAjaxResult(Constants.AJAX_CODE_ERROR_INFO, e.getMessage(), "");
			}

		}
	}

	/**
	 * 从群组中删除用户信息
	 * 
	 * @param groupId
	 * @param userIds
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("delGroupUsers")
	public @ResponseBody Map delGroupUsers(HttpServletRequest request, String groupId, String[] userIds)
			throws IOException {

		String userId = super.checkLogin(request);
		if (userId == null || "".equals(userId)) {
			return super.getAjaxResult(Constants.AJAX_CODE_ERROR_NOTLOGIN, "未登录，请重新登录！", "");
		}

		if (StringUtil.isEmpty(groupId) || userIds == null || userIds.length == 0) {
			return super.getAjaxResult(Constants.AJAX_CODE_ERROR_PARAM, "参数无效，请检查！", "");
		} else {
			try {
				ImGroupinfo group = imGroupinfoService.findById(groupId);
				if (group != null) {
					String creator = group.getCreator();
					if (userId.equals(creator)) {
						imGroupusersService.delUser(groupId, userIds);
						// 创建群发送通知给IM服务器
						/*
						 * String result =
						 * OkhttpUtils.sendHttpRequest(ossConfig.getCreate_group_notify_url(),
						 * "{\n  \"id\": \""+groupId+"\"\n}", Constants.AUTHORIZATION);
						 */
						// 通知IM
						if (userIds != null && userIds.length != 0) {
							for (int i = 0; i < userIds.length; i++) {
								IMUtil.kickTeam(group.getTid(), imPrefix + group.getMainUser(), imPrefix + userIds[i]);
							}
						}

					} else {
						throw new RuntimeException("非群管理员操作，操作失败！");
					}
				} else {
					throw new RuntimeException("获取群组信息失败，请检查群ID信息。");
				}

				return super.getSuccessAjaxResult("删除成功！", "");
			} catch (Exception e) {
				return super.getAjaxResult(Constants.AJAX_CODE_ERROR_INFO, e.getMessage(), "");
			}

		}
	}

	/**
	 * 修改标签信息
	 * 
	 * @param groupId
	 * @param favIds
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("updateGroupFavs")
	public @ResponseBody Map updateGroupFavs(HttpServletRequest request, String groupId, String[] favIds)
			throws IOException {

		String userId = super.checkLogin(request);
		if (userId == null || "".equals(userId)) {
			return super.getAjaxResult(Constants.AJAX_CODE_ERROR_NOTLOGIN, "未登录，请重新登录！", "");
		}

		if (StringUtil.isEmpty(groupId) || favIds == null || favIds.length == 0) {
			return super.getAjaxResult(Constants.AJAX_CODE_ERROR_PARAM, "参数无效，请检查！", "");
		} else {
			try {

				ImGroupinfo group = imGroupinfoService.findById(groupId);
				if (group != null) {
					String creator = group.getCreator();
					if (userId.equals(creator)) {
						imGroupfavService.updateFavListByGroupId(groupId, favIds);
					} else {
						throw new RuntimeException("非群管理员操作，操作失败！");
					}
				} else {
					throw new RuntimeException("获取群组信息失败，请检查群ID信息。");
				}
				return super.getSuccessAjaxResult("更新标签成功！", "");
			} catch (Exception e) {
				return super.getAjaxResult(Constants.AJAX_CODE_ERROR_INFO, e.getMessage(), "");
			}
		}
	}

	/**
	 * 设置私有或公开群标志
	 * 
	 * @param groupId
	 * @param favIds
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("updateGroupIsPrivate")
	public @ResponseBody Map updateGroupIsPrivate(HttpServletRequest request, String groupId, String status)
			throws IOException {

		String userId = super.checkLogin(request);
		if (userId == null || "".equals(userId)) {
			return super.getAjaxResult(Constants.AJAX_CODE_ERROR_NOTLOGIN, "未登录，请重新登录！", "");
		}

		if (StringUtil.isEmpty(groupId) || status == null || (!"1".equals(status) && !"2".equals(status))) {
			return super.getAjaxResult(Constants.AJAX_CODE_ERROR_PARAM, "参数无效，请检查！", "");
		} else {
			try {
				ImGroupinfo group = imGroupinfoService.findById(groupId);
				if (group != null) {
					String creator = group.getCreator();
					if (userId.equals(creator)) {
						imGroupinfoService.setGroupIsPrivate(groupId, status);
					} else {
						throw new RuntimeException("非群管理员操作，操作失败！");
					}
				} else {
					throw new RuntimeException("获取群组信息失败，请检查群ID信息。");
				}
				if ("1".equals(status)) {
					return super.getSuccessAjaxResult("设置为公开群成功！", "");
				} else {
					return super.getSuccessAjaxResult("设置为私有群成功！", "");
				}

			} catch (Exception e) {
				return super.getAjaxResult(Constants.AJAX_CODE_ERROR_INFO, e.getMessage(), "");
			}
		}
	}

	/**
	 * 设置是否置顶
	 * 
	 * @param groupId
	 * @param status
	 *            1 置顶 0 取消置顶
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("updateGroupIsTop")
	public @ResponseBody Map updateGroupIsTop(HttpServletRequest request, String groupId, String status)
			throws IOException {

		String userId = super.checkLogin(request);
		if (userId == null || "".equals(userId)) {
			return super.getAjaxResult(Constants.AJAX_CODE_ERROR_NOTLOGIN, "未登录，请重新登录！", "");
		}

		if (StringUtil.isEmpty(groupId) || status == null || (!"1".equals(status) && !"0".equals(status))) {
			return super.getAjaxResult(Constants.AJAX_CODE_ERROR_PARAM, "参数无效，请检查！", "");
		} else {
			try {

				ImGroupinfo group = imGroupinfoService.findById(groupId);
				if (group != null) {
					imGroupusersService.isTop(groupId, userId, status);
				} else {
					throw new RuntimeException("获取群组信息失败，请检查群ID信息。");
				}
				if ("1".equals(status)) {
					return super.getSuccessAjaxResult("设置群置顶成功！", "");
				} else {
					return super.getSuccessAjaxResult("取消置顶成功！", "");
				}

			} catch (Exception e) {
				return super.getAjaxResult(Constants.AJAX_CODE_ERROR_INFO, e.getMessage(), "");
			}
		}
	}

	/**
	 * 设置消息免打扰
	 * 
	 * @param groupId
	 * @param status
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("updateGroupMsgDistrub")
	public @ResponseBody Map updateGroupMsgDistrub(HttpServletRequest request, String groupId, String status)
			throws IOException {

		String userId = super.checkLogin(request);
		if (userId == null || "".equals(userId)) {
			return super.getAjaxResult(Constants.AJAX_CODE_ERROR_NOTLOGIN, "未登录，请重新登录！", "");
		}

		if (StringUtil.isEmpty(groupId) || status == null || (!"1".equals(status) && !"0".equals(status))) {
			return super.getAjaxResult(Constants.AJAX_CODE_ERROR_PARAM, "参数无效，请检查！", "");
		} else {
			try {

				ImGroupinfo group = imGroupinfoService.findById(groupId);
				if (group != null) {
					imGroupusersService.msgDistrub(groupId, userId, status);
				} else {
					throw new RuntimeException("获取群组信息失败，请检查群ID信息。");
				}
				if ("1".equals(status)) {
					return super.getSuccessAjaxResult("设置群消息免打扰成功！", "");
				} else {
					return super.getSuccessAjaxResult("取消群消息可打扰成功！", "");
				}

			} catch (Exception e) {
				return super.getAjaxResult(Constants.AJAX_CODE_ERROR_INFO, e.getMessage(), "");
			}
		}
	}

	/**
	 * 退群
	 * 
	 * @param groupId
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("updateBackGroup")
	public @ResponseBody Map updateBackGroup(HttpServletRequest request, String groupId) throws IOException {

		String userId = super.checkLogin(request);
		if (userId == null || "".equals(userId)) {
			return super.getAjaxResult(Constants.AJAX_CODE_ERROR_NOTLOGIN, "未登录，请重新登录！", "");
		}

		if (StringUtil.isEmpty(groupId)) {
			return super.getAjaxResult(Constants.AJAX_CODE_ERROR_PARAM, "参数无效，请检查！", "");
		} else {
			try {

				ImGroupinfo group = imGroupinfoService.findById(groupId);
				if (group != null) {
					String creator = group.getCreator();
					if (creator.equals(userId)) {
						throw new RuntimeException("群的所有者不允许退群!");
					} else {
						imGroupusersService.backGroup(groupId, userId);
						// 创建群发送通知给IM服务器
						/*
						 * String result =
						 * OkhttpUtils.sendHttpRequest(ossConfig.getCreate_group_notify_url(),
						 * "{\n  \"id\": \""+groupId+"\"\n}", Constants.AUTHORIZATION);
						 */
						// 通知IM
						IMUtil.leaveTeam(group.getTid(), imPrefix + userId);
					}
				} else {
					throw new RuntimeException("获取群组信息失败，请检查群ID信息。");
				}

				return super.getSuccessAjaxResult("退群成功！", "");
			} catch (Exception e) {
				return super.getAjaxResult(Constants.AJAX_CODE_ERROR_INFO, e.getMessage(), "");
			}
		}
	}

	/**
	 * 解散群
	 * 
	 * @param groupId
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("deleteGroup")
	public @ResponseBody Map deleteGroup(HttpServletRequest request, String groupId) throws IOException {

		String userId = super.checkLogin(request);
		if (userId == null || "".equals(userId)) {
			return super.getAjaxResult(Constants.AJAX_CODE_ERROR_NOTLOGIN, "未登录，请重新登录！", "");
		}

		if (StringUtil.isEmpty(groupId)) {
			return super.getAjaxResult(Constants.AJAX_CODE_ERROR_PARAM, "参数无效，请检查！", "");
		} else {
			try {

				ImGroupinfo group = imGroupinfoService.findById(groupId);
				if (group != null) {
					String creator = group.getCreator();
					if (creator.equals(userId)) {
						imGroupinfoService.delGroupInfo(groupId, userId);
						// 创建群发送通知给IM服务器
						/*
						 * String result =
						 * OkhttpUtils.sendHttpRequest(ossConfig.getDelete_group_notify_url(),
						 * "{\n  \"id\": \""+groupId+"\"\n}", Constants.AUTHORIZATION);
						 */
						IMUtil.removeTeam(group.getTid(), imPrefix + group.getMainUser());

					} else {
						throw new RuntimeException("非群的所有者不允许解散群!");
					}
				} else {
					throw new RuntimeException("获取群组信息失败，请检查群ID信息。");
				}

				return super.getSuccessAjaxResult("解散群成功！", "");
			} catch (Exception e) {
				return super.getAjaxResult(Constants.AJAX_CODE_ERROR_INFO, e.getMessage(), "");
			}
		}
	}

	/**
	 * 根据组id获取置顶用户信息列表
	 * 
	 * @return
	 */
	@RequestMapping("selTopFriendListByGroupId")
	public @ResponseBody Map selTopFriendListByGroupId(HttpServletRequest request, String groupId, String pageNum,
			String pageSize) throws IOException {

		String userId = super.checkLogin(request);
		if (userId == null || "".equals(userId)) {
			return super.getAjaxResult(Constants.AJAX_CODE_ERROR_NOTLOGIN, "未登录，请重新登录！", "");
		}
		if (StringUtil.isEmpty(groupId) || StringUtil.isEmpty(pageNum) || StringUtil.isEmpty(pageSize)
				|| !com.wing.socialcontact.util.StringUtil.isNumeric(pageNum)
				|| !com.wing.socialcontact.util.StringUtil.isNumeric(pageSize)) {
			return super.getAjaxResult(Constants.AJAX_CODE_ERROR_PARAM, "参数无效，请检查！", "");
		} else {
			try {
				List performs = imToprelatService.findTopFriendListByGroupId(groupId, Integer.parseInt(pageNum),
						Integer.parseInt(pageSize));
				return super.getSuccessAjaxResult("获取成功！", performs);
			} catch (Exception e) {
				return super.getAjaxResult(Constants.AJAX_CODE_ERROR_INFO, e.getMessage(), "");
			}
		}

	}

	/**
	 * 获取我的同好圈子
	 * 
	 * @return
	 */
	@RequestMapping("selGourpsListByName")
	public @ResponseBody Map selGourpsListByName(HttpServletRequest request, String groupName, String pageNum,
			String pageSize) throws IOException {
		String userId = super.checkLogin(request);
		if (userId == null || "".equals(userId)) {
			return super.getAjaxResult(Constants.AJAX_CODE_ERROR_NOTLOGIN, "未登录，请重新登录！", "");
		}

		if (StringUtil.isEmpty(pageNum) || StringUtil.isEmpty(pageSize)
				|| !com.wing.socialcontact.util.StringUtil.isNumeric(pageNum)
				|| !com.wing.socialcontact.util.StringUtil.isNumeric(pageSize)) {
			return super.getAjaxResult(Constants.AJAX_CODE_ERROR_PARAM, "参数无效，请检查！", "");
		} else {
			try {
				List performs = imGroupinfoService.selGourpsListByName(userId, groupName, Integer.parseInt(pageNum),
						Integer.parseInt(pageSize));
				return super.getSuccessAjaxResult("群组列表获取成功！", performs);
			} catch (Exception e) {
				return super.getAjaxResult(Constants.AJAX_CODE_ERROR_INFO, e.getMessage(), "");
			}
		}
	}

	/**
	 * 初始化群组
	 * 
	 * @return
	 */
	@RequestMapping("initGourps")
	public @ResponseBody Map initGourps(HttpServletRequest request) throws IOException {
		String userId = super.checkLogin(request);
		if (userId == null || "".equals(userId)) {
			return super.getAjaxResult(Constants.AJAX_CODE_ERROR_NOTLOGIN, "未登录，请重新登录！", "");
		}
		try {
			imGroupinfoService.initGourps(null, null);
			return super.getSuccessAjaxResult("初始化群组成功！", "");
		} catch (Exception e) {
			return super.getAjaxResult(Constants.AJAX_CODE_ERROR_INFO, e.getMessage(), "");
		}
	}

	/**
	 * 根据tid获取群id
	 * 
	 * @param id
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("selGroupIdByTid")
	public @ResponseBody Map selGroupIdByTid(HttpServletRequest request, String tid) throws IOException {

		String userId = super.checkLogin(request);
		if (userId == null || "".equals(userId)) {
			return super.getAjaxResult(Constants.AJAX_CODE_ERROR_NOTLOGIN, "未登录，请重新登录！", "");
		}

		if (StringUtil.isEmpty(tid)) {
			return super.getAjaxResult(Constants.AJAX_CODE_ERROR_PARAM, "参数无效，请检查！", "");
		} else {
			try {
				ImGroupinfo group = imGroupinfoService.findByTid(tid);
				return super.getSuccessAjaxResult("获取成功！", group.getId());
			} catch (Exception e) {
				return super.getAjaxResult(Constants.AJAX_CODE_ERROR_INFO, e.getMessage(), "");
			}

		}
	}
}
