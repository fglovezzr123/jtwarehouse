package com.wing.socialcontact.front.action;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wing.socialcontact.common.action.BaseAction;
import com.wing.socialcontact.common.model.Member;
import com.wing.socialcontact.front.util.ApplicationPath;
import com.wing.socialcontact.service.wx.api.ILibraryClassService;
import com.wing.socialcontact.service.wx.api.ILibraryLiveService;
import com.wing.socialcontact.service.wx.api.ILibraryService;
import com.wing.socialcontact.service.wx.api.IMeetingService;
import com.wing.socialcontact.service.wx.api.IMessageInfoService;
import com.wing.socialcontact.service.wx.api.IMyCollectionService;
import com.wing.socialcontact.service.wx.api.ISysconfigService;
import com.wing.socialcontact.service.wx.api.ITjyUserService;
import com.wing.socialcontact.service.wx.api.IWalletLogService;
import com.wing.socialcontact.service.wx.api.IWxUserService;
import com.wing.socialcontact.service.wx.api.IliveSignupService;
import com.wing.socialcontact.service.wx.bean.MessageInfo;
import com.wing.socialcontact.service.wx.bean.TjyLibrary;
import com.wing.socialcontact.service.wx.bean.TjyLibraryClass;
import com.wing.socialcontact.service.wx.bean.TjyLibraryLive;
import com.wing.socialcontact.service.wx.bean.TjyLiveSignup;
import com.wing.socialcontact.service.wx.bean.TjyUser;
import com.wing.socialcontact.service.wx.bean.WalletLog;
import com.wing.socialcontact.service.wx.bean.WxUser;
import com.wing.socialcontact.util.AldyMessageUtil;
import com.wing.socialcontact.util.CommUtil;
import com.wing.socialcontact.util.Constants;
import com.wing.socialcontact.util.DateUtils;
import com.wing.socialcontact.util.MD5UtilWx;
import com.wing.socialcontact.util.ServletUtil;
import com.wing.socialcontact.util.UUIDGenerator;
import com.wing.socialcontact.util.WxMsmUtil;
import com.wing.socialcontact.vhall.api.BaseAPI;

/**
 * 
 * @author zhangzheng 老板文库接口类
 */
@Controller
@RequestMapping("/library/m")
public class LibraryMoAction extends BaseAction {

	@Autowired
	private ILibraryClassService libraryclassservice;
	@Autowired
	private ILibraryService libraryService;
	@Autowired
	private IMyCollectionService myCollectionService;
	@Autowired
	private ITjyUserService tjyUserService;
	@Autowired
	private ISysconfigService sysconfigService;
	
	@Autowired
	private ILibraryLiveService libraryLiveService;
	@Autowired
	private IliveSignupService liveSignupService;
	
	@Autowired
	private IMeetingService meetingService;
	@Autowired
	private IWxUserService wxUserService;

	@Autowired
	private IWalletLogService walletLogService;
	@Autowired
	private IMessageInfoService messageInfoService;
	

	/**
	 * 文库首页推荐二级分类查询
	 */
	@RequestMapping(value = "/recommendclass")
	public @ResponseBody Map recommendclass(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		Member me = (Member) request.getSession().getAttribute("me");
		if (null == me) {
			return super.getAjaxResult("302", "登录超时，请重新登录", null);
		}
		String userId = me.getId();
		if (StringUtils.isEmpty(userId)) {
			return super.getAjaxResult("302", "登录超时，请重新登录", null);
		}
		List<Map> ylist = libraryclassservice.recommendclass();
		return super.getSuccessAjaxResult("操作成功！", ylist);

	}

	/**
	 * 文库分类列表查询（所有）
	 */
	@RequestMapping(value = "/classList")
	public @ResponseBody Map classList(HttpServletRequest request, HttpServletResponse response) throws IOException {
		Member me = (Member) request.getSession().getAttribute("me");
		if (null == me) {
			return super.getAjaxResult("302", "登录超时，请重新登录", null);
		}
		String userId = me.getId();
		if (StringUtils.isEmpty(userId)) {
			return super.getAjaxResult("302", "登录超时，请重新登录", null);
		}

		List<Map> res = new ArrayList<Map>();
		/**
		 * 查一级分类
		 */
		List<Map> ylist = libraryclassservice.selectonelevelclass();
		if (ylist.size() > 0) {
			for (Map cls : ylist) {
				/**
				 * 根据一级分类查二级分类
				 */
				String id = (String) cls.get("id");
				List<Map> elist = libraryclassservice.querybyparent(id);
				cls.put("son", elist);
				res.add(cls);
			}
		}
		return super.getSuccessAjaxResult("操作成功！", res);

	}

	/**
	 * 文库分类列表查询（所有）
	 */
	@RequestMapping(value = "/onelevelclass")
	public @ResponseBody Map onelevelclass(HttpServletRequest request, HttpServletResponse response, Integer position)
			throws IOException {
		Member me = (Member) request.getSession().getAttribute("me");
		if (null == me) {
			return super.getAjaxResult("302", "登录超时，请重新登录", null);
		}
		String userId = me.getId();
		if (StringUtils.isEmpty(userId)) {
			return super.getAjaxResult("302", "登录超时，请重新登录", null);
		}

		/**
		 * 查一级分类
		 */
		List<Map> res = libraryclassservice.onelevelclass(position);
		return super.getSuccessAjaxResult("操作成功！", res);

	}

	/**
	 * 根据条件查文章
	 */
	@RequestMapping(value = "/list")
	public @ResponseBody Map libraryList(HttpServletRequest request, HttpServletResponse response, String classid,
			Integer page, Integer size, Integer today, String key, Integer readtimes) throws IOException {
		Member me = (Member) request.getSession().getAttribute("me");
		if (null == me) {
			return super.getAjaxResult("302", "登录超时，请重新登录", null);
		}
		String userId = me.getId();
		if (StringUtils.isEmpty(userId)) {
			return super.getAjaxResult("302", "登录超时，请重新登录", null);
		}

		if (!StringUtils.isEmpty(key)) {
			/**
			 * post方式不用转码
			 */
			// key = new String(key.getBytes("ISO-8859-1"), "utf-8");
		}
		if (StringUtils.isEmpty(page) || page < 1) {
			page = 1;
		}
		if (StringUtils.isEmpty(size) || size < 1) {
			size = 10;
		}
		/**
		 * today 大于0时查询今天
		 */
		if (StringUtils.isEmpty(today)) {
			today = 0;
		}
		if (StringUtils.isEmpty(readtimes)) {
			readtimes = 0;
		}

		// List<Map> res= new ArrayList<Map>();
		// res = libraryService.getTjyLibraryByclassid(classid);
		List<Map> res1 = new ArrayList<Map>();
		res1 = libraryService.getTjyLibraryByTerm(classid, page, size, today, key, readtimes);

		if (res1.size() == 0) {
			res1 = libraryService.getLibraryByoneLevel(classid, page, size, today, key, readtimes);
		}

		return super.getSuccessAjaxResult(Constants.AJAX_MSG_SUCCESS, res1);
	}

	/**
	 * 根据一级id查询文章
	 */
	@RequestMapping(value = "/selbyonelevelid")
	public @ResponseBody Map selbyonelevelid(HttpServletRequest request, HttpServletResponse response, String classid)
			throws IOException {
		Member me = (Member) request.getSession().getAttribute("me");
		if (null == me) {
			return super.getAjaxResult("302", "登录超时，请重新登录", null);
		}
		String userId = me.getId();
		if (StringUtils.isEmpty(userId)) {
			return super.getAjaxResult("302", "登录超时，请重新登录", null);
		}

		List<Map> res = libraryService.selbyonelevelid(classid);

		return super.getSuccessAjaxResult(Constants.AJAX_MSG_SUCCESS, res);
	}

	/**
	 * 跳转到文章详情
	 */
	@RequestMapping(value = "librarydetail")
	public String librarydetail(HttpServletRequest request, String id, ModelMap map, Integer type) {
		Member me = (Member) request.getSession().getAttribute("me");
		if (null == me) {
			type = 2;
		}
		map.addAttribute("id", id);
		map.addAttribute("type", type);
		String path = request.getContextPath();
		map.addAttribute("web_site", sysconfigService.getSysconfig().getWebSite() + path);
		return "wisdomGroup/textDesign-detail";
	}

	/**
	 * 查看文章详情
	 */
	@RequestMapping(value = "/detail")
	public @ResponseBody Map libraryDetail(HttpServletRequest request, HttpServletResponse response, String libraryid)
			throws IOException {
		Member me = (Member) request.getSession().getAttribute("me");

		/*
		 * if (StringUtils.isEmpty(userId)) { return super.getAjaxResult("302",
		 * "登录超时，请重新登录", null); }
		 */
		if (StringUtils.isEmpty(libraryid)) {
			return super.getAjaxResult("999", "参数错误", null);
		}

		TjyUser tjyUser = null;
		if (me != null) {
			tjyUser = tjyUserService.selectByPrimaryKey(me.getId());
		}
		Map res = new HashMap();
		res = libraryService.getLibraryByid(libraryid);
		boolean iscol = false;
		if (null != me) {
			String userId = me.getId();
			iscol = myCollectionService.iscollected(userId, libraryid, 1);
		}
		// 是否收藏
		res.put("iscollection", iscol);

		Map<String, Object> signMap = null;
		String roomId = "";
		if (me != null && res != null) {
			roomId = res.get("webinarId") == null ? "" : res.get("webinarId").toString();
			if (roomId != null && !"".equals(roomId)) {
				signMap = BaseAPI.createVedioSign(tjyUser.getId(), tjyUser.getNickname(), roomId);
			}
		}
		res.put("signObj", signMap);
		/**
		 * 添加阅读数量
		 */
		libraryService.addreadtimes(libraryid);
		return super.getSuccessAjaxResult("操作成功！", res);
	}
	
	@RequestMapping(value = "/all-classes")
	public String allclasses(ModelMap map, String id,int level){
		map.addAttribute("classid", id);
		map.addAttribute("level", level);
		return "wisdomGroup/all-classes";
	}
	@RequestMapping(value = "/dynamiczf")
	public String zhuanfa(ModelMap map, String id,String fromDynamicId,int type){
		TjyLibrary res = libraryService.getTjyLibraryByid(id);
		TjyLibraryClass  cla = libraryclassservice.getTjyLibraryClassByid(res.getOneclass());
		map.addAttribute("obj", res);
		map.addAttribute("classobj", cla);
		map.addAttribute("type", type);
		map.addAttribute("fromDynamicId", fromDynamicId);
		return "wisdomGroup/dynamiczf";
	}
	
	/*---------------------------------------------------直播秀接口---------------------------------------------------------------*/
	
	/**
	 * 跳转到直播列表页面
	 * 
	 * @param type  1：俊卿解惑   2：总统谈心   3：冠军直播秀  4：总裁读书会
	 */
	 @RequestMapping("live/listPage")
	 public String listPage(ModelMap map, Integer type){
		 if(type==1){
			 return "wisdomGroup/jqjhlists";
		 }else if(type==2){
			 return "wisdomGroup/zttxlists";
		 }else if(type==3){
			 return "wisdomGroup/zbxlists";
		 }else if(type==4){
			 return "wisdomGroup/dshlists";
		 }
		return "wisdomGroup/jqjhlists";
	}
	
	/**
	 * 根据类型分页查询直播列表   名称    权重倒序   显示 
	 * @param type
	 * @param page
	 * @param size
	 * @param key
	 */
	@RequestMapping(value = "live/list")
	public @ResponseBody Map libraryliveList(HttpServletRequest request, HttpServletResponse response, Integer type,
			Integer page, Integer size,  String key) throws IOException {
		Member me = (Member) request.getSession().getAttribute("me");
		if (null == me) {
			return super.getAjaxResult("302", "登录超时，请重新登录", null);
		}
		String userId = me.getId();
		if (StringUtils.isEmpty(userId)) {
			return super.getAjaxResult("302", "登录超时，请重新登录", null);
		}
		if (StringUtils.isEmpty(page) || page < 1) {
			page = 1;
		}
		if (StringUtils.isEmpty(size) || size < 1) {
			size = 10;
		}
		List<Map> res = new ArrayList<Map>();
		res = libraryLiveService.libraryLiveList(page, size, type, key);
		return super.getSuccessAjaxResult(Constants.AJAX_MSG_SUCCESS, res);
	}
	
	/**
	 * 直播详细
	 * @return
	 */
	@RequestMapping("live/detail")
	public String detailLive(HttpServletRequest request,ModelMap res,String id){
		Member me = ServletUtil.getMember(request);
		if (null == me) {
			return "login";
		}
		String userId = me.getId();
		if (StringUtils.isEmpty(userId)) {
			return "login";
		}
		
		TjyLibraryLive t = libraryLiveService.getLibraryLive(id);
		res.put("obj", t);
		//报名状态
		int signupStatus=liveSignupService.usersignupedorno(userId, t.getId());
		res.put("signupStatus", signupStatus);
		//关注状态
		boolean iscol = false;
		if (null != me) {
			iscol = myCollectionService.iscollected(userId, id, 4);
		}
		// 是否收藏
		if(iscol){
			res.put("iscollection", 1);
		}else{
			res.put("iscollection", 0);
		}
		return "wisdomGroup/livedetail";
	}
	
	/**
	 * 进入报名页面
	 */
	 @RequestMapping("live/signupPage")
    public String signupPage(HttpServletRequest request,HttpServletResponse response,String id,ModelMap map){
		/**
		 * 返回直播详情
		 */ 
		TjyLibraryLive l = libraryLiveService.getLibraryLive(id);
		map.addAttribute("detail", l);
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
		/*if(!"1".equals(user.getIsRealname()+"")){
			return "commons/to_recon";
		}*/
		map.addAttribute("user", user);
		WxUser wxUser = wxUserService.selectByPrimaryKey(userId);
		if(wxUser != null){
			map.addAttribute("jbAmount", wxUser.getJbAmount() == null? 0: wxUser.getJbAmount().intValue());
		}
		//报名状态
		int signupStatus=liveSignupService.usersignupedorno(userId, id);
		map.put("signupStatus", signupStatus);
        return "wisdomGroup/livesignup";
    }
	 @RequestMapping("live/payorder")
	 public @ResponseBody Map payorder(HttpServletRequest request,HttpServletResponse response,String id){
		 Map res = new HashMap();
		 Member me = ServletUtil.getMember(request);
		 if(null==me){
			 return super.getAjaxResult("302","未登录", null);
		 }
		 String userId = me.getId();
		 if(null==userId){
			 return super.getAjaxResult("302","未登录", null);
		 }
		 TjyUser user=tjyUserService.selectByPrimaryKey(userId);
		 if(null==user){
			 return super.getAjaxResult("302","未登录", null);
		 }
		 Double jbAmount = 0.00;
		 WxUser wxUser = wxUserService.selectByPrimaryKey(userId);
		 if(null==wxUser){
			 return super.getAjaxResult("302","未登录", null);
		 }else{
			 jbAmount = wxUser.getJbAmount();
		 }
		 TjyLibraryLive l = libraryLiveService.getLibraryLive(id);
		 if(null==l){
			 return super.getAjaxResult("999","直播已不存在", null);
		 }
		 Integer price = l.getTicketPrice();
		 if(jbAmount<price){
			 return super.getAjaxResult("999","J币余额不足", null);
		 }
		 try{
			 /**
			  * 查询是否已成功报名
			  */
			 int i = liveSignupService.usersignupedorno(userId, id);
			 if(i==1){
				 return super.getAjaxResult("999","您已报名", null);
			 };
			 
			 /**
			  * 添加报名记录
			  */
			 TjyLiveSignup signup = new  TjyLiveSignup();
			 signup.setAmount(price);
			 signup.setLiveid(id);
			 signup.setUserid(userId);
			 signup.setCreatetime(new Date());
			 signup.setName(user.getNickname());
			 signup.setMobile(user.getMobile());
			 signup.setOrderstatus(1);
			 signup.setPaystatus(2);
			 signup.setIsremind(0);
			 signup.setPaytime(new Date());
			 if(price==0){
				 liveSignupService.addSignup(signup);
				 /**
				  * 发消息
				  */
				 String message =AldyMessageUtil.liveSignupSuccess(wxUser.getNickName(), 
						 DateUtils.dateToString(signup.getCreatetime(), "yyyy-MM-dd HH:mm:ss"), l.getTitle(),
						 DateUtils.dateToString(l.getStartTime(), "yyyy-MM-dd HH:mm:ss")); 
				 sendmessage(userId, message, 2, "LIVE_SIGNUP",null);
				 sendmessage(userId, message, 4, "LIVE_SIGNUP",null);
				//短信${name}老板，看这里！您于${paytime}成功支付${livename}直播。直播开始时间为：${playtime}，届时记得收看！
				String content1="{name:\"" + user.getNickname() + "\",paytime:\"" + DateUtils.datetimeToString(signup.getPaytime()) + "\",livename:\"" + l.getTitle() + "\",playtime:\"" + DateUtils.datetimeToString(l.getStartTime()) + "\"}";
				sendmessage(user.getId(),content1,1,AldyMessageUtil.MsmTemplateId.LIVE_SIGNUP,user.getMobile());
				 return super.getSuccessAjaxResult(Constants.AJAX_MSG_SUCCESS, res);
			 }
			 // 创建消费记录
			 WalletLog walletLog = new WalletLog();
			 walletLog.setAmount(price.doubleValue());
			 walletLog.setCreateTime(new Date());
			 walletLog.setDeleted("0");
			 walletLog.setPayStatus("1");// 支付成功
			 walletLog.setPdType("2");
			 walletLog.setRemark("直播报名");
			 walletLog.setType("2");
			 walletLog.setUserId(me.getId());
			 walletLog.setBusinessType(16);
			 String out_trade_no = walletLogService.addWalletLog(walletLog);
			 if (StringUtils.isEmpty(out_trade_no)) {
				 return super.getAjaxResult("999", "报名失败", null);
			 }
			 wxUser.setJbAmount(CommUtil.subtract(wxUser.getJbAmount(), walletLog.getAmount()));
			 wxUserService.updateWxUser(wxUser);
			 liveSignupService.addSignup(signup);
			 /**
			  * 发消息
			  */
			//短信${name}老板，看这里！您于${paytime}成功支付${livename}直播。直播开始时间为：${playtime}，届时记得收看！
				String content1="{name:\"" + user.getNickname() + "\",paytime:\"" + DateUtils.datetimeToString(signup.getPaytime()) + "\",livename:\"" + l.getTitle() + "\",playtime:\"" + DateUtils.datetimeToString(l.getStartTime()) + "\"}";
				sendmessage(user.getId(),content1,1,AldyMessageUtil.MsmTemplateId.LIVE_SIGNUP,user.getMobile());
			 
			 String message =AldyMessageUtil.liveSignupSuccess(wxUser.getNickName(), 
					 DateUtils.dateToString(signup.getCreatetime(), "yyyy-MM-dd HH:mm"), l.getTitle(),
					 DateUtils.dateToString(l.getStartTime(), "yyyy-MM-dd HH:mm:ss")); 
//			 String message ="【"+AldyMessageUtil.SMSPRE+"】尊敬的"+wxUser.getNickName()+"，"
//					 + "您于"+DateUtils.dateToString(signup.getCreatetime(), "yyyy-MM-dd HH:mm")
//					 +"成功报名"+l.getTitle()+"直播，报名信息为：姓名："+wxUser.getNickName()+"，电话："+wxUser.getMobile()+"。"; 
			 sendmessage(userId, message, 2, "LIVE_SIGNUP",null);
			 sendmessage(userId, message, 4, "LIVE_SIGNUP",null);
			 
			 return super.getSuccessAjaxResult(Constants.AJAX_MSG_SUCCESS, res);
		 }catch(Exception e){
			 return super.getAjaxResult("999", "报名失败", null);
		 }
	 }
	/**
	 * 跳转到报名成功页面
	 */
	 @RequestMapping("live/signupsuccess")
	 public String signupsuccessPage(){
			return "wisdomGroup/signupsuccess";
	}
	

	 /**
	  * 我的页面
	  */
	 
	/**
	 * 我报名的直播列表
	 */
	 @RequestMapping(value = "live/mysignuplist")
	public @ResponseBody Map mylivelist(HttpServletRequest request, HttpServletResponse response, Integer type,Integer size,Integer page)
			throws IOException {
		Member me = (Member) request.getSession().getAttribute("me");
		if (null == me) {
			return super.getAjaxResult("302", "登录超时，请重新登录", null);
		}
		String userId = me.getId();
		if (StringUtils.isEmpty(userId)) {
			return super.getAjaxResult("302", "登录超时，请重新登录", null);
		}
		if (StringUtils.isEmpty(size)) {
			size=10;
		}
		if (StringUtils.isEmpty(page)||page==0) {
			page=1;
		}

		List<Map> res = liveSignupService.selectmysignups(userId,type,page,size);
		return super.getSuccessAjaxResult("操作成功！", res);

	}
	/**
	 * 直播(网页嵌入方式)
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("/liveuseweb")
	public String liveUseWeb(HttpServletRequest request,ModelMap modelMap,String id){
		Member me = ServletUtil.getMember(request);
		if (null == me||me.getId()==null||me.getId().trim().length()==0) {
			return "login";
		}
//		if(!"1".equals(me.getIsRealname())){
//		      return "commons/to_recon";
//		}
		TjyLibraryLive t = libraryLiveService.getLibraryLive(id);
		TjyUser tjyUser = tjyUserService.selectByPrimaryKey(me.getId());
		
		if(t==null){
			modelMap.put("message", "温馨提示");
			modelMap.put("tips", "直播已不存在");
			return "exception";
		}else if(StringUtils.isEmpty(t.getWebinarId())){
			modelMap.put("message", "温馨提示");
			modelMap.put("tips", "视频已不存在");
			return "exception";
		}else if(tjyUser==null){
			modelMap.put("message", "温馨提示");
			modelMap.put("tips", "用户已不存在");
			return "exception";
		}else{
			String openK = ApplicationPath.getParameter("vhall_open_k");
			String k = meetingService.createVhallKey(openK,me.getId(), t.getWebinarId());
			try {
				return "redirect:http://e.vhall.com/webinar/inituser/"+t.getWebinarId()+"?"
						+ "email="+MD5UtilWx.MD5Encode(tjyUser.getId(), "UTF-8").toLowerCase()+"@vhall.com&"
						+ "name="+URLEncoder.encode(tjyUser.getNickname(), "UTF-8")+"&"
						+ "k="+k;
			} catch (UnsupportedEncodingException e) {
				return "redirect:http://e.vhall.com/webinar/inituser/"+t.getWebinarId()+"?"
						+ "email="+MD5UtilWx.MD5Encode(tjyUser.getId(), "UTF-8").toLowerCase()+"@vhall.com&"
						+ "k="+k;
			}
		}
		
	}
	
	/**
	 * 跳转到我的页面
	 */
	 @RequestMapping("live/myfootPrint_live")
	 public String myfootPrintLive(){
			return "mine/myfootPrint_live";
	}
	
	/**
	 * 发消息方法  1 短信  2 微信    4活动消息
	 */
	public void sendmessage(String touser, String content, int type, String templateid,String mobile) {
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
		messageInfo.setMobile(mobile);
		messageInfo.setStatus(0);// 未发送
		messageInfo.setWxMsgType(1);///** 微信消息类型（1：文本消息2：图文消息） */
		messageInfoService.addMessageInfo(messageInfo);
	}
}
