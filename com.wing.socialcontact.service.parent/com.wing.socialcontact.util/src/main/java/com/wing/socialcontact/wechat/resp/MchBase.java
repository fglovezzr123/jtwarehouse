package com.wing.socialcontact.wechat.resp;


public abstract class MchBase {
	//返回状态码  SUCCESS/FAIL	此字段是通信标识，非交易标识，交易是否成功需要查看trade_state来判断
	protected String return_code;
	//返回信息，如非空，为错误原因	签名失败 参数格式校验错误
	protected String return_msg;
	//微信分配的公众账号ID
	protected String appid;
	//微信支付分配的商户号
	protected String mch_id;
	//随机字符串，不长于32位
	protected String nonce_str;
	//签名
	protected String sign;
	//业务结果 SUCCESS/FAIL
	protected String result_code;
	//错误代码
	protected String err_code;
	//错误代码描述
	protected String err_code_des;

	public String getReturn_code() {
		return return_code;
	}

	public void setReturn_code(String return_code) {
		this.return_code = return_code;
	}

	public String getReturn_msg() {
		return return_msg;
	}

	public void setReturn_msg(String return_msg) {
		this.return_msg = return_msg;
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

	public String getResult_code() {
		return result_code;
	}

	public void setResult_code(String result_code) {
		this.result_code = result_code;
	}

	public String getErr_code() {
		return err_code;
	}

	public void setErr_code(String err_code) {
		this.err_code = err_code;
	}

	public String getErr_code_des() {
		return err_code_des;
	}

	public void setErr_code_des(String err_code_des) {
		this.err_code_des = err_code_des;
	}
	public boolean isSuccess(){
		return "SUCCESS".equalsIgnoreCase(result_code)&&"SUCCESS".equalsIgnoreCase(return_code);
	}
}
