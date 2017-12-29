package org.com.wing.enterprise.bean;

import java.io.Serializable;

import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * 
 * //TODO 企服标签
 * @author sino
 */

@Table(name = "qfy_service_tag")
public class EntryServiceTag extends EnterPriseBaseBean implements Serializable{

    @Transient
    private static final long serialVersionUID = 1L;
    
    /**
     * 标签名称
     */
    private String name;
    
    /**
     * 标签图片
     */
    private String imgPath;
    
    /**
     * 状态0可用1删除
     */
    private int status;
    
    /**
     * 热门服务企业
     */
    private String hotEntryId;
    /**
     * 热门企业排序
     */
    private int hotSort;
    
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

	public String getHotEntryId() {
		return hotEntryId;
	}

	public void setHotEntryId(String hotEntryId) {
		this.hotEntryId = hotEntryId;
	}

	public int getHotSort() {
		return hotSort;
	}

	public void setHotSort(int hotSort) {
		this.hotSort = hotSort;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
    
    
    
}
