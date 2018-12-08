package logistics.work.models.dao;

import logistics.work.models.domain.WorkSchedule;
import logistics.work.models.dto.WorkScheduleDetailDto;
import logistics.work.models.dto.WorkScheduleDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface WorkScheduleDao {
    /*根据日期查询工作计划*/
    public WorkScheduleDto queryWorkScheduleByDate(Map<String,Object> params);
    /*根据工作计划查询明细*/
    public List<WorkScheduleDetailDto> queryWorkScheduleDetailByScheduleId(Map<String,Object> params);
    /*更改工作计划明细完成情况*/
    public int updateScheduleDetail(List<WorkScheduleDetailDto> workScheduleDetailDtoList);
    /*更改工作计划提交状态*/
    public int updateSchedule(Integer scheduleId);
    /*生成工作计划*/
    public int addSchedule(WorkSchedule workSchedule);
    /*添加工作计划明细*/
    public int addScheduleDetail(@Param("idList") List<Integer> idList, @Param("scheduleId")Integer scheduleId);
}
