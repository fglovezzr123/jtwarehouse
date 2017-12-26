package org.com.wing.enterprise.bean;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 
 * //TODO 企服云--读取通讯录权限开关
 * @author sino
 */
@Table(name = "qfy_config")
public class QfyConfig implements Serializable{
    
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "UUID")
    private String id;
    
    private int cStatus;

    public int getcStatus() {
        return cStatus;
    }

    public void setcStatus(int cStatus) {
        this.cStatus = cStatus;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
