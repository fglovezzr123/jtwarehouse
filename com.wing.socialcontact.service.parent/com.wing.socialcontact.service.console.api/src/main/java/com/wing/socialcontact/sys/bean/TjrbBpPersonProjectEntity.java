package com.wing.socialcontact.sys.bean;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;



/**
 * 
 * @author fenggang
 * @date 2017-12-27 15:13:46
 */
@Table(name = "tjrb_finance_product")
public class TjrbBpPersonProjectEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//主键
	@Id
	@GeneratedValue(generator = "JDBC")
	@Column(name = "id")
	private Long id;
	//投资人名称

	@Column(name = "bp_id")
	private String bpId;
	//项目标号
	@Column(name = "product_id")
	private String productId;
	//风头项目 分为已投，收藏 ，收到
	@Column(name = "action_type")
	private Integer actionType;
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
	public void setBpId(String bpId) {
		this.bpId = bpId;
	}
	/**
	 * 获取：投资人名称
	 */
	public String getBpId() {
		return bpId;
	}
	/**
	 * 设置：项目标号
	 */
	public void setProductId(String productId) {
		this.productId = productId;
	}
	/**
	 * 获取：项目标号
	 */
	public String getProductId() {
		return productId;
	}
	/**
	 * 设置：风头项目 分为已投，收藏 ，收到
	 */
	public void setActionType(Integer actionType) {
		this.actionType = actionType;
	}
	/**
	 * 获取：风头项目 分为已投，收藏 ，收到
	 */
	public Integer getActionType() {
		return actionType;
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
}
