package com.wing.socialcontact.front.action;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tojoycloud.common.report.ResponseCode;
import com.tojoycloud.common.report.base.BaseAppAction;
import com.tojoycloud.report.RequestReport;
import com.tojoycloud.report.ResponseReport;
import com.wing.socialcontact.service.wx.api.IUserLatestvistorService;
import com.wing.socialcontact.service.wx.bean.UserLatestvistor;
/**
 * APP消息页面接口controller
 *
 */
@Controller
@RequestMapping("/m/app/message")
public class AppMessageAction extends BaseAppAction {
	
	@Autowired
	private IUserLatestvistorService userLatestvistorService;
	/**
	 * 系统消息数量
	 */
	/**
	 * 活动消息数量
	 */
	/**
	 * 打招呼数量
	 */
	/**
	 * 获取我的最近访客信息数目
	 * 
	 * @return
	 */
	@RequestMapping("vistorsCount")
	public @ResponseBody ResponseReport vistorsCount(@RequestBody RequestReport rr,HttpSession session, HttpServletResponse response) throws IOException{
		String userId =rr.getUserProperty().getUserId();
		if (userId == null || "".equals(userId)) {
			return super.getAjaxResult(rr,ResponseCode.NotSupport, "未登录", null);
		}
		try{
			UserLatestvistor ul = new UserLatestvistor();
			ul.setStatus(0);
			ul.setUserId(userId);
			Map res = new HashMap();
			List latestVistorList = userLatestvistorService.selectAllUserLatestvistor(ul);
			int o = 0;
			if (null != latestVistorList) {
				o = latestVistorList.size();
			}
			res.put("visitorCount", o);
			return super.getAjaxResult(rr,ResponseCode.OK, "获取成功", res);
		}catch(Exception e){
			return super.getAjaxResult(rr,ResponseCode.Error, "获取失败！", null);
		}
	}
	
	/**
	 * 访问记录
	 */
	@RequestMapping("visitorList")
	public  @ResponseBody ResponseReport visitorList(@RequestBody RequestReport rr,HttpSession session, HttpServletResponse response) throws IOException{
		String userId =rr.getUserProperty().getUserId();
		String pageSize = rr.getDataValue("pageSize");
		String pageNum = rr.getDataValue("pageNum");
		if (userId == null || "".equals(userId)) {
			return super.getAjaxResult(rr,ResponseCode.NotSupport, "未登录", null);
		}
		if (pageSize == null || "".equals(pageSize)) {
			return super.getAjaxResult(rr,ResponseCode.NotSupport, "参数错误", null);
		}
		if (pageNum == null || "".equals(pageNum)) {
			return super.getAjaxResult(rr,ResponseCode.NotSupport, "参数错误", null);
		}
		try{
			List res = userLatestvistorService.selectLatestVistors(userId, null,Integer.parseInt(pageNum), Integer.parseInt(pageSize));
			//标为已读
			UserLatestvistor u = new UserLatestvistor();
			u.setStatus(0);// 未读
			u.setUserId(userId);
			List<Map<String, Object>> smc = userLatestvistorService.selectAllUserLatestvistor(u);
			for (Map<String, Object> mes : smc) {
				UserLatestvistor ms = userLatestvistorService.selectByPrimaryKey((String)mes.get("id"));
				ms.setStatus(1);
				userLatestvistorService.updateUserLatestvistor(ms);
			}
			return super.getAjaxResult(rr,ResponseCode.OK, "获取成功", res);
		}catch(Exception e){
			return super.getAjaxResult(rr,ResponseCode.Error, "获取失败！", null);
		}
	}
	/**
	 * 删除最近访客接口
	 */
	/**
	 * 访问记录
	 */
	@RequestMapping("deleteVisitor")
	public  @ResponseBody ResponseReport deleteVisitor(@RequestBody RequestReport rr,HttpSession session, HttpServletResponse response) throws IOException{
		String userId =rr.getUserProperty().getUserId();
		String id = rr.getDataValue("id");
		String pageNum = rr.getDataValue("pageNum");
		if (userId == null || "".equals(userId)) {
			return super.getAjaxResult(rr,ResponseCode.NotSupport, "未登录", null);
		}
		if (id == null || "".equals(id)) {
			return super.getAjaxResult(rr,ResponseCode.NotSupport, "参数错误", null);
		}
		try{
			UserLatestvistor  userLatestvistor =userLatestvistorService.selectById(id);
			if(userLatestvistor==null){
				return super.getAjaxResult(rr,ResponseCode.Error, "参数错误，不存在此记录！", null);
			}
			userLatestvistor.setDeleted(1);
			userLatestvistorService.updateUserLatestvistor(userLatestvistor);
			return super.getAjaxResult(rr,ResponseCode.OK, "删除成功", null);
		}catch(Exception e){
			return super.getAjaxResult(rr,ResponseCode.Error, "删除失败！", null);
		}
	}
	/**
	 * 消息首页聊天列表
	 */

}
