package logistics.work.ctrls;

import logistics.work.common.Constants;
import logistics.work.common.ParamUtils;
import logistics.work.common.Result;
import logistics.work.models.domain.User;
import logistics.work.models.domain.WorkAuditDetail;
import logistics.work.models.dto.WorkAuditDetailDto;
import logistics.work.services.WorkAuditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/work/audit")
public class AuditCtrl extends BaseCtrl{
    @Autowired
    private WorkAuditService workAuditService;

    /**
     * 领导查询部门所有未审核的工作
     * @param workName
     * @param pageNumber
     * @param pageSize
     * @param employeeId
     * @param session
     * @return
     */
    @GetMapping("/leader/queryUnAuditedWork")
    public Result queryUnAuditedWork(@RequestParam(value="workName",required = false)String workName,
                                     @RequestParam(value="pageNumber", required = false)Integer pageNumber,
                                     @RequestParam(value = "pageSize",required = false)Integer pageSize,
                                     @RequestParam(value="employeeId",required = false)Integer employeeId,
                                     HttpSession session){
        User user= (User) session.getAttribute(Constants.userSession);
        Map<String,Object> params=ParamUtils.setPageInfo(pageNumber,pageSize);
        params.put("workName",workName);
        params.put("userId",user.getId());
        params.put("employeeId",employeeId);
        return this.send(workAuditService.queryDeptAllAuditInf(params));
    }

    /**
     * 审核员工的工作
     * @param workAuditDetailDto
     * @param session
     * @return
     */
    @PostMapping("/leader/auditWork")
    public Result auditWork(WorkAuditDetailDto workAuditDetailDto,
                            HttpSession session){
        User user=(User)session.getAttribute(Constants.userSession);
        try{
            if(workAuditDetailDto.getWorkAuditDetailList().get(0).getAuditStatus().equals("disagree")) {
                return this.send(workAuditService.disagreeAuditWork(workAuditDetailDto.getWorkAuditDetailList(),user.getId()));
            }
            return this.send(workAuditService.agreeAuditWork(workAuditDetailDto.getWorkAuditDetailList(),user.getId()));
        }catch(Exception e){
            return this.send(-1,"操作失败");
        }
    }
}
