package com.tojoy.vhall.resp;

import java.util.Map;

public class UserInfoResp extends BaseResp {
	private String name;
	
	private String head;
	
	private String customized_field;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getHead() {
		return head;
	}

	public void setHead(String head) {
		this.head = head;
	}

	public String getCustomized_field() {
		return customized_field;
	}

	public void setCustomized_field(String customized_field) {
		this.customized_field = customized_field;
	}

	@SuppressWarnings("unchecked")
	public UserInfoResp(Map<String, ?> map) {
		super(map);
		if(isSuccess()){
			Map<String,Object> data = (Map<String, Object>) map.get("data");
			
		}
	}
}
