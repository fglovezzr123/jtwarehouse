package com.wing.socialcontact.action;


import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.wing.socialcontact.config.OssConfig;
import com.wing.socialcontact.service.wx.api.*;
import com.wing.socialcontact.service.wx.bean.*;
import com.wing.socialcontact.util.*;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.wing.socialcontact.common.model.PageParam;
import com.wing.socialcontact.sys.action.BaseAction;

import javazoom.jl.decoder.Bitstream;
import javazoom.jl.decoder.BitstreamException;
import javazoom.jl.decoder.Header;

/**
 * 个性定制
 *
 * @author wangyansheng
 * @date 2017/11/01
 */
@Controller
@RequestMapping("/personalCustomization")
public class PersonalCustomizationAction extends BaseAction {

    @Autowired
    private IPersonalCustomizationService personalCustomizationService;

    /**
     * 个人定制首页
     *
     * @return
     */
    @RequestMapping("index")
    public String index() {
        return "personalCustomization/index";
    }

    /**
     * 个人定制列表查询
     *
     * @return
     */
    @RequestMapping("query")
    public ModelAndView query(PageParam param, PersonalCustomization t) {
        t.setDeleted(0);
        return ajaxJsonEscape(this.personalCustomizationService.selectByParam(param, t));
    }

    /**
     * 新增（发布）个人定制页面
     *
     * @return
     */
    @RequestMapping("addPage")
    public String addPage() {
        return "personalCustomization/addorupdate";
    }

    /**
     * 修改个人定制页面
     *
     * @return
     */
    @RequestMapping("updatePage")
    public String updatePage(ModelMap modelMap, String id) {
        PersonalCustomization m = this.personalCustomizationService.selectById(id);
        OssConfig ossConfig = (OssConfig) SpringContextUtil.getBean("ossConfig");
        String ossUrl = ossConfig.getOss_getUrl();
        modelMap.addAttribute("obj", m);
        modelMap.addAttribute("ossUrl", ossUrl);
        return "personalCustomization/addorupdate";
    }

    /**
     * 个人定制新增或修改保存
     *
     * @return
     */
    @RequestMapping(value = "saveOrUpdate", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> saveOrUpdate(PersonalCustomization t ,HttpServletRequest request) {

        try {

            //音频时长计算
            OssConfig ossConfig = (OssConfig) SpringContextUtil.getBean("ossConfig");
            String ossUrl = ossConfig.getOss_getUrl();

            t.setImageUrl(t.getImageUrl().replace(ossUrl,""));
            //判断有没有上传文件
            if (request instanceof MultipartHttpServletRequest) {
                MultipartFile voiceFile =  ((MultipartHttpServletRequest) request).getFile("voiceFile");
                if (null != voiceFile && !voiceFile.isEmpty() && voiceFile.getSize() > 0) {
                    try {
                        //上传音频
                        InputStream io2 = voiceFile.getInputStream();
                        String ext2 = "." + FilenameUtils.getExtension(voiceFile.getOriginalFilename());
                        String path = FileUploadUtil.uploadFileInputStream(io2, ext2, "personalCustomization", 1);
                        t.setVoiceUrl(path);

                    } catch (IOException e) {
                        return getAjaxResult("-1", "上传音频失败", null);
                    }


                    URL urlFile = new URL(ossUrl+t.getVoiceUrl());
                    URLConnection con = null;
                    try {
                        con = urlFile.openConnection();
                    } catch (IOException e) {
                        return getAjaxResult("-1", "获取音频时长失败", null);
                    }
                    int b = con.getContentLength();//
                    BufferedInputStream bis = new BufferedInputStream(con.getInputStream());
                    Bitstream bt = new Bitstream(bis);
                    Header h = bt.readFrame();
                    int time = (int) h.total_ms(b);
                    t.setVoiceTime(String.valueOf(Math.round(time / 1000)));
                }
            }

            if (t.getId() == null || "".equals(t.getId())) {
                t.setDeleted(0);
                t.setCreateTime(new Date());
                t.setCreateUserId(ServletUtil.getMember().getId());
                this.personalCustomizationService.insert(t);
                return getAjaxResult("0", "", null);
            } else {
                t.setUpdateUserId(ServletUtil.getMember().getId());
                t.setUpdateTime(new Date());
                this.personalCustomizationService.update(t);
                return getAjaxResult("0", "", null);
            }
        } catch (Exception e) {
            return getAjaxResult("-1", "保存失败", null);
        }
    }

    /**
     * 个人定制明细
     *
     * @param id
     * @return
     */
    @RequestMapping("detail")
    public String detail(ModelMap modelMap, String id) {
        PersonalCustomization m = this.personalCustomizationService.selectById(id);
        modelMap.addAttribute("obj", m);
        return "personalCustomization/detail";
    }

    /**
     * 删除个人定制（逻辑删除）
     *
     * @param t
     * @return
     */
    @RequestMapping("delete")
    @ResponseBody
    public Map<String, Object> delete(PersonalCustomization t) {
        try {

            t=personalCustomizationService.selectById(t.getId());
            t.setDeleted(1);
            t.setUpdateUserId(ServletUtil.getMember().getId());
            t.setUpdateTime(new Date());
            this.personalCustomizationService.update(t);
            return getAjaxResult("0", "", null);
        } catch (Exception e) {
            return getAjaxResult("-1", "操作失败", null);
        }
    }

    /**
     * 图片上传
     *
     * @param request
     * @param jsonp
     * @param
     * @return
     */
    @RequestMapping("uploadPic")
    @ResponseBody
    public Map<String, Object> uploadPic(HttpServletRequest request, String jsonp, MultipartFile file, String ysStyle) {
        return super.uploadImage(request, jsonp, file, ysStyle, "personalCustomization");
    }

}
