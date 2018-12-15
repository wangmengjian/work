package logistics.work.models.dao;

import logistics.work.models.domain.WorkAssess;
import logistics.work.models.dto.ShowAssessDto;

import java.util.List;
import java.util.Map;

public interface AssessDao {
    /*领导按分页查询所有待考核工作*/
    public List<ShowAssessDto> queryAllAssess(Map<String,Object> params);
    /*领导查询所有的待考核工作总数*/
    public int queryAllAssessCount(Map<String,Object> params);
    /*领导考核工作*/
    public int assessWork(List<WorkAssess> workAssessList);
    public List<ShowAssessDto> employeeQueryAllAssess(Map<String,Object> params);
    public int employeeQueryAllAssessCount(Map<String,Object> params);
}
