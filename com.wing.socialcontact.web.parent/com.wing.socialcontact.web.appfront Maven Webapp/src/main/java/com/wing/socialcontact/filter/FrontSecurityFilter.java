package com.wing.socialcontact.filter;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.google.common.collect.Maps;

public class FrontSecurityFilter implements Filter {
	public Logger log = Logger.getLogger(FrontSecurityFilter.class);
	public void init(FilterConfig filterConfig) throws ServletException {
	
	}
	public void destroy() {
	
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httprequest = (HttpServletRequest) request;
		XssRequestWrapper xrequest = new XssRequestWrapper(httprequest);
		chain.doFilter(xrequest, response);
	}
}
class XssRequestWrapper extends HttpServletRequestWrapper {
	public static final String REGEX1 = "^.*[A|a][L|l][E|e][R|r][T|t]\\s*\\(.*\\).*|" +
			".*[W|w][I|i][N|n][D|d][O|o][W|w]\\.[L|l][O|o][C|c][A|a][T|t][I|i][O|o][N|n]\\s*=.*|" +
			".*[S|s][T|t][Y|y][L|l][E|e]\\s*=.*[X|x]:[E|e][X|x].*[P|p][R|r][E|e][S|s]{1,2}[I|i][O|o][N|n]\\s*\\(.*\\).*|" +
			".*[D|d][O|o][C|c][U|u][M|m][E|e][N|n][T|t]\\.[C|c][O|o]{2}[K|k][I|i][E|e].*|" +
			".*[E|e][V|v][A|a][L|l]\\s*\\(.*\\).*|" +
			".*[U|u][N|n][E|e][S|s][C|c][A|a][P|p][E|e]\\s*\\(.*\\).*|" +
			".*[E|e][X|x][E|e][C|c][S|s][C|c][R|r][I|i][P|p][T|t]\\s*\\(.*\\).*|" +
			".*[M|m][S|s][G|g][B|b][O|o][X|x]\\s*\\(.*\\).*|" +
			".*[C|c][O|o][N|n][F|f][I|i][R|r][M|m]\\s*\\(.*\\).*|" +
			".*[P|p][R|r][O|o][M|m][P|p][T|t]\\s*\\(.*\\).*|" +
			".*<[S|s][C|c][R|r][I|i][P|p][T|t]>.*</[S|s][C|c][R|r][I|i][P|p][T|t]>.*|" +
			"[.&[^\"]]*\"[.&[^\"]]*|" +
			"[.&[^']]*'[.&[^']]*]|" +
			"[[.&[^a]]|[|a|\n|\r\n|\r|\u0085|\u2028|\u2029]]*<[S|s][C|c][R|r][I|i][P|p][T|t]>.*</[S|s][C|c][R|r][I|i][P|p][T|t]>[[.&[^a]]|[|a|\n|\r\n|\r|\u0085|\u2028|\u2029]]*$";
	
	public static final String REGEX2 = ".*(^[\\\\s%09]*(j|&\\#[0]{0,4}106|&\\#x[0]{0,4}6a)" +
			"[\\\\s%09]*(a|&\\#[0]{0,4}97|&\\#x[0]{0,4}61)[\\\\s%09]*(v|&\\#[0]{0,4}118|&" +
			"\\#x[0]{0,4}76)[\\\\s%09]*(a|&\\#[0]{0,4}97|&\\#x[0]{0,4}61)[\\\\s%09]*(s|&" +
			"\\#[0]{0,4}115|&\\#x[0]{0,4}73)[\\\\s%09]*(c|&\\#[0]{0,4}99|&\\#x[0]{0,4}63)" +
			"[\\\\s%09]*(r|&\\#[0]{0,4}114|&\\#x[0]{0,4}72)[\\\\s%09]*(i|&\\#[0]{0,4}105" +
			"|&\\#x[0]{0,4}69)[\\\\s%09]*(p|&\\#[0]{0,4}112|&\\#x[0]{0,4}70)[\\\\s%09]*" +
			"(t|&\\#[0]{0,4}116|&\\#x[0]{0,4}74)[\\\\s%09]*(\\:|&\\#[0]{0,4}58|&\\#x[0]" +
			"{0,4}3a)[\\\\s%09]*|(%27|\\\\')|--|[\\\\s+]and[\\\\s+]|/[\\\\*].*[\\\\*]/" +
			"and[\\\\s+]|/[\\\\*].*[\\\\*]/and/[\\\\*].*[\\\\*]/|[\\\\s+]and/[\\\\*]." +
			"*[\\\\*]/|[\\\\s+]or[\\\\s+]|/[\\\\*].*[\\\\*]/or[\\\\s+]|/[\\\\*].*[\\\\*]" +
			"/or/[\\\\*].*[\\\\*]/|[\\\\s+]or/[\\\\*].*[\\\\*]/|[\\:%3A](<|%3c|&lt|&\\#60)" +
			"[^\\:%3A]|[^\\:%3A](<|%3c|&lt|&\\#60)[\\:%3A]|[\\:%3A](>|%3e|&gt|&\\#62)[^\\:%3A]" +
			"|[^\\:%3A](>|%3e|&gt|&\\#62)[\\:%3A]|^(<|%3c|&lt|&\\#60).*|^(>|%3e|&gt|&\\#62).*|" +
			"(<|%3c|&lt|&\\#60)$|(>|%3e|&gt|&\\#62)$).*";
	
	public XssRequestWrapper(HttpServletRequest request) {
		super(request);
	}
	public Map<String, String[]> getParameterMap() {
		Map<String, String[]> request_map = super.getParameterMap();
		Iterator<Map.Entry<String,String[]>> iterator = request_map.entrySet().iterator();
		while (iterator.hasNext()) {
			Map.Entry<String,String[]> me = iterator.next();
			String[] values = (String[]) me.getValue();
			for (int i = 0; i < values.length; i++) {
				if (values[i] != null) {
					values[i] = doFilter(values[i]);
				}
			}
		}
		return request_map;
	}

	public String[] getParameterValues(String paramString) {
		String[] arrayOfString1 = super.getParameterValues(paramString);
		if (arrayOfString1 == null)
			return null;
		int i = arrayOfString1.length;
		String[] arrayOfString2 = new String[i];

		for (int j = 0; j < i; j++) {
			if (arrayOfString1[j] != null) {
				arrayOfString2[j] = doFilter(arrayOfString1[j]);
			}
		}
		return arrayOfString2;
	}

	public String getParameter(String paramString) {
		String str = super.getParameter(paramString);
		if (str == null){
			Map<String, String[]> kv = getPostUrlParamMap();
			String[] vs = kv.get(paramString); 
			str = vs!=null&&vs.length>0?vs[0]:null;
		}
		return doFilter(str);
	}

	public String getHeader(String paramString) {
		String str = super.getHeader(paramString);
		if (str == null)
			return null;
		return doFilter(str);
	}
	/**
	 * 获取通过post方式并且头部类型采用application/x-www-form-urlencoded
	 * @return
	 */
	public Map<String, String[]> getPostUrlParamMap() {
		Map<String, String[]> params = Maps.newHashMap();
		if(getMethod().equalsIgnoreCase("POST")&&
				getHeader("Content-Type")
				.indexOf("application/x-www-form-urlencoded")!=-1){
			params = new HashMap<String,String[]>();
			try {
				InputStream in = this.getInputStream();
				StringBuffer sbf = new StringBuffer();
				byte[] b = new byte[4096];
				for(int n;(n=in.read(b))!=-1;){
					sbf.append(new String(b,0,n));
				}
				String paramsUrl = sbf.toString();
				String[] kvs =  paramsUrl.split("&");
				for(String kv : kvs){
					if(StringUtils.isEmpty(kv)) continue;
					String[] _kv = kv.split("=");
					if(_kv!=null&&_kv.length>0){
						String k = _kv[0];
						String v = _kv.length>1?_kv[1]:"";
						params.put(k, new String[]{v});
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return params;
		}else{
			params = getParameterMap();
			return params;
		}
	}
	public String doFilter(String value) {
		if (StringUtils.isEmpty(value)) {
			return value;
		}
		value = value.replaceAll("&quot;", "\"");
		value = value.replaceAll("&amp;", "&");
		value = value.replaceAll("\r\n", " ");
		value = value.replaceAll("<", "&lt;");
		value = value.replaceAll(">", "&gt;");
		value = value.replaceAll("\n\r", " ");
		value = value.replaceAll("\n", " ");
		value = value.replaceAll("\r", " ");
		value = value.replaceAll(REGEX1, "");
		value = value.replaceAll(REGEX2, "");
		return value;
	}
}