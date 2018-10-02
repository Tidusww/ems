package com.ly.ems.model.salary;

import com.ly.ems.core.springmvc.databinder.ParamName;

import java.util.Date;

/**
 * Created by tidus on 2018/10/2.
 */
public class SalaryCondition extends SalaryVo{


    /** 工资月份 例如:201801*/
    @ParamName("monthSelect")
    private Date attendanceMonth;

    public Integer current;
    public Integer pageSize;

    public Date getAttendanceMonth() {
        return attendanceMonth;
    }

    public void setAttendanceMonth(Date attendanceMonth) {
        this.attendanceMonth = attendanceMonth;
    }

    public Integer getCurrent() {
        return current;
    }

    public void setCurrent(Integer current) {
        this.current = current;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
}
