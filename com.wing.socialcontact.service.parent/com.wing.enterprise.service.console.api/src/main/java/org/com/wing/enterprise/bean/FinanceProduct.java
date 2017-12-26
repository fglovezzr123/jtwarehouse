package org.com.wing.enterprise.bean;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by fenggang on 12/24/17.
 *
 * @author fenggang
 * @date 12/24/17
 */
@Table(name = "tjrb_finance_product")
public class FinanceProduct implements Serializable {
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
    @Column(name = "finance_type")
    private int financeType;//融资类型  具体参照原型
    @Column(name = "finance_amount")
    private String financeAmount;//融资金额
    @Column(name = "finance_period")
    private String financeperiod;//融资期限
    @Column(name = "introduce")
    private String introduce;//募集情况说明
    @Column(name = "invest_type")
    private Long investType;//投资类型 对应投资类型表主键
    @Column(name = "consultant")
    private String consultant;//关联机构顾问
    @Column(name = "org_name")
    private String orgName;//发行机构名称
    @Column(name = "org_no")
    private Integer orgNo;//发行机构号
    @Column(name = "material")
    private String material;//所需材料
    @Column(name = "conditions")
    private String conditions;//申请条件
    @Column(name = "month_supply")
    private Float monthSupply;//月供
    @Column(name = "interest")
    private Float interest;//利息
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

    public int getFinanceType() {
        return financeType;
    }

    public void setFinanceType(int financeType) {
        this.financeType = financeType;
    }

    public String getFinanceAmount() {
        return financeAmount;
    }

    public void setFinanceAmount(String financeAmount) {
        this.financeAmount = financeAmount;
    }

    public String getFinanceperiod() {
        return financeperiod;
    }

    public void setFinanceperiod(String financeperiod) {
        this.financeperiod = financeperiod;
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public Long getInvestType() {
        return investType;
    }

    public void setInvestType(Long investType) {
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

    public String getConditions() {
        return conditions;
    }

    public void setConditions(String conditions) {
        this.conditions = conditions;
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
