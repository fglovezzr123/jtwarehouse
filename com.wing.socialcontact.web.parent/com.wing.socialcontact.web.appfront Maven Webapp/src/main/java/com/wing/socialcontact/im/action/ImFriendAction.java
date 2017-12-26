/**  
 * @Title: DataPermissionsAction.java
 * @date 2016-10-18 下午4:05:33
 * @Copyright: 2016 
 */
package com.wing.socialcontact.im.action;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tojoycloud.common.ConstantDefinition;
import com.tojoycloud.common.report.ResponseCode;
import com.tojoycloud.common.report.base.BaseAppAction;
import com.tojoycloud.report.RequestReport;
import com.tojoycloud.report.ResponseReport;
import com.wing.socialcontact.config.MsgConfig;
import com.wing.socialcontact.front.util.ApplicationPath;
import com.wing.socialcontact.service.im.api.IImFriendService;
import com.wing.socialcontact.service.im.bean.ImFriend;
import com.wing.socialcontact.util.im.IMUtil;

/**
 * 好友接口
 * 
 * @author xuxinyuan
 * @version 1.0
 *
 */
@Controller
@RequestMapping("/im/m/friend")
public class ImFriendAction extends BaseAppAction {

	@Autowired
	private IImFriendService imFriendService;
	
	private String imPrefix = ApplicationPath.getParameter(ConstantDefinition.IM_PREFIX);

	/**
	 * 删除好友
	 * 
	 * @param rr
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("delete")
	public @ResponseBody ResponseReport delete(@RequestBody RequestReport rr, HttpServletRequest request)
			throws IOException {

		String fromId = rr.getDataValue("fromId");
		String toId = rr.getDataValue("toId");

		try {
			String requestStr = imFriendService.deleteEachOtherFriendByUserAndFriend(fromId, toId);

			if (requestStr.equals(MsgConfig.MSG_KEY_SUCCESS)) {
				// 通知网易IM
				IMUtil.delFriend(imPrefix + fromId, imPrefix + toId);
			}
			return super.getSuccessAjaxResult(rr, "删除成功！", requestStr);
		} catch (RuntimeException e) {
			return super.getAjaxResult(rr, ResponseCode.Error, e.getMessage(), null);
		}

	}

	/**
	 * 修改好友备注信息
	 * 
	 * @param request
	 * @param imFriendId
	 * @param friendMemo
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("update")
	public @ResponseBody ResponseReport update(@RequestBody RequestReport rr, HttpServletRequest request)
			throws IOException {

		String fromId = rr.getDataValue("fromId");
		String toId = rr.getDataValue("toId");
		String alias = rr.getDataValue("alias");
		String ex = rr.getDataValue("ex");

		boolean flag = imFriendService.isMyFriend(toId, fromId);
		if (flag) {
			ImFriend imFriend = new ImFriend();
			imFriend.setId(toId);
			imFriend.setFriendMemo(alias);
			String resultStr = imFriendService.updateFriendUserInfo(imFriend);
			if (resultStr.equals(MsgConfig.MSG_KEY_SUCCESS)) {
				// 通知网易IM
				IMUtil.updateFriend(imPrefix + fromId, imPrefix + toId, alias, ex);
			}
			return super.getSuccessAjaxResult(rr, "更新成功", null);
		} else {
			return super.getAjaxResult(rr, ResponseCode.Error, "只能修改自己好友的备注信息", "");
		}

	}

}
