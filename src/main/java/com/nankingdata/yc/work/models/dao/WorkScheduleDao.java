package com.nankingdata.yc.work.models.dao;

import com.nankingdata.yc.work.models.domain.WorkSchedule;
import com.nankingdata.yc.work.models.domain.WorkScheduleDetail;
import com.nankingdata.yc.work.models.dto.WorkScheduleDetailDto;
import com.nankingdata.yc.work.models.dto.WorkScheduleDto;
import com.nankingdata.yc.work.models.domain.WorkPool;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface WorkScheduleDao {
    /*根据日期查询工作计划*/
    public WorkScheduleDto queryWorkScheduleByDate(Map<String,Object> params);
    /*根据工作计划查询明细*/
    public List<WorkScheduleDetailDto> queryWorkScheduleDetail(Map<String,Object> params);
    /*查询计划明细总数*/
    public Integer queryWorkScheduleDetailCount(Map<String,Object> params);
    /*更改工作计划明细完成情况*/
    public int updateScheduleDetail(List<WorkScheduleDetailDto> workScheduleDetailDtoList);
    /*更改工作计划提交状态*/
    public int updateSchedule(Integer scheduleId);
    /*生成工作计划*/
    public int addSchedule(WorkSchedule workSchedule);
    /*添加工作计划明细*/
    public int addScheduleDetail(@Param(value="workScheduleDetailList")List<WorkScheduleDetail> workScheduleDetailList);
    /*查询所有工作计划*/
    public List<WorkScheduleDto> querySchedules(Map<String,Object> params);
    /*查询所有工作计划的条数*/
    public Integer querySchedulesCount(Map<String,Object> params);
    /*员工从工作计划移除工作项*/
    public Integer employeeRemoveWork(Integer id);
    /*查询多个员工的今日工作计划id*/
    public List<WorkSchedule> queryMoreWorkScheduleByEmployeeIds(List<Integer> employeeIds);
    /*批量添加工作计划*/
    public int addMoreWorkSchedule(List<WorkSchedule> workScheduleList);
    /*批量添加工作计划明细*/
    public int addWorkScheduleDetails(List<WorkScheduleDetail> workScheduleDetailList);
    /*查询计划id*/
    public Integer queryScheduleId(Map<String,Object> params);
    /*查询多个计划的明细*/
    public List<WorkScheduleDetailDto> queryMoreWorkScheduleDetails(Map<String,Object> params);
    /*查询多个员工在指定日期的计划*/
    public List<WorkScheduleDto> queryMoreEmployeesSchedules(Map<String,Object> params);
}
