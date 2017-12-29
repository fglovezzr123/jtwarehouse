package com.wing.socialcontact.front.action;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tojoycloud.common.PayConstants.PayMode;
import com.tojoycloud.common.report.ResponseCode;
import com.tojoycloud.common.report.base.BaseAppAction;
import com.tojoycloud.report.RequestReport;
import com.tojoycloud.report.ResponseReport;
import com.tojoycloud.wechat.entity.request.WxPayUnifiedOrderRequest;
import com.tojoycloud.wechat.entity.result.WxPayAppOrderResult;
import com.tojoycloud.wechat.entity.result.WxPayOrderCloseResult;
import com.tojoycloud.wechat.entity.result.WxPayOrderQueryResult;
import com.tojoycloud.wechat.entity.result.WxPayUnifiedOrderResult;
import com.tojoycloud.wechat.exception.WxPayException;
import com.tojoycloud.wechat.service.WxPayService;

@Controller
@RequestMapping("/pay/")
public class PayAction extends BaseAppAction {

	@Autowired
	private WxPayService wxPayService;

	/**
	 * 统一下单接口
	 * @param rr
	 * @param request
	 * @return
	 */
	@RequestMapping("unifiedOrder")
	public @ResponseBody ResponseReport unifiedOrder(@RequestBody RequestReport rr, HttpServletRequest request) {

		String mode = rr.getDataValue("payMode");
		String spbillCreateIp = rr.getDataValue("spbillCreateIp");
		String body = rr.getDataValue("body");
		String totalFee = rr.getDataValue("totalFee");
		String outTradeNo = String.valueOf(System.currentTimeMillis()) + RandomUtils.nextInt(1000, 9999);

		switch (mode) {
		case PayMode.WECHAT:
			WxPayUnifiedOrderRequest payRequest = WxPayUnifiedOrderRequest.newBuilder().spbillCreateIp(spbillCreateIp)
					.body(body).totalFee(WxPayUnifiedOrderRequest.yuanToFee(totalFee)).outTradeNo(outTradeNo).build();
			try {
				WxPayAppOrderResult result = wxPayService.createClientOrder(payRequest);
				return super.getSuccessAjaxResult(rr, "统一下单成功", result);
			} catch (WxPayException e) {
				return super.getAjaxResult(rr, ResponseCode.Error, e.getMessage(), null);
			}
		case PayMode.ALI:
			// TODO 待接入
			break;
		case PayMode.UNION:
			// TODO 待接入
			break;
		default:
			// TODO 待接入
			break;
		}
		return null;
	}

	/**
	 * 关闭订单接口
	 * @param rr
	 * @param request
	 * @return
	 */
	@RequestMapping("closeOrder")
	public @ResponseBody ResponseReport closeOrder(@RequestBody RequestReport rr, HttpServletRequest request) {

		String mode = rr.getDataValue("payMode");
		String outTradeNo = rr.getDataValue("outTradeNo");

		switch (mode) {
		case PayMode.WECHAT:
			try {
				WxPayOrderCloseResult closeResult = wxPayService.closeOrder(outTradeNo);
				return super.getSuccessAjaxResult(rr, "关闭订单成功", closeResult.getReturnMsg());
			} catch (WxPayException e) {
				return super.getAjaxResult(rr, ResponseCode.Error, e.getMessage(), null);
			}
		case PayMode.ALI:
			// TODO 待接入
			break;
		case PayMode.UNION:
			// TODO 待接入
			break;
		default:
			// TODO 待接入
			break;
		}

		return null;
	}

	/**
	 * 查询订单
	 * @param rr
	 * @param request
	 * @return
	 */
	@RequestMapping("orderQuery")
	public @ResponseBody ResponseReport orderQuery(@RequestBody RequestReport rr, HttpServletRequest request) {

		String mode = rr.getDataValue("payMode");
		String transactionId = rr.getDataValue("transactionId");
		String outTradeNo = rr.getDataValue("outTradeNo");

		switch (mode) {
		case PayMode.WECHAT:
			try {
				WxPayOrderQueryResult queryResult = wxPayService.queryOrder(transactionId, outTradeNo);
				return super.getSuccessAjaxResult(rr, "查询订单成功", queryResult.getTradeState());
			} catch (WxPayException e) {
				return super.getAjaxResult(rr, ResponseCode.Error, e.getMessage(), null);
			}
		case PayMode.ALI:
			// TODO 待接入
			break;
		case PayMode.UNION:
			// TODO 待接入
			break;
		default:
			// TODO 待接入
			break;
		}

		return null;
	}
}