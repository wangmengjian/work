package com.nankingdata.yc.work.models.domain;


/**
 * 审核记录
 */
public class WorkAuditDetail {
    private Integer id;
    private Integer auditUserId;
    private Integer auditItemId;
    private String auditFailReason;
    private String auditStatus;
    private String auditTime;

    public String getAuditTime() {
        return auditTime;
    }

    public void setAuditTime(String auditTime) {
        this.auditTime = auditTime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAuditUserId() {
        return auditUserId;
    }

    public void setAuditUserId(Integer auditUserId) {
        this.auditUserId = auditUserId;
    }

    public Integer getAuditItemId() {
        return auditItemId;
    }

    public void setAuditItemId(Integer auditItemId) {
        this.auditItemId = auditItemId;
    }

    public String getAuditFailReason() {
        return auditFailReason;
    }

    public void setAuditFailReason(String auditFailReason) {
        this.auditFailReason = auditFailReason;
    }

    public String getAuditStatus() {
        return auditStatus;
    }

    public void setAuditStatus(String auditStatus) {
        this.auditStatus = auditStatus;
    }

}
