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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.tojoycloud.common.ConstantDefinition;
import com.tojoycloud.common.report.ResponseCode;
import com.tojoycloud.common.report.base.BaseAppAction;
import com.tojoycloud.report.RequestReport;
import com.tojoycloud.report.ResponseReport;
import com.wing.socialcontact.config.MsgConfig;
import com.wing.socialcontact.front.util.ApplicationPath;
import com.wing.socialcontact.service.im.api.IImGroupfavService;
import com.wing.socialcontact.service.im.api.IImGroupinfoService;
import com.wing.socialcontact.service.im.api.IImGrouprequestService;
import com.wing.socialcontact.service.im.api.IImGroupusersService;
import com.wing.socialcontact.service.im.bean.ImGroupinfo;
import com.wing.socialcontact.service.im.bean.ImGrouprequest;
import com.wing.socialcontact.service.wx.api.IUserIntegralLogService;
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
@RequestMapping("/im/m/team")
public class ImGroupInfoAction extends BaseAppAction {

	@Autowired
	private IImGroupinfoService imGroupinfoService;
	@Autowired
	private IImGrouprequestService imGrouprequestService;
	@Autowired
	private IImGroupusersService imGroupusersService;
	@Autowired
	private IImGroupfavService imGroupfavService;
	@Resource
	protected IUserIntegralLogService userIntegralLogService;

	private String imPrefix = ApplicationPath.getParameter(ConstantDefinition.IM_PREFIX);

	/**
	 * 获取我的群组（暂不使用）
	 * 
	 * @param rr
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("selMyGroupInfoList")
	public @ResponseBody ResponseReport findMyGroupInfoListByUserId(@RequestBody RequestReport rr,
			HttpServletRequest request) throws IOException {

		String userId = rr.getDataValue("owner");
		String pageNum = rr.getDataValue("pageNum");
		String pageSize = rr.getDataValue("pageSize");

		try {
			List performs = imGroupinfoService.findMyGroupInfoListByUserId(userId, Integer.parseInt(pageNum),
					Integer.parseInt(pageSize), null);
			int newRequestCount = imGrouprequestService.findNewGroupRequestCountByUserid(userId);
			Map resultMap = new HashMap();
			resultMap.put("groupInfoList", performs);
			resultMap.put("newRequestCount", newRequestCount);
			return super.getSuccessAjaxResult(rr, "获取成功！", resultMap);
		} catch (Exception e) {
			return super.getAjaxResult(rr, ResponseCode.Error, e.getMessage(), "");
		}
	}

	/**
	 * 获取我的群组总数（暂不使用）
	 * 
	 * @return
	 */
	@RequestMapping("selMyGroupInfoCount")
	public @ResponseBody ResponseReport selMyGroupInfoCount(@RequestBody RequestReport rr, HttpServletRequest request)
			throws IOException {
		String userId = rr.getDataValue("owner");
		try {
			int count = imGroupinfoService.findMyGroupInfCountByUserId(userId);
			return super.getSuccessAjaxResult(rr, "获取群组数量成功！", count);
		} catch (Exception e) {
			return super.getAjaxResult(rr, ResponseCode.Error, e.getMessage(), "");
		}
	}

	/**
	 * 创建群
	 * 
	 * @param rr
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("create")
	public @ResponseBody ResponseReport create(@RequestBody RequestReport rr, HttpServletRequest request)
			throws IOException {

		String userId = rr.getDataValue("owner"); // 群主ID
		String tname = rr.getDataValue("tname");// 群名
		String[] userIds = StringUtils.split(rr.getDataValue("members"), ",");// 群成员ID
		String[] favIds = rr.getDataValue("tags") == null ? null : StringUtils.split(rr.getDataValue("tags"), ",");// 群标签组
		String isSecret = rr.getDataValue("isSecret");// 是否私密群
		String ticon = rr.getDataValue("ticon");

		String isTop = rr.getDataValue("isTop") == null ? "0" : rr.getDataValue("isTop"); // 是否置顶，默认非
		String disturb = rr.getDataValue("disturb") == null ? "0" : rr.getDataValue("disturb");// 是否免打扰，默认非

		ImGroupinfo imGroupinfo = new ImGroupinfo();
		imGroupinfo.setGroupName(tname);
		imGroupinfo.setCreator(userId);
		imGroupinfo.setGroupType(Integer.valueOf(isSecret));

		JSONObject jsonObject = new JSONObject();
		try {
			if (ticon == null) {
				String imgPath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
						+ request.getContextPath() + "/" + "resource/img/images/qun.png";
				imGroupinfo.setHeadPortrait(imgPath);
			}
			String groupId = imGroupinfoService.insertGroupInfo(imGroupinfo, userIds, isTop, disturb, favIds);
			// 通知IM
			String tid = "";
			JSONArray members = new JSONArray();
			if (userIds != null && userIds.length != 0) {
				for (int i = 0; i < userIds.length; i++) {
					members.add(imPrefix + userIds[i]);
				}
			}
			tid = IMUtil.createTeamOne(imGroupinfo.getGroupName(), imPrefix + imGroupinfo.getCreator(), members);
			ImGroupinfo groupinfo = imGroupinfoService.findById(groupId);
			groupinfo.setTid(tid);
			imGroupinfoService.updateGI(groupinfo);
			// 此项年度积分增加上限为100
			userIntegralLogService.addLntAndEmp(userId, "task_0017");
			jsonObject.put("groupId", groupId);
			jsonObject.put("tId", tid);
			return super.getSuccessAjaxResult(rr, "创建成功！", jsonObject);
		} catch (RuntimeException e) {
			jsonObject.put("desc", e.getMessage());
			return super.getAjaxResult(rr, ResponseCode.Error, e.getMessage(), jsonObject);
		}
	}

	/**
	 * 更新群：群资料（包括群名、群头像、群简介）
	 * 
	 * @param rr
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("update")
	public @ResponseBody ResponseReport updateMyGroupInfo(@RequestBody RequestReport rr, HttpServletRequest request)
			throws IOException {

		String userId = rr.getDataValue("owner");
		String groupId = rr.getDataValue("tId");
		String icon = StringUtils.isEmpty(rr.getDataValue("icon")) ? "" : rr.getDataValue("icon");// 群头像
		String intro = StringUtils.isEmpty(rr.getDataValue("intro")) ? "" : rr.getDataValue("intro");// 群简介
		String groupName = StringUtils.isEmpty(rr.getDataValue("tname")) ? "" : rr.getDataValue("tname");// 群名;
		// String announcement = rr.getDataValue("announcement");

		ImGroupinfo imGroupinfo = new ImGroupinfo();
		imGroupinfo.setId(groupId);
		if (StringUtils.isNotEmpty(icon))
			imGroupinfo.setHeadPortrait(icon);
		if (StringUtils.isNotEmpty(groupName))
			imGroupinfo.setGroupName(groupName);
		if (StringUtils.isNotEmpty(intro))
			imGroupinfo.setGroupDesc(intro);

		try {
			imGroupinfoService.updateGroupInfo(imGroupinfo, userId);
			ImGroupinfo gnew = imGroupinfoService.findById(groupId);
			IMUtil.updateTeam(gnew.getTid(), groupName, imPrefix + userId, icon, intro);
			return super.getSuccessAjaxResult(rr, "修改成功！", groupId);
		} catch (Exception e) {
			return super.getAjaxResult(rr, ResponseCode.Error, e.getMessage(), "");
		}
	}

	/**
	 * 用户主动加群请求
	 * 
	 * @param rr
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("join")
	public @ResponseBody ResponseReport join(@RequestBody RequestReport rr, HttpServletRequest request)
			throws IOException {

		String userId = rr.getDataValue("fromId");
		String groupId = rr.getDataValue("tId");
		String askmessage = rr.getDataValue("msg");
		String requestStr = "";

		try {
			requestStr = imGrouprequestService.insertGroupUserRequest(groupId, userId, askmessage);
			if (requestStr.equals(MsgConfig.MSG_KEY_SUCCESS)) {
				return super.getSuccessAjaxResult(rr, "成功", requestStr);
			}
		} catch (Exception e) {
			return super.getAjaxResult(rr, ResponseCode.Error, e.getMessage(), "");
		}
		return super.getAjaxResult(rr, ResponseCode.Error, requestStr, "");
	}

	/**
	 * 入群请求处理,只与业务交互，不与网易IM交互
	 * 
	 * @param rr
	 * @param request
	 * @return
	 */
	@RequestMapping("join/update")
	public @ResponseBody ResponseReport joinUpdate(@RequestBody RequestReport rr, HttpServletRequest request) {

		String fromId = rr.getDataValue("fromId");// 当前人ID
		String groupId = rr.getDataValue("tId"); // 群ID
		String status = rr.getDataValue("status");// 2同意，3拒绝

		try {
			ImGrouprequest imGrouprequest = new ImGrouprequest();
			imGrouprequest.setUserId(fromId);
			imGrouprequest.setGroupId(groupId);
			imGrouprequest.setStatus(1);// 待确认状态

			List<ImGrouprequest> requestList = imGrouprequestService.selectAllImGrouprequest(imGrouprequest);
			if (requestList == null) {
				throw new RuntimeException("用户加群请求不存在");
			}
			String requestStr = imGrouprequestService.updateDualGroupUserRequest(requestList.get(0).getId(), status);
			if (requestStr.equals(MsgConfig.MSG_KEY_SUCCESS)) {
				return super.getSuccessAjaxResult(rr, "成功", requestStr);
			} else {
				return super.getAjaxResult(rr, ResponseCode.Error, requestStr, "");
			}
		} catch (Exception e) {
			return super.getAjaxResult(rr, ResponseCode.Error, e.getMessage(), "");
		}
	}

	/**
	 * 获取群信息
	 * 
	 * @param rr
	 * @param request
	 * @return
	 */
	@RequestMapping("info")
	public @ResponseBody ResponseReport info(@RequestBody RequestReport rr, HttpServletRequest request) {

		String groupId = rr.getDataValue("groupId");

		try {
			Map groupInfo = new HashMap();

			ImGroupinfo group = imGroupinfoService.findById(groupId);
			List groupUsers = imGroupusersService.findUserListByGroupIdHasOwner(groupId);
			List groupFavs = imGroupfavService.findFavListByGroupId(groupId);

			groupInfo.put("groupInfo", group);
			groupInfo.put("users", groupUsers);
			groupInfo.put("favs", groupFavs);
			return super.getSuccessAjaxResult(rr, "获取成功！", groupInfo);
		} catch (Exception e) {
			return super.getAjaxResult(rr, ResponseCode.Error, e.getMessage(), "");
		}
	}

	/**
	 * 拉人进群
	 * 
	 * @param rr
	 * @param request
	 * @return
	 */
	@RequestMapping("add")
	public @ResponseBody ResponseReport add(@RequestBody RequestReport rr, HttpServletRequest request) {

		String groupId = rr.getDataValue("tId");
		String[] userIds = StringUtils.split(rr.getDataValue("members"), ",");
		String magree = "".equals(rr.getDataValue("magree")) ? "0" : rr.getDataValue("magree");
		String msg = "".equals(rr.getDataValue("msg")) ? "欢迎入群" : rr.getDataValue("msg");
		String neteaseOpeRes = "";

		try {
			ImGroupinfo group = imGroupinfoService.findById(groupId);
			if (group != null) {
				imGroupusersService.insertUser(groupId, userIds);
				// 通知网易IM
				JSONArray members = new JSONArray();
				if (userIds != null && userIds.length != 0) {
					for (int i = 0; i < userIds.length; i++) {
						members.add(imPrefix + userIds[i]);
					}
				}
				neteaseOpeRes = IMUtil.addTeam(group.getTid(), imPrefix + group.getCreator(), members, magree, msg);
			} else {
				throw new RuntimeException("群ID不存在");
			}
			return super.getSuccessAjaxResult(rr, "添加成功！", neteaseOpeRes);
		} catch (Exception e) {
			return super.getAjaxResult(rr, ResponseCode.Error, e.getMessage(), "");
		}
	}

	/**
	 * 踢人
	 * @param rr
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("kick")
	public @ResponseBody ResponseReport kick(@RequestBody RequestReport rr, HttpServletRequest request)
			throws IOException {

		String fromId = rr.getDataValue("fromId");
		String[] userIds = StringUtils.split(rr.getDataValue("toIds"), ",");
		String groupId = rr.getDataValue("tId");
		String attach = rr.getDataValue("attach");

		try {
			ImGroupinfo group = imGroupinfoService.findById(groupId);
			if (group != null) {
				String creator = group.getCreator();
				if (fromId.equals(creator)) {
					String resultMsg = imGroupusersService.delUser(groupId, userIds);
					if (resultMsg.equals(MsgConfig.MSG_KEY_SUCCESS)) {
						// 通知IM
						if (userIds != null && userIds.length != 0) {
							for (int i = 0; i < userIds.length; i++) {
								IMUtil.kickTeam(group.getTid(), imPrefix + group.getMainUser(), imPrefix + userIds[i]);
							}
						}
					}
				} else {
					throw new RuntimeException("非群主操作");
				}
			} else {
				throw new RuntimeException("群ID不存在");
			}
			return super.getSuccessAjaxResult(rr, "踢出成功！", "");
		} catch (Exception e) {
			return super.getAjaxResult(rr, ResponseCode.Error, e.getMessage(), "");
		}
	}

	/**
	 * 更新群：标签
	 * 
	 * @param rr
	 * @param request
	 * @return
	 */
	@RequestMapping("update/tags")
	public @ResponseBody ResponseReport updateGroupFavs(@RequestBody RequestReport rr, HttpServletRequest request) {

		String userId = rr.getDataValue("owner");
		String groupId = rr.getDataValue("tId");
		String[] favIds = StringUtils.split(rr.getDataValue("tags"), ",");

		try {
			ImGroupinfo group = imGroupinfoService.findById(groupId);
			if (group != null) {
				String creator = group.getCreator();
				if (userId.equals(creator)) {
					imGroupfavService.updateFavListByGroupId(groupId, favIds);
				} else {
					throw new RuntimeException("非群主操作");
				}
			} else {
				throw new RuntimeException("群ID不存在");
			}
			return super.getSuccessAjaxResult(rr, "更新标签成功！", "");
		} catch (Exception e) {
			return super.getAjaxResult(rr, ResponseCode.Error, e.getMessage(), "");
		}
	}

	/**
	 * 更新群：私密与否
	 * 
	 * @param rr
	 * @param request
	 * @return
	 */
	@RequestMapping("update/secret")
	public @ResponseBody ResponseReport updateGroupIsPrivate(@RequestBody RequestReport rr,
			HttpServletRequest request) {

		String userId = rr.getDataValue("owner");
		String groupId = rr.getDataValue("tId");
		String status = rr.getDataValue("status");

		try {
			ImGroupinfo group = imGroupinfoService.findById(groupId);
			if (group != null) {
				String creator = group.getCreator();
				if (userId.equals(creator)) {
					imGroupinfoService.setGroupIsPrivate(groupId, status);
				} else {
					throw new RuntimeException("非群主操作");
				}
			} else {
				throw new RuntimeException("群ID不存在");
			}
			return super.getSuccessAjaxResult(rr, "成功！", "");
		} catch (Exception e) {
			return super.getAjaxResult(rr, ResponseCode.Error, e.getMessage(), "");
		}
	}

	/**
	 * 更新群：是否置顶
	 * 
	 * @param rr
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("update/top")
	public @ResponseBody ResponseReport updateGroupIsTop(@RequestBody RequestReport rr, HttpServletRequest request) {

		String userId = rr.getDataValue("fromId");
		String groupId = rr.getDataValue("tId");
		String status = rr.getDataValue("status");// 0取消；1置顶

		try {
			ImGroupinfo group = imGroupinfoService.findById(groupId);
			if (group != null) {
				imGroupusersService.isTop(groupId, userId, status);
			} else {
				throw new RuntimeException("群ID不存在");
			}
			return super.getSuccessAjaxResult(rr, "成功！", "");
		} catch (Exception e) {
			return super.getAjaxResult(rr, ResponseCode.Error, e.getMessage(), "");
		}
	}

	/**
	 * 更新群：是否免打扰
	 * 
	 * @param rr
	 * @param request
	 * @return
	 */
	@RequestMapping("update/mute")
	public @ResponseBody ResponseReport updateGroupMsgDistrub(@RequestBody RequestReport rr,
			HttpServletRequest request) {

		String userId = rr.getDataValue("fromId");
		String groupId = rr.getDataValue("tId");
		String status = rr.getDataValue("status");// 0接收消息提醒； 1、不接受消息提醒

		try {
			ImGroupinfo group = imGroupinfoService.findById(groupId);
			if (group != null) {
				String neteaseTid = group.getTid();
				String resultMsg = imGroupusersService.msgDistrub(groupId, userId, status);
				if (resultMsg.equals(MsgConfig.MSG_KEY_SUCCESS)) {
					String ope = status.equals("0") ? "2" : "1";
					IMUtil.muteTeam(neteaseTid, imPrefix + userId, ope);
				}
			} else {
				throw new RuntimeException("群ID不存在");
			}
			return super.getSuccessAjaxResult(rr, "成功", "");
		} catch (Exception e) {
			return super.getAjaxResult(rr, ResponseCode.Error, e.getMessage(), "");
		}
	}

	/**
	 * TODO 更新群昵称
	 * 
	 * @param rr
	 * @param request
	 * @return
	 */
	@RequestMapping("update/teamNick")
	public @ResponseBody ResponseReport updateTeamNick(@RequestBody RequestReport rr, HttpServletRequest request) {

		String userId = rr.getDataValue("fromId");// 当前人ID
		String owner = rr.getDataValue("owner");// 群主ID
		String groupId = rr.getDataValue("tId");// 群ID
		String nick = rr.getDataValue("nick");
		String custom = rr.getDataValue("custom");

		try {
			ImGroupinfo group = imGroupinfoService.findById(groupId);
			if (group != null) {
				String neteaseTid = group.getTid();

			} else {
				throw new RuntimeException("群ID不存在");
			}
			return super.getSuccessAjaxResult(rr, "成功", "");
		} catch (Exception e) {
			return super.getAjaxResult(rr, ResponseCode.Error, e.getMessage(), "");
		}
	}

	/**
	 * 用户主动退群
	 * 
	 * @param rr
	 * @param request
	 * @return
	 */
	@RequestMapping("leave")
	public @ResponseBody ResponseReport leave(@RequestBody RequestReport rr, HttpServletRequest request) {

		String fromId = rr.getDataValue("fromId");
		String groupId = rr.getDataValue("tId");

		try {
			ImGroupinfo group = imGroupinfoService.findById(groupId);
			if (group != null) {
				imGroupusersService.backGroup(groupId, fromId);
				// 通知IM
				IMUtil.leaveTeam(group.getTid(), imPrefix + fromId);
			} else {
				throw new RuntimeException("群ID不存在");
			}
			return super.getSuccessAjaxResult(rr, "成功", "");
		} catch (Exception e) {
			return super.getAjaxResult(rr, ResponseCode.Error, e.getMessage(), "");
		}
	}

	/**
	 * 解散群
	 * 
	 * @param groupId
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("remove")
	public @ResponseBody ResponseReport remove(@RequestBody RequestReport rr, HttpServletRequest request) {

		String userId = rr.getDataValue("owner");
		String groupId = rr.getDataValue("tId");

		try {
			ImGroupinfo group = imGroupinfoService.findById(groupId);
			if (group != null) {
				String creator = group.getCreator();
				if (creator.equals(userId)) {
					imGroupinfoService.delGroupInfo(groupId, userId);
					IMUtil.removeTeam(group.getTid(), imPrefix + userId);
				} else {
					throw new RuntimeException("非群主操作");
				}
			} else {
				throw new RuntimeException("群ID不存在");
			}
			return super.getSuccessAjaxResult(rr, "成功", "");
		} catch (Exception e) {
			return super.getAjaxResult(rr, ResponseCode.Error, e.getMessage(), "");
		}
	}
}
