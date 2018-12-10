package logistics.work.services;

import logistics.work.models.dao.UserDao;
import logistics.work.models.domain.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserService {
    @Autowired
    private UserDao userDao;
    public Map<String,Object> queryEmployeeByUserId(Integer userId){
        String deptNumber=userDao.queryDeptNumberByUserId(userId);
        List<Employee> employeeList=userDao.queryEmployeeByDeptNumber(deptNumber);
        Map<String,Object> result=new HashMap<>();
        result.put("data",employeeList);
        result.put("total",employeeList.size());
        return result;
    }
}
