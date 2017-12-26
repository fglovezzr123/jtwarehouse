package com.wing.socialcontact.sys.bean;

import java.util.Date;

/**
 * 投吧产品保险信息表
 * @author 王耀民
 */
public class TjrbInvestProductInsure {
	/**
	 *   `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `product_status` int(4) DEFAULT NULL COMMENT '产品状态  在售等...',
  `product_detail` longtext COMMENT '产品详情描述',
  `product_name` varchar(255) DEFAULT NULL COMMENT '产品名称',
  `start_point` int(11) DEFAULT NULL COMMENT '认购起点金额无小数位',
  `introduce` varchar(2000) DEFAULT NULL COMMENT '募集情况说明',
  `invest_type` int(11) DEFAULT NULL COMMENT '投资类型 对应投资类型表主键',
  `consultant` varchar(64) DEFAULT NULL COMMENT '关联机构顾问',
  `org_name` varchar(255) DEFAULT NULL COMMENT '发行机构名称',
  `strengths1` varchar(255) DEFAULT NULL COMMENT '亮点1',
  `strengths2` varchar(255) DEFAULT NULL COMMENT '亮点2',
  `strengths3` varchar(255) DEFAULT NULL COMMENT '亮点3',
  `strengths4` varchar(255) DEFAULT NULL COMMENT '亮点4',
  `org_no` int(11) DEFAULT NULL COMMENT '发行机构号',
  `crowd_type` varchar(255) DEFAULT NULL COMMENT '针对人群',
  `expected_return` int(20) DEFAULT NULL COMMENT '最高保额',
  `update_date` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `create_date` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
	 */
	
	private int id;
	private int productStatus;
	private String productDetail;
	private String productName;
	private int startPoint;
	private String introduce;
	private int investType;
	private String consultant;
	private String orgName;
	private String strengths1;
	private String strengths2;
	private String strengths3;
	private String strengths4;
	private int orgNo;
	private String crowdType;
	private Long expectedReturn;
	private Date updateDate;
	private Date createDate;
	/**
	 * 是否显示
	 */
	private String isShow;
	/**
	 * 是否推荐
	 */
	private String isRecommend;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getProductStatus() {
		return productStatus;
	}
	public void setProductStatus(int productStatus) {
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
	public int getStartPoint() {
		return startPoint;
	}
	public void setStartPoint(int startPoint) {
		this.startPoint = startPoint;
	}
	public String getIntroduce() {
		return introduce;
	}
	public void setIntroduce(String introduce) {
		this.introduce = introduce;
	}
	public int getInvestType() {
		return investType;
	}
	public void setInvestType(int investType) {
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
	public int getOrgNo() {
		return orgNo;
	}
	public void setOrgNo(int orgNo) {
		this.orgNo = orgNo;
	}
	public String getCrowdType() {
		return crowdType;
	}
	public void setCrowdType(String crowdType) {
		this.crowdType = crowdType;
	}
	public Long getExpectedReturn() {
		return expectedReturn;
	}
	public void setExpectedReturn(Long expectedReturn) {
		this.expectedReturn = expectedReturn;
	}
	public Date getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
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
	

}
