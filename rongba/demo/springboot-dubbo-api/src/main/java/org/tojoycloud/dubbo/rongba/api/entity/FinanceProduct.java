package org.tojoycloud.dubbo.rongba.api.entity;  

import io.swagger.annotations.ApiModelProperty;
 

/**
 * 21
 * @author liujie
 * @date   2017-12-25 16:29:29
 */
 
public class FinanceProduct extends BaseEntity implements java.io.Serializable {  

   
	private static final long serialVersionUID = 1L;
   
      
	@ApiModelProperty(value = "产品状态  在售等...") 
	private Integer productStatus;     

	@ApiModelProperty(value = "产品详情描述...")
	private String productDetail;     

	@ApiModelProperty(value = "产品名称...")
	private String productName;     

	@ApiModelProperty(value = "融资类型")
	private Integer financeType;     

	@ApiModelProperty(value = "融资金额")
	private String financeAmount;     

	@ApiModelProperty(value = "融资期限")
	private String financePeriod;     

	@ApiModelProperty(value = "融资情况说明")
	private String introduce;     

	private Integer investType;     

	@ApiModelProperty(value = "关联顾问")
	private String consultant;     
	
	@ApiModelProperty(value = "顾问对象")
	private OrgConsultant orgConsultant;     
	
	@ApiModelProperty(value = "发行机构名称")
	private String orgName;     
	
	@ApiModelProperty(value = "发行机构号")
	private Integer orgNo;     

	@ApiModelProperty(value = "所需材料")
	private String material;     

	@ApiModelProperty(value = "申请条件")
	private String condition;     

	@ApiModelProperty(value = "月供")
	private Float monthSupply;     

	@ApiModelProperty(value = "利息")
	private Float interest;     

	@ApiModelProperty(value = "权重")
	private Integer ratio;     

	@ApiModelProperty(value = "是否显示")
	private String isShow;     

	@ApiModelProperty(value = "是否推荐")
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
	public Integer getFinanceType() {
		return financeType;
	}
	public void setFinanceType(Integer financeType) {
		this.financeType = financeType;
	}
	public String getFinanceAmount() {
		return financeAmount;
	}
	public void setFinanceAmount(String financeAmount) {
		this.financeAmount = financeAmount;
	}
	public String getFinancePeriod() {
		return financePeriod;
	}
	public void setFinancePeriod(String financePeriod) {
		this.financePeriod = financePeriod;
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
	public Integer getOrgNo() {
		return orgNo;
	}
	public void setOrgNo(Integer orgNo) {
		this.orgNo = orgNo;
	}
	public String getMaterial() {
		return material;
	}
	public void setMaterial(String material) {
		this.material = material;
	}
	public String getCondition() {
		return condition;
	}
	public void setCondition(String condition) {
		this.condition = condition;
	}
	public Float getMonthSupply() {
		return monthSupply;
	}
	public void setMonthSupply(Float monthSupply) {
		this.monthSupply = monthSupply;
	}
	public Float getInterest() {
		return interest;
	}
	public void setInterest(Float interest) {
		this.interest = interest;
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