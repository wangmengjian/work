package logistics.work.models.dao;

import logistics.work.models.domain.WorkScheduleDetail;
import logistics.work.models.dto.WorkScheduleDetailDto;
import logistics.work.models.dto.WorkScheduleDto;

import java.util.List;
import java.util.Map;

public interface WorkScheduleDao {
    /*根据日期查询工作计划*/
    public WorkScheduleDto queryWorkScheduleByDate(Map<String,Object> params);
    /*根据工作计划查询明细*/
    public List<WorkScheduleDetailDto> queryWorkScheduleDetailByScheduleId(Map<String,Object> params);
}
