package com.nankingdata.yc.work.models.dao;

import com.nankingdata.yc.work.models.domain.WorkSchedule;
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
    public int addScheduleDetail(@Param("workPoolList") List<WorkPool> workPoolList, @Param("scheduleId")Integer scheduleId);
    /*查询今日日计划id*/
    public Integer queryTodayScheduleId(Integer userId);
    /*领导查询工作计划*/
    public List<WorkScheduleDto> leaderQuerySchedule(Map<String,Object> params);
    /*部门工作计划的条数*/
    public Integer leaderQueryScheduleCount(Map<String,Object> params);
    /*员工从工作计划移除工作项*/
    public Integer employeeRemoveWork(Integer id);
}
