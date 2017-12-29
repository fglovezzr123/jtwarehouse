package org.tojoycloud.dubbo.rongba.api.entity.initPage;  

import java.util.List;

import org.tojoycloud.dubbo.rongba.api.entity.BaseEntity;
import org.tojoycloud.dubbo.rongba.api.entity.InvestProduct;
import org.tojoycloud.dubbo.rongba.api.entity.InvestType;

import io.swagger.annotations.ApiModelProperty;




/**
 * 4
 * @author liujie
 * @date   2017-12-25 16:29:36
 */

public class InvestPage extends BaseEntity implements java.io.Serializable {  

	private static final long serialVersionUID = 1L;
          
	@ApiModelProperty(value = "投吧首页,初始化非保险类")
	private List<InvestType> investTypeList;  
	
	@ApiModelProperty(value = "投吧首页,非保险类产品列表")
	private List<InvestProduct> investProductList;

	public List<InvestType> getInvestTypeList() {
		return investTypeList;
	}

	public void setInvestTypeList(List<InvestType> investTypeList) {
		this.investTypeList = investTypeList;
	}

	public List<InvestProduct> getInvestProductList() {
		return investProductList;
	}

	public void setInvestProductList(List<InvestProduct> investProductList) {
		this.investProductList = investProductList;
	}  
	
	
   
} 