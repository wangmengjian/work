package logistics.work.models.dao;

import logistics.work.models.domain.Employee;

import java.util.List;

public interface UserDao {
    public String queryDeptNumberByUserId(Integer userId);
    public List<Employee> queryEmployeeByDeptNumber(String deptNumber);
}
