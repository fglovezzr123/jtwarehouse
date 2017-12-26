package com.tojoycloud.frame.common;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;

public class HttpsRequest
{
	public static String sendHttpsRequest(String url, String content)
	{
		try
		{
			SSLContext sc = SSLContext.getInstance("SSL");
			sc.init(null, new TrustManager[]{new TrustAnyTrustManager()}, new java.security.SecureRandom());
			URL console = new URL(url);
			HttpsURLConnection conn = (HttpsURLConnection) console.openConnection();
			conn.setSSLSocketFactory(sc.getSocketFactory());
			conn.setHostnameVerifier(new TrustAnyHostnameVerifier());
			conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			conn.setDoInput(true);
			conn.setDoOutput(true);
			if (content != null)
			{
				DataOutputStream out = new DataOutputStream(conn.getOutputStream());
				out.write(content.getBytes("UTF-8"));
				out.flush();
				out.close();
			}
			String line;
			BufferedReader bufferedReader;
			StringBuilder sb = new StringBuilder();
			InputStreamReader streamReader = null;
			try
			{
				streamReader = new InputStreamReader(conn.getInputStream(), "UTF-8");
			}
			catch (IOException e)
			{
				streamReader = new InputStreamReader(conn.getErrorStream(), "UTF-8");
			}
			finally
			{
				if (streamReader != null)
				{
					bufferedReader = new BufferedReader(streamReader);
					sb = new StringBuilder();
					while ((line = bufferedReader.readLine()) != null)
					{
						sb.append(line);
					}
				}
			}
			return sb.toString();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return "";
	}
}
