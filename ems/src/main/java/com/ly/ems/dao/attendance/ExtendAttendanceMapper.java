package com.ly.ems.dao.attendance;

import com.ly.ems.model.attendance.Attendance;
import com.ly.ems.model.attendance.AttendanceConditions;
import com.ly.ems.model.attendance.AttendanceVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by tidus on 2017/11/19.
 */
public interface ExtendAttendanceMapper {

    /**
     * 条件查询(可分页)
     *
     * @param conditions
     * @return
     */
    List<AttendanceVo> getAttendancesByConditions(@Param("conditions")AttendanceConditions conditions,
                                                  @Param("attendanceTableName")String attendanceTableName);

    /**
     * 是否存在指定月份的考勤表
     * @param attendanceTableName
     * @return
     */
    int isExistAttendanceTable(@Param("attendanceTableName")String attendanceTableName);

    /**
     * 创建指定月份的考勤表
     * @param attendanceTableName
     */
    void createAttendanceTable(@Param("attendanceTableName")String attendanceTableName);

    /**
     * 批量插入考勤信息
     * @param list
     */
    void batchInsert(@Param("attendanceTableName")String attendanceTableName, @Param("list") List<Attendance> list);
}
