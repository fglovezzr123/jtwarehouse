package com.wing.socialcontact.util.im;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.cache.Cache.ValueWrapper;

import com.wing.socialcontact.util.ApplicationPath;
import com.wing.socialcontact.util.JsonUtil;
import com.wing.socialcontact.util.RedisCache;
import com.wing.socialcontact.util.SpringContextUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 网易云工具类
 * 
 * @ClassName: IMUtil
 * @Description: TODO
 * @author: zhangfan
 * @date:2017年9月4日
 */
@SuppressWarnings("deprecation")
public class IMUtil {
	private static final String url = "https://api.netease.im/nimserver/user/create.action";

	private static final String appKey = ApplicationPath.getParameter("NETEASE_IM_APPKEY");
	private static final String appSecret = ApplicationPath.getParameter("NETEASE_IM_APPSECRET");
	private static final String imPrefix = ApplicationPath.getParameter("NETEASE_IM_PREFIX");
	private static final String updateurl = "https://api.netease.im/nimserver/user/update.action";
	private static final String updateUinfourl = "https://api.netease.im/nimserver/user/updateUinfo.action";
	private static final String addfriendurl = "https://api.netease.im/nimserver/friend/add.action";
	private static final String delFriendUrl = "https://api.netease.im/nimserver/friend/delete.action";
	private static final String updateFriendUrl = "https://api.netease.im/nimserver/friend/update.action";
	private static final String teamurl = "https://api.netease.im/nimserver/team/create.action";
	private static final String updateteamurl = "https://api.netease.im/nimserver/team/update.action";
	private static final String addteamurl = "https://api.netease.im/nimserver/team/add.action";
	private static final String kickteamurl = "https://api.netease.im/nimserver/team/kick.action";
	private static final String leaveteamurl = "https://api.netease.im/nimserver/team/leave.action";
	private static final String removeteamurl = "https://api.netease.im/nimserver/team/remove.action";
	private static final String muteTeamUrl = "https://api.netease.im/nimserver/team/muteTeam.action";
	private static final String refreshTokenUrl = "https://api.netease.im/nimserver/user/refreshToken.action";
	private static final String getUinfos = "https://api.netease.im/nimserver/user/getUinfos.action";
	private static final String sendAttachMsg = "https://api.netease.im/nimserver/msg/sendAttachMsg.action";
	private static final String sendBatchAttachMsg = "https://api.netease.im/nimserver/msg/sendBatchAttachMsg.action";

	/**
	 * 创建网易IM用户
	 */
	public static String sendUser(String accid, String token, String name, String icon) {
		DefaultHttpClient httpClient = new DefaultHttpClient();
		String responseContent = "";
		try {
			HttpPost httpPost = new HttpPost(url);
			String nonce = "2345678";
			String curTime = String.valueOf((new Date()).getTime() / 1000L);
			String checkSum = CheckSumBuilder.getCheckSum(appSecret, nonce, curTime);// 参考

			// 设置请求的header
			httpPost.addHeader("AppKey", appKey);
			httpPost.addHeader("Nonce", nonce);
			httpPost.addHeader("CurTime", curTime);
			httpPost.addHeader("CheckSum", checkSum);
			httpPost.addHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");

			// 设置请求的参数
			List<NameValuePair> nvps = new ArrayList<NameValuePair>();
			nvps.add(new BasicNameValuePair("accid", accid));
			if (StringUtils.isNotBlank(token)) {
				nvps.add(new BasicNameValuePair("token", token));
			}
			if (StringUtils.isNotBlank(name)) {
				nvps.add(new BasicNameValuePair("name", name));
			}
			if (StringUtils.isNotBlank(icon)) {
				nvps.add(new BasicNameValuePair("icon", icon));
			}
			httpPost.setEntity(new UrlEncodedFormEntity(nvps, "utf-8"));
			// 执行请求
			HttpResponse response = httpClient.execute(httpPost);
			HttpEntity resEntity = response.getEntity();
			if (resEntity != null) {
				responseContent = EntityUtils.toString(resEntity, "UTF-8");
				EntityUtils.consume(resEntity);
			}
			httpClient.getConnectionManager().shutdown();
		} catch (Exception e) {
			responseContent = "{\"desc\":\" " + e.getMessage() + " \",\"code\":\"500\"}";
		}
		return responseContent;
	}

	/**
	 * 生成用户
	 */
	public static String genUser(List list) {
		DefaultHttpClient httpClient = new DefaultHttpClient();
		String responseContent = "";
		try {
			HttpPost httpPost = new HttpPost(url);
			String nonce = "2345678";
			String curTime = String.valueOf((new Date()).getTime() / 1000L);
			String checkSum = CheckSumBuilder.getCheckSum(appSecret, nonce, curTime);// 参考
																						// 计算CheckSum的java代码

			// 设置请求的header
			httpPost.addHeader("AppKey", appKey);
			httpPost.addHeader("Nonce", nonce);
			httpPost.addHeader("CurTime", curTime);
			httpPost.addHeader("CheckSum", checkSum);
			httpPost.addHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
			for (int i = 0; i < list.size(); i++) {
				Map data = new HashMap();
				data = (Map) list.get(i);
				// 设置请求的参数
				List<NameValuePair> nvps = new ArrayList<NameValuePair>();
				nvps.add(new BasicNameValuePair("accid", (String) data.get("account")));
				nvps.add(new BasicNameValuePair("token", (String) data.get("token")));
				httpPost.setEntity(new UrlEncodedFormEntity(nvps, "utf-8"));
				// 执行请求
				HttpResponse response = httpClient.execute(httpPost);
				HttpEntity resEntity = response.getEntity();
				if (resEntity != null) {
					responseContent = EntityUtils.toString(resEntity, "UTF-8");
					EntityUtils.consume(resEntity);
				}
				System.out.println(data.get("account") + "  &" + responseContent);
			}
			httpClient.getConnectionManager().shutdown();
		} catch (Exception e) {
			responseContent = "{\"desc\":\" " + e.getMessage() + " \",\"code\":\"500\"}";
		}
		return responseContent;
	}

	/**
	 * 更新用户
	 */
	public static String updateUser(List list) {
		DefaultHttpClient httpClient = new DefaultHttpClient();
		String responseContent = "";
		try {
			HttpPost httpPost = new HttpPost(updateUinfourl);
			String nonce = "2345678";
			String curTime = String.valueOf((new Date()).getTime() / 1000L);
			String checkSum = CheckSumBuilder.getCheckSum(appSecret, nonce, curTime);// 参考
																						// 计算CheckSum的java代码

			// 设置请求的header
			httpPost.addHeader("AppKey", appKey);
			httpPost.addHeader("Nonce", nonce);
			httpPost.addHeader("CurTime", curTime);
			httpPost.addHeader("CheckSum", checkSum);
			httpPost.addHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
			for (int i = 0; i < list.size(); i++) {
				Map data = new HashMap();
				data = (Map) list.get(i);
				// 设置请求的参数
				List<NameValuePair> nvps = new ArrayList<NameValuePair>();
				nvps.add(new BasicNameValuePair("accid", (String) data.get("account")));
				nvps.add(new BasicNameValuePair("name", (String) data.get("nickname")));
				nvps.add(new BasicNameValuePair("icon", (String) data.get("headPortrait")));
				nvps.add(new BasicNameValuePair("avatar", (String) data.get("headPortrait")));
				httpPost.setEntity(new UrlEncodedFormEntity(nvps, "utf-8"));
				// 执行请求
				HttpResponse response = httpClient.execute(httpPost);
				HttpEntity resEntity = response.getEntity();
				if (resEntity != null) {
					responseContent = EntityUtils.toString(resEntity, "UTF-8");
					EntityUtils.consume(resEntity);
				}
			}
			httpClient.getConnectionManager().shutdown();
		} catch (Exception e) {
			responseContent = "{\"desc\":\" " + e.getMessage() + " \",\"code\":\"500\"}";
		}
		return responseContent;
	}

	/**
	 * 更新用户
	 */
	public static String updateUserOne(String userid, String nickname, String headPortrait) {
		DefaultHttpClient httpClient = new DefaultHttpClient();
		String responseContent = "";
		try {
			HttpPost httpPost = new HttpPost(updateUinfourl);
			String nonce = "2345678";
			String curTime = String.valueOf((new Date()).getTime() / 1000L);
			String checkSum = CheckSumBuilder.getCheckSum(appSecret, nonce, curTime);// 参考计算CheckSum的java代码

			// 设置请求的header
			httpPost.addHeader("AppKey", appKey);
			httpPost.addHeader("Nonce", nonce);
			httpPost.addHeader("CurTime", curTime);
			httpPost.addHeader("CheckSum", checkSum);
			httpPost.addHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
			// 设置请求的参数
			List<NameValuePair> nvps = new ArrayList<NameValuePair>();
			nvps.add(new BasicNameValuePair("accid", userid));
			nvps.add(new BasicNameValuePair("name", nickname));
			nvps.add(new BasicNameValuePair("icon", headPortrait));
			nvps.add(new BasicNameValuePair("avatar", headPortrait));
			httpPost.setEntity(new UrlEncodedFormEntity(nvps, "utf-8"));
			// 执行请求
			HttpResponse response = httpClient.execute(httpPost);
			HttpEntity resEntity = response.getEntity();
			if (resEntity != null) {
				responseContent = EntityUtils.toString(resEntity, "UTF-8");
				EntityUtils.consume(resEntity);
			}
			httpClient.getConnectionManager().shutdown();
		} catch (Exception e) {
			responseContent = "{\"desc\":\" " + e.getMessage() + " \",\"code\":\"500\"}";
		}
		return responseContent;
	}

	/**
	 * 加为好友 批量
	 */
	public static String addFriend(List list) {
		DefaultHttpClient httpClient = new DefaultHttpClient();
		String responseContent = "";
		try {
			HttpPost httpPost = new HttpPost(addfriendurl);
			String nonce = "2345678";
			String curTime = String.valueOf((new Date()).getTime() / 1000L);
			String checkSum = CheckSumBuilder.getCheckSum(appSecret, nonce, curTime);// 参考
																						// 计算CheckSum的java代码

			// 设置请求的header
			httpPost.addHeader("AppKey", appKey);
			httpPost.addHeader("Nonce", nonce);
			httpPost.addHeader("CurTime", curTime);
			httpPost.addHeader("CheckSum", checkSum);
			httpPost.addHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
			for (int i = 0; i < list.size(); i++) {
				Map data = new HashMap();
				data = (Map) list.get(i);
				// 设置请求的参数
				List<NameValuePair> nvps = new ArrayList<NameValuePair>();
				nvps.add(new BasicNameValuePair("accid", (String) data.get("accid")));
				nvps.add(new BasicNameValuePair("faccid", (String) data.get("faccid")));
				nvps.add(new BasicNameValuePair("type", "1"));
				httpPost.setEntity(new UrlEncodedFormEntity(nvps, "utf-8"));
				// 执行请求
				HttpResponse response = httpClient.execute(httpPost);
				HttpEntity resEntity = response.getEntity();
				if (resEntity != null) {
					responseContent = EntityUtils.toString(resEntity, "UTF-8");
					EntityUtils.consume(resEntity);
				}
			}
			httpClient.getConnectionManager().shutdown();
		} catch (Exception e) {
			responseContent = "{\"desc\":\" " + e.getMessage() + " \",\"code\":\"500\"}";
		}
		return responseContent;
	}

	/**
	 * 创建群
	 */
	public static List createTeam(List list) {
		DefaultHttpClient httpClient = new DefaultHttpClient();
		String responseContent = "";
		try {
			HttpPost httpPost = new HttpPost(teamurl);
			String nonce = "2345678";
			String curTime = String.valueOf((new Date()).getTime() / 1000L);
			String checkSum = CheckSumBuilder.getCheckSum(appSecret, nonce, curTime);// 参考计算CheckSum的java代码

			// 设置请求的header
			httpPost.addHeader("AppKey", appKey);
			httpPost.addHeader("Nonce", nonce);
			httpPost.addHeader("CurTime", curTime);
			httpPost.addHeader("CheckSum", checkSum);
			httpPost.addHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
			for (int i = 0; i < list.size(); i++) {
				Map data = new HashMap();
				data = (Map) list.get(i);
				// 设置请求的参数
				List<NameValuePair> nvps = new ArrayList<NameValuePair>();
				nvps.add(new BasicNameValuePair("tname", (String) data.get("tname")));
				nvps.add(new BasicNameValuePair("owner", (String) data.get("owner")));
				nvps.add(new BasicNameValuePair("members", data.get("members").toString()));
				nvps.add(new BasicNameValuePair("msg", "welcome"));
				nvps.add(new BasicNameValuePair("magree", "0"));
				nvps.add(new BasicNameValuePair("joinmode", "0"));
				httpPost.setEntity(new UrlEncodedFormEntity(nvps, "utf-8"));
				// 执行请求
				HttpResponse response = httpClient.execute(httpPost);
				HttpEntity resEntity = response.getEntity();
				if (resEntity != null) {
					responseContent = EntityUtils.toString(resEntity, "UTF-8");
					EntityUtils.consume(resEntity);
					Map jg = JsonUtil.parseJSON2Map(responseContent);
					data.put("tid", jg.get("tid"));
				}
			}
			httpClient.getConnectionManager().shutdown();
		} catch (Exception e) {
			responseContent = "{\"desc\":\" " + e.getMessage() + " \",\"code\":\"500\"}";
		}
		return list;
	}

	/**
	 * 加为好友
	 * 
	 * @param accid
	 *            加好友发起者accid
	 * @param faccid
	 *            加好友接收者accid
	 * @return
	 */
	public static String addFriendOne(String accid, String faccid, String type, String msg) {
		DefaultHttpClient httpClient = new DefaultHttpClient();
		String responseContent = "";
		try {
			HttpPost httpPost = new HttpPost(addfriendurl);
			String nonce = "2345678";
			String curTime = String.valueOf((new Date()).getTime() / 1000L);
			String checkSum = CheckSumBuilder.getCheckSum(appSecret, nonce, curTime);// 参考
																						// 计算CheckSum的java代码

			// 设置请求的header
			httpPost.addHeader("AppKey", appKey);
			httpPost.addHeader("Nonce", nonce);
			httpPost.addHeader("CurTime", curTime);
			httpPost.addHeader("CheckSum", checkSum);
			httpPost.addHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
			// 设置请求的参数
			List<NameValuePair> nvps = new ArrayList<NameValuePair>();
			nvps.add(new BasicNameValuePair("accid", accid));
			nvps.add(new BasicNameValuePair("faccid", faccid));
			nvps.add(new BasicNameValuePair("type", type == null ? "1" : type));
			nvps.add(new BasicNameValuePair("msg", msg == null ? "" : msg));
			httpPost.setEntity(new UrlEncodedFormEntity(nvps, "utf-8"));
			// 执行请求
			HttpResponse response = httpClient.execute(httpPost);
			HttpEntity resEntity = response.getEntity();
			if (resEntity != null) {
				responseContent = EntityUtils.toString(resEntity, "UTF-8");
				EntityUtils.consume(resEntity);
			}
			httpClient.getConnectionManager().shutdown();

		} catch (Exception e) {
			responseContent = "{\"desc\":\" " + e.getMessage() + " \",\"code\":\"500\"}";
		}
		return responseContent;
	}

	/**
	 * 删除好友
	 * 
	 * @param accid
	 * @param faccid
	 * @return
	 */
	public static String delFriend(String accid, String faccid) {
		DefaultHttpClient httpClient = new DefaultHttpClient();
		String responseContent = "";
		try {
			HttpPost httpPost = new HttpPost(delFriendUrl);
			String nonce = "2345678";
			String curTime = String.valueOf((new Date()).getTime() / 1000L);
			String checkSum = CheckSumBuilder.getCheckSum(appSecret, nonce, curTime);// 参考
																						// 计算CheckSum的java代码
			// 设置请求的header
			httpPost.addHeader("AppKey", appKey);
			httpPost.addHeader("Nonce", nonce);
			httpPost.addHeader("CurTime", curTime);
			httpPost.addHeader("CheckSum", checkSum);
			httpPost.addHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
			// 设置请求的参数
			List<NameValuePair> nvps = new ArrayList<NameValuePair>();
			nvps.add(new BasicNameValuePair("accid", accid));
			nvps.add(new BasicNameValuePair("faccid", faccid));
			httpPost.setEntity(new UrlEncodedFormEntity(nvps, "utf-8"));
			// 执行请求
			HttpResponse response = httpClient.execute(httpPost);
			HttpEntity resEntity = response.getEntity();
			if (resEntity != null) {
				responseContent = EntityUtils.toString(resEntity, "UTF-8");
				EntityUtils.consume(resEntity);
			}
			httpClient.getConnectionManager().shutdown();
		} catch (Exception e) {
			responseContent = "{\"desc\":\" " + e.getMessage() + " \",\"code\":\"500\"}";
		}
		return responseContent;
	}

	/**
	 * 更新好友相关信息，如加备注名，必须是好友才可以
	 * 
	 * @param accid
	 * @param faccid
	 * @param alias
	 * @param ex
	 * @return
	 */
	public static String updateFriend(String accid, String faccid, String alias, String ex) {
		DefaultHttpClient httpClient = new DefaultHttpClient();
		String responseContent = "";
		try {
			HttpPost httpPost = new HttpPost(updateFriendUrl);
			String nonce = "2345678";
			String curTime = String.valueOf((new Date()).getTime() / 1000L);
			String checkSum = CheckSumBuilder.getCheckSum(appSecret, nonce, curTime);// 参考
																						// 计算CheckSum的java代码
			// 设置请求的header
			httpPost.addHeader("AppKey", appKey);
			httpPost.addHeader("Nonce", nonce);
			httpPost.addHeader("CurTime", curTime);
			httpPost.addHeader("CheckSum", checkSum);
			httpPost.addHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
			// 设置请求的参数
			List<NameValuePair> nvps = new ArrayList<NameValuePair>();
			nvps.add(new BasicNameValuePair("accid", accid));
			nvps.add(new BasicNameValuePair("faccid", faccid));
			nvps.add(new BasicNameValuePair("alias", alias));
			nvps.add(new BasicNameValuePair("ex", ex));
			httpPost.setEntity(new UrlEncodedFormEntity(nvps, "utf-8"));
			// 执行请求
			HttpResponse response = httpClient.execute(httpPost);
			HttpEntity resEntity = response.getEntity();
			if (resEntity != null) {
				responseContent = EntityUtils.toString(resEntity, "UTF-8");
				EntityUtils.consume(resEntity);
			}
			httpClient.getConnectionManager().shutdown();
		} catch (Exception e) {
			responseContent = "{\"desc\":\" " + e.getMessage() + " \",\"code\":\"500\"}";
		}
		return responseContent;
	}

	/**
	 * 创建群组 单个
	 * 
	 * @param tname
	 *            群名称
	 * @param owner
	 *            群主用户帐号
	 * @param members
	 *            群成员
	 * @return
	 */
	public static String createTeamOne(String tname, String owner, JSONArray members) {
		DefaultHttpClient httpClient = new DefaultHttpClient();
		String responseContent = "";
		String tid = "";
		try {
			HttpPost httpPost = new HttpPost(teamurl);
			String nonce = "2345678";
			String curTime = String.valueOf((new Date()).getTime() / 1000L);
			String checkSum = CheckSumBuilder.getCheckSum(appSecret, nonce, curTime);// 参考
																						// 计算CheckSum的java代码

			// 设置请求的header
			httpPost.addHeader("AppKey", appKey);
			httpPost.addHeader("Nonce", nonce);
			httpPost.addHeader("CurTime", curTime);
			httpPost.addHeader("CheckSum", checkSum);
			httpPost.addHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");

			// 设置请求的参数
			List<NameValuePair> nvps = new ArrayList<NameValuePair>();
			nvps.add(new BasicNameValuePair("tname", tname));
			nvps.add(new BasicNameValuePair("owner", owner));
			nvps.add(new BasicNameValuePair("members", members.toString()));
			nvps.add(new BasicNameValuePair("msg", "welcome"));
			nvps.add(new BasicNameValuePair("magree", "0"));
			nvps.add(new BasicNameValuePair("joinmode", "1"));
			httpPost.setEntity(new UrlEncodedFormEntity(nvps, "utf-8"));
			// 执行请求
			HttpResponse response = httpClient.execute(httpPost);
			HttpEntity resEntity = response.getEntity();
			if (resEntity != null) {
				responseContent = EntityUtils.toString(resEntity, "UTF-8");
				EntityUtils.consume(resEntity);
				Map jg = JsonUtil.parseJSON2Map(responseContent);
				tid = (String) jg.get("tid");
			}
			httpClient.getConnectionManager().shutdown();
		} catch (Exception e) {
			responseContent = "{\"desc\":\" " + e.getMessage() + " \",\"code\":\"500\"}";
		}
		return tid;
	}

	/**
	 * 编辑群资料
	 * 
	 * @param tid
	 * @param tname
	 *            群名
	 * @param owner
	 *            群主
	 * @param icon
	 *            群头像
	 * @param intro
	 *            群介绍
	 * @return
	 */
	public static String updateTeam(String tid, String tname, String owner, String icon, String intro) {
		DefaultHttpClient httpClient = new DefaultHttpClient();
		String responseContent = "";
		try {
			HttpPost httpPost = new HttpPost(updateteamurl);
			String nonce = "2345678";
			String curTime = String.valueOf((new Date()).getTime() / 1000L);
			String checkSum = CheckSumBuilder.getCheckSum(appSecret, nonce, curTime);// 计算CheckSum

			// 设置请求的header
			httpPost.addHeader("AppKey", appKey);
			httpPost.addHeader("Nonce", nonce);
			httpPost.addHeader("CurTime", curTime);
			httpPost.addHeader("CheckSum", checkSum);
			httpPost.addHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");

			// 设置请求的参数
			List<NameValuePair> nvps = new ArrayList<NameValuePair>();
			nvps.add(new BasicNameValuePair("tid", tid));
			nvps.add(new BasicNameValuePair("owner", owner));
			if (StringUtils.isNotEmpty(tname))
				nvps.add(new BasicNameValuePair("tname", tname));
			if (StringUtils.isNotEmpty(icon))
				nvps.add(new BasicNameValuePair("icon", icon));
			if (StringUtils.isNotEmpty(intro))
				nvps.add(new BasicNameValuePair("intro", intro));

			httpPost.setEntity(new UrlEncodedFormEntity(nvps, "utf-8"));
			// 执行请求
			HttpResponse response = httpClient.execute(httpPost);
			HttpEntity resEntity = response.getEntity();
			if (resEntity != null) {
				responseContent = EntityUtils.toString(resEntity, "UTF-8");
				EntityUtils.consume(resEntity);
			}
			System.out.println(responseContent);
			httpClient.getConnectionManager().shutdown();
		} catch (Exception e) {
			responseContent = "{\"desc\":\" " + e.getMessage() + " \",\"code\":\"500\"}";
		}
		return responseContent;
	}

	/**
	 * 移出群
	 * 
	 * @param tid
	 * @param owner
	 * @param member
	 * @return
	 */
	public static String kickTeam(String tid, String owner, String member) {
		DefaultHttpClient httpClient = new DefaultHttpClient();
		String responseContent = "";
		try {
			HttpPost httpPost = new HttpPost(kickteamurl);
			String nonce = "2345678";
			String curTime = String.valueOf((new Date()).getTime() / 1000L);
			String checkSum = CheckSumBuilder.getCheckSum(appSecret, nonce, curTime);// 参考
																						// 计算CheckSum的java代码

			// 设置请求的header
			httpPost.addHeader("AppKey", appKey);
			httpPost.addHeader("Nonce", nonce);
			httpPost.addHeader("CurTime", curTime);
			httpPost.addHeader("CheckSum", checkSum);
			httpPost.addHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");

			// 设置请求的参数
			List<NameValuePair> nvps = new ArrayList<NameValuePair>();
			nvps.add(new BasicNameValuePair("tid", tid));
			nvps.add(new BasicNameValuePair("owner", owner));
			nvps.add(new BasicNameValuePair("member", member));
			httpPost.setEntity(new UrlEncodedFormEntity(nvps, "utf-8"));
			// 执行请求
			HttpResponse response = httpClient.execute(httpPost);
			HttpEntity resEntity = response.getEntity();
			if (resEntity != null) {
				responseContent = EntityUtils.toString(resEntity, "UTF-8");
				EntityUtils.consume(resEntity);
			}
			System.out.println(responseContent);
			httpClient.getConnectionManager().shutdown();
		} catch (Exception e) {
			responseContent = "{\"desc\":\" " + e.getMessage() + " \",\"code\":\"500\"}";
		}
		return responseContent;
	}

	/**
	 * 拉人进群
	 * 
	 * @param tid
	 * @param owner
	 * @param members
	 * @param magree
	 * @param msg
	 * @return
	 */
	public static String addTeam(String tid, String owner, JSONArray members, String magree, String msg) {
		DefaultHttpClient httpClient = new DefaultHttpClient();
		String responseContent = "";
		try {
			HttpPost httpPost = new HttpPost(addteamurl);
			String nonce = "2345678";
			String curTime = String.valueOf((new Date()).getTime() / 1000L);
			String checkSum = CheckSumBuilder.getCheckSum(appSecret, nonce, curTime);// 参考计算CheckSum的java代码

			// 设置请求的header
			httpPost.addHeader("AppKey", appKey);
			httpPost.addHeader("Nonce", nonce);
			httpPost.addHeader("CurTime", curTime);
			httpPost.addHeader("CheckSum", checkSum);
			httpPost.addHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");

			// 设置请求的参数
			List<NameValuePair> nvps = new ArrayList<NameValuePair>();
			nvps.add(new BasicNameValuePair("tid", tid));
			nvps.add(new BasicNameValuePair("owner", owner));
			nvps.add(new BasicNameValuePair("members", members.toString()));
			nvps.add(new BasicNameValuePair("msg", msg));
			nvps.add(new BasicNameValuePair("magree", magree));
			httpPost.setEntity(new UrlEncodedFormEntity(nvps, "utf-8"));
			// 执行请求
			HttpResponse response = httpClient.execute(httpPost);
			HttpEntity resEntity = response.getEntity();
			if (resEntity != null) {
				responseContent = EntityUtils.toString(resEntity, "UTF-8");
				EntityUtils.consume(resEntity);
			}
			httpClient.getConnectionManager().shutdown();
		} catch (Exception e) {
			responseContent = "{\"desc\":\" " + e.getMessage() + " \",\"code\":\"500\"}";
		}
		return responseContent;
	}

	/**
	 * 退群
	 * 
	 * @param tid
	 * @param accid
	 *            退群的accid
	 * @return
	 */
	public static String leaveTeam(String tid, String accid) {
		DefaultHttpClient httpClient = new DefaultHttpClient();
		String responseContent = "";
		try {
			HttpPost httpPost = new HttpPost(leaveteamurl);
			String nonce = "2345678";
			String curTime = String.valueOf((new Date()).getTime() / 1000L);
			String checkSum = CheckSumBuilder.getCheckSum(appSecret, nonce, curTime);// 参考
																						// 计算CheckSum的java代码

			// 设置请求的header
			httpPost.addHeader("AppKey", appKey);
			httpPost.addHeader("Nonce", nonce);
			httpPost.addHeader("CurTime", curTime);
			httpPost.addHeader("CheckSum", checkSum);
			httpPost.addHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");

			// 设置请求的参数
			List<NameValuePair> nvps = new ArrayList<NameValuePair>();
			nvps.add(new BasicNameValuePair("tid", tid));
			nvps.add(new BasicNameValuePair("accid", accid));
			httpPost.setEntity(new UrlEncodedFormEntity(nvps, "utf-8"));
			// 执行请求
			HttpResponse response = httpClient.execute(httpPost);
			HttpEntity resEntity = response.getEntity();
			if (resEntity != null) {
				responseContent = EntityUtils.toString(resEntity, "UTF-8");
				EntityUtils.consume(resEntity);
			}
			System.out.println(responseContent);
			httpClient.getConnectionManager().shutdown();
		} catch (Exception e) {
			responseContent = "{\"desc\":\" " + e.getMessage() + " \",\"code\":\"500\"}";
		}
		return responseContent;
	}

	/**
	 * 解散群
	 * 
	 * @param tid
	 * @param owner
	 * @return
	 */
	public static String removeTeam(String tid, String owner) {
		DefaultHttpClient httpClient = new DefaultHttpClient();
		String responseContent = "";
		try {
			HttpPost httpPost = new HttpPost(removeteamurl);
			String nonce = "2345678";
			String curTime = String.valueOf((new Date()).getTime() / 1000L);
			String checkSum = CheckSumBuilder.getCheckSum(appSecret, nonce, curTime);// 参考
																						// 计算CheckSum的java代码

			// 设置请求的header
			httpPost.addHeader("AppKey", appKey);
			httpPost.addHeader("Nonce", nonce);
			httpPost.addHeader("CurTime", curTime);
			httpPost.addHeader("CheckSum", checkSum);
			httpPost.addHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");

			// 设置请求的参数
			List<NameValuePair> nvps = new ArrayList<NameValuePair>();
			nvps.add(new BasicNameValuePair("tid", tid));
			nvps.add(new BasicNameValuePair("owner", owner));
			httpPost.setEntity(new UrlEncodedFormEntity(nvps, "utf-8"));
			// 执行请求
			HttpResponse response = httpClient.execute(httpPost);
			HttpEntity resEntity = response.getEntity();
			if (resEntity != null) {
				responseContent = EntityUtils.toString(resEntity, "UTF-8");
				EntityUtils.consume(resEntity);
			}
			System.out.println(responseContent);
			httpClient.getConnectionManager().shutdown();
		} catch (Exception e) {
			responseContent = "{\"desc\":\" " + e.getMessage() + " \",\"code\":\"500\"}";
		}
		return responseContent;
	}

	/**
	 * 查询群
	 * 
	 * @param tids
	 * @param owner
	 * @return
	 */
	public static String queryTeam(JSONArray tids) {
		DefaultHttpClient httpClient = new DefaultHttpClient();
		String responseContent = "";
		try {
			HttpPost httpPost = new HttpPost("https://api.netease.im/nimserver/team/query.action");
			String nonce = "2345678";
			String curTime = String.valueOf((new Date()).getTime() / 1000L);
			String checkSum = CheckSumBuilder.getCheckSum(appSecret, nonce, curTime);// 参考
																						// 计算CheckSum的java代码

			// 设置请求的header
			httpPost.addHeader("AppKey", appKey);
			httpPost.addHeader("Nonce", nonce);
			httpPost.addHeader("CurTime", curTime);
			httpPost.addHeader("CheckSum", checkSum);
			httpPost.addHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");

			// 设置请求的参数
			List<NameValuePair> nvps = new ArrayList<NameValuePair>();
			nvps.add(new BasicNameValuePair("tids", tids.toString()));
			nvps.add(new BasicNameValuePair("ope", "1"));
			httpPost.setEntity(new UrlEncodedFormEntity(nvps, "utf-8"));
			// 执行请求
			HttpResponse response = httpClient.execute(httpPost);
			HttpEntity resEntity = response.getEntity();
			if (resEntity != null) {
				responseContent = EntityUtils.toString(resEntity, "UTF-8");
				EntityUtils.consume(resEntity);
			}
			System.out.println(responseContent);
			httpClient.getConnectionManager().shutdown();
		} catch (Exception e) {
			responseContent = "{\"desc\":\" " + e.getMessage() + " \",\"code\":\"500\"}";
		}
		return responseContent;
	}

	/**
	 * 更新并获取token
	 * 
	 * @param accid
	 * @return
	 */
	public static String refreshToken(String accid) {
		DefaultHttpClient httpClient = new DefaultHttpClient();
		String responseContent = "";
		try {
			HttpPost httpPost = new HttpPost(refreshTokenUrl);
			String nonce = "2345678";
			String curTime = String.valueOf((new Date()).getTime() / 1000L);
			String checkSum = CheckSumBuilder.getCheckSum(appSecret, nonce, curTime);// 参考计算CheckSum的java代码

			// 设置请求的header
			httpPost.addHeader("AppKey", appKey);
			httpPost.addHeader("Nonce", nonce);
			httpPost.addHeader("CurTime", curTime);
			httpPost.addHeader("CheckSum", checkSum);
			httpPost.addHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");

			// 设置请求的参数
			List<NameValuePair> nvps = new ArrayList<NameValuePair>();
			nvps.add(new BasicNameValuePair("accid", accid));
			httpPost.setEntity(new UrlEncodedFormEntity(nvps, "utf-8"));
			// 执行请求
			HttpResponse response = httpClient.execute(httpPost);
			HttpEntity resEntity = response.getEntity();
			if (resEntity != null) {
				responseContent = EntityUtils.toString(resEntity, "UTF-8");
				EntityUtils.consume(resEntity);
			}
			httpClient.getConnectionManager().shutdown();
			com.alibaba.fastjson.JSONObject jsonObject = com.alibaba.fastjson.JSONObject.parseObject(responseContent);
			String resDesc = jsonObject.getString("desc");
			String resCoe = jsonObject.getString("code");
			if ("414".contains(resCoe) && resDesc.contains("not register")) {
				sendUser(accid, UUID.randomUUID().toString() + accid, null, null);
				refreshToken(accid);
			}
		} catch (Exception e) {
			responseContent = "{\"desc\":\" " + e.getMessage() + " \",\"code\":\"500\"}";
		}
		return responseContent;
	}

	/**
	 * 修改群消息提醒开关
	 * 
	 * @param tid
	 * @param accid
	 * @param ope
	 *            1：关闭消息提醒，2：打开消息提醒，其他值无效
	 * @return
	 */
	public static String muteTeam(String tid, String accid, String ope) throws Exception {
		DefaultHttpClient httpClient = new DefaultHttpClient();
		String responseContent = "";
		try {
			HttpPost httpPost = new HttpPost(muteTeamUrl);
			String nonce = "2345678";
			String curTime = String.valueOf((new Date()).getTime() / 1000L);
			String checkSum = CheckSumBuilder.getCheckSum(appSecret, nonce, curTime);// 参考

			// 设置请求的header
			httpPost.addHeader("AppKey", appKey);
			httpPost.addHeader("Nonce", nonce);
			httpPost.addHeader("CurTime", curTime);
			httpPost.addHeader("CheckSum", checkSum);
			httpPost.addHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");

			// 设置请求的参数
			List<NameValuePair> nvps = new ArrayList<NameValuePair>();
			nvps.add(new BasicNameValuePair("tid", tid));
			nvps.add(new BasicNameValuePair("accid", accid));
			nvps.add(new BasicNameValuePair("ope", ope));
			httpPost.setEntity(new UrlEncodedFormEntity(nvps, "utf-8"));
			// 执行请求
			HttpResponse response = httpClient.execute(httpPost);
			HttpEntity resEntity = response.getEntity();
			if (resEntity != null) {
				responseContent = EntityUtils.toString(resEntity, "UTF-8");
				EntityUtils.consume(resEntity);
			}
			httpClient.getConnectionManager().shutdown();
		} catch (Exception e) {
			responseContent = "{\"desc\":\" " + e.getMessage() + " \",\"code\":\"500\"}";
		}
		return responseContent;
	}

	public static String getUserImToken(String userid) {
		RedisCache redisCache = (RedisCache) SpringContextUtil.getBean("redisCache");
		ValueWrapper vw = redisCache.get("IM_TOKEN_" + userid);
		String token = null;
		if (vw != null) {
			token = (String) vw.get();
			return token;
		}
		JSONObject jsonObject = JSONObject.fromObject(refreshToken(userid));
		int code = jsonObject.getInt("code");
		long ex = 24 * 3600000l; // 有效期1天
		// long ex = 120l; // 有效期2分钟 测试使用。
		if (code == 200) {
			// 返回token
			JSONObject info = jsonObject.getJSONObject("info");
			redisCache.put("IM_TOKEN_" + userid, info.getString("token"), ex);
			return info.getString("token");
		}
		// else if(code==414){
		// //未注册的话，注册并更新昵称头像返回token
		// jsonObject = JSONObject.fromObject(sendUser(userid));
		// code = jsonObject.getInt("code");
		// if(code == 200){
		// JSONObject info = jsonObject.getJSONObject("info");
		// token = info.getString("token");
		// updateUserOne(userid, nickname, headPortrait);
		// redisCache.put("IM_TOKEN_" + userid, token, ex);
		// return token;
		// }
		// }
		return "failure";
	}

	/**
	 * 获取名片
	 */
	public static String getUinfos(JSONArray accids) {
		DefaultHttpClient httpClient = new DefaultHttpClient();
		String responseContent = "";
		try {
			HttpPost httpPost = new HttpPost(getUinfos);
			String nonce = "2345678";
			String curTime = String.valueOf((new Date()).getTime() / 1000L);
			String checkSum = CheckSumBuilder.getCheckSum(appSecret, nonce, curTime);// 参考
																						// 计算CheckSum的java代码

			// 设置请求的header
			httpPost.addHeader("AppKey", appKey);
			httpPost.addHeader("Nonce", nonce);
			httpPost.addHeader("CurTime", curTime);
			httpPost.addHeader("CheckSum", checkSum);
			httpPost.addHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");

			// 设置请求的参数
			List<NameValuePair> nvps = new ArrayList<NameValuePair>();
			nvps.add(new BasicNameValuePair("accids", accids.toString()));
			httpPost.setEntity(new UrlEncodedFormEntity(nvps, "utf-8"));
			// 执行请求
			HttpResponse response = httpClient.execute(httpPost);
			HttpEntity resEntity = response.getEntity();
			if (resEntity != null) {
				responseContent = EntityUtils.toString(resEntity, "UTF-8");
				EntityUtils.consume(resEntity);
			}
			System.out.println(responseContent);
			httpClient.getConnectionManager().shutdown();
		} catch (Exception e) {
			responseContent = "{\"desc\":\" " + e.getMessage() + " \",\"code\":\"500\"}";
		}
		return responseContent;
	}

	// 自定义推送消息方法 只支持点对点
	/**
	 * 
	 * @param id   推送id
	 * @param title 标题
	 * @param content  内容
	 * @param type  类型  自定义
	 * @param timestamp   时间戳
	 * @param from  发送人
	 * @param to  接收人
	 * @param contentDetail  扩展字段详细内容
	 * @param status  状态字段  具体含义自定义  
	 * 
	 *   示例
	 * {
	        "id": "1234", // 通知ID
	        "title": "标题",//
	        "content": "内容",
	        "type": "sayhello",//通知类型，前后端统一商定类型种类字段
	        "timestamp": "1243899999",//下发时间戳
	        "extra": {    //扩展字段：根据不同的通知类型，返回不同的扩展内容，以下已打招呼为例   
				"from": "张三",
				"to": "李四",
				"content": "Hello 我是张三，这周四约一下呗",
				"status": "1" //状态：同意、拒绝、申请中
			}
	 	}
	 *
	 * @return
	 */
	public static void pushContentUtil(String dubboAppKey, String dubboAppSecret, String dubboImPrefix, String id,
			String title, String content, String type, String template, String timestamp, String from, String to,
			String contentDetail, String status) {
		Map map = new HashMap();
		Map extra = new HashMap();
		map.put("id", id);
		map.put("title", title);
		map.put("content", content);
		map.put("type", type);
		map.put("template", template);
		map.put("timestamp", timestamp);
		extra.put("from", from);
		extra.put("to", to);
		extra.put("content", contentDetail);
		extra.put("status", status);
		map.put("extra", extra);
		JSONObject jsonObject = JSONObject.fromObject(map);
		System.out.println(jsonObject);
		IMUtil.sendAttachMsgDubbo(dubboAppKey, dubboAppSecret, dubboImPrefix, from, "0", to, jsonObject.toString(),
				content, "", "", "", "");
	}

	/**
	 * 发送自定义系统通知
	 * 
	 * @param from
	 *            发送者accid，用户帐号
	 * @param msgType
	 *            0：点对点自定义通知，1：群消息自定义通知
	 * @param to
	 *            msgtype==0是表示accid即用户id，msgtype==1表示tid即群id
	 * @param attach
	 *            自定义通知内容，第三方组装的字符串，JSON串
	 * @param pushcontent
	 * @param payload
	 * @param sound
	 * @param save
	 *            1表示只发在线，2表示会存离线。默认会存离线
	 * @param option
	 * @return
	 */
	public static String sendAttachMsg(String from, String msgType, String to, String attach, String pushcontent,
			String payload, String sound, String save, String option) {
		DefaultHttpClient httpClient = new DefaultHttpClient();
		String responseContent = "";
		try {
			HttpPost httpPost = new HttpPost(sendAttachMsg);
			String nonce = "2345678";
			String curTime = String.valueOf((new Date()).getTime() / 1000L);
			String checkSum = CheckSumBuilder.getCheckSum(appSecret, nonce, curTime);// 参考计算CheckSum的java代码

			// 设置请求的header
			httpPost.addHeader("AppKey", appKey);
			httpPost.addHeader("Nonce", nonce);
			httpPost.addHeader("CurTime", curTime);
			httpPost.addHeader("CheckSum", checkSum);
			httpPost.addHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");

			// 设置请求的参数
			List<NameValuePair> nvps = new ArrayList<NameValuePair>();
			nvps.add(new BasicNameValuePair("from", from));
			nvps.add(new BasicNameValuePair("msgtype", msgType));
			nvps.add(new BasicNameValuePair("to", to));
			nvps.add(new BasicNameValuePair("attach", attach));
			nvps.add(new BasicNameValuePair("save", save));
			nvps.add(new BasicNameValuePair("option", option));
			if (StringUtils.isNotBlank(pushcontent)) {
				nvps.add(new BasicNameValuePair("pushcontent", pushcontent));
			}
			if (StringUtils.isNotBlank(payload)) {
				nvps.add(new BasicNameValuePair("payload", payload));
			}
			httpPost.setEntity(new UrlEncodedFormEntity(nvps, "utf-8"));
			// 执行请求
			HttpResponse response = httpClient.execute(httpPost);
			HttpEntity resEntity = response.getEntity();
			if (resEntity != null) {
				responseContent = EntityUtils.toString(resEntity, "UTF-8");
				EntityUtils.consume(resEntity);
			}
			httpClient.getConnectionManager().shutdown();
		} catch (Exception e) {
			responseContent = "{\"desc\":\" " + e.getMessage() + " \",\"code\":\"500\"}";
		}
		System.out.println(responseContent);
		return responseContent;
	}

	public static String sendAttachMsgDubbo(String dubboAppKey, String dubboAppSecret, String dubboImPrefix,
			String from, String msgType, String to, String attach, String pushcontent, String payload, String sound,
			String save, String option) {
		DefaultHttpClient httpClient = new DefaultHttpClient();
		String responseContent = "";
		try {
			HttpPost httpPost = new HttpPost(sendAttachMsg);
			String nonce = "2345678";
			String curTime = String.valueOf((new Date()).getTime() / 1000L);
			String checkSum = CheckSumBuilder.getCheckSum(dubboAppSecret, nonce, curTime);// 参考计算CheckSum的java代码

			// 设置请求的header
			httpPost.addHeader("AppKey", dubboAppKey);
			httpPost.addHeader("Nonce", nonce);
			httpPost.addHeader("CurTime", curTime);
			httpPost.addHeader("CheckSum", checkSum);
			httpPost.addHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");

			// 设置请求的参数
			List<NameValuePair> nvps = new ArrayList<NameValuePair>();
			nvps.add(new BasicNameValuePair("from", from));
			nvps.add(new BasicNameValuePair("msgtype", msgType));
			nvps.add(new BasicNameValuePair("to", dubboImPrefix + to));
			nvps.add(new BasicNameValuePair("attach", attach));
			nvps.add(new BasicNameValuePair("save", save));
			nvps.add(new BasicNameValuePair("option", option));
			if (StringUtils.isNotBlank(pushcontent)) {
				nvps.add(new BasicNameValuePair("pushcontent", pushcontent));
			}
			if (StringUtils.isNotBlank(payload)) {
				nvps.add(new BasicNameValuePair("payload", payload));
			}
			if (StringUtils.isNotBlank(sound)) {
				nvps.add(new BasicNameValuePair("sound", sound));
			}
			httpPost.setEntity(new UrlEncodedFormEntity(nvps, "utf-8"));
			// 执行请求
			HttpResponse response = httpClient.execute(httpPost);
			HttpEntity resEntity = response.getEntity();
			if (resEntity != null) {
				responseContent = EntityUtils.toString(resEntity, "UTF-8");
				EntityUtils.consume(resEntity);
			}
			httpClient.getConnectionManager().shutdown();
		} catch (Exception e) {
			responseContent = "{\"desc\":\" " + e.getMessage() + " \",\"code\":\"500\"}";
		}
		System.out.println(responseContent);
		return responseContent;
	}

	/**
	 * 批量发送点对点自定义系统通知
	 * 
	 * @param from
	 * @param to
	 * @param attach
	 * @param pushcontent
	 * @param payload
	 * @param sound
	 * @param save
	 * @param option
	 * @return
	 */
	public static String sendBatchAttachMsg(String from, String to, String attach, String pushcontent, String payload,
			String sound, String save, String option) {
		DefaultHttpClient httpClient = new DefaultHttpClient();
		String responseContent = "";
		try {
			HttpPost httpPost = new HttpPost(sendBatchAttachMsg);
			String nonce = "2345678";
			String curTime = String.valueOf((new Date()).getTime() / 1000L);
			String checkSum = CheckSumBuilder.getCheckSum(appSecret, nonce, curTime);// 参考计算CheckSum的java代码

			// 设置请求的header
			httpPost.addHeader("AppKey", appKey);
			httpPost.addHeader("Nonce", nonce);
			httpPost.addHeader("CurTime", curTime);
			httpPost.addHeader("CheckSum", checkSum);
			httpPost.addHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");

			// 设置请求的参数
			List<NameValuePair> nvps = new ArrayList<NameValuePair>();
			nvps.add(new BasicNameValuePair("fromAccid", from));
			nvps.add(new BasicNameValuePair("toAccids", to));
			nvps.add(new BasicNameValuePair("attach", attach));
			nvps.add(new BasicNameValuePair("save", save));
			nvps.add(new BasicNameValuePair("option", option));
			httpPost.setEntity(new UrlEncodedFormEntity(nvps, "utf-8"));
			// 执行请求
			HttpResponse response = httpClient.execute(httpPost);
			HttpEntity resEntity = response.getEntity();
			if (resEntity != null) {
				responseContent = EntityUtils.toString(resEntity, "UTF-8");
				EntityUtils.consume(resEntity);
			}
			httpClient.getConnectionManager().shutdown();
		} catch (Exception e) {
			responseContent = "{\"desc\":\" " + e.getMessage() + " \",\"code\":\"500\"}";
		}
		return responseContent;
	}

	public static void main(String[] args) throws Exception {

//		System.out.println(delFriend("tojoycloud_test_6315", "tojoycloud_test_10117"));
	}
}
