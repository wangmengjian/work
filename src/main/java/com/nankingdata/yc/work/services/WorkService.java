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
import com.nankingdata.yc.work.models.dto.WorkScheduleDetailDto;
import com.nankingdata.yc.work.models.dto.WorkScheduleDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
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
    private WorkAuditDao workAuditDao;
    /**
     * 查询员工提交的所有工作项的审核记录
     * @param params
     * @return
     */
    public Map<String,Object> queryWork(Map<String,Object> params){
        List<ShowWorkAuditConditionDto> showWorkAuditConditionDtoList=workDao.querySubmitRecordByPage(params);
        Map<String,Object> result=new HashMap<>();
        result.put("data",showWorkAuditConditionDtoList);
        result.put("total",showWorkAuditConditionDtoList.size());
        result.put("all",workDao.querySubmitRecordCount(params));
        return result;
    }

    /**
     * 员工根据工作项id查询工作
     * @param id
     * @return
     */
    public Map<String,Object> queryWorkById(Integer id){
        Map<String,Object> result=new HashMap<>();
        result.put("data",workDao.queryWorkById(id));
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
     * 人事新增员工常规工作项（无需审核）
     * @param workPoolList
     * @return
     */
    @Transactional
    public int personnelAddWork(List<WorkPool> workPoolList) throws Exception {
        for(WorkPool workPool:workPoolList){
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
    @Transactional
    public int allotWork(List<WorkPool> workPoolList){
        if(workPoolList==null||workPoolList.size()<=0){
            return 0;
        }
        for(WorkPool workPool:workPoolList){
            workPool.setWorkFrom("w3");
        }
        Integer employeeId=workPoolList.get(0).getUserId();
        Integer scheduleId=workScheduleDao.queryTodayScheduleId(employeeId);
        WorkSchedule workSchedule=new WorkSchedule();
        workSchedule.setUserId(employeeId);

        if(scheduleId==null) {
            workScheduleDao.addSchedule(workSchedule);
            scheduleId = workSchedule.getId();
        }
        /*查询计划中已有的常规工作项*/
        Map<String,Object> params=new HashMap<>();
        params.put("userId",employeeId);
        params.put("scheduleId",scheduleId);
        List<WorkScheduleDetailDto> workScheduleDetailDtoList=workScheduleDao.queryWorkScheduleDetail(params);
        List<WorkPool> notExistWork=new ArrayList<>();
        if(workScheduleDetailDtoList!=null&&workScheduleDetailDtoList.size()>0){
            for(WorkPool workPool:workPoolList){
                int i=0;
                for(;i<workScheduleDetailDtoList.size();i++){
                    if(workScheduleDetailDtoList.get(i).getWorkId()==workPool.getId()){
                        break;
                    }
                }
                if(i==workScheduleDetailDtoList.size()){
                    notExistWork.add(workPool);
                }
            }
        }else{
            notExistWork=workPoolList;
        }
        int result=0;
        if(notExistWork!=null&&notExistWork.size()>0) {
            result = workScheduleDao.addScheduleDetail(notExistWork, scheduleId);
        }
        return result;
    }

    /**
     * 领导新增员工的工作项
     * @param workPool
     * @return
     */
    @Transactional
    public int leaderAddWork(WorkPool workPool,Integer leaderId)throws Exception {
        if(workPool==null)return 0;
        else workPool.setAllotUserId(leaderId);
        List<WorkPool> workPoolList=new ArrayList<>();
        workPoolList.add(workPool);
        workDao.addWork(workPoolList);
        WorkSchedule workSchedule=new WorkSchedule();
        workSchedule.setUserId(workPool.getUserId());
        Integer scheduleId=workScheduleDao.queryTodayScheduleId(workPool.getUserId());
        if(scheduleId==null) {
            workScheduleDao.addSchedule(workSchedule);
            scheduleId = workSchedule.getId();
        }
        return workScheduleDao.addScheduleDetail(workPoolList,scheduleId);
    }
    /**
     * 领导给多个员工新增工作项
     * @param workPool
     * @return
     */
    @Transactional
    public int leaderAddWorkToEmployees(WorkPool workPool){
        Integer[] employeeIds=workPool.getEmployeeIds();
        if(workPool==null||employeeIds==null||employeeIds.length<=0)return 0;
        List<WorkPool> workPoolList=new ArrayList<>();
        for(Integer employeeId:employeeIds){
            WorkPool workPool1=new WorkPool();
            workPool1.setWorkName(workPool.getWorkName());
            workPool1.setWorkContent(workPool.getWorkContent());
            workPool1.setWorkInstructor(workPool.getWorkInstructor());
            workPool1.setWorkMinutes(workPool.getWorkMinutes());
            workPool1.setWorkFrom(workPool.getWorkFrom());
            workPool1.setUserId(employeeId);
            workPoolList.add(workPool1);
        }
        workDao.addWork(workPoolList);
        WorkSchedule workSchedule=new WorkSchedule();
        workSchedule.setUserId(workPool.getUserId());
        Integer scheduleId=workScheduleDao.queryTodayScheduleId(workPool.getUserId());
        if(scheduleId==null) {
            workScheduleDao.addSchedule(workSchedule);
            scheduleId = workSchedule.getId();
        }
        return workScheduleDao.addScheduleDetail(workPoolList,scheduleId);
    }
    /**
     * 删除员工常规工作项
     * @param workId
     * @return
     */
    @Transactional
    public int deleteWork(Integer workId){
        return workDao.deleteWork(workId);
    }

    /**
     * 领导查询员工未添加到工作计划中的工作
     * @param params
     * @return
     */
    public Map<String,Object> leaderQueryUnAddWork(Map<String,Object> params){
        Integer userId= (Integer) params.get("employeeId");
        params.put("userId",userId);
        Integer scheduleId=workScheduleDao.queryTodayScheduleId(userId);
        params.put("scheduleId",scheduleId);
        List<WorkPool> workPoolList=workDao.queryUnAddWork(params);
        Map<String,Object> result=new HashMap<>();
        result.put("total",workPoolList.size());
        result.put("data",workPoolList);
        return result;
    }

    /**
     * 领导批量给员工分配工作
     * @param params
     * @return
     */
    @Transactional
    public int leaderAllotWorks(Map<String,Object> params){
        Integer employeeId= (Integer) params.get("employeeId");
        Integer[] workIds= (Integer[]) params.get("workIds");
        if(workIds==null||workIds.length<=0)return 0;
        Integer scheduleId=workScheduleDao.queryTodayScheduleId(employeeId);
        WorkSchedule workSchedule=new WorkSchedule();
        workSchedule.setUserId(employeeId);
        if(scheduleId==null) {
            workScheduleDao.addSchedule(workSchedule);
            scheduleId = workSchedule.getId();
        }
        List<WorkPool> workPoolList=new ArrayList<>();
        for(Integer workId:workIds){
            WorkPool workPool=new WorkPool();
            workPool.setId(workId);
            workPoolList.add(workPool);
        }
        List<WorkPool> addWorkPoolList=workDao.queryWorkPool(workPoolList);
        return workScheduleDao.addScheduleDetail(addWorkPoolList,scheduleId);
    }
}
