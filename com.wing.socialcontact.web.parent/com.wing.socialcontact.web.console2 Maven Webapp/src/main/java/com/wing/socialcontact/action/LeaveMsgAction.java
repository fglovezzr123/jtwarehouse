package com.wing.socialcontact.action;

import java.util.List;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.wing.socialcontact.common.model.PageParam;
import com.wing.socialcontact.service.wx.api.ILeaveMsgService;
import com.wing.socialcontact.service.wx.bean.LeaveMsg;
import com.wing.socialcontact.sys.action.BaseAction;
import com.wing.socialcontact.sys.bean.ListValues;

/**
 * 大咖管理
 * @author xuxinyuan
 *
 */
@Controller
@RequestMapping("/leaveMsg")
public class LeaveMsgAction extends BaseAction{
	
	@Autowired
	private ILeaveMsgService leaveMsgService;
	/**
	 * 条件查询话题
	 * 
	 * @return
	 */
	@RequiresPermissions("leaveMsg:read")
	@RequestMapping("load")
	public String load(ModelMap map){
		return "leaveMsg/leaveMsg_list";
	
	}
	@RequiresPermissions("leaveMsg:read")
	@RequestMapping("query")
	public ModelAndView query(PageParam param,String nickname,LeaveMsg leaveMsg){
		return ajaxJsonEscape(leaveMsgService.selectAllLeaveMsg(param,  nickname,leaveMsg));
	}
	
	
	
	@RequiresPermissions("leaveMsg:delete")
	@RequestMapping("del")
	public ModelAndView del(String[] ids){	
		return ajaxDone(leaveMsgService.deleteMsgs(ids));
	}
	
	
	
}
