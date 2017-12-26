package com.wing.socialcontact.action;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import cn.jpush.api.utils.StringUtils;

import com.github.pagehelper.util.StringUtil;
import com.wing.socialcontact.common.model.DataGrid;
import com.wing.socialcontact.common.model.PageParam;
import com.wing.socialcontact.config.OssConfig;
import com.wing.socialcontact.service.wx.api.ICommentService;
import com.wing.socialcontact.service.wx.api.ICommentThumbupService;
import com.wing.socialcontact.service.wx.api.IDynamicPicService;
import com.wing.socialcontact.service.wx.api.IDynamicService;
import com.wing.socialcontact.service.wx.api.IMeetingService;
import com.wing.socialcontact.service.wx.api.IWxUserService;
import com.wing.socialcontact.service.wx.bean.Comment;
import com.wing.socialcontact.service.wx.bean.CommentThumbup;
import com.wing.socialcontact.service.wx.bean.Dynamic;
import com.wing.socialcontact.service.wx.bean.DynamicPic;
import com.wing.socialcontact.service.wx.bean.WxUser;
import com.wing.socialcontact.sys.action.BaseAction;
import com.wing.socialcontact.util.CommUtil;
import com.wing.socialcontact.util.Constants;
import com.wing.socialcontact.util.SpringContextUtil;

/**
 * 动态管理
 */
@Controller
@RequestMapping("/dynamic")
public class DynamicAction extends BaseAction{
	@Autowired
	private IMeetingService meetingService;
	@Autowired
	private IDynamicService dynamicService;
	@Autowired
	private IDynamicPicService dynamicPicService;
	@Autowired
	private IWxUserService wxUserService;
	
	@Autowired
	private ICommentService commentService;
	@Autowired
	private ICommentThumbupService commentThumbupService;
	/**
	 * 会议首页
	 * @return
	 */
	//@RequiresPermissions("newsclass:read")
	@RequestMapping("dynamicindex")
	public String loadMeeting(){
		return "dynamic/dynamicindex";
	}
	@RequestMapping("dynamicindex2")
	public String loadDynamic2(){
		return "dynamic/dynamicindex2";
	}
	/**
	 * 会议列表查询
	 * @return
	 */
	@RequestMapping("dynamicquery")
	@ResponseBody
	public DataGrid dynamicquery(PageParam param,String dyContent,String userName,String visitQuantity,String issuedDate,String user_id){
		return  this.dynamicService.selectAllDynamicSignup(param, dyContent, userName, visitQuantity, issuedDate,user_id);
	}
	@RequestMapping("dynamicquery2")
	@ResponseBody
	public DataGrid dynamicquery2(PageParam param,String dyContent,String userName,String visitQuantity,String issuedDate,String user_id){
		return this.dynamicService.selectAllDynamicSignup2(param, dyContent, userName, visitQuantity, issuedDate,user_id);
	}
	/**
	 * 新增保存
	 * @return
	 */
	@RequestMapping(value="savedynamic",method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> savedynamic( Date puTime,Dynamic dynamic,String zjImgerJson){
		
		String dynamicId = UUID.randomUUID().toString().replace("-", "");
		try {
			JSONArray ja = JSONArray.fromObject(zjImgerJson);
			JSONObject jo = null;
			List<DynamicPic> dynamicPicList = new ArrayList(); 
			for (int i = 0; i < ja.size(); i++) {
				jo = ja.getJSONObject(i);
				String imgUrl = jo.getString("imgUrl");
				DynamicPic dynamicPic = new DynamicPic();
				dynamicPic.setId(UUID.randomUUID().toString());
				dynamicPic.setDynamicId(dynamicId);
				dynamicPic.setCreateTime(new Date());
				dynamicPic.setPicUrl(imgUrl);
				dynamicPic.setUserId(dynamic.getUserId());
				dynamicPic.setSortNum((double)i);
				dynamicPicList.add(dynamicPic);
			}
			dynamic.setStatus(1);
			Date now = new Date();
			dynamic.setCreateTime(now);
			if(com.wing.socialcontact.util.StringUtil.isEmpty(puTime)){
				dynamic.setIssuedDate(now);
			}else{
				dynamic.setIssuedDate(puTime);
			}
			dynamic.setId(dynamicId);
			int count = dynamicService.insertDynamicSignup(dynamic);
			int dynamicPicCount = dynamicPicService.insertDynamicPicList(dynamicPicList);
			return getAjaxResult("0", "发布成功！", null);
		} catch (Exception e) {
			return super.getAjaxResult(Constants.AJAX_CODE_ERROR_INFO, e.getMessage(), "");
		}
	}
	
	/**
	 * 修改保存
	 * @return
	 */
	@RequestMapping(value="editdynamic",method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> editdynamic( Dynamic dynamic,String zjImgerJson){
		
		String dynamicId = dynamic.getId();
		if(StringUtils.isEmpty(dynamicId)){
			return super.getAjaxResult(Constants.AJAX_CODE_ERROR_INFO,"编辑动态信息错误", "");
		}
		Dynamic dynamicOld = dynamicService.getDynamicSignup(dynamicId);
		if(dynamicOld == null){
			return super.getAjaxResult(Constants.AJAX_CODE_ERROR_INFO,"编辑动态信息错误", "");
		}
		try {
			dynamicPicService.delAllDynamicPicList(dynamicId);
			JSONArray ja = JSONArray.fromObject(zjImgerJson);
			JSONObject jo = null;
			List<DynamicPic> dynamicPicList = new ArrayList(); 
			for (int i = 0; i < ja.size(); i++) {
				jo = ja.getJSONObject(i);
				String imgUrl = jo.getString("imgUrl");
				DynamicPic dynamicPic = new DynamicPic();
				dynamicPic.setId(UUID.randomUUID().toString());
				dynamicPic.setDynamicId(dynamicId);
				dynamicPic.setCreateTime(new Date());
				dynamicPic.setPicUrl(imgUrl);
				dynamicPic.setUserId(dynamic.getUserId());
				dynamicPic.setSortNum((double)i);
				dynamicPicList.add(dynamicPic);
			}
			
			int count = dynamicService.updateDynamicSignup(dynamic);
			int dynamicPicCount = dynamicPicService.insertDynamicPicList(dynamicPicList);
			return getAjaxResult("0", "编辑成功！", null);
		} catch (Exception e) {
			return super.getAjaxResult(Constants.AJAX_CODE_ERROR_INFO, e.getMessage(), "");
		}
	}
	/**
	 * 动态添加
	 * @param id
	 * @return
	 */
	@RequestMapping("dynamicaddpage")
	public String dynamicaddpage(ModelMap modelMap){	
		return "dynamic/dynamicadd";
	}
	/**
	 * 动态添加
	 * @param id
	 * @return
	 */
	@RequestMapping("dynamicaddpage2")
	public String dynamicaddpage2(ModelMap modelMap){	
		return "dynamic/dynamicadd2";
	}
	/**
	 * 动态明细
	 * @param id
	 * @return
	 */
	@RequestMapping("dynamicdetail")
	public String dynamicdetail(ModelMap modelMap,String id){	
		Map map = dynamicService.getDynamicMapById(id);
		OssConfig ossConfig = (OssConfig) SpringContextUtil
				.getBean("ossConfig");
		String ossurl = ossConfig.getOss_getUrl();
		modelMap.put("dynamic", map);
		modelMap.put("ossurl", ossurl);
		return "dynamic/dynamicdetail";
	}
	
	/**
	 * 动态明细
	 * @param id
	 * @return
	 */
	@RequestMapping("dynamicedit")
	public String dynamicedit(ModelMap modelMap,String id){	
		Dynamic dto = dynamicService.getDynamicSignup(id);
		//判断是否已经发布
		if(dto.getIssuedDate().compareTo(new Date())>0){
			modelMap.addAttribute("published", 0);
		}else{
			modelMap.addAttribute("published", 1);
		}
		Map map = dynamicService.getDynamicMapById(id);
		OssConfig ossConfig = (OssConfig) SpringContextUtil
				.getBean("ossConfig");
		String ossurl = ossConfig.getOss_getUrl();
		modelMap.put("dynamic", map);
		modelMap.put("ossurl", ossurl);
		return "dynamic/dynamicedit";
	}
	
	
	/**
	 * 动态明细
	 * @param id
	 * @return
	 */
	@RequestMapping("dynamicdetailview")
	public String dynamicdetailview(ModelMap modelMap,String id){	
		Map map = dynamicService.getDynamicMapById(id);
		OssConfig ossConfig = (OssConfig) SpringContextUtil
				.getBean("ossConfig");
		String ossurl = ossConfig.getOss_getUrl();
		JSONObject jsonObject = new JSONObject();
		jsonObject.putAll(map);
		String mapJson =jsonObject.toString();
		modelMap.put("dynamic",map);
		modelMap.put("ossurl", ossurl);
		return "dynamic/dynamicdetailview";
	}
	/**
	 * 动态明细
	 * @param id
	 * @return
	 */
	@RequestMapping("dynamicdetail2")
	public String dynamicdetail2(ModelMap modelMap,String id){	
		Map map = dynamicService.getDynamicMapById2(id);
		OssConfig ossConfig = (OssConfig) SpringContextUtil
				.getBean("ossConfig");
		String ossurl = ossConfig.getOss_getUrl();
		modelMap.put("dynamic", map);
		modelMap.put("ossurl", ossurl);
		return "dynamic/dynamicdetail";
	}
	/**
	 * 修改置顶状态
	 * @param ids
	 * @return
	 */
	@RequestMapping("updatetop")
	@ResponseBody
	public Map<String,Object> updatetop(String id,int type){	
		try {
			Dynamic dto = dynamicService.getDynamicSignup(id);
			dto.setIsStick(type);
			this.dynamicService.updateDynamicSignup(dto);
			return getAjaxResult("0", "", null);
		} catch (Exception e) {
			return getAjaxResult("-1", "操作失败", null);
		}
	}
	/**
	 * 删除会议（逻辑删除）
	 * @param ids
	 * @return
	 */
	@RequestMapping("dynamicdel")
	@ResponseBody
	public Map<String,Object> dynamicdel(String id){	
		try {
			
			this.dynamicService.deleteDynamic(id);
			return getAjaxResult("0", "", null);
		} catch (Exception e) {
			return getAjaxResult("-1", "操作失败", null);
		}
	}
	/**
	 * 图片上传
	 * @param request
	 * @param jsonp
	 * @param pic
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("uploadpic")
	@ResponseBody
	public Map uploadPic(HttpServletRequest request,String jsonp,MultipartFile file,String ysStyle) {
		return super.uploadImage(request, jsonp, file,ysStyle,"dynamic");
	}
	
	/**
	 * 根据手机号获取用户id
	 * @param mobile
	 * @return
	 */
	@RequestMapping("checkMobile")
	@ResponseBody
	public Map<String,Object> checkMobile(String mobile){	
		try {
			WxUser user = this.wxUserService.selectByMobile(mobile);
			return getAjaxResult("0", "获取用户信息成功！", user);
		} catch (Exception e) {
			return getAjaxResult("-1", "操作失败", null);
		}
	}
	
	/**
	 * 查看当前主题所有评论
	 * 
	 * @return
	 */
	@RequestMapping("selComments")
	public  ModelAndView selComments(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		Comment comment = new Comment();
		String fkId = request.getParameter("fkId");// 评论主题Id
		String cmeType = request.getParameter("cmeType");// 评论类型
		
		comment.setFkId(fkId);
		comment.setCmeType(cmeType);

		List<Map<String, Object>> commentList = commentService.selectAllComment(comment);
		int count = 0;
		int subcount = 0;
		for (Map<String, Object> m : commentList) {
			// 获取点赞数
			CommentThumbup commentThumbup = new CommentThumbup();
			commentThumbup.setPId((String) (m.get("id")));
			count = commentThumbupService.selectcount(commentThumbup);
			m.put("count", count);
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
			m.put("subCommentList", subCommentList);
			OssConfig ossConfig = (OssConfig) SpringContextUtil
					.getBean("ossConfig");
			String ossurl = ossConfig.getOss_getUrl();
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
				m.put("nickname", "匿名");
				m.put("job", "无工作");
				m.put("industry", "无职位");
			}

			m.put("createTime", CommUtil.getTimesToNow(CommUtil
					.formatLongDate(m.get("createTime"))));
		}

		return ajaxJsonEscape(commentList);
	}
	
	
	
	/**
	 * 评论新增
	 * 
	 * @return
	 */
	@RequestMapping("addComment")
	public ModelAndView addComment(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		Comment comment = new Comment();
		
		String cmeDesc = request.getParameter("cmeDesc");// 评论内容
		String mobile = request.getParameter("mobile");// 评论内容
		String fkId = request.getParameter("fkId");// 评论主题Id
		String cmeType = request.getParameter("cmeType");// 评论类型
		String parentId = request.getParameter("parentId");// 评论上级id
		
		WxUser user = wxUserService.selectByMobile(mobile);
		if(null == user){
			//return super.getAjaxResult(Constants.AJAX_CODE_ERROR_PARAM, "没有该用户", "");
			return ajaxJsonEscape(false);
		}
		
		if (StringUtil.isEmpty(cmeDesc)) {
			return ajaxDoneTextError("参数有误");
		} else {
			comment.setCmeDesc(cmeDesc);
		}
		if (!StringUtil.isEmpty(fkId)) {
			comment.setFkId(fkId);
		}
		if (!StringUtil.isEmpty(cmeType)) {
			comment.setCmeType(cmeType);
		}
		if (!StringUtil.isEmpty(parentId)) {
			comment.setParentId(parentId);
		}
		comment.setCreateTime(new Date());
		comment.setUserId(String.valueOf(user.getId()));
		commentService.addComment(comment);

		return ajaxJsonEscape(true);
	}
}
