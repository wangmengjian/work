package logistics.work.models.dao;


import logistics.work.models.domain.WorkPool;
import logistics.work.models.dto.ShowWorkAuditConditionDto;

import java.util.List;
import java.util.Map;

public interface WorkDao {
    /*查询所有提交的工作*/
    public List<ShowWorkAuditConditionDto> queryWorkByPage(Map<String,Object> params);
    public Integer queryWorkCount(Map<String,Object> params);
    public List<WorkPool> queryWorkPoolByPage(Map<String,Object> params);
    public Integer queryWorkPoolCount(Map<String,Object> params);
}
