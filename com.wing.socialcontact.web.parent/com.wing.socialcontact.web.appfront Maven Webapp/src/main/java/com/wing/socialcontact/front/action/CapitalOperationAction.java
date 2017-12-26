package com.wing.socialcontact.front.action;

import com.alibaba.fastjson.JSON;
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
import com.wing.socialcontact.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.*;

/**
 * APP资本运作  接口controller
 */
@Controller
@RequestMapping("/m/capitalOperation")
public class CapitalOperationAction extends BaseAppAction {

    @Autowired
    private IInvestmentService investmentService;

    @Autowired
    private IInvestmentIntentionService investmentIntentionService;

    /**
     * 查询资本运作
     *
     * @return
     */
    @RequestMapping("/queryCapitalOperation")
    public
    @ResponseBody
    ResponseReport queryCapitalOperation(@RequestBody RequestReport requestReport) {

        CommandInfo command = requestReport.getCommandInfo();
        String type = command.getData().get("type").toString();

        String classId = "";
        if("1".equals(type)){
            classId = "上市并购";
        } else if("2".equals(type)){
            classId = "上市孵化";
        }
        List<Map> list =  investmentService.getinvestmentList(classId);
        Map res = new HashMap();
        if(list.size()>0){
            res.put("capitalOperation", list.get(0));
        }
        return super.getAjaxResult(requestReport, ResponseCode.OK, "获取成功", res);
    }

    /**
     * 添加投资意向客户
     *
     * @return
     */
    @RequestMapping("/addCapitalOperationCustomer")
    public
    @ResponseBody
    ResponseReport addCapitalOperationCustomer(@RequestBody RequestReport requestReport) {

        CommandInfo command = requestReport.getCommandInfo();
        UserProperty userProperty = requestReport.getUserProperty();
        String userId = userProperty.getUserId();
        String capitalOperationCustomer = command.getData().get("capitalOperationCustomer").toString();
        InvestmentIntention investmentIntention = JSON.parseObject(capitalOperationCustomer, InvestmentIntention.class);

        investmentIntention.setCreateUserId(userId);
        investmentIntention.setCreateTime(new Date());
        investmentIntention.setDeleted(0);
        investmentIntention.setStatus(1);
        boolean res = investmentIntentionService.addInvestment(investmentIntention);
        if (res) {
            return super.getAjaxResult(requestReport, ResponseCode.OK, "添加成功", null);
        }
        return super.getAjaxResult(requestReport, ResponseCode.Error, "添加失败", null);
    }

    /**
     * 我提交过的意向
     *
     * @return
     */
    @RequestMapping("/queryCapitalOperationCustomer")
    public
    @ResponseBody
    ResponseReport queryCapitalOperationCustomer(@RequestBody RequestReport requestReport) {

        CommandInfo command = requestReport.getCommandInfo();
        UserProperty userProperty = requestReport.getUserProperty();
        String userId = userProperty.getUserId();
        Integer page = command.getData().get("page") != null ? Integer.valueOf(command.getData().get("page").toString()) : 1;
        Integer size = command.getData().get("size") != null ? Integer.valueOf(command.getData().get("size").toString()) : 10;
        List<Map> list =  investmentIntentionService.getinvestmentList(userId,page,size);
        return super.getAjaxResult(requestReport, ResponseCode.OK, "查询成功", list);
    }

}
