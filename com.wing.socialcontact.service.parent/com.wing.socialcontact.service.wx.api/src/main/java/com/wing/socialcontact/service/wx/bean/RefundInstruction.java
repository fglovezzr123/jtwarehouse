package com.wing.socialcontact.service.wx.bean;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
/**
 * 
 * <p>Title:退款说明管理 </p>
 * <p>Description: </p>
 * <p>Company: </p> 
 * @author zhangzheng
 * @Date  2017年5月12日 下午3:01:19
 */
/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Company: </p> 
 * @author zhangzheng
 * @Date  2017年5月12日 下午3:07:59
 */
@Table(name="tjy_refund_instruction")
public class RefundInstruction implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/** 主键 */
    @Id
	@Column(name = "id")
	private Integer id;
	/**
	 * 退款说明
	 */
	private String content;
	/**
	 * 备注
	 */
	private String remark;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	

}
