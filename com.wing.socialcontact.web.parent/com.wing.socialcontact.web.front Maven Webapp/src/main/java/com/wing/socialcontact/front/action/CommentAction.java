package com.wing.socialcontact.front.action;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.owasp.esapi.ESAPI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.github.pagehelper.util.StringUtil;
import com.wing.socialcontact.common.action.BaseAction;
import com.wing.socialcontact.common.model.Member;
import com.wing.socialcontact.config.OssConfig;
import com.wing.socialcontact.front.util.EsapiTest;
import com.wing.socialcontact.service.wx.api.IActivityService;
import com.wing.socialcontact.service.wx.api.IBusinessDisscussService;
import com.wing.socialcontact.service.wx.api.ICommentService;
import com.wing.socialcontact.service.wx.api.ICommentThumbupService;
import com.wing.socialcontact.service.wx.api.IDynamicService;
import com.wing.socialcontact.service.wx.api.INewsService;
import com.wing.socialcontact.service.wx.api.IRewardAnswerService;
import com.wing.socialcontact.service.wx.api.ITjyUserService;
import com.wing.socialcontact.service.wx.api.ITopicService;
import com.wing.socialcontact.service.wx.api.IUserIntegralLogService;
import com.wing.socialcontact.service.wx.api.IWxUserService;
import com.wing.socialcontact.service.wx.bean.Activity;
import com.wing.socialcontact.service.wx.bean.BusinessDisscuss;
import com.wing.socialcontact.service.wx.bean.Comment;
import com.wing.socialcontact.service.wx.bean.CommentThumbup;
import com.wing.socialcontact.service.wx.bean.RewardAnswer;
import com.wing.socialcontact.service.wx.bean.TjyNews;
import com.wing.socialcontact.service.wx.bean.TjyUser;
import com.wing.socialcontact.service.wx.bean.Topic;
import com.wing.socialcontact.service.wx.bean.WxUser;
import com.wing.socialcontact.util.CommUtil;
import com.wing.socialcontact.util.Constants;
import com.wing.socialcontact.util.ServletUtil;
import com.wing.socialcontact.util.SpringContextUtil;

/**
 * 项目管理
 * 
 */
@Controller
@RequestMapping("")
public class CommentAction extends BaseAction {
	@Autowired
	private ICommentService commentService;
	@Autowired
	private ICommentThumbupService commentThumbupService;
	@Autowired
	private IWxUserService wxUserService;
	@Autowired
	private ITjyUserService tjyUserService;
	@Autowired
	private IBusinessDisscussService businessDisscussService;
	@Autowired
	private INewsService newsService; 
	@Autowired
	private ITopicService topicService;
	@Autowired
	private IDynamicService dynamicService;
	@Autowired
    private IActivityService activityService;
	@Autowired
	private IRewardAnswerService rewardAnswerService;
	
	@Autowired
	private IUserIntegralLogService userIntegralLogService;
	/**
	 * 评论新增页面
	 * 
	 * @return
	 */
	@RequestMapping("/m/comment/comment_add")
	public ModelAndView comment_add(HttpServletRequest request,
			HttpServletResponse response) {
		ModelAndView mv = new ModelAndView();
		Member me = ServletUtil.getMember(request);
		if (null == me) {
			//response.sendRedirect(basePath+"m/my/reconPage.do");
			//return "login";
			mv.setViewName("login");
			return mv;
		}
		String userId = me.getId();
		if (StringUtils.isEmpty(userId)) {
			//response.sendRedirect(basePath+"m/my/reconPage.do");
			mv.setViewName("login");
			return mv;
		}
		TjyUser tjyUser = tjyUserService.selectByPrimaryKey(userId);
		if (null == tjyUser) {
			//response.sendRedirect(basePath+"m/my/reconPage.do");
			mv.setViewName("login");
			return mv;
		}
		if (!"1".equals(tjyUser.getIsRealname() + "")) {
			
			//response.sendRedirect(basePath+"m/my/reconPage.do");
			mv.setViewName("commons/to_recon");
			return mv;
		}
		String fkId = request.getParameter("fkId");// 评论主题id
		String cmeType = request.getParameter("cmeType");// 评论类型
		// String lastUrl = request.getParameter("lastUrl");//跳转页url
		mv.setViewName("comment/comment_add");
		Comment comment = new Comment();
		comment.setFkId(fkId);
		comment.setCmeType(cmeType);
		mv.addObject("comment", comment);
		// mv.addObject("lastUrl", lastUrl);
		return mv;
	}

	/**
	 * 评论新增
	 * 
	 * @return
	 */
	@RequestMapping("/add/m/comment/addComment")
	public @ResponseBody
	Map addComment(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		Comment comment = new Comment();
		Member me = (Member) request.getSession().getAttribute("me");
		if (null == me) {
			return super.getAjaxResult("302", "登录超时，请重新登录", null);
		}
		String userId = me.getId();
		if (StringUtils.isEmpty(userId)) {
			return super.getAjaxResult("302", "登录超时，请重新登录", null);
		}
		String cmeDesc = request.getParameter("cmeDesc");// 评论内容
		String imgUrl = request.getParameter("imgUrl");// 评论图片url
		String fkId = request.getParameter("fkId");// 评论主题Id
		String cmeType = request.getParameter("cmeType");// 评论类型
		String parentId = request.getParameter("parentId");// 评论上级id
		if (StringUtil.isEmpty(cmeDesc)) {
			return super.getAjaxResult(Constants.AJAX_CODE_ERROR_PARAM, "参数错误",
					null);
		} else {
			comment.setCmeDesc(cmeDesc);
		}
		if (!StringUtil.isEmpty(fkId)) {
			comment.setFkId(fkId);
		}
		cmeDesc = EsapiTest.stripXSS(cmeDesc);
		if (!StringUtil.isEmpty(cmeType)) {
			comment.setCmeType(cmeType);
			
			//cmeType;//1:资讯   2：合作 3：话题  4：活动 5：动态
			if("5".equals(cmeType)){
				//此项每日记首次评论
				userIntegralLogService.addLntAndEmp(userId, "task_0005");
			}else if("3".equals(cmeType)){
				//此项每日记首次评论
				userIntegralLogService.addLntAndEmp(userId, "task_0010");
			}else if("1".equals(cmeType)){
				//此项每日记首次评论
				userIntegralLogService.addLntAndEmp(userId, "task_0015");
			}else{
				//此项每日记首次评价
				//userIntegralLogService.addLntAndEmp(userId, "task_0011");
			}
		}
		if (!StringUtil.isEmpty(imgUrl)) {
			comment.setImgUrl(imgUrl);
		}
		if (!StringUtil.isEmpty(parentId)) {
			comment.setParentId(parentId);
		}
		comment.setCreateTime(new Date());
		comment.setUserId(userId);
		Comment cm = new Comment();
		cm.setFkId(fkId);
		List cmList = commentService.selectAllComment(cm);
		if(null==cmList||cmList.size()==0){
			comment.setStatus(1);//1为沙发
		}
		commentService.addComment(comment);

		return super.getSuccessAjaxResult();
	}

	/**
	 * 查看子评论
	 * 
	 * @return
	 */
	@RequestMapping("/m/comment/selCommentByPid")
	public ModelAndView selCommentByPid(HttpServletRequest request,
			HttpServletResponse response) {
		ModelAndView mv = new ModelAndView();
		Member me = ServletUtil.getMember(request);
		if (null == me) {
			//response.sendRedirect(basePath+"m/my/reconPage.do");
			//return "login";
			mv.setViewName("login");
			return mv;
		}
		String _userId = me.getId();
		if (StringUtils.isEmpty(_userId)) {
			//response.sendRedirect(basePath+"m/my/reconPage.do");
			mv.setViewName("login");
			return mv;
		}
		TjyUser tjyUser = tjyUserService.selectByPrimaryKey(_userId);
		if (null == tjyUser) {
			//response.sendRedirect(basePath+"m/my/reconPage.do");
			mv.setViewName("login");
			return mv;
		}
		if (!"1".equals(tjyUser.getIsRealname() + "")) {
			
			//response.sendRedirect(basePath+"m/my/reconPage.do");
			mv.setViewName("commons/to_recon");
			return mv;
		}
		Comment subcomment = new Comment();
		CommentThumbup commentThumbup = new CommentThumbup();
		String parentId = request.getParameter("parentId");// 评论上级id
		mv.setViewName("comment/comment_list");
		if (StringUtil.isEmpty(parentId)) {
			return null;
		} else {
			subcomment.setParentId(parentId);
			commentThumbup.setPId(parentId);
		}
		//zhangfan新增  合作洽谈回复
		String type = request.getParameter("type")==null?"":request.getParameter("type");
		Map<String, Object> user = new HashMap<String, Object>();
		Comment comment = new Comment();
		if(type.equals("1")){//type 是1为 合作洽谈回复
			BusinessDisscuss b = businessDisscussService.selectById(parentId);
			if (null != b) {
				String userId = b.getCreateUserId();
				comment.setId(b.getId());
				comment.setCreateTime(b.getCreateTime());
				comment.setCmeDesc(b.getContent());
				comment.setCmeType("2");
				if (!StringUtil.isEmpty(userId)) {
					// 获取用户
					user = wxUserService.queryUsersByid(userId);
				}
			}
		}else if(type.equals("2")){//type 是2为 悬赏应答
			RewardAnswer ra = rewardAnswerService.selectById(parentId);
			if (null != ra) {
				String userId = ra.getCreateUserId();
				comment.setId(ra.getId());
				comment.setCreateTime(ra.getCreateTime());
				comment.setCmeDesc(ra.getContent());
				comment.setCmeType("6");
				if (!StringUtil.isEmpty(userId)) {
					// 获取用户
					user = wxUserService.queryUsersByid(userId);
				}
			}
			
		}else{
			comment = commentService.selectById(parentId);
			if (null != comment) {
				String userId = comment.getUserId();
				if (!StringUtil.isEmpty(userId)) {
					// 获取用户
					user = wxUserService.queryUsersByid(userId);
				}
			}
		}
		
		mv.addObject("user", user);
		mv.addObject("comment", comment);
		int count = commentThumbupService.selectcount(commentThumbup);
		mv.addObject("count", count);
		
		//Member me = (Member) request.getSession().getAttribute("me");
		//String _userId = me.getId();
		commentThumbup.setUserId(_userId);

		List<Map<String, Object>> thumbupList = commentThumbupService
				.selectAllCommentThumbup(commentThumbup);
		boolean isThumbup = false;
		if (thumbupList.size() > 0) {
			isThumbup =true;
		} 
		//获取当前用户点赞状态
		mv.addObject("isThumbup",isThumbup);
		
		List<Map<String, Object>> subCommentList = commentService
				.queryCommentbyPid(parentId);
		for (Map<String, Object> m : subCommentList) {
			if (StringUtil.isEmpty((String) (m.get("userId")))) {
				m.put("formname", "");
			} else {
				// 获取用户
				Map<String, Object> subuser = wxUserService
						.queryUsersByid((String) (m.get("userId")));
				m.put("formname", (String) (subuser.get("nickname")));
				m.put("head_portrait", (String) (subuser.get("head_portrait")));
			}
		}
		mv.addObject("subCommentList", subCommentList);
		OssConfig ossConfig = (OssConfig) SpringContextUtil
				.getBean("ossConfig");
		String ossurl = ossConfig.getOss_getUrl();
		mv.addObject("ossurl", ossurl);
		return mv;
	}

	/**
	 * 查看当前主题所有评论
	 * 
	 * @return
	 */
	@RequestMapping("/m/comment/selComments")
	public @ResponseBody
	Map selComments(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		Comment comment = new Comment();
		String fkId = request.getParameter("fkId");// 评论主题Id
		String cmeType = request.getParameter("cmeType");// 评论类型

		if (StringUtil.isEmpty(cmeType) || StringUtil.isEmpty(fkId)) {
			return super.getAjaxResult("999", "参数错误", null);
		} else {
			comment.setFkId(fkId);
			comment.setCmeType(cmeType);
		}

		List<Map<String, Object>> commentList = commentService
				.selectAllComment(comment);
		int count = 0;
		int subcount = 0;
		for (Map<String, Object> m : commentList) {
			// 获取点赞数
			CommentThumbup commentThumbup = new CommentThumbup();
			commentThumbup.setPId((String) (m.get("id")));
			count = commentThumbupService.selectcount(commentThumbup);
			m.put("count", count);
			
			Member me = (Member) request.getSession().getAttribute("me");
			if (null == me) {
				return super.getAjaxResult("302", "登录超时，请重新登录", null);
			}
			String userId = me.getId();
			commentThumbup.setUserId(userId);

			List<Map<String, Object>> thumbupList = commentThumbupService
					.selectAllCommentThumbup(commentThumbup);
			boolean isThumbup = false;
			if (thumbupList.size() > 0) {
				isThumbup =true;
			} 
			//获取当前用户点赞状态
			m.put("isThumbup", isThumbup);
			
			// 获取第一条子评论
			Map subcomment = null;
			
			List<Map<String, Object>> subCommentList = commentService
					.queryCommentbyPid((String) (m.get("id")));
	
			if(subCommentList != null && subCommentList.size() != 0){
				subcomment = (Map<String, Object>)subCommentList.get(0);
				m.put("subcomment",subcomment);
				String subCommentUserId = (String)subcomment.get("userId");
				
				if (!StringUtil.isEmpty(subCommentUserId)) {
					TjyUser tu = tjyUserService.selectByPrimaryKey(subCommentUserId);
					m.put("subCommonUser", tu);
				}
				m.put("subCommonCount", 1);
			}else{
				m.put("subCommonCount", 0);
			}
			
			// m.put("subCommentList",subCommentList);
			// 获取子评论个数
			if (null != subCommentList) {
				subcount = subCommentList.size();
			}
			m.put("subcount", subcount);
			
			OssConfig ossConfig = (OssConfig) SpringContextUtil
					.getBean("ossConfig");
			String ossurl = ossConfig.getOss_getUrl();
			m.put("ossurl", ossurl);
			
			// 获取用户
			Map<String, Object> user = wxUserService.queryUsersByid((String) (m
					.get("userId")));
			if (null != user) {
				m.put("imgurl", (String) (user.get("head_portrait")));
				m.put("nickname", (String) (user.get("nickname")));
				if(null!=user.get("jobName")){
					m.put("job", (String) (user.get("jobName")));
				}else{
					m.put("job", "");
				}
				if(null!=user.get("industryName")){
					m.put("industry", (String) (user.get("industryName")));
				}else{
					m.put("industry", "");
				}
			} else {
				m.put("imgurl", "");
				m.put("nickname", "");
				m.put("job", "");
				m.put("industry", "");
			}

			//m.put("createTime", CommUtil.getTimesToNow(CommUtil
			//		.formatLongDate(m.get("createTime"))));
			m.put("createTime", CommUtil.formatLongDate2(m.get("createTime")));
		}

		return super.getSuccessAjaxResult(Constants.AJAX_MSG_SUCCESS,
				commentList);
		
	}
	/**
	 * 查看当前主题所有评论
	 * 
	 * @return
	 */
	@RequestMapping("/m/comment/selComments2")
	public @ResponseBody
	Map selComments2(HttpServletRequest request, HttpServletResponse response, int pageNum, int pageSize)
			throws IOException {
		 
			Comment comment = new Comment();
			String fkId = request.getParameter("fkId");// 评论主题Id
			String cmeType = request.getParameter("cmeType");// 评论类型
			
			if (StringUtil.isEmpty(cmeType) || StringUtil.isEmpty(fkId)) {
				return super.getAjaxResult("999", "参数错误", null);
			} else {
				comment.setFkId(fkId);
				comment.setCmeType(cmeType);
			}
			
			if (pageSize == 0) {
				pageSize = 10;
			}
			if (pageNum == 0) {
				pageNum = 1;
			}
			List<Map<String, Object>> commentList = commentService
					.selectAllComment(comment, pageNum, pageSize);
			int count = 0;
			int subcount = 0;
			for (Map<String, Object> m : commentList) {
				// 获取点赞数
				CommentThumbup commentThumbup = new CommentThumbup();
				commentThumbup.setPId((String) (m.get("id")));
				count = commentThumbupService.selectcount(commentThumbup);
				m.put("count", count);
				
				Member me = (Member) request.getSession().getAttribute("me");
				if (null == me) {
					return super.getAjaxResult("302", "登录超时，请重新登录", null);
				}
				String userId = me.getId();
				commentThumbup.setUserId(userId);
				
				List<Map<String, Object>> thumbupList = commentThumbupService
						.selectAllCommentThumbup(commentThumbup);
				boolean isThumbup = false;
				if (thumbupList.size() > 0) {
					isThumbup =true;
				} 
				//获取当前用户点赞状态
				m.put("isThumbup", isThumbup);
				// 获取第一条子评论
				Map subcomment = null;
				
				List<Map<String, Object>> subCommentList = commentService
						.queryCommentbyPid((String) (m.get("id")));
		
				if(subCommentList != null && subCommentList.size() != 0){
					subcomment = (Map<String, Object>)subCommentList.get(0);
					m.put("subcomment",subcomment);
					String subCommentUserId = (String)subcomment.get("userId");
					
					if (!StringUtil.isEmpty(subCommentUserId)) {
						TjyUser tu = tjyUserService.selectByPrimaryKey(subCommentUserId);
						m.put("subCommonUser", tu);
					}
					m.put("subCommonCount", 1);
				}else{
					m.put("subCommonCount", 0);
				}
				// m.put("subCommentList",subCommentList);
				if (null != subCommentList) {
					subcount = subCommentList.size();
				}
				m.put("subcount", subcount);
				
				OssConfig ossConfig = (OssConfig) SpringContextUtil
						.getBean("ossConfig");
				String ossurl = ossConfig.getOss_getUrl();
				m.put("ossurl", ossurl);
				
				// 获取用户
				Map<String, Object> user = wxUserService.queryUsersByid((String) (m
						.get("userId")));
				if (null != user) {
					m.put("imgurl", (String) (user.get("head_portrait")));
					m.put("honor_title", (String) (user.get("honor_title")));
					m.put("honor_flag", (String) (user.get("honor_flag")));
					m.put("nickname", (String) (user.get("nickname")));
					if(null!=user.get("jobName")){
						m.put("job", (String) (user.get("jobName")));
					}else{
						m.put("job", "");
					}
					if(null!=user.get("industryName")){
						m.put("industry", (String) (user.get("industryName")));
					}else{
						m.put("industry", "");
					}
				} else {
					m.put("imgurl", "");
					m.put("nickname", "");
					m.put("job", "");
					m.put("industry", "");
				}
				
				//m.put("createTime", CommUtil.getTimesToNow(CommUtil
				//		.formatLongDate(m.get("createTime"))));
				m.put("createTime", CommUtil.formatLongDate2(m.get("createTime")));
			}
			
			return super.getSuccessAjaxResult(Constants.AJAX_MSG_SUCCESS,
					commentList);
		
	}

	/**
	 * 查看我的当前主题所有评论
	 * 
	 * @return
	 */
	@RequestMapping("/m/comment/myselComments")
	public @ResponseBody
	Map myselComments(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		Comment comment = new Comment();
		Member me = (Member) request.getSession().getAttribute("me");
		if (null == me) {
			return super.getAjaxResult("302", "登录超时，请重新登录", null);
		}
		String userId = me.getId();
		if (StringUtils.isEmpty(userId)) {
			return super.getAjaxResult("302", "登录超时，请重新登录", null);
		}
		comment.setUserId(userId);
		String cmeType = request.getParameter("cmeType");// 评论类型

		if (StringUtil.isEmpty(cmeType)) {
			return super.getAjaxResult("999", "参数错误", null);
		} else {
			comment.setImgUrl(cmeType);
		}
		List<Map<String, Object>> commentList = commentService
				.selectAllComment(comment);
		int count = 0;	// 获取点赞数
		int subcount = 0;// 获取子评论
		for (Map<String, Object> m : commentList) {
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
			// m.put("subCommentList",subCommentList);
			if (null != subCommentList) {
				subcount = subCommentList.size();
			}
			m.put("subcount", subcount);
			// 获取用户
			WxUser user = new WxUser();
			wxUserService.selectByUserId((String) (m.get("userId")));
			m.put("user", user);
			////cmeType 1:资讯   2：合作 3：话题  4：活动 5:动态
			if("1".endsWith(cmeType)){
				TjyNews  fkMap= newsService.selectByPrimaryKey((String) (m.get("fkId")));//咨询
				m.put("fkMap", fkMap);
			}
			if("2".endsWith(cmeType)){
				BusinessDisscuss fkMap = businessDisscussService.selectByPrimaryKey((String) (m.get("fkId")));//合作
				m.put("fkMap", fkMap);
			}
			if("3".endsWith(cmeType)){
				Topic fkMap = topicService.selectByPrimaryKey((String) (m.get("fkId")));
				m.put("fkMap", fkMap);
			}
			if("5".endsWith(cmeType)){
				Map fkMap = dynamicService.getDynamicMapById((String)( m.get("fkId")));//动态
				m.put("fkMap", fkMap);
			}
		}
		

		return super.getSuccessAjaxResult(Constants.AJAX_MSG_SUCCESS,
				commentList);
	}
	
	/**
	 * 我的资讯评论
	 * 
	 * @return
	 */
	@RequestMapping("/m/comment/mynews_comment")
	public String mynewsComment(HttpServletRequest request, ModelMap modelMap) {
		Member me = ServletUtil.getMember(request);
		if (null == me) {
			return "login";
		}
		String userId = me.getId();
		if (StringUtils.isEmpty(userId)) {
			return "login";
		}
		Comment comment = new Comment();
		comment.setUserId(userId);
		comment.setCmeType("1");
		
		List<Map<String, Object>> commentList = commentService
				.selectAllComment(comment);
		
		for (Map<String, Object> m : commentList) {
			TjyNews  fkMap= newsService.selectByPrimaryKey((String) (m.get("fkId")));//咨询
			m.put("fkMap", fkMap);
		}
		modelMap.addAttribute("commentList", commentList);
		OssConfig ossConfig = (OssConfig) SpringContextUtil
				.getBean("ossConfig");
		String ossurl = ossConfig.getOss_getUrl();
		modelMap.addAttribute("ossurl", ossurl);
		return "mine/mynews_comment";
	}
	/**
	 * 我的合作评论
	 * 
	 * @return
	 */
	@RequestMapping("/m/comment/mybusinessDisscuss_comment")
	public String mybusinessDisscussComment(HttpServletRequest request, ModelMap modelMap) {
		Member me = ServletUtil.getMember(request);
		if (null == me) {
			return "login";
		}
		String userId = me.getId();
		if (StringUtils.isEmpty(userId)) {
			return "login";
		}
		Comment comment = new Comment();
		comment.setUserId(userId);
		comment.setCmeType("2");// 评论类型
		
		List<Map<String, Object>> commentList = commentService
				.selectAllComment(comment);
		
		for (Map<String, Object> m : commentList) {
			BusinessDisscuss fkMap = businessDisscussService.selectByPrimaryKey((String) (m.get("fkId")));//合作
			m.put("fkMap", fkMap);
		}
		modelMap.addAttribute("commentList", commentList);
		return "mine/mybusinessDisscuss_comment";
	}
	/**
	 * 我的话题评论
	 * 
	 * @return
	 */
	@RequestMapping("/m/comment/mytopic_comment")
	public String mytopicComment(HttpServletRequest request, ModelMap modelMap) {
		Member me = ServletUtil.getMember(request);
		if (null == me) {
			return "login";
		}
		String userId = me.getId();
		if (StringUtils.isEmpty(userId)) {
			return "login";
		}
		Comment comment = new Comment();
		comment.setUserId(userId);
		comment.setCmeType("3");// 评论类型
		
		List<Map<String, Object>> commentList = commentService
				.selectAllComment(comment);
		
		for (Map<String, Object> m : commentList) {
			Topic fkMap = topicService.selectByPrimaryKey((String) (m.get("fkId")));
		    m.put("fkMap", fkMap);
		}
		modelMap.addAttribute("commentList", commentList);
		return "mine/mytopic_comment";
	}
	
	/**
	 * 我的活动评论
	 * 
	 * @return
	 */
	@RequestMapping("/m/comment/myactivity_comment")
	public String myactivityComment(HttpServletRequest request, ModelMap modelMap) {
		Member me = ServletUtil.getMember(request);
		if (null == me) {
			return "login";
		}
		String userId = me.getId();
		if (StringUtils.isEmpty(userId)) {
			return "login";
		}
		Comment comment = new Comment();
		comment.setUserId(userId);
		comment.setCmeType("4");
		
		List<Map<String, Object>> commentList = commentService
				.selectAllComment(comment);
		
		for (Map<String, Object> m : commentList) {
			Activity  fkMap= activityService.getActivityByid((String) (m.get("fkId")));//活动
			m.put("fkMap", fkMap);
		}
		modelMap.addAttribute("commentList", commentList);
		return "mine/myactivity_comment";
	}
	/**
	 * 我的动态评论
	 * 
	 * @return
	 */
	@RequestMapping("/m/comment/mydynamic_comment")
	public String mydynamicComment(HttpServletRequest request, ModelMap modelMap) {
		Member me = ServletUtil.getMember(request);
		if (null == me) {
			return "login";
		}
		String userId = me.getId();
		if (StringUtils.isEmpty(userId)) {
			return "login";
		}
		Comment comment = new Comment();
		comment.setUserId(userId);
		comment.setCmeType("5");// 评论类型
		
		List<Map<String, Object>> commentList = commentService
				.selectAllComment(comment);
		
		for (Map<String, Object> m : commentList) {
			Map fkMap = dynamicService.getDynamicMapById((String)( m.get("fkId")));//动态
			m.put("fkMap", fkMap);
		}
		modelMap.addAttribute("commentList", commentList);
		OssConfig ossConfig = (OssConfig) SpringContextUtil
				.getBean("ossConfig");
		String ossurl = ossConfig.getOss_getUrl();
		modelMap.addAttribute("ossurl", ossurl);
		return "mine/mydynamic_comment";
	}
	
	
	/**
	 * 动态评论
	 * @param request
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	@RequestMapping("/m/comment/mydynamic")
	public @ResponseBody Map selectMydynamic(HttpServletRequest request, ModelMap modelMap) {
		Member me = ServletUtil.getMember(request);
		String userId = me.getId();
		Comment comment = new Comment();
		comment.setUserId(userId);
		comment.setCmeType("5");// 评论类型
		
		List<Map<String, Object>> commentList = commentService
				.selectAllComment(comment);
		
		for (Map<String, Object> m : commentList) {
			Map fkMap = dynamicService.getDynamicMapById((String)( m.get("fkId")));//动态
			m.put("fkMap", fkMap);
		}
		//modelMap.addAttribute("commentList", commentList);
		OssConfig ossConfig = (OssConfig) SpringContextUtil
				.getBean("ossConfig");
		String ossurl = ossConfig.getOss_getUrl();
		//modelMap.addAttribute("ossurl", ossurl);

		Map valueMap = new HashMap();
		valueMap.put("ossurl", ossurl);
		valueMap.put("commentList", commentList);
		return super.getSuccessAjaxResult("获取成功！", valueMap);
		
	}

	/**
	 * 评论点赞
	 * 
	 * @return
	 */
	@RequestMapping("/m/comment/Thumbup")
	public @ResponseBody
	Map Thumbup(HttpServletRequest request, HttpServletResponse response)
			throws IOException {

		Comment comment = new Comment();
		String id = request.getParameter("id");// 评论id
		if (StringUtil.isEmpty(id)) {
			return super.getAjaxResult(Constants.AJAX_CODE_ERROR_PARAM, "参数错误",
					null);
		}
		
		//
		CommentThumbup commentThumbup = new CommentThumbup();
		commentThumbup.setPId(id);
		Member me = (Member) request.getSession().getAttribute("me");
		if (null == me) {
			return super.getAjaxResult("302", "登录超时，请重新登录", null);
		}
		String userId = me.getId();
		
		comment = commentService.selectById(id);
		String cmeType = "";
		if(comment!=null){
			cmeType = comment.getCmeType();
		}
		if(!StringUtil.isEmpty(cmeType)){
			if(cmeType.equals("1")){
				//咨询点赞
				userIntegralLogService.addLntAndEmp(userId, "task_0014");
			}
		}
		
		commentThumbup.setUserId(userId);

		List<Map<String, Object>> thumbupList = commentThumbupService
				.selectAllCommentThumbup(commentThumbup);
		if (thumbupList.size() <= 0) {
			commentThumbupService.addCommentThumbup(commentThumbup);
			return super.getSuccessAjaxResult("0");
		} else {
			return super.getSuccessAjaxResult("1");
		}
	}
	
	/**
	 * 评论取消点赞
	 * 
	 * @return
	 */
	@RequestMapping("/m/comment/cancelThumbup")
	public @ResponseBody
	Map cancelThumbup(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		
		Comment comment = new Comment();
		String id = request.getParameter("id");// 评论id
		if (StringUtil.isEmpty(id)) {
			return super.getAjaxResult(Constants.AJAX_CODE_ERROR_PARAM, "参数错误",
					null);
		}
		//
		CommentThumbup commentThumbup = new CommentThumbup();
		commentThumbup.setPId(id);
		Member me = (Member) request.getSession().getAttribute("me");
		if (null == me) {
			return super.getAjaxResult("302", "登录超时，请重新登录", null);
		}
		String userId = me.getId();
		commentThumbup.setUserId(userId);
		
		List<Map<String, Object>> thumbupList = commentThumbupService
				.selectAllCommentThumbup(commentThumbup);
		String[] ids={};
		for(Map<String, Object> m : thumbupList){
			commentThumbupService.deleteCommentThumbups((String)m.get("id"));
		}
	    
		return super.getSuccessAjaxResult("0");
	}

	/**
	 * 评论共同页面
	 * 
	 * @return
	 */
	@RequestMapping("/m/comment/comment_page")
	public ModelAndView comment_page(HttpServletRequest request,
			HttpServletResponse response) {
		ModelAndView mv = new ModelAndView();
		String fkId = request.getParameter("fkId");// 评论主题id
		String cmeType = request.getParameter("cmeType");// 评论类型
		// /String lastUrl = request.getParameter("lastUrl");//跳转页url
		mv.setViewName("comment/comment_page");
		Comment comment = new Comment();
		comment.setFkId(fkId);
		comment.setCmeType(cmeType);
		mv.addObject("comment", comment);
		return mv;
	}
	/**
	 * 我的合作商洽评论
	 * 
	 * @return
	 */
	@RequestMapping("/m/comment/mybd_comment")
	public String myBDComment(HttpServletRequest request, ModelMap modelMap) {
		Member me = ServletUtil.getMember(request);
		if (null == me) {
			return "login";
		}
		String userId = me.getId();
		if (StringUtils.isEmpty(userId)) {
			return "login";
		}
		Comment comment = new Comment();
		comment.setUserId(userId);
		comment.setCmeType("2");// 评论类型
		
		List<Map<String, Object>> commentList = commentService.selectAllCommentbd(comment);
		
		for (Map<String, Object> m : commentList) {
			Map<String, Object> fkMap = businessDisscussService.selectBDById((String) (m.get("parentId")));//合作
			m.put("fkMap", fkMap);
		}
		modelMap.addAttribute("commentList", commentList);
		return "mine/mybd_comment";
	}
}
