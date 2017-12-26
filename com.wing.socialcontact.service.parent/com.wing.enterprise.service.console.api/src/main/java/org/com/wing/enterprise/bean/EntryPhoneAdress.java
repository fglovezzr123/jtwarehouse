package org.com.wing.enterprise.bean;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 
 * //TODO 企服用户上传通讯录
 * @author sino
 */

@Table(name = "qfy_phone_address")
public class EntryPhoneAdress implements Serializable{
    
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    @Id
    @Column(name = "id")
    //@GeneratedValue(generator = "UUID")
    private String id;
    
    /** 创建时间 */
    private Date createTime; 
    
    /**
     * 创建人ID
     */
    private String createUserId;
    
    
    /**
     * 所属人ID
     */
    private String ssUserId;
    /**
     * 所属人名称
     */
    private String ssUserName;
    /**
     * 所属人电话
     */
    private String ssUserPhone;
    /**
     * 通讯录名称
     */
    private String userName;
    /**
     * 通讯录电话
     */
    private String userPhone;
    
    public String getSsUserId() {
        return ssUserId;
    }
    public void setSsUserId(String ssUserId) {
        this.ssUserId = ssUserId;
    }
    public String getSsUserName() {
        return ssUserName;
    }
    public void setSsUserName(String ssUserName) {
        this.ssUserName = ssUserName;
    }
    public String getSsUserPhone() {
        return ssUserPhone;
    }
    public void setSsUserPhone(String ssUserPhone) {
        this.ssUserPhone = ssUserPhone;
    }
    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getUserPhone() {
        return userPhone;
    }
    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }
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
