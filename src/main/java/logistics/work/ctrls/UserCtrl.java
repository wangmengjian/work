package logistics.work.ctrls;

import logistics.work.common.Result;
import logistics.work.models.domain.User;
import logistics.work.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/work/user")
public class UserCtrl extends BaseCtrl {
    @Autowired
    private UserService userService;

    /**
     * 通过领导的id查询部门员工
     * @param session
     * @return
     */
    @GetMapping("/deptEmployees")
    public Result queryEmployeeByLeaderId(HttpSession session){
        User user=(User) session.getAttribute("USER_SESSION");
        return this.send(userService.queryEmployeeByUserId(user.getId()));
    }

    /**
     * 查询所有的员工
     * @return
     */
    @GetMapping("/queryAllEmployee")
    public Result queryAllEmployee(){
        return this.send(userService.queryAllEmployee());
    }
}
