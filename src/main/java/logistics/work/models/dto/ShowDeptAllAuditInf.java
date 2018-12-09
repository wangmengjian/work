package logistics.work.models.dto;

public class ShowDeptAllAuditInf {
    private Integer id;
    private String workName;
    private String workContent;
    private String submitter;
    private Integer submitterId;
    private String workInstructor;
    private Integer workMinutes;
    private String workFrom;
    private String auditSubmitTime;
    private Integer originWorkId;
    private String fromCode;

    public Integer getSubmitterId() {
        return submitterId;
    }

    public void setSubmitterId(Integer submitterId) {
        this.submitterId = submitterId;
    }

    public String getFromCode() {
        return fromCode;
    }

    public void setFromCode(String fromCode) {
        this.fromCode = fromCode;
    }

    public Integer getOriginWorkId() {
        return originWorkId;
    }

    public void setOriginWorkId(Integer originWorkId) {
        this.originWorkId = originWorkId;
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

    public String getSubmitter() {
        return submitter;
    }

    public void setSubmitter(String submitter) {
        this.submitter = submitter;
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

    public String getWorkFrom() {
        return workFrom;
    }

    public void setWorkFrom(String workFrom) {
        this.workFrom = workFrom;
    }

    public String getAuditSubmitTime() {
        return auditSubmitTime;
    }

    public void setAuditSubmitTime(String auditSubmitTime) {
        this.auditSubmitTime = auditSubmitTime;
    }
}
