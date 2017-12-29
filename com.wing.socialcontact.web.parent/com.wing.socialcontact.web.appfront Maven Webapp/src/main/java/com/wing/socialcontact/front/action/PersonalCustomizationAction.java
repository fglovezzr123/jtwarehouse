package com.wing.socialcontact.front.action;

import com.tojoycloud.common.report.ResponseCode;
import com.tojoycloud.common.report.base.BaseAppAction;
import com.tojoycloud.common.report.base.CommandInfo;
import com.tojoycloud.common.report.base.UserProperty;
import com.tojoycloud.report.RequestReport;
import com.tojoycloud.report.ResponseReport;
import com.wing.socialcontact.common.model.DataGrid;
import com.wing.socialcontact.common.model.PageParam;
import com.wing.socialcontact.config.OssConfig;
import com.wing.socialcontact.service.wx.api.*;
import com.wing.socialcontact.service.wx.bean.*;
import com.wing.socialcontact.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.*;

/**
 * APP智囊团  接口controller
 */
@Controller
@RequestMapping("/m/personalCustomization")
public class PersonalCustomizationAction extends BaseAppAction {

    @Autowired
    private IPersonalCustomizationCustomerService personalCustomizationCustomerService;

    @Autowired
    private IPersonalCustomizationService personalCustomizationService;

    /**
     * 个性定制列表
     *
     * @return
     */
    @RequestMapping("/queryPersonalCustomizations")
    public
    @ResponseBody
    ResponseReport queryPersonalCustomizations(@RequestBody RequestReport requestReport) {

        CommandInfo command = requestReport.getCommandInfo();
        Integer page = command.getData().get("page") != null ? Integer.valueOf(command.getData().get("page").toString()) : 1;
        Integer size = command.getData().get("size") != null ? Integer.valueOf(command.getData().get("size").toString()) : 10;

        PersonalCustomization personalCustomization = new PersonalCustomization();
        PageParam pageParam = new PageParam();
        pageParam.setPage(page);
        pageParam.setRows(size);
        personalCustomization.setIsShow(1);
        personalCustomization.setDeleted(0);
        DataGrid  dataGrid = personalCustomizationService.selectByParam(pageParam, personalCustomization);

        Map res = new HashMap();
        res.put("dataGrid", dataGrid);
        return super.getAjaxResult(requestReport, ResponseCode.OK, "获取成功", res);
    }


    /**
     * 个性定制详情
     *
     * @param requestReport
     * @return
     */
    @RequestMapping("/getPersonalCustomization")
    public
    @ResponseBody
    ResponseReport getPersonalCustomization(@RequestBody RequestReport requestReport) {

        CommandInfo command = requestReport.getCommandInfo();
        String id = command.getData().get("id").toString();
        PersonalCustomization  personalCustomization = personalCustomizationService.selectById(id);

        UserProperty userProperty = requestReport.getUserProperty();
        String userId = userProperty.getUserId();

        if(personalCustomization!=null){
            //收藏
            Attention att = new Attention();
            att.setFkId(id);
//            Integer attentionCount = attentionService.selectCount(att);
            personalCustomization.setAttentionCount(1);

            att.setUserId(userId);
//            List<Attention> attlist = attentionService.queryAttention(att);
            int isAttention = 0;
//            if(attlist.size()>0&&attlist!=null){
//                isAttention = 1;
//            }
            personalCustomization.setIsAttention(isAttention);

            //意向提交次数
            PageParam pageParam = new PageParam();
            PersonalCustomizationCustomer personalCustomizationCustomer = new PersonalCustomizationCustomer();
            personalCustomizationCustomer.setFkId(id);
            personalCustomizationCustomer.setUserId(userId);
            personalCustomizationCustomer.setDeleted(0);
            DataGrid dataGrid =  personalCustomizationCustomerService.selectByParam(pageParam,personalCustomizationCustomer);
            if(dataGrid!=null&&dataGrid.getRows()!=null){
                personalCustomization.setSubmitCount(dataGrid.getRows().size());
            }
        }

        Map res = new HashMap();
        res.put("personalCustomization", personalCustomization);
        return super.getAjaxResult(requestReport, ResponseCode.OK, "获取成功", res);
    }

    /**
     * 我有意向
     *
     * @return
     */
    @RequestMapping("/addIntentionalCustomer")
    public
    @ResponseBody
    ResponseReport addIntentionalCustomer(@RequestBody RequestReport requestReport) {

        CommandInfo command = requestReport.getCommandInfo();
        UserProperty userProperty = requestReport.getUserProperty();

        String pcId = command.getData().get("pcId").toString();
        String userId = userProperty.getUserId();

        PersonalCustomizationCustomer personalCustomizationCustomer = new PersonalCustomizationCustomer();
        personalCustomizationCustomer.setFkId(pcId);
        personalCustomizationCustomer.setUserId(userId);
        personalCustomizationCustomer.setIsHandle(0);
        personalCustomizationCustomer.setDeleted(0);
        personalCustomizationCustomer.setCreateTime(new Date());
        personalCustomizationCustomer.setCreateUserId(userId);
        Integer iCount =  personalCustomizationCustomerService.insert(personalCustomizationCustomer);
        if(iCount>0) {
            return super.getAjaxResult(requestReport, ResponseCode.OK, "添加成功", null);
        }else{
            return super.getAjaxResult(requestReport, ResponseCode.Error, "添加失败", null);
        }
    }


}
