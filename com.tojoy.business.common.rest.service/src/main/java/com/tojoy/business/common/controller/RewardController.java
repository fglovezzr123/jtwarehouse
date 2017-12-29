package com.tojoy.business.common.controller;

import com.github.pagehelper.util.StringUtil;
import com.tojoy.business.common.api.IRewardService;
import com.tojoy.business.common.api.IThumbUpService;
import com.tojoy.business.common.bean.Reward;
import com.tojoy.business.common.bean.ThumpUp;
import com.tojoy.business.common.common.report.ResponseCode;
import com.tojoy.business.common.common.report.base.BaseController;
import com.tojoy.business.common.common.report.base.CommandInfo;
import com.tojoy.business.common.common.report.base.UserProperty;
import com.tojoy.business.common.report.RequestReport;
import com.tojoy.business.common.report.ResponseReport;
import com.wing.socialcontact.service.wx.api.IWalletLogService;
import com.wing.socialcontact.service.wx.api.IWxUserService;
import com.wing.socialcontact.service.wx.bean.TjyLibrary;
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
import java.util.HashMap;
import java.util.Map;

/**
 * 打赏  接口controller
 */
@Controller
@RequestMapping("/reward")
public class RewardController extends BaseController {

    @Autowired
    private IWxUserService wxUserService;

    @Autowired
    private IWalletLogService walletLogService;

    @Autowired
    private IRewardService rewardService;

    /**
     * 打赏J币
     */
    @RequestMapping("rewardJB")
    public
    @ResponseBody
    ResponseReport rewardJB(@RequestBody RequestReport requestReport) {

        CommandInfo command = requestReport.getCommandInfo();
        UserProperty userProperty = requestReport.getUserProperty();
        String key = command.getData().get("key") != null ? command.getData().get("key").toString() : "";
        String fkId = command.getData().get("fkId") != null ? command.getData().get("fkId").toString() : "";
        Double jAmount = command.getData().get("jAmount") != null ? Double.valueOf(command.getData().get("jAmount").toString()) : 0.0;
        Integer businessType = command.getData().get("businessType") != null ? Integer.parseInt(command.getData().get("businessType").toString()) : 0;
        String remark = command.getData().get("remark") != null ? command.getData().get("remark").toString() : "";

        String userId = userProperty.getUserId();

        if (StringUtil.isEmpty(userId)) {
            return super.getAjaxResult(requestReport, ResponseCode.NotSupport, "用户id不能为空", null);
        }
        if (StringUtil.isEmpty(fkId)) {
            return super.getAjaxResult(requestReport, ResponseCode.NotSupport, "fkId不能为空", null);
        }
        if (jAmount==0.0) {
            return super.getAjaxResult(requestReport, ResponseCode.NotSupport, "J币不能为空", null);
        }

        //先减去自己的j币
        WxUser user = wxUserService.selectById(userId);
        Double surplusJb = DoubleUtil.sub(user.getJbAmount() == null ? 0 : user.getJbAmount(), Double.valueOf(jAmount));
        if (surplusJb < 0) {
            return super.getAjaxResult(requestReport, ResponseCode.NotSupport, "J币不足", null);
        } else {
            user.setJbAmount(surplusJb);
            wxUserService.updateWxUser(user);

            //钱包记录
            WalletLog log = new WalletLog();
            log.setCreateTime(new Date());
            log.setType("2");
            log.setPdType("2");
            log.setUserId(userId);
            log.setAmount(Double.valueOf(jAmount));
            log.setRemark(remark);
            log.setPayStatus("1");
            log.setYeAmount(user.getJbAmount());
            log.setBusinessType(businessType);
            log.setSourceUser("1");
            log.setFkId(fkId);
            walletLogService.addWalletLog(log);
        }

        //给默认打赏者加上j币
        //TODO
        WxUser userf = wxUserService.selectById("1");
        userf.setJbAmount(DoubleUtil.add(userf.getJbAmount(), Double.valueOf(jAmount)));
        wxUserService.updateWxUser(userf);
        WalletLog logf = new WalletLog();
        logf.setCreateTime(new Date());
        logf.setType("2");
        logf.setPdType("1");
        logf.setUserId("1");
        logf.setAmount(Double.valueOf(jAmount));
        logf.setRemark("获得打赏");
        logf.setPayStatus("1");
        logf.setYeAmount(userf.getJbAmount());
        logf.setBusinessType(15);
        logf.setSourceUser(userId);
        walletLogService.addWalletLog(logf);

        //打赏记录到打赏业务表
        Reward reward = new Reward();
        reward.setFkId(fkId);
        reward.setKeyWord(key);
        reward.setUserId(userId);
        reward.setJbAmount(jAmount);
        reward.setCreateTime(new Date());
        rewardService.insert(reward);

        return super.getAjaxResult(requestReport, ResponseCode.OK, "打赏成功", null);
    }

}
