package org.tojoycloud.dubbo.rongba.api.entity;

import io.swagger.annotations.ApiModelProperty;

/**
 * 21
 * @author liujie
 * @date   2017-12-25 16:29:57
 */

public class InvestProductInsure extends BaseEntity implements java.io.Serializable {  

   
	private static final long serialVersionUID = 1L;
 
	@ApiModelProperty(value = " 产品状态  在售等..")
	private Integer productStatus;     

	@ApiModelProperty(value = " 产品详情描述")
	private String productDetail;     

	@ApiModelProperty(value = " 产品名称")
	private String productName;     
	
	@ApiModelProperty(value = " 最低投保额")
	private Integer startPoint;     

	@ApiModelProperty(value = " 介绍")
	private String introduce;     
	
	
	@ApiModelProperty(value = "  投资类型 对应投资类型表主键")
	private Integer investType;     
	
	
	@ApiModelProperty(value = "  关联机构顾问")
	private String consultant;  
	
	@ApiModelProperty(value = " 顾问对象信息")
	private OrgConsultant orgConsultant; 
	
	@ApiModelProperty(value = "发行机构名称")
	private String orgName;     

	@ApiModelProperty(value = "亮点1")
	private String strengths1;     

	@ApiModelProperty(value = " 亮点2")
	private String strengths2;     

	@ApiModelProperty(value = "亮点3")
	private String strengths3;     

	@ApiModelProperty(value = "亮点4")
	private String strengths4;     

	@ApiModelProperty(value = " 发行机构号")
	private Integer orgNo;     

	@ApiModelProperty(value = " 针对人群")
	private String crowdType;     

	@ApiModelProperty(value = " 最高保额")
	private Integer expectedReturn;     

	@ApiModelProperty(value = "权重")
	private Integer ratio;     

	@ApiModelProperty(value = " 是否显示")
	private String isShow;     

	@ApiModelProperty(value = " 是否推荐")
	private String isRecommend;
	
	
	public Integer getProductStatus() {
		return productStatus;
	}
	public void setProductStatus(Integer productStatus) {
		this.productStatus = productStatus;
	}
	public String getProductDetail() {
		return productDetail;
	}
	public void setProductDetail(String productDetail) {
		this.productDetail = productDetail;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public Integer getStartPoint() {
		return startPoint;
	}
	public void setStartPoint(Integer startPoint) {
		this.startPoint = startPoint;
	}
	public String getIntroduce() {
		return introduce;
	}
	public void setIntroduce(String introduce) {
		this.introduce = introduce;
	}
	public Integer getInvestType() {
		return investType;
	}
	public void setInvestType(Integer investType) {
		this.investType = investType;
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
	public String getStrengths1() {
		return strengths1;
	}
	public void setStrengths1(String strengths1) {
		this.strengths1 = strengths1;
	}
	public String getStrengths2() {
		return strengths2;
	}
	public void setStrengths2(String strengths2) {
		this.strengths2 = strengths2;
	}
	public String getStrengths3() {
		return strengths3;
	}
	public void setStrengths3(String strengths3) {
		this.strengths3 = strengths3;
	}
	public String getStrengths4() {
		return strengths4;
	}
	public void setStrengths4(String strengths4) {
		this.strengths4 = strengths4;
	}
	public Integer getOrgNo() {
		return orgNo;
	}
	public void setOrgNo(Integer orgNo) {
		this.orgNo = orgNo;
	}
	public String getCrowdType() {
		return crowdType;
	}
	public void setCrowdType(String crowdType) {
		this.crowdType = crowdType;
	}
	public Integer getExpectedReturn() {
		return expectedReturn;
	}
	public void setExpectedReturn(Integer expectedReturn) {
		this.expectedReturn = expectedReturn;
	}
	public Integer getRatio() {
		return ratio;
	}
	public void setRatio(Integer ratio) {
		this.ratio = ratio;
	}
	public String getIsShow() {
		return isShow;
	}
	public void setIsShow(String isShow) {
		this.isShow = isShow;
	}
	public String getIsRecommend() {
		return isRecommend;
	}
	public void setIsRecommend(String isRecommend) {
		this.isRecommend = isRecommend;
	}
	public OrgConsultant getOrgConsultant() {
		return orgConsultant;
	}
	public void setOrgConsultant(OrgConsultant orgConsultant) {
		this.orgConsultant = orgConsultant;
	}     
	

} 