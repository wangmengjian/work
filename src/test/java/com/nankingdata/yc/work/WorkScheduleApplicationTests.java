package com.nankingdata.yc.work;

import com.nankingdata.yc.work.models.dao.WorkScheduleDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WorkScheduleApplicationTests {
    @Test
    public void contextLoads() {
        ApplicationContext applicationContext=SpringApplication.run(WorkScheduleApplication.class);
        WorkScheduleDao workScheduleDao= (WorkScheduleDao) applicationContext.getBean(WorkScheduleDao.class);
        Map<String,Object> params=new HashMap<>();
        params.put("date","2019-1-8");
        params.put("userId",1);
        System.out.println(workScheduleDao.queryScheduleId(params));
    }
}
