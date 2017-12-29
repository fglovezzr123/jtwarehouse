package com.wing.enterprise.action;

import java.io.IOException;
import java.util.Date;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.com.wing.enterprise.bean.SysMessage;
import org.com.wing.enterprise.service.ISysMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.wing.socialcontact.common.model.PageParam;
import com.wing.socialcontact.sys.action.BaseAction;
import com.wing.socialcontact.util.ServletUtil;
/**
 * 
 * <p>Title:消息管理 </p>
 * <p>Description: </p>
 * <p>Company: </p> 
 * @author zhangzheng
 * @Date  2017年5月9日 下午8:57:21
 */
@Controller
@RequestMapping("qfy/sysmessage")
public class SendMessageAction extends BaseAction {

	@Autowired
	private ISysMessageService sysMessageService;
	
	/**
     * 消息列表
     */
    @RequiresPermissions("sysmessage:read")
    @RequestMapping("load")
    public String entryload(){
        return "qfy/sysmessage/list";
    }
    
    /**
     * 消息列表查询
     * @param param
     * @param EntryPrise
     * @return
     */
    @RequiresPermissions("sysmessage:read")
    @RequestMapping("query")
    public ModelAndView entryquery(PageParam param,SysMessage sysMessage){
        return ajaxJsonEscape(sysMessageService.selsysMessage(param, sysMessage));
    }
    
    /**
     * 消息新增
     */
    @RequiresPermissions("sysmessage:add")
    @RequestMapping("addPage")
    public String entryaddPage(ModelMap map){
        return "qfy/sysmessage/add";
    }
    
    @RequiresPermissions("sysmessage:add")
    @RequestMapping("add")
    public ModelAndView entryadd(SysMessage dto,Errors errors) throws IOException{
        if(errors.hasErrors()) {  
            ModelAndView mav=getValidationMessage(errors);
            if(mav!=null)return mav;
        }
        dto.setCreateTime(new Date());
        dto.setCreateUserId(ServletUtil.getMember().getId());
        dto.setStatus(0);
        return ajaxDone(sysMessageService.addsysMessage(dto));
        
    }
    /**
    * 消息删除
    */
   @RequiresPermissions("sysmessage:delete")
   @RequestMapping("del")
   public ModelAndView del(String[] ids){
       return ajaxDone(sysMessageService.deleteSysMsg(ids));
   }
}
