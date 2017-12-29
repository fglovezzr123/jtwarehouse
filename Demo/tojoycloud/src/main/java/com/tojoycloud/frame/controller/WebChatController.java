package com.tojoycloud.frame.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.tojoycloud.frame.common.MD5;
import com.tojoycloud.frame.common.util.Dom4jUtil;
import com.tojoycloud.frame.common.util.SignUtil;

/**
 * 处理微信支付结果的回调类
 * 
 * @author qiaoweiran
 * 
 */
@Controller
@RequestMapping("/webchat")
public class WebChatController
{
	/**
	 * 支付结果回调接口
	 * 
	 * @param orderInfo
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/callback", method = RequestMethod.POST)
	public @ResponseBody
	String logon(@RequestBody String orderInfo)
	{
		System.out.println(orderInfo);
		// 先判断应答状态
		Document document = null;
		try
		{
			document = DocumentHelper.parseText(orderInfo);
		}
		catch (DocumentException e)
		{
			e.printStackTrace();
		}
		if (document == null)
			return Dom4jUtil.returnFail("传输数据错误");
		Element root = document.getRootElement();
		String return_code = root.element("return_code").getStringValue(); // 回调结果码
		if (return_code.equals("FAIL"))
		{
			// TODO: 把错误信息入库
			String return_msg = root.element("return_msg").getStringValue(); // 错误结果
			// 如果微信收到商户的应答不是成功或超时，微信认为通知失败，微信会通过一定的策略定期重新发起通知,因为这个地方为支付失败，所以认定为接收到正确回调，应答为成功
			return Dom4jUtil.returnSuccess();
		}
		// 签名校验，判断请求的真实性
		Map<String, String> parameter = new HashMap<String, String>();
		List<Element> elements = root.elements();
		for (Element e : elements)
			if (!e.getName().equals("sign"))
				parameter.put(e.getName(), e.getStringValue());
		StringBuffer parameterString = new StringBuffer();
		parameterString.append(SignUtil.composeStringToSign(parameter));
		parameterString.append("&key=FXTzl6uY2IyejcnI3WZRaxq2xTksiwBM");
		String serverSign = MD5.encode(parameterString.toString()).toUpperCase();
		if (serverSign.equals(root.element("sign").getStringValue()))
		{
			String transaction_id = root.element("transaction_id").getStringValue(); // 微信支付订单号
			String out_trade_no = root.element("out_trade_no").getStringValue(); // 商户订单号
			String total_fee = root.element("total_fee").getStringValue(); // 总金额
			String time_end = root.element("time_end").getStringValue(); // 时间:格式为yyyyMMddHHmmss
			// TODO:查询数据库orderrecord，判断订单对应的金额是否正确
			// TODO:做DB乐观锁处理，避免重复处理的情况
			return Dom4jUtil.returnSuccess();
		}
		else
		{
			return Dom4jUtil.returnFail("签名错误");
		}
	}
}
