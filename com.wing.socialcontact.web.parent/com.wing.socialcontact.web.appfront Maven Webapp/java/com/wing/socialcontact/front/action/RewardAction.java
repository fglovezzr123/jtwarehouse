package com.wing.socialcontact.front.action;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
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
import com.wing.socialcontact.service.wx.api.IAttentionService;
import com.wing.socialcontact.service.wx.api.ICommentService;
import com.wing.socialcontact.service.wx.api.ICommentThumbupService;
import com.wing.socialcontact.service.wx.api.IMessageInfoService;
import com.wing.socialcontact.service.wx.api.IRewardAnswerService;
import com.wing.socialcontact.service.wx.api.IRewardClassService;
import com.wing.socialcontact.service.wx.api.IRewardService;
import com.wing.socialcontact.service.wx.api.IRewardSetService;
import com.wing.socialcontact.service.wx.api.ITjyUserService;
import com.wing.socialcontact.service.wx.api.IUserIntegralLogService;
import com.wing.socialcontact.service.wx.api.IWalletLogService;
import com.wing.socialcontact.service.wx.api.IWxUserService;
import com.wing.socialcontact.service.wx.bean.Attention;
import com.wing.socialcontact.service.wx.bean.Comment;
import com.wing.socialcontact.service.wx.bean.CommentThumbup;
import com.wing.socialcontact.service.wx.bean.MessageInfo;
import com.wing.socialcontact.service.wx.bean.Reward;
import com.wing.socialcontact.service.wx.bean.RewardAnswer;
import com.wing.socialcontact.service.wx.bean.RewardSet;
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
 * 悬赏管理
 * @author zhangfan
 *
 */
@Controller
@RequestMapping("/m/reward")
public class RewardAction extends BaseAction{
	
	@Autowired
	private IRewardClassService rewardClassService; 
	@Autowired
	private IRewardService rewardService; 
	@Autowired
	private IRewardAnswerService rewardAnswerService;
	@Autowired
	private IRewardSetService rewardSetService;
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
	@Resource
	protected IUserIntegralLogService userIntegralLogService;
	/**
	 * 悬赏首页
	 * 
	 * @return
	 */
	@RequestMapping("indexPage")
	public String indexPage(ModelMap map){
		map.put("bannerid", Constants.BANNER_REWARD_ID);
		return "wisdomGroup/zhuge-answer";
	}
	/**
	 * 首页分类
	 * @return
	 */
	@RequestMapping("selClassList")
	public @ResponseBody Map selClassList(){
		//分类
		List classList = rewardClassService.selectAllClass(7,1);
		Map res = new HashMap();
		res.put("classList", classList);
		OssConfig ossConfig = (OssConfig) SpringContextUtil.getBean("ossConfig");
		String ossurl = ossConfig.getOss_getUrl();
		res.put("ossurl", ossurl);
		return super.getSuccessAjaxResult("获取成功！", res);
	}
	/**
	 * 首页悬赏列表
	 * @param type
	 * @return
	 */
	@RequestMapping("selRewardList")
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
		/*if(type.equals("1")){
			//高额悬赏
			list = rewardService.selectFrontReward(page, size, null, null, null,Integer.parseInt(type), 2);
		}else if(type.equals("2")){
			//最新提问
			list = rewardService.selectFrontReward(page,size, null, null, null,Integer.parseInt(type),null);
		}*/
		list = rewardService.selectFrontReward(page,size, null, null, null,Integer.parseInt(type),null,userId);
		for(Map<String, Object> m : list){
			m.put("createTime", m.get("createTime")==null?"":CommUtil.getTimesToNow2(CommUtil
					.formatLongDate(m.get("createTime"))));
		}
		return super.getSuccessAjaxResult("获取成功！", list);
	}
	/**
	 * 全部分类页面
	 * @param map
	 * @return
	 */
	@RequestMapping("classPage")
	public String classPage(ModelMap map){
		return "wisdomGroup/classPage";
	}
	@RequestMapping("selAllClassList")
	public @ResponseBody Map selAllClassList(){
		//分类
		List classList = rewardClassService.selectAllClass(null,null);
		Map res = new HashMap();
		res.put("classList", classList);
		OssConfig ossConfig = (OssConfig) SpringContextUtil.getBean("ossConfig");
		String ossurl = ossConfig.getOss_getUrl();
		res.put("ossurl", ossurl);
		return super.getSuccessAjaxResult("获取成功！", res);
	}
	/**
	 * 发布悬赏页面
	 * @return
	 */
	@RequestMapping("addRewardPage")
	public String addRewardPage(HttpServletRequest request,HttpServletResponse response,ModelMap map){
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
		List list = rewardClassService.selectAllClass(null,null);
		map.addAttribute("list", list);
		RewardSet rs = null;
		//悬赏金额设置
		Integer minReward = 1;
		List rlist = rewardSetService.selectRewardSet();
		if(rlist!=null&&rlist.size()>0){
			rs = (RewardSet)rlist.get(0);
			minReward = rs.getMinReward()==null?1:rs.getMinReward();
		}
		map.addAttribute("minReward", minReward);
		return  "wisdomGroup/rewardAdd";
	}
	/**
	 * 添加悬赏
	 * @return
	 * @throws ParseException 
	 */
	@RequestMapping("addReward")
	public @ResponseBody Map addReward(HttpServletRequest request,HttpServletResponse response,String question,
			String voType,String reward,String startTime,String endTime,String allowComment,
			String isShow,String remark) throws ParseException{
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
				return super.getAjaxResult("501", "J币不足，请充值之后再发布悬赏！", null);
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
				log.setRemark("诸葛解惑悬赏");
				log.setYeAmount(doub);
				log.setBusinessType(5);
				walletLogService.addWalletLog(log);
			}
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Reward r = new Reward();
		r.setQuestion(question);
		r.setCreateTime(new Date());
		r.setVoType(voType);
		r.setStartTime(sdf.parse(startTime));
		r.setEndTime(sdf.parse(endTime));
		r.setReward(reward.equals("")?0:Integer.parseInt(reward));
		r.setRemark(remark);
		r.setAllowComment(Integer.parseInt(allowComment));
		r.setCreateUserId(userId);
		r.setCreateUserName(user.getUsername());
		r.setStatus(2);
		r.setIsShow(Integer.parseInt(isShow));
		String resultStr = rewardService.addReward(r);
		//此项每日积分上限为30
		userIntegralLogService.addLntAndEmp(userId, "task_0006");
		return super.getSuccessAjaxResult();
	}
	/**
	 * 悬赏详情页
	 * @return
	 */
	@RequestMapping("detailPage")
	public String detailPage(HttpServletRequest request,HttpServletResponse response,ModelMap map,String id,String type){
		if("2".equals(type)){//
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
		return "wisdomGroup/jhdetail";
	}
	/**
	 * 悬赏详情
	 * @param id
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
		Map<String, Object> rd = rewardService.selectRewardById(id);
		rd.put("createTime", rd.get("createTime")==null?"":CommUtil.getTimesToNow2(CommUtil
				.formatLongDate(rd.get("createTime"))));
		//查看该悬赏的所有应答
		List<Map<String, Object>> list = rewardAnswerService.selectRAByFkId(id, null);
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
		res.put("ralist", list);
		res.put("reward", rd);
		return super.getSuccessAjaxResult("获取成功！", res);
	}
	/**
	 * 悬赏列表页面
	 * 
	 * @return
	 */
	@RequestMapping("listPage")
	public String listPage(ModelMap map,String voType){
		map.put("voType", voType);
		return "wisdomGroup/zhugeList";
	}
	/**
	 * 查询合作列表
	 * @param type
	 * @return
	 */
	@RequestMapping("selListByType")
	public @ResponseBody Map selListByType(HttpServletRequest request,HttpServletResponse response,
			String voType,String question,Integer page,Integer size,String type){
		List<Map<String, Object>> list = new ArrayList();
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
		if (page==null||page<1) {
			page = 1;
		}
		if (size==null||size<1) {
			size = 10;
		}
		list = rewardService.selectFrontReward(page, size, question, voType, null,Integer.parseInt(type), null,userId);
		for(Map<String, Object> m : list){
			m.put("createTime", CommUtil.getTimesToNow2(CommUtil
					.formatLongDate(m.get("createTime"))));
		}
		res.put("list", list);
		return super.getSuccessAjaxResult("获取成功！", res);
	}
	
	/**
	 * 发布应答页面
	 * @return
	 */
	@RequestMapping("addRAPage")
	public String addRAPage(HttpServletRequest request,HttpServletResponse response,ModelMap map,String fkId){
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
		Reward r = rewardService.selectById(fkId);
		map.addAttribute("reward", r);
		return  "wisdomGroup/addRAPage";
	}
	/**
	 * 添加应答
	 * @return
	 * @throws ParseException 
	 */
	@RequestMapping("addRA")
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
		RewardAnswer ra = new RewardAnswer();
		ra.setCreateTime(new Date());
		ra.setContent(content);
		ra.setFkId(fkId);
		ra.setCreateUserId(userId);
		ra.setCreateUserName(user.getUsername());
		ra.setIsAccept(2);
		ra.setIsShow(1);
		String resultStr = rewardAnswerService.addRA(ra);
		return super.getSuccessAjaxResult();
	}
	/**
	 * 我的提问列表页
	 * @return
	 */
	@RequestMapping("selMyRewardPage")
	public String selMyRewardPage(HttpServletRequest request,HttpServletResponse response,ModelMap map,String fkId){
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
		return  "wisdomGroup/zhugeListMy";
	}
	/**
	 * 我的提问列表页
	 * @param type
	 * @return
	 */
	@RequestMapping("selMyRewardList")
	public @ResponseBody Map selMyRewardList(HttpServletRequest request,HttpServletResponse response,
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
		List<Map<String, Object>> list = new ArrayList();
		list = rewardService.selectFrontReward(page, size, null, null, userId,Integer.parseInt(type), null,null);
		for(Map<String, Object> m : list){
			m.put("createTime", m.get("createTime")==null?"":CommUtil.getTimesToNow2(CommUtil
					.formatLongDate(m.get("createTime"))));
		}
		return super.getSuccessAjaxResult("获取成功！", list);
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
		attention.setAttType("reward");
		String resultStr = attentionService.saveOrDelAttention(attention);
		return super.getAjaxResult("0",resultStr,null);
	}
	/**
	 * 采纳
	 * @return
	 */
	/*@RequestMapping("accept")
	public @ResponseBody Map accept(HttpServletRequest request,HttpServletResponse response,String id){
		Member me = (Member) request.getSession().getAttribute("me");
		if (null == me) {
			return super.getAjaxResult("302", "登录超时，请重新登录", null);
		}
		String userId = me.getId();
		if (StringUtils.isEmpty(userId)) {
			return super.getAjaxResult("302", "登录超时，请重新登录", null);
		}
		RewardAnswer ra = rewardAnswerService.selectById(id);
		ra.setIsAccept(1);
		rewardAnswerService.updateRA(ra);
		return super.getSuccessAjaxResult();
	}*/
	/**
	 * 我发布悬赏的悬赏详情页
	 * @return
	 */
	@RequestMapping("myDetailPage")
	public String myDetailPage(ModelMap map,String id){
		map.addAttribute("id", id);
		return "wisdomGroup/jhdetailSeft";
	}
	
	/**
	 * 完成悬赏
	 * @param request
	 * @param response
	 * @param idbnyvb
	 * @return
	 */
	@RequestMapping("rewardFinish")
	public @ResponseBody Map rewardFinish(HttpServletRequest request,HttpServletResponse response,
			String[] ids,String id){
		Member me = (Member) request.getSession().getAttribute("me");
		if (null == me) {
			return super.getAjaxResult("302", "登录超时，请重新登录", null);
		}
		String userId = me.getId();
		if (StringUtils.isEmpty(userId)) {
			return super.getAjaxResult("302", "登录超时，请重新登录", null);
		}
		Reward b = rewardService.selectById(id);
		if(b.getStatus()==4){
			return super.getAjaxResult("503", "已完成悬赏，不能重复采纳应答！", null);
		}
		RewardSet rs = null;
		List rlist = rewardSetService.selectRewardSet();
		Double per = 1.0;
		if(rlist!=null&&rlist.size()>0){
			rs = (RewardSet)rlist.get(0);
			per = rs.getChargePer()==null?1.0:(1-rs.getChargePer()/100);
		}
		//该提问的被采纳应答
		if(ids!=null&&ids.length>0){
			Integer rd = (int) (b.getReward()*per);
			Integer cou = rd/ids.length;
			Integer yu = rd%ids.length;
			for (int i=0;i<ids.length;i++) {
				//更新应答状态
				RewardAnswer ra = rewardAnswerService.selectById(ids[i]);
				ra.setIsAccept(1);
				rewardAnswerService.updateRA(ra);
				//给商洽的发布者加上j币
				Integer num = 0;
				if(i==ids.length-1){
					num = cou+yu;
				}else{
					num = cou;
				}
				WxUser userf = wxUserService.selectById(ra.getCreateUserId());
				userf.setJbAmount(DoubleUtil.add(userf.getJbAmount(), Double.valueOf(num)));
				wxUserService.updateWxUser(userf);
				WalletLog logf = new WalletLog();
				logf.setCreateTime(new Date());
				logf.setType("2");
				logf.setPdType("1");
				logf.setUserId(ra.getCreateUserId());
				logf.setAmount(Double.valueOf(num));
				logf.setRemark("获得诸葛解惑悬赏");
				logf.setPayStatus("1");
				logf.setYeAmount(userf.getJbAmount());
				logf.setBusinessType(12);
				logf.setSourceUser(userId);
				walletLogService.addWalletLog(logf);
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
				String contentdx="{name:\"" + name + "\",subname:\"" + AldyMessageUtil.SMSPRE + "诸葛解惑内\",type:\"应答已被采纳，好厉害！\",money:\"" + num + "\"}";
				messageInfo.setContent(contentdx);
				messageInfo.setStatus(0);// 未发送
				messageInfo.setTemplateId(AldyMessageUtil.MsmTemplateId.NEGOTIATE_SUCCESS);
				messageInfoService.addMessageInfo(messageInfo);
				// 发送微信
				String touser = userf.getId().toString();
				messageInfo = new MessageInfo();
				messageInfo.setId(UUIDGenerator.getUUID());
				messageInfo.setDeleted(0);
				messageInfo.setType(2);// 微信
				messageInfo.setToUserId(touser);
				messageInfo.setCreateTime(new Date());
				// 组装内容
				String content = AldyMessageUtil.userzgRewardAccept(name, AldyMessageUtil.SMSPRE,num);
//				String content = "【" + AldyMessageUtil.SMSPRE + "】尊敬的" + name + ",您在" + b.getQuestion()
//						+ "合作内所发布的商洽已被采纳，共获得"+b.getReward()+"J币，望尽快查看。";
				String con = WxMsmUtil.getTextMessageContent(content);
				messageInfo.setContent(con);
				messageInfo.setTemplateId("REWARD_CAINA");
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
			b.setStatus(4);//完成悬赏
			rewardService.updateReward(b);
			return super.getSuccessAjaxResult();
		}else{
			return super.getAjaxResult("502", "没有采纳的应答，不能完成悬赏！", null);
		}
	}
	/**
	 * 搜索
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("search")
	public String search(ModelMap modelMap){
		return "wisdomGroup/rewardsearch";
	}
	@RequestMapping("searchList")
	public String searchList(HttpServletRequest request,ModelMap map,String keywords){
		Member me = ServletUtil.getMember(request);
		if (null == me) {
			return "login";
		}
		map.addAttribute("keywords", keywords);
		return "wisdomGroup/rewardSearchList";
	}
	/**
	 * 搜索
	 * @return
	 */
	@RequestMapping("searchRewardList")
	public @ResponseBody Map searchRewardList(HttpServletRequest request,String title){
		List<Map<String, Object>> list = new ArrayList();
		Member me = (Member) request.getSession().getAttribute("me");
		if (null == me) {
			return super.getAjaxResult("302", "登录超时，请重新登录", null);
		}
		String userId = me.getId();
		if (StringUtils.isEmpty(userId)) {
			return super.getAjaxResult("302", "登录超时，请重新登录", null);
		}
		list = rewardService.selectFrontReward(null, null, title, null, null, 2, null,userId);
		for(Map<String, Object> m : list){
			m.put("createTime", CommUtil.getTimesToNow2(CommUtil
					.formatLongDate(m.get("createTime"))));
		}
		return super.getSuccessAjaxResult("获取成功！", list);
	}
	
	/**
	 * 采纳页面
	 * @return
	 */
	@RequestMapping("acceptPage")
	public String acceptPage(HttpServletRequest request,HttpServletResponse response,String id,ModelMap map){
		Member me = ServletUtil.getMember(request);
		if (null == me) {
			return "login";
		}
		map.addAttribute("id", id);
		return "wisdomGroup/jb";
	}
	/**
	 * 诸葛榜页面
	 * @return
	 */
	@RequestMapping("toplistPage")
	public String toplistPage(HttpServletRequest request,HttpServletResponse response,String type,ModelMap map){
		Member me = ServletUtil.getMember(request);
		if (null == me) {
			return "login";
		}
		return "wisdomGroup/zhuge-toplist";
	}
	/**
	 * 诸葛榜
	 * @param type
	 * @return
	 */
	@RequestMapping("topList")
	public @ResponseBody Map topList(HttpServletRequest request,HttpServletResponse response,String type){
		List<Map<String, Object>> list = new ArrayList();
		String pm = "";
		Map res = new HashMap();
		Member me = (Member) request.getSession().getAttribute("me");
		if (null == me) {
			return super.getAjaxResult("302", "登录超时，请重新登录", null);
		}
		String userId = me.getId();
		if (StringUtils.isEmpty(userId)) {
			return super.getAjaxResult("302", "登录超时，请重新登录", null);
		}
		if(type.equals("1")){//征集榜
			list = rewardService.selectZjTopList(50);
			pm = rewardService.selectZjPm(userId);
		}else if(type.equals("2")){//悬赏榜
			list = rewardService.selectXsTopList(50);
			pm = rewardService.selectXsPm(userId);
		}else if(type.equals("3")){//应答榜
			list = rewardService.selectYdTopList(50);
			pm = rewardService.selectYdPm(userId);
		}
		res.put("list", list);
		res.put("pm", pm.substring(0, pm.indexOf(".")));
		return super.getSuccessAjaxResult("获取成功！", res);
	}
	/**
	 * 诸葛榜的第一名
	 * @param type
	 * @return
	 */
	@RequestMapping("topListFirst")
	public @ResponseBody Map topList(HttpServletRequest request,HttpServletResponse response){
		Member me = (Member) request.getSession().getAttribute("me");
		if (null == me) {
			return super.getAjaxResult("302", "登录超时，请重新登录", null);
		}
		String userId = me.getId();
		if (StringUtils.isEmpty(userId)) {
			return super.getAjaxResult("302", "登录超时，请重新登录", null);
		}
		Map res = new HashMap();
		Map<String, Object> zj = (Map<String, Object>) rewardService.selectZjTopList(1).get(0);
		Map<String, Object> xs = (Map<String, Object>)rewardService.selectXsTopList(1).get(0);
		Map<String, Object> yd = (Map<String, Object>)rewardService.selectYdTopList(1).get(0);
		res.put("zj", zj);
		res.put("xs", xs);
		res.put("yd", yd);
		return super.getSuccessAjaxResult("获取成功！", res);
	}
	/**
	 * 个人中心-悬赏页面
	 * 
	 * @return
	 */
	@RequestMapping("myCenterRewardPage")
	public String myCenterRewardPage(ModelMap map){
		return "wisdomGroup/myCenterReward";
	}
	/**
	 * 查询个人中心-悬赏列表
	 * @return
	 */
	@RequestMapping("selMyCenterRewardList")
	public @ResponseBody Map selMyCenterRewardList(HttpServletRequest request,HttpServletResponse response,
			String types,Integer page,Integer size){
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
		if(types.equals("1")){//我参加的悬赏
			list = rewardService.selectMyReward(userId, page, size);
		}else if(types.equals("2")){//我的悬赏
			list = rewardService.selectFrontReward(page, size, null, null, userId,2, null,null);
		}else if(types.equals("3")){//我关注的悬赏
			list = rewardService.selectMyAttention(userId, page, size);
		}
		for(Map<String, Object> m : list){
			m.put("createTime", CommUtil
					.formatLongDate2(m.get("createTime")));
		}
		Map res = new HashMap();
		res.put("rList", list);
		return super.getSuccessAjaxResult("获取成功！", res);
	}
}