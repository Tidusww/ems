package com.ly.ems.service.attendance;
import com.ly.ems.core.datasource.DetermineDataSource;
import com.ly.ems.core.datasource.MultipleRoutingDataSource;
import com.ly.ems.model.attendance.AttendanceConditions;
import com.ly.ems.model.attendance.AttendanceVo;
import com.ly.ems.model.attendance.Attendance;
import com.ly.ems.model.common.PageableResult;

import java.util.Date;

/**
 * Created by tidus on 2017/9/13.
 */
@DetermineDataSource(MultipleRoutingDataSource.DATA_SOURCE_EMS)
public interface AttendanceService {

    PageableResult<AttendanceVo> getAttendances(AttendanceConditions conditions);

    /**
     * 根据所选月份选择自动随机生成考勤信息
     * @param conditions
     */
    void generateAttendances(AttendanceConditions conditions);

    /**
     * 更新考勤信息
     * @param attendance
     */
    void updateAttendance(Attendance attendance, Date month);


}
