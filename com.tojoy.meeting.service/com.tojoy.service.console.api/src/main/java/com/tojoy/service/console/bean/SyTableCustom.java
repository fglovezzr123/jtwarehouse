package com.tojoy.service.console.bean;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;


/**
 * 自定义打印导出设置表实体
 */
@Table(name = "sy_table_custom")
public class SyTableCustom implements java.io.Serializable {

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
	 * 类型，不同类型对应不同表
	 */
	@Column(name = "tb_type")
	private Short tbType;
	/**
	 * 顺序号
	 */
	@Column(name = "field_sort")
	private Short fieldSort;
	/**
	 * 表字段对应的javaBean属性名称
	 */
	@Column(name = "field_name")
	private String fieldName;
	/**
	 * 属性类型
	 */
	@Column(name = "field_type")
	private Short fieldType;
	/**
	 * 默认的显示名
	 */
	@Column(name = "field_title")
	private String fieldTitle;
	/**
	 * 显示的别名，如果没有则显示默认名称
	 */
	@Column(name = "field_another_title")
	private String fieldAnotherTitle;
	/**
	 * 是否导出，1：导出，0：不导出
	 */
	@Column(name = "is_export")
	private Short isExport;
	/**
	 * 是否打印,1:打印，0,不打印
	 */
	@Column(name = "is_print")
	private Short isPrint;
	/**
	 * 是否显示，1：显示，0：不显示
	 */
	@Column(name = "is_show")
	private Short isShow;

	// Constructors

	/** default constructor */
	public SyTableCustom() {
	}

	/** minimal constructor */
	public SyTableCustom(Short tbType, Short fieldSort, String fieldName,
			Short fieldType, String fieldTitle, Short isExport, Short isPrint,
			Short isShow) {
		this.tbType = tbType;
		this.fieldSort = fieldSort;
		this.fieldName = fieldName;
		this.fieldType = fieldType;
		this.fieldTitle = fieldTitle;
		this.isExport = isExport;
		this.isPrint = isPrint;
		this.isShow = isShow;
	}

	/** full constructor */
	public SyTableCustom(Short tbType, Short fieldSort, String fieldName,
			Short fieldType, String fieldTitle, String fieldAnotherTitle,
			Short isExport, Short isPrint, Short isShow) {
		this.tbType = tbType;
		this.fieldSort = fieldSort;
		this.fieldName = fieldName;
		this.fieldType = fieldType;
		this.fieldTitle = fieldTitle;
		this.fieldAnotherTitle = fieldAnotherTitle;
		this.isExport = isExport;
		this.isPrint = isPrint;
		this.isShow = isShow;
	}

	// Property accessors
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}
	public Short getTbType() {
		return this.tbType;
	}

	public void setTbType(Short tbType) {
		this.tbType = tbType;
	}
	public Short getFieldSort() {
		return this.fieldSort;
	}

	public void setFieldSort(Short fieldSort) {
		this.fieldSort = fieldSort;
	}
	public String getFieldName() {
		return this.fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	public Short getFieldType() {
		return this.fieldType;
	}

	public void setFieldType(Short fieldType) {
		this.fieldType = fieldType;
	}
	public String getFieldTitle() {
		return this.fieldTitle;
	}

	public void setFieldTitle(String fieldTitle) {
		this.fieldTitle = fieldTitle;
	}
	public String getFieldAnotherTitle() {
		return this.fieldAnotherTitle;
	}

	public void setFieldAnotherTitle(String fieldAnotherTitle) {
		this.fieldAnotherTitle = fieldAnotherTitle;
	}
	public Short getIsExport() {
		return this.isExport;
	}

	public void setIsExport(Short isExport) {
		this.isExport = isExport;
	}
	public Short getIsPrint() {
		return this.isPrint;
	}

	public void setIsPrint(Short isPrint) {
		this.isPrint = isPrint;
	}
	public Short getIsShow() {
		return this.isShow;
	}

	public void setIsShow(Short isShow) {
		this.isShow = isShow;
	}

}