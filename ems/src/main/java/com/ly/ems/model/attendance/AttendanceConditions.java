package com.ly.ems.model.attendance;

import com.ly.ems.core.mybatis.pagehelper.PageableModel;
import com.ly.ems.core.springmvc.databinder.ParamName;

import java.util.Date;

/**
 * Created by tidus on 2017/10/31.
 */
public class AttendanceConditions extends PageableModel {

    /** 考勤月份 例如:201801*/
    @ParamName("monthSelect")
    private Date attendanceMonth;

    private String employeeName;
    /** 地区*/
    private String areaId;
    /** 班组*/
    private String groupId;
    /** 工种*/
    private String jobId;

    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public Date getAttendanceMonth() {
        return attendanceMonth;
    }

    public void setAttendanceMonth(Date attendanceMonth) {
        this.attendanceMonth = attendanceMonth;
    }
}
