package com.wing.enterprise.action;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.com.wing.enterprise.bean.EntryDescConfig;
import org.com.wing.enterprise.service.IEntryDescConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wing.socialcontact.common.action.BaseAction;
import com.wing.socialcontact.config.OssConfig;
import com.wing.socialcontact.util.Constants;
import com.wing.socialcontact.util.QfyConstants;


/**
 * 启动页、常用问题、关于企服联盟、首页企服联盟介绍相关接口
 * 
 * 
 * @ClassName: EntryDescConfigAction
 * @Description: TODO
 * @author: sino
 * @date:2017年5月7日
 */
@Controller
@RequestMapping("/m/descConfig")
public class EntryDescConfigAction extends BaseAction{

    @Autowired
    private IEntryDescConfigService entryDescConfigService;
    @Autowired
    private OssConfig ossConfig;
    
    
    @RequestMapping("selAppStartInfo")
    public @ResponseBody Map selActivityList(HttpServletRequest request,HttpServletResponse response){
        
        EntryDescConfig edc = new EntryDescConfig();
        edc.setType(QfyConstants.QFY_APP_START_TYPE);
        List res = entryDescConfigService.selectDescConfig(edc);
        if(null != res){
            Map map = (Map) res.get(0);
            String imgPath = (String) map.get("imgPath");
            //OssConfig ossConfig = (OssConfig) SpringContextUtil.getBean("ossConfig");
            String ossurl = ossConfig.getOss_getUrl();
            map.put("imgPath",ossurl+imgPath);
        }
        return super.getSuccessAjaxResult(Constants.AJAX_MSG_SUCCESS, res);
    }
}
