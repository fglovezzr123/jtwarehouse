package com.wing.socialcontact.action;

import java.io.IOException;
import java.util.Date;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.jdom.JDOMException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.wing.socialcontact.common.model.PageParam;
import com.wing.socialcontact.commons.util.ApplicationPath;
import com.wing.socialcontact.service.wx.api.IMessageInfoService;
import com.wing.socialcontact.service.wx.api.ITjyUserService;
import com.wing.socialcontact.service.wx.api.IWalletLogService;
import com.wing.socialcontact.service.wx.api.IWalletTxService;
import com.wing.socialcontact.service.wx.api.IWxUserService;
import com.wing.socialcontact.service.wx.bean.MessageInfo;
import com.wing.socialcontact.service.wx.bean.TjyUser;
import com.wing.socialcontact.service.wx.bean.WalletLog;
import com.wing.socialcontact.service.wx.bean.WalletTx;
import com.wing.socialcontact.service.wx.bean.WxUser;
import com.wing.socialcontact.sys.action.BaseAction;
import com.wing.socialcontact.util.AldyMessageUtil;
import com.wing.socialcontact.util.CommUtil;
import com.wing.socialcontact.util.ConfigUtil;
import com.wing.socialcontact.util.Constants;
import com.wing.socialcontact.util.DateUtils;
import com.wing.socialcontact.util.HttpClientUtil;
import com.wing.socialcontact.util.PayCommonUtil;
import com.wing.socialcontact.util.ServletUtil;
import com.wing.socialcontact.util.UUIDGenerator;
import com.wing.socialcontact.util.WxMsmUtil;
import com.wing.socialcontact.util.XMLUtil;

/**
 * 大咖管理
 * 
 * @author xuxinyuan
 *
 */
@Controller
@RequestMapping("/walletTx")
public class WalletTxAction extends BaseAction {

	@Autowired
	private IWalletTxService walletTxService;
	@Autowired
	private ITjyUserService tjyUserService;
	@Autowired
	private IWxUserService wxUserService;
	@Autowired
	private IWalletLogService walletLogService;
	@Autowired
	private IMessageInfoService messageInfoService;

	/**
	 * 条件查询话题
	 * 
	 * @return
	 */
	@RequiresPermissions("walletTx:read")
	@RequestMapping("load")
	public String load(ModelMap map) {
		return "walletTx/walletTx_list";

	}

	@RequiresPermissions("walletTx:read")
	@RequestMapping("query")
	public ModelAndView query(PageParam param, String nickname, String state, String mobile) {

		return ajaxJsonEscape(walletTxService.selectAllWalletTx(param, nickname, state, mobile));
	}

	@RequestMapping("tongguo")
	public ModelAndView tongguo(String[] ids, HttpServletRequest request) {
		for (String id : ids) {
			WalletTx walletTx = walletTxService.selectByPrimaryKey(id);
			if (null == walletTx || !"0".equals(walletTx.getState())) {
				continue;
			}
			walletTx.setState("1");
			walletTx.setOpTime(new Date());
			walletTx.setOpUser(ServletUtil.getMemberId());
			TjyUser tjyUser = tjyUserService.selectByPrimaryKey(walletTx.getUserid());
			WxUser wxUser = wxUserService.selectByPrimaryKey(walletTx.getUserid());
			String out_trade_no = walletTx.getId();
			Double tp = walletTx.getTxje() * 100;
			SortedMap<Object, Object> parameters = new TreeMap<Object, Object>();
			parameters.put("mch_appid", ApplicationPath.getParameter("wx_appid"));
			parameters.put("mchid", ConfigUtil.MCH_ID);
			parameters.put("nonce_str", PayCommonUtil.CreateNoncestr());
			parameters.put("partner_trade_no", out_trade_no);
			parameters.put("openid", tjyUser.getOpenId());
			parameters.put("check_name", "NO_CHECK");// NO_CHECK：不校验真实姓名
			// FORCE_CHECK：强校验真实姓名
			if (Constants.TEST) {
				parameters.put("amount", 100 + "");// 单笔最小1元
			} else {
				parameters.put("amount", tp.intValue() + "");
			}
			parameters.put("desc", "提现");
			parameters.put("spbill_create_ip", CommUtil.getIpAddr());
			String sign = PayCommonUtil.createSign("UTF-8", parameters);
			parameters.put("sign", sign);
			System.out.println("parameters:" + parameters);
			String requestXML = PayCommonUtil.getRequestXml(parameters);
			System.out.println("requestXML:" + requestXML);
			String result = HttpClientUtil.sendPostRequestCa(ConfigUtil.TRANSFERS_URL, requestXML, true);
			System.out.println("result:" + result);
			SortedMap<Object, Object> map;
			try {
				if (null != result) {
					map = XMLUtil.doXMLParse(result);
					// 解析微信返回的信息，以Map形式存储便于取值
					String return_code = (String) map.get("return_code");
					String return_msg = (String) map.get("return_msg");
					String result_code = (String) map.get("result_code");
					String payment_no = (String) map.get("payment_no");
					// String payment_time = (String) map.get("payment_time");
					// boolean txFlag = false;
					String time = DateUtils.dateToString(walletTx.getCreateTime(), "yyyy-MM-dd HH:mm");
					if ("SUCCESS".equals(return_code) && "SUCCESS".equals(result_code)) {
						walletTx.setPaySn(payment_no);
						walletTx.setPayStatus("1");
						walletTxService.updateWalletTx(walletTx);
						// 发送提醒
						String name = tjyUser.getNickname();
						String content = AldyMessageUtil.userwallettxsuccess(name, time, walletTx.getTxje(),
								walletTx.getSyje());
						/**
						 * 短信
						 */
						MessageInfo messageInfo = new MessageInfo();
						messageInfo.setId(UUIDGenerator.getUUID());
						messageInfo.setDeleted(0);
						messageInfo.setMobile(wxUser.getMobile());
						messageInfo.setType(1);// 短信
						messageInfo.setCreateTime(new Date());
						messageInfo.setContent("{name:\"" + name + "\",time:\"" + time + "\",money:\"RMB"
								+ CommUtil.formatNumStr(walletTx.getTxje()) + "元\",ye:\"RMB"
								+ CommUtil.formatNumStr(walletTx.getSyje()) + "元\"}");
						messageInfo.setStatus(0);// 未发送
						messageInfo.setTemplateId(AldyMessageUtil.MsmTemplateId.WALLET_TX_SUCCESS);
						messageInfoService.addMessageInfo(messageInfo);
						/**
						 * 微信
						 */
						messageInfo = new MessageInfo();
						messageInfo.setId(UUIDGenerator.getUUID());
						messageInfo.setDeleted(0);
						messageInfo.setType(2);// 微信
						messageInfo.setToUserId(wxUser.getId() + "");
						messageInfo.setCreateTime(new Date());
						String con = WxMsmUtil.getTextMessageContent(content);
						messageInfo.setContent(con);
						messageInfo.setTemplateId("txtx");
						messageInfo.setStatus(0);// 未发送
						messageInfo.setWxMsgType(1);/// ** 微信消息类型（1：文本消息2：图文消息）
						messageInfoService.addMessageInfo(messageInfo);
						/**
						 * 系统
						 */
						messageInfo = new MessageInfo();
						messageInfo.setId(UUIDGenerator.getUUID());
						messageInfo.setDeleted(0);
						messageInfo.setType(3);// 系统
						messageInfo.setToUserId(wxUser.getId() + "");
						messageInfo.setCreateTime(new Date());
						messageInfo.setContent(content);
						messageInfo.setStatus(0);// 未读
						messageInfoService.addMessageInfo(messageInfo);

					} else {
						walletTx.setPaySn(payment_no);
						walletTx.setPayStatus("2");
						walletTx.setState("0");
						walletTxService.updateWalletTx(walletTx);
						if ("NO_AUTH".equals(return_msg)) {
							return_msg = "没有授权请求此api";
						} else if ("NOTENOUGH".equals(return_msg)) {
							return_msg = "帐号余额不足";
						} else if ("AMOUNT_LIMIT".equals(return_msg)) {
							return_msg = "提现余额不能小于5元";
						}

						return ajaxDoneTextError("付款失败，错误原因：" + return_msg);
					}
				}
			} catch (JDOMException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return ajaxDone(true);
	}

	@RequestMapping("pinbi")
	public ModelAndView pinbi(String[] ids) {
		for (String id : ids) {
			WalletTx walletTx = walletTxService.selectByPrimaryKey(id);
			if (null == walletTx || !"0".equals(walletTx.getState())) {
				continue;
			}
			walletTx.setState("2");
			walletTx.setOpTime(new Date());
			walletTx.setOpUser(ServletUtil.getMemberId());
			WxUser wxUser = wxUserService.selectByPrimaryKey(walletTx.getUserid());
			wxUser.setAvailablebalance(CommUtil.add(wxUser.getAvailablebalance(), walletTx.getTxje()));
			wxUserService.updateWxUser(wxUser);
			walletTxService.updateWalletTx(walletTx);
			WalletLog walletLog = new WalletLog();
			walletLog.setAmount(walletTx.getTxje());
			walletLog.setCreateTime(new Date());
			walletLog.setDeleted("0");
			walletLog.setPayStatus("1");
			walletLog.setPdType("1");// 收入
			walletLog.setRemark("提现退回");
			walletLog.setType("1");
			walletLog.setUserId(wxUser.getId() + "");
			walletLog.setRmbAmount(walletTx.getTxje());// 实际支付金额
			walletLog.setYeAmount(wxUser.getAvailablebalance());
			walletLog.setBusinessType(3);
			walletLogService.addWalletLog(walletLog);
			// 发送提醒
			TjyUser tjyUser = tjyUserService.selectByPrimaryKey(walletTx.getUserid());
			String name = tjyUser.getNickname();
			String time = DateUtils.dateToString(walletTx.getCreateTime(), "yyyy-MM-dd HH:mm");
			String content = AldyMessageUtil.userwallettxfail(name, time, walletTx.getTxje(),
					wxUser.getAvailablebalance());
			/**
			 * 短信
			 */
			MessageInfo messageInfo = new MessageInfo();
			messageInfo.setId(UUIDGenerator.getUUID());
			messageInfo.setDeleted(0);
			messageInfo.setMobile(wxUser.getMobile());
			messageInfo.setType(1);// 短信
			messageInfo.setCreateTime(new Date());
			messageInfo.setContent("{name:\"" + name + "\",time:\"" + time + "\",money:\"RMB"
					+ CommUtil.formatNumStr(walletTx.getTxje()) + "元\",ye:\"RMB"
					+ CommUtil.formatNumStr(wxUser.getAvailablebalance()) + "元\"}");
			messageInfo.setStatus(0);// 未发送
			messageInfo.setTemplateId(AldyMessageUtil.MsmTemplateId.WALLET_TX_FAILURE);
			messageInfoService.addMessageInfo(messageInfo);
			/**
			 * 微信
			 */
			messageInfo = new MessageInfo();
			messageInfo.setId(UUIDGenerator.getUUID());
			messageInfo.setDeleted(0);
			messageInfo.setType(2);// 微信
			messageInfo.setToUserId(wxUser.getId() + "");
			messageInfo.setCreateTime(new Date());
			String con = WxMsmUtil.getTextMessageContent(content);
			messageInfo.setContent(con);
			messageInfo.setTemplateId("txtx");
			messageInfo.setStatus(0);// 未发送
			messageInfo.setWxMsgType(1);/// ** 微信消息类型（1：文本消息2：图文消息）
			messageInfoService.addMessageInfo(messageInfo);
			/**
			 * 系统
			 */
			messageInfo = new MessageInfo();
			messageInfo.setId(UUIDGenerator.getUUID());
			messageInfo.setDeleted(0);
			messageInfo.setType(3);// 系统
			messageInfo.setToUserId(wxUser.getId() + "");
			messageInfo.setCreateTime(new Date());
			messageInfo.setContent(content);
			messageInfo.setStatus(0);// 未读
			messageInfoService.addMessageInfo(messageInfo);
		}
		return ajaxDone(true);
	}

}
