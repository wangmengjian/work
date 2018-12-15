package logistics.work.services;

import logistics.work.models.dao.UserDao;
import logistics.work.models.domain.Employee;
import logistics.work.models.dto.DeptDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    /**
     * 查询所有部门的员工
     * @return
     */
    public Map<String,Object> queryAllEmployee(){
        List<DeptDto> deptDtoList=userDao.queryAllDept();
        List<Employee> employeeList=userDao.queryAllEmployee();
        for(DeptDto deptDto:deptDtoList){
            List<Employee> employeeList1=new ArrayList<>();
            for(Employee employee:employeeList){
                if(deptDto.getDeptNumber().equals(employee.getDeptNumber())){
                    employeeList1.add(employee);
                }
            }
            deptDto.setEmployeeList(employeeList1);
        }
        Map<String,Object> result=new HashMap<>();
        result.put("data",deptDtoList);
        return result;
    }
}
