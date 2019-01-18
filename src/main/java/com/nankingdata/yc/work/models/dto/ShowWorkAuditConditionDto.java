package com.nankingdata.yc.work.models.dto;

import com.nankingdata.yc.work.common.FileUtils;

/**
 * 显示员工各项工作的审核情况
 */
public class ShowWorkAuditConditionDto {
    private String id;
    private String auditStatus;
    private String workName;
    private String workContent;
    private String workInstructor;
    private String workInstructorFullPath;
    private Integer workMinutes;
    private String workPriority;
    private String time;
    private String auditFailReason;
    private String workFrom;
    private Integer originWorkId;

    public String getWorkInstructorFullPath() {
        return FileUtils.getFullPath(workInstructor);
    }

    public void setWorkInstructorFullPath(String workInstructorFullPath) {
        this.workInstructorFullPath = workInstructorFullPath;
    }

    public String getWorkPriority() {
        return workPriority;
    }

    public void setWorkPriority(String workPriority) {
        this.workPriority = workPriority;
    }

    public Integer getOriginWorkId() {
        return originWorkId;
    }

    public void setOriginWorkId(Integer originWorkId) {
        this.originWorkId = originWorkId;
    }

    public String getWorkFrom() {
        return workFrom;
    }

    public void setWorkFrom(String workFrom) {
        this.workFrom = workFrom;
    }

    public String getAuditFailReason() {
        return auditFailReason;
    }

    public void setAuditFailReason(String auditFailReason) {
        this.auditFailReason = auditFailReason;
    }
    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAuditStatus() {
        return auditStatus;
    }

    public void setAuditStatus(String auditStatus) {
        this.auditStatus = auditStatus;
    }

    public String getWorkName() {
        return workName;
    }

    public void setWorkName(String workName) {
        this.workName = workName;
    }

    public String getWorkContent() {
        return workContent;
    }

    public void setWorkContent(String workContent) {
        this.workContent = workContent;
    }

    public String getWorkInstructor() {
        return workInstructor;
    }

    public void setWorkInstructor(String workInstructor) {
        this.workInstructor = workInstructor;
    }

    public Integer getWorkMinutes() {
        return workMinutes;
    }

    public void setWorkMinutes(Integer workMinutes) {
        this.workMinutes = workMinutes;
    }
}
