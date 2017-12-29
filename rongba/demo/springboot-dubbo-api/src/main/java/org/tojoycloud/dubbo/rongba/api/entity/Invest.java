/**
 * 
 */
package org.tojoycloud.dubbo.rongba.api.entity;

/**
 * @author zhangpengzhi
 *
 */
public class Invest extends BaseEntity {

	private static final long serialVersionUID = 8530750059694607573L;

	private String userName;

	private String password;

	private String remark;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}
