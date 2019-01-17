package com.nankingdata.yc.work.models.dto;

import java.util.List;

public class DailyAssessRecordDto {
    private String date;
    private Integer assessCount;
    private List<AssessRecordDto> assessRecordDtoList;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Integer getAssessCount() {
        return assessCount;
    }

    public void setAssessCount(Integer assessCount) {
        this.assessCount = assessCount;
    }

    public List<AssessRecordDto> getAssessRecordDtoList() {
        return assessRecordDtoList;
    }

    public void setAssessRecordDtoList(List<AssessRecordDto> assessRecordDtoList) {
        this.assessRecordDtoList = assessRecordDtoList;
    }
}
