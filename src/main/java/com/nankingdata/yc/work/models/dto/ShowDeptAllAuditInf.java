package com.nankingdata.yc.work.models.dto;

/**
 * 显示部门中所有员工提交的待审核的工作项
 */
public class ShowDeptAllAuditInf {
    private Integer id;
    private String workName;
    private String workContent;
    private String submitter;
    private String workInstructor;
    private Integer workMinutes;
    private String workFrom;
    private String auditSubmitTime;

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
