package logistics.work.services;

import logistics.work.models.dao.AssessDao;
import logistics.work.models.domain.WorkAssess;
import logistics.work.models.dto.ShowAssessDto;
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
    public int assessWork(List<WorkAssess> workAssessList,Integer assessUserId){
        for(WorkAssess workAssess:workAssessList){
            workAssess.setAssessUserId(assessUserId);
        }
        return assessDao.assessWork(workAssessList);
    }
}
