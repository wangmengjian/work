package com.nankingdata.yc.work.models.dto;
import javax.validation.Valid;
import java.util.List;

/**
 * 日计划信息
 */
public class WorkScheduleDto {
    private Integer id;
    private String date;
    private String submitTime;
    private String submitStatus;
    private String empName;
    @Valid
    private List<WorkScheduleDetailDto> workScheduleDetailDtoList;

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getSubmitTime() {
        return submitTime;
    }

    public void setSubmitTime(String submitTime) {
        this.submitTime = submitTime;
    }

    public String getSubmitStatus() {
        return submitStatus;
    }

    public void setSubmitStatus(String submitStatus) {
        this.submitStatus = submitStatus;
    }

    public List<WorkScheduleDetailDto> getWorkScheduleDetailDtoList() {
        return workScheduleDetailDtoList;
    }

    public void setWorkScheduleDetailDtoList(List<WorkScheduleDetailDto> workScheduleDetailDtoList) {
        this.workScheduleDetailDtoList = workScheduleDetailDtoList;
    }
}
