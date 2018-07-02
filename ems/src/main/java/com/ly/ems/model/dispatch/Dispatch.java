package com.ly.ems.model.dispatch;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ly.ems.core.mybatis.pagehelper.PageableModel;

import java.util.Date;

/**
 * Created by tidus on 2018/7/2.
 */
public class Dispatch extends PageableModel {

    private Integer groupId;
    private String groupName;

    private Integer companyId;
    private String companyName;

    private Integer projectId;
    private String projectName;

    private Date startDate;
    private Date endDate;

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

    @JsonFormat(pattern = "yyyy-MM", locale = "zh" , timezone="GMT+8")
    public Date getStartDate() {
        return startDate;
    }
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    @JsonFormat(pattern = "yyyy-MM", locale = "zh" , timezone="GMT+8")
    public Date getEndDate() {
        return endDate;
    }
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}
