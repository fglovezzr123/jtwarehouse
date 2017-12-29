package com.wing.socialcontact.front.action;

import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import sun.misc.BASE64Decoder;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.wing.socialcontact.common.action.BaseAction;
import com.wing.socialcontact.common.model.Member;
import com.wing.socialcontact.config.OssConfig;
import com.wing.socialcontact.sys.service.IListValuesService;
import com.wing.socialcontact.util.FileUploadUtil;
import com.wing.socialcontact.util.SpringContextUtil;

/**
 * im 图片上传接口
 * @author zhangzheng
 *
 */
@Controller
@RequestMapping("/im/m")
public class ImUploadAction extends BaseAction {
	
	@Autowired
	private IListValuesService listValuesService;

	@RequestMapping("imupload")
	public String jsppage() {
		
		return "investment/invention/imupload";
		
	}
	
	
	@RequestMapping("upload")
	public @ResponseBody Map upload(HttpServletRequest request,  
			@RequestParam(value = "imgurl_file") MultipartFile[] imgurl_file,Object formData) { 
		
		Member me = (Member) request.getSession().getAttribute("me");
		if (null == me) {
			return super.getAjaxResult("302", "登录超时，请重新登录", null);
		}
		String userId = me.getId();
		if (StringUtils.isEmpty(userId)) {
			return super.getAjaxResult("302", "登录超时，请重新登录", null);
		}
		List list = new ArrayList();
		try {  
			if(imgurl_file.length<1){
				return super.getAjaxResult("999", "请重新上传图片", null);
			}
			for (MultipartFile pic : imgurl_file) {  
				if(!pic.isEmpty()){  
					OssConfig ossConfig = (OssConfig) SpringContextUtil.getBean("ossConfig");
					String ossurl = ossConfig.getOss_getUrl();
					try {
						if (pic.getSize() > 2097152) {
							return super.getAjaxResult("999", "图片最大支持2M", null);
						}
						InputStream io = pic.getInputStream();
						String uploadFilePath = "wx";
						String ext = "." + FilenameUtils.getExtension(pic.getOriginalFilename());
						String picPath = FileUploadUtil.uploadFileInputStream(io, ext, uploadFilePath);
						if (picPath != "" && !"".equals(picPath)) {
							Map map = new HashMap();
							map.put("url", ossurl + picPath);
							map.put("picPath", picPath);
							list.add(map);
						} else {
							return super.getAjaxResult("999", "上传图片信息失败", null);
						}
					} catch (Exception e) {
						return super.getAjaxResult("999", "上传图片信息失败", null);
					}   
				}  
			}  
			
		} catch (Exception e) {  
			e.printStackTrace();  
		}  
		return super.getSuccessAjaxResult("上传成功",list);  
	} 
	
	@RequestMapping("baseupload")
	public @ResponseBody Map baseupload(HttpServletRequest request,  
			String imgstr ,String proportion,String moduleName) { 
		Member me = (Member) request.getSession().getAttribute("me");
		if (null == me) {
			return super.getAjaxResult("302", "登录超时，请重新登录", null);
		}
		String userId = me.getId();
		if (StringUtils.isEmpty(userId)) {
			return super.getAjaxResult("302", "登录超时，请重新登录", null);
		}// data:image/jpeg;base64,
		String ext = "jpeg";
		try {
			BASE64Decoder decoder = new BASE64Decoder();
		      // Base64解码
			System.out.println("imgstr++++++++++++++++++++++++++++++++++++++++++++++++++++"+imgstr.length());
		      byte[] bt = decoder.decodeBuffer(imgstr);
		      for (int i = 0; i < bt.length; ++i) {
		        if (bt[i] < 0) {// 调整异常数据
		        	bt[i] += 256;
		        }
		      }
			sun.misc.BASE64Decoder decoder1 = new sun.misc.BASE64Decoder();  
			 
			OssConfig ossConfig = (OssConfig) SpringContextUtil.getBean("ossConfig");
			String ossurl = ossConfig.getOss_getUrl();
			InputStream io = new ByteArrayInputStream( bt); 
			if(moduleName==null||"".equals(moduleName)){
				moduleName="wx";
			}
			String picPath = FileUploadUtil.uploadFileInputStream(io, ext, moduleName,proportion,2);
			Map map = new HashMap();
			if (picPath != "" && !"".equals(picPath)) {
				map.put("url", ossurl + picPath);
				map.put("picPath", picPath);
			} else {
				return super.getAjaxResult("999", "上传图片信息失败", null);
			}
			return super.getSuccessAjaxResult("上传成功",map);  
		} catch (IOException e) {
			return super.getAjaxResult("999", "上传图片信息失败", null);
		} 
	} 
	
	/**
	 * 搜索热词查询（热词在字典值中配置）
	 * 1活动   2首页  3资讯  4项目联营   5合作洽谈  6话题PK
	 */
	@RequestMapping("searchKey")
	public @ResponseBody Map getSearchKey(HttpServletRequest request,Integer type){
		
		Member me = (Member) request.getSession().getAttribute("me");
		if (null == me) {
			return super.getAjaxResult("302", "登录超时，请重新登录", null);
		}
		String userId = me.getId();
		if (StringUtils.isEmpty(userId)) {
			return super.getAjaxResult("302", "登录超时，请重新登录", null);
		}
		List list = new ArrayList();
		if(type==1){
			//活动热词
			list = listValuesService.selectListByType(9002);
		}else if(type==2){
			//首页热词
			list = listValuesService.selectListByType(9003);
		}else if(type==3){
			//资讯热词
			list = listValuesService.selectListByType(9004);
		}else if(type==4){
			//项目联营热词
			list = listValuesService.selectListByType(9005);
		}else if(type==5){
			//合作洽谈热词
			list = listValuesService.selectListByType(9006);
		}else if(type==6){
			//话题PK热词
			list = listValuesService.selectListByType(9007);
		}
		return super.getSuccessAjaxResult("操作成功",list);
	}
}
