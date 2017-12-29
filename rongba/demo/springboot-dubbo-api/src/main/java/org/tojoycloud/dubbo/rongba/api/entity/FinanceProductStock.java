package org.tojoycloud.dubbo.rongba.api.entity;

import java.util.Date;

import io.swagger.annotations.ApiModelProperty;

/**
 * 21
 * @author liujie
 * @date   2017-12-25 16:29:35
 */

public class FinanceProductStock extends BaseEntity implements java.io.Serializable {  

   
	private static final long serialVersionUID = 1L;
   
      
	@ApiModelProperty(value = "项目进度 预热 募集 完成") 
	private Integer productStatus;     

	@ApiModelProperty(value = "产品详情描述") 
	private String productDetail;     

	@ApiModelProperty(value = "产品名称") 
	private String productName;     

	@ApiModelProperty(value = "亮点") 
	private String strengths;     

	@ApiModelProperty(value = "团队介绍") 
	private String teamDesc;     

	@ApiModelProperty(value = "行业") 
	private String industry;     

	@ApiModelProperty(value = "项目描述图片 多图逗号分隔") 
	private String descPicUrl;     

	@ApiModelProperty(value = "联系电话") 
	private String contractPhone;     
	
	@ApiModelProperty(value = "联系人") 
	private String contractName;     

	@ApiModelProperty(value = "地域") 
	private String region;     

	@ApiModelProperty(value = "合作方式") 
	private String cooperationType;     

	@ApiModelProperty(value = "募集情况说明") 
	private String introduce;     

	@ApiModelProperty(value = "发行机构名称") 
	private String orgName;     

	@ApiModelProperty(value = "发行机构号") 
	private Integer orgNo;     

	@ApiModelProperty(value = "目标金额") 
	private String expectedAmount;     

	@ApiModelProperty(value = "权重") 
	private Integer ratio;     

	@ApiModelProperty(value = "是否显示") 
	private String isShow;     

	@ApiModelProperty(value = "是否推荐") 
	private String isRecommend;
	
	@ApiModelProperty(value = "公司名称")
	private String company_name;

	@ApiModelProperty(value = "是否推荐")
	private Date build_date;

	@ApiModelProperty(value = "地域")
	private String regLocation;

	@ApiModelProperty(value = "法人")
	private String lawPerson;
	
	@ApiModelProperty(value = "BP计划书")
	private String bpBookUrl ;
	
	@ApiModelProperty(value = "路演计划书")
	private String roadShowUrl;
	
	@ApiModelProperty(value = "投资机构名")
	private String investOrgName;
	
	@ApiModelProperty(value = "投资金额")
	private String investAmount;
	
	@ApiModelProperty(value = "顾问职位")
	private String consulantTitle;
	
	@ApiModelProperty(value = "顾问")
	private String consulant;
	
	@ApiModelProperty(value = "顾问机构名")
	private String consulantOrgName;
	
	@ApiModelProperty(value = "顾问头像链接")
	private String consulantProfileUrl;

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
	public String getStrengths() {
		return strengths;
	}
	public void setStrengths(String strengths) {
		this.strengths = strengths;
	}
	public String getTeamDesc() {
		return teamDesc;
	}
	public void setTeamDesc(String teamDesc) {
		this.teamDesc = teamDesc;
	}
	public String getIndustry() {
		return industry;
	}
	public void setIndustry(String industry) {
		this.industry = industry;
	}
	public String getDescPicUrl() {
		return descPicUrl;
	}
	public void setDescPicUrl(String descPicUrl) {
		this.descPicUrl = descPicUrl;
	}
	public String getContractPhone() {
		return contractPhone;
	}
	public void setContractPhone(String contractPhone) {
		this.contractPhone = contractPhone;
	}
	public String getContractName() {
		return contractName;
	}
	public void setContractName(String contractName) {
		this.contractName = contractName;
	}
	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
	}
	public String getCooperationType() {
		return cooperationType;
	}
	public void setCooperationType(String cooperationType) {
		this.cooperationType = cooperationType;
	}
	public String getIntroduce() {
		return introduce;
	}
	public void setIntroduce(String introduce) {
		this.introduce = introduce;
	}
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	public Integer getOrgNo() {
		return orgNo;
	}
	public void setOrgNo(Integer orgNo) {
		this.orgNo = orgNo;
	}
	public String getExpectedAmount() {
		return expectedAmount;
	}
	public void setExpectedAmount(String expectedAmount) {
		this.expectedAmount = expectedAmount;
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
	public String getCompany_name() {
		return company_name;
	}
	public void setCompany_name(String company_name) {
		this.company_name = company_name;
	}
	public Date getBuild_date() {
		return build_date;
	}
	public void setBuild_date(Date build_date) {
		this.build_date = build_date;
	}
	public String getRegLocation() {
		return regLocation;
	}
	public void setRegLocation(String regLocation) {
		this.regLocation = regLocation;
	}
	public String getLawPerson() {
		return lawPerson;
	}
	public void setLawPerson(String lawPerson) {
		this.lawPerson = lawPerson;
	}
	public String getBpBookUrl() {
		return bpBookUrl;
	}
	public void setBpBookUrl(String bpBookUrl) {
		this.bpBookUrl = bpBookUrl;
	}
	public String getRoadShowUrl() {
		return roadShowUrl;
	}
	public void setRoadShowUrl(String roadShowUrl) {
		this.roadShowUrl = roadShowUrl;
	}
	public String getInvestOrgName() {
		return investOrgName;
	}
	public void setInvestOrgName(String investOrgName) {
		this.investOrgName = investOrgName;
	}
	public String getInvestAmount() {
		return investAmount;
	}
	public void setInvestAmount(String investAmount) {
		this.investAmount = investAmount;
	}
	public String getConsulantTitle() {
		return consulantTitle;
	}
	public void setConsulantTitle(String consulantTitle) {
		this.consulantTitle = consulantTitle;
	}
	public String getConsulant() {
		return consulant;
	}
	public void setConsulant(String consulant) {
		this.consulant = consulant;
	}
	public String getConsulantOrgName() {
		return consulantOrgName;
	}
	public void setConsulantOrgName(String consulantOrgName) {
		this.consulantOrgName = consulantOrgName;
	}
	public String getConsulantProfileUrl() {
		return consulantProfileUrl;
	}
	public void setConsulantProfileUrl(String consulantProfileUrl) {
		this.consulantProfileUrl = consulantProfileUrl;
	}     
	
   
} 