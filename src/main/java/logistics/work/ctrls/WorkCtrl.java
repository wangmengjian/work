package logistics.work.ctrls;

import logistics.work.common.FileUtils;
import logistics.work.common.Result;
import logistics.work.models.domain.User;
import logistics.work.models.domain.WorkAuditDetail;
import logistics.work.models.dto.WorkAuditDto;
import logistics.work.services.WorkAuditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;


@RestController
@RequestMapping("/api/work/schedule")
public class WorkCtrl extends BaseCtrl {
    @Autowired
    private WorkAuditService workAuditService;
    @PostMapping("/work")
    public Result addGeneralWork(@Valid WorkAuditDto workAuditDto,HttpSession session){
        User user= (User) session.getAttribute("USER_SESSION");
        try{
            return this.send(workAuditService.audit(user,workAuditDto));
        }catch(Exception e){
            return this.send(-1,"操作失败");
        }
    }
}
