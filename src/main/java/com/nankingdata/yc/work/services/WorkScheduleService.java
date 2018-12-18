package com.nankingdata.yc.work.services;

import com.nankingdata.yc.work.common.FileUtils;
import com.nankingdata.yc.work.models.domain.WorkSchedule;
import com.nankingdata.yc.work.models.dto.WorkScheduleDto;
import com.nankingdata.yc.work.models.dao.WorkDao;
import com.nankingdata.yc.work.models.dao.WorkScheduleDao;
import com.nankingdata.yc.work.models.domain.WorkPool;
import com.nankingdata.yc.work.models.dto.WorkScheduleDetailDto;
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
    @Autowired
    private WorkDao workDao;
    /**
     * 员工查询日计划
     * @param params
     * @return
     */
    public Map<String,Object> querySchedule(Map<String,Object> params){
        if(params.get("date")==null){
            LocalDate localDate=LocalDate.now();
            params.put("date",localDate.toString());
        }
        WorkScheduleDto workScheduleDto=workScheduleDao.queryWorkScheduleByDate(params);
        Map<String,Object> result=new HashMap<>();
        if(workScheduleDto==null){
            result.put("data",null);
            result.put("total",1);
            return result;
        }
        params.put("scheduleId",workScheduleDto.getId());
        List<WorkScheduleDetailDto> workScheduleDetailDtoList=workScheduleDao.queryWorkScheduleDetailByScheduleId(params);
        workScheduleDto.setWorkScheduleDetailDtoList(workScheduleDetailDtoList);
        result.put("data",workScheduleDto);
        return result;
    }

    /**
     * 员工提交日工作计划
     * @param workScheduleDto
     * @return
     * @throws Exception
     */
    @Transactional
    public int submitSchedule(WorkScheduleDto workScheduleDto) throws Exception {
        workScheduleDao.updateSchedule(workScheduleDto.getId());
        if(workScheduleDto.getWorkScheduleDetailDtoList()==null)return 0;
        for(WorkScheduleDetailDto workScheduleDetailDto:workScheduleDto.getWorkScheduleDetailDtoList()){
            String finishPicture=null;
            if(workScheduleDetailDto.getPictures()!=null&&workScheduleDetailDto.getPictures().length>0){
                finishPicture= FileUtils.upload(workScheduleDetailDto.getPictures(),"pictures").get(0);
            }
            workScheduleDetailDto.setFinishPicture(finishPicture);
        }
        int result=workScheduleDao.updateScheduleDetail(workScheduleDto.getWorkScheduleDetailDtoList());
        return result;
    }

    /**
     * 员工生成日工作计划
     * @param params
     * @return
     */
    @Transactional
    public int newSchedule(Map<String,Object> params){
        Integer userId=(Integer) params.get("userId");
        List<WorkPool> _workPoolList= (List<WorkPool>) params.get("workPoolList");
        if(_workPoolList==null||_workPoolList.size()<=0)return 0;
        List<WorkPool> workPoolList=workDao.queryWorkPool(_workPoolList);
        WorkSchedule workSchedule=new WorkSchedule();
        workSchedule.setUserId(userId);
        Integer scheduleId=workScheduleDao.queryTodayScheduleId(userId);
        if(scheduleId==null) {
            workScheduleDao.addSchedule(workSchedule);
            scheduleId = workSchedule.getId();
        }
        int result=0;
        result=workScheduleDao.addScheduleDetail(workPoolList,scheduleId);
        return result;
    }

    /**
     * 领导查询部门员工的工作计划
     * @param params
     * @return
     */
    public Map<String,Object> leaderQuerySchedule(Map<String,Object> params){
        if(params.get("date")==null){
            LocalDate localDate=LocalDate.now();
            params.put("date",localDate.toString());
        }
        WorkScheduleDto workScheduleDto=workScheduleDao.leaderQuerySchedule(params);
        Map<String,Object> result=new HashMap<>();
        if(workScheduleDto==null){
            result.put("data",null);
            result.put("total",0);
            return result;
        }
        params.put("scheduleId",workScheduleDto.getId());
        List<WorkScheduleDetailDto> workScheduleDetailDtoList=workScheduleDao.leaderQueryScheduleDetail(params);
        workScheduleDto.setWorkScheduleDetailDtoList(workScheduleDetailDtoList);
        result.put("data",workScheduleDto);
        result.put("total",workScheduleDetailDtoList.size());
        result.put("all",workScheduleDao.leaderQueryScheduleDetailCount(params));
        return result;
    }
}
