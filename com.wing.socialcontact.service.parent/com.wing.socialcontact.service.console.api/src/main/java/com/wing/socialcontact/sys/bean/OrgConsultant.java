package com.wing.socialcontact.sys.bean;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by fenggang on 12/26/17.
 *
 * @author fenggang
 * @date 12/26/17
 */
@Table(name = "tjrb_org_consultant")
public class OrgConsultant implements Serializable {

    private static final long serialVersionUID = -7679826409385749803L;
    @Id
    @GeneratedValue(generator = "JDBC")
    @Column(name = "id")
    private Long id;

    @Column(name = "org_no")
    private String orgNo;
    @Column(name = "org_name")
    private String orgName;
    @Column(name = "consultant")
    private String consultant;
    @Column(name = "title")
    private String title;
    @Column(name = "create_date")
    private Date createDate;
    @Column(name = "update_date")
    private Date updateDate;

    @Transient
    private int status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrgNo() {
        return orgNo;
    }

    public void setOrgNo(String orgNo) {
        this.orgNo = orgNo;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public String getConsultant() {
        return consultant;
    }

    public void setConsultant(String consultant) {
        this.consultant = consultant;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
