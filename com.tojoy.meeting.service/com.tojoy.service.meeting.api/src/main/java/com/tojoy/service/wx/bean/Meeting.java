package com.tojoy.service.wx.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

/**
 * TJY_MEETING 会议信息表
 * 
 * @author liangwj
 * @date 2017-04-04 00:05:28
 * @version 1.0
 */
@Table(name = "TJY_MEETING")
public class Meeting implements Serializable{
	private static final long serialVersionUID = 1L;

	/** 选集视频 */
	@Transient
	private List<MeetingRelation> meetingRelationList;

	/** 往届会议 */
	@Transient
	private List<MeetingSuccessive> meetingSuccessiveList = new ArrayList<>();

	/** 报名用户 */
	@Transient
	private List<MeetingSignup> meetingSignupList = new ArrayList<>();

	/** 主键 */
    @Id
	@Column(name = "id")
	@GeneratedValue(generator = "UUID")
	private String id;

    /** 会议主题 */
	private String titles;

    /** 会议封面图地址 */
	private String coverImg;

    /** 会议类型 */
	private Integer types;

    /** 会议地点 */
	private String place;

    /** 会议开始时间 */
	private Date startTime;

    /** 会议结束时间 */
	private Date endTime;
	
	/** 报名开始时间 */
	private Date startSignupTime;

    /** 报名截至时间 */
	private Date endSignupTime;

    /** 门票价格 */
	private Double ticketPrice;

    /** 直播地址 */
	private String liveUrl;

    /** 会议状态 */
	private Integer status;
	
	/** 权重 */
	private Integer sort;
	
	/** 会议状态描述 */
	@Transient
	private String statusDesc;

    /** 推荐投融宝首页，1是0否 */
	private Integer recommendEnable;
	
	/** 推荐投洽峰会首页，1是0否 */
	private Integer investmentEnable;

    /** 是否显示，1是0否 */
	private Integer showEnable;

    /** 创建时间 */
	private Date createTime;

    /** 创建用户ID */
	private String createUserId;

    /** 创建用户名 */
	private String createUserName;

    /** 1表示删除0表示未删除 */
	private Integer deleted;
	
	/** 主办方 */
	private String sponsor;
	
	/** 会议内容 */
	private String contents;
	
	/** 微吼会议id */
	private String webinarId;
	
	/** 微吼会议主题 */
	private String webinarSubject;
	
	/** 人员上限(0时无限制) */
	private Integer upperlimit;
	
	/** 活动省 */
	private String province;
	
	/** 活动市 */
	private String city;
	
	/** 活动市区*/
	private String county;
	
	/** 报名开始结束时间查询条件 */
	@Transient
	private Date gtStartSignupTime;//大于
	@Transient
	private Date ltStartSignupTime;//小于
	@Transient
	private Date gtEndSignupTime;//大于
	@Transient
	private Date ltEndSignupTime;//小于
	/** 会议开始结束时间查询条件  */
	@Transient
	private Date gtStartTime;//大于
	@Transient
	private Date ltStartTime;//小于
	@Transient
	private Date gtEndTime;//大于
	@Transient
	private Date ltEndTime;//小于
	@Transient
	private String orderBy;//排序
	@Transient
	private Map<String,Object>  extProps = new HashMap<String,Object>();
	@Transient
	private String projIds;
	@Transient
	private String webinarIds;
	@Transient
	private String guests;
	@Transient
	private List<MeetingGuest> meetingGuests = new ArrayList<MeetingGuest>();
	@Transient
	private List<Project> meetingProjects = new ArrayList<Project>();
	@Transient
	private Integer isSignuped;//查询条件，是否已报名1已报名
	@Transient
	private Integer isAttentioned;//查询条件，是否关注1已关注
	@Transient
	private String userId;//查询条件，当前用户ID
	@Transient
	private String pname;//省
	@Transient
	private String cname;//市
	@Transient
	private String coname;//区

	@Transient
	private String meetingIds;//往届会议主键

	public Meeting(){}

	public List<MeetingSignup> getMeetingSignupList() {
		return meetingSignupList;
	}

	public void setMeetingSignupList(List<MeetingSignup> meetingSignupList) {
		this.meetingSignupList = meetingSignupList;
	}

	public String getMeetingIds() {
		return meetingIds;
	}

	public void setMeetingIds(String meetingIds) {
		this.meetingIds = meetingIds;
	}

	public List<MeetingSuccessive> getMeetingSuccessiveList() {
		return meetingSuccessiveList;
	}

	public void setMeetingSuccessiveList(List<MeetingSuccessive> meetingSuccessiveList) {
		this.meetingSuccessiveList = meetingSuccessiveList;
	}

	public List<MeetingRelation> getMeetingRelationList() {
		return meetingRelationList;
	}

	public void setMeetingRelationList(List<MeetingRelation> meetingRelationList) {
		this.meetingRelationList = meetingRelationList;
	}

	public String getWebinarIds() {
		return webinarIds;
	}

	public void setWebinarIds(String webinarIds) {
		this.webinarIds = webinarIds;
	}

	public String getPname() {
		return pname;
	}
	public void setPname(String pname) {
		this.pname = pname;
	}
	public String getCname() {
		return cname;
	}
	public void setCname(String cname) {
		this.cname = cname;
	}
	public String getConame() {
		return coname;
	}
	public void setConame(String coname) {
		this.coname = coname;
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
	public String getCounty() {
		return county;
	}
	public void setCounty(String county) {
		this.county = county;
	}
	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	/**
	 * 获取主键
	 */
	public String getId() {
    	return id;
    }
  	
	/**
	 * 设置主键
	 */
	public void setId(String id) {
    	this.id = id;
    }

	/**
	 * 获取会议主题
	 */
	public String getTitles() {
    	return titles;
    }
  	
	/**
	 * 设置会议主题
	 */
	public void setTitles(String titles) {
    	this.titles = titles;
    }

	/**
	 * 获取会议封面图地址
	 */
	public String getCoverImg() {
    	return coverImg;
    }
  	
	/**
	 * 设置会议封面图地址
	 */
	public void setCoverImg(String coverImg) {
    	this.coverImg = coverImg;
    }

	/**
	 * 获取会议类型
	 */
	public Integer getTypes() {
    	return types;
    }
  	
	/**
	 * 设置会议类型
	 */
	public void setTypes(Integer types) {
    	this.types = types;
    }

	/**
	 * 获取会议地点
	 */
	public String getPlace() {
    	return place;
    }
  	
	/**
	 * 设置会议地点
	 */
	public void setPlace(String place) {
    	this.place = place;
    }

	/**
	 * 获取会议开始时间
	 */
	public Date getStartTime() {
    	return startTime;
    }
  	
	/**
	 * 设置会议开始时间
	 */
	public void setStartTime(Date startTime) {
    	this.startTime = startTime;
    	calcStatus();
    }

	/**
	 * 获取会议结束时间
	 */
	public Date getEndTime() {
    	return endTime;
    }
  	
	/**
	 * 设置会议结束时间
	 */
	public void setEndTime(Date endTime) {
    	this.endTime = endTime;
    	calcStatus();
    }

	/**
	 * 获取门票价格
	 */
	public Double getTicketPrice() {
    	return ticketPrice;
    }
  	
	/**
	 * 设置门票价格
	 */
	public void setTicketPrice(Double ticketPrice) {
    	this.ticketPrice = ticketPrice;
    }

	/**
	 * 获取直播地址
	 */
	public String getLiveUrl() {
    	return liveUrl;
    }
  	
	/**
	 * 设置直播地址
	 */
	public void setLiveUrl(String liveUrl) {
    	this.liveUrl = liveUrl;
    }

	/**
	 * 获取会议状态
	 */
	public Integer getStatus() {
		return status;
    }
  	
	/**
	 * 设置会议状态
	 */
	public void setStatus(Integer status) {
    	this.status = getStatus();
    }

	/**
	 * 获取是否推荐首页，1是0否
	 */
	public Integer getRecommendEnable() {
    	return recommendEnable;
    }
  	
	/**
	 * 设置是否推荐首页，1是0否
	 */
	public void setRecommendEnable(Integer recommendEnable) {
    	this.recommendEnable = recommendEnable;
    }

	/**
	 * 获取是否显示，1是0否
	 */
	public Integer getShowEnable() {
    	return showEnable;
    }
  	
	/**
	 * 设置是否显示，1是0否
	 */
	public void setShowEnable(Integer showEnable) {
    	this.showEnable = showEnable;
    }

	/**
	 * 获取创建时间
	 */
	public Date getCreateTime() {
    	return createTime;
    }
  	
	/**
	 * 设置创建时间
	 */
	public void setCreateTime(Date createTime) {
    	this.createTime = createTime;
    }

	/**
	 * 获取创建用户ID
	 */
	public String getCreateUserId() {
    	return createUserId;
    }
  	
	/**
	 * 设置创建用户ID
	 */
	public void setCreateUserId(String createUserId) {
    	this.createUserId = createUserId;
    }

	/**
	 * 获取创建用户名
	 */
	public String getCreateUserName() {
    	return createUserName;
    }
  	
	/**
	 * 设置创建用户名
	 */
	public void setCreateUserName(String createUserName) {
    	this.createUserName = createUserName;
    }

	/**
	 * 获取1表示删除0表示未删除
	 */
	public Integer getDeleted() {
    	return deleted;
    }
  	
	/**
	 * 设置1表示删除0表示未删除
	 */
	public void setDeleted(Integer deleted) {
    	this.deleted = deleted;
    }
	public Map<String, Object> getExtProps() {
		return extProps;
	}

	public void setExtProps(Map<String, Object> extProps) {
		if(extProps==null){
			this.extProps.clear();
		}else{
			this.extProps = extProps;
		}
	}
	
	@SuppressWarnings("unchecked")
	public <T> T getExtProp(String key) {
		return (T) extProps.get(key);
	}
	@SuppressWarnings("unchecked")
	public <T> T getExtProp(String key,Object defaultValue) {
		return (T) (extProps.get(key)==null?defaultValue:extProps.get(key));
	}
	
	public void setExtProp(String key ,Object value) {
		extProps.put(key, value);
	}


	public String getProjIds() {
		return projIds;
	}


	public void setProjIds(String projIds) {
		this.projIds = projIds;
	}


	public String getGuests() {
		return guests;
	}


	public void setGuests(String guests) {
		this.guests = guests;
	}


	public List<MeetingGuest> getMeetingGuests() {
		return meetingGuests;
	}


	public void setMeetingGuests(List<MeetingGuest> meetingGuests) {
		if(meetingGuests==null){
			this.meetingGuests.clear();
		}else{
			this.meetingGuests = meetingGuests;
		}
	}


	public List<Project> getMeetingProjects() {
		return meetingProjects;
	}


	public void setMeetingProjects(List<Project> meetingProjects) {
		if(meetingProjects==null){
			this.meetingProjects.clear();
		}else{
			this.meetingProjects = meetingProjects;
		}
	}
	
	public void parse(){
		this.meetingGuests.clear();
		this.meetingProjects.clear();
		
		if(this.guests!=null&&this.guests.trim().length()>0){
			List<MeetingGuest> list = JSON.parseArray(this.guests,MeetingGuest.class);
			for(MeetingGuest g : list){
				g.setMeetingId(this.id);
				g.setCreateTime(new Date());
			}
			this.meetingGuests = list;
		}
		
		if(this.projIds!=null&&this.projIds.trim().length()>0){
			String[] projectIds = this.projIds.split(",");
			for(String id : projectIds){
				Project e = new Project();
				e.setId(id);
				this.meetingProjects.add(e);
			}
		}

		//往届会议
		if(this.meetingIds!=null&&this.meetingIds.trim().length()>0){
			String[] meetingIds = this.meetingIds.split(",");
			for(String id : meetingIds){
				MeetingSuccessive e = new MeetingSuccessive();
				e.setMeetingId(id);
				this.meetingSuccessiveList.add(e);
			}
		}
	}


	public String getSponsor() {
		return sponsor;
	}


	public void setSponsor(String sponsor) {
		this.sponsor = sponsor;
	}


	public String getContents() {
		return contents;
	}


	public void setContents(String contents) {
		this.contents = contents;
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


	public Integer getUpperlimit() {
		return upperlimit;
	}


	public void setUpperlimit(Integer upperlimit) {
		this.upperlimit = upperlimit;
	}


	public Date getStartSignupTime() {
		return startSignupTime;
	}


	public void setStartSignupTime(Date startSignupTime) {
		this.startSignupTime = startSignupTime;
		calcStatus();
	}


	public Date getEndSignupTime() {
		return endSignupTime;
	}


	public void setEndSignupTime(Date endSignupTime) {
		this.endSignupTime = endSignupTime;
		calcStatus();
	}


	public String getStatusDesc() {
		return statusDesc;
	}


	public void setStatusDesc(String statusDesc) {
		this.statusDesc = statusDesc;
	}


	public Date getGtStartSignupTime() {
		return gtStartSignupTime;
	}


	public void setGtStartSignupTime(Date gtStartSignupTime) {
		this.gtStartSignupTime = gtStartSignupTime;
	}


	public Date getLtStartSignupTime() {
		return ltStartSignupTime;
	}


	public void setLtStartSignupTime(Date ltStartSignupTime) {
		this.ltStartSignupTime = ltStartSignupTime;
	}


	public Date getGtEndSignupTime() {
		return gtEndSignupTime;
	}


	public void setGtEndSignupTime(Date gtEndSignupTime) {
		this.gtEndSignupTime = gtEndSignupTime;
	}


	public Date getLtEndSignupTime() {
		return ltEndSignupTime;
	}


	public void setLtEndSignupTime(Date ltEndSignupTime) {
		this.ltEndSignupTime = ltEndSignupTime;
	}


	public Date getGtStartTime() {
		return gtStartTime;
	}


	public void setGtStartTime(Date gtStartTime) {
		this.gtStartTime = gtStartTime;
	}


	public Date getLtStartTime() {
		return ltStartTime;
	}


	public void setLtStartTime(Date ltStartTime) {
		this.ltStartTime = ltStartTime;
	}


	public Date getGtEndTime() {
		return gtEndTime;
	}


	public void setGtEndTime(Date gtEndTime) {
		this.gtEndTime = gtEndTime;
	}


	public Date getLtEndTime() {
		return ltEndTime;
	}


	public void setLtEndTime(Date ltEndTime) {
		this.ltEndTime = ltEndTime;
	}

	public String getOrderBy() {
		return orderBy;
	}


	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}
	public Integer getInvestmentEnable() {
		return investmentEnable;
	}


	public void setInvestmentEnable(Integer investmentEnable) {
		this.investmentEnable = investmentEnable;
	}


	public Integer getIsSignuped() {
		return isSignuped;
	}


	public void setIsSignuped(Integer isSignuped) {
		this.isSignuped = isSignuped;
	}


	public Integer getIsAttentioned() {
		return isAttentioned;
	}


	public void setIsAttentioned(Integer isAttentioned) {
		this.isAttentioned = isAttentioned;
	}


	public String getUserId() {
		return userId;
	}


	public void setUserId(String userId) {
		this.userId = userId;
	}


	public Integer calcStatus(){
		Date curDate = new Date();
		if(this.endTime!=null&&curDate.compareTo(this.endTime)>0){
			this.status= 5;//已结束
			this.statusDesc = "已结束";
			return status;
		}
		if(this.startTime!=null&&this.endTime!=null
				&&curDate.compareTo(this.startTime)>=0&&curDate.compareTo(this.endTime)<=0){
			this.status= 4;//进行中
			this.statusDesc = "进行中";
			return status;
		}
		if(this.endSignupTime!=null&&this.startTime!=null
				&&curDate.compareTo(this.endSignupTime)>0&&curDate.compareTo(this.startTime)<=0){
			this.status= 3;//报名结束
			this.statusDesc = "报名结束";
			return status;
		}
		if(this.startSignupTime!=null&&this.endSignupTime!=null&&curDate.compareTo(this.startSignupTime)>0
				&&curDate.compareTo(this.endSignupTime)<=0){
			this.status= 2;//报名中
			this.statusDesc = "报名中";
			return status;
		}
		if(this.startSignupTime!=null&&curDate.compareTo(this.startSignupTime)<=0){
			this.status= 1;//预告中
			this.statusDesc = "预告中";
			return status;
		}

		this.status = null;
		this.statusDesc = null;
		return status;
	}
}
