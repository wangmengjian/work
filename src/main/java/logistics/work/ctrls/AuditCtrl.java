package logistics.work.ctrls;

import logistics.work.common.Constants;
import logistics.work.common.ParamUtils;
import logistics.work.common.Result;
import logistics.work.models.domain.User;
import logistics.work.models.dto.ShowDeptAllAuditInf;
import logistics.work.services.WorkAuditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/work/audit")
public class AuditCtrl extends BaseCtrl{
    @Autowired
    private WorkAuditService workAuditService;
    @GetMapping("/leader/queryUnAuditedWork")
    public Result queryUnAuditedWork(@RequestParam(value="workName",required = false)String workName,
                                     @RequestParam(value="pageNumber", required = false)Integer pageNumber,
                                     @RequestParam(value = "pageSize",required = false)Integer pageSize,
                                     HttpSession session){
        User user= (User) session.getAttribute(Constants.userSession);
        Map<String,Object> params=ParamUtils.setPageInfo(pageNumber,pageSize);
        params.put("workName",workName);
        params.put("userId",user.getId());
        return this.send(workAuditService.queryDeptAllAuditInf(params));
    }
    @PostMapping("/leader/agreeAuditWork")
    public Result agreeAuditWork(@RequestBody List<ShowDeptAllAuditInf> showDeptAllAuditInfList,HttpSession session){
        User user=(User)session.getAttribute(Constants.userSession);
        try{
            return this.send(workAuditService.agreeAuditWork(showDeptAllAuditInfList,user.getId()));
        }catch (Exception e){
            return this.send(-1,"操作失败");
        }
    }
    @PostMapping("/leader/disagreeAuditWork")
    public Result disagreeAuditWork(@RequestBody List<ShowDeptAllAuditInf> showDeptAllAuditInfList,HttpSession session){
        User user=(User)session.getAttribute(Constants.userSession);
        try{
            return this.send(workAuditService.disagreeAuditWork(showDeptAllAuditInfList,user.getId()));
        }catch (Exception e){
            return this.send(-1,"操作失败");
        }
    }
}
