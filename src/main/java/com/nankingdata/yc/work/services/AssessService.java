package com.nankingdata.yc.work.services;

import com.nankingdata.yc.work.models.domain.WorkAssess;
import com.nankingdata.yc.work.models.dao.AssessDao;
import com.nankingdata.yc.work.models.dto.AssessRecordDto;
import com.nankingdata.yc.work.models.dto.ShowAssessDto;
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
    /**
     * 考核时查询所有工作
     * @param params
     * @return
     */
    public Map<String,Object> queryWork(Map<String,Object> params) throws ParseException {
        Integer deptStatus= (Integer) params.get("deptStatus");
        List<ShowAssessDto> showAssessDtoList=null;
        int all=0;
        if(deptStatus==1){
            showAssessDtoList=assessDao.queryAllAssess(params);
            all=assessDao.queryAllAssessCount(params);
        }else{
            showAssessDtoList=assessDao.queryOtherDeptAssess(params);
            all=assessDao.queryOtherDeptAssessCount(params);
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
        Map<String,Object> result=new HashMap<>();
        result.put("data",showAssessDtoList);
        result.put("total",showAssessDtoList.size());
        result.put("all",all);
        return result;
    }

    /**
     * 查询全公司的待考核工作
     * @param params
     * @return
     */
    public Map<String,Object> queryCompanyAssessWork(Map<String,Object> params){
        List<ShowAssessDto> showAssessDtoList=assessDao.queryAllAssess(params);
        Map<String,Object> result=new HashMap<>();
        result.put("data",showAssessDtoList);
        result.put("total",showAssessDtoList.size());
        result.put("all",assessDao.queryAllAssessCount(params));
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
    public Map<String,Object> leaderQueryAssessRecords(Map<String,Object> params) throws Exception {
        Map<String,Object> result=new HashMap<>();
        List<AssessRecordDto> assessRecordDtoList=assessDao.leaderQueryAssessRecords(params);
        for(AssessRecordDto assessRecordDto:assessRecordDtoList){
            Float workEfficiency=null;
            if(assessRecordDto.getFinishTime()!=null){
                SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date beginTime=simpleDateFormat.parse(assessRecordDto.getBeginTime());
                Date finishTime=simpleDateFormat.parse(assessRecordDto.getFinishTime());
                if((finishTime.getTime()-beginTime.getTime())==0){
                    workEfficiency=1f;
                }else {
                    workEfficiency = (float) assessRecordDto.getWorkMinutes() / ((finishTime.getTime() - beginTime.getTime()) / 60000);
                }
                assessRecordDto.setWorkEfficiency(workEfficiency);
            }else{
                assessRecordDto.setWorkEfficiency(null);
            }
        }
        result.put("data",assessRecordDtoList);
        result.put("total",assessRecordDtoList.size());
        result.put("all",assessDao.leaderQueryAssessRecordsCount(params));
        return result;
    }

    /**
     * 员工查询所有工作考核情况
     * @param params
     * @return
     */
    public Map<String,Object> employeeQueryAllAssess(Map<String,Object> params)throws Exception{
        List<ShowAssessDto> showAssessDtoList=assessDao.employeeQueryAllAssess(params);
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
        Map<String,Object> result=new HashMap<>();
        result.put("data",showAssessDtoList);
        result.put("total",showAssessDtoList.size());
        result.put("all",assessDao.employeeQueryAllAssessCount(params));
        return result;
    }

    /**
     * 考核单个员工工作
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
