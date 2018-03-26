package com.ly.ems.model.base.job;

import com.ly.ems.core.mybatis.pagehelper.PageableModel;
import com.ly.ems.model.common.constant.YesNoEnum;

/**
 * Created by tidus on 2017/10/31.
 */
public class JobConditions extends PageableModel {

    /** 工种名称*/
    private String jobName;

    /** 是否特殊工种*/
    private YesNoEnum isSpec;


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
}
