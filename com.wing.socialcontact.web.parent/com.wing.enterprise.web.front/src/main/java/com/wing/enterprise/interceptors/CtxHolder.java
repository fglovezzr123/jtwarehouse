package com.wing.enterprise.interceptors;

import javax.servlet.http.HttpServletRequest;

import com.wing.socialcontact.common.model.Member;
import com.wing.socialcontact.util.ServletUtil;

/**
 * 客户端上下文
 */
public class CtxHolder {
	private static ThreadLocal<Member> ctx = new ThreadLocal<Member>();

	public final static Member getMember() {
		return ctx.get();
	}

	public final static void setMember(HttpServletRequest request) {
		Member member = ServletUtil.getMember(request);
		//member.setId("10");
		//member.setWxUserId("10");
		ctx.set(member);
	}

	public final static String getUserId() {
		return ctx.get()==null?"":ctx.get().getId();
	}

	public final static String getUserName() {
		return ctx.get()==null?"":ctx.get().getUserName();
	}

	public final static String getWxUserId() {
		return ctx.get()==null?"":ctx.get().getWxUserId();
	}

	public final static String getOpenId() {
		return ctx.get()==null?"":ctx.get().getOpenId();
	}
}
