package logistics.work.models.dto;

/**
 * 显示员工各项工作的审核情况
 */
public class ShowWorkAuditConditionDto {
    private String id;
    private String auditStatus;
    private String workName;
    private String workContent;
    private String workInstructor;
    private Integer workMinutes;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAuditStatus() {
        return auditStatus;
    }

    public void setAuditStatus(String auditStatus) {
        this.auditStatus = auditStatus;
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

    public Integer getWorkMinutes() {
        return workMinutes;
    }

    public void setWorkMinutes(Integer workMinutes) {
        this.workMinutes = workMinutes;
    }
}
