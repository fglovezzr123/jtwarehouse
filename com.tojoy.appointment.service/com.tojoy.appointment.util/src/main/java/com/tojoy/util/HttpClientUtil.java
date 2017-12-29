package com.tojoy.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.security.KeyStore;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.HttpVersion;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.protocol.HTTP;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;

/**
 * 封装了一些采用HttpClient发送HTTP请求的方法
 * 
 * @see 本工具所采用的是最新的HttpComponents-Client-4.2.1
 * @see 关于本工具类中的一些解释说明,可参考下方列出的我的三篇文章
 * @see http://blog.csdn.net/jadyer/article/details/7615830
 * @see http://blog.csdn.net/jadyer/article/details/7615880
 * @see http://blog.csdn.net/jadyer/article/details/7802139
 * @create Feb 1, 2012 3:02:27 PM
 * @update Oct 8, 2012 3:48:55 PM
 * @author 玄玉<http://blog.csdn/net/jadyer>
 * @version v1.3
 * @history v1.0-->新建<code>sendGetRequest(String,String)</code>和
 *          <code>sendPostRequest(String,Map<String,String>,String,String)</code>
 *          方法
 * @history v1.1-->新增
 *          <code>sendPostSSLRequest(String,Map<String,String>,String,String)</code>
 *          方法,用于发送HTTPS的POST请求
 * @history v1.2-->新增
 *          <code>sendPostRequest(String,String,boolean,String,String)</code>
 *          方法,用于发送HTTP协议报文体为任意字符串的POST请求
 * @history v1.3-->新增<code>java.net.HttpURLConnection</code>实现的
 *          <code>sendPostRequestByJava()</code>方法
 */
public class HttpClientUtil {
	private HttpClientUtil() {
	}

	/**
	 * 发送HTTP_GET请求
	 * 
	 * @see 该方法会自动关闭连接,释放资源
	 * @param requestURL
	 *            请求地址(含参数)
	 * @param decodeCharset
	 *            解码字符集,解析响应数据时用之,其为null时默认采用UTF-8解码
	 * @return 远程主机响应正文
	 */
	public static String sendGetRequest(String reqURL, String decodeCharset) {
		long responseLength = 0; // 响应长度
		String responseContent = null; // 响应内容
		HttpClient httpClient = new DefaultHttpClient(); // 创建默认的httpClient实例
		HttpGet httpGet = new HttpGet(reqURL); // 创建org.apache.http.client.methods.HttpGet
		try {
			HttpResponse response = httpClient.execute(httpGet); // 执行GET请求
			HttpEntity entity = response.getEntity(); // 获取响应实体
			if (null != entity) {
				responseLength = entity.getContentLength();
				responseContent = EntityUtils.toString(entity, decodeCharset == null ? "UTF-8" : decodeCharset);
				EntityUtils.consume(entity); // Consume response content
			}
			System.out.println("请求地址: " + httpGet.getURI());
			System.out.println("响应状态: " + response.getStatusLine());
			System.out.println("响应长度: " + responseLength);
			System.out.println("响应内容: " + responseContent);
		} catch (ClientProtocolException e) {
			System.out.println("该异常通常是协议错误导致,比如构造HttpGet对象时传入的协议不对(将'http'写成'htp')或者服务器端返回的内容不符合HTTP协议要求等,堆栈信息如下");
		} catch (ParseException e) {
			System.out.println(e.getMessage());
		} catch (IOException e) {
			System.out.println("该异常通常是网络原因引起的,如HTTP服务器未启动等,堆栈信息如下");
		} finally {
			httpClient.getConnectionManager().shutdown(); // 关闭连接,释放资源
		}
		return responseContent;
	}

	/**
	 * 发送HTTP_POST请求
	 * 
	 * @see 该方法为
	 *      <code>sendPostRequest(String,String,boolean,String,String)</code>
	 *      的简化方法
	 * @see 该方法在对请求数据的编码和响应数据的解码时,所采用的字符集均为UTF-8
	 * @see 当<code>isEncoder=true</code>时,其会自动对<code>sendData</code>中的[中文][|][
	 *      ]等特殊字符进行<code>URLEncoder.encode(string,"UTF-8")</code>
	 * @param isEncoder
	 *            用于指明请求数据是否需要UTF-8编码,true为需要
	 */
	public static String sendPostRequest(String reqURL, String sendData, boolean isEncoder) {
		return sendPostRequest(reqURL, sendData, isEncoder, null, null);
	}

	/**
	 * 发送HTTP_POST请求
	 * 
	 * @see 该方法会自动关闭连接,释放资源
	 * @see 当<code>isEncoder=true</code>时,其会自动对<code>sendData</code>中的[中文][|][
	 *      ]等特殊字符进行<code>URLEncoder.encode(string,encodeCharset)</code>
	 * @param reqURL
	 *            请求地址
	 * @param sendData
	 *            请求参数,若有多个参数则应拼接成param11=value11¶m22=value22¶m33=value33的形式后,
	 *            传入该参数中
	 * @param isEncoder
	 *            请求数据是否需要encodeCharset编码,true为需要
	 * @param encodeCharset
	 *            编码字符集,编码请求数据时用之,其为null时默认采用UTF-8解码
	 * @param decodeCharset
	 *            解码字符集,解析响应数据时用之,其为null时默认采用UTF-8解码
	 * @return 远程主机响应正文
	 */
	public static String sendPostRequest(String reqURL, String sendData, boolean isEncoder, String encodeCharset,
			String decodeCharset) {
		String responseContent = null;
		HttpClient httpClient = new DefaultHttpClient();

		HttpPost httpPost = new HttpPost(reqURL);
		// httpPost.setHeader(HTTP.CONTENT_TYPE,
		// "application/x-www-form-urlencoded; charset=UTF-8");
		httpPost.setHeader(HTTP.CONTENT_TYPE, "application/x-www-form-urlencoded");
		try {
			if (isEncoder) {
				// List<NameValuePair> formParams = new
				// ArrayList<NameValuePair>();
				// for(String str : sendData.split("&")){
				// formParams.add(new
				// BasicNameValuePair(str.substring(0,str.indexOf("=")),
				// str.substring(str.indexOf("=")+1)));
				// }
				// httpPost.setEntity(new
				// StringEntity(URLEncodedUtils.format(formParams,
				// encodeCharset==null ? "UTF-8" : encodeCharset)));

				httpPost.setEntity(new StringEntity(sendData, encodeCharset == null ? "UTF-8" : encodeCharset));
			} else {
				httpPost.setEntity(new StringEntity(sendData));
			}
			// 设置请求超时时间
			httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 5000);
			HttpResponse response = httpClient.execute(httpPost);
			HttpEntity entity = response.getEntity();
			if (null != entity) {
				responseContent = EntityUtils.toString(entity, decodeCharset == null ? "UTF-8" : decodeCharset);
				EntityUtils.consume(entity);
			}
		} catch (Exception e) {
			System.out.println("与[" + reqURL + "]通信过程中发生异常,堆栈信息如下");
		} finally {
			httpClient.getConnectionManager().shutdown();
		}
		return responseContent;
	}

	/**
	 * 发送HTTP_POST请求
	 * 
	 * @see 该方法会自动关闭连接,释放资源
	 * @see 当<code>isEncoder=true</code>时,其会自动对<code>sendData</code>中的[中文][|][
	 *      ]等特殊字符进行<code>URLEncoder.encode(string,encodeCharset)</code>
	 * @param reqURL
	 *            请求地址
	 * @param sendData
	 *            请求参数,若有多个参数则应拼接成param11=value11¶m22=value22¶m33=value33的形式后,
	 *            传入该参数中
	 * @param isEncoder
	 *            请求数据是否需要encodeCharset编码,true为需要
	 * @return 远程主机响应正文
	 * @throws IOException
	 */
	public static String sendPostRequestCa(String reqURL, String sendData, boolean isEncoder) {
		CloseableHttpClient httpClient = null;
		String responseContent = null;
		try {
			KeyStore keyStore = KeyStore.getInstance("PKCS12");
			FileInputStream instream = new FileInputStream(new File(ConfigUtil.certLocalPath));
			try {
				keyStore.load(instream, ConfigUtil.certPassword.toCharArray());
			} finally {
				instream.close();
			}
			// Trust own CA and all self-signed certs
			SSLContext sslcontext = SSLContexts.custom()
					.loadKeyMaterial(keyStore, ConfigUtil.certPassword.toCharArray()).build();
			// Allow TLSv1 protocol only
			SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslcontext, new String[] { "TLSv1" },
					null, SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
			httpClient = HttpClients.custom().setSSLSocketFactory(sslsf).build();
			HttpPost httpPost = new HttpPost(reqURL);
			System.out.println("executing request" + httpPost.getRequestLine());
			
			  //设置请求头
            httpPost.addHeader("Connection", "keep-alive");

            httpPost.addHeader("Accept", "*/*");

            httpPost.addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");

            httpPost.addHeader("Host", "api.mch.weixin.qq.com");

            httpPost.addHeader("X-Requested-With", "XMLHttpRequest");

            httpPost.addHeader("Cache-Control", "max-age=0");

            httpPost.addHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.0) ");
            
            //请求参数
            HttpEntity requestEntity = new StringEntity(sendData, "UTF-8");
            httpPost.setEntity(requestEntity);
            CloseableHttpResponse response = httpClient.execute(httpPost);
			HttpEntity entity = response.getEntity();
			 System.out.println("----------------------------------------");
             System.out.println(response.getStatusLine());
			if (null != entity) {
				responseContent = EntityUtils.toString(entity, "UTF-8");
				EntityUtils.consume(entity);
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("与[" + reqURL + "]通信过程中发生异常,堆栈信息如下");
		} finally {
			if (null != httpClient) {
				try {
					httpClient.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return responseContent;
	}

	/**
	 * 发送HTTP_POST请求
	 * 
	 * @see 该方法为
	 *      <code>sendPostSSLRequest(String,Map<String,String>,String,String)</code>
	 *      方法的简化方法
	 * @see 该方法在对请求数据的编码和响应数据的解码时,所采用的字符集均为UTF-8
	 * @see 该方法会自动对<code>params</code>中的[中文][|][ ]等特殊字符进行
	 *      <code>URLEncoder.encode(string,"UTF-8")</code>
	 */
	public static String sendPostRequest(String reqURL, Map<String, String> params, Map cookiemap) {
		return sendPostRequest(reqURL, params, null, null, cookiemap);
	}

	/**
	 * 发送HTTP_POST请求
	 * 
	 * @see 该方法会自动关闭连接,释放资源
	 * @see 该方法会自动对<code>params</code>中的[中文][|][ ]等特殊字符进行
	 *      <code>URLEncoder.encode(string,encodeCharset)</code>
	 * @param reqURL
	 *            请求地址
	 * @param params
	 *            请求参数
	 * @param encodeCharset
	 *            编码字符集,编码请求数据时用之,其为null时默认采用UTF-8解码
	 * @param decodeCharset
	 *            解码字符集,解析响应数据时用之,其为null时默认采用UTF-8解码
	 * @return 远程主机响应正文
	 */
	public static String sendPostRequest(String reqURL, Map<String, String> params, String encodeCharset,
			String decodeCharset, Map cookiemap) {
		String responseContent = null;
		DefaultHttpClient httpClient = new DefaultHttpClient();

		if (cookiemap.get("cookie") != null) {
			List<Cookie> cookies = (List<Cookie>) cookiemap.get("cookie");
			for (int i = 0; i < cookies.size(); i++) {
				httpClient.getCookieStore().addCookie(cookies.get(i));
			}
		}
		HttpPost httpPost = new HttpPost(reqURL);
		// httpPost.setHeader("Content-type",
		// "application/x-www-form-urlencoded; charset=UTF-8");
		List<NameValuePair> formParams = new ArrayList<NameValuePair>(); // 创建参数队列
		for (Map.Entry<String, String> entry : params.entrySet()) {
			formParams.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
		}
		try {
			httpPost.setEntity(new UrlEncodedFormEntity(formParams, encodeCharset == null ? "UTF-8" : encodeCharset));
			httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 5000);
			HttpResponse response = httpClient.execute(httpPost);
			HttpEntity entity = response.getEntity();
			if (null != entity) {
				responseContent = EntityUtils.toString(entity, decodeCharset == null ? "UTF-8" : decodeCharset);
				EntityUtils.consume(entity);
			}
		} catch (Exception e) {
			System.out.println("与[" + reqURL + "]通信过程中发生异常,堆栈信息如下");
		} finally {
			httpClient.getConnectionManager().shutdown();
		}
		return responseContent;
	}

	/**
	 * 发送HTTPS_POST请求
	 * 
	 * @see 该方法为
	 *      <code>sendPostSSLRequest(String,Map<String,String>,String,String)</code>
	 *      方法的简化方法
	 * @see 该方法在对请求数据的编码和响应数据的解码时,所采用的字符集均为UTF-8
	 * @see 该方法会自动对<code>params</code>中的[中文][|][ ]等特殊字符进行
	 *      <code>URLEncoder.encode(string,"UTF-8")</code>
	 */
	public static String sendPostSSLRequest(String reqURL, Map<String, String> params, Map cookiemap) {
		return sendPostSSLRequest(reqURL, params, null, null, cookiemap);
	}

	/**
	 * 发送HTTPS_POST请求
	 * 
	 * @see 该方法会自动关闭连接,释放资源
	 * @see 该方法会自动对<code>params</code>中的[中文][|][ ]等特殊字符进行
	 *      <code>URLEncoder.encode(string,encodeCharset)</code>
	 * @param reqURL
	 *            请求地址
	 * @param params
	 *            请求参数
	 * @param encodeCharset
	 *            编码字符集,编码请求数据时用之,其为null时默认采用UTF-8解码
	 * @param decodeCharset
	 *            解码字符集,解析响应数据时用之,其为null时默认采用UTF-8解码
	 * @return 远程主机响应正文
	 */
	public static String sendPostSSLRequest(String reqURL, Map<String, String> params, String encodeCharset,
			String decodeCharset, Map cookiemap) {
		String responseContent = "";
		DefaultHttpClient httpClient = new DefaultHttpClient();
		X509TrustManager xtm = new X509TrustManager() {
			public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
			}

			public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
			}

			public X509Certificate[] getAcceptedIssuers() {
				return null;
			}
		};
		try {
			SSLContext ctx = SSLContext.getInstance("TLS");
			ctx.init(null, new TrustManager[] { xtm }, null);
			SSLSocketFactory socketFactory = new SSLSocketFactory(ctx);
			httpClient.getConnectionManager().getSchemeRegistry().register(new Scheme("https", 443, socketFactory));
			if (cookiemap.get("cookie") != null) {
				List<Cookie> cookies = (List<Cookie>) cookiemap.get("cookie");
				// Cookie c = new BasicClientCookie("hasWarningUser","1");
				// httpClient.getCookieStore().addCookie(c);
				for (int i = 0; i < cookies.size(); i++) {
					httpClient.getCookieStore().addCookie(cookies.get(i));
				}
			}
			HttpPost httpPost = new HttpPost(reqURL);
			List<NameValuePair> formParams = new ArrayList<NameValuePair>();
			if (params != null) {
				for (Map.Entry<String, String> entry : params.entrySet()) {
					formParams.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
				}
			}
			httpPost.setHeader("Referer", "http://mp.weixin.qq.com/");
			httpPost.setEntity(new UrlEncodedFormEntity(formParams, encodeCharset == null ? "UTF-8" : encodeCharset));
			httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 5000);
			HttpResponse response = httpClient.execute(httpPost);

			cookiemap.put("cookie", httpClient.getCookieStore().getCookies());
			HttpEntity entity = response.getEntity();
			if (null != entity) {
				responseContent = EntityUtils.toString(entity, decodeCharset == null ? "UTF-8" : decodeCharset);
				EntityUtils.consume(entity);
			}
		} catch (Exception e) {
			System.out.println("与[" + reqURL + "]通信过程中发生异常,堆栈信息为");
		} finally {
			httpClient.getConnectionManager().shutdown();
		}
		return responseContent;
	}

	/**
	 * 发送HTTPS_POST请求
	 * 
	 * @param reqURL
	 *            请求地址
	 * @param params
	 *            请求参数
	 * @return 远程主机响应正文
	 * @throws IOException
	 * @throws ClientProtocolException
	 */
	public static String sendPostFile(String reqURL, Map<String, String> params, String filepath, String mimeType,
			Map cookiemap) throws ClientProtocolException, IOException {
		String responseContent = "";
		DefaultHttpClient httpclient = new DefaultHttpClient();
		httpclient.getParams().setParameter(CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);
		if (cookiemap.get("cookie") != null) {
			List<Cookie> cookies = (List<Cookie>) cookiemap.get("cookie");
			for (int i = 0; i < cookies.size(); i++) {
				httpclient.getCookieStore().addCookie(cookies.get(i));
			}
		}
		HttpPost httppost = new HttpPost(reqURL);

		FileBody file = new FileBody(new File(filepath), mimeType);

		MultipartEntity reqEntity = new MultipartEntity();
		reqEntity.addPart("uploadfile", file);
		reqEntity.addPart("formId", new StringBody(""));
		httppost.setEntity(reqEntity);
		System.out.println("executing request " + httppost.getRequestLine());
		HttpResponse response = httpclient.execute(httppost);
		HttpEntity resEntity = response.getEntity();
		System.out.println(response.getStatusLine());
		if (resEntity != null) {
			responseContent = EntityUtils.toString(resEntity, "UTF-8");
			EntityUtils.consume(resEntity);
		}
		httpclient.getConnectionManager().shutdown();
		return responseContent;

	}

	public static String download(String url, String ext, Map cookiemap) {
		DefaultHttpClient client = new DefaultHttpClient();
		String filepath = "";
		HttpGet httpGet = new HttpGet(url);
		if (cookiemap.get("cookie") != null) {
			List<Cookie> cookies = (List<Cookie>) cookiemap.get("cookie");
			for (int i = 0; i < cookies.size(); i++) {
				client.getCookieStore().addCookie(cookies.get(i));
			}
		}
		try {
			HttpResponse response = client.execute(httpGet); // 执行GET请求
			if (HttpStatus.SC_OK == response.getStatusLine().getStatusCode()) {
				HttpEntity resEntity = response.getEntity();
				if (resEntity != null) {
					String name = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
					filepath = "C:/wxd/" + name + "." + ext;
					File storeFile = new File(filepath);
					mkdir(storeFile.getParentFile());
					FileOutputStream fileOutputStream = new FileOutputStream(storeFile);
					InputStream input = resEntity.getContent();
					byte b[] = new byte[1024 * 4];
					int j = 0;
					while ((j = input.read(b)) != -1) {
						fileOutputStream.write(b, 0, j);
					}
					fileOutputStream.flush();
					fileOutputStream.close();
					resEntity.consumeContent();
					input.close();
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		client.getConnectionManager().shutdown();
		return filepath;
	}

	public static void download(String url, String ext, Map cookiemap, HttpServletResponse res) {
		DefaultHttpClient client = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet(url);
		if (cookiemap.get("cookie") != null) {
			List<Cookie> cookies = (List<Cookie>) cookiemap.get("cookie");
			for (int i = 0; i < cookies.size(); i++) {
				client.getCookieStore().addCookie(cookies.get(i));
			}
		}
		try {
			HttpResponse response = client.execute(httpGet); // 执行GET请求
			if (HttpStatus.SC_OK == response.getStatusLine().getStatusCode()) {
				HttpEntity resEntity = response.getEntity();
				if (resEntity != null) {
					String name = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
					OutputStream os = res.getOutputStream();
					InputStream input = resEntity.getContent();
					byte b[] = new byte[1024 * 4];
					int j = 0;
					while ((j = input.read(b)) != -1) {
						os.write(b, 0, j);
					}
					os.flush();
					os.close();
					resEntity.consumeContent();
					input.close();
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		client.getConnectionManager().shutdown();
	}

	/**
	 * 递归创建目录
	 * 
	 * @param file
	 */
	public static void mkdir(File file) {
		if (file.exists()) {
			return;
		}
		if (!file.getParentFile().exists()) {
			mkdir(file.getParentFile());
		}
		file.mkdir();
	}

	public static void main(String[] args) throws UnsupportedEncodingException {
		// HttpClient httpClient = new DefaultHttpClient();
		// String url = "http://api.map.baidu.com/geocoder/v2/?ak=" +
		// ApplicationPath.getParameter("ak")
		// + "&location=39.983424,116.322987&output=json&pois=0";
		// String s = HttpClientUtil.sendGetRequest(url, null);
		// System.out.println(s);
	}

}
