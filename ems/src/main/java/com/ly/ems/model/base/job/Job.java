package com.ly.ems.model.base.job;

import com.ly.ems.model.common.constant.StatusEnum;
import com.ly.ems.model.common.constant.YesNoEnum;
import com.ly.ems.model.common.constant.YesNoEnum;

/**
 * Created by tidus on 2017/11/18.
 */
public class Job {



    private Integer id;
    private String jobName;
    private YesNoEnum isSpec;
    private Double salary;
    private StatusEnum status;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public YesNoEnum getIsSpec() {
        return isSpec;
    }

    public void setIsSpec(YesNoEnum isSpec) {
        this.isSpec = isSpec;
    }

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }

    public StatusEnum getStatus() {
        return status;
    }

    public void setStatus(StatusEnum status) {
        this.status = status;
    }
}
