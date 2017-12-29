package org.tojoycloud.dubbo.rongba.api.entity;  


import io.swagger.annotations.ApiModelProperty;




/**
 * 4
 * @author liujie
 * @date   2017-12-25 16:29:36
 */

public class FinanceTips extends BaseEntity implements java.io.Serializable {  

	private static final long serialVersionUID = 1L;
          
	@ApiModelProperty(value = "类型 0-免责申明 1-温馨提示")
	private Integer tipsType;
	
	@ApiModelProperty(value = "提示内容")
	private String tipsContent;  
	
	
	public String getTipsContent() {
		return tipsContent;
	}
	public void setTipsContent(String tipsContent) {
		this.tipsContent = tipsContent;
	}
	public Integer getTipsType() {
		return tipsType;
	}
	public void setTipsType(Integer tipsType) {
		this.tipsType = tipsType;
	}
	
   
} 