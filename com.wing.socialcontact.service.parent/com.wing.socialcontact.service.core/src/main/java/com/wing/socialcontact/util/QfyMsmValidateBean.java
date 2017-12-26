package com.wing.socialcontact.util;

import java.util.Date;

import javax.servlet.http.HttpSession;

/**
 * 企服云短信验证
 * 
 * 
 * @ClassName: QfyMsmValidateBean
 * @Description: TODO
 * @author: sino
 * @date:2017年4月25日
 */
public class QfyMsmValidateBean {
	private String mobile;
	private Date sendDate;
	private String code;

	public QfyMsmValidateBean(String mobile, Date sendDate, String code) {
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
		QfyMsmValidateBean mvb = (QfyMsmValidateBean) session.getAttribute("qfymvb");
		if (null == mvb) {
			return false;
		}
		if (!mobile.equals(mvb.getMobile())) {
			return false;
		}
		if (!vcode.equals(mvb.getCode())) {
			return false;
		}
		if (((new Date().getTime() - mvb.getSendDate().getTime()) / 1000 > Constants.REG_VALIDATE_CODE_TIMEOUT)) {
			return false;
		}
		return true;
	}

}
