package com.tojoy.vhall.api;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tojoy.util.MD5UtilWx;

public class BaseAPI {
	public static Logger logger = LoggerFactory.getLogger(BaseAPI.class);
	//private static java.util.Properties param;
	protected static int TRY_TIMES = 5;//重试次数
	protected static String APP_KEY="2f9f8c5db05c76e18f9512c8bc08b989";
	protected static String SECRET_KEY="42f2f2d43753344652e99799a5ea3b5b";
	protected static String APP_SECRET_KEY="ba376ab55e8cf0633aa00e968df85769";
	protected static String THD_USER_PASS="tjy000000!";
	public static String AUTH_URL="http://www.51adven.com/wxfront/m/vhall/validk.jhtml";
	public static String FAILURE_URL="";
	
	
	protected static String fakeUserAgent = "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 "
			+ "(KHTML, like Gecko) Chrome/45.0.2454.101 Safari/537.36 QIHU 360SE";

	protected static final ObjectMapper mapper = new ObjectMapper();

	protected static CloseableHttpClient client;

	static {
		PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager(20, TimeUnit.SECONDS);
		connManager.setMaxTotal(200);
		connManager.setDefaultMaxPerRoute(200);

		RequestConfig config = RequestConfig.custom().setRedirectsEnabled(false).setRelativeRedirectsAllowed(false)
				.setMaxRedirects(0).setConnectionRequestTimeout(1000*30).setConnectTimeout(100000).setSocketTimeout(1000*60)
				.build();
		client = HttpClientBuilder.create().setConnectionManager(connManager).setDefaultRequestConfig(config)
				.setUserAgent(fakeUserAgent).build();
//		loadProperties();
//		try {
//			String vhall_try_times = getParameter("vhall_try_times");
//			TRY_TIMES = StringUtils.isBlank(vhall_try_times)?3:Integer.valueOf(vhall_try_times).intValue();
//		} catch (Throwable e) {
//			logger.error("从dubbo.properties文件中获取vhall_try_times并转化Integer类型失败",e);
//		}
//		TRY_TIMES = TRY_TIMES<=0?3:TRY_TIMES;
//		APP_KEY = getParameter("vhall_app_key");
//		SECRET_KEY = getParameter("vhall_secret_key");
//		APP_SECRET_KEY = getParameter("vhall_app_secret_key");
//		THD_USER_PASS = getParameter("vhall_thd_user_pass");
//		THD_USER_PASS = StringUtils.isBlank(THD_USER_PASS)?"tjy000000!":THD_USER_PASS;
//
//		AUTH_URL = getParameter("vhall_auth_url");
//		FAILURE_URL = getParameter("vhall_failure_url");
	}
	
//	private static void loadProperties(){
//		InputStream in = null;
//		try {
//			in = BaseAPI.class.getClass().getResourceAsStream(
//					"/dubbo.properties");
//			Properties p = new Properties();
//			p.load(in);
//			param = p;
//		} catch (Throwable e) {
//			logger.error("加载dubbo.properties文件失败");
//		} finally {
//			try {
//				in.close();
//			} catch (Exception e) {
//			}
//		}
//	}
//	private static String getParameter(String propertyName) {
//		try {
//			if (param == null) {
//				return "";
//			}else{
//				return param.getProperty(propertyName);
//			}
//		} catch (Exception e) {
//		}
//		return "";
//	}
	
	@SuppressWarnings("unchecked")
	private static final Map<String,?> doPost(String url,SortedMap<String, String> packageParams){
		Map<String,Object> result = new HashMap<String,Object>();
		for(int i=0;i<TRY_TIMES;i++){
			if(i>0){
        		try {
					Thread.sleep(200);
				} catch (Exception e) {
					e.printStackTrace();
				}
        	}
			
			List<NameValuePair> nvps = new ArrayList<NameValuePair>();  
			for(Iterator<String> it=packageParams.keySet().iterator();it.hasNext();){
				String key = it.next();
				nvps.add(new BasicNameValuePair(key, packageParams.get(key)));  
			}
			
			HttpPost httpPost = new HttpPost(url);  
			HttpResponse httpResponse = null;
			Integer statusCode = null;
			try {
				httpPost.setEntity(new UrlEncodedFormEntity(nvps, "UTF-8"));  
				httpResponse = client.execute(httpPost);
				statusCode = httpResponse.getStatusLine().getStatusCode();
			} catch (ClientProtocolException e) {
				if(i==TRY_TIMES-1){
					throw new RuntimeException(e.getMessage()+":"+e.getClass().getName(),e);
				}else{
					continue;
				}
			} catch (IOException e) {
				if(i==TRY_TIMES-1){
					throw new RuntimeException(e.getMessage()+":"+e.getClass().getName(),e);
				}else{
					continue;
				}
			}
			if(200==statusCode){
				String entity = null;
				try {
					entity = EntityUtils.toString(httpResponse.getEntity());
				} catch (ParseException e) {
					if(i==TRY_TIMES-1){
						throw new ParseException("getEntity转换异常："+e.getMessage()+":"+e.getClass().getName());
					}else{
						continue;
					}
				} catch (IOException e) {
					if(i==TRY_TIMES-1){
						throw new ParseException("getEntity转换异常："+e.getMessage()+":"+e.getClass().getName());
					}else{
						continue;
					}
				}
				try {
					result =  mapper.readValue(entity.replace('\t', ' '), Map.class);
					break;
				} catch (JsonParseException e) {
					throw new RuntimeException("mapper转换异常："+e.getMessage()+":"+e.getClass().getName(),e);
				} catch (JsonMappingException e) {
					throw new RuntimeException("mapper转换异常："+e.getMessage()+":"+e.getClass().getName(),e);
				} catch (IOException e) {
					throw new RuntimeException("mapper转换异常："+e.getMessage()+":"+e.getClass().getName(),e);
				}
			}else{
				if(i==TRY_TIMES-1){
					throw new RuntimeException("【请求失败】,状态码："+statusCode);
				}else{
					continue;
				}
			}
		}
		
        return result;
	}
	
	
	protected static final Map<String,?> doPostVhall(String url,Map<String,String> params){
		long signed_at = System.currentTimeMillis();
		SortedMap<String, String> packageParams = new TreeMap<String, String>();
		packageParams.put("auth_type", "2");
		packageParams.put("app_key", APP_KEY);
		packageParams.put("signed_at", String.valueOf(signed_at));
		packageParams.putAll(params);
		
		StringBuffer sb = new StringBuffer();
        Set<Entry<String, String>>  es = packageParams.entrySet();
        Iterator<Entry<String, String>> it = es.iterator();
        sb.append(SECRET_KEY);
        while (it.hasNext()) {
        	Entry<String, String> entry = it.next();
            String k = (String) entry.getKey();
            String v = (String) entry.getValue();
            sb.append(k + v);
        }
        sb.append(SECRET_KEY);
        String sign =MD5UtilWx.MD5Encode(sb.toString(),"utf-8").toLowerCase();
		packageParams.put("sign",sign);
        return doPost(url, packageParams);
	}
	
	
	public static final Map<String,Object> createVedioSign(String account,String userName,String webinar_id) {
		if(!UserAPI.getThirdUserId(account).isSuccess()){
			UserAPI.registerThirdUserId(account, userName);
		}
		
		long signedat = System.currentTimeMillis();
		SortedMap<String, Object> params = new TreeMap<String, Object>();
		params.put("roomid", webinar_id);
		params.put("account", account);
		params.put("email", account+"@163.com");
		params.put("username", userName);
		params.put("app_key", APP_KEY);
		params.put("signedat", signedat);
		
		StringBuffer sb = new StringBuffer();
		sb.append(SECRET_KEY);
		Set<Entry<String, Object>> es = params.entrySet();
		Iterator<Entry<String, Object>> it = es.iterator();
		while(it.hasNext()) {
			Map.Entry<String, Object> entry = (Map.Entry<String, Object>)it.next();
			String k = (String)entry.getKey();
			Object v = entry.getValue();
			sb.append(k + "" + v);
		}
		sb.append(SECRET_KEY);
		String result = MD5UtilWx.MD5Encode(sb.toString(), "UTF-8").toLowerCase();
		params.put("sign", result);
		return params;
    }
	public static final Map<String,Object> createVedioSignWithoutRegisterUser(String account,String userName,String webinar_id) {
		long signedat = System.currentTimeMillis();
		SortedMap<String, Object> params = new TreeMap<String, Object>();
		params.put("roomid", webinar_id);
		params.put("account", account);
		params.put("email", account+"@163.com");
		params.put("username", userName);
		params.put("app_key", APP_KEY);
		params.put("signedat", signedat);
		
		StringBuffer sb = new StringBuffer();
		sb.append(SECRET_KEY);
		Set<Entry<String, Object>> es = params.entrySet();
		Iterator<Entry<String, Object>> it = es.iterator();
		while(it.hasNext()) {
			Map.Entry<String, Object> entry = (Map.Entry<String, Object>)it.next();
			String k = (String)entry.getKey();
			Object v = entry.getValue();
			sb.append(k + "" + v);
		}
		sb.append(SECRET_KEY);
		String result = MD5UtilWx.MD5Encode(sb.toString(), "UTF-8").toLowerCase();
		params.put("sign", result);
		return params;
	}
}
