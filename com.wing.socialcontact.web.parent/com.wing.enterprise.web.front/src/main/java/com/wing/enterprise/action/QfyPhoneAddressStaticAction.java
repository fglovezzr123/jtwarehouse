package com.wing.enterprise.action;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.com.wing.enterprise.bean.PhoneAdressStatic;
import org.com.wing.enterprise.service.IPhoneAddressStaticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wing.socialcontact.common.action.BaseAction;
import com.wing.socialcontact.config.MsgConfig;
import com.wing.socialcontact.service.wx.api.ITjyUserService;

/**
 * 通讯录开关统计
 * 
 * 
 * @ClassName: QfyUserAction
 * @Description: TODO
 * @author: sino
 * @date:2017年5月9日
 */
@Controller
@RequestMapping("/m/qfy/")
public class QfyPhoneAddressStaticAction extends BaseAction {

	@Autowired
	private ITjyUserService tjyUserService;
	@Autowired
    private IPhoneAddressStaticService phoneAddressStaticService;

	/**
     * 修改同步消息记录状态
     * @param request
     * @param response
     * @param uniqueId
     * @return
     */
    @RequestMapping("addPaStatic")
    public @ResponseBody Map addPaStatic(HttpSession session,HttpServletRequest request, HttpServletResponse response,String flag,String m,String key) {
       /*String userId = checkLogin(request);
        if(userId == null){
            if(StringUtils.isEmpty(m) && StringUtils.isEmpty(key)){
                return super.getAjaxResult("302", "登录超时，请重新登录", null);
            }
            if(!MD5Util.getMD5Format(QfyConstants.LOGIN_KEY+m).equals(key)){
                return super.getAjaxResult("302", "登录超时，请重新登录", null); 
            }else{
                userId = m; 
             }
        }*/
        
        PhoneAdressStatic pas = new PhoneAdressStatic();
        if(flag != null && "T".equals(flag)){
            pas.settCount(1);
        }else{
            pas.setfCount(1);
        }
        String bo = phoneAddressStaticService.add(pas);
        
        if (!MsgConfig.MSG_KEY_SUCCESS.equals(bo)) {
            return super.getAjaxResult("999", "", null);
        }
        
        return super.getSuccessAjaxResult();
    }
}
