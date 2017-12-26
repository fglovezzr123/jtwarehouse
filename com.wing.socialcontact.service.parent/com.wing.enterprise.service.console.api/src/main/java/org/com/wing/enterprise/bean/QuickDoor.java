package org.com.wing.enterprise.bean;

import java.io.Serializable;

import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * 
 * //TODO 快捷入口
 * @author sino
 */

@Table(name = "qfy_quick_entry")
public class QuickDoor extends EnterPriseBaseBean implements Serializable{

    @Transient
    private static final long serialVersionUID = 1L;
    
    /**
     * 快捷入口名称
     */
    private String quickName;
    
    /**
     * 快捷入口图片
     */
    private String quickImgPath;
    
    /**
     * 快捷入口所属模块默认0首页
     */
    private int quickModule;
    
    /**
     * 快捷入口排序(0-9999)
     */
    private int quickSortNum;
    
    /**
     * 快捷入口名称状态0可用，1删除
     */
    private int quickStatus;

    public String getQuickName() {
        return quickName;
    }

    public void setQuickName(String quickName) {
        this.quickName = quickName;
    }

    public String getQuickImgPath() {
        return quickImgPath;
    }

    public void setQuickImgPath(String quickImgPath) {
        this.quickImgPath = quickImgPath;
    }

    public Integer getQuickModule() {
        return quickModule;
    }

    public void setQuickModule(Integer quickModule) {
        this.quickModule = quickModule;
    }

    public Integer getQuickSortNum() {
        return quickSortNum;
    }

    public void setQuickSortNum(Integer quickSortNum) {
        this.quickSortNum = quickSortNum;
    }

    public Integer getQuickStatus() {
        return quickStatus;
    }

    public void setQuickStatus(Integer quickStatus) {
        this.quickStatus = quickStatus;
    }
}
