package com.wing.socialcontact.front.action;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.aliyun.mns.client.CloudQueue;
import com.aliyun.mns.client.MNSClient;
import com.aliyun.mns.model.Message;
import com.funny.ali.mns.MnsCloudCreater;
import com.funny.ali.mns.commons.enums.QueueAttributeEnum;
import com.funny.ali.mns.commons.message.MnsMessageManager;
import com.funny.ali.mns.commons.queue.MnsQueueManager;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.wing.socialcontact.common.action.BaseAction;
import com.wing.socialcontact.common.model.IpInfo;
import com.wing.socialcontact.common.model.Member;
import com.wing.socialcontact.config.MsgConfig;
import com.wing.socialcontact.front.util.ApplicationPath;
import com.wing.socialcontact.front.util.ConstantDefinition;
import com.wing.socialcontact.front.util.WxAutoReplyUtil;
import com.wing.socialcontact.service.wx.api.IIndexAdService;
import com.wing.socialcontact.service.wx.api.IInviteRecordService;
import com.wing.socialcontact.service.wx.api.IMessageInfoService;
import com.wing.socialcontact.service.wx.api.IRefundInstructionService;
import com.wing.socialcontact.service.wx.api.ISysBlacklistService;
import com.wing.socialcontact.service.wx.api.ISysconfigService;
import com.wing.socialcontact.service.wx.api.ITjyUserService;
import com.wing.socialcontact.service.wx.api.IUserIntegralLogService;
import com.wing.socialcontact.service.wx.api.IWxUserService;
import com.wing.socialcontact.service.wx.bean.IndexAd;
import com.wing.socialcontact.service.wx.bean.InviteRecord;
import com.wing.socialcontact.service.wx.bean.MessageInfo;
import com.wing.socialcontact.service.wx.bean.RefundInstruction;
import com.wing.socialcontact.service.wx.bean.Sysconfig;
import com.wing.socialcontact.service.wx.bean.TjyUser;
import com.wing.socialcontact.service.wx.bean.WxUser;
import com.wing.socialcontact.synchronization.TjySign;
import com.wing.socialcontact.sys.service.IDistrictService;
import com.wing.socialcontact.sys.service.IListValuesService;
import com.wing.socialcontact.util.AldyMessageUtil;
import com.wing.socialcontact.util.CommUtil;
import com.wing.socialcontact.util.Constants;
import com.wing.socialcontact.util.DateUtil;
import com.wing.socialcontact.util.EmojiFilterUtils;
import com.wing.socialcontact.util.HttpClientUtil;
import com.wing.socialcontact.util.IpUtil;
import com.wing.socialcontact.util.JsonUtil;
import com.wing.socialcontact.util.MD5Util;
import com.wing.socialcontact.util.MsmValidateBean;
import com.wing.socialcontact.util.RedisCache;
import com.wing.socialcontact.util.ServletUtil;
import com.wing.socialcontact.util.Sign;
import com.wing.socialcontact.util.StringUtil;
import com.wing.socialcontact.util.UUIDGenerator;
import com.wing.socialcontact.util.WeixinUtil;
import com.wing.socialcontact.util.im.IMUtil;
import com.wing.socialcontact.util.pojo.AccessToken;
import com.wing.socialcontact.util.wxres.AesException;
import com.wing.socialcontact.util.wxres.WXBizMsgCrypt;

import net.sf.json.JSONObject;
import sun.misc.BASE64Encoder;

/**
 * 对接微信企业号控制器
 * 
 * @ClassName: SysAction
 * @Description: TODO
 * @author: zengmin
 * @date:2017年4月2日 下午5:57:56
 */
@Controller
@RequestMapping("/m/sys")
public class SysAction extends BaseAction {

	@Autowired
	private IWxUserService wxUserService;
	@Autowired
	private IMessageInfoService messageInfoService;
	@Autowired
	private ITjyUserService tjyUserService;
	@Autowired
	private ISysconfigService sysconfigService;
	@Resource
	protected RedisCache redisCache;
	@Resource
	protected IUserIntegralLogService userIntegralLogService;
	@Autowired
	private IIndexAdService indexAdService;
	@Autowired
	private IInviteRecordService inviteRecordService;
	@Autowired
	private ISysBlacklistService sysBlacklistService;
	@Autowired
	private IListValuesService listValuesService;
	@Autowired
	private IRefundInstructionService refundInstructionService;
	@Autowired
	private IDistrictService districtService;

	private String imPrefix = ApplicationPath.getParameter(ConstantDefinition.IM_PREFIX);

	/**
	 * 微信url验证服务号
	 * 
	 * @Title: doget
	 * @Description: TODO
	 * @param msg_signature
	 * @param timestamp
	 * @param nonce
	 * @param echostr
	 * @param request
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws AesException
	 * @return: String
	 * @author: zengmin
	 * @date: 2017年4月7日 上午10:13:45
	 */
	@RequestMapping(value = "check", method = RequestMethod.GET)
	public @ResponseBody String doget(String signature, String timestamp, String nonce, String echostr,
			HttpServletRequest req) throws NoSuchAlgorithmException {
		String token = ApplicationPath.getParameter("wx_token");
		if (checkSignature(signature, timestamp, nonce, token)) {
			System.out.println(echostr);
			return echostr;
		}
		return "";
	}

	@RequestMapping(value = "check", method = RequestMethod.POST)
	public @ResponseBody String dopost(String msg_signature, String timestamp, String nonce, String echostr,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		System.out.println("------------------type check.do-------------------------");
		PrintWriter out = response.getWriter();

		StringBuffer buffer = new StringBuffer();
		InputStream inputStream = request.getInputStream();
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
		String inputstr = buffer.toString();
		System.out.println(inputstr);

		Document doc = null;
		SAXReader reader = new SAXReader();
		// InputStream in = request.getInputStream();
		try {
			doc = reader.read(new ByteArrayInputStream(inputstr.getBytes("UTF-8")));
			Element root = doc.getRootElement();
			String toUserName = root.element("ToUserName").getTextTrim();
			String fromUserName = root.element("FromUserName").getTextTrim();
			String type = root.element("MsgType").getTextTrim();
			String createTime = root.element("CreateTime").getTextTrim();
			String msgId = "";
			String content = "     ";
			System.out.println("------------------type " + type + "-fromUserName " + fromUserName + "-createTime "
					+ createTime + "-------------------------");
			if ("event".equals(type)) {
				String event = root.element("Event").getTextTrim();
				System.out.println("-------event:" + event + " ------");
				String eventKey = "";
				if (root.element("EventKey") != null) {
					eventKey = root.element("EventKey").getTextTrim();
				}
				if ("subscribe".equals(event) || "SCAN".equals(event)) {
					System.out
							.println("-------subscribe eventKey:" + eventKey + " fromUserName(openid):" + fromUserName);
					if (StringUtils.hasLength(fromUserName)) {
						String wx_secret = ApplicationPath.getParameter("wx_secret");
						String wx_appid = ApplicationPath.getParameter("wx_appid");
						AccessToken a = WeixinUtil.getAccessToken(wx_appid, wx_secret);
						WxUser wxUser = wxUserService.selectByOpenId(fromUserName);
						if (wxUser == null) {
							System.out.println("-------subscribe user is null-------");
							String idstr[] = eventKey.split("_");
							if (idstr.length > 1) {
								String yqUserId = idstr[1];
								if (StringUtils.hasLength(yqUserId)) {
									InviteRecord inviteRecord = new InviteRecord();
									inviteRecord.setByqOpenId(fromUserName);
									inviteRecord.setUserId(yqUserId);
									inviteRecord.setCreateTime(new Date());
									try {
										String url = "https://api.weixin.qq.com/cgi-bin/user/info?access_token="
												+ a.getToken() + "&openid=" + fromUserName + "&lang=zh_CN";
										String rs = HttpClientUtil.sendGetRequest(url, null);
										System.out.println("关注获取用户信息：" + rs);
										Map<String, Object> rsmap = JsonUtil.parseJSON2Map(rs);
										if (null != rsmap) {
											String nickname = EmojiFilterUtils
													.filterEmoji((String) rsmap.get("nickname"));
											inviteRecord.setByqNickName(nickname);
										}
									} catch (Exception e) {
										e.printStackTrace();
										System.out.println("关注获取用户信息失败");
									}
									String bo = inviteRecordService.addInviteRecord(inviteRecord);
									if (bo.equals(MsgConfig.MSG_KEY_SUCCESS)) {
										// 首次登录 送积分 经验值
										// /
										// userIntegralLogService.addLntAndEmp(yqUserId,
										// "task_0022");
										System.out.println(" insert into inviteRecord success...");
									}
								}
							}
						} else {
							System.out.println("user existed ...");
						}
						// 获取【欢迎关注天九共享会】图文消息素材
						String mediaId = WxAutoReplyUtil.TEST_WXOFFICIAL_MEDIA_ID;
						System.out.println("【获取的token】：" + a.getToken());
						String getSigleMaterialUrl = "https://api.weixin.qq.com/cgi-bin/material/get_material?access_token="
								+ a.getToken();
						try {
							JSONObject jsonObject = WeixinUtil.httpRequest(getSigleMaterialUrl, "POST",
									"{\"media_id\": \"" + mediaId + "\"}");
							System.out.println("【获取素材结果：】" + jsonObject);

							JSONObject item = jsonObject.getJSONArray("news_item").getJSONObject(0);
							out.printf(WxAutoReplyUtil.RESPONSE_NEWS, fromUserName, toUserName,
									System.currentTimeMillis(), item.getString("title"), item.getString("digest"),
									item.getString("thumb_url"), item.getString("url"));
						} catch (Exception e) {
							RefundInstruction dto = refundInstructionService.getrefundinstructionByid(4);
							String retstr = dto.getContent().replaceAll("\r\n", "\n");
							out.printf(WxAutoReplyUtil.RESPONSE_TXT, fromUserName, toUserName,
									System.currentTimeMillis(), "text", retstr);
							e.printStackTrace();
						}
					}
				} else if ("unsubscribe".equals(event)) {// 取消关注

				}
			} else {

			}
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 微信url验证企业号
	 * 
	 * @Title: check_qyh
	 * @Description: TODO
	 * @param msg_signature
	 * @param timestamp
	 * @param nonce
	 * @param echostr
	 * @param request
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws AesException
	 * @return: String
	 * @author: zengmin
	 * @date: 2017年4月7日 上午10:13:45
	 */
	@RequestMapping(value = "check_qyh", method = RequestMethod.GET)
	public @ResponseBody String check_qyh(String msg_signature, String timestamp, String nonce, String echostr,
			HttpServletRequest request) throws NoSuchAlgorithmException, AesException {
		System.out.println("msg_signature=" + msg_signature + " timestamp=" + timestamp + " nonce=" + nonce
				+ " echostr=" + echostr);
		String wx_token_qyh = ApplicationPath.getParameter("wx_token_qyh");
		String wx_aeskey_qyh = ApplicationPath.getParameter("wx_aeskey_qyh");
		String wx_corpid_qyh = ApplicationPath.getParameter("wx_corpid_qyh");
		WXBizMsgCrypt wxcpt = new WXBizMsgCrypt(wx_token_qyh, wx_aeskey_qyh, wx_corpid_qyh);
		String sVerifyMsgSig = msg_signature;
		String sVerifyTimeStamp = timestamp;
		String sVerifyNonce = nonce;
		String sVerifyEchoStr = echostr;
		String sEchoStr; // 需要返回的明文
		try {
			sEchoStr = wxcpt.VerifyURL(sVerifyMsgSig, sVerifyTimeStamp, sVerifyNonce, sVerifyEchoStr);
			return sEchoStr;
		} catch (Exception e) {
			// 验证URL失败，错误原因请查看异常
			e.printStackTrace();
		}
		return "";
	}

	@RequestMapping(value = "check_qyh", method = RequestMethod.POST)
	public @ResponseBody String check_qyh(String msg_signature, String timestamp, String nonce, String echostr,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		Document doc = null;
		String wx_token_qyh = ApplicationPath.getParameter("wx_token_qyh");
		String wx_aeskey_qyh = ApplicationPath.getParameter("wx_aeskey_qyh");
		String wx_corpid_qyh = ApplicationPath.getParameter("wx_corpid_qyh");
		WXBizMsgCrypt wxcpt = new WXBizMsgCrypt(wx_token_qyh, wx_aeskey_qyh, wx_corpid_qyh);

		SAXReader reader = new SAXReader();
		InputStream in = request.getInputStream();
		String sReqData = inputStream2String(in);
		String sMsg = wxcpt.DecryptMsg(msg_signature, timestamp, nonce, sReqData);
		try {
			doc = reader.read(new StringReader(sMsg));
			Element root = doc.getRootElement();
			String fromUserName = root.element("FromUserName").getTextTrim();
			String type = root.element("MsgType").getTextTrim();
			// String toUserName = root.element("ToUserName").getTextTrim();
			// String createTime = root.element("CreateTime").getTextTrim();
			// String msgId = "";
			// String content = "";
			if ("event".equals(type)) {
				String event = root.element("Event").getTextTrim();
				System.out.println(" FromUserName:" + fromUserName + " type:" + type + " event:" + event);
				if ("subscribe".equals(event)) {// 关注
					if (StringUtils.hasLength(fromUserName)) {
						WxUser wxUser = wxUserService.selectByWxUserId(fromUserName);
						if (null == wxUser) {
							wxUser = wxUserService.selectByPrimaryKey(fromUserName);
						}
						if (null != wxUser) {
							TjyUser tjyUser = tjyUserService.selectByPrimaryKey(wxUser.getId() + "");
							if (null != tjyUser) {
								tjyUser.setStatus(2);
								tjyUserService.updateTjyUser(tjyUser);
							}
						}
					}
				} else if ("unsubscribe".equals(event)) {// 取消关注
					if (StringUtils.hasLength(fromUserName)) {
						WxUser wxUser = wxUserService.selectByWxUserId(fromUserName);
						if (null == wxUser) {
							wxUser = wxUserService.selectByPrimaryKey(fromUserName);
						}
						if (null != wxUser) {
							TjyUser tjyUser = tjyUserService.selectByPrimaryKey(wxUser.getId() + "");
							if (null != tjyUser) {
								tjyUser.setStatus(1);
								tjyUserService.updateTjyUser(tjyUser);
							}
						}
					}
				}
			}
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		return null;

	}

	private static boolean checkSignature(String signature, String timestamp, String nonce, String token) {
		if (StringUtils.isEmpty(token) || StringUtils.isEmpty(timestamp) || StringUtils.isEmpty(nonce)) {
			return false;
		}
		String[] arr = new String[] { token, timestamp, nonce };
		Arrays.sort(arr);
		StringBuilder content = new StringBuilder();
		for (int i = 0; i < arr.length; i++) {
			content.append(arr[i]);
		}
		MessageDigest md = null;
		String tmpStr = null;

		try {
			md = MessageDigest.getInstance("SHA-1");
			byte[] digest = md.digest(content.toString().getBytes());
			tmpStr = byteToStr(digest);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

		content = null;
		return tmpStr != null ? tmpStr.equals(signature.toUpperCase()) : false;
	}

	// 将字节转换为十六进制字符串
	private static String byteToHexStr(byte ib) {
		char[] Digit = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
		char[] ob = new char[2];
		ob[0] = Digit[(ib >>> 4) & 0X0F];
		ob[1] = Digit[ib & 0X0F];

		String s = new String(ob);
		return s;
	}

	// 将字节数组转换为十六进制字符串
	private static String byteToStr(byte[] bytearray) {
		String strDigest = "";
		for (int i = 0; i < bytearray.length; i++) {
			strDigest += byteToHexStr(bytearray[i]);
		}
		return strDigest;
	}

	public static String inputStream2String(InputStream in) throws IOException {
		StringBuffer out = new StringBuffer();
		byte[] b = new byte[4096];
		for (int n; (n = in.read(b)) != -1;) {
			out.append(new String(b, 0, n));
		}
		return out.toString();
	}

	/**
	 * 企业号登录
	 * 
	 * @Title: mlogin
	 * @Description: TODO
	 * @param appno
	 * @param request
	 * @param code
	 * @param state
	 * @return
	 * @throws UnsupportedEncodingException
	 * @return: String
	 * @author: zengmin
	 * @date: 2017年4月7日 上午10:13:55
	 */
	@RequestMapping(value = "mlogin", method = RequestMethod.GET)
	public String mlogin(HttpServletRequest request, String code, String state) throws UnsupportedEncodingException {

		String contentPath = ApplicationPath.getParameter("domain");
		String path = request.getContextPath();
		String cp = contentPath + path;
		if (ServletUtil.isLogin(request)) {
			// 检测用户是否已在黑名单中
			boolean isBlack = sysBlacklistService.selectSysBlacklistByUserId(ServletUtil.getMember(request).getId());
			if (isBlack) {
				request.getSession().removeAttribute("me");
				return "redirect:" + cp + "/m/sys/blackPage.do";
			}
			state = encodeURL(state);
			if (state.indexOf("loginPage.do") != -1) {
				return "redirect:" + cp + "/m/sys/index.do";
				// return "redirect:index.do";
			}
			return "redirect:" + state;
		}

		String wx_secret = ApplicationPath.getParameter("wx_secret");
		String wx_appid = ApplicationPath.getParameter("wx_appid");

		System.out.println("++++corpid=" + wx_appid + " secret=" + wx_secret + " domain:" + request.getServerName());
		System.out.println("++++code=" + code + " state=" + state);
		HttpSession session = request.getSession(false);
		String woid = (String) session.getAttribute("wxopenid");
		String wxopenid = "";
		String access_token = "";
		String url = "";
		String rs = "";
		Map<String, Object> rsmap = null;
		if (!StringUtils.isEmpty(code)) {
			url = "https://api.weixin.qq.com/sns/oauth2/access_token?" + "appid=" + wx_appid + "&secret=" + wx_secret
					+ "&code=" + code + "&grant_type=authorization_code";
			System.out.println("url：" + url);
			rs = HttpClientUtil.sendGetRequest(url, null);
			rsmap = JsonUtil.parseJSON2Map(rs);
			wxopenid = (String) rsmap.get("openid");
			access_token = (String) rsmap.get("access_token");
		}
		System.out.println("openid---：" + wxopenid);
		System.out.println("access_token---：" + access_token);
		state = encodeURL(state, request);
		System.out.println("-----" + state);
		if (StringUtils.isEmpty(wxopenid) || StringUtils.isEmpty(access_token)) {
			return "error";
		} else {
			session.setAttribute("wxopenid", wxopenid);
			session.setAttribute("access_token", access_token);
		}
		WxUser u = wxUserService.selectByOpenId(wxopenid);
		if (u != null) {
			// 检测用户是否已在黑名单中
			boolean isBlack = sysBlacklistService.selectSysBlacklistByUserId(u.getId() + "");
			if (isBlack) {
				request.getSession().removeAttribute("me");
				return "redirect:" + cp + "/m/sys/blackPage.do";
			}
			// 用户关注上来首次登陆
			url = "https://api.weixin.qq.com/sns/userinfo?access_token=" + access_token + "&openid=" + wxopenid
					+ "&lang=zh_CN";
			rs = HttpClientUtil.sendGetRequest(url, null);
			System.out.println("rs：" + rs);
			rsmap = JsonUtil.parseJSON2Map(rs);
			if (null != rsmap) {
				String headimgurl = (String) rsmap.get("headimgurl");
				if (StringUtils.isEmpty(headimgurl) || "/0".equals(headimgurl)) {
					headimgurl = cp + "/resource/img/icons/weixinHeader.jpg";
				}
				String nickname = EmojiFilterUtils.filterEmoji((String) rsmap.get("nickname"));
				if (StringUtils.isEmpty(u.getImgUrl()) || "/0".equals(u.getImgUrl())) {
					u.setImgUrl(headimgurl);
				}
				if (StringUtils.isEmpty(u.getNickName())) {
					u.setNickName(nickname);
				}
				Long sex = (Long) rsmap.get("sex");
				u.setSex(Integer.valueOf(sex + ""));
				wxUserService.updateWxUser(u);
			}
			TjyUser tjyUser = tjyUserService.selectByPrimaryKey(u.getId() + "");
			if (null != tjyUser) {
				if (StringUtils.isEmpty(tjyUser.getNickname())) {
					tjyUser.setNickname(u.getNickName());
				}
				tjyUser.setHeadPortrait(u.getImgUrl());
				tjyUser.setOpenId(wxopenid);
				tjyUserService.updateTjyUser(tjyUser);
			} else {
				tjyUser = new TjyUser();
				tjyUser.setId(u.getId() + "");
				tjyUser.setNickname(u.getNickName());
				tjyUser.setStatus(1);
				tjyUser.setHeadPortrait(u.getImgUrl());
				tjyUser.setOpenId(wxopenid);
				tjyUser.setMobile(u.getMobile());
				tjyUser.setLastVisitDate(new Date());
				tjyUser.setFirstBindTime(new Date());
				tjyUserService.addTjyUser(tjyUser);
			}
			String loginIp = IpUtil.getIpAddr(request);
			IpInfo ipInfo = IpUtil.getIpInfo(loginIp);
			Member me = new Member();// 需要放入当前session 的用户信息
			me.setId(u.getId() + "");
			me.setIpInfo(ipInfo);
			me.setLoginTime(DateUtil.currentTimestamp());
			if (null != tjyUser) {
				me.setIsRealname(tjyUser.getIsRealname() + "");
				me.setOpenId(wxopenid);
				me.setKfTelephone(tjyUser.getKfTelephone());
			}
			int loginCount = null == u.getLogincount() ? 0 : u.getLogincount();
			u.setLogincount(loginCount + 1);
			u.setLastlogindate(new Date());
			wxUserService.updateWxUser(u);
			// 首次登录发送短信提醒
			if (u.getLogincount() == 1) {
				// 首次登录 送积分 经验值
				userIntegralLogService.addLntAndEmp(tjyUser.getId(), "task_0001");
				// 如果是被邀请的，要给被邀请人送积分
				if (!StringUtils.isEmpty(wxopenid)) {
					List<Map<String, Object>> irList = inviteRecordService.selectInviteRecordPageByOpenId(wxopenid);
					if (null != irList && irList.size() > 0) {
						for (Map<String, Object> m : irList) {
							String userId = (String) m.get("userId");
							// 邀请新用户注册:task_0022
							if (!StringUtils.isEmpty(userId)) {
								userIntegralLogService.addLntAndEmp(userId, "task_0022");
							}
						}
					}
				}
				MessageInfo messageInfo = new MessageInfo();
				messageInfo.setId(UUIDGenerator.getUUID());
				messageInfo.setDeleted(0);
				messageInfo.setMobile(u.getMobile());
				messageInfo.setType(3);// 系统
				messageInfo.setCreateTime(new Date());
				messageInfo.setToUserId(tjyUser.getId());
				String name = tjyUser.getNickname();
				messageInfo.setContent(AldyMessageUtil.userZcSuccess(name));
				messageInfo.setStatus(0);
				messageInfoService.addMessageInfo(messageInfo);
			}
			request.getSession().setAttribute("me", me);
			request.getSession().setAttribute(Constants.SESSION_WXUSER_ID, me.getId() + "");
			request.getSession().setAttribute(Constants.SESSION_WXUSER_NICKNAME, u.getNickName());
			request.getSession().setAttribute(Constants.SESSION_WXUSER_HDPIC, u.getImgUrl());
			state = encodeURL(state);
			// 通知IM
			WxUser unew = wxUserService.selectByOpenId(wxopenid);
			String res = IMUtil.updateUserOne(imPrefix + unew.getId() + "", unew.getNickName(), unew.getImgUrl());
			JSONObject jsonObject = JSONObject.fromObject(res);
			if (!"200".equals(jsonObject.getString("code"))) {
				IMUtil.sendUser(imPrefix + tjyUser.getId(), "", tjyUser.getNickname(), "");
			}
			return "redirect:" + state;
		}
		// return "redirect:guidePage.do";
		return "redirect:" + cp + "/m/sys/guidePage.do?last_url="+state;
	}

	/**
	 * 获取签名
	 * 
	 * @Title: getSignature
	 * @Description: TODO
	 * @param url
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 * @return: Map
	 * @author: zengmin
	 * @date: 2017年4月7日 上午10:14:14
	 */
	@RequestMapping(value = "getSignature", method = RequestMethod.POST)
	public @ResponseBody Map getSignature(String url, HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		/*
		 * Member me = ServletUtil.getMember(request); if (null == me) { return
		 * super.getAjaxResult("302", "登录超时，请重新登录", null); } String userId = me.getId();
		 * if (StringUtils.isEmpty(userId)) { return super.getAjaxResult("302",
		 * "登录超时，请重新登录", null); }
		 */

		if (StringUtils.isEmpty(url)) {
			return super.getAjaxResult("999", "参数错误", null);
		}
		String wx_secret = ApplicationPath.getParameter("wx_secret");
		String wx_appid = ApplicationPath.getParameter("wx_appid");
		if (StringUtils.isEmpty(wx_appid)) {
			return super.getAjaxResult("999", "获取corpid失败，请在手机上登录", null);
		}
		AccessToken a = WeixinUtil.getAccessToken(wx_appid, wx_secret);
		Map<String, String> map = null;
		if (null != a && !StringUtils.isEmpty(a.getJsapi_ticket())) {
			map = Sign.sign(a.getJsapi_ticket(), url);
		}
		map.put("appId", wx_appid);
		return super.getSuccessAjaxResult("获取成功", map);
	}

	/**
	 * 获取微信基本配置信息
	 * 
	 * @Title: getWxConfig
	 * @Description: TODO
	 * @param url
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 * @return: Map
	 * @author: zengmin
	 * @date: 2017年4月7日 上午10:14:22
	 */
	@RequestMapping(value = "getWxConfig", method = RequestMethod.POST)
	public @ResponseBody Map getWxConfig(String url, HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		String wx_corpid_qyh = ApplicationPath.getParameter("wx_corpid_qyh");
		String wx_token_qyh = ApplicationPath.getParameter("wx_token_qyh");
		String wx_aeskey_qyh = ApplicationPath.getParameter("wx_aeskey_qyh");
		String wx_secret_qyh = ApplicationPath.getParameter("wx_secret_qyh");
		String wx_appno_qyh = ApplicationPath.getParameter("wx_appno_qyh");
		String wx_appid = ApplicationPath.getParameter("wx_appid");
		String wx_token = ApplicationPath.getParameter("wx_token");
		String wx_aeskey = ApplicationPath.getParameter("wx_aeskey");
		String wx_secret = ApplicationPath.getParameter("wx_secret");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("wx_corpid_qyh", wx_corpid_qyh);
		map.put("wx_token_qyh", wx_token_qyh);
		map.put("wx_aeskey_qyh", wx_aeskey_qyh);
		map.put("wx_secret_qyh", wx_secret_qyh);
		map.put("wx_appno_qyh", wx_appno_qyh);
		map.put("wx_appid", wx_appid);
		map.put("wx_token", wx_token);
		map.put("wx_aeskey", wx_aeskey);
		map.put("wx_secret", wx_secret);
		return super.getSuccessAjaxResult("获取成功", map);
	}

	/**
	 * 解析URL
	 * 
	 * @Title: encodeURL
	 * @Description: TODO
	 * @param url
	 * @return
	 * @throws UnsupportedEncodingException
	 * @return: String
	 * @author: zengmin
	 * @date: 2017年4月7日 上午10:14:36
	 */
	private String encodeURL(String url) throws UnsupportedEncodingException {
		StringBuffer result = null;
		if (!StringUtils.isEmpty(url)) {
			url = url.replaceAll("@@2@@", "&");
			result = new StringBuffer();
			for (int i = 0; i < url.length(); i++) {
				if ((url.charAt(i) + "").getBytes().length > 1) {
					result.append(URLEncoder.encode(url.charAt(i) + "", "UTF-8"));
				} else {
					result.append(url.charAt(i));
				}
			}
			return result.toString();
		} else {
			return null;
		}
	}

	/**
	 * 跳转到新手引导页面
	 * 
	 * @Title: regPage
	 * @Description: TODO
	 * @return
	 * @return: String
	 * @author: zengmin
	 * @date: 2017年4月7日 上午10:15:01
	 */
	@RequestMapping("guidePage")
	public String guidePage(HttpServletRequest request, ModelMap modelMap) {
		String lastUrl = request.getParameter("last_url");
		modelMap.addAttribute("last_url", lastUrl);
		return "register/guide";

	}

	/**
	 * 跳转到注册页面
	 * 
	 * @Title: regPage
	 * @Description: TODO
	 * @return
	 * @return: String
	 * @author: zengmin
	 * @date: 2017年4月7日 上午10:15:01
	 */
	@RequestMapping("regPage")
	public String regPage(HttpServletRequest request, ModelMap modelMap) {

		String lastUrl = request.getParameter("last_url");
		modelMap.addAttribute("last_url", lastUrl);
		return "register/register";
	}

	/**
	 * 跳转到关注引导页面
	 * 
	 * @Title: regPage
	 * @Description: TODO
	 * @return
	 * @return: String
	 * @author: zengmin
	 * @date: 2017年4月7日 上午10:15:01
	 */
	@RequestMapping("subscribePage")
	public String subscribePage() {
		return "subscribe";

	}

	/**
	 * 跳转到黑名单页面
	 * 
	 * @Title: guidePage
	 * @Description: TODO
	 * @param request
	 * @param modelMap
	 * @return
	 * @return: String
	 * @author: zengmin
	 * @date: 2017年8月8日 上午11:24:22
	 */
	@RequestMapping("blackPage")
	public String blackPage(HttpServletRequest request, ModelMap modelMap) {
		List<Map<String, Object>> listValues = listValuesService.selectListByType(999);
		if (null != listValues && !listValues.isEmpty()) {
			modelMap.put("yqConfig", listValues.get(0));
		}
		return "black";
	}

	/**
	 * 注册保存
	 * 
	 * @Title: regSave
	 * @Description: TODO
	 * @param mobile
	 * @param yz
	 * @param dyz
	 * @param pwd
	 * @param session
	 * @return
	 * @return: Map
	 * @author: zengmin
	 * @date: 2017年4月7日 上午10:15:18
	 */
	@RequestMapping("regSave")
	public @ResponseBody Map regSave(HttpServletRequest request, String mobile, String yz, String dyz, String pwd,
			String nickName, HttpSession session) {
		String contentPath = ApplicationPath.getParameter("domain");
		String path = request.getContextPath();
		String cp = contentPath + path;
		boolean bo = MsmValidateBean.validateCode(mobile, dyz, session);
		if (!bo)
			return super.getAjaxResult("999", "手机验证码错误或已失效", null);
		String wxopenid = (String) session.getAttribute("wxopenid");
		String access_token = (String) session.getAttribute("access_token");
		if (StringUtils.isEmpty(wxopenid) || StringUtils.isEmpty(access_token))
			return super.getAjaxResult("407", "参数错误[wxopenid_or_access_token]", null);

		// 用户关注上来首次登陆
		String url = "https://api.weixin.qq.com/sns/userinfo?access_token=" + access_token + "&openid=" + wxopenid
				+ "&lang=zh_CN";
		String rs = HttpClientUtil.sendGetRequest(url, null);
		Map<String, Object> rsmap = JsonUtil.parseJSON2Map(rs);
		String imgUrl = "";
		String nickname = "";
		String sex = "";
		if (null != rsmap) {
			imgUrl = (String) rsmap.get("headimgurl");
			if (StringUtils.isEmpty(imgUrl) || "/0".equals(imgUrl))
				imgUrl = cp + "/resource/img/icons/weixinHeader.jpg";
			nickname = EmojiFilterUtils.filterEmoji((String) rsmap.get("nickname"));
			sex = rsmap.get("sex") + "";
			wxopenid = (String) rsmap.get("openid");
		}
		System.out.println(" ----------    begin ---------------------- ");
		WxUser o_wxUser = wxUserService.selectByMobile(mobile);
		if (null != o_wxUser) {
			String openId = o_wxUser.getQqOpenid();
			if (!StringUtils.isEmpty(openId)) {
				return super.getAjaxResult("999", "该手机号已注册", null);
			}
			o_wxUser.setQqOpenid(wxopenid);
			try {
				TjyUser tjyUser = tjyUserService.selectByPrimaryKey(o_wxUser.getId() + "");
				if (null == tjyUser) {
					tjyUser = new TjyUser();
					tjyUser.setId(o_wxUser.getId() + "");
					tjyUser.setMallUser(o_wxUser.getId());
					tjyUser.setNickname(o_wxUser.getNickName());
					tjyUser.setIsRealname(0);
					tjyUser.setStatus(1);
					tjyUser.setIsdk(0);
					tjyUser.setIsdisturb(0);
					tjyUser.setMobile(o_wxUser.getMobile());
					tjyUser.setHeadPortrait(o_wxUser.getImgUrl());
					tjyUser.setOpenId(wxopenid);
					tjyUser.setFirstBindTime(new Date());
					tjyUser.setReconStatus(0);
					tjyUserService.addTjyUser(tjyUser);
				} else {
					tjyUser.setNickname(o_wxUser.getNickName());
					tjyUser.setHeadPortrait(o_wxUser.getImgUrl());
					tjyUser.setOpenId(wxopenid);
					tjyUser.setFirstBindTime(new Date());
					tjyUserService.updateTjyUser(tjyUser);
				}
				/* 已改用mns方式更新用户 */
				/*Map usermap = tjyUserService.remotingGetUser(mobile);
				if (usermap != null) {
					System.out.println(" ----------   tjyUserService.remotingGetUser(mobile)  ---------------------- ");
					System.out.println(usermap.toString());
					tjyUser.setOpenId(wxopenid);
					tjyUser.setReconMobile(mobile);
					setRemoteValue(o_wxUser, tjyUser, usermap);
					tjyUserService.updateTjyUser(tjyUser);
				} else {*/
				System.out.println(" ----------    null != o_wxUser   ---------------------- ");
				// 如果是微信端创建的用户
				System.out.println(
						" ----------    StringUtils.isEmpty(openId) || !wxopenid.equals(openId)  ---------------------- ");
				o_wxUser.setQqOpenid(wxopenid);
				o_wxUser.setUsername(mobile);
				if (StringUtils.isEmpty(o_wxUser.getImgUrl()) || "/0".equals(o_wxUser.getImgUrl()))
					o_wxUser.setImgUrl(imgUrl);
				if (StringUtils.isEmpty(o_wxUser.getNickName()))
					o_wxUser.setNickName(nickname);
				if (StringUtils.hasLength(sex))
					o_wxUser.setSex(Integer.valueOf(sex));
				wxUserService.updateWxUser(o_wxUser);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			System.out.println(" ----------   null == o_wxUser  ---------------------- ");
			o_wxUser = new WxUser();
			o_wxUser.setMobile(mobile);
			if (StringUtils.hasLength(pwd))
				o_wxUser.setPassword(MD5Util.md5Hex(pwd));
			o_wxUser.setDeletestatus(false);
			o_wxUser.setAddtime(new Date());
			o_wxUser.setQqOpenid(wxopenid);
			o_wxUser.setUsername(mobile);
			o_wxUser.setUsertype((byte) 1);
			o_wxUser.setNickName(nickname);
			o_wxUser.setImgUrl(imgUrl);
			if (StringUtils.hasLength(sex))
				o_wxUser.setSex(Integer.valueOf(sex));
			session.removeAttribute("imgCode");
			TjyUser tjyUser = new TjyUser();
			try {
				/* 没有用户仍需到中间库同步拉去 */
				Map usermap = tjyUserService.remotingGetUser(mobile);
				if (usermap != null) {
					System.out.println(" ----------   tjyUserService.remotingGetUser(mobile)  ---------------------- ");
					System.out.println(usermap.toString());
					tjyUser.setReconMobile(mobile);
					setRemoteValue(o_wxUser, tjyUser, usermap);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

			System.out.println("----------  begin to addwxuser  -----------------");
			System.out.println("===================================wxUser sex:" + o_wxUser.getSex());
			wxUserService.addWxUser(o_wxUser, tjyUser);
			System.out.println("imPrefix=" + imPrefix);
		}
		o_wxUser = wxUserService.selectByMobile(mobile);
		TjyUser tuser = tjyUserService.selectByPrimaryKey(o_wxUser.getId() + "");
		IMUtil.sendUser(imPrefix + tuser.getId(), UUID.randomUUID().toString() + tuser.getId(), "", "");
		IMUtil.updateUserOne(imPrefix + tuser.getId(), tuser.getNickname(), tuser.getHeadPortrait());
		session.removeAttribute("imgCode");
		return super.getSuccessAjaxResult("注册成功");
	}

	private void setRemoteValue(WxUser wxUser, TjyUser tjyUser, Map usermap) {
		try {
			// 调用远程数据库，获取用户信息
			String customerName = (String) usermap.get("customerName");
			if (!StringUtil.isEmpty(customerName)) {
				wxUser.setTruename(customerName);
				tjyUser.setNickname(customerName);
				tjyUser.setTrueName(customerName);
				// if (!customerName.equals(wxUser.getUsername()))
				// {
				wxUser.setUsername(customerName);
				wxUser.setNickName(customerName);
				// wxUser.setTruename(customerName);
				// wxUser.setNickName(customerName);
				// wxUserService.updateWxUser(wxUser);
				// }
			}
			String sex1 = (String) usermap.get("sexId");
			if (!StringUtil.isEmpty(sex1)) {
				if ("女".equals(sex1))
					wxUser.setSex(Integer.valueOf("2"));
				else
					wxUser.setSex(Integer.valueOf("1"));
				// if (wxUser.getSex() != null && sexstr !=
				// wuser.getSex())
				// wxUserService.updateWxUser(wuser);
			}
			String activeDate = (String) usermap.get("customerActivationDate");
			if (!StringUtil.isEmpty(activeDate))
				tjyUser.setReconDate(new Date(Long.parseLong(activeDate)));
			String customerCompany = (String) usermap.get("customerCompany");
			if (!StringUtil.isEmpty(customerCompany))
				tjyUser.setComName(customerCompany.split(",")[0]);
			String customerTitle = (String) usermap.get("customerTitle");
			if (!StringUtil.isEmpty(customerTitle)) {
				List<Map<String, Object>> listValues = listValuesService.selectListByType(12, customerTitle);
				if (null != listValues && !listValues.isEmpty()) {
					Map lv = (Map) listValues.get(0);
					tjyUser.setJob((String) lv.get("id"));
				}
			}
			String customerUnitIndustry = (String) usermap.get("customerUnitIndustry");
			if (!StringUtil.isEmpty(customerUnitIndustry)) {
				List<Map<String, Object>> listValues = listValuesService.selectListByType(8001, customerUnitIndustry);
				if (null != listValues && !listValues.isEmpty()) {
					Map lv = (Map) listValues.get(0);
					tjyUser.setIndustry((String) lv.get("id"));
				} else {
					tjyUser.setIndustry("cf1b1378048c4c9a90298c847ad594ba");
				}
			}
			String customerType = (String) usermap.get("customerType");
			if (!StringUtil.isEmpty(customerType)) {
				// if ("普通客户".equals(customerType))
				// {
				// tjyUser.setReconStatus(1);
				// }
				// else
				// if ("成交客户".equals(customerType) ||
				// "认证客户".equals(customerType))
				// {
				// tjyUser.setReconStatus(2);
				// Date reconDate = new Date();
				// tjyUser.setReconDate(reconDate);
				// tjyUser.setIsRealname(1);
				// // Calendar now = Calendar.getInstance();
				// // now.add(Calendar.YEAR, 1);
				// // tjyUser.setLastRegDate(now.getTime());
				// }
				// else
				if ("问题客户".equals(customerType)) {
					tjyUser.setIsRealname(0);
					tjyUser.setReconStatus(3);
				} else {
					tjyUser.setIsRealname(1);
					tjyUser.setReconStatus(2);
				}
			}

			String customerCheckCapital = (String) usermap.get("customerCheckCapital");
			if (!StringUtil.isEmpty(customerCheckCapital)) {
				// 注册资本
				Pattern p = Pattern.compile("[0-9\\.]+");
				Matcher m = p.matcher(customerCheckCapital);
				if (m.find()) {
					customerCheckCapital = m.group();
					if (!StringUtil.isEmpty(customerCheckCapital))
						tjyUser.setReconCapital(CommUtil.null2Double(customerCheckCapital));
				}
			}

			// String customerActivationDate = (String)
			// usermap.get("customerActivationDate");
			// if (!StringUtil.isEmpty(customerActivationDate))
			// {
			// long d = CommUtil.null2Long(customerActivationDate);
			// tjyUser.setReconDate(new Date(d));
			// }
			String servantNumber = (String) usermap.get("servantNumber");
			if (!StringUtil.isEmpty(servantNumber))
				tjyUser.setKfTelephone(servantNumber);
			String customerEndDate = (String) usermap.get("customerEndDate");
			if (!StringUtil.isEmpty(customerEndDate)) {
				long d = CommUtil.null2Long(customerEndDate);
				tjyUser.setLastRegDate(new Date(d));
			}

			String customerEffectiveLevel = (String) usermap.get("customerCurrentMaxShareLevel");
			if (!StringUtil.isEmpty(customerEffectiveLevel)) {
				if ("天九家人".equals(customerEffectiveLevel)) {
					tjyUser.setHonorTitle("家人");
					tjyUser.setHonorFlag("honor_001");
				} else if ("天九云亲".equals(customerEffectiveLevel)) {
					tjyUser.setHonorTitle("云亲");
					tjyUser.setHonorFlag("honor_002");
				} else if ("天九伙伴".equals(customerEffectiveLevel)) {
					tjyUser.setHonorTitle("伙伴");
					tjyUser.setHonorFlag("honor_003");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * PC端登录
	 * 
	 * @Title: login
	 * @Description: TODO
	 * @param userName
	 * @param pwd
	 * @param request
	 * @return
	 * @return: Map
	 * @author: zengmin
	 * @date: 2017年4月7日 上午10:15:28
	 */
	@RequestMapping("login")
	public @ResponseBody Map login(String userName, String pwd, HttpServletRequest request) {
		if (StringUtils.isEmpty(userName) || StringUtils.isEmpty(pwd)) {
			return super.getAjaxResult("999", "参数错误", null);
		}
		WxUser wxUser = wxUserService.selectByUserName(userName);
		if (null == wxUser) {
			wxUser = wxUserService.selectByMobile(userName);
			if (null == wxUser) {
				return super.getAjaxResult("999", "用户不存在", null);
			}
		}
		// 检测用户是否已在黑名单中
		boolean isBlack = sysBlacklistService.selectSysBlacklistByUserId(wxUser.getId() + "");
		if (isBlack) {
			return super.getAjaxResult("700", "黑名单", null);
		}
		if (!MD5Util.md5Hex(pwd).equals(wxUser.getPassword())) {
			return super.getAjaxResult("999", "用户名密码错误", null);
		}
		String loginIp = IpUtil.getIpAddr(request);
		IpInfo ipInfo = IpUtil.getIpInfo(loginIp);

		Member me = new Member();// 需要放入当前session 的用户信息
		me.setId(wxUser.getId() + "");
		me.setIpInfo(ipInfo);
		me.setLoginTime(DateUtil.currentTimestamp());
		me.setUserName(wxUser.getUsername());
		me.setWxUserId(wxUser.getWxUserId());
		TjyUser tjyUser = tjyUserService.selectById(me.getId());
		if (null != tjyUser) {
			me.setIsRealname(tjyUser.getIsRealname() + "");
			me.setKfTelephone(tjyUser.getKfTelephone());
			// 首次登录 送积分 经验值
			userIntegralLogService.addLntAndEmp(tjyUser.getId(), "task_0001");

		} else {
			me.setIsRealname("0");
		}

		int loginCount = null == wxUser.getLogincount() ? 0 : wxUser.getLogincount();
		wxUser.setLogincount(loginCount);// 修改登录次数
		wxUser.setLastlogindate(new Date());
		wxUserService.updateWxUser(wxUser);
		request.getSession().setAttribute("me", me);
		request.getSession().setAttribute(Constants.SESSION_WXUSER_ID, me.getId() + "");
		request.getSession().setAttribute(Constants.SESSION_WXUSER_NICKNAME, tjyUser.getNickname());
		request.getSession().setAttribute(Constants.SESSION_WXUSER_HDPIC, tjyUser.getHeadPortrait());
		return super.getSuccessAjaxResult("操作成功");
	}

	/**
	 * 跳转至登录页面
	 * 
	 * @Title: loginPage
	 * @Description: TODO
	 * @return
	 * @return: String
	 * @author: zengmin
	 * @date: 2017年4月7日 上午10:15:42
	 */
	@RequestMapping("loginPage")
	public String loginPage() {
		return "login";
	}

	/**
	 * 跳转至首页
	 * 
	 * @Title: index
	 * @Description: TODO
	 * @return
	 * @return: String
	 * @author: zengmin
	 * @date: 2017年4月7日 上午10:15:51
	 */
	@RequestMapping("index")
	public String index(HttpServletRequest req, ModelMap modelMap) {
		if (!ServletUtil.isLogin(req)) {
			return "login";
		}
		// 首页广告查询
		IndexAd indexAd = indexAdService.selectIndexAdByUserId(ServletUtil.getMember(req).getId());
		modelMap.addAttribute("indexAd", indexAd);
		return "index";
	}

	/**
	 * 获取验证码
	 * 
	 * @Title: getImg
	 * @Description: TODO
	 * @param session
	 * @param response
	 * @return: void
	 * @author: zengmin
	 * @date: 2017年4月7日 上午10:16:07
	 */
	@RequestMapping("imgNum")
	public void getImg(HttpSession session, HttpServletResponse response) {

		ServletOutputStream out = null;
		try {
			DefaultKaptcha captchaProducer = (DefaultKaptcha) ServletUtil.getApplicationContext()
					.getBean("captchaProducer");

			response.setDateHeader("Expires", 0);
			response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
			response.addHeader("Cache-Control", "post-check=0, pre-check=0");
			response.setHeader("Pragma", "no-cache");
			response.setContentType("image/jpeg");
			String capText = captchaProducer.createText();
			session.setAttribute("imgCode", capText);
			BufferedImage bi = captchaProducer.createImage(capText);
			out = response.getOutputStream();
			ImageIO.write(bi, "jpg", out);
			out.flush();
		} catch (Exception e) {

		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}
	}

	/**
	 * 发送手机验证码
	 * 
	 * @Title: send_code
	 * @Description: TODO
	 * @param session
	 * @param response
	 * @param mobile
	 * @param type
	 * @return
	 * @return: Map
	 * @author: zengmin
	 * @date: 2017年4月7日 上午10:16:24
	 */
	@RequestMapping("send_code")
	public @ResponseBody Map send_code(HttpSession session, HttpServletResponse response, String yz, String mobile,
			String type) {
		try {
			if (!StringUtils.hasLength(mobile)) {
				return super.getAjaxResult("999", "参数错误", null);
			}

			// WxUser wxUser = wxUserService.selectByMobile(mobile);
			// if (null != wxUser &&
			// StringUtils.hasLength(wxUser.getQqOpenid())) {
			// return super.getAjaxResult("999", "该用户已存在，可直接登录", null);
			// }

			/*
			 * String imgCode = (String) session.getAttribute("imgCode");
			 * System.out.println("imgCode-send_code:" + imgCode + "\tymyz" + yz); if
			 * (!yz.equalsIgnoreCase(imgCode)) { return super.getAjaxResult("999", "验证码错误",
			 * null); }
			 */
			String vcode = (int) ((Math.random() * 9 + 1) * 100000) + "";
			System.out.println("-----往消息表加数据----" + vcode);

			MessageInfo messageInfo = new MessageInfo();
			messageInfo.setId(UUIDGenerator.getUUID());
			messageInfo.setDeleted(0);
			messageInfo.setMobile(mobile);
			messageInfo.setType(1);// 短信
			messageInfo.setCreateTime(new Date());
			if ("reg".equals(type)) {
				messageInfo.setContent("{code:\"" + vcode + "\",product:\"" + AldyMessageUtil.SMSPRE + "\"}");
			}
			messageInfo.setStatus(0);// 未发送
			messageInfo.setTemplateId(AldyMessageUtil.MsmTemplateId.REG);
			messageInfoService.addMessageInfo(messageInfo);
			if (!StringUtils.hasLength(vcode)) {
				return super.getAjaxResult("999", "验证码发送失败", null);
			}
			MsmValidateBean msmValidateBean = new MsmValidateBean(mobile, new Date(), vcode);
			session.setAttribute("mvb", msmValidateBean);
			return super.getSuccessAjaxResult("手机验证码发送成功");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return super.getAjaxResult("999", "验证码发送失败", null);
	}

	/**
	 * 判断用户是否认证
	 * 
	 * @Title: is_recon
	 * @Description: TODO
	 * @param session
	 * @param response
	 * @param mobile
	 * @param type
	 * @return
	 * @return: Map
	 * @author: zengmin
	 * @date: 2017年5月5日 上午10:16:24
	 */
	@RequestMapping("is_recon")
	public @ResponseBody Map is_recon(HttpServletRequest request, HttpServletResponse response) {
		Member me = ServletUtil.getMember(request);
		if (null == me) {
			return super.getAjaxResult("302", "登录超时，请重新登录", null);
		}
		String userId = me.getId();
		if (StringUtils.isEmpty(userId)) {
			return super.getAjaxResult("302", "登录超时，请重新登录", null);
		}
		TjyUser tjyUser = tjyUserService.selectByPrimaryKey(userId);
		if (null == tjyUser) {
			return super.getAjaxResult("302", "登录超时，请重新登录", null);
		}
		if ("1".equals(tjyUser.getIsRealname() + "")) {
			return super.getSuccessAjaxResult();
		}
		if (null != tjyUser.getReconStatus() && tjyUser.getReconStatus().intValue() == 2) {
			return super.getSuccessAjaxResult();
		}
		return super.getAjaxResult("600", "未认证", null);
	}

	private String encodeURL(String url, HttpServletRequest request) throws UnsupportedEncodingException {
		StringBuffer result = null;
		if (StringUtils.hasLength(url)) {
			url = url.replaceAll("@@2@@", "&");
			result = new StringBuffer();
			for (int i = 0; i < url.length(); i++) {
				if ((url.charAt(i) + "").getBytes().length > 1) {
					result.append(URLEncoder.encode(url.charAt(i) + "", "UTF-8"));
				} else {
					result.append(url.charAt(i));
				}
			}
			String res = result.toString();
			String contentPath = ApplicationPath.getParameter("domain");
			String path = request.getContextPath();
			String cp = contentPath + path;
			if (res.indexOf(cp) == -1 || !"http".equals(res.substring(0, 4))) {
				return cp + res;
			}
			return res;
		} else {
			return null;
		}
	}

	/**
	 * 获取当前登录用户，用于判断用户是否认证通过
	 * 
	 * @Title: getTjyUser
	 * @Description: TODO
	 * @param session
	 * @param response
	 * @param mobile
	 * @param type
	 * @return
	 * @return: Map
	 * @author: zengmin
	 * @date: 2017年5月5日 上午10:16:24
	 */
	@RequestMapping("getTjyUser")
	public @ResponseBody Map getTjyUser(HttpServletRequest request, HttpServletResponse response) {
		if (!ServletUtil.isLogin(request)) {
			return super.getAjaxResult("302", "登录超时，请重新登录", null);
		}
		Member me = ServletUtil.getMember(request);
		String userId = me.getId();
		if (StringUtils.isEmpty(userId)) {
			return super.getAjaxResult("302", "登录超时，请重新登录", null);
		}
		TjyUser tjyUser = tjyUserService.selectByPrimaryKey(userId);
		if (null == tjyUser) {
			return super.getAjaxResult("302", "登录超时，请重新登录", null);
		}
		if (StringUtils.isEmpty(tjyUser.getKfTelephone())) {
			tjyUser.setKfTelephone(sysconfigService.getSysconfig().getServiceTelphoneList());
		}
		return super.getSuccessAjaxResult("获取成功", tjyUser);
	}

	/**
	 * 获取当前登录用户，用于判断用户是否认证通过
	 * 
	 * @Title: getTjyUser
	 * @Description: TODO
	 * @param session
	 * @param response
	 * @param mobile
	 * @param type
	 * @return
	 * @return: Map
	 * @author: zengmin
	 * @date: 2017年5月5日 上午10:16:24
	 */
	@RequestMapping("getTjyUserByid")
	public @ResponseBody Map getTjyUserByid(HttpServletRequest request, HttpServletResponse response, String uid) {
		if (!ServletUtil.isLogin(request)) {
			return super.getAjaxResult("302", "登录超时，请重新登录", null);
		}
		Member me = ServletUtil.getMember(request);
		String userId = me.getId();
		if (StringUtils.isEmpty(userId)) {
			return super.getAjaxResult("302", "登录超时，请重新登录", null);
		}
		TjyUser tjyUser = tjyUserService.selectByPrimaryKey(uid);
		if (null == tjyUser) {
			return super.getAjaxResult("0", "用户不存在", null);
		}
		if (StringUtils.isEmpty(tjyUser.getKfTelephone())) {
			tjyUser.setKfTelephone(sysconfigService.getSysconfig().getServiceTelphoneList());
		}
		return super.getSuccessAjaxResult("获取成功", tjyUser);
	}

	@RequestMapping("getWxUser")
	public @ResponseBody Map getWxUser(HttpServletRequest request, HttpServletResponse response) {
		if (!ServletUtil.isLogin(request)) {
			return super.getAjaxResult("302", "登录超时，请重新登录", null);
		}
		Member me = ServletUtil.getMember(request);
		String userId = me.getId();
		if (StringUtils.isEmpty(userId)) {
			return super.getAjaxResult("302", "登录超时，请重新登录", null);
		}
		WxUser wxUser = wxUserService.selectByUserId(userId);
		if (null == wxUser) {
			return super.getAjaxResult("302", "登录超时，请重新登录", null);
		}
		return super.getSuccessAjaxResult("获取成功", wxUser);
	}

	@RequestMapping("shareconfig")
	public @ResponseBody Map shareconfig(HttpServletRequest request, HttpServletResponse response) {
		return super.getSuccessAjaxResult("获取成功", null);
	}

	// TODO 中间库同步接口
	@RequestMapping("updateUser")
	public @ResponseBody Map updateUser(HttpServletRequest request, HttpServletResponse response, String signature,
			String sexId, String timestamp, String userName, String mobile, String newMobile, String company,
			String job, String industry, String reconStatus, String reconDate, String honorTitle, String kfTelephone,
			String lastRegDate, String province) {

		// 中间库同步接口开关 0：关闭，1：开启
		int remoteFlag = sysconfigService.getRemoteErpSwitch();
		if (remoteFlag == 0) {
			return super.getSuccessAjaxResult("操作成功");
		}

		Map<String, String> queries = new HashMap<String, String>();
		queries.put("timestamp", timestamp);
		if (!StringUtil.isEmpty(userName))
			queries.put("userName", userName);
		if (!StringUtil.isEmpty(mobile))
			queries.put("mobile", mobile);
		if (!StringUtil.isEmpty(newMobile))
			queries.put("newMobile", newMobile);
		if (!StringUtil.isEmpty(company))
			queries.put("company", company);
		if (!StringUtil.isEmpty(job))
			queries.put("job", job);
		if (!StringUtil.isEmpty(industry))
			queries.put("industry", industry);
		if (!StringUtil.isEmpty(reconStatus))
			queries.put("reconStatus", reconStatus);
		if (!StringUtil.isEmpty(reconDate))
			queries.put("reconDate", reconDate);
		if (!StringUtil.isEmpty(honorTitle))
			queries.put("honorTitle", honorTitle);
		if (!StringUtil.isEmpty(kfTelephone))
			queries.put("kfTelephone", kfTelephone);
		if (!StringUtil.isEmpty(lastRegDate))
			queries.put("lastRegDate", lastRegDate);
		if (!StringUtil.isEmpty(province))
			queries.put("province", province);
		if (!StringUtil.isEmpty(sexId))
			queries.put("sexId", sexId);

		System.out.println("+++++++++queries：" + queries.toString());
		String perToSign = TjySign.composeStringToSign(queries);
		System.out.println("+++++++++perToSign：" + perToSign);
		byte[] sha1 = null;
		try {
			sha1 = TjySign.hmacSHA1Signature("ljWPzf0qA5I9OhBsTo5DsQEFkc2CxG&", perToSign);
		} catch (Exception e) {
			e.printStackTrace();
			// 签名校验
			return super.getAjaxResult("999", "签名错误", null);
		}
		String sign = new String(new BASE64Encoder().encode(sha1));
		if (!sign.equals(signature)) {
			// 签名校验
			return super.getAjaxResult("999", "签名错误", null);
		}
		WxUser u = wxUserService.selectByMobile(mobile);
		if (u == null) {
			return super.getAjaxResult("999", "用户不存在", null);
		}
		TjyUser user = tjyUserService.selectById(u.getId() + "");
		if (!StringUtil.isEmpty(userName)) {
			u.setUsername(userName);
			u.setTruename(userName);
			u.setNickName(userName);
			user.setNickname(userName);
			user.setTrueName(userName);
			// 更新im用户头像
			IMUtil.updateUserOne(imPrefix + user.getId(), user.getNickname(), user.getHeadPortrait());
		}

		if (!StringUtil.isEmpty(honorTitle)) {
			if ("天九家人".equals(honorTitle)) {
				user.setHonorTitle("家人");
				user.setHonorFlag("honor_001");
			} else if ("天九云亲".equals(honorTitle)) {
				user.setHonorTitle("云亲");
				user.setHonorFlag("honor_002");
			} else if ("天九伙伴".equals(honorTitle)) {
				user.setHonorTitle("伙伴");
				user.setHonorFlag("honor_003");
			}
		}
		if (!StringUtil.isEmpty(newMobile) && !newMobile.equals(mobile)) {
			u.setMobile(newMobile);
			user.setMobile(newMobile);
			user.setBindPhone(newMobile);
		}
		// String company,
		// String job,String industry,String reconStatus,String reconDate；

		if (!StringUtil.isEmpty(kfTelephone))
			user.setKfTelephone(kfTelephone);
		if (!StringUtil.isEmpty(reconDate)) {
			long d = CommUtil.null2Long(reconDate);
			user.setReconDate(new Date(d));
		}
		if (!StringUtil.isEmpty(lastRegDate)) {
			long d = CommUtil.null2Long(lastRegDate);
			user.setLastRegDate(new Date(d));
		}
		if (!StringUtil.isEmpty(company))
			user.setComName(company.split(",")[0]);
		if (!StringUtil.isEmpty(job)) {
			List listValues = listValuesService.selectListByType(12, job);
			if (null != listValues && !listValues.isEmpty()) {
				Map lv = (Map) listValues.get(0);
				user.setJob((String) lv.get("id"));
			}
		}
		if (!StringUtil.isEmpty(industry)) {
			List listValues = listValuesService.selectListByType(8001, industry);
			if (null != listValues && !listValues.isEmpty()) {
				Map lv = (Map) listValues.get(0);
				user.setIndustry((String) lv.get("id"));
			}
		}
		if (!StringUtil.isEmpty(province)) {
			Map param = new HashMap();
			param.put("disName", province);
			List lst = districtService.selectByParam(param);
			if (lst != null && lst.size() > 0) {
				Map d = (Map) lst.get(0);
				user.setProvince((String) d.get("id"));
			}
		}
		if (!StringUtil.isEmpty(reconStatus)) {
			if ("普通客户".equals(reconStatus)) {
				user.setReconStatus(1);
				user.setLastRegDate(null);
			} else if ("成交客户".equals(reconStatus) || "认证客户".equals(reconStatus)) {
				user.setReconStatus(2);
				user.setReconDate(new Date());
				user.setIsRealname(1);
			} else if ("问题客户".equals(reconStatus)) {
				user.setReconStatus(3);
				user.setLastRegDate(null);
			}
		}
		if (!StringUtil.isEmpty(sexId)) {
			int sexstr = 1;
			if ("女".equals(sexId))
				sexstr = 2;
			if (u.getSex() != null && sexstr != u.getSex())
				u.setSex(sexstr);
		}
		if (!StringUtil.isEmpty(reconDate)) {
			long time = CommUtil.null2Long(reconDate);
			if (time > 0) {
				user.setReconDate(new Date(time));
			}
		}
		wxUserService.updateWxUser(u);
		tjyUserService.updateTjyUser(user);
		System.out.println("/m/sys/updateUser:++++++" + queries.toString());
		return super.getSuccessAjaxResult("操作成功");
	}

	@RequestMapping("updateUserTest")
	public @ResponseBody Map updateUserTest(HttpServletRequest request, HttpServletResponse response, String signature,
			String mobile) {
		// TjyUser u = tjyUserService.selectByPrimaryKey("10196");
		// tjyUserService.remotingUpdateTjyUser(u, u.getMobile());

		// 通知IM
		// WxUser wuser = wxUserService.selectByMobile("13910427233");
		// System.out.println("wxUser.getId()+++" + wuser.getId());
		// TjyUser tjyUser = tjyUserService.selectById(wuser.getId() + "");
		// Map usermap = null;
		// if (tjyUser != null) {
		// usermap = tjyUserService.remotingGetUser(tjyUser.getMobile());
		// }
		// if (usermap != null) {
		// try {
		// // 调用远程数据库，获取用户信息
		// String customerName = (String) usermap.get("customerName");
		// if (!StringUtil.isEmpty(customerName)) {
		// tjyUser.setNickname(customerName);
		// tjyUser.setTrueName(customerName);
		// if (!customerName.equals(wuser.getUsername())) {
		// wuser.setUsername(customerName);
		// wuser.setTruename(customerName);
		// wuser.setNickName(customerName);
		// wxUserService.updateWxUser(wuser);
		// }
		// }
		// String sex1 = (String) usermap.get("sexId");
		// if (!StringUtil.isEmpty(sex1)) {
		// int sexstr = 1;
		// if ("女".equals(sex1)) {
		// sexstr = 2;
		// }
		// if (wuser.getSex() != null && sexstr != wuser.getSex()) {
		// wuser.setSex(sexstr);
		// wxUserService.updateWxUser(wuser);
		// }
		// }
		// String customerCompany = (String) usermap.get("customerCompany");
		// if (!StringUtil.isEmpty(customerCompany)) {
		// tjyUser.setComName(customerCompany);
		// }
		// String customerTitle = (String) usermap.get("customerTitle");
		// if (!StringUtil.isEmpty(customerTitle)) {
		// List<Map<String, Object>> listValues =
		// listValuesService.selectListByType(12,
		// customerTitle);
		// if (null != listValues && !listValues.isEmpty()) {
		// Map lv = (Map) listValues.get(0);
		// tjyUser.setJob((String)lv.get("id"));
		// }
		// }
		// String customerUnitIndustry = (String)
		// usermap.get("customerUnitIndustry");
		// if (!StringUtil.isEmpty(customerUnitIndustry)) {
		// List<Map<String, Object>> listValues =
		// listValuesService.selectListByType(8001,
		// customerUnitIndustry);
		// if (null != listValues && !listValues.isEmpty()) {
		// Map lv = (Map) listValues.get(0);
		// tjyUser.setIndustry((String)lv.get("id"));
		// }
		// }
		// String customerType = (String) usermap.get("customerType");
		// if (!StringUtil.isEmpty(customerType)) {
		// if ("普通客户".equals(customerType)) {
		// tjyUser.setReconStatus(1);
		// } else if ("成交客户".equals(customerType) ||
		// "认证客户".equals(customerType)) {
		// tjyUser.setReconStatus(2);
		// Date reconDate = new Date();
		// tjyUser.setReconDate(reconDate);
		// tjyUser.setIsRealname(1);
		// // Calendar now = Calendar.getInstance();
		// // now.add(Calendar.YEAR, 1);
		// // tjyUser.setLastRegDate(now.getTime());
		// } else if ("问题客户".equals(customerType)) {
		// tjyUser.setReconStatus(3);
		// }
		// }
		// String customerCheckCapital = (String)
		// usermap.get("customerCheckCapital");
		// if (!StringUtil.isEmpty(customerCheckCapital)) {
		// //注册资本
		// // dd
		// Pattern p = Pattern.compile("[0-9\\.]+");
		// Matcher m = p.matcher(customerCheckCapital);
		// if(m.find()){
		// customerCheckCapital = m.group();
		// if(!StringUtil.isEmpty(customerCheckCapital)){
		// tjyUser.setReconCapital(CommUtil.null2Double(customerCheckCapital));
		// }
		// }
		// }
		// String customerActivationDate = (String)
		// usermap.get("customerActivationDate");
		// if (!StringUtil.isEmpty(customerActivationDate)) {
		// long d = CommUtil.null2Long(customerActivationDate);
		// tjyUser.setReconDate(new Date(d));
		// }
		// String servantNumber = (String) usermap.get("servantNumber");
		// if (!StringUtil.isEmpty(servantNumber)) {
		// tjyUser.setKfTelephone(servantNumber);
		// }
		// String customerEndDate = (String) usermap.get("customerEndDate");
		// if (!StringUtil.isEmpty(customerEndDate)) {
		// long d = CommUtil.null2Long(customerEndDate);
		// tjyUser.setLastRegDate(new Date(d));
		// }
		//
		// String customerEffectiveLevel = (String)
		// usermap.get("customerEffectiveLevel");
		// if (!StringUtil.isEmpty(customerEffectiveLevel)) {
		// if ("天九家人".equals(customerEffectiveLevel)) {
		// tjyUser.setHonorTitle("家人");
		// tjyUser.setHonorFlag("honor_001");
		// } else if ("天九云亲".equals(customerEffectiveLevel)) {
		// tjyUser.setHonorTitle("云亲");
		// tjyUser.setHonorFlag("honor_002");
		// } else if ("天九伙伴".equals(customerEffectiveLevel)) {
		// tjyUser.setHonorTitle("伙伴");
		// tjyUser.setHonorFlag("honor_003");
		// }
		// }
		// tjyUserService.updateTjyUser(tjyUser);
		// } catch (Exception e) {
		// e.printStackTrace();
		// }
		// }
		return super.getSuccessAjaxResult("操作成功");

	}

	//
	// public static void main(String args[]){
	// String str="abcjlskdf";
	// Pattern p = Pattern.compile("[0-9\\.]+");
	// Matcher m = p.matcher(str);
	// if(m.find()){
	// System.out.print(m.group());
	// }
	// }

	@RequestMapping("getUserTest")
	public @ResponseBody Map getUserTest(HttpServletRequest request, HttpServletResponse response, String signature,
			String timestamp, String userName, String mobile) {
		// Map ret = tjyUserService.remotingGetUser(mobile);
		Map ret = null;
		return super.getSuccessAjaxResult("操作成功", ret);

	}

	/**
	 * 退出登录
	 * 
	 * @Title: logout
	 * @Description: TODO
	 * @param request
	 * @param response
	 * @param session
	 * @return
	 * @return: Map
	 * @author: zengmin
	 * @date: 2017年10月9日 下午4:43:58
	 */
	@RequestMapping("logout")
	public @ResponseBody Map logout(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		if (!ServletUtil.isLogin(request)) {
			return super.getAjaxResult("302", "登录超时，请重新登录", null);
		}
		Member me = ServletUtil.getMember(request);
		String userId = me.getId();
		if (StringUtils.isEmpty(userId)) {
			return super.getAjaxResult("302", "登录超时，请重新登录", null);
		}
		WxUser wxUser = wxUserService.selectByPrimaryKey(userId);
		if (null == wxUser) {
			return super.getAjaxResult("0", "用户不存在", null);
		}
		TjyUser tjyUser = tjyUserService.selectByPrimaryKey(userId);
		if (null == tjyUser) {
			return super.getAjaxResult("0", "用户不存在", null);
		}
		// 退出登录，清空openid
		wxUser.setQqOpenid(null);
		tjyUser.setOpenId(null);
		wxUserService.updateWxUser(wxUser);
		tjyUserService.updateTjyUser(tjyUser);
		session.removeAttribute("me");
		session.removeAttribute(Constants.SESSION_WXUSER_ID);
		session.removeAttribute(Constants.SESSION_WXUSER_NICKNAME);
		session.removeAttribute(Constants.SESSION_WXUSER_HDPIC);
		return super.getSuccessAjaxResult("退出登录成功");
	}

	/**
	 * 跳转到文章共用显示页面
	 * 
	 * @Title: wzShowPage
	 * @Description: TODO
	 * @param request
	 * @param modelMap
	 * @return
	 * @return: String
	 * @author: zengmin
	 * @date: 2017年10月9日 下午5:09:01
	 */
	@RequestMapping("wz_show")
	public String wzShowPage(HttpServletRequest request, ModelMap modelMap) {
		List<Map<String, Object>> listValues = listValuesService.selectListByType(666);
		if (null != listValues && !listValues.isEmpty()) {
			modelMap.put("content", listValues.get(0).get("listDesc"));
		}
		return "commons/wz_show";

	}

}
