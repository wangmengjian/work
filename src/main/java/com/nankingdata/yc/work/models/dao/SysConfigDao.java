package com.nankingdata.yc.work.models.dao;

import com.nankingdata.yc.work.models.domain.SysConfig;

import java.util.List;

public interface SysConfigDao {
    public List<SysConfig> querySysConfigListByType(String type);
}
