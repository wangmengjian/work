package com.nankingdata.yc.work.services;

import com.nankingdata.yc.common.Users;
import com.nankingdata.yc.work.common.FileUtils;
import com.nankingdata.yc.work.common.MyException;
import com.nankingdata.yc.work.common.ParamUtils;
import com.nankingdata.yc.work.common.Result;
import com.nankingdata.yc.work.models.dao.AssessDao;
import com.nankingdata.yc.work.models.dao.UserDao;
import com.nankingdata.yc.work.models.domain.WorkAssess;
import com.nankingdata.yc.work.models.domain.WorkSchedule;
import com.nankingdata.yc.work.models.domain.WorkScheduleDetail;
import com.nankingdata.yc.work.models.dto.*;
import com.nankingdata.yc.work.models.dao.WorkDao;
import com.nankingdata.yc.work.models.dao.WorkScheduleDao;
import com.nankingdata.yc.work.models.domain.WorkPool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

@Service
public class WorkScheduleService {
    @Autowired
    private WorkScheduleDao workScheduleDao;
    @Autowired
    private WorkDao workDao;
    @Autowired
    private AssessDao assessDao;
    @Autowired
    private UserDao userDao;

    /**
     * 员工查询日计划
     *
     * @param params
     * @return
     */
    public Map<String, Object> querySchedule(Map<String, Object> params) {
        if (params.get("date") == null) {
            LocalDate localDate = LocalDate.now();
            params.put("date", localDate.toString());
        }
        WorkScheduleDto workScheduleDto = workScheduleDao.queryWorkScheduleByDate(params);
        Map<String, Object> result = new HashMap<>();
        if (workScheduleDto == null) {
            result.put("data", null);
            result.put("total", 0);
            result.put("all", 0);
            return result;
        }
        params.put("scheduleId", workScheduleDto.getId());
        List<WorkScheduleDetailDto> workScheduleDetailDtoList = workScheduleDao.queryWorkScheduleDetail(params);
        workScheduleDto.setWorkScheduleDetailDtoList(workScheduleDetailDtoList);
        result.put("data", workScheduleDto);
        result.put("total", workScheduleDetailDtoList.size());
        result.put("all", workScheduleDao.queryWorkScheduleDetailCount(params));
        return result;
    }

    /**
     * 员工查询所有工作计划
     *
     * @param params
     * @return
     */
    public Map<String, Object> employeeQuerySchedules(Map<String, Object> params) {
        List<WorkScheduleDto> workScheduleDtoList = workScheduleDao.querySchedules(params);
        List<Integer> scheduleIds = new ArrayList<>();
        for (WorkScheduleDto workScheduleDto : workScheduleDtoList) {
            scheduleIds.add(workScheduleDto.getId());
        }
        params.put("scheduleIds", scheduleIds);
        List<WorkScheduleDetailDto> workScheduleDetailDtoList = workScheduleDao.queryWorkScheduleDetail(params);
        for (WorkScheduleDto workScheduleDto : workScheduleDtoList) {
            List<WorkScheduleDetailDto> workScheduleDetailDtoList1 = new ArrayList<>();
            for (WorkScheduleDetailDto workScheduleDetailDto : workScheduleDetailDtoList) {
                if (workScheduleDetailDto.getScheduleId().equals(workScheduleDto.getId())) {
                    workScheduleDetailDtoList1.add(workScheduleDetailDto);
                }
            }
            workScheduleDto.setWorkScheduleDetailDtoList(workScheduleDetailDtoList1);
        }
        Map<String, Object> result = new HashMap<>();
        result.put("data", workScheduleDtoList);
        result.put("total", workScheduleDtoList.size());
        result.put("all", workScheduleDao.querySchedulesCount(params));
        return result;
    }

    /**
     * 员工提交日工作计划
     *
     * @param workScheduleDto
     * @return
     * @throws Exception
     */
    @Transactional
    public int submitSchedule(WorkScheduleDto workScheduleDto) throws Exception {
        /*判断是否有进行中*/
        Map<String, Object> params = new HashMap<>();
        params.put("scheduleId", workScheduleDto.getId());
        List<WorkScheduleDetailDto> workScheduleDetailDtoList = workScheduleDao.queryWorkScheduleDetail(params);
        if (workScheduleDetailDtoList.size() > 0) {
            for (WorkScheduleDetailDto workScheduleDetailDto : workScheduleDetailDtoList) {
                if (workScheduleDetailDto.getFinishStatus().equals("进行中")||workScheduleDetailDto.getFinishStatus().equals("未开始")) {
                    throw new MyException("请填写完成情况");
                }
            }
        }

        int result1 = workScheduleDao.updateSchedule(workScheduleDto.getId());
        if (result1 == 0) throw new RuntimeException("所选计划不允许提交");
        return result1;
    }

    /**
     * 员工保存工作项的完成状态
     *
     * @param workScheduleDetailDto
     * @return
     */
    @Transactional
    public int saveWorkFinishStatus(WorkScheduleDetailDto workScheduleDetailDto) {
        List<WorkScheduleDetailDto> workScheduleDetailDtoList = new ArrayList();
        workScheduleDetailDtoList.add(workScheduleDetailDto);
        return workScheduleDao.updateScheduleDetail(workScheduleDetailDtoList);
    }

    /**
     * 员工生成日工作计划
     *
     * @param params
     * @return
     */
    @Transactional
    public int newSchedule(Map<String, Object> params) {
        Integer userId = (Integer) params.get("userId");
        List<WorkPool> _workPoolList = (List<WorkPool>) params.get("workPoolList");
        if (_workPoolList == null || _workPoolList.size() <= 0) return 0;
        List<WorkPool> workPoolList = workDao.queryWorkPool(_workPoolList);
        WorkSchedule workSchedule = new WorkSchedule();
        workSchedule.setUserId(userId);
        String date = (String) params.get("date");
        if (date == null) params.put("date", new Date());
        Integer scheduleId = null;
        scheduleId = workScheduleDao.queryScheduleId(params);
        if (scheduleId == null) {
            workScheduleDao.addSchedule(workSchedule);
            scheduleId = workSchedule.getId();
        }
        int result = 0;
        List<WorkScheduleDetail> workScheduleDetailList = new ArrayList<>();
        for (WorkPool workPool : workPoolList) {
            WorkScheduleDetail workScheduleDetail = new WorkScheduleDetail();
            workScheduleDetail.setScheduleId(scheduleId);
            workScheduleDetail.setWorkId(workPool.getId());
            workScheduleDetail.setWorkFrom(workPool.getWorkFrom());
            workScheduleDetail.setFinishStatus("unstart");
            workScheduleDetail.setAllotUserId(userId);
            workScheduleDetailList.add(workScheduleDetail);
        }
        result = workScheduleDao.addScheduleDetail(workScheduleDetailList);
        return result;
    }

    /**
     * 领导查询部门员工的工作计划
     *
     * @param params
     * @return
     */
    public Map<String, Object> leaderQuerySchedule(Map<String, Object> params) {
        if (params.get("date") == null) {
            LocalDate localDate = LocalDate.now();
            params.put("date", localDate.toString());
        }
        List<WorkScheduleDto> workScheduleDtoList = workScheduleDao.querySchedules(params);
        params.remove("pageStart");
        params.remove("pageSize");
        List<WorkScheduleDetailDto> workScheduleDetailDtoList = workScheduleDao.queryWorkScheduleDetail(params);
        Map<String, Object> result = new HashMap<>();
        if (workScheduleDtoList == null || workScheduleDtoList.size() <= 0) {
            result.put("data", null);
            result.put("total", 0);
            result.put("all", 0);
            return result;
        }
        if (workScheduleDetailDtoList != null && workScheduleDetailDtoList.size() > 0) {
            for (WorkScheduleDto workScheduleDto : workScheduleDtoList) {
                List<WorkScheduleDetailDto> workScheduleDetailDtoList1 = new ArrayList<>();
                for (WorkScheduleDetailDto workScheduleDetailDto : workScheduleDetailDtoList) {
                    if (workScheduleDetailDto.getScheduleId().equals(workScheduleDto.getId())) {
                        workScheduleDetailDtoList1.add(workScheduleDetailDto);
                    }
                }
                workScheduleDto.setWorkScheduleDetailDtoList(workScheduleDetailDtoList1);
            }
        }

        result.put("data", workScheduleDtoList);
        result.put("total", workScheduleDtoList.size());
        result.put("all", workScheduleDao.querySchedulesCount(params));
        return result;
    }

    /**
     * 员工从工作计划中移除工作项
     *
     * @param id
     * @return
     */
    @Transactional
    public int employeeRemoveWork(Integer id) throws Exception {
        WorkAssess workAssess = assessDao.queryOneAssessRecordByDetailId(id);
        if (workAssess != null) {
            throw new MyException("该项工作已经被考核，无法删除");
        }

        return workScheduleDao.employeeRemoveWork(id);
    }

    /**
     * 员工追加历史工作计划
     *
     * @param historyWorkDto
     * @return
     */
    @Transactional
    public int addHistoryWork(HistoryWorkDto historyWorkDto, Users user) {
        /*判断当天是否有计划*/
        Integer scheduleId = historyWorkDto.getScheduleId();
        if (scheduleId == null) {
            WorkSchedule workSchedule = new WorkSchedule();
            workSchedule.setUserId(user.getId());
            workSchedule.setDate(historyWorkDto.getDate());
            workScheduleDao.addSchedule(workSchedule);
            scheduleId = workSchedule.getId();
        }
        WorkScheduleDetail workScheduleDetail = new WorkScheduleDetail();
        WorkPool workPool = workDao.queryWorkById(historyWorkDto.getId());
        workScheduleDetail.setScheduleId(scheduleId);
        workScheduleDetail.setWorkId(historyWorkDto.getId());
        workScheduleDetail.setWorkFrom(workPool.getWorkFrom());
        workScheduleDetail.setBeginTime(historyWorkDto.getBeginTime());
        workScheduleDetail.setFinishTime(historyWorkDto.getFinishTime());
        workScheduleDetail.setFinishPicture(historyWorkDto.getFinishPicture());
        workScheduleDetail.setFinishCondition(historyWorkDto.getFinishCondition());
        workScheduleDetail.setFinishFeedback(historyWorkDto.getFinishFeedback());
        workScheduleDetail.setFinishStatus(historyWorkDto.getFinishStatus());
        workScheduleDetail.setAllotUserId(user.getId());
        List<WorkScheduleDetail> workScheduleDetailList = new ArrayList<>();
        workScheduleDetailList.add(workScheduleDetail);
        return workScheduleDao.addScheduleDetail(workScheduleDetailList);
    }

    /**
     * 查询员工的过往计划
     * @param params
     * @return
     */
    public Map<String,Object> queryEmployeeSchedules(Map<String,Object> params){
        List<WorkScheduleDto> workScheduleDtoList = workScheduleDao.querySchedules(params);
        params.remove("pageStart");
        params.remove("pageSize");
        List<WorkScheduleDetailDto> workScheduleDetailDtoList = workScheduleDao.queryWorkScheduleDetail(params);
        Map<String, Object> result = new HashMap<>();
        if (workScheduleDtoList == null || workScheduleDtoList.size() <= 0) {
            result.put("data", null);
            result.put("total", 0);
            result.put("all", 0);
            return result;
        }
        if (workScheduleDetailDtoList != null && workScheduleDetailDtoList.size() > 0) {
            for (WorkScheduleDto workScheduleDto : workScheduleDtoList) {
                List<WorkScheduleDetailDto> workScheduleDetailDtoList1 = new ArrayList<>();
                for (WorkScheduleDetailDto workScheduleDetailDto : workScheduleDetailDtoList) {
                    if (workScheduleDetailDto.getScheduleId().equals(workScheduleDto.getId())) {
                        workScheduleDetailDtoList1.add(workScheduleDetailDto);
                    }
                }
                workScheduleDto.setWorkScheduleDetailDtoList(workScheduleDetailDtoList1);
            }
        }

        result.put("data", workScheduleDtoList);
        result.put("total", workScheduleDtoList.size());
        result.put("all", workScheduleDao.querySchedulesCount(params));
        return result;
    }
}
