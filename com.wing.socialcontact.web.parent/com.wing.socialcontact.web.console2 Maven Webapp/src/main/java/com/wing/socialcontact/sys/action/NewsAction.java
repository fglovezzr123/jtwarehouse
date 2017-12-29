package com.wing.socialcontact.sys.action;


import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FilenameUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.util.StringUtil;
import com.wing.socialcontact.common.model.DataGrid;
import com.wing.socialcontact.common.model.PageParam;
import com.wing.socialcontact.config.OssConfig;
import com.wing.socialcontact.service.wx.api.IBusinessDisscussService;
import com.wing.socialcontact.service.wx.api.ICommentService;
import com.wing.socialcontact.service.wx.api.ICommentThumbupService;
import com.wing.socialcontact.service.wx.api.INewsClassService;
import com.wing.socialcontact.service.wx.api.INewsService;
import com.wing.socialcontact.service.wx.api.ITjyUserService;
import com.wing.socialcontact.service.wx.api.IWxUserService;
import com.wing.socialcontact.service.wx.bean.Comment;
import com.wing.socialcontact.service.wx.bean.CommentThumbup;
import com.wing.socialcontact.service.wx.bean.RefundInstruction;
import com.wing.socialcontact.service.wx.bean.TjyNews;
import com.wing.socialcontact.service.wx.bean.TjyNewsClass;
import com.wing.socialcontact.service.wx.bean.UploadResultVo;
import com.wing.socialcontact.service.wx.bean.WxUser;
import com.wing.socialcontact.util.CommUtil;
import com.wing.socialcontact.util.Constants;
import com.wing.socialcontact.util.FileUploadUtil;
import com.wing.socialcontact.util.ServletUtil;
import com.wing.socialcontact.util.SpringContextUtil;
import com.wing.socialcontact.vhall.api.WebinarAPI;
import com.wing.socialcontact.vhall.resp.WebinarListResp;

/**
 * 资讯管理
 * @author zhangfan
 *
 */
@Controller
@RequestMapping("/news")
public class NewsAction extends BaseAction{
	
	@Autowired
	private INewsClassService newsClassService; 			
	@Autowired
	private INewsService newsService; 
	
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
	
	private  List childMenu= new ArrayList(); 
	/**
	 * 条件查询资讯分类
	 * 
	 * @return
	 */
	@RequiresPermissions("newsclass:read")
	@RequestMapping("newsClassload")
	public String newsClassload(){
		
		return "system/news/newsclassload";
	
	}
	@RequiresPermissions("newsclass:read")
	@RequestMapping("classquery")
	public ModelAndView classquery(){
		
		return ajaxJsonEscape(newsClassService.selectAllclassMap(null));
	}
	/**
	 * 跳转到资讯分类添加页面
	 * @return
	 */
	@RequiresPermissions("newsclass:add")
	@RequestMapping("classaddPage")
	public String classaddPage(){
		
		return "system/news/newsclassadd";
	
	}
	/**
	 * 添加资讯分类
	 * @param newsclass
	 * @param errors
	 * @return
	 */
	@RequiresPermissions("newsclass:add")
	@RequestMapping("classadd")
	public ModelAndView classadd(TjyNewsClass newsclass,Errors errors){
		if(errors.hasErrors()) {  
			ModelAndView mav=getValidationMessage(errors);
			if(mav!=null)return mav;
        }
		newsclass.setCreateTime(new Date());
		newsclass.setCreateUserId(ServletUtil.getMember().getId());
		newsclass.setCreateUserName(ServletUtil.getMember().getUserName());
		String str = newsClassService.addNewsClass(newsclass);
		if(str.equals("msg.newsclass.unique")){
			return ajaxDoneTextError("该分类已存在！");
		}
		return ajaxDone(str);	
	}
	/**
	 * 跳转到资讯分类修改页面
	 * @param id
	 * @param map
	 * @return
	 */
	@RequiresPermissions("newsclass:update")
	@RequestMapping("classupdatePage")
	public String classupdatePage(String id,ModelMap map){
		TjyNewsClass newsclass = newsClassService.selectById(id);
		if(newsclass==null){
			return NODATA;
		}
		map.addAttribute("c",newsclass);
		
		return "system/news/newsclassupdate";
	}

	/**
	 * 修改资讯分类
	 * @param newsclass
	 * @param errors
	 * @return
	 */
	@RequiresPermissions("newsclass:update")
	@RequestMapping("classupdate")
	public ModelAndView classupdate(TjyNewsClass newsclass,Errors errors){
		if(errors.hasErrors()) {  
			ModelAndView mav=getValidationMessage(errors);
			if(mav!=null)return mav;
        }
		String str = newsClassService.updateNewsClass(newsclass);
		if(str.equals("msg.newsclass.unique")){
			return ajaxDoneTextError("该分类已存在！");
		}
		return ajaxDone(str);
		
	}
	
	/**
	 * 删除资讯分类
	 * @param ids
	 * @return
	 */
	@RequiresPermissions("newsclass:delete")
	@RequestMapping("nclassdel")
	public ModelAndView nclassdel(String id){	
		return ajaxDone(newsClassService.deleteNewsClass(id));
	}
	/**
	 * 分类查询页面
	 * @return
	 */
	@RequestMapping("lookUpPage")
	public String lookUpPage(){
			return "system/news/lookup";
	}
	@RequestMapping("lookUp")
	public ModelAndView lookUp(){
		List list = new ArrayList();
		return ajaxJsonEscape(this.treeMenuList(list, Constants.NEWS_CLASS_ID));
	}
	//递归查询出某分类根节点下的所有节点
	private List<Map<String, Object>> treeMenuList(List<Map<String, Object>> list,String pid){
		List childList = newsClassService.selectAllclassMap(pid);
		list.addAll(childList);
		for(int i=0;i<childList.size();i++){
			Map map = null;
			map = (Map) childList.get(i);
			String id =  map.get("id")==null?"":map.get("id").toString();
			treeMenuList(list,id);
		}
		return list;
	}
	
	/**
	 * 条件查询资讯
	 * 
	 * @return
	 */
	@RequiresPermissions("news:read")
	@RequestMapping("load")
	public String load(){
		return "system/news/load";
	}
	@RequiresPermissions("news:read")
	@RequestMapping("query")
	public ModelAndView query(PageParam param,TjyNews news,String startTimef,String endTimef){
		news.setClassRoot(Constants.NEWS_CLASS_ID);
		DataGrid data = newsService.selectAllNews(param, news,startTimef,endTimef);
		List<Map<String, Object>> list = data.getRows();
		int count = 0;	//评论数
		Integer commentCount = 0;
		for(Map<String, Object> m : list){
			commentCount = m.get("commentCount")==null?0:(Integer)m.get("commentCount");
			if(commentCount==0){
				Comment comment = new Comment();
				comment.setFkId((String)(m.get("id")));
				comment.setCmeType("1");
				List<Map<String, Object>> commentList = commentService
						.selectAllComment(comment);
				if (null != commentList) {
					count = commentList.size();
				}
			}else{
				count = commentCount;
			}
			m.put("count", count);
		}
		return ajaxJsonEscape(data);
	}
	/**
	 * 跳转到资讯添加页面
	 * @return
	 */
	@RequiresPermissions("news:add")
	@RequestMapping("addPage")
	public String addPage(ModelMap map){
		//查询所有资讯分类
		List<TjyNewsClass> list = newsClassService.selectAllNewsClass();
		map.addAttribute("newsclass", list);
		return "system/news/add";
	
	}
	/**
	 * 添加资讯
	 * @param newsclass
	 * @param errors
	 * @return
	 */
	@RequiresPermissions("news:add")
	@RequestMapping("add")
	public ModelAndView add(Date puTime,TjyNews news,Errors errors){
		if(errors.hasErrors()) {  
			ModelAndView mav=getValidationMessage(errors);
			if(mav!=null)return mav;
        }
		Date now = new Date();
		news.setUpdateTime(now);//资讯添加时间
		if(com.wing.socialcontact.util.StringUtil.isEmpty(puTime)){
			news.setCreateTime(now);//发布时间
		}else{
			news.setCreateTime(puTime);
		}
		news.setIsHot(0);
		news.setClassRoot(Constants.NEWS_CLASS_ID);
		news.setCreateUserId(ServletUtil.getMember().getId());
		news.setCreateUserName(ServletUtil.getMember().getUserName());
		news.setCharge(news.getCharge()==null?0:news.getCharge());
		news.setLookCount(news.getLookCount()==null?0:news.getLookCount());
		return ajaxDone(newsService.addNews(news));	
	}
	/**
	 * 跳转到资讯修改页面
	 * @param id
	 * @param map
	 * @return
	 */
	@RequiresPermissions("news:update")
	@RequestMapping("updatePage")
	public String updatePage(String id,ModelMap map){
		Map<String, Object> news = newsService.selectNewsByIdHt(id);
		TjyNews dto =newsService.selectById(id);
		if(news==null){
			return NODATA;
		}
		map.addAttribute("n",news);
		OssConfig ossConfig = (OssConfig) SpringContextUtil.getBean("ossConfig");
		String ossurl = ossConfig.getOss_getUrl();
		map.addAttribute("ossurl", ossurl);
		//查询所有资讯分类
		List<TjyNewsClass> list = newsClassService.selectAllNewsClass();
		map.addAttribute("newsclass", list);
		//判断是否已经发布
			if(dto.getCreateTime().compareTo(new Date())>0){
				map.addAttribute("published", 0);
			}else{
				map.addAttribute("published", 1);
			}
		return "system/news/update";
	}
	/**
	 * 跳转到资讯预览页面
	 * @param id
	 * @param map
	 * @return
	 */
	///@RequiresPermissions("news:update")
	@RequestMapping("viewPage")
	public String viewPage(String id,ModelMap map){
		Map<String, Object> news = newsService.selectNewsById(id);
		if(news==null){
			return NODATA;
		}
		OssConfig ossConfig = (OssConfig) SpringContextUtil.getBean("ossConfig");
		String ossurl = ossConfig.getOss_getUrl();
		map.addAttribute("ossurl", ossurl);
		//查询所有资讯分类
		List<TjyNewsClass> list = newsClassService.selectAllNewsClass();
		map.addAttribute("newsclass", list);
		int count = 0;	//评论数
		Integer commentCount = 0;
		commentCount = news.get("commentCount")==null?0:(Integer)news.get("commentCount");
		if(commentCount==0){
			Comment comment = new Comment();
			comment.setFkId((String)(news.get("id")));
			comment.setCmeType("1");
			List<Map<String, Object>> commentList = commentService
					.selectAllComment(comment);
			if (null != commentList) {
				count = commentList.size();
			}
		}else{
			count = commentCount;
		}
		news.put("count", count);
		map.addAttribute("n",news);
		return "system/news/view";
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
	
	/**
	 * 修改资讯
	 * @param newsclass
	 * @param errors
	 * @return
	 */
	@RequiresPermissions("news:update")
	@RequestMapping("update")
	public ModelAndView update(TjyNews news,Errors errors){
		if(errors.hasErrors()) {  
			ModelAndView mav=getValidationMessage(errors);
			if(mav!=null)return mav;
        }
		news.setCharge(news.getCharge()==null?0:news.getCharge());
		return ajaxDone(newsService.updateNews(news));
		
	}
	/**
	 * 修改资讯
	 * @param newsclass
	 * @param errors
	 * @return
	 */
	@RequiresPermissions("news:update")
	@RequestMapping("update1")
	public ModelAndView update1(TjyNews news,Errors errors){
		if(errors.hasErrors()) {  
			ModelAndView mav=getValidationMessage(errors);
			if(mav!=null)return mav;
		}
		news.setUpdateTime(new Date());
		news.setCharge(news.getCharge()==null?0:news.getCharge());
		return ajaxDone(newsService.updateNews1(news));
		
	}
	/**
	 * 删除资讯
	 * @param ids
	 * @return
	 */
	@RequiresPermissions("news:delete")
	@RequestMapping("del")
	public ModelAndView del(String[] ids){	
		return ajaxDone(newsService.deleteNews(ids));
	}
	/**
	 * 图片上传
	 * @param request
	 * @param ysStyle
	 * @param formId
	 * @param inputId
	 * @param inputOnChange
	 * @param jsonp
	 * @param itemPicParam
	 * @param moduleName
	 * @return
	 */
	@RequestMapping("getUploadPicForm")
	public ModelAndView getUploadPicForm(HttpServletRequest request,String ysStyle, String formId, String inputId, String inputOnChange, String jsonp,String itemPicParam,String moduleName) {
		ModelAndView modelAndView = new ModelAndView("system/news/uploadPic");
		modelAndView.addObject("formId", formId);
		modelAndView.addObject("inputId", inputId);
		modelAndView.addObject("inputOnChange", inputOnChange);
		modelAndView.addObject("jsonp", jsonp);
		modelAndView.addObject("ysStyle", ysStyle);
		modelAndView.addObject("itemPicParam",itemPicParam);
		modelAndView.addObject("moduleName",moduleName);
		return modelAndView;
	}
	@RequestMapping("uploadPic")
	public ModelAndView uploadPic(HttpServletRequest request,String ysStyle,String jsonp,@RequestParam(value="pic") MultipartFile pic,String moduleName) {
		ModelAndView modelAndView = new ModelAndView("system/news/uploadCallBack");
		modelAndView.addObject("callback", jsonp);
		UploadResultVo resultVo = new UploadResultVo(1,"系统异常,请稍后再试");
		OssConfig ossConfig = (OssConfig) SpringContextUtil.getBean("ossConfig");
		String ossurl = ossConfig.getOss_getUrl();
		if (null == pic) {
			resultVo.setMsg("请重新选择上传图片");
			modelAndView.addObject("data", JSON.toJSONString(resultVo));
			return modelAndView;
		}
		try {
			if(pic.getSize() > 2097152){
				resultVo.setMsg("请重新选择上传图片:图片最大支持2M大小");
				modelAndView.addObject("data", JSON.toJSONString(resultVo));
				return modelAndView;
			}
			InputStream io = pic.getInputStream();
			if(null==moduleName||"".equals(moduleName)){
				moduleName="wx";
			}
			String ext = FilenameUtils.getExtension(pic.getOriginalFilename());
			String picPath = FileUploadUtil.uploadFileInputStream(io, ext, moduleName);
			if(ysStyle!=null && !"".equals(ysStyle))
				picPath += "?x-oss-process=style/"+ysStyle;
			if (picPath!=""&&!"".equals(picPath)) {
				resultVo.setReturnCode(1);
				resultVo.setPicPath(picPath);
				resultVo.setMsg(picPath);
				resultVo.setImg_url(ossurl+picPath);
			}else{
				resultVo.setMsg("上传图片信息失败");
			}
		} catch (Exception e) {
			resultVo.setMsg("上传图片信息失败");
		}
		modelAndView.addObject("data", JSON.toJSONString(resultVo).replaceAll("\"", "'"));
		return modelAndView;
	}
	@RequestMapping("upload")
	public void upload(HttpServletRequest request, HttpServletResponse response,String ysStyle,String moduleName) throws ClassNotFoundException {
		String msg = "";
		JSONObject obj = new JSONObject();
		try {
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
			CommonsMultipartFile file = (CommonsMultipartFile) multipartRequest.getFile("imgFile");
			file = file==null||file.isEmpty()?(CommonsMultipartFile) multipartRequest.getFile("file"):file;
			InputStream io = file.getInputStream();
			String ext = FilenameUtils.getExtension(file.getOriginalFilename());
			String picPath = FileUploadUtil.uploadFileInputStream(io, ext, moduleName);
			OssConfig ossConfig = (OssConfig) SpringContextUtil.getBean("ossConfig");
			String ossurl = ossConfig.getOss_getUrl();
			obj.put("error", Integer.valueOf(0));
			obj.put("url", ossurl+picPath+"?x-oss-process=style/"+ysStyle);
		//	obj.put("fileName",picPath);
		} catch (IOException e) {
			obj.put("error", Integer.valueOf(1));
			obj.put("message", e.getMessage());
			e.printStackTrace();
		}
		response.setContentType("text/html");
		response.setHeader("Cache-Control", "no-cache");
		response.setCharacterEncoding("UTF-8");
		try {
			PrintWriter writer = response.getWriter();
			writer.print(obj.toJSONString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 条件查询热推资讯
	 * 
	 * @return
	 */
	@RequiresPermissions("hotnews:read")
	@RequestMapping("hotload")
	public String hotload(){
		return "system/news/hotload";
	}
	@RequiresPermissions("hotnews:read")
	@RequestMapping("hotquery")
	public ModelAndView hotquery(PageParam param,TjyNews news,String startTimef,String endTimef){
		
		return ajaxJsonEscape(newsService.selectAllHotNews(param, news,startTimef,endTimef,1));
	}
	/**
	 * 跳转到热推资讯添加页面
	 * @return
	 */
	@RequiresPermissions("hotnews:add")
	@RequestMapping("hotaddPage")
	public String hotaddPage(ModelMap map){
		return "system/news/hotadd";
	
	}
	/**
	 * 添加热推资讯
	 * @param newsclass
	 * @param errors
	 * @return
	 */
	@RequiresPermissions("hotnews:add")
	@RequestMapping("hotadd")
	public ModelAndView hotadd(TjyNews news,Errors errors){
		if(errors.hasErrors()) {  
			ModelAndView mav=getValidationMessage(errors);
			if(mav!=null)return mav;
        }
		news.setCreateTime(new Date());
		news.setUpdateTime(new Date());
		news.setIsHot(1);
		news.setCreateUserId(ServletUtil.getMember().getId());
		news.setCreateUserName(ServletUtil.getMember().getUserName());
		news.setLookCount(news.getLookCount()==null?0:news.getLookCount());
		news.setCommentCount(news.getCommentCount()==null?0:news.getCommentCount());
		return ajaxDone(newsService.addNews(news));	
	}
	/**
	 * 跳转到热推资讯修改页面
	 * @param id
	 * @param map
	 * @return
	 */
	@RequiresPermissions("hotnews:update")
	@RequestMapping("hotupdatePage")
	public String hotupdatePage(String id,ModelMap map){
		TjyNews news = newsService.selectById(id);
		if(news==null){
			return NODATA;
		}
		map.addAttribute("n",news);
		OssConfig ossConfig = (OssConfig) SpringContextUtil.getBean("ossConfig");
		String ossurl = ossConfig.getOss_getUrl();
		map.addAttribute("ossurl", ossurl);
		return "system/news/hotupdate";
	}
	/**
	 * 修改资讯
	 * @param newsclass
	 * @param errors
	 * @return
	 */
	@RequiresPermissions("hotnews:update")
	@RequestMapping("hotupdate")
	public ModelAndView hotupdate(TjyNews news,Errors errors){
		if(errors.hasErrors()) {  
			ModelAndView mav=getValidationMessage(errors);
			if(mav!=null)return mav;
        }
		news.setUpdateTime(new Date());
		news.setLookCount(news.getLookCount()==null?0:news.getLookCount());
		news.setCommentCount(news.getCommentCount()==null?0:news.getCommentCount());
		return ajaxDone(newsService.updateNews(news));
		
	}
	/**
	 * 删除热推资讯
	 * @param ids
	 * @return
	 */
	@RequiresPermissions("hotnews:delete")
	@RequestMapping("hotdel")
	public ModelAndView hotdel(String[] ids){	
		return ajaxDone(newsService.deleteNews(ids));
	}
	/**
	 * 条件查询静态页管理
	 * 
	 * @return
	 */
	@RequiresPermissions("htmlnews:read")
	@RequestMapping("htmlload")
	public String htmlload(ModelMap map){
		map.addAttribute("type", 803);
		return "system/news/htmlload";
	}
	@RequiresPermissions("htmlnews:read")
	@RequestMapping("htmlquery")
	public ModelAndView htmlquery(PageParam param,TjyNews news){
		news.setClassRoot(Constants.NEWS_CLASS_ID_HTML);
		return ajaxJsonEscape(newsService.selectAllNews(param, news,null,null));
	}
	/**
	 * 跳转到静态页添加页面
	 * @return
	 */
	@RequiresPermissions("htmlnews:add")
	@RequestMapping("htmladdPage")
	public String htmladdPage(){
		return "system/news/htmladd";
	
	}
	/**
	 * 添加静态页
	 * @param newsclass
	 * @param errors
	 * @return
	 */
	@RequiresPermissions("htmlnews:add")
	@RequestMapping("htmladd")
	public ModelAndView htmladd(TjyNews news,Errors errors){
		if(errors.hasErrors()) {  
			ModelAndView mav=getValidationMessage(errors);
			if(mav!=null)return mav;
        }
		news.setCreateTime(new Date());
		news.setUpdateTime(new Date());
		news.setClassRoot(Constants.NEWS_CLASS_ID_HTML);
		news.setIsHot(0);
		news.setCreateUserId(ServletUtil.getMember().getId());
		news.setCreateUserName(ServletUtil.getMember().getUserName());
		return ajaxDone(newsService.addNews(news));	
	}
	/**
	 * 跳转到静态页修改页面
	 * @param id
	 * @param map
	 * @return
	 */
	@RequiresPermissions("htmlnews:update")
	@RequestMapping("htmlupdatePage")
	public String htmlupdatePage(String types,ModelMap map){
		Map news = null;
		List list = newsService.selectHtmlByType(types);
		if(list!=null&&list.size()>0){
			news = (Map)list.get(0);
		}
		map.addAttribute("n",news);
		return "system/news/htmlupdate";
	}
	/**
	 * 修改静态页
	 * @param newsclass
	 * @param errors
	 * @return
	 */
	@RequiresPermissions("htmlnews:update")
	@RequestMapping("htmlupdate")
	public ModelAndView htmlupdate(TjyNews news,Errors errors){
		if(errors.hasErrors()) {  
			ModelAndView mav=getValidationMessage(errors);
			if(mav!=null)return mav;
        }
		if(news.getId().equals("")||news.getId()==null){
			news.setCreateTime(new Date());
			news.setUpdateTime(new Date());
			news.setClassRoot(Constants.NEWS_CLASS_ID_HTML);
			news.setIsHot(0);
			news.setId(null);
			news.setCreateUserId(ServletUtil.getMember().getId());
			return ajaxDone(newsService.addNews(news));	
		}else{
			news.setUpdateTime(new Date());
			news.setClassRoot(Constants.NEWS_CLASS_ID_HTML);
			return ajaxDone(newsService.updateNews(news));
		}
	}
	/**
	 * 删除静态页
	 * @param ids
	 * @return
	 */
	@RequiresPermissions("htmlnews:delete")
	@RequestMapping("htmldel")
	public ModelAndView htmldel(String[] ids){	
		return ajaxDone(newsService.deleteNews(ids));
	}
	
	/**
	 * 条件查询商友专访管理
	 * 
	 * @return
	 */
	@RequiresPermissions("viewnews:read")
	@RequestMapping("loadview")
	public String loadview(ModelMap map){
		return "system/news/viewload";
	}
	@RequiresPermissions("viewnews:read")
	@RequestMapping("queryview")
	public ModelAndView queryview(PageParam param,TjyNews news,String startTime,String endTime){
		news.setClassRoot(Constants.NEWS_CLASS_ID_VIEW);
		return ajaxJsonEscape(newsService.selectAllNews(param, news,startTime,endTime));
	}
	/**
	 * 跳转到商友专访添加页面
	 * @return
	 */
	@RequiresPermissions("viewnews:add")
	@RequestMapping("addPageView")
	public String addPageView(ModelMap map){
		return "system/news/viewadd";
	
	}
	/**
	 * 添加商友专访
	 * @param newsclass
	 * @param errors
	 * @return
	 */
	@RequiresPermissions("viewnews:add")
	@RequestMapping("addview")
	public ModelAndView addview(TjyNews news,Errors errors){
		if(errors.hasErrors()) {  
			ModelAndView mav=getValidationMessage(errors);
			if(mav!=null)return mav;
        }
		news.setCreateTime(new Date());
		news.setUpdateTime(new Date());
		news.setClassRoot(Constants.NEWS_CLASS_ID_VIEW);
		news.setCreateUserId(ServletUtil.getMember().getId());
		news.setCreateUserName(ServletUtil.getMember().getUserName());
		return ajaxDone(newsService.addNews(news));	
	}
	
	
	
	/**
	 * 跳转到商友专访浏览页面
	 * @param id
	 * @param map
	 * @return
	 */
	@RequiresPermissions("viewnews:update")
	@RequestMapping("viewPageview")
	public String viewPageview(String id,ModelMap map){
		Map<String, Object> news = newsService.selectNewsByIdHt(id);
		if(news==null){
			return NODATA;
		}
		map.addAttribute("n",news);
		OssConfig ossConfig = (OssConfig) SpringContextUtil.getBean("ossConfig");
		String ossurl = ossConfig.getOss_getUrl();
		map.addAttribute("ossurl", ossurl);
		return "system/news/viewview";
	}
	/**
	 * 跳转到商友专访预览页面
	 * @param id
	 * @param map
	 * @return
	 */
	@RequiresPermissions("viewnews:update")
	@RequestMapping("viewyulan")
	public String viewyulan(String id,ModelMap map){
		Map<String, Object> news = newsService.selectNewsByIdHt(id);
		if(news==null){
			return NODATA;
		}
		map.addAttribute("n",news);
		OssConfig ossConfig = (OssConfig) SpringContextUtil.getBean("ossConfig");
		String ossurl = ossConfig.getOss_getUrl();
		map.addAttribute("ossurl", ossurl);
		return "system/news/viewyulan";
	}
	/**
	 * 跳转到商友专访修改页面
	 * @param id
	 * @param map
	 * @return
	 */
	@RequiresPermissions("viewnews:update")
	@RequestMapping("updatePageview")
	public String updatePageview(String id,ModelMap map){
		Map<String, Object> news = newsService.selectNewsByIdHt(id);
		if(news==null){
			return NODATA;
		}
		map.addAttribute("n",news);
		OssConfig ossConfig = (OssConfig) SpringContextUtil.getBean("ossConfig");
		String ossurl = ossConfig.getOss_getUrl();
		map.addAttribute("ossurl", ossurl);
		return "system/news/viewupdate";
	}
	/**
	 * 修改商友专访
	 * @param newsclass
	 * @param errors
	 * @return
	 */
	@RequiresPermissions("viewnews:update")
	@RequestMapping("updateview")
	public ModelAndView updateview(TjyNews news,Errors errors){
		if(errors.hasErrors()) {  
			ModelAndView mav=getValidationMessage(errors);
			if(mav!=null)return mav;
        }
		news.setUpdateTime(new Date());
		return ajaxDone(newsService.updateNews(news));
		
	}
	/**
	 * 删除商友专访
	 * @param ids
	 * @return
	 */
	@RequiresPermissions("viewnews:delete")
	@RequestMapping("delview")
	public ModelAndView delview(String[] ids){	
		return ajaxDone(newsService.deleteNews(ids));
	}
	
	@RequiresPermissions("newsclass:read")
	@RequestMapping("nclassquery")
	public ModelAndView nclassquery(){
		
		return ajaxJsonEscape(newsClassService.selectNewsclassMap());
	}
	/**
	 * 微吼首页
	 * @return
	 */
	@RequestMapping("vhallindex")
	public String vhallindex(){
		return "system/news/vhallnews";
	}
	/**
	 * 点播视频列表查询
	 * @return
	 */
	@RequestMapping("vhallquery")
	public ModelAndView vhallquery(PageParam param){
		DataGrid data=new DataGrid();
		PageHelper.startPage(param.getPage(), param.getRows());
		WebinarListResp resp = WebinarAPI.listUploadWebinar(param.getPage(),param.getRows());
		List<WebinarListResp.Webinar> list = resp.getList();
		data.setRows(list);
		data.setTotal(resp.getTotal());
		return ajaxJsonEscape(data);
	}
	/**
	 * 调整数量页面
	 * @return
	 */
	@RequestMapping("adjustNumPage")
	public String adjustNumPage(String id,ModelMap map){
		map.addAttribute("id", id);
		return "system/news/tzsl";
	}
	/**
	 * 调整数量
	 * @return
	 */
	@RequiresPermissions("news:update")
	@RequestMapping("adjustNum")
	public ModelAndView adjustNum(TjyNews tjyNews,Errors errors){
		TjyNews news = newsService.selectById(tjyNews.getId());
		Integer commentCount = tjyNews.getCommentCount();
		Integer lookCount = tjyNews.getLookCount();
		if(commentCount!=null&&!commentCount.equals("")){
			news.setCommentCount(commentCount);
		}
		if(lookCount!=null&&!lookCount.equals("")){
			news.setLookCount(lookCount);
		}
		return ajaxDone(newsService.updateNews(news));
	}
	
	/**
	 * 条件查询老板新闻三分钟
	 * 
	 * @return
	 */
	@RequiresPermissions("bossnews:read")
	@RequestMapping("loadBoss")
	public String loadBoss(){
		return "system/news/bossload";
	}
	@RequiresPermissions("bossnews:read")
	@RequestMapping("queryBoss")
	public ModelAndView queryBoss(PageParam param,TjyNews news,String startTimef,String endTimef){
		
		return ajaxJsonEscape(newsService.selectAllHotNews(param, news,startTimef,endTimef,2));
	}
	/**
	 * 跳转到老板新闻三分钟添加页面
	 * @return
	 */
	@RequiresPermissions("bossnews:add")
	@RequestMapping("addPageBoss")
	public String addPageBoss(ModelMap map){
		return "system/news/bossadd";
	
	}
	/**
	 * 添加老板新闻三分钟
	 * @param newsclass
	 * @param errors
	 * @return
	 */
	@RequiresPermissions("bossnews:add")
	@RequestMapping("addBoss")
	public ModelAndView addBoss(TjyNews news,Errors errors){
		if(errors.hasErrors()) {  
			ModelAndView mav=getValidationMessage(errors);
			if(mav!=null)return mav;
        }
		news.setCreateTime(new Date());
		news.setUpdateTime(new Date());
		news.setIsHot(2);
		news.setCreateUserId(ServletUtil.getMember().getId());
		news.setCreateUserName(ServletUtil.getMember().getUserName());
		news.setLookCount(news.getLookCount()==null?0:news.getLookCount());
		return ajaxDone(newsService.addNews(news));	
	}
	/**
	 * 跳转到老板新闻三分钟修改页面
	 * @param id
	 * @param map
	 * @return
	 */
	@RequiresPermissions("bossnews:update")
	@RequestMapping("updatePageBoss")
	public String updatePageBoss(String id,ModelMap map){
		TjyNews news = newsService.selectById(id);
		if(news==null){
			return NODATA;
		}
		map.addAttribute("n",news);
		OssConfig ossConfig = (OssConfig) SpringContextUtil.getBean("ossConfig");
		String ossurl = ossConfig.getOss_getUrl();
		map.addAttribute("ossurl", ossurl);
		return "system/news/bossupdate";
	}
	/**
	 * 修改老板新闻三分钟
	 * @param newsclass
	 * @param errors
	 * @return
	 */
	@RequiresPermissions("bossnews:update")
	@RequestMapping("updateBoss")
	public ModelAndView updateBoss(TjyNews news,Errors errors){
		if(errors.hasErrors()) {  
			ModelAndView mav=getValidationMessage(errors);
			if(mav!=null)return mav;
        }
		news.setUpdateTime(new Date());
		return ajaxDone(newsService.updateNews(news));
		
	}
	/**
	 * 删除老板新闻三分钟
	 * @param ids
	 * @return
	 */
	@RequiresPermissions("bossnews:delete")
	@RequestMapping("delBoss")
	public ModelAndView delBoss(String[] ids){	
		return ajaxDone(newsService.deleteNews(ids));
	}
	
	
	/**
	 * 关于天九编辑
	 */
	@RequiresPermissions("about:update")
	@RequestMapping("updateTjIntro")
	public String updateTjIntro(ModelMap map){
		TjyNews news = newsService.selectById(Constants.NEWS_CLASS_ID_TIANJIU);
		map.addAttribute("n",news);
		return "system/news/updateabout";
	}
	/**
	 * 分享配置编辑
	 */
	@RequiresPermissions("share:update")
	@RequestMapping("updateshare")
	public String updateshare(ModelMap map){
		TjyNews news = newsService.selectById(Constants.NEWS_CLASS_ID_SHARE);
		map.addAttribute("n",news);
		return "system/news/updateshare";
	}
	
	/**
	 * 跳转到热推预览页面
	 * @param id
	 * @param map
	 * @return
	 */
	///@RequiresPermissions("news:update")
	@RequestMapping("hotviewPage")
	public String hotviewPage(String id,ModelMap map){
		Map<String, Object> news = newsService.selectNewsById(id);
		if(news==null){
			return NODATA;
		}
		OssConfig ossConfig = (OssConfig) SpringContextUtil.getBean("ossConfig");
		String ossurl = ossConfig.getOss_getUrl();
		map.addAttribute("ossurl", ossurl);
		int count = 0;	//评论数
		Integer commentCount = 0;
		commentCount = news.get("commentCount")==null?0:(Integer)news.get("commentCount");
		if(commentCount==0){
			Comment comment = new Comment();
			comment.setFkId((String)(news.get("id")));
			comment.setCmeType("1");
			List<Map<String, Object>> commentList = commentService
					.selectAllComment(comment);
			if (null != commentList) {
				count = commentList.size();
			}
		}else{
			count = commentCount;
		}
		news.put("count", count);
		map.addAttribute("n",news);
		return "system/news/hotview";
	}
}
