package com.ly.ems.service.attendance.impl;

import com.github.pagehelper.PageInfo;
import com.ly.ems.common.utils.DateUtil;
import com.ly.ems.common.utils.RandomUtil;
import com.ly.ems.core.exception.EMSBusinessException;
import com.ly.ems.dao.attendance.ExtendAttendanceMapper;
import com.ly.ems.dao.base.ExtendEmployeeMapper;
import com.ly.ems.model.attendance.AttendanceConditions;
import com.ly.ems.model.attendance.AttendanceVo;
import com.ly.ems.model.attendance.Attendance;
import com.ly.ems.model.attendance.constant.AttendanceConstant;
import com.ly.ems.model.attendance.constant.AttendanceStatusEnum;
import com.ly.ems.model.base.employee.EmployeeVo;
import com.ly.ems.model.common.PageableResult;
import com.ly.ems.model.common.constant.EnableEnum;
import com.ly.ems.service.attendance.AttendanceService;
import com.ly.ems.service.holiday.HolidayService;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by tidus on 2018/1/15.
 */
@Service
public class AttendanceServiceImpl implements AttendanceService {
    private static final Logger LOGGER = LoggerFactory.getLogger(AttendanceServiceImpl.class);


    @Autowired
    HolidayService holidayService;

    @Autowired
    ExtendEmployeeMapper extendEmployeeMapper;
    @Autowired
    ExtendAttendanceMapper extendAttendanceMapper;

    /**
     * 分页查询指定月份的考勤信息
     *
     * @param conditions
     * @return
     */
    @Override
    public PageableResult<AttendanceVo> getAttendances(AttendanceConditions conditions) {
        // 必须选定月份
        Date attendanceMonth = conditions.getAttendanceMonth();
        if (attendanceMonth == null) {
            throw new EMSBusinessException("查看考勤数据前必须先选定月份!");
        }

        // 考勤数据必须存在
        String attendanceMonthString = DateFormatUtils.format(attendanceMonth, DateUtil.YYYYMM);
        if (!this.checkAttendanceExist(attendanceMonthString)) {
            throw new EMSBusinessException(String.format("月份%s的考勤数据不存在，请先生成数据!", attendanceMonthString));
        }

        // 查询数据
        String attendanceTableName = AttendanceConstant.ATTENDANCE_TABLE_NAME_PRE + attendanceMonthString;
        List<AttendanceVo> resultList = extendAttendanceMapper.getAttendancesByConditions(conditions, attendanceTableName);
        PageInfo<AttendanceVo> pageInfo = new PageInfo(resultList);

        return new PageableResult<AttendanceVo>((int) pageInfo.getTotal(), pageInfo.getPageNum(), pageInfo.getPageSize(), resultList);

    }

    /**
     * 自动随机生成指定月份的考勤数据
     *
     * @param conditions
     */
    @Override
    public void generateAttendances(AttendanceConditions conditions) {
        Date attendanceMonth = conditions.getAttendanceMonth();
        if (attendanceMonth == null) {
            throw new EMSBusinessException("生成考勤数据前必须先选定月份!");
        }

        String attendanceMonthString = DateFormatUtils.format(attendanceMonth, DateUtil.YYYYMM);
        if (this.checkAttendanceExist(attendanceMonthString)) {
            throw new EMSBusinessException(String.format("月份%s的考勤数据已存在!", attendanceMonthString));
        }

        try {
            this.createAttendanceTable(attendanceMonth);
        } catch (Exception e) {
            LOGGER.error("创建考勤表失败", e);
            throw new EMSBusinessException("创建考勤表失败");
        }

        // 获取当月有派遣关系的员工
        List<EmployeeVo> employeeVoList = extendEmployeeMapper.getDispatchedEmployeeByMonth(attendanceMonth);

        boolean isContinue = true;
        int pageIndex = 1;
        int pageSize = 500;
        while (isContinue) {
            // 分页获取员工信息
            int formIndex = (pageIndex - 1) * pageSize;
            int toIndex = pageIndex * pageSize;
            if (toIndex > employeeVoList.size()) {
                toIndex = employeeVoList.size();
                isContinue = false;
            }
            List<EmployeeVo> employeeVoPage = employeeVoList.subList(formIndex, toIndex);

            // 为每个员工生成当月的考勤信息
            List<Attendance> attendanceList = new ArrayList<Attendance>();
            for (EmployeeVo employeeVo : employeeVoPage) {
                Attendance attendance = this.generateAttendanceForEmployeeRamdonly(employeeVo, attendanceMonth);
                attendanceList.add(attendance);
            }

        }



    }


    @Override
    public void saveAttendances(List<Attendance> attendances, String monthString) {


    }


    /**
     * 为单个员工随机生成考勤记录
     * 0、不考虑上一个月的情况。
     * 1、周一到周五都正常上班。
     * 2、周六在上周日加班的情况下，必定休息；不然有30%的机会需要加班，
     * 3、周日在前一天加班的情况下，必定休息；不然有30%的机会需要加班。
     * 4、
     *
     * @param employeeVo
     * @return
     */
    private Attendance generateAttendanceForEmployeeRamdonly(EmployeeVo employeeVo, Date attendanceMonth) {
        Date[] monthDates = DateUtil.getMonthBoundary(attendanceMonth);
        Date startDate = monthDates[0];
        Date endDate = monthDates[1];

        Date nowDate = startDate;
        Map<String, Object> attendanceMap = new HashMap();
        while (!nowDate.after(endDate)) {
            // 几号
            int monthDay = DateUtil.getDayOfMonth(nowDate);
            // 周几
            int weekDay = DateUtil.getDayOfWeek(nowDate);

            // 用于设置出勤
            final String keyString = String.format("%s%d", AttendanceConstant.ATTENDANCE_STATUS_KEY_PRE, monthDay);

            // 是否强制上班日
            if (holidayService.isForceWorkingDate(nowDate)) {
                attendanceMap.put(keyString, AttendanceStatusEnum.ATTENDANCE);
                continue;
            }

            // 是否节假日
            if (holidayService.isHoliday(nowDate)) {
                attendanceMap.put(keyString, AttendanceStatusEnum.VACATION);
                continue;
            }

            // 周一到周五正常出勤
            if (1 <= weekDay && weekDay <= 5) {
                attendanceMap.put(keyString, AttendanceStatusEnum.ATTENDANCE);
                continue;
            }

            // 周六
            if (weekDay == 6) {
                // TODO 上周日有没有加班
                attendanceMap.put(keyString,
                        RandomUtil.lottery(AttendanceConstant.OVERTIME_PROBABILITY) ?
                                AttendanceStatusEnum.OVERTIME : AttendanceStatusEnum.VACATION);
                continue;
            }

            // 周日
            if (weekDay == 0) {
                if (monthDay == 1) {
                    // 第一天，直接看看要不要加班
                    attendanceMap.put(keyString,
                            RandomUtil.lottery(AttendanceConstant.OVERTIME_PROBABILITY) ?
                                    AttendanceStatusEnum.OVERTIME : AttendanceStatusEnum.VACATION);
                } else {
                    // 看看前一天周六有没有加班
                    final String preDayKeyString = String.format("%s%d", AttendanceConstant.ATTENDANCE_STATUS_KEY_PRE, monthDay - 1);
                    AttendanceStatusEnum preAttendanceStatus = (AttendanceStatusEnum) attendanceMap.get(preDayKeyString);
                    if (preAttendanceStatus == AttendanceStatusEnum.OVERTIME) {
                        // 前一天周六加班了，必须休息
                        attendanceMap.put(keyString, AttendanceStatusEnum.VACATION);
                    } else {
                        attendanceMap.put(keyString,
                                RandomUtil.lottery(AttendanceConstant.OVERTIME_PROBABILITY) ?
                                        AttendanceStatusEnum.OVERTIME : AttendanceStatusEnum.VACATION);
                    }
                }
                continue;
            }

            nowDate = DateUtil.nextDay(nowDate);
        }

        Attendance attendance = new AttendanceVo(attendanceMap);
        attendance.setEnable(EnableEnum.ENABLED);
        attendance.setEmployeeId(employeeVo.getId());
        return attendance;
    }


    /**
     * 查询指定月份的考勤数据是否存在
     *
     * @param attendanceMonthString
     */
    private boolean checkAttendanceExist(String attendanceMonthString) {
        String attendanceTableName = AttendanceConstant.ATTENDANCE_TABLE_NAME_PRE + attendanceMonthString;
        int exists = extendAttendanceMapper.isExistAttendanceTable(attendanceTableName);
        return exists > 0;
    }

    private boolean checkAttendanceExist(Date attendanceMonth) {
        String attendanceMonthString = DateFormatUtils.format(attendanceMonth, DateUtil.YYYYMM);
        return this.checkAttendanceExist(attendanceMonthString);
    }

    /**
     * 创建指定月份的考勤表
     * @param attendanceMonth
     * @return
     */
    private void createAttendanceTable(Date attendanceMonth) {
        String attendanceMonthString = DateFormatUtils.format(attendanceMonth, DateUtil.YYYYMM);
        String attendanceTableName = AttendanceConstant.ATTENDANCE_TABLE_NAME_PRE + attendanceMonthString;
        extendAttendanceMapper.createAttendanceTable(attendanceTableName);
    }
}
