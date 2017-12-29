package com.wing.socialcontact.wechat.resp;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="xml")
@XmlAccessorType(XmlAccessType.FIELD)
public class MchOrderInfoResult extends MchBase{
	/**
	 * 交易状态
	 * SUCCESS—支付成功
	 * REFUND—转入退款
	 * NOTPAY—未支付
	 * CLOSED—已关闭
	 * REVOKED—已撤销（刷卡支付）
	 * USERPAYING--用户支付中
	 * PAYERROR--支付失败(其他原因，如银行返回失败)
	 */
	@XmlElement
	private String trade_state;
	/**
	 * 设备号
	 * 微信支付分配的终端设备号
	 */
	@XmlElement
	private String device_info;
	/**
	 * 用户标识
	 * 用户在商户appid下的唯一标识
	 */
	@XmlElement
	private String openid;
	/**
	 * 是否关注公众账号
	 * 用户是否关注公众账号，Y-关注，N-未关注，仅在公众账号类型支付有效
	 */
	@XmlElement
	private String is_subscribe;
	/**
	 * 交易类型
	 * 调用接口提交的交易类型，取值如下：JSAPI，NATIVE，APP，MICROPAY
	 */
	@XmlElement
	private String trade_type;
	/**
	 * 付款银行
	 * 银行类型，采用字符串类型的银行标识
	 */
	@XmlElement
	private String bank_type;
	/**
	 * 标价金额
	 * 订单总金额，单位为分
	 */
	@XmlElement
	private Integer total_fee;
	/**
	 * 标价币种
	 * 货币类型，符合ISO 4217标准的三位字母代码，默认人民币：CNY
	 */
	@XmlElement
	private String fee_type;
	/**
	 * 现金支付金额
	 * 现金支付金额订单现金支付金额，详见
	 */
	@XmlElement
	private Integer cash_fee;

	@XmlElement
	private String cash_fee_type;

	@XmlElement
	private Integer coupon_fee;

	@XmlElement
	private Integer coupon_count;

	@XmlElement
	private String transaction_id;
	/**
	 * 商户订单号
	 */
	@XmlElement
	private String out_trade_no;

	@XmlElement
	private String attach;
	/**
	 * 支付完成时间
	 */
	@XmlElement
	private String time_end;

	@XmlElement
	private String trade_state_desc;

	public String getTrade_state() {
		return trade_state;
	}

	public void setTrade_state(String trade_state) {
		this.trade_state = trade_state;
	}

	public String getDevice_info() {
		return device_info;
	}

	public void setDevice_info(String device_info) {
		this.device_info = device_info;
	}

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public String getIs_subscribe() {
		return is_subscribe;
	}

	public void setIs_subscribe(String is_subscribe) {
		this.is_subscribe = is_subscribe;
	}

	public String getTrade_type() {
		return trade_type;
	}

	public void setTrade_type(String trade_type) {
		this.trade_type = trade_type;
	}

	public String getBank_type() {
		return bank_type;
	}

	public void setBank_type(String bank_type) {
		this.bank_type = bank_type;
	}

	public Integer getTotal_fee() {
		return total_fee;
	}

	public void setTotal_fee(Integer total_fee) {
		this.total_fee = total_fee;
	}

	public Integer getCoupon_fee() {
		return coupon_fee;
	}

	public void setCoupon_fee(Integer coupon_fee) {
		this.coupon_fee = coupon_fee;
	}

	public String getFee_type() {
		return fee_type;
	}

	public void setFee_type(String fee_type) {
		this.fee_type = fee_type;
	}

	public String getTransaction_id() {
		return transaction_id;
	}

	public void setTransaction_id(String transaction_id) {
		this.transaction_id = transaction_id;
	}

	public String getOut_trade_no() {
		return out_trade_no;
	}

	public void setOut_trade_no(String out_trade_no) {
		this.out_trade_no = out_trade_no;
	}

	public String getAttach() {
		return attach;
	}

	public void setAttach(String attach) {
		this.attach = attach;
	}

	public String getTime_end() {
		return time_end;
	}

	public void setTime_end(String time_end) {
		this.time_end = time_end;
	}

	public Integer getCash_fee() {
		return cash_fee;
	}

	public void setCash_fee(Integer cash_fee) {
		this.cash_fee = cash_fee;
	}

	public String getCash_fee_type() {
		return cash_fee_type;
	}

	public void setCash_fee_type(String cash_fee_type) {
		this.cash_fee_type = cash_fee_type;
	}

	public Integer getCoupon_count() {
		return coupon_count;
	}

	public void setCoupon_count(Integer coupon_count) {
		this.coupon_count = coupon_count;
	}

	public String getTrade_state_desc() {
		return trade_state_desc;
	}

	public void setTrade_state_desc(String trade_state_desc) {
		this.trade_state_desc = trade_state_desc;
	}


}
