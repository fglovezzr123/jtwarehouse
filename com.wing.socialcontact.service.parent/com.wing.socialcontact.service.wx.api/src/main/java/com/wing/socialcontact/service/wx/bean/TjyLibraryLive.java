package com.wing.socialcontact.service.wx.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.alibaba.fastjson.JSON;
@Table(name="tjy_library_live")
public class TjyLibraryLive implements Serializable {


	@Id
	@Column(name = "id")
	@GeneratedValue(generator = "UUID")
	private String id;
	
	private Integer deleted;
	
	private String createUserId;
	
	private String updateUserId;
	
	private Date updateTime;

	private Date createTime;
	/**
	 * 标题
	 */
	private String title;
	/**
	 * 权重
	 */
	private Integer sort ;
	/**
	 *详情封面图
	 */
	private String imagepath;
	/**
	 *列表封面图
	 */
	private String imgpath;
	/**
	 * 内容
	 */
	private String content;
	/**
	 * 视频id
	 */
	private String webinarId;
	/**
	 * 书籍名称
	 */
	private String bookname;
	/**
	 *  微吼会议主题
	 */
	private String webinarSubject;
	/** 门票价格 */
	private Integer ticketPrice;
	/** 直播开始时间 */
	private Date startTime;

    /** 直播结束时间 */
	private Date endTime;
	
	/**
	 * 类型 1：俊卿解惑   2：总统谈心   3：冠军直播秀  4：总裁读书会
	 */
	private Integer type;
	/**
	 * 直播状态：
	 */
	private Integer status;
	/**
	 * 是否显示：0否   1是
	 */
	private Integer isShow;
	/**
	 * 是否显示：0否   1是
	 */
	private Integer isEnd;
	
	@Transient
	private List<MeetingGuest> liveGuests = new ArrayList<MeetingGuest>();
	@Transient
	private String guests;

	
	
	
	public String getGuests() {
		return guests;
	}

	public void setGuests(String guests) {
		this.guests = guests;
	}

	public String getBookname() {
		return bookname;
	}

	public void setBookname(String bookname) {
		this.bookname = bookname;
	}

	public Integer getIsShow() {
		return isShow;
	}

	public void setIsShow(Integer isShow) {
		this.isShow = isShow;
	}

	public Integer getIsEnd() {
		return isEnd;
	}

	public void setIsEnd(Integer isEnd) {
		this.isEnd = isEnd;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Integer getDeleted() {
		return deleted;
	}

	public void setDeleted(Integer deleted) {
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

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public String getImagepath() {
		return imagepath;
	}

	public void setImagepath(String imagepath) {
		this.imagepath = imagepath;
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

	public Integer getTicketPrice() {
		return ticketPrice;
	}

	public void setTicketPrice(Integer ticketPrice) {
		this.ticketPrice = ticketPrice;
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

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}
	
	public List<MeetingGuest> getLiveGuests() {
		return liveGuests;
	}

	public void setLiveGuests(List<MeetingGuest> liveGuests) {
		this.liveGuests = liveGuests;
	}
	
	public void parse(){
		this.liveGuests.clear();
		
		if(this.guests!=null&&this.guests.trim().length()>0){
			List<MeetingGuest> list = JSON.parseArray(this.guests,MeetingGuest.class);
			for(MeetingGuest g : list){
				g.setMeetingId(this.id);
				g.setCreateTime(new Date());
			}
			this.liveGuests = list;
		}
	}

}
