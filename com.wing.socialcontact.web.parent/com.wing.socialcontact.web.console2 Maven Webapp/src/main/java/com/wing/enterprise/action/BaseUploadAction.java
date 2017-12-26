package com.wing.enterprise.action;

import java.io.InputStream;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.wing.socialcontact.config.OssConfig;
import com.wing.socialcontact.service.wx.bean.UploadResultVo;
import com.wing.socialcontact.util.FileUploadUtil;
import com.wing.socialcontact.util.SpringContextUtil;


/**
 * 后台统一上传图片
 * @author sino
 *
 */
@Controller
@RequestMapping("/qfyBaseUploadPic")

public class BaseUploadAction {
    
    @RequestMapping("getUploadPicForm")
    public ModelAndView getUploadPicForm(HttpServletRequest request, String formId, String inputId, String inputOnChange, String jsonp,String itemPicParam,String picRepository) {
        ModelAndView modelAndView = new ModelAndView("qfy/uploadPic");
        modelAndView.addObject("formId", formId);
        modelAndView.addObject("inputId", inputId);
        modelAndView.addObject("inputOnChange", inputOnChange);
        modelAndView.addObject("jsonp", jsonp);
        modelAndView.addObject("itemPicParam",itemPicParam);
        modelAndView.addObject("picRepository",picRepository);
        return modelAndView;
    }
    
    @RequestMapping("uploadPic")
    public ModelAndView uploadPic(HttpServletRequest request,String jsonp,@RequestParam(value="pic") MultipartFile pic,String picRepository) {
        ModelAndView modelAndView = new ModelAndView("qfy/uploadCallBack");
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
            String ext = FilenameUtils.getExtension(pic.getOriginalFilename());
            String picPath = FileUploadUtil.uploadFileInputStream(io, ext, picRepository);
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
}
