package org.com.wing.enterprise.bean;

import java.io.Serializable;

import javax.persistence.Table;

/**
 * 
 * //TODO 企服对应的分类
 * @author sino
 */

@Table(name = "qfy_entry_class")
public class EntryClass extends EnterPriseBaseBean implements Serializable{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    /**
     * 分类ID
     */
    private String classId;
    
    /**
     * 分类序号
     */
    private int sortNum;
    
    /**
     * 企服ID
     */
    private String entryId;

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public Integer getSortNum() {
        return sortNum;
    }

    public void setSortNum(Integer sortNum) {
        this.sortNum = sortNum;
    }

    public String getEntryId() {
        return entryId;
    }

    public void setEntryId(String entryId) {
        this.entryId = entryId;
    }
}
