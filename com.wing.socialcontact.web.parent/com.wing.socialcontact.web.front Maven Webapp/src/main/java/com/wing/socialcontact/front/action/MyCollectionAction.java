package com.wing.socialcontact.front.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wing.socialcontact.common.action.BaseAction;
import com.wing.socialcontact.common.model.Member;
import com.wing.socialcontact.service.wx.api.IMyCollectionService;
import com.wing.socialcontact.util.Constants;

/**
 * 
 * @author zhangzheng
 *
 * type 定义  必须是大于0整数    已用  1：文库    2：会所  3:活动  4:直播
 *
 */
@Controller
@RequestMapping("mycollection/m/")
public class MyCollectionAction extends BaseAction {
	
	@Autowired
	private IMyCollectionService  myCollectionService;

	/**
	 * 添加到收藏
	 * libraryid
	 */
	@RequestMapping(value="/add")
	public @ResponseBody Map addCollect(HttpServletRequest request,HttpServletResponse response,
			String id ,Integer type) throws IOException {
		
		Member me = (Member) request.getSession().getAttribute("me");
		if (null == me) {
			return super.getAjaxResult("302", "登录超时，请重新登录", null);
		}
		String userId = me.getId();
		if (StringUtils.isEmpty(userId)) {
			return super.getAjaxResult("302", "登录超时，请重新登录", null);
		}
		if (StringUtils.isEmpty(id)) {
			return super.getAjaxResult("999", "参数错误", null);
		}
		if (type<1) {
			return super.getAjaxResult("999", "参数错误", null);
		}
		/**
		 * 判断是否收藏过
		 */
		if(myCollectionService.iscollected(userId,id,type)){
			return super.getSuccessAjaxResult("此内容已经关注过了哦！",null);
		}
		/**
		 * 添加到收藏
		 */
		if(myCollectionService.addcollect(userId,id,type)){
			return super.getSuccessAjaxResult(Constants.AJAX_MSG_SUCCESS,null);
		}
		return super.getSuccessAjaxResult(Constants.AJAX_MSG_ERROR,null);
	}
	
	/**
	 * 我的收藏
	 */
	@RequestMapping(value="/mycollection")
	public @ResponseBody Map CollectList(HttpServletRequest request,HttpServletResponse response, Integer type
			,Integer page,Integer size) throws IOException {
		
		Member me = (Member) request.getSession().getAttribute("me");
		if (null == me) {
			return super.getAjaxResult("302", "登录超时，请重新登录", null);
		}
		String userId = me.getId();
		if (StringUtils.isEmpty(userId)) {
			return super.getAjaxResult("302", "登录超时，请重新登录", null);
		}
		if (StringUtils.isEmpty(type)||type<1) {
			return super.getAjaxResult("999", "参数错误", null);
		}
		if (StringUtils.isEmpty(page)||page<1) {
			page = 1;
		}
		if (StringUtils.isEmpty(size)||size<1) {
			size = 10;
		}
		List<Map> res= new ArrayList<Map>();
		res =  myCollectionService.getCollections(userId,type,page,size);
		return super.getSuccessAjaxResult("操作成功！",res);
	}
	/**
	 * 取消收藏
	 */
	
	@RequestMapping(value="/del")
	public @ResponseBody Map delCollect(HttpServletRequest request,HttpServletResponse response,String id, Integer type) throws IOException {
		
		Member me = (Member) request.getSession().getAttribute("me");
		if (null == me) {
			return super.getAjaxResult("302", "登录超时，请重新登录", null);
		}
		String userId = me.getId();
		if (StringUtils.isEmpty(userId)) {
			return super.getAjaxResult("302", "登录超时，请重新登录", null);
		}
		//String userId = "402881f73e1c4ba4013e1c4c08470001";
		if (StringUtils.isEmpty(id)) {
			return super.getAjaxResult("999", "参数错误", null);
		}
		if (type<1) {
			return super.getAjaxResult("999", "参数错误", null);
		}
		/**
		 * 取消收藏
		 */
		if(myCollectionService.delCollection(id,userId,type)){
			return super.getSuccessAjaxResult(Constants.AJAX_MSG_SUCCESS,null);
		}
		return super.getSuccessAjaxResult(Constants.AJAX_MSG_ERROR,null);
	}
}
