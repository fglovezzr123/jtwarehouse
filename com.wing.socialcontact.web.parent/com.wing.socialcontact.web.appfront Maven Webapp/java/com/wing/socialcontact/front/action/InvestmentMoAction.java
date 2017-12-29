package com.wing.socialcontact.front.action;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wing.socialcontact.common.action.BaseAction;
import com.wing.socialcontact.common.model.Member;
import com.wing.socialcontact.front.util.EsapiTest;
import com.wing.socialcontact.service.wx.api.IInvestmentClassService;
import com.wing.socialcontact.service.wx.api.IInvestmentIntentionService;
import com.wing.socialcontact.service.wx.api.IInvestmentService;
import com.wing.socialcontact.service.wx.api.ITjyUserService;
import com.wing.socialcontact.service.wx.bean.InvestmentIntention;
import com.wing.socialcontact.service.wx.bean.TjyUser;
import com.wing.socialcontact.util.Constants;
/**
 * 投资意向接口
 * @author zhangzheng
 *
 */

@Controller
@RequestMapping("")
public class InvestmentMoAction extends BaseAction {

	@Autowired
	private IInvestmentIntentionService investmentIntentionService;
	@Autowired
	private IInvestmentClassService investmentClassService;
	@Autowired
	private IInvestmentService investmentService;
	@Autowired
	private ITjyUserService tjyUserService;
	
	/*
	@RequestMapping(value="/classList")
	public @ResponseBody Map classList(HttpServletRequest request,HttpServletResponse response) throws IOException {
		Member me = (Member) request.getSession().getAttribute("me");
		if (null == me) {
			return super.getAjaxResult("302", "登录超时，请重新登录", null);
		}
		String userId = me.getId();
		if (StringUtils.isEmpty(userId)) {
			return super.getAjaxResult("302", "登录超时，请重新登录", null);
		}
		List<Map> ylist =  investmentClassService.getClassList();
		return super.getSuccessAjaxResult("操作成功！",ylist);
	}*/
	/**
	 * 根据类型查询基金投资
	 * 1 定增投资
	 * 2上市孵化
	 * 3上市并购
	 * 为空或不为1、2、3时默认为1
	 */
	@RequestMapping(value="/investment/m/investview")
	public @ResponseBody Map investmentList(HttpServletRequest request,HttpServletResponse response,
			Integer type) throws IOException {
		Member me = (Member) request.getSession().getAttribute("me");
		if (null == me) {
			return super.getAjaxResult("302", "登录超时，请重新登录", null);
		}
		String userId = me.getId();
		if (StringUtils.isEmpty(userId)) {
			return super.getAjaxResult("302", "登录超时，请重新登录", null);
		}
		String classId = "定增投资";
		if (StringUtils.isEmpty(type)) {
			type=1;
		}
		if(type == 2){
			classId = "上市孵化";
		}else if(type == 3){
			classId = "上市并购";
		}
		List<Map> list =  investmentService.getinvestmentList(classId);
		Map map = new HashMap();
		if(list.size()>0){
			map = list.get(0);
		}
		return super.getSuccessAjaxResult("操作成功！",map);
	}
	/**
	 *新增投资顾问
	 * @param request
	 * @param response
	 * @param username   联系人
	 * @param phone  联系电话
	 * @param message  留言
	 * @param position 预算
	 * @param classnames 分类名称
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value="/add/investment/m/addInvestment")
	public @ResponseBody Map addInvestment(HttpServletRequest request,HttpServletResponse response,
			String username,String phone,String message,Double position,String className) throws IOException {
		Member me = (Member) request.getSession().getAttribute("me");
		if (null == me) {
			return super.getAjaxResult("302", "登录超时，请重新登录", null);
		}
		String userId = me.getId();
		if (StringUtils.isEmpty(userId)) {
			return super.getAjaxResult("302", "登录超时，请重新登录", null);
		}
		TjyUser tjyUser=tjyUserService.selectByPrimaryKey(userId);
		if(!"1".equals(tjyUser.getIsRealname()+"")){
			return super.getAjaxResult("600", "您还未认证", null);
		}
		if (StringUtils.isEmpty(username)) {
			return super.getAjaxResult("999", "参数错误", null);
		}
		if (StringUtils.isEmpty(phone)) {
			return super.getAjaxResult("999", "参数错误", null);
		}
		if (StringUtils.isEmpty(message)) {
			return super.getAjaxResult("999", "参数错误", null);
		}
		if (StringUtils.isEmpty(position)) {
			return super.getAjaxResult("999", "参数错误", null);
		}
		if (StringUtils.isEmpty(className)) {
			return super.getAjaxResult("999", "参数错误", null);
		}
		username = EsapiTest.stripXSS(username);
		phone = EsapiTest.stripXSS(phone);
		message = EsapiTest.stripXSS(message);
		className = EsapiTest.stripXSS(className);
		InvestmentIntention dto = new InvestmentIntention();
		dto.setMessage(message);
		dto.setClassName(className);
		dto.setCreateTime(new Date());
		dto.setCreateUserId(userId);
		dto.setDeleted(0);
		dto.setStatus(1);
		dto.setPhone(phone);
		dto.setPosition(position);
		dto.setUserName(username);
		boolean res = investmentIntentionService.addInvestment(dto);
		if (res) {
			return super.getSuccessAjaxResult(Constants.AJAX_MSG_SUCCESS,null);
		}
		return super.getSuccessAjaxResult(Constants.AJAX_MSG_ERROR, null);
	}
	/**
	 * 投资顾问列表
	 */
	@RequestMapping(value="/investment/m/intentionList")
	public @ResponseBody Map intentionList(HttpServletRequest request,HttpServletResponse response,
			Integer page,Integer size) throws IOException {
		Member me = (Member) request.getSession().getAttribute("me");
		if (null == me) {
			return super.getAjaxResult("302", "登录超时，请重新登录", null);
		}
		String userId = me.getId();
		if (StringUtils.isEmpty(userId)) {
			return super.getAjaxResult("302", "登录超时，请重新登录", null);
		}
		if (StringUtils.isEmpty(page)||page<1) {
			page = 1;
		}
		if (StringUtils.isEmpty(size)||size<1) {
			size = 10;
		}
		List<Map> list =  investmentIntentionService.getinvestmentList(userId,page,size);
		return super.getSuccessAjaxResult("操作成功！",list);
	}
}
