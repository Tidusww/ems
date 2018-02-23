package com.ly.ems.model.base.project;

import com.ly.ems.model.BaseModel;
import com.ly.ems.model.common.constant.StatusEnum;

import java.util.Date;

/**
 * Created by tidus on 2017/11/18.
 */
public class Project extends BaseModel{


    private String projectName;
    private Date startDate;
    private Date endDate;


    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}
