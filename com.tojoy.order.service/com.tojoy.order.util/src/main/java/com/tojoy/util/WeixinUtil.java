package com.tojoy.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.StringWriter;
import java.net.ConnectException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

import com.tojoy.util.pojo.CommonUrlButton;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;

import org.apache.commons.collections.map.LinkedMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.Cache.ValueWrapper;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tojoy.util.pojo.AccessToken;
import com.tojoy.util.pojo.ComplexButton;
import com.tojoy.util.pojo.Menu;

/**
 * 公众平台通用接口工具类
 * 
 * @author liuyq
 * @date 2013-08-09
 */
public class WeixinUtil {
	private static Logger log = LoggerFactory.getLogger(WeixinUtil.class);

	private static ObjectMapper objectMapper = null;

	// private static final String appId = "wxdeb91ec655bab664";
	// private static final String appSecret =
	// "56125d3a84cc4a8be6ca375822d63441";

	public static ObjectMapper getIntance() {
		if (objectMapper == null)
			objectMapper = new ObjectMapper();
		return objectMapper;
	}

	public static String toJson(Object obj) {
		StringWriter stringWriter = new StringWriter();
		try {
			getIntance().writeValue(stringWriter, obj);
			String ss = stringWriter.toString();
			ss = thkey(ss).replaceAll("\";:", "\":");
			return ss;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			try {
				stringWriter.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public static String thkey(String str) {
		int ei = str.indexOf("\":");
		if (ei != -1) {
			String substr = str.substring(0, ei);
			int si = substr.lastIndexOf("\"");
			String subkey = substr.substring(si, ei);
			str = str.replace(subkey + "\":", subkey.toLowerCase() + "\";:");
			str = thkey(str);
		}
		return str;
	}

	/**
	 * 发起https请求并获取结果
	 * 
	 * @param requestUrl
	 *            请求地址
	 * @param requestMethod
	 *            请求方式（GET、POST）
	 * @param outputStr
	 *            提交的数据
	 * @return JSONObject(通过JSONObject.get(key)的方式获取json对象的属性值)
	 */
	public static String httpRequestStr(String requestUrl, String requestMethod, String outputStr) {
		String result = null;
		StringBuffer buffer = new StringBuffer();
		try {
			// 创建SSLContext对象，并使用我们指定的信任管理器初始化
			TrustManager[] tm = { new MyX509TrustManager() };
			SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
			sslContext.init(null, tm, new java.security.SecureRandom());
			// 从上述SSLContext对象中得到SSLSocketFactory对象
			SSLSocketFactory ssf = sslContext.getSocketFactory();

			URL url = new URL(requestUrl);
			HttpsURLConnection httpUrlConn = (HttpsURLConnection) url.openConnection();
			httpUrlConn.setSSLSocketFactory(ssf);

			httpUrlConn.setDoOutput(true);
			httpUrlConn.setDoInput(true);
			httpUrlConn.setUseCaches(false);
			// 设置请求方式（GET/POST）
			httpUrlConn.setRequestMethod(requestMethod);

			if ("GET".equalsIgnoreCase(requestMethod))
				httpUrlConn.connect();

			// 当有数据需要提交时
			if (null != outputStr) {
				OutputStream outputStream = httpUrlConn.getOutputStream();
				// 注意编码格式，防止中文乱码
				outputStream.write(outputStr.getBytes("UTF-8"));
				outputStream.close();
			}

			// 将返回的输入流转换成字符串
			InputStream inputStream = httpUrlConn.getInputStream();
			InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
			BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

			String str = null;
			while ((str = bufferedReader.readLine()) != null) {
				buffer.append(str);
			}
			bufferedReader.close();
			inputStreamReader.close();
			// 释放资源
			inputStream.close();
			inputStream = null;
			httpUrlConn.disconnect();
			result = buffer.toString();
		} catch (ConnectException ce) {
			log.error("Weixin server connection timed out.");
		} catch (Exception e) {
			log.error("https request error:{}", e);
		}
		return result;
	}

	/**
	 * 发起https请求并获取结果
	 * 
	 * @param requestUrl
	 *            请求地址
	 * @param requestMethod
	 *            请求方式（GET、POST）
	 * @param outputStr
	 *            提交的数据
	 * @return JSONObject(通过JSONObject.get(key)的方式获取json对象的属性值)
	 */
	public static JSONObject httpRequest(String requestUrl, String requestMethod, String outputStr) {
		String str = httpRequestStr(requestUrl, requestMethod, outputStr);
		System.out.println("httpRequest:" + str);
		JSONObject jsonObject = null;
		try {
			jsonObject = JSONObject.fromObject(str);
		} catch (Exception e) {
		}
		return jsonObject;
	}

	// 获取服务号access_token的接口地址（GET） 限200（次/天）
	public final static String access_token_url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
	public final static String jsapi_ticket_url = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=ACCESS_TOKEN&type=jsapi";

	/**
	 * 获取服务号access_token
	 * 
	 * @param appid
	 *            凭证
	 * @param appsecret
	 *            密钥
	 * @return
	 */
	public static AccessToken getAccessToken(String appid, String secret) {
		RedisCache redisCache = (RedisCache) SpringContextUtil.getBean("redisCache");
		AccessToken a = null;
		ValueWrapper vw = redisCache.get("Cache_AccessToken_FWH" + appid);
		Map amap = null;
		if (vw != null) {
			amap = (Map) vw.get();
			log.info("get Cache_AccessToken_FWH" + appid + "++++++++++:" + amap.toString());
			a = new AccessToken();
			a.setExpiresDate((Date) amap.get("expiresDate"));
			a.setExpiresIn((int) amap.get("expiresIn"));
			a.setJsapi_ticket((String) amap.get("jsapi_ticket"));
			a.setToken((String) amap.get("token"));
		}
		if (a == null || a.getExpiresDate().getTime() < (new Date()).getTime()) {
			String requestUrl = access_token_url.replace("APPID", appid).replace("APPSECRET", secret);
			JSONObject jsonObject = httpRequest(requestUrl, "GET", null);
			// 如果请求成功
			if (null != jsonObject) {
				try {
					a = new AccessToken();
					a.setToken(jsonObject.getString("access_token"));
					int expires_in = jsonObject.getInt("expires_in");
					// a.setExpiresIn(7200);
					a.setExpiresIn(expires_in);
					Date now = new Date();
					a.setExpiresDate(new Date(now.getTime() + (expires_in - 20) * 1000));
					String jsapi_ticket = getJsapiTicket(a.getToken());
					a.setJsapi_ticket(jsapi_ticket);

					amap = new HashMap();
					amap.put("expiresDate", a.getExpiresDate());
					amap.put("expiresIn", a.getExpiresIn());
					amap.put("jsapi_ticket", a.getJsapi_ticket());
					amap.put("token", a.getToken());
					redisCache.put("Cache_AccessToken_FWH" + appid, amap);

					log.info("set Cache_AccessToken_FWH" + appid + "++++++++++:" + amap.toString());
				} catch (JSONException e) {
					// 获取token失败
					log.error("获取token失败 errcode:{} errmsg:{}", jsonObject.getInt("errcode"),
							jsonObject.getString("errmsg"));
				}
			}
		}
		System.out.println("etAccessToken++++++++++:token:" + a.getToken() + " time:" + a.getExpiresDate());
		return a;
	}

	/**
	 * 获取服务号access_token测试用
	 * 
	 * @param appid
	 *            凭证
	 * @param appsecret
	 *            密钥
	 * @return
	 */
	private static AccessToken getAccessTokenTest(String appid, String secret) {
		// RedisCache redisCache = (RedisCache)
		// SpringContextUtil.getBean("redisCache");
		// System.out.println("get getAccessToken++++++++++:" + appid);
		AccessToken a = null;
		// ValueWrapper vw = redisCache.get("Cache_AccessToken_FWH" + appid);
		// Map amap = null;
		// if (vw != null) {
		// amap = (Map) vw.get();
		// System.out.println("get Cache_AccessToken_FWH" + appid +
		// "++++++++++:" + amap.toString());
		// a = new AccessToken();
		// a.setExpiresDate((Date) amap.get("expiresDate"));
		// a.setExpiresIn((int) amap.get("expiresIn"));
		// a.setJsapi_ticket((String) amap.get("jsapi_ticket"));
		// a.setToken((String) amap.get("token"));
		// }
		// if (a == null || a.getExpiresDate().getTime() < (new
		// Date()).getTime()) {
		String requestUrl = access_token_url.replace("APPID", appid).replace("APPSECRET", secret);
		JSONObject jsonObject = httpRequest(requestUrl, "GET", null);
		// 如果请求成功
		if (null != jsonObject) {
			try {
				a = new AccessToken();
				a.setToken(jsonObject.getString("access_token"));
				a.setExpiresIn(7200);
				Date now = new Date();
				a.setExpiresDate(new Date(now.getTime() + 7100000));
				String jsapi_ticket = getJsapiTicket(a.getToken());
				a.setJsapi_ticket(jsapi_ticket);

				// amap = new HashMap();
				// amap.put("expiresDate", a.getExpiresDate());
				// amap.put("expiresIn", a.getExpiresIn());
				// amap.put("jsapi_ticket", a.getJsapi_ticket());
				// amap.put("token", a.getToken());
				// redisCache.put("Cache_AccessToken_FWH" + appid, amap);

				// System.out.println("set Cache_AccessToken_FWH" + appid +
				// "++++++++++:" + amap.toString());
			} catch (JSONException e) {
				// 获取token失败
				log.error("获取token失败 errcode:{} errmsg:{}", jsonObject.getInt("errcode"),
						jsonObject.getString("errmsg"));
			}
		}
		// }
		return a;
	}

	private static String getJsapiTicket(String token) {
		try {
			if (StringUtils.hasLength(token)) {
				String requestUrl = jsapi_ticket_url.replace("ACCESS_TOKEN", token);
				JSONObject jsonObject = httpRequest(requestUrl, "GET", null);
				// 如果请求成功
				if (null != jsonObject) {
					System.out.println("jsapi result:" + jsonObject.toString());
					return jsonObject.getString("ticket");
				}
			}
		} catch (Exception e) {
		}
		return "";
	}

	// 获取企业号access_token的接口地址（GET） 限200（次/天）
	public final static String access_token_url_qyh = "https://qyapi.weixin.qq.com/cgi-bin/gettoken?corpid=CORPID&corpsecret=CORPSECRET";
	public final static String jsapi_ticket_url_qyh = "https://qyapi.weixin.qq.com/cgi-bin/get_jsapi_ticket?access_token=ACCESS_TOKEN";

	/**
	 * 获取企业号accessToken
	 * 
	 * @Title: getAccessTokenQyh
	 * @Description: TODO
	 * @param corpid
	 * @param secret
	 * @return
	 * @return: AccessToken
	 * @author: zengmin
	 * @date: 2017年5月14日 下午4:40:08
	 */
	public static AccessToken getAccessTokenQyh(String corpid, String secret) {
		RedisCache redisCache = (RedisCache) SpringContextUtil.getBean("redisCache");
		System.out.println("get getAccessTokenQYH++++++++++:" + corpid);
		AccessToken a = null;
		ValueWrapper vw = redisCache.get("Cache_AccessToken_QYH" + corpid);
		Map amap = null;
		if (vw != null) {
			amap = (Map) vw.get();
			System.out.println("get Cache_AccessToken_QYH" + corpid + "++++++++++:" + amap.toString());
			a = new AccessToken();
			a.setExpiresDate((Date) amap.get("expiresDate"));
			a.setExpiresIn((int) amap.get("expiresIn"));
			a.setJsapi_ticket((String) amap.get("jsapi_ticket"));
			a.setToken((String) amap.get("token"));
		}
		if (a == null || a.getExpiresDate().getTime() < (new Date()).getTime()) {
			String requestUrl = access_token_url_qyh.replace("CORPID", corpid).replace("CORPSECRET", secret);
			JSONObject jsonObject = httpRequest(requestUrl, "GET", null);
			// 如果请求成功
			if (null != jsonObject) {
				try {
					a = new AccessToken();
					a.setToken(jsonObject.getString("access_token"));
					a.setExpiresIn(7200);
					Date now = new Date();
					a.setExpiresDate(new Date(now.getTime() + 7100000));
					String jsapi_ticket = getJsapiTicketQyh(a.getToken());
					a.setJsapi_ticket(jsapi_ticket);

					amap = new HashMap();
					amap.put("expiresDate", a.getExpiresDate());
					amap.put("expiresIn", a.getExpiresIn());
					amap.put("jsapi_ticket", a.getJsapi_ticket());
					amap.put("token", a.getToken());
					redisCache.put("Cache_AccessToken_QYH" + corpid, amap);

					System.out.println("set Cache_AccessToken_QYH" + corpid + "++++++++++:" + amap.toString());
				} catch (JSONException e) {
					// 获取token失败
					log.error("获取token失败 errcode:{} errmsg:{}", jsonObject.getInt("errcode"),
							jsonObject.getString("errmsg"));
				}
			}
		}
		return a;
	}

	/**
	 * 获取企业号accessToken
	 * 
	 * @Title: getAccessTokenQyh
	 * @Description: TODO
	 * @param corpid
	 * @param secret
	 * @return
	 * @return: AccessToken
	 * @author: zengmin
	 * @date: 2017年5月14日 下午4:40:08
	 */
	public static AccessToken getAccessTokenQyhTest(String corpid, String secret) {
		System.out.println("get getAccessTokenQYHTest++++++++++:" + corpid);
		AccessToken a = null;
		if (a == null || a.getExpiresDate().getTime() < (new Date()).getTime()) {
			String requestUrl = access_token_url_qyh.replace("CORPID", corpid).replace("CORPSECRET", secret);
			JSONObject jsonObject = httpRequest(requestUrl, "GET", null);
			// 如果请求成功
			if (null != jsonObject) {
				try {
					a = new AccessToken();
					a.setToken(jsonObject.getString("access_token"));
					a.setExpiresIn(7200);
					Date now = new Date();
					a.setExpiresDate(new Date(now.getTime() + 7100000));
					String jsapi_ticket = getJsapiTicketQyh(a.getToken());
					a.setJsapi_ticket(jsapi_ticket);
				} catch (JSONException e) {
					// 获取token失败
					log.error("获取token失败 errcode:{} errmsg:{}", jsonObject.getInt("errcode"),
							jsonObject.getString("errmsg"));
				}
			}
		}
		return a;
	}

	private static String getJsapiTicketQyh(String token) {
		try {
			if (StringUtils.hasLength(token)) {
				String requestUrl = jsapi_ticket_url_qyh.replace("ACCESS_TOKEN", token);
				JSONObject jsonObject = httpRequest(requestUrl, "GET", null);
				// 如果请求成功
				if (null != jsonObject) {
					System.out.println("jsapi result:" + jsonObject.toString());
					return jsonObject.getString("ticket");
				}
			}
		} catch (Exception e) {
		}
		return "";
	}

	// 菜单创建（POST） 限100（次/天）

	public static String menu_create_url = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN";

	/**
	 * 创建菜单
	 * 
	 * @param menu
	 *            菜单实例
	 * @param accessToken
	 *            有效的access_token
	 * @return 0表示成功，其他值表示失败
	 */
	public static int createMenu(Menu menu, String accessToken) {
		int result = 0;
		// 拼装创建菜单的url
		String url = menu_create_url.replace("ACCESS_TOKEN", accessToken);
		// 将菜单对象转换成json字符串
		String jsonMenu = JSONObject.fromObject(menu).toString();
		// 调用接口创建菜单

		System.out.println(jsonMenu);
		String s = httpRequestStr(url, "POST", jsonMenu);
		System.out.println(s);
		JSONObject jsonObject = JSONObject.fromObject(s);
		if (null != jsonObject) {
			if (0 != jsonObject.getInt("errcode")) {
				result = jsonObject.getInt("errcode");
				log.error("创建菜单失败 errcode:{} errmsg:{}", jsonObject.getInt("errcode"), jsonObject.getString("errmsg"));
			}
		}
		return result;
	}

	public static int createMenu(Menu menu, String corpid, String secret) {
		AccessToken a = getAccessTokenTest(corpid, secret);
		if (null == a) {
			return -1;
		}
		return createMenu(menu, a.getToken());
	}

	// 删除菜单 限100（次/天）

	public static String delete_menu_url = "https://api.weixin.qq.com/cgi-bin/menu/delete?access_token=ACCESS_TOKEN";

	/**
	 * 创建菜单
	 * 
	 * @param menu
	 *            菜单实例
	 * @param accessToken
	 *            有效的access_token
	 * @return 0表示成功，其他值表示失败
	 */
	public static int deleteMenu(String accessToken) {
		int result = 0;
		// 拼装创建菜单的url
		String url = delete_menu_url.replace("ACCESS_TOKEN", accessToken);

		String s = httpRequestStr(url, "GET", null);
		JSONObject jsonObject = JSONObject.fromObject(s);
		if (null != jsonObject) {
			if (0 != jsonObject.getInt("errcode")) {
				result = jsonObject.getInt("errcode");
			}
		}
		return result;
	}

	public static int deleteMenu(String corpid, String secret) {
		AccessToken a = getAccessToken(corpid, secret);
		if (null == a) {
			return -1;
		}
		return deleteMenu(a.getToken());
	}

	public static String view_menu_url = "https://api.weixin.qq.com/cgi-bin/menu/get?access_token=ACCESS_TOKEN";

	/**
	 * 查看菜单
	 * 
	 * @param menu
	 *            菜单实例
	 * @param accessToken
	 *            有效的access_token
	 * @return 0表示成功，其他值表示失败
	 */
	public static String viewMenu(String accessToken) {
		int result = 0;
		// 拼装创建菜单的url
		String url = view_menu_url.replace("ACCESS_TOKEN", accessToken);

		String s = httpRequestStr(url, "GET", null);
		return s;
	}

	public static String viewMenu(String corpid, String secret) {
		AccessToken a = getAccessTokenTest(corpid, secret);
		if (null == a) {
			return "";
		}
		return viewMenu(a.getToken());
	}

	// 获取部门列表
	public final static String getorg_url = "https://qyapi.weixin.qq.com/cgi-bin/department/list?access_token=ACCESS_TOKEN";

	public static JSONObject getOrgs(String corpid, String secret) {
		AccessToken at = WeixinUtil.getAccessTokenQyh(corpid, secret);
		String requestUrl = getorg_url.replace("ACCESS_TOKEN", at.getToken());
		JSONObject jsonObject = httpRequest(requestUrl, "GET", null);
		return jsonObject;
	}

	// 获取部门成员
	public final static String getuserbyorgid_url = "https://qyapi.weixin.qq.com/cgi-bin/user/simplelist?access_token=ACCESS_TOKEN&department_id=ORGID&fetch_child=0&status=1";

	public static JSONObject getUserByOrgId(String corpid, String secret, String orgId) {
		AccessToken at = WeixinUtil.getAccessTokenQyh(corpid, secret);
		String requestUrl = getuserbyorgid_url.replace("ACCESS_TOKEN", at.getToken()).replace("ORGID", orgId);
		JSONObject jsonObject = httpRequest(requestUrl, "GET", null);
		return jsonObject;
	}

	// 获取部门成员（详情）
	public final static String getuserbyorgiddetail_url = "https://qyapi.weixin.qq.com/cgi-bin/user/list?access_token=ACCESS_TOKEN&department_id=DEPARTMENT_ID&fetch_child=FETCH_CHILD&status=STATUS";

	public static JSONObject getUserDetailByOrgId(String corpid, String secret, String orgId, String fetchChild,
			String status) {
		AccessToken at = WeixinUtil.getAccessTokenQyh(corpid, secret);
		String requestUrl = getuserbyorgiddetail_url.replace("ACCESS_TOKEN", at.getToken())
				.replace("DEPARTMENT_ID", orgId).replace("FETCH_CHILD", fetchChild).replace("STATUS", status);
		JSONObject jsonObject = httpRequest(requestUrl, "GET", null);
		return jsonObject;
	}

	// 获取成员
	public final static String getuserbyUserid_url = "https://qyapi.weixin.qq.com/cgi-bin/user/get?access_token=ACCESS_TOKEN&userid=USERID";

	public static JSONObject getUserByUserId(String corpid, String secret, String userid) {
		AccessToken at = WeixinUtil.getAccessTokenQyh(corpid, secret);
		String requestUrl = getuserbyUserid_url.replace("ACCESS_TOKEN", at.getToken()).replace("USERID", userid);
		JSONObject jsonObject = httpRequest(requestUrl, "GET", null);
		return jsonObject;
	}

	// 创建成员
	public final static String createUser_url = "https://qyapi.weixin.qq.com/cgi-bin/user/create?access_token=ACCESS_TOKEN";

	public static JSONObject createUser(String corpid, String secret, String data) {
		AccessToken at = WeixinUtil.getAccessTokenQyh(corpid, secret);
		String requestUrl = createUser_url.replace("ACCESS_TOKEN", at.getToken());
		JSONObject jsonObject = httpRequest(requestUrl, "POST", data);
		return jsonObject;
	}

	// 创建成员
	public final static String updateUser_url = "https://qyapi.weixin.qq.com/cgi-bin/user/update?access_token=ACCESS_TOKEN";

	public static JSONObject updateUser(String corpid, String secret, String data) {
		AccessToken at = WeixinUtil.getAccessTokenQyh(corpid, secret);
		String requestUrl = updateUser_url.replace("ACCESS_TOKEN", at.getToken());
		JSONObject jsonObject = httpRequest(requestUrl, "POST", data);
		return jsonObject;
	}

	// 批量删除成员
	public final static String deleteUser_url = "https://qyapi.weixin.qq.com/cgi-bin/user/delete?access_token=ACCESS_TOKEN&userid=USERID";

	public static JSONObject deleteUser(String corpid, String secret, String userId) {
		AccessToken at = WeixinUtil.getAccessTokenQyh(corpid, secret);
		String requestUrl = deleteUser_url.replace("ACCESS_TOKEN", at.getToken()).replace("USERID", userId);
		JSONObject jsonObject = httpRequest(requestUrl, "GET", null);
		return jsonObject;
	}

	public final static String batchDeleteUser_url = "https://qyapi.weixin.qq.com/cgi-bin/user/batchdelete?access_token=ACCESS_TOKEN";

	public static JSONObject deleteUserAll(String corpid, String secret, String data) {
		AccessToken at = WeixinUtil.getAccessTokenQyh(corpid, secret);
		String requestUrl = batchDeleteUser_url.replace("ACCESS_TOKEN", at.getToken());
		JSONObject jsonObject = httpRequest(requestUrl, "POST", data);
		return jsonObject;
	}

	public static String upwxmenupub(String appId, String appSecret) {
		String domain = ConfigUtil.DOMAIN + "/wxfront";
		CommonUrlButton b = null;
		ComplexButton cb = null;
		List firstbtns = new ArrayList();
		List sbtns = null;

		// cb = new ComplexButton();
		// cb.setName("圣熹集团");
		//
		// sbtns = new ArrayList();
		// b = new CommonUrlButton();
		// b.setType("view");
		// b.setName("六六下午茶");
		// b.setUrl(domain + "/wx/article_view.htm?id=196646");
		// sbtns.add(b);
		//
		// b = new CommonUrlButton();
		// b.setType("view");
		// b.setName("靖观天下");
		// b.setUrl(domain + "/wx/article_view.htm?id=196651");
		// sbtns.add(b);
		//
		// b = new CommonUrlButton();
		// b.setType("view");
		// b.setName("圣熹模式");
		// b.setUrl(domain + "/wx/article_view.htm?id=196649");
		// sbtns.add(b);
		//
		// cb.setSub_button(sbtns);
		// firstbtns.add(cb);
		//
		// b = new CommonUrlButton();
		// b.setType("view");
		// b.setName("圣熹e购");
		// b.setUrl(domain + "/wx/index.htm");
		// firstbtns.add(b);
		//
		// cb = new ComplexButton();
		// cb.setName("客服中心");
		//
		// sbtns = new ArrayList();
		// b = new CommonUrlButton();
		// b.setType("view");
		// b.setName("申请会员");
		// b.setUrl(domain + "/wx/reg.htm");
		// sbtns.add(b);
		//
		// b = new CommonUrlButton();
		// b.setType("view");
		// b.setName("申请合伙人");
		// b.setUrl(domain + "/wx/hhr_sq.htm");
		// sbtns.add(b);
		//
		// b = new CommonUrlButton();
		// b.setType("view");
		// b.setName("个人中心");
		// b.setUrl(domain + "/wx/my_center.htm");
		// sbtns.add(b);
		//
		// b = new CommonUrlButton();
		// b.setType("view");
		// b.setName("圣熹女人圈");
		// b.setUrl(domain + "/wx/article_view.htm?id=196652");
		// sbtns.add(b);

		b = new CommonUrlButton();
		b.setType("view");
		b.setName("首页");
		b.setUrl(domain + "/m/sys/index.do");
		// sbtns.add(b);

		// cb.setSub_button(sbtns);
		firstbtns.add(b);

		// b = new CommonUrlButton();
		// b.setType("view");
		// b.setName("活动领奖");
		// b.setUrl(domain+"/wx/aoff/list.htm");
		// firstbtns.add(b);
		// b = new CommonUrlButton();
		// b.setType("view");
		// b.setName("我的奖品");
		// b.setUrl(domain+"/wx/aoff/mylist.htm");
		// firstbtns.add(b);

		// b = new CommonUrlButton();
		// b.setType("view");
		// b.setName("进入商城");
		// b.setUrl(domain+"/wx/index.htm");
		// firstbtns.add(b);
		//
		// sbtns = new ArrayList();
		// b = new CommonUrlButton();
		// b.setType("view");
		// b.setName("活动领奖");
		// b.setUrl(domain+"/wx/aoff/list.htm");
		// sbtns.add(b);
		// b = new CommonUrlButton();
		// b.setType("view");
		// b.setName("我的奖品");
		// b.setUrl(domain+"/wx/aoff/mylist.htm");
		// sbtns.add(b);
		//
		// cb = new ComplexButton();
		// cb.setName("活动领奖");
		// cb.setSub_button(sbtns);
		// firstbtns.add(cb);

		Menu menu = new Menu();
		menu.setButton(firstbtns);
		if (WeixinUtil.createMenu(menu, appId, appSecret) != 0) {
			return "failure";
		}
		return WeixinUtil.viewMenu(appId, appSecret);
	}

	public static String upwxmenu(String appId, String appSecret) {
		String domain = "http://hgmtmall.6655.la/mall";
		domain = "http://wp86679861.imwork.net/mall";
		domain = "http://test.hgmtou.com/mall";
		// domain="http://www.hgmtmall.com/mall";
		CommonUrlButton b = null;
		ComplexButton cb = null;
		List firstbtns = new ArrayList();
		List sbtns = null;
		sbtns = new ArrayList();
		b = new CommonUrlButton();
		b.setType("view");
		b.setName("首页");
		b.setUrl(domain + "/wx/index.htm");
		sbtns.add(b);
		b = new CommonUrlButton();
		b.setType("view");
		b.setName("用户中心");
		b.setUrl(domain + "/wx/my_center.htm");
		sbtns.add(b);
		b = new CommonUrlButton();
		b.setType("view");
		b.setName("重要提醒");
		b.setUrl(domain + "/wx/tixing_services.htm");
		sbtns.add(b);
		// b = new CommonUrlButton();
		// b.setType("view");
		// b.setName("纪念");
		// b.setUrl("http://www.baidu.com/");
		// sbtns.add(b);
		// b = new CommonUrlButton();
		// b.setType("view");
		// b.setName("q客服2");
		// b.setUrl("http://wpa.qq.com/msgrd?v=3&uin=286081157&site=qq&menu=yes");
		// sbtns.add(b);
		// b = new CommonUrlButton();
		// b.setType("view");
		// b.setName("q客服1");
		// b.setUrl("http://wpa.qq.com/msgrd?v=3&uin=286081157&site=qq&menu=yes&from=message&isappinstalled=0");
		// sbtns.add(b);
		cb = new ComplexButton();
		cb.setName("微商城");
		cb.setSub_button(sbtns);
		firstbtns.add(cb);

		sbtns = new ArrayList();
		// b = new CommonUrlButton();
		// b.setType("view");
		// b.setName("私人订制");
		// b.setUrl(domain+"/m/psetmeal_list.htm");
		// sbtns.add(b);
		b = new CommonUrlButton();
		b.setType("view");
		b.setName("周期配送");
		b.setUrl(domain + "/m/csetmeal_list.htm?type=4");
		sbtns.add(b);
		// b = new CommonUrlButton();
		// b.setType("view");
		// b.setName("企业服务");
		// b.setUrl(domain+"/wx/index.htm");
		// sbtns.add(b);
		b = new CommonUrlButton();
		b.setType("view");
		b.setName("礼品码兑换");
		b.setUrl(domain + "/wx/lpq_lq_code.htm");
		sbtns.add(b);
		cb = new ComplexButton();
		cb.setName("特色套餐");
		cb.setSub_button(sbtns);
		firstbtns.add(cb);

		sbtns = new ArrayList();
		b = new CommonUrlButton();
		b.setType("view");
		b.setName("活动领奖");
		b.setUrl(domain + "/wx/aoff/list.htm");
		sbtns.add(b);
		b = new CommonUrlButton();
		b.setType("view");
		b.setName("我的奖品");
		b.setUrl(domain + "/wx/aoff/mylist.htm");
		sbtns.add(b);

		cb = new ComplexButton();
		cb.setName("线下活动");
		cb.setSub_button(sbtns);
		firstbtns.add(cb);

		Menu menu = new Menu();
		menu.setButton(firstbtns);
		if (WeixinUtil.createMenu(menu, appId, appSecret) != 0) {
			return "failure";
		}
		return WeixinUtil.viewMenu(appId, appSecret);
	}

	// 获取视频
	public final static String getMedia_url = "https://api.weixin.qq.com/cgi-bin/media/get?access_token=ACCESS_TOKEN&media_id=MEDIA_ID";

	/*
	 * public static void downloadMedia(String mediaid, String savepath) {
	 * AccessToken at = WeixinUtil.getAccessToken(appId, appSecret); String
	 * requestUrl = getMedia_url.replace("ACCESS_TOKEN",
	 * at.getToken()).replace("MEDIA_ID", mediaid);
	 * System.out.println(requestUrl);
	 * 
	 * HttpClientUtil.download(requestUrl, savepath, new HashMap()); }
	 */

	public final static String send_temp_msg_url = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=ACCESS_TOKEN";

	public static String sendTempMsg(String openid, String appId, String appSecret) {
		// Map data = new LinkedMap();
		// data.put("first", new TemplateWXMsgKey("您好，您已成功支付。", "#173177"));
		// data.put("keyword1", new TemplateWXMsgKey("王府井百货大楼", "#173177"));
		// data.put("keyword2", new TemplateWXMsgKey("20150822324432",
		// "#173177"));
		// data.put("keyword3", new TemplateWXMsgKey("90元", "#173177"));
		// data.put("keyword4", new TemplateWXMsgKey("2015-8-22 11:00",
		// "#173177"));
		// data.put("remark", new TemplateWXMsgKey("感谢您的光临！点击详情查看订单明细。",
		// "#173177"));
		//
		Map twm = new LinkedMap();
		twm.put("touser", openid);
		twm.put("template_id", "Ias6HawLv41a_HQDM_D7ZbESi4xEDxUh1ER6uycqfEU");
		twm.put("url", "http://www.baidu.com");
		twm.put("topcolor", "#FF0000");
		twm.put("data", null);

		String jsontempmsg = JSONObject.fromObject(twm).toString();
		return sendTempMsg(openid, jsontempmsg, appId, appSecret);
	}

	public static String sendTempMsg(String openid, String content, String appId, String appSecret) {
		AccessToken at = WeixinUtil.getAccessToken(appId, appSecret);
		String requestUrl = send_temp_msg_url.replace("ACCESS_TOKEN", at.getToken());
		System.out.println(content);
		String s = httpRequestStr(requestUrl, "POST", content);
		int result = 0;
		JSONObject jsonObject = JSONObject.fromObject(s);
		if (null != jsonObject) {
			if (0 != jsonObject.getInt("errcode")) {
				result = jsonObject.getInt("errcode");
				log.error("发送信息失败 errcode:{} errmsg:{}", jsonObject.getInt("errcode"), jsonObject.getString("errmsg"));
			}
		}
		return s;
	}

	public static void main(String[] args) {
		// 发送微信模板短信
		// System.out.println(sendTempMsg("oD1ePuDWOPXNmg5_6Iah_Y_gqOuY"));

		// 发送短信
		// String content = "滴滴巴士获取“水奥雪世界”门票的小伙伴注意了！门票领取办法:1.关注
		// “花港码头商城”公众号，2点击#活动领奖#按键，输入手机号。特别提醒：使用时请在雪世界售票处，在检票员验证下点击#立即兑换#按键，兑换纸质票进入。有效期至2016年2月20日。滴滴巴士";
		// CommUtil.sendSMS("18611682516", content);
		// CommUtil.sendSMS("18500610761", content);

		// 更新微信菜单
		// System.out.println(upwxmenupub());

		// System.out.println(getOrgs("wx34c5f34afae4c27b","IrA3D1Iesb4TaUGLdt6UlPEjbweMrFCaAMMkSfadTT8bvlbe3lusUFCFIlSlADEk"));
		// System.out.println(getUserByOrgId("wx34c5f34afae4c27b",
		// "IrA3D1Iesb4TaUGLdt6UlPEjbweMrFCaAMMkSfadTT8bvlbe3lusUFCFIlSlADEk",
		// "1"));
		// System.out.println(getUserDetailByOrgId("wx34c5f34afae4c27b",
		// "IrA3D1Iesb4TaUGLdt6UlPEjbweMrFCaAMMkSfadTT8bvlbe3lusUFCFIlSlADEk",
		// "2", "0", "1"));

		// 删除微信菜单
		// System.out.println(deleteMenu(ApplicationPath.getParameter("wx_appid"),
		// ApplicationPath.getParameter("wx_appsecret")));

		// 获取公众号token
		// AccessToken at =
		// WeixinUtil.getAccessToken(ApplicationPath.getParameter("wx_appid"),
		// ApplicationPath.getParameter("wx_appsecret"));
		// System.out.println(at.getToken());

		// 字符串校验
		// System.out.println("字符串验证码：" + getValidateCode("252302194234")); //
		// 字符串校验码：3

		// String
		// mediaid="d4rsSWuGP-NBG_ZmLYNnJveda_vIO_WS5P5VFuvOZui1TEAXpZV_fzklcHScLafm";
		// String
		// mediaidpic="vQE0C_0-IjyKlUXXmgZ1ZcvaAjiEzZl-9mJd04dKtgMwGgFm3PFWlKQcbHlIXMi0";
		// getMedia(ApplicationPath.getParameter("wx_appid"),
		// ApplicationPath.getParameter("wx_appsecret"),mediaidpic,"jpg");
		// getMedia(ApplicationPath.getParameter("wx_appid"),
		// ApplicationPath.getParameter("wx_appsecret"),mediaid,"mp4");
		// Map<String, Object> map = new HashMap<String, Object>();
		// map.put("touser", "oBhgswtb14vrsHgvU4nA07yCMEyg");
		// map.put("msgtype", "text");
		// map.put("text", "{\"content\":\"Hello World\"}");
		// String content =
		// "{\"touser\":\"oBhgswtb14vrsHgvU4nA07yCMEyg\",\"msgtype\":\"text\",\"text\":{\"content\":\"张三给您发送了一条语音消息[2017年5月16日]\"}}";
		// System.out.println(sendKfMsgFwhTest(content));
	}

	public static char getValidateCode(String id17) {
		int[] weight = { 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2 }; // 数字本体码权重
		char[] validate = { '1', '0', '3', '9', '8', '7', '6', '5', '4', '3', '2' }; // mod11,对应校验码字符值
		int sum = 0;
		int mode = 0;
		for (int i = 0; i < id17.length(); i++) {
			sum = sum + Integer.parseInt(String.valueOf(id17.charAt(i))) * weight[i];
		}
		mode = sum % 11;
		return validate[mode];
	}

	/**
	 * 发送企业号消息通用方法
	 * 
	 * @Title: sendMsg
	 * @Description: TODO
	 * @param corpid
	 * @param secret
	 * @param content
	 * @return
	 * @return: boolean
	 * @author: zengmin
	 * @date: 2017年4月17日 下午3:21:30
	 */
	public static boolean sendMsg(String corpid, String secret, String content) {
		System.out.println("qyh_content:" + content);
		AccessToken at = WeixinUtil.getAccessTokenQyh(corpid, secret);
		String requestUrl = "https://qyapi.weixin.qq.com/cgi-bin/message/send?access_token=" + at.getToken();
		String rs = httpRequestStr(requestUrl, "POST", content);
		JSONObject jsonObject = JSONObject.fromObject(rs);
		if (null != jsonObject) {
			if (0 != jsonObject.getInt("errcode")) {
				System.out.println("发送信息失败 errcode:{" + jsonObject.getInt("errcode") + "} errmsg:{"
						+ jsonObject.getString("errmsg") + "}");
				return false;
			}
		}
		return true;
	}

	/**
	 * 发送服务号消息通用方法
	 * 
	 * @Title: sendMsgFwh
	 * @Description: TODO
	 * @param corpid
	 * @param secret
	 * @param content
	 * @return
	 * @return: boolean
	 * @author: zengmin
	 * @date: 2017年4月17日 下午3:21:30
	 */
	public static boolean sendMsgFwh(String appid, String secret, String content) {
		AccessToken at = WeixinUtil.getAccessToken(appid, secret);
		String requestUrl = send_temp_msg_url.replace("ACCESS_TOKEN", at.getToken());
		System.out.println(content);
		String s = httpRequestStr(requestUrl, "POST", content);
		JSONObject jsonObject = JSONObject.fromObject(s);
		if (null != jsonObject) {
			if (0 != jsonObject.getInt("errcode")) {
				System.out.println("发送信息失败 errcode:{" + jsonObject.getInt("errcode") + "} errmsg:{"
						+ jsonObject.getString("errmsg") + "}");
				return false;
			}
		}
		return true;
	}

	public static final String FWH_MSG_URL = "https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=ACCESS_TOKEN";

	/**
	 * 发送服务号客服消息
	 * 
	 * @Title: sendKfMsgFwh
	 * @Description: TODO
	 * @param corpid
	 * @param secret
	 * @param content
	 * @return
	 * @return: boolean
	 * @author: zengmin
	 * @date: 2017年4月17日 下午3:21:30
	 */
	public static boolean sendKfMsgFwh(String appid, String secret, String content) {
		AccessToken at = WeixinUtil.getAccessToken(appid, secret);
		String requestUrl = FWH_MSG_URL.replace("ACCESS_TOKEN", at.getToken());
		System.out.println("fwh_content:" + content);
		String s = httpRequestStr(requestUrl, "POST", content);
		JSONObject jsonObject = JSONObject.fromObject(s);
		if (null != jsonObject) {
			if (0 != jsonObject.getInt("errcode")) {
				System.out.println("发送信息失败 errcode:{" + jsonObject.getInt("errcode") + "} errmsg:{"
						+ jsonObject.getString("errmsg") + "}");
				return false;
			}
		}
		return true;
	}

	/**
	 * 发送服务号客服消息--测试使用
	 * 
	 * @Title: sendKfMsgFwhTest
	 * @Description: TODO
	 * @param corpid
	 * @param secret
	 * @param content
	 * @return
	 * @return: boolean
	 * @author: zengmin
	 * @date: 2017年4月17日 下午3:21:30
	 */
	public static boolean sendKfMsgFwhTest(String content, String appId, String appSecret) {
		AccessToken at = WeixinUtil.getAccessTokenTest(appId, appSecret);
		String requestUrl = FWH_MSG_URL.replace("ACCESS_TOKEN", at.getToken());
		System.out.println(content);
		String s = httpRequestStr(requestUrl, "POST", content);
		JSONObject jsonObject = JSONObject.fromObject(s);
		if (null != jsonObject) {
			if (0 != jsonObject.getInt("errcode")) {
				System.out.println("发送信息失败 errcode:{" + jsonObject.getInt("errcode") + "} errmsg:{"
						+ jsonObject.getString("errmsg") + "}");
				return false;
			}
		}
		return true;
	}

}