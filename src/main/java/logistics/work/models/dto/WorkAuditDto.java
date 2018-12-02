package logistics.work.models.dto;

import logistics.work.models.domain.WorkAuditDetail;

import javax.validation.Valid;
import java.util.List;

/**
 * 批量提交审核
 */
public class WorkAuditDto {
    @Valid
    private List<WorkAuditDetail> workAuditDetails;

    public List<WorkAuditDetail> getWorkAuditDetails() {
        return workAuditDetails;
    }

    public void setWorkAuditDetails(List<WorkAuditDetail> workAuditDetails) {
        this.workAuditDetails = workAuditDetails;
    }
}

