package com.nankingdata.yc.work.models.dto;

import javax.validation.constraints.NotNull;

/**
 * 接受添加的历史工作
 */
public class HistoryWorkDto {
    private Integer id;
    private Integer scheduleId;
    @NotNull(message = "请输入完成状态")
    private String finishStatus;
    private String date;
    private String finishPicture;
    private String finishFeedback;
    private String finishCondition;
    @NotNull(message = "请输入开始时间")
    private String beginTime;
    private String finishTime;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
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

    public String getFinishStatus() {
        return finishStatus;
    }

    public void setFinishStatus(String finishStatus) {
        this.finishStatus = finishStatus;
    }

    public String getFinishPicture() {
        return finishPicture;
    }

    public void setFinishPicture(String finishPicture) {
        this.finishPicture = finishPicture;
    }

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
}
