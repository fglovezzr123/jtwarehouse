package com.wing.socialcontact.front.util;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class OkhttpUtils {

	/**
	 * 发送http请求
	 * @param url 请求url
	 * @param param post 参数 json 字符串
	 * @param authorization 加密后的用户信息
	 */
	public static String sendHttpRequest(String url,String param,String authorization) {
		OkHttpClient okHttpClient = new OkHttpClient();
		MediaType mediaType = MediaType.parse("application/json");
		RequestBody body = RequestBody.create(mediaType,
				param);

		Request request = new Request.Builder()
				.url(url)
				.post(body)
				.addHeader("content-type", "application/json")
				.addHeader("authorization",
						authorization)
				.addHeader("cache-control", "no-cache").build();

		Call call = okHttpClient.newCall(request);
		try {
			Response response = call.execute();
			return response.body().string();
		} catch (IOException e) {
			e.printStackTrace();
			
		}
		return null;
	}

	/*public static void main(String[] args) {
		String result = OkhttpUtils.sendHttpRequest(Constants.CREATE_GROUP_NOTIFY_URL,
				"{\n  \"id\": \"323c5ff0-0149-4934-9b5a-a652990e329f\"\n}",
				Constants.AUTHORIZATION);
		System.out.println(result);
	}*/

}
