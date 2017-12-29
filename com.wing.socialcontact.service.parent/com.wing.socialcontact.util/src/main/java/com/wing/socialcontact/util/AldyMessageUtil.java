package com.wing.socialcontact.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.dubbo.common.json.JSONArray;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.reflect.TypeToken;
//import com.taobao.api.ApiException;
//import com.taobao.api.DefaultTaobaoClient;
//import com.taobao.api.TaobaoClient;
//import com.taobao.api.request.AlibabaAliqinFcSmsNumSendRequest;
//import com.taobao.api.response.AlibabaAliqinFcSmsNumSendResponse;
import com.wing.socialcontact.cn.emay.eucp.inter.http.v1.dto.response.ResponseData;
import com.wing.socialcontact.cn.emay.eucp.inter.http.v1.dto.response.SmsResponse;
import com.wing.socialcontact.cn.emay.util.DateUtil;
import com.wing.socialcontact.cn.emay.util.JsonHelper;
import com.wing.socialcontact.cn.emay.util.Md5;
import com.wing.socialcontact.cn.emay.util.http.EmayHttpClient;
import com.wing.socialcontact.cn.emay.util.http.EmayHttpRequestKV;
import com.wing.socialcontact.cn.emay.util.http.EmayHttpResponseString;
import com.wing.socialcontact.cn.emay.util.http.EmayHttpResponseStringPraser;
import com.wing.socialcontact.cn.emay.util.http.EmayHttpResultCode;

/**
 * 阿里大鱼短信接口
 * 
 * @ClassName: AldyMessageUtil
 * @Description: TODO
 * @author: zengmin
 * @date:2017年3月29日 上午9:40:46
 */
public class AldyMessageUtil {
	private static final String URL = ApplicationPath.getParameter("URL");
	private static final String APP_KEY = ApplicationPath.getParameter("APP_KEY");
	private static final String APP_SECRET = ApplicationPath.getParameter("APP_SECRET");
	// public static final String SMSPRE = "天九云";// 签名1
	public static final String SMSPRE = "天九共享网";// 签名4
	// public static final String SMSPRE =ApplicationPath.getParameter("SMSPRE")
	// ;// 签名5
	// public static final String SMSPRE = "天九集团"; // 签名2
	public static final String SMSPRE_APP = "企服云"; // 签名3
	// public static final String SMSPRE_APP
	// =ApplicationPath.getParameter("SMSPRE_APP") ; // 签名3

	// emay message
	private static final String EMAY_APPID = ApplicationPath.getParameter("EMAY_APPID");
	private static final String EMAY_HOST = ApplicationPath.getParameter("EMAY_HOST");
	private static final String EMAY_APPSECRET = ApplicationPath.getParameter("EMAY_APPSECRET");
	
//	private static final String EMAY_APPID = "EUCP-EMY-SMS1-0FFAZ";
//	private static final String EMAY_HOST = "bjmtn.b2m.cn";
//	private static final String EMAY_APPSECRET = "0EF2F09CD803C10F";

	/**
	 * 短信模板编号
	 * 
	 * @ClassName: MsmTemplateId
	 * @Description: TODO
	 * @author: zengmin
	 * @date: 2017年4月21日 上午10:36:55
	 */
	public static class MsmTemplateId {
		/**
		 * 注册验证码，template:验证码${code}，您正在注册成为${product}用户，感谢您的支持！
		 */
		public static final String REG = "SMS_88375020";
		/**
		 * 首次登录，template:终于等到你，${name}老板，热烈欢迎您登录天九共享网，来到这里说明您已注册，越早进行认证，
		 * 越早收获优质服务哦。如遇操作问题，请联系专属服务秘书。
		 */
		public static final String ONE_LOGIN = "SMS_79060093";
		/**
		 * 认证审核通过，template:恭喜恭喜，${name}老板，您提交的认证信息已通过审核啰，再次恭喜您成为天九共享会认证用户。天九在手，
		 * 老板无忧。
		 */
		public static final String RECON_SUCCESS = "SMS_79020093";
		/**
		 * 认证审核未通过，template:${name}老板，非常抱歉，您提交的认证信息未通过审核，还请继续完善认证信息，确保认证成功哦，${qm
		 * }各项优质服务等待您。如有问题请拨打客服电话${dh}。
		 */
		public static final String RECON_FAILURE = "SMS_91055010";
		/**
		 * 活动结束提醒，template:尊敬的${name}，您所发布的${hdname}活动已结束，报名费共计：${fy}元，已到您的RMB余额
		 * ，望尽快查收。 (包含立即前往，弃用)
		 */
		// public static final String ACTIVITY_FINISH = "SMS_62815001";
		/**
		 * 活动结束，template:${name}老板，您在平台发布的${hdname}活动已结束，报名费共计：${fy}元，款项已到账，
		 * 赶紧确认下吧。
		 */
		public static final String ACTIVITY_FINISH = "SMS_90975065";
		/**
		 * 模板名称: 活动取消（发起人） 模板ID: SMS_91005076 模板内容:
		 * ${name}老板，您所发布的${hdname}活动已取消，请您尽快前去核实，特此通知。
		 */
		public static final String ACTIVITY_CANCEL = "SMS_91005076";
		/**
		 * 模板名称: 活动取消后，后台再次开通 模板ID: SMS_90980085 模板内容:
		 * ${name}老板，您于${time}取消的${hdname}活动已被后台打开，请您尽快前去核实，特此通知。
		 */
		public static final String ACTIVITY_ZAIKAITONG = "SMS_90980085";
		/**
		 * 会议开始提醒，template:${name}老板，您所报名的${hyname}会议于${time}正式开始啦，请您提前持入场凭证参加会议
		 * ，并将手机调至震动模式。收看直播请立即前往。
		 */
		public static final String MEETING_START = "SMS_90880075";
		/**
		 * 会议报名成功提醒，template:尊敬的${name}，您于${time}成功报名${hyname}会议，会议凭证为（姓名：${
		 * uname}，电话：${mobilem}）。
		 */
		public static final String MEETING_SIGNUP_SUCCESS = "SMS_90925060";
		/**
		 * 充值提醒，template:${name}土豪，您的账户于${time}成功充值${money}，当前${czType}余额为${ye}。
		 * 。
		 */
		public static final String RECHARGE = "SMS_79100083";
		/**
		 * 消费提醒，template:${name}土豪,您的账户于${time}消费${money}，当前${xfType}余额为${ye}。
		 */
		public static final String CONSUMPTION = "SMS_79245001";

		/**
		 * 商洽及应答被采纳，template:${name}老板，您在${subname}所发布的${type}您共计获得${money}J币，
		 * 已经放入您的账户，还请尽快登录查看。
		 */
		public static final String NEGOTIATE_SUCCESS = "SMS_91735003";

		/**
		 * 提现审核通过，template:${name}老板，您的账户于${time}成功提现${money}，您当前账户余额为${ye}。
		 * 请确认好资金安全。
		 */
		public static final String WALLET_TX_SUCCESS = "SMS_78995082";

		/**
		 * 提现审核未通过，template:${name}老板，您的账户于${time}申请的提现${money}审核失败，现已归还到您的账户余额，
		 * 当前您的账户余额为${ye}。请确认好资金安全。
		 */
		public static final String WALLET_TX_FAILURE = "SMS_79040072";

		/**
		 * 开通互助宝，template:${name}老板，恭喜您！您已成功开通${level}，更多充值，将尊享更多服务。${type}
		 * 为您开启互助共赢模式。
		 */
		public static final String HZB_KT = "SMS_79345001";
		/**
		 * 启用互助宝，template:${name}老板，恭喜恭喜，您的${time}于后台被启用，请您尽快前去验证。${type}
		 * 为您开启互助共赢模式。
		 */
		public static final String HZB_QY = "SMS_79290001";
		/**
		 * 升级互助宝，template:${name}老板，恭喜您！您的${type}累计充值金额已达到${money}等级提升为${level}，
		 * 为您开启互助共赢模式。
		 */
		public static final String HZB_SJ = "SMS_79410003";
		/**
		 * 降级互助宝，template:${name}老板，很抱歉，因为您的${type}尚未达到年度承诺累计消费${money}，所以您的${
		 * type2}等级降为${level}，为您开启互助共赢模式。
		 */
		public static final String HZB_JJ = "SMS_79440001";

		/**
		 * 到期互助宝，template:${name}老板，您的${type}已到期，请您尽快前去验证。${type2}为您开启互助共赢模式。
		 */
		public static final String HZB_DQ = "SMS_79300006";

		/**
		 * 停用互助宝，template:${name}老板，非常遗憾，您的${time}被后台停用，请您尽快前去验证。${type}
		 * 为您开启互助共赢模式。
		 */
		public static final String HZB_TY = "SMS_79335003";
		/**
		 * 直播开始前通知，template:${name}老板，您所支付的${livename}直播于${starttime}正式开始，
		 * 还有3分钟就开始了，精彩不容错过。
		 * 
		 */
		public static final String LIVE_START = "SMS_90065045";
		/**
		 * 直播报名，template:${name}老板，看这里！您于${paytime}成功支付${livename}直播。直播开始时间为：${
		 * playtime}，届时记得收看！
		 */
		public static final String LIVE_SIGNUP = "SMS_90120039";

		/**
		 * 认证用户30天到期，template:【天九共享网】敬爱的${name}老板，您在天九共享网的认证资格将于30日后到期，请您尽快前去核实、
		 * 办理。如遇操作问题，请联系专属服务秘书。天九共享网期待您的光临。
		 */
		public static final String RECON_DQ_30 = "SMS_90120041";
		/**
		 * 认证用户7天到期，template:【天九共享网】敬爱的${name}老板，您在天九共享网的认证资格将于7日后到期，为了保证您的良好体验，
		 * 请您尽快前去核实、办理。如遇操作问题，请联系专属服务秘书。天九共享网期待您的光临。
		 */
		public static final String RECON_DQ_7 = "SMS_90120042";
		/**
		 * 认证用户30天到期(服务秘书)，template:【天九共享网】亲爱的${msname}，您的${name}
		 * 客户在天九共享网的认证资格将于30日后到期，请您尽快联系客户上线核实。天九共享网与您共同进步。
		 */
		public static final String RECON_MS_30 = "SMS_90120043";
		/**
		 * 认证用户7天到期(服务秘书)，template:【天九共享网】亲爱的${msname}，您的${name}
		 * 客户在天九共享网的认证资格将于7日后到期，届时将丧失线上服务资格，请您尽快联系客户上线核实、续办认证业务。天九共享网与您共同进步。
		 */
		public static final String RECON_MS_7 = "SMS_90120044";
	}

	/**
	 * 消息发送共用方法
	 * 
	 * @Title: sendSms
	 * @Description: TODO
	 * @param templateId
	 * @param recNum
	 * @param param
	 * @return
	 * @return: boolean
	 * @author: zengmin
	 * @date: 2017年3月21日 下午3:50:59
	 */

	// EMAY
	public static boolean sendSms(String templateId, String recNum, String param) {
		try {
			// appId
			String appId = EMAY_APPID.trim();// 请联系销售，或者在页面中 获取
			// 密钥
			String secretKey = EMAY_APPSECRET.trim();// 请联系销售，或者在页面中 获取
			// 时间戳
			String timestamp = DateUtil.toString(new Date(), "yyyyMMddHHmmss");
			// 签名
			String sign = Md5.md5((appId + secretKey + timestamp).getBytes());
			// 接口地址
			String host = EMAY_HOST.trim();// 请联系销售获取
			System.out.println("templateId============" + templateId);
			String content = ApplicationPath.getParameter(templateId);
			com.alibaba.fastjson.JSONArray ja = JSON.parseArray("[" + param + "]");
			for (int i = 0; i < ja.size(); i++) {
				JSONObject jo = ja.getJSONObject(i);
				Set<String> obset = jo.keySet();
				for (String key : obset) {
					content = content.replaceAll("!" + key + "!", (String) jo.get(key));
				}
			}
			System.out.println("content============" + content);
			// 发送批次短信,定时时间格式yyyyMMddHHmmss
			return setSms(appId, sign, timestamp, host, content, recNum, "839273940", null, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	/**
	 * 直接发送短信方法
	 * @param templateId
	 * @param recNum
	 * @param param
	 * @return
	 */
	public static boolean directSend(String content,String mobile) {
		try {
			// appId
			String appId = EMAY_APPID.trim();// 请联系销售，或者在页面中 获取
			// 密钥
			String secretKey = EMAY_APPSECRET.trim();// 请联系销售，或者在页面中 获取
			// 时间戳
			String timestamp = DateUtil.toString(new Date(), "yyyyMMddHHmmss");
			// 签名
			String sign = Md5.md5((appId + secretKey + timestamp).getBytes());
			System.out.println(sign);
			// 接口地址
			String host = EMAY_HOST.trim();// 请联系销售获取
			// 发送批次短信,定时时间格式yyyyMMddHHmmss
			return setSms(appId, sign, timestamp, host, content, mobile, "839273940", null, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	/**
	 * @param appId
	 * @param sign
	 * @param timestamp
	 * @param host
	 * @param content
	 * @param mobiles
	 * @param customSmsId
	 * @param extendedCode
	 * @param timerTime
	 * @return
	 */

	private static boolean setSms(String appId, String sign, String timestamp, String host, String content,
			String mobiles, String customSmsId, String extendedCode, String timerTime) {
		System.out.println("============= setSms==================");
		Map<String, String> params = new HashMap<String, String>();
		try {
			params.put("appId", appId);
			params.put("sign", sign);
			params.put("timestamp", timestamp);
			params.put("mobiles", mobiles);
			params.put("content", URLEncoder.encode(content, "utf-8"));
			if (customSmsId != null) {
				params.put("customSmsId", customSmsId);
			}
			if (timerTime != null) {
				params.put("timerTime", timerTime);
			}
			if (extendedCode != null) {
				params.put("extendedCode", extendedCode);
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		String json = request(params, "http://" + host + "/simpleinter/sendSMS");
		if (json != null) {
			ResponseData<SmsResponse[]> data = JsonHelper.fromJson(new TypeToken<ResponseData<SmsResponse[]>>() {
			}, json);
			String code = data.getCode();
			if ("SUCCESS".equals(code)) {
				for (SmsResponse d : data.getData()) {
					System.out.println("data:" + d.getMobile() + "," + d.getSmsId() + "," + d.getCustomSmsId());
				}
				return true;
			}
		}
		System.out.println("=============end setSms==================");
		return false;
	}

	/**
	 * 公共请求方法
	 */
	public static String request(Map<String, String> params, String url) {
		EmayHttpRequestKV request = new EmayHttpRequestKV(url, "UTF-8", "POST", null, null, params);
		EmayHttpClient client = new EmayHttpClient();
		String json = null;
		try {
			String mapst = "";
			for (String key : params.keySet()) {
				String value = params.get(key);
				mapst += key + "=" + value + "&";
			}
			mapst = mapst.substring(0, mapst.length() - 1);
			System.out.println("request params: " + mapst);
			EmayHttpResponseString res = client.service(request, new EmayHttpResponseStringPraser());
			if (res == null) {
				System.err.println("请求接口异常");
				return null;
			}
			if (res.getResultCode().equals(EmayHttpResultCode.SUCCESS)) {
				if (res.getHttpCode() == 200) {
					json = res.getResultString();
					System.out.println("response json: " + json);
				} else {
					System.out.println("请求接口异常,请求码:" + res.getHttpCode());
				}
			} else {
				System.out.println("请求接口网络异常:" + res.getResultCode().getCode());
			}
		} catch (Exception e) {
			System.err.println("解析失败");
			e.printStackTrace();
		}
		return json;
	}

	public static boolean sendSms1(String templateId, String recNum, String param) {
//		try {
//			TaobaoClient client = new DefaultTaobaoClient("http://gw.api.taobao.com/router/rest", "23736541",
//					"a9990790515e900198fc3111b10dd34a");
//			AlibabaAliqinFcSmsNumSendRequest req = new AlibabaAliqinFcSmsNumSendRequest();
//			// req.setExtend("123456");//可选
//			req.setSmsType("normal");
//			if (MsmTemplateId.REG.equals(templateId) && param.indexOf(SMSPRE_APP) != -1) {
//				req.setSmsFreeSignName(SMSPRE_APP);
//			} else {
//				req.setSmsFreeSignName(SMSPRE);
//			}
//			req.setSmsParamString(param);
//			req.setRecNum(recNum);// 接收人，可传多个手机号，单次最多200个
//			req.setSmsTemplateCode(templateId);
//			AlibabaAliqinFcSmsNumSendResponse rsp = client.execute(req);
//			if (parseResult(rsp.getBody())) {
//				return true;
//			} else {
//				return false;
//			}
//		} catch (ApiException e) {
//			e.printStackTrace();
//		}
		return false;
	}

	private static boolean parseResult(String result) {
		// System.out.println("sms_send_result:" + URL+" "+APP_KEY+"
		// "+APP_SECRET);
		System.out.println("sms_send_result:" + result);
		if (StringUtils.isEmpty(result)) {
			return false;
		}
		JSONObject jo = JSON.parseObject(result);
		JSONObject jo2 = jo.getJSONObject("alibaba_aliqin_fc_sms_num_send_response");
		JSONObject jo3 = jo2.getJSONObject("result");
		boolean bo = jo3.getBooleanValue("success");
		return bo;
	}

	public static void main(String[] args) {
		System.out.println(directSend("邀请通知", "18511595606"));
	}

	/**
	 * 活动消息内容
	 */
	/**
	 * 收到报名 通知发布人
	 * 
	 * @param createusername
	 * @param title
	 * @return
	 */
	public static String activitysignup(String createusername, String title) {
		String content = "【" + SMSPRE + "】" + createusername + "老板,您在平台所发布的的" + title + "活动收到新的报名，望尽快处理。";
		return content;
	}

	/**
	 * 活动取消 通知报名人
	 * 
	 * @param username
	 * @param title
	 * @return
	 */
	public static String activitycancel(String username, String title) {
		String content = "【" + SMSPRE + "】" + username + "老板,您在平台所报名的" + title + "活动已取消，望尽快与活动主办人核实，特此通知。";
		return content;
	}

	/**
	 * 活动取消申请通过 发布人
	 * 
	 * @param username
	 * @param title
	 * @return 【天九共享会】**老板，您所发布的****活动已取消，请您尽快前去核实，特此通知。
	 */
	public static String activitycancelaccept(String username, String title) {
		String content = "【" + SMSPRE + "】" + username + "老板,您所发布的" + title + "活动取消申请已通过，望尽快处理。";
		return content;
	}

	/**
	 * 活动取消申请拒绝 发布人
	 * 
	 * @param username
	 * @param title
	 * @return
	 */
	public static String activitycancelrefuse(String username, String title) {
		String content = "【" + SMSPRE + "】" + username + "老板,您所发布的" + title + "活动取消申请未通过，望尽快处理。";
		return content;
	}

	/**
	 * 活动被拒绝通知报名人
	 * 
	 * @param username
	 * @param title
	 * @return
	 */
	public static String activityrefuse(String username, String title, String id) {
		String content = "【" + SMSPRE + "】" + username + "老板,您在平台所报名的" + title + "活动未通过审核，望尽快于活动主办人进行沟通。<a href='"
				+ ConfigUtil.DOMAIN + "/wxfront/m/activity/activityDetailPage.do?id=" + id + "'>立即前往</a>";
		return content;
	}

	/**
	 * 活动报名通过 通知报名人
	 * 
	 * @param username
	 * @param title
	 *            【天九共享会】老板**，您所报名的****活动已通过审核，恭喜恭喜！请您尽快与活动主办人进行沟通。立即前往
	 * @return
	 */
	public static String activityaccept(String username, String title, String id) {
		String content = "【" + SMSPRE + "】" + username + "老板,您在平台所报名的" + title + "活动已通过主办人审核，恭喜恭喜！望尽快于主办人进行沟通。<a href='"
				+ ConfigUtil.DOMAIN + "/wxfront/m/activity/activityDetailPage.do?id=" + id + "'>立即前往</a>";
		return content;
	}

	/**
	 * 活动结束 通知发布人
	 * 
	 * @param username
	 * @param title
	 * @param fy
	 *            【天九共享网】**老板，您在平台发布的****活动已结束，报名费共计：987元，款项已到账，赶紧确认下吧。立即前往
	 * @return
	 */
	public static String activityover(String username, String title, Double fy) {
		String content = "【" + SMSPRE + "】" + username + "老板，您在平台发布的" + title + "活动已结束，报名费共计："
				+ CommUtil.formatNumStr(fy) + "元，款项已到账，赶紧确认下吧。";
		return content;
	}

	/**
	 * 活动退款 通知报名人
	 * 
	 * @param username
	 * @param title
	 * @param fy
	 *            【天九共享会】**老板，您在平台报名的****活动已退款，退款金额：98元，款项已到账，赶紧确认下吧。
	 * @return
	 */
	public static String activityrefund(String username, String title, Double fy) {
		String content = "【" + SMSPRE + "】" + username + "老板，您在平台报名的" + title + "活动已退款，退款金额："
				+ CommUtil.formatNumStr(fy) + "元，款项已到账，赶紧确认下吧。";
		return content;
	}

	/**
	 * 活动延期通过 报名人
	 * 
	 * @param username
	 * @param title
	 * @param starttime
	 * @param id
	 *            【天九共享会】**老板，您所报名的****活动已延期至2017-09-08
	 *            19:00:00，还请您尽快与活动主办人核实信息。立即前往
	 * @return
	 */
	public static String activitydelay(String username, String title, String starttime, String id) {
		String content = "【" + SMSPRE + "】" + username + "老板,您所报名的" + title + "活动已延期至" + starttime
				+ "，还请您尽快与活动主办人核实信息。<a href='" + ConfigUtil.DOMAIN + "/wxfront/m/activity/activityDetailPage.do?id="
				+ id + "'>立即前往</a>";
		// String content = "【" + SMSPRE + "】尊敬的" + username + ",您所报名的" + title
		// + "活动已延期至" + starttime
		// + "，望尽快与活动主办人核实。<a href='" + ConfigUtil.DOMAIN +
		// "/wxfront/m/activity/activityDetailPage.do?id=" + id
		// + "'>立即前往</a>";
		return content;
	}

	/**
	 * 管理员同意活动延期申请
	 * 
	 * @param username
	 * @param title
	 * @return
	 */
	public static String activitydelayaccept(String username, String title) {
		String content = "【" + SMSPRE + "】" + username + "老板,您所发布的" + title + "活动延期申请已通过，望尽快处理。";
		return content;
	}

	/**
	 * 管理员拒绝活动延期申请
	 * 
	 * @param username
	 * @param title
	 * @return
	 */
	public static String activitydelayrefuse(String username, String title) {
		String content = "【" + SMSPRE + "】" + username + "老板,您所发布的" + title + "活动延期申请未通过，望尽快处理。";
		return content;
	}

	/**
	 * 管理员重新打开取消的活动 // 发消息 【天九共享会】**老板，您于2016-03-12
	 * 19:22取消的****活动已被后台打开，请您尽快前去核实，特此通知。
	 */
	public static String activityOpen(String name, String title, String cancelTime) {
		String content = "【" + SMSPRE + "】" + name + "老板,您于" + cancelTime + "取消的" + title + "活动已被后台打开，请您尽快前去核实，特此通知。";
		return content;
	}

	/**
	 * 管理员直接取消活动
	 * 
	 * @param createUserName
	 * @param titles
	 * @return //【天九共享会】**老板，您所发布的****活动已取消，请您尽快前去核实，特此通知。
	 */
	public static String activitycancelToSponsor(String createUserName, String titles) {
		String content = "【" + SMSPRE + "】" + createUserName + "老板,您所发布的" + titles + "活动已取消，请您尽快前去核实，特此通知";
		return content;
	}

	/**
	 * 会议消息
	 */
	/**
	 * 会议报名成功
	 * 
	 * @param nickname
	 * @param signupdate
	 * @param titles
	 * @param mobile
	 *            【天九共享网】**老板，恭喜恭喜，您于2016-10-32
	 *            19:00成功报名****会议，出席会议别忘了携带会议凭证。凭证信息为：姓名：***，电话：********。
	 * @return
	 */
	public static String meetingsignup(String nickname, String signupdate, String titles, String mobile) {
		// String message = "【" + SMSPRE + "】尊敬的" + nickname + "，" + "您于" +
		// signupdate + "成功报名" + titles + "会议，会议凭证为：姓名："
		// + nickname + "，电话：" + mobile + "。";
		String message = "【" + SMSPRE + "】" + nickname + "老板，恭喜恭喜，您于" + signupdate + "成功报名" + titles
				+ "会议，出席会议别忘了携带会议凭证。凭证信息为：姓名：" + nickname + "，电话：" + mobile + "。";
		return message;
	}

	/**
	 * 会议报名提醒 发给关注人
	 * 
	 * @param nickname
	 * @param signupdate
	 * @param titles
	 * @param id
	 * @return 【天九共享会】**老板，您所关注的****会议于2016-03-01开始报名啦。千万不要错过！立即前往
	 */
	public static String meetingysignupremind(String nickname, String signupdate, String titles, String id) {
		String message = "【" + SMSPRE + "】" + nickname + "老板，您所关注的" + titles + "会议于" + signupdate + "开始报名啦。<a href='"
				+ ConfigUtil.DOMAIN + "/wxfront/m/meeting/detail/index.do?id=" + id + "'>立即前往</a>";
		// String message = "【" + SMSPRE + "】尊敬的" + nickname + "，" + "您所收藏的" +
		// titles + "会议于" + signupdate
		// + "开始报名。<a href='" + ConfigUtil.DOMAIN +
		// "/wxfront/m/meeting/detail/index.do?id=" + id + "'>立即前往</a>";
		return message;
	}

	/**
	 * 会议开始通知 报名人
	 * 
	 * @param nickname
	 * @param startdate
	 * @param titles
	 * @param id
	 *            【天九共享会】【天九共享网】**老板，您所报名的****会议于 2016-03-01
	 *            19:00正式开始啦，请您提前持入场凭证参加会议，并将手机调至震动模式。收看直播请立即前往。
	 * @return
	 */
	public static String meetingystartremind(String nickname, String startdate, String titles, String id) {
		String message = "【" + SMSPRE + "】" + nickname + "老板 ，您所报名的" + titles + "会议于" + startdate + "正式开始啦，"
				+ "请您提前持入场凭证参加会议，并将手机调至震动模式。收看直播请<a href='" + ConfigUtil.DOMAIN
				+ "/wxfront/m/meeting/detail/index.do?id=" + id + "'>立即前往</a>。";
		// String message = "【" + SMSPRE + "】尊敬的" + nickname + "，您所报名的" + titles
		// + "会议于" + startdate + "正式开始，"
		// + "请您提前持入场凭证参加会议，并将手机调至震动模式。<a href='" + ConfigUtil.DOMAIN +
		// "/wxfront/m/meeting/detail/index.do?id="
		// + id + "'>立即前往</a>";
		return message;
	}

	/**
	 * 项目
	 */
	/**
	 * 项目审核通过
	 * 
	 * @param nickname
	 * @param prjName
	 * @return
	 */
	public static String projectrecommendaccept(String nickname, String prjName) {
		String message = "【" + SMSPRE + "】尊敬的" + nickname + "，恭喜！您所推荐的" + prjName + "项目于"
				+ DateUtils.datetimeToString(new Date()) + "已通过审核。";
		return message;
	}

	/**
	 * 项目推荐被拒绝
	 * 
	 * @param nickname
	 * @param prjName
	 * @return
	 */
	public static String projectrecommendrefuse(String nickname, String prjName) {
		String message = "【" + SMSPRE + "】尊敬的" + nickname + "，抱歉！您所推荐的" + prjName + "项目于"
				+ DateUtils.datetimeToString(new Date()) + "未通过审核。";
		return message;
	}

	/**
	 * 用户相关
	 */
	/**
	 * 认证审核通过
	 * 
	 * @param name
	 * @return 【天九共享会】恭喜恭喜，**老板，您提交的认证信息已通过审核啰，再次恭喜您成为天九共享会认证用户。天九在手，老板无忧。
	 */
	public static String userreconaccept(String name) {
		String content = "【" + SMSPRE + "】恭喜恭喜，" + name + "老板，您提交的认证信息已通过审核啰，再次恭喜您成为天九共享会认证用户。天九在手，老板无忧。";
		return content;
	}

	/**
	 * 认证审核拒绝
	 * 
	 * @param name
	 * @return 【天九共享会】**老板，非常抱歉，您提交的认证信息未通过审核，还请继续完善认证信息，确保认证成功哦，平台优质服务等待您。
	 */
	public static String userreconrefuse(String name) {
		String content = "【" + SMSPRE + "】" + name + "老板，非常抱歉，您提交的认证信息未通过审核，还请继续完善认证信息，确保认证成功哦，" + SMSPRE
				+ "各项优质服务等待您。如有问题请拨打客服电话010-53118922。";
		return content;
	}

	/**
	 * 用户认证提醒
	 * 
	 * @param name
	 * @return 【天九共享会】终于等到你，**老板，热烈欢迎您登录天九共享网，来到这里说明您已注册，越早进行认证，越早收获优质服务哦^^。
	 *         如遇操作问题，请联系专属服务秘书。
	 */
	public static String userreconremind(String name) {
		String content = "【" + SMSPRE + "】终于等到你，" + name
				+ "老板，热烈欢迎您登录天九共享网，来到这里说明您已注册，越早进行认证，越早收获优质服务哦^^。如遇操作问题，请联系专属服务秘书。";
		// String content = "【" + SMSPRE + "】尊敬的" + name +
		// "，欢迎登录天九云平台，您已成功注册。请您尽快进行认证，收获优质服务。";
		return content;
	}

	/**
	 * 提现成功通知
	 * 
	 * @param name
	 * @param createtime
	 * @param txje
	 * @param syje
	 * @return
	 */
	public static String userwallettxsuccess(String name, String createtime, Double txje, Double syje) {
		String content = "【" + SMSPRE + "】" + name + "老板，您的账户于" + createtime + "成功提现RMB" + CommUtil.formatNumStr(txje)
				+ "元，您当前账户余额为RMB" + CommUtil.formatNumStr(syje) + "元。请确认好资金安全。";
		return content;
	}

	/**
	 * 提现失败通知
	 * 
	 * @param name
	 * @param createtime
	 * @param txje
	 * @param ye
	 * @return
	 */
	public static String userwallettxfail(String name, String createtime, Double txje, Double ye) {
		String content = "【" + SMSPRE + "】" + name + "老板，您的账户于" + createtime + "申请的提现RMB" + CommUtil.formatNumStr(txje)
				+ "元审核失败，现已归还到您的账户余额，当前您的账户余额为RMB" + CommUtil.formatNumStr(ye) + "元。请确认好资金安全。";
		return content;
	}

	/**
	 * 充值成功提醒
	 * 
	 * @param name
	 * @param str1
	 * @param czType
	 * @param ye
	 * @return
	 */
	public static String userwalletczsuccess(String name, String str1, String czType, String ye) {
		String content = "【" + SMSPRE + "】" + name + "土豪，您的账户于" + DateUtils.getCurrTimeStr("yyyy-MM-dd HH:mm") + "成功充值"
				+ str1 + "，当前" + czType + "余额为" + ye + "。";
		return content;
	}

	/**
	 * 注册成功提醒
	 * 
	 * @param name
	 * @return
	 */
	public static String userZcSuccess(String name) {
		// String content = "【" + SMSPRE + "】终于等到你，" + name
		// + "老板，热烈欢迎您登录天九共享网，来到这里说明您已注册，越早进行认证，越早收获优质服务哦^^。如遇操作问题，请联系专属服务秘书。";
		String content = "【" + SMSPRE + "】尊敬的" + name
				+ "，恭喜您已成为天九共享会尊贵的会员！点击此链接查看：天九共享会功能介绍及用户公约(<a style='color:blue;' href='http://t.cn/R9tLmMS'>http://t.cn/R9tLmMS</a>)。";
		return content;
	}

	/**
	 * 商洽
	 */
	/**
	 * 商洽被采纳
	 * 
	 * @param name
	 * @param question
	 * @param integer
	 * @return 【天九共享网】**老板，您在*****所发布的合作信息已被采纳，棒棒哒！您共计获得共计***J币，已经放入您的账户，
	 *         还请尽快登录查看，立即前往。
	 */
	public static String userRewardAccept(String name, String question, Integer integer) {
		String content = "【" + SMSPRE + "】" + name + "老板,您在" + question + "所发布的合作信息已被采纳，棒棒哒！您共计获得共计" + integer
				+ "J币，已经放入您的账户，还请尽快登录查看，立即前往。";
		return content;
	}

	/**
	 * 直播
	 */
	/**
	 * 报名成功
	 * 
	 * @param nickname
	 * @param createdate
	 * @param title
	 * @param mobile
	 * @return
	 * 
	 * 		【天九共享会】**老板，看这里！您于2016-10-32 19:00成功支付****直播。直播开始时间为：2016-10-32
	 *         19:00，届时记得收看！
	 */
	public static String liveSignupSuccess(String nickname, String createdate, String title, String startdate) {
		String content = "【" + SMSPRE + "】" + nickname + "老板，" + "您于" + createdate + "成功支付" + title + "直播，直播开始时间为："
				+ startdate + "，届时记得收看！";
		return content;
	}

	/**
	 * 直播开始提醒
	 * 
	 * @param nickname
	 * @param startdate
	 * @param title
	 * @param id
	 * @return
	 * 
	 * 		【天九共享会】**老板，您所支付的****直播于 2016-03-01
	 *         19:00正式开始，还有3分钟就开始了，精彩不容错过，收看直播请立即前往。
	 */
	public static String liveStartRemind(String nickname, String startdate, String title, String id) {
		// String content="【"+SMSPRE+"】尊敬的"+nickname+"，您所报名的"+title
		// +"直播于"+startdate+"正式开始，"+ "请您届时前往观看。<a href='"+ConfigUtil.DOMAIN
		// +"/wxfront/library/m/live/detail.do?id="+id+"'>立即前往</a>";
		String content = "【" + SMSPRE + "】" + nickname + "老板，您所支付的" + title + "直播于" + startdate
				+ "正式开始，还有3分钟就开始了，精彩不容错过，收看直播请<a href='" + ConfigUtil.DOMAIN + "/wxfront/library/m/live/detail.do?id="
				+ id + "'>立即前往</a>。";
		return content;
	}

	/**
	 * 悬赏
	 */
	/**
	 * 应答被采纳
	 * 
	 * @param name
	 * @param question
	 * @param integer
	 * @return
	 */
	public static String userzgRewardAccept(String name, String question, Integer integer) {
		String content = "【" + SMSPRE + "】" + name + "老板,您在" + question + "诸葛解惑内所发布的应答已被采纳，好厉害！您共计获得共计" + integer
				+ "J币，已经放入您的账户，还请尽快登录查看。";
		return content;
	}

	/**
	 * 互助宝升级
	 * 
	 * @Title: userhzbsj
	 * @Description: TODO
	 * @param name
	 * @param type
	 * @param money
	 * @param level
	 * @return
	 * @return: String
	 * @author: zengmin
	 * @date: 2017年7月27日 下午5:20:27
	 */
	public static String userhzbsj(String name, String type, String money, String level) {
		String content = "【" + SMSPRE + "】" + name + "老板，恭喜您！您的" + type + "累计充值金额已达到" + money + "等级提升为" + level
				+ "，为您开启互助共赢模式。";
		return content;
	}

	/**
	 * 互助宝到期
	 * 
	 * @Title: userhzbdq
	 * @Description: TODO
	 * @param name
	 * @return
	 * @return: String
	 * @author: zengmin
	 * @date: 2017年7月28日 上午9:10:12
	 */
	public static String userhzbdq(String name) {
		String content = "【" + SMSPRE + "】" + name + "老板，您的互助宝已到期，请您尽快前去验证。互助宝，为您开启互助共赢模式。";
		return content;
	}

	/**
	 * 互助宝降级
	 * 
	 * @Title: userhzbjj
	 * @Description: TODO
	 * @param name
	 * @param jj_money
	 * @param level
	 * @return
	 * @return: String
	 * @author: zengmin
	 * @date: 2017年7月28日 上午9:20:23
	 */
	public static String userhzbjj(String name, String jj_money, String level) {
		String content = "【" + SMSPRE + "】" + name + "老板，很抱歉，因为您的互助宝尚未达到年度承诺累计消费" + jj_money + "，所以您的互助宝等级降为" + level
				+ "。互助宝，为您开启互助共赢模式。";
		return content;
	}

	/**
	 * 互助宝开通
	 * 
	 * @Title: userhzbkt
	 * @Description: TODO
	 * @param name
	 * @param levelStr
	 * @return
	 * @return: String
	 * @author: zengmin
	 * @date: 2017年7月28日 上午9:41:21
	 */
	public static String userhzbkt(String name, String levelStr) {
		String content = "【" + SMSPRE + "】" + name + "老板，恭喜您！您已成功开通互助宝" + levelStr + "，更多充值，将尊享更多服务。互助宝，为您开启互助共赢模式。";
		return content;
	}

	/**
	 * 互助宝停用
	 * 
	 * @Title: userhzbty
	 * @Description: TODO
	 * @param name
	 * @param time
	 * @return
	 * @return: String
	 * @author: zengmin
	 * @date: 2017年7月28日 上午9:45:45
	 */
	public static String userhzbty(String name, String time) {
		String content = "【" + SMSPRE + "】" + name + "老板，非常遗憾，您的" + time + "被后台停用，请您尽快前去验证。互助宝，为您开启互助共赢模式。";
		return content;
	}

	public static String userhzbqy(String name, String time) {
		String content = "【" + SMSPRE + "】" + name + "老板，恭喜恭喜，您的" + time + "于后台被启用，请您尽快前去验证。互助宝，为您开启互助共赢模式。";
		return content;
	}

	/**
	 * 兑换券
	 * 
	 * @param name
	 * @param integer
	 * @return
	 */
	public static String receiveCoupon(String name, Integer integer, String cointype) {
		String content = "【" + SMSPRE + "】" + name + "老板,恭喜您获得" + integer + cointype + "兑换券，已兑换成功，" + integer + cointype
				+ "已存入钱包，请注意查收。";
		return content;
	}
}
