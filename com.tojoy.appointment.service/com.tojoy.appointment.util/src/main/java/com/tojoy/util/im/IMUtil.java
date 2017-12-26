package com.tojoy.util.im;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.tojoy.util.JsonUtil;
import com.tojoy.util.RedisCache;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.cache.Cache.ValueWrapper;

import com.tojoy.util.SpringContextUtil;

/**
 * 网易云工具类
 * 
 * @ClassName: IMUtil
 * @Description: TODO
 * @author: zhangfan
 * @date:2017年9月4日
 */
public class IMUtil {
	private static final String url = "https://api.netease.im/nimserver/user/create.action";
	private static final String appKey = "e7382694a60dc94ecc2550f20d6612de";
	private static final String appSecret = "cb906ec420e7";

	private static final String updateurl = "https://api.netease.im/nimserver/user/update.action";
	// 更新用户信息
	private static final String updateUinfourl = "https://api.netease.im/nimserver/user/updateUinfo.action";

	private static final String addfriendurl = "https://api.netease.im/nimserver/friend/add.action";
	// 创建群
	private static final String teamurl = "https://api.netease.im/nimserver/team/create.action";

	private static final String updateteamurl = "https://api.netease.im/nimserver/team/update.action";

	private static final String addteamurl = "https://api.netease.im/nimserver/team/add.action";

	private static final String kickteamurl = "https://api.netease.im/nimserver/team/kick.action";

	private static final String leaveteamurl = "https://api.netease.im/nimserver/team/leave.action";

	private static final String removeteamurl = "https://api.netease.im/nimserver/team/remove.action";

	// 更新token
	private static final String refreshTokenUrl = "https://api.netease.im/nimserver/user/refreshToken.action";

	/**
	 * 生成token
	 */
	public static String sendUser(String accid,String token) {
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

			// 设置请求的参数
			List<NameValuePair> nvps = new ArrayList<NameValuePair>();
			nvps.add(new BasicNameValuePair("accid", accid));
			nvps.add(new BasicNameValuePair("token", token));
			httpPost.setEntity(new UrlEncodedFormEntity(nvps, "utf-8"));
			// 执行请求
			HttpResponse response = httpClient.execute(httpPost);
			HttpEntity resEntity = response.getEntity();
			if (resEntity != null) {
				responseContent = EntityUtils.toString(resEntity, "UTF-8");
				EntityUtils.consume(resEntity);
			}
			System.out.println(accid + "  &" + responseContent);
			httpClient.getConnectionManager().shutdown();
		} catch (Exception e) {

		}
		return responseContent;
	}

	/**
	 * 生成用户
	 */
	public static void genUser(List list) {
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

		}

	}

	/**
	 * 更新用户
	 */
	public static void updateUser(List list) {
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
				System.out.println(responseContent);
			}
			httpClient.getConnectionManager().shutdown();
		} catch (Exception e) {

		}
	}

	/**
	 * 更新用户
	 */
	public static void updateUserOne(String userid, String nickname, String headPortrait) {
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
			// 设置请求的参数
			List<NameValuePair> nvps = new ArrayList<NameValuePair>();
			nvps.add(new BasicNameValuePair("accid", userid));
			nvps.add(new BasicNameValuePair("name", nickname));
			nvps.add(new BasicNameValuePair("icon", headPortrait));
			nvps.add(new BasicNameValuePair("avatar",headPortrait));
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

		}
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
				System.out.println(responseContent);
			}
			httpClient.getConnectionManager().shutdown();

		} catch (Exception e) {

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
	public static String addFriendOne(String accid, String faccid) {
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
			nvps.add(new BasicNameValuePair("type", "1"));
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
			nvps.add(new BasicNameValuePair("joinmode", "0"));
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
			System.out.println(responseContent);
			httpClient.getConnectionManager().shutdown();
		} catch (Exception e) {

		}
		return tid;
	}

	/**
	 * 更新群信息
	 * 
	 * @param tid
	 *            网易云通信服务器产生，群唯一标识
	 * @param tname
	 *            群名称
	 * @param owner
	 *            群主用户帐号
	 * @return
	 */
	public static String updateTeam(String tid, String tname, String owner) {
		DefaultHttpClient httpClient = new DefaultHttpClient();
		String responseContent = "";
		try {
			HttpPost httpPost = new HttpPost(updateteamurl);
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
			nvps.add(new BasicNameValuePair("tname", tname));
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

		}
		return responseContent;
	}

	/**
	 * 入群
	 * 
	 * @param tid
	 * @param owner
	 * @param members
	 * @return
	 */
	public static String addTeam(String tid, String owner, JSONArray members) {
		DefaultHttpClient httpClient = new DefaultHttpClient();
		String responseContent = "";
		try {
			HttpPost httpPost = new HttpPost(addteamurl);
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
			nvps.add(new BasicNameValuePair("members", members.toString()));
			nvps.add(new BasicNameValuePair("msg", "welcome"));
			nvps.add(new BasicNameValuePair("magree", "0"));
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

		}
		return responseContent;
	}

	/**
	 * 创建群
	 */
	public static String refreshToken(String accid) {
		DefaultHttpClient httpClient = new DefaultHttpClient();
		String responseContent = "";
		try {
			HttpPost httpPost = new HttpPost(refreshTokenUrl);
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
		// long ex = 24*3600000l; //有效期1天
		long ex = 120l; // 有效期2分钟 测试使用。
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

	public static List getDataList() {
		List dataList = new ArrayList();
		Map data = new HashMap();
		data.put("account", "6770");
		data.put("headPortrait",
				"http://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTJLYl4RGExAHgAiapZbEO7dOMAibxQAtC6iafV2S9tZVxnFqoiaZ0A5fY5DvFEV6icykliaa8qWK1VA48Og/0");
		data.put("nickname", "赵变");
		data.put("token", "1d9bd7bf255488d2235eb75ef1fb82c8");
		dataList.add(data);
		Map data1 = new HashMap();
		data1.put("account", "6771");
		data1.put("headPortrait",
				"http://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTJLYl4RGExAHgAiapZbEO7dOMAibxQAtC6iafV2S9tZVxnFqoiaZ0A5fY5DvFEV6icykliaa8qWK1VA48Og/0");
		data1.put("nickname", "张三");
		data1.put("token", "2194349666f25034a3cd85171017e2e5");
		dataList.add(data1);
		Map data2 = new HashMap();
		data2.put("account", "6772");
		data2.put("headPortrait",
				"http://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTJLYl4RGExAHgAiapZbEO7dOMAibxQAtC6iafV2S9tZVxnFqoiaZ0A5fY5DvFEV6icykliaa8qWK1VA48Og/0");
		data2.put("nickname", "李四");
		data2.put("token", "909e1ad712ecc1e2d2d8ddfd86a2db9f");
		dataList.add(data2);
		Map data3 = new HashMap();
		data3.put("account", "6773");
		data3.put("headPortrait",
				"http://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTJLYl4RGExAHgAiapZbEO7dOMAibxQAtC6iafV2S9tZVxnFqoiaZ0A5fY5DvFEV6icykliaa8qWK1VA48Og/0");
		data3.put("nickname", "赵发生");
		data3.put("token", "6d373d7ec64d0f0cdfb53ab348a00829");
		dataList.add(data3);
		Map data4 = new HashMap();
		data4.put("account", "6774");
		data4.put("headPortrait",
				"http://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTJLYl4RGExAHgAiapZbEO7dOMAibxQAtC6iafV2S9tZVxnFqoiaZ0A5fY5DvFEV6icykliaa8qWK1VA48Og/0");
		data4.put("nickname", "嘎斯到");
		data4.put("token", "d892eb9ccae6901a5e40df57acc62b8c");
		dataList.add(data4);
		Map data5 = new HashMap();
		data5.put("account", "6775");
		data5.put("headPortrait",
				"http://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTJLYl4RGExAHgAiapZbEO7dOMAibxQAtC6iafV2S9tZVxnFqoiaZ0A5fY5DvFEV6icykliaa8qWK1VA48Og/0");
		data5.put("nickname", "赵为");
		data5.put("token", "c61dc78dc40eba01c58ade7ee4bbdecc");
		dataList.add(data5);
		Map data6 = new HashMap();
		data6.put("account", "6776");
		data6.put("headPortrait",
				"http://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTJLYl4RGExAHgAiapZbEO7dOMAibxQAtC6iafV2S9tZVxnFqoiaZ0A5fY5DvFEV6icykliaa8qWK1VA48Og/0");
		data6.put("nickname", "犹太人");
		data6.put("token", "57427f3f8e582c3ae45830e0793d36b0");
		dataList.add(data6);
		Map data7 = new HashMap();
		data7.put("account", "6777");
		data7.put("headPortrait",
				"http://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTJLYl4RGExAHgAiapZbEO7dOMAibxQAtC6iafV2S9tZVxnFqoiaZ0A5fY5DvFEV6icykliaa8qWK1VA48Og/0");
		data7.put("nickname", "金坷垃");
		data7.put("token", "0ae2a4f30b041c305d0b44cd6670f75a");
		dataList.add(data7);
		Map data8 = new HashMap();
		data8.put("account", "6778");
		data8.put("headPortrait",
				"http://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTJLYl4RGExAHgAiapZbEO7dOMAibxQAtC6iafV2S9tZVxnFqoiaZ0A5fY5DvFEV6icykliaa8qWK1VA48Og/0");
		data8.put("nickname", "问问去");
		data8.put("token", "ae2a6778af390f7355081c85291a60db");
		dataList.add(data8);
		Map data9 = new HashMap();
		data9.put("account", "6779");
		data9.put("headPortrait",
				"http://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTJLYl4RGExAHgAiapZbEO7dOMAibxQAtC6iafV2S9tZVxnFqoiaZ0A5fY5DvFEV6icykliaa8qWK1VA48Og/0");
		data9.put("nickname", "排位");
		data9.put("token", "c530e05fe05c43d7328582e0080ba9f4");
		dataList.add(data9);

		Map data10 = new HashMap();
		data10.put("account", "6196");
		data10.put("headPortrait",
				"http://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTJLYl4RGExAHgAiapZbEO7dOMAibxQAtC6iafV2S9tZVxnFqoiaZ0A5fY5DvFEV6icykliaa8qWK1VA48Og/0");
		data10.put("nickname", "排位");
		data10.put("token", "c530e05fe05c43d7328582e0080ba9f4");
		dataList.add(data10);
		return dataList;
	}

	public static void main(String[] args) throws Exception {

		List dataList = getDataList();

		// System.out.println(getUserImToken("6196"));
//		genUser(dataList);
//		updateUser(dataList);
		// for(int i=0;i<dataList.size();i++){
		// Map d = new HashMap();
		// d = (Map) dataList.get(i);
		// System.out.println(d.get("nickname").toString()+"
		// "+d.get("headPortrait").toString()+" "+d.get("account"));
		// }

		// System.out.println(addFriend("6770","6771"));
		// System.out.println(addFriend("6770","6772"));
		// System.out.println(addFriend("6770","6773"));
		// System.out.println(addFriend("6770","6774"));
		// System.out.println(addFriend("6770","6775"));
		// System.out.println(addFriend("6770","6776"));
		// System.out.println(addFriend("6770","6777"));
		// System.out.println(addFriend("6770","6778"));
		// System.out.println(addFriend("6770","6779"));
		//
		// System.out.println(addFriend("6771","6771"));
		// System.out.println(addFriend("6771","6772"));
		// System.out.println(addFriend("6771","6773"));
		// System.out.println(addFriend("6771","6774"));
		// System.out.println(addFriend("6771","6775"));
		// System.out.println(addFriend("6771","6776"));
		// System.out.println(addFriend("6771","6777"));
		// System.out.println(addFriend("6771","6778"));
		// System.out.println(addFriend("6771","6779"));

		// List dataList2 = new ArrayList();
		// JSONArray members = new JSONArray();
		// Map d1 = new HashMap();
		// d1.put("tname", "222");
		// d1.put("owner", "6770");
		// members.add("6771");
		// members.add("6772");
		// d1.put("members",members);
		// dataList2.add(d1);
		//
		// Map dd = new HashMap();
		// dd.put("tname", "333");
		// dd.put("owner", "6770");
		// members.add("6771");
		// members.add("6772");
		// dd.put("members",members);
		// dataList2.add(dd);
		//
		// List rl = createTeam(dataList2);
		// for(int i=0;i<rl.size();i++){
		// Map d2 = new HashMap();
		// d2 = (Map) rl.get(i);
		// System.out.println(d2.get("tname").toString()+"
		// "+d2.get("tid").toString());
		// }
		JSONArray ids = new JSONArray();
		ids.add("123712837");
		queryTeam(ids);
	}
}
