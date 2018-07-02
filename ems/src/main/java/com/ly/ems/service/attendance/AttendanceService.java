package com.ly.ems.service.attendance;
import com.ly.ems.core.datasource.DetermineDataSource;
import com.ly.ems.core.datasource.MultipleRoutingDataSource;
import com.ly.ems.model.attendance.Attendance;
import com.ly.ems.model.attendance.AttendanceConditions;
import com.ly.ems.model.attendance.AttendanceDTO;
import com.ly.ems.model.base.employee.EmployeeDTO;
import com.ly.ems.model.common.PageableResult;
import com.ly.ems.model.condition.ConditionItemDTO;

import java.util.List;

/**
 * Created by tidus on 2017/9/13.
 */
@DetermineDataSource(MultipleRoutingDataSource.DATA_SOURCE_EMS)
public interface AttendanceService {

    PageableResult<AttendanceDTO> getAttendances(AttendanceConditions conditions);

    void saveAttendances(List<Attendance> attendances, String monthString);


}
