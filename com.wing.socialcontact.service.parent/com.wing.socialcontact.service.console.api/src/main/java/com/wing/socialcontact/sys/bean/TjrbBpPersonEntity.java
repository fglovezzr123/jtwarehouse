package com.wing.socialcontact.sys.bean;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 
 *
 * @author fenggang
 * @date 2017-12-27 15:13:46
 */
@Table(name = "tjrb_finance_product")
public class TjrbBpPersonEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//主键
	@Id
	@GeneratedValue(generator = "JDBC")
	@Column(name = "id")
	private Long id;
	//投资人名称

	@Column(name = "bp_name")
	private String bpName;
	//机构代码
	@Column(name = "org_no")
	private String orgNo;
	//倾向轮次
	@Column(name = "tendency")
	private String tendency;
	//是否认证 0- 未认证 1-认证
	@Column(name = "org_name")
	private String orgName;
	//关注行业 ，多个已逗号分隔
	@Column(name = "focus_on")
	private String focusOn;
	//履历
	@Column(name = "curriculum_vitae")
	private String curriculumVitae;
	//
	@Column(name = "title")
	private String title;

	@Column(name = "mobile")
	private String mobile;
	@Column(name = "area")
	private String area;
	@Column(name = "position")
	private String position;
	//
	@Column(name = "update_date")
	private Date updateDate;
	//免责声明 0- 平台无责  1-未操作
	@Column(name = "create_date")
	private Date createDate;

	/**
	 * 设置：主键
	 */
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * 获取：主键
	 */
	public Long getId() {
		return id;
	}
	/**
	 * 设置：投资人名称
	 */
	public void setBpName(String bpName) {
		this.bpName = bpName;
	}
	/**
	 * 获取：投资人名称
	 */
	public String getBpName() {
		return bpName;
	}
	/**
	 * 设置：机构代码
	 */
	public void setOrgNo(String orgNo) {
		this.orgNo = orgNo;
	}
	/**
	 * 获取：机构代码
	 */
	public String getOrgNo() {
		return orgNo;
	}
	/**
	 * 设置：倾向轮次
	 */
	public void setTendency(String tendency) {
		this.tendency = tendency;
	}
	/**
	 * 获取：倾向轮次
	 */
	public String getTendency() {
		return tendency;
	}
	/**
	 * 设置：是否认证 0- 未认证 1-认证
	 */
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	/**
	 * 获取：是否认证 0- 未认证 1-认证
	 */
	public String getOrgName() {
		return orgName;
	}
	/**
	 * 设置：关注行业 ，多个已逗号分隔
	 */
	public void setFocusOn(String focusOn) {
		this.focusOn = focusOn;
	}
	/**
	 * 获取：关注行业 ，多个已逗号分隔
	 */
	public String getFocusOn() {
		return focusOn;
	}
	/**
	 * 设置：履历
	 */
	public void setCurriculumVitae(String curriculumVitae) {
		this.curriculumVitae = curriculumVitae;
	}
	/**
	 * 获取：履历
	 */
	public String getCurriculumVitae() {
		return curriculumVitae;
	}
	/**
	 * 设置：
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * 获取：
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * 设置：
	 */
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	/**
	 * 获取：
	 */
	public Date getUpdateDate() {
		return updateDate;
	}
	/**
	 * 设置：免责声明 0- 平台无责  1-未操作
	 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	/**
	 * 获取：免责声明 0- 平台无责  1-未操作
	 */
	public Date getCreateDate() {
		return createDate;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}
}
