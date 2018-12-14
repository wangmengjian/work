package logistics.work.services;

import logistics.work.common.FileUtils;
import logistics.work.models.dao.UserDao;
import logistics.work.models.dao.WorkAuditDao;
import logistics.work.models.dao.WorkDao;
import logistics.work.models.domain.WorkAudit;
import logistics.work.models.domain.WorkAuditDetail;
import logistics.work.models.domain.WorkPool;
import logistics.work.models.dto.ShowDeptAllAuditInf;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.*;

@Service
public class WorkAuditService {
    @Autowired
    private WorkAuditDao workAuditDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private WorkDao workDao;
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
                String instructor = null;
                if (workAudit.getFile() != null) {
                    instructor = FileUtils.upload(workAudit.getFile());
                }
                workAudit.setWorkUserId(userId);
                workAudit.setWorkInstructor(instructor);
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
     * 查询本部门未审核的工作
     * @param params
     * @return
     */
    public Map<String,Object> queryDeptAllAuditInf(Map<String,Object> params){
        String deptNumber=userDao.queryDeptNumberByUserId((Integer) params.get("userId"));
        params.put("deptNumber",deptNumber);
        List<ShowDeptAllAuditInf> showDeptAllAuditInfList=workAuditDao.queryDeptAllAuditInfByPage(params);
        Map<String,Object> result=new HashMap<>();
        result.put("data",showDeptAllAuditInfList);
        result.put("total",showDeptAllAuditInfList.size());
        result.put("all",workAuditDao.queryDeptAllAuditInfCount(params));
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
        List<WorkPool> addWorkPool=new ArrayList<>();
        List<WorkPool> updateWorkPool=new ArrayList<>();
        List<WorkAudit> workAuditList=workAuditDao.queryAllAuditByDetail(workAuditDetails);
        for(WorkAudit workAudit:workAuditList){
            WorkPool workPool=new WorkPool();
            workPool.setUserId(workAudit.getWorkUserId());
            workPool.setWorkName(workAudit.getWorkName());
            workPool.setWorkContent(workAudit.getWorkContent());
            workPool.setWorkInstructor(workAudit.getWorkInstructor());
            workPool.setWorkFrom(workAudit.getWorkFrom());
            workPool.setWorkMinutes(workAudit.getWorkMinutes());
            if(workAudit.getOriginWorkId()!=null){
                workPool.setId(workAudit.getOriginWorkId());
                updateWorkPool.add(workPool);
            }
            else{
                addWorkPool.add(workPool);
            }
        }
        if(addWorkPool.size()>0) {
            workDao.addAgreeWork(addWorkPool);
        }
        if(updateWorkPool.size()>0) {
            workDao.updateAgreeWork(updateWorkPool);
        }
        /*更改审核记录的完成状态*/
        List<WorkAuditDetail> workAuditDetailList=new ArrayList<>();
        for(WorkAudit workAudit:workAuditList){
            WorkAuditDetail workAuditDetail=new WorkAuditDetail();
            workAuditDetail.setAuditItemId(workAudit.getId());
            workAuditDetail.setAuditStatus("agree");
            workAuditDetail.setAuditUserId(userId);
            workAuditDetailList.add(workAuditDetail);
        }
        workAuditDao.updateAuditStatus(workAuditDetailList);
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
        return workAuditDetailList.size();
    }
}
