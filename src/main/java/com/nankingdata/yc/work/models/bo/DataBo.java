package com.nankingdata.yc.work.models.bo;

import java.util.List;

public class DataBo {
    private Integer empId;
    private String empName;
    private List<AssessConditionBo> assessCondition;
    private List<FinishConditionBo> finishCondition;

    public Integer getEmpId() {
        return empId;
    }

    public void setEmpId(Integer empId) {
        this.empId = empId;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public List<AssessConditionBo> getAssessCondition() {
        return assessCondition;
    }

    public void setAssessCondition(List<AssessConditionBo> assessCondition) {
        this.assessCondition = assessCondition;
    }

    public List<FinishConditionBo> getFinishCondition() {
        return finishCondition;
    }

    public void setFinishCondition(List<FinishConditionBo> finishCondition) {
        this.finishCondition = finishCondition;
    }
}
