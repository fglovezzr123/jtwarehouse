package com.wing.socialcontact.action;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.wing.socialcontact.common.model.PageParam;
import com.wing.socialcontact.config.MsgConfig;
import com.wing.socialcontact.config.OssConfig;
import com.wing.socialcontact.service.wx.api.IActivityCancelService;
import com.wing.socialcontact.service.wx.api.IActivityDelayService;
import com.wing.socialcontact.service.wx.api.IActivityRefundService;
import com.wing.socialcontact.service.wx.api.IActivityService;
import com.wing.socialcontact.service.wx.api.IActivityTagService;
import com.wing.socialcontact.service.wx.api.IActivityUserService;
import com.wing.socialcontact.service.wx.api.IMessageInfoService;
import com.wing.socialcontact.service.wx.api.ITjyUserService;
import com.wing.socialcontact.service.wx.bean.Activity;
import com.wing.socialcontact.service.wx.bean.ActivityCancel;
import com.wing.socialcontact.service.wx.bean.ActivityDelay;
import com.wing.socialcontact.service.wx.bean.ActivityRefund;
import com.wing.socialcontact.service.wx.bean.MessageInfo;
import com.wing.socialcontact.service.wx.bean.TjyUser;
import com.wing.socialcontact.sys.action.BaseAction;
import com.wing.socialcontact.sys.service.IDistrictService;
import com.wing.socialcontact.sys.service.IListValuesService;
import com.wing.socialcontact.sys.service.IUserService;
import com.wing.socialcontact.util.AldyMessageUtil;
import com.wing.socialcontact.util.ConfigUtil;
import com.wing.socialcontact.util.DateUtils;
import com.wing.socialcontact.util.SpringContextUtil;
import com.wing.socialcontact.util.UUIDGenerator;
import com.wing.socialcontact.util.WxMsmUtil;

/**
 * 活动管理
 * 
 * @author zhangzheng
 * 
 */
@Controller
@RequestMapping("/activity")
public class ActivityAction extends BaseAction {

	@Autowired
	private IActivityService activityService;

	@Autowired
	private IActivityTagService activityTagService;

	@Autowired
	private IListValuesService listValuesService;

	@Autowired
	private IDistrictService districtService;

	@Autowired
	private IUserService userService;

	@Autowired
	private IActivityUserService activityUserService;

	@Autowired
	private IMessageInfoService messageInfoService;

	@Autowired
	private IActivityRefundService activityRefundService;

	@Autowired
	private ITjyUserService tjyUserService;

	@Autowired
	private IActivityCancelService activityCancelService;

	@Autowired
	private IActivityDelayService activityDelayService;

	/**
	 * 活动列表
	 */
	@RequiresPermissions("activity:read")
	@RequestMapping("load")
	public String classload(ModelMap map) {
		List tag = activityTagService.selectalltag();
		map.addAttribute("tag", tag);
		return "system/activity/load";
	}

	/**
	 * 活动列表查询
	 * 
	 * @param param
	 * @param activity
	 * @return
	 */
	@RequiresPermissions("activity:read")
	@RequestMapping("query")
	public ModelAndView classquery(PageParam param, Activity activity,String userId) {
		return ajaxJsonEscape(activityService.selectactivity(param, activity,userId));
	}

	/**
	 * 活动新增
	 */
	@RequiresPermissions("activity:add")
	@RequestMapping("addPage")
	public String classaddPage(ModelMap map) {
		// 获取省
		List provinceList = districtService.selectDistrictByType("1");
		map.addAttribute("provinceList", provinceList);
		return "system/activity/add";
	}

	@RequiresPermissions("activity:add")
	@RequestMapping("add")
	public ModelAndView classadd(Activity dto, Errors errors, String phone) {
		if (errors.hasErrors()) {
			ModelAndView mav = getValidationMessage(errors);
			if (mav != null)
				return mav;
		}

		List<TjyUser> peo = tjyUserService.selectbymobile(phone, 2);
		if (peo.size() == 0) {
			return ajaxDone(MsgConfig.MSG_KEY_FAIL);
		}
		TjyUser user = peo.get(0);
		dto.setCreateTime(new Date());
		dto.setCreateUserId(user.getId());
		dto.setCreateUserName(user.getNickname());
		dto.setSponsor(user.getComName());
		dto.setSponsorIntroduce(user.getComProfile());
		dto.setIscod(0);
		dto.setIsdelay(0);
		dto.setIscancel(0);
		return ajaxDone(activityService.addActivity(dto));

	}

	/**
	 * 活动修改
	 */
	@RequiresPermissions("activity:update")
	@RequestMapping("updatePage")
	public String classupdatePage(String id, ModelMap map) {
		Activity dto = activityService.getActivityByid(id);
		map.addAttribute("dto", dto);
		map.addAttribute("type");
		OssConfig ossConfig = (OssConfig) SpringContextUtil.getBean("ossConfig");
		String ossurl = ossConfig.getOss_getUrl();
		map.addAttribute("ossurl", ossurl);
		// 获取省
		List provinceList = districtService.selectDistrictByType("1");
		map.addAttribute("provinceList", provinceList);
		return "system/activity/update";
	}

	@RequiresPermissions("activity:update")
	@RequestMapping("update")
	public ModelAndView classupdate(Activity dto, Errors errors) {
		if (errors.hasErrors()) {
			ModelAndView mav = getValidationMessage(errors);
			if (mav != null)
				return mav;
		}
		Date now = new Date();
		//已取消活动打开，发消息给活动发布人
		if(dto.getStatus()==6){
			dto.setStatus(2);
			String userid = dto.getCreateUserId();
			TjyUser tjyUser = tjyUserService.selectById(userid);
			String name = tjyUser.getTrueName();
			if(tjyUser!=null){
				// 发消息  【天九共享会】**老板，您于2016-03-12 19:22取消的****活动已被后台打开，请您尽快前去核实，特此通知。
				String content = AldyMessageUtil.activityOpen(name,dto.getTitles(),dto.getRefundDescription());
				sendmessage(userid, content, 2, "ACTIVITY_OPEN");
				sendmessage(userid, content, 4, "ACTIVITY_OPEN");
				/**
				 * 模板名称: 活动取消后，后台再次开通
				 *	模板ID: SMS_90980085
				 *	模板内容: ${name}老板，您于${time}取消的${hdname}活动已被后台打开，请您尽快前去核实，特此通知。
				 */
				//短信
				String content1="{name:\"" + name + "\",time:\"" + dto.getRefundDescription() + "\",hdname:\"" +dto.getTitles() + "\"}";
				sendmessage1(tjyUser.getId(),content1,1,AldyMessageUtil.MsmTemplateId.ACTIVITY_ZAIKAITONG,tjyUser.getMobile());
			}
			
		}
		
		if(now.before(dto.getSignupTime())){
			//报名中
			dto.setStatus(2);
		}else if(now.before(dto.getStartTime())){
			dto.setStatus(3);
		}else if(now.before(dto.getEndTime())){
			dto.setStatus(4);
			//拒绝报名
			activityService.refuseSignup(dto);
		}else if(now.after(dto.getEndTime())){
			dto.setStatus(5);
			dto.setSort(1);//权重1
			dto.setRecommendEnable(0);//首页推荐 否
			dto.setRecommendList(0);//列表推荐  否
			dto.setShowEnable(1);//是否显示  是
			//拒绝报名
			activityService.refuseSignup(dto);
			//付款
			activityService.jiesuanSignup(dto);
		}
		return ajaxDone(activityService.updateActivity(dto));

	}

	/**
	 * 活动查看
	 */
	@RequiresPermissions("activity:read")
	@RequestMapping("viewPage")
	public String viewPage(String id, ModelMap map) {
		Activity dto = activityService.getActivityByid(id);
		map.addAttribute("dto", dto);
		OssConfig ossConfig = (OssConfig) SpringContextUtil.getBean("ossConfig");
		String ossurl = ossConfig.getOss_getUrl();
		map.addAttribute("ossurl", ossurl);
		// 获取省
		List provinceList = districtService.selectDistrictByType("1");
		map.addAttribute("provinceList", provinceList);
		return "system/activity/view";
	}

	/**
	 * 活动删除
	 */
	@RequiresPermissions("activity:delete")
	@RequestMapping("del")
	public ModelAndView del(String id) {

		return ajaxDone(activityService.deleteactivity(id));
	}

	/** 延期页面 */
	@RequestMapping("delayPage")
	public String delayPage(String acid, ModelMap map) {
		ActivityDelay parm = new ActivityDelay();
		parm.setActivityId(acid);
		parm.setStatus(0);
		List<ActivityDelay> lst = activityDelayService.selectbyTerm(parm);
		if (lst.size() > 0) {
			map.addAttribute("dto", lst.get(0));
		} else {
			map.addAttribute("dto", new ActivityDelay());
		}
		Activity activity = activityService.getActivityByid(acid);
		map.addAttribute("activity", activity);
		return "system/activity/delayPage";
	}

	/**
	 * 活动延期
	 * 
	 */
	@RequiresPermissions("activity:update")
	@RequestMapping("delay")
	public ModelAndView delay(ActivityDelay dto, Integer type) {
		String id = dto.getActivityId();
		Activity activity = activityService.getActivityByid(id);
		activity.setIsdelay(0);
		// 同意
		dto.setStatus(type);
		if (type == 1) {
			String msg = activityDelayService.updateActivityDelay(dto);
			activity.setStartTime(dto.getStartTime());
			activity.setEndTime(dto.getEndTime());
			activity.setSignupTime(dto.getSignupTime());
			activity.setStatus(2);
			/**
			 * 同意延期申请，给活动发布人及报名用户发消息
			 */
			/**
			 * 获取所有已报名且 未取消 未拒绝的报名记录
			 */
			List<Map> users = activityUserService.getusersbyactivityid(id);
			/**
			 * 活动延期 尊敬的**，您所报名的****活动已延期至2017-09-08 19:00:00，望尽快与主办人核实。
			 * 【AldyMessageUtil.SMSPRE】尊敬的***，您所报名的****活动已延期至2017-09-08 19:00:00，望尽快与活动主办人核实。立即前往
			 */
			for (Map user : users) {
				String touser = (String) user.get("userId");
				String name = (String) user.get("userName");
				// 发消息
				String content = AldyMessageUtil.activitydelay(name,activity.getTitles(),dategeshi(activity.getStartTime()),activity.getId());
//				String content = "【"+AldyMessageUtil.SMSPRE+"】尊敬的" + name + ",您所报名的" + activity.getTitles() + "活动已延期至"
//						+ dategeshi(activity.getStartTime()) + "，望尽快与活动主办人核实。<a href='"+ConfigUtil.DOMAIN+"/wxfront/m/activity/activityDetailPage.do?id="+activity.getId()+"'>立即前往</a>";
				sendmessage(touser, content, 2, "ACTIVITY_DELAY");
				sendmessage(touser, content, 4, "ACTIVITY_DELAY");
			}
			
			String touser = activity.getCreateUserId();
			String name = activity.getCreateUserName();
			String content = AldyMessageUtil.activitydelayaccept(name, activity.getTitles());
//			String content = "【"+AldyMessageUtil.SMSPRE+"】尊敬的" + name + ",您所发布的" + activity.getTitles() + "活动延期申请已通过，望尽快处理。";
			sendmessage(touser, content, 2, "ACTIVITY_DELAY");
			sendmessage(touser, content, 4, "ACTIVITY_DELAY");
		}
		// 拒绝
		else {
			String msg = activityDelayService.updateActivityDelay(dto);
			/**
			 * 不同意延期申请，给活动发布人发消息
			 */
			String touser = activity.getCreateUserId();
			String name = activity.getCreateUserName();
			String content = AldyMessageUtil.activitydelayrefuse(name, activity.getTitles());;
//			String content = "【"+AldyMessageUtil.SMSPRE+"】尊敬的" + name + ",您所发布的" + activity.getTitles() + "活动延期申请未通过，望尽快处理。";
			sendmessage(touser, content, 2, "ACTIVITY_DELAY");
			sendmessage(touser, content, 4, "ACTIVITY_DELAY");
		}

		return ajaxDone(activityService.updateActivity(activity));
	}

	/**
	 * 时间格式化
	 * 
	 * @param date
	 * @return
	 */
	public String dategeshi(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat(" yyyy-MM-dd HH:mm:ss");
		String str = sdf.format(date);
		return str;
	}

	/**
	 * 根据省id获取市
	 */
	@RequestMapping("citys")
	public ModelAndView getcitise(String pid) {

		return ajaxJsonEscape(districtService.selectDistrictBySuperId(pid));
	}

	/**
	 * 活动审核
	 */
	@RequestMapping("checkactivity")
	public ModelAndView checkactivity(String id, Integer status) {

		Activity activity = activityService.getActivityByid(id);
		activity.setStatus(status);
		if(status==2){
			Date now = new Date();
			if(now.before(activity.getSignupTime())){
				//报名中
				activity.setStatus(2);
			}else if(now.before(activity.getStartTime())){
				activity.setStatus(3);
			}else if(now.before(activity.getEndTime())){
				activity.setStatus(4);
			}else if(now.after(activity.getEndTime())){
				activity.setStatus(5);
			}
		}
		return ajaxDone(activityService.updateActivity(activity));

	}

	/**
	 * 延期列表页面
	 */
	@RequiresPermissions("activity:read")
	@RequestMapping("delaylist")
	public String delaylist() {
		return "system/activity/delaylist";
	}

	/**
	 * 延期数据
	 */
	@RequiresPermissions("activity:read")
	@RequestMapping("delayquery")
	public ModelAndView delayquery(PageParam param, ActivityDelay dto,String userName,String titles) {
		return ajaxJsonEscape(activityDelayService.selectList(param, dto,userName,titles));
	}

	/** 取消页面 */
	@RequestMapping("cancelPage")
	public String cancelPage(String acid, ModelMap map) {
		ActivityCancel parm = new ActivityCancel();
		parm.setActivityId(acid);
		parm.setStatus(0);
		List<ActivityCancel> lst = activityCancelService.selectbyTerm(parm);
		if (lst.size() > 0) {
			map.addAttribute("dto", lst.get(0));
		} else {
			map.addAttribute("dto", new ActivityCancel());
		}
		Activity activity = activityService.getActivityByid(acid);
		map.addAttribute("activity", activity);
		return "system/activity/cancelPage";
	}

	/**
	 * 退款记录列表页面
	 */
	@RequiresPermissions("activity:read")
	@RequestMapping("refundlist")
	public String refundquery() {
		return "system/activity/refundlist";
	}

	/**
	 * 退款记录数据
	 */
	@RequiresPermissions("activity:read")
	@RequestMapping("refundquery")
	public ModelAndView refundquery(PageParam param, ActivityRefund dto,String titles) {
		return ajaxJsonEscape(activityRefundService.selectList(param, dto,titles));
	}
	/**
	 * 取消申请列表页面
	 */
	@RequiresPermissions("activity:read")
	@RequestMapping("cancellist")
	public String cancellist() {
		return "system/activity/cancellist";
	}
	
	/**
	 * 取消申请数据
	 */
	@RequiresPermissions("activity:read")
	@RequestMapping("cancelquery")
	public ModelAndView cancelquery(PageParam param, ActivityCancel dto,String userName,String titles) {
		return ajaxJsonEscape(activityCancelService.selectList(param, dto,userName,titles));
	}

	/**
	 * 取消审核
	 * 
	 * @param dto
	 * @param type
	 * @return
	 */
	@RequiresPermissions("activity:update")
	@RequestMapping("cancel")
	public ModelAndView cancel(ActivityCancel dto, Integer type) {
		String id = dto.getActivityId();
		Activity activity = activityService.getActivityByid(id);
		activity.setIscancel(0);
		// 同意
		dto.setStatus(type);
		String msg = activityCancelService.updateActivityCancel(dto);
		if (type == 1) {
			activity.setStatus(6);
			activity.setRefundDescription(DateUtils.datetimeToString(new Date()));
			/**
			 * 同意取消申请，给活动发布人及报名用户发消息
			 */
			TjyUser tjyUser = tjyUserService.selectById(activity.getCreateUserId());
			String content1 =AldyMessageUtil.activitycancelaccept(tjyUser.getTrueName(), activity.getTitles() );
			sendmessage(activity.getCreateUserId(), content1, 2, "ACTIVITY_CANCEL");
			sendmessage(activity.getCreateUserId(), content1, 4, "ACTIVITY_CANCEL");
			/**
			 * 模板名称: 活动取消（发起人）
			 *	模板ID: SMS_91005076  ACTIVITY_CANCEL
			 *	模板内容: ${name}老板，您所发布的${hdname}活动已取消，请您尽快前去核实，特此通知。
			 */
			//短信
			String content2="{name:\"" + tjyUser.getTrueName() + "\",hdname:\"" +activity.getTitles() + "\"}";
			sendmessage1(tjyUser.getId(),content2,1,AldyMessageUtil.MsmTemplateId.ACTIVITY_CANCEL,tjyUser.getMobile());
			/**
			 * 获取所有已报名且 未取消 未拒绝的报名记录
			 * 【AldyMessageUtil.SMSPRE】尊敬的***，您所报名的****活动已取消，望尽快与活动主办人核实，特此通知。
			 */
			List<Map> users = activityUserService.getusersbyactivityid(id);
			for (Map user : users) {
				String touser = (String) user.get("userId");
				String name = (String) user.get("userName");
				// 发消息
				String content =AldyMessageUtil.activitycancel(name, activity.getTitles());
				sendmessage(touser, content, 2, "ACTIVITY_CANCEL");
				sendmessage(touser, content, 4, "ACTIVITY_CANCEL");
				
				if((Double) user.get("payPrice")!=0&&!StringUtils.isEmpty((String) user.get("transactionId"))){
					// 退款
					ActivityRefund refund = new ActivityRefund();
					refund.setAmount((Double) user.get("payPrice"));
					refund.setCreateTime(new Date());
					refund.setStatus(0);
					refund.setType(2);
					refund.setUserId(touser);
					refund.setOrderId((String) user.get("orderId"));
					refund.setActivityId((String) user.get("activityId"));
					refund.setTransactionId((String) user.get("transactionId"));
					boolean bo = activityRefundService.insertRefund(refund);
				 }
			}
			//修改报名状态
			activityUserService.updatesignupstatus(id, 3);
		}
		// 拒绝
		else {
			/**
			 * 不同意取消，给活动发布人发消息
			 */
			String touser = activity.getCreateUserId();
			String name = activity.getCreateUserName();
			String content = AldyMessageUtil.activitycancelrefuse(name, activity.getTitles());
			sendmessage(touser, content, 2, "ACTIVITY_CANCEL");
			sendmessage(touser, content, 4, "ACTIVITY_CANCEL");
		}
		return ajaxDone(activityService.updateActivity(activity));
	}

	/**
	 * 管理员取消活动
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("cancelactivity")
	public ModelAndView cancelactivity(String id) {

		Activity activity = activityService.getActivityByid(id);
		activity.setStatus(6);
		activity.setRefundDescription(DateUtils.datetimeToString(new Date()));
		//【天九共享会】**老板，您所发布的****活动已取消，请您尽快前去核实，特此通知。
		// 发消息
		TjyUser tjyUser = tjyUserService.selectById(activity.getCreateUserId());
		String content1 = AldyMessageUtil.activitycancelToSponsor(tjyUser.getTrueName(), activity.getTitles());
		sendmessage(activity.getCreateUserId(), content1, 2, "ACTIVITY_CANCEL");
		sendmessage(activity.getCreateUserId(), content1, 4, "ACTIVITY_CANCEL");
		//短信
		String content2="{name:\"" + tjyUser.getTrueName() + "\",hdname:\"" +activity.getTitles() + "\"}";
		sendmessage1(tjyUser.getId(),content2,1,AldyMessageUtil.MsmTemplateId.ACTIVITY_CANCEL,tjyUser.getMobile());
		/**
		 * 获取所有已报名且 未取消 未拒绝的报名记录
		 */
		List<Map> users = activityUserService.getusersbyactivityid(id);
		/**
		 * 活动取消 发送微信 并且添加退款记录 尊敬的**，您所报名的****活动已取消，望尽快与主办人核实。
		 */
		for (Map user : users) {
			String touser = (String) user.get("userId");
			String name = (String) user.get("userName");
			// 发消息
			String content = AldyMessageUtil.activitycancel(name, activity.getTitles());
			sendmessage(touser, content, 2, "ACTIVITY_CANCEL");
			sendmessage(touser, content, 4, "ACTIVITY_CANCEL");
			if((Double) user.get("payPrice")!=0&&!StringUtils.isEmpty((String) user.get("transactionId"))){
				// 退款
				ActivityRefund refund = new ActivityRefund();
				refund.setAmount((Double) user.get("payPrice"));
				refund.setCreateTime(new Date());
				refund.setStatus(0);
				refund.setType(2);
				refund.setUserId(touser);
				refund.setOrderId((String) user.get("orderId"));
				refund.setActivityId((String) user.get("activityId"));
				refund.setTransactionId((String) user.get("transactionId"));
				boolean bo = activityRefundService.insertRefund(refund);
			 }
		}
		//修改报名状态
		activityUserService.updatesignupstatus(id, 3);
		return ajaxDone(activityService.updateActivity(activity));
	}

	// 校验电话号码

	@RequestMapping("validate")
	public ModelAndView validate(String phone) {
		boolean data = true;
		List<TjyUser> peo = tjyUserService.selectbymobile(phone, 2);
		if (peo.size() != 0) {
			data = false;
		}
		return ajaxJson(data);
	}

	/**
	 * 发消息方法
	 */
	public void sendmessage(String touser, String content, int type, String templateid) {
		MessageInfo messageInfo = new MessageInfo();
		messageInfo.setId(UUIDGenerator.getUUID());
		messageInfo.setDeleted(0);
		messageInfo.setType(type);// 微信
		messageInfo.setToUserId(touser);
		messageInfo.setCreateTime(new Date());
		// 组装内容
		if(type==2){
			String con = WxMsmUtil.getTextMessageContent(content);
			messageInfo.setContent(con);
		}else{
			messageInfo.setContent(content);
		}
		messageInfo.setTemplateId(templateid);
		messageInfo.setStatus(0);// 未发送
		messageInfo.setWxMsgType(1);///** 微信消息类型（1：文本消息2：图文消息） */
		messageInfoService.addMessageInfo(messageInfo);
	}
	/**
	 * 发消息方法
	 */
	public void sendmessage1(String touser, String content, int type, String templateid,String mobile ) {
		MessageInfo messageInfo = new MessageInfo();
		messageInfo.setId(UUIDGenerator.getUUID());
		messageInfo.setDeleted(0);
		messageInfo.setToUserId(touser);
		messageInfo.setType(type);// 微信
		messageInfo.setMobile(mobile);
		messageInfo.setCreateTime(new Date());
		// 组装内容
		if(type==2){
			String con = WxMsmUtil.getTextMessageContent(content);
			messageInfo.setContent(con);
		}else{
			messageInfo.setContent(content);
		}
		messageInfo.setTemplateId(templateid);
		messageInfo.setStatus(0);// 未发送
		messageInfo.setWxMsgType(1);///** 微信消息类型（1：文本消息2：图文消息） */
		messageInfoService.addMessageInfo(messageInfo);
	}
	
}
