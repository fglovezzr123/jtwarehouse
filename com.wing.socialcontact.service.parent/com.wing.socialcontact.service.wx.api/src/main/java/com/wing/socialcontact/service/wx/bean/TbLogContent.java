package com.wing.socialcontact.service.wx.bean;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * 表操作日志内容
 * 
 * @author wangyansheng
 * @date 2017-11-28
 * @version 1.0
 */
@Table(name = "tjy_tb_log_content")
public class TbLogContent implements Serializable{
	private static final long serialVersionUID = 1L;

    /**主键*/
    @Id
	@Column(name = "id")
	private String id;

    /** log日志 */
	private String logId;

	/**字段英文名*/
    private String tbKey;

	/**旧值*/
	private String oldTbValue;

	/**当前值*/
	private String currentTbValue;

	/**字段中文名*/
	private String comment;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getLogId() {
		return logId;
	}

	public void setLogId(String logId) {
		this.logId = logId;
	}

	public String getTbKey() {
		return tbKey;
	}

	public void setTbKey(String tbKey) {
		this.tbKey = tbKey;
	}

	public String getOldTbValue() {
		return oldTbValue;
	}

	public void setOldTbValue(String oldTbValue) {
		this.oldTbValue = oldTbValue;
	}

	public String getCurrentTbValue() {
		return currentTbValue;
	}

	public void setCurrentTbValue(String currentTbValue) {
		this.currentTbValue = currentTbValue;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}
}
