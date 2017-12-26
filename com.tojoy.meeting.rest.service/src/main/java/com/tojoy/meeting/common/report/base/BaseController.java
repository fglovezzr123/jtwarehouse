package com.tojoy.meeting.common.report.base;

import com.tojoy.meeting.common.report.ResponseCode;
import com.tojoy.meeting.report.RequestReport;
import com.tojoy.meeting.report.ResponseReport;

/**
 * 
 * 类名：BaseAction
 * 功能：action 父类
 * 详细：所有action类的父类 一些常用方法
 * 版本：1.0
 *
 */
public abstract class BaseController {
	
	/**
	 * 自定义状态码 和自定义提示信息，
	 * @return
	 */
	protected ResponseReport getAjaxResult(RequestReport rr, Byte code, String msg, Object dataObj) {
		ResponseReport res = new ResponseReport();
		res.setResponseCode(code);
		res.setResponseTips(msg);
		res.setDataObj(dataObj);
		CommandInfo commandInfo = rr.getCommandInfo();
		commandInfo.setData(null);
		res.setCommandInfo(commandInfo);
		return res;
	}
	protected ResponseReport getSuccessAjaxResult(RequestReport rr,String msg,Object dataobj) {
		ResponseReport res = new ResponseReport();
		res.setResponseCode(ResponseCode.OK);
		res.setResponseTips(msg);
		res.setDataObj(dataobj);
		CommandInfo commandInfo = rr.getCommandInfo();
		commandInfo.setData(null);
		res.setCommandInfo(commandInfo);
		return res;
	}
	protected ResponseReport getSuccessAjaxResult(RequestReport rr,String msg) {
		ResponseReport res = new ResponseReport();
		res.setResponseCode(ResponseCode.OK);
		res.setResponseTips(msg);
		CommandInfo commandInfo = rr.getCommandInfo();
		commandInfo.setData(null);
		res.setCommandInfo(commandInfo);
		return res;
	}
	protected ResponseReport getSuccessAjaxResult(RequestReport rr) {
		ResponseReport res = new ResponseReport();
		res.setResponseCode(ResponseCode.OK);
		res.setResponseTips("操作成功！");
		CommandInfo commandInfo = rr.getCommandInfo();
		commandInfo.setData(null);
		res.setCommandInfo(commandInfo);
		return res;
	}
	
	
}
