/**
 * 
 */
package com.wing.socialcontact.im.action;

import javax.servlet.http.HttpServletRequest;

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
import com.wing.socialcontact.front.util.ApplicationPath;
import com.wing.socialcontact.util.im.IMUtil;

/**
 * @author zhangpengzhi
 *
 */
@Controller
@RequestMapping("/im/m/notify")
public class ImCustomNotifyAction extends BaseAppAction {

	private String imPrefix = ApplicationPath.getParameter(ConstantDefinition.IM_PREFIX);

	/**
	 * 发送自定义系统通知
	 * 
	 * @param rr
	 * @param request
	 * @return
	 */
	@RequestMapping("sendAttachMsg")
	public @ResponseBody ResponseReport sendAttachMsg(@RequestBody RequestReport rr, HttpServletRequest request) {

		String from = rr.getDataValue("from");
		String msgType = rr.getDataValue("msgType");
		String to = rr.getDataValue("to");
		String attach = rr.getDataValue("attach");
		String pushContent = rr.getDataValue("pushContent");
		String payload = rr.getDataValue("payload");
		String sound = rr.getDataValue("sound");
		String save = rr.getDataValue("save");
		String option = rr.getDataValue("option");

		try {
			// TODO 入库记录

			String resMsg = IMUtil.sendAttachMsg(imPrefix + from, msgType, to, attach, pushContent, payload, sound,
					save, option);
			JSONObject jsonObject = JSONObject.parseObject(resMsg);

			if (null != jsonObject && "200".equals(jsonObject.getString("code"))) {
				return super.getSuccessAjaxResult(rr, "成功", null);
			}
			return super.getAjaxResult(rr, ResponseCode.Error, jsonObject.getString("desc"), jsonObject);
		} catch (Exception e) {
			return super.getAjaxResult(rr, ResponseCode.Error, e.getMessage(), null);
		}
	}
}
