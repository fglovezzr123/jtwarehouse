/**  
 * @Title: DataPermissionsAction.java
 * @date 2016-10-18 下午4:05:33
 * @Copyright: 2016 
 */
package com.wing.socialcontact.im.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import tk.mybatis.mapper.util.StringUtil;

import com.wing.socialcontact.common.action.BaseAction;
import com.wing.socialcontact.config.MsgConfig;
import com.wing.socialcontact.front.util.ApplicationPath;
import com.wing.socialcontact.service.im.api.IImFriendrequestService;
import com.wing.socialcontact.service.im.bean.ImFriendrequest;
import com.wing.socialcontact.service.wx.api.IMessageInfoService;
import com.wing.socialcontact.service.wx.api.ITjyUserService;
import com.wing.socialcontact.service.wx.api.IWxUserService;
import com.wing.socialcontact.service.wx.bean.MessageInfo;
import com.wing.socialcontact.service.wx.bean.TjyUser;
import com.wing.socialcontact.service.wx.bean.WxUser;
import com.wing.socialcontact.sys.bean.ListValues;
import com.wing.socialcontact.sys.service.IDistrictService;
import com.wing.socialcontact.sys.service.IListValuesService;
import com.wing.socialcontact.util.Constants;
import com.wing.socialcontact.util.ServletUtil;
import com.wing.socialcontact.util.UUIDGenerator;
import com.wing.socialcontact.util.WxMsmUtil;
import com.wing.socialcontact.util.im.IMUtil;

/**
 * 好友请求
 * 
 * @author xuxinyuan
 * @version 1.0
 *
 */
@Controller
@RequestMapping("/im/m")
public class ImFriendRequestAction extends BaseAction {

	@Autowired
	private IImFriendrequestService imFriendrequestService;

	@Autowired
	private IMessageInfoService messageInfoService;

	@Autowired
	private IWxUserService wxUserService;
	@Autowired
	private IDistrictService districtService;
	@Autowired
	private IListValuesService listValuesService;

	@Autowired
	private ITjyUserService tjyUserService;
	/**
	 * 获取新好友列表
	 * 
	 * @return
	 */
	@RequestMapping("selMyNewFriendList")
	public @ResponseBody Map selMyNewFriendList(HttpServletRequest request, String pageNum, String pageSize)
			throws IOException {

		String userId = super.checkLogin(request);
		if (userId == null || "".equals(userId)) {
			return super.getAjaxResult(Constants.AJAX_CODE_ERROR_NOTLOGIN, "未登录，请重新登录！", "");
		}
		if (StringUtil.isEmpty(userId) || StringUtil.isEmpty(pageNum) || StringUtil.isEmpty(pageSize)
				|| !com.wing.socialcontact.util.StringUtil.isNumeric(pageNum)
				|| !com.wing.socialcontact.util.StringUtil.isNumeric(pageSize)) {
			return super.getAjaxResult(Constants.AJAX_CODE_ERROR_PARAM, "参数无效，请检查！", "");
		} else {
			try {
				List performs = imFriendrequestService.findMyNewFriendListByUserId(userId, Integer.parseInt(pageNum),
						Integer.parseInt(pageSize));
				return super.getSuccessAjaxResult("获取成功！", performs);
			} catch (Exception e) {
				return super.getAjaxResult(Constants.AJAX_CODE_ERROR_INFO, e.getMessage(), "");
			}
		}

	}

	/**
	 * 获取新好友数量
	 * 
	 * @return
	 */
	@RequestMapping("selMyNewFriendCount")
	public @ResponseBody Map selMyNewFriendCount(HttpServletRequest request) throws IOException {

		String userId = super.checkLogin(request);
		if (userId == null || "".equals(userId)) {
			return super.getAjaxResult(Constants.AJAX_CODE_ERROR_NOTLOGIN, "未登录，请重新登录！", "");
		}
		if (StringUtil.isEmpty(userId)) {
			return super.getAjaxResult(Constants.AJAX_CODE_ERROR_PARAM, "参数无效，请检查！", "");
		} else {
			try {
				int count = imFriendrequestService.findMyNewfriendCountByUserId(userId);
				return super.getSuccessAjaxResult("获取成功！", count);
			} catch (Exception e) {
				return super.getAjaxResult(Constants.AJAX_CODE_ERROR_INFO, e.getMessage(), "");
			}
		}

	}

	/**
	 * 添加好友请求
	 * 
	 * @return
	 */
	@RequestMapping("addFriendRequest")
	public @ResponseBody Map addFriendRequest(HttpServletRequest request, String friendUserId, String askmessage)
			throws IOException {
		String userId = super.checkLogin(request);
		if (userId == null || "".equals(userId)) {
			return super.getAjaxResult(Constants.AJAX_CODE_ERROR_NOTLOGIN, "未登录，请重新登录！", "");
		}
		TjyUser tjyUser2=tjyUserService.selectByPrimaryKey(userId);
		if(!"1".equals(tjyUser2.getIsRealname()+"")){
			return super.getAjaxResult("600", "未认证", "");
		}
		if (StringUtil.isEmpty(friendUserId)) {
			return super.getAjaxResult(Constants.AJAX_CODE_ERROR_PARAM, "参数无效，请检查！", "");
		} else {
			try {
				String requestStr = imFriendrequestService.saveFriendRequest(userId, friendUserId, askmessage);

				Map tjyUser = imFriendrequestService.findUserByUserId(userId);
				if (tjyUser == null) {
					tjyUser = new HashMap();
				}
				// 给提交用户提醒
				WxUser wxUser = wxUserService.selectByPrimaryKey(friendUserId);
				MessageInfo messageInfo = new MessageInfo();
				messageInfo.setId(UUIDGenerator.getUUID());
				messageInfo.setDeleted(0);
				messageInfo.setType(2);// 微信
				messageInfo.setToUserId(wxUser.getWxUserId());
				messageInfo.setCreateTime(new Date());
				// 组装内容
				String content = "【" + tjyUser.get("com_name") + "/" + tjyUser.get("job_name") + "/"
						+ tjyUser.get("nickname") + "】" + "请求加您为好友！";
				String con = WxMsmUtil.getTextMessageContent(content);
				messageInfo.setContent(con);
				messageInfo.setTemplateId("RECON_USER");
				messageInfo.setStatus(0);// 未发送
				messageInfo.setWxMsgType(1);/// ** 微信消息类型（1：文本消息2：图文消息） */
				messageInfoService.addMessageInfo(messageInfo);

				return super.getSuccessAjaxResult("发送添加好友请求成功！", requestStr);
			} catch (RuntimeException e) {
				return super.getAjaxResult(Constants.AJAX_CODE_ERROR_INFO, e.getMessage(), "");
			}
		}
	}

	/**
	 * 处理好友请求
	 * 
	 * @param response
	 * @param friendRequestId
	 *            请求id
	 * @param status
	 *            处理结果 2、同意 3、拒绝
	 * @throws IOException
	 */
	@RequestMapping("updateFriendRequestStatus")
	public @ResponseBody Map updateFriendRequestStatus(HttpServletRequest request, String requestId, String status,
			String friendMemo) throws IOException {
		String userId = super.checkLogin(request);
		if (userId == null || "".equals(userId)) {
			return super.getAjaxResult(Constants.AJAX_CODE_ERROR_NOTLOGIN, "未登录，请重新登录！", "");
		}
		if (StringUtil.isEmpty(requestId) || StringUtil.isEmpty(status)
				|| !com.wing.socialcontact.util.StringUtil.isNumeric(status)
				|| (!status.equals("2") && !status.equals("3"))) {
			return super.getAjaxResult(Constants.AJAX_CODE_ERROR_PARAM, "参数无效，请检查！", "");
		} else {
			try {
				String requestStr = imFriendrequestService.updateFriendRequestStatus(requestId,
						Integer.parseInt(status), friendMemo);
				ImFriendrequest friendRequest = imFriendrequestService.loadById(requestId);
				Map tjyUser = imFriendrequestService.findUserByUserId(userId);
				if (tjyUser == null) {
					tjyUser = new HashMap();
				}
				// 给提交用户提醒
				WxUser wxUser = wxUserService.selectByPrimaryKey(friendRequest.getUserId());
				MessageInfo messageInfo = new MessageInfo();
				messageInfo.setId(UUIDGenerator.getUUID());
				messageInfo.setDeleted(0);
				messageInfo.setType(2);// 微信
				messageInfo.setToUserId(wxUser.getWxUserId());
				messageInfo.setCreateTime(new Date());
				// 组装内容
				String content = "【" + tjyUser.get("com_name") + "/" + tjyUser.get("job_name") + "/"
						+ tjyUser.get("nickname") + "】" + "已同意您的好友申请！";
				if (status.equals("3")) {
					// 组装内容
					content = "【" + tjyUser.get("com_name") + "/" + tjyUser.get("job_name") + "/"
							+ tjyUser.get("nickname") + "】" + "已拒绝您的好友申请！";
				}
				String con = WxMsmUtil.getTextMessageContent(content);
				messageInfo.setContent(con);
				messageInfo.setTemplateId("RECON_USER");
				messageInfo.setStatus(0);// 未发送
				messageInfo.setWxMsgType(1);///** 微信消息类型（1：文本消息2：图文消息） */
				messageInfoService.addMessageInfo(messageInfo);
				//同意好友请求 通知IM
				if (status.equals("2")) {
					IMUtil.addFriendOne(friendRequest.getUserId(), friendRequest.getFriendUser());
				}
				return super.getSuccessAjaxResult("添加成功！", requestStr);
			} catch (RuntimeException e) {
				return super.getAjaxResult(Constants.AJAX_CODE_ERROR_INFO, e.getMessage(), "");
			}
		}
	}

	/**
	 * 获取天就云用户信息
	 * 
	 * @param request
	 * @param tjyUserId
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("selTjyUserInfoByTyjUserid")
	public @ResponseBody Map selTjyUserInfoByTyjUserid(HttpServletRequest request, String tjyUserId)
			throws IOException {
		String userId = super.checkLogin(request);
		if (userId == null || "".equals(userId)) {
			return super.getAjaxResult(Constants.AJAX_CODE_ERROR_NOTLOGIN, "未登录，请重新登录！", "");
		}
		if (StringUtil.isEmpty(tjyUserId)) {
			return super.getAjaxResult(Constants.AJAX_CODE_ERROR_PARAM, "参数无效，请检查！", "");
		} else {
			try {
				Map map = imFriendrequestService.findUserByUserId(tjyUserId);
				return super.getSuccessAjaxResult("获取用户信息成功！", map);
			} catch (RuntimeException e) {
				return super.getAjaxResult(Constants.AJAX_CODE_ERROR_INFO, e.getMessage(), "");
			}
		}
	}

	/**
	 * 
	 * @param request
	 * @param pageNum
	 * @param pageSize
	 * @param trueName
	 * @param cityId
	 *            获取未添加好友列表
	 * @param industryId
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("selMyNoFriendList")
	public @ResponseBody Map selMyNoFriendList(HttpServletRequest request, String pageNum, String pageSize,
			String trueName, String cityId, String industryId) throws IOException {

		String userId = super.checkLogin(request);
		if (userId == null || "".equals(userId)) {
			return super.getAjaxResult(Constants.AJAX_CODE_ERROR_NOTLOGIN, "未登录，请重新登录！", "");
		}
		if (StringUtil.isEmpty(userId) || StringUtil.isEmpty(pageNum) || StringUtil.isEmpty(pageSize)
				|| !com.wing.socialcontact.util.StringUtil.isNumeric(pageNum)
				|| !com.wing.socialcontact.util.StringUtil.isNumeric(pageSize)) {
			return super.getAjaxResult(Constants.AJAX_CODE_ERROR_PARAM, "参数无效，请检查！", "");
		} else {
			try {
				List performs = imFriendrequestService.findNoFriendUserListByUserId(userId, Integer.parseInt(pageNum),
						Integer.parseInt(pageSize), trueName, cityId, industryId);
				return super.getSuccessAjaxResult("获取成功！", performs);
			} catch (Exception e) {
				return super.getAjaxResult(Constants.AJAX_CODE_ERROR_INFO, e.getMessage(), "");
			}
		}

	}

	/**
	 * 获取所有省
	 * 
	 * @return
	 */
	@RequestMapping("selAllProvince")
	public @ResponseBody Map selAllProvince(HttpServletRequest request) throws IOException {

		String userId = super.checkLogin(request);
		if (userId == null || "".equals(userId)) {
			return super.getAjaxResult(Constants.AJAX_CODE_ERROR_NOTLOGIN, "未登录，请重新登录！", "");
		}
		try {
			Map param = new HashMap();
			param.put("superId", "402881ea3f5b1d14013f5b1fdc080006");
			List performs = districtService.selectByParam(param);
			return super.getSuccessAjaxResult("获取成功！", performs);
		} catch (Exception e) {
			return super.getAjaxResult(Constants.AJAX_CODE_ERROR_INFO, e.getMessage(), "");
		}

	}

	/**
	 * 获取省下的所有市
	 * 
	 * @return
	 */
	@RequestMapping("selAllCityByProvinceId")
	public @ResponseBody Map selAllCityByProvinceId(HttpServletRequest request, String proviceId) throws IOException {

		String userId = super.checkLogin(request);
		if (userId == null || "".equals(userId)) {
			return super.getAjaxResult(Constants.AJAX_CODE_ERROR_NOTLOGIN, "未登录，请重新登录！", "");
		}
		try {
			Map param = new HashMap();
			param.put("superId", proviceId);
			List performs = districtService.selectByParam(param);
			return super.getSuccessAjaxResult("获取成功！", performs);
		} catch (Exception e) {
			return super.getAjaxResult(Constants.AJAX_CODE_ERROR_INFO, e.getMessage(), "");
		}

	}

	/**
	 * 获取所有行业
	 * 
	 * @return
	 */
	@RequestMapping("selAllIndustry")
	public @ResponseBody Map selAllIndustry(HttpServletRequest request) throws IOException {

		String userId = super.checkLogin(request);
		if (userId == null || "".equals(userId)) {
			return super.getAjaxResult(Constants.AJAX_CODE_ERROR_NOTLOGIN, "未登录，请重新登录！", "");
		}
		try {
			List<ListValues> industrys = listValuesService.selectListByType(8001);
			return super.getSuccessAjaxResult("获取成功！", industrys);
		} catch (Exception e) {
			return super.getAjaxResult(Constants.AJAX_CODE_ERROR_INFO, e.getMessage(), "");
		}

	}

	/**
	 * 获取所有标签
	 * 
	 * @return
	 */
	@RequestMapping("selAllFavs")
	public @ResponseBody Map selAllFavs(HttpServletRequest request) throws IOException {

		String userId = super.checkLogin(request);
		if (userId == null || "".equals(userId)) {
			return super.getAjaxResult(Constants.AJAX_CODE_ERROR_NOTLOGIN, "未登录，请重新登录！", "");
		}
		try {
			List<ListValues> industrys = listValuesService.selectListByType(8002);
			return super.getSuccessAjaxResult("获取成功！", industrys);
		} catch (Exception e) {
			return super.getAjaxResult(Constants.AJAX_CODE_ERROR_INFO, e.getMessage(), "");
		}

	}

}
