package logistics.work.models.domain;

import java.util.Date;

/**
 * 审核信息
 */
public class WorkAudit {
    private Integer id;
    private Integer auditUserId;
    private Integer auditDetailId;
    private Integer auditSubmitterId;
    private Date auditSubmitTime;
    private Date auditSuccessTime;
    private Date auditFailTime;
    private String auditFailReason;
    private String auditStatus;

    public Integer getAuditSubmitterId() {
        return auditSubmitterId;
    }

    public void setAuditSubmitterId(Integer auditSubmitterId) {
        this.auditSubmitterId = auditSubmitterId;
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

    public Integer getAuditDetailId() {
        return auditDetailId;
    }

    public void setAuditDetailId(Integer auditDetailId) {
        this.auditDetailId = auditDetailId;
    }

    public Date getAuditSubmitTime() {
        return auditSubmitTime;
    }

    public void setAuditSubmitTime(Date auditSubmitTime) {
        this.auditSubmitTime = auditSubmitTime;
    }

    public Date getAuditSuccessTime() {
        return auditSuccessTime;
    }

    public void setAuditSuccessTime(Date auditSuccessTime) {
        this.auditSuccessTime = auditSuccessTime;
    }

    public Date getAuditFailTime() {
        return auditFailTime;
    }

    public void setAuditFailTime(Date auditFailTime) {
        this.auditFailTime = auditFailTime;
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
