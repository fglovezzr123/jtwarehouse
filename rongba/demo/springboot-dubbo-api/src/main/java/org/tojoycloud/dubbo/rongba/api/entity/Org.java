package org.tojoycloud.dubbo.rongba.api.entity;

import io.swagger.annotations.ApiModelProperty;

/**
 * 7
 * @author liujie
 * @date   2017-12-25 16:30:03
 */

public class Org extends BaseEntity implements java.io.Serializable {  

   
	private static final long serialVersionUID = 1L;
  
	@ApiModelProperty(value = " 机构代码")
	private String orgNo;     
	
	@ApiModelProperty(value = " 机构名称")
	private String orgName;     
	
	@ApiModelProperty(value = " 机构简介包含机构简介图片")
	private String orgProfile;     
	
	@ApiModelProperty(value = " 机构logo图")
	private String orgLogo;  
	
	
	public String getOrgNo() {
		return orgNo;
	}
	public void setOrgNo(String orgNo) {
		this.orgNo = orgNo;
	}
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	public String getOrgProfile() {
		return orgProfile;
	}
	public void setOrgProfile(String orgProfile) {
		this.orgProfile = orgProfile;
	}
	public String getOrgLogo() {
		return orgLogo;
	}
	public void setOrgLogo(String orgLogo) {
		this.orgLogo = orgLogo;
	}
	
} 