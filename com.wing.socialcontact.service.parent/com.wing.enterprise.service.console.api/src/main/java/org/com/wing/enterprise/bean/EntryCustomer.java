package org.com.wing.enterprise.bean;

import java.io.Serializable;

import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * 
 * //TODO 企服对应客服
 * @author sino
 */
@Table(name = "qfy_entry_customer")
public class EntryCustomer extends EnterPriseBaseBean implements Serializable{
    
    @Transient
    private static final long serialVersionUID = 1L;
    
    /**
     * 企服ID
     */
    private String entryId;
    
    /**
     * 客服电话
     */
    private String customerPhoneNum;
    
    /**
     * 客服名称
     */
    private String customerName;
    
    /**
     * 排序
     */
    private int sortNum;

    public String getEntryId() {
        return entryId;
    }

    public void setEntryId(String entryId) {
        this.entryId = entryId;
    }

    public String getCustomerPhoneNum() {
        return customerPhoneNum;
    }

    public void setCustomerPhoneNum(String customerPhoneNum) {
        this.customerPhoneNum = customerPhoneNum;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public int getSortNum() {
        return sortNum;
    }

    public void setSortNum(int sortNum) {
        this.sortNum = sortNum;
    }
}
