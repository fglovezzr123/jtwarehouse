package org.com.wing.enterprise.bean;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 
 * //TODO 企服云--读取通讯录统计
 * @author sino
 */
@Table(name = "qfy_phone_address_static")
public class PhoneAdressStatic implements Serializable{
    
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "UUID")
    private String id;
    
    private int fCount;
    
    private int tCount;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getfCount() {
        return fCount;
    }

    public void setfCount(int fCount) {
        this.fCount = fCount;
    }

    public int gettCount() {
        return tCount;
    }

    public void settCount(int tCount) {
        this.tCount = tCount;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }
}
