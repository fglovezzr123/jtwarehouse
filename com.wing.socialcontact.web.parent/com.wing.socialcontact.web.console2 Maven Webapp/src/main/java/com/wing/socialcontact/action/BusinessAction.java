package com.wing.socialcontact.action;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.wing.socialcontact.common.model.PageParam;
import com.wing.socialcontact.config.OssConfig;
import com.wing.socialcontact.service.wx.api.IBusinessClassService;
import com.wing.socialcontact.service.wx.api.IBusinessDisscussService;
import com.wing.socialcontact.service.wx.api.IBusinessService;
import com.wing.socialcontact.service.wx.api.ICommentService;
import com.wing.socialcontact.service.wx.api.ICommentThumbupService;
import com.wing.socialcontact.service.wx.api.IWxUserService;
import com.wing.socialcontact.service.wx.bean.Business;
import com.wing.socialcontact.service.wx.bean.BusinessClass;
import com.wing.socialcontact.service.wx.bean.BusinessDisscuss;
import com.wing.socialcontact.service.wx.bean.Comment;
import com.wing.socialcontact.service.wx.bean.CommentThumbup;
import com.wing.socialcontact.sys.action.BaseAction;
import com.wing.socialcontact.util.ServletUtil;
import com.wing.socialcontact.util.SpringContextUtil;

/**
 * 合作管理
 * @author zhangfan
 *
 */
@Controller
@RequestMapping("/business")
public class BusinessAction extends BaseAction{
	
	@Autowired
	private IBusinessService businessService; 
	@Autowired
	private IBusinessClassService businessClassService;
	@Autowired
	private IBusinessDisscussService businessDisscussService;
	@Autowired
	private ICommentThumbupService commentThumbupService;
	@Autowired
	private ICommentService commentService;
	@Autowired
	private IWxUserService wxUserService;
	/**
	 * 条件查询合作
	 * 
	 * @return
	 */
	@RequiresPermissions("business:read")
	@RequestMapping("load")
	public String load(ModelMap map){
		List list = businessClassService.selectAllClass(null,null);
		map.addAttribute("list", list);
		return "business/load";
	
	}
	@RequiresPermissions("business:read")
	@RequestMapping("query")
	public ModelAndView query(PageParam param,Business business,String startTimef,String endTimef,String createUserId,String userId){
		
		return ajaxJsonEscape(businessService.selectAllBusiness(param, business, startTimef, endTimef, createUserId,userId));
	}
	@RequiresPermissions("business:read")
	@RequestMapping("query2")
	public ModelAndView query2(PageParam param,Business business,String startTimef,String endTimef,String createUserId,String userId){
		
		return ajaxJsonEscape(businessService.selectAllBusiness2(param, business, startTimef, endTimef, createUserId,userId));
	}
	/**
	 * 跳转到合作添加页面
	 * @return
	 */
	@RequiresPermissions("business:add")
	@RequestMapping("addPage")
	public String addPage(ModelMap map){
		return "business/add";
	
	}
	/**
	 * 添加合作
	 * @param business
	 * @param errors
	 * @return
	 */
	@RequiresPermissions("business:add")
	@RequestMapping("add")
	public ModelAndView add(Business business,Errors errors){
		if(errors.hasErrors()) {  
			ModelAndView mav=getValidationMessage(errors);
			if(mav!=null)return mav;
        }
		business.setCreateTime(new Date());
		business.setCreateUserId(ServletUtil.getMember().getId());
		business.setCreateUserName(ServletUtil.getMember().getUserName());
		business.setLookCount(0);
		business.setStatus(2);
		return ajaxDone(businessService.addBusiness(business));	
	}
	/**
	 * 跳转到合作修改页面
	 * @param id
	 * @param map
	 * @return
	 */
	@RequiresPermissions("business:update")
	@RequestMapping("updatePage")
	public String updatePage(String id,ModelMap map){
		Business business = businessService.selectById(id);
		if(business==null){
			return NODATA;
		}
		map.addAttribute("b",business);
		List list = businessClassService.selectAllClass(null,null);
		map.addAttribute("list", list);
		return "business/update";
	}
	/**
	 * 跳转到合作浏览页面
	 * @param id
	 * @param map
	 * @return
	 */
	@RequiresPermissions("business:update")
	@RequestMapping("viewPage")
	public String viewPage(HttpServletRequest request,HttpServletResponse response,String id,ModelMap map){
		Map<String, Object> business = businessService.selectBusinessById(id);
		if(business==null){
			return NODATA;
		}
		map.addAttribute("b",business);
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
			
			// 获取子评论
			Comment subcomment = new Comment();
			subcomment.setParentId((String) (m.get("id")));
			List<Map<String, Object>> subCommentList = commentService
					.queryCommentbyPid((String) (m.get("id")));
			if (null != subCommentList) {
				subcount = subCommentList.size();
			}
			for(Map<String, Object> s : subCommentList){
				// 获取用户
				Map<String, Object> user = wxUserService.queryUsersByid((String) (s
						.get("userId")));
				if (null != user) {
					s.put("imgurl", (String) (user.get("head_portrait")));
					s.put("nickname", (String) (user.get("nickname")));
					if(null!=user.get("jobName")){
						s.put("job", (String) (user.get("jobName")));
					}else{
						s.put("job", "");
					}
					if(null!=user.get("industryName")){
						s.put("industry", (String) (user.get("industryName")));
					}else{
						s.put("industry", "");
					}
				} else {
					s.put("imgurl", "");
					s.put("nickname", "匿名");
					s.put("job", "无工作");
					s.put("industry", "无职位");
				}
			}
			m.put("subcount", subcount);
			m.put("subCommentList", subCommentList);
		}
		map.addAttribute("list", list);
		return "business/view";
	}
	/**
	 * 修改合作
	 * @param business
	 * @param errors
	 * @return
	 * @throws ParseException 
	 */
	@RequiresPermissions("business:update")
	@RequestMapping("update")
	public ModelAndView update(Business business,Errors errors) throws ParseException{
		if(errors.hasErrors()) {  
			ModelAndView mav=getValidationMessage(errors);
			if(mav!=null)return mav;
        }
		//判断有效日期是否过期，如果过期，修改状态
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		if(business.getEndTime().getTime()<sdf.parse(sdf.format(new Date())).getTime()){
			business.setRewardFinish(3);
		}else{
			if(business.getRewardFinish()==3){
				business.setRewardFinish(2);
			}
		}
		return ajaxDone(businessService.updateBusiness(business));
		
	}
	/**
	 * 删除合作
	 * @param ids
	 * @return
	 */
	@RequiresPermissions("business:delete")
	@RequestMapping("del")
	public ModelAndView del(String[] ids){	
		return ajaxDone(businessService.deleteBusiness(ids));
	}
	/**
	 * 更新审核状态
	 * @param ids
	 * @return
	 */
	@RequiresPermissions("business:update")
	@RequestMapping("updateStatus")
	public ModelAndView updateStatus(String[] ids){	
		return ajaxDone(businessService.updateStatus(ids));
	}
	
	/**
	 * 条件查询合作类别
	 * 
	 * @return
	 */
	@RequiresPermissions("bclass:read")
	@RequestMapping("loadClass")
	public String loadClass(ModelMap map){
		return "business/loadclass";
	
	}
	@RequiresPermissions("bclass:read")
	@RequestMapping("queryClass")
	public ModelAndView queryClass(PageParam param,BusinessClass bclass){
		
		return ajaxJsonEscape(businessClassService.selectClasses(param, bclass));
	}
	/**
	 * 跳转到合作类别添加页面
	 * @return
	 */
	@RequiresPermissions("bclass:add")
	@RequestMapping("addPageClass")
	public String addPageClass(ModelMap map){
		return "business/addClass";
	
	}
	/**
	 * 添加合作类别
	 * @param bclass
	 * @param errors
	 * @return
	 */
	@RequiresPermissions("bclass:add")
	@RequestMapping("addClass")
	public ModelAndView addClass(BusinessClass bclass,Errors errors){
		if(errors.hasErrors()) {  
			ModelAndView mav=getValidationMessage(errors);
			if(mav!=null)return mav;
        }
		bclass.setCreateTime(new Date());
		bclass.setCreateUserId(ServletUtil.getMember().getId());
		bclass.setCreateUserName(ServletUtil.getMember().getUserName());
		String str = businessClassService.addClass(bclass);
		if(str.equals("msg.businessClass.unique")){
			return ajaxDoneTextError("该合作类别已存在！");
		}
		return ajaxDone(str);	
	}
	/**
	 * 跳转到合作类别修改页面
	 * @param id
	 * @param map
	 * @return
	 */
	@RequiresPermissions("bclass:update")
	@RequestMapping("updatePageClass")
	public String updatePageClass(String id,ModelMap map){
		BusinessClass bclass = businessClassService.selectById(id);
		if(bclass==null){
			return NODATA;
		}
		map.addAttribute("b",bclass);
		OssConfig ossConfig = (OssConfig) SpringContextUtil.getBean("ossConfig");
		String ossurl = ossConfig.getOss_getUrl();
		map.addAttribute("ossurl", ossurl);
		return "business/updateclass";
	}
	/**
	 * 修改合作类别
	 * @param bclass
	 * @param errors
	 * @return
	 */
	@RequiresPermissions("bclass:update")
	@RequestMapping("updateClass")
	public ModelAndView updateClass(BusinessClass bclass,Errors errors){
		if(errors.hasErrors()) {  
			ModelAndView mav=getValidationMessage(errors);
			if(mav!=null)return mav;
        }
		String str = businessClassService.updateClass(bclass);
		if(str.equals("msg.businessClass.unique")){
			return ajaxDoneTextError("该合作类别已存在！");
		}
		return ajaxDone(str);	
		
	}
	/**
	 * 删除合作类别
	 * @param ids
	 * @return
	 */
	@RequiresPermissions("bclass:delete")
	@RequestMapping("delClass")
	public ModelAndView delClass(String id){
		return ajaxDone(businessClassService.deleteClass(id));
	}
	
	/**
	 * 条件查询合作洽谈
	 * 
	 * @return
	 */
	@RequiresPermissions("businessDis:read")
	@RequestMapping("loadBD")
	public String loadBD(){
		return "business/loadBD";
	
	}
	@RequiresPermissions("businessDis:read")
	@RequestMapping("queryBD")
	public ModelAndView queryBD(PageParam param,BusinessDisscuss bd,String titles,String startTimef,String endTimef){
		
		return ajaxJsonEscape(businessDisscussService.selectAllBD(param, bd, titles, startTimef, endTimef));
	}
	/**
	 * 跳转到合作洽谈修改页面
	 * @param id
	 * @param map
	 * @return
	 */
	@RequiresPermissions("businessDis:update")
	@RequestMapping("updatePageDB")
	public String updatePageDB(String id,ModelMap map){
		Map<String, Object> bd = businessDisscussService.selectBDById(id);
		if(bd==null){
			return NODATA;
		}
		map.addAttribute("b",bd);
		return "business/updateBD";
	}
	/**
	 * 修改合作洽谈
	 * @param business
	 * @param errors
	 * @return
	 */
	@RequiresPermissions("businessDis:update")
	@RequestMapping("updateDB")
	public ModelAndView updateDB(BusinessDisscuss bd,Errors errors){
		if(errors.hasErrors()) {  
			ModelAndView mav=getValidationMessage(errors);
			if(mav!=null)return mav;
        }
		return ajaxDone(businessDisscussService.updateBD(bd));
		
	}
	/**
	 * 删除合作洽谈
	 * @param ids
	 * @return
	 */
	@RequiresPermissions("businessDis:delete")
	@RequestMapping("delDB")
	public ModelAndView delDB(String[] ids){	
		return ajaxDone(businessDisscussService.deleteBDs(ids));
	}
	
	/**
	 * 更新显示状态
	 * @return
	 */
	@RequiresPermissions("businessDis:update")
	@RequestMapping("updateIsShow")
	public ModelAndView updateIsShow(String id,String isShow){
		BusinessDisscuss bd = businessDisscussService.selectById(id);
		bd.setIsShow(Integer.valueOf(isShow));
		return ajaxDone(businessDisscussService.updateBD(bd));
	}
}
