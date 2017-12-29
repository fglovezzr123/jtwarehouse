package com.wing.socialcontact.service.wx.bean;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Table(name = "tjy_project_supermarket")
public class ProjectSupermarket  implements Serializable {


    /**
     *
     */
    private static final long serialVersionUID = 5151769777960683099L;

    @Id
    @Column(name = "id")
    private String id;

    private String userId;

    private String name;

    private String introduce;

    private String highlights;

    private String illustration;

    private String teamPresentation;

    private String cooperativeMode;
    @Transient
    private String cooperativeModeName;

    private String province;
    @Transient
    private String provinceName;

    private String city;
    @Transient
    private String cityName;

    private String county;
    @Transient
    private String countyName;

    private String industry;
    @Transient
    private String industryName;

    private Integer sort;

    private Integer isShow;

    private Integer isTop;

    private Integer isDelete;

    private Date createTime;

    private String createUserId;

    private Date updateTime;

    private String updateUserId;

    @Transient
    private String images;

    @Transient
    private List<ProjectSupermarketImages> projectSupermarketImagesList = new ArrayList<ProjectSupermarketImages>();;

    @Transient
    private String mobile;

    @Transient
    private Integer attentionCount;

    @Transient
    private Integer isAttention;

    public Integer getIsAttention() {
        return isAttention;
    }

    public void setIsAttention(Integer isAttention) {
        this.isAttention = isAttention;
    }

    public Integer getAttentionCount() {
        return attentionCount;
    }

    public void setAttentionCount(Integer attentionCount) {
        this.attentionCount = attentionCount;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public List<ProjectSupermarketImages> getProjectSupermarketImagesList() {
        return projectSupermarketImagesList;
    }

    public void setProjectSupermarketImagesList(List<ProjectSupermarketImages> projectSupermarketImagesList) {
        this.projectSupermarketImagesList = projectSupermarketImagesList;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getCooperativeModeName() {
        return cooperativeModeName;
    }

    public void setCooperativeModeName(String cooperativeModeName) {
        this.cooperativeModeName = cooperativeModeName;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCountyName() {
        return countyName;
    }

    public void setCountyName(String countyName) {
        this.countyName = countyName;
    }

    public String getIndustryName() {
        return industryName;
    }

    public void setIndustryName(String industryName) {
        this.industryName = industryName;
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public String getHighlights() {
        return highlights;
    }

    public void setHighlights(String highlights) {
        this.highlights = highlights;
    }

    public String getIllustration() {
        return illustration;
    }

    public void setIllustration(String illustration) {
        this.illustration = illustration;
    }

    public String getTeamPresentation() {
        return teamPresentation;
    }

    public void setTeamPresentation(String teamPresentation) {
        this.teamPresentation = teamPresentation;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCooperativeMode() {
        return cooperativeMode;
    }

    public void setCooperativeMode(String cooperativeMode) {
        this.cooperativeMode = cooperativeMode == null ? null : cooperativeMode.trim();
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province == null ? null : province.trim();
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city == null ? null : city.trim();
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county == null ? null : county.trim();
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry == null ? null : industry.trim();
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Integer getIsShow() {
        return isShow;
    }

    public void setIsShow(Integer isShow) {
        this.isShow = isShow;
    }

    public Integer getIsTop() {
        return isTop;
    }

    public void setIsTop(Integer isTop) {
        this.isTop = isTop;
    }

    public Integer getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(String createUserId) {
        this.createUserId = createUserId == null ? null : createUserId.trim();
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getUpdateUserId() {
        return updateUserId;
    }

    public void setUpdateUserId(String updateUserId) {
        this.updateUserId = updateUserId == null ? null : updateUserId.trim();
    }
}