package com.wing.socialcontact.action;


import com.wing.socialcontact.common.model.DataGrid;
import com.wing.socialcontact.common.model.PageParam;
import com.wing.socialcontact.config.OssConfig;
import com.wing.socialcontact.service.wx.api.*;
import com.wing.socialcontact.service.wx.bean.*;
import com.wing.socialcontact.sys.action.BaseAction;
import com.wing.socialcontact.util.ServletUtil;
import com.wing.socialcontact.util.SpringContextUtil;
import com.wing.socialcontact.util.StringUtil;
import com.wing.socialcontact.util.UUIDGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import java.util.Date;
import java.util.Map;

/**
 * 用户拍照
 *
 * @author wangyansheng
 * @date 2017/11/01
 */
@Controller
@RequestMapping("/userPhoto")
public class UserPhotoAction extends BaseAction {

    @Autowired
    private IUserPhotoService userPhotoService;

    @Autowired
    private ITjyUserService tjyUserService;

    @Autowired
    private IMessageInfoService messageInfoService;

    /**
     * 拍照首页
     *
     * @return
     */
    @RequestMapping("index")
    public String index(ModelMap modelMap) {

        OssConfig ossConfig = (OssConfig) SpringContextUtil
                .getBean("ossConfig");
        String ossUrl = ossConfig.getOss_getUrl();
        modelMap.put("ossUrl", ossUrl);
        return "system/userPhoto/index";
    }

    /**
     * 拍照列表查询
     *
     * @return
     */
    @RequestMapping("queryUserPhotoList")
    public ModelAndView queryUserPhotoList(PageParam param, UserPhoto t) {
        return ajaxJsonEscape(this.userPhotoService.selectByParam(param, t));
    }

    /**
     * 明细
     *
     * @param id
     * @return
     */
    @RequestMapping("detail")
    public String detailProject(ModelMap modelMap, String id) {

        UserPhoto m = this.userPhotoService.selectById(id);
        modelMap.addAttribute("obj", m);
        OssConfig ossConfig = (OssConfig) SpringContextUtil
                .getBean("ossConfig");
        String ossUrl = ossConfig.getOss_getUrl();
        modelMap.put("ossUrl", ossUrl);
        return "system/userPhoto/detail";
    }

    /**
     * 审核
     *
     * @return
     */
    @RequestMapping(value = "examineVerify", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> examineVerify(String id) {

        try {

            UserPhoto t = new UserPhoto();
            t = userPhotoService.
                    selectById(id);
            if(t.getStatus()==1){
                t.setStatus(0);
            }else{
                t.setStatus(1);
            }
            t.setUpdateUserId(ServletUtil.getMember().getId());
            t.setUpdateTime(new Date());
            this.userPhotoService.update(t);

            //没有审核通过给app发送通知
            if(t.getStatus()==0){
                MessageInfo messageInfo = new MessageInfo();
                messageInfo.setId(UUIDGenerator.getUUID());
                messageInfo.setContent("注册拍照:照片审核未通过");
                messageInfo.setType(3);
                messageInfo.setTemplateId("user_photo_not_through");
                messageInfo.setStatus(0);
                messageInfo.setCreateTime(new Date());
                messageInfo.setToUserId(t.getUserId());
                messageInfoService.addMessageInfo(messageInfo);
            }

            return getAjaxResult("0", "", null);

        } catch (Exception e) {
            return getAjaxResult("-1", "处理失败", null);
        }
    }
}
