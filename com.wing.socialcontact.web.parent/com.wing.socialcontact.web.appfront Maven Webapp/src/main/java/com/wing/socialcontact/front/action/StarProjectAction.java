package com.wing.socialcontact.front.action;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import com.tojoycloud.common.report.ResponseCode;
import com.tojoycloud.common.report.base.BaseAppAction;
import com.tojoycloud.common.report.base.CommandInfo;
import com.tojoycloud.common.report.base.UserProperty;
import com.tojoycloud.report.RequestReport;
import com.tojoycloud.report.ResponseReport;
import com.wing.socialcontact.common.model.DataGrid;
import com.wing.socialcontact.common.model.PageParam;
import com.wing.socialcontact.front.util.EsapiTest;
import com.wing.socialcontact.service.wx.api.*;
import com.wing.socialcontact.service.wx.bean.*;
import com.wing.socialcontact.sys.bean.ListValues;
import com.wing.socialcontact.sys.service.IDistrictService;
import com.wing.socialcontact.sys.service.IListValuesService;
import com.wing.socialcontact.util.StringUtil;
import com.wing.socialcontact.util.UUIDGenerator;
import com.wing.socialcontact.utils.CtxHolder;
import com.wing.socialcontact.vhall.api.BaseAPI;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * APP明星项目  接口controller
 */
@Controller
@RequestMapping("/m/starProject")
public class StarProjectAction extends BaseAppAction {

    @Autowired
    private IProjectService projectService;

    @Autowired
    private IAttentionService attentionService;

    @Autowired
    private IListValuesService listValuesService;

    @Autowired
    private IProjectWillService projectWillService;

    @Autowired
    private IUserIntegralLogService userIntegralLogService;

    /**
     * 获取明星项目列表
     *
     * @param requestReport
     * @return
     */
    @RequestMapping("/queryStarProjectList")
    public
    @ResponseBody
    ResponseReport queryStarProjectList(@RequestBody RequestReport requestReport) {

        CommandInfo command = requestReport.getCommandInfo();
        Integer page = command.getData().get("page") != null ? Integer.valueOf(command.getData().get("page").toString()) : 1;
        Integer size = command.getData().get("size") != null ? Integer.valueOf(command.getData().get("size").toString()) : 10;
        UserProperty userProperty = requestReport.getUserProperty();
        String userId = userProperty.getUserId();
        if (StringUtil.isEmpty(userId)) {
            return super.getAjaxResult(requestReport, ResponseCode.NotSupport, "用户id不能为空", null);
        }

        PageParam pageParam = new PageParam();
        pageParam.setPage(page);
        pageParam.setRows(size);
        Project project = new Project();
        project.setShowEnable(1);
        project.setIsApl(1);
        DataGrid dataGrid = this.projectService.selectAllProject2(pageParam, project);
        for(int i=0;i<dataGrid.getRows().size();i++){

            //收藏
            Project projectParam = (Project)dataGrid.getRows().get(i);
            Attention att = new Attention();
            att.setFkId(projectParam.getId());
            Integer attentionCount = attentionService.selectCount(att);
            ((Project) dataGrid.getRows().get(i)).setAttentionCount(attentionCount);

            att.setUserId(userId);
            List<Attention> attlist = attentionService.queryAttention(att);
            int isAttention = 0;
            if(attlist.size()>0&&attlist!=null){
                isAttention = 1;
            }
            ((Project) dataGrid.getRows().get(i)).setIsAttentioned(isAttention);
        }

        Map res = new HashMap();
        res.put("dataGrid", dataGrid);
        return super.getAjaxResult(requestReport, ResponseCode.OK, "获取成功", res);
    }

    /**
     * 获取明星项目详细
     *
     * @param requestReport
     * @return
     */
    @RequestMapping("/queryStarProjectDetail")
    public
    @ResponseBody
    ResponseReport queryStarProjectDetail(@RequestBody RequestReport requestReport) {

        CommandInfo command = requestReport.getCommandInfo();
        String id = command.getData().get("id") != null ? command.getData().get("id").toString() : "";
        UserProperty userProperty = requestReport.getUserProperty();
        String userId = userProperty.getUserId();
        if (StringUtil.isEmpty(id)) {
            return super.getAjaxResult(requestReport, ResponseCode.NotSupport, "id不能为空", null);
        }
        if (StringUtil.isEmpty(userId)) {
            return super.getAjaxResult(requestReport, ResponseCode.NotSupport, "用户id不能为空", null);
        }

        Project p = this.projectService.getProject(id,userId);
        Map res = new HashMap();
        res.put("obj", p);
        return super.getAjaxResult(requestReport, ResponseCode.OK, "获取成功", res);
    }

    /**
     * 意向类型
     *
     * @return
     */
    @RequestMapping("/queryIntentionType")
    public
    @ResponseBody
    ResponseReport queryIntentionType(@RequestBody RequestReport requestReport) {

        //项目类型
        List list = listValuesService.selectListByType(8006004);
        Map res = new HashMap();
        res.put("list", list);
        return super.getAjaxResult(requestReport, ResponseCode.OK, "查询成功", res);
    }

    /**
     * 我有意向
     *
     * @return
     */
    @RequestMapping("/addStarProjectCustomer")
    public
    @ResponseBody
    ResponseReport addStarProjectCustomer(@RequestBody RequestReport requestReport) {
        CommandInfo command = requestReport.getCommandInfo();
        UserProperty userProperty = requestReport.getUserProperty();
        String userId = userProperty.getUserId();
        String projectWillJson = command.getData().get("projectWill") != null ?  command.getData().get("projectWill").toString():"";

        if (StringUtil.isEmpty(projectWillJson)) {
            return super.getAjaxResult(requestReport, ResponseCode.NotSupport, "客户信息不能为空", null);
        }

        ProjectWill projectWill = JSON.parseObject(projectWillJson, ProjectWill.class);

        projectWill.setId(UUIDGenerator.getUUID());
        projectWill.setWillDesc(EsapiTest.stripXSS(projectWill.getWillDesc()));
        projectWill.setUserId(userId);
        projectWill.setCreateTime(new Date());
        projectWill.setDeleted(0);
        projectWillService.insertProjectWill(projectWill);
        //此项每日积分上限为90
        userIntegralLogService.addLntAndEmp(userId, "task_0012");
        return super.getAjaxResult(requestReport, ResponseCode.OK, "添加成功", null);
    }

}
