package com.tojoy.config;

/**
 * oss基本信息，根据spring配置读取sysconfig.properties中oss相关信息
 * @author sino
 *
 */
public class OssConfig {
	/**
	 * OSS的配置
	 */
	private String oss_access_Key;
	private String oss_secret_Access_Key;
	private String oss_bucketName;
	private String oss_endpoint;
	private String oss_getUrl;
	private String create_group_notify_url; 
	private String delete_group_notify_url;
	
	public String getOss_access_Key() {
		return oss_access_Key;
	}
	public void setOss_access_Key(String oss_access_Key) {
		this.oss_access_Key = oss_access_Key;
	}
	public String getOss_secret_Access_Key() {
		return oss_secret_Access_Key;
	}
	public void setOss_secret_Access_Key(String oss_secret_Access_Key) {
		this.oss_secret_Access_Key = oss_secret_Access_Key;
	}
	public String getOss_bucketName() {
		return oss_bucketName;
	}
	public void setOss_bucketName(String oss_bucketName) {
		this.oss_bucketName = oss_bucketName;
	}
	public String getOss_endpoint() {
		return oss_endpoint;
	}
	public void setOss_endpoint(String oss_endpoint) {
		this.oss_endpoint = oss_endpoint;
	}
	public String getOss_getUrl() {
		return oss_getUrl;
	}
	public void setOss_getUrl(String oss_getUrl) {
		this.oss_getUrl = oss_getUrl;
	}
    public String getCreate_group_notify_url() {
        return create_group_notify_url;
    }
    public void setCreate_group_notify_url(String create_group_notify_url) {
        this.create_group_notify_url = create_group_notify_url;
    }
    public String getDelete_group_notify_url() {
        return delete_group_notify_url;
    }
    public void setDelete_group_notify_url(String delete_group_notify_url) {
        this.delete_group_notify_url = delete_group_notify_url;
    }
}
