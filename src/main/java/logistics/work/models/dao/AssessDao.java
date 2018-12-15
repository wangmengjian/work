package logistics.work.models.dao;

import logistics.work.models.dto.ShowAssessDto;

import java.util.List;
import java.util.Map;

public interface AssessDao {
    public List<ShowAssessDto> queryAllAssess(Map<String,Object> params);
    public int queryAllAssessCount(Map<String,Object> params);
}
