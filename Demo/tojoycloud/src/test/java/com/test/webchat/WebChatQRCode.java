package com.test.webchat;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.tojoycloud.frame.common.AccessTokenCache;
import com.tojoycloud.frame.common.HttpsRequest;

public class WebChatQRCode
{
	public static void main(String[] args)
	{
//		 setQRCode();
		setUserLabel();
	}

	/**
	 * 设施各渠道的二维码
	 */
	public static void setQRCode()
	{
		// 设置渠道二维码
		String getTicket = "https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token=" + AccessTokenCache.getInstance().getAccessToken();
		// 临时二维码
//		String data = "{\"expire_seconds\": 3600, \"action_name\": \"QR_SCENE\", \"action_info\": {\"scene\": {\"scene_id\": \"beijing\"}}}";
		// 永久二维码
		String data = "{\"action_name\": \"QR_LIMIT_SCENE\", \"action_info\": {\"scene\": {\"scene_id\": 100}}}";
		String ticketString = HttpsRequest.sendHttpsRequest(getTicket, data);
		JSONObject object = JSON.parseObject(ticketString.toString());
		if (object.containsKey("ticket"))
			System.out.println("https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket=" + (String) object.get("ticket"));
	}

	public static void setUserLabel()
	{
		// 获取所有的标签列表
		String getLabels = "https://api.weixin.qq.com/cgi-bin/tags/get?access_token=" + AccessTokenCache.getInstance().getAccessToken();
		String labelsList = HttpsRequest.sendHttpsRequest(getLabels, null);
		System.out.println(labelsList);// {"tags":[{"id":2,"name":"星标组","count":0},{"id":100,"name":"北京机场","count":0}]}
		String setLabel = "https://api.weixin.qq.com/cgi-bin/tags/members/batchtagging?access_token=" + AccessTokenCache.getInstance().getAccessToken();
		String userLabelData = "{\"openid_list\":[\"oBhgswvmo4JUxls86QBJ7OH1wSns\"],\"tagid\":100}";
		String setLabelResult = HttpsRequest.sendHttpsRequest(setLabel, userLabelData);
		System.out.println(setLabelResult);
	}
}
