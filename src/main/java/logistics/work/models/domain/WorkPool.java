package logistics.work.models.domain;

import javax.validation.constraints.NotNull;

/**
 * 工作池
 */
public class WorkPool {
    private Integer id;
    private Integer userId;
    private String username;
    @NotNull(message = "请填写作名称")
    private String workName;
    @NotNull(message="请填写工作内容工作内容")
    private String workContent;
    private String workInstructor;
    @NotNull(message="请填写标准工时")
    private Integer workMinutes;
    @NotNull(message="工作项来源")
    private String workFrom;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public String getWorkFrom() {
        return workFrom;
    }

    public void setWorkFrom(String workFrom) {
        this.workFrom = workFrom;
    }
}
