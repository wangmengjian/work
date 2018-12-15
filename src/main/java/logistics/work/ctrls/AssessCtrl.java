package logistics.work.ctrls;

import logistics.work.common.ParamUtils;
import logistics.work.common.Result;
import logistics.work.services.AssessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/work/assess")
public class AssessCtrl extends BaseCtrl {
    @Autowired
    private AssessService assessService;

    /**
     * 查询待考核工作
     * @param workName
     * @param employeeId
     * @param beginTime
     * @param endTime
     * @param pageNumber
     * @param pageSize
     * @return
     */
    @GetMapping("/queryAssessWork")
    public Result queryWork(@RequestParam(value = "workName",required = false)String workName,
                            @RequestParam(value = "employeeId",required = false)Integer employeeId,
                            @RequestParam(value = "beginTime",required = false)String beginTime,
                            @RequestParam(value = "endTime",required = false)String endTime,
                            @RequestParam(value = "pageNumber",required = false)Integer pageNumber,
                            @RequestParam(value = "pageSize",required = false)Integer pageSize,
                            @RequestParam(value = "finishStatus",required = false)String finishStatus){
        Map<String,Object> params= ParamUtils.setPageInfo(pageNumber,pageSize);
        params.put("workName",workName);
        params.put("employeeId",employeeId);
        params.put("beginTime",beginTime);
        params.put("endTime",endTime);
        params.put("finishStatus",finishStatus);
        try{
            return this.send(assessService.queryWork(params));
        }catch (Exception e){
            return this.send(-1,"操作失败");
        }
    }
}
