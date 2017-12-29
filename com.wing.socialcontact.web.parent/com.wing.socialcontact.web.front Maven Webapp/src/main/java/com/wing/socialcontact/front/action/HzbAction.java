package com.wing.socialcontact.front.action;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jdom.JDOMException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wing.socialcontact.common.action.BaseAction;
import com.wing.socialcontact.common.model.Member;
import com.wing.socialcontact.config.MsgConfig;
import com.wing.socialcontact.config.OssConfig;
import com.wing.socialcontact.front.util.ApplicationPath;
import com.wing.socialcontact.service.wx.api.IHzbManagerLogService;
import com.wing.socialcontact.service.wx.api.IMessageInfoService;
import com.wing.socialcontact.service.wx.api.IOpenHzbOrderService;
import com.wing.socialcontact.service.wx.api.IOpenHzbPayLogService;
import com.wing.socialcontact.service.wx.api.ITjyUserService;
import com.wing.socialcontact.service.wx.api.IWalletLogService;
import com.wing.socialcontact.service.wx.api.IWxUserService;
import com.wing.socialcontact.service.wx.bean.HzbManagerLog;
import com.wing.socialcontact.service.wx.bean.MessageInfo;
import com.wing.socialcontact.service.wx.bean.OpenHzbOrder;
import com.wing.socialcontact.service.wx.bean.OpenHzbPayLog;
import com.wing.socialcontact.service.wx.bean.TjyUser;
import com.wing.socialcontact.service.wx.bean.WalletLog;
import com.wing.socialcontact.service.wx.bean.WxUser;
import com.wing.socialcontact.sys.bean.ListValues;
import com.wing.socialcontact.sys.service.IListValuesService;
import com.wing.socialcontact.util.AldyMessageUtil;
import com.wing.socialcontact.util.CommUtil;
import com.wing.socialcontact.util.ConfigUtil;
import com.wing.socialcontact.util.Constants;
import com.wing.socialcontact.util.DateUtil;
import com.wing.socialcontact.util.DateUtils;
import com.wing.socialcontact.util.HttpClientUtil;
import com.wing.socialcontact.util.PayCommonUtil;
import com.wing.socialcontact.util.ServletUtil;
import com.wing.socialcontact.util.SpringContextUtil;
import com.wing.socialcontact.util.UUIDGenerator;
import com.wing.socialcontact.util.WxMsmUtil;
import com.wing.socialcontact.util.XMLUtil;

/**
 * 互助宝控制器
 * 
 * @ClassName: JhyAction
 * @Description: TODO
 * @author: zengmin
 * @date:2017年4月2日 下午5:57:56
 */
@Controller
@RequestMapping("/m/hzb")
public class HzbAction extends BaseAction {

	@Autowired
	private IOpenHzbOrderService openHzbOrderService;
	@Autowired
	private IOpenHzbPayLogService openHzbPayLogService;
	@Autowired
	private IListValuesService listValuesService;
	@Autowired
	private IWxUserService wxUserService;
	@Autowired
	private ITjyUserService tjyUserService;
	@Autowired
	private IMessageInfoService messageInfoService;
	@Autowired
	private IHzbManagerLogService hzbManagerLogService;

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		binder.registerCustomEditor(Timestamp.class, new CustomDateEditor(dateFormat, false));
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
	}

	/**
	 * 跳转至开通互助宝页面一
	 * 
	 * @Title: first
	 * @Description: TODO
	 * @param map
	 * @param request
	 * @return
	 * @return: String
	 * @author: zengmin
	 * @date: 2017年7月18日 上午9:59:48
	 */
	@RequestMapping(value = "first")
	public String first(ModelMap map, HttpServletRequest request) {
		String contentPath = ApplicationPath.getParameter("domain");
		String path = request.getContextPath();
		String cp = contentPath + path;
		if (!ServletUtil.isLogin(request)) {
			return "login";
		}
		Member me = ServletUtil.getMember(request);
		WxUser wxUser = wxUserService.selectByPrimaryKey(me.getId());
		// 互助宝已开通
		if (null != wxUser.getHzbOpenFlag() && wxUser.getHzbOpenFlag() == 1) {
			return "redirect:"+cp+"/m/hzb/four.do";
		}
		// 互助宝已停用
		if (null != wxUser.getHzbOpenFlag() && wxUser.getHzbOpenFlag() == 2) {
			map.addAttribute("error_info", "您的互助宝账户已停用，请联系客服");
			return "error";
		}
		// 互助宝已过期，需要重新走开通
		if (null == wxUser.getHzbOpenFlag() || wxUser.getHzbOpenFlag() != 3) {
			OpenHzbOrder order = openHzbOrderService.selectByUserId(me.getId());
			if (null != order) {
				map.addAttribute("order", order);
				if (order.getStatus() == 3) {
					return "redirect:"+cp+"/m/hzb/four.do";
				}
				return "redirect:"+cp+"/m/hzb/third.do";
			}
		}
		return "hzb/first";
	}

	/**
	 * 跳转至开通互助宝页面二
	 * 
	 * @Title: second
	 * @Description: TODO
	 * @param map
	 * @param request
	 * @return
	 * @return: String
	 * @author: zengmin
	 * @date: 2017年7月18日 上午9:59:48
	 */
	@RequestMapping(value = "second")
	public String second(ModelMap map, HttpServletRequest request) {
		return "hzb/second";
	}

	/**
	 * 跳转至开通互助宝页面三
	 * 
	 * @Title: third
	 * @Description: TODO
	 * @param map
	 * @param request
	 * @return
	 * @return: String
	 * @author: zengmin
	 * @date: 2017年7月18日 上午9:59:48
	 */
	@RequestMapping(value = "third")
	public String third(Short level, ModelMap map, HttpServletRequest request) {
		String contentPath = ApplicationPath.getParameter("domain");
		String path = request.getContextPath();
		String cp = contentPath + path;
		if (!ServletUtil.isLogin(request)) {
			return "login";
		}
		Member me = ServletUtil.getMember(request);
		WxUser wxUser = wxUserService.selectByPrimaryKey(me.getId());
		OpenHzbOrder order = openHzbOrderService.selectByUserId(me.getId());
		double yfk = 0;
		double dfk = 0;
		if (null != order) {
			level = order.getLevel();
		}
		if (level == 1) {
			dfk = 20 * 10000;
		} else if (level == 2) {
			dfk = 50 * 10000;
		} else {
			dfk = 100 * 10000;
		}
		if (null == order) {
			// 未选择开通等级
			if (null == level) {
				return "redirect:"+cp+"/m/hzb/first.do";
			}
			if (level < 1 || level > 3) {
				map.addAttribute("error_info", "参数错误");
				return "error";
			}
			order = new OpenHzbOrder();
			order.setYfk(yfk);
			order.setDfk(dfk);
			order.setLevel(level);
			order.setStatus(0);// 未开通
			map.addAttribute("level", level);
		} else {
			// 互助宝已开通
			if (wxUser.getHzbOpenFlag() == 1) {
				return "redirect:"+cp+"/m/hzb/four.do";
			}
			if (order.getStatus() == 3) {
				map.addAttribute("order", order);
				return "redirect:"+cp+"/m/hzb/four.do";
			}
			// 计算已付款及待付款
			List<OpenHzbPayLog> payLogList = order.getPayLogList();
			if (null != payLogList && !payLogList.isEmpty()) {
				for (OpenHzbPayLog openHzbPayLog : payLogList) {
					if (null != openHzbPayLog.getShStatus() && openHzbPayLog.getShStatus() == 1) {
						yfk += openHzbPayLog.getFkMoney();
					}
				}
				dfk -= yfk;
			}
			order.setYfk(yfk);
			order.setDfk(dfk);
		}
		// 获取对公账户--准备从字典
		List dgzhList = listValuesService.selectListByType(8007);
		map.addAttribute("dgzhList", dgzhList);
		OssConfig ossConfig = (OssConfig) SpringContextUtil.getBean("ossConfig");
		String ossurl = ossConfig.getOss_getUrl();
		map.addAttribute("ossurl", ossurl);
		map.addAttribute("order", order);
		map.addAttribute("wxUser", wxUser);
		return "hzb/third";
	}

	/**
	 * 跳转至开通互助宝页面四（已开通）
	 * 
	 * @Title: four
	 * @Description: TODO
	 * @param map
	 * @param request
	 * @return
	 * @return: String
	 * @author: zengmin
	 * @date: 2017年7月18日 上午9:59:48
	 */
	@RequestMapping(value = "four")
	public String four(ModelMap map, HttpServletRequest request) {
		if (!ServletUtil.isLogin(request)) {
			return "login";
		}
		Member me = ServletUtil.getMember(request);
		WxUser wxUser = wxUserService.selectByPrimaryKey(me.getId());
		if (wxUser.getHzbOpenFlag() == 2) {
			map.addAttribute("error_info", "您的互助宝账户已停用，请联系客服");
			return "error";
		} else if (wxUser.getHzbOpenFlag() == 3) {// 互助宝已过期，需要重新开启
			String contentPath = ApplicationPath.getParameter("domain");
			String path = request.getContextPath();
			String cp = contentPath + path;
			return "redirect:"+cp+"/m/hzb/first.do";
		}
		List<HzbManagerLog> logList = hzbManagerLogService.selectByUserId(me.getId());
		map.addAttribute("wxUser", wxUser);
		if(null == wxUser.getHzbOpenFlag() || wxUser.getHzbOpenFlag() == 0){
			OpenHzbOrder order = openHzbOrderService.selectByUserId(me.getId());
			map.addAttribute("order", order);
		}
		double ljcz = 0, ljxf = 0;
		if (null != logList && !logList.isEmpty()) {
			for (HzbManagerLog hzbManagerLog : logList) {
				double money = null == hzbManagerLog.getManagerMoney() ? 0 : hzbManagerLog.getManagerMoney();
				if (hzbManagerLog.getType() == 4 || hzbManagerLog.getType() == 7) {
					ljcz += money;
				} else if (hzbManagerLog.getType() == 8) {
					ljxf += money;
				}
			}
		}
		map.addAttribute("ljcz", ljcz);
		map.addAttribute("ljxf", ljxf);
		return "hzb/four";
	}

	/**
	 * 保存互助宝支付记录
	 * 
	 * @Title: savePayLog
	 * @Description: TODO
	 * @param mobile
	 * @param yz
	 * @param dyz
	 * @param pwd
	 * @param session
	 * @return
	 * @return: Map
	 * @author: zengmin
	 * @date: 2017年4月7日 上午10:15:18
	 */
	@RequestMapping("savePayLog")
	public @ResponseBody Map savePayLog(OpenHzbPayLog hzbPayLog, Short level, HttpServletRequest req) {
		Member me = ServletUtil.getMember(req);
		if (hzbPayLog.getLogType() == 1) {
			OpenHzbOrder order = null;
			String orderId = "";
			if (StringUtils.isEmpty(hzbPayLog.getOrderId())) {
				order = new OpenHzbOrder();
				order.setLevel(level);
				order.setCreateTime(new Date());
				order.setLastTime(new Date());
				order.setStatus(1);// 待支付
				order.setUserId(me.getId());
				orderId = openHzbOrderService.addOpenHzbOrder(order);
			} else {
				orderId = hzbPayLog.getOrderId();
				order = openHzbOrderService.selectByPrimaryKey(hzbPayLog.getOrderId());
			}
			hzbPayLog.setOrderId(orderId);
		}
		hzbPayLog.setShStatus((short) 0);// 待审核
		if (hzbPayLog.getFkType().shortValue() == 1) {
			hzbPayLog.setFkTime(new Date());
			hzbPayLog.setFkStatus((short) 0);// 待付款
		} else {
			hzbPayLog.setFkStatus((short) 1);// 已付款--线下
		}
		hzbPayLog.setUserId(me.getId());
		String logId = openHzbPayLogService.addOpenHzbPayLog(hzbPayLog);
		if (StringUtils.isEmpty(logId)) {
			return super.getAjaxResult("999", "操作失败", null);
		}
		return super.getSuccessAjaxResult("操作成功", logId);
	}

	/**
	 * 互助宝开通订单付款
	 * 
	 * @Title: paycwx
	 * @Description: TODO
	 * @param je--充值金额
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 * @throws JDOMException
	 * @return: String
	 * @author: zengmin
	 * @date: 2017年4月10日 下午1:50:29
	 */
	@RequestMapping("paycwx")
	public @ResponseBody Map paycwx(Double je, String logId, HttpServletRequest request, HttpServletResponse response)
			throws IOException, JDOMException {

		Member me = ServletUtil.getMember(request);
		if (!ServletUtil.isLogin(request)) {
			return super.getAjaxResult("302", "登录超时", null);
		}

		if (je <= 0) {
			return super.getAjaxResult("401", "支付金额必须大于0元", null);
		}
		double czje = je;
		WxUser wxUser = wxUserService.selectByPrimaryKey(me.getId());

		String out_trade_no = logId;
		if (StringUtils.isEmpty(out_trade_no)) {
			return super.getAjaxResult("999", "充值失败", null);
		}
		// 互助宝充值单位万元
		Double tp = czje * 100;
		SortedMap<Object, Object> parameters = new TreeMap<Object, Object>();
		parameters.put("appid", ApplicationPath.getParameter("wx_appid"));
		parameters.put("mch_id", ConfigUtil.MCH_ID);
		parameters.put("nonce_str", PayCommonUtil.CreateNoncestr());
		parameters.put("body", "钱包充值付款");
		parameters.put("out_trade_no", out_trade_no);
		if(Constants.TEST){
			parameters.put("total_fee", 1 + "");
		}else{
			parameters.put("total_fee", tp.intValue() + "");
		}
		parameters.put("spbill_create_ip", request.getRemoteAddr());
		parameters.put("notify_url", ConfigUtil.NOTIFY_HZB_URL);
		parameters.put("trade_type", "JSAPI");
		parameters.put("openid", wxUser.getQqOpenid());
		String sign = PayCommonUtil.createSign("UTF-8", parameters);
		parameters.put("sign", sign);
		System.out.println("parameters:" + parameters);
		String requestXML = PayCommonUtil.getRequestXml(parameters);
		System.out.println("requestXML:" + requestXML);
		String result = HttpClientUtil.sendPostRequest(ConfigUtil.UNIFIED_ORDER_URL, requestXML, true);
		System.out.println("result:" + result);
		SortedMap<Object, Object> map = XMLUtil.doXMLParse(result);// 解析微信返回的信息，以Map形式存储便于取值
		String return_code = (String) map.get("return_code");
		if ("SUCCESS".equals(return_code)) {
			SortedMap<Object, Object> params = new TreeMap<Object, Object>();
			params.put("appId", ApplicationPath.getParameter("wx_appid"));
			params.put("timeStamp", Long.toString(new Date().getTime()));
			params.put("nonceStr", PayCommonUtil.CreateNoncestr());
			params.put("package", "prepay_id=" + map.get("prepay_id"));
			params.put("signType", ConfigUtil.SIGN_TYPE);
			String paySign = PayCommonUtil.createSign("UTF-8", params);
			params.put("packageValue", "prepay_id=" + map.get("prepay_id")); // 这里用packageValue是预防package是关键字在js获取值出错
			params.put("paySign", paySign); // paySign的生成规则和Sign的生成规则一致
			String userAgent = request.getHeader("user-agent");
			char agent = userAgent.charAt(userAgent.indexOf("MicroMessenger") + 15);
			params.put("agent", new String(new char[] { agent }));// 微信版本号，用于前面提到的判断用户手机微信的版本是否是5.0以上版本。
			System.out.println("----------paycwx order success----------");
			return super.getSuccessAjaxResult("操作成功", params);
		} else {
			String return_msg = (String) map.get("return_msg");
			return super.getAjaxResult("999", return_msg, null);
		}
	}

	/**
	 * 互助宝开通订单付款回调
	 * 
	 * @Title: notify
	 * @Description: TODO
	 * @param request
	 * @param response
	 * @return
	 * @return: String
	 * @author: zengmin
	 * @date: 2017年4月10日 下午3:00:34
	 */
	@RequestMapping("notify")
	public @ResponseBody String notify(HttpServletRequest request, HttpServletResponse response) {
		try {
			String return_msg = "";
			BufferedReader br = request.getReader();
			String valueString = null;
			while ((valueString = br.readLine()) != null) {
				return_msg += valueString;
			}
			System.out.println("return_msg--order--hzbNotify:" + return_msg);
			SortedMap<Object, Object> map = XMLUtil.doXMLParse(return_msg);
			String sign = (String) map.get("sign");
			String out_trade_no = (String) map.get("out_trade_no");
			String transaction_id = (String) map.get("transaction_id");
			String return_code = (String) map.get("return_code");
			String sign1 = PayCommonUtil.createSign("UTF-8", map);
			// System.out.println("sign:"+sign+" sign1:"+sign1);
			if (sign1.equals(sign)) {
				// 校验成功
				System.out.println("success");
				OpenHzbPayLog payLog = openHzbPayLogService.selectByPrimaryKey(out_trade_no);
				if ("SUCCESS".equals(return_code)) {// 支付成功
					// 获取支付记录
					System.out.println(out_trade_no + "-----------" + transaction_id);
					WxUser wxUser = wxUserService.selectByPrimaryKey(payLog.getUserId());
					TjyUser tjyUser = tjyUserService.selectByPrimaryKey(wxUser.getId() + "");
					// 增加互助宝充值记录
					HzbManagerLog hzbManagerLog = new HzbManagerLog();
					hzbManagerLog.setCurrYe(wxUser.getHzbAmount() + payLog.getFkMoney());
					hzbManagerLog.setManagerMoney(payLog.getFkMoney());
					hzbManagerLog.setManagerTime(new Date());
					hzbManagerLog.setPdType(1);
					if (payLog.getLogType() == 1) {
						hzbManagerLog.setRemark("订单在线充值");
					} else {
						hzbManagerLog.setRemark("在线充值");
					}

					hzbManagerLog.setType(7);
					hzbManagerLog.setUserId(payLog.getUserId());
					hzbManagerLogService.addHzbManagerLog(hzbManagerLog);
					wxUser.setHzbAmount(hzbManagerLog.getCurrYe());
					wxUserService.updateWxUser(wxUser);

					String czType = "";
					String dw = "";
					String ye_text = "";// 余额显示值
					String end = "";
					double ye = 0;// 余额
					String cz_text = "";
					czType = "互助宝";
					dw = "元";
					ye = wxUser.getHzbAmount();
					ye_text = CommUtil.formatNumStr(wxUser.getHzbAmount());
					cz_text = CommUtil.formatNumStr(payLog.getFkMoney());
					end = "。互助宝，帮您在刀刃上花钱";
					// 发送短信
					MessageInfo messageInfo = new MessageInfo();
					messageInfo.setId(UUIDGenerator.getUUID());
					messageInfo.setDeleted(0);
					messageInfo.setMobile(wxUser.getMobile());
					messageInfo.setType(1);// 短信
					messageInfo.setCreateTime(new Date());
					String name = tjyUser.getNickname();
					messageInfo.setContent("{name:\"" + name + "\",time:\""
							+ DateUtils.getCurrTimeStr("yyyy-MM-dd HH:mm") + "\",money:\"" + czType + cz_text + dw
							+ "\",czType:\"" + czType + "\",ye:\"" + ye_text + dw + end + "\"}");
					messageInfo.setStatus(0);// 未发送
					messageInfo.setTemplateId(AldyMessageUtil.MsmTemplateId.RECHARGE);
					messageInfoService.addMessageInfo(messageInfo);
					// 发送微信--给用户发送微信消息提醒，告知支付成功
					messageInfo = new MessageInfo();
					messageInfo.setId(UUIDGenerator.getUUID());
					messageInfo.setDeleted(0);
					messageInfo.setType(2);// 微信
					messageInfo.setToUserId(wxUser.getId() + "");
					messageInfo.setCreateTime(new Date());
					String content = AldyMessageUtil.userwalletczsuccess(name, czType + cz_text + dw, czType,
							ye_text + dw + end);
					String con = WxMsmUtil.getTextMessageContent(content);
					messageInfo.setContent(con);
					messageInfo.setTemplateId("cztx");
					messageInfo.setStatus(0);// 未发送
					messageInfo.setWxMsgType(1);/// ** 微信消息类型（1：文本消息2：图文消息）
					// */
					messageInfoService.addMessageInfo(messageInfo);
					/**
					 * 发送系统消息
					 */
					messageInfo = new MessageInfo();
					messageInfo.setId(UUIDGenerator.getUUID());
					messageInfo.setDeleted(0);
					messageInfo.setType(3);// 系统消息
					messageInfo.setToUserId(wxUser.getId() + "");
					messageInfo.setCreateTime(new Date());
					messageInfo.setContent(content);
					messageInfo.setStatus(0);// 不需要发送，未读
					messageInfoService.addMessageInfo(messageInfo);
					payLog.setFkSn(transaction_id);
					payLog.setFkStatus((short) 1);
					payLog.setFkTime(new Date());
					payLog.setShStatus((short) 1);// 线上支付不需要审核
					// 订单支付才需要走下面的逻辑
					if (payLog.getLogType().intValue() == 1) {
						OpenHzbOrder openHzbOrder = openHzbOrderService.selectByPrimaryKey(payLog.getOrderId());
						// 检测用户是否已完成还款
						List<OpenHzbPayLog> orderPayLogList = openHzbPayLogService.selectByOrderId(payLog.getOrderId());
						double ljhk = 0;// 累计还款
						double levelMoney = 0;// 等级总还款
						for (OpenHzbPayLog openHzbPayLog : orderPayLogList) {
							if (openHzbPayLog.getShStatus() == 1 || openHzbPayLog.getId().equals(payLog.getId())) {
								ljhk += openHzbPayLog.getFkMoney();
							}
						}
						if (openHzbOrder.getLevel().intValue() == 1) {
							levelMoney = 200000;
						} else if (openHzbOrder.getLevel().intValue() == 2) {
							levelMoney = 500000;
						} else {
							levelMoney = 1000000;
						}
						if (ljhk >= levelMoney) {
							// 修改订单状态为已付尾款
							openHzbOrder.setStatus(3);
							openHzbOrderService.updateOpenHzbOrder(openHzbOrder);
						} else {
							if (openHzbOrder.getStatus().intValue() < 2) {
								openHzbOrder.setStatus(2);
								openHzbOrderService.updateOpenHzbOrder(openHzbOrder);
							}
						}
					}
					executeSj(wxUser);
				} else {// 充值失败
					payLog.setFkSn(transaction_id);
					payLog.setFkStatus((short) 2);
					payLog.setFkTime(new Date());
					payLog.setShStatus((short) 2);// 线上支付不需要审核
				}
				String result = openHzbPayLogService.updateOpenHzbPayLog(payLog);
				if (MsgConfig.MSG_KEY_SUCCESS.equals(result)) {
					System.out.println("状态更新成功");
				} else {
					System.out.println("状态更新失败");
				}
				return "<xml><return_code><![CDATA[SUCCESS]]></return_code><return_msg><![CDATA[OK]]></return_msg></xml>";
			} else {
				System.out.println("failure");
				// 校验失败
				return "<xml><return_code><![CDATA[FAIL]]></return_code><return_msg></return_msg></xml>";

			}
		} catch (Exception e) {
			e.printStackTrace();
			return "<xml><return_code><![CDATA[FAIL]]></return_code><return_msg></return_msg></xml>";
		}
	}

	/**
	 * 升级
	 * 
	 * @Title: executeSj
	 * @Description: TODO
	 * @return: void
	 * @author: zengmin
	 * @date: 2017年7月27日 上午9:32:03
	 */
	public void executeSj(WxUser wxUser) {
		try {
			int hzbLevel = wxUser.getHzbLevel();
			// 最高级钻石级
			if (hzbLevel == 3) {
				return;
			}
			Long ndc = wxUserService.selectHzbNdcByUserId(wxUser.getId() + "");
			if (ndc == -1) {
				System.out.println("用户开通时间为空，跳过自动升级[" + wxUser.getId() + "]");
				return;
			}
			Date hzbOpenTime = wxUser.getHzbOpenTime();
			Date startDate = null, endDate = null;
			if (ndc > 0) {
				Calendar s1 = Calendar.getInstance();
				s1.setTime(hzbOpenTime);
				s1.set(Calendar.YEAR, s1.get(Calendar.YEAR) + ndc.intValue());
				startDate = s1.getTime();
			} else {
				startDate = hzbOpenTime;
			}
			Calendar s2 = Calendar.getInstance();
			s2.setTime(hzbOpenTime);
			s2.set(Calendar.YEAR, s2.get(Calendar.YEAR) + ndc.intValue() + 1);
			s2.set(Calendar.SECOND, s2.get(Calendar.SECOND) - 1);
			endDate = s2.getTime();
			sj(wxUser, startDate, endDate);
		} catch (Throwable e) {
			e.printStackTrace();
		}

	}

	private void sj(WxUser wxUser, Date startDate, Date endDate) {
		String userId = wxUser.getId() + "";
		double lc = hzbManagerLogService.selectHzbLcjeByUserIdAndTime(userId, startDate, endDate);
		int hzbLevel = wxUser.getHzbLevel();
		double sj_money = 0;
		int level = 0;
		if (hzbLevel == 1) {
			sj_money = 500000;
			if (lc >= sj_money) {
				// 达到白金等级升级条件
				level = 2;
			}
			sj_money = 1000000;
			if (lc >= sj_money) {
				// 达到钻石等级升级条件
				level = 3;
			}
		} else if (hzbLevel == 2) {
			sj_money = 1000000;
			if (lc >= sj_money) {
				// 达到钻石等级升级条件
				level = 3;
			}
		}
		if (level != 0) {
			HzbManagerLog hzbManagerLog = new HzbManagerLog();
			hzbManagerLog.setType(6);// 调整等级
			hzbManagerLog.setManagerTime(new Date());
			hzbManagerLog.setPdType(level + 2);
			if (level == 2) {
				hzbManagerLog.setRemark("达到升级条件自动调整等级至：白金级");
			} else {
				hzbManagerLog.setRemark("达到升级条件自动调整等级至：钻石级");
			}
			hzbManagerLog.setUserId(userId);
			String result = hzbManagerLogService.addHzbManagerLog(hzbManagerLog);
			if (MsgConfig.MSG_KEY_SUCCESS.equals(result)) {
				WxUser user = wxUserService.selectByPrimaryKey(userId);
				user.setHzbLevel(level);
				wxUserService.updateWxUser(user);
				TjyUser tjyUser = tjyUserService.selectByPrimaryKey(wxUser.getId() + "");
				// 推送消息
				MessageInfo messageInfo = new MessageInfo();
				messageInfo.setId(UUIDGenerator.getUUID());
				messageInfo.setDeleted(0);
				messageInfo.setMobile(wxUser.getMobile());
				messageInfo.setType(1);// 短信
				messageInfo.setCreateTime(new Date());
				String name = tjyUser.getNickname();
				String moneyStr = "";
				String levelStr = "";
				String typeStr = "互助宝";
				if (level == 2) {
					moneyStr = "50W";
					levelStr = "白金级";
				} else {
					moneyStr = "100W";
					levelStr = "钻石级";
				}
				messageInfo.setContent("{name:\"" + name + "\",type:\"" + typeStr + "\",money:\"" + moneyStr
						+ "，互助宝\",level:\"" + levelStr + "\"}");
				messageInfo.setStatus(0);// 未发送
				messageInfo.setTemplateId(AldyMessageUtil.MsmTemplateId.HZB_SJ);
				messageInfoService.addMessageInfo(messageInfo);
				// 发送微信--给用户发送微信消息提醒，告知支付成功
				messageInfo = new MessageInfo();
				messageInfo.setId(UUIDGenerator.getUUID());
				messageInfo.setDeleted(0);
				messageInfo.setType(2);// 微信
				messageInfo.setToUserId(wxUser.getId() + "");
				messageInfo.setCreateTime(new Date());
				String content = AldyMessageUtil.userhzbsj(name, typeStr, moneyStr, levelStr);
				String con = WxMsmUtil.getTextMessageContent(content);
				messageInfo.setContent(con);
				messageInfo.setTemplateId("cztx");
				messageInfo.setStatus(0);// 未发送
				messageInfo.setWxMsgType(1);/// ** 微信消息类型（1：文本消息2：图文消息）
				// */
				messageInfoService.addMessageInfo(messageInfo);
				/**
				 * 发送系统消息
				 */
				messageInfo = new MessageInfo();
				messageInfo.setId(UUIDGenerator.getUUID());
				messageInfo.setDeleted(0);
				messageInfo.setType(3);// 系统消息
				messageInfo.setToUserId(wxUser.getId() + "");
				messageInfo.setCreateTime(new Date());
				messageInfo.setContent(content);
				messageInfo.setStatus(0);// 不需要发送，未读
				messageInfoService.addMessageInfo(messageInfo);
			}
		}
	}
}
