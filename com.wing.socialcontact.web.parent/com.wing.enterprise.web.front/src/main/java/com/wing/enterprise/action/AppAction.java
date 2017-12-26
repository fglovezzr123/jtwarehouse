package com.wing.enterprise.action;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
import com.wing.socialcontact.service.wx.api.IMessageInfoService;
import com.wing.socialcontact.service.wx.api.ITjyUserService;
import com.wing.socialcontact.service.wx.api.IWxUserService;
import com.wing.socialcontact.service.wx.bean.MessageInfo;
import com.wing.socialcontact.service.wx.bean.TjyUser;
import com.wing.socialcontact.service.wx.bean.WxUser;
import com.wing.socialcontact.sys.service.IListValuesService;
import com.wing.socialcontact.util.AldyMessageUtil;
import com.wing.socialcontact.util.CommUtil;
import com.wing.socialcontact.util.Constants;
import com.wing.socialcontact.util.DateUtil;
import com.wing.socialcontact.util.IpUtil;
import com.wing.socialcontact.util.MD5Util;
import com.wing.socialcontact.util.MsmValidateBean;
import com.wing.socialcontact.util.StringUtil;
import com.wing.socialcontact.util.UUIDGenerator;
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
@RequestMapping("/m/app")
public class AppAction extends BaseAction {

	@Autowired
	private IWxUserService wxUserService;

	@Autowired
	private IMessageInfoService messageInfoService;

	@Autowired
	private ITjyUserService tjyUserService;

	@Autowired
	private IListValuesService listValuesService;

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
			wxUser.setIsAppLogin(1);// 是否app登录
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
				messageInfo.setContent("【" + AldyMessageUtil.SMSPRE_APP + "】尊敬的" + name + "，欢迎登录企服联盟。");
				messageInfo.setStatus(0);
				messageInfoService.addMessageInfo(messageInfo);
			}
			session.setAttribute("me", me);
			session.setAttribute(Constants.SESSION_WXUSER_ID, wxUser.getWxUserId());
			session.setAttribute(Constants.SESSION_WXUSER_NICKNAME, wxUser.getNickName());
			session.setAttribute(Constants.SESSION_WXUSER_HDPIC, wxUser.getImgUrl());
			System.out.println("JSESSIONID=" + session.getId());
			response.addHeader("Set-Cookie", "JSESSIONID=" + session.getId());
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
			boolean bo = true;
			
			bo = MsmValidateBean.validateCode(mobile, dyz, session);
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
				

				wxUser = wxUserService.selectByMobile(mobile);
				TjyUser tjyUser = tjyUserService.selectByPrimaryKey(wxUser.getId() + "");
				Map usermap = tjyUserService.remotingGetUser(tjyUser.getMobile());
				if(usermap!=null){
					try {
						//调用远程数据库，获取用户信息
						String customerName = (String)usermap.get("customerName");
						if(!StringUtil.isEmpty(customerName)){
							tjyUser.setNickname(customerName);
							tjyUser.setTrueName(customerName);
							if(!customerName.equals(wxUser.getUsername())){
								wxUser.setUsername(customerName);
								wxUser.setTruename(customerName);
								wxUser.setNickName(customerName);
								wxUserService.updateWxUser(wxUser);
							}
						}
						String sex = (String)usermap.get("sexId");
						if(!StringUtil.isEmpty(sex)){
							int sexstr = 1;
							if("女".equals(sex)){
								sexstr = 2;
							}
							if(wxUser.getSex()!=null && sexstr!=wxUser.getSex()){
								wxUser.setSex(sexstr);
								wxUserService.updateWxUser(wxUser);
							}
						}
						String customerCompany = (String)usermap.get("customerCompany");
						if(!StringUtil.isEmpty(customerCompany)){
							tjyUser.setComName(customerCompany);
						}
						String customerTitle = (String)usermap.get("customerTitle");
						if(!StringUtil.isEmpty(customerTitle)){
							List<Map<String, Object>> listValues = listValuesService.selectListByType(12,customerTitle);
							if (null != listValues && listValues.size()>0) {
								Map lv = (Map) listValues.get(0);
								tjyUser.setJob((String)lv.get("id"));
							}
						}
						String customerUnitIndustry = (String)usermap.get("customerUnitIndustry");
						if(!StringUtil.isEmpty(customerUnitIndustry)){
							List<Map<String, Object>> listValues = listValuesService.selectListByType(8001,customerUnitIndustry);

							if (null != listValues && listValues.size()>0) {
								Map lv = (Map) listValues.get(0);
								tjyUser.setIndustry((String)lv.get("id"));
							}
						}
						String customerType = (String)usermap.get("customerType");
						if(!StringUtil.isEmpty(customerType)){
							if("普通客户".equals(customerType)){
								tjyUser.setReconStatus(1);
							}else if("成交客户".equals(customerType)||"认证客户".equals(customerType)){
								tjyUser.setReconStatus(2);
								Date reconDate = new Date();
								tjyUser.setReconDate(reconDate);
								tjyUser.setIsRealname(1);
//							Calendar now = Calendar.getInstance();
//							now.add(Calendar.YEAR, 1);
//							tjyUser.setLastRegDate(now.getTime());
							}else if("问题客户".equals(customerType)){
								tjyUser.setReconStatus(3);
							}
						}

						String customerCheckCapital = (String) usermap.get("customerCheckCapital");
						if (!StringUtil.isEmpty(customerCheckCapital)) {
							//注册资本
//							dd
							Pattern p = Pattern.compile("[0-9\\.]+");
							Matcher m = p.matcher(customerCheckCapital);
							if(m.find()){
								customerCheckCapital =  m.group();
								if(!StringUtil.isEmpty(customerCheckCapital)){
									tjyUser.setReconCapital(CommUtil.null2Double(customerCheckCapital));
								}
							}
						}

						String customerActivationDate = (String)usermap.get("customerActivationDate");
						if(!StringUtil.isEmpty(customerActivationDate)){
							long d = CommUtil.null2Long(customerActivationDate);
							tjyUser.setReconDate(new Date(d));
						}
						String servantNumber = (String)usermap.get("servantNumber");
						if(!StringUtil.isEmpty(servantNumber)){
							tjyUser.setKfTelephone(servantNumber);
						}
						String customerEndDate = (String)usermap.get("customerEndDate");
						if(!StringUtil.isEmpty(customerEndDate)){
							long d = CommUtil.null2Long(customerEndDate);
							tjyUser.setLastRegDate(new Date(d));
						}

						String customerEffectiveLevel = (String)usermap.get("customerEffectiveLevel");
						if(!StringUtil.isEmpty(customerEffectiveLevel)){
							if("天九家人".equals(customerEffectiveLevel)){
								tjyUser.setHonorTitle("家人");
								tjyUser.setHonorFlag("honor_001");
							}else if("天九云亲".equals(customerEffectiveLevel)){
								tjyUser.setHonorTitle("云亲");
								tjyUser.setHonorFlag("honor_002");
							}else if("天九伙伴".equals(customerEffectiveLevel)){
								tjyUser.setHonorTitle("伙伴");
								tjyUser.setHonorFlag("honor_003");
							}
						}
						tjyUserService.updateTjyUser(tjyUser);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				//通知IM
				TjyUser tuser = tjyUserService.selectByPrimaryKey(wxUser.getId() + "");
				IMUtil.sendUser(tuser.getId(),UUID.randomUUID().toString()+tuser.getId(),null,null);
				IMUtil.updateUserOne(tuser.getId(), tuser.getNickname(), tuser.getHeadPortrait());
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
			wxUser.setIsAppLogin(1);// 是否app登录
			wxUserService.updateWxUser(wxUser);
			// 首次登录发送短信提醒
			if (wxUser.getLogincount() == 1) {
				MessageInfo messageInfo = new MessageInfo();
				messageInfo.setId(UUIDGenerator.getUUID());
				messageInfo.setDeleted(0);
				messageInfo.setMobile(wxUser.getMobile());
				messageInfo.setType(3);// 系统
				messageInfo.setCreateTime(new Date());
				messageInfo.setToUserId(tjyUser.getId());
				String name = tjyUser.getNickname();
				messageInfo.setContent("【" + AldyMessageUtil.SMSPRE_APP + "】尊敬的" + name + "，欢迎登录企服联盟。");
				messageInfo.setStatus(0);
				messageInfoService.addMessageInfo(messageInfo);
			}
			session.setAttribute("me", me);
			session.setAttribute(Constants.SESSION_WXUSER_ID, wxUser.getWxUserId());
			session.setAttribute(Constants.SESSION_WXUSER_NICKNAME, wxUser.getNickName());
			session.setAttribute(Constants.SESSION_WXUSER_HDPIC, wxUser.getImgUrl());
			response.addHeader("Set-Cookie", "JSESSIONID=" + session.getId());
			
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
	@RequestMapping("get_imtoken")
	public @ResponseBody Map getImToken(String uid) {
		if (StringUtils.isEmpty(uid)) {
			return super.getAjaxResult("401", "参数错误", null);
		}
		return super.getAjaxResult("0", "获取成功", IMUtil.getUserImToken(uid));
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

				/**
				 * 没有用户需要到中间库同步数据
				 */
				Map usermap = tjyUserService.remotingGetUser(tjyUser.getMobile());
				if(usermap!=null){
					//调用远程数据库，获取用户信息
					try {
						String customerName = (String)usermap.get("customerName");
						if(!StringUtil.isEmpty(customerName)){
							tjyUser.setNickname(customerName);
							tjyUser.setTrueName(customerName);
							if(!customerName.equals(wxUser.getUsername())){
								wxUser.setUsername(customerName);
								wxUser.setTruename(customerName);
								wxUser.setNickName(customerName);
								wxUserService.updateWxUser(wxUser);
							}
						}
						String sex = (String)usermap.get("sexId");
						if(!StringUtil.isEmpty(sex)){
							int sexstr = 1;
							if("女".equals(sex)){
								sexstr = 2;
							}
							if(wxUser.getSex()!=null && sexstr!=wxUser.getSex()){
								wxUser.setSex(sexstr);
								wxUserService.updateWxUser(wxUser);
							}
						}
						String customerCompany = (String)usermap.get("customerCompany");
						if(!StringUtil.isEmpty(customerCompany)){
							tjyUser.setComName(customerCompany);
						}
						String customerTitle = (String)usermap.get("customerTitle");
						if(!StringUtil.isEmpty(customerTitle)){
							List<Map<String, Object>> listValues = listValuesService.selectListByType(12,customerTitle);

							if (null != listValues && listValues.size()>0) {
								Map lv = (Map) listValues.get(0);
								tjyUser.setJob((String)lv.get("id"));
							}
						}
						String customerUnitIndustry = (String)usermap.get("customerUnitIndustry");
						if(!StringUtil.isEmpty(customerUnitIndustry)){
							List<Map<String, Object>> listValues = listValuesService.selectListByType(8001,customerUnitIndustry);

							if (null != listValues && listValues.size()>0) {
								Map lv = (Map) listValues.get(0);
								tjyUser.setIndustry((String)lv.get("id"));

							}
						}
						String customerType = (String)usermap.get("customerType");
						if(!StringUtil.isEmpty(customerType)){
							if("普通客户".equals(customerType)){
								tjyUser.setReconStatus(1);
							}else if("成交客户".equals(customerType)||"认证客户".equals(customerType)){
								tjyUser.setReconStatus(2);
								Date reconDate = new Date();
								tjyUser.setReconDate(reconDate);
								tjyUser.setIsRealname(1);
//						Calendar now = Calendar.getInstance();
//						now.add(Calendar.YEAR, 1);
//						tjyUser.setLastRegDate(now.getTime());
							}else if("问题客户".equals(customerType)){
								tjyUser.setReconStatus(3);
							}
						}

						String customerCheckCapital = (String) usermap.get("customerCheckCapital");
						if (!StringUtil.isEmpty(customerCheckCapital)) {
							//注册资本
//						dd
							Pattern p = Pattern.compile("[0-9\\.]+");
							Matcher m = p.matcher(customerCheckCapital);
							if(m.find()){
								customerCheckCapital =  m.group();
								if(!StringUtil.isEmpty(customerCheckCapital)){
									tjyUser.setReconCapital(CommUtil.null2Double(customerCheckCapital));
								}
							}
						}

						String customerActivationDate = (String)usermap.get("customerActivationDate");
						if(!StringUtil.isEmpty(customerActivationDate)){
							long d = CommUtil.null2Long(customerActivationDate);
							tjyUser.setReconDate(new Date(d));
						}
						String servantNumber = (String)usermap.get("servantNumber");
						if(!StringUtil.isEmpty(servantNumber)){
							tjyUser.setKfTelephone(servantNumber);
						}
						String customerEndDate = (String)usermap.get("customerEndDate");
						if(!StringUtil.isEmpty(customerEndDate)){
							long d = CommUtil.null2Long(customerEndDate);
							tjyUser.setLastRegDate(new Date(d));
						}

						String customerEffectiveLevel = (String)usermap.get("customerEffectiveLevel");
						if(!StringUtil.isEmpty(customerEffectiveLevel)){
							if("天九家人".equals(customerEffectiveLevel)){
								tjyUser.setHonorTitle("家人");
								tjyUser.setHonorFlag("honor_001");
							}else if("天九云亲".equals(customerEffectiveLevel)){
								tjyUser.setHonorTitle("云亲");
								tjyUser.setHonorFlag("honor_002");
							}else if("天九伙伴".equals(customerEffectiveLevel)){
								tjyUser.setHonorTitle("伙伴");
								tjyUser.setHonorFlag("honor_003");
							}
						}
						tjyUserService.updateTjyUser(tjyUser);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
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
					tjyUser.setLastBindTime(new Date());
					tjyUserService.updateTjyUser(tjyUser);
					tjyUserService.remotingUpdateTjyUser(tjyUser, tjyUser.getMobile());
				}
			}
			
			//通知IM
			TjyUser tuser = tjyUserService.selectByPrimaryKey(wxUser.getId() + "");
			IMUtil.sendUser(tuser.getId(),UUID.randomUUID().toString()+tuser.getId(),null,null);
			IMUtil.updateUserOne(tuser.getId(), tuser.getNickname(), tuser.getHeadPortrait());
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
			wxUser.setIsAppLogin(1);// 是否app登录
			wxUserService.updateWxUser(wxUser);
			// 首次登录发送短信提醒
			if (wxUser.getLogincount() == 1) {
				MessageInfo messageInfo = new MessageInfo();
				messageInfo.setId(UUIDGenerator.getUUID());
				messageInfo.setDeleted(0);
				messageInfo.setMobile(wxUser.getMobile());
				messageInfo.setType(3);// 系统
				messageInfo.setCreateTime(new Date());
				messageInfo.setToUserId(tjyUser.getId());
				String name = tjyUser.getNickname();
				messageInfo.setContent("【" + AldyMessageUtil.SMSPRE_APP + "】尊敬的" + name + "，欢迎登录企服联盟。");
				messageInfo.setStatus(0);
				messageInfoService.addMessageInfo(messageInfo);
			}
			session.setAttribute("me", me);
			session.setAttribute(Constants.SESSION_WXUSER_ID, wxUser.getWxUserId());
			session.setAttribute(Constants.SESSION_WXUSER_NICKNAME, wxUser.getNickName());
			session.setAttribute(Constants.SESSION_WXUSER_HDPIC, wxUser.getImgUrl());
			response.addHeader("Set-Cookie", "JSESSIONID=" + session.getId());
			return super.getAjaxResult("0", "用户绑定成功", tjyUser);
		} catch (Exception e) {
			e.printStackTrace();
			return super.getAjaxResult("500", "系统错误", null);
		}
	}

	/**
	 * //TODO 模拟登录
	 * 
	 * @param userName
	 * @param pwd
	 * @param request
	 * @return
	 */
	@RequestMapping("val_login")
	public @ResponseBody Map login(HttpServletRequest request, String m) {
		WxUser wu = wxUserService.selectById(m);
		if (wu == null) {
			return super.getAjaxResult("302", "登录超时，请重新登录", null);
		} else {
			Member me = new Member();// 需要放入当前session 的用户信息
			me.setId(wu.getId() + "");
			me.setWxUserId(wu.getWxUserId());

			request.getSession().setAttribute("me", me);
		}
		return super.getSuccessAjaxResult("操作成功");
	}

	/**
	 * 
	 * //TODO 模拟登录
	 * 
	 * @param userName
	 * @param pwd
	 * @param request
	 * @return
	 */
	@RequestMapping("login")
	public @ResponseBody Map login(String userName, String pwd, HttpServletRequest request) {
		if (StringUtils.isEmpty(userName) || StringUtils.isEmpty(pwd)) {
			return super.getAjaxResult("999", "参数错误", null);
		}
		WxUser wxUser = wxUserService.selectByUserName(userName);
		if (null == wxUser) {
			wxUser = wxUserService.selectByMobile(userName);
			if (null == wxUser) {
				return super.getAjaxResult("999", "用户不存在", null);
			}
		}
		if (!MD5Util.md5Hex(pwd).equals(wxUser.getPassword())) {
			return super.getAjaxResult("999", "用户名密码错误", null);
		}
		String loginIp = IpUtil.getIpAddr(request);
		IpInfo ipInfo = IpUtil.getIpInfo(loginIp);

		Member me = new Member();// 需要放入当前session 的用户信息
		me.setId(wxUser.getId() + "");
		me.setIpInfo(ipInfo);
		me.setLoginTime(DateUtil.currentTimestamp());
		me.setUserName(wxUser.getUsername());
		me.setWxUserId(wxUser.getWxUserId());
		TjyUser user = tjyUserService.selectById(me.getId());
		me.setIsRealname(user.getIsRealname() + "");
		me.setOpenId(user.getOpenId());
		int loginCount = null == wxUser.getLogincount() ? 0 : wxUser.getLogincount();
		wxUser.setLogincount(loginCount);// 修改登录次数
		wxUserService.updateWxUser(wxUser);
		request.getSession().setAttribute("me", me);
		request.getSession().setAttribute(Constants.SESSION_WXUSER_ID, me.getWxUserId());
		request.getSession().setAttribute(Constants.SESSION_WXUSER_NICKNAME, user.getNickname());
		request.getSession().setAttribute(Constants.SESSION_WXUSER_HDPIC, user.getHeadPortrait());
		return super.getSuccessAjaxResult("操作成功");
	}

	/**
	 * //TODO 跳转至首页
	 * 
	 * @return
	 */
	@RequestMapping("index")
	public String index() {
		return "index";
	}

	/**
	 * //TODO 跳转至首页
	 * 
	 * @return
	 */
	@RequestMapping("loginPage")
	public String loginPage() {
		return "login";
	}
}
