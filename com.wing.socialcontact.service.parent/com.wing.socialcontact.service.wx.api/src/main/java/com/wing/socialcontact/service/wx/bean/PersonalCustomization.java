package com.wing.socialcontact.service.wx.bean;

import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

/**
 * 个性定制
 * 
 * @author wangyansheng
 * @date 2017-11-01
 * @version 1.0
 */
@Table(name = "tjy_personal_customization")
public class PersonalCustomization implements Serializable{
	private static final long serialVersionUID = 1L;

    /**主键*/
    @Id
	@Column(name = "id")
	private String id;


    /** 标题 */
	private String title;

	/**权重*/
    private Integer weight;

	/**服务内容*/
	private String content;

	/**封面图片地址*/
	private String imageUrl;

	/**微吼id*/
	private String webinarId;

	/**微吼主题*/
	private String webinarSubject;

	/**音频文件地址*/
	private String voiceUrl;

	/**1 显示 ， 2 不显示*/
	private Integer isShow;

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

	/**音频时长*/
	private String voiceTime;

	/**意向提交次数*/
	@Transient
	private Integer submitCount;

	public Integer getSubmitCount() {
		return submitCount;
	}

	public void setSubmitCount(Integer submitCount) {
		this.submitCount = submitCount;
	}

	@Transient
	private String orderBy;

	@Transient
	private Integer attentionCount;

	@Transient
	private Integer isAttention;

	public String getVoiceTime() {
		return voiceTime;
	}

	public void setVoiceTime(String voiceTime) {
		this.voiceTime = voiceTime;
	}

	public Integer getAttentionCount() {
		return attentionCount;
	}

	public void setAttentionCount(Integer attentionCount) {
		this.attentionCount = attentionCount;
	}

	public Integer getIsAttention() {
		return isAttention;
	}

	public void setIsAttention(Integer isAttention) {
		this.isAttention = isAttention;
	}

	public String getWebinarId() {
		return webinarId;
	}

	public void setWebinarId(String webinarId) {
		this.webinarId = webinarId;
	}

	public String getWebinarSubject() {
		return webinarSubject;
	}

	public void setWebinarSubject(String webinarSubject) {
		this.webinarSubject = webinarSubject;
	}

	public String getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	public Integer getDeleted() {
		return deleted;
	}

	public void setDeleted(Integer deleted) {
		this.deleted = deleted;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getWeight() {
		return weight;
	}

	public void setWeight(Integer weight) {
		this.weight = weight;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}


	public String getVoiceUrl() {
		return voiceUrl;
	}

	public void setVoiceUrl(String voiceUrl) {
		this.voiceUrl = voiceUrl;
	}

	public Integer getIsShow() {
		return isShow;
	}

	public void setIsShow(Integer isShow) {
		this.isShow = isShow;
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
