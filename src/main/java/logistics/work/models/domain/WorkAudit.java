package logistics.work.models.domain;

import java.util.Date;

/**
 * 审核信息
 */
public class WorkAudit {
    private Integer id;
    private Integer auditUserId;
    private Integer auditDescId;
    private Date auditSubmitTime;
    private Date auditSuccessTime;
    private Date auditFailTime;
    private String auditFailReason;
    private String auditStatus;
    private String readStatus;
    private String deleteStatus;

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

    public Integer getAuditDescId() {
        return auditDescId;
    }

    public void setAuditDescId(Integer auditDescId) {
        this.auditDescId = auditDescId;
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

    public String getReadStatus() {
        return readStatus;
    }

    public void setReadStatus(String readStatus) {
        this.readStatus = readStatus;
    }

    public String getDeleteStatus() {
        return deleteStatus;
    }

    public void setDeleteStatus(String deleteStatus) {
        this.deleteStatus = deleteStatus;
    }
}
