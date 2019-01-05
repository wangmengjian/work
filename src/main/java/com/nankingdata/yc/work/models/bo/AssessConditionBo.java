package com.nankingdata.yc.work.models.bo;

/**
 * 考核情况统计
 */
public class AssessConditionBo {
    private Integer empId;
    private String empName;
    private String assessGrade;
    private Integer count;

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

    public String getAssessGrade() {
        return assessGrade;
    }

    public void setAssessGrade(String assessGrade) {
        this.assessGrade = assessGrade;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
