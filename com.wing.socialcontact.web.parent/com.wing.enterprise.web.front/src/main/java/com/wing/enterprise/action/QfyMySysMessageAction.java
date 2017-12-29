package com.wing.enterprise.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.com.wing.enterprise.bean.MySysMessage;
import org.com.wing.enterprise.service.IMySysMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gexin.rp.sdk.base.uitls.MD5Util;
import com.wing.socialcontact.common.action.BaseAction;
import com.wing.socialcontact.common.model.PageParam;
import com.wing.socialcontact.util.Constants;
import com.wing.socialcontact.util.QfyConstants;

/**
 * 我的系统消息
 * 
 * 
 * @ClassName: QfyMySysMessageAction
 * @Description: TODO
 * @author: sino
 * @date:2017年5月12日
 * 
 */
@Controller
@RequestMapping("/m/qfy/myMsg")
public class QfyMySysMessageAction extends BaseAction {

	@Autowired
	private IMySysMessageService mySysMessageService;

	/**
	 * 未读系统消息
	 * //TODO 添加方法功能描述
	 * @param request
	 * @param response
	 * @param m
	 * @param uid
	 * @return
	 */
	@RequestMapping("selNoMsg")
    public @ResponseBody Map selNoMsg(HttpServletRequest request, HttpServletResponse response,String uid,String key) {
	    String userId = checkLogin(request);
        if(userId == null){
            if(StringUtils.isEmpty(uid) || StringUtils.isEmpty(key)){
                return super.getAjaxResult("302", "登录超时，请重新登录", null);
            }
            if(!MD5Util.getMD5Format(QfyConstants.LOGIN_KEY+uid).equals(key)){
                return super.getAjaxResult("302", "登录超时，请重新登录", null); 
            }
        }
        
        Map res = new HashMap();
        
        MySysMessage msm = new MySysMessage();
        msm.setSsUserId(uid);
        msm.setStatus(0);
        List<Map> lst = mySysMessageService.selectMyMessage(null, msm);
        
        res.put("count", lst.size());
        
        return super.getSuccessAjaxResult(Constants.AJAX_MSG_SUCCESS, res);
    }
	/**
	 * 查询所有消息
	 * //TODO 添加方法功能描述
	 * @param request
	 * @param response
	 * @param m
	 * @param uid
	 * @return
	 */
	@RequestMapping("selMsgs")
	public @ResponseBody Map selMsgs(HttpServletRequest request, HttpServletResponse response,String uid,Integer page,Integer size,String key) {
	   String userId = checkLogin(request);
        if(userId == null){
            if(StringUtils.isEmpty(uid) && StringUtils.isEmpty(key)){
                return super.getAjaxResult("302", "登录超时，请重新登录", null);
            }
            if(!MD5Util.getMD5Format(QfyConstants.LOGIN_KEY+uid).equals(key)){
                return super.getAjaxResult("302", "登录超时，请重新登录", null); 
            }
        }
        
	    if (page==null||page<1) {
            page = 1;
        }
        if (size==null||size<1) {
            size = 10;
        }
	    
        PageParam param = new PageParam();
        param.setRows(size);
        param.setPage(page);
        
        
	    Map res = new HashMap();
	    
	    MySysMessage msm = new MySysMessage();
	    msm.setSsUserId(uid);
	    msm.setStatus(3);
	    
	    List<Map> lst = mySysMessageService.selectMyMessage(param, msm);
	    
	    res.put("lst", lst);
	    
	    return super.getSuccessAjaxResult(Constants.AJAX_MSG_SUCCESS, res);
	}
	
	/**
     * 更改消息状态，变为已读
     * @param request
     * @param response
     * @param uniqueId
     * @return
     */
    @RequestMapping("changStatus")
    public @ResponseBody Map bindwx(HttpServletRequest request, HttpServletResponse response,String uid,String key) {
        String userId = checkLogin(request);
        if(userId == null){
            if(StringUtils.isEmpty(uid) && StringUtils.isEmpty(key)){
                return super.getAjaxResult("302", "登录超时，请重新登录", null);
            }
            if(!MD5Util.getMD5Format(QfyConstants.LOGIN_KEY+uid).equals(key)){
                return super.getAjaxResult("302", "登录超时，请重新登录", null); 
            }
        }
        mySysMessageService.updateMyMsgBatch(uid);
        
        return super.getSuccessAjaxResult();
    }
	
}
