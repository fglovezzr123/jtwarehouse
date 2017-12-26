package com.wing.socialcontact.front.action;

import com.tojoycloud.common.report.ResponseCode;
import com.tojoycloud.common.report.base.BaseAppAction;
import com.tojoycloud.common.report.base.CommandInfo;
import com.tojoycloud.common.report.base.UserProperty;
import com.tojoycloud.report.RequestReport;
import com.tojoycloud.report.ResponseReport;
import com.wing.socialcontact.common.model.DataGrid;
import com.wing.socialcontact.common.model.PageParam;
import com.wing.socialcontact.config.OssConfig;
import com.wing.socialcontact.service.wx.api.*;
import com.wing.socialcontact.service.wx.bean.*;
import com.wing.socialcontact.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.*;

/**
 * 注册拍照  接口controller
 */
@Controller
@RequestMapping("/m/userPhoto")
public class UserPhotoAction extends BaseAppAction {

    @Autowired
    private IUserPhotoService userPhotoService;

    /**
     * 获取照片审核状态
     *
     * @param requestReport
     * @return
     */
    @RequestMapping("/queryStatusByUserId")
    public
    @ResponseBody
    ResponseReport queryStatusByUserId(@RequestBody RequestReport requestReport) {

        CommandInfo command = requestReport.getCommandInfo();
        String userId = command.getData().get("userId").toString();

        UserPhoto userPhoto = userPhotoService.selectByUserId(userId);
        Map result = new HashMap();
        if(userPhoto !=null && userPhoto.getStatus()==1){
            result.put("status", 1);
        }else{
            result.put("status", 0);
        }

        return super.getAjaxResult(requestReport, ResponseCode.OK, "获取成功", result);
    }

    /**
     * 存储照片
     *
     * @return
     */
    @RequestMapping("/setUserPhoto")
    public
    @ResponseBody
    ResponseReport setUserPhoto(@RequestBody RequestReport requestReport) {

        CommandInfo command = requestReport.getCommandInfo();
        String userId = command.getData().get("userId").toString();
        String photoUrl = command.getData().get("photoUrl").toString();

        Integer iCount = 0;
        UserPhoto userPhoto = userPhotoService.selectByUserId(userId);
        if(userPhoto !=null){
            userPhoto.setPhotoUrl(photoUrl);
            userPhoto.setStatus(1);
            userPhoto.setUpdateTime(new Date());
            userPhoto.setUpdateUserId(userId);
            iCount = userPhotoService.update(userPhoto);
        }else{
            userPhoto = new UserPhoto();
            userPhoto.setUserId(userId);
            userPhoto.setPhotoUrl(photoUrl);
            userPhoto.setStatus(1);
            userPhoto.setCreateTime(new Date());
            userPhoto.setCreateUserId(userId);
            iCount = userPhotoService.insert(userPhoto);
        }

        if(iCount>0){
            return super.getAjaxResult(requestReport, ResponseCode.OK, "添加成功", null);
        }else{
            return super.getAjaxResult(requestReport, ResponseCode.Error, "添加失败", null);
        }
    }

}
