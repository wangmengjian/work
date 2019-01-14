package com.nankingdata.yc.work.ctrls;

import com.nankingdata.yc.common.Users;
import com.nankingdata.yc.work.common.MyException;
import com.nankingdata.yc.work.models.dto.*;
import com.nankingdata.yc.work.common.Constants;
import com.nankingdata.yc.work.common.ParamUtils;
import com.nankingdata.yc.work.common.Result;
import com.nankingdata.yc.work.models.domain.WorkPool;
import com.nankingdata.yc.work.services.WorkAuditService;
import com.nankingdata.yc.work.services.WorkScheduleService;
import com.nankingdata.yc.work.services.WorkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/work/schedule")
public class ScheduleCtrl extends BaseCtrl {
    @Autowired
    private WorkAuditService workAuditService;
    @Autowired
    private WorkScheduleService workScheduleService;
    @Autowired
    private WorkService workService;

    /**
     * 员工添加工作项
     *
     * @param workAuditDto
     * @param session
     * @return
     */
    @PostMapping("/employee/addWork")
    public Result employeeAddWork(@Valid WorkAuditDto workAuditDto, HttpSession session) {
        Users users = (Users) session.getAttribute(Constants.userSession);
        Map<String, Object> params = new HashMap<>();
        params.put("userId", users.getId());
        params.put("workAuditList", workAuditDto.getWorkAudits());
        try {
            return this.send(workAuditService.submitAudit(params));
        } catch (Exception e) {
            return this.send(-1, "操作失败");
        }
    }

    /**
     * 员工查询单个工作项
     *
     * @param id
     * @return
     */
    @GetMapping("employee/queryOneWork")
    public Result employeeQueryWorkById(@RequestParam("id") Integer id) {
        return this.send(workService.queryWorkById(id));
    }

    /**
     * 员工根据时间查询日工作计划
     *
     * @param date
     * @param session
     * @return
     */
    @GetMapping("/employee/querySchedule")
    public Result employeeQueryScheduleByDate(@RequestParam(value = "date", required = false) String date,
                                              @RequestParam(value = "workFrom", required = false) String workFrom,
                                              @RequestParam(value = "workName", required = false) String workName,
                                              @RequestParam(value = "finishStatus", required = false) String finishStatus,
                                              @RequestParam(value = "pageNumber", required = false) Integer pageNumber,
                                              @RequestParam(value = "pageSize", required = false) Integer pageSize,
                                              HttpSession session) {
        Users users = (Users) session.getAttribute(Constants.userSession);
        Map<String, Object> params = ParamUtils.setPageInfo(pageNumber, pageSize);
        params.put("userId", users.getId());
        params.put("workFrom", workFrom);
        params.put("workName", workName);
        params.put("finishStatus", finishStatus);
        params.put("date", date);
        Map<String, Object> result = workScheduleService.querySchedule(params);
        return this.send(result);
    }

    /**
     * 员工按分页查询所有的历史计划
     *
     * @param pageNumber
     * @param pageSize
     * @param date
     * @param session
     * @return
     */
    @GetMapping("/employee/querySchedules")
    public Result employeeQuerySchedules(@RequestParam(value = "pageNumber", required = false) Integer pageNumber,
                                         @RequestParam(value = "pageSize", required = false) Integer pageSize,
                                         @RequestParam(value = "date", required = false) String date,
                                         HttpSession session) {
        Users users = (Users) session.getAttribute("user");
        Map<String, Object> params = ParamUtils.setPageInfo(pageNumber, pageSize);
        params.put("date", date);
        params.put("employeeId", users.getId());
        try {
            return this.send(workScheduleService.employeeQuerySchedules(params));
        } catch (Exception e) {
            return this.send(-1, "操作失败");
        }
    }


    /**
     * 员工更改工作项
     *
     * @param updateWorkDto
     * @param session
     * @return
     */
    @PostMapping("/employee/updateWork")
    public Result employeeUpdateWork(@Valid UpdateWorkDto updateWorkDto, HttpSession session) {
        try {
            return this.send(workAuditService.employeeUpdateWork(updateWorkDto));
        } catch (Exception e) {
            return this.send(-1, "操作失败");
        }
    }

    /**
     * 员工查询所有工作项（待审核，通过，不通过）
     *
     * @param workName
     * @param pageNumber
     * @param pageSize
     * @param session
     * @return
     */
    @GetMapping("/employee/queryWork")
    public Result employeeQueryWork(@RequestParam(value = "workName", required = false) String workName,
                                    @RequestParam(value = "pageNumber", required = false) Integer pageNumber,
                                    @RequestParam(value = "pageSize", required = false) Integer pageSize,
                                    @RequestParam(value = "auditStatus", required = false) String auditStatus,
                                    @RequestParam(value = "workFrom", required = false) String workFrom,
                                    HttpSession session) {
        Map<String, Object> params = ParamUtils.setPageInfo(pageNumber, pageSize);
        params.put("workName", workName);
        Users users = (Users) session.getAttribute(Constants.userSession);
        params.put("userId", users.getId());
        params.put("auditStatus", auditStatus);
        params.put("workFrom", workFrom);
        return this.send(workService.queryWork(params));
    }

    /**
     * 员工删除审核
     *
     * @param id
     * @return
     */
    @DeleteMapping("/employee/deleteAudit")
    public Result employeeDeleteAuditRecord(@RequestParam(value = "id", required = true) Integer id) {
        try {
            return this.send(workService.deleteAuditRecord(id));
        } catch (Exception e) {
            return this.send(-1, "操作失败");
        }
    }

    /**
     * 员工查询常规工作池
     *
     * @param workName
     * @param pageNumber
     * @param pageSize
     * @param session
     * @return
     */
    @GetMapping("/employee/queryWorkPool")
    public Result employeeQueryWorkPool(@RequestParam(value = "workName", required = false) String workName,
                                        @RequestParam(value = "scheduleId", required = false) Integer scheduleId,
                                        @RequestParam(value = "pageNumber", required = false) Integer pageNumber,
                                        @RequestParam(value = "pageSize", required = false) Integer pageSize, HttpSession session) {
        Map<String, Object> params = ParamUtils.setPageInfo(pageNumber, pageSize);
        params.put("workName", workName);
        Users users = (Users) session.getAttribute(Constants.userSession);
        params.put("userId", users.getId());
        params.put("scheduleId", scheduleId);
        return this.send(workService.queryUnAddWork(params));
    }

    /**
     * 生成日工作计划
     *
     * @param workPoolDto
     * @param session
     * @return
     */
    @PostMapping("/employee/newSchedule")
    public Result employeeNewSchedule(WorkPoolDto workPoolDto,
                                      HttpSession session) {
        Users users = (Users) session.getAttribute(Constants.userSession);
        Map<String, Object> params = new HashMap<>();
        params.put("workPoolList", workPoolDto.getWorkPoolList());
        params.put("userId", users.getId());
        params.put("date", workPoolDto.getDate());
        try {
            return this.send(workScheduleService.newSchedule(params));
        } catch (Exception e) {
            return this.send(-1, "操作失败");
        }
    }

    /**
     * 员工提交日计划
     *
     * @param workScheduleDto
     * @return
     */
    @PostMapping("/employee/submitSchedule")
    public Result submitSchedule(@Valid WorkScheduleDto workScheduleDto) {
        try {
            return this.send(workScheduleService.submitSchedule(workScheduleDto));
        } catch (MyException e) {
            return this.send(-1, "有未填写完成情况的工作");
        } catch (Exception e1) {
            return this.send(-1, "操作失败");
        }
    }

    /**
     * 员工保存工作计划中工作项的完成状态
     *
     * @param workScheduleDetailDto
     * @return
     */
    @PostMapping("/employee/saveFinishStatus")
    public Result employeeSaveStatus(@Valid WorkScheduleDetailDto workScheduleDetailDto) {
        try {
            return this.send(workScheduleService.saveWorkFinishStatus(workScheduleDetailDto));
        } catch (Exception e) {
            return this.send(-1, "操作失败");
        }
    }

    /**
     * 员工从工作计划中移除工作
     *
     * @param id
     * @return
     */
    @DeleteMapping("/employee/removeWork")
    public Result employeeRemoveWork(Integer id) {
        try {
            return this.send(workScheduleService.employeeRemoveWork(id));
        } catch (MyException e) {
            return this.send(-1, e.getMessage());
        } catch (Exception e) {
            return this.send(-1, "操作失败");
        }
    }

    /**
     * 员工取消申请
     *
     * @param id
     * @return
     */
    @PostMapping("/employee/cancelAudit")
    public Result employeeCancelAudit(Integer id) {
        try {
            return this.send(workAuditService.employeeCancelAudit(id));
        } catch (Exception e) {
            return this.send(-1, "操作失败");
        }
    }

    /**
     * 领导查询部门员工的工作项
     *
     * @param workName
     * @param employeeId
     * @param pageNumber
     * @param pageSize
     * @return
     */
    @GetMapping("/leader/queryWork")
    public Result leaderQueryWork(@RequestParam(value = "workName", required = false) String workName,
                                  @RequestParam(value = "employeeId", required = false) Integer employeeId,
                                  @RequestParam(value = "pageNumber", required = false) Integer pageNumber,
                                  @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        Map<String, Object> params = ParamUtils.setPageInfo(pageNumber, pageSize);
        params.put("workName", workName);
        params.put("employeeId", employeeId);
        try {
            return this.send(workService.queryWorkByEmployeeId(params));
        } catch (Exception e) {
            return this.send(-1, "操作失败");
        }
    }

    /**
     * 领导查询部门员工的日计划
     *
     * @param date
     * @param employeeId
     * @param pageNumber
     * @param pageSize
     * @return
     */
    @GetMapping("/leader/querySchedule")
    public Result leaderQuerySchedule(@RequestParam(value = "date", required = false) String date,
                                      @RequestParam(value = "employeeId", required = false) Integer employeeId,
                                      @RequestParam(value = "pageNumber", required = false) Integer pageNumber,
                                      @RequestParam(value = "pageSize", required = false) Integer pageSize,
                                      HttpSession session) {
        Users users = (Users) session.getAttribute("user");
        Map<String, Object> params = ParamUtils.setPageInfo(pageNumber, pageSize);
        params.put("date", date);
        params.put("employeeId", employeeId);
        params.put("departmentId", users.getDepartmentId());
        try {
            return this.send(workScheduleService.leaderQuerySchedule(params));
        } catch (Exception e) {
            return this.send(-1, "操作失败");
        }
    }
    /**
     * 查询公司工作项
     *
     * @param pageNumber
     * @param pageSize
     * @param departmentId
     * @param workName
     * @param employeeId
     * @param session
     * @return
     */
    @GetMapping("/queryCompanyWorks")
    public Result queryCompanyWorks(@RequestParam(value = "pageNumber", required = false) Integer pageNumber,
                                    @RequestParam(value = "pageSize", required = false) Integer pageSize,
                                    @RequestParam(value = "departmentId", required = false) Integer departmentId,
                                    @RequestParam(value = "workName", required = false) String workName,
                                    @RequestParam(value = "employeeId", required = false) Integer employeeId,
                                    HttpSession session) {
        Users users = (Users) session.getAttribute("user");
        Map<String, Object> params = ParamUtils.setPageInfo(pageNumber, pageSize);
        params.put("departmentId", departmentId);
        params.put("workName", workName);
        params.put("employeeId", employeeId);
        params.put("companyId", users.getCompanyId());
        try {
            return this.send(workService.queryCompanyWorks(params));
        } catch (Exception e) {
            return this.send(-1, "操作失败");
        }
    }

    /**
     * 领导新增员工工作
     *
     * @param workPool 工作项
     * @return
     */
    @PostMapping("/leader/addWork")
    public Result leaderAddWork(@Valid WorkPool workPool, HttpSession session) {
        Users users = (Users) session.getAttribute("user");
        Integer leaderId = users.getId();
        try {
            return this.send(workService.leaderAddWork(workPool, leaderId));
        } catch (Exception e) {
            return this.send(-1, "操作失败");
        }
    }

    /**
     * 领导分配工作
     *
     * @param workPoolDto
     * @return
     */
    @PostMapping("/leader/allotWork")
    public Result leaderAllotWork(WorkPoolDto workPoolDto, HttpSession session) {
        try {
            return this.send(workService.allotWork(workPoolDto.getWorkPoolList(), (Users) session.getAttribute("user")));
        } catch (Exception e) {
            return this.send(-1, "操作失败");
        }
    }

    /**
     * 删除常规工作项
     *
     * @param workPool
     * @return
     */
    @DeleteMapping("/leader/deleteWork")
    public Result leaderDeleteWork(WorkPool workPool) {
        return this.send(workService.deleteWork(workPool.getId()));
    }

    /**
     * 人事查询部门员工的工作
     *
     * @param workName
     * @param employeeId
     * @param pageNumber
     * @param pageSize
     * @return
     */
    @GetMapping("/personnel/queryWork")
    public Result personnelQueryWork(@RequestParam(value = "workName", required = false) String workName,
                                     @RequestParam(value = "employeeId", required = false) Integer employeeId,
                                     @RequestParam(value = "pageNumber", required = false) Integer pageNumber,
                                     @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        Map<String, Object> params = ParamUtils.setPageInfo(pageNumber, pageSize);
        params.put("workName", workName);
        params.put("employeeId", employeeId);
        try {
            return this.send(workService.queryWorkByEmployeeId(params));
        } catch (Exception e) {
            return this.send(-1, "操作失败");
        }
    }

    /**
     * 人事新增员工常规工作项
     *
     * @param workPoolDto
     * @return
     */
    @RequestMapping("/personnel/addWork")
    public Result personnelAddWork(@Valid WorkPoolDto workPoolDto) {
        try {
            return this.send(workService.addWork(workPoolDto.getWorkPoolList()));
        } catch (Exception e) {
            return this.send(-1, "操作失败");
        }
    }

    /**
     * 人事管理员更改常规工作项
     *
     * @param workPool
     * @return
     */
    @RequestMapping("/personnel/updateWork")
    public Result personnelUpdateWork(@Valid WorkPool workPool) {
        try {
            return this.send(workService.updateWork(workPool));
        } catch (Exception e) {
            return this.send(-1, "操作失败");
        }
    }

    /**
     * 领导给多个员工添加工作
     *
     * @param workPool
     * @return
     */
    @PostMapping("/leader/addWorkToEmployees")
    public Result leaderAddWorkToEmployees(@Valid WorkPool workPool, HttpSession session) {
        Users user = (Users) session.getAttribute("user");
        try {
            return this.send(workService.addWorkToEmployees(workPool, user.getId()));
        } catch (Exception e) {
            return this.send(-1, "操作失败");
        }
    }

    /**
     * 领导根据员工id查询未添加到工作计划的工作项
     *
     * @param employeeId
     * @param session
     * @return
     */
    @GetMapping("/leader/queryUnAddWork")
    public Result leaderQueryUnAddWork(@RequestParam(value = "employeeId") Integer employeeId,
                                       HttpSession session) {
        Users users = (Users) session.getAttribute("user");
        Integer deptId = users.getDepartmentId();
        Map<String, Object> params = new HashMap<>();
        params.put("deptId", deptId);
        params.put("userId", employeeId);
        return this.send(workService.queryUnAddWork(params));
    }

    /**
     * 领导批量安排工作
     *
     * @param workIds
     * @param employeeId
     * @return
     */
    @PostMapping("/leader/allotWorks")
    public Result leaderAllotWorks(@RequestParam(value = "workIds", required = false) Integer[] workIds,
                                   @RequestParam(value = "employeeId", required = false) Integer employeeId,
                                   HttpSession session) {
        Map<String, Object> params = new HashMap<>();
        params.put("workIds", workIds);
        params.put("employeeId", employeeId);
        params.put("user", session.getAttribute("user"));
        try {
            return this.send(workService.leaderAllotWorks(params));
        } catch (Exception e) {
            return this.send(-1, "操作失败");
        }
    }

    /**
     * 追加历史工作
     *
     * @param historyWorkDto
     * @return
     */
    @PostMapping("/employee/addHistoryWork")
    public Result addHistoryWork(@Valid HistoryWorkDto historyWorkDto, HttpSession session) {
        try {
            return this.send(workScheduleService.addHistoryWork(historyWorkDto, (Users) session.getAttribute("user")));
        } catch (Exception e) {
            return this.send(-1, "操作失败");
        }
    }

    /**
     * 查询员工工作计划
     * @param employeeId
     * @param pageNumber
     * @param pageSize
     * @param date
     * @return
     */
    @GetMapping("/queryEmployeeSchedules")
    public Result queryEmployeeSchedules(@RequestParam(value = "employeeId",required = false)Integer employeeId,
                                         @RequestParam(value = "pageNumber",required = false)Integer pageNumber,
                                         @RequestParam(value = "pageSize",required = false)Integer pageSize,
                                         @RequestParam(value = "date",required = false)String date){
        Map<String,Object> params= ParamUtils.setPageInfo(pageNumber,pageSize);
        params.put("employeeId",employeeId);
        params.put("date",date);
        try{
            return this.send(workScheduleService.queryEmployeeSchedules(params));
        }catch (Exception e){
            return this.send(-1,"操作失败");
        }
    }

    /**
     * 员工开始工作
     * @param id
     * @return
     */
    @PutMapping("/beginWork")
    public Result beginWork(@RequestParam(value = "id",required = true)Integer id){
        try{
            return this.send(workService.beginWork(id));
        }catch (Exception e){
            return this.send(-1,"操作失败");
        }
    }
}
