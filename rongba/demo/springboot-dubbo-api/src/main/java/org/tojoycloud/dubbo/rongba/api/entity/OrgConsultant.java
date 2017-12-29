package org.tojoycloud.dubbo.rongba.api.entity;  


import io.swagger.annotations.ApiModelProperty;




/**
 * 7
 * @author liujie
 * @date   2017-12-25 16:30:05
 */

public class OrgConsultant extends BaseEntity implements java.io.Serializable {  

   
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = " 职位")
	private String title;     
 
	@ApiModelProperty(value = " 顾问名称")
	private String consultant;     

	@ApiModelProperty(value = " 机构名称")
	private String orgName;     

	@ApiModelProperty(value = " 所属机构编号")
	private String orgNo;  
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getConsultant() {
		return consultant;
	}
	public void setConsultant(String consultant) {
		this.consultant = consultant;
	}
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	public String getOrgNo() {
		return orgNo;
	}
	public void setOrgNo(String orgNo) {
		this.orgNo = orgNo;
	}

} 