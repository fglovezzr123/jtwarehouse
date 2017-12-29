package com.tojoy.business.common.controller;


import com.github.pagehelper.util.StringUtil;
import com.tojoy.business.common.api.ICommentService;
import com.tojoy.business.common.api.ICommentThumbUpService;
import com.tojoy.business.common.bean.Comment;
import com.tojoy.business.common.bean.CommentThumbUp;
import com.tojoy.business.common.common.report.ResponseCode;
import com.tojoy.business.common.common.report.base.BaseController;
import com.tojoy.business.common.common.report.base.CommandInfo;
import com.tojoy.business.common.common.report.base.UserProperty;
import com.tojoy.business.common.model.PageParam;
import com.tojoy.business.common.report.RequestReport;
import com.tojoy.business.common.report.ResponseReport;
import com.wing.socialcontact.service.wx.api.IWxUserService;
import com.wing.socialcontact.util.CommUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.*;

/**
 * 评论  接口controller
 */
@Controller
@RequestMapping("/comment")
public class CommentController extends BaseController {

    @Autowired
    private IWxUserService wxUserService;
    @Autowired
    private ICommentThumbUpService commentThumbUpService;
    @Autowired
    private ICommentService commentService;

    /**
     * 查看子评论
     *
     * @return
     */
    @RequestMapping("/querySubCommentByPId")
    public
    @ResponseBody
    ResponseReport querySubCommentByPId(@RequestBody RequestReport requestReport) {

        CommandInfo command = requestReport.getCommandInfo();
        UserProperty userProperty = requestReport.getUserProperty();
        String userId = userProperty.getUserId();

        String parentId = command.getData().get("parentId") != null ? command.getData().get("parentId").toString() : "";// 评论上级id
        String key = command.getData().get("key") != null ? command.getData().get("key").toString() : "";

        if (StringUtil.isEmpty(parentId) || StringUtil.isEmpty(key)) {
            return super.getAjaxResult(requestReport, ResponseCode.NotSupport, "参数错误", null);
        }

        Comment commentPara = new Comment();
        commentPara.setId(parentId);
        commentPara.setKeyWord(key);

        // 获取用户
        Map<String, Object> user = new HashMap<String, Object>();
        Map<String, Object> comment = commentService.selectById(commentPara);
        if (null != comment) {
            if (!StringUtil.isEmpty((String) comment.get("userId"))) {
                user = wxUserService.queryUsersByid((String) comment.get("userId"));
            }
        }

        Map userMap = new HashMap();
        userMap.put("imgUrl", user.get("head_portrait"));
        userMap.put("honorTitle", user.get("honor_title"));
        userMap.put("honorFlag", user.get("honor_flag"));
        userMap.put("nickName", user.get("nickname"));
        userMap.put("jobName", user.get("jobName"));
        userMap.put("industryName", user.get("industryName"));

        Map map = new HashMap();
        map.put("user", userMap);

        //点赞数量
        CommentThumbUp commentThumbUp = new CommentThumbUp();
        commentThumbUp.setFkId(parentId);
        commentThumbUp.setKeyWord(key);
        int ThumbUpCount = commentThumbUpService.queryCount(commentThumbUp);
        comment.put("thumbUpCount", ThumbUpCount);

        //获取当前用户点赞状态
        commentThumbUp.setUserId(userId);
        List<Map<String, Object>> thumbUpList = commentThumbUpService
                .selectCommentThumbUpList(commentThumbUp);
        if (thumbUpList.size() > 0) {
            comment.put("isThumbUp", 1);
        } else {
            comment.put("isThumbUp", 0);
        }

        map.put("comment", comment);

        //子评论
        commentPara = new Comment();
        commentPara.setParentId(parentId);
        commentPara.setKeyWord(key);
        List<Map<String, Object>> subCommentList = commentService
                .queryCommentByPId(commentPara);

        // 子评论用户信息
        for (Map<String, Object> m : subCommentList) {
            Map<String, Object> subUser = wxUserService
                    .queryUsersByid((String) (m.get("userId")));
            if (subUser != null) {
                m.put("nickName",subUser.get("nickname"));
                m.put("headPortrait",(subUser.get("head_portrait")));
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
    @RequestMapping("queryCommentList")
    public
    @ResponseBody
    ResponseReport queryCommentList(@RequestBody RequestReport requestReport)
            throws IOException {

        CommandInfo command = requestReport.getCommandInfo();
        UserProperty userProperty = requestReport.getUserProperty();
        String fkId = command.getData().get("fkId").toString();
        String userId = userProperty.getUserId();
        Integer pageNum = command.getData().get("page") != null ? Integer.valueOf(command.getData().get("page").toString()) : 1;
        Integer pageSize = command.getData().get("size") != null ? Integer.valueOf(command.getData().get("size").toString()) : 10;
        String key = command.getData().get("key") != null ? command.getData().get("key").toString() : "";

        Comment comment = new Comment();

        if (StringUtil.isEmpty(userId)) {
            return super.getAjaxResult(requestReport, ResponseCode.NotSupport, "用户id不能为空", null);
        }
        if (StringUtil.isEmpty(fkId)) {
            return super.getAjaxResult(requestReport, ResponseCode.NotSupport, "参数错误", null);
        } else {
            comment.setFkId(fkId);
        }

        comment.setKeyWord(key);
        PageParam pageParam = new PageParam();
        pageParam.setPage(pageNum);
        pageParam.setRows(pageSize);
        List<Map<String, Object>> commentList = commentService
                .selectCommentList(pageParam, comment);

        int thumbUpCount = 0;
        int subCommentCount = 0;
        List<Map<String, Object>> resultCommentList = new ArrayList<>();
        for (Map<String, Object> m : commentList) {
            Map<String, Object> resultComment = new HashMap<>();

            // 获取点赞数
            CommentThumbUp commentThumbUp = new CommentThumbUp();
            commentThumbUp.setFkId((String) m.get("id"));
            commentThumbUp.setKeyWord(key);
            thumbUpCount = commentThumbUpService.queryCount(commentThumbUp);
            m.put("thumbUpCount", thumbUpCount);

            //获取当前用户点赞状态
            commentThumbUp.setUserId(userId);
            List<Map<String, Object>> thumbupList = commentThumbUpService
                    .selectCommentThumbUpList(commentThumbUp);
            int isThumbUp = 0;
            if (thumbupList.size() > 0) {
                isThumbUp = 1;
            }
            m.put("isThumbUp", isThumbUp);

            // 获取第一条子评论
            comment = new Comment();
            comment.setParentId((String) m.get("id"));
            comment.setKeyWord(key);
            List<Comment> subCommentList = commentService
                    .queryCommentByPId(comment);

            if (subCommentList != null && subCommentList.size() != 0) {
                resultComment.put("firstSubComment", subCommentList.get(0));
            }
            if (null != subCommentList) {
                subCommentCount = subCommentList.size();
            }
            m.put("subCommentCount", subCommentCount);
            m.put("createTime", CommUtil.formatLongDate2(m.get("createTime")));

            // 获取用户
            Map<String, Object> user = wxUserService.queryUsersByid((String) m.get("userId"));
            if (null != user) {
                Map userMap = new HashMap();
                userMap.put("imgUrl", user.get("head_portrait"));
                userMap.put("honorTitle", user.get("honor_title"));
                userMap.put("honorFlag", user.get("honor_flag"));
                userMap.put("nickName", user.get("nickname"));
                userMap.put("jobName", user.get("jobName"));
                userMap.put("industryName", user.get("industryName"));
                resultComment.put("user",userMap);
            }
            resultComment.put("comment",m);
            resultCommentList.add(resultComment);
        }

        Map res = new  HashMap();
        res.put("commentList",commentList);
        return super.getAjaxResult(requestReport, ResponseCode.OK, "查询成功", resultCommentList);
    }

    /**
     * 评论点赞
     *
     * @return
     */
    @RequestMapping("/thumbUp")
    public
    @ResponseBody
    ResponseReport ThumbUp(@RequestBody RequestReport requestReport) {

        CommandInfo command = requestReport.getCommandInfo();
        UserProperty userProperty = requestReport.getUserProperty();
        String userId = userProperty.getUserId();

        String id = command.getData().get("id").toString();// 评论id
        String key = command.getData().get("key") != null ? command.getData().get("key").toString() : "";

        if (com.github.pagehelper.util.StringUtil.isEmpty(id)) {
            super.getAjaxResult(requestReport, ResponseCode.NotAvailable, "参数错误", null);
        }

        CommentThumbUp commentThumbUp = new CommentThumbUp();
        commentThumbUp.setFkId(id);
        commentThumbUp.setUserId(userId);
        commentThumbUp.setKeyWord(key);

        List<Map<String, Object>> thumbupList = commentThumbUpService
                .selectCommentThumbUpList(commentThumbUp);
        if (thumbupList.size() <= 0) {
            commentThumbUpService.insert(commentThumbUp);
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
    public
    @ResponseBody
    ResponseReport cancelThumbUp(@RequestBody RequestReport requestReport) {

        CommandInfo command = requestReport.getCommandInfo();
        UserProperty userProperty = requestReport.getUserProperty();
        String userId = userProperty.getUserId();

        String id = command.getData().get("id").toString();// 评论id
        String key = command.getData().get("key") != null ? command.getData().get("key").toString() : "";

        if (com.github.pagehelper.util.StringUtil.isEmpty(id)) {
            super.getAjaxResult(requestReport, ResponseCode.NotAvailable, "参数错误", null);
        }

        CommentThumbUp commentThumbUp = new CommentThumbUp();
        commentThumbUp.setFkId(id);
        commentThumbUp.setUserId(userId);
        commentThumbUp.setKeyWord(key);

        List<Map<String, Object>> thumbupList = commentThumbUpService
                .selectCommentThumbUpList(commentThumbUp);

        for (Map<String, Object> t : thumbupList) {
            commentThumbUp = new CommentThumbUp();
            commentThumbUp.setId((String) t.get("id"));
            commentThumbUp.setKeyWord(key);
            commentThumbUpService.delete(commentThumbUp);
        }

        return super.getAjaxResult(requestReport, ResponseCode.OK, "取消点赞成功", null);
    }

    /**
     * 评论新增
     *
     * @return
     */
    @RequestMapping("/addComment")
    public
    @ResponseBody
    ResponseReport addComment(@RequestBody RequestReport requestReport) {

        CommandInfo command = requestReport.getCommandInfo();
        UserProperty userProperty = requestReport.getUserProperty();
        String userId = userProperty.getUserId();

        String commentDesc = command.getData().get("commentDesc") != null ? command.getData().get("commentDesc").toString() : ""; // 评论内容
        String imgUrl = command.getData().get("imgUrl") != null ? command.getData().get("imgUrl").toString() : "";// 评论图片url
        String fkId = command.getData().get("fkId") != null ? command.getData().get("fkId").toString() : "";// 评论主题Id
        String parentId = command.getData().get("parentId") != null ? command.getData().get("parentId").toString() : "";// 评论上级id
        String key = command.getData().get("key") != null ? command.getData().get("key").toString() : "";

        Comment comment = new Comment();
        comment.setKeyWord(key);

        if (StringUtil.isEmpty(commentDesc)) {
            super.getAjaxResult(requestReport, ResponseCode.NotAvailable, "参数错误", null);

        } else {
            comment.setCommentDesc(commentDesc);
        }
        if (!StringUtil.isEmpty(fkId)) {
            comment.setFkId(fkId);
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
        cm.setKeyWord(key);
        List cmList = commentService.selectCommentList(cm);
        if (null == cmList || cmList.size() == 0) {
            comment.setStatus(1);//1为沙发
        }
        commentService.addComment(comment);

        return super.getAjaxResult(requestReport, ResponseCode.OK, "新增成功", null);
    }

}
