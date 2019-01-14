package com.nankingdata.yc.work.models.domain;


/**
 * 日工作计划明细
 */
public class WorkScheduleDetail {
    private Integer id;
    private Integer scheduleId;
    private Integer workId;
    private String workFrom;
    private String finishPicture;
    private String finishCondition;
    private String finishFeedback;
    private String finishStatus;
    private String finishTime;
    private String beginTime;
    private Integer allotUserId;

    public Integer getAllotUserId() {
        return allotUserId;
    }

    public void setAllotUserId(Integer allotUserId) {
        this.allotUserId = allotUserId;
    }

    public String getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    public String getFinishFeedback() {
        return finishFeedback;
    }

    public void setFinishFeedback(String finishFeedback) {
        this.finishFeedback = finishFeedback;
    }

    public String getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(String finishTime) {
        this.finishTime = finishTime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(Integer scheduleId) {
        this.scheduleId = scheduleId;
    }

    public Integer getWorkId() {
        return workId;
    }

    public void setWorkId(Integer workId) {
        this.workId = workId;
    }

    public String getWorkFrom() {
        return workFrom;
    }

    public void setWorkFrom(String workFrom) {
        this.workFrom = workFrom;
    }

    public String getFinishPicture() {
        return finishPicture;
    }

    public void setFinishPicture(String finishPicture) {
        this.finishPicture = finishPicture;
    }

    public String getFinishCondition() {
        return finishCondition;
    }

    public void setFinishCondition(String finishCondition) {
        this.finishCondition = finishCondition;
    }

    public String getFinishStatus() {
        return finishStatus;
    }

    public void setFinishStatus(String finishStatus) {
        this.finishStatus = finishStatus;
    }
}
