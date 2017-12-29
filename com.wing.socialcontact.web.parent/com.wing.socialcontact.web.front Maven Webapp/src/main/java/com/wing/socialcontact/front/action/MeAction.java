/**  
 * @Title: DataPermissionsAction.java
 * @date 2016-10-18 下午4:05:33
 * @Copyright: 2016 
 */
package com.wing.socialcontact.front.action;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wing.socialcontact.common.action.BaseAction;
import com.wing.socialcontact.common.model.Member;
import com.wing.socialcontact.service.wx.api.IMessageInfoService;
import com.wing.socialcontact.service.wx.api.IWxUserService;
import com.wing.socialcontact.service.wx.bean.WxUser;

/**
 * 数据权限管理
 * 
 * @author dijuli
 * @version 1.0
 *
 */
@Controller
@RequestMapping("/m")
public class MeAction extends BaseAction {

	@Autowired
	private IMessageInfoService messageInfoService;
	
	@Autowired
	private IWxUserService wxUserService;
	/**
	 * 测试
	 * 
	 * @return
	 */
	@RequestMapping("test")
	public @ResponseBody Map findMyFriendListByUserId(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
//		TaskQueue tq = TaskQueueManager.get(TaskQueueManager.SMS_QUEUE);
//		tq.pushTask("test");
//		String task = tq.popTask();
//		System.out.println("---" + tq.popTask());
//		while (task != null) {
//			task = tq.popTask();
//		}
		messageInfoService.addMessageInfo(null);
		return super.getSuccessAjaxResult("seesionid:" + request.getSession().getId());
	}

	
	
	
	
}
