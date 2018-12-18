package com.nankingdata.yc.work.models.dto;

import com.nankingdata.yc.work.models.domain.WorkPool;

import javax.validation.Valid;
import java.util.List;

/**
 * 新增员工的常规工作项
 */
public class WorkPoolDto {
    @Valid
    private List<WorkPool> workPoolList;

    public List<WorkPool> getWorkPoolList() {
        return workPoolList;
    }

    public void setWorkPoolList(List<WorkPool> workPoolList) {
        this.workPoolList = workPoolList;
    }
}
