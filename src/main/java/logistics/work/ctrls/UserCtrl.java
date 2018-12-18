package logistics.work.ctrls;

import logistics.work.common.Result;
import logistics.work.models.domain.User;
import logistics.work.services.UserService;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;

@RestController
@RequestMapping("/work/user")
public class UserCtrl extends BaseCtrl {
    @GetMapping("/test")
    public void test(MultipartFile[] files, HttpServletResponse response){
        OkHttpClient client = new OkHttpClient();
        Request req = new Request.Builder().url("http://pjuzxdszq.bkt.clouddn.com/instructor/318x77iK9w72/%E8%AF%84%E5%88%86%E8%A1%A8.docx").build();
        Response resp = null;
        try {
            resp = client.newCall(req).execute();
            if(resp.isSuccessful()) {
                ResponseBody body = resp.body();
                InputStream is = body.byteStream();
                ByteArrayOutputStream writer = new ByteArrayOutputStream();
                byte[] buff = new byte[1024 * 2];
                int len = 0;
                try {
                    while((len = is.read(buff)) != -1) {
                        writer.write(buff, 0, len);
                    }
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                System.out.println(body.contentType().toString());
                response.setHeader("Content-Type",body.contentType().toString());
                byte[] data=writer.toByteArray();
                response.getWriter().print(data);
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Unexpected code " + resp);
        }
    }

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
