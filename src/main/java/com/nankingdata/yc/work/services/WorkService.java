package com.nankingdata.yc.work.services;

import com.nankingdata.yc.work.common.FileUtils;
import com.nankingdata.yc.work.models.dao.UserDao;
import com.nankingdata.yc.work.models.dao.WorkAuditDao;
import com.nankingdata.yc.work.models.dao.WorkDao;
import com.nankingdata.yc.work.models.dao.WorkScheduleDao;
import com.nankingdata.yc.work.models.domain.WorkAuditDetail;
import com.nankingdata.yc.work.models.domain.WorkPool;
import com.nankingdata.yc.work.models.domain.WorkSchedule;
import com.nankingdata.yc.work.models.dto.ShowWorkAuditConditionDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    @Autowired
    private WorkAuditDao workAuditDao;
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
     * 新增员工常规工作项（无需审核）
     * @param workPoolList
     * @return
     */
    @Transactional
    public int addWork(List<WorkPool> workPoolList) throws Exception {
        for(WorkPool workPool:workPoolList){
            String instructor = null;
            if (workPool.getFile() != null) {
                instructor = FileUtils.upload(workPool.getFile(),"instructor").get(0);
            }
            workPool.setWorkInstructor(instructor);
            workPool.setWorkFrom("w3");
        }
        return workDao.addWork(workPoolList);
    }

    /**
     * 更改工作项（无需审核）
     * @param workPool
     * @return
     */
    @Transactional
    public int updateWork(WorkPool workPool) throws Exception {
        String instructor = null;
        if (workPool.getFile() != null) {
            instructor = FileUtils.upload(workPool.getFile(),"instructor").get(0);
        }
        workPool.setWorkInstructor(instructor);
        return workDao.updateWork(workPool);
    }

    /**
     * 删除审核记录
     * @param id
     * @return
     */
    @Transactional
    public int deleteAuditRecord(Integer id){
        WorkAuditDetail workAuditDetail=workAuditDao.queryWorkAuditDetailById(id);
        workAuditDao.deleteAuditDetail(id);
        workAuditDao.deleteAudit(workAuditDetail.getAuditItemId());
        return 1;
    }

    /**
     * 领导分配常规工作项
     * @param workPoolList
     * @return
     */
    public int allotWork(List<WorkPool> workPoolList){
        if(workPoolList==null||workPoolList.size()<=0){
            return 0;
        }
        Integer employeeId=workPoolList.get(0).getUserId();
        Integer scheduleId=workScheduleDao.queryTodayScheduleId(employeeId);
        WorkSchedule workSchedule=new WorkSchedule();
        workSchedule.setUserId(employeeId);
        if(scheduleId==null) {
            workScheduleDao.addSchedule(workSchedule);
            scheduleId = workSchedule.getId();
        }
        int result=0;
        result=workScheduleDao.addScheduleDetail(workPoolList,scheduleId);
        return result;
    }
}
