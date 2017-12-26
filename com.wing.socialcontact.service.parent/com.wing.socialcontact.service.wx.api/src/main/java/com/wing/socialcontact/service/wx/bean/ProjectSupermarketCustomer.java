package com.wing.socialcontact.service.wx.bean;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.Date;

/**
 * 项目意向客户
 *
 * @author wangyansheng
 * @date 2017-11-01
 * @version 1.0
 */
@Table(name = "tjy_project_supermarket_customer")
public class ProjectSupermarketCustomer implements Serializable{
	private static final long serialVersionUID = 1L;

    /**主键*/
    @Id
	@Column(name = "id")
	private String id;

    /** 项目id */
	private String fkId;

	/**用户id*/
	private String userId;

	/**是否处理*/
	private Integer isHandle;

	/**创建时间*/
	private Date createTime;

	/**创建人id*/
	private String createUserId;

	/**更新时间*/
	private Date updateTime;

	/**更新人id*/
	private String updateUserId;

	/**删除标识*/
	private Integer deleted;

	/**项目名称*/
	@Transient
	private String name;

	/**姓名*/
	@Transient
	private String trueName;

	/**手机号码*/
	@Transient
	private String mobile;

	/**公司名称*/
	@Transient
	private String comName;

	/**服务秘书电话*/
	@Transient
	private String kfTelephone;

	/**职位*/
	@Transient
	private String jobName;

	@Transient
	private String orderBy;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getJobName() {
		return jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	public String getTrueName() {
		return trueName;
	}

	public void setTrueName(String trueName) {
		this.trueName = trueName;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getComName() {
		return comName;
	}

	public void setComName(String comName) {
		this.comName = comName;
	}

	public String getKfTelephone() {
		return kfTelephone;
	}

	public void setKfTelephone(String kfTelephone) {
		this.kfTelephone = kfTelephone;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFkId() {
		return fkId;
	}

	public void setFkId(String fkId) {
		this.fkId = fkId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Integer getIsHandle() {
		return isHandle;
	}

	public void setIsHandle(Integer isHandle) {
		this.isHandle = isHandle;
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
		this.updateUserId = updateUserId;
	}

	public Integer getDeleted() {
		return deleted;
	}

	public void setDeleted(Integer deleted) {
		this.deleted = deleted;
	}

	public String getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}
}
