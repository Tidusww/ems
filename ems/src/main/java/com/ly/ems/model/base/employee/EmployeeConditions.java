package com.ly.ems.model.base.employee;

import com.ly.ems.core.mybatis.pagehelper.PageableModel;
import com.ly.ems.core.springmvc.databinder.ParamName;

/**
 * Created by tidus on 2017/10/31.
 */
public class EmployeeConditions extends PageableModel {

    @ParamName("name")
    private String employeeName;
    /** 地区*/
    private String areaId;
    /** 班组*/
    private String groupId;


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
}
