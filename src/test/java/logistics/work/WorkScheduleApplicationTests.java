package logistics.work;

import logistics.work.models.dao.WorkScheduleDao;
import logistics.work.models.dto.WorkScheduleDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WorkScheduleApplicationTests {
    @Test
    public void contextLoads() {
        ApplicationContext applicationContext=SpringApplication.run(WorkScheduleApplication.class);
        WorkScheduleDao workScheduleDao=applicationContext.getBean(WorkScheduleDao.class);
        Map<String,Object> params=new HashMap<>();
        params.put("userId",1);
        params.put("date","2018-12-03");
        WorkScheduleDto workScheduleDto=workScheduleDao.queryWorkScheduleByDate(params);
        System.out.println(workScheduleDto.getDate());
    }

}
