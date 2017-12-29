package com.wing.enterprise.action;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gexin.rp.sdk.base.uitls.MD5Util;
import com.wing.socialcontact.common.action.BaseAction;
import com.wing.socialcontact.gexin.GeTuiModel;
import com.wing.socialcontact.gexin.GetuiAndroidPushUtil;
import com.wing.socialcontact.gexin.GetuiIosPushUtil;
import com.wing.socialcontact.service.wx.api.IMessageInfoService;
import com.wing.socialcontact.service.wx.api.ITjyUserService;
import com.wing.socialcontact.service.wx.api.IWxUserService;
import com.wing.socialcontact.service.wx.bean.TjyUser;
import com.wing.socialcontact.service.wx.bean.WxUser;
import com.wing.socialcontact.util.Constants;
import com.wing.socialcontact.util.MsmValidateBean;
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
@RequestMapping("/m/qfy/user")
public class QfyUserAction extends BaseAction {

	@Autowired
	private ITjyUserService tjyUserService;
	@Autowired
	private IWxUserService wxUserService;
	@Autowired
	private IMessageInfoService messageInfoService;

	@RequestMapping("selAllUserForApp")
	public @ResponseBody Map selAllUserForApp(HttpServletRequest request, HttpServletResponse response,String uids) {
	    if(StringUtils.isEmpty(uids))
	        return super.getSuccessAjaxResult();
	    else
	        return super.getSuccessAjaxResult(Constants.AJAX_MSG_SUCCESS, tjyUserService.selAllUserForApp(uids));
	}

	/**
	 * 已经登录的情况，绑定新的微信号
	 * @param request
	 * @param response
	 * @param uniqueId
	 * @return
	 */
	@RequestMapping("bindwx")
	public @ResponseBody Map bindwx(HttpServletRequest request, HttpServletResponse response,String uniqueId,String uid,String key) {
	   String userId = checkLogin(request);
        if(userId == null){
            if(StringUtils.isEmpty(uid) && StringUtils.isEmpty(key)){
                return super.getAjaxResult("302", "登录超时，请重新登录", null);
            }
            if(!MD5Util.getMD5Format(QfyConstants.LOGIN_KEY+uid).equals(key)){
                return super.getAjaxResult("302", "登录超时，请重新登录", null); 
            }else{
                userId = uid;
            }
        }
	    
        if (null == uniqueId || "".equals(uniqueId)) {
            return super.getAjaxResult("401", "参数错误", null);
        }
        
    	//如果此微信的标示绑定过其他账户，先解绑。
        TjyUser u = tjyUserService.selectByWxUniqueId(uniqueId);
        if(u!=null){
        	u.setWxUniqueId("");
    		tjyUserService.updateTjyUser(u);
        }
        
        //绑定微信号
		TjyUser user = tjyUserService.selectById(userId);
		user.setWxUniqueId(uniqueId);
		tjyUserService.updateTjyUser(user);
		try {
			tjyUserService.remotingUpdateTjyUser(user, user.getMobile());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return super.getSuccessAjaxResult();
    }

	/**
	 * 修改用户手机号
	 * @param request
	 * @param response
	 * @param uniqueId
	 * @return
	 */
	@RequestMapping("mmobile")
	public @ResponseBody Map mmobile(HttpSession session,HttpServletRequest request, HttpServletResponse response,String mmobile,String vcode) {
	    String userId = checkLogin(request);
        if(userId == null){
            return super.getAjaxResult("302", "登录超时，请重新登录", null); 
        }
        
        if (null == mmobile || "".equals(mmobile) || null == vcode || "".equals(vcode)) {
            return super.getAjaxResult("401", "参数错误", null);
        }
		boolean bo = MsmValidateBean.validateCode(mmobile, vcode,session);
		if (!bo) {
			return super.getAjaxResult("999", "手机验证码错误或已失效", null);
		}
		// 清除手机验证码
		session.removeAttribute("mvb");
		WxUser wxUser = wxUserService.selectByMobile(mmobile);
		if (null == wxUser) {
			//修改用户手机号
			wxUserService.updateMobile(Long.valueOf(userId),mmobile);
		}else{
			return super.getAjaxResult("999", "该手机号已被绑定，请重新输入", null);
		}
    	
        return super.getSuccessAjaxResult();
    }
	/**
	 * 校验手机号是否存在 
	 * //TODO 添加方法功能描述
	 * @param session
	 * @param request
	 * @param response
	 * @param mmobile
	 * @param vcode
	 * @return
	 */
	@RequestMapping("validPhone")
	public @ResponseBody Map mmobile(HttpSession session,HttpServletRequest request, HttpServletResponse response,String phone) {
	    String userId = checkLogin(request);
	    if(userId == null){
	        return super.getAjaxResult("302", "登录超时，请重新登录", null); 
	    }
	    
	    if (null == phone || "".equals(phone)) {
	        return super.getAjaxResult("401", "参数错误", null);
	    }
	    WxUser wxUser = wxUserService.selectByMobile(phone);
	    if (null == wxUser) {
	        return super.getSuccessAjaxResult("操作成功", "n");
	    }else{
	        return super.getSuccessAjaxResult("操作成功", "h");
	        //return super.getAjaxResult("999", "该手机号已被绑定，请重新输入", null);
	    }
	    
	    //return super.getSuccessAjaxResult();
	}
	/**
	 * 修改同步消息记录状态
	 * @param request
	 * @param response
	 * @param uniqueId
	 * @return
	 */
	@RequestMapping("updateSynStatus")
	public @ResponseBody Map updateSynStatus(HttpSession session,HttpServletRequest request, HttpServletResponse response,int flag,String m,String key) {
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
	    
	    TjyUser tjyUser = tjyUserService.selectById(userId);
	    tjyUser.setAppSynMsgTime(new Date());
	    tjyUser.setAppSynMsgToTjy(flag);
	    
        boolean bo = tjyUserService.updateTjyUser(tjyUser);
	    if (!bo) {
	        return super.getAjaxResult("999", "同步消息记录失败!", null);
	    }
	    
	    return super.getSuccessAjaxResult();
	}
	/**
	 * 读取同步消息记录状态
	 * @param request
	 * @param response
	 * @param uniqueId
	 * @return
	 */
	public static void main(String[] args) {
		System.out.println(MD5Util.getMD5Format(QfyConstants.LOGIN_KEY+"6296"));
	}
	@RequestMapping("selSynStatus")
	public @ResponseBody Map selSynStatus(HttpSession session,HttpServletRequest request, HttpServletResponse response,String key,String m) {

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
	    TjyUser tjyUser = tjyUserService.selectById(userId);
	    Integer synStatus = tjyUser.getAppSynMsgToTjy();
	    
	    Map res = new HashMap();
	    res.put("synStatus", synStatus);
        res.put("reconFlag", tjyUser.getIsRealname());
        return super.getSuccessAjaxResult(Constants.AJAX_MSG_SUCCESS, res);
	}
	@RequestMapping("selDndInfoForPush")
	public @ResponseBody  Map selDndInfoForPush(HttpSession session,HttpServletRequest request, HttpServletResponse response,String key,String m,String fromUserId,String toUserId) {
	    
	   /* String userId = checkLogin(request);
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
	    
        List<Map> lstDnd = messageInfoService.selOneToOneDnd(fromUserId, toUserId);
        
        boolean push = true;
        
        // 如果没有点对点免打扰
        if (CollectionUtils.isEmpty(lstDnd)) {
            Map map = messageInfoService.selToken(toUserId);
            String dnd = (String) map.get("dnd");
            // 消息通知全局配置 F：开启，T：关闭
            if ("F".equals(dnd)) {
            }else{
                push = false;
            }
        }else{
            push = false;
        }
	    
	    
	    Map res = new HashMap();
	    res.put("push", push);
	    return super.getSuccessAjaxResult(Constants.AJAX_MSG_SUCCESS, res);
	}

}
