package com.wing.socialcontact.service.wx.bean;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
@Table(name = "tjy_app_invite_log")
public class AppInviteLog implements Serializable {
		/** 主键 */
	    @Id
		@Column(name = "id")
	    @GeneratedValue(generator = "UUID")
	    private String id;
	    /**
	     * 邀请人id
	     */
	    private String userId;
		/**
		 * 被邀请人手机号
		 */
	    private String mobile;
	    /**
	     * 邀请内容
	     */
	    private String content;
	    
	    /** 创建时间 */
	  	private Date createTime;

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public String getUserId() {
			return userId;
		}

		public void setUserId(String userId) {
			this.userId = userId;
		}

		public String getMobile() {
			return mobile;
		}

		public void setMobile(String mobile) {
			this.mobile = mobile;
		}

		public Date getCreateTime() {
			return createTime;
		}

		public void setCreateTime(Date createTime) {
			this.createTime = createTime;
		}

		public String getContent() {
			return content;
		}

		public void setContent(String content) {
			this.content = content;
		}
	  	
	  	
	  	
}
