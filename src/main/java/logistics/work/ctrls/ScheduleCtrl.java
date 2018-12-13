package logistics.work.ctrls;

import logistics.work.common.Constants;
import logistics.work.common.FileUtils;
import logistics.work.common.ParamUtils;
import logistics.work.common.Result;
import logistics.work.models.domain.User;
import logistics.work.models.domain.WorkAudit;
import logistics.work.models.dto.WorkAuditDto;
import logistics.work.models.dto.WorkScheduleDto;
import logistics.work.services.WorkAuditService;
import logistics.work.services.WorkScheduleService;
import logistics.work.services.WorkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.*;
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
    @Autowired
    private WorkService workService;
    /**
     * 员工添加工作项
     * @param workAuditDto
     * @param session
     * @return
     */
    @PostMapping("/employee/addWork")
    public Result employeeAddWork(@Valid WorkAuditDto workAuditDto,HttpSession session){
        User user= (User) session.getAttribute(Constants.userSession);
        Map<String,Object> params=new HashMap<>();
        params.put("userId",user.getId());
        params.put("workAuditList",workAuditDto.getWorkAudits());
        try{
            return this.send(workAuditService.submitAudit(params));
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
                                  @RequestParam(value = "finishStatus",required = false)String finishStatus,
                                  HttpSession session){
        User user= (User) session.getAttribute(Constants.userSession);
        Map<String,Object> params=new HashMap<>();
        params.put("userId",user.getId());
        params.put("workFrom",workFrom);
        params.put("workName",workName);
        params.put("finishStatus",finishStatus);
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
    public Result submitSchedule(@Valid WorkScheduleDto workScheduleDto){
        try {
            return this.send(workScheduleService.submitSchedule(workScheduleDto));
        } catch (Exception e) {
            return this.send(-1,"操作失败");
        }
    }
    /**
     * 员工更改工作项
     * @param workAudit
     * @param session
     * @return
     */
    @PostMapping("/employee/updateWork")
    public Result employeeUpdateWork(@Valid WorkAudit workAudit, HttpSession session){
        User user= (User) session.getAttribute(Constants.userSession);
        List<WorkAudit> workAuditList =new ArrayList<>();
        workAuditList.add(workAudit);
        Map<String,Object> params=new HashMap<>();
        params.put("userId",user.getId());
        params.put("workAuditList", workAuditList);
        try{
            return this.send(workAuditService.submitAudit(params));
        }catch(Exception e){
            return this.send(-1,"操作失败");
        }
    }

    /**
     * 员工查询所有工作项（待审核，通过，不通过）
     * @param workName
     * @param pageNumber
     * @param pageSize
     * @param session
     * @return
     */
    @GetMapping("/employee/queryWork")
    public Result employeeQueryWork(@RequestParam(value="workName",required = false)String workName,
                                    @RequestParam(value="pageNumber",required = false)Integer pageNumber,
                                    @RequestParam(value = "pageSize",required = false)Integer pageSize,
                                    @RequestParam(value="auditStatus",required = false)String auditStatus,
                                    @RequestParam(value = "workFrom",required = false)String workFrom,
                                    HttpSession session){
        Map<String,Object> params=ParamUtils.setPageInfo(pageNumber,pageSize);
        params.put("workName",workName);
        User user= (User) session.getAttribute(Constants.userSession);
        params.put("userId",user.getId());
        params.put("auditStatus",auditStatus);
        params.put("workFrom",workFrom);
        return this.send(workService.queryWorkByWorkName(params));
    }


    /**
     * 员工查询常规工作池
     * @param workName
     * @param pageNumber
     * @param pageSize
     * @param session
     * @return
     */
    @GetMapping("/employee/queryWorkPool")
    public Result employeeQueryWorkPool(@RequestParam(value="workName",required = false)String workName,
                                        @RequestParam(value="pageNumber",required = false)Integer pageNumber,
                                        @RequestParam(value = "pageSize",required = false)Integer pageSize,HttpSession session){
        Map<String,Object> params=ParamUtils.setPageInfo(pageNumber,pageSize);
        params.put("workName",workName);
        User user= (User) session.getAttribute(Constants.userSession);
        params.put("userId",user.getId());
        return this.send(workService.queryUnAddWork(params));
    }

    /**
     * 生成日工作计划
     * @param idList
     * @return
     */
    @PostMapping("/employee/newSchedule")
    public Result employeeNewSchedule(@RequestBody List<Integer> idList,
                                      HttpSession session){
        User user= (User) session.getAttribute(Constants.userSession);
        Map<String,Object> params=new HashMap<>();
        params.put("idList",idList);
        params.put("userId",user.getId());
        try{
            return this.send(workScheduleService.newSchedule(params));
        }catch (Exception e){
            return this.send(-1,"操作失败");
        }
    }


    /**
     * 领导查询部门员工的常规工作项
     * @param workName
     * @param employeeId
     * @param pageNumber
     * @param pageSize
     * @return
     */
    @GetMapping("/leader/queryWork")
    public Result leaderQueryWork(@RequestParam(value="workName",required = false)String workName,
                                  @RequestParam(value="employeeId",required = false)Integer employeeId,
                                  @RequestParam(value="pageNumber", required = false)Integer pageNumber,
                                  @RequestParam(value = "pageSize",required = false)Integer pageSize){
        Map<String,Object> params=ParamUtils.setPageInfo(pageNumber,pageSize);
        params.put("workName",workName);
        params.put("employeeId",employeeId);
        try{
            return this.send(workService.queryWorkByEmployeeId(params));
        }catch(Exception e){
            return this.send(-1,"操作失败");
        }
    }
    @GetMapping("/leader/querySchedule")
    public Result leaderQuerySchedule(@RequestParam(value="date",required = false)String date,
                                      @RequestParam(value="employeeId",required = false)Integer employeeId,
                                      @RequestParam(value="pageNumber", required = false)Integer pageNumber,
                                      @RequestParam(value = "pageSize",required = false)Integer pageSize){
        Map<String,Object> params=ParamUtils.setPageInfo(pageNumber,pageSize);
        params.put("date",date);
        params.put("employeeId",employeeId);
        try{
            return this.send(workScheduleService.leaderQuerySchedule(params));
        }catch (Exception e){
            return this.send(-1,"操作失败");
        }
    }
    @GetMapping("/download")
    public Result download(HttpServletRequest request, HttpServletResponse response){
        try {
            FileUtils.download(response,"m22126EpUGGU");
        } catch (Exception e) {
        }
        return null;
    }
}
