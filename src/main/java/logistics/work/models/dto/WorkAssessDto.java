package logistics.work.models.dto;

import logistics.work.models.domain.WorkAssess;

import java.util.List;

/**
 * 领导考核工作
 */
public class WorkAssessDto {
    private List<WorkAssess> workAssessList;

    public List<WorkAssess> getWorkAssessList() {
        return workAssessList;
    }

    public void setWorkAssessList(List<WorkAssess> workAssessList) {
        this.workAssessList = workAssessList;
    }
}
