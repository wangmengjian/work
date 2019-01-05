package com.nankingdata.yc.work.models.dao;


import com.nankingdata.yc.work.models.bo.FinishConditionBo;
import com.nankingdata.yc.work.models.domain.WorkPool;
import com.nankingdata.yc.work.models.dto.ShowWorkAuditConditionDto;

import java.util.List;
import java.util.Map;

public interface WorkDao {
    /*查询所有提交的工作*/
    public List<ShowWorkAuditConditionDto> querySubmitRecordByPage(Map<String,Object> params);
    /*查看所有提交的工作总数*/
    public Integer querySubmitRecordCount(Map<String,Object> params);
    /*查询未添加的工作*/
    public List<WorkPool> queryUnAddWork(Map<String,Object> params);
    /*添加通过的工作项*/
    public int addAgreeWork(List<WorkPool> workPoolList);
    /*更新通过的工作项*/
    public int updateAgreeWork(List<WorkPool> workPoolList);
    /*查询部门下员工的常规工作项*/
    public List<WorkPool> queryWorkByEmployeeId(Map<String,Object> params);
    /*查询部门下员工的常规工作项*/
    public int queryWorkCountByEmployeeId(Map<String,Object> params);
    /*添加工作项*/
    public int addWork(List<WorkPool> workPoolList);
    /*更改常规工作项*/
    public int updateWork(WorkPool workPool);
    /*根据工作项id查询工作项*/
    public List<WorkPool> queryWorkPool(List<WorkPool> workPoolList);
    /*领导删除工作项*/
    public int deleteWork(Integer workId);
    /*员工根据id查询工作项*/
    public WorkPool queryWorkById(Integer id);
    /*更改工作的审核状态*/
    public int updateWorkAuditStatus(List<WorkPool> workPoolList);
    /*查询工作完成情况*/
    public List<FinishConditionBo> queryWorkFinishStatus(Map<String,Object> params);
}
