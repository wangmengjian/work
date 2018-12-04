package logistics.work.services;

import logistics.work.common.FileUtils;
import logistics.work.models.dao.WorkScheduleDao;
import logistics.work.models.dto.WorkScheduleDetailDto;
import logistics.work.models.dto.WorkScheduleDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class WorkScheduleService {
    @Autowired
    private WorkScheduleDao workScheduleDao;

    /**
     * 查询日计划
     * @param params
     * @return
     */
    public Map<String,Object> querySchedule(Map<String,Object> params){
        if(params.get("date")==null){
            LocalDate localDate=LocalDate.now();
            params.put("date",localDate.toString());
        }
        WorkScheduleDto workScheduleDto=workScheduleDao.queryWorkScheduleByDate(params);
        if(workScheduleDto==null){
            return null;
        }
        params.put("scheduleId",workScheduleDto.getId());
        List<WorkScheduleDetailDto> workScheduleDetailDtoList=workScheduleDao.queryWorkScheduleDetailByScheduleId(params);
        workScheduleDto.setWorkScheduleDetailDtoList(workScheduleDetailDtoList);
        Map<String,Object> result=new HashMap<>();
        result.put("data",workScheduleDto);
        result.put("total",1);
        return result;
    }
    @Transactional
    public int submitSchedule(WorkScheduleDto workScheduleDto) throws Exception {
        workScheduleDao.updateSchedule(workScheduleDto.getId());
        if(workScheduleDto.getWorkScheduleDetailDtoList()==null)return 0;
        for(WorkScheduleDetailDto workScheduleDetailDto:workScheduleDto.getWorkScheduleDetailDtoList()){
            String finishPicture=null;
            if(workScheduleDetailDto.getPictures()!=null&&workScheduleDetailDto.getPictures().length>0){
                finishPicture=FileUtils.upload(workScheduleDetailDto.getPictures());
            }
            workScheduleDetailDto.setFinishPicture(finishPicture);
        }
        int result=workScheduleDao.updateScheduleDetail(workScheduleDto.getWorkScheduleDetailDtoList());
        return result;
    }
}
