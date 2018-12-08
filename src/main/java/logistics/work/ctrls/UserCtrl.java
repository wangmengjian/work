package logistics.work.ctrls;

import logistics.work.common.Result;
import logistics.work.models.domain.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/work/user")
public class UserCtrl {
    @GetMapping("/deptEmployees")
    public Result queryEmployeeByLeaderId(HttpSession session){
        User user=(User) session.getAttribute("USER_SESSION");
        return null;
    }
}
