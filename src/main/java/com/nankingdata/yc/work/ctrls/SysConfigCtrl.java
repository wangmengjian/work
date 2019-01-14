package com.nankingdata.yc.work.ctrls;

import com.nankingdata.yc.work.common.Constants;
import com.nankingdata.yc.work.common.Result;
import com.nankingdata.yc.work.services.SysConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/work/sysConfig")
public class SysConfigCtrl extends BaseCtrl{
    @Autowired
    private SysConfigService sysConfigService;
    @GetMapping("/workFrom")
    public Result queryWorkFrom(){
        return this.send(sysConfigService.querySysCofnig(Constants.workFrom));
    }
    @GetMapping("/failReason")
    public Result queryFailReason(){
        return this.send(sysConfigService.querySysCofnig(Constants.failReason));
    }
    @GetMapping("/finishStatus")
    public Result queryFinishStatus(){
        return this.send(sysConfigService.querySysCofnig(Constants.finishStatus));
    }
    @GetMapping("/auditStatus")
    public Result queryAuditStatus(){
        return this.send(sysConfigService.querySysCofnig(Constants.auditStatus));
    }
    @GetMapping("/workPriority")
    public Result queryWorkPriority(){
        return this.send(sysConfigService.querySysCofnig(Constants.workPriority));
    }
}
