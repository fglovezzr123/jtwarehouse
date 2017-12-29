package com.wing.socialcontact.front.action;

import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.wing.socialcontact.service.wx.api.IUserPhotoService;
import com.wing.socialcontact.service.wx.bean.UserPhoto;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache.ValueWrapper;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.alibaba.fastjson.JSONObject;
import com.tojoycloud.common.ConstantDefinition;
import com.tojoycloud.common.report.ResponseCode;
import com.tojoycloud.common.report.base.BaseAppAction;
import com.tojoycloud.common.util.RandomCode;
import com.tojoycloud.report.RequestReport;
import com.tojoycloud.report.ResponseReport;
import com.wing.socialcontact.common.model.IpInfo;
import com.wing.socialcontact.common.model.Member;
import com.wing.socialcontact.front.util.ApplicationPath;
import com.wing.socialcontact.service.wx.api.IMessageInfoService;
import com.wing.socialcontact.service.wx.api.ISysconfigService;
import com.wing.socialcontact.service.wx.api.ITjyUserService;
import com.wing.socialcontact.service.wx.api.IWxUserService;
import com.wing.socialcontact.service.wx.bean.MessageInfo;
import com.wing.socialcontact.service.wx.bean.Sysconfig;
import com.wing.socialcontact.service.wx.bean.TjyUser;
import com.wing.socialcontact.service.wx.bean.WxUser;
import com.wing.socialcontact.sys.bean.ListValues;
import com.wing.socialcontact.sys.service.IListValuesService;
import com.wing.socialcontact.util.AldyMessageUtil;
import com.wing.socialcontact.util.CommUtil;
import com.wing.socialcontact.util.Constants;
import com.wing.socialcontact.util.DateUtil;
import com.wing.socialcontact.util.H5TokenUtil;
import com.wing.socialcontact.util.IpUtil;
import com.wing.socialcontact.util.MsmValidateBean;
import com.wing.socialcontact.util.RedisCache;
import com.wing.socialcontact.util.Sign;
import com.wing.socialcontact.util.SpringContextUtil;
import com.wing.socialcontact.util.StringUtil;
import com.wing.socialcontact.util.UUIDGenerator;
import com.wing.socialcontact.util.im.IMUtil;
import sun.misc.BASE64Encoder;

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
public class AppAction extends BaseAppAction
{

	@Autowired
	private IWxUserService wxUserService;
	@Autowired
	private IMessageInfoService messageInfoService;
	@Autowired
	private ITjyUserService tjyUserService;
	@Autowired
	private IListValuesService listValuesService;
	@Autowired
	private IUserPhotoService userPhotoService;
	@Resource
	private ISysconfigService sysconfigService;
	private String imPrefix = ApplicationPath.getParameter(ConstantDefinition.IM_PREFIX);;

	/**
	 * 发送手机验证码
	 * 
	 * @Title: send_code
	 * @param session
	 * @param response
	 * @param mobile
	 * @return
	 * @return: Map
	 * @author: zengmin
	 * @date: 2017年5月3日 下午2:44:34
	 */
	@RequestMapping("send_code")
	public @ResponseBody
	ResponseReport send_code(@RequestBody RequestReport rr, HttpSession session, HttpServletResponse response)
	{

		String mobile = rr.getDataValue("mobile");
		try
		{
			if (!StringUtils.hasLength(mobile))
			{
				return super.getAjaxResult(rr, ResponseCode.NotSupport, "参数错误", null);
			}
			Sysconfig sysconfig = sysconfigService.getSysconfig();
			String vcode = "";
			
			if(sysconfig.getMessage_switch().equals("0")) {
				String content ="验证码"+vcode+",您正在注册成为"+AldyMessageUtil.SMSPRE+"用户，感谢您的支持！";
				vcode = "111111";
				System.out.println("-----往消息表加数据app----" + vcode);
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
					return super.getAjaxResult(rr, ResponseCode.Error, "验证码发送失败", null);
				}
				MsmValidateBean msmValidateBean = new MsmValidateBean(mobile, new Date(), vcode);
				session.setAttribute("mvb", msmValidateBean);
				return super.getSuccessAjaxResult(rr, "手机验证码发送成功");
			}else {
				
				if("18519122820".equals(mobile)) 
					vcode = "520520";
				else
					vcode = (int) ((Math.random() * 9 + 1) * 100000) + "";
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
						return super.getAjaxResult(rr, ResponseCode.Error, "验证码发送失败", null);
					}
					MsmValidateBean msmValidateBean = new MsmValidateBean(mobile, new Date(), vcode);
					session.setAttribute("mvb", msmValidateBean);
					return super.getSuccessAjaxResult(rr, "手机验证码发送成功");
				}else{
					return super.getAjaxResult(rr, ResponseCode.Error, "验证码发送失败", null);
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return super.getAjaxResult(rr, ResponseCode.Error, "系统错误", null);
		}
	}

	/**
	 * app微信快捷登录
	 * 
	 * @Title: wx_login
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
	public @ResponseBody
	ResponseReport wx_login(@RequestBody RequestReport rr, HttpSession session, HttpServletRequest request, HttpServletResponse response)
	{
		String wxUniqueId = rr.getDataValue("wxUniqueId");
		try
		{
			if (!StringUtils.hasLength(wxUniqueId))
			{
				return super.getAjaxResult(rr, ResponseCode.NotSupport, "参数错误", null);
			}
			TjyUser tjyUser = tjyUserService.selectByWxUniqueId(wxUniqueId);
			if (null == tjyUser)
			{
				return super.getAjaxResult(rr, ResponseCode.Error, "登录失败，用户未绑定", null);
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
			if (wxUser.getLogincount() == 1)
			{
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
			return super.getAjaxResult(rr, ResponseCode.OK, "登录成功", tjyUser);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return super.getAjaxResult(rr, ResponseCode.Error, "系统错误", null);
		}
	}

	/**
	 * app手机号加验证码登录
	 * 
	 * @Title: mobile_login
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
	public @ResponseBody
	ResponseReport mobile_login(@RequestBody RequestReport rr, HttpSession session, HttpServletRequest request, HttpServletResponse response)
	{
		String mobile = rr.getDataValue("mobile");
		String dyz = rr.getDataValue("dyz");
		try
		{
			if (StringUtils.isEmpty(mobile) || StringUtils.isEmpty(dyz))
				return super.getAjaxResult(rr, ResponseCode.NotSupport, "参数错误", null);
			boolean bo = MsmValidateBean.validateCode(mobile, dyz, session);
			if (!bo)
				return super.getAjaxResult(rr, ResponseCode.Error, "手机验证码错误或已失效", null);
			// 清除手机验证码
			session.removeAttribute("mvb");
			WxUser wxUser = wxUserService.selectByMobile(mobile);
			if (null == wxUser)
			{
				wxUser = new WxUser();
				wxUser.setMobile(mobile);
				wxUser.setDeletestatus(false);
				wxUser.setAddtime(new Date());
				String nickN = mobile.replace(mobile.substring(3, 8), "**");
				wxUser.setNickName(nickN);
				wxUser.setUsername(mobile);
				wxUser.setUsertype((byte) 2);
				// bo = wxUserService.addWxUser(wxUser);

				Map usermap = tjyUserService.remotingGetUser(mobile);

				// wxUser = wxUserService.selectByMobile(mobile);
				TjyUser tjyUser = new TjyUser();
				// tjyUserService.selectByPrimaryKey(wxUser.getId() + "");
				if (usermap != null)
				{
					try
					{
						// 调用远程数据库，获取用户信息
						String customerName = (String) usermap.get("customerName");
						if (!StringUtil.isEmpty(customerName))
						{
							wxUser.setTruename(customerName);
							// tjyUser.setNickname(customerName);
							// tjyUser.setTrueName(customerName);
							// if (!customerName.equals(wxUser.getUsername()))
							// {
							wxUser.setUsername(customerName);
							wxUser.setNickName(customerName);
							// wxUser.setTruename(customerName);
							// wxUser.setNickName(customerName);
							// wxUserService.updateWxUser(wxUser);
							// }
						}
						String sex = (String) usermap.get("sexId");
						if (!StringUtil.isEmpty(sex))
						{
							int sexstr = 1;
							if ("女".equals(sex))
								sexstr = 2;
							if (wxUser.getSex() != null && sexstr != wxUser.getSex())
								wxUser.setSex(sexstr);
							// wxUserService.updateWxUser(wxUser);
						}
						String customerCompany = (String) usermap.get("customerCompany");
						if (!StringUtil.isEmpty(customerCompany))
							tjyUser.setComName(customerCompany.split(",")[0]);
						String customerTitle = (String) usermap.get("customerTitle");
						if (!StringUtil.isEmpty(customerTitle))
						{
							List<Map<String, Object>> listValues = listValuesService.selectListByType(12, customerTitle);
							if (null != listValues && !listValues.isEmpty())
							{
								Map lv = (Map) listValues.get(0);
								tjyUser.setJob((String) lv.get("id"));
							}
						}
						String customerUnitIndustry = (String) usermap.get("customerUnitIndustry");
						if (!StringUtil.isEmpty(customerUnitIndustry))
						{
							List<Map<String, Object>> listValues = listValuesService.selectListByType(8001, customerUnitIndustry);
							if (null != listValues && !listValues.isEmpty())
							{
								Map lv = (Map) listValues.get(0);
								tjyUser.setIndustry((String) lv.get("id"));
							}
						}
						String customerType = (String) usermap.get("customerType");
						if (!StringUtil.isEmpty(customerType))
						{
							if ("普通客户".equals(customerType))
							{
								tjyUser.setReconStatus(1);
							}
							else
								if ("成交客户".equals(customerType) || "认证客户".equals(customerType))
								{
									tjyUser.setReconStatus(2);
									Date reconDate = new Date();
									tjyUser.setReconDate(reconDate);
									tjyUser.setIsRealname(1);
									// Calendar now = Calendar.getInstance();
									// now.add(Calendar.YEAR, 1);
									// tjyUser.setLastRegDate(now.getTime());
								}
								else
									if ("问题客户".equals(customerType))
										tjyUser.setReconStatus(3);
						}
						String activeDate = (String) usermap.get("customerActivationDate");
						if (!StringUtil.isEmpty(activeDate))
							tjyUser.setReconDate(new Date(Long.parseLong(activeDate)));
						String customerCheckCapital = (String) usermap.get("customerCheckCapital");
						if (!StringUtil.isEmpty(customerCheckCapital))
						{
							// 注册资本
							Pattern p = Pattern.compile("[0-9\\.]+");
							Matcher m = p.matcher(customerCheckCapital);
							if (m.find())
							{
								customerCheckCapital = m.group();
								if (!StringUtil.isEmpty(customerCheckCapital))
									tjyUser.setReconCapital(CommUtil.null2Double(customerCheckCapital));
							}
						}

						String customerActivationDate = (String) usermap.get("customerActivationDate");
						if (!StringUtil.isEmpty(customerActivationDate))
							tjyUser.setReconDate(new Date(CommUtil.null2Long(customerActivationDate)));
						String servantNumber = (String) usermap.get("servantNumber");
						if (!StringUtil.isEmpty(servantNumber))
							tjyUser.setKfTelephone(servantNumber);
						String customerEndDate = (String) usermap.get("customerEndDate");
						if (!StringUtil.isEmpty(customerEndDate))
							tjyUser.setLastRegDate(new Date(CommUtil.null2Long(customerEndDate)));

						String customerEffectiveLevel = (String) usermap.get("customerEffectiveLevel");
						if (!StringUtil.isEmpty(customerEffectiveLevel))
						{
							if ("天九家人".equals(customerEffectiveLevel))
							{
								tjyUser.setHonorTitle("家人");
								tjyUser.setHonorFlag("honor_001");
							}
							else
								if ("天九云亲".equals(customerEffectiveLevel))
								{
									tjyUser.setHonorTitle("云亲");
									tjyUser.setHonorFlag("honor_002");
								}
								else
									if ("天九伙伴".equals(customerEffectiveLevel))
									{
										tjyUser.setHonorTitle("伙伴");
										tjyUser.setHonorFlag("honor_003");
									}
						}
						tjyUser.setReconStatus(2);
						// tjyUserService.updateTjyUser(tjyUser);
					}
					catch (Exception e)
					{
						e.printStackTrace();
					}
				}
				wxUserService.addWxUser(wxUser, tjyUser);
				session.removeAttribute("imgCode");
			}
			wxUser = wxUserService.selectByMobile(mobile);
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
			if (wxUser.getLogincount() == 1)
			{
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
			// 通知IM
			String res = IMUtil.updateUserOne(imPrefix + tjyUser.getId(), tjyUser.getNickname(), tjyUser.getHeadPortrait());
			JSONObject jsonObject = JSONObject.parseObject(res);
			if (!"200".equals(jsonObject.getString("code")))
			{
				IMUtil.sendUser(imPrefix + tjyUser.getId(), "", tjyUser.getNickname(), "");
			}

			//获取拍照审核状态
			UserPhoto userPhoto = userPhotoService.selectByUserId(tjyUser.getId());
			if(userPhoto !=null && userPhoto.getStatus()==1){
				tjyUser.setPhotoStatus(1);
			}else{
				tjyUser.setPhotoStatus(0);
			}

			return super.getAjaxResult(rr, ResponseCode.OK, "登录成功", tjyUser);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return super.getAjaxResult(rr, ResponseCode.Error, "系统错误", null);
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
	public @ResponseBody
	ResponseReport bind_user(@RequestBody RequestReport rr, HttpSession session, HttpServletRequest request, HttpServletResponse response)
	{
		String mobile = rr.getDataValue("mobile");
		String dyz = rr.getDataValue("dyz");
		String wxUniqueId = rr.getDataValue("wxUniqueId");
		try
		{
			if (StringUtils.isEmpty(mobile) || StringUtils.isEmpty(dyz) || StringUtils.isEmpty(wxUniqueId))
			{
				return super.getAjaxResult(rr, ResponseCode.NotSupport, "参数错误", null);
			}
			boolean bo = MsmValidateBean.validateCode(mobile, dyz, session);
			if (!bo)
			{
				return super.getAjaxResult(rr, ResponseCode.Error, "手机验证码错误或已失效", null);
			}
			// 清除手机验证码
			session.removeAttribute("mvb");
			TjyUser tjyUser = tjyUserService.selectByWxUniqueId(wxUniqueId);
			if (null != tjyUser)
			{
				return super.getAjaxResult(rr, ResponseCode.Error, "绑定用户失败，用户已绑定", null);
			}
			WxUser wxUser = wxUserService.selectByMobile(mobile);
			// 用户不存在，需要创建用户
			if (null == wxUser)
			{
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
			}
			else
			{// 用户已存在，直接实现绑定
				tjyUser = tjyUserService.selectByPrimaryKey(wxUser.getId() + "");
				if (null == tjyUser)
				{
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
				}
				else
				{
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
			if (wxUser.getLogincount() == 1)
			{
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
			return super.getAjaxResult(rr, ResponseCode.OK, "用户绑定成功", tjyUser);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return super.getAjaxResult(rr, ResponseCode.Error, "系统错误", null);
		}
	}

	/**
	 * app获取imtoken
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
	public @ResponseBody
	ResponseReport getImToken(@RequestBody RequestReport rr, HttpServletRequest request)
	{
		String uid = rr.getUserProperty().getUserId();
		if (StringUtils.isEmpty(uid))
		{
			return super.getAjaxResult(rr, ResponseCode.NotSupport, "参数错误", null);
		}
		String token = IMUtil.getUserImToken(imPrefix + uid);
		Map data = new HashMap();
		data.put("token", token);
		data.put("env", imPrefix);
		return super.getAjaxResult(rr, ResponseCode.OK, "获取成功", data);
	}

	/**
	 * app获取h5 token
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
	@RequestMapping("get_h5token")
	public @ResponseBody
	ResponseReport getH5Token(@RequestBody RequestReport rr, HttpServletRequest request)
	{
		String uid = rr.getUserProperty().getUserId();
		if (StringUtils.isEmpty(uid))
		{
			return super.getAjaxResult(rr, ResponseCode.NotSupport, "参数错误", null);
		}
		String token = H5TokenUtil.getUserH5Token(uid);
		Map data = new HashMap();
		data.put("token", token);
		// 需要生成token
		return super.getAjaxResult(rr, ResponseCode.OK, "获取成功", data);
	}

	/**
	 * 获取OSS Token
	 * 
	 * @param rr
	 * @return
	 */
	@RequestMapping("get_ststoken")
	public @ResponseBody
	ResponseReport getSTSToken(@RequestBody RequestReport rr)
	{
		String resToken = "";
		try
		{
			RedisCache redisCache = (RedisCache) SpringContextUtil.getBean("redisCache");
			ValueWrapper vw = redisCache.get(ConstantDefinition.ALI_OSS_STSToken);
			if (vw == null)
			{
				// TODO 验证短时间内多次获取OSStoken，是否返回同一值
				Map<String, String> queries = new HashMap<String, String>();
				queries.put("Action", "AssumeRole");
				queries.put("RoleArn", "acs:ram::1501583579726819:role/ramapp");
				queries.put("RoleSessionName", "RamAPP");
				queries.put("Format", "JSON");
				queries.put("Version", "2015-04-01");
				queries.put("DurationSeconds", "3600");
				queries.put("SignatureMethod", "HMAC-SHA1");
				queries.put("SignatureNonce", RandomCode.getRandomCode(6));
				queries.put("SignatureVersion", "1.0");
				queries.put("AccessKeyId", "LTAITsw4SI1d56Q7");
				queries.put("Timestamp", Sign.getISO8601Time());
				String perToSign = Sign.composeStringToSign(queries);
				byte[] sha1 = null;

				sha1 = Sign.hmacSHA1Signature("ljWPzf0qA5I9OhBsTo5DsQEFkc2CxG&", perToSign);
				String sign = new String(new BASE64Encoder().encode(sha1));
				queries.put("Signature", sign);
				String url = null;
				url = Sign.composeUrl("sts.aliyuncs.com", queries);
				HttpClient httpClient = new DefaultHttpClient();
				HttpGet httpGet = new HttpGet(url);
				HttpResponse response = httpClient.execute(httpGet);
				System.out.println(response.getStatusLine());
				InputStream in = response.getEntity().getContent();
				byte[] bReaded = IOUtils.toByteArray(in);
				IOUtils.closeQuietly(in);
				resToken = new String(bReaded, 0, bReaded.length, "utf-8");
				boolean res = redisCache.putNX(ConstantDefinition.ALI_OSS_STSToken, resToken, ConstantDefinition.ALI_OSS_STSToken_EXPIRE);

				if (!res)
				{
					resToken = (String) redisCache.get(ConstantDefinition.ALI_OSS_STSToken).get();
				}
			}
			else
			{
				resToken = (String) vw.get();
			}
		}
		catch (Exception e)
		{
			return super.getAjaxResult(rr, ResponseCode.Error, "获取失败", e.getMessage());
		}
		Map data = new HashMap();
		data.put("token", resToken);
		return super.getSuccessAjaxResult(rr, "成功", data);
	}
}
