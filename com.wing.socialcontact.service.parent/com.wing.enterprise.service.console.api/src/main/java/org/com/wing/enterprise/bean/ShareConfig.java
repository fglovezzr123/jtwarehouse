package org.com.wing.enterprise.bean;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
/**
 * 
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Company: </p> 
 * @author zhangzheng
 * @Date  2017年5月10日 下午4:33:12
 */
@Table(name = "qfy_share_config")
public class ShareConfig implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "id")
    @GeneratedValue(generator = "UUID")
	private String  id;
	/**
	 * 文本
	 */
	private String content;
	/**
	 * 链接
	 */
	private String link;
	/**
	 * 图片
	 */
	private String imagePath;
	/**
	 * 标题
	 */
	private String title;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public String getImagePath() {
		return imagePath;
	}
	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	
}
