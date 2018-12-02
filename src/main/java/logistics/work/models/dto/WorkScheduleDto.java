package logistics.work.models.dto;

import logistics.work.models.domain.WorkScheduleDetail;

import java.util.Date;
import java.util.List;

/**
 * 日计划信息
 */
public class WorkScheduleDto {
    private Integer id;
    private Date date;
    private Date submitTime;
    private String submitStatus;
    private List<WorkScheduleDetail> workScheduleDetailList;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getSubmitTime() {
        return submitTime;
    }

    public void setSubmitTime(Date submitTime) {
        this.submitTime = submitTime;
    }

    public String getSubmitStatus() {
        return submitStatus;
    }

    public void setSubmitStatus(String submitStatus) {
        this.submitStatus = submitStatus;
    }

    public List<WorkScheduleDetail> getWorkScheduleDetailList() {
        return workScheduleDetailList;
    }

    public void setWorkScheduleDetailList(List<WorkScheduleDetail> workScheduleDetailList) {
        this.workScheduleDetailList = workScheduleDetailList;
    }
}
