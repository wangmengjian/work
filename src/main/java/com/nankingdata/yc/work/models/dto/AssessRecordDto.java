package com.nankingdata.yc.work.models.dto;

/**
 * 考核记录
 */
public class AssessRecordDto {
    private Integer id;
    private String assessTime;
    private String assessGrade;
    private String assessDesc;
    private String workName;
    private String workContent;
    private String workInstructor;
    private String workFrom;
    private String empName;
    private String beginTime;
    private String finishTime;
    private String finishStatus;

    public String getWorkFrom() {
        return workFrom;
    }

    public void setWorkFrom(String workFrom) {
        this.workFrom = workFrom;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAssessTime() {
        return assessTime;
    }

    public void setAssessTime(String assessTime) {
        this.assessTime = assessTime;
    }

    public String getAssessGrade() {
        return assessGrade;
    }

    public void setAssessGrade(String assessGrade) {
        this.assessGrade = assessGrade;
    }

    public String getAssessDesc() {
        return assessDesc;
    }

    public void setAssessDesc(String assessDesc) {
        this.assessDesc = assessDesc;
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

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public String getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    public String getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(String finishTime) {
        this.finishTime = finishTime;
    }

    public String getFinishStatus() {
        return finishStatus;
    }

    public void setFinishStatus(String finishStatus) {
        this.finishStatus = finishStatus;
    }
}
