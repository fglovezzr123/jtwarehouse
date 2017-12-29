package com.tojoy.business.common.bean;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 *  评论点赞
 * 
 * @author wangyansheng
 * @date 2017-12-24 15:00:56
 * @version 1.0
 */

public class CommentThumbUp implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

    /**主键*/
    @Id
	@Column(name = "id")
	@GeneratedValue(generator = "UUID")
	private String id;

    /** 点赞人  */
	private String userId;

    /** 评论  */
	private String fkId;

    /** 创建时间 */
	private Date createTime;

    /** 创建人ID */
	private String createUserId;

    /** 修改时间 */
	private Date updateTime;

    /** 修改人ID */
	private String updateUserId;

	/**业务key值*/
	@Transient
	private  String keyWord;

	public String getKeyWord() {
		return keyWord;
	}

	public void setKeyWord(String keyWord) {
		this.keyWord = keyWord;
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
		this.userId = userId;
	}

	public String getFkId() {
		return fkId;
	}

	public void setFkId(String fkId) {
		this.fkId = fkId;
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
