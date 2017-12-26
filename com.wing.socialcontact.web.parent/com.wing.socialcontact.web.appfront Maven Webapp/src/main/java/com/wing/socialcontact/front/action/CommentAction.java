package com.wing.socialcontact.front.action;

import com.tojoycloud.common.report.ResponseCode;
import com.tojoycloud.common.report.base.BaseAppAction;
import com.tojoycloud.common.report.base.CommandInfo;
import com.tojoycloud.common.report.base.UserProperty;
import com.tojoycloud.report.RequestReport;
import com.tojoycloud.report.ResponseReport;
import com.wing.socialcontact.common.model.Member;
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
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.util.*;

/**
 * 评论  接口controller
 */
@Controller
@RequestMapping("/m/comment")
public class CommentAction extends BaseAppAction {

    @Autowired
    private IWxUserService wxUserService;
    @Resource
    protected IUserIntegralLogService userIntegralLogService;
    @Autowired
    private IRewardAnswerService rewardAnswerService;
    @Autowired
    private ICommentThumbupService commentThumbupService;
    @Autowired
    private ICommentService commentService;
    @Autowired
    private IBusinessDisscussService businessDisscussService;

    /**
     * 查看子评论
     *
     * @return
     */
    @RequestMapping("/queryCommentByPId")
    public
    @ResponseBody
    ResponseReport queryCommentByPId(@RequestBody RequestReport requestReport) {

        CommandInfo command = requestReport.getCommandInfo();
        UserProperty userProperty = requestReport.getUserProperty();
        String userPropertyUserId = userProperty.getUserId();

        String parentId = command.getData().get("parentId") != null ? command.getData().get("parentId").toString() : "";// 评论上级id
        //1:资讯   2：合作 3：话题  4：活动 5：动态  6悬赏 7文章
        String type = command.getData().get("type") != null ? command.getData().get("type").toString() : "";
        if (StringUtil.isEmpty(parentId)) {
            return super.getAjaxResult(requestReport, ResponseCode.NotSupport, "父id不能为空", null);
        }

        CommentThumbup commentThumbup = new CommentThumbup();

        if (StringUtil.isEmpty(parentId)) {
            return null;
        } else {
            commentThumbup.setPId(parentId);
        }

        Map<String, Object> user = new HashMap<String, Object>();
        Comment comment = new Comment();

        if(type.equals("2")){//type 是2为 合作洽谈回复
            BusinessDisscuss b = businessDisscussService.selectById(parentId);
            if (null != b) {
                String userId = b.getCreateUserId();
                comment.setId(b.getId());
                comment.setCreateTime(b.getCreateTime());
                comment.setCmeDesc(b.getContent());
                comment.setCmeType("2");
                if (!com.github.pagehelper.util.StringUtil.isEmpty(userId)) {
                    // 获取用户
                    user = wxUserService.queryUsersByid(userId);
                }
            }
        }else if(type.equals("6")){//type 是6为 悬赏应答
            RewardAnswer ra = rewardAnswerService.selectById(parentId);
            if (null != ra) {
                String userId = ra.getCreateUserId();
                comment.setId(ra.getId());
                comment.setCreateTime(ra.getCreateTime());
                comment.setCmeDesc(ra.getContent());
                comment.setCmeType("6");
                if (!com.github.pagehelper.util.StringUtil.isEmpty(userId)) {
                    // 获取用户
                    user = wxUserService.queryUsersByid(userId);
                }
            }

        }else{
            comment = commentService.selectById(parentId);
            if (null != comment) {
                String userId = comment.getUserId();
                if (!StringUtil.isEmpty(userId)) {
                    // 获取用户
                    user = wxUserService.queryUsersByid(userId);
                }
            }
        }

        Map map = new HashMap();
        map.put("user", user);

        //点赞数量
        int ThumbUpCount = commentThumbupService.selectcount(commentThumbup);
        comment.setThumbUpCount(ThumbUpCount);

        //获取当前用户点赞状态
        commentThumbup.setUserId(userPropertyUserId);
        List<Map<String, Object>> thumbUpList = commentThumbupService
                .selectAllCommentThumbup(commentThumbup);

        if (thumbUpList.size() > 0) {
            comment.setIsThumbUp(1);
        }else{
            comment.setIsThumbUp(0);
        }
        map.put("comment", comment);

        List<Map<String, Object>> subCommentList = commentService
                .queryCommentbyPid(parentId);
        for (Map<String, Object> m : subCommentList) {
            if (StringUtil.isEmpty((String) (m.get("userId")))) {
                m.put("formname", "");
            } else {
                // 获取用户
                Map<String, Object> subuser = wxUserService
                        .queryUsersByid((String) (m.get("userId")));
                m.put("formname", (String) (subuser.get("nickname")));
                m.put("head_portrait", (String) (subuser.get("head_portrait")));
            }
        }
        map.put("subCommentList", subCommentList);
        return super.getAjaxResult(requestReport, ResponseCode.OK, "查询成功", map);
    }

    /**
     * 查看当前主题所有评论
     *
     * @return
     */
    @RequestMapping("queryCommentsPage")
    public @ResponseBody
    ResponseReport queryCommentsPage(@RequestBody RequestReport requestReport)
            throws IOException {

        CommandInfo command = requestReport.getCommandInfo();
        UserProperty userProperty = requestReport.getUserProperty();
        String fkId = command.getData().get("fkId").toString();
        //1:资讯   2：合作 3：话题  4：活动 5：动态  6悬赏 7文章
        String cmeType = command.getData().get("cmeType").toString();
        String userId = userProperty.getUserId();
        Integer pageNum = command.getData().get("page") != null ? Integer.valueOf(command.getData().get("page").toString()) : 1;
        Integer pageSize = command.getData().get("size") != null ? Integer.valueOf(command.getData().get("size").toString()) : 10;
        Comment comment = new Comment();

        if (StringUtil.isEmpty(userId)) {
            return super.getAjaxResult(requestReport, ResponseCode.NotSupport, "用户id不能为空", null);
        }
        if (StringUtil.isEmpty(cmeType) || StringUtil.isEmpty(fkId)) {
            return super.getAjaxResult(requestReport, ResponseCode.NotSupport, "参数错误", null);
        } else {
            comment.setFkId(fkId);
            comment.setCmeType(cmeType);
        }

        List<Map<String, Object>> commentList = commentService
                .selectAllComment(comment, pageNum, pageSize);
        int thumbUpCount = 0;
        int subCommentCount = 0;
        for (Map<String, Object> m : commentList) {
            // 获取点赞数
            CommentThumbup commentThumbup = new CommentThumbup();
            commentThumbup.setPId((String) (m.get("id")));
            thumbUpCount = commentThumbupService.selectcount(commentThumbup);
            m.put("thumbUpCount", thumbUpCount);
            commentThumbup.setUserId(userId);

            List<Map<String, Object>> thumbupList = commentThumbupService
                    .selectAllCommentThumbup(commentThumbup);
            boolean isThumbUp = false;
            if (thumbupList.size() > 0) {
                isThumbUp =true;
            }
            //获取当前用户点赞状态
            m.put("isThumbUp", isThumbUp);
            // 获取第一条子评论
            Map firstSubComment = null;

            List<Map<String, Object>> subCommentList = commentService
                    .queryCommentbyPid((String) (m.get("id")));

            if(subCommentList != null && subCommentList.size() != 0){
                firstSubComment = (Map<String, Object>)subCommentList.get(0);
                m.put("firstSubComment",firstSubComment);
            }

            if (null != subCommentList) {
                subCommentCount = subCommentList.size();
            }
            m.put("subCommentCount", subCommentCount);

            // 获取用户
            Map<String, Object> user = wxUserService.queryUsersByid((String) (m
                    .get("userId")));
            if (null != user) {
                m.put("imgUrl", (String) (user.get("head_portrait")));
                m.put("honorTitle", (String) (user.get("honor_title")));
                m.put("honorFlag", (String) (user.get("honor_flag")));
                m.put("nickName", (String) (user.get("nickname")));
                if(null!=user.get("jobName")){
                    m.put("jobName", (String) (user.get("jobName")));
                }else{
                    m.put("jobName", "");
                }
                if(null!=user.get("industryName")){
                    m.put("industryName", (String) (user.get("industryName")));
                }else{
                    m.put("industryName", "");
                }
            } else {
                m.put("imgUrl", "");
                m.put("nickName", "");
                m.put("jobName", "");
                m.put("industryName", "");
            }

            m.put("createTime", CommUtil.formatLongDate2(m.get("createTime")));
        }
        return super.getAjaxResult(requestReport, ResponseCode.OK, "查询成功", commentList);
    }

    /**
     * 评论点赞
     *
     * @return
     */
    @RequestMapping("/ThumbUp")
    public @ResponseBody
    ResponseReport ThumbUp(@RequestBody RequestReport requestReport) {

        CommandInfo command = requestReport.getCommandInfo();
        UserProperty userProperty = requestReport.getUserProperty();
        String userId = userProperty.getUserId();

        String id = command.getData().get("id").toString();// 评论id

        if (com.github.pagehelper.util.StringUtil.isEmpty(id)) {
            super.getAjaxResult(requestReport, ResponseCode.NotAvailable, "参数错误", null);
        }

        CommentThumbup commentThumbup = new CommentThumbup();
        commentThumbup.setPId(id);

        Comment comment = commentService.selectById(id);
        String cmeType = "";
        if(comment!=null){
            cmeType = comment.getCmeType();
        }
        if(!com.github.pagehelper.util.StringUtil.isEmpty(cmeType)){
            if(cmeType.equals("1")){
                //修积分和经验值
                userIntegralLogService.addLntAndEmp(userId, "task_0014");
            }
        }
        commentThumbup.setUserId(userId);

        List<Map<String, Object>> thumbupList = commentThumbupService
                .selectAllCommentThumbup(commentThumbup);
        if (thumbupList.size() <= 0) {
            commentThumbupService.addCommentThumbup(commentThumbup);
            return super.getAjaxResult(requestReport, ResponseCode.OK, "点赞成功", null);
        } else {
            return super.getAjaxResult(requestReport, ResponseCode.OK, "已经成功", null);
        }
    }

    /**
     * 评论取消点赞
     *
     * @return
     */
    @RequestMapping("/cancelThumbUp")
    public @ResponseBody
    ResponseReport cancelThumbUp(@RequestBody RequestReport requestReport) {

        CommandInfo command = requestReport.getCommandInfo();
        UserProperty userProperty = requestReport.getUserProperty();
        String userId = userProperty.getUserId();

        String id = command.getData().get("id").toString();// 评论id

        if (com.github.pagehelper.util.StringUtil.isEmpty(id)) {
            super.getAjaxResult(requestReport, ResponseCode.NotAvailable, "参数错误", null);
        }

        CommentThumbup commentThumbup = new CommentThumbup();
        commentThumbup.setPId(id);
        commentThumbup.setUserId(userId);

        List<Map<String, Object>> thumbupList = commentThumbupService
                .selectAllCommentThumbup(commentThumbup);

        for(Map<String, Object> m : thumbupList){
            commentThumbupService.deleteCommentThumbups((String)m.get("id"));
        }

        return super.getAjaxResult(requestReport, ResponseCode.OK, "取消点赞成功", null);
    }

    /**
     * 评论新增
     *
     * @return
     */
    @RequestMapping("/addComment")
    public @ResponseBody
    ResponseReport addComment(@RequestBody RequestReport requestReport){

        CommandInfo command = requestReport.getCommandInfo();
        UserProperty userProperty = requestReport.getUserProperty();
        String userId = userProperty.getUserId();

        String cmeDesc =  command.getData().get("cmeDesc").toString();// 评论内容
        String imgUrl =  command.getData().get("imgUrl") != null ? command.getData().get("imgUrl").toString():"";// 评论图片url
        String fkId =  command.getData().get("fkId")!= null ? command.getData().get("fkId").toString():"";// 评论主题Id
        String cmeType =  command.getData().get("cmeType")!= null ? command.getData().get("cmeType").toString():"";// 评论类型
        String parentId =  command.getData().get("parentId")!= null ? command.getData().get("parentId").toString():"";// 评论上级id

        Comment comment = new Comment();

        if (StringUtil.isEmpty(cmeDesc)) {
            super.getAjaxResult(requestReport, ResponseCode.NotAvailable, "参数错误", null);

        } else {
            comment.setCmeDesc(cmeDesc);
        }
        if (!StringUtil.isEmpty(fkId)) {
            comment.setFkId(fkId);
        }
        cmeDesc = EsapiTest.stripXSS(cmeDesc);
        if (!com.github.pagehelper.util.StringUtil.isEmpty(cmeType)) {
            comment.setCmeType(cmeType);

            //cmeType;//1:资讯   2：合作 3：话题  4：活动 5：动态
            if("5".equals(cmeType)){
                //此项每日记首次评论
                userIntegralLogService.addLntAndEmp(userId, "task_0005");
            }else if("3".equals(cmeType)){
                //此项每日记首次评论
                userIntegralLogService.addLntAndEmp(userId, "task_0010");
            }else if("1".equals(cmeType)){
                //此项每日记首次评论
                userIntegralLogService.addLntAndEmp(userId, "task_0015");
            }else{
                //此项每日记首次评价
                //userIntegralLogService.addLntAndEmp(userId, "task_0011");
            }
        }
        if (!StringUtil.isEmpty(imgUrl)) {
            comment.setImgUrl(imgUrl);
        }
        if (!StringUtil.isEmpty(parentId)) {
            comment.setParentId(parentId);
        }
        comment.setCreateTime(new Date());
        comment.setUserId(userId);
        Comment cm = new Comment();
        cm.setFkId(fkId);
        List cmList = commentService.selectAllComment(cm);
        if(null==cmList||cmList.size()==0){
            comment.setStatus(1);//1为沙发
        }
        commentService.addComment(comment);

        return super.getAjaxResult(requestReport, ResponseCode.OK, "新增成功", null);
    }


}
