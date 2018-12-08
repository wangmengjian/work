package logistics.work.models.dto;

import logistics.work.models.domain.WorkAudit;

import javax.validation.Valid;
import java.util.List;

/**
 * 批量提交审核
 */
public class WorkAuditDto {
    @Valid
    private List<WorkAudit> workAudits;

    public List<WorkAudit> getWorkAudits() {
        return workAudits;
    }

    public void setWorkAudits(List<WorkAudit> workAudits) {
        this.workAudits = workAudits;
    }
}

