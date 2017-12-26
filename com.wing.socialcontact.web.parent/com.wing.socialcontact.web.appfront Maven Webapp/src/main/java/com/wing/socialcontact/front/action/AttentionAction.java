package com.wing.socialcontact.front.action;

import com.tojoycloud.common.report.ResponseCode;
import com.tojoycloud.common.report.base.BaseAppAction;
import com.tojoycloud.common.report.base.CommandInfo;
import com.tojoycloud.common.report.base.UserProperty;
import com.tojoycloud.report.RequestReport;
import com.tojoycloud.report.ResponseReport;
import com.wing.socialcontact.config.OssConfig;
import com.wing.socialcontact.front.util.EsapiTest;
import com.wing.socialcontact.service.wx.api.*;
import com.wing.socialcontact.service.wx.bean.*;
import com.wing.socialcontact.util.SpringContextUtil;
import com.wing.socialcontact.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 收藏  接口controller
 */
@Controller
@RequestMapping("/m/attention")
public class AttentionAction extends BaseAppAction {

    /**
     * 收藏服务
     */
    @Autowired
    private IAttentionService attentionService;

    @Resource
    protected IUserIntegralLogService userIntegralLogService;

    /**
     * 收藏
     *
     * @param requestReport 请求参数
     * @return
     */
    @RequestMapping(value = "/addAttention", method = RequestMethod.POST)
    public
    @ResponseBody
    ResponseReport addAttention(@RequestBody RequestReport requestReport) {

        CommandInfo command = requestReport.getCommandInfo();
        String fkId = command.getData().get("fkId").toString();
        String attentionType = command.getData().get("attentionType").toString();

        UserProperty userProperty = requestReport.getUserProperty();
        String userId = userProperty.getUserId();

        ResponseReport responseReport = new ResponseReport();
        Attention p = attentionService.getAttentionByFkIdAndUserId(userId, fkId);
        //已关注
        if (p != null) {
            return super.getAjaxResult(requestReport, ResponseCode.OK, "已经关注", null);
        } else {
            Attention t = new Attention();
            t.setAttType(attentionType);
            t.setCreateTime(new Date());
            t.setDeleted(0);
            t.setFkId(fkId);
            t.setUserId(userId);
            Integer iCount = attentionService.insertAttention(t);
            if (iCount > 0) {
                return super.getAjaxResult(requestReport, ResponseCode.OK, "关注成功", null);
            } else {
                return super.getAjaxResult(requestReport, ResponseCode.Error, "关注失败", null);
            }
        }
    }

    /**
     * 取消收藏
     *
     * @param requestReport 请求参数
     * @return
     */
    @RequestMapping(value = "/cancelAttention", method = RequestMethod.POST)
    public
    @ResponseBody
    ResponseReport cancelAttention(@RequestBody RequestReport requestReport) {

        CommandInfo command = requestReport.getCommandInfo();
        String fkId = command.getData().get("fkId").toString();

        UserProperty userProperty = requestReport.getUserProperty();
        String userId = userProperty.getUserId();

        ResponseReport responseReport = new ResponseReport();
        Attention p = attentionService.getAttentionByFkIdAndUserId(userId, fkId);
        //已关注
        if (p != null) {
            int iCount = attentionService.deleteAttention(p);
            if (iCount > 0) {
                return super.getAjaxResult(requestReport, ResponseCode.OK, "取消关注成功", null);
            } else {
                return super.getAjaxResult(requestReport, ResponseCode.Error, "取消关注失败", null);
            }
        } else {
            return super.getAjaxResult(requestReport, ResponseCode.OK, "已经取消关注", null);
        }
    }
}
