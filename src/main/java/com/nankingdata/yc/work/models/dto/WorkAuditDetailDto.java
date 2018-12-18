package com.nankingdata.yc.work.models.dto;

import com.nankingdata.yc.work.models.domain.WorkAuditDetail;

import java.util.List;

/**
 * 提交工作审核情况
 */
public class WorkAuditDetailDto {
    private List<WorkAuditDetail> workAuditDetailList;
    public List<WorkAuditDetail> getWorkAuditDetailList() {
        return workAuditDetailList;
    }

    public void setWorkAuditDetailList(List<WorkAuditDetail> workAuditDetailList) {
        this.workAuditDetailList = workAuditDetailList;
    }
}
