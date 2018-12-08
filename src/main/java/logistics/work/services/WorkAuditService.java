package logistics.work.services;

import logistics.work.common.FileUtils;
import logistics.work.models.dao.WorkAuditDao;
import logistics.work.models.domain.WorkAudit;
import logistics.work.models.domain.WorkAuditDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class WorkAuditService {
    @Autowired
    private WorkAuditDao workAuditDao;

    /**
     * 提交审核
     * @param params
     * @return
     * @throws Exception
     */
    @Transactional
    public int submitAuditDetail(Map<String,Object> params) throws Exception {
        List<WorkAuditDetail> workAuditDetailList= (List<WorkAuditDetail>) params.get("workAuditDetailList");
        Integer userId= (Integer) params.get("userId");
        if(workAuditDetailList!=null&&workAuditDetailList.size()>0) {
            for (WorkAuditDetail workAuditDetail : workAuditDetailList) {
                String instructor = null;
                if (workAuditDetail.getFile() != null) {
                    instructor = FileUtils.upload(workAuditDetail.getFile());
                }
                workAuditDetail.setWorkUserId(userId);
                workAuditDetail.setWorkInstructor(instructor);
            }
        }
        workAuditDao.addWorkAuditDetail(workAuditDetailList);
        List<WorkAudit> workAuditList=new ArrayList<>();
        if(workAuditDetailList!=null&&workAuditDetailList.size()>0) {
            for (WorkAuditDetail workAuditDetail : workAuditDetailList) {
                WorkAudit workAudit = new WorkAudit();
                workAudit.setAuditDetailId(workAuditDetail.getId());
                workAudit.setAuditSubmitterId(userId);
                Date d = new Date();
                Timestamp timeStamep = new Timestamp(d.getTime());
                workAudit.setAuditSubmitTime(timeStamep);
                workAuditList.add(workAudit);
            }
        }
        workAuditDao.addWorkAudit(workAuditList);
        return workAuditList.size();
    }
}
