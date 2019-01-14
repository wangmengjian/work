package com.nankingdata.yc.work.services;

import com.nankingdata.yc.work.models.dao.UserDao;
import com.nankingdata.yc.work.models.domain.Department;
import com.nankingdata.yc.work.models.dto.DepartmentDto;
import com.nankingdata.yc.work.models.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CommonService {
    @Autowired
    private UserDao userDao;

    /**
     * 查询公司中所有部门以及员工
     *
     * @param params
     * @return
     */
    public Map<String, Object> queryAllEmployees(Map<String, Object> params) {
        Integer companyId = (Integer) params.get("companyId");
        Integer departmentId = (Integer) params.get("departmentId");
        Integer employeeId = (Integer) params.get("employeeId");
        List<UserDto> userDtoList = new ArrayList<>();//数据中所有的员工
        List<DepartmentDto> departmentDtoList = new ArrayList<>();//数据中所有的部门
        if (employeeId != null) {
            UserDto employee = userDao.queryUsersById(employeeId);
            List<Integer> idList = new ArrayList<>();
            if (departmentId == null) {//部门为空
                idList.add(employee.getDepartmentId());
                departmentDtoList = userDao.queryDepartmentsByIds(idList);
            } else {//部门不为空
                if (departmentId.equals(employee.getDepartmentId())) {//员工在选择的部门中
                    idList.add(employee.getDepartmentId());
                    departmentDtoList = userDao.queryDepartmentsByIds(idList);
                }
            }
            userDtoList.add(employee);
        } else {//员工为空
            if (departmentId == null) {//部门为空
                departmentDtoList = userDao.queryDepartmentsInCompany(params);//分页查询公司中所有部门
                List<Integer> idList = new ArrayList<>();
                for (DepartmentDto departmentDto : departmentDtoList) {
                    idList.add(departmentDto.getId());
                }
                Map<String, Object> temp_param = new HashMap<>();
                temp_param.put("idList", idList);
                userDtoList = userDao.queryUsersInDepartment(temp_param);//查询部门中所有员工
            } else {//部门不为空
                List<Integer> idList = new ArrayList<>();
                idList.add(departmentId);
                departmentDtoList = userDao.queryDepartmentsByIds(idList);//查询部门
                Map<String, Object> temp_param = new HashMap<>();
                temp_param.put("idList", idList);
                userDtoList = userDao.queryUsersInDepartment(temp_param);//查询部门中的所有员工
            }
        }
        Map<String, Object> result = new HashMap<>();
        if (departmentDtoList == null || departmentDtoList.size() <= 0) {//判断是否存在部门
            result.put("data", null);
            result.put("total", 0);
            result.put("all", 0);
            return result;
        }
        //部门中添加员工
        if (userDtoList != null && userDtoList.size() > 0) {
            for (DepartmentDto departmentDto : departmentDtoList) {
                List<UserDto> userDtoList1 = new ArrayList<>();
                for (UserDto userDto : userDtoList) {
                    if (userDto.getDepartmentId().equals(departmentDto.getId())) {
                        userDtoList1.add(userDto);
                    }
                }
                departmentDto.setUserDtoList(userDtoList1);
            }
        }
        result.put("data", departmentDtoList);
        result.put("total", departmentDtoList.size());
        return result;
    }

    /**
     * 查询管理的部门
     *
     * @param params
     * @return
     */
    public Map<String, Object> queryLeadDepartment(Map<String, Object> params) {
        Integer principalId = (Integer) params.get("principalId");
        Integer employeeId = (Integer) params.get("employeeId");
        Integer departmentId = (Integer) params.get("departmentId");
        if (employeeId != null) {
            if(departmentId!=null) {
                UserDto userDto = userDao.queryUsersById(employeeId);
                if (!userDto.getDepartmentId().equals(departmentId)) {
                    params.put("departmentId", -1);
                }
            }
        }
        List<DepartmentDto> departmentDtoList = userDao.queryLeadDepartment(params);
        List<Integer> idList = null;
        if (departmentDtoList != null && departmentDtoList.size() > 0) {
            idList = new ArrayList<>();
            for (DepartmentDto departmentDto : departmentDtoList) {
                idList.add(departmentDto.getId());
            }
        }
        params.put("idList", idList);
        List<UserDto> userDtoList = userDao.queryUsersInDepartment(params);
        if (departmentDtoList != null && departmentDtoList.size() > 0 && userDtoList != null && userDtoList.size() > 0) {
            for (DepartmentDto departmentDto : departmentDtoList) {
                List<UserDto> userDtoList1 = new ArrayList<>();
                for (UserDto userDto : userDtoList) {
                    if(userDto.getDepartmentId().equals(departmentDto.getId())) {
                        userDtoList1.add(userDto);
                    }
                }
                departmentDto.setUserDtoList(userDtoList1);
            }
        }
        Map<String, Object> result = new HashMap<>();
        result.put("data", departmentDtoList);
        result.put("total",departmentDtoList.size());
        return result;
    }
}
