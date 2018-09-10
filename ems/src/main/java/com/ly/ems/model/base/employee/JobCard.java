package com.ly.ems.model.base.employee;

import com.ly.ems.core.mybatis.pagehelper.PageableModel;

import java.util.Date;

/**
 * TODO 工种证
 * Created by tidus on 2018/9/10.
 */
public class JobCard extends PageableModel{
    private String jobCardNo;
    private String jobCardDesc;
    private Date validDate;
    private Integer employeeId;

    public String getJobCardNo() {
        return jobCardNo;
    }

    public void setJobCardNo(String jobCardNo) {
        this.jobCardNo = jobCardNo;
    }

    public String getJobCardDesc() {
        return jobCardDesc;
    }

    public void setJobCardDesc(String jobCardDesc) {
        this.jobCardDesc = jobCardDesc;
    }

    public Date getValidDate() {
        return validDate;
    }

    public void setValidDate(Date validDate) {
        this.validDate = validDate;
    }

    public Integer getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Integer employeeId) {
        this.employeeId = employeeId;
    }
}
