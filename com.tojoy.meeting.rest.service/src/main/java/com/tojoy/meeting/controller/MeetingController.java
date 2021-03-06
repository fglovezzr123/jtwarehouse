package com.tojoy.meeting.controller;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import com.tojoy.common.model.DataGrid;
import com.tojoy.common.model.PageParam;
import com.tojoy.meeting.common.ConstantDefinition;
import com.tojoy.meeting.common.ConstantService;
import com.tojoy.meeting.common.report.ResponseCode;
import com.tojoy.meeting.common.report.base.BaseController;
import com.tojoy.meeting.common.report.base.CommandInfo;
import com.tojoy.meeting.common.report.base.UserProperty;
import com.tojoy.meeting.model.*;
import com.tojoy.meeting.report.DataObjReport;
import com.tojoy.meeting.report.RequestReport;
import com.tojoy.meeting.report.ResponseReport;
import com.tojoy.service.wx.api.IMeetingService;
import com.tojoy.service.wx.api.IMeetingSignupService;
import com.tojoy.service.wx.bean.Meeting;
import com.tojoy.service.wx.bean.MeetingSignup;
import com.tojoy.util.*;
import com.tojoy.util.DateUtils;
import com.tojoy.util.DoubleUtil;
import com.tojoy.vhall.api.UserAPI;
import com.tojoy.vhall.api.WebinarAPI;
import com.tojoy.vhall.resp.UserIdResp;
import com.tojoy.vhall.resp.UserRegisterResp;
import com.tojoy.vhall.resp.WebinarStateResp;
import com.wing.socialcontact.service.wx.api.*;
import com.wing.socialcontact.service.wx.bean.*;
import com.wing.socialcontact.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/*******************************************************************************************
 * 类描述：会议相关restfull接口（投洽峰会）
 *
 * @author: wangyansheng
 * @date： 2017-10-26
 *******************************************************************************************/

@RestController
@RequestMapping("/meeting")
public class MeetingController  extends BaseController {

    @Autowired
    private ConstantService constantService;

    /**
     * logger
     */
    private final Logger logger = LoggerFactory.getLogger(MeetingController.class);

    /**
     * 会议服务
     */
    @Autowired
    private IMeetingService meetingService;

    /**
     * 会议报名服务
     */
    @Autowired
    private IMeetingSignupService meetingSignupService;

    /**
     * 收藏服务
     */
    @Autowired
    private IAttentionService attentionService;

    /**
     * message服务
     */
    @Autowired
    private IMessageInfoService messageInfoService;

    /**
     * 查询会议列表
     *
     * @return
     */
    @RequestMapping(value = "/queryMeetingList", method = RequestMethod.POST)
    public
    @ResponseBody
    ResponseReport queryMeetingList(@RequestBody RequestReport requestReport) {

        CommandInfo command = requestReport.getCommandInfo();
        Integer page = command.getData().get("page") != null ? Integer.valueOf(command.getData().get("page").toString()) : 1;
        Integer size = command.getData().get("size") != null ? Integer.valueOf(command.getData().get("size").toString()) : 10;
        Long contentVisibleRange = command.getData().get("contentVisibleRange") != null ? Long.valueOf(command.getData().get("contentVisibleRange").toString()) : 10;

        Meeting param = new Meeting();
        param.setShowEnable(1);
        param.setContentVisibleRange(contentVisibleRange);
        param.setInvestmentEnable(null);
        param.setRecommendEnable(null);
        param.setOrderBy("order by  m.sort desc,m.start_time desc");

        DataGrid grid = meetingService.selectAllMeeting(new PageParam(page, size), param);

        MeetingListModel meetingListModel = new MeetingListModel();
        if(grid!=null){
            meetingListModel.setList(grid.getRows());
        }

        DataObjReport dataObjReport = new DataObjReport();
        dataObjReport.setResponseCode(ResponseCode.OK);
        dataObjReport.setResponseTips("查询成功");
        dataObjReport.setDataObj(meetingListModel);
        return dataObjReport;
    }

    /**
     * 查询会议详情
     *
     * @param requestReport 请求参数
     * @return
     */
    @RequestMapping(value = "/getMeetingById", method = RequestMethod.POST)
    public
    @ResponseBody
    ResponseReport getMeetingById(@RequestBody RequestReport requestReport) {

        CommandInfo command = requestReport.getCommandInfo();
        String meetingId = command.getData().get("meetingId").toString();
        UserProperty userProperty = requestReport.getUserProperty();
        String userId = userProperty.getUserId();

        Meeting meeting = meetingService.getMeeting(meetingId);

        //报名用户
        MeetingSignup meetingSignup = new MeetingSignup();
        meetingSignup.setMeetingId(meetingId);
        List<MeetingSignup> meetingSignupList = meetingSignupService.queryMeetingSignup(meetingSignup);
        meeting.setMeetingSignupList(meetingSignupList);

        MeetingModel meetingModel = new MeetingModel();

        //报名状态
        MeetingSignup signUp = meetingSignupService.selectByMeetingIdAndUserId(userId, meetingId);
        meetingModel.setSignupStatus(signUp == null ? 0 : 1);

        meetingModel.setMeeting(meeting);
        return super.getAjaxResult(requestReport, ResponseCode.OK, "查询成功", meetingModel);

    }

    /**
     * 收藏会议
     *
     * @param requestReport 请求参数
     * @return
     */
    @RequestMapping(value = "/saveForAttention", method = RequestMethod.POST)
    public
    @ResponseBody
    ResponseReport saveForAttention(@RequestBody RequestReport requestReport) {

        CommandInfo command = requestReport.getCommandInfo();
        String meetingId = command.getData().get("meetingId").toString();
        UserProperty userProperty = requestReport.getUserProperty();
        String userId = userProperty.getUserId();

        ResponseReport responseReport = new ResponseReport();
        Attention p = attentionService.getAttentionByFkIdAndUserId(userId, meetingId);
        //已关注
        if (p != null) {
            responseReport.setResponseCode(ResponseCode.OK);
            responseReport.setResponseTips("已经关注");
            return responseReport;
        } else {
            Attention t = new Attention();
            t.setAttType("meeting");
            t.setCreateTime(new Date());
            t.setDeleted(0);
            t.setFkId(meetingId);
            t.setUserId(userId);
            Integer iCount = attentionService.insertAttention(t);
            if (iCount > 0) {
                responseReport.setResponseCode(ResponseCode.OK);
                responseReport.setResponseTips("关注成功");
                return responseReport;
            } else {
                responseReport.setResponseCode(ResponseCode.Error);
                responseReport.setResponseTips("关注失败");
                return responseReport;
            }
        }
    }

    /**
     * 查询已报名数量
     *
     * @param requestReport 请求参数
     * @return
     */
    @RequestMapping(value = "/getMeetingSignupCount", method = RequestMethod.POST)
    public
    @ResponseBody
    ResponseReport getMeetingSignupCount(@RequestBody RequestReport requestReport) {

        CommandInfo command = requestReport.getCommandInfo();
        String meetingId = command.getData().get("meetingId").toString();

        Map<String, Object> param = Maps.newHashMap();
        param.put("meetingId", meetingId);
        param.put("orderStatus", 2);
        long lCount = this.meetingSignupService.selectAllMeetingSignup(new PageParam(1, 1), param).getTotal();

        SignUpCountModel signUpCountModel = new SignUpCountModel();
        //报名数量
        signUpCountModel.setSignUpCount(lCount);

        DataObjReport dataObjReport = new DataObjReport();
        dataObjReport.setResponseCode(ResponseCode.OK);
        dataObjReport.setResponseTips("查询成功");
        dataObjReport.setDataObj(signUpCountModel);

        return dataObjReport;
    }

    /**
     * 查询是否是白名单
     *
     * @param requestReport 请求参数
     * @return
     */
    @RequestMapping(value = "/isWhiteList", method = RequestMethod.POST)
    public
    @ResponseBody
    ResponseReport isWhiteList(@RequestBody RequestReport requestReport) {

        CommandInfo command = requestReport.getCommandInfo();
        String meetingId = command.getData().get("meetingId").toString();
        UserProperty userProperty = requestReport.getUserProperty();
        String userId = userProperty.getUserId();

        //判断当前用户是否是白名单用户
        boolean isWhite = meetingService.isWhitelist(userId, meetingId);
        IsWhiteModel isWhiteModel = new IsWhiteModel();
        isWhiteModel.setWhite(isWhite);

        DataObjReport dataObjReport = new DataObjReport();
        dataObjReport.setResponseCode(ResponseCode.OK);
        dataObjReport.setResponseTips("查询成功");
        dataObjReport.setDataObj(isWhiteModel);
        return dataObjReport;

    }

    /**
     * 添加会议报名信息
     *
     * @param requestReport 请求参数
     * @return
     */
    @RequestMapping(value = "/insertMeetingSignUp", method = RequestMethod.POST)
    public
    @ResponseBody
    ResponseReport insertMeetingSignUp(@RequestBody RequestReport requestReport) {

        CommandInfo command = requestReport.getCommandInfo();
        String signUpJson = command.getData().get("signUpJson").toString();
        UserProperty userProperty = requestReport.getUserProperty();
        String userId = userProperty.getUserId();

        MeetingSignup signUp = JSON.parseObject(signUpJson, MeetingSignup.class);
        signUp.setId(UUIDGenerator.getUUID());
        int iCount = meetingSignupService.insertMeetingSignup(signUp);
        if (iCount > 0) {
            //报名成功发送短信、微信
            sendMessage(signUp.getMeetingId(), userId);

            ResponseReport responseReport = new ResponseReport();
            responseReport.setResponseCode(ResponseCode.OK);
            responseReport.setResponseTips("报名成功");
            return responseReport;
        } else {
            ResponseReport responseReport = new ResponseReport();
            responseReport.setResponseCode(ResponseCode.Error);
            responseReport.setResponseTips("报名失败");
            return responseReport;
        }
    }

    /**
     * 微吼第三方用户注册或更新
     *
     * @param requestReport 请求参数
     * @return 微吼用户id
     */
    @RequestMapping(value = "/toVhallUser", method = RequestMethod.POST)
    public
    @ResponseBody
    ResponseReport toVhallUser(@RequestBody RequestReport requestReport) {

        CommandInfo command = requestReport.getCommandInfo();
        String thirdUserId = command.getData().get("thirdUserId").toString();
        String thirdUserName = command.getData().get("thirdUserName").toString();
        String thirdUserHead = command.getData().get("thirdUserHead").toString();

        UserRegisterResp userRegisterResp = null;

        UserIdResp userIdResp = UserAPI.getThirdUserId(thirdUserId);
        if (userIdResp.isSuccess() && userIdResp.getId() != null) {
            userRegisterResp = UserAPI.updateThirdUser(thirdUserId, MD5Util.MD5(thirdUserId + ConstantDefinition.TOJOYVHALL), thirdUserName, thirdUserHead);
        } else {
            userRegisterResp = UserAPI.registerThirdUser(thirdUserId, MD5Util.MD5(thirdUserId + ConstantDefinition.TOJOYVHALL), thirdUserName, thirdUserHead);
        }

        if (userRegisterResp != null && userRegisterResp.isSuccess()) {
            VhallUserModel vhallUserModel = new VhallUserModel();
            vhallUserModel.setUserId(userRegisterResp.getUser_id().toString());
            DataObjReport dataObjReport = new DataObjReport();
            dataObjReport.setDataObj(vhallUserModel);
            dataObjReport.setResponseCode(ResponseCode.OK);
            dataObjReport.setResponseTips("成功");
            return dataObjReport;
        }

        DataObjReport dataObjReport = new DataObjReport();
        dataObjReport.setResponseCode(ResponseCode.Error);
        dataObjReport.setResponseTips("失败");
        return dataObjReport;
    }

    /**
     * 获取微吼会议状态
     *
     * @param requestReport 请求参数
     * @return
     */
    @RequestMapping(value = "/getWebinarStatus", method = RequestMethod.POST)
    public
    @ResponseBody
    ResponseReport getWebinarStatus(@RequestBody RequestReport requestReport) {

        CommandInfo command = requestReport.getCommandInfo();
        String webinarId = command.getData().get("webinarId").toString();

        WebinarStateResp webinarStateResp = WebinarAPI.stateWebinar(webinarId);
        if (webinarStateResp.isSuccess()) {
            String status = webinarStateResp.getState();
            VhallStateModel vhallStateModel = new VhallStateModel();
            vhallStateModel.setState(status);

            DataObjReport dataObjReport = new DataObjReport();
            dataObjReport.setResponseCode(ResponseCode.OK);
            dataObjReport.setResponseTips("查询成功");
            dataObjReport.setDataObj(vhallStateModel);
            return dataObjReport;
        }
        DataObjReport dataObjReport = new DataObjReport();
        dataObjReport.setResponseCode(ResponseCode.Error);
        dataObjReport.setResponseTips("查询失败");
        return dataObjReport;
    }

    /**
     * 报名成功消息发送
     *
     * @param meetingId 会议id
     * @param userId    用户id
     */
    private void sendMessage(String meetingId, String userId) {
        MeetingSignup ms = null;
        try {
            ms = meetingSignupService.selectByMeetingIdAndUserId(userId, meetingId);
            if (ms.getOrderStatus() == null || ms.getOrderStatus().intValue() == 1) {
                return;
            }
        } catch (Throwable e) {
            logger.error("查询用户【" + userId + "】的报名信息失败", e);
        }
        if (ms == null) {
            return;
        }
        //发送报名消息
        try {
            String message = String.format(constantService.MEETING_SIGNUP_MESSAGE,ms.getNickname(), DateUtils.dateToString(ms.getSignupTime(), "yyyy-MM-dd HH:mm"), ms.getTitles(), ms.getMobile());
            String con = WxMsmUtil.getTextMessageContent(message);
            MessageInfo messageInfo = new MessageInfo();
            messageInfo.setId(UUIDGenerator.getUUID());
            messageInfo.setDeleted(0);
            messageInfo.setType(2);  //微信
            messageInfo.setToUserId(userId);
            messageInfo.setCreateTime(new Date());
            messageInfo.setContent(con);
            messageInfo.setTemplateId("ACTIVITY_BM_TX");
            messageInfo.setStatus(0);
            messageInfo.setWxMsgType(1); // ** 微信消息类型（1：文本消息2：图文消息） */
            messageInfoService.addMessageInfo(messageInfo);
        } catch (Throwable e) {
            logger.error("发送报名成功微信消息【userId:" + userId + "】失败", e);
        }
        //发送短信
        try {
            if (ms.getMobile() == null) {
                logger.error("发送报名成功短信【userId:" + userId + "】失败，手机号为空");
                return;
            } else if (ms.getMobile().length() != 11) {
                logger.error("手机号错误");
                logger.error("发送报名成功短信【userId:" + userId + "】失败，手机号错误【mobile:" + ms.getMobile() + "】");
                return;
            }

            Map<String, String> params = Maps.newHashMap();
            params.put("name", ms.getNickname());
            params.put("time", DateUtils.dateToString(ms.getSignupTime(), "yyyy-MM-dd HH:mm"));
            params.put("hyname", ms.getTitles());
            params.put("uname", ms.getNickname());
            params.put("mobilem", ms.getMobile());

            MessageInfo messageInfo = new MessageInfo();
            messageInfo.setId(UUIDGenerator.getUUID());
            messageInfo.setDeleted(0);
            messageInfo.setMobile(ms.getMobile());
            messageInfo.setType(1); // 短信
            messageInfo.setCreateTime(new Date());
            messageInfo.setContent(JSON.toJSONString(params));
            messageInfo.setStatus(0); // 未发送
            messageInfo.setTemplateId(constantService.MEETING_SIGNUP_SUCCESS);
            messageInfoService.addMessageInfo(messageInfo);
        } catch (Throwable e) {
            logger.error("发送报名成功短信消息【userId:" + userId + "】失败", e);
        }
    }

}
