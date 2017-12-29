package com.wing.socialcontact.sys.bean;

import java.util.Date;

/**
 * 投资意愿表
 * @author 郭晓朋
 *
 */
public class TjrbInvestWill {
	
	/**
	 * 主键
	 */
	private int id;
	/**
	 * 产品所在机构
	 */
	private int orgNo;
	/**
	 * 是否是保险
	 */
	private String isInsure;
	/**
	 * 产品类型
	 */
	private String investType;
	/**
	 * 投资人意向的产品编号
	 */
	private int productId;
	/**
	 * 投资人名称
	 */
	private String personName;
	/**
	 * 投资人编号
	 */
	private String person;
	/**
	 * 修改时间
	 */
	private Date updateDate;
	/**
	 * 创建时间
	 */
	private Date createDate;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getOrgNo() {
		return orgNo;
	}
	public void setOrgNo(int orgNo) {
		this.orgNo = orgNo;
	}
	public String getIsInsure() {
		return isInsure;
	}
	public void setIsInsure(String isInsure) {
		this.isInsure = isInsure;
	}
	public String getInvestType() {
		return investType;
	}
	public void setInvestType(String investType) {
		this.investType = investType;
	}
	public int getProductId() {
		return productId;
	}
	public void setProductId(int productId) {
		this.productId = productId;
	}
	public String getPersonName() {
		return personName;
	}
	public void setPersonName(String personName) {
		this.personName = personName;
	}
	public String getPerson() {
		return person;
	}
	public void setPerson(String person) {
		this.person = person;
	}
	public Date getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	
}
