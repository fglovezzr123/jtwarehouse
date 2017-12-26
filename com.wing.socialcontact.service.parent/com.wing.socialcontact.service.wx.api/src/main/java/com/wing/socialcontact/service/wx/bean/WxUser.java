package com.wing.socialcontact.service.wx.bean;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * USER
 * 
 * @author zengmin
 * @date 2017-04-03 23:44:43
 * @version 1.0
 */
@Table(name = "USER")
public class WxUser implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**  */
	@Id
	private Long id;

	/**  */
	private Date addtime;

	/**  */
	private Boolean deletestatus;

	/**  */
	private String msn;

	/**  */
	private String qq;

	/**  */
	private String ww;

	/**  */
	private String address;

	/**  */
	private Double availablebalance;

	/**  */
	private Date birthday;

	/**  */
	private String email;

	/**  */
	private Double freezeblance;

	/**  */
	private Integer gold;

	/**  */
	private Integer integral;

	/**  */
	private Date lastlogindate;

	/**  */
	private String lastloginip;

	/**  */
	private Integer logincount;

	/**  */
	private Date logindate;

	/**  */
	private String loginip;

	/**  */
	private String mobile;

	/**  */
	private String password;

	/**  */
	private Integer report;

	/** 1男 2女 0未知 */
	private Integer sex;

	/** 用户状态（0无效、1有效、2关注企业号）--是否关注企业号不使用该状态字段，使用tjyUser表status */
	private Integer status;

	/**  */
	private String telephone;

	/**  */
	private String truename;

	/**  */
	private String username;

	/**  */
	private String userrole;

	/**  */
	private Integer userCredit;

	/**  */
	private Long photoId;

	/**  */
	private Long storeId;

	/**  */
	private String qqOpenid;

	/**  */
	private String sinaOpenid;

	/**  */
	private String storeQuickMenu;

	/**  */
	private Long parentId;

	/**  */
	private Integer years;

	/**  */
	private Long areaId;

	/** 用户来源（1微信2app） */
	private Byte usertype;

	/** 累计返利金额 */
	private Double rebateTotal;

	/**  */
	private Double subcommission;

	/** 是否是第三方用户（0否，1是） */
	private Long thirdPartyUser;

	/** 推送用户player_id */
	private String pushuserid;

	/** 累计积分 */
	private Integer integralTotal;

	/** 累计经验值 */
	private Integer empiricalTotal;

	/** 用户等级 */
	private String level;

	/** 被邀请人ID */
	private Long inviterUserId;

	/** æ¸¸å®¢session id */
	private String cartSessionId;

	/** 鍦板尯 */
	private String areaInfo;

	/** 璇︾粏鍦板潃 */
	private String addressInfo;

	/** 微信用户编号 */
	private String wxUserId;
	/**
	 * 用户图像地址
	 */
	private String imgUrl;
	/**
	 * 昵称
	 */
	private String nickName;
	/**
	 * J币
	 */
	private Double jbAmount;
	/**
	 * 互助宝总金额
	 */
	private Double hzbAmount;

	/** 互助宝开通标记（0未开通1已开通/已启用2已停用3已过期） */
	private Integer hzbOpenFlag;

	/** 互助宝开通时间 */
	private Date hzbOpenTime;

	/** 互助宝开通审核人 */
	private String hzbOpenShUserId;
	/**
	 * 互助宝等级
	 */
	private Integer hzbLevel;

	/** app是否登录过(1登录过) */
	private Integer isAppLogin;

	/** 用户对应微信公众号唯一二维码用于邀请好友 */
	private String hdticket;

	@Transient
	private TjyUser tjyUser;

	/**
	 * 后台互助宝查询使用
	 */
	@Transient
	private String shUser;

	@Transient
	private String keyword;

	public WxUser() {
		this.hzbOpenFlag = 0;//设置初始值
	}

	/**
	 * 获取
	 */
	public Long getId() {
		return id;
	}

	/**
	 * 设置
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * 获取
	 */
	public Date getAddtime() {
		return addtime;
	}

	/**
	 * 设置
	 */
	public void setAddtime(Date addtime) {
		this.addtime = addtime;
	}

	/**
	 * 获取
	 */
	public Boolean getDeletestatus() {
		return deletestatus;
	}

	/**
	 * 设置
	 */
	public void setDeletestatus(Boolean deletestatus) {
		this.deletestatus = deletestatus;
	}

	/**
	 * 获取
	 */
	public String getMsn() {
		return msn;
	}

	/**
	 * 设置
	 */
	public void setMsn(String msn) {
		this.msn = msn;
	}

	/**
	 * 获取
	 */
	public String getQq() {
		return qq;
	}

	/**
	 * 设置
	 */
	public void setQq(String qq) {
		this.qq = qq;
	}

	/**
	 * 获取
	 */
	public String getWw() {
		return ww;
	}

	/**
	 * 设置
	 */
	public void setWw(String ww) {
		this.ww = ww;
	}

	/**
	 * 获取
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * 设置
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * 获取
	 */
	public Double getAvailablebalance() {
		return availablebalance;
	}

	/**
	 * 设置
	 */
	public void setAvailablebalance(Double availablebalance) {
		this.availablebalance = availablebalance;
	}

	/**
	 * 获取
	 */
	public Date getBirthday() {
		return birthday;
	}

	/**
	 * 设置
	 */
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	/**
	 * 获取
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * 设置
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * 获取
	 */
	public Double getFreezeblance() {
		return freezeblance;
	}

	/**
	 * 设置
	 */
	public void setFreezeblance(Double freezeblance) {
		this.freezeblance = freezeblance;
	}

	/**
	 * 获取
	 */
	public Integer getGold() {
		return gold;
	}

	/**
	 * 设置
	 */
	public void setGold(Integer gold) {
		this.gold = gold;
	}

	/**
	 * 获取
	 */
	public Integer getIntegral() {
		return integral;
	}

	/**
	 * 设置
	 */
	public void setIntegral(Integer integral) {
		this.integral = integral;
	}

	/**
	 * 获取
	 */
	public Date getLastlogindate() {
		return lastlogindate;
	}

	/**
	 * 设置
	 */
	public void setLastlogindate(Date lastlogindate) {
		this.lastlogindate = lastlogindate;
	}

	/**
	 * 获取
	 */
	public String getLastloginip() {
		return lastloginip;
	}

	/**
	 * 设置
	 */
	public void setLastloginip(String lastloginip) {
		this.lastloginip = lastloginip;
	}

	/**
	 * 获取
	 */
	public Integer getLogincount() {
		return logincount;
	}

	/**
	 * 设置
	 */
	public void setLogincount(Integer logincount) {
		this.logincount = logincount;
	}

	/**
	 * 获取
	 */
	public Date getLogindate() {
		return logindate;
	}

	/**
	 * 设置
	 */
	public void setLogindate(Date logindate) {
		this.logindate = logindate;
	}

	/**
	 * 获取
	 */
	public String getLoginip() {
		return loginip;
	}

	/**
	 * 设置
	 */
	public void setLoginip(String loginip) {
		this.loginip = loginip;
	}

	/**
	 * 获取
	 */
	public String getMobile() {
		return mobile;
	}

	/**
	 * 设置
	 */
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	/**
	 * 获取
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * 设置
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * 获取
	 */
	public Integer getReport() {
		return report;
	}

	/**
	 * 设置
	 */
	public void setReport(Integer report) {
		this.report = report;
	}

	/**
	 * 获取
	 */
	public Integer getSex() {
		return sex;
	}

	/**
	 * 设置
	 */
	public void setSex(Integer sex) {
		this.sex = sex;
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

	/**
	 * 获取
	 */
	public String getTelephone() {
		return telephone;
	}

	/**
	 * 设置
	 */
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	/**
	 * 获取
	 */
	public String getTruename() {
		return truename;
	}

	/**
	 * 设置
	 */
	public void setTruename(String truename) {
		this.truename = truename;
	}

	/**
	 * 获取
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * 设置
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * 获取
	 */
	public String getUserrole() {
		return userrole;
	}

	/**
	 * 设置
	 */
	public void setUserrole(String userrole) {
		this.userrole = userrole;
	}

	/**
	 * 获取
	 */
	public Integer getUserCredit() {
		return userCredit;
	}

	/**
	 * 设置
	 */
	public void setUserCredit(Integer userCredit) {
		this.userCredit = userCredit;
	}

	/**
	 * 获取
	 */
	public Long getPhotoId() {
		return photoId;
	}

	/**
	 * 设置
	 */
	public void setPhotoId(Long photoId) {
		this.photoId = photoId;
	}

	/**
	 * 获取
	 */
	public Long getStoreId() {
		return storeId;
	}

	/**
	 * 设置
	 */
	public void setStoreId(Long storeId) {
		this.storeId = storeId;
	}

	/**
	 * 获取
	 */
	public String getQqOpenid() {
		return qqOpenid;
	}

	/**
	 * 设置
	 */
	public void setQqOpenid(String qqOpenid) {
		this.qqOpenid = qqOpenid;
	}

	/**
	 * 获取
	 */
	public String getSinaOpenid() {
		return sinaOpenid;
	}

	/**
	 * 设置
	 */
	public void setSinaOpenid(String sinaOpenid) {
		this.sinaOpenid = sinaOpenid;
	}

	/**
	 * 获取
	 */
	public String getStoreQuickMenu() {
		return storeQuickMenu;
	}

	/**
	 * 设置
	 */
	public void setStoreQuickMenu(String storeQuickMenu) {
		this.storeQuickMenu = storeQuickMenu;
	}

	/**
	 * 获取
	 */
	public Long getParentId() {
		return parentId;
	}

	/**
	 * 设置
	 */
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	/**
	 * 获取
	 */
	public Integer getYears() {
		return years;
	}

	/**
	 * 设置
	 */
	public void setYears(Integer years) {
		this.years = years;
	}

	/**
	 * 获取
	 */
	public Long getAreaId() {
		return areaId;
	}

	/**
	 * 设置
	 */
	public void setAreaId(Long areaId) {
		this.areaId = areaId;
	}

	/**
	 * 获取
	 */
	public Byte getUsertype() {
		return usertype;
	}

	/**
	 * 设置
	 */
	public void setUsertype(Byte usertype) {
		this.usertype = usertype;
	}

	/**
	 * 获取累计返利金额
	 */
	public Double getRebateTotal() {
		return rebateTotal;
	}

	/**
	 * 设置累计返利金额
	 */
	public void setRebateTotal(Double rebateTotal) {
		this.rebateTotal = rebateTotal;
	}

	/**
	 * 获取
	 */
	public Double getSubcommission() {
		return subcommission;
	}

	/**
	 * 设置
	 */
	public void setSubcommission(Double subcommission) {
		this.subcommission = subcommission;
	}

	/**
	 * 获取是否是第三方用户（0否，1是）
	 */
	public Long getThirdPartyUser() {
		return thirdPartyUser;
	}

	/**
	 * 设置是否是第三方用户（0否，1是）
	 */
	public void setThirdPartyUser(Long thirdPartyUser) {
		this.thirdPartyUser = thirdPartyUser;
	}

	/**
	 * 获取推送用户player_id
	 */
	public String getPushuserid() {
		return pushuserid;
	}

	/**
	 * 设置推送用户player_id
	 */
	public void setPushuserid(String pushuserid) {
		this.pushuserid = pushuserid;
	}

	/**
	 * 获取累计积分
	 */
	public Integer getIntegralTotal() {
		return integralTotal;
	}

	/**
	 * 设置累计积分
	 */
	public void setIntegralTotal(Integer integralTotal) {
		this.integralTotal = integralTotal;
	}

	/**
	 * 获取被邀请人ID
	 */
	public Long getInviterUserId() {
		return inviterUserId;
	}

	/**
	 * 设置被邀请人ID
	 */
	public void setInviterUserId(Long inviterUserId) {
		this.inviterUserId = inviterUserId;
	}

	/**
	 * 获取æ¸¸å®¢session id
	 */
	public String getCartSessionId() {
		return cartSessionId;
	}

	/**
	 * 设置æ¸¸å®¢session id
	 */
	public void setCartSessionId(String cartSessionId) {
		this.cartSessionId = cartSessionId;
	}

	/**
	 * 获取鍦板尯
	 */
	public String getAreaInfo() {
		return areaInfo;
	}

	/**
	 * 设置鍦板尯
	 */
	public void setAreaInfo(String areaInfo) {
		this.areaInfo = areaInfo;
	}

	/**
	 * 获取璇︾粏鍦板潃
	 */
	public String getAddressInfo() {
		return addressInfo;
	}

	/**
	 * 设置璇︾粏鍦板潃
	 */
	public void setAddressInfo(String addressInfo) {
		this.addressInfo = addressInfo;
	}

	/**
	 * 获取微信用户编号
	 */
	public String getWxUserId() {
		return wxUserId;
	}

	/**
	 * 设置微信用户编号
	 */
	public void setWxUserId(String wxUserId) {
		this.wxUserId = wxUserId;
	}

	public TjyUser getTjyUser() {
		return tjyUser;
	}

	public void setTjyUser(TjyUser tjyUser) {
		this.tjyUser = tjyUser;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	/**
	 * 获取
	 */
	public Double getJbAmount() {
		return jbAmount;
	}

	/**
	 * 设置
	 */
	public void setJbAmount(Double jbAmount) {
		this.jbAmount = jbAmount;
	}

	/**
	 * 获取
	 */
	public Double getHzbAmount() {
		return hzbAmount;
	}

	/**
	 * 设置
	 */
	public void setHzbAmount(Double hzbAmount) {
		this.hzbAmount = hzbAmount;
	}

	public Integer getIsAppLogin() {
		return isAppLogin;
	}

	public void setIsAppLogin(Integer isAppLogin) {
		this.isAppLogin = isAppLogin;
	}

	public Integer getEmpiricalTotal() {
		return empiricalTotal;
	}

	public void setEmpiricalTotal(Integer empiricalTotal) {
		this.empiricalTotal = empiricalTotal;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getHdticket() {
		return hdticket;
	}

	public void setHdticket(String hdticket) {
		this.hdticket = hdticket;
	}

	/** 互助宝开通标记（0未开通1已开通/已启用2已停用3已过期） */
	public Integer getHzbOpenFlag() {
		return hzbOpenFlag;
	}

	/** 互助宝开通标记（0未开通1已开通/已启用2已停用3已过期） */
	public void setHzbOpenFlag(Integer hzbOpenFlag) {
		this.hzbOpenFlag = hzbOpenFlag;
	}

	public Date getHzbOpenTime() {
		return hzbOpenTime;
	}

	public void setHzbOpenTime(Date hzbOpenTime) {
		this.hzbOpenTime = hzbOpenTime;
	}

	public Integer getHzbLevel() {
		return hzbLevel;
	}

	public void setHzbLevel(Integer hzbLevel) {
		this.hzbLevel = hzbLevel;
	}

	public String getHzbOpenShUserId() {
		return hzbOpenShUserId;
	}

	public void setHzbOpenShUserId(String hzbOpenShUserId) {
		this.hzbOpenShUserId = hzbOpenShUserId;
	}

	public String getShUser() {
		return shUser;
	}

	public void setShUser(String shUser) {
		this.shUser = shUser;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

}
