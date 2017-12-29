package org.tojoycloud.dubbo.rongba.api.entity;

import io.swagger.annotations.ApiModelProperty;

/**
 * 6
 * @author liujie
 * @date   2017-12-25 16:29:22
 */
public class BpPersonProject extends BaseEntity implements java.io.Serializable {  

   
	private static final long serialVersionUID = 1L;
  
	@ApiModelProperty(value = "投资人Id")
	private String bpId;     
	
	@ApiModelProperty(value = "项目编号")
	private String productId;     

	@ApiModelProperty(value = "风头项目 分为已投，收藏 ，收到")
	private Integer actionType;     
	
	
	public String getBpId() {
		return bpId;
	}
	public void setBpId(String bpId) {
		this.bpId = bpId;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public Integer getActionType() {
		return actionType;
	}
	public void setActionType(Integer actionType) {
		this.actionType = actionType;
	}
	
	
} 