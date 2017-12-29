package com.wing.socialcontact.sys.bean;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by fenggang on 12/24/17.
 *
 * @author fenggang
 * @date 12/24/17
 */
@Table(name = "tjrb_finance_product_stock")
public class FinanceProductStock implements Serializable {
    private static final long serialVersionUID = 477670072422550245L;
    /**
     * `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
     `product_status` int(4) DEFAULT NULL COMMENT '产品状态  在售等...',
     `product_detail` longtext COMMENT '产品详情描述',
     `product_name` varchar(255) DEFAULT NULL COMMENT '产品名称',
     `finance_type` int(4) DEFAULT NULL COMMENT '融资类型  具体参照原型',
     `finance_amount` varchar(255) DEFAULT NULL COMMENT '融资金额',
     `finance_period` varchar(64) DEFAULT NULL COMMENT '融资期限',
     `introduce` varchar(2000) DEFAULT NULL COMMENT '募集情况说明',
     `invest_type` int(11) DEFAULT NULL COMMENT '投资类型 对应投资类型表主键',
     `consultant` varchar(64) DEFAULT NULL COMMENT '关联机构顾问',
     `org_name` varchar(255) DEFAULT NULL COMMENT '发行机构名称',
     `org_no` int(11) DEFAULT NULL COMMENT '发行机构号',
     `material` varchar(1000) DEFAULT NULL COMMENT '所需材料',
     `condition` varchar(1000) DEFAULT NULL COMMENT '申请条件',
     `month_supply` float(10,2) DEFAULT NULL COMMENT '月供',
     `interest` float(10,2) DEFAULT NULL COMMENT '利息',
     `update_date` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
     `create_date` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
     PRIMARY KEY (`id`)
     */

    @Id
    @GeneratedValue(generator = "JDBC")
    @Column(name = "id")
    private Long id;
    @Column(name = "product_status")
    private int productStatus;//产品状态  在售等...
    @Column(name = "product_detail")
    private String productDetail;//产品详情描述
    @Column(name = "product_name")
    private String productName;//产品名称
    @Column(name = "strengths")
    private String strengths;
    @Column(name = "")
    private String teamDesc;
    @Column(name = "industry")
    private String industry;
    @Column(name ="desc_pic_url")
    private String descPicUrl;
    @Column(name = "contract_phone")
    private String contractPhone;
    @Column(name = "contract_name")
    private String contractName;
    @Column(name = "region")
    private String region;
    @Column(name = "cooperation_type")
    private String cooperationType;
    @Column(name = "introduce")
    private String introduce;
    @Column(name = "update_date")
    private Date updateDate;
    @Column(name = "create_date")
    private Date createDate;

    @Column(name = "ratio")
    private int ratio;
    @Column(name = "is_show")
    private int isShow;
    @Column(name = "is_recommend")
    private int isRecommend;
    //    @Column(name = "status")
    @Transient
    private int status;

    /**company_name` varchar(255) DEFAULT NULL COMMENT '公司名称',
            `build_date` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '成立日期',
            `reg_location` varchar(255) DEFAULT NULL COMMENT '注册地',
            `law_person` varchar(255) DEFAULT NULL COMMENT '法人',
            `bp_book_url` varchar(255) DEFAULT NULL COMMENT 'bp计划书',
            `road_show_url` varchar(255) DEFAULT NULL COMMENT '路演计划书',
            `invest_org_name` varchar(255) DEFAULT NULL COMMENT '机构名称',
            `invest_amount` varchar(255) DEFAULT NULL COMMENT '投资额',
            `consulant_title` varchar(255) DEFAULT NULL COMMENT '顾问职位',
            `consulant` varchar(255) DEFAULT NULL COMMENT '顾问名称',
            `consulant_org_name` varchar(255) DEFAULT NULL COMMENT '顾问机构',
            `consulant_profile_url` varchar(255) DEFAULT NULL COMMENT '顾问头像',
            `evlu_amount` varchar(255) DEFAULT NULL COMMENT '项目估值',
            `invest_amount_min**/
    @Column(name = "company_name")
    private String companyName;
    @Column(name = "build_date")
    private Date buildDate;
    @Column(name = "reg_location")
    private String regLocation;
    @Column(name = "law_person")
    private String lawPerson;
    @Column(name = "bp_book_url")
    private String bpBookUrl;
    @Column(name = "road_show_url")
    private String roadShowUrl;
    @Column(name = "invest_org_name")
    private String investOrnName;
    @Column(name = "invest_amount")
    private String investAmout;
    @Column(name = "consulant_title")
    private String consulantTile;
    @Column(name = "consulant")
    private String consulat;
    @Column(name = "consulant_org_name")
    private String consulatOrgName;
    @Column(name = "consulant_profile_url")
    private String consulatProfileUrl;
    @Column(name = "evlu_amount")
    private String evluAmount;
    @Column(name = "invest_amount_min")
    private String investAmoutMin;
    @Column(name = "project_profile_url")
    private String projectProfileUrl;

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

    public int getRatio() {
        return ratio;
    }

    public void setRatio(int ratio) {
        this.ratio = ratio;
    }

    public int getIsShow() {
        return isShow;
    }

    public void setIsShow(int isShow) {
        this.isShow = isShow;
    }

    public int getIsRecommend() {
        return isRecommend;
    }

    public void setIsRecommend(int isRecommend) {
        this.isRecommend = isRecommend;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
