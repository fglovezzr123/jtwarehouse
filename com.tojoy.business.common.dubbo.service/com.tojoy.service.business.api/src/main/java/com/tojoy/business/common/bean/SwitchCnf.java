package com.tojoy.business.common.bean;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.Date;

/**
 *
 * 
 * @author wangyansheng
 * @date 2017-11-01
 * @version 1.0
 */
@Table(name = "tjy_switch_cnf")
public class SwitchCnf implements Serializable{
	private static final long serialVersionUID = 1L;

    /**主键*/
    @Id
	@Column(name = "id")
	private String id;

    /** 业务key */
	private String keyWord;

	/** 配置值 */
	private long cnfValue;

	/** 删除标识 */
	private Integer deleted;

	/** 创建时间 */
	private Date createTime;

	/** 创建人 */
	private String createUserId;

	/** 更新时间 */
	private Date updateTime;

	/** 更新人 */
	private String updateUserId;

	@Transient
	private long collection;

	@Transient
	private long thumbUp;

	@Transient
	private long comment;

	@Transient
	private long reward;

	@Transient
	private long share;

	public long getCollection() {
		return collection;
	}

	public void setCollection(long collection) {
		this.collection = collection;
	}

	public long getThumbUp() {
		return thumbUp;
	}

	public void setThumbUp(long thumbUp) {
		this.thumbUp = thumbUp;
	}

	public long getComment() {
		return comment;
	}

	public void setComment(long comment) {
		this.comment = comment;
	}

	public long getReward() {
		return reward;
	}

	public void setReward(long reward) {
		this.reward = reward;
	}

	public long getShare() {
		return share;
	}

	public void setShare(long share) {
		this.share = share;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getKeyWord() {
		return keyWord;
	}

	public void setKeyWord(String keyWord) {
		this.keyWord = keyWord;
	}

	public long getCnfValue() {
		return cnfValue;
	}

	public void setCnfValue(long cnfValue) {
		this.cnfValue = cnfValue;
	}

	public Integer getDeleted() {
		return deleted;
	}

	public void setDeleted(Integer deleted) {
		this.deleted = deleted;
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
}
