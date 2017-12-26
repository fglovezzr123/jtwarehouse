package com.wing.socialcontact.service.wx.bean;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * tjy_meeting_signup 
 * 
 * @author liangwj
 * @version 1.0
 */
@Table(name = "tjy_meeting_signup")
public class MeetingSignup implements Serializable{
	private static final long serialVersionUID = 1L;

    /** 主键 */
    @Id
	@Column(name = "id")
	@GeneratedValue(generator = "UUID")
	private String id;

    /** 会议主键 */
	private String meetingId;

    /** 用户ID */
	private String userId;

    /** 报名时间 */
	private Date signupTime;

    /** 逻辑删除标识，1删除0不删除 */
	private Integer deleted;

    /** 是否免费，1免费0收费 */
	private Integer isFree;

    /** 订单ID */
	private String orderId;

    /** 支付状态，1预支付2已支付 */
	private Integer orderStatus;

    /** 支付时间 */
	private Date payTime;

    /** 主营业务 */
	private String mainBusiness;

    /** 注册资本 */
	private String regCapital;

    /** 实缴资本 */
	private Double payCapital;

    /** 总资产 */
	private Double totalAssets;

    /** 年销售额 */
	private Double annualSales;

    /** 参会方式1上2线下 */
	private Integer attendType;

    /** 其他需求 */
	private String otherReq;

    /** 天就联系人 */
	private String tjLinkMan;

    /** 推荐联系人 */
	private String recLinkMan;
	
	/** 类别，1正常支付2白名单支付 */
	private Integer payType;

    /** 姓名 */
	@Transient 
	private String trueName;
	
	/** 昵称 */
	@Transient 
	private String nickname;

    /** 电话 */
	private String mobile;

    /** 所在公司 */
	@Transient 
	private String comName;
	
	@Transient 
    private Date gtsignupTime;
    
    @Transient
    private Date gesignupTime;
    
    @Transient
    private Date ltsignupTime;
    
    @Transient
    private Date lesignupTime;
    
	@Transient 
    private Date gtpayTime;
    
    @Transient
    private Date gepayTime;
    
    @Transient
    private Date ltpayTime;
    
    @Transient
    private Date lepayTime;
    
    @Transient
    private String types;
    
    @Transient
    private String typesName;
    
    @Transient
    private String kfTelephone;
    
    
    public String getKfTelephone() {
		return kfTelephone;
	}
	public void setKfTelephone(String kfTelephone) {
		this.kfTelephone = kfTelephone;
	}
	@Transient
    private String titles;

    /** 优惠券金额*/
    private Double coupon;
    
    /** 门票价格 */
	@Transient
	private Double ticketPrice;
	
	/**
	 * 会议开始提醒消息发送完成的时间
	 */
	private Date remindTime;
	@Transient
	private Date startTime;
	
	@Transient
	private Map<String,Object>  extProps = new HashMap<String,Object>();
	
	public MeetingSignup(){
		super();
	}
	public MeetingSignup(String meetingId,String userId){
		this();
		this.meetingId = meetingId;
		this.userId = userId;
	}

	
	
	
	public Double getCoupon() {
		return coupon;
	}
	public void setCoupon(Double coupon) {
		this.coupon = coupon;
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
	 * 获取会议主键
	 */
	public String getMeetingId() {
    	return meetingId;
    }
  	
	/**
	 * 设置会议主键
	 */
	public void setMeetingId(String meetingId) {
    	this.meetingId = meetingId;
    }

    

	/**
	 * 获取用户ID
	 */
	public String getUserId() {
    	return userId;
    }
  	
	/**
	 * 设置用户ID
	 */
	public void setUserId(String userId) {
    	this.userId = userId;
    }

    

	/**
	 * 获取报名时间
	 */
	public Date getSignupTime() {
    	return signupTime;
    }
  	
	/**
	 * 设置报名时间
	 */
	public void setSignupTime(Date signupTime) {
    	this.signupTime = signupTime;
    }

	public Date getGtsignupTime() {
    	return gtsignupTime;
    }
    
    public void setGtsignupTime(Date gtsignupTime) {
    	this.gtsignupTime = gtsignupTime;
    }
    
    public Date getGesignupTime() {
    	return signupTime;
    }
    
    public void setGesignupTime(Date gesignupTime) {
    	this.gesignupTime = gesignupTime;
    }
    
    public Date getLtsignupTime() {
    	return ltsignupTime;
    }
    
    public void setLtsignupTime(Date ltsignupTime) {
    	this.ltsignupTime = ltsignupTime;
    }
    
    public Date getLesignupTime() {
    	return lesignupTime;
    }
    
    public void setLesignupTime(Date lesignupTime) {
    	this.lesignupTime = lesignupTime;
    }
    

	/**
	 * 获取逻辑删除标识，1删除0不删除
	 */
	public Integer getDeleted() {
    	return deleted;
    }
  	
	/**
	 * 设置逻辑删除标识，1删除0不删除
	 */
	public void setDeleted(Integer deleted) {
    	this.deleted = deleted;
    }

    

	/**
	 * 获取是否免费，1免费0收费
	 */
	public Integer getIsFree() {
    	return isFree;
    }
  	
	/**
	 * 设置是否免费，1免费0收费
	 */
	public void setIsFree(Integer isFree) {
    	this.isFree = isFree;
    }

    

	/**
	 * 获取订单ID
	 */
	public String getOrderId() {
    	return orderId;
    }
  	
	/**
	 * 设置订单ID
	 */
	public void setOrderId(String orderId) {
    	this.orderId = orderId;
    }

    

	/**
	 * 获取支付状态，1预支付2已支付
	 */
	public Integer getOrderStatus() {
    	return orderStatus;
    }
  	
	/**
	 * 设置支付状态，1预支付2已支付
	 */
	public void setOrderStatus(Integer orderStatus) {
    	this.orderStatus = orderStatus;
    }

    

	/**
	 * 获取支付时间
	 */
	public Date getPayTime() {
    	return payTime;
    }
  	
	/**
	 * 设置支付时间
	 */
	public void setPayTime(Date payTime) {
    	this.payTime = payTime;
    }

	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getGtpayTime() {
    	return gtpayTime;
    }
    
    public void setGtpayTime(Date gtpayTime) {
    	this.gtpayTime = gtpayTime;
    }
    
    public Date getGepayTime() {
    	return payTime;
    }
    
    public void setGepayTime(Date gepayTime) {
    	this.gepayTime = gepayTime;
    }
    
    public Date getLtpayTime() {
    	return ltpayTime;
    }
    
    public void setLtpayTime(Date ltpayTime) {
    	this.ltpayTime = ltpayTime;
    }
    
    public Date getLepayTime() {
    	return lepayTime;
    }
    
    public void setLepayTime(Date lepayTime) {
    	this.lepayTime = lepayTime;
    }
    

	/**
	 * 获取主营业务
	 */
	public String getMainBusiness() {
    	return mainBusiness;
    }
  	
	/**
	 * 设置主营业务
	 */
	public void setMainBusiness(String mainBusiness) {
    	this.mainBusiness = mainBusiness;
    }

    

	/**
	 * 获取注册资本
	 */
	public String getRegCapital() {
    	return regCapital;
    }
  	
	/**
	 * 设置注册资本
	 */
	public void setRegCapital(String regCapital) {
    	this.regCapital = regCapital;
    }

    

	/**
	 * 获取实缴资本
	 */
	public Double getPayCapital() {
    	return payCapital;
    }
  	
	/**
	 * 设置实缴资本
	 */
	public void setPayCapital(Double payCapital) {
    	this.payCapital = payCapital;
    }

    

	/**
	 * 获取总资产
	 */
	public Double getTotalAssets() {
    	return totalAssets;
    }
  	
	/**
	 * 设置总资产
	 */
	public void setTotalAssets(Double totalAssets) {
    	this.totalAssets = totalAssets;
    }

    

	/**
	 * 获取年销售额
	 */
	public Double getAnnualSales() {
    	return annualSales;
    }
  	
	/**
	 * 设置年销售额
	 */
	public void setAnnualSales(Double annualSales) {
    	this.annualSales = annualSales;
    }

    

	/**
	 * 获取参会方式1上2线下
	 */
	public Integer getAttendType() {
    	return attendType;
    }
  	
	/**
	 * 设置参会方式1上2线下
	 */
	public void setAttendType(Integer attendType) {
    	this.attendType = attendType;
    }

    

	/**
	 * 获取其他需求
	 */
	public String getOtherReq() {
    	return otherReq;
    }
  	
	/**
	 * 设置其他需求
	 */
	public void setOtherReq(String otherReq) {
    	this.otherReq = otherReq;
    }

    

	/**
	 * 获取天就联系人
	 */
	public String getTjLinkMan() {
    	return tjLinkMan;
    }
  	
	/**
	 * 设置天就联系人
	 */
	public void setTjLinkMan(String tjLinkMan) {
    	this.tjLinkMan = tjLinkMan;
    }

    

	/**
	 * 获取推荐联系人
	 */
	public String getRecLinkMan() {
    	return recLinkMan;
    }
  	
	/**
	 * 设置推荐联系人
	 */
	public void setRecLinkMan(String recLinkMan) {
    	this.recLinkMan = recLinkMan;
    }

    

	/**
	 * 获取姓名
	 */
	public String getTrueName() {
    	return trueName;
    }
  	
	/**
	 * 设置姓名
	 */
	public void setTrueName(String trueName) {
    	this.trueName = trueName;
    }

    

	/**
	 * 获取电话
	 */
	public String getMobile() {
    	return mobile;
    }
  	
	/**
	 * 设置电话
	 */
	public void setMobile(String mobile) {
    	this.mobile = mobile;
    }

    

	/**
	 * 获取所在公司
	 */
	public String getComName() {
    	return comName;
    }
  	
	/**
	 * 设置所在公司
	 */
	public void setComName(String comName) {
    	this.comName = comName;
    }

    
	public String getTitles() {
		return titles;
	}
	public void setTitles(String titles) {
		this.titles = titles;
	}
	public Double getTicketPrice() {
		return ticketPrice;
	}
	public void setTicketPrice(Double ticketPrice) {
		this.ticketPrice = ticketPrice;
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
	
	public void setExtProp(String key ,Object value) {
		extProps.put(key, value);
	}
	public String getTypes() {
		return types;
	}
	public void setTypes(String types) {
		this.types = types;
		setTypesName(types);
	}
	public String getTypesName() {
		return "1".equals(types)?"线上会议":("2".equals(types)?"线下会议":"");
	}
	public void setTypesName(String typesName) {
		this.typesName = "1".equals(types)?"线上会议":("2".equals(types)?"线下会议":"");
	}
	public Integer getPayType() {
		return payType;
	}
	public void setPayType(Integer payType) {
		this.payType = payType;
	}
	public Date getRemindTime() {
		return remindTime;
	}
	public void setRemindTime(Date remindTime) {
		this.remindTime = remindTime;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
}
