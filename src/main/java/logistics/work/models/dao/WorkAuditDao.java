package logistics.work.models.dao;

import logistics.work.models.domain.WorkAudit;
import logistics.work.models.domain.WorkAuditDetail;

import java.util.List;

public interface WorkAuditDao {
    /*添加审核明细*/
    public int addWorkAuditDetail(List<WorkAuditDetail> workAuditDetailList);
    /*添加审核记录*/
    public int addWorkAudit(List<WorkAudit> workAuditList);
}
