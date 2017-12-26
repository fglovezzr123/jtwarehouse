package org.com.wing.enterprise.bean;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;

/**
 * 
 * //TODO 实体标准字段
 * @author sino
 */
public class EnterPriseBaseBean implements Serializable{
    
    @Transient
    private static final long serialVersionUID = 1L;
    
    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "UUID")
    private String id;
    
    /** 创建时间 */
    private Date createTime; 
    
    /**
     * 创建人ID
     */
    private String createUserId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(String createUserId) {
        this.createUserId = createUserId;
    }
    
    
}
