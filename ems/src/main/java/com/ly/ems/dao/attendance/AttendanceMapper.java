package com.ly.ems.dao.attendance;

import com.ly.ems.model.attendance.AttendanceConditions;
import com.ly.ems.model.attendance.AttendanceDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by tidus on 2017/11/19.
 */
public interface AttendanceMapper {

    /**
     * 条件查询(可分页)
     *
     * @param conditions
     * @return
     */
    List<AttendanceDTO> getAttendancesByConditions(@Param("conditions")AttendanceConditions conditions,
                                                   @Param("attendanceTableName")String attendanceTableName);

    /**
     *
     * @param attendanceTableName
     * @return
     */
    int isExistAttendanceTable(@Param("attendanceTableName")String attendanceTableName);
    void createAttendanceTable(@Param("attendanceTableName")String attendanceTableName);

}
