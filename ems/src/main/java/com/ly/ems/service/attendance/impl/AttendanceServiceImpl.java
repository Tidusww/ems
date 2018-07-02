package com.ly.ems.service.attendance.impl;

import com.github.pagehelper.PageInfo;
import com.ly.ems.core.exception.EMSBusinessException;
import com.ly.ems.dao.attendance.AttendanceMapper;
import com.ly.ems.model.attendance.Attendance;
import com.ly.ems.model.attendance.AttendanceConditions;
import com.ly.ems.model.attendance.AttendanceDTO;
import com.ly.ems.model.common.PageableResult;
import com.ly.ems.service.attendance.AttendanceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by tidus on 2018/1/15.
 */
@Service
public class AttendanceServiceImpl implements AttendanceService {
    private static final Logger LOGGER = LoggerFactory.getLogger(AttendanceServiceImpl.class);

    private static final String ATTENDANCE_TABLE_NAME_PRE = "ly_attendance_";

    @Autowired
    AttendanceMapper attendanceMapper;

    @Override
    public PageableResult<AttendanceDTO> getAttendances(AttendanceConditions conditions) {
        //必须选定月份
        Date attendanceMonth = conditions.getAttendanceMonth();
        if(attendanceMonth == null){
            throw new EMSBusinessException("查看考勤数据前必须先选定月份!");
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
        String attendanceMonthString = sdf.format(attendanceMonth);
        String attendanceTableName = ATTENDANCE_TABLE_NAME_PRE+attendanceMonthString;

        //是否存在
        int exists = attendanceMapper.isExistAttendanceTable(attendanceTableName);
        if(exists <= 0){
            throw new EMSBusinessException(String.format("月份%s的考勤数据不存在，请先生成数据!", attendanceMonthString));
        }

        List<AttendanceDTO> resultList = attendanceMapper.getAttendancesByConditions(conditions, attendanceTableName);
        PageInfo<AttendanceDTO> pageInfo = new PageInfo(resultList);

        return new PageableResult<AttendanceDTO>((int) pageInfo.getTotal(), pageInfo.getPageNum(), pageInfo.getPageSize(), resultList);



    }

    @Override
    public void saveAttendances(List<Attendance> attendances, String monthString) {



    }
}
