package org.com.wing.enterprise.bean;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;


/**
 * 
 * //TODO 企服云-企服
 * @author sino
 */
@Table(name = "qfy_entry")
public class EntryPrise implements Serializable{

    @Transient
    private static final long serialVersionUID = 1L;
    
    
    @Id
    @Column(name = "id")
    /*@GeneratedValue(generator = "UUID")*/
    private String id;
    
    /** 创建时间 */
    private Date createTime; 
    
    /**
     * 创建人ID
     */
    private String createUserId;

    /**
     * 企服名称
     */
    private String name;
    
    /**
     * 企服标题
     */
    private String title;
    
    /**
     * 企服服务数量 1~99999999
     */
    private Integer serviceCount;
    
    /**
     * 企服所属用户ID
     */
    private String ssUserId;
    
    /**
     * 企服logo
     */
    private String logoImgPath;
    
    /**
     * 企服标题简介
     */
    private String titleDesc;
    
    /**
     * 企服简介 
     */
    private String entryDesc;
    
    /**
     * 详情介绍
     */
    private String detailDesc;
    /**
     * 企服服务案例
     */
    private String serviceCase;
    
    /**
     * 企服序号，默认9999
     */
    private int sortNum;
    
    /**
     * 是否精选企服 1：精选，0：非精选
     */
    private int isGood;

    /**
     * 0可用，1删除
     */
    private int status;
    
    /**
     * 客服联系方式
     */
    private String phone;
    
    /**
     * banner图
     */
    private String bannerPath;
    
    

    public String getBannerPath() {
		return bannerPath;
	}

	public void setBannerPath(String bannerPath) {
		this.bannerPath = bannerPath;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
        this.createUserId = createUserId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getServiceCount() {
        return serviceCount;
    }

    public void setServiceCount(Integer serviceCount) {
        this.serviceCount = serviceCount;
    }

    public String getSsUserId() {
        return ssUserId;
    }

    public void setSsUserId(String ssUserId) {
        this.ssUserId = ssUserId;
    }

    public String getLogoImgPath() {
        return logoImgPath;
    }

    public void setLogoImgPath(String logoImgPath) {
        this.logoImgPath = logoImgPath;
    }

    public String getTitleDesc() {
        return titleDesc;
    }

    public void setTitleDesc(String titleDesc) {
        this.titleDesc = titleDesc;
    }

    public String getEntryDesc() {
        return entryDesc;
    }

    public void setEntryDesc(String entryDesc) {
        this.entryDesc = entryDesc;
    }

    public String getDetailDesc() {
        return detailDesc;
    }

    public void setDetailDesc(String detailDesc) {
        this.detailDesc = detailDesc;
    }

    public String getServiceCase() {
        return serviceCase;
    }

    public void setServiceCase(String serviceCase) {
        this.serviceCase = serviceCase;
    }

    public int getSortNum() {
        return sortNum;
    }

    public void setSortNum(int sortNum) {
        this.sortNum = sortNum;
    }

    public int getIsGood() {
        return isGood;
    }

    public void setIsGood(int isGood) {
        this.isGood = isGood;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
