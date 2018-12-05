package logistics.work.ctrls;

import logistics.work.common.Constants;
import logistics.work.common.Result;
import logistics.work.models.domain.User;
import logistics.work.models.domain.WorkAuditDetail;
import logistics.work.models.dto.WorkAuditDto;
import logistics.work.models.dto.WorkScheduleDetailDto;
import logistics.work.models.dto.WorkScheduleDto;
import logistics.work.services.WorkAuditService;
import logistics.work.services.WorkScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
    @PostMapping("/employee/addWork")
    public Result employeeAddWork(@Valid WorkAuditDto workAuditDto,HttpSession session){
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
    @GetMapping("/employee/querySchedule")
    public Result employeeQueryScheduleByDate(@RequestParam(value = "date",required = false)String date,
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

    /**
     * 员工提交日计划
     * @param workScheduleDto
     * @return
     */
    @PostMapping("/employee/submitSchedule")
    public Result submitSchedule(WorkScheduleDto workScheduleDto){
        try {
            return this.send(workScheduleService.submitSchedule(workScheduleDto));
        } catch (Exception e) {
            return this.send(-1,"操作失败");
        }
    }
    /**
     * 更改工作项
     * @param workAuditDetail
     * @param session
     * @return
     */
    @PostMapping("/employee/updateWork")
    public Result employeeUpdateWork(@Valid WorkAuditDetail workAuditDetail,HttpSession session){
        User user= (User) session.getAttribute(Constants.userSession);
        WorkAuditDto workAuditDto=new WorkAuditDto();
        List<WorkAuditDetail> workAuditDetailList=new ArrayList<>();
        workAuditDetailList.add(workAuditDetail);
        workAuditDto.setWorkAuditDetails(workAuditDetailList);
        try{
            return this.send(workAuditService.submitAuditDetail(user,workAuditDto));
        }catch(Exception e){
            return this.send(-1,"操作失败");
        }
    }
    @GetMapping("/employee/queryWork")
    public Result employeeQueryWork(@RequestParam("workName")String workName){
return null;
    }
}
