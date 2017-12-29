package org.com.wing.enterprise.bean;

import java.io.Serializable;

import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * 
 * //TODO 快捷入口详情分类
 * @author sino
 */

@Table(name = "qfy_quick_detail_class")
public class QuickDetailClass extends EnterPriseBaseBean implements Serializable{

    @Transient
    private static final long serialVersionUID = 1L;
    
    /**
     * 服务分类ID
     */
    private String classId;
    
    /**
     * 快捷入口ID
     */
    private String quickDoorId;
    
    /**
     * 序号
     */
    private int sortNum;
    
    /**
     * 状态0:可用，1：删除
     */
    private int status;

    
    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public String getQuickDoorId() {
        return quickDoorId;
    }

    public void setQuickDoorId(String quickDoorId) {
        this.quickDoorId = quickDoorId;
    }

    public Integer getSortNum() {
        return sortNum;
    }

    public void setSortNum(Integer sortNum) {
        this.sortNum = sortNum;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
