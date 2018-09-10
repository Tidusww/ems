package com.ly.ems.model.base.group;

import com.ly.ems.core.mybatis.pagehelper.PageableModel;

/**
 * Created by tidus on 2017/11/18.
 */
public class Group extends PageableModel {

    private String groupName;
    private Integer employeeId;


    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public Integer getEmployeeId() {
        return employeeId;
    }

}
