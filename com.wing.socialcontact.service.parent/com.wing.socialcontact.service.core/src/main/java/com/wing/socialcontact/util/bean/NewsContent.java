package com.wing.socialcontact.util.bean;

import java.util.List;

/**
 * 微信图文消息内容实体
 * @author zengmin
 *
 */
public class NewsContent {
	private List<ArticlesBean> articles;

	public List<ArticlesBean> getArticles() {
		return articles;
	}

	public void setArticles(List<ArticlesBean> articles) {
		this.articles = articles;
	}

	public NewsContent(List<ArticlesBean> articles) {
		this.articles = articles;
	}

	public NewsContent() {
	}

	
}
