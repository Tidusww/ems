package com.ly.ems.model.salary;

import com.ly.ems.model.base.employee.constant.GenderEnum;

/**
 * Created by tidus on 2018/10/2.
 */
public class SalaryVo extends Salary {

    private String numInGroup;
    private String employeeName;
    private String idCard;
    private GenderEnum gender;
    private Integer jobId;
    private String jobName;
    private Integer groupId;
    private String groupName;
    private Integer projectId;
    private String projectName;
    private Integer companyId;
    private String companyName;

    public String getNumInGroup() {
        return numInGroup;
    }

    public void setNumInGroup(String numInGroup) {
        this.numInGroup = numInGroup;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public GenderEnum getGender() {
        return gender;
    }

    public void setGender(GenderEnum gender) {
        this.gender = gender;
    }

    public Integer getJobId() {
        return jobId;
    }

    public void setJobId(Integer jobId) {
        this.jobId = jobId;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
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


    /**
     * 性别
     * @return
     */
    public String getGenderValue() {
        if(this.getGender() == null) {
            return "";
        }
        return this.getGender().getValue();
    }
}
