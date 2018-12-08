package logistics.work.services;

import logistics.work.models.dao.WorkDao;
import logistics.work.models.domain.WorkPool;
import logistics.work.models.dto.ShowWorkAuditConditionDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Service
public class WorkService {
    @Autowired
    private WorkDao workDao;
    public Map<String,Object> queryWorkByWorkName(Map<String,Object> params){
        List<ShowWorkAuditConditionDto> showWorkAuditConditionDtoList=workDao.queryWorkByPage(params);
        Map<String,Object> result=new HashMap<>();
        result.put("data",showWorkAuditConditionDtoList);
        result.put("total",showWorkAuditConditionDtoList.size());
        result.put("all",workDao.queryWorkCount(params));
        return result;
    }
    public Map<String,Object> queryWorkPool(Map<String,Object> params){
        List<WorkPool> workPoolList=workDao.queryWorkPoolByPage(params);
        Map<String,Object> result=new HashMap<>();
        result.put("data",workPoolList);
        result.put("total",workPoolList.size());
        result.put("all",workDao.queryWorkPoolCount(params));
        return result;
    }
}
