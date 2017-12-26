package com.wing.socialcontact.front.action;

import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wing.socialcontact.common.action.BaseAction;
import com.wing.socialcontact.common.model.IpInfo;
import com.wing.socialcontact.common.model.Member;
import com.wing.socialcontact.front.util.ApplicationPath;
import com.wing.socialcontact.front.util.ConstantDefinition;
import com.wing.socialcontact.service.wx.api.IMessageInfoService;
import com.wing.socialcontact.service.wx.api.ITjyUserService;
import com.wing.socialcontact.service.wx.api.IWxUserService;
import com.wing.socialcontact.service.wx.bean.MessageInfo;
import com.wing.socialcontact.service.wx.bean.TjyUser;
import com.wing.socialcontact.service.wx.bean.WxUser;
import com.wing.socialcontact.util.AldyMessageUtil;
import com.wing.socialcontact.util.Constants;
import com.wing.socialcontact.util.DateUtil;
import com.wing.socialcontact.util.IpUtil;
import com.wing.socialcontact.util.MsmValidateBean;
import com.wing.socialcontact.util.ServletUtil;
import com.wing.socialcontact.util.UUIDGenerator;
import com.wing.socialcontact.util.WxMsmUtil;
import com.wing.socialcontact.util.im.IMUtil;

/**
 * 对app接口控制器----已移动到 com.wing.enterprise.web.front
 * 
 * @ClassName: SysAction
 * @Description: TODO
 * @author: zengmin
 * @date:2017年4月2日 下午5:57:56
 */
@Controller
@RequestMapping("/m/app1")
public class AppAction extends BaseAction {

	@Autowired
	private IWxUserService wxUserService;

	@Autowired
	private IMessageInfoService messageInfoService;

	@Autowired
	private ITjyUserService tjyUserService;

	private String imPrefix = ApplicationPath.getParameter(ConstantDefinition.IM_PREFIX);

	/**
	 * 发送手机验证码
	 * 
	 * @Title: send_code
	 * @Description: TODO
	 * @param session
	 * @param response
	 * @param mobile
	 * @return
	 * @return: Map
	 * @author: zengmin
	 * @date: 2017年5月3日 下午2:44:34
	 */
	@RequestMapping("send_code")
	public @ResponseBody Map send_code(HttpSession session, HttpServletResponse response, String mobile) {
		try {
			if (!StringUtils.hasLength(mobile)) {
				return super.getAjaxResult("401", "参数错误", null);
			}
			String vcode = (int) ((Math.random() * 9 + 1) * 100000) + "";
			System.out.println("-----往消息表加数据app----" + vcode);
			String content ="验证码"+vcode+",您正在注册成为"+AldyMessageUtil.SMSPRE+"用户，感谢您的支持！";
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
				messageInfo.setTemplateId(AldyMessageUtil.MsmTemplateId.REG);
				messageInfoService.addMessageInfo(messageInfo);
				if (!StringUtils.hasLength(vcode))
				{
					return super.getAjaxResult("500", "系统错误", null);
				}
				MsmValidateBean msmValidateBean = new MsmValidateBean(mobile, new Date(), vcode);
				session.setAttribute("mvb", msmValidateBean);
				return super.getSuccessAjaxResult("手机验证码发送成功");
			}else{
				return super.getAjaxResult("500", "系统错误", null);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return super.getAjaxResult("500", "系统错误", null);
		}
	}

	/**
	 * app微信快捷登录
	 * 
	 * @Title: wx_login
	 * @Description: TODO
	 * @param session
	 * @param request
	 * @param response
	 * @param wxUniqueId
	 * @return
	 * @return: Map
	 * @author: zengmin
	 * @date: 2017年5月3日 下午2:56:15
	 */
	@RequestMapping("wx_login")
	public @ResponseBody Map wx_login(HttpSession session, HttpServletRequest request, HttpServletResponse response,
			String wxUniqueId) {
		try {
			if (!StringUtils.hasLength(wxUniqueId)) {
				return super.getAjaxResult("401", "参数错误", null);
			}
			TjyUser tjyUser = tjyUserService.selectByWxUniqueId(wxUniqueId);
			if (null == tjyUser) {
				return super.getAjaxResult("700", "登录失败，用户未绑定", null);
			}
			WxUser wxUser = wxUserService.selectByPrimaryKey(tjyUser.getId());
			String loginIp = IpUtil.getIpAddr(request);
			IpInfo ipInfo = IpUtil.getIpInfo(loginIp);
			Member me = new Member();// 需要放入当前session 的用户信息
			me.setId(wxUser.getId() + "");
			me.setIpInfo(ipInfo);
			me.setLoginTime(DateUtil.currentTimestamp());
			me.setWxUserId(wxUser.getWxUserId());
			me.setIsRealname(tjyUser.getIsRealname() + "");
			int loginCount = null == wxUser.getLogincount() ? 0 : wxUser.getLogincount();
			wxUser.setLogincount(loginCount + 1);
			wxUserService.updateWxUser(wxUser);
			// 首次登录发送短信提醒
			if (wxUser.getLogincount() == 1) {
				MessageInfo messageInfo = new MessageInfo();
				messageInfo.setId(UUIDGenerator.getUUID());
				messageInfo.setDeleted(0);
				messageInfo.setMobile(wxUser.getMobile());
				messageInfo.setType(3);// 系统
				messageInfo.setCreateTime(new Date());
				String name = tjyUser.getNickname();
				messageInfo.setToUserId(tjyUser.getId());
				messageInfo.setContent(AldyMessageUtil.userZcSuccess(name));
				messageInfo.setStatus(0);
				messageInfoService.addMessageInfo(messageInfo);
			}
			session.setAttribute("me", me);
			session.setAttribute(Constants.SESSION_WXUSER_ID, wxUser.getWxUserId());
			session.setAttribute(Constants.SESSION_WXUSER_NICKNAME, wxUser.getNickName());
			session.setAttribute(Constants.SESSION_WXUSER_HDPIC, wxUser.getImgUrl());
			return super.getAjaxResult("0", "登录成功", tjyUser);
		} catch (Exception e) {
			e.printStackTrace();
			return super.getAjaxResult("500", "系统错误", null);
		}
	}

	/**
	 * app手机号加验证码登录
	 * 
	 * @Title: mobile_login
	 * @Description: TODO
	 * @param session
	 * @param request
	 * @param response
	 * @param wxUniqueId
	 * @return
	 * @return: Map
	 * @author: zengmin
	 * @date: 2017年5月3日 下午2:56:15
	 */
	@RequestMapping("mobile_login")
	public @ResponseBody Map mobile_login(HttpSession session, HttpServletRequest request, HttpServletResponse response,
			String mobile, String dyz) {
		try {
			if (StringUtils.isEmpty(mobile) || StringUtils.isEmpty(dyz)) {
				return super.getAjaxResult("401", "参数错误", null);
			}
			boolean bo = MsmValidateBean.validateCode(mobile, dyz, session);
			if (!bo) {
				return super.getAjaxResult("999", "手机验证码错误或已失效", null);
			}
			// 清除手机验证码
			session.removeAttribute("mvb");
			WxUser wxUser = wxUserService.selectByMobile(mobile);
			if (null == wxUser) {
				wxUser = new WxUser();
				wxUser.setMobile(mobile);
				wxUser.setDeletestatus(false);
				wxUser.setAddtime(new Date());
				String nickN = mobile.replace(mobile.substring(3, 8), "**");
				wxUser.setNickName(nickN);
				// wxUser.setQqOpenid(reg_openId);
				wxUser.setUsername(mobile);
				wxUser.setUsertype((byte) 2);
				bo = wxUserService.addWxUser(wxUser);
			}
			TjyUser tjyUser = tjyUserService.selectByPrimaryKey(wxUser.getId() + "");
			String loginIp = IpUtil.getIpAddr(request);
			IpInfo ipInfo = IpUtil.getIpInfo(loginIp);
			Member me = new Member();// 需要放入当前session 的用户信息
			me.setId(wxUser.getId() + "");
			me.setIpInfo(ipInfo);
			me.setLoginTime(DateUtil.currentTimestamp());
			me.setWxUserId(wxUser.getWxUserId());
			me.setIsRealname(tjyUser.getIsRealname() + "");
			int loginCount = null == wxUser.getLogincount() ? 0 : wxUser.getLogincount();
			wxUser.setLogincount(loginCount + 1);
			wxUserService.updateWxUser(wxUser);
			// 首次登录发送短信提醒
			if (wxUser.getLogincount() == 1) {
				MessageInfo messageInfo = new MessageInfo();
				messageInfo.setId(UUIDGenerator.getUUID());
				messageInfo.setDeleted(0);
				messageInfo.setMobile(wxUser.getMobile());
				messageInfo.setType(3);// 系统
				messageInfo.setCreateTime(new Date());
				String name = tjyUser.getNickname();
				messageInfo.setToUserId(tjyUser.getId());
				messageInfo.setContent(AldyMessageUtil.userZcSuccess(name));
				messageInfo.setStatus(0);
				messageInfoService.addMessageInfo(messageInfo);
			}
			session.setAttribute("me", me);
			session.setAttribute(Constants.SESSION_WXUSER_ID, wxUser.getWxUserId());
			session.setAttribute(Constants.SESSION_WXUSER_NICKNAME, wxUser.getNickName());
			session.setAttribute(Constants.SESSION_WXUSER_HDPIC, wxUser.getImgUrl());
			return super.getAjaxResult("0", "登录成功", tjyUser);
		} catch (Exception e) {
			e.printStackTrace();
			return super.getAjaxResult("500", "系统错误", null);
		}
	}

	/**
	 * 用户手机号绑定
	 * 
	 * @Title: bind_user
	 * @Description: TODO
	 * @param session
	 * @param response
	 * @param mobile
	 * @param dyz
	 * @param wxUniqueId
	 * @return
	 * @return: Map
	 * @author: zengmin
	 * @date: 2017年5月3日 下午3:19:01
	 */
	@RequestMapping("bind_user")
	public @ResponseBody Map bind_user(HttpSession session, HttpServletRequest request, HttpServletResponse response,
			String mobile, String dyz, String wxUniqueId) {
		try {
			if (StringUtils.isEmpty(mobile) || StringUtils.isEmpty(dyz) || StringUtils.isEmpty(wxUniqueId)) {
				return super.getAjaxResult("401", "参数错误", null);
			}
			boolean bo = MsmValidateBean.validateCode(mobile, dyz, session);
			if (!bo) {
				return super.getAjaxResult("999", "手机验证码错误或已失效", null);
			}
			// 清除手机验证码
			session.removeAttribute("mvb");
			TjyUser tjyUser = tjyUserService.selectByWxUniqueId(wxUniqueId);
			if (null != tjyUser) {
				return super.getAjaxResult("999", "绑定用户失败，用户已绑定", null);
			}
			WxUser wxUser = wxUserService.selectByMobile(mobile);
			// 用户不存在，需要创建用户
			if (null == wxUser) {
				wxUser = new WxUser();
				wxUser.setMobile(mobile);
				wxUser.setDeletestatus(false);
				wxUser.setAddtime(new Date());
				String nickN = mobile.replace(mobile.substring(3, 8), "**");
				wxUser.setNickName(nickN);
				// wxUser.setQqOpenid(reg_openId);
				wxUser.setUsername(mobile);
				wxUser.setUsertype((byte) 2);
				tjyUser = new TjyUser();
				tjyUser.setWxUniqueId(wxUniqueId);
				tjyUser.setBindPhone(mobile);
				tjyUser.setFirstBindTime(new Date());
				tjyUser.setLastBindTime(new Date());
				wxUser.setTjyUser(tjyUser);
				bo = wxUserService.addWxUser(wxUser);
			} else {// 用户已存在，直接实现绑定
				tjyUser = tjyUserService.selectByPrimaryKey(wxUser.getId() + "");
				if (null == tjyUser) {
					tjyUser = new TjyUser();
					tjyUser.setId(wxUser.getId() + "");
					tjyUser.setMallUser(wxUser.getId());
					tjyUser.setNickname(wxUser.getNickName());
					tjyUser.setIsRealname(0);
					tjyUser.setStatus(1);
					tjyUser.setIsdk(0);
					tjyUser.setMobile(wxUser.getMobile());
					tjyUser.setHomepagePic(wxUser.getImgUrl());
					tjyUser.setOpenId(wxUser.getQqOpenid());
					tjyUser.setWxUniqueId(wxUniqueId);
					tjyUser.setBindPhone(mobile);
					tjyUser.setFirstBindTime(new Date());
					tjyUser.setLastBindTime(new Date());
					tjyUserService.addTjyUser(tjyUser);
				} else {
					tjyUser.setWxUniqueId(wxUniqueId);
					tjyUser.setBindPhone(mobile);
					tjyUser.setFirstBindTime(new Date());
					tjyUser.setLastBindTime(new Date());
					tjyUserService.updateTjyUser(tjyUser);
				}
			}
			String loginIp = IpUtil.getIpAddr(request);
			IpInfo ipInfo = IpUtil.getIpInfo(loginIp);
			Member me = new Member();// 需要放入当前session 的用户信息
			me.setId(wxUser.getId() + "");
			me.setIpInfo(ipInfo);
			me.setLoginTime(DateUtil.currentTimestamp());
			me.setWxUserId(wxUser.getWxUserId());
			me.setIsRealname(tjyUser.getIsRealname() + "");
			int loginCount = null == wxUser.getLogincount() ? 0 : wxUser.getLogincount();
			wxUser.setLogincount(loginCount + 1);
			wxUserService.updateWxUser(wxUser);
			// 首次登录发送短信提醒
			if (wxUser.getLogincount() == 1) {
				MessageInfo messageInfo = new MessageInfo();
				messageInfo.setId(UUIDGenerator.getUUID());
				messageInfo.setDeleted(0);
				messageInfo.setMobile(wxUser.getMobile());
				messageInfo.setType(3);// 系统
				messageInfo.setCreateTime(new Date());
				String name = tjyUser.getNickname();
				messageInfo.setToUserId(tjyUser.getId());
				messageInfo.setContent(AldyMessageUtil.userZcSuccess(name));
				messageInfo.setStatus(0);
				messageInfoService.addMessageInfo(messageInfo);
			}
			session.setAttribute("me", me);
			session.setAttribute(Constants.SESSION_WXUSER_ID, wxUser.getWxUserId());
			session.setAttribute(Constants.SESSION_WXUSER_NICKNAME, wxUser.getNickName());
			session.setAttribute(Constants.SESSION_WXUSER_HDPIC, wxUser.getImgUrl());
			return super.getAjaxResult("0", "用户绑定成功", tjyUser);
		} catch (Exception e) {
			e.printStackTrace();
			return super.getAjaxResult("500", "系统错误", null);
		}
	}

	/**
	 * app手机号加验证码登录
	 * 
	 * @Title: mobile_login
	 * @Description: TODO
	 * @param session
	 * @param request
	 * @param response
	 * @param wxUniqueId
	 * @return
	 * @return: Map
	 * @author: zengmin
	 * @date: 2017年5月3日 下午2:56:15
	 */
	@RequestMapping("get_imtoken")
	public @ResponseBody Map getImToken(HttpServletRequest request, String uid) {
		Member me = ServletUtil.getMember(request);
		if (null != me) {
			uid = me.getId();
		}
		if (StringUtils.isEmpty(uid)) {
			return super.getAjaxResult("401", "参数错误", null);
		}
		return super.getAjaxResult("0", "获取成功", IMUtil.getUserImToken(imPrefix + uid));
	}
}
