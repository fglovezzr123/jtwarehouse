package com.wing.socialcontact.sys.bean;



import java.util.Date;

/**
 * 融吧产品信息意向表
 * @author 郭晓朋
 *
 */
public class TjrbFinanceWill {
	
	/**
	 * 主键
	 */
	private int id;
	/**
	 * 产品所在机构
	 */
	private int orgNo;
	/**
	 * 期望金额
	 */
	private String willAmountj;
	/**
	 * 期望期限
	 */
	private String willPeriod;
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
	public String getWillAmountj() {
		return willAmountj;
	}
	public void setWillAmountj(String willAmountj) {
		this.willAmountj = willAmountj;
	}
	public String getWillPeriod() {
		return willPeriod;
	}
	public void setWillPeriod(String willPeriod) {
		this.willPeriod = willPeriod;
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
