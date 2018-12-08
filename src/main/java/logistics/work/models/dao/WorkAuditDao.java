package logistics.work.models.dao;

import logistics.work.models.domain.WorkAudit;
import logistics.work.models.domain.WorkAuditDetail;
import logistics.work.models.dto.ShowDeptAllAuditInf;

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
}
