package com.wing.socialcontact.service.im.bean;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TJY_IM_GROUPFAV 群爱好标签
 * 
 * @author xuxinyuan
 * @date 2017-04-03 18:19:52
 * @version 1.0
 */
@Table(name = "TJY_IM_GROUPFAV")
public class ImGroupfav implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

    /** 主键 */
    @Id
	@Column(name = "id")
	@GeneratedValue(generator = "UUID")
	private String id;

    /** 群主键 */
	private String groupId;

    /** 喜好编码(关联字典表) */
	private String favId;

    /** 排序 */
	private Integer sortno;

	public ImGroupfav(){}


	/**
	 * 获取主键
	 */
	public String getId() {
    	return id;
    }
  	
	/**
	 * 设置主键
	 */
	public void setId(String id) {
    	this.id = id;
    }

	/**
	 * 获取群主键
	 */
	public String getGroupId() {
    	return groupId;
    }
  	
	/**
	 * 设置群主键
	 */
	public void setGroupId(String groupId) {
    	this.groupId = groupId;
    }

	/**
	 * 获取喜好编码(关联字典表)
	 */
	public String getFavId() {
    	return favId;
    }
  	
	/**
	 * 设置喜好编码(关联字典表)
	 */
	public void setFavId(String favId) {
    	this.favId = favId;
    }

	/**
	 * 获取排序
	 */
	public Integer getSortno() {
    	return sortno;
    }
  	
	/**
	 * 设置排序
	 */
	public void setSortno(Integer sortno) {
    	this.sortno = sortno;
    }
}
