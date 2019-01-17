package com.nankingdata.yc.work.models.dao;

import com.nankingdata.yc.work.models.bo.AssessConditionBo;
import com.nankingdata.yc.work.models.domain.WorkAssess;
import com.nankingdata.yc.work.models.dto.AssessRecordDto;
import com.nankingdata.yc.work.models.dto.DailyAssessRecordDto;
import com.nankingdata.yc.work.models.dto.ShowAssessDto;

import java.util.List;
import java.util.Map;

public interface AssessDao {
    /*领导查询本部门所有待考核工作*/
    public List<ShowAssessDto> queryAllAssess(Map<String,Object> params);
    /*领导查询本部门所有的待考核工作总数*/
    public int queryAllAssessCount(Map<String,Object> params);
    /*领导查询分配给其他部门的考核工作*/
    public List<ShowAssessDto> queryOtherDeptAssess(Map<String,Object> params);
    /*领导查询分配给其他部门的考核工作*/
    public int queryOtherDeptAssessCount(Map<String,Object> params);
    /*领导考核工作*/
    public int assessWork(List<WorkAssess> workAssessList);
    public List<ShowAssessDto> employeeQueryAllAssess(Map<String,Object> params);
    public int employeeQueryAllAssessCount(Map<String,Object> params);
    /*领导查询考核记录*/
    public List<AssessRecordDto> leaderQueryAssessRecords(Map<String,Object> params);
    /*领导查询考核记录总数*/
    public Integer leaderQueryAssessRecordsCount(Map<String,Object> params);
    /*查询员工在时间范围内考核的情况*/
    public List<AssessConditionBo> queryAssessCondition(Map<String,Object> params);
    /*通过明细的id查询一条考核记录*/
    public WorkAssess queryOneAssessRecordByDetailId(Integer id);
    /*领导查询有考核的日期*/
    public List<DailyAssessRecordDto> queryDateHasAssessRecords(Map<String,Object> params);
    /*领导查询有考核的日期*/
    public int queryDateHasAssessRecordsCount(Map<String,Object> params);
}
