package com.wing.socialcontact.vhall.api;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.client.ClientProtocolException;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.wing.socialcontact.vhall.resp.UserIdResp;
import com.wing.socialcontact.vhall.resp.UserInfoResp;
import com.wing.socialcontact.vhall.resp.UserRegisterResp;

/**
 * 微吼第三方用户注册
 * @author liangwj
 * @version 1.0
 */
public class UserAPI extends BaseAPI {
	public static final String REGISTER_URL = "http://e.vhall.com/api/vhallapi/v2/user/register";
	public static final String GET_USER_ID_URL = "http://e.vhall.com/api/vhallapi/v2/user/get-user-id";
	public static final String GET_USER_INFO_URL = "http://e.vhall.com/api/vhallapi/v2/user/get-user-info";
	
	public final static UserRegisterResp registerThirdUserId(String thirdUserId,String name){
		Map<String, String> params = new HashMap<String, String>();
		params.put("third_user_id", thirdUserId);
		params.put("pass", THD_USER_PASS);
		params.put("name", name);
		Map<String, ?> result = doPostVhall(REGISTER_URL, params);
		return new UserRegisterResp(result);
	}
	
	public final static UserIdResp getThirdUserId(String thirdUserId){
		Map<String, String> params = new HashMap<String, String>();
		params.put("third_user_id", thirdUserId);
		Map<String, ?> result = doPostVhall(GET_USER_ID_URL, params);
		return new UserIdResp(result);
	}
	public final static UserInfoResp getUserInfo(String vhallUserId){
		Map<String, String> params = new HashMap<String, String>();
		params.put("user_id", vhallUserId);
		params.put("fields", "name,head,customized_field");
		Map<String, ?> result = doPostVhall(GET_USER_INFO_URL, params);
		return new UserInfoResp(result);
	}
	
	
	public static void main(String[] args) throws ClientProtocolException, IOException {
		//{"code":200,"data":{"user_id":"17699875"},"msg":"success"}
		//{"code":10070,"data":[],"msg":"第三方用户不能参数为空"}
		UserRegisterResp result1 = registerThirdUserId("ww","ww");
		System.out.println(JSON.toJSON(result1));
		
	}
}
