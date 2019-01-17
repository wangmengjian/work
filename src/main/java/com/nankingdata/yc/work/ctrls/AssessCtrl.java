package com.nankingdata.yc.work.ctrls;

import com.nankingdata.yc.common.Users;
import com.nankingdata.yc.work.common.Constants;
import com.nankingdata.yc.work.common.ParamUtils;
import com.nankingdata.yc.work.common.Result;
import com.nankingdata.yc.work.models.dto.WorkAssessDto;
import com.nankingdata.yc.work.services.AssessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/work/assess")
public class AssessCtrl extends BaseCtrl {
    @Autowired
    private AssessService assessService;

    /**
     * 部门领导查询分配给其他部门的待考核工作（不分计划）
     * @param workName
     * @param employeeId
     * @param beginTime
     * @param endTime
     * @param pageNumber
     * @param pageSize
     * @return
     */
    @GetMapping("/queryOtherDeptAssess")
    public Result queryOtherDeptAssess(@RequestParam(value = "workName",required = false)String workName,
                            @RequestParam(value = "employeeId",required = false)Integer employeeId,
                            @RequestParam(value = "beginTime",required = false)String beginTime,
                            @RequestParam(value = "endTime",required = false)String endTime,
                            @RequestParam(value = "pageNumber",required = false)Integer pageNumber,
                            @RequestParam(value = "pageSize",required = false)Integer pageSize,
                            @RequestParam(value = "finishStatus",required = false)String finishStatus,
                            @RequestParam(value = "workFrom",required = false)String workFrom,
                            HttpSession session){
        Users users= (Users) session.getAttribute("user");
        Map<String,Object> params= ParamUtils.setPageInfo(pageNumber,pageSize);
        params.put("workName",workName);
        params.put("employeeId",employeeId);
        params.put("beginTime",beginTime);
        params.put("endTime",endTime);
        params.put("finishStatus",finishStatus);
        params.put("workFrom",workFrom);
        params.put("principalId",users.getId());
        try{
            return this.send(assessService.queryOtherDeptAssess(params));
        }catch (Exception e){
            return this.send(-1,"操作失败");
        }
    }

    /**
     * 领导考核工作
     * @param workAssessDto
     * @return
     */
    @PostMapping("/assessWork")
    public Result assessWork(WorkAssessDto workAssessDto, HttpSession session){
        Users users = (Users) session.getAttribute(Constants.userSession);
        try{
            return this.send(assessService.assessWork(workAssessDto.getWorkAssessList(), users.getId()));
        }catch(Exception e){
            return this.send(-1,"操作失败");
        }
    }

    /**
     * 领导查询考核记录
     * @param workName
     * @param employeeId
     * @param beginTime
     * @param endTime
     * @param pageNumber
     * @param pageSize
     * @param finishStatus
     * @param workFrom
     * @param session
     * @return
     */
    @GetMapping("/queryAssessRecords")
    public Result queryAssessRecords(@RequestParam(value = "workName",required = false)String workName,
                                           @RequestParam(value = "employeeId",required = false)Integer employeeId,
                                           @RequestParam(value = "beginTime",required = false)String beginTime,
                                           @RequestParam(value = "endTime",required = false)String endTime,
                                           @RequestParam(value = "pageNumber",required = false)Integer pageNumber,
                                           @RequestParam(value = "pageSize",required = false)Integer pageSize,
                                           @RequestParam(value = "finishStatus",required = false)String finishStatus,
                                           @RequestParam(value = "workFrom",required = false)String workFrom,
                                           @RequestParam(value = "departmentId",required = false)Integer departmentId,
                                           HttpSession session){
        Users users= (Users) session.getAttribute("user");
        Map<String,Object> params= ParamUtils.setPageInfo(pageNumber,pageSize);
        params.put("workName",workName);
        params.put("employeeId",employeeId);
        params.put("beginTime",beginTime);
        params.put("endTime",endTime);
        params.put("finishStatus",finishStatus);
        params.put("workFrom",workFrom);
        params.put("assessUserId",users.getId());
        params.put("departmentId",departmentId);
        try{
            return this.send(assessService.queryAssessRecords(params));
        }catch (Exception e){
            return this.send(-1,"操作失败");
        }
    }
    /**
     * 员工查询工作考核情况
     * @param workName
     * @param assessGrade
     * @param beginTime
     * @param endTime
     * @param pageNumber
     * @param pageSize
     * @param finishStatus
     * @param workFrom
     * @return
     */
    @GetMapping("/employee/queryAssess")
    public Result employeeQueryAssess(@RequestParam(value = "workName",required = false)String workName,
                                      @RequestParam(value = "assessGrade",required = false)String assessGrade,
                                      @RequestParam(value = "beginTime",required = false)String beginTime,
                                      @RequestParam(value = "endTime",required = false)String endTime,
                                      @RequestParam(value = "pageNumber",required = false)Integer pageNumber,
                                      @RequestParam(value = "pageSize",required = false)Integer pageSize,
                                      @RequestParam(value = "finishStatus",required = false)String finishStatus,
                                      @RequestParam(value = "workFrom",required = false)String workFrom,
                                      @RequestParam(value = "assessStatus",required = false)Integer assessStatus,
                                      HttpSession session){
        Map<String,Object> params= ParamUtils.setPageInfo(pageNumber,pageSize);
        params.put("workName",workName);
        params.put("assessGrade",assessGrade);
        params.put("beginTime",beginTime);
        params.put("endTime",endTime);
        params.put("finishStatus",finishStatus);
        params.put("workFrom",workFrom);
        params.put("isAssess",assessStatus);
        Users users= (Users) session.getAttribute("user");
        params.put("employeeId",users.getId());
        try{
            return this.send(assessService.employeeQueryAllAssess(params));
        }catch (Exception e){
            return this.send(-1,"操作失败");
        }
    }

    /**
     *查询公司的待考核工作
     * @param workName
     * @param employeeId
     * @param beginTime
     * @param endTime
     * @param pageNumber
     * @param pageSize
     * @param finishStatus
     * @param workFrom
     * @param departmentId
     * @return
     */
    @GetMapping("/queryCompanyAssessWork")
    public Result queryCompanyAssessWork(@RequestParam(value = "workName",required = false)String workName,
                                         @RequestParam(value = "employeeId",required = false)Integer employeeId,
                                         @RequestParam(value = "beginTime",required = false)String beginTime,
                                         @RequestParam(value = "endTime",required = false)String endTime,
                                         @RequestParam(value = "pageNumber",required = false)Integer pageNumber,
                                         @RequestParam(value = "pageSize",required = false)Integer pageSize,
                                         @RequestParam(value = "finishStatus",required = false)String finishStatus,
                                         @RequestParam(value = "workFrom",required = false)String workFrom,
                                         @RequestParam(value = "departmentId",required = false)Integer departmentId){
        Map<String,Object> params= ParamUtils.setPageInfo(pageNumber,pageSize);
        params.put("workName",workName);
        params.put("employeeId",employeeId);
        params.put("beginTime",beginTime);
        params.put("endTime",endTime);
        params.put("finishStatus",finishStatus);
        params.put("workFrom",workFrom);
        params.put("departmentId",departmentId);
        try{
            return this.send(assessService.queryCompanyAssessWork(params));
        }catch (Exception e){
            return this.send(-1,"操作失败");
        }
    }

    /**
     * 考核员工的所有工作
     * @param employeeIds
     * @param assessGrade
     * @param assessDesc
     * @param session
     * @return
     */
    @PostMapping("/assessEmployee")
    public Result assessEmployee(@RequestParam(value = "employeeIds",required = false)Integer[] employeeIds,
                                 @RequestParam(value = "assessGrade",required = true)String assessGrade,
                                 @RequestParam(value = "assessDesc",required = false)String assessDesc,
                                 HttpSession session){
        Users users= (Users) session.getAttribute("user");
        Map<String,Object> params=new HashMap<>();
        params.put("employeeIds",employeeIds);
        params.put("assessGrade",assessGrade);
        params.put("assessDesc",assessDesc);
        params.put("assessUserId",users.getId());
        try{
            return this.send(assessService.assessEmployeee(params));
        }catch (Exception e){
            return this.send(-1,"操作失败");
        }
    }

}
