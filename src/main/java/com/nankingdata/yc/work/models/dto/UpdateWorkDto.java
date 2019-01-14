package com.nankingdata.yc.work.models.dto;

import javax.validation.constraints.NotNull;

/**
 * 员工更改工作项
 */
public class UpdateWorkDto {
    private int id;
    @NotNull(message = "请输入工作名称")
    private String workName;
    @NotNull(message = "请输入工作内容")
    private String workContent;
    @NotNull(message = "请选择工作来源")
    private String workFrom;
    @NotNull(message = "请输入标准工时")
    private Integer workMinutes;
    @NotNull(message="请选择工作优先级")
    private String workPriority;
    private String workInstructor;
    private Integer originWorkId;

    public String getWorkPriority() {
        return workPriority;
    }

    public void setWorkPriority(String workPriority) {
        this.workPriority = workPriority;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public String getWorkFrom() {
        return workFrom;
    }

    public void setWorkFrom(String workFrom) {
        this.workFrom = workFrom;
    }

    public Integer getWorkMinutes() {
        return workMinutes;
    }

    public void setWorkMinutes(Integer workMinutes) {
        this.workMinutes = workMinutes;
    }

    public String getWorkInstructor() {
        return workInstructor;
    }

    public void setWorkInstructor(String workInstructor) {
        this.workInstructor = workInstructor;
    }

    public Integer getOriginWorkId() {
        return originWorkId;
    }

    public void setOriginWorkId(Integer originWorkId) {
        this.originWorkId = originWorkId;
    }
}
