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
        return result;
    }

    /**
     * 审核员工工作项
     * @param showDeptAllAuditInfList
     * @return
     */
    @Transactional
    public int auditWork(List<ShowDeptAllAuditInf> showDeptAllAuditInfList){
        List<WorkPool> addWorkPool=new ArrayList<>();
        List<WorkPool> updateWorkPool=new ArrayList<>();
        for(ShowDeptAllAuditInf showDeptAllAuditInf:showDeptAllAuditInfList){
            WorkPool workPool=new WorkPool();
            workPool.setUseId(showDeptAllAuditInf.getSubmitterId());
            workPool.setWorkName(showDeptAllAuditInf.getWorkName());
            workPool.setWorkContent(showDeptAllAuditInf.getWorkContent());
            workPool.setWorkInstructor(showDeptAllAuditInf.getWorkInstructor());
            workPool.setWorkFrom(showDeptAllAuditInf.getFromCode());
            workPool.setWorkMinutes(showDeptAllAuditInf.getWorkMinutes());
            if(showDeptAllAuditInf.getOriginWorkId()==null){
                addWorkPool.add(workPool);
            }else{
                updateWorkPool.add(workPool);
            }
        }
        workDao.addAgreeWork(addWorkPool);
        workDao.updateAgreeWork(updateWorkPool);
        return showDeptAllAuditInfList.size();
    }
}
