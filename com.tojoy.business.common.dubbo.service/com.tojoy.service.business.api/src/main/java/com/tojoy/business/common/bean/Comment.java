package com.tojoy.business.common.bean;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 评论信息
 * 
 * @author wangyansheng
 * @date 2017-12-24 09:01:08
 * @version 1.0
 */
public class Comment implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

    /**  */
    @Id
	@Column(name = "id")
	@GeneratedValue(generator = "UUID")
	private String id;

    /** 上一级评论ID */
	private String parentId;

    /** 评论人ID */
	private String userId;

    /** 评论类别对应表的主键(如话题表ID) */
	private String fkId;

    /** 评论内容 */
	private String commentDesc;

    /** 图片url */
	private String imgUrl;

    /** 状态 */
	private Integer status;

    /** 创建时间 */
	private Date createTime;

    /** 创建人ID */
	private String createUserId;

    /** 修改时间 */
	private Date updateTime;

    /** 修改人ID */
	private String updateUserId;

    /** 逻辑删除标识 */
	private Integer deleted;

	/**点赞数 */
	private Integer thumbUpCount;

	/** 是否点赞 */
	private Integer isThumbUp;

	/**业务key值*/
	private  String keyWord;

	/**第一条子评论*/
	Comment firstSubComment;

	/**子评论数 */
	private Integer subCommentCount;

	public Integer getSubCommentCount() {
		return subCommentCount;
	}

	public void setSubCommentCount(Integer subCommentCount) {
		this.subCommentCount = subCommentCount;
	}

	public Comment getFirstSubComment() {
		return firstSubComment;
	}

	public void setFirstSubComment(Comment firstSubComment) {
		this.firstSubComment = firstSubComment;
	}

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

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
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

	public String getCommentDesc() {
		return commentDesc;
	}

	public void setCommentDesc(String commentDesc) {
		this.commentDesc = commentDesc;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
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

	public Integer getThumbUpCount() {
		return thumbUpCount;
	}

	public void setThumbUpCount(Integer thumbUpCount) {
		this.thumbUpCount = thumbUpCount;
	}

	public Integer getIsThumbUp() {
		return isThumbUp;
	}

	public void setIsThumbUp(Integer isThumbUp) {
		this.isThumbUp = isThumbUp;
	}
}
