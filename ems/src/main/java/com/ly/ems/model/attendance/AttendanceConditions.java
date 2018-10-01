package com.ly.ems.model.attendance;

import com.ly.ems.core.mybatis.pagehelper.PageableModel;
import com.ly.ems.core.springmvc.databinder.ParamName;

import java.util.Date;

/**
 * Created by tidus on 2017/10/31.
 */
public class AttendanceConditions extends AttendanceVo {

    /** 考勤月份 例如:201801*/
    @ParamName("monthSelect")
    private Date attendanceMonth;

    public Date getAttendanceMonth() {
        return attendanceMonth;
    }

    public void setAttendanceMonth(Date attendanceMonth) {
        this.attendanceMonth = attendanceMonth;
    }
}
