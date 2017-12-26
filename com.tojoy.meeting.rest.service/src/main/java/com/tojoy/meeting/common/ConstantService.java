package com.tojoy.meeting.common;


import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;

@Getter
@Setter
public class ConstantService
{
    //报名成功模板
	public String MEETING_SIGNUP_SUCCESS;

	//报名成功发送消息
	public String MEETING_SIGNUP_MESSAGE;
}
