package logistics.work.services;

import logistics.work.common.FileUtils;
import logistics.work.models.dao.UserDao;
import logistics.work.models.dao.WorkDao;
import logistics.work.models.dao.WorkScheduleDao;
import logistics.work.models.domain.Employee;
import logistics.work.models.domain.WorkPool;
import logistics.work.models.dto.DeptDto;
import logistics.work.models.dto.ShowWorkAuditConditionDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import java.time.temporal.Temporal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Service
public class WorkService {
    @Autowired
    private WorkDao workDao;
    @Autowired
    private WorkScheduleDao workScheduleDao;
    @Autowired
    private UserDao userDao;

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
     * 查询员工的常规工作项
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

    /**
     * 直接添加工作项（无需审核）
     * @param workPoolList
     * @return
     */
    @Transactional
    public int addWork(List<WorkPool> workPoolList) throws Exception {
        for(WorkPool workPool:workPoolList){
            String instructor = null;
            if (workPool.getFile() != null) {
                instructor = FileUtils.upload(workPool.getFile());
            }
            workPool.setWorkInstructor(instructor);
        }
        return workDao.addWork(workPoolList);
    }

    /**
     * 更改工作项（无需审核）
     * @param workPool
     * @return
     */
    @Transactional
    public int updateWork(@Valid WorkPool workPool) throws Exception {
        String instructor = null;
        if (workPool.getFile() != null) {
            instructor = FileUtils.upload(workPool.getFile());
        }
        workPool.setWorkInstructor(instructor);
        return workDao.updateWork(workPool);
    }

}
