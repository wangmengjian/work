package com.nankingdata.yc.work.services;

import com.nankingdata.yc.common.Users;
import com.nankingdata.yc.work.models.bo.AssessConditionBo;
import com.nankingdata.yc.work.models.bo.DataBo;
import com.nankingdata.yc.work.models.bo.FinishConditionBo;
import com.nankingdata.yc.work.models.dao.AssessDao;
import com.nankingdata.yc.work.models.dao.UserDao;
import com.nankingdata.yc.work.models.dao.WorkDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DataService {
    @Autowired
    private AssessDao assessDao;
    @Autowired
    private WorkDao workDao;
    @Autowired
    private UserDao userDao;

    /**
     * 统计考核以及完成情况
     * @param params
     * @return
     */
    public Map<String,Object> countData(Map<String,Object> params){
        DataBo dataBo=new DataBo();
        Users users=userDao.queryUsersById((Integer) params.get("empId"));
        dataBo.setEmpName(users.getRealName());
        dataBo.setEmpId((Integer) params.get("empId"));
        List<FinishConditionBo> finishConditionBoList=workDao.queryWorkFinishStatus(params);
        List<AssessConditionBo> assessConditionBoList=assessDao.queryAssessCondition(params);
        dataBo.setAssessCondition(assessConditionBoList);
        dataBo.setFinishCondition(finishConditionBoList);
        Map<String,Object> result=new HashMap<>();
        result.put("data",dataBo);
        result.put("total",1);
        return result;
    }
}
