package logistics.work.models.dao;


import logistics.work.models.dto.ShowWorkAuditConditionDto;

import java.util.List;
import java.util.Map;

public interface WorkDao {
    /*查询所有提交的工作*/
    public List<ShowWorkAuditConditionDto> queryWork(Map<String,Object> params);
}
