package com.wing.enterprise.action;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.com.wing.enterprise.bean.EntryPrise;
import org.com.wing.enterprise.bean.EntryStaticClick;
import org.com.wing.enterprise.service.IEntryPriseService;
import org.com.wing.enterprise.service.IEntryStaticClickService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wing.socialcontact.common.action.BaseAction;

/**
 * 企服点击统计管理
 * 
 * 
 * @ClassName: QfyStaticClickAction
 * @Description: TODO
 * @author: sino
 * @date:2017年5月13日
 */
@Controller
@RequestMapping("/m/qfy/")
public class QfyStaticClickAction extends BaseAction {

    @Autowired
    private IEntryStaticClickService entryStaticClickService;
    @Autowired
    private IEntryPriseService entryPriseService;
	/**
     * 增加点击次数
     * @param request
     * @param response
     * @param uniqueId
     * @return
     */
    @RequestMapping("staticClick")
    public @ResponseBody Map staticClick(HttpServletRequest request, HttpServletResponse response,String entryId,int type) {
        String userId = checkLogin(request);
        if(userId == null){
            return super.getAjaxResult("302", "登录超时，请重新登录", null); 
        }
        
        //根据用户ID、企服ID查询此用户是否点击过
        List<EntryStaticClick> lst = entryStaticClickService.selStaticClick(entryId, userId);
        
        //type=1是电话咨询，type=2是在线咨询
        if(CollectionUtils.isEmpty(lst)){
            //为空的情况直接插入一条记录
            EntryStaticClick esc = new EntryStaticClick();
            esc.setEntryId(entryId);
            esc.setUserId(userId);
            if(type==1){
                esc.setPhoneCount(1);
                esc.setMsgCount(0);
            }else{
                esc.setPhoneCount(0);
                esc.setMsgCount(1);
            }
            EntryPrise ep = entryPriseService.getEntryPriseByid(entryId);
            int count = ep.getServiceCount();
            count++;
            ep.setServiceCount(count);
            entryPriseService.updateEntryPriseByDto(ep);
            entryStaticClickService.addEntryStaticClick(esc);
            return super.getSuccessAjaxResult();
        }else{
            EntryStaticClick esc = lst.get(0);
            int phoneCount = esc.getPhoneCount();
            int msgCount = esc.getMsgCount();
            if(type == 1){
                if(phoneCount <= 0){
                    //更新
                    esc.setPhoneCount(1);
                    entryStaticClickService.updateEntryStaticClick(esc);
                    return super.getSuccessAjaxResult();
                }
            }else{
                if(msgCount <= 0){
                    //更新
                    esc.setMsgCount(1);
                    entryStaticClickService.updateEntryStaticClick(esc);
                    return super.getSuccessAjaxResult();
                }
            }
        }
        return super.getSuccessAjaxResult();
    }
}
