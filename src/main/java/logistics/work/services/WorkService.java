package logistics.work.services;

import logistics.work.models.dao.WorkDao;
import logistics.work.models.dto.ShowWorkAuditConditionDto;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

public class WorkService {
    @Autowired
    private WorkDao workDao;
    public Map<String,Object> queryWorkByWorkName(Map<String,Object> params){
        List<ShowWorkAuditConditionDto> showWorkAuditConditionDtoList=workDao.queryWork(params);
        return null;
    }
}
