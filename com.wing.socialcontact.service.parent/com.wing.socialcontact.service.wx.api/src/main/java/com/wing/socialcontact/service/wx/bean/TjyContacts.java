package com.wing.socialcontact.service.wx.bean;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Devil
 * @description: 通讯录列表实体
 * @date 2017/12/14 11:09
 */
public class TjyContacts implements Serializable {

    /** 主键 **/
    private Long id;

    /** 用户id **/
    private Long userId;

    /** 手机号 **/
    private String mobile;

    /** 状态 **/
    private String status;

    /** 创建时间 **/
    private Date createTime;

    /** 更新时间 **/
    private Date updateTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
