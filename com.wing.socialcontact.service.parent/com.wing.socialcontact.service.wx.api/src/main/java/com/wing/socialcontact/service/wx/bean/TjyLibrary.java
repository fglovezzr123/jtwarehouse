package com.wing.socialcontact.service.wx.bean;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 文库
 * @author zhangzheng
 *
 */
@Table(name = "tjy_library")
public class TjyLibrary implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2894276245768521439L;
	
	@Id
	@Column(name = "id")
	@GeneratedValue(generator = "UUID")
	private String id;
	
	private int deleted;
	
	private String createUserId;
	
	private String updateUserId;
	
	private Date updateTime;

	private Date createTime;
	/**
	 * 标题
	 */
	private String title;
	/**
	 * 分类id
	 */
	private String classId;
	/**
	 * 推荐  1：是  0：否
	 */
	private int recommend;
	/**
	 * 排序
	 */
	private int sort ;
	/**
	 * 阅读次数
	 */
	private int readtimes ;
	/**
	 * 封面图
	 */
	private String imgpath;
	/**
	 * 内容
	 */
	private String content;
	/**
	 * 标签
	 */
	private String tag;
	/**
	 * 视频id
	 */
	private String webinarId;
	/**
	 * 附件
	 */
	private String fileId;
	/**
	 * 一级分类
	 */
	private String oneclass;
	/** 微吼会议主题 */
	private String webinarSubject;
	
	/** 文章点赞功能 1开启，2关闭 */
	private int thumbup;
	
	/** 文章评论功能 1开启，2关闭 */
	private int comment;
	
	/** 文章打赏功能1开启，2关闭*/
	private int reward;
	
	/** 打赏默认账户*/
	private String rewardUser;

	/** 内容可视范围*/
	private long contentVisibleRange;

	/** 视频可视范围*/
	private long videoVisibleRange;

	/** 音频文件 */
	private String audioFile;

	/** 音频文件时长 */
	private String audioTime;

	public long getContentVisibleRange() {
		return contentVisibleRange;
	}

	public void setContentVisibleRange(long contentVisibleRange) {
		this.contentVisibleRange = contentVisibleRange;
	}

	public long getVideoVisibleRange() {
		return videoVisibleRange;
	}

	public void setVideoVisibleRange(long videoVisibleRange) {
		this.videoVisibleRange = videoVisibleRange;
	}

	public String getAudioTime() {
		return audioTime;
	}

	public void setAudioTime(String audioTime) {
		this.audioTime = audioTime;
	}

	public String getOneclass() {
		return oneclass;
	}

	public void setOneclass(String oneclass) {
		this.oneclass = oneclass;
	}

	public String getWebinarId() {
		return webinarId;
	}

	public void setWebinarId(String webinarId) {
		this.webinarId = webinarId;
	}

	public String getFileId() {
		return fileId;
	}

	public void setFileId(String fileId) {
		this.fileId = fileId;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public int getReadtimes() {
		return readtimes;
	}

	public void setReadtimes(int readtimes) {
		this.readtimes = readtimes;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getClassId() {
		return classId;
	}

	public void setClassId(String classId) {
		this.classId = classId;
	}

	public int getRecommend() {
		return recommend;
	}

	public void setRecommend(int recommend) {
		this.recommend = recommend;
	}

	public int getSort() {
		return sort;
	}

	public void setSort(int sort) {
		this.sort = sort;
	}

	public String getImgpath() {
		return imgpath;
	}

	public void setImgpath(String imgpath) {
		this.imgpath = imgpath;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
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

	public int getDeleted() {
		return deleted;
	}

	public void setDeleted(int deleted) {
		this.deleted = deleted;
	}

	public String getCreateUserId() {
		return createUserId;
	}

	public void setCreateUserId(String createUserId) {
		this.createUserId = createUserId;
	}


	public String getUpdateUserId() {
		return updateUserId;
	}

	public void setUpdateUserId(String updateUserId) {
		this.updateUserId = updateUserId;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getWebinarSubject() {
		return webinarSubject;
	}

	public void setWebinarSubject(String webinarSubject) {
		this.webinarSubject = webinarSubject;
	}

	public int getThumbup() {
		return thumbup;
	}

	public void setThumbup(int thumbup) {
		this.thumbup = thumbup;
	}

	public int getComment() {
		return comment;
	}

	public void setComment(int comment) {
		this.comment = comment;
	}

	public int getReward() {
		return reward;
	}

	public void setReward(int reward) {
		this.reward = reward;
	}

	public String getRewardUser() {
		return rewardUser;
	}

	public void setRewardUser(String rewardUser) {
		this.rewardUser = rewardUser;
	}

	public String getAudioFile() {
		return audioFile;
	}

	public void setAudioFile(String audioFile) {
		this.audioFile = audioFile;
	}
	
	
	
	
	
	
}
