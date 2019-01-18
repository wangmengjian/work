package com.nankingdata.yc.work.models.dto;

import com.nankingdata.yc.work.common.FileUtils;

/**
 * 显示部门中所有员工提交的审核详情
 */
public class ShowDeptAllAuditInf {
    private Integer id;
    private String workName;
    private String workContent;
    private String submitter;
    private String workInstructor;
    private String workInstructorFullPath;
    private Integer workMinutes;
    private String workFrom;
    private String auditSubmitTime;
    private String auditStatus;
    private String workPriority;

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

    public String getAuditStatus() {
        return auditStatus;
    }

    public void setAuditStatus(String auditStatus) {
        this.auditStatus = auditStatus;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getSubmitter() {
        return submitter;
    }

    public void setSubmitter(String submitter) {
        this.submitter = submitter;
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

    public String getWorkFrom() {
        return workFrom;
    }

    public void setWorkFrom(String workFrom) {
        this.workFrom = workFrom;
    }

    public String getAuditSubmitTime() {
        return auditSubmitTime;
    }

    public void setAuditSubmitTime(String auditSubmitTime) {
        this.auditSubmitTime = auditSubmitTime;
    }
}
