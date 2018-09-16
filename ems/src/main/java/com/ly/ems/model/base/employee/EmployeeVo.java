package com.ly.ems.model.base.employee;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ly.ems.model.base.employee.constant.GenderEnum;
import com.ly.ems.model.base.employee.constant.SalaryBankEnum;
import com.ly.ems.model.common.constant.StatusEnum;

import java.util.Date;

/**
 * Created by tidus on 2017/11/19.
 */
public class EmployeeVo extends Employee{

    private String groupName;
    private String jobName;

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getGenderValue() {
        if(super.getGender() == null) {
            return "";
        }
        return super.getGender().getValue();
    }

    public String getSalaryBankValue(){
        if(super.getSalaryBank() == null){
            return "";
        }
        return super.getSalaryBank().getValue();
    }


    @Override
    @JsonFormat(pattern = "yyyy-MM-dd", locale = "zh" , timezone="GMT+8")
    public Date getEntryDate() {
        return super.getEntryDate();
    }
}
