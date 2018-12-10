package logistics.work.ctrls;

import logistics.work.common.Constants;
import logistics.work.common.Result;
import logistics.work.services.SysConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/work/sysConfig")
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
}
