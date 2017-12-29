package com.wing.enterprise.action;

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
/**
 * 
 * <p>Title:客户留言管理 </p>
 * <p>Description: </p>
 * <p>Company: </p> 
 * @author zhangzheng
 * @Date  2017年5月9日 下午8:30:27
 */
@Controller
@RequestMapping("qfy/message")
public class CustomerMessageAction extends BaseAction {

	@Autowired
	private ILeaveMsgService leaveMsgService;
	/**
	 * 条件查询留言
	 * 
	 * @return
	 */
	@RequiresPermissions("message:read")
	@RequestMapping("load")
	public String load(ModelMap map){
		return "qfy/message/list";
	
	}
	@RequiresPermissions("message:read")
	@RequestMapping("query")
	public ModelAndView query(PageParam param,String nickname,LeaveMsg leaveMsg){
		return ajaxJsonEscape(leaveMsgService.selectAllLeaveMsg(param,nickname,leaveMsg));
	}
	/**
    * 留言删除
    */
   @RequiresPermissions("message:delete")
   @RequestMapping("del")
   public ModelAndView del(String[] ids){
       return ajaxDone(leaveMsgService.deleteMsgs(ids));
   }
}
