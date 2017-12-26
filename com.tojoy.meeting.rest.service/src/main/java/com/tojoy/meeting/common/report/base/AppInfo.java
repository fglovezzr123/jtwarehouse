package com.tojoy.meeting.common.report.base;

public class AppInfo
{
	// 产品ID
	private String productId;

	// 产品版本号
	private String versionCode;

	// 访问渠道
	private String visitDevice;

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getVersionCode() {
		return versionCode;
	}

	public void setVersionCode(String versionCode) {
		this.versionCode = versionCode;
	}

	public String getVisitDevice() {
		return visitDevice;
	}

	public void setVisitDevice(String visitDevice) {
		this.visitDevice = visitDevice;
	}
}
