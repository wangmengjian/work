package logistics.work.models.dao;

import logistics.work.models.domain.Employee;
import logistics.work.models.dto.DeptDto;

import java.util.List;

public interface UserDao {
    public String queryDeptNumberByUserId(Integer userId);
    public List<Employee> queryEmployeeByDeptNumber(String deptNumber);
    public List<DeptDto> queryAllDept();
    public List<Employee> queryAllEmployee();
}
