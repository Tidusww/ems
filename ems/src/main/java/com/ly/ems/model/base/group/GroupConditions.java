package com.ly.ems.model.base.group;

import com.ly.ems.core.mybatis.pagehelper.PageableCondition;
import com.ly.ems.core.mybatis.pagehelper.PageableCondition;

/**
 * Created by tidus on 2017/10/31.
 */
public class GroupConditions extends PageableCondition {


    /** 开始结束时间*/
//    private Date orderStartDate;
//    private Date orderEndDate;

    /** 班组名称*/
    private String groupName;

    /** 组长姓名*/
    private String employeeName;

    /** 电话*/
    private String phone;


    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
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
