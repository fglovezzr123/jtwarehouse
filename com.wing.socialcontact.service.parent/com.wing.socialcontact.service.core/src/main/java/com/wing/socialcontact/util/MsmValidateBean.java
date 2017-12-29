package com.wing.socialcontact.util;

import java.io.Serializable;
import java.util.Date;

import javax.servlet.http.HttpSession;

public class MsmValidateBean implements Serializable {
	private static final long serialVersionUID = 1L;
	private String mobile;
	private Date sendDate;
	private String code;

	public MsmValidateBean(String mobile, Date sendDate, String code) {
		super();
		this.mobile = mobile;
		this.sendDate = sendDate;
		this.code = code;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Date getSendDate() {
		return sendDate;
	}

	public void setSendDate(Date sendDate) {
		this.sendDate = sendDate;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * 验证验证码
	 * 
	 * @param mobile
	 * @param vcode
	 * @param request
	 * @return
	 */
	public static boolean validateCode(String mobile, String vcode, HttpSession session) {
		//System.out.println("sessionid----validateCode" + session.getId());
		MsmValidateBean mvb = (MsmValidateBean) session.getAttribute("mvb");
		if (null == mvb) {
			//System.out.println("---mvb is null---");
			return false;
		}
		if (!mobile.equals(mvb.getMobile())) {
			//System.out.println(mobile + "---手机号不匹配---" + mvb.getMobile());
			return false;
		}
		if (!vcode.equals(mvb.getCode())) {
			//System.out.println(vcode + "---验证码不匹配---" + mvb.getCode());
			return false;
		}
		if (((new Date().getTime() - mvb.getSendDate().getTime()) / 1000 > Constants.REG_VALIDATE_CODE_TIMEOUT)) {
			//System.out.println(new Date().getTime() + "---时间失败---" + mvb.getSendDate().getTime());
			return false;
		}
		return true;
	}

}
