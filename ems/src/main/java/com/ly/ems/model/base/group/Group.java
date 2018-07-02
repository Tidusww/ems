package com.ly.ems.model.base.group;

import com.ly.ems.core.mybatis.pagehelper.PageableModel;

/**
 * Created by tidus on 2017/11/18.
 */
public class Group extends PageableModel {

    private String groupName;
    private Integer employeeId;
    private String employeeName;
    private String phone;


    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public Integer getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Integer employeeId) {
        this.employeeId = employeeId;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

}
