package com.wing.socialcontact.util.bean;

/**
 * 图文消息，一个图文消息支持1到8条图文
 * 
 * @ClassName: ArticlesBean
 * @Description: TODO
 * @author: zengmin
 * @date: 2017年4月17日 上午11:56:46
 */
public class ArticlesBean {
	/**
	 * 标题，不超过128个字节，超过会自动截断
	 */
	private String title;
	/**
	 * 描述，不超过512个字节，超过会自动截断
	 */
	private String description;
	/**
	 * 点击后跳转的链接
	 */
	private String url;
	/**
	 * 图文消息的图片链接，支持JPG、PNG格式，较好的效果为大图640*320，小图80*80。如不填，在客户端不显示图片
	 */
	private String picurl;
	
	public ArticlesBean() {
	}

	public ArticlesBean(String title) {
		this.title = title;
	}

	public ArticlesBean(String title, String description) {
		this.title = title;
		this.description = description;
	}

	public ArticlesBean(String title, String description, String url) {
		this.title = title;
		this.description = description;
		this.url = url;
	}

	public ArticlesBean(String title, String description, String url, String picurl) {
		this.title = title;
		this.description = description;
		this.url = url;
		this.picurl = picurl;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getPicurl() {
		return picurl;
	}

	public void setPicurl(String picurl) {
		this.picurl = picurl;
	}

	
}
