package com.wing.enterprise.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.com.wing.enterprise.service.IEntryServiceClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wing.socialcontact.common.action.BaseAction;
import com.wing.socialcontact.common.model.Member;
import com.wing.socialcontact.service.wx.api.IWxUserService;
import com.wing.socialcontact.service.wx.bean.WxUser;
import com.wing.socialcontact.util.Constants;

/**
 * H5首页
 * 
 * 
 * @ClassName: QfyIndexAction
 * @Description: TODO
 * @author: sino
 * @date:2017年5月9日
 */
@Controller
@RequestMapping("/m/qfy/")
public class QyfClassIndexAction extends BaseAction {

	@Autowired
	private IEntryServiceClassService entryServiceClassService;
	
	@RequestMapping("classIndexPage")
    public String homePage(ModelMap map){
        return "classIndex";
    }
	@RequestMapping("classIndex")
	public @ResponseBody Map classIndex(HttpServletRequest request, HttpServletResponse response,String searchName) {
	    String userId = checkLogin(request);
        if(userId == null){
            return super.getAjaxResult("302", "登录超时，请重新登录", null); 
        }
        
        
        Map res = new HashMap();
        
        List<Map> roots = entryServiceClassService.selectByParentKey("one",null,"0");
        List<Map> sencond = null;
        List<Map> rootList = new ArrayList<>();
        if(!CollectionUtils.isEmpty(roots)){
            Map rootMap = null;
            for (Map map : roots) {
                rootMap = new HashMap<>();
                rootMap.put("id", map.get("id"));
                rootMap.put("className", map.get("className"));
                sencond = entryServiceClassService.selectByParentKey((String)map.get("id"),searchName,"0");
                rootMap.put("sencond", sencond);
                rootList.add(rootMap);
            }
            res.put("classes", rootList);
        }
        return super.getSuccessAjaxResult(Constants.AJAX_MSG_SUCCESS, res);
	}

}
