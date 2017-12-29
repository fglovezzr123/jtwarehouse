package org.tojoycloud.dubbo.rongba.api.entity;

import io.swagger.annotations.ApiModelProperty;

/**
 * 
 * @author liujie
 * @date   2017-12-25 16:29:41
 */

public class InvestPerson extends BaseEntity implements java.io.Serializable {  

   
	private static final long serialVersionUID = 1L;
   
	@ApiModelProperty(value = "投资人名称")
	private String investName;     
	
	@ApiModelProperty(value = "股权融资投资人认证 0-未认证 1-已认证")
	private String accreditatedFinance;     
	
	@ApiModelProperty(value = "投吧投资人认证 0-未认证 1-已认证")
	private String accreditatedInvset;     

	@ApiModelProperty(value = "免责声明   0- 平台无责  1-未操作")
	private String noRelief;
	
	
	
	public String getInvestName() {
		return investName;
	}
	public void setInvestName(String investName) {
		this.investName = investName;
	}
	public String getAccreditatedFinance() {
		return accreditatedFinance;
	}
	public void setAccreditatedFinance(String accreditatedFinance) {
		this.accreditatedFinance = accreditatedFinance;
	}
	public String getAccreditatedInvset() {
		return accreditatedInvset;
	}
	public void setAccreditatedInvset(String accreditatedInvset) {
		this.accreditatedInvset = accreditatedInvset;
	}
	public String getNoRelief() {
		return noRelief;
	}
	public void setNoRelief(String noRelief) {
		this.noRelief = noRelief;
	}     
	
	
} 