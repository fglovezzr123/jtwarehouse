package com.tojoy.service.console.bean;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;



/**
 * 部门表对应实体
 */
@Table(name = "sy_dept")
public class SyDept implements java.io.Serializable {

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
	 * 部门序号
	 */
	@Column(name = "dept_sort")
	private Short deptSort;
	/**
	 * 部门名称
	 */
	@Column(name = "dept_name")
	private String deptName;
	/**
	 * 部门电话
	 */
	@Column(name = "dept_phone")
	private String deptPhone;
	/**
	 * 部门传真
	 */
	@Column(name = "dept_fax")
	private String deptFax;
	/**
	 * 部门地址
	 */
	@Column(name = "dept_address")
	private String deptAddress;
	/**
	 * 上级部门
	 */
	@Column(name = "super_id")
	private String superId;
	/**
	 * 部门主管
	 */
	@Column(name = "lead_uid")
	private String leadUid;
	/**
	 * 部门描述
	 */
	@Column(name = "dept_desc")
	private String deptDesc;
	

	// Constructors

	/** default constructor */
	public SyDept() {
	}

	/** minimal constructor */
	public SyDept(Short deptSort, String deptName, String superId) {
		this.deptSort = deptSort;
		this.deptName = deptName;
		this.superId = superId;
	}

	/** full constructor */
	public SyDept(Short deptSort, String deptName, String deptPhone,
			String deptFax, String deptAddress, String superId, String leadUid,
			String deptDesc) {
		this.deptSort = deptSort;
		this.deptName = deptName;
		this.deptPhone = deptPhone;
		this.deptFax = deptFax;
		this.deptAddress = deptAddress;
		this.superId = superId;
		this.leadUid = leadUid;
		this.deptDesc = deptDesc;
		
	}

	// Property accessors
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}
	public Short getDeptSort() {
		return this.deptSort;
	}

	public void setDeptSort(Short deptSort) {
		this.deptSort = deptSort;
	}
	public String getDeptName() {
		return this.deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	public String getDeptPhone() {
		return this.deptPhone;
	}

	public void setDeptPhone(String deptPhone) {
		this.deptPhone = deptPhone;
	}
	public String getDeptFax() {
		return this.deptFax;
	}

	public void setDeptFax(String deptFax) {
		this.deptFax = deptFax;
	}
	public String getDeptAddress() {
		return this.deptAddress;
	}

	public void setDeptAddress(String deptAddress) {
		this.deptAddress = deptAddress;
	}
	public String getSuperId() {
		return this.superId;
	}

	public void setSuperId(String superId) {
		this.superId = superId;
	}
	public String getLeadUid() {
		return this.leadUid;
	}

	public void setLeadUid(String leadUid) {
		this.leadUid = leadUid;
	}
	public String getDeptDesc() {
		return this.deptDesc;
	}

	public void setDeptDesc(String deptDesc) {
		this.deptDesc = deptDesc;
	}



}