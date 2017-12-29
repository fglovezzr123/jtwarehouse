package org.com.wing.enterprise.bean;

import java.io.Serializable;

import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * 
 * //TODO 快捷入口聚合页banner
 * @author sino
 */

@Table(name = "qfy_quick_detail_banner")
public class QuickDetailBanner extends EnterPriseBaseBean implements Serializable{

    @Transient
    private static final long serialVersionUID = 1L;
    
    /**
     * 图片路径
     */
    private String imgPath;
    
    /**
     * 图片名称
     */
    private String name;
    
    /**
     * 图片链接 
     */
    private String link;
    
    /**
     * 序号
     */
    private int sortNum;
    
    /**
     * 状态0:可用，1:删除
     */
    private int status;
    
    /**
     * 快捷入口ID
     */
    private String quickDoorId;

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public int getSortNum() {
        return sortNum;
    }

    public void setSortNum(int sortNum) {
        this.sortNum = sortNum;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getQuickDoorId() {
        return quickDoorId;
    }

    public void setQuickDoorId(String quickDoorId) {
        this.quickDoorId = quickDoorId;
    }
}
