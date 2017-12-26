package com.tojoy.vhall.resp;

import java.util.Map;

public class UserIdResp extends BaseResp {
	private String id;
	
	public String getId() {
		return id;
	}
	@SuppressWarnings("unchecked")
	public UserIdResp(Map<String, ?> map) {
		super(map);
		if(isSuccess()){
			Map<String,Object> data = (Map<String, Object>) map.get("data");
			this.id = data.get("id")==null?"":data.get("id").toString();
		}
	}
}
