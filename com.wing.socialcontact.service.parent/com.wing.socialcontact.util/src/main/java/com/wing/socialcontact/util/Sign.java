package com.wing.socialcontact.util;

import java.util.UUID;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.lang.StringUtils;

import java.util.Map;
import java.util.SimpleTimeZone;
import java.util.HashMap;
import java.util.Arrays;
import java.util.Date;
import java.util.Formatter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class Sign {
	public static void main(String[] args) {
		// String jsapi_ticket =
		// "bxLdikRXVbTPdHSM05e5u4RbEYQn7pNQMPrfzl8lJNb1foLDa3HIwI3BRMkQmSO_5F64VFa75uURcq6Uz7QHgA";
		// String url =
		// "http://omstest.vmall.com:23568/thirdparty/wechat/vcode/gotoshare?quantity=1&batchName=MATE7";
		// Map<String, String> ret = sign(jsapi_ticket, url);
		// for (Map.Entry entry : ret.entrySet()) {
		// System.out.println(entry.getKey() + ", " + entry.getValue());
		// }
	};

	public static Map<String, String> sign(String jsapi_ticket, String url) {
		Map<String, String> ret = new HashMap<String, String>();
		String nonce_str = create_nonce_str();
		String timestamp = create_timestamp();
		String string1;
		String signature = "";

		// 注意这里参数名必须全部小写，且必须有序
		string1 = "jsapi_ticket=" + jsapi_ticket + "&noncestr=" + nonce_str + "&timestamp=" + timestamp + "&url=" + url;
		System.out.println(string1);

		try {
			MessageDigest crypt = MessageDigest.getInstance("SHA-1");
			crypt.reset();
			crypt.update(string1.getBytes("UTF-8"));
			signature = byteToHex(crypt.digest());
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		ret.put("url", url);
		ret.put("jsapi_ticket", jsapi_ticket);
		ret.put("nonceStr", nonce_str);
		ret.put("timestamp", timestamp);
		ret.put("signature", signature);

		return ret;
	}

	private static String byteToHex(final byte[] hash) {
		Formatter formatter = new Formatter();
		for (byte b : hash) {
			formatter.format("%02x", b);
		}
		String result = formatter.toString();
		formatter.close();
		return result;
	}

	private static String create_nonce_str() {
		return UUID.randomUUID().toString();
	}

	private static String create_timestamp() {
		return Long.toString(System.currentTimeMillis() / 1000);
	}

	public static String getISO8601Time() {
		Date nowDate = new Date();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
		df.setTimeZone(new SimpleTimeZone(0, "GMT"));
		return df.format(nowDate);
	}

	public static String composeStringToSign(Map<String, String> queries) {
		String[] sortedKeys = (String[]) queries.keySet().toArray(new String[0]);
		Arrays.sort(sortedKeys);
		StringBuilder canonicalizedQueryString = new StringBuilder();
		for (String key : sortedKeys) {
			canonicalizedQueryString.append("&").append(percentEncode(key)).append("=")
					.append(percentEncode((String) queries.get(key)));
		}
		StringBuilder stringToSign = new StringBuilder();
		stringToSign.append("GET");
		stringToSign.append("&");
		stringToSign.append(percentEncode("/"));
		stringToSign.append("&");
		stringToSign.append(percentEncode(canonicalizedQueryString.toString().substring(1)));
		return stringToSign.toString();
	}

	public static String percentEncode(String value) {
		try {
			return value == null ? null
					: URLEncoder.encode(value, "UTF-8").replace("+", "%20").replace("*", "%2A").replace("%7E", "~");
		} catch (Exception e) {
		}
		return "";
	}

	public static String composeUrl(String endpoint, Map<String, String> queries) throws UnsupportedEncodingException {
		Map<String, String> mapQueries = queries;
		StringBuilder urlBuilder = new StringBuilder("");
		urlBuilder.append("https");
		urlBuilder.append("://").append(endpoint);
		if (-1 == urlBuilder.indexOf("?")) {
			urlBuilder.append("/?");
		}
		urlBuilder.append(concatQueryString(mapQueries));
		return urlBuilder.toString();
	}

	public static String concatQueryString(Map<String, String> parameters) throws UnsupportedEncodingException {
		if (null == parameters) {
			return null;
		}
		StringBuilder urlBuilder = new StringBuilder("");
		for (Map.Entry<String, String> entry : parameters.entrySet()) {
			String key = (String) entry.getKey();
			String val = (String) entry.getValue();
			urlBuilder.append(encode(key));
			if (val != null) {
				urlBuilder.append("=").append(encode(val));
			}
			urlBuilder.append("&");
		}
		int strIndex = urlBuilder.length();
		if (parameters.size() > 0) {
			urlBuilder.deleteCharAt(strIndex - 1);
		}
		return urlBuilder.toString();
	}

	public static String encode(String value) throws UnsupportedEncodingException {
		return URLEncoder.encode(value, "UTF-8");
	}

	public static byte[] hmacSHA1Signature(String secret, String baseString) throws Exception {
		if (StringUtils.isEmpty(secret)) {
			throw new IOException("secret can not be empty");
		}
		if (StringUtils.isEmpty(baseString)) {
			return null;
		}
		Mac mac = Mac.getInstance("HmacSHA1");
		SecretKeySpec keySpec = new SecretKeySpec(secret.getBytes("UTF-8"), "HmacSHA1");
		mac.init(keySpec);
		return mac.doFinal(baseString.getBytes("UTF-8"));
	}
}
