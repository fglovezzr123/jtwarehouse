package com.wing.socialcontact.util;


import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSON;
import com.wing.socialcontact.util.bean.NewsContent;
import com.wing.socialcontact.util.bean.TextContent;

/**
 * 微信获取消息内容通用接口
 * 
 * @ClassName: WxMsmUtil
 * @Description: TODO
 * @author: zengmin
 * @date: 2017年4月17日 下午12:02:19
 */
public class WxMsmUtil {
	/**
	 * 组装文本消息内容
	 * 
	 * @Title: getTextMessageContent
	 * @Description: TODO
	 * @param content
	 * @return
	 * @return: String
	 * @author: zengmin
	 * @date: 2017年4月17日 下午12:06:32
	 */
	public static String getTextMessageContent(String content) {
		if (StringUtils.isEmpty(content)) {
			return "error";
		}
		TextContent text = new TextContent(content);
		String result = JSON.toJSONString(text);
		return result;
	}

	/**
	 * 组装图文消息内容
	 * 
	 * @Title: getNewsMessageContent
	 * @Description: TODO
	 * @param content
	 * @return
	 * @return: String
	 * @author: zengmin
	 * @date: 2017年4月17日 下午12:06:32
	 */
	public static String getNewsMessageContent(NewsContent content) {
		if (null == content) {
			return "error";
		}
		String result = JSON.toJSONString(content);
		return result;
	}
}
