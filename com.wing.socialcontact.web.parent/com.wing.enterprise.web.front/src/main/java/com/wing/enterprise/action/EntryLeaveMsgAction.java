package com.wing.enterprise.action;

import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wing.socialcontact.common.action.BaseAction;
import com.wing.socialcontact.service.wx.api.ILeaveMsgService;
import com.wing.socialcontact.service.wx.api.IWxUserService;
import com.wing.socialcontact.service.wx.bean.LeaveMsg;

/**
 * 客户留言
 * 
 * 
 * @ClassName: EntryLeaveMsgAction
 * @Description: TODO
 * @author: sino
 * @date:2017年5月8日
 */

@Controller
@RequestMapping("/m/app")
public class EntryLeaveMsgAction extends BaseAction{

    @Autowired
    private ILeaveMsgService leaveMsgService;
    @Autowired
    private IWxUserService wxUserService;
    
    /**
     * 客服留言
     * 
     * @param content
     * @param type
     * @param request
     * @return
     */
    @RequestMapping("leave_msg")
    public @ResponseBody Map leave_msg(LeaveMsg leaveMsg, HttpServletRequest request) {
        if (StringUtils.isEmpty(leaveMsg.getContent()) || StringUtils.isEmpty(leaveMsg.getType())
                || StringUtils.isEmpty(leaveMsg.getSource())) {
            return super.getAjaxResult("401", "参数错误", null);
        }

        String userId = checkLogin(request);
        if(userId == null){
            return super.getAjaxResult("302", "登录超时，请重新登录", null); 
        }
        
        leaveMsg.setCreateTime(new Date());
        leaveMsg.setDeleted(0);
        leaveMsg.setUserId(userId);
        boolean bo = leaveMsgService.addLeaveMsg(leaveMsg);
        if (bo) {
            return super.getSuccessAjaxResult();
        } else {
            return super.getAjaxResult("999", "留言失败", null);
        }
    }
}
