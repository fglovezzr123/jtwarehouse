package org.tojoycloud.dubbo.rongba.api.entity;  


import io.swagger.annotations.ApiModelProperty;




/**
 * 9
 * @author liujie
 * @date   2017-12-25 16:29:39
 */

public class FinanceWill extends BaseEntity implements java.io.Serializable {  

   
	private static final long serialVersionUID = 1L;
   
	@ApiModelProperty(value = "产品所在机构") 
	private Integer orgNo;     
	
	@ApiModelProperty(value = "期望金额")    
	private String willAmount;     

	@ApiModelProperty(value = "期望期限") 
	private String willPeriod;     
	
	@ApiModelProperty(value = "意向的产品编号")    
	private Integer productId;     

	@ApiModelProperty(value = "投资人名称") 
	private String personName;     
	
	@ApiModelProperty(value = "投资人编号")    
	private String person;   
	
	
	public Integer getOrgNo() {
		return orgNo;
	}
	public void setOrgNo(Integer orgNo) {
		this.orgNo = orgNo;
	}
	public String getWillAmount() {
		return willAmount;
	}
	public void setWillAmount(String willAmount) {
		this.willAmount = willAmount;
	}
	public String getWillPeriod() {
		return willPeriod;
	}
	public void setWillPeriod(String willPeriod) {
		this.willPeriod = willPeriod;
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