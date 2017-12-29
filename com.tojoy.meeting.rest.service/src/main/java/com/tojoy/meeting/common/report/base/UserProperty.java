package com.tojoy.meeting.common.report.base;

public class UserProperty
{
	// 用户ID
	private String userId;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}


	// TODO:其余参数可根据业务进行添加，需要剥离出report放到基础包中供其他项目引用

}
