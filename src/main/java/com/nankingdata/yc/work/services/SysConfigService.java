package com.nankingdata.yc.work.services;

import com.nankingdata.yc.work.models.domain.SysConfig;
import com.nankingdata.yc.work.models.dao.SysConfigDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SysConfigService {
    @Autowired
    private SysConfigDao sysConfigDao;
    public Map<String,Object> querySysCofnig(String type){
        List<SysConfig> sysConfigList=sysConfigDao.querySysConfigListByType(type);
        Map<String,Object> result=new HashMap<>();
        result.put("data",sysConfigList);
        result.put("total",sysConfigList.size());
        return result;
    }
}
