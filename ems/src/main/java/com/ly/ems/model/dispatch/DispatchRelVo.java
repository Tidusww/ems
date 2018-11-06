package com.ly.ems.model.dispatch;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 * Created by tidus on 2018/9/23.
 */
public class DispatchRelVo extends DispatchRel {
    private String companyName;
    private String groupName;
    private String projectName;

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }


    @Override
    @JsonFormat(pattern = "yyyy-MM", locale = "zh" , timezone="GMT+8")
    public Date getStartDate() {
        return super.getStartDate();
    }
    @Override
    @JsonFormat(pattern = "yyyy-MM", locale = "zh" , timezone="GMT+8")
    public Date getEndDate() {
        return super.getEndDate();
    }
}
