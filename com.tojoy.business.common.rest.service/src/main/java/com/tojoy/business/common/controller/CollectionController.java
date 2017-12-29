package com.tojoy.business.common.controller;

import com.tojoy.business.common.common.report.ResponseCode;
import com.tojoy.business.common.common.report.base.BaseController;
import com.tojoy.business.common.common.report.base.CommandInfo;
import com.tojoy.business.common.common.report.base.UserProperty;
import com.tojoy.business.common.report.RequestReport;
import com.tojoy.business.common.report.ResponseReport;
import com.tojoy.business.common.api.ICollectionService;
import com.tojoy.business.common.bean.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 收藏  接口controller
 */
@Controller
@RequestMapping("/collection")
public class CollectionController extends BaseController {

    /**
     * 收藏服务
     */
    @Autowired
    private ICollectionService collectionService;

    /**
     * 收藏
     *
     * @param requestReport 请求参数
     * @return
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public
    @ResponseBody
    ResponseReport addCollection(@RequestBody RequestReport requestReport) {

        CommandInfo command = requestReport.getCommandInfo();
        String fkId = command.getData().get("fkId").toString();
        String key = command.getData().get("key").toString();
        UserProperty userProperty = requestReport.getUserProperty();
        String userId = userProperty.getUserId();

        Collection collection = new Collection();
        collection.setKeyWord(key);
        collection.setUserId(userId);
        collection.setFkId(fkId);

        Collection p = collectionService.queryByFkIdUserId(collection);
        //已关注
        if (p != null) {
            return super.getAjaxResult(requestReport, ResponseCode.OK, "已经关注", null);
        } else {
            Collection t = new Collection();
            t.setKeyWord(key);
            t.setCreateTime(new Date());
            t.setFkId(fkId);
            t.setUserId(userId);
            Integer iCount = collectionService.insert(t);
            if (iCount > 0) {
                return super.getAjaxResult(requestReport, ResponseCode.OK, "收藏成功", null);
            } else {
                return super.getAjaxResult(requestReport, ResponseCode.Error, "收藏失败", null);
            }
        }
    }

    /**
     * 取消收藏
     *
     * @param requestReport 请求参数
     * @return
     */
    @RequestMapping(value = "/cancel", method = RequestMethod.POST)
    public
    @ResponseBody
    ResponseReport cancelCollection(@RequestBody RequestReport requestReport) {

        CommandInfo command = requestReport.getCommandInfo();
        String fkId = command.getData().get("fkId").toString();
        String key = command.getData().get("key").toString();
        UserProperty userProperty = requestReport.getUserProperty();
        String userId = userProperty.getUserId();

        Collection collection = new Collection();
        collection.setKeyWord(key);
        collection.setUserId(userId);
        collection.setFkId(fkId);

        Collection p = collectionService.queryByFkIdUserId(collection);
        p.setKeyWord(key);
        //已关注
        if (p != null) {
            int iCount = collectionService.delete(p);
            if (iCount > 0) {
                return super.getAjaxResult(requestReport, ResponseCode.OK, "取消收藏成功", null);
            } else {
                return super.getAjaxResult(requestReport, ResponseCode.Error, "取消收藏失败", null);
            }
        } else {
            return super.getAjaxResult(requestReport, ResponseCode.OK, "已经取消收藏", null);
        }
    }

    /**
     * 查询收藏数和当前用户是否收藏
     *
     * @param requestReport 请求参数
     * @return
     */
    @RequestMapping(value = "/queryCountIsCollection", method = RequestMethod.POST)
    public
    @ResponseBody
    ResponseReport queryCountIsCollection(@RequestBody RequestReport requestReport) {

        CommandInfo command = requestReport.getCommandInfo();
        String fkId = command.getData().get("fkId").toString();
        String key = command.getData().get("key").toString();
        UserProperty userProperty = requestReport.getUserProperty();
        String userId = userProperty.getUserId();

        Collection collection = new Collection();
        collection.setKeyWord(key);
        collection.setUserId(userId);
        collection.setFkId(fkId);
        Collection p = collectionService.queryByFkIdUserId(collection);

        collection = new Collection();
        collection.setFkId(fkId);
        collection.setKeyWord(key);
        int collectionCount =  collectionService.queryCollectionCount(collection);

        Map map = new HashMap();
        if (p != null) {
            map.put("isCollection", 1);
        } else {
            map.put("isCollection", 0);
        }
        map.put("collectionCount",collectionCount);

        return super.getAjaxResult(requestReport, ResponseCode.OK, "查询成功", map);
    }

}
