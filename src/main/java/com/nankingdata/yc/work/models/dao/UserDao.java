package com.nankingdata.yc.work.models.dao;

import com.nankingdata.yc.common.Users;
import com.nankingdata.yc.work.models.domain.Department;
import com.nankingdata.yc.work.models.dto.DepartmentDto;
import com.nankingdata.yc.work.models.dto.UserDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface UserDao {
    public UserDto queryUsersById(Integer id);
    /*根据公司的id查询部门*/
    public List<DepartmentDto> queryDepartmentsInCompany(Map<String,Object> params);
    /*查询公司的部门总数*/
    public Integer queryDepartmentsCount(Map<String,Object> params);
    /*根据部门id查询部门*/
    public List<DepartmentDto> queryDepartmentsByIds(@Param("idList") List<Integer> idList);
    /*根据部门id查询员工*/
    public List<UserDto> queryUsersInDepartment(Map<String,Object> params);
    /*查询管理的部门*/
    public List<DepartmentDto> queryLeadDepartment(Map<String,Object> params);
}
