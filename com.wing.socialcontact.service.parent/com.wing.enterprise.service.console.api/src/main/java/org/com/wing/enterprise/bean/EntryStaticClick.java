package org.com.wing.enterprise.bean;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 
 * //TODO 企服点击量
 * @author sino
 */

@Table(name = "qfy_static_click")
public class EntryStaticClick implements Serializable{
    
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "UUID")
    private String id;
    
    /**
     * 企服ID
     */
    private String entryId;
    
    
    /**
     * 用户ID
     */
    private String userId;
    /**
     * 电话访问
     */
    private int phoneCount;
    /**
     * 消息资讯
     */
    private int msgCount;
    
    
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getEntryId() {
        return entryId;
    }
    public void setEntryId(String entryId) {
        this.entryId = entryId;
    }
    public String getUserId() {
        return userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }
    public int getPhoneCount() {
        return phoneCount;
    }
    public void setPhoneCount(int phoneCount) {
        this.phoneCount = phoneCount;
    }
    public int getMsgCount() {
        return msgCount;
    }
    public void setMsgCount(int msgCount) {
        this.msgCount = msgCount;
    }
}
