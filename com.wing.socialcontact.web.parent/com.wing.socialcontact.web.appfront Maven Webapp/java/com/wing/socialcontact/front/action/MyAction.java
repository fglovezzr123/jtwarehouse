package com.wing.socialcontact.front.action;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.jdom.JDOMException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import tk.mybatis.mapper.util.StringUtil;

import com.alibaba.fastjson.JSON;
import com.wing.socialcontact.common.action.BaseAction;
import com.wing.socialcontact.common.model.Member;
import com.wing.socialcontact.config.OssConfig;
import com.wing.socialcontact.front.util.ApplicationPath;
import com.wing.socialcontact.service.im.api.IImFollowService;
import com.wing.socialcontact.service.im.api.IImFriendService;
import com.wing.socialcontact.service.im.bean.ImFriend;
import com.wing.socialcontact.service.wx.api.ICouponLogService;
import com.wing.socialcontact.service.wx.api.IHonorService;
import com.wing.socialcontact.service.wx.api.IHzbManagerLogService;
import com.wing.socialcontact.service.wx.api.IInviteRecordService;
import com.wing.socialcontact.service.wx.api.ILeaveMsgService;
import com.wing.socialcontact.service.wx.api.IMessageInfoService;
import com.wing.socialcontact.service.wx.api.IReconPhotosService;
import com.wing.socialcontact.service.wx.api.ISysconfigService;
import com.wing.socialcontact.service.wx.api.ITjyUserService;
import com.wing.socialcontact.service.wx.api.IUserEmpiricalService;
import com.wing.socialcontact.service.wx.api.IUserFavService;
import com.wing.socialcontact.service.wx.api.IUserFriendimpressService;
import com.wing.socialcontact.service.wx.api.IUserGreetingsService;
import com.wing.socialcontact.service.wx.api.IUserHonorService;
import com.wing.socialcontact.service.wx.api.IUserIntegralEmpiricalService;
import com.wing.socialcontact.service.wx.api.IUserIntegralLogService;
import com.wing.socialcontact.service.wx.api.IUserLatestvistorService;
import com.wing.socialcontact.service.wx.api.IWalletLogService;
import com.wing.socialcontact.service.wx.api.IWalletTxService;
import com.wing.socialcontact.service.wx.api.IWxUserService;
import com.wing.socialcontact.service.wx.bean.Comment;
import com.wing.socialcontact.service.wx.bean.Honor;
import com.wing.socialcontact.service.wx.bean.HzbManagerLog;
import com.wing.socialcontact.service.wx.bean.LeaveMsg;
import com.wing.socialcontact.service.wx.bean.MessageInfo;
import com.wing.socialcontact.service.wx.bean.ReconPhotos;
import com.wing.socialcontact.service.wx.bean.Sysconfig;
import com.wing.socialcontact.service.wx.bean.TjyUser;
import com.wing.socialcontact.service.wx.bean.UserEmpirical;
import com.wing.socialcontact.service.wx.bean.UserFav;
import com.wing.socialcontact.service.wx.bean.UserFriendimpress;
import com.wing.socialcontact.service.wx.bean.UserGreetings;
import com.wing.socialcontact.service.wx.bean.UserHonor;
import com.wing.socialcontact.service.wx.bean.UserIntegralEmpirical;
import com.wing.socialcontact.service.wx.bean.UserLatestvistor;
import com.wing.socialcontact.service.wx.bean.WalletLog;
import com.wing.socialcontact.service.wx.bean.WalletTx;
import com.wing.socialcontact.service.wx.bean.WxUser;
import com.wing.socialcontact.sys.bean.ListValues;
import com.wing.socialcontact.sys.bean.SyDistrict;
import com.wing.socialcontact.sys.service.IDistrictService;
import com.wing.socialcontact.sys.service.IListValuesService;
import com.wing.socialcontact.util.AldyMessageUtil;
import com.wing.socialcontact.util.CommUtil;
import com.wing.socialcontact.util.ConfigUtil;
import com.wing.socialcontact.util.Constants;
import com.wing.socialcontact.util.DateUtils;
import com.wing.socialcontact.util.HttpClientUtil;
import com.wing.socialcontact.util.JsonUtil;
import com.wing.socialcontact.util.PayCommonUtil;
import com.wing.socialcontact.util.ServletUtil;
import com.wing.socialcontact.util.SpringContextUtil;
import com.wing.socialcontact.util.UUIDGenerator;
import com.wing.socialcontact.util.WeixinUtil;
import com.wing.socialcontact.util.WxMsmUtil;
import com.wing.socialcontact.util.XMLUtil;
import com.wing.socialcontact.util.bean.ArticlesBean;
import com.wing.socialcontact.util.bean.NewsContent;
import com.wing.socialcontact.util.bean.TextContent;
import com.wing.socialcontact.util.bean.WxMsmBean;
import com.wing.socialcontact.util.pojo.AccessToken;

/**
 * 个人相关操作
 * 
 * @ClassName: MyAction
 * @Description: TODO
 * @author: zengmin
 * @date: 2017年4月9日 下午10:22:17
 */
@Controller
@RequestMapping("/m/my")
public class MyAction extends BaseAction {

	@Autowired
	private IListValuesService listValuesService;

	@Autowired
	private IDistrictService districtService;

	@Autowired
	private IWxUserService wxUserService;

	@Autowired
	private ITjyUserService tjyUserService;

	@Autowired
	private IUserFavService userFavService;

	@Autowired
	private IWalletLogService walletLogService;

	@Autowired
	private IReconPhotosService reconPhotosService;

	@Autowired
	private IUserFriendimpressService userFriendimpressService;

	@Autowired
	private IUserHonorService userHonorService;

	@Autowired
	private IHonorService honorService;

	@Autowired
	private ISysconfigService sysconfigService;

	@Autowired
	private IMessageInfoService messageInfoService;

	@Autowired
	private IUserGreetingsService userGreetingsService;

	@Autowired
	private ILeaveMsgService leaveMsgService;

	@Autowired
	private IUserLatestvistorService userLatestvistorService;

	@Autowired
	private IWalletTxService walletTxService;

	@Autowired
	private IImFriendService imFriendService;

	@Autowired
	private IImFollowService imFollowService;

	@Autowired
	private IUserEmpiricalService userEmpiricalService;

	@Autowired
	private IUserIntegralEmpiricalService userIntegralEmpiricalService;

	@Resource
	protected IUserIntegralLogService userIntegralLogService;

	@Autowired
	private IInviteRecordService inviteRecordService;

	@Autowired
	private IHzbManagerLogService hzbManagerLogService;

	@Autowired
	private ICouponLogService couponLogService;

	/**
	 * 跳转至认证页面
	 * 
	 * @Title: reconPage
	 * @Description: TODO
	 * @return
	 * @return: String
	 * @author: zengmin
	 * @date: 2017年4月7日 上午10:16:15
	 */
	@RequestMapping("reconPage")
	public String reconPage(ModelMap modelMap, HttpServletRequest request) {
		Member me = ServletUtil.getMember(request);
		if (null == me) {
			return "login";
		}
		String userId = me.getId();
		if (StringUtils.isEmpty(userId)) {
			return "login";
		}
		TjyUser tjyUser = tjyUserService.selectByPrimaryKey(userId);
		if (null == tjyUser.getReconStatus() || "0".equals(tjyUser.getReconStatus() + "")) {
			// 获取职位
			List zwList = listValuesService.selectListByType(12);
			modelMap.addAttribute("zwList", zwList);
			// 获取行业
			List hyList = listValuesService.selectListByType(8001);
			modelMap.addAttribute("hyList", hyList);
			// 获取省
			List provinceList = districtService.selectDistrictByType("1");
			modelMap.addAttribute("provinceList", provinceList);
			// 获取市
			List cityList = districtService.selectDistrictByType("2");
			modelMap.addAttribute("cityList", cityList);
			// 获取区
			List areaList = districtService.selectDistrictByType("3");
			modelMap.addAttribute("areaList", areaList);
			modelMap.addAttribute("tjyUser", tjyUser);
			return "mine/recon";
		} else if ("3".equals(tjyUser.getReconStatus() + "")) {// 认证不通过，需要再次修改认证
			// 获取职位
			List zwList = listValuesService.selectListByType(12);
			modelMap.addAttribute("zwList", zwList);
			// 获取行业
			List hyList = listValuesService.selectListByType(8001);
			modelMap.addAttribute("hyList", hyList);
			// 获取省
			List provinceList = districtService.selectDistrictByType("1");
			modelMap.addAttribute("provinceList", provinceList);
			// 获取市
			List cityList = districtService.selectDistrictByType("2");
			modelMap.addAttribute("cityList", cityList);
			// 获取区
			List areaList = districtService.selectDistrictByType("3");
			modelMap.addAttribute("areaList", areaList);
			// 获取已选职位
			ListValues job = listValuesService.selectByPrimaryKey(tjyUser.getJob());
			modelMap.addAttribute("jobName", job.getListValue());
			// 获取已选行业
			ListValues industry = listValuesService.selectByPrimaryKey(tjyUser.getIndustry());
			modelMap.addAttribute("industryName", industry.getListValue());
			// 获取已选省
			if (StringUtils.hasLength(tjyUser.getProvince())) {
				SyDistrict province = districtService.selectByPrimaryKey(tjyUser.getProvince());
				modelMap.addAttribute("provinceName", province.getDisName());
			}

			// 获取已选市
			if (StringUtils.hasLength(tjyUser.getCity())) {
				SyDistrict city = districtService.selectByPrimaryKey(tjyUser.getCity());
				modelMap.addAttribute("cityName", city.getDisName());
			}

			// 获取已选区
			if (StringUtils.hasLength(tjyUser.getCounty())) {
				SyDistrict county = districtService.selectByPrimaryKey(tjyUser.getCounty());
				modelMap.addAttribute("countyName", county.getDisName());
			}
			// 获取认证图片
			List<Map<String, Object>> imgList = reconPhotosService.selectByUserId(tjyUser.getId());
			modelMap.addAttribute("imgList", imgList);
			OssConfig ossConfig = (OssConfig) SpringContextUtil.getBean("ossConfig");
			String ossurl = ossConfig.getOss_getUrl();
			modelMap.addAttribute("ossurl", ossurl);
			modelMap.addAttribute("tjyUser", tjyUser);
			return "mine/recon_edit";
		} else {
			// 获取职位
			ListValues job = listValuesService.selectByPrimaryKey(tjyUser.getJob());
			tjyUser.setJob(job.getListValue());
			// 获取行业
			ListValues industry = listValuesService.selectByPrimaryKey(tjyUser.getIndustry());
			tjyUser.setIndustry(industry.getListValue());
			// 获取省
			if (StringUtils.hasLength(tjyUser.getProvince())) {
				SyDistrict province = districtService.selectByPrimaryKey(tjyUser.getProvince());
				tjyUser.setProvince(province.getDisName());
			}

			// 获取市
			if (StringUtils.hasLength(tjyUser.getCity())) {
				SyDistrict city = districtService.selectByPrimaryKey(tjyUser.getCity());
				tjyUser.setCity(city.getDisName());
			}

			// 获取区
			if (StringUtils.hasLength(tjyUser.getCounty())) {
				SyDistrict county = districtService.selectByPrimaryKey(tjyUser.getCounty());
				tjyUser.setCounty(county.getDisName());
			}
			// 获取认证图片
			List<Map<String, Object>> imgList = reconPhotosService.selectByUserId(tjyUser.getId());
			modelMap.addAttribute("imgList", imgList);
			modelMap.addAttribute("tjyUser", tjyUser);
			OssConfig ossConfig = (OssConfig) SpringContextUtil.getBean("ossConfig");
			String ossurl = ossConfig.getOss_getUrl();
			modelMap.addAttribute("ossurl", ossurl);
			return "mine/recon_view";
		}
	}

	@RequestMapping("/add/reconSave")
	public @ResponseBody Map reconSave(HttpServletRequest request, HttpSession session, TjyUser tjyUser,
			String zjImgerJson) {
		try {
			Member me = ServletUtil.getMember(request);
			TjyUser oldTjyUser = tjyUserService.selectByPrimaryKey(me.getId());
			oldTjyUser.setAddress(tjyUser.getAddress());
			oldTjyUser.setCity(tjyUser.getCity());
			oldTjyUser.setComName(tjyUser.getComName());
			oldTjyUser.setComProfile(tjyUser.getComProfile());
			oldTjyUser.setCounty(tjyUser.getCounty());
			oldTjyUser.setIndustry(tjyUser.getIndustry());
			oldTjyUser.setIsRealname(0);// 认证状态
			oldTjyUser.setReconCapital(tjyUser.getReconCapital());// 注册资金
			oldTjyUser.setJob(tjyUser.getJob());
			oldTjyUser.setProvince(tjyUser.getProvince());
			oldTjyUser.setTrueName(tjyUser.getTrueName());
			oldTjyUser.setReconStatus(1);// 提交认证审核
			oldTjyUser.setReconMobile(oldTjyUser.getMobile());// 认证手机号和注册手机号保持一致
			oldTjyUser.setTjReconDate(new Date());

			if (!StringUtils.isEmpty(tjyUser.getComName()) || !StringUtils.isEmpty(tjyUser.getComProfile())
					|| !StringUtils.isEmpty(tjyUser.getIndustry()) || !StringUtils.isEmpty(tjyUser.getJob())) {
				// 此项积分按照完成度百分百获得积分，终身一次
				userIntegralLogService.addLntAndEmp(oldTjyUser.getId(), "task_0020");
			}
			boolean bo = tjyUserService.updateTjyUser(oldTjyUser);
			if (bo) {
				System.out.println("zjImgerJson" + zjImgerJson);
				if (StringUtils.isEmpty(zjImgerJson)) {
					return super.getAjaxResult("999", "认证资料提交失败，证件照片不能为空", null);
				}
				String[] images = zjImgerJson.split(";");
				String[] img = null;
				for (int i = 0; i < images.length; i++) {
					img = images[i].split(",");
					String id = img[0];
					// 如果有id则不作处理
					if (StringUtils.hasLength(id) && !"0".equals(id)) {
						continue;
					}
					String imgUrl = img[1];
					int type = Integer.valueOf(img[2]);
					ReconPhotos reconPhotos = new ReconPhotos();
					reconPhotos.setUserId(oldTjyUser.getId());
					reconPhotos.setImgUrl(imgUrl);
					reconPhotos.setType(type);
					reconPhotosService.addReconPhotos(reconPhotos);
				}
				// // 获取后台管理员
				// List<Map<String, Object>> managerList =
				// wxUserService.selectByManager("ADMIN");
				// String touser = "";
				// int i = 0;
				// for (Map<String, Object> map : managerList) {
				// String wxUserId = (String) map.get("wx_user_id");
				// if (StringUtils.isEmpty(wxUserId)) {
				// continue;
				// }
				// if (i > 0) {
				// touser += "|";
				// }
				// touser += (String) map.get("wx_user_id");
				// i++;
				// }
				// // 给后台管理员发送微信消息，提醒他进行审核
				// if (i > 0) {
				// MessageInfo messageInfo = new MessageInfo();
				// messageInfo.setId(UUIDGenerator.getUUID());
				// messageInfo.setDeleted(0);
				// messageInfo.setType(2);// 微信
				// messageInfo.setToUserId(touser);
				// messageInfo.setCreateTime(new Date());
				// // 组装内容
				// List<ArticlesBean> articleList = new
				// ArrayList<ArticlesBean>();
				// ArticlesBean articlesBean = new ArticlesBean("会员资料认证提醒");
				// articleList.add(articlesBean);
				// articlesBean = new ArticlesBean("会员名称:" +
				// tjyUser.getTrueName());
				// articleList.add(articlesBean);
				// articlesBean = new ArticlesBean("提交时间:" +
				// DateUtils.datetimeToString(oldTjyUser.getTjReconDate()));
				// articleList.add(articlesBean);
				// NewsContent content = new NewsContent(articleList);
				// String con = WxMsmUtil.getNewsMessageContent(content);
				// messageInfo.setContent(con);
				// messageInfo.setTemplateId("RECON_ADMIN");
				// messageInfo.setStatus(0);// 未发送
				// messageInfo.setWxMsgType(2);/// ** 微信消息类型（1：文本消息2：图文消息） */
				// messageInfoService.addMessageInfo(messageInfo);
				// }
				// 给提交用户提醒
				WxUser wxUser = wxUserService.selectByPrimaryKey(oldTjyUser.getId());
				MessageInfo messageInfo = new MessageInfo();
				messageInfo.setId(UUIDGenerator.getUUID());
				messageInfo.setDeleted(0);
				messageInfo.setType(2);// 微信
				messageInfo.setToUserId(wxUser.getWxUserId());
				messageInfo.setCreateTime(new Date());
				// 组装内容
				String content = "您的认证资料已提交，请等待审核";
				String con = WxMsmUtil.getTextMessageContent(content);
				messageInfo.setContent(con);
				messageInfo.setTemplateId("RECON_USER");
				messageInfo.setStatus(0);// 未发送
				messageInfo.setWxMsgType(1);/// ** 微信消息类型（1：文本消息2：图文消息） */
				messageInfoService.addMessageInfo(messageInfo);
				// 发送短信提醒
				/*
				 * // 此项积分1年获取一次
				 * userIntegralLogService.addLntAndEmp(wxUser.getWxUserId(),
				 * "task_0018");
				 * 
				 * //如果是被邀请的，要给被邀请人送积分 String openId=oldTjyUser.getOpenId();
				 * if(!StringUtils.isEmpty(openId)){ List<Map<String, Object>>
				 * irList =
				 * inviteRecordService.selectInviteRecordPageByOpenId(openId);
				 * if(null != irList && irList.size()>0){ for(Map<String,
				 * Object> m:irList){ String userId = (String)m.get("userId");
				 * //邀请新用户注册+认证:task_0023 if(!StringUtils.isEmpty(userId)){
				 * userIntegralLogService.addLntAndEmp(wxUser.getWxUserId(),
				 * "task_0023"); } } } }
				 */
				return super.getSuccessAjaxResult("认证资料已提交，待审核");
			} else {
				return super.getAjaxResult("999", "认证资料提交失败", null);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return super.getAjaxResult("500", "系统错误", null);
		}
	}

	/**
	 * 个人中心，首页
	 * 
	 * @return
	 */
	@RequestMapping("my_center")
	public String personCentre(HttpServletRequest request, ModelMap modelMap) {
		Member me = ServletUtil.getMember(request);
		if (null == me) {
			return "login";
		}
		String userId = me.getId();
		if (StringUtils.isEmpty(userId)) {
			return "login";
		}
		WxUser user = wxUserService.selectByPrimaryKey(userId);
		modelMap.addAttribute("user", user);
		TjyUser tjyuser = tjyUserService.selectByPrimaryKey(userId);
		if (!StringUtils.isEmpty(tjyuser.getIndustry())) {
			ListValues sd = listValuesService.selectByPrimaryKey(tjyuser.getIndustry());
			ListValues job = listValuesService.selectByPrimaryKey(tjyuser.getJob());
			tjyuser.setIndustry("");
			tjyuser.setJob("");
			if (null != sd) {
				tjyuser.setIndustry(sd.getListValue());
			}
			if (null != job) {
				tjyuser.setJob(job.getListValue());
			}
		}
		if (StringUtils.isEmpty(tjyuser.getVisitQuantity())) {
			tjyuser.setVisitQuantity(0.0);
		}

		UserHonor userHonor = new UserHonor();
		userHonor.setUserId(userId);
		List userhonors = userHonorService.selectAllUserHonor(userHonor);

		OssConfig ossConfig = (OssConfig) SpringContextUtil.getBean("ossConfig");
		String ossurl = ossConfig.getOss_getUrl();
		modelMap.addAttribute("ossurl", ossurl);

		modelMap.addAttribute("userhonors", userhonors);
		modelMap.addAttribute("user", user);
		modelMap.addAttribute("tjyuser", tjyuser);
		return "mine/my_center";
	}

	/**
	 * 个人设置
	 * 
	 * @return
	 */
	@RequestMapping("person_setting")
	public String personSetting(HttpServletRequest request, ModelMap modelMap) {
		Member me = ServletUtil.getMember(request);
		if (null == me) {
			return "login";
		}
		String userId = me.getId();
		if (StringUtils.isEmpty(userId)) {
			return "login";
		}
		Map<String, Object> user = wxUserService.queryUsersByid(userId);

		if (!StringUtils.isEmpty(user.get("province"))) {
			SyDistrict sd = districtService.selectByPrimaryKey((String) user.get("province"));
			user.put("province", sd.getDisName());
		}
		if (!StringUtils.isEmpty(user.get("city"))) {
			SyDistrict sd = districtService.selectByPrimaryKey((String) user.get("city"));
			user.put("city", sd.getDisName());
		}
		if (!StringUtils.isEmpty(user.get("county"))) {
			SyDistrict sd = districtService.selectByPrimaryKey((String) user.get("county"));
			user.put("county", sd.getDisName());
		}

		modelMap.addAttribute("user", user);
		UserFav userFav = new UserFav();
		userFav.setUserId(user.get("id") + "");
		List<Map<String, Object>> userFavs = userFavService.selectAllUserFav2(userFav);

		modelMap.addAttribute("userFavs", userFavs);

		// 获取省
		List provinceList = districtService.selectDistrictByType("1");
		// 获取市
		List cityList = districtService.selectDistrictByType("2");
		// 获取区
		List areaList = districtService.selectDistrictByType("3");
		modelMap.addAttribute("provinceList", provinceList);
		modelMap.addAttribute("cityList", cityList);
		modelMap.addAttribute("areaList", areaList);

		return "mine/personal_setting";
	}

	/**
	 * 个人主页-个人设置
	 * 
	 * @Title: personalHome
	 * @Description: TODO
	 * @param request
	 * @param modelMap
	 * @return
	 * @return: String
	 * @author: zengmin
	 * @date: 2017年4月24日 上午11:23:11
	 */
	@RequestMapping("personal_home")
	public String personalHome(HttpServletRequest request, ModelMap modelMap) {
		Member me = ServletUtil.getMember(request);
		if (null == me) {
			return "login";
		}
		String userId = me.getId();
		if (StringUtils.isEmpty(userId)) {
			return "login";
		}
		WxUser user = wxUserService.selectByPrimaryKey(userId);
		modelMap.addAttribute("user", user);
		TjyUser tjyuser = tjyUserService.selectByPrimaryKey(userId);
		if (!StringUtils.isEmpty(tjyuser.getIndustry())) {
			ListValues sd = listValuesService.selectByPrimaryKey(tjyuser.getIndustry());
			tjyuser.setIndustry("");
			if (null != sd) {
				tjyuser.setIndustry(sd.getListValue());
			}
		}
		if (!StringUtils.isEmpty(tjyuser.getJob())) {
			ListValues sd = listValuesService.selectByPrimaryKey(tjyuser.getJob());
			tjyuser.setJob("");
			if (null != sd) {
				tjyuser.setJob(sd.getListValue());
			}
		}
		if (StringUtils.isEmpty(tjyuser.getVisitQuantity())) {
			tjyuser.setVisitQuantity(0.0);
		}

		if (!StringUtils.isEmpty(tjyuser.getProvince())) {
			SyDistrict sd = districtService.selectByPrimaryKey(tjyuser.getProvince());
			tjyuser.setProvince(sd.getDisName());
		}
		if (!StringUtils.isEmpty(tjyuser.getCity())) {
			SyDistrict sd = districtService.selectByPrimaryKey(tjyuser.getCity());
			tjyuser.setCity(sd.getDisName());
		}
		if (!StringUtils.isEmpty(tjyuser.getCounty())) {
			SyDistrict sd = districtService.selectByPrimaryKey(tjyuser.getCounty());
			tjyuser.setCounty(sd.getDisName());
		}
		OssConfig ossConfig = (OssConfig) SpringContextUtil.getBean("ossConfig");
		String ossurl = ossConfig.getOss_getUrl();
		modelMap.addAttribute("ossurl", ossurl);

		modelMap.addAttribute("user", user);
		modelMap.addAttribute("tjyuser", tjyuser);
		// 获取印象
		List<Map<String, Object>> userFriendimpresss0 = userFriendimpressService.selectcountByUserId(userId, 0);
		List<Map<String, Object>> userFriendimpresss1 = userFriendimpressService.selectByUserIdAndType(userId, 1);
		List<Map<String, Object>> userFriendimpresss2 = userFriendimpressService.selectByUserIdAndType(userId, 2);
		modelMap.addAttribute("userFriendimpresss0", userFriendimpresss0);
		modelMap.addAttribute("userFriendimpresss1", userFriendimpresss1);
		modelMap.addAttribute("userFriendimpresss2", userFriendimpresss2);

		return "mine/personal_home";
	}

	/**
	 * 他人访问个人主页
	 * 
	 * @Title: personalHome2
	 * @Description: TODO
	 * @param request
	 * @param modelMap
	 * @return
	 * @return: String
	 * @author: zengmin
	 * @date: 2017年4月24日 上午11:12:02
	 */
	@RequestMapping("personal_home2")
	public String personalHome2(String userId, HttpServletRequest request, ModelMap modelMap) {
		Member me = ServletUtil.getMember(request);
		if (null == me) {
			return "login";
		}
		if (StringUtils.isEmpty(userId)) {
			return "error";
		}

		String loginId = me.getId();
		WxUser user = wxUserService.selectByPrimaryKey(userId);
		modelMap.addAttribute("user", user);
		TjyUser tjyuser = tjyUserService.selectByPrimaryKey(userId);

		if (userId.equals(loginId)) {
			return personalHome(request, modelMap);
		} else {
			// 访问量+1
			double visitQuantity = 0.0;
			if (null == tjyuser.getVisitQuantity()) {
				visitQuantity = visitQuantity + 1;
			} else {
				visitQuantity = tjyuser.getVisitQuantity() + 1;
			}
			tjyuser.setVisitQuantity(visitQuantity);
			tjyUserService.updateTjyUser(tjyuser);
			// 更新最近访客
			UserLatestvistor ul = new UserLatestvistor();
			ul.setStatus(0);
			ul.setUserId(userId);
			ul.setVistorUserId(loginId);
			List<Map<String, Object>> uls = userLatestvistorService.selectAllUserLatestvistor(ul);
			if ((null == uls) || uls.size() <= 0) {
				ul.setCreateTime(new Date());
				ul.setUpdateTime(new Date());
				userLatestvistorService.addUserLatestvistor(ul);
			} else {
				UserLatestvistor ultemp = userLatestvistorService.selectByPrimaryKey((String) uls.get(0).get("id"));
				ultemp.setUpdateTime(new Date());
				userLatestvistorService.updateUserLatestvistor(ultemp);
			}
		}

		if (!StringUtils.isEmpty(tjyuser.getIndustry())) {
			ListValues sd = listValuesService.selectByPrimaryKey(tjyuser.getIndustry());
			tjyuser.setIndustry("");
			if (null != sd) {
				tjyuser.setIndustry(sd.getListValue());
			}
		}
		if (!StringUtils.isEmpty(tjyuser.getJob())) {
			ListValues sd = listValuesService.selectByPrimaryKey(tjyuser.getJob());
			tjyuser.setJob("");
			if (null != sd) {
				tjyuser.setJob(sd.getListValue());
			}
		}
		if (StringUtils.isEmpty(tjyuser.getVisitQuantity())) {
			tjyuser.setVisitQuantity(0.0);
		}

		if (!StringUtils.isEmpty(tjyuser.getProvince())) {
			SyDistrict sd = districtService.selectByPrimaryKey(tjyuser.getProvince());
			tjyuser.setProvince("");
			if (null != sd) {
				tjyuser.setProvince(sd.getDisName());
			}
		}
		if (!StringUtils.isEmpty(tjyuser.getCity())) {
			SyDistrict sd = districtService.selectByPrimaryKey(tjyuser.getCity());
			tjyuser.setCity("");
			if (null != sd) {
				tjyuser.setCity(sd.getDisName());
			}
		}
		if (!StringUtils.isEmpty(tjyuser.getCounty())) {
			SyDistrict sd = districtService.selectByPrimaryKey(tjyuser.getCounty());
			tjyuser.setCounty("");
			if (null != sd) {
				tjyuser.setCounty(sd.getDisName());
			}
		}
		OssConfig ossConfig = (OssConfig) SpringContextUtil.getBean("ossConfig");
		String ossurl = ossConfig.getOss_getUrl();
		modelMap.addAttribute("ossurl", ossurl);

		modelMap.addAttribute("user", user);
		modelMap.addAttribute("tjyuser", tjyuser);
		// 获取印象
		List<Map<String, Object>> userFriendimpresss0 = userFriendimpressService.selectcountByUserId(userId, 0);
		List<Map<String, Object>> userFriendimpresss1 = userFriendimpressService.selectByUserIdAndType(userId, 1);
		List<Map<String, Object>> userFriendimpresss2 = userFriendimpressService.selectByUserIdAndType(userId, 2);
		modelMap.addAttribute("userFriendimpresss0", userFriendimpresss0);
		modelMap.addAttribute("userFriendimpresss1", userFriendimpresss1);
		modelMap.addAttribute("userFriendimpresss2", userFriendimpresss2);

		/*
		 * boolean isMyFriend = false; isMyFriend =
		 * imFriendService.isMyFriend(userId, loginId);
		 * modelMap.addAttribute("isMyFriend", isMyFriend);
		 */
		/******** 是否是好友 ********/
		String isFriend = "0";
		ImFriend imfriend = imFriendService.findByUserAndFriend(loginId, userId);
		if (imfriend != null) {
			isFriend = "1";
		}
		modelMap.addAttribute("isFriend", isFriend);
		/******** 是否关注 ********/
		boolean isFollowUser = imFollowService.isFollowUser(loginId, userId);
		modelMap.addAttribute("isFollowUser", isFollowUser);
		return "mine/personal_home2";
	}

	/**
	 * 个人主页banner图添加
	 * 
	 * @return
	 */
	@RequestMapping("background_add")
	public ModelAndView background_add(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("mine/background_add");
		Comment comment = new Comment();
		mv.addObject("comment", comment);
		return mv;
	}

	/**
	 * 共同好友
	 * 
	 * @return
	 */
	@RequestMapping("commonfriend")
	public ModelAndView commonfriend(HttpServletRequest request, HttpServletResponse response) {
		String userId = request.getParameter("userId");
		ModelAndView mv = new ModelAndView();
		mv.addObject("userId", userId);
		mv.setViewName("mine/commonfriend");
		return mv;
	}

	/**
	 * 个人设置
	 * 
	 * @return
	 */
	@RequestMapping("personal_edit")
	public String personedit(HttpServletRequest request, ModelMap modelMap) {
		String flag = request.getParameter("flag");// 评论主题Id
		Member me = (Member) request.getSession().getAttribute("me");
		if (null == me) {
			return "login";
		}
		String userId = me.getId();
		if (StringUtils.isEmpty(userId)) {
			return "login";
		}
		Map<String, Object> user = wxUserService.queryUsersByid(userId);
		modelMap.addAttribute("flag", flag);
		modelMap.addAttribute("user", user);
		return "mine/personal_edit";
	}

	/**
	 * 个人设置
	 * 
	 * @return
	 */
	@RequestMapping("personal_edit2")
	public String personedit2(HttpServletRequest request, ModelMap modelMap) {
		String flag = request.getParameter("flag");// 评论主题Id
		Member me = (Member) request.getSession().getAttribute("me");
		if (null == me) {
			return "login";
		}
		String userId = me.getId();
		if (StringUtils.isEmpty(userId)) {
			return "login";
		}
		Map<String, Object> user = wxUserService.queryUsersByid(userId);
		modelMap.addAttribute("flag", flag);
		modelMap.addAttribute("user", user);
		return "mine/personal_edit2";
	}

	/**
	 * 加好友
	 * 
	 * @return
	 */
	@RequestMapping("add_friend")
	public String addFriend(HttpServletRequest request, ModelMap modelMap) {
		String userId = request.getParameter("userId");// 评论主题Id

		modelMap.addAttribute("userId", userId);
		return "mine/add_friend";
	}

	/**
	 * 打招呼
	 * 
	 * @return
	 */
	@RequestMapping("say_hello")
	public String sayHello(HttpServletRequest request, ModelMap modelMap) {
		String userId = request.getParameter("userId");// 被打招呼Id

		modelMap.addAttribute("userId", userId);
		return "mine/say_hello";
	}

	/**
	 * 打招呼回复
	 * 
	 * @return
	 */
	@RequestMapping("answer_hello")
	public String answerHello(HttpServletRequest request, ModelMap modelMap) {
		String ugId = request.getParameter("ugId");// 打招呼记录Id

		modelMap.addAttribute("ugId", ugId);
		UserGreetings userGreetings = userGreetingsService.selectByPrimaryKey(ugId);
		modelMap.addAttribute("userGreetings", userGreetings);
		return "mine/answer_hello";
	}

	/**
	 * 打招呼回复
	 * 
	 * @return
	 */
	@RequestMapping("answer_hello2")
	public String answerHello2(HttpServletRequest request, ModelMap modelMap) {
		String ugId = request.getParameter("ugId");// 打招呼记录Id

		modelMap.addAttribute("ugId", ugId);
		UserGreetings userGreetings = userGreetingsService.selectByPrimaryKey(ugId);
		modelMap.addAttribute("userGreetings", userGreetings);
		return "mine/answer_hello2";
	}

	/**
	 * 是否打过招呼但没有回复
	 * 
	 * @return
	 */
	@RequestMapping("is_noanswer")
	public @ResponseBody Map isNoanswer(HttpServletRequest request, String followUser) throws IOException {
		String userId = super.checkLogin(request);
		if (userId == null || "".equals(userId)) {
			return super.getAjaxResult(Constants.AJAX_CODE_ERROR_NOTLOGIN, "未登录，请重新登录！", "");
		}
		if (StringUtil.isEmpty(followUser)) {
			return super.getAjaxResult(Constants.AJAX_CODE_ERROR_PARAM, "参数无效，请检查！", "");
		} else {
			try {
				String resultStr = "0";
				UserGreetings userGreetings = new UserGreetings();
				userGreetings.setUserId(userId);
				userGreetings.setFriendUser(followUser);
				userGreetings.setAnswerstatus("0");
				List ugList = userGreetingsService.selectUserGreetings(userGreetings);
				if (ugList.size() > 0) {
					resultStr = "1";// 末回复
				}
				int countOneday = userGreetingsService.getCountOneDay(userId);// 当日打招呼数量
				WxUser user = wxUserService.selectById(userId);
				String level = "LV1";
				if (null != user.getLevel()) {
					level = user.getLevel();
				}
				UserEmpirical userEmpirical = userEmpiricalService.selectBylevel(level);
				int greetingsCount = 0;
				if (null != userEmpirical.getGreetingsCount()) {
					greetingsCount = userEmpirical.getGreetingsCount();
				}
				if (countOneday >= greetingsCount) {
					resultStr = "2";// 超过今日打招呼的数量
				}

				//
				return super.getSuccessAjaxResult("获取成功！", resultStr);
			} catch (Exception e) {
				return super.getAjaxResult(Constants.AJAX_CODE_ERROR_INFO, e.getMessage(), "");
			}
		}
	}
	
	/**
	 * 是否打过招呼但没有回复
	 * 
	 * @return
	 */
	@RequestMapping("is_address")
	public @ResponseBody Map is_address(HttpServletRequest request) throws IOException {
		String userId = super.checkLogin(request);
		if (userId == null || "".equals(userId)) {
			return super.getAjaxResult(Constants.AJAX_CODE_ERROR_NOTLOGIN, "未登录，请重新登录！", "");
		}
		try {
			String resultStr = "0";
			TjyUser tjyuser = tjyUserService.selectByPrimaryKey(userId);
		    String city = tjyuser.getCity();
		    if(null == city || "".equals(city)){
		    	resultStr = "0";
		    }else{
		    	resultStr = "1";// 存在地址
		    }
			return super.getSuccessAjaxResult("获取成功！", resultStr);
		} catch (Exception e) {
			return super.getAjaxResult(Constants.AJAX_CODE_ERROR_INFO, e.getMessage(), "");
		}
	}
	
	/**
	 * 提示完善个人信息
	 * 
	 * @return
	 */
	@RequestMapping("add_address")
	public String add_address(HttpServletRequest request, ModelMap modelMap) {
		//String userId = request.getParameter("userId");// 被打招呼Id

		//modelMap.addAttribute("userId", userId);
		return "mine/add_address";
	}
	/**
	 * 设置
	 * 
	 * @return
	 */
	@RequestMapping("my_setting")
	public String my_setting(HttpServletRequest request, ModelMap modelMap) {
		
		return "mine/my_setting";
	}
	/**
	 * 免打扰设置
	 * 
	 * @return
	 */
	@RequestMapping("my_disturb_set")
	public String my_disturb_set(HttpServletRequest request, ModelMap modelMap) {
		//String userId = request.getParameter("userId");// 被打招呼Id
		String userId = super.checkLogin(request);
		if (StringUtils.isEmpty(userId)) {
			return "login";
		}
		TjyUser tjyuser = tjyUserService.selectByPrimaryKey(userId);
			
		modelMap.addAttribute("tjyuser", tjyuser);
		return "mine/my_disturb_set";
	}
	
	/**
	 * 设置免打扰
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("UpdateIsdisturb")
	public  @ResponseBody Map UpdateIsdisturb(HttpServletRequest request, String isdisturb) throws IOException{
	
		String userId =  super.checkLogin(request);
		if(userId == null || "".equals(userId))	{
			return super.getAjaxResult(Constants.AJAX_CODE_ERROR_NOTLOGIN, "未登录，请重新登录！", "");
		}	
		if(isdisturb == null || "".equals(isdisturb))	{
			return super.getAjaxResult(Constants.AJAX_CODE_ERROR_PARAM, "参数错误！", "");
		}	
        try{
        	TjyUser tjyuser = tjyUserService.selectByPrimaryKey(userId);
        	tjyuser.setIsdisturb(Integer.parseInt(isdisturb));
        	Boolean resultStr = tjyUserService.updateTjyUser(tjyuser);
			String str = "设置成功！";
			if("0".equals(isdisturb)){
				str = "成功取消免打扰！";
			}
			if("1".equals(isdisturb)){
				str = "成功设置免打扰！";
			}
			return super.getSuccessAjaxResult(str, resultStr);
		}catch(Exception e){
			return super.getAjaxResult(Constants.AJAX_CODE_ERROR_INFO, e.getMessage(), "");
		}
			
		}
		
	

	/**
	 * 积分和经验值
	 * 
	 * @return
	 */
	@RequestMapping("addLntAndEmp")
	public @ResponseBody Map addLntAndEmp(HttpServletRequest request, String taskId) throws IOException {
		String userId = super.checkLogin(request);
		if (userId == null || "".equals(userId)) {
			return super.getAjaxResult(Constants.AJAX_CODE_ERROR_NOTLOGIN, "未登录，请重新登录！", "");
		}
		if (StringUtil.isEmpty(taskId)) {
			return super.getAjaxResult(Constants.AJAX_CODE_ERROR_PARAM, "参数无效，请检查！", "");
		} else {
			try {
				boolean resultStr = true;
				// 送积分 经验值
				userIntegralLogService.addLntAndEmp(userId, taskId);
				return super.getSuccessAjaxResult("获取成功！", resultStr);
			} catch (Exception e) {
				return super.getAjaxResult(Constants.AJAX_CODE_ERROR_INFO, e.getMessage(), "");
			}
		}
	}

	/**
	 * 个人爱好
	 * 
	 * @return
	 */
	@RequestMapping("personal_fav")
	public String personalFav(HttpServletRequest request, ModelMap modelMap) {
		Member me = (Member) request.getSession().getAttribute("me");
		if (null == me) {
			return "login";
		}
		String userId = me.getId();
		if (StringUtils.isEmpty(userId)) {
			return "login";
		}
		// 获取
		List<Map<String, Object>> favList = listValuesService.selectListByType(8002);

		UserFav userFav = new UserFav();
		userFav.setUserId(userId);
		List<Map<String, Object>> userFavs = userFavService.selectAllUserFav(userFav);
		StringBuilder result = new StringBuilder();
		boolean flag = false;
		for (Map<String, Object> m : userFavs) {
			if (flag) {
				result.append(",");
			} else {
				flag = true;
			}
			result.append((String) m.get("favId"));
		}

		for (Map<String, Object> m : favList) {
			if (result.toString().indexOf((String) m.get("id")) != -1) {
				m.put("is_content", 1);
			} else {
				m.put("is_content", 0);
			}
		}
		modelMap.addAttribute("favList", favList);

		return "mine/personal_fav";
	}

	/**
	 * 好友印象
	 * 
	 * @return
	 */
	@RequestMapping("personal_impress")
	public String personal_impress(HttpServletRequest request, ModelMap modelMap) {
		String userId = request.getParameter("userId");
		Member me = ServletUtil.getMember(request);
		if (null == me) {
			/// return "login";
		}
		String loginId = me.getId();
		if (StringUtils.isEmpty(userId)) {
			/// return "login";
		}
		Integer type = Integer.valueOf(request.getParameter("type"));
		if (type == 0) {
			// 获取朋友印象库
			List<Map<String, Object>> impressList = listValuesService.selectListByType(8003);
			modelMap.addAttribute("impressList", impressList);
		} else if (type == 1) {
			// 获取朋友印象库
			List<Map<String, Object>> impressList = listValuesService.selectListByType(8004);
			modelMap.addAttribute("impressList", impressList);
		} else if (type == 2) {
			// 获取朋友印象库
			List<Map<String, Object>> impressList = listValuesService.selectListByType(8005);
			modelMap.addAttribute("impressList", impressList);
		}

		if (type == 1 || type == 2) {
			List<Map<String, Object>> userFriendimpresss = userFriendimpressService.selectByUserIdAndType(userId, type);
			modelMap.addAttribute("userFriendimpresss", userFriendimpresss);
			modelMap.addAttribute("type", type);
		} else if (type == 0) {
			List<Map<String, Object>> userFriendimpresss = userFriendimpressService.selectByUserIdAndType(loginId,
					userId, type);
			modelMap.addAttribute("userFriendimpresss", userFriendimpresss);
			modelMap.addAttribute("type", type);
			modelMap.addAttribute("userId", userId);
		}

		return "mine/personal_impress";
	}

	/**
	 * 好友印象
	 * 
	 * @return
	 */
	@RequestMapping("personal_impress2")
	public String personal_impress2(HttpServletRequest request, ModelMap modelMap) {
		String userId = request.getParameter("userId");
		Member me = ServletUtil.getMember(request);
		if (null == me) {
			/// return "login";
		}
		String loginId = me.getId();
		if (StringUtils.isEmpty(userId)) {
			/// return "login";
		}
		Integer type = Integer.valueOf(request.getParameter("type"));
		if (type == 0) {
			// 获取朋友印象库
			List<Map<String, Object>> impressList = listValuesService.selectListByType(8003);
			modelMap.addAttribute("impressList", impressList);
		} else if (type == 1) {
			// 获取朋友印象库
			List<Map<String, Object>> impressList = listValuesService.selectListByType(8004);
			modelMap.addAttribute("impressList", impressList);
		} else if (type == 2) {
			// 获取朋友印象库
			List<Map<String, Object>> impressList = listValuesService.selectListByType(8005);
			modelMap.addAttribute("impressList", impressList);
		}

		if (type == 1 || type == 2) {
			List<Map<String, Object>> userFriendimpresss = userFriendimpressService.selectByUserIdAndType(loginId,
					type);
			modelMap.addAttribute("userFriendimpresss", userFriendimpresss);
			modelMap.addAttribute("type", type);
		} else if (type == 0) {
			List<Map<String, Object>> userFriendimpresss = userFriendimpressService.selectByUserIdAndType(loginId,
					userId, type);
			modelMap.addAttribute("userFriendimpresss", userFriendimpresss);
			modelMap.addAttribute("type", type);
			modelMap.addAttribute("userId", userId);
		}

		return "mine/personal_impress2";
	}

	/**
	 * 添加到user
	 * 
	 */
	@RequestMapping(value = "/add/addusers")
	public @ResponseBody Map addusers(HttpServletRequest request, HttpServletResponse response) throws IOException {
		/// request.setCharacterEncoding("UTF-8");
		Member me = ServletUtil.getMember(request);
		if (null == me) {
			return super.getAjaxResult("302", "登录超时，请重新登录", null);
		}
		String userId = me.getId();
		if (StringUtils.isEmpty(userId)) {
			return super.getAjaxResult("302", "登录超时，请重新登录", null);
		}

		TjyUser tjyuser = tjyUserService.selectByPrimaryKey(userId);
		WxUser user = wxUserService.selectByPrimaryKey(userId);
		String flag_temp = request.getParameter("flag");
		String nickname = request.getParameter("nickname");
		if (!StringUtils.isEmpty(nickname)) {
			user.setTruename(nickname);
			tjyuser.setNickname(nickname);
			tjyuser.setTrueName(nickname);
		}
		String mobile = request.getParameter("mobile");
		if (!StringUtils.isEmpty(mobile)) {
			user.setMobile(mobile);
		}
		String user_profile = request.getParameter("user_profile");
		if ("3".equals(flag_temp)) {
			tjyuser.setUserProfile(user_profile);
		}

		String user_signature = request.getParameter("user_signature");
		if ("4".equals(flag_temp)) {
			tjyuser.setUserSignature(user_signature);
		}
		String sex = request.getParameter("sex");
		if (!StringUtils.isEmpty(sex)) {
			user.setSex(Integer.parseInt(sex));
		}
		String province = request.getParameter("province");
		if (!StringUtils.isEmpty(province)) {
			tjyuser.setProvince(province);
		}
		String city = request.getParameter("city");
		if (!StringUtils.isEmpty(city)) {
			tjyuser.setCity(city);
		}
		String county = request.getParameter("county");
		if (!StringUtils.isEmpty(county)) {
			tjyuser.setCounty(county);
		}
		String birthday = request.getParameter("birthday");
		if (!StringUtils.isEmpty(birthday)) {
			user.setBirthday(DateUtils.stringToDate(birthday, "yyyy-MM-dd"));
		}
		if (!StringUtils.isEmpty(user.getSex()) || !StringUtils.isEmpty(user.getBirthday())
				|| !StringUtils.isEmpty(tjyuser.getUserProfile()) || !StringUtils.isEmpty(tjyuser.getCity())) {
			// 此项积分按照完成度百分百获得积分，终身一次
			userIntegralLogService.addLntAndEmp(tjyuser.getId(), "task_0019");
		}

		
		WxUser user2 = wxUserService.selectByPrimaryKey(userId);
		user2.setTruename(user.getTruename());
		user2.setMobile(user.getMobile());
		user2.setSex(user.getSex());
		user2.setBirthday(user.getBirthday());
		String homepagePic = request.getParameter("homepagePic");
		if (!StringUtils.isEmpty(homepagePic)) {
			tjyuser.setHomepagePic(homepagePic);
		}
		String favs = request.getParameter("favs");
		String isfav = request.getParameter("isfav");
		if (!StringUtils.isEmpty(isfav) && StringUtils.isEmpty(favs)) {
			// 删除原来的爱好
			UserFav userFav = new UserFav();
			userFav.setUserId(userId);
			List<Map<String, Object>> userFavs = userFavService.selectAllUserFav(userFav);
			StringBuilder result = new StringBuilder();
			boolean flag = false;
			for (Map<String, Object> m : userFavs) {
				if (flag) {
					result.append(",");
				} else {
					flag = true;
				}
				result.append((String) m.get("id"));
			}
			// 直接初始化数组
			String[] arr = { result.toString() };
			userFavService.deleteUserFavs(arr);
		} else if (!StringUtils.isEmpty(favs)) {
			String[] strArray = null;
			strArray = favs.split(",");
			// 先删除原来的爱好
			UserFav userFav = new UserFav();
			userFav.setUserId(userId);
			List<Map<String, Object>> userFavs = userFavService.selectAllUserFav(userFav);
			StringBuilder result = new StringBuilder();
			boolean flag = false;
			for (Map<String, Object> m : userFavs) {
				if (flag) {
					result.append(",");
				} else {
					flag = true;
				}
				result.append((String) m.get("id"));
			}
			// 直接初始化数组
			String[] arr = { result.toString() };
			userFavService.deleteUserFavs(arr);
			for (String m : strArray) {
				UserFav uf = new UserFav();
				uf.setUserId(userId);
				uf.setFavId(m);
				userFavService.addUserFav(uf);
			}
		}

		if (wxUserService.updateWxUser(user2) && tjyUserService.updateTjyUser(tjyuser)) {
			return super.getSuccessAjaxResult();
		}

		return super.getSuccessAjaxResult(Constants.AJAX_MSG_ERROR, null);
	}

	/**
	 * 我需要的，我能提供的
	 * 
	 */
	@RequestMapping(value = "/addusersImpress")
	public @ResponseBody Map addusersImpress(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		Member me = ServletUtil.getMember(request);
		if (null == me) {
			return super.getAjaxResult("302", "登录超时，请重新登录", null);
		}
		String loginId = me.getId();
		if (StringUtils.isEmpty(loginId)) {
			return super.getAjaxResult("302", "登录超时，请重新登录", null);
		}

		// 个人印象
		String impress = request.getParameter("impress");
		String type = request.getParameter("type");// 印象类型：0：朋友印象 1：我需要 2:我能提供的
		UserFriendimpress userFriendimpress = new UserFriendimpress();
		userFriendimpress.setUserId(loginId);
		// userFriendimpress.setCreateUserId(loginId);
		userFriendimpress.setType(Integer.parseInt(type));
		List<Map<String, Object>> userFriendimpresss = userFriendimpressService
				.selectAllUserFriendimpress(userFriendimpress);
		StringBuilder result = new StringBuilder();
		boolean flag = false;
		for (Map<String, Object> m : userFriendimpresss) {
			if (flag) {
				result.append(",");
			} else {
				flag = true;
			}
			result.append((String) m.get("id"));
		}
		// 直接初始化数组
		String[] arr = { result.toString() };
		// 先删除原来的我需要的，我能提供的
		userFriendimpressService.deleteUserFriendimpress(arr);

		if (!StringUtils.isEmpty(impress)) {
			String[] strArray = null;
			strArray = impress.split(",");
			if ("1".equals(type) || "2".equals(type)) {
				for (String m : strArray) {
					UserFriendimpress uf = new UserFriendimpress();
					uf.setUserId(loginId);
					uf.setImpressId(m);
					uf.setType(Integer.parseInt(type));
					uf.setCreateUserId(loginId);
					userFriendimpressService.addUserFriendimpress(uf);
				}
			}
		}
		return super.getSuccessAjaxResult();
	}

	/**
	 * 好友印象
	 * 
	 */
	@RequestMapping(value = "/addusersImpress0")
	public @ResponseBody Map addusersImpress0(HttpServletRequest request, HttpServletResponse response)
			throws IOException {

		String userId = request.getParameter("userId");
		if (StringUtils.isEmpty(userId)) {
			return super.getAjaxResult("401", "参数错误", null);
		}
		Member me = ServletUtil.getMember(request);
		if (null == me) {
			return super.getAjaxResult("302", "登录超时，请重新登录", null);
		}
		String loginId = me.getId();
		if (StringUtils.isEmpty(loginId)) {
			return super.getAjaxResult("302", "登录超时，请重新登录", null);
		}

		// 个人印象
		String impress = request.getParameter("impress");
		String type = request.getParameter("type");// 印象类型：0：朋友印象 1：我需要 2:我能提供的

		if (!StringUtils.isEmpty(impress)) {
			String[] strArray = null;
			strArray = impress.split(",");
			UserFriendimpress userFriendimpress = new UserFriendimpress();
			userFriendimpress.setUserId(userId);
			userFriendimpress.setCreateUserId(loginId);
			userFriendimpress.setType(Integer.parseInt(type));
			List<Map<String, Object>> userFriendimpresss = userFriendimpressService
					.selectAllUserFriendimpress(userFriendimpress);
			if ("0".equals(type)) {
				StringBuilder result = new StringBuilder();
				boolean flag = false;
				for (Map<String, Object> m : userFriendimpresss) {
					if (flag) {
						result.append(",");
					} else {
						flag = true;
					}
					result.append((String) m.get("id"));
				}
				// 直接初始化数组
				String[] arr = { result.toString() };
				// 先删除原来的印像
				// userFriendimpressService.deleteUserFriendimpress(arr);
				for (String m : strArray) {
					UserFriendimpress uf = new UserFriendimpress();
					uf.setUserId(userId);
					uf.setImpressId(m);
					uf.setType(Integer.parseInt(type));
					uf.setCreateUserId(loginId);
					List<Map<String, Object>> fiList = userFriendimpressService.selectAllUserFriendimpress(uf);
					if (fiList.size() == 0) {
						userFriendimpressService.addUserFriendimpress(uf);
					}
				}
			}
		}
		return super.getSuccessAjaxResult();
	}

	/**
	 * 好友印象
	 * 
	 */
	@RequestMapping(value = "/removeusersImpress")
	public @ResponseBody Map removeusersImpress(HttpServletRequest request, HttpServletResponse response)
			throws IOException {

		Member me = ServletUtil.getMember(request);
		if (null == me) {
			return super.getAjaxResult("302", "登录超时，请重新登录", null);
		}
		String loginId = me.getId();
		if (StringUtils.isEmpty(loginId)) {
			return super.getAjaxResult("302", "登录超时，请重新登录", null);
		}

		// 个人印象
		String impress = request.getParameter("impress");
		String type = request.getParameter("type");// 印象类型：0：朋友印象 1：我需要 2:我能提供的

		if (!StringUtils.isEmpty(impress)) {
			String[] strArray = null;
			strArray = impress.split(",");
			if ("0".equals(type)) {
				for (String m : strArray) {
					UserFriendimpress uf = new UserFriendimpress();
					uf.setImpressId(m);
					uf.setType(Integer.parseInt(type));
					List<Map<String, Object>> userFriendimpresss = userFriendimpressService
							.selectAllUserFriendimpress(uf);
					StringBuilder result = new StringBuilder();
					boolean flag = false;
					for (Map<String, Object> m0 : userFriendimpresss) {
						if (flag) {
							result.append(",");
						} else {
							flag = true;
						}
						result.append((String) m0.get("id"));
					}
					// 直接初始化数组
					String[] arr = { result.toString() };
					// 先删除原来的爱好
					userFriendimpressService.deleteUserFriendimpress(arr);
				}
			}
		}
		return super.getSuccessAjaxResult();
	}

	/**
	 * 打招呼
	 * 
	 */
	@RequestMapping(value = "/addusersgreetings")
	public @ResponseBody Map addusersgreetings(HttpServletRequest request, HttpServletResponse response)
			throws IOException {

		String friendUser = request.getParameter("friendUser");
		if (StringUtils.isEmpty(friendUser)) {
			return super.getAjaxResult("401", "参数错误", null);
		}
		Member me = ServletUtil.getMember(request);
		if (null == me) {
			return super.getAjaxResult("302", "登录超时，请重新登录", null);
		}
		String userId = me.getId();
		if (StringUtils.isEmpty(userId)) {
			return super.getAjaxResult("302", "登录超时，请重新登录", null);
		}

		// 打招呼
		String askmessage = request.getParameter("askmessage");

		if (!StringUtils.isEmpty(askmessage)) {
			UserGreetings userGreetings = new UserGreetings();
			userGreetings.setUserId(userId);
			userGreetings.setStatus(0);
			userGreetings.setFriendUser(friendUser);
			userGreetings.setAskmessage(askmessage);
			userGreetings.setCreateTime(new Date());
			userGreetings.setAnswerstatus("0");
			userGreetingsService.addUserGreetings(userGreetings);
		}
		return super.getSuccessAjaxResult();
	}

	/**
	 * 打招呼
	 * 
	 */
	@RequestMapping(value = "/answerusersgreetings")
	public @ResponseBody Map answerusersgreetings(HttpServletRequest request, HttpServletResponse response)
			throws IOException {

		String ugId = request.getParameter("ugId");
		UserGreetings userGreetings = userGreetingsService.selectByPrimaryKey(ugId);

		// 回复打招呼
		String answermessage = request.getParameter("answermessage");
		if (!StringUtils.isEmpty(answermessage)) {
			userGreetings.setAnswermessage(answermessage);
			userGreetings.setAnswerstatus("1");
			userGreetingsService.updateUserGreetings(userGreetings);
		}
		// 创建一个新的招呼
		UserGreetings u = new UserGreetings();
		u.setUserId(userGreetings.getFriendUser());
		u.setFriendUser(userGreetings.getUserId());
		u.setStatus(0);
		u.setAskmessage(answermessage);
		u.setCreateTime(new Date());
		userGreetingsService.addUserGreetings(u);
		return super.getSuccessAjaxResult();
	}

	/**
	 * 收到的招呼
	 * 
	 */
	@RequestMapping(value = "/getUsersGreetings")
	public @ResponseBody Map getUsersGreetings(HttpServletRequest request, HttpServletResponse response)
			throws IOException {

		Member me = ServletUtil.getMember(request);
		if (null == me) {
			return super.getAjaxResult("302", "登录超时，请重新登录", null);
		}
		String userId = me.getId();
		if (StringUtils.isEmpty(userId)) {
			return super.getAjaxResult("302", "登录超时，请重新登录", null);
		}

		UserGreetings userGreetings = new UserGreetings();
		userGreetings.setFriendUser(userId);
		List userGreetingsList = userGreetingsService.selectAllUserGreetings(userGreetings);
		Object dataobj = userGreetingsList;
		return super.getSuccessAjaxResult("SUCCESS", dataobj);
	}

	/**
	 * 收到的招呼
	 * 
	 */
	@RequestMapping(value = "/sendUsersGreetings")
	public @ResponseBody Map sendUsersGreetings(HttpServletRequest request, HttpServletResponse response)
			throws IOException {

		Member me = ServletUtil.getMember(request);
		if (null == me) {
			return super.getAjaxResult("302", "登录超时，请重新登录", null);
		}
		String userId = me.getId();
		if (StringUtils.isEmpty(userId)) {
			return super.getAjaxResult("302", "登录超时，请重新登录", null);
		}

		UserGreetings userGreetings = new UserGreetings();
		userGreetings.setUserId(userId);
		List userGreetingsList = userGreetingsService.selectAllUserGreetings(userGreetings);
		Object dataobj = userGreetingsList;
		return super.getSuccessAjaxResult("SUCCESS", dataobj);
	}

	/**
	 * 我的钱包
	 * 
	 * @return
	 */
	@RequestMapping("personal_wallet")
	public String personalWallet(HttpServletRequest request, ModelMap modelMap) {
		Member me = ServletUtil.getMember(request);
		if (null == me) {
			return "login";
		}
		String userId = me.getId();
		if (StringUtils.isEmpty(userId)) {
			return "login";
		}
		WxUser user = wxUserService.selectByPrimaryKey(userId);
		modelMap.addAttribute("user", user);
		List<HzbManagerLog> logList = hzbManagerLogService.selectByUserId(me.getId());
		double ljcz = 0, ljxf = 0;
		if (null != logList && !logList.isEmpty()) {
			for (HzbManagerLog hzbManagerLog : logList) {
				double money = null == hzbManagerLog.getManagerMoney() ? 0 : hzbManagerLog.getManagerMoney();
				if (hzbManagerLog.getType() == 4 || hzbManagerLog.getType() == 7) {
					ljcz += money;
				} else if (hzbManagerLog.getType() == 8) {
					ljxf += money;
				}
			}
		}
		// 可用优惠券数量
		List<Map<String, Object>> list = new ArrayList();
		list = couponLogService.selMyCouponList(userId, 1, null, null);
		modelMap.addAttribute("ccount", list.size());
		modelMap.addAttribute("ljcz", ljcz);
		modelMap.addAttribute("ljxf", ljxf);
		return "mine/personal_wallet";
	}

	/**
	 * 钱包明细
	 * 
	 * @Title: walletLog
	 * @Description: TODO
	 * @param type
	 * @param request
	 * @param modelMap
	 * @return
	 * @return: String
	 * @author: zengmin
	 * @date: 2017年4月14日 下午3:35:03
	 */
	@RequestMapping("wallet_log")
	public String walletLog(String type, HttpServletRequest request, ModelMap modelMap) {
		Member me = ServletUtil.getMember(request);
		if (null == me) {
			return "login";
		}
		String userId = me.getId();
		if (StringUtils.isEmpty(userId)) {
			return "login";
		}
		// List<Map<String, Object>> logList =
		// walletLogService.selectByUserIdAndTypeAndStatus(me.getId(), type,
		// null);
		modelMap.addAttribute("type", type);
		// modelMap.addAttribute("logList", logList);
		return "mine/wallet_log";
	}

	/**
	 * 钱包明细
	 * 
	 * @Title: walletLogAjax
	 * @Description: TODO
	 * @param type
	 * @param request
	 * @param modelMap
	 * @return
	 * @return: Map
	 * @author: zengmin
	 * @date: 2017年4月14日 下午3:35:03
	 */
	@RequestMapping("wallet_log_ajax")
	public @ResponseBody Map walletLogAjax(String type, HttpServletRequest request) {
		Member me = ServletUtil.getMember(request);
		if (null == me) {
			return super.getAjaxResult("302", "登录超时", null);
		}
		String userId = me.getId();
		if (StringUtils.isEmpty(userId)) {
			return super.getAjaxResult("302", "登录超时", null);
		}
		List<Map<String, Object>> logList = walletLogService.selectByUserIdAndTypeAndStatus(me.getId(), type, null);
		return super.getAjaxResult("0", "获取成功", logList);
	}

	/**
	 * 钱包明细分页查询
	 * 
	 * @Title: wallet_log_ajax_page
	 * @Description: TODO
	 * @param type
	 * @param request
	 * @param modelMap
	 * @return
	 * @return: Map
	 * @author: zengmin
	 * @date: 2017年4月14日 下午3:35:03
	 */
	@RequestMapping("wallet_log_ajax_page")
	public @ResponseBody Map walletLogAjaxPage(String userId, String type, int pageNum, int pageSize,
			HttpServletRequest request) {
		if (StringUtils.isEmpty(userId)) {
			Member me = ServletUtil.getMember(request);
			if (null == me) {
				return super.getAjaxResult("302", "登录超时", null);
			}
			userId = me.getId();
			if (StringUtils.isEmpty(userId)) {
				return super.getAjaxResult("302", "登录超时", null);
			}
		}
		if (pageSize == 0) {
			pageSize = 10;
		}
		if (pageNum == 0) {
			pageNum = 1;
		}
		List<Map<String, Object>> logList = walletLogService.selectWalletLogPageByUserIdAndType(userId, type, pageNum,
				pageSize);
		return super.getAjaxResult("0", "获取成功", logList);
	}

	/**
	 * 互助宝明细
	 * 
	 * @Title: hzbLog
	 * @Description: TODO
	 * @param type
	 * @param request
	 * @param modelMap
	 * @return
	 * @return: String
	 * @author: zengmin
	 * @date: 2017年4月14日 下午3:35:03
	 */
	@RequestMapping("hzb_log")
	public String hzbLog(String type, HttpServletRequest request, ModelMap modelMap) {
		Member me = ServletUtil.getMember(request);
		if (null == me) {
			return "login";
		}
		String userId = me.getId();
		if (StringUtils.isEmpty(userId)) {
			return "login";
		}
		// List<Map<String, Object>> logList =
		// walletLogService.selectByUserIdAndTypeAndStatus(me.getId(), type,
		// null);
		modelMap.addAttribute("type", type);
		// modelMap.addAttribute("logList", logList);
		return "mine/hzb_log";
	}

	/**
	 * 积分明细
	 * 
	 * @Title: hzbLog
	 * @Description: TODO
	 * @param type
	 * @param request
	 * @param modelMap
	 * @return
	 * @return: String
	 * @author: zengmin
	 * @date: 2017年4月14日 下午3:35:03
	 */
	@RequestMapping("integral_log")
	public String integral_log(HttpServletRequest request, ModelMap modelMap) {
		Member me = ServletUtil.getMember(request);
		if (null == me) {
			return "login";
		}
		String userId = me.getId();
		if (StringUtils.isEmpty(userId)) {
			return "login";
		}
		return "mine/integral_log";
	}

	/**
	 * 互助宝明细分页查询
	 * 
	 * @Title: hzb_log_ajax_page
	 * @Description: TODO
	 * @param type
	 * @param request
	 * @param modelMap
	 * @return
	 * @return: Map
	 * @author: zengmin
	 * @date: 2017年4月14日 下午3:35:03
	 */
	@RequestMapping("hzb_log_ajax_page")
	public @ResponseBody Map hzbLogAjaxPage(String userId, int pageNum, int pageSize, HttpServletRequest request) {
		if (StringUtils.isEmpty(userId)) {
			Member me = ServletUtil.getMember(request);
			if (null == me) {
				return super.getAjaxResult("302", "登录超时", null);
			}
			userId = me.getId();
			if (StringUtils.isEmpty(userId)) {
				return super.getAjaxResult("302", "登录超时", null);
			}
		}
		if (pageSize == 0) {
			pageSize = 10;
		}
		if (pageNum == 0) {
			pageNum = 1;
		}
		List<Map<String, Object>> logList = hzbManagerLogService.selectHzbLogPageByUserId(userId, pageNum, pageSize);
		return super.getAjaxResult("0", "获取成功", logList);
	}

	/**
	 * 积分明细分页查询
	 * 
	 * @Title: integral_log_ajax_page
	 * @Description: TODO
	 * @param type
	 * @param request
	 * @param modelMap
	 * @return
	 * @return: Map
	 * @author: zengmin
	 * @date: 2017年4月14日 下午3:35:03
	 */
	@RequestMapping("integral_log_ajax_page")
	public @ResponseBody Map integralLogAjaxPage(String userId, int pageNum, int pageSize, HttpServletRequest request) {
		if (StringUtils.isEmpty(userId)) {
			Member me = ServletUtil.getMember(request);
			if (null == me) {
				return super.getAjaxResult("302", "登录超时", null);
			}
			userId = me.getId();
			if (StringUtils.isEmpty(userId)) {
				return super.getAjaxResult("302", "登录超时", null);
			}
		}
		if (pageSize == 0) {
			pageSize = 10;
		}
		if (pageNum == 0) {
			pageNum = 1;
		}
		List<Map<String, Object>> logList = userIntegralLogService.selectIntegralLogPageByUserId(userId, pageNum,
				pageSize);
		return super.getAjaxResult("0", "获取成功", logList);
	}

	/**
	 * 我的活动
	 * 
	 * @return
	 */
	@RequestMapping("myfootPrint_activity")
	public String myfootPrintActivity(HttpServletRequest request, ModelMap modelMap) {
		return "mine/myfootPrint_activity";
	}

	/**
	 * 我的活题
	 * 
	 * @return
	 */
	@RequestMapping("myfootPrint_topic")
	public String myfootPrintTopic(HttpServletRequest request, ModelMap modelMap) {
		return "mine/myfootPrint_topic";
	}

	/**
	 * 我的项目
	 * 
	 * @return
	 */
	@RequestMapping("myfootPrint_project")
	public String myfootPrintProject(HttpServletRequest request, ModelMap modelMap) {
		String tab = request.getParameter("tab") == null ? "1" : request.getParameter("tab");
		tab = ("1".equals(tab) || "2".equals(tab) || "3".equals(tab)) ? tab : "1";
		modelMap.addAttribute("tab", Integer.valueOf(tab));
		return "mine/myfootPrint_project";// 却jsp
	}

	/**
	 * 我的评价
	 * 
	 * @return
	 */
	@RequestMapping("myfootPrint_comment")
	public String myfootPrintComment(HttpServletRequest request, ModelMap modelMap) {
		return "mine/myfootPrint_comment";
	}

	/**
	 * 我的文库
	 * 
	 * @return
	 */
	@RequestMapping("myfootPrint_textHouseware")
	public String myfootPrintTextHouseware(HttpServletRequest request, ModelMap modelMap) {
		return "mine/myfootPrint_textHouseware";
	}

	/**
	 * 我的会议
	 * 
	 * @return
	 */
	@RequestMapping("myfootPrint_meeting")
	public String myfootPrintMeeting(HttpServletRequest request, ModelMap modelMap) {
		return "mine/myfootPrint_meeting";
	}

	/**
	 * 钱包充值选择方式选择页面
	 * 
	 * @Title: walletCz
	 * @Description: TODO
	 * @param request
	 * @param modelMap
	 * @return
	 * @return: String
	 * @author: zengmin
	 * @date: 2017年4月10日 上午11:18:52
	 */
	@RequestMapping("wallet_cz")
	public String walletCz(HttpServletRequest request, ModelMap modelMap) {
		if (!ServletUtil.isLogin(request)) {
			return "login";
		}
		Member me = ServletUtil.getMember(request);
		WxUser wxUser = wxUserService.selectByPrimaryKey(me.getId());
		modelMap.addAttribute("user", wxUser);
		return "mine/wallet_cz";
	}

	/**
	 * 人民币余额充值页
	 * 
	 * @Title: rbmCz
	 * @Description: TODO
	 * @param request
	 * @param modelMap
	 * @return
	 * @return: String
	 * @author: zengmin
	 * @date: 2017年5月24日 下午2:28:55
	 */
	@RequestMapping("rmb_cz")
	public String rbmCz(HttpServletRequest request, ModelMap modelMap) {
		return "mine/rmb_cz";
	}

	/**
	 * J币充值页
	 * 
	 * @Title: jbCz
	 * @Description: TODO
	 * @param request
	 * @param modelMap
	 * @return
	 * @return: String
	 * @author: zengmin
	 * @date: 2017年5月24日 下午2:29:07
	 */
	@RequestMapping("jb_cz")
	public String jbCz(HttpServletRequest request, ModelMap modelMap) {
		ListValues listValue = listValuesService.selectByPrimaryKey("fbc8733aafed4fd9ac4f341b155b5311");
		modelMap.addAttribute("jb_rmb_show", listValue.getListDesc());
		return "mine/jb_cz";
	}

	/**
	 * 互助宝充值页
	 * 
	 * @Title: hzbCz
	 * @Description: TODO
	 * @param request
	 * @param modelMap
	 * @return
	 * @return: String
	 * @author: zengmin
	 * @date: 2017年5月24日 下午2:29:21
	 */
	@RequestMapping("hzb_cz")
	public String hzbCz(HttpServletRequest request, ModelMap modelMap) {
		if (!ServletUtil.isLogin(request)) {
			return "login";
		}
		Member me = ServletUtil.getMember(request);
		WxUser wxUser = wxUserService.selectByPrimaryKey(me.getId());
		List dgzhList = listValuesService.selectListByType(8007);
		modelMap.addAttribute("dgzhList", dgzhList);
		OssConfig ossConfig = (OssConfig) SpringContextUtil.getBean("ossConfig");
		String ossurl = ossConfig.getOss_getUrl();
		modelMap.addAttribute("ossurl", ossurl);
		modelMap.addAttribute("user", wxUser);
		modelMap.addAttribute("fkTelephone", me.getKfTelephone());
		return "mine/hzb_cz";
	}

	/**
	 * 钱包提现
	 * 
	 * @return
	 */
	@RequestMapping("wallet_tx")
	public String walletTx(HttpServletRequest request, ModelMap modelMap) {
		Member me = ServletUtil.getMember(request);
		if (null == me) {
			return "login";
		}
		String userId = me.getId();
		if (StringUtils.isEmpty(userId)) {
			return "login";
		}
		WxUser user = wxUserService.selectByPrimaryKey(userId);
		modelMap.addAttribute("user", user);
		return "mine/wallet_tx";
	}

	/**
	 * 充值支付
	 * 
	 * @Title: paycwx
	 * @Description: TODO
	 * @param je--充值金额
	 * @param czType--充值类型
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 * @throws JDOMException
	 * @return: String
	 * @author: zengmin
	 * @date: 2017年4月10日 下午1:50:29
	 */
	@RequestMapping("paycwx")
	public @ResponseBody Map paycwx(double je, String czType, HttpServletRequest request, HttpServletResponse response)
			throws IOException, JDOMException {

		Member me = ServletUtil.getMember(request);
		System.out.println("----------paycwx----------" + ConfigUtil.certLocalPath);
		if (!ServletUtil.isLogin(request)) {
			return super.getAjaxResult("302", "登录超时", null);
		}

		if (je <= 0) {
			return super.getAjaxResult("401", "充值金额必须大于0元", null);
		}
		double czje = je;
		if ("2".equals(czType)) {
			// J币人民币换算
			ListValues listValue = listValuesService.selectByPrimaryKey("fbc8733aafed4fd9ac4f341b155b5311");
			if (StringUtils.isEmpty(listValue.getListValue())) {
				return super.getAjaxResult("999", "当前J币兑换比例尚未设置，不能充值，请联系客服人员", null);
			} else {
				double bl = Double.valueOf(listValue.getListValue());
				czje = je * bl;
			}
		}
		WxUser wxUser = wxUserService.selectByPrimaryKey(me.getId());

		// 创建充值记录
		WalletLog walletLog = new WalletLog();
		walletLog.setAmount(czje);
		walletLog.setCreateTime(new Date());
		walletLog.setDeleted("0");
		walletLog.setPayStatus("0");// 待支付
		walletLog.setPdType("1");
		walletLog.setRemark("充值");
		walletLog.setType(czType);
		walletLog.setUserId(me.getId());
		walletLog.setBusinessType(1);
		walletLog.setRmbAmount(je);// 实际支付金额
		if ("1".equals(czType)) {
			walletLog.setYeAmount(wxUser.getAvailablebalance());
		} else if ("2".equals(czType)) {
			walletLog.setYeAmount(wxUser.getJbAmount());
		} else if ("3".equals(czType)) {
			walletLog.setYeAmount(wxUser.getHzbAmount());
		}
		String out_trade_no = walletLogService.addWalletLog(walletLog);
		if (StringUtils.isEmpty(out_trade_no)) {
			return super.getAjaxResult("999", "充值失败", null);
		}
		Double tp = walletLog.getRmbAmount() * 100;
		SortedMap<Object, Object> parameters = new TreeMap<Object, Object>();
		parameters.put("appid", ApplicationPath.getParameter("wx_appid"));
		parameters.put("mch_id", ConfigUtil.MCH_ID);
		parameters.put("nonce_str", PayCommonUtil.CreateNoncestr());
		parameters.put("body", "钱包充值付款");
		parameters.put("out_trade_no", out_trade_no);

		// parameters.put("total_fee", tp.intValue() + "");
		parameters.put("total_fee", 1 + "");

		parameters.put("spbill_create_ip", request.getRemoteAddr());
		parameters.put("notify_url", ConfigUtil.NOTIFY_WALLET_URL);
		parameters.put("trade_type", "JSAPI");
		parameters.put("openid", wxUser.getQqOpenid());
		String sign = PayCommonUtil.createSign("UTF-8", parameters);
		parameters.put("sign", sign);
		System.out.println("parameters:" + parameters);
		String requestXML = PayCommonUtil.getRequestXml(parameters);
		System.out.println("requestXML:" + requestXML);
		String result = HttpClientUtil.sendPostRequest(ConfigUtil.UNIFIED_ORDER_URL, requestXML, true);
		System.out.println("result:" + result);
		SortedMap<Object, Object> map = XMLUtil.doXMLParse(result);// 解析微信返回的信息，以Map形式存储便于取值
		String return_code = (String) map.get("return_code");
		if ("SUCCESS".equals(return_code)) {
			SortedMap<Object, Object> params = new TreeMap<Object, Object>();
			params.put("appId", ApplicationPath.getParameter("wx_appid"));
			params.put("timeStamp", Long.toString(new Date().getTime()));
			params.put("nonceStr", PayCommonUtil.CreateNoncestr());
			params.put("package", "prepay_id=" + map.get("prepay_id"));
			params.put("signType", ConfigUtil.SIGN_TYPE);
			String paySign = PayCommonUtil.createSign("UTF-8", params);
			params.put("packageValue", "prepay_id=" + map.get("prepay_id")); // 这里用packageValue是预防package是关键字在js获取值出错
			params.put("paySign", paySign); // paySign的生成规则和Sign的生成规则一致
			String userAgent = request.getHeader("user-agent");
			char agent = userAgent.charAt(userAgent.indexOf("MicroMessenger") + 15);
			params.put("agent", new String(new char[] { agent }));// 微信版本号，用于前面提到的判断用户手机微信的版本是否是5.0以上版本。
			System.out.println("----------paycwx success----------");
			return super.getSuccessAjaxResult("操作成功", params);
		} else {
			String return_msg = (String) map.get("return_msg");
			return super.getAjaxResult("999", return_msg, null);
		}
	}

	/**
	 * 提现
	 * 
	 * @Title: tixian
	 * @Description: TODO
	 * @param je
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 * @throws JDOMException
	 * @return: Map
	 * @author: zengmin
	 * @date: 2017年5月4日 上午9:31:58
	 */
	@RequestMapping("tixian")
	public @ResponseBody Map tixian(double je, HttpServletRequest request, HttpServletResponse response)
			throws IOException, JDOMException {

		if (!ServletUtil.isLogin(request)) {
			return super.getAjaxResult("302", "登录超时", null);
		}
		Member me = ServletUtil.getMember(request);
		WxUser wxUser = wxUserService.selectByPrimaryKey(me.getId());
		if (je > wxUser.getAvailablebalance()) {
			return super.getAjaxResult("401", "参数错误，提现金额不能大于钱包金额", null);
		}
		if (je <= 0 || je > 20000) {
			return super.getAjaxResult("401", "参数错误，提现金额范围1-20000", null);
		}

		// 创建充值记录
		// WalletLog walletLog = new WalletLog();
		// walletLog.setAmount(je);
		// walletLog.setCreateTime(new Date());
		// walletLog.setDeleted("0");
		// walletLog.setPayStatus("0");// 待支付
		// walletLog.setPdType("2");// 消费
		// walletLog.setRemark("提现");
		// walletLog.setType("1");
		// walletLog.setUserId(me.getId());
		// walletLog.setRmbAmount(je);// 实际支付金额
		// String out_trade_no = walletLogService.addWalletLog(walletLog);
		// if (StringUtils.isEmpty(out_trade_no)) {
		// return super.getAjaxResult("999", "提现失败", null);
		// }
		// Double tp = walletLog.getRmbAmount() * 100;
		// SortedMap<Object, Object> parameters = new TreeMap<Object, Object>();
		// parameters.put("mch_appid",
		// ApplicationPath.getParameter("wx_appid"));
		// parameters.put("mchid", ConfigUtil.MCH_ID);
		// parameters.put("nonce_str", PayCommonUtil.CreateNoncestr());
		// parameters.put("partner_trade_no", out_trade_no);
		// parameters.put("openid", wxopenid);
		// parameters.put("check_name", "NO_CHECK");// NO_CHECK：不校验真实姓名
		// // FORCE_CHECK：强校验真实姓名
		// // parameters.put("amount", tp.intValue() + "");
		// parameters.put("amount", 1 + "");
		// parameters.put("desc", "提现");
		// parameters.put("spbill_create_ip", request.getRemoteAddr());
		// String sign = PayCommonUtil.createSign("UTF-8", parameters);
		// parameters.put("sign", sign);
		// System.out.println("parameters:" + parameters);
		// String requestXML = PayCommonUtil.getRequestXml(parameters);
		// System.out.println("requestXML:" + requestXML);
		// String result =
		// HttpClientUtil.sendPostRequest(ConfigUtil.TRANSFERS_URL, requestXML,
		// true);
		// System.out.println("result:" + result);
		// SortedMap<Object, Object> map = XMLUtil.doXMLParse(result);//
		// 解析微信返回的信息，以Map形式存储便于取值
		// String return_code = (String) map.get("return_code");
		// String return_msg = (String) map.get("return_msg");
		// String result_code = (String) map.get("result_code");
		// String payment_no = (String) map.get("payment_no");
		// String payment_time = (String) map.get("payment_time");
		// boolean txFlag = false;
		// if ("SUCCESS".equals(return_code) && "SUCCESS".equals(result_code)) {
		// walletLog.setPaySn(payment_no);
		// walletLog.setPayStatus("1");
		// wxUser.setAvailablebalance(CommUtil.subtract(wxUser.getAvailablebalance(),
		// je));
		// if (wxUserService.updateWxUser(wxUser)) {
		// walletLogService.updateWalletLog(walletLog);
		// }
		// txFlag = true;
		// } else {
		// walletLog.setPaySn(payment_no);
		// walletLog.setPayStatus("2");
		// walletLogService.updateWalletLog(walletLog);
		// }
		// if (txFlag) {
		// return super.getSuccessAjaxResult("提现成功");
		// } else {
		// return super.getAjaxResult("999", return_msg, null);
		// }
		// 钱包剩余金额
		double syje = CommUtil.subtract(wxUser.getAvailablebalance(), je);
		WalletTx walletTx = new WalletTx();
		walletTx.setUserid(wxUser.getId() + "");
		walletTx.setUsername(wxUser.getNickName());
		walletTx.setTxje(je);
		walletTx.setSyje(syje);
		walletTx.setCreateTime(new Date());
		walletTx.setState("0");
		boolean bo = walletTxService.addWalletTx(walletTx);
		if (bo) {
			wxUser.setAvailablebalance(syje);
			wxUserService.updateWxUser(wxUser);
			// 创建钱包提现记录
			WalletLog walletLog = new WalletLog();
			walletLog.setAmount(je);
			walletLog.setCreateTime(new Date());
			walletLog.setDeleted("0");
			walletLog.setPayStatus("0");// 待支付
			walletLog.setPdType("2");// 消费
			walletLog.setRemark("提现");
			walletLog.setType("1");
			walletLog.setUserId(me.getId());
			walletLog.setRmbAmount(je);// 实际支付金额
			walletLog.setYeAmount(syje);
			walletLog.setBusinessType(2);
			walletLogService.addWalletLog(walletLog);
			// 发送短信及微信提醒－－－－－－－－－－－－－－
			return super.getSuccessAjaxResult("提现申请成功");
		}
		return super.getAjaxResult("999", "提现申请失败", null);
	}

	/**
	 * 充值支付回调
	 * 
	 * @Title: notify
	 * @Description: TODO
	 * @param request
	 * @param response
	 * @return
	 * @return: String
	 * @author: zengmin
	 * @date: 2017年4月10日 下午3:00:34
	 */
	@RequestMapping("notify")
	public @ResponseBody String notify(HttpServletRequest request, HttpServletResponse response) {
		try {
			String return_msg = "";
			BufferedReader br = request.getReader();
			String valueString = null;
			while ((valueString = br.readLine()) != null) {
				return_msg += valueString;
			}
			System.out.println("return_msg:" + return_msg);
			SortedMap<Object, Object> map = XMLUtil.doXMLParse(return_msg);
			String sign = (String) map.get("sign");
			String out_trade_no = (String) map.get("out_trade_no");
			String transaction_id = (String) map.get("transaction_id");
			String return_code = (String) map.get("return_code");
			String sign1 = PayCommonUtil.createSign("UTF-8", map);
			// System.out.println("sign:"+sign+" sign1:"+sign1);
			if (sign1.equals(sign)) {
				// 校验成功
				System.out.println("success");
				WalletLog walletLog = walletLogService.selectByPrimaryKey(out_trade_no);
				if ("SUCCESS".equals(return_code)) {// 充值成功
					// 获取充值记录
					System.out.println(out_trade_no + "-----------" + transaction_id);
					WxUser wxUser = wxUserService.selectByPrimaryKey(walletLog.getUserId());
					TjyUser tjyUser = tjyUserService.selectByPrimaryKey(wxUser.getId() + "");
					// 修改钱包
					String czType = "";
					String dw = "";
					String ye_text = "";// 余额显示值
					double ye = 0;// 余额
					String cz_text = "";
					String end = "";
					if ("1".equals(walletLog.getType())) {
						wxUser.setAvailablebalance(CommUtil.add(wxUser.getAvailablebalance(), walletLog.getAmount()));
						czType = "RMB";
						dw = "元";
						ye = wxUser.getAvailablebalance();
						ye_text = CommUtil.formatNumStr(wxUser.getAvailablebalance());
						cz_text = CommUtil.formatNumStr(walletLog.getAmount());
						end = "。多多充值多多消费才能确保土豪形象";
						// 勋章
						// 钻石土豪 一次性充值100w人民币 honor_010
						// 金土豪 一次性充值10w人民币 honor_011
						// 银土豪 一次性充值1w人民币 honor_012
						Double rmb = walletLog.getAmount();
						if (rmb >= 1000000) {
							userHonorService.addUserAndHonor(walletLog.getUserId(), "honor_004");
						} else if (rmb >= 100000 && rmb < 1000000) {
							userHonorService.addUserAndHonor(walletLog.getUserId(), "honor_005");
						} else if (rmb >= 10000 && rmb < 100000) {
							userHonorService.addUserAndHonor(walletLog.getUserId(), "honor_006");
						}

					} else if ("2".equals(walletLog.getType())) {
						wxUser.setJbAmount(CommUtil.add(wxUser.getJbAmount(), walletLog.getAmount()));
						czType = "J币";
						dw = "个";
						ye = wxUser.getJbAmount();
						ye_text = Math.round(wxUser.getJbAmount()) + "";
						cz_text = Math.round(walletLog.getAmount()) + "";
						end = "。多打赏，多互动，朋友遍天下";
					} else {
						wxUser.setHzbAmount(CommUtil.add(wxUser.getHzbAmount(), walletLog.getAmount()));
						czType = "互助宝";
						dw = "元";
						ye = wxUser.getHzbAmount();
						ye_text = CommUtil.formatNumStr(wxUser.getHzbAmount());
						cz_text = CommUtil.formatNumStr(walletLog.getAmount());
						end = "。互助宝，帮您在刀刃上花钱";
					}
					wxUserService.updateWxUser(wxUser);
					// 发送短信
					MessageInfo messageInfo = new MessageInfo();
					messageInfo.setId(UUIDGenerator.getUUID());
					messageInfo.setDeleted(0);
					messageInfo.setMobile(wxUser.getMobile());
					messageInfo.setType(1);// 短信
					messageInfo.setCreateTime(new Date());
					String name = tjyUser.getNickname();
					messageInfo.setContent("{name:\"" + name + "\",time:\""
							+ DateUtils.getCurrTimeStr("yyyy-MM-dd HH:mm") + "\",money:\"" + czType + cz_text + dw
							+ "\",czType:\"" + czType + "\",ye:\"" + ye_text + dw + end + "\"}");
					messageInfo.setStatus(0);// 未发送
					messageInfo.setTemplateId(AldyMessageUtil.MsmTemplateId.RECHARGE);
					messageInfoService.addMessageInfo(messageInfo);
					// 发送微信--给用户发送微信消息提醒，告知充值成功
					messageInfo = new MessageInfo();
					messageInfo.setId(UUIDGenerator.getUUID());
					messageInfo.setDeleted(0);
					messageInfo.setType(2);// 微信
					messageInfo.setToUserId(wxUser.getId() + "");
					messageInfo.setCreateTime(new Date());
					// 组装内容
					// Sysconfig sysconfig = sysconfigService.getSysconfig();
					// String site_path = sysconfig.getWebSite();
					// String url = site_path + "/m/my/personal_wallet.do";
					String content = AldyMessageUtil.userwalletczsuccess(name, czType + cz_text + dw, czType,
							ye_text + dw + end);
					// String content = "【" + AldyMessageUtil.SMSPRE + "】尊敬的" +
					// name + "，您的账户于"
					// + DateUtils.getCurrTimeStr("yyyy-MM-dd HH:mm") + "成功充值" +
					// czType + cz_text + dw + "，当前"
					// + czType + "余额为" + ye_text + dw + "。";
					String con = WxMsmUtil.getTextMessageContent(content);
					messageInfo.setContent(con);
					messageInfo.setTemplateId("cztx");
					messageInfo.setStatus(0);// 未发送
					messageInfo.setWxMsgType(1);/// ** 微信消息类型（1：文本消息2：图文消息） */
					messageInfoService.addMessageInfo(messageInfo);
					/**
					 * 发送系统消息
					 */
					messageInfo = new MessageInfo();
					messageInfo.setId(UUIDGenerator.getUUID());
					messageInfo.setDeleted(0);
					messageInfo.setType(3);// 系统消息
					messageInfo.setToUserId(wxUser.getId() + "");
					messageInfo.setCreateTime(new Date());
					messageInfo.setContent(content);
					messageInfo.setStatus(0);// 不需要发送，未读
					messageInfoService.addMessageInfo(messageInfo);
					walletLog.setPaySn(transaction_id);
					walletLog.setPayStatus("1");
					walletLog.setPayType("1");
					walletLog.setYeAmount(ye);
				} else {// 充值失败
					walletLog.setPaySn(transaction_id);
					walletLog.setPayStatus("2");
					walletLog.setPayType("1");
				}
				boolean bo = walletLogService.updateWalletLog(walletLog);
				if (bo) {
					System.out.println("状态更新成功");
				} else {
					System.out.println("状态更新失败");
				}
				return "<xml><return_code><![CDATA[SUCCESS]]></return_code><return_msg><![CDATA[OK]]></return_msg></xml>";
			} else {
				System.out.println("failure");
				// 校验失败
				return "<xml><return_code><![CDATA[FAIL]]></return_code><return_msg></return_msg></xml>";

			}
		} catch (Exception e) {
			e.printStackTrace();
			return "<xml><return_code><![CDATA[FAIL]]></return_code><return_msg></return_msg></xml>";
		}
	}

	/**
	 * 添加朋友印象
	 * 
	 * @Title: pyyx_edit
	 * @Description: TODO
	 * @param request
	 * @param modelMap
	 * @return
	 * @return: String
	 * @author: zengmin
	 * @date: 2017年4月17日 上午1:07:38
	 */
	@RequestMapping("pyyx_add")
	public String pyyx_add(HttpServletRequest request, ModelMap modelMap) {
		Member me = ServletUtil.getMember(request);
		if (null == me) {
			return "login";
		}
		String userId = me.getId();
		if (StringUtils.isEmpty(userId)) {
			return "login";
		}
		// 获取朋友印象库
		List yxkList = listValuesService.selectListByType(8003);
		modelMap.addAttribute("yxkList", yxkList);
		// 获取已有印象
		List<Map<String, Object>> yxList = userFriendimpressService.selectByUserIdAndType(userId, 0);
		modelMap.addAttribute("yxList", yxList);
		return "mine/pyyx_add";
	}

	/**
	 * 朋友印象，编辑
	 * 
	 * @Title: pyyx_edit
	 * @Description: TODO
	 * @param request
	 * @param modelMap
	 * @return
	 * @return: String
	 * @author: zengmin
	 * @date: 2017年4月17日 上午1:07:38
	 */
	@RequestMapping("pyyx_edit")
	public String pyyx_edit(HttpServletRequest request, ModelMap modelMap) {
		Member me = ServletUtil.getMember(request);
		if (null == me) {
			return "login";
		}
		String userId = me.getId();
		if (StringUtils.isEmpty(userId)) {
			return "login";
		}
		// 获取朋友印象库
		// List yxkList = listValuesService.selectListByType(8003);
		// modelMap.addAttribute("yxkList", yxkList);
		// 获取已有印象
		List<Map<String, Object>> yxList = userFriendimpressService.selectcountByUserId(userId, 0);
		String type = request.getParameter("type");
		modelMap.addAttribute("yxList", yxList);
		modelMap.addAttribute("type", type);
		return "mine/pyyx_edit";
	}

	/**
	 * 客服留言
	 * 
	 * @Title: leaveMsgPage
	 * @Description: TODO
	 * @param request
	 * @param modelMap
	 * @return
	 * @return: String
	 * @author: zengmin
	 * @date: 2017年4月29日 上午12:01:27
	 */
	@RequestMapping("leaveMsgPage")
	public String leaveMsgPage(HttpServletRequest request, ModelMap modelMap) {
		Member me = ServletUtil.getMember(request);
		if (null == me) {
			return "login";
		}
		String userId = me.getId();
		if (StringUtils.isEmpty(userId)) {
			return "login";
		}
		Sysconfig sysconfig = sysconfigService.getSysconfig();
		modelMap.addAttribute("kf_tel", sysconfig.getServiceTelphoneList());
		return "mine/leaveMsg";
	}

	/**
	 * 客服留言2 个人中心
	 * 
	 * @Title: leaveMsgPage
	 * @Description: TODO
	 * @param request
	 * @param modelMap
	 * @return
	 * @return: String
	 * @author: zengmin
	 * @date: 2017年4月29日 上午12:01:27
	 */
	@RequestMapping("leaveMsgPage2")
	public String leaveMsgPage2(HttpServletRequest request, ModelMap modelMap) {
		Member me = ServletUtil.getMember(request);
		if (null == me) {
			return "login";
		}
		String userId = me.getId();
		if (StringUtils.isEmpty(userId)) {
			return "login";
		}
		// Sysconfig sysconfig = sysconfigService.getSysconfig();
		modelMap.addAttribute("kf_tel", "010-53118922");
		return "mine/leaveMsg";
	}

	@RequestMapping("leaveMsgSave")
	public @ResponseBody Map leaveMsgSave(String content, String type, HttpServletRequest request) {
		if (StringUtils.isEmpty(content) || StringUtils.isEmpty(type)) {
			return super.getAjaxResult("401", "参数错误", null);
		}
		Member me = ServletUtil.getMember(request);
		if (null == me) {
			return super.getAjaxResult("302", "登录超时，请重新登录", null);
		}
		String userId = me.getId();
		if (StringUtils.isEmpty(userId)) {
			return super.getAjaxResult("302", "登录超时，请重新登录", null);
		}
		LeaveMsg leaveMsg = new LeaveMsg();
		leaveMsg.setCreateTime(new Date());
		leaveMsg.setContent(content);
		leaveMsg.setDeleted(0);
		leaveMsg.setUserId(userId);
		leaveMsg.setSource("1");
		leaveMsg.setType(type);
		boolean bo = leaveMsgService.addLeaveMsg(leaveMsg);
		if (bo) {
			return super.getSuccessAjaxResult();
		} else {
			return super.getAjaxResult("999", "留言失败", null);
		}
	}

	/**
	 * 删除认证图片
	 * 
	 * @Title: reconPhotoDelete
	 * @Description: TODO
	 * @param id
	 * @param request
	 * @return
	 * @return: Map
	 * @author: zengmin
	 * @date: 2017年5月3日 下午4:37:16
	 */
	@RequestMapping("reconPhotoDelete")
	public @ResponseBody Map reconPhotoDelete(String id, HttpServletRequest request) {
		if (StringUtils.isEmpty(id)) {
			return super.getAjaxResult("401", "参数错误", null);
		}
		Member me = ServletUtil.getMember(request);
		if (null == me) {
			return super.getAjaxResult("302", "登录超时，请重新登录", null);
		}
		String userId = me.getId();
		if (StringUtils.isEmpty(userId)) {
			return super.getAjaxResult("302", "登录超时，请重新登录", null);
		}
		boolean bo = reconPhotosService.deleteByPrimaryKey(id);
		if (bo) {
			return super.getSuccessAjaxResult();
		} else {
			return super.getAjaxResult("999", "删除失败", null);
		}
	}

	public static void main(String[] args) throws JDOMException, IOException {
		String txt = WxMsmUtil.getTextMessageContent("hello world");
		// System.out.println(txt);
		// oBhgswtb14vrsHgvU4nA07yCMEyg
		TextContent textContent = JSON.toJavaObject(JSON.parseObject(txt), TextContent.class);
		WxMsmBean xmb = new WxMsmBean();
		xmb.setMsgtype("text");
		xmb.setTouser("oBhgswtb14vrsHgvU4nA07yCMEyg");
		xmb.setText(textContent);
		xmb.setTotag("s");
		String result = JSON.toJSONString(xmb);
//		System.out.println("-------" + WeixinUtil.sendKfMsgFwhTest(result));

		ArticlesBean articlesBean = new ArticlesBean("Happy Day", "Is Really A Happy Day", "http://www.baidu.com");
		ArticlesBean articlesBean2 = new ArticlesBean("Happy Day22", "中华人民共和国", "http://news.sina.com.cn");
		List<ArticlesBean> articlesBeans = new ArrayList<ArticlesBean>();
		articlesBeans.add(articlesBean);
		articlesBeans.add(articlesBean2);
		NewsContent newsContent = new NewsContent(articlesBeans);
		txt = WxMsmUtil.getNewsMessageContent(newsContent);
		// System.out.println(txt);
		NewsContent nc = JSON.toJavaObject(JSON.parseObject(txt), NewsContent.class);
		xmb = new WxMsmBean();
		xmb.setMsgtype("news");
		xmb.setTouser("oBhgswtb14vrsHgvU4nA07yCMEyg1111");
		xmb.setNews(nc);
		result = JSON.toJSONString(xmb);
//		System.out.println("----3333333---" + WeixinUtil.sendKfMsgFwhTest(result));

	}

	/**
	 * 勋章详情页
	 * 
	 * @return
	 */
	@RequestMapping("my_honor_info")
	public String my_honor_info(String id, HttpServletRequest request, ModelMap modelMap) {
		Honor honor = honorService.selectByPrimaryKey(id);
		modelMap.addAttribute("honor", honor);
		return "mine/my_honor_info";
	}

	/**
	 * 用户等级
	 * 
	 * @return
	 */
	@RequestMapping("my_level_info")
	public String my_level_info(HttpServletRequest request, ModelMap modelMap) {

		Member me = ServletUtil.getMember(request);
		if (null == me) {
			return "login";
		}
		String userId = me.getId();
		if (StringUtils.isEmpty(userId)) {
			return "login";
		}
		WxUser user = wxUserService.selectByPrimaryKey(userId);
		modelMap.addAttribute("user", user);

		UserIntegralEmpirical userIntegralEmpirical = userIntegralEmpiricalService.selectByIeType("2");
		modelMap.addAttribute("userIntegralEmpirical", userIntegralEmpirical);
		// 用户等级
		// UserEmpirical userEmpirical =
		// userEmpiricalService.selectBylevel("LV10");

		// modelMap.addAttribute("userEmpirical", userEmpirical);
		return "mine/my_level_info";
	}

	/**
	 * 用户积分规则
	 * 
	 * @return
	 */
	@RequestMapping("my_integral_info")
	public String my_integral_info(HttpServletRequest request, ModelMap modelMap) {

		Member me = ServletUtil.getMember(request);
		if (null == me) {
			return "login";
		}
		String userId = me.getId();
		if (StringUtils.isEmpty(userId)) {
			return "login";
		}

		UserIntegralEmpirical userIntegralEmpirical = userIntegralEmpiricalService.selectByIeType("1");
		modelMap.addAttribute("userIntegralEmpirical", userIntegralEmpirical);
		// 用户等级
		// UserEmpirical userEmpirical =
		// userEmpiricalService.selectBylevel("LV10");

		// modelMap.addAttribute("userEmpirical", userEmpirical);
		return "mine/my_integral_info";
	}

	/**
	 * 邀请好友列表页
	 * 
	 * @Title: my_honor_info
	 * @Description: TODO
	 * @param id
	 * @param request
	 * @param modelMap
	 * @return
	 * @return: String
	 * @author: zengmin
	 * @date: 2017年7月13日 上午11:36:07
	 */
	@RequestMapping("invite_record")
	public String invite_record(HttpServletRequest request, ModelMap modelMap) {
		return "mine/invite_record";
	}

	/**
	 * 分页查询邀请记录
	 * 
	 * @Title: inviteRecordAjaxPage
	 * @Description: TODO
	 * @param userId
	 * @param page
	 * @param pageSize
	 * @param request
	 * @param response
	 * @return
	 * @return: Map
	 * @author: zengmin
	 * @date: 2017年7月13日 上午11:39:43
	 */
	@RequestMapping("invite_record_ajax_page")
	public @ResponseBody Map inviteRecordAjaxPage(String userId, int page, int pageSize, HttpServletRequest request,
			HttpServletResponse response) {
		if (StringUtils.isEmpty(userId)) {
			Member me = ServletUtil.getMember(request);
			userId = me.getId();
		}
		if (StringUtils.isEmpty(userId)) {
			return super.getAjaxResult("401", "参数错误", null);
		}
		List list = inviteRecordService.selectInviteRecordPageByUserId(userId, page, pageSize);
		return super.getSuccessAjaxResult("success", list);
	}

	/**
	 * 分享中转页
	 * 
	 * @Title: fxyl
	 * @Description: TODO
	 * @param request
	 * @param code
	 * @param state
	 * @param response
	 * @param modelMap
	 * @return
	 * @return: String
	 * @author: zengmin
	 * @date: 2017年7月13日 下午2:13:57
	 */
	@RequestMapping("fxyl")
	public String fxyl(HttpServletRequest request, String code, String state, HttpServletResponse response,
			ModelMap modelMap) {
		if (!ServletUtil.isLogin(request)) {
			return "login";
		}

		Member me = ServletUtil.getMember(request);
		modelMap.addAttribute("userId", me.getId());
		WxUser wxUser = wxUserService.selectByPrimaryKey(me.getId());
		if (null != wxUser) {
			if (StringUtils.hasLength(wxUser.getHdticket())) {
				modelMap.addAttribute("hdticket", wxUser.getHdticket());
			} else {
				String wx_secret = ApplicationPath.getParameter("wx_secret");
				String wx_appid = ApplicationPath.getParameter("wx_appid");
				AccessToken a = WeixinUtil.getAccessToken(wx_appid, wx_secret);
				String url = "https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token=" + a.getToken();
				String data = "{ \"action_name\": \"QR_LIMIT_STR_SCENE\", \"action_info\": {\"scene\": {\"scene_str\": \""
						+ wxUser.getId() + "\"}}}";

				String rs = HttpClientUtil.sendPostRequest(url, data, true);
				System.out.println("-----data----" + data);
				System.out.println("-----rs----" + rs);
				Map rsmap = JsonUtil.parseJSON2Map(rs);
				String ticket = (String) rsmap.get("ticket");
				wxUser.setHdticket(ticket);
				wxUserService.updateWxUser(wxUser);
				modelMap.addAttribute("hdticket", ticket);
			}
		} else {
			return "login";
		}
		return "mine/fxyl";
	}

	/**
	 * 分享页
	 * 
	 * @Title: fxylshare
	 * @Description: TODO
	 * @param request
	 * @param t
	 * @param uid
	 * @param response
	 * @param modelMap
	 * @return
	 * @return: String
	 * @author: zengmin
	 * @date: 2017年7月13日 下午2:18:06
	 */
	@RequestMapping("fxylshare")
	public String fxylshare(HttpServletRequest request, String t, String uid, HttpServletResponse response,
			ModelMap modelMap) {
		modelMap.addAttribute("hdticket", t);
		List<Map<String, Object>> listValues = listValuesService.selectListByType(888);
		if (null != listValues && !listValues.isEmpty()) {
			modelMap.put("yqConfig", listValues.get(0));
		}
		if (StringUtils.hasLength(uid)) {
			WxUser wxUser = wxUserService.selectByPrimaryKey(uid);
			modelMap.addAttribute("user_img", wxUser.getImgUrl());
			modelMap.addAttribute("user_name", wxUser.getNickName());
			modelMap.addAttribute("uid", uid);
		}
		return "mine/fxylshare";
	}

}
