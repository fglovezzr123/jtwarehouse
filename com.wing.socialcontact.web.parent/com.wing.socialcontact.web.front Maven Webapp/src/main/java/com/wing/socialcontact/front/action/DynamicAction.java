package com.wing.socialcontact.front.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache.ValueWrapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import tk.mybatis.mapper.util.StringUtil;

import com.wing.socialcontact.common.action.BaseAction;
import com.wing.socialcontact.common.model.Member;
import com.wing.socialcontact.config.OssConfig;
import com.wing.socialcontact.front.util.EsapiTest;
import com.wing.socialcontact.service.im.api.IImFriendService;
import com.wing.socialcontact.service.im.bean.ImFriend;
import com.wing.socialcontact.service.wx.api.IDynamicOpLogService;
import com.wing.socialcontact.service.wx.api.IDynamicPayLogService;
import com.wing.socialcontact.service.wx.api.IDynamicPicService;
import com.wing.socialcontact.service.wx.api.IDynamicService;
import com.wing.socialcontact.service.wx.api.ILibraryClassService;
import com.wing.socialcontact.service.wx.api.ILibraryService;
import com.wing.socialcontact.service.wx.api.ITjyUserService;
import com.wing.socialcontact.service.wx.api.IUserIntegralLogService;
import com.wing.socialcontact.service.wx.api.IWxUserService;
import com.wing.socialcontact.service.wx.bean.Dynamic;
import com.wing.socialcontact.service.wx.bean.DynamicOpLog;
import com.wing.socialcontact.service.wx.bean.DynamicPayLog;
import com.wing.socialcontact.service.wx.bean.DynamicPic;
import com.wing.socialcontact.service.wx.bean.TjyLibrary;
import com.wing.socialcontact.service.wx.bean.TjyLibraryClass;
import com.wing.socialcontact.service.wx.bean.TjyUser;
import com.wing.socialcontact.service.wx.bean.WxUser;
import com.wing.socialcontact.sys.service.IListValuesService;
import com.wing.socialcontact.util.Constants;
import com.wing.socialcontact.util.RedisCache;
import com.wing.socialcontact.util.ServletUtil;
import com.wing.socialcontact.util.SpringContextUtil;

/**
 * 动态相关
 * @author Administrator
 *
 */
@Controller
@RequestMapping("")
public class DynamicAction extends BaseAction {

	@Autowired
	private IListValuesService listValuesService;
	@Autowired
	private IDynamicService dynamicService;
	@Autowired
	private IDynamicPicService dynamicPicService;
	@Autowired
	private IDynamicOpLogService dynamicOpLogService;
	@Autowired
	private  IDynamicPayLogService dynamicPayLogService;
	@Autowired
	private IWxUserService wxUserService;
	@Autowired
	private IImFriendService imFriendService; 

	@Autowired
	private ITjyUserService tjyUserService;
	
	@Autowired
	private IUserIntegralLogService userIntegralLogService;
	
	@Autowired
	private ILibraryClassService libraryclassservice;
	@Autowired
	private ILibraryService libraryService;
	
	/**
	 * 跳转到发布动态
	 * @param modelMap
	 * @return
	 * @throws IOException 
	 */

	@RequestMapping("/m/dynamic/publishDynamic")
	public String publishDynamic(HttpServletRequest request,ModelMap modelMap,HttpServletResponse response) throws IOException {
		String path = request.getContextPath(); 
		String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
		Member me = ServletUtil.getMember(request);
		if (null == me) {
			//response.sendRedirect(basePath+"m/my/reconPage.do");
			return "login";
		}
		String userId = me.getId();
		if (StringUtils.isEmpty(userId)) {
			//response.sendRedirect(basePath+"m/my/reconPage.do");
			return "login";
		}
		TjyUser tjyUser = tjyUserService.selectByPrimaryKey(userId);
		if (null == tjyUser) {
			//response.sendRedirect(basePath+"m/my/reconPage.do");
			return "login";
		}
		if (!"1".equals(tjyUser.getIsRealname() + "")) {
			
			//response.sendRedirect(basePath+"m/my/reconPage.do");
			return "commons/to_recon";
		}
		return "dynamic/dynamic";
	}
	
	@RequestMapping("/add/m/dynamic/insertDynamic")
	public @ResponseBody Map insertDynamic(HttpServletRequest request, Dynamic dynamic,
			String zjImgerJson) {
		dynamic.setDyContent(EsapiTest.stripXSS(dynamic.getDyContent()));
		String userId =  super.checkLogin(request);
		if(userId == null || "".equals(userId))	{
			return super.getAjaxResult(Constants.AJAX_CODE_ERROR_NOTLOGIN, "未登录，请重新登录！", "");
		}	
		//此项每日记首次发布，等级达到10时积分不再增加
		userIntegralLogService.addLntAndEmp(userId, "task_0004");
		
		String dynamicId = UUID.randomUUID().toString().replace("-", "");
		try {
			String[] zjImgerJsons = zjImgerJson.split(",");
//			JSONArray ja = JSONArray.fromObject(zjImgerJson);
//			JSONObject jo = null;
			List<DynamicPic> dynamicPicList = new ArrayList(); 
			for (int i = 0; i < zjImgerJsons.length; i++) {
				//jo = ja.getJSONObject(i);
				String imgUrl = zjImgerJsons[i];
				if(!StringUtil.isEmpty(imgUrl)&&!"".equals(imgUrl)){
					int type = 1;
					DynamicPic dynamicPic = new DynamicPic();
					dynamicPic.setId(UUID.randomUUID().toString());
					dynamicPic.setDynamicId(dynamicId);
					dynamicPic.setCreateTime(new Date());
					dynamicPic.setPicUrl(imgUrl);
					dynamicPic.setUserId(userId);
					dynamicPic.setSortNum((double)i);
					dynamicPicList.add(dynamicPic);
				}
			}
			dynamic.setStatus(1);
			dynamic.setUserId(userId);
			Date now = new Date();
			dynamic.setIssuedDate(now);
			dynamic.setCreateTime(now);
			dynamic.setIsStick(0);
			dynamic.setId(dynamicId);
			dynamic.setDyType("0");
			String articleid = dynamic.getArticleid();
			if(null!=articleid&&!"".equals(articleid)){
				TjyLibrary li = libraryService.getTjyLibraryByid(articleid);
				if(null!=li){
					TjyLibraryClass lic = libraryclassservice.getTjyLibraryClassByid(li.getOneclass());
					dynamic.setAdate(li.getCreateTime());
					dynamic.setAtitle(li.getTitle());
					dynamic.setAimgpath(li.getImgpath());
					dynamic.setAclassname(lic.getName());
				}
			}
			int count = dynamicService.insertDynamicSignup(dynamic);
			int dynamicPicCount = dynamicPicService.insertDynamicPicList(dynamicPicList);
			return super.getSuccessAjaxResult("发布成功！", "");
		} catch (Exception e) {
			return super.getAjaxResult(Constants.AJAX_CODE_ERROR_INFO, e.getMessage(), "");
		}
	}
	
	/**
	 * 获取我的商友动态
	 * @param request
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	@RequestMapping("/m/dynamic/selectMyFriendDynamic")
	public @ResponseBody Map selectMyFriendDynamic(HttpServletRequest request, String pageNum, String pageSize,String dyContent,Long dynamicloadtime) {
		
		if(StringUtils.isEmpty(pageSize)){
			pageSize = "10";
		}
		//如里不传userId 则查自己好友的动态，如果传userId  则查userId好友的动态  
		String userId = request.getParameter("userId");
		if(StringUtil.isEmpty(userId)){
			userId =  super.checkLogin(request);
		}
		if(userId == null || "".equals(userId))	{
			return super.getAjaxResult(Constants.AJAX_CODE_ERROR_NOTLOGIN, "未登录，请重新登录！", "");
		}	
		if(StringUtil.isEmpty(pageNum) || StringUtil.isEmpty(pageSize) || !com.wing.socialcontact.util.StringUtil.isNumeric(pageNum) || !com.wing.socialcontact.util.StringUtil.isNumeric(pageSize)){
			return super.getAjaxResult(Constants.AJAX_CODE_ERROR_PARAM, "参数无效，请检查！", "");
		}else{
			try{
				List dynamicList =dynamicService.selectAllUserDynamic(userId, Integer.parseInt(pageNum), Integer.parseInt(pageSize),dyContent,dynamicloadtime);
				OssConfig ossConfig = (OssConfig) SpringContextUtil
						.getBean("ossConfig");
				String ossurl = ossConfig.getOss_getUrl();
				Map valueMap = new HashMap();
				valueMap.put("ossurl", ossurl);
				valueMap.put("dynamicList", dynamicList);
				return super.getSuccessAjaxResult("获取成功！", valueMap);
			}catch(Exception e){
				return super.getAjaxResult(Constants.AJAX_CODE_ERROR_INFO, e.getMessage(), "");
			}
		}
	}
	
	/**
	 * 根据动态id获取动态信息
	 * @param request
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	@RequestMapping("/m/dynamic/selectDynamicById")
	public @ResponseBody Map selectDynamicById(HttpServletRequest request, String dynamicId) {
		String userId =  super.checkLogin(request);
		if(userId == null || "".equals(userId))	{
			return super.getAjaxResult(Constants.AJAX_CODE_ERROR_NOTLOGIN, "未登录，请重新登录！", "");
		}	
		if(StringUtils.isEmpty(dynamicId)){
			return super.getAjaxResult(Constants.AJAX_CODE_ERROR_PARAM, "参数无效，请检查！", "");
		}else{
			try{
				Map dynamicMap =dynamicService.selectDynamicById(userId,dynamicId);
				Dynamic dynamic = dynamicService.getDynamicSignup(dynamicId);
				if(dynamic != null){
					if(dynamic.getVisitQuantity() != null){
						dynamic.setVisitQuantity(dynamic.getVisitQuantity()+1);
					}else{
						dynamic.setVisitQuantity(1l);
					}
					
					dynamicService.updateDynamicSignup(dynamic);
				}
				
				
				List dynamicList = new ArrayList();
				dynamicList.add(dynamicMap);
				OssConfig ossConfig = (OssConfig) SpringContextUtil
						.getBean("ossConfig");
				String ossurl = ossConfig.getOss_getUrl();
				Map valueMap = new HashMap();
				valueMap.put("ossurl", ossurl);
				valueMap.put("dynamicList", dynamicList);
				return super.getSuccessAjaxResult("获取成功！", valueMap);
			}catch(Exception e){
				return super.getAjaxResult(Constants.AJAX_CODE_ERROR_INFO, e.getMessage(), "");
			}
		}
	}
	
	/**
	 * 获取我的关注用户动态
	 * @param request
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	@RequestMapping("/m/dynamic/selectMyFollowDynamic")
	public @ResponseBody Map selectMyFollowDynamic(HttpServletRequest request, String pageNum, String pageSize) {
		String userId =  super.checkLogin(request);
		if(userId == null || "".equals(userId))	{
			return super.getAjaxResult(Constants.AJAX_CODE_ERROR_NOTLOGIN, "未登录，请重新登录！", "");
		}	
		if(StringUtil.isEmpty(pageNum) || StringUtil.isEmpty(pageSize) || !com.wing.socialcontact.util.StringUtil.isNumeric(pageNum) || !com.wing.socialcontact.util.StringUtil.isNumeric(pageSize)){
			return super.getAjaxResult(Constants.AJAX_CODE_ERROR_PARAM, "参数无效，请检查！", "");
		}else{
			try{
				List dynamicList =dynamicService.selectMyFollowDynamic(userId, Integer.parseInt(pageNum), Integer.parseInt(pageSize));
				OssConfig ossConfig = (OssConfig) SpringContextUtil
						.getBean("ossConfig");
				String ossurl = ossConfig.getOss_getUrl();
				Map valueMap = new HashMap();
				valueMap.put("ossurl", ossurl);
				valueMap.put("dynamicList", dynamicList);
				return super.getSuccessAjaxResult("获取成功！", valueMap);
			}catch(Exception e){
				return super.getAjaxResult(Constants.AJAX_CODE_ERROR_INFO, e.getMessage(), "");
			}
		}
	}
	
	/**
	 * 获取我的动态
	 * @param request
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	@RequestMapping("/m/dynamic/selectMyDynamicList")
	public @ResponseBody Map selectMyDynamicList(HttpServletRequest request, String pageNum, String pageSize) {
		String userId =  super.checkLogin(request);
		if(userId == null || "".equals(userId))	{
			return super.getAjaxResult(Constants.AJAX_CODE_ERROR_NOTLOGIN, "未登录，请重新登录！", "");
		}	
		if(StringUtil.isEmpty(pageNum) || StringUtil.isEmpty(pageSize) || !com.wing.socialcontact.util.StringUtil.isNumeric(pageNum) || !com.wing.socialcontact.util.StringUtil.isNumeric(pageSize)){
			return super.getAjaxResult(Constants.AJAX_CODE_ERROR_PARAM, "参数无效，请检查！", "");
		}else{
			try{
				List dynamicList =dynamicService.selectMyDynamicList(userId, Integer.parseInt(pageNum), Integer.parseInt(pageSize));
				OssConfig ossConfig = (OssConfig) SpringContextUtil
						.getBean("ossConfig");
				String ossurl = ossConfig.getOss_getUrl();
				Map valueMap = new HashMap();
				valueMap.put("ossurl", ossurl);
				valueMap.put("dynamicList", dynamicList);
				return super.getSuccessAjaxResult("获取成功！", valueMap);
			}catch(Exception e){
				return super.getAjaxResult(Constants.AJAX_CODE_ERROR_INFO, e.getMessage(), "");
			}
		}
	}
	
	/**
	 * 获取所有动态
	 * @param request
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	@RequestMapping("/m/dynamic/selectAllDynamic")
	public @ResponseBody Map selectAllDynamic(HttpServletRequest request, String pageNum, String pageSize) {
		RedisCache redisCache = (RedisCache) SpringContextUtil.getBean("redisCache");
		if(StringUtils.isEmpty(pageSize)){
			pageSize = "10";
		}
		if(StringUtil.isEmpty(pageNum) || StringUtil.isEmpty(pageSize) || !com.wing.socialcontact.util.StringUtil.isNumeric(pageNum) || !com.wing.socialcontact.util.StringUtil.isNumeric(pageSize)){
			return super.getAjaxResult(Constants.AJAX_CODE_ERROR_PARAM, "参数无效，请检查！", "");
		}else{
			try{
				ValueWrapper vw = redisCache.get("selectAllDynamic_"+pageNum);
				
				List dynamicList = null;
				if(vw != null){
					dynamicList = (List)vw.get();
				}
						
				if(dynamicList == null){
					dynamicList =dynamicService.selectAllDynamic(Integer.parseInt(pageNum), Integer.parseInt(pageSize));
					//设置缓存时间为120秒
					redisCache.put("selectAllDynamic_"+pageNum, dynamicList, 120l);
				}
				
				OssConfig ossConfig = (OssConfig) SpringContextUtil
						.getBean("ossConfig");
				String ossurl = ossConfig.getOss_getUrl();
				Map valueMap = new HashMap();
				valueMap.put("ossurl", ossurl);
				valueMap.put("dynamicList", dynamicList);
				return super.getSuccessAjaxResult("获取成功！", valueMap);
			}catch(Exception e){
				return super.getAjaxResult(Constants.AJAX_CODE_ERROR_INFO, e.getMessage(), "");
			}
		}
	}
	
	/**
	 * 获取我访问人的动态
	 * @param request
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	@RequestMapping("/m/dynamic/selectMyVisitUserDynamicList")
	public @ResponseBody Map selectMyVisitUserDynamicList(HttpServletRequest request, String pageNum, String pageSize,String visitUserId) {
		String userId =  super.checkLogin(request);
		if(userId == null || "".equals(userId))	{
			return super.getAjaxResult(Constants.AJAX_CODE_ERROR_NOTLOGIN, "未登录，请重新登录！", "");
		}	
		if(StringUtil.isEmpty(visitUserId) || StringUtil.isEmpty(pageNum) || StringUtil.isEmpty(pageSize) || !com.wing.socialcontact.util.StringUtil.isNumeric(pageNum) || !com.wing.socialcontact.util.StringUtil.isNumeric(pageSize)){
			return super.getAjaxResult(Constants.AJAX_CODE_ERROR_PARAM, "参数无效，请检查！", "");
		}else{
			try{
				String isFriend = "0";
				ImFriend imfriend = imFriendService.findByUserAndFriend(userId,visitUserId);
				if(imfriend != null){
					isFriend = "1";
				}
				List dynamicList = dynamicService.selectMyVisitUserDynamicList(userId, visitUserId, isFriend, Integer.parseInt(pageNum), Integer.parseInt(pageSize));
				OssConfig ossConfig = (OssConfig) SpringContextUtil
						.getBean("ossConfig");
				String ossurl = ossConfig.getOss_getUrl();
				Map valueMap = new HashMap();
				valueMap.put("ossurl", ossurl);
				valueMap.put("dynamicList", dynamicList);
				return super.getSuccessAjaxResult("获取成功！", valueMap);
			}catch(Exception e){
				return super.getAjaxResult(Constants.AJAX_CODE_ERROR_INFO, e.getMessage(), "");
			}
		}
	}
	
	/**
	 * 动态点赞
	 * 
	 * @return
	 */
	@RequestMapping("/m/dynamic/thumbup")
	public @ResponseBody Map thumbup(HttpServletRequest request, String id) throws IOException {
		
		String userId =  super.checkLogin(request);
		if(userId == null || "".equals(userId))	{
			return super.getAjaxResult(Constants.AJAX_CODE_ERROR_NOTLOGIN, "未登录，请重新登录！", "");
		}	
		
		//动态点赞
		//userIntegralLogService.addLntAndEmp(userId, "task_0014");
		
		if (StringUtil.isEmpty(id)) {
			return super.getAjaxResult(Constants.AJAX_CODE_ERROR_PARAM, "参数错误",
					null);
		}
		
		DynamicOpLog dynamicOpLog = new DynamicOpLog();
		dynamicOpLog.setUserId(userId);
		dynamicOpLog.setDynamicId(id);
		dynamicOpLog.setOpType(1);
		
		List dynamicOplogList = dynamicOpLogService.selectAllDynamicOpLog(dynamicOpLog);
		
		if (dynamicOplogList.size() <= 0) {
			dynamicOpLog.setId(UUID.randomUUID().toString());
			dynamicOpLog.setActionTime(new Date());
			dynamicOpLogService.addDynamicOpLog(dynamicOpLog);
			return super.getSuccessAjaxResult("0");
		} else {
			Map opLog = (Map)dynamicOplogList.get(0);
			dynamicOpLogService.deleteOpLogById((String)opLog.get("id"));
			return super.getSuccessAjaxResult("1");
		}
	}
	
	/**
	 * 判断是否已经转发
	 * 
	 * @return
	 */
	@RequestMapping("/m/dynamic/isForward")
	public @ResponseBody Map isForward(HttpServletRequest request, String dynamicId) throws IOException {
		
		String userId =  super.checkLogin(request);
		if(userId == null || "".equals(userId))	{
			return super.getAjaxResult(Constants.AJAX_CODE_ERROR_NOTLOGIN, "未登录，请重新登录！", "");
		}	
		
		if (StringUtil.isEmpty(dynamicId)) {
			return super.getAjaxResult(Constants.AJAX_CODE_ERROR_PARAM, "参数错误",
					null);
		}
		
		DynamicOpLog dynamicOpLog = new DynamicOpLog();
		dynamicOpLog.setUserId(userId);
		dynamicOpLog.setDynamicId(dynamicId);
		dynamicOpLog.setOpType(2);
		
		List dynamicOplogList = dynamicOpLogService.selectAllDynamicOpLog(dynamicOpLog);
		
		if (dynamicOplogList.size() <= 0) {
			return super.getSuccessAjaxResult("0");
		} else {
			return super.getSuccessAjaxResult("1");
		}
	}
	
	/**
	 * 判断是否已经打赏
	 * 
	 * @return
	 */
	@RequestMapping("/m/dynamic/isReward")
	public @ResponseBody Map isReward(HttpServletRequest request, String dynamicId) throws IOException {
		
		String userId =  super.checkLogin(request);
		if(userId == null || "".equals(userId))	{
			return super.getAjaxResult(Constants.AJAX_CODE_ERROR_NOTLOGIN, "未登录，请重新登录！", "");
		}	
		
		if (StringUtil.isEmpty(dynamicId)) {
			return super.getAjaxResult(Constants.AJAX_CODE_ERROR_PARAM, "参数错误",
					null);
		}
		
		
		
		DynamicPayLog dynamicPayLog = new DynamicPayLog();
		dynamicPayLog.setUserId(userId);
		dynamicPayLog.setDynamicId(dynamicId);
		dynamicPayLog.setActionType(1);
		
		List dynamicPaylogList = dynamicPayLogService.selectAllDynamicPayLog(dynamicPayLog);
		
		if (dynamicPaylogList.size() <= 0) {
			return super.getSuccessAjaxResult("0");
		} else {
			return super.getSuccessAjaxResult("1");
		}
	}
	
	/**
	 * 评论页面
	 * @return
	 */
	@RequestMapping("/m/dynamic/commentPage")
	public String commentPage(ModelMap map,String id){
		map.addAttribute("id", id);
		return "dynamic/commentPage";
	}
	/**
	 * 评论页面 活动评论
	 * @return
	 */
	@RequestMapping("/m/dynamic/commentPage2")
	public String commentPage2(ModelMap map,String id){
		map.addAttribute("id", id);
		return "dynamic/commentPage2";
	}
	
	/**
	 * 评论页面
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping("/m/dynamic/forwardDynamic")
	public String forwardDynamic(HttpServletRequest request,HttpServletResponse response,ModelMap map,String dynamicId,String fromurl) throws IOException{
		String path = request.getContextPath(); 
		String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
		Member me = ServletUtil.getMember(request);
		if (null == me) {
			//response.sendRedirect(basePath+"m/my/reconPage.do");
		}
		String userId = me.getId();
		if (StringUtils.isEmpty(userId)) {
			//response.sendRedirect(basePath+"m/my/reconPage.do");
		}
		TjyUser tjyUser = tjyUserService.selectByPrimaryKey(userId);
		if (null == tjyUser) {
			//response.sendRedirect(basePath+"m/my/reconPage.do");
		}
		if (!"1".equals(tjyUser.getIsRealname() + "")) {
			
			//response.sendRedirect(basePath+"m/my/reconPage.do");
		}
		
		OssConfig ossConfig = (OssConfig) SpringContextUtil
				.getBean("ossConfig");
		String ossurl = ossConfig.getOss_getUrl();
		Dynamic dynamic = dynamicService.getDynamicSignup(dynamicId);
		List<DynamicPic> picList = dynamicPicService.selectAllDynamicPicList(dynamicId);
		String fromDynamicId = dynamicId;
		map.addAttribute("fromDynamicId", dynamicId);
		map.addAttribute("ossurl", ossurl);
		map.addAttribute("dynamic", dynamic);
		
		JSONArray jsonArray = new JSONArray();
		for(int i = 0;i<picList.size();i++){
			DynamicPic pic = picList.get(i);
			JSONObject obj = JSONObject.fromObject(pic);
			jsonArray.add(obj);
		}
		map.addAttribute("picList", jsonArray);
		
		map.addAttribute("dyContentLenth", dynamic.getDyContent().length());
		map.addAttribute("dyContent", dynamic.getDyContent());
		map.addAttribute("fromurl", fromurl);
		return "dynamic/forwardDynamic";
	}
	
	
	@RequestMapping("/add/m/dynamic/insertForwardDynamic")
	public @ResponseBody Map insertForwardDynamic(HttpServletRequest request, Dynamic dynamic,
			String zjImgerJson,String fromDynamicId) {
		String userId =  super.checkLogin(request);
		if(userId == null || "".equals(userId))	{
			return super.getAjaxResult(Constants.AJAX_CODE_ERROR_NOTLOGIN, "未登录，请重新登录！", "");
		}	
		String dynamicId = UUID.randomUUID().toString().replace("-", "");
		dynamic.setDyContent(EsapiTest.stripXSS(dynamic.getDyContent()));
		try {
			
			DynamicOpLog dynamicOpLogOld = new DynamicOpLog();
			dynamicOpLogOld.setUserId(userId);
			dynamicOpLogOld.setDynamicId(fromDynamicId);
			dynamicOpLogOld.setOpType(2);
			
			List dynamicOplogListOld = dynamicOpLogService.selectAllDynamicOpLog(dynamicOpLogOld);
			
			if (dynamicOplogListOld.size() > 0) {
				throw new Exception("您已转发过此动态，不能重新转发!");
			} 
			String[] zjImgerJsons = zjImgerJson.split(",");
//			JSONArray ja = JSONArray.fromObject(zjImgerJson);
//			JSONObject jo = null;
			List<DynamicPic> dynamicPicList = new ArrayList(); 
			for (int i = 0; i < zjImgerJsons.length; i++) {
				//jo = ja.getJSONObject(i);
				String imgUrl = zjImgerJsons[i];
				if(!StringUtil.isEmpty(imgUrl)&&!"".equals(imgUrl)){
					int type = 1;
					DynamicPic dynamicPic = new DynamicPic();
					dynamicPic.setId(UUID.randomUUID().toString());
					dynamicPic.setDynamicId(dynamicId);
					dynamicPic.setCreateTime(new Date());
					dynamicPic.setPicUrl(imgUrl);
					dynamicPic.setUserId(userId);
					dynamicPic.setSortNum((double)i);
					dynamicPicList.add(dynamicPic);
				}
			}
			dynamic.setStatus(1);
			dynamic.setUserId(userId);
			Date now = new Date();
			dynamic.setIssuedDate(now);
			dynamic.setCreateTime(now);
			dynamic.setIsStick(0);
			dynamic.setId(dynamicId);
			dynamic.setDyType("0");
			dynamic.setAllowComment(1);
			dynamic.setAllowReword(1);
			String articleid = dynamic.getArticleid();
			if(null!=articleid&&!"".equals(articleid)){
				TjyLibrary li = libraryService.getTjyLibraryByid(articleid);
				if(null!=li){
					TjyLibraryClass lic = libraryclassservice.getTjyLibraryClassByid(li.getOneclass());
					dynamic.setAdate(li.getCreateTime());
					dynamic.setAtitle(li.getTitle());
					dynamic.setAimgpath(li.getImgpath());
					dynamic.setAclassname(lic.getName());
				}
			}
			int count = dynamicService.insertDynamicSignup(dynamic);
			int dynamicPicCount = dynamicPicService.insertDynamicPicList(dynamicPicList);
			
			//设置转发记录
			DynamicOpLog dynamicOpLog = new DynamicOpLog();
			dynamicOpLog.setUserId(userId);
			dynamicOpLog.setDynamicId(fromDynamicId);
			dynamicOpLog.setOpType(2);
			dynamicOpLog.setId(UUID.randomUUID().toString());
			dynamicOpLog.setActionTime(new Date());
			dynamicOpLogService.addDynamicOpLog(dynamicOpLog);
			
			return super.getSuccessAjaxResult("转发成功！", "");
		} catch (Exception e) {
			return super.getAjaxResult(Constants.AJAX_CODE_ERROR_INFO, e.getMessage(), "");
		}
	}
	
	/**
	 * 打赏页面
	 * @return
	 */
	@RequestMapping("/m/dynamic/rewardPage")
	public String rewardPage(HttpServletRequest request,ModelMap map,String dynamicId,String fromUrl){
		String userId =  super.checkLogin(request);
		map.addAttribute("dynamicId", dynamicId);
		WxUser wxUser = wxUserService.selectByPrimaryKey(userId);
		if(wxUser != null){
			map.addAttribute("jbAmount", wxUser.getJbAmount() == null? 0: wxUser.getJbAmount().intValue());
		}
		map.addAttribute("fromUrl", fromUrl);
		return "dynamic/rewardPage";
	}
	
	/**
	 * 打赏j币
	 * @param request
	 * @param response
	 * @param fkId
	 * @param jcount
	 * @return
	 */
	@RequestMapping("/m/dynamic/rewardJ")
	public @ResponseBody Map rewardJ(HttpServletRequest request,HttpServletResponse response,String dynamicId,String jcount){
		String userId =  super.checkLogin(request);
		if(userId == null || "".equals(userId))	{
			return super.getAjaxResult(Constants.AJAX_CODE_ERROR_NOTLOGIN, "未登录，请重新登录！", "");
		}	
		if(StringUtil.isEmpty(jcount) || StringUtils.isEmpty(dynamicId)){
			return super.getAjaxResult(Constants.AJAX_CODE_ERROR_PARAM, "参数无效，请检查！", "");
		}else{
			try{
				dynamicPayLogService.insertGratuity(userId, dynamicId, jcount);
				return super.getSuccessAjaxResult("打赏成功！", "");
			}catch(Exception e){
				System.out.println(e.getMessage());
				return super.getAjaxResult(Constants.AJAX_CODE_ERROR_INFO, e.getMessage(), "");
			}
			
		}
		
	}
	
	/**
	 * 删除动态
	 * @param request
	 * @param dynamicId
	 * @return
	 */
	@RequestMapping("/m/dynamic/delDynamic")
	public @ResponseBody Map delDynamic(HttpServletRequest request, String dynamicId) {
		String userId =  super.checkLogin(request);
		if(userId == null || "".equals(userId))	{
			return super.getAjaxResult(Constants.AJAX_CODE_ERROR_NOTLOGIN, "未登录，请重新登录！", "");
		}	
		if(StringUtils.isEmpty(dynamicId)){
			return super.getAjaxResult(Constants.AJAX_CODE_ERROR_PARAM, "参数无效，请检查！", "");
		}else{
			try{
				dynamicService.deleteDynamic(dynamicId);
				return super.getSuccessAjaxResult("删除成功！", "");
			}catch(Exception e){
				return super.getAjaxResult(Constants.AJAX_CODE_ERROR_INFO, e.getMessage(), "");
			}
		}
	}
	
	/**
	 * 判断是否需要付费
	 * 
	 * @return
	 */
	@RequestMapping("/m/dynamic/isPayofMedia")
	public @ResponseBody Map isPayofMedia(HttpServletRequest request, String dynamicId) throws IOException {
		
		String userId =  super.checkLogin(request);
		if(userId == null || "".equals(userId))	{
			return super.getAjaxResult(Constants.AJAX_CODE_ERROR_NOTLOGIN, "未登录，请重新登录！", "");
		}	
		
		if (StringUtil.isEmpty(dynamicId)) {
			return super.getAjaxResult(Constants.AJAX_CODE_ERROR_PARAM, "参数错误",
					null);
		}
		
		Dynamic dynamic = dynamicService.getDynamicSignup(dynamicId);
		
		if(userId.equals(dynamic.getUserId())){
			return super.getSuccessAjaxResult("1");
		}
		
		long mediaPrice = dynamic.getMediaPrice();
		
		
		DynamicPayLog dynamicPayLog = new DynamicPayLog();
		dynamicPayLog.setUserId(userId);
		dynamicPayLog.setDynamicId(dynamicId);
		dynamicPayLog.setActionType(2);
		
		List dynamicPaylogList = dynamicPayLogService.selectAllDynamicPayLog(dynamicPayLog);
		
		if (dynamicPaylogList.size() <= 0) {
			if(mediaPrice == 0){
				return super.getSuccessAjaxResult("1");
			}else{
				return super.getSuccessAjaxResult("0");
			}
			
		} else {
			return super.getSuccessAjaxResult("1");
		}
	}
	
	/**
	 * 支付语音费用页面
	 * @return
	 */
	@RequestMapping("/m/dynamic/paymediaPage")
	public String paymediaPage(HttpServletRequest request,ModelMap map,String dynamicId){
		String userId =  super.checkLogin(request);
		if(userId == null || "".equals(userId))	{
			throw new RuntimeException("未登录，请重新登录!");
		}	
		WxUser wxUser = wxUserService.selectByPrimaryKey(userId);
		Dynamic dynamic = dynamicService.getDynamicSignup(dynamicId);
		map.addAttribute("dynamicObj", dynamic);
		if(wxUser != null){
			map.addAttribute("jbAmount", wxUser.getJbAmount() == null? 0: wxUser.getJbAmount());
		}
		
		return "dynamic/paymediaPage";
	}
	
	/**
	 * 打赏j币
	 * @param request
	 * @param response
	 * @param fkId
	 * @param jcount
	 * @return
	 */
	@RequestMapping("/m/dynamic/paymedia")
	public @ResponseBody Map paymedia(HttpServletRequest request,HttpServletResponse response,String dynamicId,String jcount){
		String userId =  super.checkLogin(request);
		if(userId == null || "".equals(userId))	{
			return super.getAjaxResult(Constants.AJAX_CODE_ERROR_NOTLOGIN, "未登录，请重新登录！", "");
		}	
		if(StringUtil.isEmpty(jcount) || StringUtils.isEmpty(dynamicId)){
			return super.getAjaxResult(Constants.AJAX_CODE_ERROR_PARAM, "参数无效，请检查！", "");
		}else{
			try{
				WxUser wxUser = wxUserService.selectByPrimaryKey(userId);
				if((wxUser.getJbAmount() != null ?wxUser.getJbAmount():0)  < Double.parseDouble(jcount)){
					return super.getAjaxResult(Constants.AJAX_CODE_ERROR_INFO, "余额不足，不能支付!", "");
				}
				
				dynamicPayLogService.insertMediaPay(userId, dynamicId, jcount);
				return super.getSuccessAjaxResult("支付成功！", "");
			}catch(Exception e){
				e.printStackTrace();
				return super.getAjaxResult(Constants.AJAX_CODE_ERROR_INFO, e.getMessage(), "");
			}
			
		}
		
	}
	
	/**
	 * 获取系统时间戳
	 * @param request
	 * @param response
	 * @param fkId
	 * @param jcount
	 * @return
	 */
	@RequestMapping("/m/dynamic/sysdatetime")
	public @ResponseBody Map sysdatetime(HttpServletRequest request,HttpServletResponse response){
		String userId =  super.checkLogin(request);
		if(userId == null || "".equals(userId))	{
			return super.getAjaxResult(Constants.AJAX_CODE_ERROR_NOTLOGIN, "未登录，请重新登录！", "");
		}	
		Long nowtime = (new Date()).getTime()/1000;
		return super.getSuccessAjaxResult("获取系统时间成功！", String.valueOf(nowtime));
	}
	
	/**
	 * 获取我的商友动态
	 * @param request
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	@RequestMapping("/m/dynamic/selectNewMyFriendDynamicCount")
	public @ResponseBody Map selectNewMyFriendDynamicCount(HttpServletRequest request,Long dynamicloadtime) {
		//如里不传userId 则查自己好友的动态，如果传userId  则查userId好友的动态  
		String userId = request.getParameter("userId");
		if(StringUtil.isEmpty(userId)){
			userId =  super.checkLogin(request);
		}
		if(userId == null || "".equals(userId))	{
			return super.getAjaxResult(Constants.AJAX_CODE_ERROR_NOTLOGIN, "未登录，请重新登录！", "");
		}	
		int newDynamicCount = dynamicService.selectCountAllUserDynamicByUserId(userId, dynamicloadtime);
		return super.getSuccessAjaxResult("获取成功！", newDynamicCount);
	}
}
