package com.tojoy.wechat.entity;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 * 
 * <p>Title:申请退款参数 </p>
 * <p>Description: </p>
 * <p>Company: </p> 
 * @author zhangzheng
 * @Date  2017年5月4日 下午9:00:00
 */
@XmlRootElement(name="xml")
@XmlAccessorType(XmlAccessType.FIELD)
public class ApplyBack {
	
	@XmlElement
	private String appid;//微信分配的公众账号ID（企业号corpid即为此appId）

	@XmlElement
	private String mch_id;//微信支付分配的商户号

	@XmlElement
	private String nonce_str;//随机字符串
	
	@XmlElement
	@XmlJavaTypeAdapter(value = AdaptorCDATA.class)
	private String sign;//签名
	
	@XmlElement
	private String out_trade_no;//订单号
	
	@XmlElement
	private String out_refund_no;//商户系统内部的退款单号，商户系统内部唯一，只能是数字、大小写字母_-|*@ ，同一退款单号多次请求只退一笔。

	@XmlElement
	private String total_fee;//支付金额
	
	@XmlElement
	private String refund_fee; //退款金额
	
	@XmlElement
	private String op_user_id;  //操作员帐号, 默认为商户号
	
	@XmlElement
	private String transaction_id;  //微信返回订单号

	
	public String getTransaction_id() {
		return transaction_id;
	}

	public void setTransaction_id(String transaction_id) {
		this.transaction_id = transaction_id;
	}

	public String getAppid() {
		return appid;
	}

	public void setAppid(String appid) {
		this.appid = appid;
	}

	public String getMch_id() {
		return mch_id;
	}

	public void setMch_id(String mch_id) {
		this.mch_id = mch_id;
	}

	public String getNonce_str() {
		return nonce_str;
	}

	public void setNonce_str(String nonce_str) {
		this.nonce_str = nonce_str;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getOut_trade_no() {
		return out_trade_no;
	}

	public void setOut_trade_no(String out_trade_no) {
		this.out_trade_no = out_trade_no;
	}

	public String getOut_refund_no() {
		return out_refund_no;
	}

	public void setOut_refund_no(String out_refund_no) {
		this.out_refund_no = out_refund_no;
	}

	public String getTotal_fee() {
		return total_fee;
	}

	public void setTotal_fee(String total_fee) {
		this.total_fee = total_fee;
	}

	public String getRefund_fee() {
		return refund_fee;
	}

	public void setRefund_fee(String refund_fee) {
		this.refund_fee = refund_fee;
	}

	public String getOp_user_id() {
		return op_user_id;
	}

	public void setOp_user_id(String op_user_id) {
		this.op_user_id = op_user_id;
	}
	
	

}
