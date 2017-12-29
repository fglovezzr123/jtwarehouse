package com.tojoy.service.console.bean;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * 地区表对应实体
 */
@Table(name = "sy_district")
public class SyDistrict implements java.io.Serializable {

	// Fields

	/**
	 * @Fields serialVersionUID : 
	 */

	@Transient
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "id")
	@GeneratedValue(generator = "UUID")
	private String id;
	/**
	 * 上级id 上级地区不能为空
	 */
	@Column(name = "super_id")
	private String superId;
	/**
	 * 排序号 顺序号必须为1-9999的整数
	 */
	@Column(name = "dis_sort")
	private Short disSort;
	/**
	 * 地区名称
	 * 地区名称长度必须在1-50之间
	 */
	@Column(name = "dis_name")
	private String disName;
	/**
	 * 地区说明
	 */
	@Column(name = "dis_desc")
	private String disDesc;
	
	/**
	 * 级别
	 */
	@Column(name = "type")
	private String type;
	
	// Constructors

	/** default constructor */
	public SyDistrict() {
	}

	/** minimal constructor */
	public SyDistrict(Short disSort, String disName) {
		this.disSort = disSort;
		this.disName = disName;
	}

	/** full constructor */
	public SyDistrict(String superId, Short disSort, String disName,
			String disDesc,String type) {
		this.superId = superId;
		this.disSort = disSort;
		this.disName = disName;
		this.disDesc = disDesc;
		this.type = type;
	}

	// Property accessors
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}
	public String getSuperId() {
		return this.superId;
	}

	public void setSuperId(String superId) {
		this.superId = superId;
	}
	public Short getDisSort() {
		return this.disSort;
	}

	public void setDisSort(Short disSort) {
		this.disSort = disSort;
	}
	public String getDisName() {
		return this.disName;
	}

	public void setDisName(String disName) {
		this.disName = disName;
	}
	public String getDisDesc() {
		return this.disDesc;
	}

	public void setDisDesc(String disDesc) {
		this.disDesc = disDesc;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}