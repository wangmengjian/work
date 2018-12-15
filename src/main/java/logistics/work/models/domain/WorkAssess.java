package logistics.work.models.domain;

import java.util.Date;

/**
 * 考核记录
 */
public class WorkAssess {
    private Integer id;
    private Integer userId;
    private Integer assessUserId;
    private Date assessTime;
    private String deleteStatus;
    private String assessGrade;
    private Integer scheduleDetailId;
    private String assessDesc;

    public Integer getScheduleDetailId() {
        return scheduleDetailId;
    }

    public void setScheduleDetailId(Integer scheduleDetailId) {
        this.scheduleDetailId = scheduleDetailId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getAssessUserId() {
        return assessUserId;
    }

    public void setAssessUserId(Integer assessUserId) {
        this.assessUserId = assessUserId;
    }

    public Date getAssessTime() {
        return assessTime;
    }

    public void setAssessTime(Date assessTime) {
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

    public String getDeleteStatus() {
        return deleteStatus;
    }

    public void setDeleteStatus(String deleteStatus) {
        this.deleteStatus = deleteStatus;
    }
}
