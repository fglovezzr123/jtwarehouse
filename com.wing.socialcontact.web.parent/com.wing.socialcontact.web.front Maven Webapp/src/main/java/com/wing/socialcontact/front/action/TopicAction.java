package com.wing.socialcontact.front.action;

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

import tk.mybatis.mapper.util.StringUtil;

import com.wing.socialcontact.common.action.BaseAction;
import com.wing.socialcontact.common.model.Member;
import com.wing.socialcontact.config.MsgConfig;
import com.wing.socialcontact.front.util.EsapiTest;
import com.wing.socialcontact.service.wx.api.IAttentionService;
import com.wing.socialcontact.service.wx.api.IReportService;
import com.wing.socialcontact.service.wx.api.ITjyUserService;
import com.wing.socialcontact.service.wx.api.ITopicService;
import com.wing.socialcontact.service.wx.api.IUserHonorService;
import com.wing.socialcontact.service.wx.api.IUserIntegralLogService;
import com.wing.socialcontact.service.wx.api.IVoteService;
import com.wing.socialcontact.service.wx.api.IWalletLogService;
import com.wing.socialcontact.service.wx.api.IWxUserService;
import com.wing.socialcontact.service.wx.bean.Attention;
import com.wing.socialcontact.service.wx.bean.Report;
import com.wing.socialcontact.service.wx.bean.TjyUser;
import com.wing.socialcontact.service.wx.bean.Topic;
import com.wing.socialcontact.service.wx.bean.Vote;
import com.wing.socialcontact.service.wx.bean.WalletLog;
import com.wing.socialcontact.service.wx.bean.WxUser;
import com.wing.socialcontact.util.Constants;
import com.wing.socialcontact.util.DoubleUtil;
import com.wing.socialcontact.util.ServletUtil;

/**
 * 话题pk管理
 * @author zhangfan
 *
 */
@Controller
@RequestMapping("/m/topic")
public class TopicAction extends BaseAction{
	
	@Autowired
	private ITopicService topicService; 
	@Autowired
	private IVoteService voteService;
	@Autowired
	private IReportService reportService; 
	@Autowired
	private IAttentionService attentionService;
	@Autowired
	private IWalletLogService walletLogService;
	@Autowired
	private IWxUserService wxUserService;
	@Autowired
	private ITjyUserService tjyUserService;
	@Autowired
	private IUserIntegralLogService userIntegralLogService;
	@Autowired
	private IUserHonorService userHonorService;
	/**
	 * 话题首页面
	 * 
	 * @return
	 */
	@RequestMapping("topicPage")
	public String topicPage(ModelMap map){
		map.put("bannerid", Constants.BANNER_PK_ID);
		return "topic/topicList";
	}
	@RequestMapping("selTopicList")
	public @ResponseBody Map selTopicList(HttpServletRequest request,HttpServletResponse response,
			String types,String titles,Integer page,Integer size){
		List list = new ArrayList();
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
		if(types.equals("1")){//最热
			list = topicService.selectHotList(titles,userId,page,size);
		}else if(types.equals("2")){//最新
			list = topicService.selectNewList(titles,userId,page,size);
		}else if(types.equals("3")){//最火
			list = topicService.selectFireList(titles,userId,page,size);
		}
		Map res = new HashMap();
		res.put("bannerid", Constants.BANNER_PK_ID);
		res.put("topicList", list);
		return super.getSuccessAjaxResult("获取成功！", res);
	}
	/**
	 * 我发布的话题页面
	 * @param map
	 * @return
	 */
	@RequestMapping("myTopicPage")
	public String myTopicPage(){
		return "topic/mytopicList";
	}
	/**
	 * 查询我发布的话题
	 * @return
	 */
	@RequestMapping("selMyTopic")
	public @ResponseBody Map selMyTopic(HttpServletRequest request,HttpServletResponse response,Integer page,Integer size){
		List list = new ArrayList();
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
		list = topicService.selectMyTopic(userId,page,size);
		Map res = new HashMap();
		res.put("topicList", list);
		return super.getSuccessAjaxResult("获取成功！", res);
	}
	/**
	 * 发布话题页面
	 * @return
	 */
	@RequestMapping("topicAddPage")
	public String topicAddPage(HttpServletRequest request,HttpServletResponse response){
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
		return  "topic/topicAdd";
	}
	/**
	 * 添加话题
	 * @param topicDesc
	 * @param redPoint
	 * @param bluePoint
	 * @param allowComment
	 * @return
	 */
	@RequestMapping("add/addTopic")
	public @ResponseBody Map addTopic(HttpServletRequest request,HttpServletResponse response,String topicDesc,
			String redPoint,String bluePoint,String allowComment,String isShow,String topicExplain){
		topicDesc= EsapiTest.stripXSS(topicDesc);
		redPoint= EsapiTest.stripXSS(redPoint);
		bluePoint= EsapiTest.stripXSS(bluePoint);
		allowComment= EsapiTest.stripXSS(allowComment);
		isShow= EsapiTest.stripXSS(isShow);
		topicExplain= EsapiTest.stripXSS(topicExplain);
		Member me = (Member) request.getSession().getAttribute("me");
		if (null == me) {
			return super.getAjaxResult("302", "登录超时，请重新登录", null);
		}
		String userId = me.getId();
		if (StringUtils.isEmpty(userId)) {
			return super.getAjaxResult("302", "登录超时，请重新登录", null);
		}
		//此项每日记首次发布
		userIntegralLogService.addLntAndEmp(userId, "task_0009");
		WxUser user = wxUserService.selectById(userId);
		Topic topic = new Topic();
		topic.setTopicDesc(topicDesc);
		topic.setRedPoint(redPoint);
		topic.setBluePoint(bluePoint);
		topic.setCreateTime(new Date());
		topic.setAllowComment(Integer.parseInt(allowComment));
		topic.setRedCount(0);
		topic.setBlueCount(0);
		topic.setCreateUserId(userId);
		topic.setCreateUserName(user.getUsername());
		topic.setIsShow(Integer.parseInt(isShow));
		topic.setIsRecommend(2);
		topic.setStatus(1);
		topic.setIsAd(0);
		topic.setTopicExplain(topicExplain);
		topic.setSort(0);
		String resultStr = topicService.addTopic(topic);
		
		//话题王	话题制造超过100条 honor_018
		List list = new ArrayList();
		list = topicService.selectMyTopic(userId,null,null);
		if(null != list && list.size()>=100){
			userHonorService.addUserAndHonor(userId, "honor_003");

		}
		if(resultStr.equals("msg.topic.unique")){
			return super.getAjaxResult("201", "该话题已存在！", null);
		}
		
		return super.getSuccessAjaxResult();
	}
	/**
	 * 详情页面
	 * @return
	 */
	@RequestMapping("detailPage")
	public String detailPage(ModelMap map,String id){
		map.addAttribute("id", id);
		return "topic/topicDetail";
	}
	/**
	 * 话题详情
	 * @param types
	 * @return
	 */
	@RequestMapping("detail")
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
		Map<String, Object> topic = topicService.selectTopicById(id);
		res.put("topic", topic);
		Integer attentionCount = topicService.attentionCount(id);
		res.put("attentionCount", attentionCount);
		//当前用户的观点
		String voteType = voteService.selectMyVoteType(userId, id);
		res.put("voteType", voteType);
		Attention a = attentionService.getAttentionByFkIdAndUserId(userId,id);
		if(null==a){
			res.put("iscollected", 0);
		}else{
			res.put("iscollected", 1);
		}
		return super.getSuccessAjaxResult("获取成功！", res);
	}
	/**
	 * 站队，选红、蓝方观点
	 * @param voteType 1红方/2蓝方
	 * @param fkId 话题id
	 * @return
	 */
	@RequestMapping("addVote")
	public @ResponseBody Map addVote(HttpServletRequest request,HttpServletResponse response,String voteType,String fkId){
		Member me = ServletUtil.getMember(request);
		if (null == me) {
			return super.getAjaxResult("302", "登录超时，请重新登录", null);
		}
		String userId = me.getId();
		if (StringUtils.isEmpty(userId)) {
			return super.getAjaxResult("302", "登录超时，请重新登录", null);
		}
		//此项每日记首次发布
		userIntegralLogService.addLntAndEmp(userId, "task_0013");
				
		Vote vote = new Vote();
		vote.setFkId(fkId);
		vote.setVoteType(voteType);
		vote.setCreateTime(new Date());
		vote.setUserId(userId);
		String resultStr = voteService.addVote(vote);
		if(resultStr.equals(MsgConfig.MSG_KEY_SUCCESS)){
			//更新话题表
			Topic topic = topicService.selectById(fkId);
			if(voteType.equals("1")||voteType=="1"){
				topic.setRedCount(topic.getRedCount()+1);
			}else if(voteType.equals("2")||voteType=="2"){
				topic.setBlueCount(topic.getBlueCount()+1);
			}
			topicService.updateTopic(topic);
			
		}else if(resultStr.equals("msg.vote.unique")){
			return super.getAjaxResult("801", "您已投过票，不能重复投票！", null);
		}
		return super.getSuccessAjaxResult();
	}
	/**
	 * 举报页面
	 * @return
	 */
	@RequestMapping("reportPage")
	public String reportPage(ModelMap map,String fkId){
		map.addAttribute("fkId", fkId);
		return "topic/reportPage";
	}
	/**
	 * 添加举报
	 * @param topicDesc
	 * @param redPoint
	 * @param bluePoint
	 * @param allowComment
	 * @return
	 */
	@RequestMapping("addReport")
	public @ResponseBody Map addReport(HttpServletRequest request,HttpServletResponse response,String fkId,String content){
		Member me = (Member) request.getSession().getAttribute("me");
		if (null == me) {
			return super.getAjaxResult("302", "登录超时，请重新登录", null);
		}
		String userId = me.getId();
		if (StringUtils.isEmpty(userId)) {
			return super.getAjaxResult("302", "登录超时，请重新登录", null);
		}
		Report report = new Report();
		report.setCreateTime(new Date());
		report.setFkId(fkId);
		report.setUserId(userId);
		report.setContent(content);
		report.setRtType("1");
		report.setIsShow(1);
		String resultStr = reportService.addReport(report);
		return super.getSuccessAjaxResult();
	}
	/**
	 * 添加关注
	 * @return
	 */
	@RequestMapping("addAttention")
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
		attention.setAttType("1");
		String resultStr = attentionService.saveOrDelAttention(attention);
		return super.getAjaxResult("0",resultStr,null);
	}
	/**
	 * 打赏页面
	 */
	@RequestMapping("rewardPage")
	public String rewardPage(HttpServletRequest request,ModelMap map,String fkId){
		String userId =  super.checkLogin(request);
		WxUser wxUser = wxUserService.selectByPrimaryKey(userId);
		if(wxUser != null){
			map.addAttribute("jbAmount", wxUser.getJbAmount() == null? 0: wxUser.getJbAmount().intValue());
		}
		map.addAttribute("fkId", fkId);
		return "topic/rewardPage";
	}
	/**
	 * 打赏j币
	 * @param request
	 * @param response
	 * @param fkId
	 * @param jcount
	 * @return
	 */
	@RequestMapping("rewardJ")
	public @ResponseBody Map rewardJ(HttpServletRequest request,HttpServletResponse response,String fkId,String jcount){
		Member me = (Member) request.getSession().getAttribute("me");
		if (null == me) {
			return super.getAjaxResult("302", "登录超时，请重新登录", null);
		}
		String userId = me.getId();
		if (StringUtils.isEmpty(userId)) {
			return super.getAjaxResult("302", "登录超时，请重新登录", null);
		}
		if(StringUtil.isEmpty(jcount)){
			return super.getAjaxResult(Constants.AJAX_CODE_ERROR_PARAM, "参数无效，请检查！", "");
		}else{
			Topic topic = topicService.selectById(fkId);
			//先减去自己的j币
			WxUser user = wxUserService.selectById(userId);
			Double doub = DoubleUtil.sub(user.getJbAmount()==null?0:user.getJbAmount(), Double.valueOf(jcount));
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
				log.setAmount(Double.valueOf(jcount));
				log.setRemark("打赏话题");
				log.setPayStatus("1");
				log.setYeAmount(user.getJbAmount());
				log.setBusinessType(10);
				log.setSourceUser(topic.getCreateUserId());
				walletLogService.addWalletLog(log);
			}
			//给话题的发布者加上j币
			WxUser userf = wxUserService.selectById(topic.getCreateUserId());
			userf.setJbAmount(DoubleUtil.add(userf.getJbAmount(), Double.valueOf(jcount)));
			wxUserService.updateWxUser(userf);
			WalletLog logf = new WalletLog();
			logf.setCreateTime(new Date());
			logf.setType("2");
			logf.setPdType("1");
			logf.setUserId(topic.getCreateUserId());
			logf.setAmount(Double.valueOf(jcount));
			logf.setRemark("获得打赏");
			logf.setPayStatus("1");
			logf.setYeAmount(userf.getJbAmount());
			logf.setBusinessType(15);
			logf.setSourceUser(userId);
			walletLogService.addWalletLog(logf);
		}
		return super.getSuccessAjaxResult();
	}
	/**
	 * 精选话题 
	 * @return
	 */
	@RequestMapping("selJxTopicList")
	public @ResponseBody Map selJxTopicList(HttpServletRequest request,HttpServletResponse response){
		Member me = (Member) request.getSession().getAttribute("me");
		if (null == me) {
			return super.getAjaxResult("302", "登录超时，请重新登录", null);
		}
		String userId = me.getId();
		if (StringUtils.isEmpty(userId)) {
			return super.getAjaxResult("302", "登录超时，请重新登录", null);
		}
		List list = new ArrayList();
		list = topicService.selectJxTopic(userId);
		return super.getSuccessAjaxResult("获取成功！", list);
	}
	
	/**
	 * 个人中心-话题页面
	 * 
	 * @return
	 */
	@RequestMapping("myCenterTopicPage")
	public String myCenterTopicPage(ModelMap map,HttpServletRequest request,HttpServletResponse response){
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
		return "topic/myCenterTopic";
	}
	/**
	 * 查询个人中心-话题列表
	 * @param types
	 * @return
	 */
	@RequestMapping("selMyCenterTopicList")
	public @ResponseBody Map selMyCenterTopicList(HttpServletRequest request,HttpServletResponse response,
			String types){
		List list = new ArrayList();
		Member me = (Member) request.getSession().getAttribute("me");
		if (null == me) {
			return super.getAjaxResult("302", "登录超时，请重新登录", null);
		}
		String userId = me.getId();
		if (StringUtils.isEmpty(userId)) {
			return super.getAjaxResult("302", "登录超时，请重新登录", null);
		}
		if(types.equals("1")){//我参与的话题
			list = topicService.selectMyVote(userId);
		}else if(types.equals("2")){//我发布的话题
			list = topicService.selectMyTopicMy(userId,null,null);
		}else if(types.equals("3")){//我关注的话题
			list = topicService.selectMyAttention(userId);
		}
		Map res = new HashMap();
		res.put("topicList", list);
		return super.getSuccessAjaxResult("获取成功！", res);
	}
	/**
	 * 搜索
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("search")
	public String search(ModelMap modelMap){
		return "topic/topicsearch";
	}
	@RequestMapping("searchList")
	public String searchList(HttpServletRequest request,ModelMap map,String keywords){
		Member me = ServletUtil.getMember(request);
		if (null == me) {
			return "login";
		}
		map.addAttribute("keywords", keywords);
		return "topic/topicSearchList";
	}
	/**
	 * 搜索
	 * @return
	 */
	@RequestMapping("searchTopicList")
	public @ResponseBody Map searchTopicList(HttpServletRequest request,String titles){
		Member me = (Member) request.getSession().getAttribute("me");
		if (null == me) {
			return super.getAjaxResult("302", "登录超时，请重新登录", null);
		}
		String userId = me.getId();
		if (StringUtils.isEmpty(userId)) {
			return super.getAjaxResult("302", "登录超时，请重新登录", null);
		}
		List list = topicService.selectNewList(titles,userId,null,null);
		return super.getSuccessAjaxResult("获取成功！", list);
	}
}
