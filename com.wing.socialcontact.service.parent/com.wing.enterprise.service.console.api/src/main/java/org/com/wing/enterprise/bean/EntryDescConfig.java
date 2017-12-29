package org.com.wing.enterprise.bean;

import java.io.Serializable;

import javax.persistence.Table;
import javax.persistence.Transient;


/**
 * 企服联盟介绍等等统一配置
 * 图片+链接
 * //TODO 
 * @author sino
 */
@Table(name = "qfy_desc_config")
public class EntryDescConfig extends EnterPriseBaseBean implements Serializable{
    
    @Transient
    private static final long serialVersionUID = 1L;
    
    /**
     * 图片路径
     */
    private String imgPath;
    
    /**
     * 图片链接
     */
    private String link;
    
    /**
     * 类型，1：首页企服联盟介绍
     */
    private int type;

    /**
     * 0可用，1删除
     */
    private int status;
    
    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
