package org.tojoycloud.dubbo.rongba.api.entity;  

import java.util.Date;

import io.swagger.annotations.ApiModelProperty;




/**
 * 18
 * @author liujie
 * @date   2017-12-25 16:29:50
 */

public class InvestProduct extends BaseEntity implements java.io.Serializable {  

   
	private static final long serialVersionUID = 1L;
  

	@ApiModelProperty(value = "产品状态  在售等...")
	private Integer productStatus;     

	@ApiModelProperty(value = "产品详情描述")
	private String productDetail;     
	
	@ApiModelProperty(value = "产品名称")
	private String productName;     
	
	@ApiModelProperty(value = "募集日期")
	private Date placeDate;     
	
	@ApiModelProperty(value = "认购起点金额无小数位")
	private Integer startPoint;     
	
	@ApiModelProperty(value = "募集情况说明")
	private String introduce;     
	
	@ApiModelProperty(value = "投资类型 对应投资类型主键")
	private String investType;     
	
	@ApiModelProperty(value = "关联机构顾问")
	private String consultant;  
	
	@ApiModelProperty(value = "关联机构顾问对象")
	private OrgConsultant orgConsultant;    

	@ApiModelProperty(value = "发行机构名称")
	private String orgName;     
	
	@ApiModelProperty(value = "发行机构号")
	private Integer orgNo;     

	@ApiModelProperty(value = "投资期限")
	private String period;     
	
	@ApiModelProperty(value = "预计收益率(强制保留2位小数，不四舍五入)")
	private Float expectedRate;     

	@ApiModelProperty(value = "权重")
	private Integer ratio;     
	
	@ApiModelProperty(value = "是否显示")
	private String isShow;     
	
	@ApiModelProperty(value = "是否推荐 ,相当于置顶")
	private String isRecommend;
	
	public InvestProduct(){
	
	}
	
	public InvestProduct(String investType ){
		this.investType=investType;
	}
	
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
	public Date getPlaceDate() {
		return placeDate;
	}
	public void setPlaceDate(Date placeDate) {
		this.placeDate = placeDate;
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
	public String getInvestType() {
		return investType;
	}
	public void setInvestType(String investType) {
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
	public String getPeriod() {
		return period;
	}
	public void setPeriod(String period) {
		this.period = period;
	}
	public Float getExpectedRate() {
		return expectedRate;
	}
	public void setExpectedRate(Float expectedRate) {
		this.expectedRate = expectedRate;
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