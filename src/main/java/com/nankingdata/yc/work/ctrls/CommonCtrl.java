package com.nankingdata.yc.work.ctrls;

import com.nankingdata.yc.common.Authority;
import com.nankingdata.yc.common.Users;
import com.nankingdata.yc.work.common.FileUtils;
import com.nankingdata.yc.work.common.ParamUtils;
import com.nankingdata.yc.work.common.Result;
import com.nankingdata.yc.work.services.CommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/work/common")
public class CommonCtrl extends BaseCtrl{
    @Autowired
    private CommonService commonService;
    @GetMapping("/noSign")
    public Result noSign(){
        return this.send(-2,"未登录");
    }
    @PostMapping("/upload")
    public Result upload(MultipartFile file){
        if(file==null)return this.send(null);
        try{
            return this.send(FileUtils.upload(file));
        }catch (Exception e){
            return this.send(-1,"操作失败");
        }
    }
    @GetMapping("/queryWorkAuthority")
    public Result getWorkAuthority(HttpSession session){
        List<Authority> authorities= (List<Authority>) session.getAttribute("authorities");
        List<Authority> workAuthorities=new ArrayList<>();
        if(authorities!=null&&authorities.size()>0) {
            for (Authority authority : authorities) {
                String menuUrl = authority.getMenuUrl();
                if (menuUrl!=null&&menuUrl.startsWith("workNote")) {
                    workAuthorities.add(authority);
                }
            }
        }
        return this.send(workAuthorities);
    }
    /**
     * 查询公司下所有员工
     *
     * @param employeeId
     * @param session
     * @return
     */
    @GetMapping("/queryAllEmployees")
    public Result queryAllEmployees(@RequestParam(value = "departmentId", required = false) Integer departmentId,
                                    @RequestParam(value = "employeeId", required = false) Integer employeeId,
                                    HttpSession session) {
        Users users = (Users) session.getAttribute("user");
        Map<String, Object> params = new HashMap<>();
        params.put("employeeId", employeeId);
        params.put("companyId", users.getCompanyId());
        params.put("departmentId", departmentId);
        params.put("myself",users.getId());
        try {
            return this.send(commonService.queryAllEmployees(params));
        } catch (Exception e) {
            return this.send(-1, "操作失败");
        }
    }

    /**
     * 查询管理的部门
     * @param departmentId
     * @param employeeId
     * @param session
     * @return
     */
    @GetMapping("/queryLeadDepartment")
    public Result queryLeadDepartment(@RequestParam(value = "departmentId", required = false) Integer departmentId,
                                    @RequestParam(value = "employeeId", required = false) Integer employeeId,
                                    HttpSession session){
        Users users = (Users) session.getAttribute("user");
        Map<String, Object> params = new HashMap<>();
        params.put("employeeId", employeeId);
        params.put("principalId", users.getId());
        params.put("departmentId", departmentId);
        params.put("myself",users.getId());
        try {
            return this.send(commonService.queryLeadDepartment(params));
        } catch (Exception e) {
            return this.send(-1, "操作失败");
        }
    }
}
