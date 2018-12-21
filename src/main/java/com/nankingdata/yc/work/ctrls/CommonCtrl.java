package com.nankingdata.yc.work.ctrls;

import com.nankingdata.yc.work.common.FileUtils;
import com.nankingdata.yc.work.common.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/work/common")
public class CommonCtrl extends BaseCtrl{
    @GetMapping("/noSign")
    public Result noSign(){
        return this.send(-2,"未登录");
    }
    @PostMapping("/upload")
    public Result upload(MultipartFile file){
        try{
            return this.send(FileUtils.upload(file));
        }catch (Exception e){
            return this.send(-1,"操作失败");
        }
    }
}
