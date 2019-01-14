package com.nankingdata.yc.work.models.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.nankingdata.yc.work.models.domain.WorkPool;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

public class UserDto {
    private static final long serialVersionUID = 4125096758372084309L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotNull(message = "电话不能为空")
    private String phone;
    /**
     * 昵称
     */
    private String nickname;
    /**
     * 真实名字
     */
    private String realName;
    /**
     * 年龄
     */
    private Integer age;
    /**
     * 公司编号
     */
    private Integer companyId;
    /**
     * 公司名字
     */
    private String companyName;

    /**
     * 部门编号
     */
    private Integer departmentId;
    /**
     * 部门名称
     */
    private String departmentName;
    /**
     * 上级领导id
     */
    private Integer leaderId;
    /**
     * 上级领导名字
     */
    private String leaderName;

    private List<WorkPool> workPoolList;
    private List<WorkScheduleDto> workScheduleDtoList;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }


    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }


    public Integer getLeaderId() {
        return leaderId;
    }

    public void setLeaderId(Integer leaderId) {
        this.leaderId = leaderId;
    }



    public String getLeaderName() {
        return leaderName;
    }

    public void setLeaderName(String leaderName) {
        this.leaderName = leaderName;
    }

    public List<WorkScheduleDto> getWorkScheduleDtoList() {
        return workScheduleDtoList;
    }

    public void setWorkScheduleDtoList(List<WorkScheduleDto> workScheduleDtoList) {
        this.workScheduleDtoList = workScheduleDtoList;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }


    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public Integer getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }

    public List<WorkPool> getWorkPoolList() {
        return workPoolList;
    }

    public void setWorkPoolList(List<WorkPool> workPoolList) {
        this.workPoolList = workPoolList;
    }
}
