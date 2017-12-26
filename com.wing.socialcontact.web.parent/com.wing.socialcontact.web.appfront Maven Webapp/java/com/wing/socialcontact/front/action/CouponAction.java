package com.wing.socialcontact.front.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
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
import com.wing.socialcontact.service.wx.api.ICouponGenerateService;
import com.wing.socialcontact.service.wx.api.ICouponLogService;
import com.wing.socialcontact.service.wx.api.ICouponService;
import com.wing.socialcontact.service.wx.api.IWxUserService;
import com.wing.socialcontact.service.wx.bean.CouponLog;
import com.wing.socialcontact.util.Constants;

/**
 * 优惠券管理
 * @author zhangfan
 *
 */
@Controller
@RequestMapping("/m/coupon")
public class CouponAction extends BaseAction{
	
	@Autowired
	private ICouponService couponService; 
	@Autowired
	private ICouponGenerateService couponGenerateService; 
	@Autowired
	private ICouponLogService couponLogService;
	@Autowired
	private IWxUserService wxUserService;
	
	

	/**
	 * 我的钱包优惠券页面
	 * @param map
	 * @param orderMinBuy 订单消费金额
	 * @param useRange 1全平台，2会议，3活动，4互助商城
	 * @param currency    币种   1J币   2RMB  3不限
	 * @return
	 */
	@RequestMapping("/selectPage")
	public String selectPage(ModelMap map,String orderMinBuy,String useRange,Integer currency ){
		map.addAttribute("orderMinBuy", orderMinBuy);
		map.addAttribute("useRange", useRange);
		map.addAttribute("currency", currency);
		return "mine/selectyhq";
	}
	/**
	 * 查询该用户可用优惠券
	 * @param request
	 * @param response
	 * @param orderMinBuy 订单消费金额
	 * @param useRange 1全平台，2会议，3活动，4互助商城
	 * @param currency    币种   1J币   2RMB  3不限
	 * @return
	 */
	@RequestMapping("selCanUseCouponList")
	public @ResponseBody Map selCanUseCouponList(HttpServletRequest request,HttpServletResponse response,String orderMinBuy,String useRange,Integer currency){
		Member me = (Member) request.getSession().getAttribute("me");
		if (null == me) {
			return super.getAjaxResult("302", "登录超时，请重新登录", null);
		}
		String userId = me.getId();
		if (StringUtils.isEmpty(userId)) {
			return super.getAjaxResult("302", "登录超时，请重新登录", null);
		}
		Map res = new HashMap();
		List<Map<String, Object>> list = new ArrayList();
		list = couponLogService.selectCanUseCoupon(userId, 2, useRange, orderMinBuy,currency);
		int count = list.size();
		res.put("count", count);
		res.put("clist", list);
		return super.getSuccessAjaxResult("获取成功！", res);
	}
	/**
	 * 使用优惠券
	 * @param request
	 * @param response
	 * @param orderId 订单号
	 * @param id   优惠券id
	 * @return
	 */
	@RequestMapping("useCoupon")
	public @ResponseBody Map useCoupon(HttpServletRequest request,HttpServletResponse response,String orderId,String id){
		Member me = (Member) request.getSession().getAttribute("me");
		if (null == me) {
			return super.getAjaxResult("302", "登录超时，请重新登录", null);
		}
		String userId = me.getId();
		if (StringUtils.isEmpty(userId)) {
			return super.getAjaxResult("302", "登录超时，请重新登录", null);
		}
		CouponLog cl = couponLogService.selectById(id);
		if(null!=cl){
			cl.setOrderId(orderId);
			cl.setUseStatus(1);
			cl.setUseTime(new Date());
			couponLogService.updateCL(cl);
		}
		return super.getSuccessAjaxResult();
	}
	/**
	 * 我的钱包优惠券页面
	 * @param map
	 * @return
	 */
	@RequestMapping("/myCouponPage")
	public String myCouponPage(ModelMap map){
		return "mine/yhq";
	}
	/**
	 * 我的钱包优惠券列表
	 * @param request
	 * @param response
	 * @param type 1未使用 2已使用 3已过期
	 * @return
	 */
	@RequestMapping("selMyCouponList")
	public @ResponseBody Map selMyCouponList(HttpServletRequest request,HttpServletResponse response,
			String type,Integer page,Integer size){
		Member me = (Member) request.getSession().getAttribute("me");
		if (null == me) {
			return super.getAjaxResult("302", "登录超时，请重新登录", null);
		}
		String userId = me.getId();
		if (StringUtils.isEmpty(userId)) {
			return super.getAjaxResult("302", "登录超时，请重新登录", null);
		}
		Map res = new HashMap();
		List<Map<String, Object>> list = new ArrayList();
		list = couponLogService.selMyCouponList(userId, Integer.valueOf(type),page,size);
		res.put("clist", list);
		return super.getSuccessAjaxResult("获取成功！", res);
	}
}
