package com.nankingdata.yc.work.services;

import com.nankingdata.yc.work.models.dao.UserDao;
import com.nankingdata.yc.work.models.dao.WorkScheduleDao;
import com.nankingdata.yc.work.models.domain.Department;
import com.nankingdata.yc.work.models.domain.WorkAssess;
import com.nankingdata.yc.work.models.dao.AssessDao;
import com.nankingdata.yc.work.models.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


@Service
public class AssessService {
    @Autowired
    private AssessDao assessDao;
    @Autowired
    private WorkScheduleDao workScheduleDao;
    @Autowired
    private UserDao userDao;
    /**
     * 考核时查询所有工作
     * @param params
     * @return
     */
    public Map<String,Object> queryOtherDeptAssess(Map<String,Object> params) throws ParseException {
        List<DepartmentDto> departmentDtoList=userDao.queryLeadDepartment(params);
        List<Integer> departmentIds=new ArrayList<>();
        if(departmentDtoList!=null&&departmentDtoList.size()>0){
            for(DepartmentDto departmentDto:departmentDtoList){
                departmentIds.add(departmentDto.getId());
            }
        }
        if(departmentIds.size()>0)params.put("departmentIds",departmentIds);
        List<ShowAssessDto> showAssessDtoList=assessDao.queryOtherDeptAssess(params);
        Map<String,Object> result=new HashMap<>();
        if(showAssessDtoList==null||showAssessDtoList.size()<=0){
            result.put("data",null);
            result.put("total",0);
            result.put("all",0);
            return result;
        }
        for(ShowAssessDto showAssessDto:showAssessDtoList){
            float workEfficiency=0;
            if(showAssessDto.getFinishTime()!=null){
                SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date beginTime=simpleDateFormat.parse(showAssessDto.getBeginTime());
                Date finishTime=simpleDateFormat.parse(showAssessDto.getFinishTime());
                workEfficiency=(float)showAssessDto.getWorkMinutes()/((finishTime.getTime()-beginTime.getTime())/60000);
            }
            showAssessDto.setWorkEfficiency(workEfficiency);
        }
        result.put("data",showAssessDtoList);
        result.put("total",showAssessDtoList.size());
        result.put("all",assessDao.queryOtherDeptAssessCount(params));
        return result;
    }

    /**
     * 查询全公司的待考核工作
     * @param params
     * @return
     */
    public Map<String,Object> queryCompanyAssessWork(Map<String,Object> params){
        /*得到该员工存在未考核工作的所有工作计划*/
        params.put("isAssess",0);
        List<WorkScheduleDto> workScheduleDtoList=workScheduleDao.querySchedulesHasAssess(params);
        Map<String,Object> result=new HashMap<>();
        if(workScheduleDtoList==null||workScheduleDtoList.size()<=0){
            result.put("data",null);
            result.put("total",0);
            result.put("all",0);
            return result;
        }
        /*得到所有的日计划id*/
        List<Integer> scheduleIds=new ArrayList<>();
        for(WorkScheduleDto workScheduleDto:workScheduleDtoList){
            scheduleIds.add(workScheduleDto.getId());
        }
        params.put("scheduleIds",scheduleIds);
        params.remove("pageStart");
        params.remove("pageSize");
        List<ShowAssessDto> showAssessDtoList=assessDao.queryAllAssess(params);
        for(WorkScheduleDto workScheduleDto:workScheduleDtoList){
            List<ShowAssessDto> showAssessDtoList1=new ArrayList<>();
            for(ShowAssessDto showAssessDto:showAssessDtoList){
                if(showAssessDto.getScheduleId().equals(workScheduleDto.getId())){
                    showAssessDtoList1.add(showAssessDto);
                }
            }
            workScheduleDto.setShowAssessDtoList(showAssessDtoList1);
        }
        result.put("data",workScheduleDtoList);
        result.put("total",workScheduleDtoList.size());
        result.put("all",workScheduleDao.querySchedulesHasAssessCount(params));
        return result;
    }
    /**
     * 领导考核工作
     * @param workAssessList
     * @param assessUserId
     * @return
     */
    @Transactional
    public int assessWork(List<WorkAssess> workAssessList, Integer assessUserId){
        for(WorkAssess workAssess:workAssessList){
            workAssess.setAssessUserId(assessUserId);
        }
        return assessDao.assessWork(workAssessList);
    }

    /**
     * 领导查询考核记录
     * @param params
     * @return
     */
    public Map<String,Object> queryAssessRecords(Map<String,Object> params) throws Exception {
        List<DailyAssessRecordDto> dailyAssessRecordDtoList=assessDao.queryDateHasAssessRecords(params);
        Map<String,Object> result=new HashMap<>();
        if(dailyAssessRecordDtoList==null||dailyAssessRecordDtoList.size()<=0){
            result.put("data",null);
            result.put("total",0);
            result.put("all",0);
            return result;
        }
        List<String> dateList=new ArrayList<>();
        for(DailyAssessRecordDto dailyAssessRecordDto:dailyAssessRecordDtoList){
            dateList.add(dailyAssessRecordDto.getDate());
        }
        params.put("dateList",dateList);
        params.remove("pageStart");
        params.remove("pageSize");
        List<AssessRecordDto> assessRecordDtoList=assessDao.leaderQueryAssessRecords(params);
        for(DailyAssessRecordDto dailyAssessRecordDto:dailyAssessRecordDtoList){
            List<AssessRecordDto> assessRecordDtoList1=new ArrayList<>();
            for(AssessRecordDto assessRecordDto:assessRecordDtoList){
                if(assessRecordDto.getDate().equals(dailyAssessRecordDto.getDate())){
                    assessRecordDtoList1.add(assessRecordDto);
                }
            }
            dailyAssessRecordDto.setAssessRecordDtoList(assessRecordDtoList1);
        }
        result.put("data",dailyAssessRecordDtoList);
        result.put("total",dailyAssessRecordDtoList.size());
        result.put("all",assessDao.queryDateHasAssessRecordsCount(params));
        return result;
    }

    /**
     * 员工查询所有工作考核情况
     * @param params
     * @return
     */
    public Map<String,Object> employeeQueryAllAssess(Map<String,Object> params)throws Exception{
        List<WorkScheduleDto> workScheduleDtoList=workScheduleDao.querySchedulesHasAssess(params);
        Map<String,Object> result=new HashMap<>();
        if(workScheduleDtoList==null||workScheduleDtoList.size()<=0){
            result.put("data",null);
            result.put("total",0);
            result.put("all",0);
            return result;
        }
        List<Integer> scheduleIds=new ArrayList<>();
        for(WorkScheduleDto workScheduleDto:workScheduleDtoList){
            scheduleIds.add(workScheduleDto.getId());
        }
        params.put("scheduleIds",scheduleIds);
        params.remove("pageStart");
        params.remove("pageSize");
        List<ShowAssessDto> showAssessDtoList=assessDao.queryAllAssess(params);
        for(WorkScheduleDto workScheduleDto:workScheduleDtoList){
            List<ShowAssessDto> showAssessDtoList1=new ArrayList<>();
            for(ShowAssessDto showAssessDto:showAssessDtoList){
                if(showAssessDto.getScheduleId().equals(workScheduleDto.getId())){
                    showAssessDtoList1.add(showAssessDto);
                }
            }
            workScheduleDto.setShowAssessDtoList(showAssessDtoList1);
        }
        for(ShowAssessDto showAssessDto:showAssessDtoList){
            Float workEfficiency=null;
            if(showAssessDto.getFinishTime()!=null){
                SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date beginTime=simpleDateFormat.parse(showAssessDto.getBeginTime());
                Date finishTime=simpleDateFormat.parse(showAssessDto.getFinishTime());
                if((finishTime.getTime()-beginTime.getTime())==0){
                    workEfficiency=1f;
                }else {
                    workEfficiency = (float) showAssessDto.getWorkMinutes() / ((finishTime.getTime() - beginTime.getTime()) / 60000);
                }
                showAssessDto.setWorkEfficiency(workEfficiency);
            }else{
                showAssessDto.setWorkEfficiency(null);
            }
        }
        result.put("data",workScheduleDtoList);
        result.put("total",workScheduleDtoList.size());
        result.put("all",workScheduleDao.querySchedulesHasAssessCount(params));
        return result;
    }

    /**
     * 批量考核员工的工作
     * @param params
     * @return
     */
    @Transactional
    public int assessEmployeee(Map<String,Object> params){
        Integer[] employeeIds= (Integer[]) params.get("employeeIds");
        if(employeeIds==null){
            return 0;
        }
        Integer assessUserId= (Integer) params.get("assessUserId");
        String assessGrade= (String) params.get("assessGrade");
        String assessDesc= (String) params.get("assessDesc");
        params.remove("assessGrade");
        params.put("isAssess",0);
        List<ShowAssessDto> showAssessDtoList=assessDao.queryAllAssess(params);
        if(showAssessDtoList!=null&&showAssessDtoList.size()>=0){
            List<WorkAssess> workAssessList=new ArrayList<>();
            for(ShowAssessDto showAssessDto:showAssessDtoList){
                WorkAssess workAssess=new WorkAssess();
                workAssess.setAssessUserId(assessUserId);
                workAssess.setAssessGrade(assessGrade);
                workAssess.setAssessDesc(assessDesc);
                workAssess.setScheduleDetailId(showAssessDto.getId());
                workAssessList.add(workAssess);
            }
            return assessDao.assessWork(workAssessList);
        }
        return 0;
    }
}
