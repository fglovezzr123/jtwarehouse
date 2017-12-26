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
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wing.socialcontact.common.action.BaseAction;
import com.wing.socialcontact.front.util.ApplicationPath;
import com.wing.socialcontact.front.util.ConstantDefinition;
import com.wing.socialcontact.service.im.api.IImFriendService;
import com.wing.socialcontact.service.im.api.IImGroupinfoService;
import com.wing.socialcontact.service.im.api.IImToprelatService;
import com.wing.socialcontact.service.im.bean.ImFriend;
import com.wing.socialcontact.util.Constants;
import com.wing.socialcontact.util.MD5Util;
import com.wing.socialcontact.util.im.IMUtil;

import tk.mybatis.mapper.util.StringUtil;

/**
 * 好友接口
 * @author	xuxinyuan
 * @version	 1.0
 *
 */
@Controller
@RequestMapping("/im/m")
public class ImFriendAction extends BaseAction {

	@Autowired
	private IImFriendService imFriendService;
	@Autowired
	private IImToprelatService imToprelatService;
	@Autowired
	private IImGroupinfoService imGroupinfoService;

	private String imPrefix = ApplicationPath.getParameter(ConstantDefinition.IM_PREFIX);

	/**
	 * 获取好友列表
	 * @return
	 */
	@RequestMapping("selMyFriendList")
	public @ResponseBody Map selMyFriendList(HttpServletRequest request, String pageNum, String pageSize, String isAll,
			String nickname, String userId) throws IOException {

		if (StringUtils.isEmpty(userId)) {
			userId = super.checkLogin(request);
		}
		if (userId == null || "".equals(userId)) {
			return super.getAjaxResult(Constants.AJAX_CODE_ERROR_NOTLOGIN, "未登录，请重新登录！", "");
		}
		if (StringUtil.isEmpty(isAll) || StringUtil.isEmpty(pageNum) || StringUtil.isEmpty(pageSize)
				|| !com.wing.socialcontact.util.StringUtil.isNumeric(pageNum)
				|| !com.wing.socialcontact.util.StringUtil.isNumeric(pageSize)) {
			return super.getAjaxResult(Constants.AJAX_CODE_ERROR_PARAM, "参数无效，请检查！", "");
		} else {
			try {
				List performs = imFriendService.findMyFriendListByUserId(userId, Integer.parseInt(pageNum),
						Integer.parseInt(pageSize), isAll, nickname);
				return super.getSuccessAjaxResult("获取成功！", performs);
			} catch (Exception e) {
				return super.getAjaxResult(Constants.AJAX_CODE_ERROR_INFO, e.getMessage(), "");
			}
		}

	}

	/**
	 * 获取好友列表不含星标用户
	 * @return
	 */
	@RequestMapping("selMyFriendList1")
	public @ResponseBody Map selMyFriendList1(HttpServletRequest request, String pageNum, String pageSize, String isAll,
			String nickname, String userId) throws IOException {

		if (StringUtils.isEmpty(userId)) {
			userId = super.checkLogin(request);
		}
		if (userId == null || "".equals(userId)) {
			return super.getAjaxResult(Constants.AJAX_CODE_ERROR_NOTLOGIN, "未登录，请重新登录！", "");
		}
		if (StringUtil.isEmpty(isAll) || StringUtil.isEmpty(pageNum) || StringUtil.isEmpty(pageSize)
				|| !com.wing.socialcontact.util.StringUtil.isNumeric(pageNum)
				|| !com.wing.socialcontact.util.StringUtil.isNumeric(pageSize)) {
			return super.getAjaxResult(Constants.AJAX_CODE_ERROR_PARAM, "参数无效，请检查！", "");
		} else {
			try {
				List performs = imFriendService.findMyFriendListByUserId1(userId, Integer.parseInt(pageNum),
						Integer.parseInt(pageSize), isAll, nickname);
				return super.getSuccessAjaxResult("获取成功！", performs);
			} catch (Exception e) {
				return super.getAjaxResult(Constants.AJAX_CODE_ERROR_INFO, e.getMessage(), "");
			}
		}

	}

	/**
	 * 获取星标好友列表
	 * @return
	 */
	@RequestMapping("selMyFriendList2")
	public @ResponseBody Map selMyFriendList2(HttpServletRequest request, String pageNum, String pageSize,
			String nickname, String userId) throws IOException {

		if (StringUtils.isEmpty(userId)) {
			userId = super.checkLogin(request);
		}
		if (userId == null || "".equals(userId)) {
			return super.getAjaxResult(Constants.AJAX_CODE_ERROR_NOTLOGIN, "未登录，请重新登录！", "");
		}
		if (StringUtil.isEmpty(pageNum) || StringUtil.isEmpty(pageSize)
				|| !com.wing.socialcontact.util.StringUtil.isNumeric(pageNum)
				|| !com.wing.socialcontact.util.StringUtil.isNumeric(pageSize)) {
			return super.getAjaxResult(Constants.AJAX_CODE_ERROR_PARAM, "参数无效，请检查！", "");
		} else {
			try {
				List performs = imFriendService.findMyFriendListByUserId2(userId, Integer.parseInt(pageNum),
						Integer.parseInt(pageSize), nickname);
				return super.getSuccessAjaxResult("获取成功！", performs);
			} catch (Exception e) {
				return super.getAjaxResult(Constants.AJAX_CODE_ERROR_INFO, e.getMessage(), "");
			}
		}

	}

	/**
	 * 获取好友列表和群组列表
	 * @return
	 */
	@RequestMapping("selMyFriendAndGroupList")
	public @ResponseBody Map selMyFriendAndGroupList(HttpServletRequest request, String nickname) throws IOException {
		String userId = super.checkLogin(request);
		if (userId == null || "".equals(userId)) {
			return super.getAjaxResult(Constants.AJAX_CODE_ERROR_NOTLOGIN, "未登录，请重新登录！", "");
		}
		if (StringUtils.isEmpty(nickname)) {
			return super.getAjaxResult(Constants.AJAX_CODE_ERROR_INFO, "参数无效,请检查！", "");
		}
		try {
			List friendList = imFriendService.findMyFriendListByUserId(userId, 0, 0, "1", nickname);
			List groupInfoList = imGroupinfoService.findMyGroupInfoListByUserId(userId, 0, 0, nickname);
			Map resultData = new HashMap();
			resultData.put("friendList", friendList);
			resultData.put("groupInfoList", groupInfoList);
			return super.getSuccessAjaxResult("获取成功！", resultData);
		} catch (Exception e) {
			return super.getAjaxResult(Constants.AJAX_CODE_ERROR_INFO, e.getMessage(), "");
		}

	}

	/**
	 * 获取好友数量
	 * @return
	 */
	@RequestMapping("selMyFriendCount")
	public @ResponseBody Map selMyFriendCount(HttpServletRequest request, String userId) throws IOException {

		if (StringUtils.isEmpty(userId)) {
			userId = super.checkLogin(request);
		}
		if (userId == null || "".equals(userId)) {
			return super.getAjaxResult(Constants.AJAX_CODE_ERROR_NOTLOGIN, "未登录，请重新登录！", "");
		}
		try {
			int count = imFriendService.findMyFriendCountByUserId(userId);
			return super.getSuccessAjaxResult("获取好友数量成功！", count);
		} catch (Exception e) {
			return super.getAjaxResult(Constants.AJAX_CODE_ERROR_INFO, e.getMessage(), "");
		}

	}

	// 获取我的好友数

	/**
	 * 删除好友列表
	 * @return
	 */
	@RequestMapping("delMyFriendByFriendUserid")
	public @ResponseBody Map delMyFriendByFriendUserid(HttpServletRequest request, String friendUserId)
			throws IOException {

		String userId = super.checkLogin(request);
		if (userId == null || "".equals(userId)) {
			return super.getAjaxResult(Constants.AJAX_CODE_ERROR_NOTLOGIN, "未登录，请重新登录！", "");
		}

		if (StringUtil.isEmpty(friendUserId)) {
			return super.getAjaxResult(Constants.AJAX_CODE_ERROR_PARAM, "参数无效，请检查！", "");
		} else {
			try {
				String requestStr = imFriendService.deleteEachOtherFriendByUserAndFriend(userId, friendUserId);
				return super.getSuccessAjaxResult("删除成功！", requestStr);
			} catch (RuntimeException e) {
				return super.getAjaxResult(Constants.AJAX_CODE_ERROR_INFO, e.getMessage(), "");
			}

		}

	}

	/**
	 * 根据用户id获取置顶用户信息列表
	 * @return
	 */
	@RequestMapping("selTopFriendAndTopGroupListByUserId")
	public @ResponseBody Map selTopFriendAndTopGroupListByUserId(HttpServletRequest request, String pageNum,
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
				List topUserList = imToprelatService.findTopFriendListByUserId(userId, Integer.parseInt(pageNum),
						Integer.parseInt(pageSize));
				List topGgroupList = imToprelatService.findTopGroupListByUserId(userId, Integer.parseInt(pageNum),
						Integer.parseInt(pageSize));
				Map map = new HashMap();
				map.put("topUserList", topUserList);
				map.put("topGgroupList", topGgroupList);
				return super.getSuccessAjaxResult("获取成功！", map);
			} catch (Exception e) {
				return super.getAjaxResult(Constants.AJAX_CODE_ERROR_INFO, e.getMessage(), "");
			}
		}

	}

	/**
	 * 修改好友备注信息
	 * @param request
	 * @param imFriendId
	 * @param friendMemo
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("updateFriendUserfriendMemo")
	public @ResponseBody Map updateFriendUserfriendMemo(HttpServletRequest request, String imFriendId,
			String friendMemo) throws IOException {

		String userId = super.checkLogin(request);
		if (userId == null || "".equals(userId)) {
			return super.getAjaxResult(Constants.AJAX_CODE_ERROR_NOTLOGIN, "未登录，请重新登录！", "");
		}
		if (StringUtil.isEmpty(imFriendId) || StringUtil.isEmpty(friendMemo)) {
			return super.getAjaxResult(Constants.AJAX_CODE_ERROR_PARAM, "参数无效，请检查！", "");
		} else {
			boolean flag = imFriendService.isMyFriend(imFriendId, userId);
			if (flag) {
				ImFriend imFriend = new ImFriend();
				imFriend.setId(imFriendId);
				imFriend.setFriendMemo(friendMemo);
				String resultStr = imFriendService.updateFriendUserInfo(imFriend);
				return super.getSuccessAjaxResult("获取成功！", resultStr);
			} else {
				return super.getAjaxResult(Constants.AJAX_CODE_ERROR_INFO, "只能修改自己好友的备注信息", "");
			}

		}

	}

	/**
	 * 修改好友的消息免打扰
	 * @param request
	 * @param friendUserid 好友用户id
	 * @param distrubFlag // 0 可打扰  1免打扰
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("updateFriendUserDisturb")
	public @ResponseBody Map updateFriendUserDisturb(HttpServletRequest request, String friendUserId,
			String distrubFlag) throws IOException {

		String userId = super.checkLogin(request);
		if (userId == null || "".equals(userId)) {
			return super.getAjaxResult(Constants.AJAX_CODE_ERROR_NOTLOGIN, "未登录，请重新登录！", "");
		}
		if (StringUtil.isEmpty(friendUserId) || StringUtil.isEmpty(distrubFlag)
				|| (!"0".equals(distrubFlag) && !"1".equals(distrubFlag))) {
			return super.getAjaxResult(Constants.AJAX_CODE_ERROR_PARAM, "参数无效，请检查！", "");
		} else {
			ImFriend imFriendSel = imFriendService.findByUserAndFriend(userId, friendUserId);
			if (imFriendSel == null) {
				return super.getAjaxResult(Constants.AJAX_CODE_ERROR_INFO, "只能设置自己好友的免打扰信息", "");
			}
			boolean flag = imFriendService.isMyFriend(imFriendSel.getId(), userId);
			if (flag) {
				ImFriend imFriend = new ImFriend();
				imFriend.setId(imFriendSel.getId());
				imFriend.setMsgDisturb(Integer.parseInt(distrubFlag));
				String resultStr = imFriendService.updateFriendUserInfo(imFriend);
				return super.getSuccessAjaxResult("设置成功！", resultStr);
			} else {
				return super.getAjaxResult(Constants.AJAX_CODE_ERROR_INFO, "只能设置自己好友的免打扰信息", "");
			}

		}

	}

	/**
	 * 获取共同好友列表
	 * @return
	 */
	@RequestMapping("selCommonFriendList")
	public @ResponseBody Map selCommonFriendList(HttpServletRequest request, String aUserId, String pageNum,
			String pageSize) throws IOException {

		String userId = super.checkLogin(request);
		if (userId == null || "".equals(userId)) {
			return super.getAjaxResult(Constants.AJAX_CODE_ERROR_NOTLOGIN, "未登录，请重新登录！", "");
		}
		if (StringUtil.isEmpty(aUserId) || StringUtil.isEmpty(pageNum) || StringUtil.isEmpty(pageSize)
				|| !com.wing.socialcontact.util.StringUtil.isNumeric(pageNum)
				|| !com.wing.socialcontact.util.StringUtil.isNumeric(pageSize)) {
			return super.getAjaxResult(Constants.AJAX_CODE_ERROR_PARAM, "参数无效，请检查！", "");
		} else {
			try {
				List performs = imFriendService.findCommonFriendsByUserAndUser(userId, aUserId,
						Integer.parseInt(pageNum), Integer.parseInt(pageSize));
				return super.getSuccessAjaxResult("获取共同好友成功！", performs);
			} catch (Exception e) {
				return super.getAjaxResult(Constants.AJAX_CODE_ERROR_INFO, e.getMessage(), "");
			}
		}

	}

	/**
	 * 获取好友的个人信息
	 * @return
	 */
	@RequestMapping("selMyFriendUserInfo")
	public @ResponseBody Map selMyFriendUserInfo(HttpServletRequest request, String friendUserId) throws IOException {

		String userId = super.checkLogin(request);
		if (userId == null || "".equals(userId)) {
			return super.getAjaxResult(Constants.AJAX_CODE_ERROR_NOTLOGIN, "未登录，请重新登录！", "");
		}
		if (StringUtil.isEmpty(friendUserId)) {
			return super.getAjaxResult(Constants.AJAX_CODE_ERROR_PARAM, "参数无效，请检查！", "");
		} else {
			try {
				Map friendUserInfo = imFriendService.findMyFriendUserInfo(userId, friendUserId);
				return super.getSuccessAjaxResult("获取我的好友信息成功！", friendUserInfo);
			} catch (Exception e) {
				return super.getAjaxResult(Constants.AJAX_CODE_ERROR_INFO, e.getMessage(), "");
			}
		}

	}

	/**
	 * 设置通讯录置顶用户
	 * @param request
	 * @param friendUserId
	 * @param status
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("updateFriendUserIsTop")
	public @ResponseBody Map updateFriendUserIsTop(HttpServletRequest request, String friendUserId, String status)
			throws IOException {

		String userId = super.checkLogin(request);
		if (userId == null || "".equals(userId)) {
			return super.getAjaxResult(Constants.AJAX_CODE_ERROR_NOTLOGIN, "未登录，请重新登录！", "");
		}
		if (StringUtil.isEmpty(friendUserId) || StringUtil.isEmpty(status)
				|| (!"0".equals(status) && !"1".equals(status))) {
			return super.getAjaxResult(Constants.AJAX_CODE_ERROR_PARAM, "参数无效，请检查！", "");
		} else {
			try {
				String resultStr = imFriendService.updateFriendUserIsTop(userId, friendUserId, status);
				return super.getSuccessAjaxResult("设置置顶成功！", resultStr);
			} catch (Exception e) {
				return super.getAjaxResult(Constants.AJAX_CODE_ERROR_INFO, e.getMessage(), "");
			}

		}

	}

	/**
	 * 设置星标用户
	 * @param request
	 * @param friendUserId
	 * @param status
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("UpdateStarFlagByFriendUserid")
	public @ResponseBody Map UpdateStarFlagByFriendUserid(HttpServletRequest request, String friendUserId,
			String starFlag) throws IOException {

		String userId = super.checkLogin(request);
		if (userId == null || "".equals(userId)) {
			return super.getAjaxResult(Constants.AJAX_CODE_ERROR_NOTLOGIN, "未登录，请重新登录！", "");
		}
		if (StringUtil.isEmpty(friendUserId) || StringUtil.isEmpty(starFlag)
				|| (!"0".equals(starFlag) && !"1".equals(starFlag))) {
			return super.getAjaxResult(Constants.AJAX_CODE_ERROR_PARAM, "参数无效，请检查！", "");
		} else {
			try {
				String resultStr = imFriendService.UpdateStarFlagByFriendUserid(userId, friendUserId, starFlag);
				String str = "设置成功！";
				if ("0".equals(starFlag)) {
					str = "成功取消星标成员！";
				}
				if ("1".equals(starFlag)) {
					str = "成功设置星标成员！";
				}
				return super.getSuccessAjaxResult(str, resultStr);
			} catch (Exception e) {
				return super.getAjaxResult(Constants.AJAX_CODE_ERROR_INFO, e.getMessage(), "");
			}

		}

	}

	/**
	 * 初始化用户
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("initUser")
	public @ResponseBody Map initUser(HttpServletRequest request) throws IOException {

		String userId = super.checkLogin(request);
		if (userId == null || "".equals(userId)) {
			return super.getAjaxResult(Constants.AJAX_CODE_ERROR_NOTLOGIN, "未登录，请重新登录！", "");
		}
		for (int i = 1; i < 176; i++) {
			imFriendService.initUsers(i, 1000);
			/*
			 * try { Thread.sleep(10000); } catch (InterruptedException e) { // TODO
			 * Auto-generated catch block e.printStackTrace(); }
			 */
		}
		return super.getSuccessAjaxResult("初始化用户成功！", "");

	}

	/**
	 * 初始化好友关系
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("initFriend")
	public @ResponseBody Map initFriend(HttpServletRequest request) throws IOException {

		String userId = super.checkLogin(request);
		if (userId == null || "".equals(userId)) {
			return super.getAjaxResult(Constants.AJAX_CODE_ERROR_NOTLOGIN, "未登录，请重新登录！", "");
		}
		imFriendService.initFrined();
		return super.getSuccessAjaxResult("初始化好友关系成功！", "");
	}

	/**
	 *  更新企服云客服
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("initQfyEntryCustomer")
	public @ResponseBody Map initQfyEntryCustomer(HttpServletRequest request) throws IOException {

		String userId = super.checkLogin(request);
		if (userId == null || "".equals(userId)) {
			return super.getAjaxResult(Constants.AJAX_CODE_ERROR_NOTLOGIN, "未登录，请重新登录！", "");
		}
		imFriendService.initQfyEntryCustomer();
		return super.getSuccessAjaxResult("初始化企服云客服成功！", "");
	}

	/**
	 * 电商的服务器端调用接口，创建IM用户
	 * @param request
	 * @param sinstr 密文
	 * @param userId 用户id
	 * @param headPortrait 头像
	 * @param nickname     昵称
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("createIMUser")
	public @ResponseBody Map createIMUser(HttpServletRequest request, String sinstr, String userId, String headPortrait,
			String nickname) throws IOException {
		if (StringUtil.isEmpty(userId) || StringUtil.isEmpty(userId)) {
			return super.getAjaxResult(Constants.AJAX_CODE_ERROR_PARAM, "参数无效，请检查！", "");
		} else {
			if (MD5Util.MD5Validate2(Constants.PRIVATE_KEY, sinstr)) {
				// 通知IM
				IMUtil.sendUser(imPrefix + userId, UUID.randomUUID().toString() + userId, "", "");
				IMUtil.updateUserOne(imPrefix + userId, nickname, headPortrait);
				return super.getSuccessAjaxResult("创建IM用户成功！", "");
			} else {
				return super.getAjaxResult(Constants.AJAX_CODE_ERROR_PARAM, "秘钥不正确，请检查！", "");
			}

		}
	}

}
