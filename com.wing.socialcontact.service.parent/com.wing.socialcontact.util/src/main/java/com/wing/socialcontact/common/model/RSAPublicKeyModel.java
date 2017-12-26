/**  
 * @Title: RSAPublicKeyModel.java
 * @date 2016-10-12 下午2:09:22
 * @Copyright: 2016 
 */
package com.wing.socialcontact.common.model;


/**
 * RSA 公钥 
 * @author	dijuli
 * @version	 1.0
 *
 */
public class RSAPublicKeyModel {
	/**
	 * Hex.encode 编码之后的公钥系数
	 */
	private String hexModulus;
	/**
	 * Hex.encode 编码之后的 公钥指数
	 */
	private String hexPublicExponent;

	public String getHexModulus() {
		return hexModulus;
	}

	public void setHexModulus(String hexModulus) {
		this.hexModulus = hexModulus;
	}

	public String getHexPublicExponent() {
		return hexPublicExponent;
	}

	public void setHexPublicExponent(String hexPublicExponent) {
		this.hexPublicExponent = hexPublicExponent;
	}
	
}
