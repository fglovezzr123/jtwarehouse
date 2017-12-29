/**
 * 
 */
package com.wing.socialcontact.front.util;

/**
 * 关注或者扫描公众号后，自动回复设置
 * 
 * @author zhangpengzhi
 *
 */
public class WxAutoReplyUtil {

	public static final String RESPONSE_TXT = "<xml><ToUserName><![CDATA[%s]]></ToUserName><FromUserName><![CDATA[%s]]></FromUserName><CreateTime>%s</CreateTime><MsgType><![CDATA[%s]]></MsgType><Content><![CDATA[%s]]></Content></xml>";
	public static final String RESPONSE_NEWS = "<xml>\r\n" + "<ToUserName><![CDATA[%s]]></ToUserName>\r\n"
			+ "<FromUserName><![CDATA[%s]]></FromUserName>\r\n" + "<CreateTime>%s</CreateTime>\r\n"
			+ "<MsgType><![CDATA[news]]></MsgType>\r\n" + "<ArticleCount>1</ArticleCount>\r\n" + "<Articles>\r\n"
			+ "<item>\r\n" + "<Title><![CDATA[%s]]></Title> \r\n"
			+ "<Description><![CDATA[%s]]></Description>\r\n"
			+ "<PicUrl><![CDATA[%s]]></PicUrl>\r\n"
			+ "<Url><![CDATA[%s]]></Url>\r\n" + "</item>\r\n"
			+ "</Articles>\r\n" + "</xml>";
	
	public static final String TEST_WXOFFICIAL_MEDIA_ID = "Fcil5ooGuE_eucBaXhLmXfFkKVU9_dhF8sdu4_QaSAA";
	public static final String ONLINE_WXOFFICIAL_MEDIA_ID = "teuIZstXicTRBK9ZGl2c6JFN83fZIUcNTQMkVwoD_-0";
}
