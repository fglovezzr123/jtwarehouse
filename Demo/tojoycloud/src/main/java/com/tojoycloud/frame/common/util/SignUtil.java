package com.tojoycloud.frame.common.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;
import java.util.SimpleTimeZone;
import java.util.TreeMap;
import java.util.UUID;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import sun.misc.BASE64Encoder;

public class SignUtil
{
	public static Map<String, String> splitQueryString(String url) throws URISyntaxException, UnsupportedEncodingException
	{
		URI uri = new URI(url);
		String query = uri.getQuery();
		final String[] pairs = query.split("&");
		TreeMap<String, String> queryMap = new TreeMap<String, String>();
		for (String pair : pairs)
		{
			final int idx = pair.indexOf("=");
			final String key = idx > 0 ? pair.substring(0, idx) : pair;
			if (!queryMap.containsKey(key))
			{
				queryMap.put(key, URLDecoder.decode(pair.substring(idx + 1), "UTF-8"));
			}
		}
		return queryMap;
	}

	/** 对参数名称和参数值进行URL编码 **/
	// public static String generate(String method, Map<String, String>
	// parameter, String accessKeySecret) throws Exception
	// {
	// String signString = generateSignString(method, parameter);
	// System.out.println("signString---" + signString);
	// byte[] signBytes = hmacSHA1Signature(accessKeySecret + "&", signString);
	// String signature = newStringByBase64(signBytes);
	// System.out.println("signature---" + signature);
	// if ("POST".equals(method))
	// return signature;
	// return URLEncoder.encode(signature, "UTF-8");
	// }

	// public static String generateSignString(String httpMethod, Map<String,
	// String> parameter) throws IOException
	// {
	// TreeMap<String, String> sortParameter = new TreeMap<String, String>();
	// sortParameter.putAll(parameter);
	// String canonicalizedQueryString =
	// UrlUtil.generateQueryString(sortParameter, true);
	// if (null == httpMethod)
	// {
	// throw new RuntimeException("httpMethod can not be empty");
	// }
	// /** 构造待签名的字符串* */
	// StringBuilder stringToSign = new StringBuilder();
	// stringToSign.append(httpMethod).append("&");
	// stringToSign.append(percentEncode("/")).append("&");
	// stringToSign.append(percentEncode(canonicalizedQueryString));
	// return stringToSign.toString();
	// }

	public static byte[] hmacSHA1Signature(String secret, String baseString) throws Exception
	{
		if (isEmpty(secret))
		{
			throw new IOException("secret can not be empty");
		}
		if (isEmpty(baseString))
		{
			return null;
		}
		Mac mac = Mac.getInstance("HmacSHA1");
		SecretKeySpec keySpec = new SecretKeySpec(secret.getBytes("UTF-8"), "HmacSHA1");
		mac.init(keySpec);
		return mac.doFinal(baseString.getBytes("UTF-8"));
	}

	private static boolean isEmpty(String str)
	{
		return (str == null || str.length() == 0);
	}

	public static String newStringByBase64(byte[] bytes) throws UnsupportedEncodingException
	{
		if (bytes == null || bytes.length == 0)
		{
			return null;
		}
		return new String(new BASE64Encoder().encode(bytes));
	}

	public static String composeStringToSign(Map<String, String> queries)
	{
		String[] sortedKeys = (String[]) queries.keySet().toArray(new String[0]);
		Arrays.sort(sortedKeys);
		StringBuilder canonicalizedQueryString = new StringBuilder();
		for (String key : sortedKeys)
		{
//			canonicalizedQueryString.append("&").append(percentEncode(key)).append("=").append(percentEncode((String) queries.get(key)));
			canonicalizedQueryString.append("&").append(key).append("=").append((String) queries.get(key));
		}
		StringBuilder stringToSign = new StringBuilder();
//		stringToSign.append(percentEncode(canonicalizedQueryString.toString().substring(1)));
		stringToSign.append(canonicalizedQueryString.toString().substring(1));
		return stringToSign.toString();
	}

	public static String concatQueryString(Map<String, String> parameters) throws UnsupportedEncodingException
	{
		if (null == parameters)
		{
			return null;
		}
		StringBuilder urlBuilder = new StringBuilder("");
		for (Map.Entry<String, String> entry : parameters.entrySet())
		{
			String key = (String) entry.getKey();
			String val = (String) entry.getValue();
			urlBuilder.append(encode(key));
			if (val != null)
			{
				urlBuilder.append("=").append(encode(val));
			}
			urlBuilder.append("&");
		}
		int strIndex = urlBuilder.length();
		if (parameters.size() > 0)
		{
			urlBuilder.deleteCharAt(strIndex - 1);
		}
		return urlBuilder.toString();
	}

	public static String encode(String value) throws UnsupportedEncodingException
	{
		return URLEncoder.encode(value, "UTF-8");
	}

	public static String percentEncode(String value)
	{
		try
		{
			return value == null ? null : URLEncoder.encode(value, "UTF-8").replace("+", "%20").replace("*", "%2A").replace("%7E", "~");
		}
		catch (Exception e)
		{
		}
		return "";
	}

	/**
	 * get SignatureNonce
	 ** */
	public static String getUniqueNonce()
	{
		UUID uuid = UUID.randomUUID();
		return uuid.toString();
	}

	/**
	 * get timestamp
	 **/
	public static String getISO8601Time()
	{
		Date nowDate = new Date();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
		df.setTimeZone(new SimpleTimeZone(0, "GMT"));
		return df.format(nowDate);
	}
}
