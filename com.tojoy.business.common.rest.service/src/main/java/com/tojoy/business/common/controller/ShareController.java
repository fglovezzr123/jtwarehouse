package com.tojoy.business.common.controller;

import com.github.pagehelper.util.StringUtil;
import com.tojoy.business.common.api.IShareService;
import com.tojoy.business.common.bean.Share;
import com.tojoy.business.common.bean.ThumpUp;
import com.tojoy.business.common.common.report.ResponseCode;
import com.tojoy.business.common.common.report.base.BaseController;
import com.tojoy.business.common.common.report.base.CommandInfo;
import com.tojoy.business.common.common.report.base.UserProperty;
import com.tojoy.business.common.report.RequestReport;
import com.tojoy.business.common.report.ResponseReport;
import com.wing.socialcontact.service.wx.api.IWalletLogService;
import com.wing.socialcontact.service.wx.api.IWxUserService;
import com.wing.socialcontact.service.wx.bean.WalletLog;
import com.wing.socialcontact.service.wx.bean.WxUser;
import com.wing.socialcontact.util.DoubleUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;

/**
 * 分享  接口controller
 */
@Controller
@RequestMapping("/share")
public class ShareController extends BaseController {


    @Autowired
    private IShareService shareService;

    /**
     * 分享
     *
     * @param requestReport 请求参数
     * @return
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public
    @ResponseBody
    ResponseReport add(@RequestBody RequestReport requestReport) {

        CommandInfo command = requestReport.getCommandInfo();
        String fkId = command.getData().get("fkId").toString();
        String key = command.getData().get("key").toString();
        UserProperty userProperty = requestReport.getUserProperty();
        String userId = userProperty.getUserId();

        Share share = new Share();
        share.setKeyWord(key);
        share.setUserId(userId);
        share.setFkId(fkId);
        share.setCreateTime(new Date());

        Integer iCount = shareService.insert(share);
        if (iCount > 0) {
            return super.getAjaxResult(requestReport, ResponseCode.OK, "分享成功", null);
        } else {
            return super.getAjaxResult(requestReport, ResponseCode.Error, "分享失败", null);
        }
    }

}
