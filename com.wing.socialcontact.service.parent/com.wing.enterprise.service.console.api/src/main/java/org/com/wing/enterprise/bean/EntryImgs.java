package org.com.wing.enterprise.bean;

import java.io.Serializable;

import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * 
 * //TODO 企服图片
 * @author sino
 */

@Table(name = "qfy_entry_img")
public class EntryImgs extends EnterPriseBaseBean implements Serializable{

    @Transient
    private static final long serialVersionUID = 1L;
    
    /**
     * 企服ID
     */
    private String entryId;
    
    /**
     * 图片路径
     */
    private String imgPath;
    
    /**
     * 图片类别 0:企服认证，1：企服banner
     */
    private int type;
    
    private int sortNum;
    
    public int getSortNum() {
        return sortNum;
    }

    public void setSortNum(int sortNum) {
        this.sortNum = sortNum;
    }

    public String getEntryId() {
        return entryId;
    }

    public void setEntryId(String entryId) {
        this.entryId = entryId;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
