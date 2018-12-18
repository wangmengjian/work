package com.nankingdata.yc.work.ctrls;

import com.nankingdata.yc.work.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/work/user")
public class UserCtrl extends BaseCtrl {


    @Autowired
    private UserService userService;

    /**
     * 通过领导的id查询部门员工
     * @param session
     * @return
     */
    /*@GetMapping("/deptEmployees")
    public Result queryEmployeeByLeaderId(HttpSession session){
        Users user=(Users) session.getAttribute(Constants.userSession);
        return this.send(userService.queryEmployeeByUserId(user.getId()));
    }*/

    /**
     * 查询所有的员工
     * @return
     */
    /*@GetMapping("/queryAllEmployee")
    public Result queryAllEmployee(){
        return this.send(userService.queryAllEmployee());
    }*/
}
