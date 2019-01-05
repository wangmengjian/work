package com.nankingdata.yc.work.ctrls;

import com.nankingdata.yc.common.Users;
import com.nankingdata.yc.work.common.Result;
import com.nankingdata.yc.work.services.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;
@RequestMapping("/work/data")
@RestController
public class DataCtrl extends BaseCtrl {
    @Autowired
    private DataService dataService;
    @GetMapping("/queryWorkAndAssess")
    public Result countData(@RequestParam(value = "beginTime",required = false)String beginTime,
                            @RequestParam(value = "endTime",required = false)String endTime,
                            @RequestParam(value = "empId",required = false)Integer empId,
                            HttpSession session){
        Users users= (Users) session.getAttribute("user");
        Map<String,Object> params=new HashMap<>();
        if(empId==null)empId=users.getId();
        params.put("beginTime",beginTime);
        params.put("endTime",endTime);
        params.put("empId",empId);
        try{
            return this.send(dataService.countData(params));
        }catch (Exception e){
            return this.send(-1,"操作失败");
        }
    }
}
