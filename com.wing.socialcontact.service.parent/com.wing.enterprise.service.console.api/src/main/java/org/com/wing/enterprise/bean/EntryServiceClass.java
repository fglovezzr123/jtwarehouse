package org.com.wing.enterprise.bean;

import java.io.Serializable;

import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * 
 * //TODO 企服分类
 * @author sino
 */

@Table(name = "qfy_service_class")
public class EntryServiceClass extends EnterPriseBaseBean implements Serializable{

    @Transient
    private static final long serialVersionUID = 1L;
    
    /**
     * 分类名称
     */
    private String className;
    
    /**
     * 父级分类ID
     */
    private String parentId;
    
    
    /**
     * 分类序号(1~99999999)
     */
    private int sortNum;
    
    /**
     * 时候显示（0显示，1不显示）
     */
    private int isShow;

    /**
     * 状态0：可用，1：删除
     */
    private int status;
    
    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public Integer getSortNum() {
        return sortNum;
    }

    public void setSortNum(Integer sortNum) {
        this.sortNum = sortNum;
    }

    public Integer getIsShow() {
        return isShow;
    }

    public void setIsShow(Integer isShow) {
        this.isShow = isShow;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
