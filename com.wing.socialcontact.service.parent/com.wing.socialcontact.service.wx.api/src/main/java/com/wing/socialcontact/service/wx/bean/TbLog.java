package com.wing.socialcontact.service.wx.bean;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 表操作日志
 * 
 * @author wangyansheng
 * @date 2017-11-01
 * @version 1.0
 */
@Table(name = "tjy_tb_log")
public class TbLog implements Serializable{
	private static final long serialVersionUID = 1L;

    /**主键*/
    @Id
	@Column(name = "id")
	private String id;

	/**用户id*/
    private String userId;

	/**用户姓名*/
	@Transient
	private String trueName;

	/**操作类型：1新增2修改3删除*/
	private Integer type;

	/**英文表名*/
	private String tableName;

	/**中文表名*/
	private String comment;

	/**创建时间*/
	private Date createTime;

	/**变更内容*/
	@Transient
	private List<TbLogContent> logContentList = new ArrayList<>();

	public List<TbLogContent> getLogContentList() {
		return logContentList;
	}

	public void setLogContentList(List<TbLogContent> logContentList) {
		this.logContentList = logContentList;
	}

	public String getTrueName() {
		return trueName;
	}

	public void setTrueName(String trueName) {
		this.trueName = trueName;
	}

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

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}
