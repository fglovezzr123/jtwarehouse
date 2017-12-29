package com.test;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import com.tojoycloud.frame.common.ConstantDefinition;
import com.tojoycloud.frame.common.MD5;
import com.tojoycloud.frame.common.util.Dom4jUtil;
import com.tojoycloud.frame.common.util.SignUtil;

public class WebChatPayTest
{
	public static void main(String[] args)
	{
		sendOrder();// 测试下单接口
		// checkCallback(true); // 模拟下单后回调请求到服务器
		// searchOrder();// 查询订单
		// closeOrder();// 关闭订单
	}

	public static void sendOrder()
	{
		String newUrl = "https://api.mch.weixin.qq.com/pay/unifiedorder";
		HttpClient httpClient = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost(newUrl);
		try
		{
			WebChatSendBean sendBean = new WebChatSendBean();
			sendBean.setAppid(ConstantDefinition.appid);
			sendBean.setMch_id(ConstantDefinition.mch_id);
			String nonceStr = RandomStringUtils.randomAlphanumeric(32);
			sendBean.setNonce_str(nonceStr);
			sendBean.setBody("天九共享会——会员充值");
			String outTradeNo = RandomStringUtils.randomAlphanumeric(12);
			sendBean.setOut_trade_no(outTradeNo);
			sendBean.setTotal_fee("1");
			sendBean.setSpbill_create_ip("60.205.178.98");
			sendBean.setNotify_url("http://47.93.20.212:51155/tojoycloud/webchat/callback");
			sendBean.setTrade_type("APP");

			Map<String, String> parameter = new HashMap<String, String>();
			parameter.put("appid", ConstantDefinition.appid);
			parameter.put("mch_id", ConstantDefinition.mch_id);
			parameter.put("nonce_str", nonceStr);
			parameter.put("body", "天九共享会——会员充值");
			parameter.put("out_trade_no", outTradeNo);
			parameter.put("total_fee", "1");
			parameter.put("spbill_create_ip", "60.205.178.98");
			parameter.put("notify_url", "http://47.93.20.212:51155/tojoycloud/webchat/callback");
			parameter.put("trade_type", "APP");
			StringBuffer parameterString = new StringBuffer();
			parameterString.append(SignUtil.composeStringToSign(parameter));
			parameterString.append("&key=FXTzl6uY2IyejcnI3WZRaxq2xTksiwBM");
			String sign = MD5.encode(parameterString.toString()).toUpperCase();
			sendBean.setSign(sign);

			String xmlString = Dom4jUtil.getXMLForWebChat(sendBean);
			ByteArrayEntity reqEntity = new ByteArrayEntity(xmlString.getBytes());
			httpPost.setEntity(reqEntity);

			HttpResponse response = httpClient.execute(httpPost);
			InputStream in = response.getEntity().getContent();
			byte[] bReaded = IOUtils.toByteArray(in);
			IOUtils.closeQuietly(in);
			String responseReport = new String(bReaded, 0, bReaded.length, "utf-8");
			System.out.println(responseReport);
			// 序列化应答
			Document document = DocumentHelper.parseText(responseReport);
			Element root = document.getRootElement();
			Element element = root.element("return_code");
			if (element.getStringValue().equals("SUCCESS"))
			{
				element = root.element("result_code");
				if (element.getStringValue().equals("SUCCESS"))
				{
					// TODO： 微信订单号，入库保存,额外增加version字段，为以后回调做乐观锁使用
					element = root.element("prepay_id");
					String orderID = element.getStringValue();
					System.out.println("orderid  =======  " + orderID);
				}
				else
				{
					element = root.element("err_code_des");
					System.out.println("下单失败:" + element.getStringValue());
				}
			}
			else
			{
				element = root.element("return_msg");
				System.out.println("下单失败:" + element.getStringValue());
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public static void checkCallback(boolean isSuccess)
	{
		String newUrl = "http://127.0.0.1:8080/tojoycloud/webchat/callback";
		HttpClient httpClient = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost(newUrl);
		String xmlString = null;
		if (!isSuccess)
		{
			xmlString = Dom4jUtil.returnFail("发送测试错误结果");
		}
		else
		{
			WebChatCallbackBean callbackBean = new WebChatCallbackBean();
			callbackBean.setAppid(ConstantDefinition.appid);
			callbackBean.setMch_id(ConstantDefinition.mch_id);
			String nonceStr = RandomStringUtils.randomAlphanumeric(32);
			callbackBean.setNonce_str(nonceStr);
			callbackBean.setResult_code("SUCCESS");
			callbackBean.setOpenid("wxd930ea5d5a258f4f");
			callbackBean.setOut_trade_no("1212321211201407033568112322");
			callbackBean.setTotal_fee("1");
			callbackBean.setCash_fee("1");
			callbackBean.setBank_type("CMC");
			callbackBean.setTransaction_id("1217752501201407033233368018");
			callbackBean.setTime_end("20141030133525");
			callbackBean.setTrade_type("APP");
			callbackBean.setReturn_code("SUCCESS");

			Map<String, String> parameter = new HashMap<String, String>();
			parameter.put("appid", ConstantDefinition.appid);
			parameter.put("mch_id", ConstantDefinition.mch_id);
			parameter.put("nonce_str", nonceStr);
			parameter.put("result_code", "SUCCESS");
			parameter.put("openid", "wxd930ea5d5a258f4f");
			parameter.put("trade_type", "APP");
			parameter.put("bank_type", "CMC");
			parameter.put("total_fee", "1");
			parameter.put("cash_fee", "1");
			parameter.put("transaction_id", "1217752501201407033233368018");
			parameter.put("out_trade_no", "1212321211201407033568112322");
			parameter.put("time_end", "20141030133525");
			parameter.put("return_code", "SUCCESS");
			StringBuffer parameterString = new StringBuffer();
			parameterString.append(SignUtil.composeStringToSign(parameter));
			parameterString.append("&key=FXTzl6uY2IyejcnI3WZRaxq2xTksiwBM");
			String sign = MD5.encode(parameterString.toString()).toUpperCase();
			callbackBean.setSign(sign);

			xmlString = Dom4jUtil.getXMLForWebChat(callbackBean);
		}
		ByteArrayEntity reqEntity = new ByteArrayEntity(xmlString.getBytes());
		httpPost.setEntity(reqEntity);
		String responseReport;
		try
		{
			HttpResponse response = httpClient.execute(httpPost);
			InputStream in = response.getEntity().getContent();
			byte[] bReaded = IOUtils.toByteArray(in);
			IOUtils.closeQuietly(in);
			responseReport = new String(bReaded, 0, bReaded.length, "utf-8");
			System.out.println(responseReport);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public static void searchOrder()
	{
		String newUrl = "https://api.mch.weixin.qq.com/pay/orderquery";
		HttpClient httpClient = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost(newUrl);

		WebChatCallbackBean callbackBean = new WebChatCallbackBean();
		callbackBean.setAppid(ConstantDefinition.appid);
		callbackBean.setMch_id(ConstantDefinition.mch_id);
		String nonceStr = RandomStringUtils.randomAlphanumeric(32);
		callbackBean.setNonce_str(nonceStr);
		callbackBean.setOut_trade_no("UrcERVCuKy3P");// 订单号必须是第一个方法中的设置的订单号，也是入库数据

		Map<String, String> parameter = new HashMap<String, String>();
		parameter.put("appid", ConstantDefinition.appid);
		parameter.put("mch_id", ConstantDefinition.mch_id);
		parameter.put("nonce_str", nonceStr);
		parameter.put("out_trade_no", "UrcERVCuKy3P");// 订单号必须是第一个方法中的设置的订单号，也是入库数据
		StringBuffer parameterString = new StringBuffer();
		parameterString.append(SignUtil.composeStringToSign(parameter));
		parameterString.append("&key=FXTzl6uY2IyejcnI3WZRaxq2xTksiwBM");
		String sign = MD5.encode(parameterString.toString()).toUpperCase();
		callbackBean.setSign(sign);

		String xmlString = Dom4jUtil.getXMLForWebChat(callbackBean);
		ByteArrayEntity reqEntity = new ByteArrayEntity(xmlString.getBytes());
		httpPost.setEntity(reqEntity);
		String responseReport;
		try
		{
			HttpResponse response = httpClient.execute(httpPost);
			InputStream in = response.getEntity().getContent();
			byte[] bReaded = IOUtils.toByteArray(in);
			IOUtils.closeQuietly(in);
			responseReport = new String(bReaded, 0, bReaded.length, "utf-8");
			System.out.println(responseReport);
			// 序列化应答
			Document document = DocumentHelper.parseText(responseReport);
			Element root = document.getRootElement();
			Element element = root.element("return_code");
			if (element.getStringValue().equals("SUCCESS"))
			{
				element = root.element("result_code");
				if (element.getStringValue().equals("SUCCESS"))
				{
					// 查询数据内容
					// 总金额
					String total_fee = root.element("total_fee").getStringValue();
					// 交易状态:SUCCESS—支付成功 REFUND—转入退款 NOTPAY—未支付 CLOSED—已关闭
					// REVOKED—已撤销（刷卡支付）USERPAYING--用户支付中
					// PAYERROR--支付失败(其他原因，如银行返回失败)
					String trade_state = root.element("trade_state").getStringValue();
					// 交易状态描述
					String trade_state_desc = root.element("trade_state_desc").getStringValue();
					String time_end = root.element("time_end").getStringValue();
					// TODO 返回前端展现即可
				}
				else
				{
					element = root.element("err_code_des");
					System.out.println("查询失败:" + element.getStringValue());
				}
			}
			else
			{
				element = root.element("return_msg");
				System.out.println("查询失败:" + element.getStringValue());
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public static void closeOrder()
	{
		String newUrl = "https://api.mch.weixin.qq.com/pay/closeorder";
		HttpClient httpClient = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost(newUrl);

		WebChatCallbackBean callbackBean = new WebChatCallbackBean();
		callbackBean.setAppid(ConstantDefinition.appid);
		callbackBean.setMch_id(ConstantDefinition.mch_id);
		String nonceStr = RandomStringUtils.randomAlphanumeric(32);
		callbackBean.setNonce_str(nonceStr);
		callbackBean.setOut_trade_no("UrcERVCuKy3P");

		Map<String, String> parameter = new HashMap<String, String>();
		parameter.put("appid", ConstantDefinition.appid);
		parameter.put("mch_id", ConstantDefinition.mch_id);
		parameter.put("nonce_str", nonceStr);
		parameter.put("out_trade_no", "UrcERVCuKy3P");
		StringBuffer parameterString = new StringBuffer();
		parameterString.append(SignUtil.composeStringToSign(parameter));
		parameterString.append("&key=FXTzl6uY2IyejcnI3WZRaxq2xTksiwBM");
		String sign = MD5.encode(parameterString.toString()).toUpperCase();
		callbackBean.setSign(sign);

		String xmlString = Dom4jUtil.getXMLForWebChat(callbackBean);
		ByteArrayEntity reqEntity = new ByteArrayEntity(xmlString.getBytes());
		httpPost.setEntity(reqEntity);
		String responseReport;
		try
		{
			HttpResponse response = httpClient.execute(httpPost);
			InputStream in = response.getEntity().getContent();
			byte[] bReaded = IOUtils.toByteArray(in);
			IOUtils.closeQuietly(in);
			responseReport = new String(bReaded, 0, bReaded.length, "utf-8");
			System.out.println(responseReport);
			// 序列化应答
			Document document = DocumentHelper.parseText(responseReport);
			Element root = document.getRootElement();
			Element element = root.element("return_code");
			if (element.getStringValue().equals("SUCCESS"))
			{
				element = root.element("result_code");
				if (element.getStringValue().equals("SUCCESS"))
				{
					// TODO 直接入库，标记订单状态为关闭(订单已支付，不能发起关单)
				}
				else
				{
					element = root.element("err_code_des");
					System.out.println("关闭订单失败:" + element.getStringValue());
				}
			}
			else
			{
				element = root.element("return_msg");
				System.out.println("关闭订单失败:" + element.getStringValue());
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
