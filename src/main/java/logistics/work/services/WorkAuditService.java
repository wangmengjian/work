package logistics.work.services;

import logistics.work.common.FileUtils;
import logistics.work.models.dao.WorkAuditDao;
import logistics.work.models.domain.User;
import logistics.work.models.domain.WorkAudit;
import logistics.work.models.domain.WorkAuditDetail;
import logistics.work.models.dto.WorkAuditDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class WorkAuditService {
    @Autowired
    private WorkAuditDao workAuditDao;
    /**
     * 提交审核
     * @param user 提交人信息
     * @param workAuditDto 提交的多条记录
     * @return
     */
    @Transactional
    public int submitAuditDetail(User user, WorkAuditDto workAuditDto) throws Exception {
        List<WorkAuditDetail> workAuditDetailList=workAuditDto.getWorkAuditDetails();
        for(WorkAuditDetail workAuditDetail:workAuditDetailList){
            String instructor=null;
            if(workAuditDetail.getFile()!=null) {
                instructor = FileUtils.upload(workAuditDetail.getFile());
            }
            workAuditDetail.setWorkUserId(user.getId());
            workAuditDetail.setWorkInstructor(instructor);
        }
        workAuditDao.addWorkAuditDetail(workAuditDto.getWorkAuditDetails());
        Integer max=workAuditDao.queryMaxIdByWorkUserId(user.getId());
        int beginIndex=1;
        if(max!=null) {
            beginIndex = max - workAuditDetailList.size() + 1;
        }
        List<WorkAudit> workAuditList=new ArrayList<>();
        for (int i=0;i<workAuditDetailList.size();i++){
            WorkAudit workAudit=new WorkAudit();
            workAudit.setAuditDetailId(beginIndex+i);
            workAudit.setAuditSubmitterId(user.getId());
            Date d=new Date();
            Timestamp timeStamep = new Timestamp(d.getTime());
            workAudit.setAuditSubmitTime(timeStamep);
            workAuditList.add(workAudit);
        }
        System.out.println(workAuditList);
        workAuditDao.addWorkAudit(workAuditList);
        return workAuditList.size();
    }
}
