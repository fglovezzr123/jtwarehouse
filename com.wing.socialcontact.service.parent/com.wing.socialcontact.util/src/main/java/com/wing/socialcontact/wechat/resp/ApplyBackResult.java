package com.wing.socialcontact.wechat.resp;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import com.wing.socialcontact.wechat.entity.AdaptorCDATA;

/**
 * 
 * <p>Title:请求退款返回结果 </p>
 * <p>Description: </p>
 * <p>Company: </p> 
 * @author zhangzheng
 * @Date  2017年5月5日 上午10:03:49
 */
@XmlRootElement(name="xml")
@XmlAccessorType(XmlAccessType.FIELD)
public class ApplyBackResult extends MchBase {
	//返回结果码
	@XmlElement
	@XmlJavaTypeAdapter(value = AdaptorCDATA.class)
	private String result_code;
	
	//微信订单号
	@XmlElement
	@XmlJavaTypeAdapter(value = AdaptorCDATA.class)
	private String transaction_id;
	
	//商户订单号
	@XmlElement
	@XmlJavaTypeAdapter(value = AdaptorCDATA.class)
	private String out_trade_id;
	
	//商户退款单号
	@XmlElement
	@XmlJavaTypeAdapter(value = AdaptorCDATA.class)
	private String out_refund_id;
	
	//微信退款单号
	@XmlElement
	@XmlJavaTypeAdapter(value = AdaptorCDATA.class)
	private String refund_id;
	
	//退款总金额
	@XmlElement
	@XmlJavaTypeAdapter(value = AdaptorCDATA.class)
	private String refund_fee;
	
	//订单总金额
	@XmlElement
	@XmlJavaTypeAdapter(value = AdaptorCDATA.class)
	private String total_fee;
	
	//现金支付金额
	@XmlElement
	@XmlJavaTypeAdapter(value = AdaptorCDATA.class)
	private String cash_fee;
	
	
	

}
