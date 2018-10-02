package com.ly.ems.model.salary;

import com.ly.ems.core.springmvc.databinder.ParamName;

import java.util.Date;

/**
 * Created by tidus on 2018/10/2.
 */
public class SalaryCondition extends SalaryVo{


    /** 工资月份 例如:201801*/
    @ParamName("monthSelect")
    private Date month;

    public Integer current;
    public Integer pageSize;

    public Date getMonth() {
        return month;
    }

    public void setMonth(Date attendanceMonth) {
        this.month = attendanceMonth;
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
