package com.wing.socialcontact.action;

import java.util.Date;
import java.util.List;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.wing.socialcontact.common.model.PageParam;
import com.wing.socialcontact.config.MsgConfig;
import com.wing.socialcontact.config.OssConfig;
import com.wing.socialcontact.service.wx.api.ILibraryClassService;
import com.wing.socialcontact.service.wx.api.ILibraryService;
import com.wing.socialcontact.service.wx.bean.TjyLibraryClass;
import com.wing.socialcontact.sys.action.BaseAction;
import com.wing.socialcontact.util.ServletUtil;
import com.wing.socialcontact.util.SpringContextUtil;

/**
 * 
 * @author zhangzheng
 *	文库分类及文库控制器
 */
@Controller
@RequestMapping("/libraryclass")
public class LibraryClassAction extends BaseAction {
	

	@Autowired
	private ILibraryClassService libraryClassService;
	@Autowired
	private ILibraryService libraryService;

	
	/**
	 * 文库分类列表
	 */
	@RequiresPermissions("libraryclass:read")
	@RequestMapping("classload")
	public String classload(){
		return "system/libraryclass/classload";
	}
	
	/**
	 * 文库分类列表查询
	 * @param param
	 * @param librarycla
	 * @return
	 */
	@RequiresPermissions("libraryclass:read")
	@RequestMapping("classquery")
	public ModelAndView classquery(PageParam param,TjyLibraryClass librarycla){
		librarycla.setLevel(1);
		return ajaxJsonEscape(libraryClassService.selectLibraryClass(param, librarycla));
	}
	/**
	 * 文库标签列表
	 */
	@RequiresPermissions("libraryclass:read")
	@RequestMapping("tagload")
	public String tagload(){
		return "system/libraryclass/tagload";
	}
	
	/**
	 * 文库标签列表查询
	 * @param param
	 * @param librarycla
	 * @return
	 */
	@RequiresPermissions("libraryclass:read")
	@RequestMapping("tagquery")
	public ModelAndView tagquery(PageParam param,TjyLibraryClass librarycla){
		librarycla.setLevel(2);
		return ajaxJsonEscape(libraryClassService.selectLibraryClass(param, librarycla));
	}
	
	/**
	 * 文库分类新增
	 */
	@RequiresPermissions("libraryclass:add")
	@RequestMapping("classaddPage")
	public String classaddPage(){
		return "system/libraryclass/classadd";
	}
	
	@RequiresPermissions("libraryclass:add")
	@RequestMapping("classadd")
	public ModelAndView classadd(TjyLibraryClass dto,Errors errors){
		if(errors.hasErrors()) {  
			ModelAndView mav=getValidationMessage(errors);
			if(mav!=null)return mav;
        }
		dto.setCreateTime(new Date());
		dto.setCreateUserId(ServletUtil.getMember().getId());
		dto.setLevel(1);
		dto.setContent(dto.getName());
		return ajaxDone(libraryClassService.addTjyLibraryClass(dto));
		
	}
	/**
	 * 文库标签新增
	 */
	@RequiresPermissions("libraryclass:add")
	@RequestMapping("tagaddPage")
	public String tagaddPage(ModelMap map ){
		TjyLibraryClass parm = new TjyLibraryClass();
		parm.setLevel(1);
		List classList = libraryClassService.selectLevelOne(parm);
		map.addAttribute("classList", classList);
		return "system/libraryclass/tagadd";
	}
	
	@RequiresPermissions("libraryclass:add")
	@RequestMapping("tagadd")
	public ModelAndView tagadd(TjyLibraryClass dto,Errors errors){
		if(errors.hasErrors()) {  
			ModelAndView mav=getValidationMessage(errors);
			if(mav!=null)return mav;
		}
		dto.setCreateTime(new Date());
		dto.setCreateUserId(ServletUtil.getMember().getId());
		dto.setLevel(2);
		TjyLibraryClass liclass = libraryClassService.getTjyLibraryClassByid(dto.getParentId());
		if(liclass==null||liclass.getName()==null){
			return ajaxDone(MsgConfig.MSG_KEY_FAIL);
		}
		if(1!=liclass.getLevel()){
			return ajaxDone(MsgConfig.MSG_KEY_FAIL);
		}
		dto.setContent(liclass.getName()+"-"+dto.getName());
		return ajaxDone(libraryClassService.addTjyLibraryClass(dto));
		
	}
	
	/**
	 * 文库分类修改
	 */
	@RequiresPermissions("libraryclass:update")
	@RequestMapping("classupdatePage")
	public String classupdatePage(String id,ModelMap map){
		TjyLibraryClass dto = libraryClassService.getTjyLibraryClassByid(id);
		map.addAttribute("dto", dto);
		OssConfig ossConfig = (OssConfig) SpringContextUtil.getBean("ossConfig");
		String ossurl = ossConfig.getOss_getUrl();
		map.addAttribute("ossurl", ossurl);
		return "system/libraryclass/classupdate";
	}
	
	@RequiresPermissions("libraryclass:update")
	@RequestMapping("classupdate")
	public ModelAndView classupdate( TjyLibraryClass dto,Errors errors){
		if(errors.hasErrors()) {  
			ModelAndView mav=getValidationMessage(errors);
			if(mav!=null)return mav;
        }
		dto.setUpdateTime(new Date());
		dto.setUpdateUserId(ServletUtil.getMember().getId());
		dto.setContent(dto.getName());
		return ajaxDone(libraryClassService.updateTjyLibraryClass(dto));
		
	}
	/**
	 * 文库标签修改
	 */
	@RequiresPermissions("libraryclass:update")
	@RequestMapping("tagupdatePage")
	public String tagupdatePage(String id,ModelMap map){
		TjyLibraryClass dto = libraryClassService.getTjyLibraryClassByid(id);
		map.addAttribute("dto", dto);
		OssConfig ossConfig = (OssConfig) SpringContextUtil.getBean("ossConfig");
		String ossurl = ossConfig.getOss_getUrl();
		map.addAttribute("ossurl", ossurl);
		TjyLibraryClass parm = new TjyLibraryClass();
		parm.setLevel(1);
		List classList = libraryClassService.selectLevelOne(parm);
		map.addAttribute("classList", classList);
		return "system/libraryclass/tagupdate";
	}
	
	@RequiresPermissions("libraryclass:update")
	@RequestMapping("tagupdate")
	public ModelAndView tagupdate( TjyLibraryClass dto,Errors errors){
		if(errors.hasErrors()) {  
			ModelAndView mav=getValidationMessage(errors);
			if(mav!=null)return mav;
		}
		dto.setUpdateTime(new Date());
		dto.setUpdateUserId(ServletUtil.getMember().getId());
		TjyLibraryClass liclass = libraryClassService.getTjyLibraryClassByid(dto.getParentId());
		if(liclass==null||liclass.getName()==null){
			return ajaxDone(MsgConfig.MSG_KEY_FAIL);
		}
		if(1!=liclass.getLevel()){
			return ajaxDone(MsgConfig.MSG_KEY_FAIL);
		}
		dto.setContent(liclass.getName()+"-"+dto.getName());
		return ajaxDone(libraryClassService.updateTjyLibraryClass(dto));
		
	}
	
	/**
	 * 文库分类删除
	 */
	@RequiresPermissions("libraryclass:delete")
	@RequestMapping("classdel")
	public ModelAndView classdel(String id){
		TjyLibraryClass dto = libraryClassService.getTjyLibraryClassByid(id);
		if(dto.getLevel()==2){
			//查询是否存在文章
			List l = libraryService.getTjyLibraryByclassid(dto.getId());
			if(l.size()>0){
				return ajaxDone(MsgConfig.MSG_KEY_ERROR_NODEL);
			}
		}else if(dto.getLevel()==1){
			//查询是否存在二级分类
			List l=libraryClassService.querybyparent(dto.getId());
			if(l.size()>0){
				return ajaxDone(MsgConfig.MSG_KEY_ERROR_NODEL);
			}
		}
		return ajaxDone(libraryClassService.deleteTjyLibraryClass(id));
	}
	
	/**
	 * 查询所有分类，返回json格式数据
	 * @return
	 */
	@RequestMapping("load/all")
	public ModelAndView allclass(){
		
		return ajaxJsonEscape(libraryClassService.selectAllClassMap());
		
	}
	/**
	 * 文库分类标签选择页
	 * @return
	 */
	@RequestMapping("lookUpPage")
	public String lookUpPage(){
	
			return "system/libraryclass/lookup";
	}
	
	/**
	 * 查询文库分类标签选择页数据
	 * @param param
	 * @param dto
	 * @return
	 */
	@RequestMapping("lookUp")
	public ModelAndView lookUp(PageParam param,TjyLibraryClass dto){
		
		return ajaxJsonEscape(libraryClassService.selectClass(param, dto));
	}
	
	
	
}
