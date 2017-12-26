/**  
 * @Project: tjy
 * @Title: BaseConfig.java
 * @Package com.oa.commons.base
 * @date 2016-3-28 下午3:20:50
 * @Copyright: 2016 
 */
package com.wing.socialcontact.config;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;
import java.util.Properties;

import javax.servlet.ServletContext;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.web.context.ServletContextAware;

import com.wing.socialcontact.util.ServletUtil;

/**
 * 
 * 类名：BaseConfig
 * 功能：基本配置文件加载
 * 详细：
 * 作者：dijuli
 * 版本：1.0
 * 日期：2016-3-28 下午3:20:50
 *
 */

public class BaseConfig  implements ServletContextAware{
	
	private static Logger logger = Logger.getLogger(BaseConfig.class);
	
	/**
	 * classPath 路径
	 */
	public static String classPath="";
	/**
	 * web跟目录 webroot
	 */
	public static String webPath="";

	/**
	 * 一般上传文件的跟目录
	 */
	public static String uploadPath="";
	
	private ServletContext servletContext;
	
	/**
	 * 将webconfig配置文件转换成java对象 
	 */
	public static final  WebConfig webconfig = new WebConfig();
	
	
	/**
	 * 开发人员id
	 */
	private  String devName;
	/**
	 * 超级管理员id
	 */
	private  String saName;
	
	private String jpushSecret;
	
	private String jpushAppKey;
	
	
	public static final MasConfig masconfig = new MasConfig(); 
	
	/**
	 * 将SerialConfig配置文件转换成java对象 
	 */
	public static final  SerialConfig serialconfig = new SerialConfig();
 	//私有的默认构造子
	private BaseConfig(){} 
	 //获取spring创建的bean对象
    public synchronized static BaseConfig getInstance() {
    	
        return ServletUtil.getApplicationContext().getBean(BaseConfig.class);
    }
	
	/**
	 * 初始化加载ini配置文件
	 * 
	 * @throws IOException
	 */
     
	public  void load()throws Exception{
		
		classPath=this.getClass().getResource("/").getPath();//获取classpath路径
		
		webPath= classPath+"../../";//获取webroot跟目录;
		
		//设置一般文件上传目录
		uploadPath=webPath+"/upfiles/";
		
		//System.out.println("webPath===="+webPath);
		
		//加载webconfig配置文件
		
		Map<String,String> map=readProperties("/config/webconfig.properties");
		//将配置文件信息赋值到 webconfig
		

		WebConfig web =BaseConfig.webconfig;
		
		web.setAllowIps(map.get("allow_ips"));
		web.setAppStart(Integer.parseInt(map.get("app_start")));
		web.setLimitIps(map.get("limit_ips"));
		web.setLoginEndTime(map.get("login_endTime"));
		web.setLoginStartTime(map.get("login_startTime"));
		web.setMsgwarnTime(Integer.parseInt(map.get("msgwarn_time")));
		web.setPwdErrorNum(Integer.parseInt(map.get("pwd_error_num")));
		web.setPwdErrorTime(Integer.parseInt(map.get("pwd_error_time")));
		
	}
	
	/**
	 * 将properties配置文件转换为Map键值对
	 * @param path
	 * @return
	 * @throws IOException
	 */
	public Map<String,String>  readProperties(String path) throws IOException{
		
		InputStream is = getClass().getResourceAsStream(path);
		Properties prop = new Properties();
		prop.load(is);
		is.close();
		return (Map)prop;
		
	}
	
	public synchronized boolean updateWebconfig(WebConfig newconf){
		InputStream is = null;   
        OutputStream fos = null; 
		try {
			BeanUtils.copyProperties(newconf,BaseConfig.webconfig);//修改属性对象
			
			//修改配置文件值
			is = getClass().getResourceAsStream("/config/webconfig.properties");
			//System.out.println("==="+is);
			Properties prop = new Properties();
			prop.load(is);
			is.close();//修改之前必须关闭
			prop.setProperty("allow_ips", newconf.getAllowIps());
			prop.setProperty("app_start", ""+newconf.getAppStart());
			prop.setProperty("limit_ips", newconf.getLimitIps());
			prop.setProperty("login_endTime", newconf.getLoginEndTime());
			prop.setProperty("login_startTime", newconf.getLoginStartTime());
			prop.setProperty("msgwarn_time", ""+newconf.getMsgwarnTime());
			prop.setProperty("pwd_error_num", ""+newconf.getPwdErrorNum());
			prop.setProperty("pwd_error_time", ""+newconf.getPwdErrorTime());
			
			fos=new FileOutputStream(classPath+"/config/webconfig.properties");
			prop.store(fos, null);
			fos.close();
			return true;
		} catch (Exception e) {
			
			e.printStackTrace();
			return false;
		}finally{   
            try {   
                fos.close();   
                is.close();   
            } catch (IOException e) {   
                // TODO Auto-generated catch block   
                e.printStackTrace();   
            }   
        }   

	}

	public String getDevName() {
		return devName;
	}
	public String getSaName() {
		return saName;
	}
	public void setDevName(String devName) {
		this.devName = devName;
	}
	public void setSaName(String saName) {
		this.saName = saName;
	}
	@Override
	public void setServletContext(ServletContext arg0) {
		 this.servletContext=arg0;
	}
    public String getJpushSecret() {
        return jpushSecret;
    }
    public void setJpushSecret(String jpushSecret) {
        this.jpushSecret = jpushSecret;
    }
    public String getJpushAppKey() {
        return jpushAppKey;
    }
    public void setJpushAppKey(String jpushAppKey) {
        this.jpushAppKey = jpushAppKey;
    }
}
