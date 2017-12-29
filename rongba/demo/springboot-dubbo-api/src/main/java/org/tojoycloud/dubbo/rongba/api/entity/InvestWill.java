package org.tojoycloud.dubbo.rongba.api.entity;  

import java.util.ArrayList;
import java.util.List;
import java.util.Date;



/**
 * 9
 * @author liujie
 * @date   2017-12-25 16:30:01
 */

public class InvestWill extends BaseEntity implements java.io.Serializable {  

   
	private static final long serialVersionUID = 1L;

      
	/**
	 * 产品所在机构
	 */   
	private Integer orgNo;     
	/**
	 * 是否是保险  0-不是 1-是
	 */   
	private String isInsure;     
	/**
	 * 产品类型
	 */   
	private String investType;     
	/**
	 * 投资人意向的产品编号
	 */   
	private Integer productId;     
	/**
	 * 投资人名称
	 */   
	private String personName;     
	/**
	 * 投资人编号
	 */   
	private String person;   
	
	
	public Integer getOrgNo() {
		return orgNo;
	}
	public void setOrgNo(Integer orgNo) {
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
	public Integer getProductId() {
		return productId;
	}
	public void setProductId(Integer productId) {
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
	
	
} 