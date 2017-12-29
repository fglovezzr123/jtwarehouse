package com.tojoy.util;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.KeyStore;

import javax.net.ssl.SSLContext;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class ApplyBackUtil {

	public static String sendGetRequestCa(String reqURL, String xml, boolean isEncoder){  
        System.out.println("==========================微信退款开始!!========================");  
        CloseableHttpClient httpClient = null;
		String responseContent = null;
		CloseableHttpResponse responseEntry=null;
		try{  
            KeyStore keyStore = KeyStore.getInstance("PKCS12");  
            FileInputStream instream = new FileInputStream(new File(ConfigUtil.certLocalPath)); 
            
            System.out.println(ConfigUtil.certLocalPath);
            System.out.println(ConfigUtil.certPassword.toCharArray());
            try {  
                keyStore.load(instream,ConfigUtil.certPassword.toCharArray());  
            }finally {  
                instream.close();  
            }  
            // Trust own CA and all self-signed certs  
            SSLContext sslcontext = SSLContexts.custom().loadKeyMaterial(keyStore,ConfigUtil.certPassword.toCharArray()).build();  
            // Allow TLSv1 protocol only  
            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(  
                    sslcontext, new String[] { "TLSv1" }, null,  
                    SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);  
            httpClient = HttpClients.custom()  
                    .setSSLSocketFactory(sslsf).build();  
            HttpPost httppost = new HttpPost(reqURL);  
              
            
    		System.out.println("申请退款请求：" + xml);
            StringEntity se = new StringEntity(xml);  
            httppost.setEntity(se);  
            System.out.println("executing request" + httppost.getRequestLine());  
            responseEntry = httpClient.execute(httppost);  
                HttpEntity entity = responseEntry.getEntity();  
                if (null != entity) {
    				responseContent = EntityUtils.toString(entity, "UTF-8");
    				EntityUtils.consume(entity);
    			}
                EntityUtils.consume(entity);  
        }catch(Exception e){  
            e.printStackTrace();  
        }finally{
        	try {
				httpClient.close();
				responseEntry.close(); 
			} catch (IOException e) {
				e.printStackTrace();
			}  
        }  
		return responseContent;
	}  
	
}
