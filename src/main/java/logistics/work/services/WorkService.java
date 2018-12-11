package logistics.work.services;

import logistics.work.models.dao.WorkDao;
import logistics.work.models.dao.WorkScheduleDao;
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
    @Autowired
    private WorkScheduleDao workScheduleDao;

    /**
     * 查询员工提交的所有工作项的审核记录
     * @param params
     * @return
     */
    public Map<String,Object> queryWorkByWorkName(Map<String,Object> params){
        List<ShowWorkAuditConditionDto> showWorkAuditConditionDtoList=workDao.querySubmitRecordByPage(params);
        Map<String,Object> result=new HashMap<>();
        result.put("data",showWorkAuditConditionDtoList);
        result.put("total",showWorkAuditConditionDtoList.size());
        result.put("all",workDao.querySubmitRecordCount(params));
        return result;
    }

    /**
     * 员工查询未添加到日计划里的工作
     * @param params
     * @return
     */
    public Map<String,Object> queryUnAddWork(Map<String,Object> params){
        Integer scheduleId=workScheduleDao.queryTodayScheduleId((Integer)params.get("userId"));
        params.put("scheduleId",scheduleId);
        List<WorkPool> workPoolList=workDao.queryUnAddWork(params);
        Map<String,Object> result=new HashMap<>();
        result.put("data",workPoolList);
        result.put("total",workPoolList.size());
        return result;
    }

    /**
     * 部门领导查询员工的常规工作项
     * @param params
     * @return
     */
    public Map<String,Object> queryWorkByEmployeeId(Map<String,Object> params){
        Map<String,Object> result=new HashMap<>();
        List<WorkPool> workPoolList=workDao.queryWorkByEmployeeId(params);
        result.put("data",workPoolList);
        result.put("total",workPoolList.size());
        result.put("all",workDao.queryWorkCountByEmployeeId(params));
        return result;
    }
}
