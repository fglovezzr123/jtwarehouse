package com.wing.socialcontact.service.wx.bean;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * TJY_HTML_CONTENT html内容表
 * 
 * @author liangwj
 * @date 2017-04-04 00:05:28
 * @version 1.0
 */
@Table(name = "TJY_HTML_CONTENT")
public class HtmlContent implements Serializable{
	private static final long serialVersionUID = 1L;

    /**  */
    @Id
	@Column(name = "id")
	@GeneratedValue(generator = "UUID")
	private String id;

    /** 关联的业务主键 */
	private String fkid;

    /** 类型 */
	private Integer types;

    /**  */
	private String contents;
	@Transient
	private Map<String,Object>  extProps = new HashMap<String,Object>();
	public HtmlContent(){}


	/**
	 * 获取
	 */
	public String getId() {
    	return id;
    }
  	
	/**
	 * 设置
	 */
	public void setId(String id) {
    	this.id = id;
    }

	/**
	 * 获取关联的业务主键
	 */
	public String getFkid() {
    	return fkid;
    }
  	
	/**
	 * 设置关联的业务主键
	 */
	public void setFkid(String fkid) {
    	this.fkid = fkid;
    }

	/**
	 * 获取类型
	 */
	public Integer getTypes() {
    	return types;
    }
  	
	/**
	 * 设置类型
	 */
	public void setTypes(Integer types) {
    	this.types = types;
    }

	/**
	 * 获取
	 */
	public String getContents() {
    	return contents;
    }
  	
	/**
	 * 设置
	 */
	public void setContents(String contents) {
    	this.contents = contents;
    }
	public Map<String, Object> getExtProps() {
		return extProps;
	}

	public void setExtProps(Map<String, Object> extProps) {
		if(extProps==null){
			this.extProps.clear();
		}else{
			this.extProps = extProps;
		}
	}
	
	@SuppressWarnings("unchecked")
	public <T> T getExtProp(String key) {
		return (T) extProps.get(key);
	}
	
	public void setExtProp(String key ,Object value) {
		extProps.put(key, value);
	}
}
