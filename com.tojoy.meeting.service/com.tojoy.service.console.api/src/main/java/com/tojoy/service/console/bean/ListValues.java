package com.tojoy.service.console.bean;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;



/**
 * 数据类型字典表对应实体
 *
 */
@Table(name = "list_values")
public class ListValues implements java.io.Serializable {


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
	 * 字典值类型
	 */
	@Column(name = "list_type")
	private Integer listType;
	/**
	 * 字典值
	 */
	@Column(name = "list_value")
	private String listValue;
	/**
	 * 备注
	 */
	@Column(name = "list_desc")
	private String listDesc;
	/**排序*/
	@Column(name = "sortno")
	private Integer sortno = 0;
	/**逻辑删除标识*/
	@Column(name = "deleted")
	private Integer deleted = 0;
	
	// Constructors

	/** default constructor */
	public ListValues() {
	}

	/** minimal constructor */
	public ListValues(Integer listType, String listValue) {
		this.listType = listType;
		this.listValue = listValue;
	}

	/** full constructor */
	public ListValues(Integer listType, String listValue, String listDesc) {
		this.listType = listType;
		this.listValue = listValue;
		this.listDesc = listDesc;
	}

	// Property accessors
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}
	public Integer getListType() {
		return this.listType;
	}

	public void setListType(Integer listType) {
		this.listType = listType;
	}
	public String getListValue() {
		return this.listValue;
	}

	public void setListValue(String listValue) {
		this.listValue = listValue;
	}
	public String getListDesc() {
		return this.listDesc;
	}

	public void setListDesc(String listDesc) {
		this.listDesc = listDesc;
	}

	public Integer getSortno() {
		return sortno==null?0:sortno;
	}

	public void setSortno(Integer sortno) {
		this.sortno = (sortno==null?0:sortno);
	}

	public Integer getDeleted() {
		return deleted==null?0:deleted;
	}

	public void setDeleted(Integer deleted) {
		this.deleted = (deleted==null?0:deleted);
	}

}