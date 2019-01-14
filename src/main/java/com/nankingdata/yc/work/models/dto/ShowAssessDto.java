package com.nankingdata.yc.work.models.dto;

/**
 * 显示考核的工作
 */
public class ShowAssessDto {
    private Integer id;
    private String finishStatus;
    private String workName;
    private String workContent;
    private String workFrom;
    private Integer workMinutes;
    private String beginTime;
    private String finishTime;
    private String assessGrade;
    private String empName;
    private String assessDesc;
    private Integer assessStatus;
    private Float workEfficiency;
    private String workPriority;
    private String finishCondition;
    private String finishFeedback;

    public String getFinishFeedback() {
        return finishFeedback;
    }

    public void setFinishFeedback(String finishFeedback) {
        this.finishFeedback = finishFeedback;
    }

    public String getFinishCondition() {
        return finishCondition;
    }

    public void setFinishCondition(String finishCondition) {
        this.finishCondition = finishCondition;
    }

    public String getWorkPriority() {
        return workPriority;
    }

    public void setWorkPriority(String workPriority) {
        this.workPriority = workPriority;
    }

    public Float getWorkEfficiency() {
        return workEfficiency;
    }

    public void setWorkEfficiency(Float workEfficiency) {
        this.workEfficiency = workEfficiency;
    }

    public Integer getAssessStatus() {
        return assessStatus;
    }

    public void setAssessStatus(Integer assessStatus) {
        this.assessStatus = assessStatus;
    }

    public String getAssessDesc() {
        return assessDesc;
    }

    public void setAssessDesc(String assessDesc) {
        this.assessDesc = assessDesc;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFinishStatus() {
        return finishStatus;
    }

    public void setFinishStatus(String finishStatus) {
        this.finishStatus = finishStatus;
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

    public String getWorkFrom() {
        return workFrom;
    }

    public void setWorkFrom(String workFrom) {
        this.workFrom = workFrom;
    }

    public Integer getWorkMinutes() {
        return workMinutes;
    }

    public void setWorkMinutes(Integer workMinutes) {
        this.workMinutes = workMinutes;
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

    public String getAssessGrade() {
        return assessGrade;
    }

    public void setAssessGrade(String assessGrade) {
        this.assessGrade = assessGrade;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }
}
