package logistics.work.ctrls;

import logistics.work.common.Constants;
import logistics.work.common.ParamUtils;
import logistics.work.common.Result;
import logistics.work.models.domain.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.Map;

@RestController
@RequestMapping("/api/work/audit")
public class AuditCtrl extends BaseCtrl{
    @GetMapping("/leader/queryUnAuditedWork")
    public Result queryUnAuditedWork(@RequestParam(value="workName",required = false)String workName,
                                     @RequestParam(value="pageNumber", required = false)Integer pageNumber,
                                     @RequestParam(value = "pageSize",required = false)Integer pageSize,
                                     HttpSession session){
        User user= (User) session.getAttribute(Constants.userSession);
        Map<String,Object> params=ParamUtils.setPageInfo(pageNumber,pageSize);
        params.put("workName",workName);
        params.put("userId",user.getId());
        return null;
    }
}
