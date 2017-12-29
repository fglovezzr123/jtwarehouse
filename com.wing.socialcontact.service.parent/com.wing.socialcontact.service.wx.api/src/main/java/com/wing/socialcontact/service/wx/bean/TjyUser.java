package com.wing.socialcontact.service.wx.bean;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * TJY_USER 用户信息表
 * 
 * @author zengmin
 * @date 2017-04-07 01:41:07
 * @version 1.0
 */
@Table(name = "TJY_USER")
public class TjyUser implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** 主键和mallUser编号保持一致 */
	@Id
	private String id;

	/**  */
	private Long mallUser;

	/** 微信openid */
	private String openId;

	/** 公司 */
	private String comName;

	/** 公司简介 */
	private String comProfile;

	/** 职位编码(关联字典表) */
	private String job;

	/** 微信号 */
	private String wechart;

	/** 个人简介 */
	private String userProfile;

	/** 省(关联字典表) */
	private String province;

	/** 市(关联字典表) */
	private String city;

	/** 县(关联字典表) */
	private String county;

	/** 区(关联字典表) */
	private String region;

	/** 行业代码(关联字典表) */
	private String industry;

	/** 昵称 */
	private String nickname;

	/** 头像 */
	private String headPortrait;

	/** 留言 */
	private String leaveMsg;

	/** 访问量 */
	private Double visitQuantity;

	/** 首页图片 */
	private String homepagePic;

	/** 是否实名(0,未实名，1已实名) */
	private Integer isRealname;

	/** 用户状态（0无效、1有效、2关注企业号） */
	private Integer status;

	/** 姓名 */
	private String trueName;

	/** 详细地址 */
	private String address;

	/** 电话注册手机（绑定手机） */
	private String mobile;

	private String userSignature;

	/** 是否大咖（0不是1：是） */
	private Integer isdk;
	/** 成为大咖的时间 */
	private Date dkDate;

	/**
	 * 认证审核状态（0未提交1提交审核2审核通过3审核不通过）
	 */
	private Integer reconStatus;

	/**
	 * 认证审核时间
	 */
	private Date reconDate;
	
	/**
	 * 认证手机号
	 */
	private String reconMobile;

	/**
	 * 认证人(后台用户)
	 */
	private String reconUserId;

	/** 提交认证时间 */
	private Date tjReconDate;

	// 一下字段作用于企服云app
	/**
	 * 绑定手机号，用于app登录
	 */
	private String bindPhone;

	/**
	 * 微信unionid
	 */
	private String unionId;

	/**
	 * 首次绑定时间
	 */
	private Date firstBindTime;

	/**
	 * 最后绑定时间 ---更改手机号
	 */
	private Date lastBindTime;

	/**
	 * 微信唯一标识
	 */
	private String wxUniqueId;

	/**
	 * app同步消息记录至天九云标识：0未同步，1同步
	 */
	private Integer appSynMsgToTjy;
	/**
	 * app同步消息记录至天九云时间
	 */
	private Date appSynMsgTime;

	/** 认证客服电话 */
	private String kfTelephone;
	
	/** 注册资金认证时填写(单位万元)*/
	private Double reconCapital;
	
	/**注册时间
	 */
	private Date regDate;
	
	/**最后一次访问时间
	 */
	private Date lastVisitDate;
	
	/** 大咖权重 */
	private Integer sort;
	/** 智同道合权重 */
	private Integer ztdhsort;

	@Transient
	private String orderby;
	
	/** 是否志同道合推荐（0不是1：是） */
	private Integer isztdh;
	
	/** 是否免打扰（0：可打扰 1：免打扰） */
	private Integer isdisturb;
	
	
	/**
	 * 推荐人姓名
	 */
	private String tjName;
	/**
	 * 推荐人电话
	 */
	private String tjMobile;
	/**
	 * 推荐人id
	 */
	private String tjId;
	/**认证有效截止时间
	 */
	private Date lastRegDate;
	/**
	 * 用户勋章
	 */
	private String honorTitle;
	/**
	 * 用户勋章
	 */
	private String honorFlag;

	/**
	 * 拍照审核状态
	 */
	@Transient
	private Integer photoStatus;

	public Integer getPhotoStatus() {
		return photoStatus;
	}

	public void setPhotoStatus(Integer photoStatus) {
		this.photoStatus = photoStatus;
	}

	public Integer getIsdisturb() {
		return isdisturb;
	}



	public void setIsdisturb(Integer isdisturb) {
		this.isdisturb = isdisturb;
	}



	public Integer getZtdhsort() {
		return ztdhsort;
	}



	public void setZtdhsort(Integer ztdhsort) {
		this.ztdhsort = ztdhsort;
	}



	public Integer getIsztdh() {
		return isztdh;
	}



	public void setIsztdh(Integer isztdh) {
		this.isztdh = isztdh;
	}



	public TjyUser() {
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
	 * 获取
	 */
	public Long getMallUser() {
		return mallUser;
	}

	/**
	 * 设置
	 */
	public void setMallUser(Long mallUser) {
		this.mallUser = mallUser;
	}

	/**
	 * 获取微信openid
	 */
	public String getOpenId() {
		return openId;
	}

	/**
	 * 设置微信openid
	 */
	public void setOpenId(String openId) {
		this.openId = openId;
	}

	/**
	 * 获取公司
	 */
	public String getComName() {
		return comName;
	}

	/**
	 * 设置公司
	 */
	public void setComName(String comName) {
		this.comName = comName;
	}

	/**
	 * 获取公司简介
	 */
	public String getComProfile() {
		return comProfile;
	}

	/**
	 * 设置公司简介
	 */
	public void setComProfile(String comProfile) {
		this.comProfile = comProfile;
	}

	/**
	 * 获取职位编码(关联字典表)
	 */
	public String getJob() {
		return job;
	}

	/**
	 * 设置职位编码(关联字典表)
	 */
	public void setJob(String job) {
		this.job = job;
	}

	/**
	 * 获取微信号
	 */
	public String getWechart() {
		return wechart;
	}

	/**
	 * 设置微信号
	 */
	public void setWechart(String wechart) {
		this.wechart = wechart;
	}

	/**
	 * 获取个人简介
	 */
	public String getUserProfile() {
		return userProfile;
	}

	/**
	 * 设置个人简介
	 */
	public void setUserProfile(String userProfile) {
		this.userProfile = userProfile;
	}

	/**
	 * 获取省(关联字典表)
	 */
	public String getProvince() {
		return province;
	}

	/**
	 * 设置省(关联字典表)
	 */
	public void setProvince(String province) {
		this.province = province;
	}

	/**
	 * 获取市(关联字典表)
	 */
	public String getCity() {
		return city;
	}

	/**
	 * 设置市(关联字典表)
	 */
	public void setCity(String city) {
		this.city = city;
	}

	/**
	 * 获取县(关联字典表)
	 */
	public String getCounty() {
		return county;
	}

	/**
	 * 设置县(关联字典表)
	 */
	public void setCounty(String county) {
		this.county = county;
	}

	/**
	 * 获取区(关联字典表)
	 */
	public String getRegion() {
		return region;
	}

	/**
	 * 设置区(关联字典表)
	 */
	public void setRegion(String region) {
		this.region = region;
	}

	/**
	 * 获取行业代码(关联字典表)
	 */
	public String getIndustry() {
		return industry;
	}

	/**
	 * 设置行业代码(关联字典表)
	 */
	public void setIndustry(String industry) {
		this.industry = industry;
	}

	/**
	 * 获取昵称
	 */
	public String getNickname() {
		return nickname;
	}

	/**
	 * 设置昵称
	 */
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	/**
	 * 获取头像
	 */
	public String getHeadPortrait() {
		return headPortrait;
	}

	/**
	 * 设置头像
	 */
	public void setHeadPortrait(String headPortrait) {
		this.headPortrait = headPortrait;
	}

	/**
	 * 获取留言
	 */
	public String getLeaveMsg() {
		return leaveMsg;
	}

	/**
	 * 设置留言
	 */
	public void setLeaveMsg(String leaveMsg) {
		this.leaveMsg = leaveMsg;
	}

	/**
	 * 获取访问量
	 */
	public Double getVisitQuantity() {
		return visitQuantity;
	}

	/**
	 * 设置访问量
	 */
	public void setVisitQuantity(Double visitQuantity) {
		this.visitQuantity = visitQuantity;
	}

	/**
	 * 获取首页图片
	 */
	public String getHomepagePic() {
		return homepagePic;
	}

	/**
	 * 设置首页图片
	 */
	public void setHomepagePic(String homepagePic) {
		this.homepagePic = homepagePic;
	}

	/**
	 * 获取是否实名(0,未实名，1已实名)
	 */
	public Integer getIsRealname() {
		return isRealname;
	}

	/**
	 * 设置是否实名(0,未实名，1已实名)
	 */
	public void setIsRealname(Integer isRealname) {
		this.isRealname = isRealname;
	}

	/**
	 * 获取用户状态（0无效、1有效、2关注企业号）
	 */
	public Integer getStatus() {
		return status;
	}

	/**
	 * 设置用户状态（0无效、1有效、2关注企业号）
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getTrueName() {
		return trueName;
	}

	public void setTrueName(String trueName) {
		this.trueName = trueName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	/**获取电话注册手机（绑定手机） */
	public String getMobile() {
		return mobile;
	}

	/**设置电话注册手机（绑定手机） */
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Integer getIsdk() {
		return isdk;
	}

	public void setIsdk(Integer isdk) {
		this.isdk = isdk;
	}

	public Date getDkDate() {
		return dkDate;
	}

	public void setDkDate(Date dkDate) {
		this.dkDate = dkDate;
	}

	public String getUserSignature() {
		return userSignature;
	}

	public void setUserSignature(String userSignature) {
		this.userSignature = userSignature;
	}

	public Integer getReconStatus() {
		return reconStatus;
	}

	public void setReconStatus(Integer reconStatus) {
		this.reconStatus = reconStatus;
	}

	public Date getReconDate() {
		return reconDate;
	}

	public void setReconDate(Date reconDate) {
		this.reconDate = reconDate;
	}

	public String getReconUserId() {
		return reconUserId;
	}

	public void setReconUserId(String reconUserId) {
		this.reconUserId = reconUserId;
	}

	public Date getTjReconDate() {
		return tjReconDate;
	}

	public void setTjReconDate(Date tjReconDate) {
		this.tjReconDate = tjReconDate;
	}

	public String getBindPhone() {
		return bindPhone;
	}

	public void setBindPhone(String bindPhone) {
		this.bindPhone = bindPhone;
	}

	public String getUnionId() {
		return unionId;
	}

	public void setUnionId(String unionId) {
		this.unionId = unionId;
	}

	public Date getFirstBindTime() {
		return firstBindTime;
	}

	public void setFirstBindTime(Date firstBindTime) {
		this.firstBindTime = firstBindTime;
	}

	public Date getLastBindTime() {
		return lastBindTime;
	}

	public void setLastBindTime(Date lastBindTime) {
		this.lastBindTime = lastBindTime;
	}

	public String getWxUniqueId() {
		return wxUniqueId;
	}

	public void setWxUniqueId(String wxUniqueId) {
		this.wxUniqueId = wxUniqueId;
	}

	public Integer getAppSynMsgToTjy() {
		return appSynMsgToTjy;
	}

	public void setAppSynMsgToTjy(Integer appSynMsgToTjy) {
		this.appSynMsgToTjy = appSynMsgToTjy;
	}

	public Date getAppSynMsgTime() {
		return appSynMsgTime;
	}

	public void setAppSynMsgTime(Date appSynMsgTime) {
		this.appSynMsgTime = appSynMsgTime;
	}

	public String getKfTelephone() {
		return kfTelephone;
	}

	public void setKfTelephone(String kfTelephone) {
		this.kfTelephone = kfTelephone;
	}

	public String getOrderby() {
		return orderby;
	}

	public void setOrderby(String orderby) {
		this.orderby = orderby;
	}

	public String getReconMobile() {
		return reconMobile;
	}

	public void setReconMobile(String reconMobile) {
		this.reconMobile = reconMobile;
	}

	public Double getReconCapital() {
		return reconCapital;
	}

	public void setReconCapital(Double reconCapital) {
		this.reconCapital = reconCapital;
	}

	public Date getRegDate() {
		return regDate;
	}

	public void setRegDate(Date regDate) {
		this.regDate = regDate;
	}

	public Date getLastVisitDate() {
		return lastVisitDate;
	}

	public void setLastVisitDate(Date lastVisitDate) {
		this.lastVisitDate = lastVisitDate;
	}



	public String getTjName() {
		return tjName;
	}



	public void setTjName(String tjName) {
		this.tjName = tjName;
	}



	public String getTjMobile() {
		return tjMobile;
	}



	public void setTjMobile(String tjMobile) {
		this.tjMobile = tjMobile;
	}



	public String getTjId() {
		return tjId;
	}



	public void setTjId(String tjId) {
		this.tjId = tjId;
	}



	public Date getLastRegDate() {
		return lastRegDate;
	}



	public void setLastRegDate(Date lastRegDate) {
		this.lastRegDate = lastRegDate;
	}



	public String getHonorTitle() {
		return honorTitle;
	}



	public void setHonorTitle(String honorTitle) {
		this.honorTitle = honorTitle;
	}



	public String getHonorFlag() {
		return honorFlag;
	}



	public void setHonorFlag(String honorFlag) {
		this.honorFlag = honorFlag;
	}
	
	
	
	
	
	
}
