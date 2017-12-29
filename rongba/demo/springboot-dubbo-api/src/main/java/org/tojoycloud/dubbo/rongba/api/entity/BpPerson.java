package org.tojoycloud.dubbo.rongba.api.entity;

import io.swagger.annotations.ApiModelProperty;

/**
 * 10
 * @author liujie
 * @date   2017-12-25 16:29:21
 */
public class BpPerson extends BaseEntity implements java.io.Serializable {  

   
	private static final long serialVersionUID = 1L;
   
	@ApiModelProperty(value = "投资人名称")
	private String bpName;     

	@ApiModelProperty(value = "机构代码")
	private String orgNo;     

	@ApiModelProperty(value = "倾向轮次")
	private String tendency;     

	@ApiModelProperty(value = "机构名称")
	private String orgName;     

	@ApiModelProperty(value = "关注行业")
	private String focusOn;     

	@ApiModelProperty(value = "履历")
	private String curriculumVitae;     

	@ApiModelProperty(value = "职位")
	private String title;
	
	
	public String getBpName() {
		return bpName;
	}
	
	public void setBpName(String bpName) {
		this.bpName = bpName;
	}
	
	public String getOrgNo() {
		return orgNo;
	}
	
	public void setOrgNo(String orgNo) {
		this.orgNo = orgNo;
	}
	
	public String getTendency() {
		return tendency;
	}
	
	public void setTendency(String tendency) {
		this.tendency = tendency;
	}
	
	public String getOrgName() {
		return orgName;
	}
	
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	
	public String getFocusOn() {
		return focusOn;
	}
	
	public void setFocusOn(String focusOn) {
		this.focusOn = focusOn;
	}
	
	public String getCurriculumVitae() {
		return curriculumVitae;
	}
	
	public void setCurriculumVitae(String curriculumVitae) {
		this.curriculumVitae = curriculumVitae;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}     
	  
	
	
} 