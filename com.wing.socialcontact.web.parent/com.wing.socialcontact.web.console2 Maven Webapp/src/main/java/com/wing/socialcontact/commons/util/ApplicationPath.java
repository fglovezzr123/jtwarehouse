package com.wing.socialcontact.commons.util;

import java.io.InputStream;
import java.util.Properties;

public class ApplicationPath {
	public static java.util.Properties param;

	private static ApplicationPath instance = null;

	private ApplicationPath() {
		InputStream in = null;
		try {
			in = this.getClass().getResourceAsStream(
					"/app.properties");
			Properties p = new Properties();
			p.load(in);
			param = p;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				in.close();
			} catch (Exception e) {
			}
		}
	}

	public static synchronized ApplicationPath getInstance() {
		if (instance == null) {
			instance = new ApplicationPath();
		}
		return instance;
	}

	/**
	 * 获得配置文件中某个参数的值
	 * 
	 * @param paramName
	 *            参数名称
	 * @return 参数值
	 * 
	 * @author 
	 */
	public static String getParameter(String propertyName) {
		try {
			if (instance == null) {
				instance = new ApplicationPath();
			}
			return param.getProperty(propertyName);
		} catch (Exception e) {
		}
		return "";
	}
}
