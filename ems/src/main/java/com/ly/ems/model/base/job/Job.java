package com.ly.ems.model.base.job;

import com.ly.ems.core.mybatis.pagehelper.PageableModel;
import com.ly.ems.model.common.constant.YesNoEnum;

/**
 * Created by tidus on 2017/11/18.
 */
public class Job extends PageableModel{

    /** 工种名称*/
    private String jobName;
    /** 是否特殊工种*/
    private YesNoEnum isSpec;
    private Double salary;

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

    //用于前端展示
    public String getIsSpecValue() {
        if(this.getIsSpec() == null){
            return "";
        }

        return this.getIsSpec().getValue();
    }

}
