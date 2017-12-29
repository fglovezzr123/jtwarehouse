package com.wing.enterprise.action;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.wing.socialcontact.common.action.BaseAction;
import com.wing.socialcontact.config.OssConfig;
import com.wing.socialcontact.util.FileUploadUtil;
import com.wing.socialcontact.util.SpringContextUtil;

/**
 * im 图片上传接口
 * 
 * 
 * @ClassName: QfyImUploadAction
 * @Description: TODO
 * @author: sino
 * @date:2017年4月26日
 */
@Controller
@RequestMapping("m/qfy/im")
public class QfyImUploadAction extends BaseAction {
    
    @RequestMapping("upload")
    public @ResponseBody Map upload(HttpServletRequest request,  
            @RequestParam(value = "imgurl_file") MultipartFile[] imgurl_file,Object formData) { 
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
                        String uploadFilePath = "qfy/im";
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
}
