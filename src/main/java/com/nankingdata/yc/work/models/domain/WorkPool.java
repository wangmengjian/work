package com.nankingdata.yc.work.models.domain;

import com.nankingdata.yc.work.common.FileUtils;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * 工作池
 */
public class WorkPool {
    private Integer id;
    private Integer userId;
    private String employeeName;
    @NotNull(message = "请填写工作名称")
    private String workName;
    @NotNull(message="请填写工作内容工作内容")
    private String workContent;
    private String workInstructor;
    private String workInstructorFullPath;
    @NotNull(message="请填写标准工时")
    @Min(value=1,message = "标准时间有误")
    @Max(value=720,message = "标准时间有误")
    private Integer workMinutes;
    @NotNull(message = "请选择工作优先级")
    private String workPriority;
    private String workFrom;
    private String createTime;
    private String updateTime;
    private Integer allotUserId;
    private Integer auditRecordId;
    private Integer isDoing=0;
    private Integer[] employeeIds;
    private Integer departmentId;

    public String getWorkInstructorFullPath() {
        return FileUtils.getFullPath(workInstructor);
    }

    public void setWorkInstructorFullPath(String workInstructorFullPath) {
        this.workInstructorFullPath = workInstructorFullPath;
    }

    public Integer getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }

    public String getWorkPriority() {
        return workPriority;
    }

    public void setWorkPriority(String workPriority) {
        this.workPriority = workPriority;
    }

    public Integer[] getEmployeeIds() {
        return employeeIds;
    }

    public void setEmployeeIds(Integer[] employeeIds) {
        this.employeeIds = employeeIds;
    }

    public String getAuditStatus() {
        return auditStatus;
    }

    public void setAuditStatus(String auditStatus) {
        this.auditStatus = auditStatus;
    }

    private String auditStatus;

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public Integer getAuditRecordId() {
        return auditRecordId;
    }

    public void setAuditRecordId(Integer auditRecordId) {
        this.auditRecordId = auditRecordId;
    }

    public Integer getIsDoing() {
        return isDoing;
    }

    public void setIsDoing(Integer isDoing) {
        this.isDoing = isDoing;
    }

    public Integer getAllotUserId() {
        return allotUserId;
    }

    public void setAllotUserId(Integer allotUserId) {
        this.allotUserId = allotUserId;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
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
