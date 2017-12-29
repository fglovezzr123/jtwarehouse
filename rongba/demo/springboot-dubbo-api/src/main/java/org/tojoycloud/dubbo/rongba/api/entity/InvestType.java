package org.tojoycloud.dubbo.rongba.api.entity;  


import io.swagger.annotations.ApiModelProperty;


/**
 * 5
 * @author liujie
 * @date   2017-12-25 16:29:59
 */

public class InvestType extends BaseEntity implements java.io.Serializable {  

   
	private static final long serialVersionUID = 1L;
   
      
	@ApiModelProperty(value = "是否所属保险类别 0- 不是 1- 是")
	private String isInsure;   
	
	@ApiModelProperty(value = "投资方式即是产品名称：阳光私募集等")
	private String investName;    
	
	
	public String getIsInsure() {
		return isInsure;
	}
	public void setIsInsure(String isInsure) {
		this.isInsure = isInsure;
	}
	public String getInvestName() {
		return investName;
	}
	public void setInvestName(String investName) {
		this.investName = investName;
	}
	
	
} 