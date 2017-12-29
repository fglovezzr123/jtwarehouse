/**  
 * @Title: DataPermissionsAction.java
 * @date 2016-10-18 下午4:05:33
 * @Copyright: 2016 
 */
package com.wing.socialcontact.im.action;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.tojoycloud.common.ConstantDefinition;
import com.tojoycloud.common.report.ResponseCode;
import com.tojoycloud.common.report.base.BaseAppAction;
import com.tojoycloud.report.RequestReport;
import com.tojoycloud.report.ResponseReport;
import com.wing.socialcontact.config.MsgConfig;
import com.wing.socialcontact.front.util.ApplicationPath;
import com.wing.socialcontact.service.im.api.IImFriendrequestService;
import com.wing.socialcontact.service.im.bean.ImFriendrequest;
import com.wing.socialcontact.util.im.IMUtil;

/**
 * 好友请求
 * 
 * @author xuxinyuan
 * @version 1.0
 *
 */
@Controller
@RequestMapping("/im/m/friend")
public class ImFriendRequestAction extends BaseAppAction {

	@Autowired
	private IImFriendrequestService imFriendrequestService;

	private String imPrefix = ApplicationPath.getParameter(ConstantDefinition.IM_PREFIX);

	/**
	 * 添加好友请求
	 * 
	 * @return
	 */
	@RequestMapping("add")
	public @ResponseBody ResponseReport addFriendRequest(@RequestBody RequestReport rr, HttpServletRequest request)
			throws IOException {
		String fromId = rr.getDataValue("fromId").trim();
		String toId = rr.getDataValue("toId").trim();
		String type = rr.getDataValue("type").trim();
		String msg = rr.getDataValue("msg");

		try {
			String procRestultMsg = "";
			JSONObject jsonObject = JSONObject.parseObject(msg);

			Map paramMap = new HashMap();
			paramMap.put("userId", fromId);
			paramMap.put("friendUser", toId);
			ImFriendrequest friendrequest = imFriendrequestService.findRequestByParam(paramMap);

			switch (type) {
			case "2": {
				// 2 表示请求添加好友
				if (null == friendrequest || friendrequest.getStatus().equals(MsgConfig.RELATION_STATUS.REJECT)) {
					String requestStr = imFriendrequestService.saveFriendRequest(fromId, toId,
							jsonObject.getString("message"));
					if (requestStr.equals(MsgConfig.MSG_KEY_SUCCESS)) {
						friendrequest = imFriendrequestService.findRequestByParam(paramMap);
						jsonObject.put("addRequestId", friendrequest.getId());
						procRestultMsg = "请求已发送";
					}
				} else {
					jsonObject.put("addRequestId", friendrequest.getId());
					procRestultMsg = "请求已发送，勿重复提交";
				}
				// 调用IMUtil通知网易IM
				String res = IMUtil.addFriendOne(imPrefix + fromId, imPrefix + toId, type, jsonObject.toJSONString());
				JSONObject object = JSONObject.parseObject(res);
				if ("500".equals(object.getString("code"))) {
					throw new RuntimeException(object.getString("desc"));
				}
				break;
			}
			default: {
				/**
				 * 网易：3 同意、4拒绝 <br >
				 * 业务：2同意、3拒绝
				 */
				String status = type.equals("3") ? "2" : "3"; // 网易IM状态与业务状态转换
				String requestStr = imFriendrequestService.updateFriendRequestStatus(
						jsonObject.getString("addRequestId"), Integer.parseInt(status), jsonObject.getString("name"));
				if (requestStr.equals(MsgConfig.MSG_KEY_SUCCESS)) {
					// 调用IMUtil通知网易IM
					IMUtil.addFriendOne(imPrefix + fromId, imPrefix + toId, type, msg);
					procRestultMsg = "请求已发送";
				}
				break;
			}
			}
			return super.getSuccessAjaxResult(rr, procRestultMsg, jsonObject);
		} catch (RuntimeException e) {
			return super.getAjaxResult(rr, ResponseCode.Error, e.getMessage(), null);
		}
	}
}
