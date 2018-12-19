package com.nankingdata.yc.work.ctrls;

import com.nankingdata.yc.work.common.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/common")
public class CommonCtrl extends BaseCtrl{
    @GetMapping("/noSign")
    public Result noSign(){
        return this.send(-2,"未登录");
    }
}
