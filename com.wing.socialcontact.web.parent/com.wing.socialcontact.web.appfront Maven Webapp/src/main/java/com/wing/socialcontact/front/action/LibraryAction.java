package com.wing.socialcontact.front.action;

import com.tojoycloud.common.report.ResponseCode;
import com.tojoycloud.common.report.base.BaseAppAction;
import com.tojoycloud.common.report.base.CommandInfo;
import com.tojoycloud.common.report.base.UserProperty;
import com.tojoycloud.report.RequestReport;
import com.tojoycloud.report.ResponseReport;
import com.wing.socialcontact.common.model.Member;
import com.wing.socialcontact.config.OssConfig;
import com.wing.socialcontact.service.wx.api.*;
import com.wing.socialcontact.service.wx.bean.*;
import com.wing.socialcontact.util.*;
import com.wing.socialcontact.util.StringUtil;
import com.wing.socialcontact.vhall.api.BaseAPI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import tk.mybatis.mapper.util.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

/**
 * APP智囊团 文章（老板悄悄话等）接口 controller
 */
@Controller
@RequestMapping("/m/library")
public class LibraryAction extends BaseAppAction {

    @Autowired
    private ILibraryService libraryService;

    @Autowired
    private IMyCollectionService myCollectionService;

    @Autowired
    private IWalletLogService walletLogService;

    @Autowired
    private ILibraryOpLogService libraryOpLogService;

    @Autowired
    private IWxUserService wxUserService;

    /**
     * 获取文章列表
     *
     * @return
     */
    @RequestMapping("/queryLibraryList")
    public
    @ResponseBody
    ResponseReport queryLibraryList(@RequestBody RequestReport requestReport) {

        CommandInfo command = requestReport.getCommandInfo();
        String classId = command.getData().get("classId") != null ? command.getData().get("classId").toString() : "";
        Integer page = command.getData().get("page") != null ? Integer.valueOf(command.getData().get("page").toString()) : 1;
        Integer size = command.getData().get("size") != null ? Integer.valueOf(command.getData().get("size").toString()) : 10;
        Integer readTimesFlag = command.getData().get("readTimesFlag") != null ? Integer.valueOf(command.getData().get("readTimesFlag").toString()) : 0;

        if (StringUtil.isEmpty(classId)) {
            return super.getAjaxResult(requestReport, ResponseCode.NotSupport, "分类id不能为空", null);
        }

        //app端参数不传
        Integer today =  0;
        String key = "";

        List<Map> list = new ArrayList<Map>();
        list = libraryService.getTjyLibraryByTerm(classId, page, size, today, key, readTimesFlag);

        if (list == null || list.size() == 0) {
            //查询"一级"文章
            list = libraryService.getLibraryByoneLevel(classId, page, size, today, key, readTimesFlag);
        }

        return super.getAjaxResult(requestReport, ResponseCode.OK, "获取成功", list);
    }

    /**
     * 获取文章详情
     *
     * @return
     */
    @RequestMapping("/queryLibraryDetail")
    public
    @ResponseBody
    ResponseReport queryLibraryDetail(@RequestBody RequestReport requestReport) {

        CommandInfo command = requestReport.getCommandInfo();
        UserProperty userProperty = requestReport.getUserProperty();
        String libraryId = command.getData().get("libraryId") != null ? command.getData().get("libraryId").toString() : "";
        String userId = userProperty.getUserId();
        if (StringUtil.isEmpty(libraryId)) {
            return super.getAjaxResult(requestReport, ResponseCode.NotSupport, "文章id不能为空", null);
        }
        if (StringUtil.isEmpty(userId)) {
            return super.getAjaxResult(requestReport, ResponseCode.NotSupport, "用户id不能为空", null);
        }

        //文章信息
        Map result = new HashMap();
        result = libraryService.getLibraryByid(libraryId);
        if(result != null){

            //是否收藏
            boolean isCollection = false;
            isCollection = myCollectionService.iscollected(userId, libraryId, 1);
            result.put("isCollection", isCollection);

            //打赏总数
            int rewardCount = 0;
            //点赞总数
            int thumbUpCount = 0;
            //当前用户是否点赞
            boolean isThumbUp = false;
            rewardCount = walletLogService.selectRewardSum("17", libraryId);
            thumbUpCount = libraryOpLogService.getCountByFkIdAndType(libraryId, "1", "1");
            LibraryOpLog opLog = new LibraryOpLog();
            opLog.setUserId(userId);
            opLog.setOpType(1);
            opLog.setType(1);
            opLog.setFkId(libraryId);
            List list = libraryOpLogService.selectAllOpLog(opLog);
            if (list != null && list.size() > 0) {
                isThumbUp = true;
            }

            result.put("rewardCount", rewardCount);
            result.put("thumbUpCount", thumbUpCount);
            result.put("isThumbUp", isThumbUp);

            //添加阅读数量
            libraryService.addreadtimes(libraryId);
        }



        return super.getAjaxResult(requestReport, ResponseCode.OK, "获取成功", result);
    }

    /**
     * 文章点赞、取消点赞
     *
     * @return
     */
    @RequestMapping("thumbUpLibrary")
    public  @ResponseBody
    ResponseReport thumbUpLibrary(@RequestBody RequestReport requestReport) {

        CommandInfo command = requestReport.getCommandInfo();
        UserProperty userProperty = requestReport.getUserProperty();
        String libraryId = command.getData().get("libraryId") != null ? command.getData().get("libraryId").toString() : "";
        String userId = userProperty.getUserId();
        if (StringUtil.isEmpty(libraryId)) {
            return super.getAjaxResult(requestReport, ResponseCode.NotSupport, "文章id不能为空", null);
        }
        if (StringUtil.isEmpty(userId)) {
            return super.getAjaxResult(requestReport, ResponseCode.NotSupport, "用户id不能为空", null);
        }

        LibraryOpLog opLog = new LibraryOpLog();
        opLog.setUserId(userId);
        opLog.setOpType(1);
        opLog.setType(1);
        opLog.setFkId(libraryId);
        List list = libraryOpLogService.selectAllOpLog(opLog);

        if (list.size() <= 0) {
            //点赞
            opLog.setId(UUID.randomUUID().toString());
            opLog.setActionTime(new Date());
            libraryOpLogService.addOpLog(opLog);
            return super.getAjaxResult(requestReport, ResponseCode.OK, "点赞成功", null);
        } else {
            //取消点赞
            Map opLog1 = (Map)list.get(0);
            libraryOpLogService.deleteOpLogById((String)opLog1.get("id"));
            return super.getAjaxResult(requestReport, ResponseCode.OK, "点赞已取消", null);
        }
    }


    /**
     * 打赏J币
     */
    @RequestMapping("rewardJB")
    public @ResponseBody ResponseReport rewardJB(@RequestBody RequestReport requestReport) {

        CommandInfo command = requestReport.getCommandInfo();
        UserProperty userProperty = requestReport.getUserProperty();
        String libraryId = command.getData().get("libraryId") != null ? command.getData().get("libraryId").toString() : "";
        String jCount = command.getData().get("jCount") != null ? command.getData().get("jCount").toString() : "";
        String userId = userProperty.getUserId();
        if (StringUtil.isEmpty(libraryId)) {
            return super.getAjaxResult(requestReport, ResponseCode.NotSupport, "文章id不能为空", null);
        }
        if (StringUtil.isEmpty(userId)) {
            return super.getAjaxResult(requestReport, ResponseCode.NotSupport, "用户id不能为空", null);
        }
        if (StringUtil.isEmpty(jCount)) {
            return super.getAjaxResult(requestReport, ResponseCode.NotSupport, "J币不能为空", null);
        }

        TjyLibrary tl = libraryService.getTjyLibraryByid(libraryId);
        //先减去自己的j币
        WxUser user = wxUserService.selectById(userId);
        Double surplusJb = DoubleUtil.sub(user.getJbAmount()==null?0:user.getJbAmount(), Double.valueOf(jCount));
        if(surplusJb<0){
            return super.getAjaxResult(requestReport, ResponseCode.NotSupport, "J币不足", null);
        }else{
            user.setJbAmount(surplusJb);
            wxUserService.updateWxUser(user);
            WalletLog log = new WalletLog();
            log.setCreateTime(new Date());
            log.setType("2");
            log.setPdType("2");
            log.setUserId(userId);
            log.setAmount(Double.valueOf(jCount));
            log.setRemark("打赏文章");
            log.setPayStatus("1");
            log.setYeAmount(user.getJbAmount());
            log.setBusinessType(17);
            log.setSourceUser(tl.getCreateUserId());
            log.setFkId(libraryId);
            walletLogService.addWalletLog(log);
        }
        //给文章默认打赏者加上j币
        WxUser userf = wxUserService.selectById(tl.getRewardUser());
        userf.setJbAmount(DoubleUtil.add(userf.getJbAmount(), Double.valueOf(jCount)));
        wxUserService.updateWxUser(userf);
        WalletLog logf = new WalletLog();
        logf.setCreateTime(new Date());
        logf.setType("2");
        logf.setPdType("1");
        logf.setUserId(tl.getCreateUserId());
        logf.setAmount(Double.valueOf(jCount));
        logf.setRemark("获得打赏");
        logf.setPayStatus("1");
        logf.setYeAmount(userf.getJbAmount());
        logf.setBusinessType(15);
        logf.setSourceUser(userId);
        walletLogService.addWalletLog(logf);

        return super.getAjaxResult(requestReport, ResponseCode.OK, "打赏成功", null);
    }



}
