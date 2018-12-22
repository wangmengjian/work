package com.nankingdata.yc.work.services;

import com.nankingdata.yc.work.models.domain.WorkAssess;
import com.nankingdata.yc.work.models.dao.AssessDao;
import com.nankingdata.yc.work.models.dto.AssessRecordDto;
import com.nankingdata.yc.work.models.dto.ShowAssessDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class AssessService {
    @Autowired
    private AssessDao assessDao;

    /**
     * 考核时查询所有工作
     * @param params
     * @return
     */
    public Map<String,Object> queryWork(Map<String,Object> params){
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
        Map<String,Object> result=new HashMap<>();
        result.put("data",showAssessDtoList);
        result.put("total",showAssessDtoList.size());
        result.put("all",all);
        return result;
    }

    /**
     * 领导考核工作
     * @param workAssessList
     * @param assessUserId
     * @return
     */
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
    public Map<String,Object> leaderQueryAssessRecords(Map<String,Object> params){
        Map<String,Object> result=new HashMap<>();
        List<AssessRecordDto> assessRecordDtoList=assessDao.leaderQueryAssessRecords(params);
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
    public Map<String,Object> employeQueryAllAssess(Map<String,Object> params){
        List<ShowAssessDto> showAssessDtoList=assessDao.employeeQueryAllAssess(params);
        Map<String,Object> result=new HashMap<>();
        result.put("data",showAssessDtoList);
        result.put("total",showAssessDtoList.size());
        result.put("all",assessDao.employeeQueryAllAssessCount(params));
        return result;
    }

}
