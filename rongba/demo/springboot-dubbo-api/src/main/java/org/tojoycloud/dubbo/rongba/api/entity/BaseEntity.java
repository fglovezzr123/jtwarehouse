package org.tojoycloud.dubbo.rongba.api.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 公用实体对象
 * @author zhangpengzhi
 */
public class BaseEntity implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2452522346022801138L;

	/**
	 * 业务主键
	 */
	private String id;

	/**
	 * 记录更新时间
	 */   
	private Date updateDate;     
	/**
	 * 记录创建时间
	 */   
	private Date createDate;

	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}   
	
	 /**
     * 删除标记（0：正常；1：删除）
     */
    public static final String DEL_FLAG_NORMAL = "0";
    public static final String DEL_FLAG_DELETE = "1";
	
}
