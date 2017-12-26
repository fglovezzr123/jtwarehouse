package com.wing.socialcontact.util;


public class PaymentRequestBean {

	private static final long serialVersionUID = -7682226991855956344L;

	/**版本号**/
	private String version;

	/**支付交易商户编号**/
	private String oid_partner;

    /**商户用唯一编号**/
	private String user_id;

	/**时间戳**/
	private String timestamp;

    /** 签名方式 . */
    private String sign_type;

    /** 签名方 . */
    private String sign;

    /**商户业务类型     虚拟商品销售： 101001  实物商品销售： 109001**/
	private String busi_partner;

    /** 商户付款流水号 . */
    private String no_order;

    /** 商户付款时间 . */
    private String dt_order;

    /**商品名称**/
    private String name_goods;

    /** 付款金额 . */
    private String money_order;

    /** 服务器异步通知地址 . */
    private String notify_url;

	private String risk_item;

	private String url_return;

	private String app_request;

	/***风控基本参数***/
	private String frms_ware_category;

	private String user_info_mercht_userno;

	private String user_info_dt_register;
	
	private String back_url;

	private String bg_color;

	public String getBack_url() {
		return back_url;
	}

	public void setBack_url(String back_url) {
		this.back_url = back_url;
	}

	public String getBg_color() {
		return bg_color;
	}

	public void setBg_color(String bg_color) {
		this.bg_color = bg_color;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getOid_partner() {
		return oid_partner;
	}

	public void setOid_partner(String oid_partner) {
		this.oid_partner = oid_partner;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public String getSign_type() {
		return sign_type;
	}

	public void setSign_type(String sign_type) {
		this.sign_type = sign_type;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getBusi_partner() {
		return busi_partner;
	}

	public void setBusi_partner(String busi_partner) {
		this.busi_partner = busi_partner;
	}

	public String getNo_order() {
		return no_order;
	}

	public void setNo_order(String no_order) {
		this.no_order = no_order;
	}

	public String getDt_order() {
		return dt_order;
	}

	public void setDt_order(String dt_order) {
		this.dt_order = dt_order;
	}

	public String getName_goods() {
		return name_goods;
	}

	public void setName_goods(String name_goods) {
		this.name_goods = name_goods;
	}

	public String getMoney_order() {
		return money_order;
	}

	public void setMoney_order(String money_order) {
		this.money_order = money_order;
	}

	public String getNotify_url() {
		return notify_url;
	}

	public void setNotify_url(String notify_url) {
		this.notify_url = notify_url;
	}

	public String getRisk_item() {
		return risk_item;
	}

	public void setRisk_item(String risk_item) {
		this.risk_item = risk_item;
	}

	public String getUrl_return() {
		return url_return;
	}

	public void setUrl_return(String url_return) {
		this.url_return = url_return;
	}

	public String getApp_request() {
		return app_request;
	}

	public void setApp_request(String app_request) {
		this.app_request = app_request;
	}

	public String getFrms_ware_category() {
		return frms_ware_category;
	}

	public void setFrms_ware_category(String frms_ware_category) {
		this.frms_ware_category = frms_ware_category;
	}

	public String getUser_info_mercht_userno() {
		return user_info_mercht_userno;
	}

	public void setUser_info_mercht_userno(String user_info_mercht_userno) {
		this.user_info_mercht_userno = user_info_mercht_userno;
	}

	public String getUser_info_dt_register() {
		return user_info_dt_register;
	}

	public void setUser_info_dt_register(String user_info_dt_register) {
		this.user_info_dt_register = user_info_dt_register;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}






}
