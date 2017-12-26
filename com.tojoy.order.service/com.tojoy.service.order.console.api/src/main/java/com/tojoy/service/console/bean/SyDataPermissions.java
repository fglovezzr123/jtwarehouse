package com.tojoy.service.console.bean;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;


/**
 * 数据权限
 */
@Table(name = "sy_data_permissions")
public class SyDataPermissions implements java.io.Serializable {

	// Fields

	/**
	 * @Fields serialVersionUID : 
	 */
	//@Transient
	//private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "UUID")
	private String id;
	/**
	 * 模块名称
	 */
	@Column(name = "_name")
	private String name;
	/**
	 * 模块类型
	 */
	@Column(name = "_type")
	private String type;
	/**
	 * 规则
	 */
	@Column(name = "_rules")
	private String rules;
	/**
	 * 说明
	 */
	@Column(name = "_desc")
	private String desc;

	// Constructors

	/** default constructor */
	public SyDataPermissions() {
	}

	/** minimal constructor */
	public SyDataPermissions(String name, String type) {
		this.name = name;
		this.type = type;
	}

	/** full constructor */
	public SyDataPermissions(String name, String type, String rules, String desc) {
		this.name = name;
		this.type = type;
		this.rules = rules;
		this.desc = desc;
	}

	// Property accessors
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getRules() {
		return this.rules;
	}

	public void setRules(String rules) {
		this.rules = rules;
	}

	public String getDesc() {
		return this.desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

}