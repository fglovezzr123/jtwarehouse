package com.wing.socialcontact.front.action;

import com.tojoycloud.common.report.ResponseCode;
import com.tojoycloud.common.report.base.BaseAppAction;
import com.tojoycloud.common.report.base.CommandInfo;
import com.tojoycloud.common.report.base.UserProperty;
import com.tojoycloud.report.RequestReport;
import com.tojoycloud.report.ResponseReport;
import com.wing.socialcontact.common.model.Member;
import com.wing.socialcontact.service.wx.api.IMyCollectionService;
import com.wing.socialcontact.util.Constants;
import com.wing.socialcontact.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * 收藏 controller
 * @author wangyansheng
 *
 * type 定义  必须是大于0整数    已用  1：文库    2：会所  3:活动  4:直播
 *
 */
@Controller
@RequestMapping("/m/myCollection")
public class MyCollectionAction extends BaseAppAction {
	
	@Autowired
	private IMyCollectionService  myCollectionService;

	/**
	 * 添加到收藏
	 * libraryid
	 */
	@RequestMapping(value="/add")
	public @ResponseBody
	ResponseReport addCollect(@RequestBody RequestReport requestReport) throws IOException {

		CommandInfo command = requestReport.getCommandInfo();
		String collectionId = command.getData().get("collectionId") != null ? command.getData().get("collectionId").toString() : "";
		Integer type = command.getData().get("type") != null ? Integer.valueOf(command.getData().get("type").toString()) : 0;
		UserProperty userProperty = requestReport.getUserProperty();
		String userId = userProperty.getUserId();

		if (StringUtil.isEmpty(userId)) {
			return super.getAjaxResult(requestReport, ResponseCode.NotSupport, "用户id不能为空", null);
		}
		if (StringUtil.isEmpty(collectionId)) {
			return super.getAjaxResult(requestReport, ResponseCode.NotSupport, "被收藏id不能为空", null);
		}
		if (type<1) {
			return super.getAjaxResult(requestReport, ResponseCode.NotSupport, "type参数错误", null);
		}

		//判断是否收藏过
		if(myCollectionService.iscollected(userId,collectionId,type)){
			return super.getAjaxResult(requestReport, ResponseCode.OK, "此内容已经收藏过了哦!", null);
		}
		//添加到收藏
		if(myCollectionService.addcollect(userId,collectionId,type)){
			return super.getAjaxResult(requestReport, ResponseCode.OK, "收藏成功!", null);
		}
		return super.getAjaxResult(requestReport, ResponseCode.Error, "收藏失败!", null);
	}
	

	/**
	 * 取消收藏
	 */
	
	@RequestMapping(value="/cancel")
	public @ResponseBody
	ResponseReport cancel(@RequestBody RequestReport requestReport) throws IOException {

		CommandInfo command = requestReport.getCommandInfo();
		String collectionId = command.getData().get("collectionId") != null ? command.getData().get("collectionId").toString() : "";
		Integer type = command.getData().get("type") != null ? Integer.valueOf(command.getData().get("type").toString()) : 0;
		UserProperty userProperty = requestReport.getUserProperty();
		String userId = userProperty.getUserId();

		if (StringUtil.isEmpty(userId)) {
			return super.getAjaxResult(requestReport, ResponseCode.NotSupport, "用户id不能为空", null);
		}
		if (StringUtil.isEmpty(collectionId)) {
			return super.getAjaxResult(requestReport, ResponseCode.NotSupport, "被收藏id不能为空", null);
		}
		if (type<1) {
			return super.getAjaxResult(requestReport, ResponseCode.NotSupport, "type参数错误", null);
		}

		/**
		 * 取消收藏
		 */
		if(myCollectionService.delCollection(collectionId,userId,type)){
			return super.getAjaxResult(requestReport, ResponseCode.OK, "取消收藏成功!", null);
		}
		return super.getAjaxResult(requestReport, ResponseCode.Error, "取消收藏失败!", null);
	}
}
