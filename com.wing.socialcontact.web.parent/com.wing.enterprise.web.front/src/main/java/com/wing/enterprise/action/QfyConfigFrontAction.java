package com.wing.enterprise.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.com.wing.enterprise.bean.EntryPhoneAdress;
import org.com.wing.enterprise.bean.QfyConfig;
import org.com.wing.enterprise.service.IEntryPhoneAddressService;
import org.com.wing.enterprise.service.IQfyConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gexin.rp.sdk.base.uitls.MD5Util;
import com.wing.socialcontact.common.action.BaseAction;
import com.wing.socialcontact.service.wx.api.ITjyUserService;
import com.wing.socialcontact.util.Constants;
import com.wing.socialcontact.util.QfyConstants;

/**
 * 用户管理
 * 
 * 
 * @ClassName: QfyUserAction
 * @Description: TODO
 * @author: sino
 * @date:2017年5月9日
 */
@Controller
@RequestMapping("/m/qfy/")
public class QfyConfigFrontAction extends BaseAction {

	@Autowired
	private ITjyUserService tjyUserService;
	@Autowired
    private IQfyConfigService qfyConfigService;
	@Autowired
    private IEntryPhoneAddressService entryPhoneAddressService;

	@RequestMapping("selQfyStatus")
	public @ResponseBody Map selQfyStatus(HttpSession session,HttpServletRequest request, HttpServletResponse response,String key,String m) {

	    String userId = checkLogin(request);
        if(userId == null){
            if(StringUtils.isEmpty(m) && StringUtils.isEmpty(key)){
                return super.getAjaxResult("302", "登录超时，请重新登录", null);
            }
            if(!MD5Util.getMD5Format(QfyConstants.LOGIN_KEY+m).equals(key)){
                return super.getAjaxResult("302", "登录超时，请重新登录", null); 
            }else{
               userId = m; 
            }
        }
	    Map res = new HashMap();
	    QfyConfig qc = qfyConfigService.selConfig("1");
	    if(qc == null){
	        qc.setcStatus(1);
	    }
	    res.put("statusFlag", qc.getcStatus());
	    
	    EntryPhoneAdress entryPhoneAdress = new EntryPhoneAdress();
        entryPhoneAdress.setSsUserId(userId);
        List lst = entryPhoneAddressService.selectPhoneAdress(entryPhoneAdress);
        if(lst != null && lst.size() >0){
            res.put("status", "T");
        }else{
            res.put("status", "F");
        }
        return super.getSuccessAjaxResult(Constants.AJAX_MSG_SUCCESS, res);
	}

}
