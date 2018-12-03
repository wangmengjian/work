package logistics.work.ctrls;

import logistics.work.common.Constants;
import logistics.work.common.Result;
import logistics.work.models.domain.User;
import logistics.work.models.dto.WorkAuditDto;
import logistics.work.models.dto.WorkScheduleDto;
import logistics.work.services.WorkAuditService;
import logistics.work.services.WorkScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/api/work/schedule")
public class ScheduleCtrl extends BaseCtrl {
    @Autowired
    private WorkAuditService workAuditService;
    @Autowired
    private WorkScheduleService workScheduleService;
    /**
     * 员工添加工作项
     * @param workAuditDto
     * @param session
     * @return
     */
    @PostMapping("/employee/work")
    public Result addWork(@Valid WorkAuditDto workAuditDto,HttpSession session){
        User user= (User) session.getAttribute(Constants.userSession);
        try{
            return this.send(workAuditService.submitAuditDetail(user,workAuditDto));
        }catch(Exception e){
            return this.send(-1,"操作失败");
        }
    }

    /**
     * 员工根据时间查询日工作计划
     * @param date
     * @param session
     * @return
     */
    @GetMapping("/employee/work")
    public Result queryWorkByDate(@RequestParam(value = "date",required = false)String date,
                                  @RequestParam(value="workFrom",required = false)String workFrom,
                                  @RequestParam(value="workName",required = false)String workName,
                                  HttpSession session){
        User user= (User) session.getAttribute(Constants.userSession);
        Map<String,Object> params=new HashMap<>();
        params.put("userId",user.getId());
        params.put("workFrom",workFrom);
        params.put("workName",workName);
        params.put("date",date);
        Map<String,Object> result=workScheduleService.querySchedule(params);
        return this.send(result);
    }
}
