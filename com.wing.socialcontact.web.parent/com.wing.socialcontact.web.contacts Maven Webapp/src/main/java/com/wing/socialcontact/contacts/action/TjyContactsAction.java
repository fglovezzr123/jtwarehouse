package com.wing.socialcontact.contacts.action;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.wing.socialcontact.service.wx.bean.TjyContacts;
import com.wing.socialcontact.service.wx.bean.TjyContactsVersion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;
import tk.mybatis.mapper.util.StringUtil;

import com.tojoycloud.common.report.ResponseCode;
import com.tojoycloud.common.report.base.BaseAppAction;
import com.tojoycloud.report.RequestReport;
import com.tojoycloud.report.ResponseReport;
import com.wing.socialcontact.service.wx.api.ITjyContactsService;

/**
 * @author devil
 * @desicription: app通讯录同步
 * @date Created in 2017/12/13 20:20
 */
@RestController
@RequestMapping("/m/app/tjyContacts")
public class TjyContactsAction extends BaseAppAction {

    @Autowired
    private ITjyContactsService tjyContactsService;

    /**
     * 获取服务端当前通讯录版本
     *
     * @param rr
     * @param session
     * @param response
     * @return
     */
    @RequestMapping("getVersion")
    public ResponseReport getVersion(@RequestBody RequestReport rr, HttpSession session, HttpServletResponse response) {
        try {
            String userId = rr.getUserProperty().getUserId();
            if (StringUtil.isEmpty(userId)) {
                return super.getAjaxResult(rr, ResponseCode.NotSupport, "未登录", null);
            }
            Map map = tjyContactsService.getVersionByUserId(Long.valueOf(userId));
            if (map != null) {
                int version = map.get("version") != null ? Integer.valueOf(map.get("version").toString()) : 0;
                if (version == 0) {
                    //删除相关userId的记录
                    tjyContactsService.removeByUserId(Long.valueOf(userId));
                    map.put("uploadSize", 100);
                }
            }
            return super.getAjaxResult(rr, ResponseCode.OK, "获取成功！", map);
        } catch (Exception e) {
            return super.getAjaxResult(rr, ResponseCode.Error, "获取失败！", null);
        }
    }

    /**
     * 上传通讯录
     * @param rr
     * @param session
     * @param response
     * @return
     */
    @RequestMapping("uploadContacts")
    public ResponseReport uploadMailList(@RequestBody RequestReport rr, HttpSession session, HttpServletResponse response) {
        try {
            String userId = rr.getUserProperty().getUserId();
            if (StringUtil.isEmpty(userId)) {
                return super.getAjaxResult(rr, ResponseCode.NotSupport, "未登录", null);
            }
            String mobiles = rr.getDataValue("mobiles");
            if (StringUtil.isEmpty(mobiles)) {
                return super.getAjaxResult(rr, ResponseCode.NotSupport, "参数有误", null);
            }
            String isLast = rr.getDataValue("isLast");
            if (StringUtil.isEmpty(isLast)) {
                isLast = "0";
            }
            int result = tjyContactsService.uploadContacts(Long.valueOf(userId), mobiles, isLast);
            if (result == -2) {
                return super.getAjaxResult(rr, ResponseCode.Error, "上传失败！", "上传条数不能超过100条！");
            }
            return super.getAjaxResult(rr, ResponseCode.OK, "上传成功！", result);
        } catch (Exception e) {
            return super.getAjaxResult(rr, ResponseCode.Error, "上传失败！", null);
        }
    }

    /**
     * 获取通讯录
     * @param rr
     * @return
     */
    @RequestMapping("getContacts")
    public ResponseReport getAddressList(@RequestBody RequestReport rr) {
        try {
            String userId = rr.getUserProperty().getUserId();
            if (StringUtil.isEmpty(userId)) {
                return super.getAjaxResult(rr, ResponseCode.NotSupport, "未登录", null);
            }
            String pageNum = rr.getDataValue("pageNum");
            String pageSize = rr.getDataValue("pageSize");
            if (StringUtil.isEmpty(pageNum) || StringUtil.isEmpty(pageSize)) {
                return super.getAjaxResult(rr, ResponseCode.NotSupport, "页码或行数参数错误", null);
            }
            String status = rr.getDataValue("status");
            if (StringUtil.isEmpty(status)) {
                return super.getAjaxResult(rr, ResponseCode.NotSupport, "状态参数错误", null);
            }
            String randomFlag = rr.getDataValue("randomFlag");
            List<TjyContacts> tjyAddressListList = tjyContactsService.getContacts(Long.valueOf(userId), Integer.valueOf(pageNum),
                    Integer.valueOf(pageSize), status, Boolean.valueOf(randomFlag));
            return super.getAjaxResult(rr, ResponseCode.OK, "获取通讯录成功！", tjyAddressListList);
        } catch (Exception e) {
            return super.getAjaxResult(rr, ResponseCode.Error, "获取通讯录失败！", null);
        }
    }

    /**
     * 邀请通讯录好友注册
     *//*
    @RequestMapping("investFriend")
    public ResponseReport investFriend(@RequestBody RequestReport rr, HttpSession session, HttpServletResponse response) throws IOException {
        String userId = rr.getUserProperty().getUserId();
        String mobile = rr.getDataValue("mobile");
        String name = rr.getDataValue("name");
        if (userId == null || "".equals(userId)) {
            return super.getAjaxResult(rr, ResponseCode.NotSupport, "未登录", null);
        }
        try {
            TjyUser user = tjyUserService.selectByPrimaryKey(userId);
            //发送邀请短信
            // 发送短信
            String content = "注册邀请";
            if (AldyMessageUtil.directSend(content, mobile)) {
                AppInviteLog ail = new AppInviteLog();
                ail.setCreateTime(new Date());
                ail.setMobile(mobile);
                ail.setUserId(userId);
                ail.setContent(content);
                appInviteLogService.insertLog(ail);
                return super.getAjaxResult(rr, ResponseCode.OK, "发送邀请成功", null);
            }
            return super.getAjaxResult(rr, ResponseCode.Error, "发送邀请失败！", null);
        } catch (Exception e) {
            return super.getAjaxResult(rr, ResponseCode.Error, "发送邀请失败！", null);
        }
    }*/

}
