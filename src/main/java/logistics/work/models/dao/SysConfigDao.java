package logistics.work.models.dao;

import logistics.work.models.domain.SysConfig;

import java.util.List;

public interface SysConfigDao {
    public List<SysConfig> querySysConfigListByType(String type);
}
