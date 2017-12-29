package com.wing.socialcontact.service.wx.bean;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
/**
 * 
 * <p>Title:活动表 </p>
 * <p>Description: </p>
 * <p>Company: </p> 
 * @author zhangzheng
 * @Date  2017年4月18日 下午3:16:26
 */

@Table(name = "TJY_Activity")
public class Activity implements Serializable {

	 /**
	 * 
	 */
	private static final long serialVersionUID = -256714248102917079L;

	/** 主键 */
    @Id
	@Column(name = "id")
	@GeneratedValue(generator = "UUID")
	private String id;

    /** 活动主题 */
	private String titles;

    /** 活动封面图地址 */
	private String imagePath;
	
	/** 活动发起者 */
	private String sponsor;
	
	/** 活动发起者介绍 */
	private String sponsorIntroduce;

    /** 活动省 */
	private String province;
	
	/** 活动市 */
	private String city;
	
	/** 活动市区*/
	private String county;
	
	/** 活动地点 */
	private String place;

    /** 活动开始时间 */
	private Date startTime;

    /** 活动结束时间 */
	private Date endTime;

    /** 报名截至时间 */
	private Date signupTime;

    /** 门票价格 */
	private Double ticketPrice;

    /** 活动状态    1-6  未审核   报名中  报名结束  进行中  已结束    已取消 */
	private Integer status;

    /** 是否推荐首页，1是0否 */
	private Integer recommendEnable;

    /** 是否显示，1是0否 */
	private Integer showEnable;
	
	/** 是否评论，1是0否 */
	private Integer commentEnable;

    /** 创建时间 */
	private Date createTime;

    /** 创建用户ID */
	private String createUserId;

    /** 创建用户名 */
	private String createUserName;

    /** 1表示删除0表示未删除 */
	private Integer deleted;
	
	/** 活动内容 */
	private String contents;
	
	/** 活动标签 */
	private String tag;
	
	/** 活动类别   1以玩会友，2以书会友*/
	private String classId;
	
	/** 推荐列表  0 不推荐   1 推荐*/
	private int recommendList;
	
	/** 用户可见   0 所有用户可见  1仅好友可见*/
	private int showUser;
	
	/** 活动方式 2线上参与  1线下参与 */
	private int pattern;
	/** 取消时间 */
	private String refundDescription;
	/** 延期申请  0 否    1 是 */
	private Integer isdelay;
	/** 延期天数*/
	private Integer delaydays;
	/** 是否取消 */
	private Integer iscancel;
	/** 是否已付款*/
	private Integer iscod;
	
	/** 权重 */
	private Integer sort;

	
	
	
	
	
	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public Integer getIscod() {
		return iscod;
	}

	public void setIscod(Integer iscod) {
		this.iscod = iscod;
	}

	public Integer getDelaydays() {
		return delaydays;
	}

	public void setDelaydays(Integer delaydays) {
		this.delaydays = delaydays;
	}

	public String getCounty() {
		return county;
	}

	public void setCounty(String county) {
		this.county = county;
	}

	public Integer getIsdelay() {
		return isdelay;
	}

	public void setIsdelay(Integer isdelay) {
		this.isdelay = isdelay;
	}

	public Integer getIscancel() {
		return iscancel;
	}

	public void setIscancel(Integer iscancel) {
		this.iscancel = iscancel;
	}

	public String getRefundDescription() {
		return refundDescription;
	}

	public void setRefundDescription(String refundDescription) {
		this.refundDescription = refundDescription;
	}

	public String getSponsorIntroduce() {
		return sponsorIntroduce;
	}

	public void setSponsorIntroduce(String sponsorIntroduce) {
		this.sponsorIntroduce = sponsorIntroduce;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public Integer getCommentEnable() {
		return commentEnable;
	}

	public void setCommentEnable(Integer commentEnable) {
		this.commentEnable = commentEnable;
	}

	public String getSponsor() {
		return sponsor;
	}

	public void setSponsor(String sponsor) {
		this.sponsor = sponsor;
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

	public String getTitles() {
		return titles;
	}

	public void setTitles(String titles) {
		this.titles = titles;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Date getSignupTime() {
		return signupTime;
	}

	public void setSignupTime(Date signupTime) {
		this.signupTime = signupTime;
	}

	public Double getTicketPrice() {
		return ticketPrice;
	}

	public void setTicketPrice(Double ticketPrice) {
		this.ticketPrice = ticketPrice;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getRecommendEnable() {
		return recommendEnable;
	}

	public void setRecommendEnable(Integer recommendEnable) {
		this.recommendEnable = recommendEnable;
	}

	public Integer getShowEnable() {
		return showEnable;
	}

	public void setShowEnable(Integer showEnable) {
		this.showEnable = showEnable;
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

	public String getCreateUserName() {
		return createUserName;
	}

	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}

	public Integer getDeleted() {
		return deleted;
	}

	public void setDeleted(Integer deleted) {
		this.deleted = deleted;
	}

	public String getContents() {
		return contents;
	}

	public void setContents(String contents) {
		this.contents = contents;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public String getClassId() {
		return classId;
	}

	public void setClassId(String classId) {
		this.classId = classId;
	}

	public int getRecommendList() {
		return recommendList;
	}

	public void setRecommendList(int recommendList) {
		this.recommendList = recommendList;
	}

	public int getShowUser() {
		return showUser;
	}

	public void setShowUser(int showUser) {
		this.showUser = showUser;
	}

	public int getPattern() {
		return pattern;
	}

	public void setPattern(int pattern) {
		this.pattern = pattern;
	}
	
	
	
}
