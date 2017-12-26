package com.wing.socialcontact.service.wx.bean;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * SYSCONFIG
 * 
 * @author zengmin
 * @date 2017-04-17 15:04:05
 * @version 1.0
 */
@Table(name = "SYSCONFIG")
public class Sysconfig implements Serializable {
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
	private String address;

	/**  */
	private Integer bigheight;

	/**  */
	private Integer bigwidth;

	/**  */
	private String closereason;

	/**  */
	private String codestat;

	/**  */
	private Integer complaintTime;

	/**  */
	private Integer consumptionratio;

	/**  */
	private String copyright;

	/**  */
	private String creditrule;

	/**  */
	private Boolean deposit;

	/**  */
	private String description;

	/**  */
	private Boolean emailenable;

	/**  */
	private String emailhost;

	/**  */
	private Integer emailport;

	/**  */
	private String emailpws;

	/**  */
	private String emailtest;

	/**  */
	private String emailuser;

	/**  */
	private String emailusername;

	/**  */
	private Integer everyindentlimit;

	/**  */
	private Boolean gold;

	/**  */
	private Integer goldmarketvalue;

	/**  */
	private Boolean groupbuy;

	/**  */
	private String hotsearch;

	/**  */
	private Integer imagefilesize;

	/**  */
	private String imagesavetype;

	/**  */
	private String imagesuffix;

	/**  */
	private Integer indentcomment;

	/**  */
	private Boolean integral;

	/**  */
	private Integer integralrate;

	/**  */
	private Boolean integralstore;

	/**  */
	private String keywords;

	/**  */
	private Integer memberdaylogin;

	/**  */
	private Integer memberregister;

	/**  */
	private Integer middleheight;

	/**  */
	private Integer middlewidth;

	/**  */
	private Boolean securitycodeconsult;

	/**  */
	private Boolean securitycodelogin;

	/**  */
	private Boolean securitycoderegister;

	/**  */
	private String securitycodetype;

	/**  */
	private String shareCode;

	/**  */
	private Integer smallheight;

	/**  */
	private Integer smallwidth;

	/**  */
	private Boolean smsenbale;

	/**  */
	private String smspassword;

	/**  */
	private String smstest;

	/**  */
	private String smsurl;

	/**  */
	private String smsusername;

	/**  */
	private Boolean storeAllow;

	/**  */
	private String storePayment;

	/**  */
	private String syslanguage;

	/**  */
	private String templates;

	/**  */
	private String title;

	/**  */
	private String uploadfilepath;

	/**  */
	private String userCreditrule;

	/**  */
	private Boolean visitorconsult;

	/**  */
	private Boolean voucher;

	/**  */
	private String websitename;

	/**  */
	private Boolean websitestate;

	/**  */
	private Integer ztcPrice;

	/**  */
	private Boolean ztcStatus;

	/**  */
	private Long goodsimageId;

	/**  */
	private Long membericonId;

	/**  */
	private Long storeimageId;

	/**  */
	private Long websitelogoId;

	/**  */
	private Long applogoId;

	/**  */
	private Integer domainAllowCount;

	/**  */
	private Boolean secondDomainOpen;

	/**  */
	private String sysDomain;

	/**  */
	private Boolean qqLogin;

	/**  */
	private String qqLoginId;

	/**  */
	private String qqLoginKey;

	/**  */
	private String qqDomainCode;

	/**  */
	private String sinaDomainCode;

	/**  */
	private Boolean sinaLogin;

	/**  */
	private String sinaLoginId;

	/**  */
	private String sinaLoginKey;

	/**  */
	private String imagewebserver;

	/**  */
	private Date luceneUpdate;

	/**  */
	private Integer alipayFenrun;

	/**  */
	private Integer balanceFenrun;

	/**  */
	private Integer autoOrderConfirm;

	/**  */
	private Integer autoOrderNotice;

	/**  */
	private Integer bargainMaximum;

	/**  */
	private Double bargainRebate;

	/**  */
	private String bargainState;

	/**  */
	private Integer bargainStatus;

	/**  */
	private String bargainTitle;

	/**  */
	private String serviceQqList;

	/**  */
	private String serviceTelphoneList;

	/**  */
	private Integer sysDeliveryMaximum;

	/**  */
	private Boolean ucBbs;

	/**  */
	private String kuaidiId;

	/**  */
	private String ucApi;

	/**  */
	private String ucAppid;

	/**  */
	private String ucDatabase;

	/**  */
	private String ucDatabasePort;

	/**  */
	private String ucDatabasePws;

	/**  */
	private String ucDatabaseUrl;

	/**  */
	private String ucDatabaseUsername;

	/**  */
	private String ucIp;

	/**  */
	private String ucKey;

	/**  */
	private String ucTablePreffix;

	/**  */
	private String currencyCode;

	/**  */
	private Integer bargainValidity;

	/**  */
	private Integer deliveryAmount;

	/**  */
	private Integer deliveryStatus;

	/**  */
	private String deliveryTitle;

	/**  */
	private String websitecss;

	/**  */
	private Integer combinAmount;

	/**  */
	private Integer combinCount;

	/**  */
	private Integer ztcGoodsView;

	/**  */
	private Integer autoOrderEvaluate;

	/**  */
	private Integer autoOrderReturn;

	/**  */
	private Boolean weixinStore;

	/**  */
	private Integer weixinAmount;

	/**  */
	private Integer configPaymentType;

	/**  */
	private String weixinAccount;

	/** 微信服务号appid */
	private String weixinAppid;

	/** 微信服务号appsecret */
	private String weixinAppsecret;

	/** 微信企业号appid */
	private String weixinAppidQyh;

	/** 微信企业号appsecret */
	private String weixinAppsecretQyh;

	/** 微信企业号appNo */
	private String weixinAppno;

	/**  */
	private String weixinToken;

	/**  */
	private String weixinWelecomeContent;

	/**  */
	private Long storeWeixinLogoId;

	/**  */
	private Long weixinQrImgId;

	/**  */
	private Integer vipIntegral;

	/**  */
	private Double taxRateValue;

	/**  */
	private Date addTaxRateTime;

	/**  */
	private Double oneRates;

	/**  */
	private Double twoRates;

	/**  */
	private Double threeRates;

	/**  */
	private String pointsServants;

	/**  */
	private Boolean returnRates;

	/**  */
	private Boolean centCommission;

	/** 手续费开关 */
	private Byte commissionswitch;

	/** æ‰‹ç»­è´¹ç¨ŽçŽ‡ */
	private Double commissionrate;

	/** ios金币充值的开关 */
	private Boolean iosgoldrechargeswitch;

	/** 积分开始时间 */
	private Date integralBeginTime;

	/** 积分结束时间 */
	private Date integralEndTime;

	/** 积分倍数 */
	private Integer integralMultiple;

	/** 订单取消时间 */
	private Integer autoOrderCancel;

	/** 订单变成已完成时间 */
	private Integer autoOrderReceipttocompleted;

	/**  */
	private Double platformToBusinessFee;

	/**  */
	private Double revpayToPlatformFee;

	/**  */
	private Double revpayToPlatformFeeBank;

	/**  */
	private Double sellerFreight;

	/** 线下支付的开关 */
	private Boolean offlineswitch;

	/**  */
	private Double revpayToPlatformFeeOnline;

	/**  */
	private Double freightRate;

	/** 平台像商家收取税费的开关 */
	private Boolean isStorePayTaxes;

	/** 分佣的税费 */
	private Double commissionTaxes;

	/** 支付手续费税费 */
	private Double payTaxes;

	/** 支付平台向平台收取支付的最低手续费 */
	private Double revpayToPlatformFeeBankLimit;

	/**  */
	private Boolean pushenale;

	/**  */
	private String msgswitch;

	private String webSite;
	
	/** 短信开关 */
	private String message_switch;
	
	public Sysconfig() {
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
	public Integer getBigheight() {
		return bigheight;
	}

	/**
	 * 设置
	 */
	public void setBigheight(Integer bigheight) {
		this.bigheight = bigheight;
	}

	/**
	 * 获取
	 */
	public Integer getBigwidth() {
		return bigwidth;
	}

	/**
	 * 设置
	 */
	public void setBigwidth(Integer bigwidth) {
		this.bigwidth = bigwidth;
	}

	/**
	 * 获取
	 */
	public String getClosereason() {
		return closereason;
	}

	/**
	 * 设置
	 */
	public void setClosereason(String closereason) {
		this.closereason = closereason;
	}

	/**
	 * 获取
	 */
	public String getCodestat() {
		return codestat;
	}

	/**
	 * 设置
	 */
	public void setCodestat(String codestat) {
		this.codestat = codestat;
	}

	/**
	 * 获取
	 */
	public Integer getComplaintTime() {
		return complaintTime;
	}

	/**
	 * 设置
	 */
	public void setComplaintTime(Integer complaintTime) {
		this.complaintTime = complaintTime;
	}

	/**
	 * 获取
	 */
	public Integer getConsumptionratio() {
		return consumptionratio;
	}

	/**
	 * 设置
	 */
	public void setConsumptionratio(Integer consumptionratio) {
		this.consumptionratio = consumptionratio;
	}

	/**
	 * 获取
	 */
	public String getCopyright() {
		return copyright;
	}

	/**
	 * 设置
	 */
	public void setCopyright(String copyright) {
		this.copyright = copyright;
	}

	/**
	 * 获取
	 */
	public String getCreditrule() {
		return creditrule;
	}

	/**
	 * 设置
	 */
	public void setCreditrule(String creditrule) {
		this.creditrule = creditrule;
	}

	/**
	 * 获取
	 */
	public Boolean getDeposit() {
		return deposit;
	}

	/**
	 * 设置
	 */
	public void setDeposit(Boolean deposit) {
		this.deposit = deposit;
	}

	/**
	 * 获取
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * 设置
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * 获取
	 */
	public Boolean getEmailenable() {
		return emailenable;
	}

	/**
	 * 设置
	 */
	public void setEmailenable(Boolean emailenable) {
		this.emailenable = emailenable;
	}

	/**
	 * 获取
	 */
	public String getEmailhost() {
		return emailhost;
	}

	/**
	 * 设置
	 */
	public void setEmailhost(String emailhost) {
		this.emailhost = emailhost;
	}

	/**
	 * 获取
	 */
	public Integer getEmailport() {
		return emailport;
	}

	/**
	 * 设置
	 */
	public void setEmailport(Integer emailport) {
		this.emailport = emailport;
	}

	/**
	 * 获取
	 */
	public String getEmailpws() {
		return emailpws;
	}

	/**
	 * 设置
	 */
	public void setEmailpws(String emailpws) {
		this.emailpws = emailpws;
	}

	/**
	 * 获取
	 */
	public String getEmailtest() {
		return emailtest;
	}

	/**
	 * 设置
	 */
	public void setEmailtest(String emailtest) {
		this.emailtest = emailtest;
	}

	/**
	 * 获取
	 */
	public String getEmailuser() {
		return emailuser;
	}

	/**
	 * 设置
	 */
	public void setEmailuser(String emailuser) {
		this.emailuser = emailuser;
	}

	/**
	 * 获取
	 */
	public String getEmailusername() {
		return emailusername;
	}

	/**
	 * 设置
	 */
	public void setEmailusername(String emailusername) {
		this.emailusername = emailusername;
	}

	/**
	 * 获取
	 */
	public Integer getEveryindentlimit() {
		return everyindentlimit;
	}

	/**
	 * 设置
	 */
	public void setEveryindentlimit(Integer everyindentlimit) {
		this.everyindentlimit = everyindentlimit;
	}

	/**
	 * 获取
	 */
	public Boolean getGold() {
		return gold;
	}

	/**
	 * 设置
	 */
	public void setGold(Boolean gold) {
		this.gold = gold;
	}

	/**
	 * 获取
	 */
	public Integer getGoldmarketvalue() {
		return goldmarketvalue;
	}

	/**
	 * 设置
	 */
	public void setGoldmarketvalue(Integer goldmarketvalue) {
		this.goldmarketvalue = goldmarketvalue;
	}

	/**
	 * 获取
	 */
	public Boolean getGroupbuy() {
		return groupbuy;
	}

	/**
	 * 设置
	 */
	public void setGroupbuy(Boolean groupbuy) {
		this.groupbuy = groupbuy;
	}

	/**
	 * 获取
	 */
	public String getHotsearch() {
		return hotsearch;
	}

	/**
	 * 设置
	 */
	public void setHotsearch(String hotsearch) {
		this.hotsearch = hotsearch;
	}

	/**
	 * 获取
	 */
	public Integer getImagefilesize() {
		return imagefilesize;
	}

	/**
	 * 设置
	 */
	public void setImagefilesize(Integer imagefilesize) {
		this.imagefilesize = imagefilesize;
	}

	/**
	 * 获取
	 */
	public String getImagesavetype() {
		return imagesavetype;
	}

	/**
	 * 设置
	 */
	public void setImagesavetype(String imagesavetype) {
		this.imagesavetype = imagesavetype;
	}

	/**
	 * 获取
	 */
	public String getImagesuffix() {
		return imagesuffix;
	}

	/**
	 * 设置
	 */
	public void setImagesuffix(String imagesuffix) {
		this.imagesuffix = imagesuffix;
	}

	/**
	 * 获取
	 */
	public Integer getIndentcomment() {
		return indentcomment;
	}

	/**
	 * 设置
	 */
	public void setIndentcomment(Integer indentcomment) {
		this.indentcomment = indentcomment;
	}

	/**
	 * 获取
	 */
	public Boolean getIntegral() {
		return integral;
	}

	/**
	 * 设置
	 */
	public void setIntegral(Boolean integral) {
		this.integral = integral;
	}

	/**
	 * 获取
	 */
	public Integer getIntegralrate() {
		return integralrate;
	}

	/**
	 * 设置
	 */
	public void setIntegralrate(Integer integralrate) {
		this.integralrate = integralrate;
	}

	/**
	 * 获取
	 */
	public Boolean getIntegralstore() {
		return integralstore;
	}

	/**
	 * 设置
	 */
	public void setIntegralstore(Boolean integralstore) {
		this.integralstore = integralstore;
	}

	/**
	 * 获取
	 */
	public String getKeywords() {
		return keywords;
	}

	/**
	 * 设置
	 */
	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	/**
	 * 获取
	 */
	public Integer getMemberdaylogin() {
		return memberdaylogin;
	}

	/**
	 * 设置
	 */
	public void setMemberdaylogin(Integer memberdaylogin) {
		this.memberdaylogin = memberdaylogin;
	}

	/**
	 * 获取
	 */
	public Integer getMemberregister() {
		return memberregister;
	}

	/**
	 * 设置
	 */
	public void setMemberregister(Integer memberregister) {
		this.memberregister = memberregister;
	}

	/**
	 * 获取
	 */
	public Integer getMiddleheight() {
		return middleheight;
	}

	/**
	 * 设置
	 */
	public void setMiddleheight(Integer middleheight) {
		this.middleheight = middleheight;
	}

	/**
	 * 获取
	 */
	public Integer getMiddlewidth() {
		return middlewidth;
	}

	/**
	 * 设置
	 */
	public void setMiddlewidth(Integer middlewidth) {
		this.middlewidth = middlewidth;
	}

	/**
	 * 获取
	 */
	public Boolean getSecuritycodeconsult() {
		return securitycodeconsult;
	}

	/**
	 * 设置
	 */
	public void setSecuritycodeconsult(Boolean securitycodeconsult) {
		this.securitycodeconsult = securitycodeconsult;
	}

	/**
	 * 获取
	 */
	public Boolean getSecuritycodelogin() {
		return securitycodelogin;
	}

	/**
	 * 设置
	 */
	public void setSecuritycodelogin(Boolean securitycodelogin) {
		this.securitycodelogin = securitycodelogin;
	}

	/**
	 * 获取
	 */
	public Boolean getSecuritycoderegister() {
		return securitycoderegister;
	}

	/**
	 * 设置
	 */
	public void setSecuritycoderegister(Boolean securitycoderegister) {
		this.securitycoderegister = securitycoderegister;
	}

	/**
	 * 获取
	 */
	public String getSecuritycodetype() {
		return securitycodetype;
	}

	/**
	 * 设置
	 */
	public void setSecuritycodetype(String securitycodetype) {
		this.securitycodetype = securitycodetype;
	}

	/**
	 * 获取
	 */
	public String getShareCode() {
		return shareCode;
	}

	/**
	 * 设置
	 */
	public void setShareCode(String shareCode) {
		this.shareCode = shareCode;
	}

	/**
	 * 获取
	 */
	public Integer getSmallheight() {
		return smallheight;
	}

	/**
	 * 设置
	 */
	public void setSmallheight(Integer smallheight) {
		this.smallheight = smallheight;
	}

	/**
	 * 获取
	 */
	public Integer getSmallwidth() {
		return smallwidth;
	}

	/**
	 * 设置
	 */
	public void setSmallwidth(Integer smallwidth) {
		this.smallwidth = smallwidth;
	}

	/**
	 * 获取
	 */
	public Boolean getSmsenbale() {
		return smsenbale;
	}

	/**
	 * 设置
	 */
	public void setSmsenbale(Boolean smsenbale) {
		this.smsenbale = smsenbale;
	}

	/**
	 * 获取
	 */
	public String getSmspassword() {
		return smspassword;
	}

	/**
	 * 设置
	 */
	public void setSmspassword(String smspassword) {
		this.smspassword = smspassword;
	}

	/**
	 * 获取
	 */
	public String getSmstest() {
		return smstest;
	}

	/**
	 * 设置
	 */
	public void setSmstest(String smstest) {
		this.smstest = smstest;
	}

	/**
	 * 获取
	 */
	public String getSmsurl() {
		return smsurl;
	}

	/**
	 * 设置
	 */
	public void setSmsurl(String smsurl) {
		this.smsurl = smsurl;
	}

	/**
	 * 获取
	 */
	public String getSmsusername() {
		return smsusername;
	}

	/**
	 * 设置
	 */
	public void setSmsusername(String smsusername) {
		this.smsusername = smsusername;
	}

	/**
	 * 获取
	 */
	public Boolean getStoreAllow() {
		return storeAllow;
	}

	/**
	 * 设置
	 */
	public void setStoreAllow(Boolean storeAllow) {
		this.storeAllow = storeAllow;
	}

	/**
	 * 获取
	 */
	public String getStorePayment() {
		return storePayment;
	}

	/**
	 * 设置
	 */
	public void setStorePayment(String storePayment) {
		this.storePayment = storePayment;
	}

	/**
	 * 获取
	 */
	public String getSyslanguage() {
		return syslanguage;
	}

	/**
	 * 设置
	 */
	public void setSyslanguage(String syslanguage) {
		this.syslanguage = syslanguage;
	}

	/**
	 * 获取
	 */
	public String getTemplates() {
		return templates;
	}

	/**
	 * 设置
	 */
	public void setTemplates(String templates) {
		this.templates = templates;
	}

	/**
	 * 获取
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * 设置
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * 获取
	 */
	public String getUploadfilepath() {
		return uploadfilepath;
	}

	/**
	 * 设置
	 */
	public void setUploadfilepath(String uploadfilepath) {
		this.uploadfilepath = uploadfilepath;
	}

	/**
	 * 获取
	 */
	public String getUserCreditrule() {
		return userCreditrule;
	}

	/**
	 * 设置
	 */
	public void setUserCreditrule(String userCreditrule) {
		this.userCreditrule = userCreditrule;
	}

	/**
	 * 获取
	 */
	public Boolean getVisitorconsult() {
		return visitorconsult;
	}

	/**
	 * 设置
	 */
	public void setVisitorconsult(Boolean visitorconsult) {
		this.visitorconsult = visitorconsult;
	}

	/**
	 * 获取
	 */
	public Boolean getVoucher() {
		return voucher;
	}

	/**
	 * 设置
	 */
	public void setVoucher(Boolean voucher) {
		this.voucher = voucher;
	}

	/**
	 * 获取
	 */
	public String getWebsitename() {
		return websitename;
	}

	/**
	 * 设置
	 */
	public void setWebsitename(String websitename) {
		this.websitename = websitename;
	}

	/**
	 * 获取
	 */
	public Boolean getWebsitestate() {
		return websitestate;
	}

	/**
	 * 设置
	 */
	public void setWebsitestate(Boolean websitestate) {
		this.websitestate = websitestate;
	}

	/**
	 * 获取
	 */
	public Integer getZtcPrice() {
		return ztcPrice;
	}

	/**
	 * 设置
	 */
	public void setZtcPrice(Integer ztcPrice) {
		this.ztcPrice = ztcPrice;
	}

	/**
	 * 获取
	 */
	public Boolean getZtcStatus() {
		return ztcStatus;
	}

	/**
	 * 设置
	 */
	public void setZtcStatus(Boolean ztcStatus) {
		this.ztcStatus = ztcStatus;
	}

	/**
	 * 获取
	 */
	public Long getGoodsimageId() {
		return goodsimageId;
	}

	/**
	 * 设置
	 */
	public void setGoodsimageId(Long goodsimageId) {
		this.goodsimageId = goodsimageId;
	}

	/**
	 * 获取
	 */
	public Long getMembericonId() {
		return membericonId;
	}

	/**
	 * 设置
	 */
	public void setMembericonId(Long membericonId) {
		this.membericonId = membericonId;
	}

	/**
	 * 获取
	 */
	public Long getStoreimageId() {
		return storeimageId;
	}

	/**
	 * 设置
	 */
	public void setStoreimageId(Long storeimageId) {
		this.storeimageId = storeimageId;
	}

	/**
	 * 获取
	 */
	public Long getWebsitelogoId() {
		return websitelogoId;
	}

	/**
	 * 设置
	 */
	public void setWebsitelogoId(Long websitelogoId) {
		this.websitelogoId = websitelogoId;
	}

	/**
	 * 获取
	 */
	public Long getApplogoId() {
		return applogoId;
	}

	/**
	 * 设置
	 */
	public void setApplogoId(Long applogoId) {
		this.applogoId = applogoId;
	}

	/**
	 * 获取
	 */
	public Integer getDomainAllowCount() {
		return domainAllowCount;
	}

	/**
	 * 设置
	 */
	public void setDomainAllowCount(Integer domainAllowCount) {
		this.domainAllowCount = domainAllowCount;
	}

	/**
	 * 获取
	 */
	public Boolean getSecondDomainOpen() {
		return secondDomainOpen;
	}

	/**
	 * 设置
	 */
	public void setSecondDomainOpen(Boolean secondDomainOpen) {
		this.secondDomainOpen = secondDomainOpen;
	}

	/**
	 * 获取
	 */
	public String getSysDomain() {
		return sysDomain;
	}

	/**
	 * 设置
	 */
	public void setSysDomain(String sysDomain) {
		this.sysDomain = sysDomain;
	}

	/**
	 * 获取
	 */
	public Boolean getQqLogin() {
		return qqLogin;
	}

	/**
	 * 设置
	 */
	public void setQqLogin(Boolean qqLogin) {
		this.qqLogin = qqLogin;
	}

	/**
	 * 获取
	 */
	public String getQqLoginId() {
		return qqLoginId;
	}

	/**
	 * 设置
	 */
	public void setQqLoginId(String qqLoginId) {
		this.qqLoginId = qqLoginId;
	}

	/**
	 * 获取
	 */
	public String getQqLoginKey() {
		return qqLoginKey;
	}

	/**
	 * 设置
	 */
	public void setQqLoginKey(String qqLoginKey) {
		this.qqLoginKey = qqLoginKey;
	}

	/**
	 * 获取
	 */
	public String getQqDomainCode() {
		return qqDomainCode;
	}

	/**
	 * 设置
	 */
	public void setQqDomainCode(String qqDomainCode) {
		this.qqDomainCode = qqDomainCode;
	}

	/**
	 * 获取
	 */
	public String getSinaDomainCode() {
		return sinaDomainCode;
	}

	/**
	 * 设置
	 */
	public void setSinaDomainCode(String sinaDomainCode) {
		this.sinaDomainCode = sinaDomainCode;
	}

	/**
	 * 获取
	 */
	public Boolean getSinaLogin() {
		return sinaLogin;
	}

	/**
	 * 设置
	 */
	public void setSinaLogin(Boolean sinaLogin) {
		this.sinaLogin = sinaLogin;
	}

	/**
	 * 获取
	 */
	public String getSinaLoginId() {
		return sinaLoginId;
	}

	/**
	 * 设置
	 */
	public void setSinaLoginId(String sinaLoginId) {
		this.sinaLoginId = sinaLoginId;
	}

	/**
	 * 获取
	 */
	public String getSinaLoginKey() {
		return sinaLoginKey;
	}

	/**
	 * 设置
	 */
	public void setSinaLoginKey(String sinaLoginKey) {
		this.sinaLoginKey = sinaLoginKey;
	}

	/**
	 * 获取
	 */
	public String getImagewebserver() {
		return imagewebserver;
	}

	/**
	 * 设置
	 */
	public void setImagewebserver(String imagewebserver) {
		this.imagewebserver = imagewebserver;
	}

	/**
	 * 获取
	 */
	public Date getLuceneUpdate() {
		return luceneUpdate;
	}

	/**
	 * 设置
	 */
	public void setLuceneUpdate(Date luceneUpdate) {
		this.luceneUpdate = luceneUpdate;
	}

	/**
	 * 获取
	 */
	public Integer getAlipayFenrun() {
		return alipayFenrun;
	}

	/**
	 * 设置
	 */
	public void setAlipayFenrun(Integer alipayFenrun) {
		this.alipayFenrun = alipayFenrun;
	}

	/**
	 * 获取
	 */
	public Integer getBalanceFenrun() {
		return balanceFenrun;
	}

	/**
	 * 设置
	 */
	public void setBalanceFenrun(Integer balanceFenrun) {
		this.balanceFenrun = balanceFenrun;
	}

	/**
	 * 获取
	 */
	public Integer getAutoOrderConfirm() {
		return autoOrderConfirm;
	}

	/**
	 * 设置
	 */
	public void setAutoOrderConfirm(Integer autoOrderConfirm) {
		this.autoOrderConfirm = autoOrderConfirm;
	}

	/**
	 * 获取
	 */
	public Integer getAutoOrderNotice() {
		return autoOrderNotice;
	}

	/**
	 * 设置
	 */
	public void setAutoOrderNotice(Integer autoOrderNotice) {
		this.autoOrderNotice = autoOrderNotice;
	}

	/**
	 * 获取
	 */
	public Integer getBargainMaximum() {
		return bargainMaximum;
	}

	/**
	 * 设置
	 */
	public void setBargainMaximum(Integer bargainMaximum) {
		this.bargainMaximum = bargainMaximum;
	}

	/**
	 * 获取
	 */
	public Double getBargainRebate() {
		return bargainRebate;
	}

	/**
	 * 设置
	 */
	public void setBargainRebate(Double bargainRebate) {
		this.bargainRebate = bargainRebate;
	}

	/**
	 * 获取
	 */
	public String getBargainState() {
		return bargainState;
	}

	/**
	 * 设置
	 */
	public void setBargainState(String bargainState) {
		this.bargainState = bargainState;
	}

	/**
	 * 获取
	 */
	public Integer getBargainStatus() {
		return bargainStatus;
	}

	/**
	 * 设置
	 */
	public void setBargainStatus(Integer bargainStatus) {
		this.bargainStatus = bargainStatus;
	}

	/**
	 * 获取
	 */
	public String getBargainTitle() {
		return bargainTitle;
	}

	/**
	 * 设置
	 */
	public void setBargainTitle(String bargainTitle) {
		this.bargainTitle = bargainTitle;
	}

	/**
	 * 获取
	 */
	public String getServiceQqList() {
		return serviceQqList;
	}

	/**
	 * 设置
	 */
	public void setServiceQqList(String serviceQqList) {
		this.serviceQqList = serviceQqList;
	}

	/**
	 * 获取
	 */
	public String getServiceTelphoneList() {
		return serviceTelphoneList;
	}

	/**
	 * 设置
	 */
	public void setServiceTelphoneList(String serviceTelphoneList) {
		this.serviceTelphoneList = serviceTelphoneList;
	}

	/**
	 * 获取
	 */
	public Integer getSysDeliveryMaximum() {
		return sysDeliveryMaximum;
	}

	/**
	 * 设置
	 */
	public void setSysDeliveryMaximum(Integer sysDeliveryMaximum) {
		this.sysDeliveryMaximum = sysDeliveryMaximum;
	}

	/**
	 * 获取
	 */
	public Boolean getUcBbs() {
		return ucBbs;
	}

	/**
	 * 设置
	 */
	public void setUcBbs(Boolean ucBbs) {
		this.ucBbs = ucBbs;
	}

	/**
	 * 获取
	 */
	public String getKuaidiId() {
		return kuaidiId;
	}

	/**
	 * 设置
	 */
	public void setKuaidiId(String kuaidiId) {
		this.kuaidiId = kuaidiId;
	}

	/**
	 * 获取
	 */
	public String getUcApi() {
		return ucApi;
	}

	/**
	 * 设置
	 */
	public void setUcApi(String ucApi) {
		this.ucApi = ucApi;
	}

	/**
	 * 获取
	 */
	public String getUcAppid() {
		return ucAppid;
	}

	/**
	 * 设置
	 */
	public void setUcAppid(String ucAppid) {
		this.ucAppid = ucAppid;
	}

	/**
	 * 获取
	 */
	public String getUcDatabase() {
		return ucDatabase;
	}

	/**
	 * 设置
	 */
	public void setUcDatabase(String ucDatabase) {
		this.ucDatabase = ucDatabase;
	}

	/**
	 * 获取
	 */
	public String getUcDatabasePort() {
		return ucDatabasePort;
	}

	/**
	 * 设置
	 */
	public void setUcDatabasePort(String ucDatabasePort) {
		this.ucDatabasePort = ucDatabasePort;
	}

	/**
	 * 获取
	 */
	public String getUcDatabasePws() {
		return ucDatabasePws;
	}

	/**
	 * 设置
	 */
	public void setUcDatabasePws(String ucDatabasePws) {
		this.ucDatabasePws = ucDatabasePws;
	}

	/**
	 * 获取
	 */
	public String getUcDatabaseUrl() {
		return ucDatabaseUrl;
	}

	/**
	 * 设置
	 */
	public void setUcDatabaseUrl(String ucDatabaseUrl) {
		this.ucDatabaseUrl = ucDatabaseUrl;
	}

	/**
	 * 获取
	 */
	public String getUcDatabaseUsername() {
		return ucDatabaseUsername;
	}

	/**
	 * 设置
	 */
	public void setUcDatabaseUsername(String ucDatabaseUsername) {
		this.ucDatabaseUsername = ucDatabaseUsername;
	}

	/**
	 * 获取
	 */
	public String getUcIp() {
		return ucIp;
	}

	/**
	 * 设置
	 */
	public void setUcIp(String ucIp) {
		this.ucIp = ucIp;
	}

	/**
	 * 获取
	 */
	public String getUcKey() {
		return ucKey;
	}

	/**
	 * 设置
	 */
	public void setUcKey(String ucKey) {
		this.ucKey = ucKey;
	}

	/**
	 * 获取
	 */
	public String getUcTablePreffix() {
		return ucTablePreffix;
	}

	/**
	 * 设置
	 */
	public void setUcTablePreffix(String ucTablePreffix) {
		this.ucTablePreffix = ucTablePreffix;
	}

	/**
	 * 获取
	 */
	public String getCurrencyCode() {
		return currencyCode;
	}

	/**
	 * 设置
	 */
	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	/**
	 * 获取
	 */
	public Integer getBargainValidity() {
		return bargainValidity;
	}

	/**
	 * 设置
	 */
	public void setBargainValidity(Integer bargainValidity) {
		this.bargainValidity = bargainValidity;
	}

	/**
	 * 获取
	 */
	public Integer getDeliveryAmount() {
		return deliveryAmount;
	}

	/**
	 * 设置
	 */
	public void setDeliveryAmount(Integer deliveryAmount) {
		this.deliveryAmount = deliveryAmount;
	}

	/**
	 * 获取
	 */
	public Integer getDeliveryStatus() {
		return deliveryStatus;
	}

	/**
	 * 设置
	 */
	public void setDeliveryStatus(Integer deliveryStatus) {
		this.deliveryStatus = deliveryStatus;
	}

	/**
	 * 获取
	 */
	public String getDeliveryTitle() {
		return deliveryTitle;
	}

	/**
	 * 设置
	 */
	public void setDeliveryTitle(String deliveryTitle) {
		this.deliveryTitle = deliveryTitle;
	}

	/**
	 * 获取
	 */
	public String getWebsitecss() {
		return websitecss;
	}

	/**
	 * 设置
	 */
	public void setWebsitecss(String websitecss) {
		this.websitecss = websitecss;
	}

	/**
	 * 获取
	 */
	public Integer getCombinAmount() {
		return combinAmount;
	}

	/**
	 * 设置
	 */
	public void setCombinAmount(Integer combinAmount) {
		this.combinAmount = combinAmount;
	}

	/**
	 * 获取
	 */
	public Integer getCombinCount() {
		return combinCount;
	}

	/**
	 * 设置
	 */
	public void setCombinCount(Integer combinCount) {
		this.combinCount = combinCount;
	}

	/**
	 * 获取
	 */
	public Integer getZtcGoodsView() {
		return ztcGoodsView;
	}

	/**
	 * 设置
	 */
	public void setZtcGoodsView(Integer ztcGoodsView) {
		this.ztcGoodsView = ztcGoodsView;
	}

	/**
	 * 获取
	 */
	public Integer getAutoOrderEvaluate() {
		return autoOrderEvaluate;
	}

	/**
	 * 设置
	 */
	public void setAutoOrderEvaluate(Integer autoOrderEvaluate) {
		this.autoOrderEvaluate = autoOrderEvaluate;
	}

	/**
	 * 获取
	 */
	public Integer getAutoOrderReturn() {
		return autoOrderReturn;
	}

	/**
	 * 设置
	 */
	public void setAutoOrderReturn(Integer autoOrderReturn) {
		this.autoOrderReturn = autoOrderReturn;
	}

	/**
	 * 获取
	 */
	public Boolean getWeixinStore() {
		return weixinStore;
	}

	/**
	 * 设置
	 */
	public void setWeixinStore(Boolean weixinStore) {
		this.weixinStore = weixinStore;
	}

	/**
	 * 获取appno
	 */
	public Integer getWeixinAmount() {
		return weixinAmount;
	}

	/**
	 * 设置
	 */
	public void setWeixinAmount(Integer weixinAmount) {
		this.weixinAmount = weixinAmount;
	}

	/**
	 * 获取
	 */
	public Integer getConfigPaymentType() {
		return configPaymentType;
	}

	/**
	 * 设置
	 */
	public void setConfigPaymentType(Integer configPaymentType) {
		this.configPaymentType = configPaymentType;
	}

	/**
	 * 获取
	 */
	public String getWeixinAccount() {
		return weixinAccount;
	}

	/**
	 * 设置
	 */
	public void setWeixinAccount(String weixinAccount) {
		this.weixinAccount = weixinAccount;
	}

	/**
	 * 获取微信服务号appid
	 */
	public String getWeixinAppid() {
		return weixinAppid;
	}

	/**
	 * 设置
	 */
	public void setWeixinAppid(String weixinAppid) {
		this.weixinAppid = weixinAppid;
	}

	/**
	 * 获取获取微信服务号appsecret
	 */
	public String getWeixinAppsecret() {
		return weixinAppsecret;
	}

	/**
	 * 设置
	 */
	public void setWeixinAppsecret(String weixinAppsecret) {
		this.weixinAppsecret = weixinAppsecret;
	}

	/**
	 * 获取
	 */
	public String getWeixinToken() {
		return weixinToken;
	}

	/**
	 * 设置
	 */
	public void setWeixinToken(String weixinToken) {
		this.weixinToken = weixinToken;
	}

	/**
	 * 获取
	 */
	public String getWeixinWelecomeContent() {
		return weixinWelecomeContent;
	}

	/**
	 * 设置
	 */
	public void setWeixinWelecomeContent(String weixinWelecomeContent) {
		this.weixinWelecomeContent = weixinWelecomeContent;
	}

	/**
	 * 获取
	 */
	public Long getStoreWeixinLogoId() {
		return storeWeixinLogoId;
	}

	/**
	 * 设置
	 */
	public void setStoreWeixinLogoId(Long storeWeixinLogoId) {
		this.storeWeixinLogoId = storeWeixinLogoId;
	}

	/**
	 * 获取
	 */
	public Long getWeixinQrImgId() {
		return weixinQrImgId;
	}

	/**
	 * 设置
	 */
	public void setWeixinQrImgId(Long weixinQrImgId) {
		this.weixinQrImgId = weixinQrImgId;
	}

	/**
	 * 获取
	 */
	public Integer getVipIntegral() {
		return vipIntegral;
	}

	/**
	 * 设置
	 */
	public void setVipIntegral(Integer vipIntegral) {
		this.vipIntegral = vipIntegral;
	}

	/**
	 * 获取
	 */
	public Double getTaxRateValue() {
		return taxRateValue;
	}

	/**
	 * 设置
	 */
	public void setTaxRateValue(Double taxRateValue) {
		this.taxRateValue = taxRateValue;
	}

	/**
	 * 获取
	 */
	public Date getAddTaxRateTime() {
		return addTaxRateTime;
	}

	/**
	 * 设置
	 */
	public void setAddTaxRateTime(Date addTaxRateTime) {
		this.addTaxRateTime = addTaxRateTime;
	}

	/**
	 * 获取
	 */
	public Double getOneRates() {
		return oneRates;
	}

	/**
	 * 设置
	 */
	public void setOneRates(Double oneRates) {
		this.oneRates = oneRates;
	}

	/**
	 * 获取
	 */
	public Double getTwoRates() {
		return twoRates;
	}

	/**
	 * 设置
	 */
	public void setTwoRates(Double twoRates) {
		this.twoRates = twoRates;
	}

	/**
	 * 获取
	 */
	public Double getThreeRates() {
		return threeRates;
	}

	/**
	 * 设置
	 */
	public void setThreeRates(Double threeRates) {
		this.threeRates = threeRates;
	}

	/**
	 * 获取
	 */
	public String getPointsServants() {
		return pointsServants;
	}

	/**
	 * 设置
	 */
	public void setPointsServants(String pointsServants) {
		this.pointsServants = pointsServants;
	}

	/**
	 * 获取
	 */
	public Boolean getReturnRates() {
		return returnRates;
	}

	/**
	 * 设置
	 */
	public void setReturnRates(Boolean returnRates) {
		this.returnRates = returnRates;
	}

	/**
	 * 获取
	 */
	public Boolean getCentCommission() {
		return centCommission;
	}

	/**
	 * 设置
	 */
	public void setCentCommission(Boolean centCommission) {
		this.centCommission = centCommission;
	}

	/**
	 * 获取手续费开关
	 */
	public Byte getCommissionswitch() {
		return commissionswitch;
	}

	/**
	 * 设置手续费开关
	 */
	public void setCommissionswitch(Byte commissionswitch) {
		this.commissionswitch = commissionswitch;
	}

	/**
	 * 获取æ‰‹ç»­è´¹ç¨ŽçŽ‡
	 */
	public Double getCommissionrate() {
		return commissionrate;
	}

	/**
	 * 设置æ‰‹ç»­è´¹ç¨ŽçŽ‡
	 */
	public void setCommissionrate(Double commissionrate) {
		this.commissionrate = commissionrate;
	}

	/**
	 * 获取ios金币充值的开关
	 */
	public Boolean getIosgoldrechargeswitch() {
		return iosgoldrechargeswitch;
	}

	/**
	 * 设置ios金币充值的开关
	 */
	public void setIosgoldrechargeswitch(Boolean iosgoldrechargeswitch) {
		this.iosgoldrechargeswitch = iosgoldrechargeswitch;
	}

	/**
	 * 获取积分开始时间
	 */
	public Date getIntegralBeginTime() {
		return integralBeginTime;
	}

	/**
	 * 设置积分开始时间
	 */
	public void setIntegralBeginTime(Date integralBeginTime) {
		this.integralBeginTime = integralBeginTime;
	}

	/**
	 * 获取积分结束时间
	 */
	public Date getIntegralEndTime() {
		return integralEndTime;
	}

	/**
	 * 设置积分结束时间
	 */
	public void setIntegralEndTime(Date integralEndTime) {
		this.integralEndTime = integralEndTime;
	}

	/**
	 * 获取积分倍数
	 */
	public Integer getIntegralMultiple() {
		return integralMultiple;
	}

	/**
	 * 设置积分倍数
	 */
	public void setIntegralMultiple(Integer integralMultiple) {
		this.integralMultiple = integralMultiple;
	}

	/**
	 * 获取订单取消时间
	 */
	public Integer getAutoOrderCancel() {
		return autoOrderCancel;
	}

	/**
	 * 设置订单取消时间
	 */
	public void setAutoOrderCancel(Integer autoOrderCancel) {
		this.autoOrderCancel = autoOrderCancel;
	}

	/**
	 * 获取订单变成已完成时间
	 */
	public Integer getAutoOrderReceipttocompleted() {
		return autoOrderReceipttocompleted;
	}

	/**
	 * 设置订单变成已完成时间
	 */
	public void setAutoOrderReceipttocompleted(Integer autoOrderReceipttocompleted) {
		this.autoOrderReceipttocompleted = autoOrderReceipttocompleted;
	}

	/**
	 * 获取
	 */
	public Double getPlatformToBusinessFee() {
		return platformToBusinessFee;
	}

	/**
	 * 设置
	 */
	public void setPlatformToBusinessFee(Double platformToBusinessFee) {
		this.platformToBusinessFee = platformToBusinessFee;
	}

	/**
	 * 获取
	 */
	public Double getRevpayToPlatformFee() {
		return revpayToPlatformFee;
	}

	/**
	 * 设置
	 */
	public void setRevpayToPlatformFee(Double revpayToPlatformFee) {
		this.revpayToPlatformFee = revpayToPlatformFee;
	}

	/**
	 * 获取
	 */
	public Double getRevpayToPlatformFeeBank() {
		return revpayToPlatformFeeBank;
	}

	/**
	 * 设置
	 */
	public void setRevpayToPlatformFeeBank(Double revpayToPlatformFeeBank) {
		this.revpayToPlatformFeeBank = revpayToPlatformFeeBank;
	}

	/**
	 * 获取
	 */
	public Double getSellerFreight() {
		return sellerFreight;
	}

	/**
	 * 设置
	 */
	public void setSellerFreight(Double sellerFreight) {
		this.sellerFreight = sellerFreight;
	}

	/**
	 * 获取线下支付的开关
	 */
	public Boolean getOfflineswitch() {
		return offlineswitch;
	}

	/**
	 * 设置线下支付的开关
	 */
	public void setOfflineswitch(Boolean offlineswitch) {
		this.offlineswitch = offlineswitch;
	}

	/**
	 * 获取
	 */
	public Double getRevpayToPlatformFeeOnline() {
		return revpayToPlatformFeeOnline;
	}

	/**
	 * 设置
	 */
	public void setRevpayToPlatformFeeOnline(Double revpayToPlatformFeeOnline) {
		this.revpayToPlatformFeeOnline = revpayToPlatformFeeOnline;
	}

	/**
	 * 获取
	 */
	public Double getFreightRate() {
		return freightRate;
	}

	/**
	 * 设置
	 */
	public void setFreightRate(Double freightRate) {
		this.freightRate = freightRate;
	}

	/**
	 * 获取平台像商家收取税费的开关
	 */
	public Boolean getIsStorePayTaxes() {
		return isStorePayTaxes;
	}

	/**
	 * 设置平台像商家收取税费的开关
	 */
	public void setIsStorePayTaxes(Boolean isStorePayTaxes) {
		this.isStorePayTaxes = isStorePayTaxes;
	}

	/**
	 * 获取分佣的税费
	 */
	public Double getCommissionTaxes() {
		return commissionTaxes;
	}

	/**
	 * 设置分佣的税费
	 */
	public void setCommissionTaxes(Double commissionTaxes) {
		this.commissionTaxes = commissionTaxes;
	}

	/**
	 * 获取支付手续费税费
	 */
	public Double getPayTaxes() {
		return payTaxes;
	}

	/**
	 * 设置支付手续费税费
	 */
	public void setPayTaxes(Double payTaxes) {
		this.payTaxes = payTaxes;
	}

	/**
	 * 获取支付平台向平台收取支付的最低手续费
	 */
	public Double getRevpayToPlatformFeeBankLimit() {
		return revpayToPlatformFeeBankLimit;
	}

	/**
	 * 设置支付平台向平台收取支付的最低手续费
	 */
	public void setRevpayToPlatformFeeBankLimit(Double revpayToPlatformFeeBankLimit) {
		this.revpayToPlatformFeeBankLimit = revpayToPlatformFeeBankLimit;
	}

	/**
	 * 获取
	 */
	public Boolean getPushenale() {
		return pushenale;
	}

	/**
	 * 设置
	 */
	public void setPushenale(Boolean pushenale) {
		this.pushenale = pushenale;
	}

	/**
	 * 获取
	 */
	public String getMsgswitch() {
		return msgswitch;
	}

	/**
	 * 设置
	 */
	public void setMsgswitch(String msgswitch) {
		this.msgswitch = msgswitch;
	}

	public String getWebSite() {
		return webSite;
	}

	public void setWebSite(String webSite) {
		this.webSite = webSite;
	}

	/** 获取微信企业号appid */
	public String getWeixinAppidQyh() {
		return weixinAppidQyh;
	}

	public void setWeixinAppidQyh(String weixinAppidQyh) {
		this.weixinAppidQyh = weixinAppidQyh;
	}

	/** 获取微信企业号appsecret */
	public String getWeixinAppsecretQyh() {
		return weixinAppsecretQyh;
	}

	public void setWeixinAppsecretQyh(String weixinAppsecretQyh) {
		this.weixinAppsecretQyh = weixinAppsecretQyh;
	}

	/** 获取微信企业号appno */
	public String getWeixinAppno() {
		return weixinAppno;
	}

	public void setWeixinAppno(String weixinAppno) {
		this.weixinAppno = weixinAppno;
	}


	public String getMessage_switch()
	{
		return message_switch;
	}


	public void setMessage_switch(String message_switch)
	{
		this.message_switch = message_switch;
	}

}
