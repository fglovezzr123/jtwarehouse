package com.tojoy.business.common.controller;

import com.tojoy.business.common.api.ICollectionService;
import com.tojoy.business.common.api.IThumbUpService;
import com.tojoy.business.common.bean.Collection;
import com.tojoy.business.common.bean.ThumpUp;
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
 * 点赞  接口controller
 */
@Controller
@RequestMapping("/thumbUp")
public class ThumbUpController extends BaseController {

    /**
     * 收藏服务
     */
    @Autowired
    private IThumbUpService thumbUpService;

    /**
     * 点赞
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

        ThumpUp thumpUp = new ThumpUp();
        thumpUp.setKeyWord(key);
        thumpUp.setUserId(userId);
        thumpUp.setFkId(fkId);

        ThumpUp p = thumbUpService.queryByFkIdUserId(thumpUp);
        //已关注
        if (p != null) {
            return super.getAjaxResult(requestReport, ResponseCode.OK, "已经点赞", null);
        } else {
            ThumpUp t = new ThumpUp();
            t.setKeyWord(key);
            t.setCreateTime(new Date());
            t.setFkId(fkId);
            t.setUserId(userId);
            Integer iCount = thumbUpService.insert(t);
            if (iCount > 0) {
                return super.getAjaxResult(requestReport, ResponseCode.OK, "点赞成功", null);
            } else {
                return super.getAjaxResult(requestReport, ResponseCode.Error, "点赞失败", null);
            }
        }
    }

    /**
     * 取消点赞
     *
     * @param requestReport 请求参数
     * @return
     */
    @RequestMapping(value = "/cancel", method = RequestMethod.POST)
    public
    @ResponseBody
    ResponseReport cancel(@RequestBody RequestReport requestReport) {

        CommandInfo command = requestReport.getCommandInfo();
        String fkId = command.getData().get("fkId").toString();
        String key = command.getData().get("key").toString();
        UserProperty userProperty = requestReport.getUserProperty();
        String userId = userProperty.getUserId();

        ThumpUp thumpUp = new ThumpUp();
        thumpUp.setKeyWord(key);
        thumpUp.setUserId(userId);
        thumpUp.setFkId(fkId);

        ThumpUp p = thumbUpService.queryByFkIdUserId(thumpUp);
        p.setKeyWord(key);
        //已关注
        if (p != null) {
            int iCount = thumbUpService.delete(p);
            if (iCount > 0) {
                return super.getAjaxResult(requestReport, ResponseCode.OK, "取消点赞成功", null);
            } else {
                return super.getAjaxResult(requestReport, ResponseCode.Error, "取消点赞失败", null);
            }
        } else {
            return super.getAjaxResult(requestReport, ResponseCode.OK, "已经取消点赞", null);
        }
    }

    /**
     * 查询点赞数和当前用户是否点赞
     *
     * @param requestReport 请求参数
     * @return
     */
    @RequestMapping(value = "/queryCountIsThumpUp", method = RequestMethod.POST)
    public
    @ResponseBody
    ResponseReport queryCountIsThumpUp(@RequestBody RequestReport requestReport) {

        CommandInfo command = requestReport.getCommandInfo();
        String fkId = command.getData().get("fkId").toString();
        String key = command.getData().get("key").toString();
        UserProperty userProperty = requestReport.getUserProperty();
        String userId = userProperty.getUserId();

        ThumpUp thumpUp = new ThumpUp();
        thumpUp.setKeyWord(key);
        thumpUp.setUserId(userId);
        thumpUp.setFkId(fkId);
        ThumpUp p = thumbUpService.queryByFkIdUserId(thumpUp);

        thumpUp = new ThumpUp();
        thumpUp.setFkId(fkId);
        thumpUp.setKeyWord(key);
        int thumpUpCount =  thumbUpService.queryCount(thumpUp);

        Map map = new HashMap();
        if (p != null) {
            map.put("isThumpUp", 1);
        } else {
            map.put("isThumpUp", 0);
        }
        map.put("thumpUpCount",thumpUpCount);

        return super.getAjaxResult(requestReport, ResponseCode.OK, "查询成功", map);
    }


}
