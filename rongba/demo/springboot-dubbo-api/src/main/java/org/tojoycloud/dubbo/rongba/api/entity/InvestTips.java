package org.tojoycloud.dubbo.rongba.api.entity;

import io.swagger.annotations.ApiModelProperty;

/**
 * 4
 * @author liujie
 * @date   2017-12-25 16:29:58
 */

public class InvestTips extends BaseEntity implements java.io.Serializable {  

   
	private static final long serialVersionUID = 1L;
 
	@ApiModelProperty(value = "投注人认证时 温馨提示内容")
	private String tipsContent;   
	
	public String getTipsContent() {
		return tipsContent;
	}
	public void setTipsContent(String tipsContent) {
		this.tipsContent = tipsContent;
	}
	
	
} 