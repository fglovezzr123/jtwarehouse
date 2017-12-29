package com.tojoy.vhall.resp;

import java.util.Map;

public abstract class BaseResp {
	protected Integer code;
	protected String msg;
	protected boolean success = false;
	
	public BaseResp(Map<String, ?> map) {
		if(map==null || !map.containsKey("code") || !map.containsKey("msg")){
			this.code = 999;
			this.msg = "map参数格式错误";
		}else{
			this.code = Integer.valueOf(map.get("code").toString());
			this.msg = (String) map.get("msg");
			if(this.code!=null&&this.code.intValue()==200){
				this.success = true;
			}
		}
	}
	public Integer getCode() {
		return code;
	}
	public void setCode(Integer code) {
		this.code = code;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	public boolean isSuccess() {
		return success;
	}
}
