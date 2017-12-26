package com.tojoy.wechat.resp;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import com.tojoy.wechat.entity.AdaptorCDATA;

/**
 * 统一支付请求返回对象
 * @author liangwj
 */
@XmlRootElement(name="xml")
@XmlAccessorType(XmlAccessType.FIELD)
public class UnifiedorderResult extends MchBase{
	//设备号
	@XmlElement
	@XmlJavaTypeAdapter(value = AdaptorCDATA.class)
	private String device_info;
	
	//交易类型，取值为：JSAPI，NATIVE，APP等
	@XmlElement
	@XmlJavaTypeAdapter(value = AdaptorCDATA.class)
	private String trade_type;
	
	//预支付交易会话标识，微信生成的预支付会话标识，用于后续接口调用中使用，该值有效期为2小时
	@XmlElement
	@XmlJavaTypeAdapter(value = AdaptorCDATA.class)
	private String prepay_id;
	
	//二维码链接，trade_type为NATIVE时有返回，用于生成二维码，展示给用户进行扫码支付
	@XmlElement
	@XmlJavaTypeAdapter(value = AdaptorCDATA.class)
	private String code_url;


	public String getDevice_info() {
		return device_info;
	}

	public void setDevice_info(String device_info) {
		this.device_info = device_info;
	}

	public String getTrade_type() {
		return trade_type;
	}

	public void setTrade_type(String trade_type) {
		this.trade_type = trade_type;
	}

	public String getPrepay_id() {
		return prepay_id;
	}

	public void setPrepay_id(String prepay_id) {
		this.prepay_id = prepay_id;
	}

	public String getCode_url() {
		return code_url;
	}

	public void setCode_url(String code_url) {
		this.code_url = code_url;
	}



}
