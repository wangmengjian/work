package com.nankingdata.yc.work.models.dao;

import com.nankingdata.yc.work.models.domain.WorkAudit;
import com.nankingdata.yc.work.models.domain.WorkAuditDetail;
import com.nankingdata.yc.work.models.dto.ShowDeptAllAuditInf;
import com.nankingdata.yc.work.models.dto.UpdateWorkDto;

import java.util.List;
import java.util.Map;

public interface WorkAuditDao {
    /*添加审核明细*/
    public int addWorkAuditDetail(List<WorkAuditDetail> workAuditDetailList);
    /*添加审核记录*/
    public int addWorkAudit(List<WorkAudit> workAuditList);
    /*分页查询部门所有未审核的工作*/
    public List<ShowDeptAllAuditInf> queryDeptAllUnauditedWorkByPage(Map<String,Object> params);
    /*查询部门所有未审核的工作总数*/
    public Integer queryDeptAllUnauditedWorkCount(Map<String,Object> params);
    /*查询领导自己的审核记录*/
    public List<ShowDeptAllAuditInf> queryAuditedRecord(Map<String,Object> params);
    /*查询领导审核记录总数*/
    public Integer queryAuditedRecordCount(Map<String,Object> params);
    /*更改工作项的完成状态*/
    public int updateAuditStatus(List<WorkAuditDetail> workAuditDetailList);
    /*查询所有的审核工作项*/
    public List<WorkAudit> queryAllAuditByDetail(List<WorkAuditDetail> workAuditDetailList);
    /*查询审核明细*/
    public WorkAuditDetail queryWorkAuditDetailById(Integer id);
    /*删除审核的工作*/
    public int deleteAudit(Integer id);
    /*删除对应的审核明细*/
    public int deleteAuditDetail(Integer id);
    public int updateAuditStatusById(Integer id);
    /*审核通过添加原工作项id*/
    public int addOriginWorkId(List<WorkAudit> workAuditList);
    /*修改审核明细状态为未修改*/
    public int updateAuditDetail(Integer id);
    /*修改审核详情*/
    public int updateAudit(WorkAudit workAudit);
}
