package com.nankingdata.yc.work.services;

import com.nankingdata.yc.common.Users;
import com.nankingdata.yc.work.models.dao.UserDao;
import com.nankingdata.yc.work.models.dao.WorkAuditDao;
import com.nankingdata.yc.work.models.dao.WorkDao;
import com.nankingdata.yc.work.models.dao.WorkScheduleDao;
import com.nankingdata.yc.work.models.domain.WorkAuditDetail;
import com.nankingdata.yc.work.models.domain.WorkPool;
import com.nankingdata.yc.work.models.domain.WorkSchedule;
import com.nankingdata.yc.work.models.domain.WorkScheduleDetail;
import com.nankingdata.yc.work.models.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class WorkService {
    @Autowired
    private WorkDao workDao;
    @Autowired
    private WorkScheduleDao workScheduleDao;
    @Autowired
    private WorkAuditDao workAuditDao;
    @Autowired
    private UserDao userDao;

    /**
     * 查询员工提交的所有工作项的审核记录
     *
     * @param params
     * @return
     */
    public Map<String, Object> queryWork(Map<String, Object> params) {
        List<ShowWorkAuditConditionDto> showWorkAuditConditionDtoList = workDao.querySubmitRecordByPage(params);
        Map<String, Object> result = new HashMap<>();
        result.put("data", showWorkAuditConditionDtoList);
        result.put("total", showWorkAuditConditionDtoList.size());
        result.put("all", workDao.querySubmitRecordCount(params));
        return result;
    }

    /**
     * 员工根据工作项id查询工作
     *
     * @param id
     * @return
     */
    public Map<String, Object> queryWorkById(Integer id) {
        Map<String, Object> result = new HashMap<>();
        result.put("data", workDao.queryWorkById(id));
        return result;
    }

    /**
     * 查询未添加到日计划里的工作
     *
     * @param params
     * @return
     */
    public Map<String, Object> queryUnAddWork(Map<String, Object> params) {
        Map<String, Object> scheduleParams = new HashMap<>();
        Integer scheduleId = (Integer) params.get("scheduleId");
        if (scheduleId == null) {
            scheduleParams.put("date", new Date());
            scheduleParams.put("userId", params.get("userId"));
            scheduleId = workScheduleDao.queryScheduleId(scheduleParams);
        }
        params.put("scheduleId", scheduleId);
        List<WorkPool> workPoolList = workDao.queryUnAddWork(params);
        Map<String, Object> result = new HashMap<>();
        result.put("data", workPoolList);
        result.put("total", workPoolList.size());
        return result;
    }

    /**
     * 查询员工的常规工作项
     *
     * @param params
     * @return
     */
    public Map<String, Object> queryWorkByEmployeeId(Map<String, Object> params) {
        Map<String, Object> result = new HashMap<>();
        List<WorkPool> workPoolList = workDao.queryWorks(params);
        params.put("date",new Date());
        List<WorkScheduleDetailDto> workScheduleDetailDtoList=workScheduleDao.queryWorkScheduleDetail(params);
        if(workPoolList!=null&&workPoolList.size()>0&&workScheduleDetailDtoList!=null&&workPoolList.size()>0){
            for(WorkPool workPool:workPoolList){
                for(WorkScheduleDetailDto workScheduleDetailDto:workScheduleDetailDtoList){
                    if(workPool.getId().equals(workScheduleDetailDto.getWorkId())){
                        workPool.setIsDoing(1);
                    }
                }
            }
        }
        result.put("data", workPoolList);
        result.put("total", workPoolList.size());
        result.put("all", workDao.queryWorksCount(params));
        return result;
    }

    /**
     * 新增员工常规工作项（无需审核）
     *
     * @param workPoolList
     * @return
     */
    @Transactional
    public int addWork(List<WorkPool> workPoolList) throws Exception {
        return workDao.addWork(workPoolList);
    }

    /**
     * 更改工作项（无需审核）
     *
     * @param workPool
     * @return
     */
    @Transactional
    public int updateWork(WorkPool workPool) throws Exception {
        return workDao.updateWork(workPool);
    }

    /**
     * 删除审核记录
     *
     * @param id
     * @return
     */
    @Transactional
    public int deleteAuditRecord(Integer id) {
        WorkAuditDetail workAuditDetail = workAuditDao.queryWorkAuditDetailById(id);
        workAuditDao.deleteAuditDetail(id);
        workAuditDao.deleteAudit(workAuditDetail.getAuditItemId());
        return 1;
    }

    /**
     * 领导分配常规工作项
     *
     * @param workPoolList
     * @return
     */
    @Transactional
    public int allotWork(List<WorkPool> workPoolList, Users users) {
        if (workPoolList == null || workPoolList.size() <= 0) {
            return 0;
        }
        for (WorkPool workPool : workPoolList) {
            workPool.setWorkFrom("w3");
        }
        Integer employeeId = workPoolList.get(0).getUserId();
        Map<String, Object> scheduleParams = new HashMap<>();
        scheduleParams.put("date", new Date());
        scheduleParams.put("userId", employeeId);
        Integer scheduleId = workScheduleDao.queryScheduleId(scheduleParams);
        WorkSchedule workSchedule = new WorkSchedule();
        workSchedule.setUserId(employeeId);

        if (scheduleId == null) {
            workScheduleDao.addSchedule(workSchedule);
            scheduleId = workSchedule.getId();
        }
        /*查询计划中已有的常规工作项*/
        Map<String, Object> params = new HashMap<>();
        params.put("userId", employeeId);
        params.put("scheduleId", scheduleId);
        List<WorkScheduleDetailDto> workScheduleDetailDtoList = workScheduleDao.queryWorkScheduleDetail(params);
        List<WorkPool> notExistWork = new ArrayList<>();
        if (workScheduleDetailDtoList != null && workScheduleDetailDtoList.size() > 0) {
            for (WorkPool workPool : workPoolList) {
                int i = 0;
                for (; i < workScheduleDetailDtoList.size(); i++) {
                    if (workScheduleDetailDtoList.get(i).getWorkId() == workPool.getId()) {
                        break;
                    }
                }
                if (i == workScheduleDetailDtoList.size()) {
                    notExistWork.add(workPool);
                }
            }
        } else {
            notExistWork = workPoolList;
        }
        int result = 0;
        if (notExistWork != null && notExistWork.size() > 0) {
            List<WorkScheduleDetail> workScheduleDetailList = new ArrayList<>();
            for (WorkPool workPool : notExistWork) {
                WorkScheduleDetail workScheduleDetail = new WorkScheduleDetail();
                workScheduleDetail.setWorkId(workPool.getId());
                workScheduleDetail.setScheduleId(scheduleId);
                workScheduleDetail.setWorkFrom(workPool.getWorkFrom());
                workScheduleDetail.setAllotUserId(users.getId());
                workScheduleDetail.setFinishStatus("unstart");
                workScheduleDetailList.add(workScheduleDetail);
            }
            result = workScheduleDao.addScheduleDetail(workScheduleDetailList);
        }
        return result;
    }

    /**
     * 领导新增员工的工作项
     *
     * @param workPool
     * @return
     */
    @Transactional
    public int leaderAddWork(WorkPool workPool, Integer allotUserId) {
        if (workPool == null) return 0;
        else workPool.setAllotUserId(allotUserId);
        List<WorkPool> workPoolList = new ArrayList<>();
        workPoolList.add(workPool);
        int result=workDao.addWork(workPoolList);
        if(workPool.getWorkFrom().equals("w3")){
            return result;
        }
        WorkSchedule workSchedule = new WorkSchedule();
        workSchedule.setUserId(workPool.getUserId());
        Map<String, Object> scheduleParams = new HashMap<>();
        scheduleParams.put("date", new Date());
        scheduleParams.put("userId", workPool.getUserId());
        Integer scheduleId = workScheduleDao.queryScheduleId(scheduleParams);
        if (scheduleId == null) {
            workScheduleDao.addSchedule(workSchedule);
            scheduleId = workSchedule.getId();
        }
        List<WorkScheduleDetail> workScheduleDetailList = new ArrayList<>();
        for (WorkPool workPool1 : workPoolList) {
            WorkScheduleDetail workScheduleDetail = new WorkScheduleDetail();
            workScheduleDetail.setAllotUserId(allotUserId);
            workScheduleDetail.setWorkFrom(workPool1.getWorkFrom());
            workScheduleDetail.setScheduleId(scheduleId);
            workScheduleDetail.setWorkId(workPool1.getId());
            workScheduleDetail.setFinishStatus("unstart");
            workScheduleDetailList.add(workScheduleDetail);
        }
        return workScheduleDao.addScheduleDetail(workScheduleDetailList);
    }

    /**
     * 给多个员工新增工作项
     *
     * @param workPool
     * @return
     */
    @Transactional
    public int addWorkToEmployees(WorkPool workPool, Integer allotUserId) {
        if(workPool==null)return 0;
        Integer[] employeeIds = workPool.getEmployeeIds();
        if (workPool == null || employeeIds == null || employeeIds.length <= 0) return 0;
        List<WorkPool> workPoolList = new ArrayList<>();
        //将工作添加到工作池中
        for (Integer employeeId : employeeIds) {
            WorkPool workPool1 = new WorkPool();
            workPool1.setWorkName(workPool.getWorkName());
            workPool1.setWorkContent(workPool.getWorkContent());
            workPool1.setWorkInstructor(workPool.getWorkInstructor());
            workPool1.setWorkMinutes(workPool.getWorkMinutes());
            workPool1.setWorkFrom(workPool.getWorkFrom());
            workPool1.setUserId(employeeId);
            workPool1.setWorkPriority(workPool.getWorkPriority());
            workPool1.setAllotUserId(allotUserId);
            workPoolList.add(workPool1);
        }
        int result=workDao.addWork(workPoolList);
        //查询已经有工作计划的员工
        List<Integer> employeeIdList = Arrays.asList(employeeIds);
        List<WorkSchedule> workScheduleList = workScheduleDao.queryMoreWorkScheduleByEmployeeIds(employeeIdList);
        //找到未创建工作计划的员工
        List<WorkSchedule> addWorkScheduleList = new ArrayList<>();
        for (Integer employeeId : employeeIds) {
            boolean flag = false;
            for (WorkSchedule workSchedule : workScheduleList) {
                if (workSchedule.getUserId().equals(employeeId)) {
                    flag = true;
                    break;
                }
            }
            if (flag == false) {//无今日工作计划
                WorkSchedule workSchedule = new WorkSchedule();
                workSchedule.setUserId(employeeId);
                addWorkScheduleList.add(workSchedule);
            }
        }
        //批量创建工作计划
        if (addWorkScheduleList.size() > 0) {
            workScheduleDao.addMoreWorkSchedule(addWorkScheduleList);
            for (WorkSchedule workSchedule : addWorkScheduleList) {
                workScheduleList.add(workSchedule);
            }
        }
        List<WorkScheduleDetail> workScheduleDetailList = new ArrayList<>();

        for (WorkPool workPool1 : workPoolList) {
            if(workPool1.getWorkFrom().equals("w2")) {
                WorkScheduleDetail workScheduleDetail = new WorkScheduleDetail();
                workScheduleDetail.setAllotUserId(allotUserId);
                workScheduleDetail.setWorkId(workPool1.getId());
                workScheduleDetail.setWorkFrom(workPool1.getWorkFrom());
                //设置计划id
                for (WorkSchedule workSchedule : workScheduleList) {
                    if (workSchedule.getUserId().equals(workPool1.getUserId())) {
                        workScheduleDetail.setScheduleId(workSchedule.getId());
                        break;
                    }
                }
                workScheduleDetailList.add(workScheduleDetail);
            }
        }
        if(workScheduleDetailList!=null&&workScheduleDetailList.size()>0) {
            workScheduleDao.addWorkScheduleDetails(workScheduleDetailList);
        }
        return result;
    }

    /**
     * 删除员工常规工作项
     *
     * @param workId
     * @return
     */
    @Transactional
    public int deleteWork(Integer workId) {
        return workDao.deleteWork(workId);
    }


    /**
     * 领导批量给员工分配工作
     *
     * @param params
     * @return
     */
    @Transactional
    public int leaderAllotWorks(Map<String, Object> params) {
        Integer employeeId = (Integer) params.get("employeeId");
        Integer[] workIds = (Integer[]) params.get("workIds");
        if (workIds == null || workIds.length <= 0) return 0;
        Map<String, Object> scheduleParams = new HashMap<>();
        scheduleParams.put("date", new Date());
        scheduleParams.put("userId", employeeId);
        Integer scheduleId = workScheduleDao.queryScheduleId(scheduleParams);
        WorkSchedule workSchedule = new WorkSchedule();
        workSchedule.setUserId(employeeId);
        if (scheduleId == null) {
            workScheduleDao.addSchedule(workSchedule);
            scheduleId = workSchedule.getId();
        }
        List<WorkPool> workPoolList = new ArrayList<>();
        for (Integer workId : workIds) {
            WorkPool workPool = new WorkPool();
            workPool.setId(workId);
            workPoolList.add(workPool);
        }
        List<WorkPool> addWorkPoolList = workDao.queryWorkPool(workPoolList);
        List<WorkScheduleDetail> workScheduleDetailList = new ArrayList<>();
        Users user = (Users) params.get("user");
        for (WorkPool workPool : addWorkPoolList) {
            WorkScheduleDetail workScheduleDetail = new WorkScheduleDetail();
            workScheduleDetail.setAllotUserId(user.getId());
            workScheduleDetail.setWorkFrom(workPool.getWorkFrom());
            workScheduleDetail.setScheduleId(scheduleId);
            workScheduleDetail.setWorkId(workPool.getId());
            workScheduleDetail.setFinishStatus("unstart");
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss");
            Date d = new Date();
            String now = simpleDateFormat.format(d);
            workScheduleDetail.setBeginTime(now);
            workScheduleDetailList.add(workScheduleDetail);
        }
        return workScheduleDao.addScheduleDetail(workScheduleDetailList);
    }

    /**
     * 查询公司员工的常规工作
     *
     * @param params
     * @return
     */
    public Map<String, Object> queryCompanyWorks(Map<String, Object> params) {
        Integer companyId = (Integer) params.get("companyId");
        Integer departmentId = (Integer) params.get("departmentId");
        Integer employeeId= (Integer) params.get("employeeId");
        UserDto _userDto=null;
        if(employeeId!=null){
            _userDto=userDao.queryUsersById(employeeId);
            departmentId=_userDto.getDepartmentId();
        }
        List<Integer> departmentIdList = new ArrayList<>();
        if (departmentId != null) departmentIdList.add(departmentId);

        List<DepartmentDto> departmentDtoList = null;
        if (departmentId != null) {
            departmentDtoList = userDao.queryDepartmentsByIds(departmentIdList);
        } else {
            departmentDtoList = userDao.queryDepartmentsInCompany(params);
        }
        Map<String,Object> result=new HashMap<>();
        if(departmentDtoList!=null&&departmentDtoList.size()<=0){
            result.put("total",0);
            result.put("all",0);
            result.put("data",null);
            return result;
        }
        //查询的部门id参数封装
        for (DepartmentDto departmentDto : departmentDtoList) {
            departmentIdList.add(departmentDto.getId());
        }
        params.remove("pageStart");
        params.remove("pageSize");
        List<WorkPool> workPoolList = workDao.queryWorks(params);

        List<UserDto> userDtoList=new ArrayList<>();
        if(employeeId==null) {
            Map<String,Object> tempParams=new HashMap<>();
            tempParams.put("idList",departmentDtoList);
            userDtoList = userDao.queryUsersInDepartment(tempParams);
        }else{
            userDtoList.add(_userDto);
        }
        for(UserDto userDto:userDtoList){
            List<WorkPool> workPoolList1=new ArrayList<>();
            for(WorkPool workPool:workPoolList){
                if(userDto.getId().equals(workPool.getUserId())){
                    workPoolList1.add(workPool);
                }
            }
            userDto.setWorkPoolList(workPoolList1);
        }
        for(DepartmentDto departmentDto:departmentDtoList){
            List<UserDto> userDtoList1=new ArrayList<>();
            for(UserDto userDto:userDtoList){
                if(departmentDto.getId().equals(userDto.getDepartmentId())){
                    userDtoList1.add(userDto);
                }
            }
            departmentDto.setUserDtoList(userDtoList1);
        }
        result.put("data",departmentDtoList);
        result.put("total",departmentDtoList.size());
        if(departmentId==null) {
            result.put("all", userDao.queryDepartmentsCount(params));
        }else{
            result.put("all",1);
        }
        return result;
    }

    /**
     * 开始工作
     * @param id
     * @return
     */
    @Transactional
    public int beginWork(Integer id){
        return workDao.beginWork(id);
    }
}
