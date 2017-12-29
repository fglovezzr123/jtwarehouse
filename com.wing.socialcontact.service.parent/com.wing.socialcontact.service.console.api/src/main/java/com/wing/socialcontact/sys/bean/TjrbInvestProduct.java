package com.wing.socialcontact.sys.bean;

import java.io.Serializable;

/**
 * 投吧产品非保险信息表
 * @author 郭晓朋
 */

import java.util.Date;

import javax.persistence.*;

@Table(name = "tjrb_invest_product")
public class TjrbInvestProduct implements Serializable {

	private static final long serialVersionUID = 1L;
	//主键
    @Id
    @GeneratedValue(generator = "JDBC")
    @Column(name = "id")
	private Long id;
	//产品状态
    @Column(name = "product_status")
	private int productStatus;
	//产品详情描述
    @Column(name = "product_detail")
	private String productDetail;
	//产品名称
    @Column(name = "product_name")
	private String productName;
	//募集日期
    @Column(name = "place_date")
	private Date placeDate;
	//认购起点金额
    @Column(name = "start_point")
	private int startPoint;
	//募集情况说明
    @Column(name = "introduce")
	private String introduce;
	//投资类型 对应投资类型表主键
    @Column(name = "invest_type")
	private int investType;
	//关联机构顾问
    @Column(name = "consultant")
	private String consultant;
	//发行机构名称
    @Column(name = "org_name")
	private String orgName;
	//发行机构号
    @Column(name = "org_no")
	private int orgNo;
	//投资期限
    @Column(name = "period")
	private String period;
	//预计收益率(强制保留2位小数，不四舍五入)
    @Column(name = "expected_rate")
	private float expectedRate;
	//权重
    @Column(name = "ratio")
	private int ratio;
	//是否显示
    @Column(name = "is_show")
	private String isShow;
	//是否推荐
    @Column(name = "is_recommend")
	private String isRecommend;
	//创建时间
    @Column(name = "create_date")
	private Date createDate;
	//修改时间
    @Column(name = "update_date")
	private Date updateDate;

    @Transient
    private Integer status;


	public Long getId() {
		return id;
	}
	public void setId(Long id) {
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
	public Date getPlaceDate() {
		return placeDate;
	}
	public void setPlaceDate(Date placeDate) {
		this.placeDate = placeDate;
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
	public int getOrgNo() {
		return orgNo;
	}
	public void setOrgNo(int orgNo) {
		this.orgNo = orgNo;
	}
	public String getPeriod() {
		return period;
	}
	public void setPeriod(String period) {
		this.period = period;
	}
	public float getExpectedRate() {
		return expectedRate;
	}
	public void setExpectedRate(float expectedRate) {
		this.expectedRate = expectedRate;
	}
	public int getRatio() {
		return ratio;
	}
	public void setRatio(int ratio) {
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
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public Date getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
}
