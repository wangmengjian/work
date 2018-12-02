package logistics.work.models.domain;

import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;

/**
 * 审核详情
 */
public class WorkAuditDetail {
    private Integer id;
    @NotNull(message = "请填写作名称")
    private String workName;
    @NotNull(message="请填写工作内容工作内容")
    private String workContent;
    private String workInstructor;
    @NotNull(message="请填写标准工时")
    private Integer workMinutes;
    @NotNull(message="工作项来源")
    private String workFrom;
    private String auditStatus;
    private MultipartFile file;
    private Integer workUserId;

    public Integer getWorkUserId() {
        return workUserId;
    }

    public void setWorkUserId(Integer workUserId) {
        this.workUserId = workUserId;
    }

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }

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

    public String getAuditStatus() {
        return auditStatus;
    }

    public void setAuditStatus(String auditStatus) {
        this.auditStatus = auditStatus;
    }

    public String getWorkFrom() {
        return workFrom;
    }

    public void setWorkFrom(String workFrom) {
        this.workFrom = workFrom;
    }

    @Override
    public String toString() {
        return "WorkAuditDetail{" +
                "id=" + id +
                ", workName='" + workName + '\'' +
                ", workContent='" + workContent + '\'' +
                ", workInstructor='" + workInstructor + '\'' +
                ", workMinutes=" + workMinutes +
                ", workFrom='" + workFrom + '\'' +
                ", auditStatus='" + auditStatus + '\'' +
                ", file=" + file +
                '}';
    }
}
