package com.wing.socialcontact.front.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import tk.mybatis.mapper.util.StringUtil;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.tojoycloud.common.report.ResponseCode;
import com.tojoycloud.common.report.base.BaseAppAction;
import com.tojoycloud.report.RequestReport;
import com.tojoycloud.report.ResponseReport;
import com.wing.socialcontact.service.im.api.IImFollowService;
import com.wing.socialcontact.service.im.api.IImFriendService;
import com.wing.socialcontact.service.im.api.IImFriendrequestService;
import com.wing.socialcontact.service.im.api.IImGroupfavService;
import com.wing.socialcontact.service.im.api.IImGroupinfoService;
import com.wing.socialcontact.service.im.api.IImGroupusersService;
import com.wing.socialcontact.service.im.bean.ImGroupinfo;
import com.wing.socialcontact.service.wx.api.IMessageInfoService;
import com.wing.socialcontact.service.wx.api.ITjyUserService;
import com.wing.socialcontact.service.wx.api.IWxUserService;
import com.wing.socialcontact.service.wx.bean.MessageInfo;
import com.wing.socialcontact.service.wx.bean.TjyUser;
import com.wing.socialcontact.service.wx.bean.WxUser;
import com.wing.socialcontact.util.AldyMessageUtil;
import com.wing.socialcontact.util.DateUtils;
import com.wing.socialcontact.util.UUIDGenerator;
/**
 * APP商友页面接口controller
 *
 */
@Controller
@RequestMapping("/m/app/friend")
public class AppBusinessFriendAction extends BaseAppAction {
	
	@Autowired
	private IImFriendrequestService imFriendrequestService;
	@Autowired
	private ITjyUserService tjyUserService;
	@Autowired
	private IImFriendService imFriendService; 
	@Autowired
	private IWxUserService wxUserService;
	@Autowired
	private IImFollowService imFollowService; 
	@Autowired
	private IImGroupinfoService imGroupinfoService;
	@Autowired
	private IImGroupusersService imGroupusersService;

	@Autowired
	private IImGroupfavService imGroupfavService;
	@Autowired
	private IMessageInfoService messageInfoService;
	/**
	 * 商友搜索列表
	 * 
	 */
	@RequestMapping("searchList")
	public @ResponseBody ResponseReport searchList(@RequestBody RequestReport rr,HttpSession session, HttpServletResponse response){
		try{
			String userId =rr.getUserProperty().getUserId();
			String pageSize = rr.getDataValue("pageSize");
			String pageNum = rr.getDataValue("pageNum");
			String trueName = rr.getDataValue("trueName");
			String cityId = rr.getDataValue("cityId");
			String industryId = rr.getDataValue("industryId");
			if (StringUtil.isEmpty(userId) || StringUtil.isEmpty(pageNum) || StringUtil.isEmpty(pageSize)
					|| !com.wing.socialcontact.util.StringUtil.isNumeric(pageNum)
					|| !com.wing.socialcontact.util.StringUtil.isNumeric(pageSize)) {
				return super.getAjaxResult(rr,ResponseCode.NotSupport, "参数有误", null);
			} 
			List res = new ArrayList();
			res= imFriendrequestService.findNoFriendUserListByUserId(userId, Integer.parseInt(pageNum),
					Integer.parseInt(pageSize), trueName, cityId, industryId);
			return super.getAjaxResult(rr,ResponseCode.OK, "获取成功！", res);
		}catch(Exception e){
			e.printStackTrace();
			return super.getAjaxResult(rr,ResponseCode.Error, "获取失败！", null);
		}
	}
	/**
	 * 根据用户id个人信息
	 */
	@RequestMapping("userInformation")
	public @ResponseBody ResponseReport userInformation(@RequestBody RequestReport rr,HttpSession session, HttpServletResponse response){
		try{
			String userId =rr.getUserProperty().getUserId();
			String tjUserId = rr.getDataValue("tjUserId");
			if (userId == null || "".equals(userId)) {
				return super.getAjaxResult(rr,ResponseCode.NotSupport, "未登录", null);
			}
			if (StringUtil.isEmpty(tjUserId)) {
				return super.getAjaxResult(rr,ResponseCode.NotSupport, "参数有误", null);
			} 
			Map res= imFriendrequestService.findUserByUserId(tjUserId);
			//查询是否为心标好友
			int starFlag = imFriendService.getStarFlag(userId,tjUserId);
			res.put("starFlag", starFlag);
			return super.getAjaxResult(rr,ResponseCode.OK, "获取成功！", res);
		}catch(Exception e){
			e.printStackTrace();
			return super.getAjaxResult(rr,ResponseCode.Error, "获取失败！", null);
		}
	}
	/**
	 * 同步通讯录
	 */
	@RequestMapping("syncBookList")
	public @ResponseBody ResponseReport syncList(@RequestBody RequestReport rr,HttpSession session, HttpServletResponse response){
		try{
			String userId =rr.getUserProperty().getUserId();
			String bookList = rr.getDataValue("bookList");
			if (StringUtil.isEmpty(userId) || StringUtil.isEmpty(bookList)) {
				return super.getAjaxResult(rr,ResponseCode.NotSupport, "参数有误", null);
			} 
			List res = new ArrayList();
			JSONObject job =JSON.parseObject(bookList);
			Object jsonobj = job.get("contacts");
			com.alibaba.fastjson.JSONArray ja =JSON.parseArray(jsonobj.toString()) ;
			for(int i=0;i<ja.size();i++){
				Map map = new HashMap();
				JSONObject jo = ja.getJSONObject(i);
				String name = jo.getString("name");
				String mobile = jo.getString("mobile");
				map.put("name", name);
				map.put("mobile", mobile);
				WxUser wxuser = wxUserService.selectByMobile(mobile);
				if(wxuser==null||wxuser.getId()==null){
//					if(wxuser==null||wxuser.getId()==null||"".equals(wxuser.getId())){
					map.put("userFlag", 0);
					map.put("isMyFriend", 0);
				}else{
					map.put("userFlag", 1);
					map.put("id", wxuser.getId());
					map.put("nickName", wxuser.getNickName());
					map.put("trueName", wxuser.getTruename());
					map.put("headImgUrl", wxuser.getImgUrl());
					//判断是否为好友
					boolean isMyFriend = imFriendService.isMyFriend(wxuser.getId().toString(), userId);
					if(isMyFriend){
						map.put("isMyFriend", 1);
					}else{
						map.put("isMyFriend", 0);
					}
				}
				res.add(map);
			}
			return super.getAjaxResult(rr,ResponseCode.OK, "获取成功！", res);
		}catch(Exception e){
			e.printStackTrace();
			return super.getAjaxResult(rr,ResponseCode.Error, "获取失败！", null);
		}
	}
	/** 设置星标用户
	 * @param request
	 * @param friendUserId
	 * @param starFlag
	 * @param userId
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("updateStarFlag")
	public  @ResponseBody ResponseReport updateStarFlag(@RequestBody RequestReport rr,HttpSession session, HttpServletResponse response) throws IOException{
		String userId =rr.getUserProperty().getUserId();
		String friendUserId = rr.getDataValue("friendUserId");
		String starFlag = rr.getDataValue("starFlag");
		if (userId == null || "".equals(userId)) {
			return super.getAjaxResult(rr,ResponseCode.NotSupport, "未登录", null);
		}
		if (StringUtil.isEmpty(friendUserId)) {
			return super.getAjaxResult(rr,ResponseCode.NotSupport, "参数friendUserId有误", null);
		} 
		if (StringUtil.isEmpty(starFlag)) {
			return super.getAjaxResult(rr,ResponseCode.NotSupport, "参数starFlag有误", null);
		}else{
			try{
				String resultStr = imFriendService.UpdateStarFlagByFriendUserid(userId, friendUserId, starFlag);
				String str = "设置成功！";
				if("0".equals(starFlag)){
					str = "成功取消星标成员！";
				}
				if("1".equals(starFlag)){
					str = "成功设置星标成员！";
				}
				return super.getAjaxResult(rr,ResponseCode.OK, str, null);
			}catch(Exception e){
				return super.getAjaxResult(rr,ResponseCode.Error, "设置失败！", null);
			}
			
		}
	}
	/**
	 * 我的粉丝/关注数量
	 */
	@RequestMapping("fansAndAttentionCount")
	public  @ResponseBody ResponseReport fansAndAttentionCount(@RequestBody RequestReport rr,HttpSession session, HttpServletResponse response) throws IOException{
		String userId =rr.getUserProperty().getUserId();
		if (userId == null || "".equals(userId)) {
			return super.getAjaxResult(rr,ResponseCode.NotSupport, "未登录", null);
		}
			try{
				Map map = new HashMap();
				//关注数
				int attentionCount = imFollowService.findMyFollowUsersCount(userId);
				map.put("attentionCount", attentionCount);
				//粉丝
				int fansCount = imFollowService.findMyFansUsersCount(userId);
				map.put("fansCount", fansCount);
				return super.getAjaxResult(rr,ResponseCode.OK, "获取成功", map);
			}catch(Exception e){
				return super.getAjaxResult(rr,ResponseCode.Error, "获取失败！", null);
			}
	}
	/**
	 * 我的粉丝/关注列表
	 */
	@RequestMapping("fansList")
	public  @ResponseBody ResponseReport fansList(@RequestBody RequestReport rr,HttpSession session, HttpServletResponse response) throws IOException{
		String userId =rr.getUserProperty().getUserId();
		String pageSize = rr.getDataValue("pageSize");
		String pageNum = rr.getDataValue("pageNum");
		if (userId == null || "".equals(userId)) {
			return super.getAjaxResult(rr,ResponseCode.NotSupport, "未登录", null);
		}
		if (pageSize == null || "".equals(pageSize)) {
			return super.getAjaxResult(rr,ResponseCode.NotSupport, "参数错误", null);
		}
		if (pageNum == null || "".equals(pageNum)) {
			return super.getAjaxResult(rr,ResponseCode.NotSupport, "参数错误", null);
		}
			try{
				List res = imFollowService.findMyFansUsers(userId, Integer.parseInt(pageNum), Integer.parseInt(pageSize));
				return super.getAjaxResult(rr,ResponseCode.OK, "获取成功", res);
			}catch(Exception e){
				return super.getAjaxResult(rr,ResponseCode.Error, "获取失败！", null);
			}
	}
	@RequestMapping("attentionList")
	public  @ResponseBody ResponseReport attentionList(@RequestBody RequestReport rr,HttpSession session, HttpServletResponse response) throws IOException{
		String userId =rr.getUserProperty().getUserId();
		String pageSize = rr.getDataValue("pageSize");
		String pageNum = rr.getDataValue("pageNum");
		if (userId == null || "".equals(userId)) {
			return super.getAjaxResult(rr,ResponseCode.NotSupport, "未登录", null);
		}
		if (pageSize == null || "".equals(pageSize)) {
			return super.getAjaxResult(rr,ResponseCode.NotSupport, "参数错误", null);
		}
		if (pageNum == null || "".equals(pageNum)) {
			return super.getAjaxResult(rr,ResponseCode.NotSupport, "参数错误", null);
		}
		try{
			List res = imFollowService.findMyFollowUsers(userId, Integer.parseInt(pageNum), Integer.parseInt(pageSize));
			return super.getAjaxResult(rr,ResponseCode.OK, "获取成功", res);
		}catch(Exception e){
			return super.getAjaxResult(rr,ResponseCode.Error, "获取失败！", null);
		}
	}
	/**
	 * 关注/取消关注    imFollowService.insertFollowUsers(userId, followUser);
	 */
	@RequestMapping("addAttention")
	public  @ResponseBody ResponseReport addAttention(@RequestBody RequestReport rr,HttpSession session, HttpServletResponse response) throws IOException{
		String userId =rr.getUserProperty().getUserId();
		String followUser = rr.getDataValue("uid");
		if (userId == null || "".equals(userId)) {
			return super.getAjaxResult(rr,ResponseCode.NotSupport, "未登录", null);
		}
		if (followUser == null || "".equals(followUser)) {
			return super.getAjaxResult(rr,ResponseCode.NotSupport, "参数错误", null);
		}
		try{
			imFollowService.insertFollowUsers(userId, followUser);
			return super.getAjaxResult(rr,ResponseCode.OK, "成功", null);
		}catch(Exception e){
			return super.getAjaxResult(rr,ResponseCode.Error, "失败！", null);
		}
	}
	@RequestMapping("deleteAttention")
	public  @ResponseBody ResponseReport deleteAttention(@RequestBody RequestReport rr,HttpSession session, HttpServletResponse response) throws IOException{
		String userId =rr.getUserProperty().getUserId();
		String followUser = rr.getDataValue("uid");
		if (userId == null || "".equals(userId)) {
			return super.getAjaxResult(rr,ResponseCode.NotSupport, "未登录", null);
		}
		if (followUser == null || "".equals(followUser)) {
			return super.getAjaxResult(rr,ResponseCode.NotSupport, "参数错误", null);
		}
		try{
			imFollowService.deleteFollowUsers(userId, followUser);
			return super.getAjaxResult(rr,ResponseCode.OK, "成功", null);
		}catch(Exception e){
			return super.getAjaxResult(rr,ResponseCode.Error, "失败！", null);
		}
	}
	/**
	 * 群搜索
	 */
	@RequestMapping("groupSearch")
	public  @ResponseBody ResponseReport groupSearch(@RequestBody RequestReport rr,HttpSession session, HttpServletResponse response) throws IOException{
		String userId =rr.getUserProperty().getUserId();
		String pageSize = rr.getDataValue("pageSize");
		String pageNum = rr.getDataValue("pageNum");
		String groupName = rr.getDataValue("searchKey");
		if (userId == null || "".equals(userId)) {
			return super.getAjaxResult(rr,ResponseCode.NotSupport, "未登录", null);
		}
		if (pageSize == null || "".equals(pageSize)) {
			return super.getAjaxResult(rr,ResponseCode.NotSupport, "参数错误", null);
		}
		if (pageNum == null || "".equals(pageNum)) {
			return super.getAjaxResult(rr,ResponseCode.NotSupport, "参数错误", null);
		}
			try{
				List res = imGroupinfoService.selGourpsListByName(userId, groupName, Integer.parseInt(pageNum),
						Integer.parseInt(pageSize));
				return super.getAjaxResult(rr,ResponseCode.OK, "获取成功", res);
			}catch(Exception e){
				return super.getAjaxResult(rr,ResponseCode.Error, "获取失败！", null);
			}
	}
	/**
	 * 群详情
	 */
	@RequestMapping("groupInfo")
	public  @ResponseBody ResponseReport groupInfo(@RequestBody RequestReport rr,HttpSession session, HttpServletResponse response) throws IOException{
		String userId =rr.getUserProperty().getUserId();
		String groupId = rr.getDataValue("groupId");
		String searchType = rr.getDataValue("type");
		if (userId == null || "".equals(userId)) {
			return super.getAjaxResult(rr,ResponseCode.NotSupport, "未登录", null);
		}
		if (groupId == null || "".equals(groupId)) {
			return super.getAjaxResult(rr,ResponseCode.NotSupport, "参数错误", null);
		}
		if (searchType == null || "".equals(searchType)) {
			return super.getAjaxResult(rr,ResponseCode.NotSupport, "参数searchType错误", null);
		}
		
		try{
			ImGroupinfo imGroupinfo = null;
			if("2".equals(searchType)){
				//根据网易群id查询群组信息
				imGroupinfo = imGroupinfoService.findByTid(groupId);
				if(imGroupinfo==null){
					return super.getAjaxResult(rr,ResponseCode.NotSupport, "不存在此群", null);
				}
				groupId = imGroupinfo.getId();
			}
			Map groupInfo = new HashMap();
			ImGroupinfo group = imGroupinfoService.findById(groupId);
			List groupUsers = imGroupusersService.findUserListByGroupIdHasOwner(groupId);
			List groupFavs = imGroupfavService.findFavListByGroupId(groupId);
			groupInfo.put("groupInfo", group);
			groupInfo.put("users", groupUsers);
			groupInfo.put("favs", groupFavs);
			return super.getAjaxResult(rr,ResponseCode.OK, "获取成功", groupInfo);
		}catch(Exception e){
			return super.getAjaxResult(rr,ResponseCode.Error, "获取失败！", null);
		}
	}
	/**
	 * 获取星标好友列表
	 */
	@RequestMapping("starFriend")
	public  @ResponseBody ResponseReport starFriend(@RequestBody RequestReport rr,HttpSession session, HttpServletResponse response) throws IOException{
		String userId =rr.getUserProperty().getUserId();
		if (userId == null || "".equals(userId)) {
			return super.getAjaxResult(rr,ResponseCode.NotSupport, "未登录", null);
		}
			try{
				//获取星标好友列表
				List res =imFriendService.selectStarFriendList(userId);
				return super.getAjaxResult(rr,ResponseCode.OK, "获取成功", res);
			}catch(Exception e){
				return super.getAjaxResult(rr,ResponseCode.Error, "获取失败！", null);
			}
	}
	
	/**
	 * 邀请通讯录好友注册
	 */
	@RequestMapping("investFriend")
	public  @ResponseBody ResponseReport investFriend(@RequestBody RequestReport rr,HttpSession session, HttpServletResponse response) throws IOException{
		String userId =rr.getUserProperty().getUserId();
		String mobile = rr.getDataValue("mobile");
		String name = rr.getDataValue("name");
		if (userId == null || "".equals(userId)) {
			return super.getAjaxResult(rr,ResponseCode.NotSupport, "未登录", null);
		}
			try{
				TjyUser user = tjyUserService.selectByPrimaryKey(userId);
				//发送邀请短信
				// 发送短信
				String content = "注册邀请";
				if(AldyMessageUtil.directSend(content,mobile)){
				MessageInfo messageInfo = new MessageInfo();
				messageInfo.setId(UUIDGenerator.getUUID());
				messageInfo.setDeleted(0);
				messageInfo.setMobile(mobile);
				messageInfo.setType(1);// 短信
				messageInfo.setCreateTime(new Date());
				messageInfo.setSendTime(new Date());
				messageInfo.setContent(content);
				messageInfo.setStatus(1);// 已发送
				messageInfo.setTemplateId("invest_friend");
				messageInfoService.addMessageInfo(messageInfo);
					return super.getAjaxResult(rr,ResponseCode.OK, "发送邀请成功", null);
				};
				return super.getAjaxResult(rr,ResponseCode.Error, "发送邀请失败！", null);
			}catch(Exception e){
				return super.getAjaxResult(rr,ResponseCode.Error, "发送邀请失败！", null);
			}
	}
}
