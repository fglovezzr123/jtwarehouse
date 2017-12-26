package com.wing.socialcontact.front.action;

import java.io.BufferedReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.jdom.JDOMException;
import org.owasp.esapi.ESAPI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Maps;
import com.wing.socialcontact.common.action.BaseAction;
import com.wing.socialcontact.common.model.DataGrid;
import com.wing.socialcontact.common.model.Member;
import com.wing.socialcontact.common.model.PageParam;
import com.wing.socialcontact.config.MsgConfig;
import com.wing.socialcontact.front.util.ApplicationPath;
import com.wing.socialcontact.front.util.EsapiTest;
import com.wing.socialcontact.front.util.JavaMapUtil;
import com.wing.socialcontact.service.wx.api.IActivityCancelService;
import com.wing.socialcontact.service.wx.api.IActivityDelayService;
import com.wing.socialcontact.service.wx.api.IActivityRefundService;
import com.wing.socialcontact.service.wx.api.IActivityService;
import com.wing.socialcontact.service.wx.api.IActivityTagService;
import com.wing.socialcontact.service.wx.api.IActivityUserService;
import com.wing.socialcontact.service.wx.api.ICouponGenerateService;
import com.wing.socialcontact.service.wx.api.ICouponLogService;
import com.wing.socialcontact.service.wx.api.ICouponService;
import com.wing.socialcontact.service.wx.api.IMessageInfoService;
import com.wing.socialcontact.service.wx.api.IMyCollectionService;
import com.wing.socialcontact.service.wx.api.IRefundInstructionService;
import com.wing.socialcontact.service.wx.api.ITjyUserService;
import com.wing.socialcontact.service.wx.api.IUserIntegralLogService;
import com.wing.socialcontact.service.wx.api.IWxUserService;
import com.wing.socialcontact.service.wx.bean.Activity;
import com.wing.socialcontact.service.wx.bean.ActivityCancel;
import com.wing.socialcontact.service.wx.bean.ActivityDelay;
import com.wing.socialcontact.service.wx.bean.ActivityRefund;
import com.wing.socialcontact.service.wx.bean.ActivityTag;
import com.wing.socialcontact.service.wx.bean.ActivityUser;
import com.wing.socialcontact.service.wx.bean.Coupon;
import com.wing.socialcontact.service.wx.bean.CouponGenerate;
import com.wing.socialcontact.service.wx.bean.CouponLog;
import com.wing.socialcontact.service.wx.bean.MessageInfo;
import com.wing.socialcontact.service.wx.bean.Project;
import com.wing.socialcontact.service.wx.bean.TjyUser;
import com.wing.socialcontact.sys.bean.SyDistrict;
import com.wing.socialcontact.sys.service.IDistrictService;
import com.wing.socialcontact.sys.service.IListValuesService;
import com.wing.socialcontact.util.AldyMessageUtil;
import com.wing.socialcontact.util.ConfigUtil;
import com.wing.socialcontact.util.Constants;
import com.wing.socialcontact.util.PayCommonUtil;
import com.wing.socialcontact.util.ServletUtil;
import com.wing.socialcontact.util.UUIDGenerator;
import com.wing.socialcontact.util.WxMsmUtil;
import com.wing.socialcontact.util.XMLUtil;
import com.wing.socialcontact.wechat.api.PayMchAPI;
import com.wing.socialcontact.wechat.entity.MchOrderquery;
import com.wing.socialcontact.wechat.entity.Unifiedorder;
import com.wing.socialcontact.wechat.resp.MchOrderInfoResult;
import com.wing.socialcontact.wechat.resp.UnifiedorderResult;

/**
 * 
 * //TODO 人脉圈-以玩会友、以书会友
 * @author lajz
 */

@Controller
@RequestMapping("")
public class ActivityAction extends BaseAction{
    
    @Autowired
    private IActivityService activityService;
    @Autowired
    private IActivityTagService activityTagService;
	@Autowired
	private IDistrictService districtService;
	@Autowired
	private IListValuesService listValuesService;
	@Autowired
	private IActivityUserService activityUserService;
	@Autowired
	private ITjyUserService tjyUserService;
	@Autowired
	private IMyCollectionService  myCollectionService;
	@Autowired
	private IMessageInfoService messageInfoService;
	@Autowired
	private IActivityRefundService activityRefundService;
	@Autowired
	private IWxUserService wxUserService;
	@Autowired
	private IRefundInstructionService refundInstructionService;
	@Autowired
	private IActivityCancelService activityCancelService;
	@Autowired
	private IActivityDelayService activityDelayService;
	@Autowired
	private IUserIntegralLogService userIntegralLogService;
	
	@Autowired
	private ICouponService couponService; 
	@Autowired
	private ICouponGenerateService couponGenerateService; 
	@Autowired
	private ICouponLogService couponLogService;
    
    /**
     * 跳转到以玩会友页面
     * 
     * @return
     */
    @RequestMapping("/m/activity/friendPlayPage")
    public String playHomePage(HttpServletRequest request){
		Member me = ServletUtil.getMember(request);
		if (null == me) {
		return "login";
		}
        return "netWork/friendPlay";
    }
    /**
     * 跳转到以书会友页面
     * 
     * @return
     */
    @RequestMapping("/m/activity/bookPlayPage")
    public String bookPlayPage(HttpServletRequest request){
		Member me = ServletUtil.getMember(request);
		if (null == me) {
		return "login";
		}
    	return "netWork/bookPlay";
    }
    /**
     * laijz
     * // 根据活动类别(以书会友，以玩会友),是否推荐( 0 不推荐   1 推荐),获取条数查询活动标签
     * @param classId 活动类别(以书会友，以玩会友)
     * @param recommnend 是否推荐( 0 不推荐   1 推荐)
     * @param topNum 获取条数
     * @return
     */
    @RequestMapping("/m/activity/selTagsList")
    public @ResponseBody Map selTagsList(HttpServletRequest request,HttpServletResponse response,String classId,Integer recommnend, Integer topNum){
    	Member me = ServletUtil.getMember(request);
		if (null == me) {
			return super.getAjaxResult("302", "登录超时，请重新登录", null);
		}
		String userId = me.getId();
		if (StringUtils.isEmpty(userId)) {
			return super.getAjaxResult("302", "登录超时，请重新登录", null);
		}
        //标签 
        List<ActivityTag> tags = activityTagService.selTags(classId);
        //分类
        return super.getSuccessAjaxResult(Constants.AJAX_MSG_SUCCESS, tags);
    }
    /**
     * 条件查询活动列表
     * @param request
     * @param response
     * @param classId  类型  1以玩会友  2以书会友 
     * @param titles   搜索条件   关键字
     * @param page  页码 默认1
     * @param size 每页条数  默认10
     * @param recommendEnable 首页推荐  
     * @param pattren  参与方式
     * @param status  活动状态
     * @param province 省 id
     * @param city  市id
     * @return
     */
    @RequestMapping("/m/activity/selActivityList")
    public @ResponseBody Map selActivityList(HttpServletRequest request,HttpServletResponse response,Activity activity,Integer page,Integer size){
    	Member me = ServletUtil.getMember(request);
		if (null == me) {
			return super.getAjaxResult("302", "登录超时，请重新登录", null);
		}
		String userId = me.getId();
		if (StringUtils.isEmpty(userId)) {
			return super.getAjaxResult("302", "登录超时，请重新登录", null);
		}
		if (page==null||page<1) {
			page = 1;
		}
		if (size==null||size<1) {
			size = 10;
		}
		List<Map> res= new ArrayList<Map>();
		res =  activityService.getActivityByTermf(activity,page,size,userId);
        return super.getSuccessAjaxResult(Constants.AJAX_MSG_SUCCESS, res);
    }
    /**
     * 跳转到活动列表页面
     */
    @RequestMapping("/m/activity/activitylistPage")
    public String activitylistPage(String id,ModelMap map,HttpServletRequest request){
		Member me = ServletUtil.getMember(request);
		if (null == me) {
		return "login";
		}
    	map.addAttribute("id", id);
        return "netWork/action2";
    }
    /**
     * 跳转到活动详情页面
     */
    @RequestMapping("/m/activity/activityDetailPage")
    public String activityDetailPage(String id,ModelMap map,HttpServletRequest request){
    	Member me = ServletUtil.getMember(request);
		if (null == me) {
			return "login";
		}
		String userId = me.getId();
		if (StringUtils.isEmpty(userId)) {
			return "login";
		}
		TjyUser user=tjyUserService.selectByPrimaryKey(userId);
		if(!"1".equals(user.getIsRealname()+"")){
			return "commons/to_recon";
		}
    	map.addAttribute("id", id);
    	/**
		 * 查询用户是否报名过  状态  1   4 
		 */
		 List<ActivityUser> users =   activityUserService.getsignupusers(id,me.getId(),2);
		 if(users.size()>0){
			 map.addAttribute("issignup", 1);
		 }else{
			 map.addAttribute("issignup", 0);
		 }
        return "netWork/activityDetail";
    }
    /**
     * 跳转到活动详情页面
     */
    @RequestMapping("/m/activity/detial")
    public String detial(String id,ModelMap map,HttpServletRequest request){
		Member me = ServletUtil.getMember(request);
		if (null == me) {
		return "login";
		}
    	map.addAttribute("id", id);
    	return "netWork/detial";
    }
    /**
     * 根据活动id查询活动详情
     */
    @RequestMapping("/m/activity/activityDetail")
    public @ResponseBody Map activityDetail(HttpServletRequest request,HttpServletResponse response,String id){
    	Member me = ServletUtil.getMember(request);
		if (null == me) {
			return super.getAjaxResult("302", "登录超时，请重新登录", null);
		}
		String userId = me.getId();
		if (StringUtils.isEmpty(userId)) {
			return super.getAjaxResult("302", "登录超时，请重新登录", null);
		}
		if (StringUtils.isEmpty(id)) {
			return super.getAjaxResult("999", "参数错误", null);
		}
		Map map = new HashMap();
		map = activityService.getactivityDetailByid(id);
		boolean iscol=myCollectionService.iscollected(userId, id, 3);
		map.put("iscollection", iscol);
        return super.getSuccessAjaxResult(Constants.AJAX_MSG_SUCCESS, map);
    }
    /**
     * 跳转到发布活动页面
     */
    @RequestMapping("/m/activity/addActivityPage")
    public String addActivityPage(String type,ModelMap map,HttpServletRequest request){
    	Member me = ServletUtil.getMember(request);
		if (null == me) {
			return "login";
		}
		String userId = me.getId();
		if (StringUtils.isEmpty(userId)) {
			return "login";
		}
		TjyUser tjyUser=tjyUserService.selectByPrimaryKey(userId);
		if(!"1".equals(tjyUser.getIsRealname()+"")){
			return "commons/to_recon";
		}
    	map.addAttribute("type", type);
    	// 获取省
		List provinceList = districtService.selectDistrictByType("1");
		map.addAttribute("provinceList", provinceList);
		// 获取市
		List cityList = districtService.selectDistrictByType("2");
		map.addAttribute("cityList", cityList);
		// 获取区
		List areaList = districtService.selectDistrictByType("3");
		map.addAttribute("areaList", areaList);
		//获取所有标签
		List tagList = activityTagService.selectalltag();
		map.addAttribute("tagList", tagList);
        return "netWork/activityrepublic";
    }
    
    /**
     * 活动添加接口
     */
    @RequestMapping("/add/m/activity/activityAdd")
    public @ResponseBody Map addActivity(HttpServletRequest request,HttpServletResponse response,Activity activity){
    	activity.setTitles(EsapiTest.stripXSS(activity.getTitles()));
    	activity.setPlace(EsapiTest.stripXSS(activity.getPlace()));
    	activity.setContents(EsapiTest.stripXSS(activity.getContents()));
    	Member me = ServletUtil.getMember(request);
		if (null == me) {
			return super.getAjaxResult("302", "登录超时，请重新登录", null);
		}
		String userId = me.getId();
		if (StringUtils.isEmpty(userId)) {
			return super.getAjaxResult("302", "登录超时，请重新登录", null);
		}
		//此项每日积分上限为30
		userIntegralLogService.addLntAndEmp(userId, "task_0007");
				
		TjyUser user = tjyUserService.selectById(userId);
		activity.setCreateTime(new Date());
		activity.setCreateUserId(userId);
		activity.setCreateUserName(user.getNickname());
		activity.setIsdelay(0);
		activity.setIscancel(0);
		activity.setSponsor(user.getComName());
		activity.setSponsorIntroduce(user.getComProfile());
		activity.setRecommendEnable(0);
		activity.setRecommendList(0);
		activity.setShowEnable(1);
		activity.setSort(0);
		activity.setIscod(0);
		if(null==activity.getId()||"".equals(activity.getId())){
			activity.setStatus(2);
			activityService.addActivity(activity);
		}else{
			activity.setStatus(1);
			activityService.updateActivity(activity);
		}
        return super.getSuccessAjaxResult(Constants.AJAX_MSG_SUCCESS, null);
    }
    /**
     * 跳转到分类页面
     * 
     */
    @RequestMapping("/m/activity/activityTagsPage")
    public String activityTagsPage(String classid,ModelMap map,HttpServletRequest request){
		Member me = ServletUtil.getMember(request);
		if (null == me) {
		return "login";
		}
    	map.addAttribute("classid", classid);
        return "netWork/activityclass";
    }
    /**
     * 查询分类标签
     * classid 1:以玩会友    2：以书会友
     */
    @RequestMapping("/m/activity/activityTags")
    public @ResponseBody Map activityTags(HttpServletRequest request,HttpServletResponse response,String classid){
    	Member me = ServletUtil.getMember(request);
		if (null == me) {
			return super.getAjaxResult("302", "登录超时，请重新登录", null);
		}
		String userId = me.getId();
		if (StringUtils.isEmpty(userId)) {
			return super.getAjaxResult("302", "登录超时，请重新登录", null);
		}

		if (StringUtils.isEmpty(classid)) {
			return super.getAjaxResult("999", "参数错误", null);
		}
		List<Map<String,Object>> list  = activityTagService.activityTag(classid);
        return super.getSuccessAjaxResult(Constants.AJAX_MSG_SUCCESS, list);
    }
    
    /**
     * 跳转到活动报名页面
     */
    @RequestMapping("/m/my/signupPage")
    public String signupPage(HttpServletRequest request,HttpServletResponse response,String id,ModelMap map){
		/**
		 * 返回活动详情
		 */ 
	    Map	res = activityService.getactivityDetailByid(id);
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	    String startTime = sdf.format(res.get("startTime"));
	    res.put("startTime", startTime);
	    map.addAttribute("detail", res);
	    /**
	     * 返回用户信息
	     */
	    Member me = ServletUtil.getMember(request);
		if (null == me) {
			return "login";
		}
		String userId = me.getId();
		if (StringUtils.isEmpty(userId)) {
			return "login";
		}
		TjyUser user=tjyUserService.selectByPrimaryKey(userId);
		if(!"1".equals(user.getIsRealname()+"")){
			return "commons/to_recon";
		}
		map.addAttribute("user", user);
		/**
		 * 查询用户是否报名过  状态  1   4 
		 */
		 List<ActivityUser> users =   activityUserService.getsignupusers(id,me.getId(),2);
		 if(users.size()>0){
			 map.addAttribute("issignup", 1);
		 }else{
			 map.addAttribute("issignup", 0);
		 }
		String instruction = refundInstructionService.getrefundinstructionByid(1).getContent();
		map.put("instruction", instruction);
        return "netWork/activitysignup";
    }
    /**
     * 添加报名信息接口
     */
    @RequestMapping("/m/activity/activitysignup")
    public @ResponseBody Map activitysignup(HttpServletRequest request,HttpServletResponse response,ActivityUser user){
    	Member me = ServletUtil.getMember(request);
		if (null == me) {
			return super.getAjaxResult("302", "登录超时，请重新登录", null);
		}
		String userId = me.getId();
		if (StringUtils.isEmpty(userId)) {
			return super.getAjaxResult("302", "登录超时，请重新登录", null);
		}
		if (StringUtils.isEmpty(user.getUserName())) {
			return super.getAjaxResult("999", "参数错误", null);
		}
		if (StringUtils.isEmpty(user.getPhone())) {
			return super.getAjaxResult("999", "参数错误", null);
		}
		if (StringUtils.isEmpty(user.getActivityId())) {
			return super.getAjaxResult("999", "参数错误", null);
		}
		user.setCreateTime(new Date());
		user.setUserId(userId);
		user.setStatus(1);
		if(activityUserService.addActivityUser(user)){
			/**
			 * 发送报名成功消息
			 * 
			 * 尊敬的**，您所发布的的****活动收到新的报名，望尽快处理。
			 */
			Activity activity = activityService.getActivityByid(user.getActivityId());
			// 组装内容 
			//String content = "尊敬的"+activity.getCreateUserName()+",您所发布的的"+activity.getTitles()+"活动收到新的报名，望尽快处理。";
			String content = AldyMessageUtil.activitysignup(activity.getCreateUserName(), activity.getTitles());
			sendmessage(activity.getCreateUserId(),content,2,"ACTIVITY_SIGNUP");
			sendmessage(activity.getCreateUserId(),content,4,"ACTIVITY_SIGNUP");
			return super.getSuccessAjaxResult(Constants.AJAX_MSG_SUCCESS, null);
		}
		return super.getSuccessAjaxResult(Constants.AJAX_MSG_ERROR, null);
    }
    /**
     * 我发布的活动列表
     */
    @RequestMapping("/m/activity/myactivities")
    public @ResponseBody Map myactivities(HttpServletRequest request,HttpServletResponse response,Integer page,Integer size,Integer status){
    	Member me = ServletUtil.getMember(request);
		if (null == me) {
			return super.getAjaxResult("302", "登录超时，请重新登录", null);
		}
		String userId = me.getId();
		if (StringUtils.isEmpty(userId)) {
			return super.getAjaxResult("302", "登录超时，请重新登录", null);
		}
		if (page==null||page<1) {
			page = 1;
		}
		if (size==null||size<1) {
			size = 10;
		}
		if (status<1) {
			status = 1;
		}
		List list  = activityService.getmyactivitiesbyid(userId,page,size,status);
		return super.getSuccessAjaxResult(Constants.AJAX_MSG_SUCCESS, list);
    }
    /**
     * 我报名的活动列表
     */
    @RequestMapping("/m/activity/mysignup")
    public @ResponseBody Map mysignup(HttpServletRequest request,HttpServletResponse response,Integer page,Integer size){
    	Member me = ServletUtil.getMember(request);
		if (null == me) {
			return super.getAjaxResult("302", "登录超时，请重新登录", null);
		}
		String userId = me.getId();
		if (StringUtils.isEmpty(userId)) {
			return super.getAjaxResult("302", "登录超时，请重新登录", null);
		}
		if (page==null||page<1) {
			page = 1;
		}
		if (size==null||size<1) {
			size = 10;
		}
		List list  = activityUserService.getmysignupbyid(userId, page, size);
		return super.getSuccessAjaxResult(Constants.AJAX_MSG_SUCCESS, list);
    }
    /**
     * 获取所有省接口
     */
    @RequestMapping("/m/activity/getprovinces")
    public @ResponseBody Map allprovince(HttpServletRequest request,HttpServletResponse response){
    	Member me = ServletUtil.getMember(request);
    	if (null == me) {
    		return super.getAjaxResult("302", "登录超时，请重新登录", null);
    	}
    	String userId = me.getId();
    	if (StringUtils.isEmpty(userId)) {
    		return super.getAjaxResult("302", "登录超时，请重新登录", null);
    	}
    	// 获取省
    	List list = districtService.selectDistrictByType("1");
    	return super.getSuccessAjaxResult(Constants.AJAX_MSG_SUCCESS, list);
    }
    /**
     * 根据省id获取所有市信息
     */
    @RequestMapping("/m/activity/getcitys")
    public @ResponseBody Map citys(HttpServletRequest request,HttpServletResponse response,String provinceid){
    	Member me = ServletUtil.getMember(request);
    	if (null == me) {
    		return super.getAjaxResult("302", "登录超时，请重新登录", null);
    	}
    	String userId = me.getId();
    	if (StringUtils.isEmpty(userId)) {
    		return super.getAjaxResult("302", "登录超时，请重新登录", null);
    	}
    	if (StringUtils.isEmpty(provinceid)) {
    		return super.getAjaxResult("999", "参数有误", null);
    	}
    	// 获取市
    	List list = districtService.selectDistrictBySuperId(provinceid);
    	return super.getSuccessAjaxResult(Constants.AJAX_MSG_SUCCESS, list);
    }
    /**
     * 获取所有省市二级
     */
    
    @RequestMapping("/m/activity/getproandcity")
    public @ResponseBody Map getproandcity(HttpServletRequest request,HttpServletResponse response){
    	Member me = ServletUtil.getMember(request);
    	if (null == me) {
    		return super.getAjaxResult("302", "登录超时，请重新登录", null);
    	}
    	String userId = me.getId();
    	if (StringUtils.isEmpty(userId)) {
    		return super.getAjaxResult("302", "登录超时，请重新登录", null);
    	}
    	List<Map> res= new ArrayList<Map>();
    	// 获取省
    	List<Map> list = districtService.selectDistrictByType("1");
    	/**
    	 * 根据省id获取市
    	 */
    	for (Map map :list) {
			String id= (String) map.get("id"); 
			List citylist = districtService.selectDistrictBySuperId(id);
			map.put("city", citylist);
			res.add(map);
		}
    	return super.getSuccessAjaxResult(Constants.AJAX_MSG_SUCCESS, res);
    }
    /**
     * 获取所有分类标签二级
     */
    
    @RequestMapping("/m/activity/getclassandtag")
    public @ResponseBody Map getclassandtag(HttpServletRequest request,HttpServletResponse response){
    	Member me = ServletUtil.getMember(request);
    	if (null == me) {
    		return super.getAjaxResult("302", "登录超时，请重新登录", null);
    	}
    	String userId = me.getId();
    	if (StringUtils.isEmpty(userId)) {
    		return super.getAjaxResult("302", "登录超时，请重新登录", null);
    	}
    	List<Map> res= new ArrayList<Map>();
    	Map map1 =new HashMap<>();
    	String classid1= "1"; 
		List tags1 = activityTagService.activityTag(classid1);
		map1.put("id", "1");
		map1.put("className", "以玩会友");
		map1.put("tags", tags1);
		res.add(map1);
		Map map2 =new HashMap<>();
		String classid2= "2"; 
		List tags2 = activityTagService.activityTag(classid2);
		map2.put("id", "2");
		map2.put("className", "以书会友");
		map2.put("tags", tags2);
		res.add(map2);
    	return super.getSuccessAjaxResult(Constants.AJAX_MSG_SUCCESS, res);
    }
    /**
     * 活动发布者取消活动
     * 
     * @param id 活动id
     */
    @RequestMapping("/m/activity/activitycancel")
    public @ResponseBody Map activitycancel(HttpServletRequest request,HttpServletResponse response,String id){
    	Member me = ServletUtil.getMember(request);
		if (null == me) {
			return super.getAjaxResult("302", "登录超时，请重新登录", null);
		}
		String userId = me.getId();
		if (StringUtils.isEmpty(userId)) {
			return super.getAjaxResult("302", "登录超时，请重新登录", null);
		}
		if (StringUtils.isEmpty(id)) {
			return super.getAjaxResult("999", "参数错误", null);
		}
		Activity activity = activityService.getActivityByid(id);
		activity.setStatus(6);
		String cancel = activityService.updateActivity(activity);
		if(cancel.equals(MsgConfig.MSG_KEY_SUCCESS)){
			/**
			 * 更新报名状态  1、待确认  2 用户取消  3 活动取消 4同意 5 拒绝
			 */
			//String reString = activityUserService.updatesignupstatus(id,3);
			 /**
			  * 获取所有已报名且 未取消  未拒绝的报名记录
			  */
			 List<Map> users = activityUserService.getusersbyactivityid(id);
			 /**
			  * 活动取消  发送微信  
			  * 尊敬的**，您所报名的****活动已取消，望尽快与主办人核实。
			  */
			 for (Map user : users) {
				 String touser = (String) user.get("userId");
				 String name = (String) user.get("userName");
				// 组装内容
				//String content = "【"+AldyMessageUtil.SMSPRE+"】尊敬的"+name+",您所报名的"+activity.getTitles()+"活动已取消，望尽快与主办人核实。";
				 String content = AldyMessageUtil.activitycancel(name, activity.getTitles());
				sendmessage(touser,content,2,"ACTIVITY_CANCEL");
				sendmessage(touser,content,4,"ACTIVITY_CANCEL");
			}
		}
        return super.getSuccessAjaxResult(Constants.AJAX_MSG_SUCCESS, cancel);
    }
    /**
     * 报名用户取消报名（活动未开始可以）  原型上暂时未有用户取消报名
     * 活动id
     */
    @RequestMapping("/m/activity/delactivity")
    public @ResponseBody Map delactivity(HttpServletRequest request,HttpServletResponse response,String signupid){
    	Member me = ServletUtil.getMember(request);
		if (null == me) {
			return super.getAjaxResult("302", "登录超时，请重新登录", null);
		}
		String userId = me.getId();
		if (StringUtils.isEmpty(userId)) {
			return super.getAjaxResult("302", "登录超时，请重新登录", null);
		}

		if (StringUtils.isEmpty(signupid)) {
			return super.getAjaxResult("999", "参数错误", null);
		}
			/**
			 * 更新报名状态  1、待确认  2 用户取消  3 活动取消   4已确认 5已拒绝
			 */
		String reString = activityUserService.updatestatusbyid(signupid,2);
        return super.getSuccessAjaxResult(Constants.AJAX_MSG_SUCCESS, reString);
    }
    
    /**
     * 跳转到我发布的活动列表页面
     */
    @RequestMapping("/m/activity/myAction")
    public String myAction(HttpServletRequest request){
		Member me = ServletUtil.getMember(request);
		if (null == me) {
		return "login";
		}
        return "mine/myAction";
    }
    /**
     * 跳转到我收藏的活动列表页面
     */
    @RequestMapping("/m/activity/mycolAction")
    public String mycolAction(HttpServletRequest request){
		Member me = ServletUtil.getMember(request);
		if (null == me) {
		return "login";
		}
    	return "mine/actioncollect";
    }
    
    /**
	 * 申请延期界面
	 * 
	 * @return
	 */
	@RequestMapping("/m/activity/delayedit")
	public  String delayedit(HttpServletRequest request, ModelMap modelMap) {
		String id = request.getParameter("id");// 活动id
		Member me = ServletUtil.getMember(request);
		if (null == me) {
			return "login";
		}
		String userId = me.getId();
		if (StringUtils.isEmpty(userId)) {
			return "login";
		}
		modelMap.addAttribute("user", me);
		modelMap.addAttribute("id", id);
		return "mine/delay_edit";
	}
	/**
	 * 申请延期接口
	 */
	@RequestMapping("/m/activity/delay")
	public @ResponseBody Map delay(HttpServletRequest request, ActivityDelay dto) {
		Member me = ServletUtil.getMember(request);
		if (null == me) {
			return super.getAjaxResult("302", "登录超时，请重新登录", null);
		}
		String userId = me.getId();
		if (StringUtils.isEmpty(userId)) {
			return super.getAjaxResult("302", "登录超时，请重新登录", null);
		}

		if (StringUtils.isEmpty(dto.getActivityId())) {
			return super.getAjaxResult("999", "参数错误", null);
		}
		
		//判断是否有未审核的的延期申请
		ActivityDelay parm = new ActivityDelay();
		parm.setActivityId(dto.getActivityId());
		parm.setStatus(0);
		List<ActivityDelay> lst = activityDelayService.selectbyTerm(parm);
		
		if(lst.size()>0){
			return super.getAjaxResult("1", "您之前提交的申请正在审核中，请耐心等待。", null);
		}
		
		Activity activity = activityService.getActivityByid(dto.getActivityId());
		activity.setIsdelay(1);
		String reString = activityService.updateActivity(activity);
		
		dto.setCreateTime(new Date());
		dto.setStatus(0);
		dto.setUserId(userId);
		activityDelayService.addDelay(dto);
        return super.getSuccessAjaxResult(Constants.AJAX_MSG_SUCCESS,null );
	}
	/**
	 * 申请取消活动界面
	 * 
	 * @return
	 */
	@RequestMapping("/m/activity/canceledit")
	public  String canceledit(HttpServletRequest request, ModelMap modelMap) {
		String id = request.getParameter("id");// 活动id
		Member me = ServletUtil.getMember(request);
		if (null == me) {
			return "login";
		}
		String userId = me.getId();
		if (StringUtils.isEmpty(userId)) {
			return "login";
		}
		modelMap.addAttribute("user", me);
		modelMap.addAttribute("id", id);
		return "mine/cancel_edit";
	}
	/**
	 * 申请延期接口
	 */
	@RequestMapping("/m/activity/cancel")
	public @ResponseBody Map cancel(HttpServletRequest request,ActivityCancel dto) {
		Member me = ServletUtil.getMember(request);
		if (null == me) {
			return super.getAjaxResult("302", "登录超时，请重新登录", null);
		}
		String userId = me.getId();
		if (StringUtils.isEmpty(userId)) {
			return super.getAjaxResult("302", "登录超时，请重新登录", null);
		}
		//判断是否有未审核的的取消申请
		ActivityCancel parm = new ActivityCancel();
		parm.setActivityId(dto.getActivityId());
		parm.setStatus(0);
		List<ActivityCancel> lst = activityCancelService.selectbyTerm(parm);
		
		if(lst.size()>0){
			return super.getAjaxResult("1", "您之前提交的申请正在审核中，请耐心等待。", null);
		}
		
		Activity activity = activityService.getActivityByid(dto.getActivityId());
		activity.setIscancel(1);
		String reString = activityService.updateActivity(activity);
		
		dto.setCreateTime(new Date());
		dto.setStatus(0);
		dto.setUserId(userId);
		activityCancelService.addCancel(dto);
		return super.getSuccessAjaxResult(Constants.AJAX_MSG_SUCCESS,null );
	}
	/**
	 * 活动报名管理页面
	 * 
	 * @return
	 */
	@RequestMapping("/m/activity/bmglpage")
	public  String bmglpage(HttpServletRequest request, ModelMap modelMap) {
		String id = request.getParameter("id");// 活动id
		Member me = ServletUtil.getMember(request);
		if (null == me) {
			return "login";
		}
		String userId = me.getId();
		if (StringUtils.isEmpty(userId)) {
			return "login";
		}
		modelMap.addAttribute("id", id);
		return "mine/actionbmgl2";
	}
	/**
	 * 报名管理数据分页查询
	 * 
	 * @return
	 */
	@RequestMapping("/m/activity/bmgllist2")
	public @ResponseBody Map bmgllist2(HttpServletRequest request, String id,Integer page,Integer size,Integer status) {
		Member me = ServletUtil.getMember(request);
		if (null == me) {
			return super.getAjaxResult("302", "登录超时，请重新登录", null);
		}
		String userId = me.getId();
		if (StringUtils.isEmpty(userId)) {
			return super.getAjaxResult("302", "登录超时，请重新登录", null);
		}
		if (StringUtils.isEmpty(id)) {
			return super.getAjaxResult("999", "参数错误", null);
		}
		if (page==null||page<1) {
			page = 1;
		}
		if (status==null||status<1) {
			return super.getAjaxResult("999", "参数错误", null);
		}
		if (size==null||size<1) {
			size = 10;
		}
		Map res =  activityUserService.selectbyactivityid1(id,page,size,status);
		
		return super.getSuccessAjaxResult(Constants.AJAX_MSG_SUCCESS,res );
	}
	/**
	 * 报名审核(4同意  5 拒绝)
	 */
	@RequestMapping("/m/activity/bmglcheck")
	public @ResponseBody Map bmglcheck(HttpServletRequest request,String id,Integer type) {
		Member me = ServletUtil.getMember(request);
		if (null == me) {
			return super.getAjaxResult("302", "登录超时，请重新登录", null);
		}
		String userId = me.getId();
		if (StringUtils.isEmpty(userId)) {
			return super.getAjaxResult("302", "登录超时，请重新登录", null);
		}
		if (StringUtils.isEmpty(id)) {
			return super.getAjaxResult("999", "参数错误", null);
		}
		if (type<1) {
			return super.getAjaxResult("999", "参数错误", null);
		}
		 activityUserService.updatestatusbyid(id, type);
		 if(type==5){
			 /**
			  * 添加退款记录
			  */
			//添加微信退款记录   
			 ActivityUser user = activityUserService.getactivityUserByid(id);
			 if(user.getPayPrice()!=0&&!StringUtils.isEmpty(user.getTransactionId())){
				 ActivityRefund refund = new ActivityRefund();
				 refund.setAmount(user.getPayPrice());
				 refund.setCreateTime(new Date());
				 refund.setStatus(0);
				 refund.setType(1);
				 refund.setUserId(user.getUserId());
				 refund.setOrderId(user.getOrderId());
				 refund.setActivityId(user.getActivityId());
				 refund.setTransactionId(user.getTransactionId());
				 boolean bo = activityRefundService.insertRefund(refund);
			 }
			 /**
			  * 报名被拒绝  发送微信  并且退款
			  * 尊敬的**，您所报名的****活动未通过主办人审核，望尽快于主办人进行沟通。
			  * //【AldyMessageUtil.SMSPRE】尊敬的***，您所报名的****活动未通过审核，望尽快于活动主办人进行沟通。立即前往
			  */
			 Activity activity = activityService.getActivityByid(user.getActivityId());
			// 组装内容
			//String content = "【"+AldyMessageUtil.SMSPRE+"】尊敬的"+user.getUserName()+",您所报名的"+activity.getTitles()+"活动未通过审核，望尽快于活动主办人进行沟通。";
			 String content = AldyMessageUtil.activityrefuse(user.getUserName(),activity.getTitles(),activity.getId());
			sendmessage(user.getUserId(),content,2,"ACTIVITY_REFUSE");
			sendmessage(user.getUserId(),content,4,"ACTIVITY_REFUSE");
			/**
			 * 
			 */
		 }
        return super.getSuccessAjaxResult(Constants.AJAX_MSG_SUCCESS,null );
	}
	/**
	 * 报名审核(4同意  5 拒绝)
	 */
	@RequestMapping("/m/activity/bmglcheck2")
	public @ResponseBody Map bmglcheck2(HttpServletRequest request,String[] ids,Integer type) {
		Member me = ServletUtil.getMember(request);
		if (null == me) {
			return super.getAjaxResult("302", "登录超时，请重新登录", null);
		}
		String userId = me.getId();
		if (StringUtils.isEmpty(userId)) {
			return super.getAjaxResult("302", "登录超时，请重新登录", null);
		}
		if (ids.length==0) {
			return super.getAjaxResult("999", "参数错误", null);
		}
		if (type<1) {
			return super.getAjaxResult("999", "参数错误", null);
		}
		for(String id :ids){
				activityUserService.updatestatusbyid(id, type);
				if(type==4){
					/**
					 * 报名被拒绝  发送微信  并且退款
					 * 尊敬的**，您所报名的****活动未通过主办人审核，望尽快于主办人进行沟通。
					 */
					ActivityUser user = activityUserService.getactivityUserByid(id);
					Activity activity = activityService.getActivityByid(user.getActivityId());
					// 组装内容
					String content = AldyMessageUtil.activityaccept(user.getUserName(),activity.getTitles(),activity.getId());//"【"+AldyMessageUtil.SMSPRE+"】尊敬的"+user.getUserName()+",您所报名的"+activity.getTitles()+"活动已通过主办人审核，望尽快于主办人进行沟通。<a href='"+ConfigUtil.DOMAIN+"/wxfront/m/activity/activityDetailPage.do?id="+activity.getId()+"'>立即前往</a>";
//					String content = "【"+AldyMessageUtil.SMSPRE+"】尊敬的"+user.getUserName()+",您所报名的"+activity.getTitles()+"活动已通过主办人审核，望尽快于主办人进行沟通。<a href='"+ConfigUtil.DOMAIN+"/wxfront/m/activity/activityDetailPage.do?id="+activity.getId()+"'>立即前往</a>";
					sendmessage(user.getUserId(),content,2,"ACTIVITY_ACCEPT");
					sendmessage(user.getUserId(),content,4,"ACTIVITY_ACCEPT");
				}else if(type==5){
					/**
					 * 添加退款记录
					 */
					//添加微信退款记录   
					ActivityUser user = activityUserService.getactivityUserByid(id);
					if(user.getPayPrice()!=0&&!StringUtils.isEmpty(user.getTransactionId())){
						ActivityRefund refund = new ActivityRefund();
						refund.setAmount(user.getPayPrice());
						refund.setCreateTime(new Date());
						refund.setStatus(0);
						refund.setType(1);
						refund.setUserId(user.getUserId());
						refund.setOrderId(user.getOrderId());
						refund.setActivityId(user.getActivityId());
						refund.setTransactionId(user.getTransactionId());
						boolean bo = activityRefundService.insertRefund(refund);
					}
					/**
					 * 报名被拒绝  发送微信  并且退款
					 * 尊敬的**，您所报名的****活动未通过主办人审核，望尽快于主办人进行沟通。
					 */
					Activity activity = activityService.getActivityByid(user.getActivityId());
					// 组装内容
					//String content = "【"+AldyMessageUtil.SMSPRE+"】尊敬的"+user.getUserName()+",您所报名的"+activity.getTitles()+"活动未通过主办人审核，望尽快于主办人进行沟通。<a href='"+ConfigUtil.DOMAIN+"/wxfront/m/activity/activityDetailPage.do?id="+activity.getId()+"'>立即前往</a>";
					String content = AldyMessageUtil.activityrefuse(user.getUserName(),activity.getTitles(),activity.getId());
					sendmessage(user.getUserId(),content,2,"ACTIVITY_REFUSE");
					sendmessage(user.getUserId(),content,4,"ACTIVITY_REFUSE");
				}
		}
		return super.getSuccessAjaxResult(Constants.AJAX_MSG_SUCCESS,null );
	}
	/**
	 * 活动编辑页面
	 * 
	 * @return
	 */
	@RequestMapping("/m/activity/editaction")
	public  String editaction(HttpServletRequest request, ModelMap modelMap,String id) {
		Member me = ServletUtil.getMember(request);
		if (null == me) {
			return "login";
		}
		String userId = me.getId();
		if (StringUtils.isEmpty(userId)) {
			return "login";
		}
		TjyUser tjyUser=tjyUserService.selectByPrimaryKey(userId);
		if(!"1".equals(tjyUser.getIsRealname()+"")){
			return "commons/to_recon";
		}
		Map dto =activityService.getactivityDetailByid(id);
		modelMap.addAttribute("dto", dto);
		// 获取省
		List provinceList = districtService.selectDistrictByType("1");
		modelMap.addAttribute("provinceList", provinceList);
		// 获取市
		List cityList = districtService.selectDistrictByType("2");
		modelMap.addAttribute("cityList", cityList);
		// 获取区
		List areaList = districtService.selectDistrictByType("3");
		modelMap.addAttribute("areaList", areaList);
		// 获取已选省
		if (!StringUtils.isEmpty((String)dto.get("province"))) {
			SyDistrict province = districtService.selectByPrimaryKey((String)dto.get("province"));
			modelMap.addAttribute("provinceName", province.getDisName());
		}
		// 获取已选市
		if (!StringUtils.isEmpty((String)dto.get("city"))) {
			SyDistrict city = districtService.selectByPrimaryKey((String)dto.get("city"));
			modelMap.addAttribute("cityName", city.getDisName());
		}
		// 获取已选区
		if (!StringUtils.isEmpty((String)dto.get("county"))) {
			SyDistrict county = districtService.selectByPrimaryKey((String)dto.get("county"));
			modelMap.addAttribute("countyName", county.getDisName());
		}
		//获取已选标签
		if (!StringUtils.isEmpty((String)dto.get("tag"))) {
			ActivityTag tag = activityTagService.getActivityTagByid((String)dto.get("tag"));
			modelMap.addAttribute("tagName", tag.getName());
		}
		//获取已选分类
		if (!StringUtils.isEmpty((String)dto.get("classId"))) {
			if("1".equals((String)dto.get("classId"))){
				modelMap.addAttribute("className", "以玩会友");
			}else{
				modelMap.addAttribute("className", "以书会友");
			}
		}
		return "netWork/actionedit";
	}
	/**
     * 活动报名统一下单
     * @param unifiedorder
     * @param key
     * @return
	 * @throws IOException 
	 * @throws JDOMException 
     */
	@RequestMapping("/m/my/activityprepay")
	@ResponseBody
    public Map<String,Object> payUnifiedorder(HttpServletRequest request,String id,String name,String mobile,String couponlogid)throws IOException, JDOMException {
			Map<String,String> resultObj = Maps.newHashMap();
			resultObj.put("result_code", "-1");
			resultObj.put("result_msg", "支付失败");
			
			Member me = ServletUtil.getMember(request);
			if (null == me) {
				return super.getAjaxResult("302", "登录超时", null);
			}
			String wxopenid = me.getOpenId();
			if (StringUtils.isEmpty(wxopenid)) {
				return super.getAjaxResult("999", "报名失败，请与管理员联系", null);
			} else {
				me.setWxUserId(wxopenid);
				request.getSession().setAttribute("me", me);
			}
			if (StringUtils.isEmpty(name)) {
				return super.getAjaxResult("999", "参数错误", null);
			}
			if (StringUtils.isEmpty(mobile)) {
				return super.getAjaxResult("999", "参数错误", null);
			}
			Activity activity = activityService.getActivityByid(id);
			if (activity == null) {
				resultObj.put("result_msg", "活动不存在");
				return super.getAjaxResult("0", "活动不存在", resultObj);
			}
			if (activity.getTicketPrice()==null||activity.getTicketPrice() < 0) {
				resultObj.put("result_msg", "支付金额错误");
				return super.getAjaxResult("0", "支付金额错误", resultObj);
			}
			/**
			 * 根据 mobile查询是否成功报名  订单状态2 （已付款 ）  ，防止重复报名
			 */
			List<ActivityUser> signuped = activityUserService.getsignuphistory(id,mobile,2);
			if(signuped.size()>0){
				SortedMap<Object, Object> param = new TreeMap<Object, Object>();
				param.put("signuped", 1);
				param.put("free", 0);
				return super.getSuccessAjaxResult("此手机号已经成功报名", param);
				
			}
			Double ticketprice = activity.getTicketPrice();
			/**
			 * 添加优惠券功能
			 */
			Double couponprice = 0.00;
			CouponLog  clog = null;
			Coupon cou = null;
			CouponGenerate couponGenerate = null;
			if(!StringUtils.isEmpty(couponlogid)){
				clog = couponLogService.selectById(couponlogid);
				if(null!=clog){
					couponGenerate = couponGenerateService.selectById(clog.getFkId());
					if(null!=couponGenerate){
						cou = couponService.selectById(couponGenerate.getFkId());
					}
				}
			}
			if(null!=clog&&null!=cou){
				/**
				 * 校验优惠券是否可用
				 * @param  优惠券记录表ID
				 * @param  用户ID
				 * @param  原支付金额
				 * @param useRange 1全平台，2会议，3活动，4互助商城          订单类型
				 * @param currency    币种   1:J币     2:RMB  
				 */
				DecimalFormat df = new DecimalFormat("#.00");
				Integer useRange = 3;
				Integer currency = 2;
				boolean canUse = couponLogService.checkCanUse(couponlogid,me.getId(),activity.getTicketPrice(),useRange,currency);
				if(canUse){
					couponprice= cou.getCouponAmount();
					if(ticketprice<=couponprice){
						ticketprice=0.00;
					}else{
						ticketprice =Double.parseDouble(df.format( ticketprice - couponprice));
					}
				}
			};
			/**
			 * 根据手机号查询用户是否报名过  订单状态1 （未付款 ）
			 */
			List<ActivityUser> list =   activityUserService.getsignuphistory(id,mobile,1);
			ActivityUser signup = list.size()>0?list.get(0):new ActivityUser();
			if (ticketprice == 0) {
				/**
				 * 免费活动
				 */
				ActivityUser free = new ActivityUser();
				TjyUser user = tjyUserService.selectById(me.getId());
				free.setOrderStatus(2);
				free.setStatus(1);
				free.setUserId(me.getId());
				free.setUserName(name);
				free.setPhone(mobile);
				free.setPayTime(new Date());
				free.setOrderId(UUIDGenerator.getUUID());
				free.setActivityId(id);
				free.setPayPrice(0.00);
				free.setCreateTime(new Date());
				free.setCoupon(couponprice);
				free.setBalance(0);
				activityUserService.insertsignup(free);
				SortedMap<Object, Object> param = new TreeMap<Object, Object>();
				param.put("free", 1);
				param.put("signuped", 0);
				/**
				 * 发送报名成功消息
				 * 
				 * 尊敬的**，您所发布的的****活动收到新的报名，望尽快处理。
				 * 【AldyMessageUtil.SMSPRE】尊敬的***，您在平台发布的的****活动收到新的报名，望尽快处理。立即前往
				 */
				//String content = "【"+AldyMessageUtil.SMSPRE+"】尊敬的"+activity.getCreateUserName()+",您在平台发布的"+activity.getTitles()+"活动收到新的报名，望尽快处理。";
				String content = AldyMessageUtil.activitysignup(activity.getCreateUserName(), activity.getTitles());
				sendmessage(activity.getCreateUserId(),content,2,"ACTIVITY_SIGNUP");
				sendmessage(activity.getCreateUserId(),content,4,"ACTIVITY_SIGNUP");
				return super.getSuccessAjaxResult("操作成功", param);
			}
			signup.setActivityId(StringUtils.isBlank(signup.getActivityId())?id:signup.getActivityId());
			signup.setUserId(StringUtils.isBlank(signup.getUserId())?me.getId():signup.getUserId());
			signup.setCreateTime(signup.getCreateTime()==null?new Date():signup.getCreateTime());
			signup.setOrderId(StringUtils.isBlank(signup.getOrderId())?UUIDGenerator.getUUID():signup.getOrderId());
			if(signup.getOrderStatus()!=null&&signup.getOrderStatus().intValue()==2){
				resultObj.put("result_msg", "已支付");
				return super.getAjaxResult("0", "已支付", resultObj);
			}else{
				signup.setOrderStatus(1);
			}
			//从微信接口查询支付状态（存在支付完成后没有更新天九系统订单状态的情况）
			if(StringUtils.isNotBlank(signup.getId())){
				MchOrderquery mchOrderquery = new MchOrderquery();
				mchOrderquery.setAppid(ApplicationPath.getParameter("wx_appid"));
				mchOrderquery.setMch_id(ConfigUtil.MCH_ID);
				mchOrderquery.setTransaction_id(signup.getOrderId());
				mchOrderquery.setNonce_str(PayCommonUtil.CreateNoncestr());
				
				MchOrderInfoResult o = PayMchAPI.payOrderquery(mchOrderquery);
				if(o.isSuccess()&&"SUCCESS".equalsIgnoreCase(o.getTrade_state())){
					signup.setOrderStatus(2);
					signup.setPayTime(new Date(Long.valueOf(o.getTime_end())));
					activityUserService.updatesignup(signup);
					resultObj.put("result_msg", "已支付");
					return super.getAjaxResult("0", "已支付", resultObj);
				}
				signup.setOrderId(UUIDGenerator.getUUID());
			}
			
			Unifiedorder unifiedorder = new Unifiedorder();
			unifiedorder.setAppid(ApplicationPath.getParameter("wx_appid"));
			unifiedorder.setMch_id(ConfigUtil.MCH_ID);
			unifiedorder.setNonce_str(PayCommonUtil.CreateNoncestr());
			unifiedorder.setBody("活动报名");
			unifiedorder.setOut_trade_no(signup.getOrderId());
			Double d = ticketprice*100;
			unifiedorder.setTotal_fee(String.valueOf(d.intValue()));
			unifiedorder.setSpbill_create_ip(request.getRemoteAddr());
			unifiedorder.setNotify_url(ConfigUtil.NOTIFY_ACTIVITY_URL);
			unifiedorder.setTrade_type("JSAPI");
			unifiedorder.setOpenid(me.getOpenId());
			UnifiedorderResult res = PayMchAPI.payUnifiedorder(unifiedorder);
			String return_code =res.getReturn_code();
			
			if(StringUtils.isNotBlank(signup.getId())){
				signup.setOrderStatus(1);
				signup.setPayTime(null);
				signup.setPayPrice(ticketprice);
				signup.setCoupon(couponprice);
				signup.setBalance(0);
				activityUserService.updatesignup(signup);
			}else{
				TjyUser user = tjyUserService.selectById(signup.getUserId());
				signup.setOrderStatus(1);
				signup.setPayTime(null);
				signup.setStatus(1);
				signup.setPayPrice(ticketprice);
				signup.setUserName(name);
				signup.setPhone(mobile);
				signup.setCoupon(couponprice);
				signup.setBalance(0);
				activityUserService.insertsignup(signup);
			}
			if ("SUCCESS".equals(return_code)) {
				SortedMap<Object, Object> params = new TreeMap<Object, Object>();
				params.put("appId", ApplicationPath.getParameter("wx_appid"));
				params.put("timeStamp", Long.toString(new Date().getTime()));
				params.put("nonceStr", PayCommonUtil.CreateNoncestr());
				params.put("package", "prepay_id=" +res.getPrepay_id() );
				params.put("signType", ConfigUtil.SIGN_TYPE);
				String paySign = PayCommonUtil.createSign("UTF-8", params);
				params.put("packageValue", "prepay_id=" + res.getPrepay_id()); // 这里用packageValue是预防package是关键字在js获取值出错
				params.put("paySign", paySign); // paySign的生成规则和Sign的生成规则一致
				String userAgent = request.getHeader("user-agent");
				char agent = userAgent.charAt(userAgent.indexOf("MicroMessenger") + 15);
				params.put("agent", new String(new char[] { agent }));// 微信版本号，用于前面提到的判断用户手机微信的版本是否是5.0以上版本。
				System.out.println("----------paycwx success----------");
				params.put("free", 0);
				params.put("signuped", 0);
				params.put("orderId", signup.getOrderId());
				return super.getSuccessAjaxResult("操作成功", params);
			} else {
					return super.getAjaxResult("999", "请求失败！", null);
			}
    }
	/**
	 * 支付成功后更新支付状态和支付时间
	 */
	@RequestMapping("/m/my/activityupdatesignup")
	public @ResponseBody String updatesignup(HttpServletRequest request, HttpServletResponse response){
		try{
			String return_msg = "";
			BufferedReader br = request.getReader();
			String valueString = null;
			while ((valueString = br.readLine()) != null) {
				return_msg += valueString;
			}
			System.out.println("return_msg:" + return_msg);
			SortedMap<Object, Object> map = XMLUtil.doXMLParse(return_msg);
			String sign = (String) map.get("sign");
			String out_trade_no = (String) map.get("out_trade_no");//订单id
			String transaction_id = (String) map.get("transaction_id");
			String return_code = (String) map.get("return_code");
			String sign1 = PayCommonUtil.createSign("UTF-8", map);
			if (sign1.equals(sign)) {
				// 校验成功
				System.out.println("success");
				ActivityUser signup = activityUserService.getactivityUserByorderid(out_trade_no);
				signup.setOrderStatus(2);
				signup.setPayTime(new Date());
				signup.setTransactionId(transaction_id);
				activityUserService.updatesignup(signup);
				/**
				 * 发送报名成功消息
				 * 尊敬的**，您所发布的的****活动收到新的报名，望尽快处理。
				 * 【AldyMessageUtil.SMSPRE】尊敬的***，您在平台发布的的****活动收到新的报名，望尽快处理。立即前往
				 */
				Activity activity = activityService.getActivityByid(signup.getActivityId());
				// 组装内容
				String content =AldyMessageUtil.activitysignup(activity.getCreateUserName(), activity.getTitles());
//				String content = "【"+AldyMessageUtil.SMSPRE+"】尊敬的"+activity.getCreateUserName()+",您在平台发布的"+activity.getTitles()+"活动收到新的报名，望尽快处理。";
				
				sendmessage(activity.getCreateUserId(),content,2,"ACTIVITY_SIGNUP");
				sendmessage(activity.getCreateUserId(),content,4,"ACTIVITY_SIGNUP");
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
	 *活动发起者点击退款操作
	 */
	@RequestMapping("/m/activity/refundop")
	public @ResponseBody Map refundop(HttpServletRequest request,String id) {
		Member me = ServletUtil.getMember(request);
		if (null == me) {
			return super.getAjaxResult("302", "登录超时，请重新登录", null);
		}
		String userId = me.getId();
		if (StringUtils.isEmpty(userId)) {
			return super.getAjaxResult("302", "登录超时，请重新登录", null);
		}
		if (StringUtils.isEmpty(id)) {
			return super.getAjaxResult("999", "参数错误", null);
		}
		ActivityUser user = activityUserService.getactivityUserByid(id);
		user.setOrderStatus(3);
		activityUserService.updatesignup(user);
		/**
		 * 添加退款记录
		 */
		if(user.getPayPrice()!=0&&!StringUtils.isEmpty(user.getTransactionId())){
			//添加微信退款记录   
			ActivityRefund refund = new ActivityRefund();
			refund.setAmount(user.getPayPrice());
			refund.setCreateTime(new Date());
			refund.setStatus(0);
			refund.setType(1);
			refund.setUserId(user.getUserId());
			refund.setOrderId(user.getOrderId());
			refund.setActivityId(user.getActivityId());
			refund.setTransactionId(user.getTransactionId());
			boolean bo = activityRefundService.insertRefund(refund);
		}
        return super.getSuccessAjaxResult(Constants.AJAX_MSG_SUCCESS,null );
	}
	/**
	 *活动发起者点击退款操作
	 */
	@RequestMapping("/m/activity/refundop2")
	public @ResponseBody Map refundop2(HttpServletRequest request,String[] ids) {
		Member me = ServletUtil.getMember(request);
		if (null == me) {
			return super.getAjaxResult("302", "登录超时，请重新登录", null);
		}
		String userId = me.getId();
		if (StringUtils.isEmpty(userId)) {
			return super.getAjaxResult("302", "登录超时，请重新登录", null);
		}
		if (ids.length==0) {
			return super.getAjaxResult("999", "参数错误", null);
		}
		for(String id :ids){
			ActivityUser user = activityUserService.getactivityUserByid(id);
			user.setOrderStatus(3);
			user.setStatus(5);
			activityUserService.updatesignup(user);
			/**
			 * 添加退款记录
			 */
			if(user.getPayPrice()!=0&&!StringUtils.isEmpty(user.getTransactionId())){
				//添加微信退款记录   
				ActivityRefund refund = new ActivityRefund();
				refund.setAmount(user.getPayPrice());
				refund.setCreateTime(new Date());
				refund.setStatus(0);
				refund.setType(1);
				refund.setUserId(user.getUserId());
				refund.setOrderId(user.getOrderId());
				refund.setActivityId(user.getActivityId());
				refund.setTransactionId(user.getTransactionId());
				boolean bo = activityRefundService.insertRefund(refund);
			}
		}
		return super.getSuccessAjaxResult(Constants.AJAX_MSG_SUCCESS,null );
	}
	/**
	 * 发消息方法
	 */
	public void sendmessage(String touser,String content,int type,String templateid ){
		MessageInfo messageInfo = new MessageInfo();
		 messageInfo.setId(UUIDGenerator.getUUID());
		 messageInfo.setDeleted(0);
		 messageInfo.setType(type);// 微信
		 messageInfo.setToUserId(touser);
		 messageInfo.setCreateTime(new Date());
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
	 * 跳转到活动评论页面
	 */
	@RequestMapping("/m/activity/activityComment")
	public String commonPage(ModelMap map , String id){
		map.addAttribute("id", id);
		return "netWork/activityComment";
	}
	
	/**
	 * 跳转到活动评论页面
	 */
	@RequestMapping("/m/activity/signupsuccess")
	public String signupsuccessPage(){
		return "netWork/signupsuccess";
	}
	
	/**
	 * 活动搜索页面
	 */
	@RequestMapping("/m/activity/activitysearch")
	public String search(HttpServletRequest request,ModelMap modelMap,String classid){
		Member me = ServletUtil.getMember(request);
		if (null == me) {
			return "login";
		}
		String userId = me.getId();
		if (StringUtils.isEmpty(userId)) {
			return "login";
		}
		TjyUser user=tjyUserService.selectByPrimaryKey(userId);
		if(!"1".equals(user.getIsRealname()+"")){
			return "commons/to_recon";
		}
		//DataGrid grid = keywordsService.selectAllTop(1, 5);
		List list = new ArrayList();
		list = listValuesService.selectListByType(9002);
		list.subList(0, 4);
		modelMap.put("list", list);
		modelMap.put("classid", classid);
		return "netWork/activitysearch";
	}
	
	
	/**
	 * 活动搜索列表
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/m/activity/activitysearchlist")
	public String searchMeeting(HttpServletRequest request,ModelMap modelMap,String keywords,String classid){
		Member me = ServletUtil.getMember(request);
		if (null == me) {
			return "login";
		}
		String userId = me.getId();
		if (StringUtils.isEmpty(userId)) {
			return "login";
		}
		TjyUser user=tjyUserService.selectByPrimaryKey(userId);
		if(!"1".equals(user.getIsRealname()+"")){
			return "commons/to_recon";
		}
		
		/*Activity activity = new Activity();
		activity.setTitles(keywords);
		activity.setClassId(classid);
		List<Map> res= new ArrayList<Map>();
		res =  activityService.getActivityByTermf(activity,1,10,userId);
		modelMap.addAttribute("list", res);*/
		modelMap.addAttribute("keywords", keywords);
		modelMap.addAttribute("classid", classid);
		
		return "netWork/activitysearchlist";
	}
	
	/**
	 * 跳转到查看入场凭证页面
	 */
	@RequestMapping("/m/activity/signupok")
	public String signupok(HttpServletRequest request,ModelMap modelMap,String id){
		Member me = ServletUtil.getMember(request);
		if (null == me) {
			return "login";
		}
		String userId = me.getId();
		if (StringUtils.isEmpty(userId)) {
			return "login";
		}
		TjyUser user=tjyUserService.selectByPrimaryKey(userId);
		if(!"1".equals(user.getIsRealname()+"")){
			return "commons/to_recon";
		}
		
		ActivityUser activityuser =  activityUserService.getactivityUserByid(id);
		modelMap.addAttribute("user", activityuser);
		
		return "netWork/signupok";
	}
	
}
