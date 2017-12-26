package com.tojoy.vhall.resp;

import java.util.Map;

public class UserRegisterResp extends BaseResp {
	private Integer user_id;
	
	public Integer getUser_id() {
		return user_id;
	}
	@SuppressWarnings("unchecked")
	public UserRegisterResp(Map<String, ?> map) {
		super(map);
		if(isSuccess()){
			Map<String,Object> data = (Map<String, Object>) map.get("data");
			try {
				this.user_id = (Integer) data.get("user_id");
			} catch (Exception e) {
				this.success = false;
				this.user_id=null;
				this.msg = data.get("user_id")==null?"":data.get("user_id").toString();
			}
		}
	}
}
