package com.ly.ems.model.base.group;

/**
 * Created by tidus on 2018/9/10.
 */
public class GroupDTO extends Group {
    private String employeeName;
    private String phone;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }
}
