package com.nankingdata.yc.work.services;

import com.nankingdata.yc.work.common.FileUtils;
import com.nankingdata.yc.work.models.domain.WorkAudit;
import com.nankingdata.yc.work.models.domain.WorkSchedule;
import com.nankingdata.yc.work.models.dao.UserDao;
import com.nankingdata.yc.work.models.dao.WorkAuditDao;
import com.nankingdata.yc.work.models.dao.WorkDao;
import com.nankingdata.yc.work.models.dao.WorkScheduleDao;
import com.nankingdata.yc.work.models.domain.WorkAuditDetail;
import com.nankingdata.yc.work.models.domain.WorkPool;
import com.nankingdata.yc.work.models.dto.ShowDeptAllAuditInf;
import com.nankingdata.yc.work.models.dto.UpdateWorkDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class WorkAuditService {
    @Autowired
    private WorkAuditDao workAuditDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private WorkDao workDao;
    @Autowired
    private WorkScheduleDao workScheduleDao;

    /**
     * 提交审核
     * @param params
     * @return
     * @throws Exception
     */
    @Transactional
    public int submitAudit(Map<String,Object> params) throws Exception {
        List<WorkAudit> workAuditList= (List<WorkAudit>) params.get("workAuditList");
        Integer userId= (Integer) params.get("userId");
        if(workAuditList!=null&&workAuditList.size()>0) {
            for (WorkAudit workAudit : workAuditList) {
                workAudit.setWorkUserId(userId);
            }
        }
        workAuditDao.addWorkAudit(workAuditList);
        List<WorkAuditDetail> workAuditDetailList=new ArrayList<>();
        if(workAuditList!=null&&workAuditList.size()>0) {
            for (WorkAudit workAudit : workAuditList) {
                WorkAuditDetail workAuditDetail = new WorkAuditDetail();
                workAuditDetail.setAuditItemId(workAudit.getId());
                workAuditDetailList.add(workAuditDetail);
            }
        }
        workAuditDao.addWorkAuditDetail(workAuditDetailList);
        return workAuditList.size();
    }

    /**
     * 查询部门审核工作
     * @param params
     * @return
     */
    public Map<String,Object> queryDeptAllAuditInf(Map<String,Object> params){
        Integer auditStatus= (Integer) params.get("auditStatus");
        List<ShowDeptAllAuditInf> showDeptAllAuditInfList=null;
        Integer all=0;
        if(auditStatus==0){
            //查询部门未审核工作
            showDeptAllAuditInfList=workAuditDao.queryDeptAllUnauditedWorkByPage(params);
            all=workAuditDao.queryDeptAllUnauditedWorkCount(params);
        }
        else{
            //领导个人的审核记录
            showDeptAllAuditInfList=workAuditDao.queryAuditedRecord(params);
            all=workAuditDao.queryAuditedRecordCount(params);
        }
        Map<String,Object> result=new HashMap<>();
        result.put("data",showDeptAllAuditInfList);
        result.put("total",showDeptAllAuditInfList.size());
        result.put("all",all);
        return result;
    }

    /**
     * 审核通过员工工作项
     * @param workAuditDetails 提交的审核明细
     * @param userId 审核人id
     * @return
     */
    @Transactional
    public int agreeAuditWork(List<WorkAuditDetail> workAuditDetails,Integer userId){
        if(workAuditDetails.size()<=0)return 0;
        List<WorkPool> addWorkPool=new ArrayList<>();//需要添加到工作池中的工作项
        List<WorkPool> updateWorkPool=new ArrayList<>();//需要更改的工作项
        List<WorkAudit> workAuditList=workAuditDao.queryAllAuditByDetail(workAuditDetails);//查询部门员工所有的审核
        if(workAuditList!=null&&workAuditList.size()>0) {
            /*遍历领导选择的所有的审核记录，将需要添加的和需要更新的分离*/
            for (WorkAudit workAudit : workAuditList) {
                WorkPool workPool = new WorkPool();
                workPool.setUserId(workAudit.getWorkUserId());
                workPool.setWorkName(workAudit.getWorkName());
                workPool.setWorkContent(workAudit.getWorkContent());
                workPool.setWorkInstructor(workAudit.getWorkInstructor());
                workPool.setWorkFrom(workAudit.getWorkFrom());
                workPool.setWorkMinutes(workAudit.getWorkMinutes());
                workPool.setAuditRecordId(workAudit.getId());
                if (workAudit.getOriginWorkId() != null) {
                    workPool.setId(workAudit.getOriginWorkId());
                    updateWorkPool.add(workPool);
                } else {
                    addWorkPool.add(workPool);
                }
            }
        }
        if(addWorkPool.size()>0) {
            workDao.addAgreeWork(addWorkPool);
            /*新增的工作绑定到对应的审核详情*/
            for(WorkPool workPool:addWorkPool){
                for(WorkAudit workAudit:workAuditList){
                    if(workPool.getAuditRecordId().equals(workAudit.getId())){
                        workAudit.setOriginWorkId(workPool.getId());
                    }
                }
            }
            workAuditDao.addOriginWorkId(workAuditList);
        }
        if(updateWorkPool.size()>0) {

            workDao.updateAgreeWork(updateWorkPool);
        }
        /*更改记录的审核状态*/
        List<WorkAuditDetail> workAuditDetailList=new ArrayList<>();
        if(workAuditList!=null&&workAuditList.size()>0) {
            for (WorkAudit workAudit : workAuditList) {
                WorkAuditDetail workAuditDetail = new WorkAuditDetail();
                workAuditDetail.setAuditItemId(workAudit.getId());
                workAuditDetail.setAuditStatus("agree");
                workAuditDetail.setAuditUserId(userId);
                workAuditDetailList.add(workAuditDetail);
            }
        }
        workAuditDao.updateAuditStatus(workAuditDetailList);

        //遍历临时工作项，直接添加到当日工作计划中
        Map<Integer,List<WorkPool>> temporary=new HashMap<>();
        if(addWorkPool!=null&&addWorkPool.size()>0) {
            for (WorkPool workPool : addWorkPool) {
                if (workPool.getWorkFrom().equals("w3")) continue;
                List<WorkPool> idList = temporary.get(workPool.getUserId());
                if (idList == null) {
                    idList = new ArrayList<>();
                    temporary.put(workPool.getUserId(), idList);
                }
                idList.add(workPool);
            }
        }
        /*临时工作项添加时，需要判断是否存在日计划*/
        if(temporary.size()>0) {
            for (Integer id : temporary.keySet()) {
                Integer scheduleId = workScheduleDao.queryTodayScheduleId(id);
                WorkSchedule workSchedule = new WorkSchedule();
                workSchedule.setUserId(id);
                if (scheduleId == null) {
                    workScheduleDao.addSchedule(workSchedule);
                    scheduleId = workSchedule.getId();
                }
                workScheduleDao.addScheduleDetail(temporary.get(id), scheduleId);
            }
        }
        return workAuditList.size();
    }

    /**
     * 审核不通过员工的工作项
     * @param workAuditDetailList
     * @param userId
     * @return
     */
    @Transactional
    public int disagreeAuditWork(List<WorkAuditDetail> workAuditDetailList,Integer userId){
        if(workAuditDetailList.size()<=0){
            return 0;
        }
        for(WorkAuditDetail workAuditDetail:workAuditDetailList){
            workAuditDetail.setAuditUserId(userId);
        }
        workAuditDao.updateAuditStatus(workAuditDetailList);
        List<WorkAudit> workAuditList=workAuditDao.queryAllAuditByDetail(workAuditDetailList);
        List<WorkPool> workPoolList=new ArrayList<>();
        for(WorkAudit workAudit:workAuditList){
            if(workAudit.getOriginWorkId()!=null){
                WorkPool workPool=new WorkPool();
                workPool.setAuditStatus("agree");
                workPool.setId(workAudit.getOriginWorkId());
                workPoolList.add(workPool);
            }
        }
        workDao.updateWorkAuditStatus(workPoolList);
        return workAuditDetailList.size();
    }

    /**
     * 员工取消申请
     * @param id
     * @return
     */
    @Transactional
    public int employeeCancelAudit(Integer id){
        return workAuditDao.updateAuditStatusById(id);
    }

    /**
     * 员工修改工作项
     * @param updateWorkDto
     * @return
     */
    @Transactional
    public int employeeUpdateWork(UpdateWorkDto updateWorkDto){
        /*修改工作项审核明细*/
        workAuditDao.updateAuditDetail(updateWorkDto.getId());
        WorkAuditDetail workAuditDetail=workAuditDao.queryWorkAuditDetailById(updateWorkDto.getId());
        WorkAudit workAudit=new WorkAudit();
        workAudit.setWorkName(updateWorkDto.getWorkName());
        workAudit.setWorkContent(updateWorkDto.getWorkContent());
        workAudit.setWorkInstructor(updateWorkDto.getWorkInstructor());
        workAudit.setWorkMinutes(updateWorkDto.getWorkMinutes());
        workAudit.setWorkFrom(updateWorkDto.getWorkFrom());
        workAudit.setId(workAuditDetail.getAuditItemId());
        WorkPool workPool=new WorkPool();
        workPool.setId(updateWorkDto.getOriginWorkId());
        workPool.setAuditStatus("unaudited");
        List<WorkPool> workPoolList=new ArrayList<>();
        workPoolList.add(workPool);
        workDao.updateWorkAuditStatus(workPoolList);
        /*修改工作项审核详情*/
        return workAuditDao.updateAudit(workAudit);
    }
}
