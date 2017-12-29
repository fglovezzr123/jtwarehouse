package com.wing.socialcontact.action;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;

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
import com.wing.socialcontact.common.model.PageParam;
import com.wing.socialcontact.config.OssConfig;
import com.wing.socialcontact.service.wx.api.IInvestmentClassService;
import com.wing.socialcontact.service.wx.api.IInvestmentService;
import com.wing.socialcontact.service.wx.bean.Investment;
import com.wing.socialcontact.service.wx.bean.InvestmentClass;
import com.wing.socialcontact.service.wx.bean.UploadResultVo;
import com.wing.socialcontact.sys.action.BaseAction;
import com.wing.socialcontact.sys.service.IListValuesService;
import com.wing.socialcontact.util.FileUploadUtil;
import com.wing.socialcontact.util.ServletUtil;
import com.wing.socialcontact.util.SpringContextUtil;
@Controller
@RequestMapping("/investment")
public class InvestmentAction extends BaseAction {

	@Autowired
	private IInvestmentService investmentService;
	@Autowired
	private IInvestmentClassService investmentClassService;
	@Autowired
	private IListValuesService listValuesservice; 
	
	
	@RequiresPermissions("investment:read")
	@RequestMapping("load")
	public String load(){
		return "system/investment/load";
	}
	
	@RequiresPermissions("investment:read")
	@RequestMapping("query")
	public ModelAndView query(PageParam param,Investment investment){
		return ajaxJsonEscape(investmentService.selectinvestment(param, investment));
	}
	
	/**
	 * 投资新增
	 */
	@RequiresPermissions("investment:add")
	@RequestMapping("addPage")
	public String addPage(ModelMap map){
		//获取所有分类
		/*List<InvestmentClass> cla = investmentClassService.getAllClass();
		map.addAttribute("cla", cla);*/
		List tags = listValuesservice.selectListByType(9001);
		map.addAttribute("tags", tags);
		return "system/investment/add";
	}
	
	@RequiresPermissions("investment:add")
	@RequestMapping("add")
	public ModelAndView add( Investment dto,Errors errors){
		if(errors.hasErrors()) {  
			ModelAndView mav=getValidationMessage(errors);
			if(mav!=null)return mav;
        }
		dto.setCreateTime(new Date());
		dto.setCreateUserId(ServletUtil.getMember().getId());
		return ajaxDone(investmentService.addinvestment(dto));
	
	}
	
	/**
	 * 投资修改
	 */
	@RequiresPermissions("investment:update")
	@RequestMapping("updatePage")
	public String updatePage(String id,ModelMap map){
		
		OssConfig ossConfig = (OssConfig) SpringContextUtil.getBean("ossConfig");
		String ossurl = ossConfig.getOss_getUrl();
		map.addAttribute("ossurl", ossurl);
		//获取所有分类
		/*List<InvestmentClass> cla = investmentClassService.getAllClass();
		map.addAttribute("cla", cla);*/
		List tags = listValuesservice.selectListByType(9001);
		map.addAttribute("tags", tags);
		Investment dto = investmentService.getinvestmentByid(id);
		map.addAttribute("dto", dto);
		return "system/investment/update";
	}
	
	@RequiresPermissions("investment:update")
	@RequestMapping("update")
	public ModelAndView update( Investment dto,Errors errors){
		if(errors.hasErrors()) {  
			ModelAndView mav=getValidationMessage(errors);
			if(mav!=null)return mav;
        }
		dto.setUpdateTime(new Date());
		dto.setUpdateUserId(ServletUtil.getMember().getId());
		return ajaxDone(investmentService.updateinvestment(dto));
		
	}
	
	
	/**
	 * 投资详情
	 */
	@RequiresPermissions("investment:read")
	@RequestMapping("viewPage")
	public String viewPage(String id,ModelMap map){
		
		OssConfig ossConfig = (OssConfig) SpringContextUtil.getBean("ossConfig");
		String ossurl = ossConfig.getOss_getUrl();
		map.addAttribute("ossurl", ossurl);
		//获取所有分类
		/*List<InvestmentClass> cla = investmentClassService.getAllClass();
		map.addAttribute("cla", cla);*/
		List tags = listValuesservice.selectListByType(9001);
		map.addAttribute("tags", tags);
		Investment dto = investmentService.getinvestmentByid(id);
		map.addAttribute("dto", dto);
		return "system/investment/view";
	}
	
	/**
	 * 投资删除
	 */
	@RequiresPermissions("investment:delete")
	@RequestMapping("del")
	public ModelAndView del(String[] ids){
		
		return ajaxDone(investmentService.deleteinvestment(ids));
	}
	
	
	@RequiresPermissions("investment:add")
	@RequestMapping("getUploadPicForm")
	public ModelAndView getUploadPicForm(HttpServletRequest request, String formId, String inputId, String inputOnChange, String jsonp,String itemPicParam) {
		ModelAndView modelAndView = new ModelAndView("system/investment/uploadPic");
		modelAndView.addObject("formId", formId);
		modelAndView.addObject("inputId", inputId);
		modelAndView.addObject("inputOnChange", inputOnChange);
		modelAndView.addObject("jsonp", jsonp);
		modelAndView.addObject("itemPicParam",itemPicParam);
		return modelAndView;
	}
	@RequiresPermissions("investment:add")
	@RequestMapping("uploadPic")
	public ModelAndView uploadPic(HttpServletRequest request,String jsonp,@RequestParam(value="pic") MultipartFile pic) {
		ModelAndView modelAndView = new ModelAndView("system/investment/uploadCallBack");
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
			String uploadFilePath = "investment";
			String ext = FilenameUtils.getExtension(pic.getOriginalFilename());
			String picPath = FileUploadUtil.uploadFileInputStream(io, ext, uploadFilePath);
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
	public void upload(HttpServletRequest request, HttpServletResponse response) throws ClassNotFoundException {
		String msg = "";
		JSONObject obj = new JSONObject();
		try {
			String uploadFilePath = "investment";
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
			CommonsMultipartFile file = (CommonsMultipartFile) multipartRequest.getFile("imgFile");
			file = file==null||file.isEmpty()?(CommonsMultipartFile) multipartRequest.getFile("file"):file;
			InputStream io = file.getInputStream();
			String ext = FilenameUtils.getExtension(file.getOriginalFilename());
			String picPath = FileUploadUtil.uploadFileInputStream(io, ext, uploadFilePath);
			OssConfig ossConfig = (OssConfig) SpringContextUtil.getBean("ossConfig");
			String ossurl = ossConfig.getOss_getUrl();
			obj.put("error", Integer.valueOf(0));
			obj.put("url", ossurl+picPath);
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
}
