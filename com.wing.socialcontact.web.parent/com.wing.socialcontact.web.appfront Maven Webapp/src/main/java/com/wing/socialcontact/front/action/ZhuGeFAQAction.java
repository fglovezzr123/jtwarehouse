package com.wing.socialcontact.front.action;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.alibaba.dubbo.common.json.JSONArray;
import com.github.pagehelper.util.*;
import com.tojoycloud.common.report.base.CommandInfo;
import com.tojoycloud.common.report.base.UserProperty;
import com.wing.socialcontact.common.model.Member;
import com.wing.socialcontact.config.OssConfig;
import com.wing.socialcontact.service.wx.api.*;
import com.wing.socialcontact.service.wx.bean.*;
import com.wing.socialcontact.util.*;
import com.wing.socialcontact.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tojoycloud.common.report.ResponseCode;
import com.tojoycloud.common.report.base.BaseAppAction;
import com.tojoycloud.report.RequestReport;
import com.tojoycloud.report.ResponseReport;

/**
 * APP智囊团 诸葛解惑  接口controller
 */
@Controller
@RequestMapping("/m/zhuGeFAQ")
public class ZhuGeFAQAction extends BaseAppAction {

    @Autowired
    private IRewardClassService rewardClassService;

    @Autowired
    private IRewardService rewardService;

    @Autowired
    private IWxUserService wxUserService;

    @Autowired
    private IWalletLogService walletLogService;

    @Resource
    protected IUserIntegralLogService userIntegralLogService;

    @Autowired
    private IRewardAnswerService rewardAnswerService;

    @Autowired
    private ICommentThumbupService commentThumbupService;

    @Autowired
    private ICommentService commentService;

    @Autowired
    private IRewardSetService rewardSetService;

    @Autowired
    private ITjyUserService tjyUserService;

    @Autowired
    private IMessageInfoService messageInfoService;


    /**
     * 诸葛解惑：获取行业分类
     *
     * @return
     */
    @RequestMapping("/queryIndustryList")
    public
    @ResponseBody
    ResponseReport queryIndustryList(@RequestBody RequestReport requestReport) {


        List list = rewardClassService.selectAllClass(null, 1);
        Map res = new HashMap();
        res.put("IndustryList", list);
        OssConfig ossConfig = (OssConfig) SpringContextUtil.getBean("ossConfig");
        String ossUrl = ossConfig.getOss_getUrl();
        res.put("ossUrl", ossUrl);
        return super.getAjaxResult(requestReport, ResponseCode.OK, "获取成功", res);
    }


    /**
     * 诸葛解惑：获取解惑列表
     *
     * @param requestReport
     * @return
     */
    @RequestMapping("/queryZhuGeFAQ")
    public
    @ResponseBody
    ResponseReport queryZhuGeFAQ(@RequestBody RequestReport requestReport) {

        CommandInfo command = requestReport.getCommandInfo();
        UserProperty userProperty = requestReport.getUserProperty();

        Integer page = command.getData().get("page") != null ? Integer.valueOf(command.getData().get("page").toString()) : 1;
        Integer size = command.getData().get("size") != null ? Integer.valueOf(command.getData().get("size").toString()) : 10;
        String userId = userProperty.getUserId();
        String type = command.getData().get("type") !=null ? command.getData().get("type").toString():"1";
        String voType = "";
        if(command.getData().get("voType")!=null) {
            voType = command.getData().get("voType").toString();
        }

        List<Map<String, Object>> list = rewardService.selectFrontReward(page, size, null, voType, null, Integer.parseInt(type), null, userId);

        return super.getAjaxResult(requestReport, ResponseCode.OK, "获取成功", list);
    }

    /**
     * 诸葛解惑：获取我参与的或者我发布的解惑列表
     *
     * @return
     */
    @RequestMapping("/queryMyZhuGeFAQ")
    public
    @ResponseBody
    ResponseReport queryMyZhuGeFAQ(@RequestBody RequestReport requestReport) {

        List<Map<String, Object>> list = new ArrayList();
        CommandInfo command = requestReport.getCommandInfo();
        UserProperty userProperty = requestReport.getUserProperty();

        Integer page = command.getData().get("page") != null ? Integer.valueOf(command.getData().get("page").toString()) : 1;
        Integer size = command.getData().get("size") != null ? Integer.valueOf(command.getData().get("size").toString()) : 10;
        String userId = userProperty.getUserId();
        String type = command.getData().get("type").toString();

        if (type.equals("1")) {//我参加的悬赏
            list = rewardService.selectMyReward(userId, page, size);
        } else if (type.equals("2")) {//我的悬赏
            list = rewardService.selectFrontReward(page, size, null, null, userId, 2, null, null);
        }

        Map map = new HashMap();
        map.put("list", list);
        return super.getAjaxResult(requestReport, ResponseCode.OK, "获取成功", map);
    }


    /**
     * 诸葛解惑：发布诸葛解惑
     *
     * @return
     */
    @RequestMapping("/add")
    public
    @ResponseBody
    ResponseReport add(@RequestBody RequestReport requestReport) {

        CommandInfo command = requestReport.getCommandInfo();
        UserProperty userProperty = requestReport.getUserProperty();

        String userId = userProperty.getUserId();
        String question = command.getData().get("question").toString();
        String industryType = command.getData().get("iTypeId").toString();
        String rewardPrice = command.getData().get("rewardPrice").toString();
        String isShow = command.getData().get("isShow").toString();

        WxUser user = wxUserService.selectById(userId);
        if (rewardPrice != null && !"".equals(rewardPrice)) {

            //先减去自己的j币
            Double doub = DoubleUtil.sub(user.getJbAmount() == null ? 0 : user.getJbAmount(), Double.valueOf(rewardPrice));
            if (doub < 0) {
                super.getAjaxResult(requestReport, ResponseCode.NotAvailable, "J币不足，请充值之后再发布悬赏！", null);
            } else {
                user.setJbAmount(doub);
                wxUserService.updateWxUser(user);
                WalletLog log = new WalletLog();
                log.setCreateTime(new Date());
                log.setType("2");
                log.setPdType("2");
                log.setPayStatus("1");
                log.setUserId(userId);
                log.setAmount(Double.valueOf(rewardPrice));
                log.setRemark("诸葛解惑悬赏");
                log.setYeAmount(doub);
                log.setBusinessType(5);
                walletLogService.addWalletLog(log);
            }
        }

        Reward r = new Reward();
        r.setQuestion(question);
        r.setCreateTime(new Date());
        r.setVoType(industryType);
        r.setReward(rewardPrice.equals("") ? 0 : Integer.parseInt(rewardPrice));
        r.setAllowComment(1);
        r.setCreateUserId(userId);
        r.setCreateUserName(user.getUsername());
        r.setStatus(2);
        r.setIsShow(Integer.parseInt(isShow));
        rewardService.addReward(r);
        //此项每日积分上限为30
        userIntegralLogService.addLntAndEmp(userId, "task_0006");

        return super.getAjaxResult(requestReport, ResponseCode.OK, "添加成功", null);
    }


    /**
     * 诸葛解惑：添加解答
     *
     * @return
     */
    @RequestMapping("/addAnswer")
    public
    @ResponseBody
    ResponseReport addFAQAnswer(@RequestBody RequestReport requestReport) {

        CommandInfo command = requestReport.getCommandInfo();
        UserProperty userProperty = requestReport.getUserProperty();
        String userId = userProperty.getUserId();

        String content = command.getData().get("content").toString();
        String fkId = command.getData().get("fkId").toString();

        WxUser user = wxUserService.selectById(userId);
        RewardAnswer ra = new RewardAnswer();
        ra.setCreateTime(new Date());
        ra.setContent(content);
        ra.setFkId(fkId);
        ra.setCreateUserId(userId);
        ra.setCreateUserName(user.getUsername());
        ra.setIsAccept(2);
        ra.setIsShow(1);
        String resultStr = rewardAnswerService.addRA(ra);
        return super.getAjaxResult(requestReport, ResponseCode.OK, "添加成功", null);
    }

    /**
     * 诸葛解惑：解惑详情
     *
     * @return
     */
    @RequestMapping("queryDetail")
    public
    @ResponseBody
    ResponseReport queryZhuGeDetail(@RequestBody RequestReport requestReport) {

        CommandInfo command = requestReport.getCommandInfo();
        UserProperty userProperty = requestReport.getUserProperty();
        String userId = userProperty.getUserId();

        String id = command.getData().get("id").toString();
        Map<String, Object> rd = rewardService.selectRewardById(id);
        rd.put("createTime", rd.get("createTime") == null ? "" : CommUtil.getTimesToNow2(CommUtil
                .formatLongDate(rd.get("createTime"))));
        //查看该悬赏的所有应答
        List<Map<String, Object>> list = rewardAnswerService.selectRAByFkId(id, null);
        int count = 0;
        int subCount = 0;
        for (Map<String, Object> m : list) {
            // 获取点赞数
            CommentThumbup commentThumbup = new CommentThumbup();
            commentThumbup.setPId((String) (m.get("id")));
            count = commentThumbupService.selectcount(commentThumbup);
            m.put("count", count);

            commentThumbup.setUserId(userId);
            List<Map<String, Object>> thumbupList = commentThumbupService
                    .selectAllCommentThumbup(commentThumbup);
            boolean isThumbUp = false;
            if (thumbupList.size() > 0) {
                isThumbUp = true;
            }
            //获取当前用户点赞状态
            m.put("isThumbUp", isThumbUp);
            // 获取子评论数
            Comment subComment = new Comment();
            subComment.setParentId((String) (m.get("id")));
            List<Map<String, Object>> subCommentList = commentService
                    .queryCommentbyPid((String) (m.get("id")));
            if (null != subCommentList) {
                subCount = subCommentList.size();
            }
            m.put("subCount", subCount);
        }

        Map map = new HashMap();
        map.put("answerList", list);
        map.put("reward", rd);
        return super.getAjaxResult(requestReport, ResponseCode.OK, "获取成功", map);
    }

    /**
     * 设置最佳（可以设置多个最佳）
     *
     * @return
     */
    @RequestMapping("setBestAnswer")
    public
    @ResponseBody
    ResponseReport setBestAnswer(@RequestBody RequestReport requestReport) {

        CommandInfo command = requestReport.getCommandInfo();
        UserProperty userProperty = requestReport.getUserProperty();
        String userId = userProperty.getUserId();

        String fqaId = command.getData().get("fqaId").toString();
        List<String> answerIds = Arrays.asList(command.getData().get("answerIds").toString().split(","));
        Reward b = rewardService.selectById(fqaId);
        if (b.getStatus() == 4) {
            return super.getAjaxResult(requestReport, ResponseCode.OK, "已完成悬赏，不能重复采纳应答！", null);
        }
        RewardSet rs = null;
        List rList = rewardSetService.selectRewardSet();
        Double per = 1.0;
        if (rList != null && rList.size() > 0) {
            rs = (RewardSet) rList.get(0);
            per = rs.getChargePer() == null ? 1.0 : (1 - rs.getChargePer() / 100);
        }
        //该提问的被采纳应答
        if (answerIds != null && answerIds.size() > 0) {
            Integer rd = (int) (b.getReward() * per);
            Integer cou = rd / answerIds.size();
            Integer yu = rd % answerIds.size();
            for (int i = 0; i < answerIds.size(); i++) {
                //更新应答状态
                RewardAnswer ra = rewardAnswerService.selectById(answerIds.get(i));
                ra.setIsAccept(1);
                rewardAnswerService.updateRA(ra);
                //给商洽的发布者加上j币
                Integer num = 0;
                if (i == answerIds.size() - 1) {
                    num = cou + yu;
                } else {
                    num = cou;
                }
                WxUser userf = wxUserService.selectById(ra.getCreateUserId());
                userf.setJbAmount(DoubleUtil.add(userf.getJbAmount(), Double.valueOf(num)));
                wxUserService.updateWxUser(userf);
                WalletLog logf = new WalletLog();
                logf.setCreateTime(new Date());
                logf.setType("2");
                logf.setPdType("1");
                logf.setUserId(ra.getCreateUserId());
                logf.setAmount(Double.valueOf(num));
                logf.setRemark("获得诸葛解惑悬赏");
                logf.setPayStatus("1");
                logf.setYeAmount(userf.getJbAmount());
                logf.setBusinessType(12);
                logf.setSourceUser(userId);
                walletLogService.addWalletLog(logf);
                //采纳之后，发了一条信息，包含短信、微信、系统消息
                // 发送短信
                TjyUser tjyUser = tjyUserService.selectByPrimaryKey(userf.getId() + "");
                MessageInfo messageInfo = new MessageInfo();
                messageInfo.setId(UUIDGenerator.getUUID());
                messageInfo.setDeleted(0);
                messageInfo.setMobile(userf.getMobile());
                messageInfo.setType(1);// 短信
                messageInfo.setCreateTime(new Date());
                String name = tjyUser.getNickname();
                String contentdx = "{name:\"" + name + "\",subname:\"" + AldyMessageUtil.SMSPRE + "诸葛解惑内\",type:\"应答已被采纳，好厉害！\",money:\"" + num + "\"}";
                messageInfo.setContent(contentdx);
                messageInfo.setStatus(0);// 未发送
                messageInfo.setTemplateId(AldyMessageUtil.MsmTemplateId.NEGOTIATE_SUCCESS);
                messageInfoService.addMessageInfo(messageInfo);
                // 发送微信
                String touser = userf.getId().toString();
                messageInfo = new MessageInfo();
                messageInfo.setId(UUIDGenerator.getUUID());
                messageInfo.setDeleted(0);
                messageInfo.setType(2);// 微信
                messageInfo.setToUserId(touser);
                messageInfo.setCreateTime(new Date());
                // 组装内容
                String content = AldyMessageUtil.userzgRewardAccept(name, AldyMessageUtil.SMSPRE, num);
                String con = WxMsmUtil.getTextMessageContent(content);
                messageInfo.setContent(con);
                messageInfo.setTemplateId("REWARD_CAINA");
                messageInfo.setStatus(0);// 未发送
                messageInfo.setWxMsgType(1);///** 微信消息类型（1：文本消息2：图文消息） */
                messageInfoService.addMessageInfo(messageInfo);
                /**
                 * 发送系统消息
                 */
                messageInfo = new MessageInfo();
                messageInfo.setId(UUIDGenerator.getUUID());
                messageInfo.setDeleted(0);
                messageInfo.setType(3);// 系统消息
                messageInfo.setToUserId(touser);
                messageInfo.setCreateTime(new Date());
                messageInfo.setContent(content);
                messageInfo.setStatus(0);// 不需要发送
                messageInfoService.addMessageInfo(messageInfo);
            }
            b.setStatus(4);//完成悬赏
            rewardService.updateReward(b);
            return super.getAjaxResult(requestReport, ResponseCode.OK, "设置成功", null);
        } else {
            return super.getAjaxResult(requestReport, ResponseCode.OK, "没有采纳的应答，不能完成悬赏！", null);
        }
    }


}
