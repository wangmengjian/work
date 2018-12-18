package com.nankingdata.yc.work.models.dao;

import com.nankingdata.yc.work.models.domain.WorkAudit;
import com.nankingdata.yc.work.models.domain.WorkAuditDetail;
import com.nankingdata.yc.work.models.dto.ShowDeptAllAuditInf;

import java.util.List;
import java.util.Map;

public interface WorkAuditDao {
    /*添加审核明细*/
    public int addWorkAuditDetail(List<WorkAuditDetail> workAuditDetailList);
    /*添加审核记录*/
    public int addWorkAudit(List<WorkAudit> workAuditList);
    /*分页查询部门所有审核详情*/
    public List<ShowDeptAllAuditInf> queryDeptAllAuditInfByPage(Map<String,Object> params);
    /*查询部门所有审核详情的数量*/
    public Integer queryDeptAllAuditInfCount(Map<String,Object> params);
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
}
