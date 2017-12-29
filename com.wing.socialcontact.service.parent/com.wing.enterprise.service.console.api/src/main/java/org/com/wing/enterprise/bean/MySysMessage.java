package org.com.wing.enterprise.bean;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 我的系统消息
 * 
 * 
 * @ClassName: MySysMessage
 * @Description: TODO
 * @author: sino
 * @date:2017年5月12日
 */
@Table(name = "qfy_m_sys_message")
public class MySysMessage implements Serializable{
    
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
     * 信息归属用户id
     */
    private String ssUserId;
    
    /**
     * 消息内容
     */
    private String content;
    /**
     * 消息状态 0：未读，1：已读
     */
    private int status;
    
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
    public String getSsUserId() {
        return ssUserId;
    }
    public void setSsUserId(String ssUserId) {
        this.ssUserId = ssUserId;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public int getStatus() {
        return status;
    }
    public void setStatus(int status) {
        this.status = status;
    }
    
    
}
