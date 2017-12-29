package com.tojoy.business.common.controller;

import com.github.pagehelper.util.StringUtil;
import com.tojoy.business.common.api.ICollectionService;
import com.tojoy.business.common.api.ISwitchCnfService;
import com.tojoy.business.common.bean.Collection;
import com.tojoy.business.common.bean.SwitchCnf;
import com.tojoy.business.common.common.report.ResponseCode;
import com.tojoy.business.common.common.report.base.BaseController;
import com.tojoy.business.common.common.report.base.CommandInfo;
import com.tojoy.business.common.common.report.base.UserProperty;
import com.tojoy.business.common.report.RequestReport;
import com.tojoy.business.common.report.ResponseReport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 评论、点赞等开关接口  接口controller
 */
@Controller
@RequestMapping("/switchCnf")
public class SwitchCnfController extends BaseController {

    @Autowired
    private ISwitchCnfService switchCnfService;


    @RequestMapping(value = "/querySwitchCnfByKey", method = RequestMethod.POST)
    public
    @ResponseBody
    ResponseReport querySwitchCnfByKey(@RequestBody RequestReport requestReport) {

        CommandInfo command = requestReport.getCommandInfo();
        String key = command.getData().get("key").toString();
        if (StringUtil.isEmpty(key)) {
            return super.getAjaxResult(requestReport, ResponseCode.NotSupport, "key不能为空", null);
        }

        SwitchCnf switchCnf = new SwitchCnf();
        switchCnf.setKeyWord(key);
        switchCnf = switchCnfService.queryByKeyWord(switchCnf);
        Map map = new HashMap();
        if(switchCnf!=null){
            map.put("cnfValue",switchCnf.getCnfValue());
        }
        return super.getAjaxResult(requestReport, ResponseCode.OK, "查询成功", map);
    }

}
