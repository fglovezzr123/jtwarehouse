package com.wing.socialcontact.front.action;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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
import com.wing.socialcontact.config.OssConfig;
import com.wing.socialcontact.front.util.EsapiTest;
import com.wing.socialcontact.service.wx.api.IAttentionService;
import com.wing.socialcontact.service.wx.api.IBusinessClassService;
import com.wing.socialcontact.service.wx.api.IBusinessDisscussService;
import com.wing.socialcontact.service.wx.api.IBusinessService;
import com.wing.socialcontact.service.wx.api.ICommentService;
import com.wing.socialcontact.service.wx.api.ICommentThumbupService;
import com.wing.socialcontact.service.wx.api.IMessageInfoService;
import com.wing.socialcontact.service.wx.api.ITjyUserService;
import com.wing.socialcontact.service.wx.api.IWalletLogService;
import com.wing.socialcontact.service.wx.api.IWxUserService;
import com.wing.socialcontact.service.wx.bean.Attention;
import com.wing.socialcontact.service.wx.bean.Business;
import com.wing.socialcontact.service.wx.bean.BusinessDisscuss;
import com.wing.socialcontact.service.wx.bean.Comment;
import com.wing.socialcontact.service.wx.bean.CommentThumbup;
import com.wing.socialcontact.service.wx.bean.MessageInfo;
import com.wing.socialcontact.service.wx.bean.TjyUser;
import com.wing.socialcontact.service.wx.bean.WalletLog;
import com.wing.socialcontact.service.wx.bean.WxUser;
import com.wing.socialcontact.util.AldyMessageUtil;
import com.wing.socialcontact.util.CommUtil;
import com.wing.socialcontact.util.Constants;
import com.wing.socialcontact.util.DoubleUtil;
import com.wing.socialcontact.util.ServletUtil;
import com.wing.socialcontact.util.SpringContextUtil;
import com.wing.socialcontact.util.UUIDGenerator;
import com.wing.socialcontact.util.WxMsmUtil;


/**
 * 合作管理
 * @author zhangfan
 *
 */
@Controller
@RequestMapping("")
public class BusinessAction extends BaseAction{
	
	@Autowired
	private IBusinessService businessService; 
	@Autowired
	private IBusinessClassService businessClassService;
	@Autowired
	private IBusinessDisscussService businessDisscussService;
	@Autowired
	private IAttentionService attentionService;
	@Autowired
	private ICommentThumbupService commentThumbupService;
	@Autowired
	private ICommentService commentService;
	@Autowired
	private IWalletLogService walletLogService;
	@Autowired
	private IWxUserService wxUserService;
	@Autowired
	private ITjyUserService tjyUserService;
	@Autowired
	private IMessageInfoService messageInfoService;
	/**
	 * 合作洽谈首页
	 * 
	 * @return
	 */
	@RequestMapping("/m/business/indexPage")
	public String indexPage(ModelMap map){
		map.put("bannerid", Constants.BANNER_BUSINESS_ID);
		return "netWork/cooperate";
	}
	/**
	 * 首页分类
	 * @return
	 */
	@RequestMapping("/m/business/selClassList")
	public @ResponseBody Map selClassList(){
		//分类
		List classList = businessClassService.selectAllClass(7,1);
		Map res = new HashMap();
		res.put("classList", classList);
		OssConfig ossConfig = (OssConfig) SpringContextUtil.getBean("ossConfig");
		String ossurl = ossConfig.getOss_getUrl();
		res.put("ossurl", ossurl);
		return super.getSuccessAjaxResult("获取成功！", res);
	}
	/**
	 * 首页合作列表
	 * @param type
	 * @return
	 */
	@RequestMapping("/m/business/selBusinessList")
	public @ResponseBody Map selBusinessList(HttpServletRequest request,HttpServletResponse response,String type,Integer page,Integer size){
		List<Map<String, Object>> list = new ArrayList();
		Member me = (Member) request.getSession().getAttribute("me");
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
		if(type.equals("1")){
			//土豪悬赏
			list = businessService.selectFrontBusiness(page,size, null, null, 1,null,1,userId);
		}else if(type.equals("2")){
			//最新合作
			list = businessService.selectFrontBusiness(page,size, null, null, null,null,null,userId);
		}
		for(Map<String, Object> m : list){
			m.put("createTime", m.get("createTime")==null?"":CommUtil.getTimesToNow(CommUtil
					.formatLongDate(m.get("createTime"))));
		}
		return super.getSuccessAjaxResult("获取成功！", list);
	}
	/**
	 * 全部分类页面
	 * @param map
	 * @return
	 */
	@RequestMapping("/m/business/classPage")
	public String classPage(ModelMap map){
		return "netWork/classPage";
	}
	@RequestMapping("/m/business/selAllClassList")
	public @ResponseBody Map selAllClassList(){
		//分类
		List classList = businessClassService.selectAllClass(null,null);
		Map res = new HashMap();
		res.put("classList", classList);
		OssConfig ossConfig = (OssConfig) SpringContextUtil.getBean("ossConfig");
		String ossurl = ossConfig.getOss_getUrl();
		res.put("ossurl", ossurl);
		return super.getSuccessAjaxResult("获取成功！", res);
	}
	/**
	 * 发布合作页面
	 * @return
	 */
	@RequestMapping("/m/business/addBusinessPage")
	public String addBusinessPage(HttpServletRequest request,HttpServletResponse response,ModelMap map){
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
		List list = businessClassService.selectAllClass(null,null);
		map.addAttribute("list", list);
		return  "netWork/businessAdd";
	}
	/**
	 * 添加合作
	 * @return
	 * @throws ParseException 
	 */
	@RequestMapping("/add/m/business/addBusiness")
	public @ResponseBody Map addBusiness(HttpServletRequest request,HttpServletResponse response,String titles,
			String bizType,String appealType,String appealSummary,String reward,String startTime,String endTime,
			String appealDesc,String allowComment,String isShow) throws ParseException{
		titles= EsapiTest.stripXSS(titles);
		bizType= EsapiTest.stripXSS(bizType);
		appealType= EsapiTest.stripXSS(appealType);
		appealSummary= EsapiTest.stripXSS(appealSummary);
		reward= EsapiTest.stripXSS(reward);
		appealDesc= EsapiTest.stripXSS(appealDesc);
		allowComment= EsapiTest.stripXSS(allowComment);
		Member me = (Member) request.getSession().getAttribute("me");
		if (null == me) {
			return super.getAjaxResult("302", "登录超时，请重新登录", null);
		}
		String userId = me.getId();
		if (StringUtils.isEmpty(userId)) {
			return super.getAjaxResult("302", "登录超时，请重新登录", null);
		}
		WxUser user = wxUserService.selectById(userId);
		if(reward!=null&&!"".equals(reward)){
			//先减去自己的j币
			Double doub = DoubleUtil.sub(user.getJbAmount()==null?0:user.getJbAmount(), Double.valueOf(reward));
			if(doub<0){
				return super.getAjaxResult("501", "J币不足！", null);
			}else{
				user.setJbAmount(doub);
				wxUserService.updateWxUser(user);
				WalletLog log = new WalletLog();
				log.setCreateTime(new Date());
				log.setType("2");
				log.setPdType("2");
				log.setPayStatus("1");
				log.setUserId(userId);
				log.setAmount(Double.valueOf(reward));
				log.setRemark("悬赏合作");
				log.setYeAmount(doub);
				log.setBusinessType(5);
				walletLogService.addWalletLog(log);
			}
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Business business = new Business();
		business.setTitles(titles);
		business.setCreateTime(new Date());
		business.setBizType(bizType);
		business.setAppealType(Integer.parseInt(appealType));
		business.setAppealSummary(appealSummary);
		business.setStartTime(sdf.parse(startTime));
		business.setEndTime(sdf.parse(endTime));
		business.setReward(reward.equals("")?0:Integer.parseInt(reward));
		business.setAppealDesc(appealDesc);
		business.setAllowComment(Integer.parseInt(allowComment));
		business.setCreateUserId(userId);
		business.setCreateUserName(user.getUsername());
		business.setStatus(2);
		business.setLookCount(0);
		business.setRewardFinish(2);
		business.setIsShow(Integer.parseInt(isShow));
		String resultStr = businessService.addBusiness(business);
		return super.getSuccessAjaxResult();
	}
	/**
	 * 合作详情页
	 * @return
	 */
	@RequestMapping("/m/business/detailPage")
	public String detailPage(HttpServletRequest request,HttpServletResponse response,ModelMap map,String id,String type){
		if("2".equals(type)){//最新合作
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
		}
		map.addAttribute("id", id);
		return "netWork/my-cooperate";
	}
	/**
	 * 合作详情
	 * @param id
	 * @return
	 */
	@RequestMapping("/m/business/detail")
	public @ResponseBody Map detail(HttpServletRequest request,HttpServletResponse response,String id){
		Map res = new HashMap();
		Member me = (Member) request.getSession().getAttribute("me");
		if (null == me) {
			return super.getAjaxResult("302", "登录超时，请重新登录", null);
		}
		String userId = me.getId();
		if (StringUtils.isEmpty(userId)) {
			return super.getAjaxResult("302", "登录超时，请重新登录", null);
		}
		res.put("userId", userId);
		Map<String, Object> business = businessService.selectBusinessById(id);
		//增加浏览记录
		Business b = businessService.selectById(id);
		b.setLookCount(b.getLookCount()==null?1:b.getLookCount()+1);
		businessService.updateBusiness(b);
		//查看该合作的所有商洽
		List<Map<String, Object>> list = businessDisscussService.selectBDByFkId(id,null);
		int count = 0;
		int subcount = 0;
		for (Map<String, Object> m : list) {
			// 获取点赞数
			CommentThumbup commentThumbup = new CommentThumbup();
			commentThumbup.setPId((String) (m.get("id")));
			count = commentThumbupService.selectcount(commentThumbup);
			m.put("count", count);
			
			commentThumbup.setUserId(userId);
			List<Map<String, Object>> thumbupList = commentThumbupService
					.selectAllCommentThumbup(commentThumbup);
			boolean isThumbup = false;
			if (thumbupList.size() > 0) {
				isThumbup =true;
			} 
			//获取当前用户点赞状态
			m.put("isThumbup", isThumbup);
			// 获取子评论
			Comment subcomment = new Comment();
			subcomment.setParentId((String) (m.get("id")));
			List<Map<String, Object>> subCommentList = commentService
					.queryCommentbyPid((String) (m.get("id")));
			if (null != subCommentList) {
				subcount = subCommentList.size();
			}
			m.put("subcount", subcount);
			
		}
		//查询是否收藏
		Attention att = new Attention();
		att.setFkId(id);
		att.setUserId(userId);
		List<Attention> attlist = attentionService.queryAttention(att);
		int isAttention = 0;
		if(attlist.size()>0&&attlist!=null){
			isAttention = 1;
		}
		res.put("isAttention", isAttention);
		res.put("bdlist", list);
		res.put("business", business);
		return super.getSuccessAjaxResult("获取成功！", res);
	}
	/**
	 * 合作洽谈列表页面
	 * 
	 * @return
	 */
	@RequestMapping("/m/business/listPage")
	public String listPage(ModelMap map,String bizType){
		map.put("bizType", bizType);
		return "netWork/cooperation-list";
	}
	/**
	 * 查询合作列表
	 * @param type
	 * @return
	 */
	@RequestMapping("/m/business/selListByType")
	public @ResponseBody Map selListByType(HttpServletRequest request,HttpServletResponse response,
			String bizType,String titles,Integer page,Integer size){
		List<Map<String, Object>> list = new ArrayList();
		Member me = (Member) request.getSession().getAttribute("me");
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
		list = businessService.selectFrontBusiness(page,size, titles, bizType, null,null,null,userId);
		for(Map<String, Object> m : list){
			m.put("createTime", CommUtil.getTimesToNow(CommUtil
					.formatLongDate(m.get("createTime"))));
		}
		return super.getSuccessAjaxResult("获取成功！", list);
	}
	
	/**
	 * 发布合作洽谈页面
	 * @return
	 */
	@RequestMapping("/m/business/addBDPage")
	public String addBDPage(HttpServletRequest request,HttpServletResponse response,ModelMap map,String fkId){
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
		map.addAttribute("fkId", fkId);
		return  "netWork/addDBPage";
	}
	/**
	 * 添加合作洽谈
	 * @return
	 * @throws ParseException 
	 */
	@RequestMapping("/add/m/business/addBD")
	public @ResponseBody Map addBD(HttpServletRequest request,HttpServletResponse response,String content,String fkId) throws ParseException{
		Member me = (Member) request.getSession().getAttribute("me");
		if (null == me) {
			return super.getAjaxResult("302", "登录超时，请重新登录", null);
		}
		String userId = me.getId();
		if (StringUtils.isEmpty(userId)) {
			return super.getAjaxResult("302", "登录超时，请重新登录", null);
		}
		WxUser user = wxUserService.selectById(userId);
		BusinessDisscuss bd = new BusinessDisscuss();
		bd.setCreateTime(new Date());
		bd.setContent(content);
		bd.setFkId(fkId);
		bd.setCreateUserId(userId);
		bd.setCreateUserName(user.getUsername());
		bd.setIsAccept(2);
		bd.setIsShow(1);
		String resultStr = businessDisscussService.addBD(bd);
		return super.getSuccessAjaxResult();
	}
	/**
	 * 我的合作页面
	 * @return
	 */
	@RequestMapping("/m/business/selMyHzPage")
	public String selMyHzPage(HttpServletRequest request,HttpServletResponse response,ModelMap map,String fkId){
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
		map.addAttribute("fkId", fkId);
		return  "netWork/myh";
	}
	/**
	 * 我的合作列表页面
	 * @param type
	 * @return
	 */
	@RequestMapping("/m/business/selMyBusinessList")
	public @ResponseBody Map selMyBusinessList(HttpServletRequest request,HttpServletResponse response,
			String type,Integer page,Integer size){
		Member me = (Member) request.getSession().getAttribute("me");
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
		List list = new ArrayList();
		if(type.equals("1")){
			//我发布的合作
			list = businessService.selectMyBusiness(userId,page,size);
		}else if(type.equals("2")){
			//我关注的合作
			list = businessService.selectMyAttention(userId,page,size);
		}else if(type.equals("3")){
			//我的商洽
			list = businessDisscussService.selectMyBD(page, size, userId);
		}
		return super.getSuccessAjaxResult("获取成功！", list);
	}
	
	/**
	 * 添加关注
	 * @return
	 */
	@RequestMapping("/m/business/addAttention")
	public @ResponseBody Map addAttention(HttpServletRequest request,HttpServletResponse response,String fkId){
		Member me = (Member) request.getSession().getAttribute("me");
		if (null == me) {
			return super.getAjaxResult("302", "登录超时，请重新登录", null);
		}
		String userId = me.getId();
		if (StringUtils.isEmpty(userId)) {
			return super.getAjaxResult("302", "登录超时，请重新登录", null);
		}
		Attention attention = new Attention();
		attention.setCreateTime(new Date());
		attention.setFkId(fkId);
		attention.setUserId(userId);
		attention.setAttType("2");
		String resultStr = attentionService.saveOrDelAttention(attention);
		return super.getAjaxResult("0",resultStr,null);
	}
	
	/**
	 * 我发布合作的合作详情页
	 * @return
	 */
	@RequestMapping("/m/business/myDetailPage")
	public String myDetailPage(ModelMap map,String id){
		map.addAttribute("id", id);
		return "netWork/my-cooperateMe";
	}
	
	/**
	 * 采纳
	 * @return
	 */
	@RequestMapping("/m/business/accept")
	public @ResponseBody Map accept(HttpServletRequest request,HttpServletResponse response,String id,String fkId){
		Member me = (Member) request.getSession().getAttribute("me");
		if (null == me) {
			return super.getAjaxResult("302", "登录超时，请重新登录", null);
		}
		String userId = me.getId();
		if (StringUtils.isEmpty(userId)) {
			return super.getAjaxResult("302", "登录超时，请重新登录", null);
		}
		Business b = businessService.selectById(fkId);
		//查看该合作的被采纳商洽
		List<Map<String, Object>> list = businessDisscussService.selectBDByFkId(fkId,1);
		//只能采纳一条，先判断是否已经采纳
		if(list!=null&&list.size()>0){
			return super.getAjaxResult("602", "已进行过采纳操作，不可多次采纳!", null);
		}else{
			//给商洽的发布者加上j币
			Integer num = b.getReward()==null?0:b.getReward();
			BusinessDisscuss bd = businessDisscussService.selectById(id);
			WalletLog logf = new WalletLog();
			logf.setCreateTime(new Date());
			logf.setType("2");
			logf.setPdType("1");
			logf.setUserId(bd.getCreateUserId());
			logf.setAmount(Double.valueOf(num));
			logf.setRemark("获得合作悬赏");
			logf.setPayStatus("1");
			logf.setBusinessType(12);
			logf.setSourceUser(userId);
			walletLogService.addWalletLog(logf);
			WxUser userf = wxUserService.selectById(bd.getCreateUserId());
			userf.setJbAmount(DoubleUtil.add(userf.getJbAmount(), Double.valueOf(num)));
			wxUserService.updateWxUser(userf);
			b.setRewardFinish(1);
			businessService.updateBusiness(b);
			bd.setIsAccept(1);
			businessDisscussService.updateBD(bd);
			//采纳之后，发了一条信息，包含短信、微信、系统消息
			// 发送短信
			TjyUser tjyUser = tjyUserService.selectByPrimaryKey(userf.getId() + "");
			MessageInfo messageInfo = new MessageInfo();
			messageInfo.setId(UUIDGenerator.getUUID());
			messageInfo.setDeleted(0);
			messageInfo.setMobile(userf.getMobile());
			messageInfo.setType(1);// 短信
			messageInfo.setCreateTime(new Date());
			String name = tjyUser.getNickname();
			String contentdx="{name:\"" + name + "\",subname:\"" + b.getTitles() +  "\",type:\""+"合作信息"+ "\",money:\"" + b.getReward() + "\"}";
			messageInfo.setContent(contentdx);
			messageInfo.setStatus(0);// 未发送
			messageInfo.setTemplateId(AldyMessageUtil.MsmTemplateId.NEGOTIATE_SUCCESS);
			messageInfoService.addMessageInfo(messageInfo);
			// 发送微信
			String touser = bd.getCreateUserId();
			messageInfo = new MessageInfo();
			messageInfo.setId(UUIDGenerator.getUUID());
			messageInfo.setDeleted(0);
			messageInfo.setType(2);// 微信
			messageInfo.setToUserId(touser);
			messageInfo.setCreateTime(new Date());
			// 组装内容
			String content = AldyMessageUtil.userRewardAccept(name, b.getTitles(), b.getReward());
//			String content = "【" + AldyMessageUtil.SMSPRE + "】尊敬的" + name + ",您在" + b.getTitles()
//					+ "合作内所发布的商洽已被采纳，共获得"+b.getReward()+"J币，望尽快查看。";
			String con = WxMsmUtil.getTextMessageContent(content);
			messageInfo.setContent(con);
			messageInfo.setTemplateId("BUSINESS_CAINA");
			messageInfo.setStatus(0);// 未发送
			messageInfo.setWxMsgType(1);///** 微信消息类型（1：文本消息2：图文消息） */
			messageInfoService.addMessageInfo(messageInfo);
			/**
			 * 发送系统消息
			 */
			messageInfo = new MessageInfo();
			messageInfo.setId(UUIDGenerator.getUUID());
			messageInfo.setDeleted(0);
			messageInfo.setType(3);// 系统消息
			messageInfo.setToUserId(touser);
			messageInfo.setCreateTime(new Date());
			messageInfo.setContent(content);
			messageInfo.setStatus(0);// 不需要发送
			messageInfoService.addMessageInfo(messageInfo);
			return super.getSuccessAjaxResult();
		}
	}
	/**
	 * 完成悬赏
	 * @param request
	 * @param response
	 * @param idbnyvb
	 * @return
	 */
	@RequestMapping("/m/business/rewardFinish")
	public @ResponseBody Map rewardFinish(HttpServletRequest request,HttpServletResponse response,String[] ids,String id){
		Member me = (Member) request.getSession().getAttribute("me");
		if (null == me) {
			return super.getAjaxResult("302", "登录超时，请重新登录", null);
		}
		String userId = me.getId();
		if (StringUtils.isEmpty(userId)) {
			return super.getAjaxResult("302", "登录超时，请重新登录", null);
		}
		Business b = businessService.selectById(id);
		if(b.getRewardFinish()==1){
			return super.getAjaxResult("503", "该合作已完成，不能重复采纳商洽！", null);
		}
	/*	//先减去自己的j币
		WxUser user = wxUserService.selectById(userId);
		Double doub = DoubleUtil.sub(user.getJbAmount()==null?0:user.getJbAmount(), Double.valueOf(b.getReward()));
		if(doub<0){
			return super.getAjaxResult("501", "J币不足！", null);
		}else{
			user.setJbAmount(doub);
			wxUserService.updateWxUser(user);
			WalletLog log = new WalletLog();
			log.setCreateTime(new Date());
			log.setType("2");
			log.setPdType("2");
			log.setUserId(userId);
			log.setAmount(Double.valueOf(b.getReward()));
			log.setRemark("打赏合作");
			walletLogService.addWalletLog(log);
		}*/
		//该提问的被采纳应答
		if(ids!=null&&ids.length>0){
			Integer cou = b.getReward()/ids.length;
			Integer yu = b.getReward()%ids.length;
			for (int i=0;i<ids.length;i++) {
				BusinessDisscuss bd = businessDisscussService.selectById(ids[i]);
				//给被采纳者加上j币
				Integer num = 0;
				if(i==ids.length-1){
					num = cou+yu;
				}else{
					num = cou;
				}
				WxUser userf = wxUserService.selectById(bd.getCreateUserId());
				Double doub = DoubleUtil.add(userf.getJbAmount(), Double.valueOf(num));
				userf.setJbAmount(doub);
				wxUserService.updateWxUser(userf);
				//日志
				WalletLog logf = new WalletLog();
				logf.setCreateTime(new Date());
				logf.setType("2");
				logf.setPdType("1");
				logf.setUserId(bd.getCreateUserId());
				logf.setAmount(Double.valueOf(num));
				logf.setRemark("获得合作悬赏");
				logf.setPayStatus("1");
				logf.setBusinessType(12);
				logf.setSourceUser(userId);
				logf.setYeAmount(doub);
				walletLogService.addWalletLog(logf);
				//更新采纳状态
				bd.setIsAccept(1);
				businessDisscussService.updateBD(bd);
				//采纳之后，发了一条信息，包含短信、微信、系统消息
				// 发送短信
				TjyUser tjyUser = tjyUserService.selectByPrimaryKey(userf.getId() + "");
				MessageInfo messageInfo = new MessageInfo();
				messageInfo.setId(UUIDGenerator.getUUID());
				messageInfo.setDeleted(0);
				messageInfo.setMobile(userf.getMobile());
				messageInfo.setType(1);// 短信
				messageInfo.setCreateTime(new Date());
				String name = tjyUser.getNickname();
				String contentdx="{name:\"" + name + "\",subname:\"" + AldyMessageUtil.SMSPRE + "\",type:\"合作信息已被采纳，棒棒哒！\",money:\"" + num + "\"}";
				messageInfo.setContent(contentdx);
				messageInfo.setStatus(0);// 未发送
				messageInfo.setTemplateId(AldyMessageUtil.MsmTemplateId.NEGOTIATE_SUCCESS);
				messageInfoService.addMessageInfo(messageInfo);
				// 发送微信
				String touser = bd.getCreateUserId();
				messageInfo = new MessageInfo();
				messageInfo.setId(UUIDGenerator.getUUID());
				messageInfo.setDeleted(0);
				messageInfo.setType(2);// 微信
				messageInfo.setToUserId(touser);
				messageInfo.setCreateTime(new Date());
				// 组装内容
//				String content = "【" + AldyMessageUtil.SMSPRE + "】尊敬的" + name + ",您在" + b.getTitles()
//						+ "合作内所发布的商洽已被采纳，共获得"+num+"J币，望尽快查看。";
				String content = AldyMessageUtil.userRewardAccept(name, AldyMessageUtil.SMSPRE,num);
				String con = WxMsmUtil.getTextMessageContent(content);
				messageInfo.setContent(con);
				messageInfo.setTemplateId("BUSINESS_CAINA");
				messageInfo.setStatus(0);// 未发送
				messageInfo.setWxMsgType(1);///** 微信消息类型（1：文本消息2：图文消息） */
				messageInfoService.addMessageInfo(messageInfo);
				/**
				 * 发送系统消息
				 */
				messageInfo = new MessageInfo();
				messageInfo.setId(UUIDGenerator.getUUID());
				messageInfo.setDeleted(0);
				messageInfo.setType(3);// 系统消息
				messageInfo.setToUserId(touser);
				messageInfo.setCreateTime(new Date());
				messageInfo.setContent(content);
				messageInfo.setStatus(0);// 不需要发送
				messageInfoService.addMessageInfo(messageInfo);
			}
			b.setRewardFinish(1);
			businessService.updateBusiness(b);
			return super.getSuccessAjaxResult();
		}else{
			return super.getAjaxResult("502", "没有采纳的商洽，不能完成悬赏！", null);
		}
	}
	/**
	 * 搜索
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("/m/business/search")
	public String search(ModelMap modelMap){
		return "netWork/coopsearch";
	}
	@RequestMapping("/m/business/searchList")
	public String searchList(HttpServletRequest request,ModelMap map,String keywords){
		Member me = ServletUtil.getMember(request);
		if (null == me) {
			return "login";
		}
		map.addAttribute("keywords", keywords);
		return "netWork/coopSearchList";
	}
	/**
	 * 搜索
	 * @return
	 */
	@RequestMapping("/m/business/searchCoopList")
	public @ResponseBody Map searchCoopList(HttpServletRequest request,String title){
		List<Map<String, Object>> list = new ArrayList();
		Member me = (Member) request.getSession().getAttribute("me");
		if (null == me) {
			return super.getAjaxResult("302", "登录超时，请重新登录", null);
		}
		String userId = me.getId();
		if (StringUtils.isEmpty(userId)) {
			return super.getAjaxResult("302", "登录超时，请重新登录", null);
		}
		list = businessService.selectFrontBusiness(null,null, title, null, null,null,null,userId);
		for(Map<String, Object> m : list){
			m.put("createTime", CommUtil.getTimesToNow(CommUtil
					.formatLongDate(m.get("createTime"))));
		}
		return super.getSuccessAjaxResult("获取成功！", list);
	}
	/**
	 * 采纳页面
	 * @return
	 */
	@RequestMapping("/m/business/acceptPage")
	public String acceptPage(HttpServletRequest request,HttpServletResponse response,String id,ModelMap map){
		Member me = ServletUtil.getMember(request);
		if (null == me) {
			return "login";
		}
		map.addAttribute("id", id);
		return "netWork/acceptPage";
	}
}